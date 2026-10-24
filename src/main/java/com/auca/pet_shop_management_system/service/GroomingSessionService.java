package com.auca.pet_shop_management_system.service;

import com.auca.pet_shop_management_system.domain.GroomingSession;
import com.auca.pet_shop_management_system.domain.SessionStatus;
import com.auca.pet_shop_management_system.repository.GroomingSessionRepository;
import com.auca.pet_shop_management_system.repository.ProductSaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GroomingSessionService {

    @Autowired
    private GroomingSessionRepository groomingSessionRepository;

    @Autowired
    private ProductSaleRepository productSaleRepository;

    public String save(GroomingSession groomingSession) {
        if (groomingSession.getSessionDate().isBefore(LocalDate.now())) {
            return "Grooming session date cannot be in the past";
        }
        boolean alreadyBooked = groomingSessionRepository.existsByGroomerIdAndSessionDateAndStatusNot(
                groomingSession.getGroomer().getId(),
                groomingSession.getSessionDate(),
                SessionStatus.CANCELLED);
        if (alreadyBooked) {
            return "Groomer is already booked on that date";
        }
        groomingSession.setStatus(SessionStatus.BOOKED);
        groomingSessionRepository.save(groomingSession);
        return "Grooming session saved successfully";
    }

    public List<GroomingSession> findAll() {
        return groomingSessionRepository.findAll();
    }

    public Optional<GroomingSession> findById(UUID id) {
        return groomingSessionRepository.findById(id);
    }

    public String update(UUID id, GroomingSession groomingSession) {
        Optional<GroomingSession> existing = groomingSessionRepository.findById(id);
        if (existing.isEmpty()) {
            return "The grooming session with that id does not exist";
        }
        GroomingSession current = existing.get();
        current.setSessionDate(groomingSession.getSessionDate());
        current.setService(groomingSession.getService());
        current.setPetOwner(groomingSession.getPetOwner());
        current.setGroomer(groomingSession.getGroomer());
        groomingSessionRepository.save(current);
        return "Grooming session updated successfully";
    }

    public String delete(UUID id) {
        Optional<GroomingSession> existing = groomingSessionRepository.findById(id);
        if (existing.isEmpty()) {
            return "The grooming session with that id does not exist";
        }
        if (productSaleRepository.existsByGroomingSession(existing.get())) {
            return "That grooming session has product sales, delete them first";
        }
        groomingSessionRepository.deleteById(id);
        return "Grooming session deleted successfully";
    }

    public List<GroomingSession> findByStatus(SessionStatus status) {
        return groomingSessionRepository.findByStatusOrderBySessionDateAsc(status);
    }

    public List<GroomingSession> findBetween(LocalDate start, LocalDate end) {
        return groomingSessionRepository.findBySessionDateBetweenOrderBySessionDateAsc(start, end);
    }

    public String updateStatus(UUID id, SessionStatus newStatus) {
        Optional<GroomingSession> existing = groomingSessionRepository.findById(id);
        if (existing.isEmpty()) {
            return "The grooming session with that id does not exist";
        }
        GroomingSession groomingSession = existing.get();
        SessionStatus currentStatus = groomingSession.getStatus();
        boolean allowed = false;
        if (currentStatus == SessionStatus.BOOKED) {
            allowed = newStatus == SessionStatus.IN_PROGRESS || newStatus == SessionStatus.CANCELLED;
        } else if (currentStatus == SessionStatus.IN_PROGRESS) {
            allowed = newStatus == SessionStatus.FINISHED || newStatus == SessionStatus.CANCELLED;
        }
        if (!allowed) {
            return "Cannot change status from " + currentStatus + " to " + newStatus;
        }
        groomingSession.setStatus(newStatus);
        groomingSessionRepository.save(groomingSession);
        return "Grooming session status updated successfully";
    }
}