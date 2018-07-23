package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {
    private SwipeRefreshLayout swipeContainer;

    TwitterClient client;
    TweetAdapter tweetAdapter;
    ArrayList<Tweet>tweets;
    RecyclerView rvTweets;
   // SimpleDateFormat sf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

       // swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

       /* swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                                @Override
                                                public void onRefresh() {
                                                    fetchTimelineAsync(0);

                                                }
                                            });*/

        client= TwitterApp.getRestClient(this);
        //find the recycleView
        rvTweets = (RecyclerView) findViewById(R.id.rvTweet);
        //init the arrayList (data source)
        tweets = new ArrayList<>();
        //construct the adapter from this datasource
        tweetAdapter = new TweetAdapter(tweets);
        //RecyclerView setup(layout manager, use adapter)
        rvTweets.setLayoutManager(new LinearLayoutManager(this));
        //set the adapter
        rvTweets.setAdapter(tweetAdapter);
        populateTimeline();

    }

   /* public void fetchTimelineAsync(int i) {
    }*/

    private void populateTimeline() {
        client.getHomeTimeline(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("TwitterClient", response.toString());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
 //               Log.d("TwitterClient", response.toString());
                //iterate through the JSON Array
                //for each entry, deserialize the JSON Object

                for (int x=0; x<response.length();x++){
                    //convert each object to a tweet model
                    //add that tweet model to our data source
                    //notify the adapter that we've added a item
                    try {
                        Tweet tweet = Tweet.fromJSON(response.getJSONObject(x));
                        tweets.add(tweet);
                        tweetAdapter.notifyItemInserted(tweets.size() - 1);
                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("TwitterClient", responseString);
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("TwitterClient", errorResponse.toString());
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("TwitterClient", errorResponse.toString());
                throwable.printStackTrace();
            }
        });
    }
}
