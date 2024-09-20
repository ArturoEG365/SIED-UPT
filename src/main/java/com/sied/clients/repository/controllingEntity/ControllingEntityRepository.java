package com.sied.clients.repository.controllingEntity;

import com.sied.clients.entity.controllingEntity.ControllingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ControllingEntityRepository extends JpaRepository<ControllingEntity, Long> {
}