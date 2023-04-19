package com.einfo.Project.Ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.einfo.Project.Ecommerce.Model.Product;

public interface ProductrRepo extends JpaRepository<Product,Integer>{

}
