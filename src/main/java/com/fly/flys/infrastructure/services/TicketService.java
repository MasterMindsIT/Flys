package com.fly.flys.infrastructure.services;

import com.fly.flys.api.dto.request.TicketRequest;
import com.fly.flys.api.dto.response.FlyResponse;
import com.fly.flys.api.dto.response.TicketResponse;
import com.fly.flys.domain.entities.TicketEntity;
import com.fly.flys.domain.repositories.CustomerRepository;
import com.fly.flys.domain.repositories.FlyRepository;
import com.fly.flys.domain.repositories.TicketRepository;
import com.fly.flys.infrastructure.iservices.ITicketService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class TicketService  implements ITicketService {
    private final FlyRepository flyRepository;
    private final CustomerRepository customerRepository;
    private final TicketRepository ticketRepository;
    @Override
    public TicketResponse create(TicketRequest request) {
        var fly  = flyRepository.findById(request.getIdFly()).orElseThrow();
        var customer = customerRepository.findById(request.getIdClient()).orElseThrow();

        var ticketToPersist = TicketEntity.builder()
                .id(UUID.randomUUID())
                .fly(fly)
                .customer(customer)
                .price(fly.getPrice().multiply(BigDecimal.valueOf(0.25)))
                .purchaseDate(LocalDate.now())
                .arrivalDate(LocalDateTime.now())
                .departureDate(LocalDateTime.now())
                .build();

        var ticketPersisted = this.ticketRepository.save(ticketToPersist);

      log.info("Ticket saved with id: {}", ticketPersisted.getId());
        return this.entityToResponse(ticketPersisted);
    }

    @Override
    public TicketResponse read(UUID id) {
        var ticketFromDB = this.ticketRepository.findById(id).orElseThrow();
        return this.entityToResponse(ticketFromDB);
    }

    @Override
    public TicketResponse update(TicketRequest request, UUID id) {
        var ticketToUpdate = ticketRepository.findById(id).orElseThrow();
        var fly  = flyRepository.findById(request.getIdFly()).orElseThrow();

        ticketToUpdate.setFly(fly);
        ticketToUpdate.setPrice(fly.getPrice().multiply(BigDecimal.valueOf(0.25)));
        ticketToUpdate.setDepartureDate(LocalDateTime.now());
        ticketToUpdate.setArrivalDate(LocalDateTime.now());

        var ticketUpdated = this.ticketRepository.save(ticketToUpdate);

        log.info("Ticket updated with id {}", ticketUpdated.getId());

        return this.entityToResponse(ticketToUpdate);
    }

    @Override
    public void delete(UUID id) {
        var ticketToDelete = ticketRepository.findById(id).orElseThrow();
        this.ticketRepository.delete(ticketToDelete);
    }
    private TicketResponse entityToResponse(TicketEntity entity) {
        var response = new TicketResponse();
        BeanUtils.copyProperties(entity, response);
        var flyResponse = new FlyResponse();
        BeanUtils.copyProperties(entity.getFly(), flyResponse);
        response.setFly(flyResponse);
        return response;
    }
}
