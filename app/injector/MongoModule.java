package injector;

import com.google.inject.AbstractModule;
import com.mongodb.*;
import models.TravisLog;
import org.apache.commons.lang3.StringUtils;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.logging.MorphiaLoggerFactory;
import play.Configuration;
import play.Environment;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MongoModule extends AbstractModule {

    private final String mongoHost;
    private final String mongoHosts;
    private final String mongoPort;
    private final String dbName;
    private final String dbUser;
    private final String password;
    private final boolean replicateSet;

    private Datastore datastore;
    private MongoClient mongo;
    private Morphia morphia;

    /**
     * Morphia Google Guice Module used to be able to inject Morphia and Datastore instances elsewhere.
     */
    public MongoModule(Environment environment, Configuration configuration) {
        mongoHost = configuration.getString("mongodb.host", "mongohost");
        mongoHosts = configuration.getString("mongodb.hosts");
        mongoPort = configuration.getString("mongodb.port", "27017");
        dbName = configuration.getString("mongodb.db", "redmart");
        dbUser = configuration.getString("mongodb.user");
        password = configuration.getString("mongodb.password");
        replicateSet = configuration.getBoolean("mongodb.rs.enabled", false);
    }

    /**
     * Constructor.
     */
    public MongoModule(String mongoHost, String mongoHosts, String mongoPort,
                       String dbName, String dbUser, String password, boolean replicateSet) {
        this.mongoHost = mongoHost;
        this.mongoHosts = mongoHosts;
        this.mongoPort = mongoPort;
        this.dbName = dbName;
        this.dbUser = dbUser;
        this.password = password;
        this.replicateSet = replicateSet;
    }

    /**
     * Static factory method to create a MongoModule for a single MongoDB instance.
     */
    public static MongoModule fromSingleMongo(String host, String port, String dbName,
                                              @Nullable String user, @Nullable String password) {
        MongoModule mongoModule = new MongoModule(host, null, port, dbName, user, password, false);
        mongoModule.init();
        return mongoModule;
    }

    /**
     * Static factory method to create a MongoModule for a MongoDB cluster running as a Replica Set.
     */
    public static MongoModule fromReplicaSet(String hosts, String ports, String dbName,
                                             @Nullable String user, @Nullable String password) {
        MongoModule mongoModule = new MongoModule(null, hosts, ports, dbName, user, password, true);
        mongoModule.init();
        return mongoModule;
    }

    /**
     * Invoked by Guice during bootstrap.
     */
    @Override
    protected void configure() {
        MorphiaLoggerFactory.reset();
        init();
        bind();
    }

    /**
     * Doesn't require Guice to be in context.
     */
    private void init() {
        mongo = createMongoClient();

        morphia = new Morphia();
        morphia.map(TravisLog.class);

        datastore = createDataStore();
    }

    /**
     * Requires Guice to be in context.
     */
    private void bind() {
        bind(Morphia.class).toInstance(morphia);
        bind(Datastore.class).toInstance(datastore);
    }

    private MongoClient createMongoClient() {
        MongoCredential credential = getCredentials();

        MongoClient mongo = replicateSet ? configureReplicaSet(credential) : configureSingleMongo(credential);
        mongo.setReadPreference(ReadPreference.secondaryPreferred());
        return mongo;
    }

    private Datastore createDataStore() {
        Datastore datastore = morphia.createDatastore(mongo, dbName);
        datastore.ensureIndexes();
        datastore.ensureCaps();
        datastore.setDefaultWriteConcern(WriteConcern.ACKNOWLEDGED);
        return datastore;
    }

    private MongoClient configureSingleMongo(MongoCredential credential) {
        ServerAddress serverAddress = new ServerAddress(mongoHost, Integer.parseInt(mongoPort));
        if (credential == null) {
            return new MongoClient(serverAddress);
        } else {
            return new MongoClient(serverAddress, Collections.singletonList(credential));
        }
    }

    private MongoClient configureReplicaSet(MongoCredential credential) {
        String[] hosts = mongoHosts.split(",");
        String[] ports = mongoPort.split(",");
        if (hosts.length != ports.length) {
            throw new IllegalArgumentException("The ports don't match the number of hosts for Mongo for Replica Set config. "
                    + "Hosts: " + StringUtils.join(hosts, ',') + ", Ports: " + StringUtils.join(ports, ','));
        }
        List<ServerAddress> servers = new ArrayList<>();
        for (int i = 0; i < hosts.length; i++) {
            servers.add(new ServerAddress(hosts[i], Integer.parseInt(ports[i % ports.length])));
        }

        if (credential == null) {
            return new MongoClient(servers);
        } else {
            return new MongoClient(servers, Collections.singletonList(credential));
        }
    }

    private MongoCredential getCredentials() {
        if (!StringUtils.isBlank(dbUser) && !StringUtils.isBlank(password)) {
            return MongoCredential.createCredential(dbUser, dbName, password.toCharArray());
        }
        return null;
    }

    public Datastore getDatastore() {
        return datastore;
    }

    public MongoClient getMongo() {
        return mongo;
    }

    public Morphia getMorphia() {
        return morphia;
    }
}