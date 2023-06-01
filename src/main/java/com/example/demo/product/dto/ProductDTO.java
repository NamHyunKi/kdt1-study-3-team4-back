package com.example.demo.product.dto;

import com.example.demo.product.entity.Product;
import com.example.demo.product.entity.ProductImages;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {
    private Long productId;
    private String productName;
    private Integer productPrice;
    private String vendor;
    private String productDetails;
    private List<String> productImagesPathList;
    public ProductDTO(Product product) {
        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.productPrice = product.getProductPrice();
        List<ProductImages> productImages = product.getProductImagesList();
        List<String> imagePaths = new ArrayList<String>();
        for( ProductImages productImage : productImages ) {
            imagePaths.add(productImage.getImageResourcePath());
        }
        this.productImagesPathList = imagePaths;
        this.vendor = product.getVendor();
        this.productDetails = product.getProductDetails();
    }
}