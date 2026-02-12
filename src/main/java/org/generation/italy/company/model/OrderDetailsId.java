package org.generation.italy.company.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class OrderDetailsId implements Serializable {
    private Integer orderId;
    private Integer productId;

    public OrderDetailsId(Integer orderId, Integer productId) {
        this.orderId = orderId;
        this.productId = productId;
    }
}
