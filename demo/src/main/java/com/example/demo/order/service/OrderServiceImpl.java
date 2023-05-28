package com.example.demo.order.service;

import com.example.demo.Account.dto.AccountDTO;
import com.example.demo.Account.entity.Account;
import com.example.demo.Account.repository.AccountRepository;
import com.example.demo.order.dto.OrderDTO;
import com.example.demo.order.entity.Order;
import com.example.demo.order.repository.OrderRepository;
import com.example.demo.product.dto.ProductDTO;
import com.example.demo.product.entity.Product;
import com.example.demo.product.entity.ProductImages;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    final private OrderRepository orderRepository;
    final private AccountRepository accountRepository;
    @Transactional
    @Override
    public List<OrderDTO> list(long accountId) {
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isEmpty()) {
            log.info("정보가 없습니다!");
            return null;
        }
        List<Order> orders = orderRepository.findByAccount(account.get(), Sort.by(Sort.Direction.DESC, "orderId"));
        List<OrderDTO> orderDTOs = new ArrayList<>();

        for (Order order : orders) {
            OrderDTO orderDTO = new OrderDTO(
                    order.getOrderId(),
                    new AccountDTO(order.getAccount()),
                    new ProductDTO(order.getProduct()),
                    order.getOrderStatus(),
                    order.getDeliveryStatus(),
                    order.getCreateDate()
            );

            orderDTOs.add(orderDTO);
        }

        return orderDTOs;
    }
    @Transactional
    @Override
    public OrderDTO read(Long orderId) {
        Optional<Order> maybe = orderRepository.findById(orderId);
        if (maybe.isEmpty()) {
            log.info("정보가 없습니다!");
            return null;
        }
        Order order = maybe.get();
        Product product = order.getProduct();
        List<ProductImages> pi = product.getProductImagesList();
        log.info(pi.toString());
        OrderDTO orderDTO = new OrderDTO(
            order.getOrderId(),
            new AccountDTO(order.getAccount()),
            new ProductDTO(product),
            order.getOrderStatus(),
            order.getDeliveryStatus(),
            order.getCreateDate()
        );

        return orderDTO;
    }

    @Override
    public int delete(Long orderId) {
        Optional<Order> maybe = orderRepository.findById(orderId);
        if (maybe.isEmpty()) {
            log.info("정보가 없습니다!");
            return -1;
        }
        Order order = maybe.get();
        order.setOrderStatus("-1");
        orderRepository.save(order);
        return 1;
    }
}