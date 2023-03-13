package ibf2022.ssf.day16workshop.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.security.SecureRandom;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Boardgame implements Serializable{
    private String name;
    private Pieces pieces;
    private String id;
    private int insertCount;
    private int updateCount;
    private boolean isUpsert;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Pieces getPieces() {
        return pieces;
    }

    public void setPieces(Pieces pieces) {
        this.pieces = pieces;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getInsertCount() {
        return insertCount;
    }

    public void setInsertCount(int insertCount) {
        this.insertCount = insertCount;
    }

    public int getUpdateCount() {
        return updateCount;
    }

    public void setUpdateCount(int updateCount) {
        this.updateCount = updateCount;
    }

    public boolean isUpsert() {
        return isUpsert;
    }

    public void setUpsert(boolean isUpsert) {
        this.isUpsert = isUpsert;
    }

    public Boardgame() {
        this.id = generateId(8);
    }

    public synchronized String generateId(int maxChars) {
        SecureRandom sr = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        while (sb.length() < maxChars) {
            sb.append(Integer.toHexString(sr.nextInt()));
        }

        return sb.toString().substring(0, maxChars);
    }

    public JsonObject toJSON() {
        return Json.createObjectBuilder()
        .add("name", this.getName())
        .add("pieces", this.getPieces().toJSON())
        .add("id", this.getId())
        .build();
    }

    public JsonObject toJSONInsert() {
        return Json.createObjectBuilder()
        .add("id", this.getId())
        .add("insert_count", this.getInsertCount())
        .build();
    }

    public JsonObject toJSONUpdate() {
        return Json.createObjectBuilder()
        .add("id", this.getId())
        .add("update_count", this.getUpdateCount())
        .build();
    }

    public static Boardgame create(String json) throws IOException {
        Boardgame bg = new Boardgame();
        if (json != null) {
            try (InputStream is = new ByteArrayInputStream(json.getBytes())) {
                JsonReader jr = Json.createReader(is);
                JsonObject o = jr.readObject();
                bg.setName(o.getString("name"));
                JsonObject pieces = o.getJsonObject("pieces");
                bg.setPieces(Pieces.createJSON(pieces));
            }
        }
        return bg;
    }

    @Override
    public String toString() {
        return this.getId() + " " + this.getName();
    }

    
}

