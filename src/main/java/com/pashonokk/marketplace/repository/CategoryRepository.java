package com.pashonokk.marketplace.repository;

import com.pashonokk.marketplace.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("Select c from Category c left join fetch c.subCategories where c.id=:id")
    Optional<Category> findByIdWithSubCategories(@Param("id") Long id);
}
