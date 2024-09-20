package com.sied.clients.repository.relatedPep;

import com.sied.clients.entity.relatedPep.RelatedPep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelatedPepRepository extends JpaRepository<RelatedPep, Long> {
}