package com.workskop.repository;

import com.workskop.entity.CategoryService;
import com.workskop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {


}
