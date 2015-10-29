package service.impl;

import com.google.inject.Inject;
import dao.impl.TravisLogDaoImpl;
import models.TravisLog;
import service.TravisLogService;

/**
 * Created by lakhdeep on 27/10/15.
 */
public class TravisLogServiceImpl implements TravisLogService{

    @Inject
    private TravisLogDaoImpl travisLogDaoImpl;

    @Override
    public TravisLog findOneTravisLog() {
        return travisLogDaoImpl.getOneTravisLog();
    }
}
