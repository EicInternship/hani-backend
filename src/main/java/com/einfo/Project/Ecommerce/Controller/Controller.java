package com.einfo.Project.Ecommerce.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.einfo.Project.Ecommerce.Exception.UserAlaradyExiest;
import com.einfo.Project.Ecommerce.Model.Category;
import com.einfo.Project.Ecommerce.Model.Product;
import com.einfo.Project.Ecommerce.Model.User;
import com.einfo.Project.Ecommerce.dto.Categorydto;
import com.einfo.Project.Ecommerce.dto.UserRequest;
import com.einfo.Project.Ecommerce.repo.ProductrRepo;
import com.einfo.Project.Ecommerce.repo.UserRepo;
import com.einfo.Project.Ecommerce.service.Categoryservice;
import com.einfo.Project.Ecommerce.service.ImageService;
import com.einfo.Project.Ecommerce.service.userService;

import jakarta.validation.Valid;
@CrossOrigin("http://localhost:3000/")
@RestController
public class Controller {
	
     @Autowired
     userService uservice;
     
     @Autowired 
      UserRepo repo;
     
     @Autowired 
     ImageService service;
     
     @Autowired
     ProductrRepo productrepo;
     
     @Autowired
     Categoryservice cService;
     
//     @PostMapping("/signup")
//	public User saveuser(@RequestBody User user)
//	{  
//    	 return repo.save(user);
//	}
     @PostMapping("/signup")
     public  ResponseEntity<User>saveuser(@RequestBody @Valid UserRequest userRequest) throws UserAlaradyExiest {
    
    	 return  new ResponseEntity<>(uservice.saveuser(userRequest),HttpStatus.CREATED);
     }
     
    @PostMapping("/login")
	public ResponseEntity<User>getLogin(@RequestBody User user){
    	User userdata =uservice.userlogin(user.getEmail());
    	if(userdata==null) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    	}
    	else if(user.getEmail().equals(userdata.getEmail())&&user.getPassword().equals(userdata.getPassword())) {
    		return ResponseEntity.ok().build();
    	}
    	else {
    	 return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
         }

    }
    
	@PostMapping("image/add")
	public ResponseEntity<String> productImageupload(@RequestParam("file") MultipartFile file,
		    @RequestParam("pname") String productName,
		    @RequestParam("description") String Description,
		    @RequestParam("price") int productPrice,
		    @RequestParam("category") String category ) throws IOException {
		String imagename = service.uploadImage(file);
		  
		   Product newproduct =productrepo.save(Product.builder().pname(productName)
				   .description(Description)
				   .price(productPrice)
				   .category(category)
				   .pimagename(imagename).build());
		return ResponseEntity.status(HttpStatus.OK).body("prodct and image uploaded successfuly");
			
	}

	@GetMapping("/{fileName}")
	public ResponseEntity<?> downloadImage(@PathVariable String fileName){
		byte[] imageData=service.downloadImage(fileName);
		return ResponseEntity.status(HttpStatus.OK)
				.contentType(MediaType.valueOf("image/png"))
				.body(imageData);

	}
	@GetMapping("/product")
	public List<Product>getproduct(){
	     return uservice.getallproduct();
		
	}
	
	@PostMapping("/addcategory")
	   public  ResponseEntity<Category>categorysave(@RequestBody @Valid Categorydto categorydto){
   	 return  new ResponseEntity<>(cService.savecategory(categorydto),HttpStatus.CREATED);
    }
    
	@GetMapping("/category")
	public ResponseEntity<List<Category>> getAllCategory() {
	    List<Category> categories = cService.getcategory();
	    return ResponseEntity.ok().body(categories);
	}
    
    
	
   
}
