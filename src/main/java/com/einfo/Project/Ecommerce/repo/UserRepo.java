package com.einfo.Project.Ecommerce.repo;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.einfo.Project.Ecommerce.Model.User;

public interface UserRepo extends JpaRepository<User,Integer> {
      List<User> findByEmail(String email);
}
