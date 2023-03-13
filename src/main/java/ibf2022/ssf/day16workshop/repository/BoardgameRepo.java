package ibf2022.ssf.day16workshop.repository;

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
}
