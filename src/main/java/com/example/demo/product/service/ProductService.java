package com.example.demo.product.service;

import com.example.demo.product.dto.ProductDTO;
import com.example.demo.product.form.ModifyRequestProductForm;
import com.example.demo.product.form.RegisterRequestProductForm;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {


    Boolean register(RegisterRequestProductForm info, List<MultipartFile> fileList);

    ProductDTO read(Long productId);

    List<ProductDTO> list();

    Boolean modify(Long productId, List<MultipartFile> fileList, ModifyRequestProductForm requestProductForm);
}
