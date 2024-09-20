package com.sied.clients.repository.shareholder;

import com.sied.clients.entity.shareholder.Shareholder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShareholderRepository extends JpaRepository<Shareholder, Long> {
}