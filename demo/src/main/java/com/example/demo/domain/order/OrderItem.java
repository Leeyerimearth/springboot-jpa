package com.example.demo.domain.order;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Long price;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    public OrderItem(int quantity, Order order, Item item) {
        this.quantity = quantity;
        this.order = order;
        order.getOrderItems().add(this);

        this.item = item;
        item.getOrderItems().add(this);

        this.price = item.getPrice() * quantity;
    }

    public void setOrder(Order order) {
        if(Objects.nonNull(this.order)) {
            this.order.getOrderItems().remove(this);
        }
        this.order = order;
        order.getOrderItems().add(this);
    }

    public void setItem(Item item) {
       if(Objects.nonNull(this.item)) {
           this.item.getOrderItems().remove(this);
       }
       this.item = item;
       item.getOrderItems().add(this);
    }

}
