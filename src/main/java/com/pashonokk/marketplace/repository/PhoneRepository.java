package com.pashonokk.marketplace.repository;


import com.pashonokk.marketplace.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {

    @Modifying
    @Query("Update Phone p set p.isMain=false where p.user.id = :userId and p.isMain=true")
    void setOldMainPhoneAsNotMain(@Param("userId") Long userId);
}
