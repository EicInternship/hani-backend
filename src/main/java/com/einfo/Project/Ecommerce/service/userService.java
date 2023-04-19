package com.einfo.Project.Ecommerce.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.einfo.Project.Ecommerce.Exception.UserAlaradyExiest;
import com.einfo.Project.Ecommerce.Model.Image;
import com.einfo.Project.Ecommerce.Model.Product;
import com.einfo.Project.Ecommerce.Model.User;
import com.einfo.Project.Ecommerce.dto.UserRequest;
import com.einfo.Project.Ecommerce.repo.ProductrRepo;
import com.einfo.Project.Ecommerce.repo.UserRepo;

@Service
public class userService {
   @Autowired
   UserRepo repo;
   
   @Autowired
   ProductrRepo productrepo;
   
    public User saveuser( UserRequest userRequest ) throws UserAlaradyExiest {
      User u=new User();
    if(repo.findByEmail(userRequest.getEmail())!=null) throw new UserAlaradyExiest("user Email:"+userRequest.getEmail()+"user is already present");
    else { User user= User
    		  .build(0, userRequest.getFirstName(),
    				  userRequest.getLastName(),userRequest.getPhonenum(),
    				  userRequest.getEmail(), userRequest.getPassword());
      return repo.save(user);}
    }
     
   
   public User userlogin(String email)
   {    
	  return  repo.findByEmail(email).get(0);
   }
     

   
   public List<Product>getallproduct(){
	   return productrepo.findAll();
   }
}
