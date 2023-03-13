package ibf2022.ssf.day16workshop.repository;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import ibf2022.ssf.day16workshop.model.Boardgame;

@Repository
public class BoardgameRepo {
    
    @Autowired
    RedisTemplate<String, String> template;

    public int saveGame(final Boardgame bg) {
        template.opsForValue().set(bg.getId(), bg.toJSON().toString());
        String result = (String) template.opsForValue().get(bg.getId());
        if (result != null) {
            return 1;
        }
        return 0;
    }

    public Boardgame findById(final String bgId) throws IOException {
        String jsonStringValue = (String) template.opsForValue().get(bgId);
        Boardgame bg = Boardgame.create(jsonStringValue);
        return bg;
    }

    public int updateBoardgame(final Boardgame bg) {
        String result = template.opsForValue().get(bg.getId());
        if (bg.isUpsert()) {
            if (result != null) {
                template.opsForValue().set(bg.getId(), bg.toJSON().toString());
            } else {
                bg.setId(bg.generateId(8));
                template.opsForValue().setIfAbsent(bg.getId(), bg.toJSON().toString());
            }
        } else {
            if (result != null) {
                template.opsForValue().set(bg.getId(), bg.toJSON().toString());
            }
        }
        result = (String) template.opsForValue().get(bg.getId());
        if (result != null) {
            return 1;
        }
        return 0;
    }
}
