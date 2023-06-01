package com.example.demo.product.repository;

import com.example.demo.product.entity.ProductImages;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

public interface ImageRepository extends JpaRepository<ProductImages,Long> {
    @Query("select pi from ProductImages pi join fetch pi.product p where p.id = :productId")
    List<ProductImages> findImagePathByProductId(Long productId);

    @Modifying
    @Transactional
    @Query(value = "delete \n" +
            "from product_images\n" +
            "where product_id = :productId", nativeQuery = true)
    void deleteByProductId(Long productId);

//     "SELECT o.id, p.product_Name, p.product_Price, p.product_Details, create_date " +
//            "FROM orders o " +
//            "JOIN product p ON p.id = o.product_Id " +
//            "JOIN account a ON a.account_Id = o.account_account_Id " +
//            "WHERE a.account_Id = :accountId", nativeQuery = true)
}
