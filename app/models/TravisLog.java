package models;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

import java.util.Date;

/**
 * Created by lakhdeep on 22/10/15.
 */
@Entity(value = "travisLogs")
public class TravisLog {
    @Id
    @Property("_id")
    private ObjectId objectId;
    private String path;
    private Date createdDateTime;

    public String getPath() {

        return path;
    }

    public void setPath(String path) {

        this.path = path;
    }

    public Date getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(Date createdDateTime) {
        this.createdDateTime = createdDateTime;
    }
}
