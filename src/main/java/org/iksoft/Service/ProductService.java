package org.iksoft.Service;

import org.iksoft.Entity.Product;
import org.iksoft.Exception.NotFoundException;
import org.iksoft.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

/**
 * @author IK
 */

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts(){
        List<Product> list = productRepository.findAll();

        list.sort(Comparator.comparing(Product::getName));

        return list;
    }

    public Product getProductById(Integer id){
        return productRepository.findById(id).orElseThrow(
                new NotFoundException("Not found product with id "+id)
        );
    }

    //update and add is almost the same
    public void addOrUpdateProduct(Product product){
        productRepository.save(product);
    }

    public void deleteProduct(Integer id){
        Product tmp = getProductById(id);
        tmp.setQuantity(0);
        addOrUpdateProduct(tmp);
    }
}
