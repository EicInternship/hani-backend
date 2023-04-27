package com.einfo.Project.Ecommerce.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

	public User saveuser(UserRequest userRequest) throws UserAlaradyExiest {
	    Optional<User> li = repo.findByEmail(userRequest.getEmail());
		BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
		 String brepctpassword=passwordEncoder.encode(userRequest.getPassword());
		if (!li.isEmpty()) {
			throw new UserAlaradyExiest("user Email:" + userRequest.getEmail() + "user is already present");
		}

		else {
			User user = User.build(0, userRequest.getFirstName(), userRequest.getLastName(), userRequest.getPhonenum(),
					userRequest.getEmail(),brepctpassword);
			return repo.save(user);
		}
	}

//	public User userlogin(String email, String password) {
//		BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
//          List<User> li=repo.findByEmail(email);
//		
//		if (repo.findByEmail(email).size() > 0) {
//			User dbuser=li.get(0);
//			if (passwordEncoder.matches(password, dbuser.getPassword())) {
//				System.out.println("password mathch");
//				return  dbuser;
//			}
//			else {
//				System.out.println("password not match");
//				return null;
//			}
//		}
//		return null;
//
//	}

	public List<Product> getallproduct() {
		return productrepo.findAll();
	}
	 public Product getProductById(int id) {
	         return productrepo.findById(id).get(0);
	         }
	   
}
