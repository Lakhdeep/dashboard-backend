package models;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

/**
 * Created by lakhdeep on 22/10/15.
 */
@Entity(value = "travisLogs")
public class TravisLog {
    @Id
    @Property("_id")
    private ObjectId objectId;
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
