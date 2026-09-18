package com.V.FBasket.VnFBasket.service;

import com.V.FBasket.VnFBasket.model.Categories;

import java.util.List;

public interface CategoriesService {

    Categories addCategory(Categories category);
    Categories getCategoryById(Long categoryId);
    Categories updateCategory(Categories category, Long categoryId);
    boolean deleteCategory(Long categoryId);
    Categories getCategoryByName(String categoryName);
    List<Categories> getAllCategories();
}
