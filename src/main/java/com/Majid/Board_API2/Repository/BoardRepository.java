package com.Majid.Board_API2.Repository;

import com.Majid.Board_API2.Models.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findAll();

}