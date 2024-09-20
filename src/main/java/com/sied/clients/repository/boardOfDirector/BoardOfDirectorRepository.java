package com.sied.clients.repository.boardOfDirector;

import com.sied.clients.entity.boardOfDirector.BoardOfDirector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardOfDirectorRepository extends JpaRepository<BoardOfDirector, Long> {
}