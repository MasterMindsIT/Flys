package com.fly.flys.infrastructure.iservices;

public interface SimpleCrudService <RQ, RS, ID> {

    RS create(RQ request);

    RS read(ID id);

    void delete(ID id);
}
