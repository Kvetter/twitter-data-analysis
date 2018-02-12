package org.kvetter.database;

import com.mongodb.*;
import org.kvetter.database.mongo.GetTweets;
import org.kvetter.database.mongo.MongoConnection;


public class MongoTest {

    public static void main(String[] args) {
        MongoClient mongoClient = MongoConnection.getInstance();
        GetTweets tweets = new GetTweets();
        DB db = mongoClient.getDB("social_net");
        DBCollection collection = db.getCollection("tweets");
        String input = args[0];
        if(null != input) {
            switch (input) {
                case "getTotalUsers":
                    System.out.println("");
                    System.out.println("\n" + "Total amount of users: " + tweets.countUsers(collection));
                    break;
                case "getTop10Mentioners":
                    System.out.println("\n" + "Top 10 Mentioners: " + tweets.linkTheMost(collection));
                    break;
                case "getTop5Mentioned":
                    System.out.println("\n" + "Top 5 Mentioned users: " + tweets.mostMentioned(collection));
                    break;
                case "getTop10MostActive":
                    System.out.println("\n" + "Top 10 most active users" + tweets.mostActiveUsers(collection));
                    break;
                case "getTop5Happy":
                    System.out.println("\n" + "Top 5 positive users" + tweets.getPositiveTweets(collection));
                    break;
                case "getTop5Grumpy":
                    System.out.println("\n" + "Top 5 negative users" + tweets.getNegativeTweets(collection));
                    break;
                default:
                    break;
            }

        }
    }
}