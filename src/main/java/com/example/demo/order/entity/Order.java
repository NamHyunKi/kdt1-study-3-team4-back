package com.example.demo.order.entity;

import com.example.demo.Account.entity.Account;
import com.example.demo.product.entity.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
@Getter
@Entity
@Table(name = "order_entity")
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Setter
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Setter
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Setter
    private String orderStatus;

    @Setter
    private String deliveryStatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    @CreationTimestamp
    private LocalDateTime createDate;

    @JsonManagedReference // 이 어노테이션을 추가하여 연관된 Product 엔티티를 JSON에 포함합니다
    public Product getProduct() {
        return product;
    }



    public Order(String orderStatus, String deliveryStatus) {
        this.orderStatus = orderStatus;
        this.deliveryStatus = deliveryStatus;
    }

    public Order(Account account, Product product) {
        this.account = account;
        this.product = product;
        this.deliveryStatus = "1";
        this.orderStatus = "1";
    }

    public Order(Long orderId, Account account, Product product, String orderStatus, String deliveryStatus, LocalDateTime createDate) {
        this.orderId = orderId;
        this.account = account;
        this.product = product;
        this.orderStatus = orderStatus;
        this.deliveryStatus = deliveryStatus;
        this.createDate = createDate;
    }
}
