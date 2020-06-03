package org.iksoft.Service;

import org.iksoft.Entity.Order;
import org.iksoft.Entity.OrderItem;
import org.iksoft.Entity.Product;
import org.iksoft.Repository.OrderItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author IK
 */


@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public void addOrderItem(Integer orderId, Integer productId, Integer quantity, Double curPrice){
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(new Order());
        orderItem.getOrder().setId(orderId);
        orderItem.setProduct(new Product());
        orderItem.getProduct().setId(productId);
        orderItem.setQuantity(quantity);
        orderItem.setCurPrice(curPrice);
        orderItemRepository.save(orderItem);
    }

    public List<OrderItem> getAllByOrderId(Integer orderId){
        return orderItemRepository.findAllByOrderId(orderId);
    }
}
