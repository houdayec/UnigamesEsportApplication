package hantizlabs.unigamesesportapplication;

import android.app.ListActivity;
import android.content.Context;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

/**
 * Created by Corentin on 20/09/2016.
 */
public class NewsFragment extends ListFragment{



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.news_layout, container, false);

        //Get the swiperefresh in the view
        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override public void onRefresh() {


                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                        final UserTimeline userTimeline = new UserTimeline.Builder()
                                .screenName("Unigames_FIN")
                                .build();

                        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(getActivity())
                                .setTimeline(userTimeline)
                                .build();

                        setListAdapter(adapter);
                    }
                }, 2000);

            }
        });

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final UserTimeline userTimeline = new UserTimeline.Builder()
                .screenName("Unigames_FIN")
                .build();

        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(getActivity())
                .setTimeline(userTimeline)
                .build();

        setListAdapter(adapter);
    }





}
