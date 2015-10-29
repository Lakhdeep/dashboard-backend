package injector;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import dao.TravisLogDao;
import dao.impl.TravisLogDaoImpl;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import service.TravisLogService;
import service.impl.TravisLogServiceImpl;

public class TravisModule extends AbstractModule {

    @Inject
    TravisLogDaoImpl daoImpl;

    @Inject
    TravisLogServiceImpl serviceImpl;


    @Override
    protected void configure() {
        bind(TravisLogDao.class).toInstance(daoImpl);
        bind(TravisLogService.class).toInstance(serviceImpl);
    }
}