package org.kvetter.database.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class MongoConnection {

    private static MongoClient instance = null;
    protected MongoConnection() {
        // Exists only to defeat instantiation.
    }
    public static MongoClient getInstance() {
        if(instance == null) {
            MongoClientURI connStr = new MongoClientURI("mongodb://localhost:27017");
            instance = new MongoClient(connStr);
        }
        return instance;
    }

}
