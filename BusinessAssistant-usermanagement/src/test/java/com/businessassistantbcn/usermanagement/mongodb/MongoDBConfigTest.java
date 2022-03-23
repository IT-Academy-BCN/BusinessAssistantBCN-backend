package com.businessassistantbcn.usermanagement.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MongoDBConfigTest {
    private static final String CONNECTION_STRING = "mongodb://%s:%d";
    private static final String DATABASE_NAME = "testMongoDB";
    private static final String COLLECTION_NAME = "testCollection";
    private static final String DOCUMENT_NAME = "testDoc";

    private MongodExecutable mongodExe;
    private MongodProcess mongod;
    private MongoTemplate mongoTemplate;

    @BeforeEach
    public void beforeEach() throws Exception {
        MongodStarter starter = MongodStarter.getDefaultInstance();
        String bindIp = "localhost";
        int port = 27017;
        MongodConfig mongodConfig = MongodConfig.builder()
                .version(Version.Main.DEVELOPMENT)
                .net(new Net(bindIp, port, Network.localhostIsIPv6()))
                .build();
        this.mongodExe = starter.prepare(mongodConfig);
        if (this.mongod != null) {
            this.mongod = mongodExe.start();
        }

        this.mongoTemplate = new MongoTemplate(MongoClients.create(String.format(CONNECTION_STRING, bindIp, port)), DATABASE_NAME);
    }

    @AfterEach
    public void afterEach() throws Exception {
        if (this.mongod != null) {
            this.mongod.stop();
            this.mongodExe.stop();
        }
    }

    @Test
    public void test() {
        MongoDatabase db = mongoTemplate.getDb();
        db.createCollection(COLLECTION_NAME);
        MongoCollection<BasicDBObject> col = db.getCollection(COLLECTION_NAME, BasicDBObject.class);
        col.insertOne(new BasicDBObject(DOCUMENT_NAME, new Date()));
        assertEquals(1L, col.countDocuments());
        db.drop();
    }
}