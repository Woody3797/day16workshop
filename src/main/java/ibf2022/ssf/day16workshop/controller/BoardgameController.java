package ibf2022.ssf.day16workshop.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping(path = "{bgId}")
    public ResponseEntity<String> getBoardgame(@PathVariable String bgId) throws IOException {
        Boardgame bg = bgService.findById(bgId);
        if (bg == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body("");
        }
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(bg.toJSON().toString());
    }

    @PutMapping(path = "{bgId}")
    public ResponseEntity<String> updateBoardgame(@RequestBody Boardgame bg, @PathVariable String bgId, @RequestParam(defaultValue = "false") boolean isUpsert) throws IOException {
        Boardgame result = null;
        bg.setUpsert(isUpsert);
        if (!isUpsert) {
            bgService.findById(bgId);
            if (result == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body("");
            }
        }
        bg.setId(bgId);
        int updateCount = bgService.updateBoardgame(bg);
        bg.setUpdateCount(updateCount);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(bg.toJSON().toString());
    }
}
