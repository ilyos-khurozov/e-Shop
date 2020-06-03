package org.iksoft.Service;

import org.iksoft.DTO.OrderDetailedDTO;
import org.iksoft.DTO.PlaceOrderDTO;
import org.iksoft.Entity.Customer;
import org.iksoft.Entity.Invoice;
import org.iksoft.Entity.Order;
import org.iksoft.Entity.OrderItem;
import org.iksoft.Exception.NotFoundException;
import org.iksoft.Repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

/**
 * @author IK
 */

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final OrderItemService orderItemService;

    public OrderService(OrderRepository orderRepository,
                        ProductService productService,
                        OrderItemService orderItemService){

        this.orderRepository = orderRepository;
        this.productService = productService;
        this.orderItemService = orderItemService;
    }

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    public List<Order> getAllByUserId(Integer userId){
        return orderRepository.findByCustomer_User_Id(userId);
    }

    public Invoice placeOrder(Integer customerId, PlaceOrderDTO placeOrderDTO){
        Order order = new Order();
        order.setDate(Date.valueOf(LocalDate.now()));
        order.setCustomer(new Customer());
        order.getCustomer().setId(customerId);

        order = orderRepository.save(order);

        double amount = 0.00;
        for(PlaceOrderDTO.Item item : placeOrderDTO.getItems()){
            amount += item.getQuantity()*
                    productService.getProductById(item.getProductId()).getPrice();

            orderItemService.addOrderItem(order.getId(),
                    item.getProductId(),item.getQuantity(), item.getCurPrice());
        }

        Invoice invoice =  new Invoice();
        invoice.setAmount(amount);
        invoice.setOrder(order);

        return invoice;
    }

    public OrderDetailedDTO getDetails(Integer orderId){
        Order order = orderRepository.findById(orderId).orElseThrow(
                new NotFoundException("Not found order with id "+orderId)
        );

        List<OrderItem> list = orderItemService.getAllByOrderId(orderId);
        List<OrderDetailedDTO.Item> items = new LinkedList<>();
        
        for(OrderItem orderItem : list){
            items.add(new OrderDetailedDTO.Item(
                    orderItem.getProduct().getName(),
                    orderItem.getQuantity(),
                    orderItem.getProduct().getPrice()
            ));
        }

        OrderDetailedDTO dto = new OrderDetailedDTO();
        dto.setOrderId(orderId);
        dto.setDate(order.getDate());
        dto.setItems(items);

        return dto;
    }
}
