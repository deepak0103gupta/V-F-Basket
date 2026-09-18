package com.V.FBasket.VnFBasket.serviceImpl;

import com.V.FBasket.VnFBasket.dao.CategoriesRepository;
import com.V.FBasket.VnFBasket.model.Categories;
import com.V.FBasket.VnFBasket.service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesServiceImpl implements CategoriesService {

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Override
    public Categories addCategory(Categories category) {
        try {
            return categoriesRepository.save(category);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Categories getCategoryById(Long categoryId) {
        try {
            return categoriesRepository.findById(categoryId).get();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Categories updateCategory(Categories category, Long categoryId) {
        try{
            Categories existingCategory = categoriesRepository.findById(categoryId).get();
            existingCategory.setCategoryName(category.getCategoryName());
            existingCategory.setCategoryDescription(category.getCategoryDescription());
            return categoriesRepository.save(existingCategory);

        }catch(Exception e){
            return null;
        }

    }

    @Override
    public boolean deleteCategory(Long categoryId) {
        try {
            categoriesRepository.deleteById(categoryId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Categories getCategoryByName(String categoryName) {
        try{
            return categoriesRepository.findByCategoryName(categoryName);
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public List<Categories> getAllCategories() {
        try{
            return categoriesRepository.findAll();
        } catch (Exception e) {
            return null;
        }
    }

}
