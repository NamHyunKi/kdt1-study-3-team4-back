package com.example.demo.product.service;

import com.example.demo.Account.entity.Account;
import com.example.demo.Account.repository.AccountRepository;
import com.example.demo.Account.repository.UserTokenRepository;
import com.example.demo.Account.repository.UserTokenRepositoryImpl;
import com.example.demo.product.dto.ProductDTO;
import com.example.demo.product.entity.Product;
import com.example.demo.product.entity.ProductImages;
import com.example.demo.product.form.ModifyRequestProductForm;
import com.example.demo.product.form.RegisterRequestProductForm;
import com.example.demo.product.repository.ImageRepository;
import com.example.demo.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.demo.Account.entity.RoleType.BUSINESS;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    final private ProductRepository productRepository;
    final private UserTokenRepository userTokenRepository = UserTokenRepositoryImpl.getInstance();
    final private AccountRepository accountRepository;
    final private ImageRepository imageRepository;

    @Transactional
    @Override
    public Boolean register(RegisterRequestProductForm info, List<MultipartFile> fileList) {
        log.info("service.register()");
        log.info(info.getUserToken());
        Long maybeBusinessId = userTokenRepository.findAccountIdByToken(info.getUserToken());
        log.info(String.valueOf(maybeBusinessId));

        Optional<Account> maybeBusiness = accountRepository.findById(maybeBusinessId);
        if(maybeBusiness.isEmpty()){
            log.info("noAccount");
            return false;
        }
        if(maybeBusiness.get().getAccountRole().getRole().getRoleType() == BUSINESS){
            Product savedProduct = productRepository.save(info.toProduct());

            try{
                for(MultipartFile multipartFile: fileList){

                    String originalFileName = multipartFile.getOriginalFilename();
                    log.info("requestFilename: " +multipartFile.getOriginalFilename());
                    String currentPath = System.getProperty("user.dir");
                    log.info(currentPath);
                    FileOutputStream writer =new FileOutputStream(
                            "./src/main/java/com/example/demo/UploadImgs/" +
                                    //"../../../Vue/YeoulCho/frontend/src/assets/uploadImgs" +
                                    multipartFile.getOriginalFilename()
                    );
                    //하드디스크에 multipartFile을 기록할꺼야 경로랑 가져온 이름으로
                    writer.write(multipartFile.getBytes());
                    writer.flush();
                    //byte파일로 기록할꺼야
                    writer.close();
                    //기록 끝! 그만할꺼야
                    ProductImages image = new ProductImages(originalFileName, savedProduct);
                    //fileTest 엔티티에 파일정보를 저장하겠다
                    log.info(originalFileName);
                    imageRepository.save(image);


                }
            }catch(FileNotFoundException e){
                e.printStackTrace();
                //파일이 존재하지 않을 때 에러 프린트해라
                return false;
            }catch(IOException e){
                e.printStackTrace();
                //입출력 에러(IO)않을 때 에러 프린트해라
                return false;
            }
        }else {log.info("noBusiness");
            return false;}

       return true;





    }

    @Override
    @Transactional
    public ProductDTO read(Long productId) {
        final Optional<Product> maybeProduct = productRepository.findById(productId);

        if (maybeProduct.isEmpty()) {
            return null;
        }
        final Product product = maybeProduct.get();

        final List<ProductImages> productImagesList =
                imageRepository.findImagePathByProductId(productId);

        log.info("productImagesList: " + productImagesList);

        ProductDTO productDTO = new ProductDTO(product);

        return productDTO;
    }

    @Override
    @Transactional
    public List<ProductDTO> list() {
        List<Product> productList = productRepository.findAll();

        List<ProductDTO> productDTOs = new ArrayList<>();
        for(Product product : productList){
            ProductDTO productDTO = new ProductDTO(product);
            productDTOs.add(productDTO);
            log.info(String.valueOf(productDTO));
        }

        return productDTOs;
    }

    @Override
    public Boolean modify(Long productId, List<MultipartFile> fileList, ModifyRequestProductForm info) {
        Optional<Product> maybeProduct = productRepository.findById(productId);

        if(maybeProduct.isEmpty()){
            return false;
        }
        log.info("value" + info.getProductName() + info.getProductPrice() + info.getProductDetails());

        Product product = maybeProduct.get();
        product.setProductName(info.getProductName());
        product.setProductPrice(info.getProductPrice());
        product.setProductDetails(info.getProductDetails());
        product.setVendor(info.getVendor());

        Product savedProduct = productRepository.save(product);

        imageRepository.deleteByProductId(productId);
        try{
            for(MultipartFile multipartFile: fileList){

                String originalFileName = multipartFile.getOriginalFilename();
                log.info("requestFilename: " +multipartFile.getOriginalFilename());
                String currentPath = System.getProperty("user.dir");
                log.info(currentPath);
                FileOutputStream writer =new FileOutputStream(
                        "./demo/src/main/java/com/example/demo/UploadImgs/" +
                                //"../../../Vue/YeoulCho/frontend/src/assets/uploadImgs" +
                                multipartFile.getOriginalFilename()
                );
                //하드디스크에 multipartFile을 기록할꺼야 경로랑 가져온 이름으로
                writer.write(multipartFile.getBytes());
                //byte파일로 기록할꺼야
                writer.close();
                //기록 끝! 그만할꺼야
                ProductImages image = new ProductImages(originalFileName, savedProduct);
                //fileTest 엔티티에 파일정보를 저장하겠다
                log.info(originalFileName);
                imageRepository.save(image);

            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
            //파일이 존재하지 않을 때 에러 프린트해라
            return false;
        }catch(IOException e){
            e.printStackTrace();
            //입출력 에러(IO)않을 때 에러 프린트해라
            return false;
        }


        return true;
    }


}
