package com.example.priceComparatorBackend.service.database;

import com.example.priceComparatorBackend.dao.database.BrandRepository;
import com.example.priceComparatorBackend.entity.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class BrandServiceImpl implements BrandService{

    private BrandRepository brandRepository;

    @Autowired
    public BrandServiceImpl(BrandRepository theBrandRepository){
        brandRepository = theBrandRepository;
    }

    @Override
    public List<Brand> findAll(){
        return brandRepository.findAll();
    }


    @Override
    public Brand findById(long theId){
       Optional<Brand> result =   brandRepository.findById(theId);
       Brand theBrand = null;

       if(result.isPresent()){
           theBrand = result.get();
       }else{
           throw new RuntimeException("Did not find Brand id  - " + theId);
       }
       return theBrand;
    }

    @Override
    public Brand save(Brand theBrand){
        return brandRepository.save(theBrand);
    }

    @Override
    public void deleteById(long theId){
        brandRepository.deleteById(theId);
    }

    @Override
    public Optional<Brand> findByName(String name){
        return brandRepository.findByName(name);
    }
}
