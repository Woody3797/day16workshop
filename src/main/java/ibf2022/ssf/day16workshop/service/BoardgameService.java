package ibf2022.ssf.day16workshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ibf2022.ssf.day16workshop.repository.BoardgameRepo;
import ibf2022.ssf.day16workshop.model.Boardgame;


@Service
public class BoardgameService {
    
    @Autowired
    private BoardgameRepo bgRepo;

    public int saveGame(final Boardgame bg) {
        return bgRepo.saveGame(bg);
    }
}
