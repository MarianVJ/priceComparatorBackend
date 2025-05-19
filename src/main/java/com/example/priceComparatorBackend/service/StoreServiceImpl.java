package com.example.priceComparatorBackend.service;

import com.example.priceComparatorBackend.dao.StoreRepository;
import com.example.priceComparatorBackend.entity.Brand;
import com.example.priceComparatorBackend.entity.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class StoreServiceImpl implements StoreService {

    private StoreRepository storeRepository;

    @Autowired
    public StoreServiceImpl(StoreRepository theStoreRepository){
        storeRepository = theStoreRepository;
    }

    @Override
    public List<Store> findAll(){
        return storeRepository.findAll();
    }


    @Override
    public Store findById(long theId){
       Optional<Store> result =   storeRepository.findById(theId);
       Store theStore = null;

       if(result.isPresent()){
           theStore = result.get();
       }else{
           throw new RuntimeException("Did not find Brand id  - " + theId);
       }
       return theStore;
    }

    @Override
    public Store save(Store theStore){
        return storeRepository.save(theStore);
    }

    @Override
    public void deleteById(long theId){
        storeRepository.deleteById(theId);
    }

    @Override
    public Optional<Store> findByName(String name){
        return storeRepository.findByName(name);
    }
}
