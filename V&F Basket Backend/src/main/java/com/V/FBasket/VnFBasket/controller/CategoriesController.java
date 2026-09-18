package com.V.FBasket.VnFBasket.controller;


import com.V.FBasket.VnFBasket.model.Categories;
import com.V.FBasket.VnFBasket.serviceImpl.CategoriesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vnfbasket")
public class CategoriesController {

    @Autowired
    private CategoriesServiceImpl categoriesService;

    @PostMapping("/addCategory")
    public ResponseEntity<Categories> addCategory(@RequestBody Categories categories) {
        Categories category = categoriesService.addCategory(categories);
        if(category != null) {
            return new ResponseEntity<>(category, HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getCategoryById/{categoryId}")
    public ResponseEntity<Categories> getCategoryById(@PathVariable long categoryId) {
        Categories category = categoriesService.getCategoryById(categoryId);
        if (category != null) {
            return new ResponseEntity<>(category, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateCategory/{categoryId}")
    public ResponseEntity<Categories> updateCategory(@RequestBody Categories category, @PathVariable long categoryId) {
        Categories updatedCategory = categoriesService.updateCategory(category, categoryId);
        if (updatedCategory != null) {
            return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteCategory/{categoryId}")
    public ResponseEntity<Categories> deleteCategory(@PathVariable long categoryId) {
        Categories category = categoriesService.getCategoryById(categoryId);
        if(category!=null && categoriesService.deleteCategory(
                categoryId)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getCategoriesByName/{categoryName}")
    public ResponseEntity<Categories> getCategoryByName(@PathVariable String categoryName) {
        Categories category = categoriesService.getCategoryByName(categoryName);
        if (category != null) {
            return new ResponseEntity<>(category, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAllCategories")
    public ResponseEntity<List<Categories>> getAllCategories() {
        List<Categories> categories = categoriesService.getAllCategories();
        if (categories != null) {
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
