package org.iksoft.Service;

import org.iksoft.Entity.Customer;
import org.iksoft.Entity.User;
import org.iksoft.Exception.NotFoundException;
import org.iksoft.Repository.CustomerRepository;
import org.springframework.stereotype.Service;

/**
 * @author IK
 */

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer getCustomerByUsername(String username){
        return customerRepository.getCustomerByUserUsername(username).orElseThrow(
                new NotFoundException("Not found customer with username "+username)
        );
    }

    public void addCustomer(String name, String country, String address, Integer userId){
        Customer customer = new Customer();
        customer.setName(name);
        customer.setCountry(country);
        customer.setAddress(address);
        customer.setUser(new User());
        customer.getUser().setId(userId);

        customerRepository.save(customer);
    }

    public void updateCustomer(Customer customer){
        customerRepository.save(customer);
    }
}
