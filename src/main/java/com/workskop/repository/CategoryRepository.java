package com.workskop.repository;

import com.workskop.entity.CategoryService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryService, Long> {

}
