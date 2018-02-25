package com.example.mayingnan.project301;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by wdong2 on 2/15/18.
 */

public class ModelTest extends ActivityInstrumentationTestCase2 {


    public ModelTest() {
        super(User.class);
    }
}

    /**
    public void testAddTweet()
    {
        TweetList tweets = new TweetList();

        Tweet tweet = new NormalTweet("adding a normal Tweet");

        tweets.add(tweet);

        assertTrue(tweets.hasTweet(tweet));
    }

    public void testHasTweet()
    {
        TweetList tweets = new TweetList();

        Tweet tweet = new NormalTweet("adding a normal Tweet");

        assertFalse(tweets.hasTweet(tweet));

        tweets.add(tweet);

        assertTrue(tweets.hasTweet(tweet));

    }

    public void testGetTweetsTweet()
    {
        TweetList tweets = new TweetList();

        Tweet tweet = new NormalTweet("adding a normal Tweet");

        tweets.add(tweet);

        Tweet returnTweet = tweets.getTweet(0);

        assertEquals(returnTweet.getDate(),tweet.getDate());
    }

    public void testDeleatTweet()
    {
        TweetList tweets = new TweetList();

        Tweet tweet = new NormalTweet("adding a normal Tweet");

        tweets.add(tweet);

        tweets.deleat(tweet);

        assertFalse(tweets.hasTweet(tweet));
    }

    public void testaddTweetTweet() {
        TweetList tweets = new TweetList();

        Tweet tweet = new NormalTweet("adding a normal Tweet");

        tweets.add(tweet);

        try {
            tweets.addTweet(tweet);
        }catch (Exception DuplicateExcaption){
            assertTrue(true);
        }

        assertFalse(tweets.getCount()==2);
    }

    public void testgetTweetsTweet(){
        TweetList tweets = new TweetList();

        Tweet tweet1 = new NormalTweet("adding a normal Tweet");

        Tweet tweet2 = new NormalTweet("adding a normal Tweet");

        tweets.add(tweet1);

        tweets.add(tweet2);

        assertEquals(tweet1, tweets.getTweets().get(1));
    }

    public void testgetCountTweet(){
        TweetList tweets = new TweetList();

        Tweet tweet1 = new NormalTweet("adding a normal Tweet");

        tweets.add(tweet1);

        assertEquals(tweets.getCount(),1);

        Tweet tweet2 = new NormalTweet("adding a normal Tweet");

        tweets.add(tweet2);

        assertEquals(tweets.getCount(),2);
    }
}
*/