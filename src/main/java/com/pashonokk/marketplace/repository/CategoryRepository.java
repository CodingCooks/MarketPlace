package com.pashonokk.marketplace.repository;

import com.pashonokk.marketplace.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
