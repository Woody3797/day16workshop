package ibf2022.ssf.day16workshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ibf2022.ssf.day16workshop.model.Boardgame;
import ibf2022.ssf.day16workshop.service.BoardgameService;

@RestController
@RequestMapping(path = "/api/boardgame", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class BoardgameController {
    
    @Autowired
    private BoardgameService bgService;

    @PostMapping
    public ResponseEntity<String> createBoardgame(@RequestBody Boardgame bg) {
        int insertCount = bgService.saveGame(bg);
        Boardgame result = new Boardgame();
        result.setId(bg.getId());
        result.setInsertCount(insertCount);
        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON)
        .body(result.toJSONInsert().toString());
    }
}
