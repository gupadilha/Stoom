package com.stoom.stoomer.model.repository;

import org.springframework.data.repository.CrudRepository;

import com.stoom.stoomer.model.entity.Address;

public interface AddressRepository extends CrudRepository<Address, Long> { }
