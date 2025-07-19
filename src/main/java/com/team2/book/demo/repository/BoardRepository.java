package com.team2.book.demo.repository;


import com.team2.book.demo.entity.BoardEntity_unused;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity_unused, Integer> {
    
}

