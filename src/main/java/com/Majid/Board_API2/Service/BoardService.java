package com.Majid.Board_API2.Service;

import com.Majid.Board_API2.Repository.BoardRepository;
import com.Majid.Board_API2.Request.BoardRequest;
import com.Majid.Board_API2.Models.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public Board createBoard(BoardRequest boardRequest) {
        Board board = new Board();
        board.setTitle(boardRequest.getTitle());
        // Add default columns
        board.getColumns().put(1, "To do");
        board.getColumns().put(2, "In progress");
        board.getColumns().put(3, "Done");

        return boardRepository.save(board);
    }


    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    public Board getBoardById(Long boardId) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        return optionalBoard.orElse(null);
    }

    public Board updateBoardName(Long boardId, String newName) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);

        if (optionalBoard.isPresent()) {
            Board board = optionalBoard.get();
            board.setTitle(newName);
            return boardRepository.save(board);
        }

        return null;
    }

    public boolean deleteBoard(Long boardId) {
        if (boardRepository.existsById(boardId)) {
            boardRepository.deleteById(boardId);
            return true;
        }
        return false;
    }
}
