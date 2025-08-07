package com.auth.user.core.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auth.user.core.entity.Address;

@Repository
public interface AddressRepositories extends JpaRepository<Address, UUID> {

	List<Address> findByUserId(UUID userId);
}
