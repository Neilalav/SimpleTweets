package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.List;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder>{


    private List<Tweet> mTweets;
    Context context;
    private TweetAdapterListener mListener;

    //define an interface required by viewholder
    public  interface TweetAdapterListener{
        public void onItemSelect(View view, int position);
    }
    //pass in the Tweets array in the constructor
    public TweetAdapter(List<Tweet> tweets, TweetAdapterListener listener){
        mTweets = tweets;
        this.mListener = listener;
    }
    // for each row, inflate the layout and cache references into ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View tweetView = inflater.inflate(R.layout.item_tweet, parent, false);
        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;
    }

    //Bind the values  based on the position of the elements

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
        //GET THE DATA according to position
        Tweet tweet = mTweets.get(position);
        //populate the view according to this data
        holder.tvUsername.setText(tweet.user.name);
        holder.tvBody.setText(tweet.body);

        Glide.with(context).load(tweet.user.profileImageUrl).into(holder.ivProfileImage);


    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    //create ViewHolder
    public    class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView ivProfileImage;
        public TextView tvUsername;
        public TextView tvBody;


        public ViewHolder(View itemView){
            super(itemView);

            // perform findViewById
            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfilImage);
            tvUsername = (TextView) itemView.findViewById(R.id.tvUserName);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            //hanle row click event
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener !=null);
                    //get the position of row element
                    int position = getAdapterPosition();
                    //fire the listener callback
                    mListener.onItemSelect(view, position);

                }
            });



        }


    }
 }
