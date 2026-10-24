package com.auca.pet_shop_management_system.repository;

import com.auca.pet_shop_management_system.domain.GroomingSession;
import com.auca.pet_shop_management_system.domain.ProductSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ProductSaleRepository extends JpaRepository<ProductSale, UUID> {

    @Query("SELECT d FROM ProductSale d WHERE d.groomingSession.petOwner.ownerCode = :ownerCode ORDER BY d.soldDate DESC")
    List<ProductSale> findProductSalesByOwnerCode(@Param("ownerCode") String ownerCode);

    boolean existsByGroomingSession(GroomingSession groomingSession);
}