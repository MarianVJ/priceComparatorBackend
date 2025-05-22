package com.example.priceComparatorBackend.service.database;

import com.example.priceComparatorBackend.dao.database.StoreDateBatchProductRepository;
import com.example.priceComparatorBackend.entity.StoreDateBatchProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class StoreDateBatchProductServiceImpl
        implements StoreDateBatchProductService {

    private StoreDateBatchProductRepository storeDateBatchProductRepository;

    @Autowired
    public StoreDateBatchProductServiceImpl(
            StoreDateBatchProductRepository theStoreDateBatchProductRepository){
        storeDateBatchProductRepository = theStoreDateBatchProductRepository;
    }

    @Override
    public List<StoreDateBatchProduct> findAll(){
        return storeDateBatchProductRepository.findAll();
    }


    @Override
    public StoreDateBatchProduct findById(long theId){
       Optional<StoreDateBatchProduct> result =   storeDateBatchProductRepository.findById(theId);
        StoreDateBatchProduct theStoreDateBatchProduct = null;

       if(result.isPresent()){
           theStoreDateBatchProduct = result.get();
       }else{
           throw new RuntimeException("Did not find StoreProduct id  - " + theId);
       }
       return theStoreDateBatchProduct;
    }

    @Override
    public StoreDateBatchProduct save(
            StoreDateBatchProduct theStoreDateBatchProduct){
        return storeDateBatchProductRepository.save(theStoreDateBatchProduct);
    }

    @Override
    public void deleteById(long theId){
        storeDateBatchProductRepository.deleteById(theId);
    }

    @Override
    public Long count() {
        return storeDateBatchProductRepository.count();
    }
}
