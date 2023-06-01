package com.example.demo.order.service;

import com.example.demo.Account.dto.AccountDTO;
import com.example.demo.Account.entity.Account;
import com.example.demo.Account.entity.Role;
import com.example.demo.Account.repository.AccountRepository;
import com.example.demo.Account.repository.AccountRoleRepository;
import com.example.demo.Account.repository.UserTokenRepository;
import com.example.demo.Account.repository.UserTokenRepositoryImpl;
import com.example.demo.order.dto.OrderDTO;
import com.example.demo.order.entity.Order;
import com.example.demo.order.repository.OrderRepository;
import com.example.demo.order.service.request.OrderRegisterRequest;
import com.example.demo.product.dto.ProductDTO;
import com.example.demo.product.entity.Product;
import com.example.demo.product.entity.ProductImages;
import com.example.demo.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.demo.Account.entity.RoleType.NORMAL;


@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    final private OrderRepository orderRepository;
    final private AccountRepository accountRepository;
    final private ProductRepository productRepository;
    final private AccountRoleRepository accountRoleRepository;
    final private UserTokenRepository userTokenRepository = UserTokenRepositoryImpl.getInstance();
    @Transactional
    @Override
    public List<OrderDTO> list(String userToken) {
        Long accountId = userTokenRepository.findAccountIdByToken(userToken);
        log.info(accountId.toString());
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

    @Override
    public Boolean register(Long id, OrderRegisterRequest orderRegisterRequest) {
        final Optional<Account> maybeAccount = accountRepository.findById(id);

        if (maybeAccount.isEmpty()) {
            return null;
        }

        final Account account = maybeAccount.get();
        final Role role = accountRoleRepository.findRoleInfoByAccount(account);

        if (role.getRoleType() != NORMAL) {
            return null;
        }

        final Optional<Product> maybeProduct = productRepository.findById(orderRegisterRequest.getProductId());

        if (maybeProduct.isEmpty()) {
            return null;
        }

        final Order order = new Order(account, maybeProduct.get());
        orderRepository.save(order);

        return true;
    }
}
