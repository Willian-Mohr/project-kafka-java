package br.com.wohr.ecommerce;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Order {

    private final String userId, orderId;
    private final BigDecimal amount;

    public Order(String userId, String orderId, BigDecimal amount) {
        this.userId = userId;
        this.orderId = orderId;
        this.amount = amount;
    }
}
