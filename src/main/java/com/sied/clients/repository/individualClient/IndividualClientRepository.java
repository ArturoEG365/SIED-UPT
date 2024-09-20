package com.sied.clients.repository.individualClient;

import com.sied.clients.entity.individualClient.IndividualClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndividualClientRepository extends JpaRepository<IndividualClient, Long> {
}