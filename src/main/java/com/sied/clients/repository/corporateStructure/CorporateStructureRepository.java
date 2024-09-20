package com.sied.clients.repository.corporateStructure;


import com.sied.clients.entity.corporateStructure.CorporateStructure;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface CorporateStructureRepository extends JpaRepository<CorporateStructure, Long> {


}
