package com.sied.clients.repository.corporateClient;

import com.sied.clients.entity.corporateClient.CorporateClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CorporateClientRepository extends JpaRepository<CorporateClient, Long> {
}