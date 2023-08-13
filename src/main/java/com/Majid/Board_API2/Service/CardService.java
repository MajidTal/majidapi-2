package com.Majid.Board_API2.Service;

import com.Majid.Board_API2.Repository.BoardRepository;
import com.Majid.Board_API2.Repository.CardRepository;
import com.Majid.Board_API2.Request.CardRequest;
import com.Majid.Board_API2.Response.CardResponse;
import com.Majid.Board_API2.Models.Board;
import com.Majid.Board_API2.Models.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CardService {
    private final CardRepository cardRepository;
    private final BoardRepository boardRepository;

    @Autowired
    public CardService(CardRepository cardRepository, BoardRepository boardRepository) {
        this.cardRepository = cardRepository;
        this.boardRepository = boardRepository;
    }

    public CardResponse createCard(Long boardId, CardRequest cardRequest) {
        Board board = boardRepository.findById(boardId).orElse(null);
        if (board == null) {
            // Handle board not found error
        }

        Card card = new Card();
        card.setTitle(cardRequest.getTitle());
        card.setDescription(cardRequest.getDescription());
        card.setSection(cardRequest.getSection());
        card.setBoard(board);

        card = cardRepository.save(card);

        CardResponse cardResponse = new CardResponse();
        cardResponse.setCard_id(card.getId());
        cardResponse.setTitle(card.getTitle());
        cardResponse.setDescription(card.getDescription());
        cardResponse.setSection(card.getSection());

        return cardResponse;
    }

    public List<CardResponse> getCardsFromBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElse(null);
        if (board == null) {
            // Handle board not found error
        }

        List<Card> cards = board.getCards();
        List<CardResponse> cardResponses = new ArrayList<>();
        for (Card card : cards) {
            CardResponse cardResponse = new CardResponse();
            cardResponse.setCard_id(card.getId());
            cardResponse.setTitle(card.getTitle());
            cardResponse.setDescription(card.getDescription());
            cardResponse.setSection(card.getSection());
            cardResponses.add(cardResponse);
        }

        return cardResponses;
    }

    public CardResponse getCardFromBoard(Long boardId, Long cardId) {
        Board board = boardRepository.findById(boardId).orElse(null);
        if (board == null) {
            return null; // Return null or handle the error accordingly
        }

        Card card = cardRepository.findByBoardIdAndId(boardId, cardId);
        if (card == null || !board.getCards().contains(card)) {
            return null; // Return null or handle the error accordingly
        }

        CardResponse cardResponse = new CardResponse();
        cardResponse.setCard_id(card.getId());
        cardResponse.setTitle(card.getTitle());
        cardResponse.setDescription(card.getDescription());
        cardResponse.setSection(card.getSection());

        return cardResponse;
    }

    public CardResponse updateCard(Long boardId, Long cardId, CardRequest cardRequest) {
        Board board = boardRepository.findById(boardId).orElse(null);
        if (board == null) {
            return null; // Handle board not found error
        }

        Card card = cardRepository.findByBoardIdAndId(boardId, cardId);
        if (card == null || !board.getCards().contains(card)) {
            return null; // Handle card not found error
        }

        if (cardRequest.getTitle() != null && !cardRequest.getTitle().isEmpty()) {
            card.setTitle(cardRequest.getTitle());
        }

        if (cardRequest.getDescription() != null && !cardRequest.getDescription().isEmpty()) {
            card.setDescription(cardRequest.getDescription());
        }

        if (cardRequest.getSection() > 0) {
            card.setSection(cardRequest.getSection());
        }

        card = cardRepository.save(card);

        CardResponse cardResponse = new CardResponse();
        cardResponse.setCard_id(card.getId());
        cardResponse.setTitle(card.getTitle());
        cardResponse.setDescription(card.getDescription());
        cardResponse.setSection(card.getSection());

        return cardResponse;
    }


    public String deleteCard(Long boardId, Long cardId) {
//        Board board = boardRepository.findById(boardId).orElse(null);
//        if (board == null) {
//            return false; // Handle board not found error
//        }
//
//        Card card = cardRepository.findByBoardIdAndId(boardId, cardId);
//        if (card == null || !board.getCards().contains(card)) {
//            return false; // Handle card not found error
//        }
//
//        board.getCards().remove(card); // Remove card from board's card list
//        cardRepository.delete(card);

        Card card = cardRepository.findByBoardIdAndId(boardId, cardId);
        cardRepository.delete(card);

        return "Done";
    }

}
