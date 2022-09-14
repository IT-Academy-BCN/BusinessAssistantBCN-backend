package com.businessassistantbcn.usermanagement.config;

import com.mongodb.BasicDBObject;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;


//TODO - PLATFORM BUG - java.lang.RuntimeException: Could not start process: <EOF>
public class EmbeddedMongoTest {

    private static final String DATABASE_NAME = "embedded";

    private MongodExecutable mongodExe;
    private MongodProcess mongod;
    private MongoClient mongo;

    //@BeforeEach
    public void beforeEach() throws Exception {
        MongodStarter starter = MongodStarter.getDefaultInstance();
        String bindIp = "localhost";
        int port = 12345;
        MongodConfig mongodConfig = MongodConfig.builder()
                //.version(Version.Main.PRODUCTION)
                .version(Version.V3_6_5)
                .net(new Net(bindIp, port, Network.localhostIsIPv6()))
                .build();
        this.mongodExe = starter.prepare(mongodConfig);
        this.mongod = mongodExe.start();

        //this.mongo = new MongoClient(bindIp, port);
        //this.mongo = mongodExe.
        this.mongo = MongoClients.create();
    }

    public void afterEach() throws Exception {
        if (this.mongod != null) {
            this.mongod.stop();
            this.mongodExe.stop();
        }
    }

    //@Test
    public void shouldCreateNewObjectInEmbeddedMongoDb() {
        // given
        MongoDatabase db = mongo.getDatabase(DATABASE_NAME);
        db.createCollection("testCollection");
        MongoCollection<BasicDBObject> col = db.getCollection("testCollection", BasicDBObject.class);

        // when
        col.insertOne(new BasicDBObject("testDoc", new Date()));

        // then
        assertEquals(1L, col.countDocuments());
    }

}
