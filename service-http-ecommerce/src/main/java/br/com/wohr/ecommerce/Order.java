package br.com.wohr.ecommerce;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Order {

    private final String orderId, email;
    private final BigDecimal amount;

}
