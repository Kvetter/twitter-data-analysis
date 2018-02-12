package org.kvetter.database.mongo;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.Arrays;
import java.util.List;

public class GetTweets {

    public GetTweets() {

    }

    public long tweetsAmount(DBCollection collection) {
        return collection.count();
    }


    public Iterable<DBObject> getNegativeTweets(DBCollection collection) {
        //top 5 negative comments
        Iterable<DBObject> cs = collection.aggregate(Arrays.asList(
                new BasicDBObject("$match", new BasicDBObject("polarity", 0)),
                new BasicDBObject("$group", new BasicDBObject("_id", "$user").append("count", new BasicDBObject("$sum", 1))),
                new BasicDBObject("$sort", new BasicDBObject("count", -1)),
                new BasicDBObject("$limit",5)
        )).results();
        return cs;
    }

    public Iterable<DBObject> getPositiveTweets(DBCollection collection) {
        //top 5 positive comments
        Iterable<DBObject> cs = collection.aggregate(Arrays.asList(
                new BasicDBObject("$match", new BasicDBObject("polarity", 4)),
                new BasicDBObject("$group", new BasicDBObject("_id", "$user").append("count", new BasicDBObject("$sum", 1))),
                new BasicDBObject("$sort", new BasicDBObject("count", -1)),
                new BasicDBObject("$limit",5)
        )).results();
        return cs;
    }

    public int countUsers(DBCollection collection) {
        return collection.distinct("user").size();
    }

    public Iterable<DBObject> mostActiveUsers(DBCollection collection) {
        Iterable<DBObject> cs = collection.aggregate(Arrays.asList(
                new BasicDBObject("$group", new BasicDBObject("_id", "$user").append("count", new BasicDBObject("$sum", 1))),
                new BasicDBObject("$sort", new BasicDBObject("count", -1)),
                new BasicDBObject("$limit",10)
        )).results();
        return cs;
    }

    public Iterable<DBObject> linkTheMost(DBCollection collection) {
        Iterable<DBObject> cs = collection.aggregate(Arrays.asList(
                new BasicDBObject("$match", new BasicDBObject("text", new BasicDBObject("$regex", "(?<=^|(?<=[^a-zA-Z0-9-_\\\\.]))@([A-Za-z]+[A-Za-z0-9_]+)"))),
                new BasicDBObject("$group", new BasicDBObject("_id", "$user").append("count", new BasicDBObject("$sum", 1))),
                new BasicDBObject("$sort", new BasicDBObject("count", -1)),
                new BasicDBObject("$limit",10)
        )).results();
        return cs;
    }

    public Iterable<DBObject> mostMentioned(DBCollection collection) {
        // top 5 users that are mentioned
        Iterable<DBObject> cs = collection.aggregate(Arrays.asList(
                new BasicDBObject("$match", new BasicDBObject("text", new BasicDBObject("$regex", "(?<=^|(?<=[^a-zA-Z0-9-_\\\\.]))@([A-Za-z]+[A-Za-z0-9_]+)"))),
                new BasicDBObject("$project", new BasicDBObject("user", "$user").append("texts", new BasicDBObject("$split", Arrays.asList("$text", " ")))),
                new BasicDBObject("$unwind", "$texts"),
                new BasicDBObject("$match", new BasicDBObject("texts", new BasicDBObject("$regex", "(?<=^|(?<=[^a-zA-Z0-9-_\\\\.]))@([A-Za-z]+[A-Za-z0-9_]+)"))),
                new BasicDBObject("$group", new BasicDBObject("_id", "$texts").append("count", new BasicDBObject("$sum", 1))),
                new BasicDBObject("$sort", new BasicDBObject("count", -1)),
                new BasicDBObject("$limit", 10)
        )).results();

        return cs;
    }
}
