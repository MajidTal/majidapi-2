package com.Majid.Board_API2.Controller;

import com.Majid.Board_API2.Response.CardResponse;
import com.Majid.Board_API2.Request.CardRequest;
import com.Majid.Board_API2.Service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/boards")
@CrossOrigin("*")
public class CardController {
    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("/{boardId}/cards")
    public ResponseEntity<CardResponse> createCard(
            @PathVariable Long boardId,
            @RequestBody CardRequest cardRequest) {
        CardResponse createdCard = cardService.createCard(boardId, cardRequest);
        return ResponseEntity.ok(createdCard);
    }

    @GetMapping("/{boardId}/cards")
    public ResponseEntity<Map<String, Object>> getCardsFromBoard(@PathVariable Long boardId) {
        List<CardResponse> cards = cardService.getCardsFromBoard(boardId);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("cards", cards);

        return ResponseEntity.ok(responseBody);
    }


    @GetMapping("/{boardId}/cards/{cardId}")
    public ResponseEntity<CardResponse> getCardFromBoard(
            @PathVariable Long boardId,
            @PathVariable Long cardId) {
        CardResponse cardResponse = cardService.getCardFromBoard(boardId, cardId);

        if (cardResponse == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cardResponse);
    }

    @PutMapping("/{boardId}/cards/{cardId}")
    public ResponseEntity<CardResponse> updateCard(
            @PathVariable Long boardId,
            @PathVariable Long cardId,
            @RequestBody CardRequest cardRequest) {
        CardResponse updatedCard = cardService.updateCard(boardId, cardId, cardRequest);

        if (updatedCard == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedCard);
    }

    @DeleteMapping("/{boardId}/cards/{cardId}")
    public String deleteCard(
            @PathVariable Long boardId,
            @PathVariable Long cardId) {
        cardService.deleteCard(boardId, cardId);
        return "Done";
    }


}
