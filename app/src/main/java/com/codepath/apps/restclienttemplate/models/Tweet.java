package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Tweet {
    //LIst out the atttributes
    public String body;
    public long uid;
    public User user;
    public String createdAt;

    //deserialize the JSON
    public static  Tweet fromJSON(JSONObject jsonObject) throws JSONException{
        Tweet tweet=new Tweet();

        //extract values from JSON
        tweet.body= jsonObject.getString("text");
        tweet.uid= jsonObject.getLong("id");
        tweet.createdAt= jsonObject.getString("created_at");
        tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));

        return tweet;
    }
}
