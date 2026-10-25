package com.auca.pet_shop_management_system.repository;

import com.auca.pet_shop_management_system.domain.GroomingSession;
import com.auca.pet_shop_management_system.domain.SessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface GroomingSessionRepository extends JpaRepository<GroomingSession, UUID> {

    List<GroomingSession> findByStatusOrderBySessionDateAsc(SessionStatus status);

    List<GroomingSession> findBySessionDateBetweenOrderBySessionDateAsc(LocalDate start, LocalDate end);

    boolean existsByGroomerIdAndSessionDateAndStatusNot(UUID groomerId, LocalDate sessionDate, SessionStatus status);

    long countBySessionDate(LocalDate sessionDate);

    long countByStatus(SessionStatus status);

    @Query("SELECT s.status, COUNT(s) FROM GroomingSession s GROUP BY s.status")
    List<Object[]> countGroomingSessionsByStatus();

    @Modifying
    @Query("UPDATE GroomingSession s SET s.status = :cancelled WHERE s.groomer.id = :groomerId AND s.sessionDate = :date AND s.status <> :finished")
    int cancelDayOfGroomer(@Param("groomerId") UUID groomerId,
                           @Param("date") LocalDate date,
                           @Param("cancelled") SessionStatus cancelled,
                           @Param("finished") SessionStatus finished);
}