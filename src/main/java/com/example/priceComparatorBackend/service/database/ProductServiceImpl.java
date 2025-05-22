package com.example.priceComparatorBackend.service.database;

import com.example.priceComparatorBackend.dao.database.ProductRepository;
import com.example.priceComparatorBackend.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ProductServiceImpl implements ProductService{

    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository theProductRepository){
        productRepository = theProductRepository;
    }

    @Override
    public List<Product> findAll(){
        return productRepository.findAll();
    }


    @Override
    public Product findById(long theId){
       Optional<Product> result =   productRepository.findById(theId);
        Product theProduct = null;

       if(result.isPresent()){
           theProduct = result.get();
       }else{
           throw new RuntimeException("Did not find Product id  - " + theId);
       }
       return theProduct;
    }

    @Override
    public Product save(Product theProduct){
        return productRepository.save(theProduct);
    }

    @Override
    public void deleteById(long theId){
        productRepository.deleteById(theId);
    }

    @Override
    public boolean existsById(Long id){
        return productRepository.existsById(id);
    }
}
