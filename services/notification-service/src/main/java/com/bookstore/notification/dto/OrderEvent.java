package com.bookstore.notification.dto;

import java.time.LocalDateTime;

public class OrderEvent {

    private Long orderId;
    private Long productId;
    private String type;
    private LocalDateTime timestamp;

    public OrderEvent() {}

    public Long getOrderId() { return orderId; }
    public Long getProductId() { return productId; }
    public String getType() { return type; }
    public LocalDateTime getTimestamp() { return timestamp; }

    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public void setType(String type) { this.type = type; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    @Override
    public String toString() {
        return "OrderEvent{" +
                "orderId=" + orderId +
                ", productId=" + productId +
                ", type='" + type + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}