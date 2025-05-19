package com.example.priceComparatorBackend.service;

import com.example.priceComparatorBackend.dao.StoreDateBatchRepository;
import com.example.priceComparatorBackend.dao.StoreProductRepository;
import com.example.priceComparatorBackend.entity.StoreDateBatch;
import com.example.priceComparatorBackend.entity.StoreProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoreDateBatchServiceImpl implements StoreDateBatchService{

    private StoreDateBatchRepository storeDateBatchRepository;

    @Autowired
    public StoreDateBatchServiceImpl(StoreDateBatchRepository theStoreDateBatchRepository){
        storeDateBatchRepository = theStoreDateBatchRepository;
    }

    @Override
    public List<StoreDateBatch> findAll(){
        return storeDateBatchRepository.findAll();
    }


    @Override
    public StoreDateBatch findById(long theId){
        Optional<StoreDateBatch> result =   storeDateBatchRepository.findById(theId);
        StoreDateBatch theStoreDateBatch = null;

        if(result.isPresent()){
            theStoreDateBatch = result.get();
        }else{
            throw new RuntimeException("Did not find StoreProduct id  - " + theId);
        }
        return theStoreDateBatch;
    }

    @Override
    public StoreDateBatch save(StoreDateBatch theStoreDateBath){
        return storeDateBatchRepository.save(theStoreDateBath);
    }

    @Override
    public void deleteById(long theId){
        storeDateBatchRepository.deleteById(theId);
    }
}
