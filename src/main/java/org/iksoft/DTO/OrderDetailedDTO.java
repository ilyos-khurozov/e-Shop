package org.iksoft.DTO;

import java.sql.Date;
import java.util.List;

/**
 * @author IK
 *
 * DTO class to retrieve detailed list of order,
 * which contains name, quantity and price of products
 */

public class OrderDetailedDTO {

    private Integer orderId;
    private Date date;
    private List<Item> items;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public static class Item{
        private String name;
        private Integer quantity;
        private Double price;

        public Item() {}

        public Item(String name, Integer quantity, Double price) {
            this.name = name;
            this.quantity = quantity;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }
    }
}
