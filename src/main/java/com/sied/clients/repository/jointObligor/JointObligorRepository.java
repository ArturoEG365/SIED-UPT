package com.sied.clients.repository.jointObligor;

import com.sied.clients.entity.jointObligor.JointObligor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JointObligorRepository extends JpaRepository<JointObligor, Long> {
}