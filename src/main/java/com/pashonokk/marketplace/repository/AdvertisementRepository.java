package com.pashonokk.marketplace.repository;

import com.pashonokk.marketplace.entity.Advertisement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    @EntityGraph(attributePaths = {"category", "subCategory"})
    Optional<Advertisement> findById(@Param("id") Long id);

    @EntityGraph(attributePaths = {"category", "subCategory"})
    Page<Advertisement> findAll(Pageable pageable);
}
