package com.fly.flys.domain.repositories;

import com.fly.flys.domain.entities.TicketEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface TicketRepository extends CrudRepository<TicketEntity, UUID> {
    Optional<TicketEntity> findByTourId(Long id);
}
