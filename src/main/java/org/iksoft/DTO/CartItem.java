package org.iksoft.DTO;

/**
 * @author IK
 */

public class CartItem {
    private String name;
    private Integer quantity;
    private Double curPrice;

    public CartItem() {
    }

    public CartItem(String name, Integer quantity, Double curPrice) {
        this.name = name;
        this.quantity = quantity;
        this.curPrice = curPrice;
    }

    public void plusOne(){
        this.quantity++;
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

    public Double getCurPrice() {
        return curPrice;
    }

    public void setCurPrice(Double curPrice) {
        this.curPrice = curPrice;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                ", curPrice=" + curPrice +
                '}';
    }
}
