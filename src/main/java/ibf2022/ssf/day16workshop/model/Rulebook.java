package ibf2022.ssf.day16workshop.model;

import jakarta.json.Json;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class Rulebook {
    
    private int total_count;
    private String file;

    public int getTotal_count() {
        return total_count;
    }
    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }
    public String getFile() {
        return file;
    }
    public void setFile(String file) {
        this.file = file;
    }

    public JsonObjectBuilder toJSON() {
        return Json.createObjectBuilder().add("total_count", this.getTotal_count())
        .add("file", this.getFile());

    }

    public static Rulebook createJSON(JsonObject obj) {
        Rulebook rulebook = new Rulebook();
        JsonNumber tc = obj.getJsonNumber("total_count");
        String file = obj.getString("file");
        rulebook.setTotal_count(tc.intValue());
        rulebook.setFile(file);
        return rulebook;
    }
}
