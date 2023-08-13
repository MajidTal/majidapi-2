package com.Majid.Board_API2.Controller;

import com.Majid.Board_API2.Response.BoardResponse;
import com.Majid.Board_API2.Models.Board;
import com.Majid.Board_API2.Request.BoardRequest;
import com.Majid.Board_API2.Service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/boards")
@CrossOrigin("*")
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping
    public ResponseEntity<BoardResponse> createBoard(@RequestBody BoardRequest boardRequest) {
        Board createdBoard = boardService.createBoard(boardRequest);

        BoardResponse boardResponse = new BoardResponse();
        boardResponse.setBoard_id(createdBoard.getId());
        boardResponse.setName(createdBoard.getTitle());
        boardResponse.setColumns(createdBoard.getColumns());

        return ResponseEntity.ok(boardResponse);
    }


    @GetMapping
    public ResponseEntity<List<BoardResponse>> getAllBoards() {
        List<Board> boards = boardService.getAllBoards();
        List<BoardResponse> boardResponses = new ArrayList<>();

        for (Board board : boards) {
            BoardResponse boardResponse = new BoardResponse();
            boardResponse.setBoard_id(board.getId());
            boardResponse.setName(board.getTitle());
            boardResponse.setColumns(board.getColumns()); // Assuming Board has a getColumns() method
            boardResponses.add(boardResponse);
        }

        return ResponseEntity.ok(boardResponses);
    }


    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponse> getBoardById(@PathVariable Long boardId) {
        Board board = boardService.getBoardById(boardId);

        if (board == null) {
            return ResponseEntity.notFound().build();
        }

        BoardResponse boardResponse = new BoardResponse();
        boardResponse.setBoard_id(board.getId());
        boardResponse.setName(board.getTitle());
        boardResponse.setColumns(board.getColumns());

        return ResponseEntity.ok(boardResponse);
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<BoardResponse> updateBoardName(
            @PathVariable Long boardId,
            @RequestBody Map<String, String> updateRequest) {
        String newName = updateRequest.get("name");
        Board updatedBoard = boardService.updateBoardName(boardId, newName);

        if (updatedBoard == null) {
            return ResponseEntity.notFound().build();
        }

        BoardResponse boardResponse = new BoardResponse();
        boardResponse.setBoard_id(updatedBoard.getId());
        boardResponse.setName(updatedBoard.getTitle());
        boardResponse.setColumns(updatedBoard.getColumns());

        return ResponseEntity.ok(boardResponse);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Map<String, Object>> deleteBoard(
            @PathVariable Long boardId) {
        boolean isDeleted = boardService.deleteBoard(boardId);

        if (isDeleted) {
            Map<String, Object> response = new HashMap<>();
            response.put("successful", true);
            response.put("message", "Board with ID " + boardId + " has been deleted successfully.");
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}