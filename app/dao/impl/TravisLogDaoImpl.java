package dao.impl;

import com.google.inject.Inject;
import dao.TravisLogDao;
import models.TravisLog;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.aggregation.Sort;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;

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
        Query query = ds.createQuery(TravisLog.class).order("-createdDateTime").limit(1);
//        query.with(new Sort(Sort.Direction.DESC, "lastModifiedDate"))
        return this.findOne(query);
//        find().asList().get(0);
    }

}
