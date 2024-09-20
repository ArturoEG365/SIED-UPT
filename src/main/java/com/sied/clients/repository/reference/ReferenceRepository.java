package com.sied.clients.repository.reference;

import com.sied.clients.entity.reference.Reference;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ReferenceRepository extends JpaRepository<Reference, Long> {


}
