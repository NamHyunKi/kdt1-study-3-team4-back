package com.example.demo.product.form;

import com.example.demo.product.entity.Product;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class ModifyRequestProductForm {
    private String productName;
    private Integer productPrice;
    private String vendor;
    private String productDetails;

    public Product toProduct() {
        return new Product(productName, productPrice, vendor, productDetails);
    }

}
