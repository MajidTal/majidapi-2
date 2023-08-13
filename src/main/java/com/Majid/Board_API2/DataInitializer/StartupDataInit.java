package com.Majid.Board_API2.DataInitializer;

import com.Majid.Board_API2.Models.Board;
import com.Majid.Board_API2.Repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class StartupDataInit implements ApplicationListener<ApplicationReadyEvent> {
    /**
     * Things to do when the application startup successfully.
     * This class can help us add initial records in the DB in case there are none.
     */
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if(boardRepository.count() == 0){
            Board initialBoard = new Board();
            initialBoard.setId(1L);
            initialBoard.setTitle("General Sprint Board");
            boardRepository.save(initialBoard);
        }
    }
    @Autowired
    private BoardRepository boardRepository;
}