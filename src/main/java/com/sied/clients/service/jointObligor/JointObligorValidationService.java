package com.sied.clients.service.jointObligor;

import com.sied.clients.entity.jointObligor.JointObligor;
import com.sied.clients.exceptions.global.EntityNotFoundException;
import com.sied.clients.repository.jointObligor.JointObligorRepository;
import org.springframework.stereotype.Service;

@Service
public class JointObligorValidationService {
    private final JointObligorRepository jointObligorRepository;

    public JointObligorValidationService(JointObligorRepository jointObligorRepository) {
        this.jointObligorRepository = jointObligorRepository;
    }

    public JointObligor validateJointObligorExists(Long id) {
        return jointObligorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Joint Obligor with id " + id + " does not exist."));
    }
}