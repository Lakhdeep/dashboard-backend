package dao.impl;

import com.google.inject.Inject;
import dao.TravisLogDao;
import models.TravisLog;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

/**
 * Created by lakhdeep on 26/10/15.
 */
public class TravisLogDaoImpl extends BasicDAO<TravisLog, ObjectId> implements TravisLogDao{

    @Inject
    protected TravisLogDaoImpl(Datastore ds) {
        super(ds);
    }

    @Override
    public TravisLog getOneTravisLog() {
        return this.find().asList().get(0);
    }

}
