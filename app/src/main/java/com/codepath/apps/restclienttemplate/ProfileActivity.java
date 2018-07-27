package com.codepath.apps.restclienttemplate;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.fragments.UserTimelineFragment;
import com.codepath.apps.restclienttemplate.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ProfileActivity extends AppCompatActivity {
    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        String screenName= getIntent().getStringExtra("screen_name");
        //create the user fragment
        UserTimelineFragment userTimelineFragment =UserTimelineFragment.newInstances("screenName");
        //display the user timeline fragment inside the container(dynamically)

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        //make change
        ft.replace(R.id.flContainer,userTimelineFragment);
        //commit
        ft.commit();

        client = TwitterApp.getRestClient(this);
        client.getUserInfo(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // deserialize the user objet
                try {
                    User user = User.fromJSON(response);
                    getSupportActionBar().setTitle(user.screenName);
                    //populate the user headLine
                     populateUserHeadLine(user);
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }
    public void populateUserHeadLine(User user){
        TextView tvName = findViewById(R.id.tvName);
        TextView tvTagLine = findViewById(R.id.tvTagLine);
        TextView tvFollowers  = findViewById(R.id.tvFollowers);
        TextView tvFollowing = findViewById(R.id.tvFollowing);

        ImageView ivProfileImage = findViewById(R.id.ivProfileImage);
        tvName.setText(user.name);

        tvTagLine.setText(user.tagLine);
        tvFollowers.setText(user.followersCount + "Followers");
        tvFollowing.setText(user.followingCount + "Following");
         //load profille immage
        Glide.with(this).load(user.profileImageUrl ).into(ivProfileImage);

    }
}
