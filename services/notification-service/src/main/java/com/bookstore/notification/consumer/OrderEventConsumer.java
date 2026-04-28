package com.bookstore.notification.consumer;

import com.bookstore.notification.dto.OrderEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderEventConsumer {

    @KafkaListener(
            topics = "order-events",
            groupId = "notification-group-v2"
    )
    public void consume(OrderEvent event) {
        System.out.println("🔥 ORDER EVENT RECEIVED 🔥");
        System.out.println("Order ID: " + event.getOrderId());
        System.out.println("Product ID: " + event.getProductId());
        System.out.println("Type: " + event.getType());
        System.out.println("Timestamp: " + event.getTimestamp());
    }
}