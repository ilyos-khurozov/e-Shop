package org.iksoft.DTO;

import java.util.LinkedList;
import java.util.List;

/**
 * @author IK
 *
 * DTO class to get data about product
 * ids and quantities
 */

public class PlaceOrderDTO {

    public PlaceOrderDTO() {
        items = new LinkedList<>();
    }

    private List<Item> items;

    public List<Item> getItems() {
        return this.items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void addItem(Item item){
        this.items.add(item);
    }

    @Override
    public String toString() {
        return "PlaceOrderDTO{" +
                "items=" + items +
                '}';
    }

    public static class Item{
        private Integer productId;
        private Integer quantity;
        private Double curPrice;

        public Item() {
        }

        public Item(Integer productId, Integer quantity, Double curPrice) {
            this.productId = productId;
            this.quantity = quantity;
            this.curPrice = curPrice;
        }

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public Double getCurPrice() {
            return curPrice;
        }

        public void setCurPrice(Double curPrice) {
            this.curPrice = curPrice;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "productId=" + productId +
                    ", quantity=" + quantity +
                    ", curPrice=" + curPrice +
                    '}';
        }
    }
}
