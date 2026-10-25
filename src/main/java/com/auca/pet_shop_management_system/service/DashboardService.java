package com.auca.pet_shop_management_system.service;

import com.auca.pet_shop_management_system.domain.SessionStatus;
import com.auca.pet_shop_management_system.repository.GroomerRepository;
import com.auca.pet_shop_management_system.repository.GroomingBayRepository;
import com.auca.pet_shop_management_system.repository.GroomingSessionRepository;
import com.auca.pet_shop_management_system.repository.PetOwnerRepository;
import com.auca.pet_shop_management_system.repository.ProductSaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    private PetOwnerRepository petOwnerRepository;

    @Autowired
    private GroomerRepository groomerRepository;

    @Autowired
    private GroomingBayRepository groomingBayRepository;

    @Autowired
    private GroomingSessionRepository groomingSessionRepository;

    @Autowired
    private ProductSaleRepository productSaleRepository;

    public Map<String, Long> summary() {
        Map<String, Long> summary = new LinkedHashMap<>();
        summary.put("petOwners", petOwnerRepository.count());
        summary.put("groomers", groomerRepository.count());
        summary.put("freeBays", (long) groomingBayRepository.findFreeGroomingBays().size());
        summary.put("sessionsToday", groomingSessionRepository.countBySessionDate(LocalDate.now()));
        summary.put("booked", groomingSessionRepository.countByStatus(SessionStatus.BOOKED));
        summary.put("productSales", productSaleRepository.count());
        return summary;
    }
}