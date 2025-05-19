package com.example.priceComparatorBackend.service;

import com.example.priceComparatorBackend.dao.StoreProductRepository;
import com.example.priceComparatorBackend.entity.StoreProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class StoreProductServiceImpl implements StoreProductService{

    private StoreProductRepository storeProductRepository;

    @Autowired
    public StoreProductServiceImpl(StoreProductRepository theStoreProductRepository){
        storeProductRepository = theStoreProductRepository;
    }

    @Override
    public List<StoreProduct> findAll(){
        return storeProductRepository.findAll();
    }


    @Override
    public StoreProduct findById(long theId){
       Optional<StoreProduct> result =   storeProductRepository.findById(theId);
        StoreProduct theStoreProduct = null;

       if(result.isPresent()){
           theStoreProduct = result.get();
       }else{
           throw new RuntimeException("Did not find StoreProduct id  - " + theId);
       }
       return theStoreProduct;
    }

    @Override
    public StoreProduct save(StoreProduct theStoreProduct){
        return storeProductRepository.save(theStoreProduct);
    }

    @Override
    public void deleteById(long theId){
        storeProductRepository.deleteById(theId);
    }
}
