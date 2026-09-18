package com.V.FBasket.VnFBasket.dao;

import com.V.FBasket.VnFBasket.model.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoriesRepository extends JpaRepository<Categories,Long> {

    Categories findByCategoryName(String categoryName);

    
}
