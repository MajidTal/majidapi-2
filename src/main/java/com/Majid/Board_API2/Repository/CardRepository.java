package com.Majid.Board_API2.Repository;

import com.Majid.Board_API2.Models.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findBySection(Long boardId);
    List<Card> findByBoardId(Long boardId);
    Card findByBoardIdAndId(Long boardId, Long cardId);



}

