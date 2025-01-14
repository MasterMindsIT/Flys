package com.fly.flys.infrastructure.iservices;

import com.fly.flys.api.dto.request.TicketRequest;
import com.fly.flys.api.dto.response.TicketResponse;

import java.util.UUID;

public interface ITicketService extends CrudService<TicketRequest, TicketResponse, UUID>  {

}
