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
    private String pathToSummary;
    private String pathToLog;
    private Date createdDateTime;

    public String getPathToSummary() {
        return pathToSummary;
    }

    public void setPathToSummary(String pathToSummary) {
        this.pathToSummary = pathToSummary;
    }

    public String getPathToLog() {
        return pathToLog;
    }

    public void setPathToLog(String pathToLog) {
        this.pathToLog = pathToLog;
    }

    public Date getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(Date createdDateTime) {
        this.createdDateTime = createdDateTime;
    }
}
