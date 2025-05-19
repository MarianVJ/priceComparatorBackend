package com.example.priceComparatorBackend.service;

import com.example.priceComparatorBackend.dao.BrandRepository;
import com.example.priceComparatorBackend.dao.CategoryRepository;
import com.example.priceComparatorBackend.entity.Brand;
import com.example.priceComparatorBackend.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CategoryServiceImpl implements CategoryService{

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository theBrandRepository){
        categoryRepository = theBrandRepository;
    }

    @Override
    public List<Category> findAll(){
        return categoryRepository.findAll();
    }


    @Override
    public Category findById(long theId){
       Optional<Category> result =   categoryRepository.findById(theId);
        Category theCategory = null;

       if(result.isPresent()){
           theCategory = result.get();
       }else{
           throw new RuntimeException("Did not find Category id  - " + theId);
       }
       return theCategory;
    }

    @Override
    public Category save(Category theCategory){
        return categoryRepository.save(theCategory);
    }

    @Override
    public void deleteById(long theId){
        categoryRepository.deleteById(theId);
    }


    @Override
    public Optional<Category> findByName(String name){
        return categoryRepository.findByName(name);
    }
}
