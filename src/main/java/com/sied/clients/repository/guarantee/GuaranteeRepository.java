package com.sied.clients.repository.guarantee;

import com.sied.clients.entity.guarantee.Guarantee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuaranteeRepository extends JpaRepository<Guarantee, Long> {
}