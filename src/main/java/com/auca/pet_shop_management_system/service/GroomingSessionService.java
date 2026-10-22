package com.auca.pet_shop_management_system.service;

import com.auca.pet_shop_management_system.domain.GroomingSession;
import com.auca.pet_shop_management_system.repository.GroomingSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GroomingSessionService {

    @Autowired
    private GroomingSessionRepository groomingSessionRepository;

    public String save(GroomingSession groomingSession) {
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
        current.setStatus(groomingSession.getStatus());
        current.setService(groomingSession.getService());
        current.setPetOwner(groomingSession.getPetOwner());
        current.setGroomer(groomingSession.getGroomer());
        groomingSessionRepository.save(current);
        return "Grooming session updated successfully";
    }

    public String delete(UUID id) {
        if (!groomingSessionRepository.existsById(id)) {
            return "The grooming session with that id does not exist";
        }
        groomingSessionRepository.deleteById(id);
        return "Grooming session deleted successfully";
    }
}