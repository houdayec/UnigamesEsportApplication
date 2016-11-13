package hantizlabs.unigamesesportapplication;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

/**
 * Created by Corentin on 20/09/2016.
 */
public class NewsFragment extends ListFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.news_layout, container, false);

        //Get the swiperefresh in the view
        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);

        //Implementation of the swipe refresh pull action
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                //Calling a new task provided by Fabric plugin and refresh tweets
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
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
    public void onResume() {
        super.onResume();

        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = ((AppCompatActivity) getActivity());
            if (activity.getSupportActionBar() != null)
                activity.getSupportActionBar().setTitle("Unigames Twitter");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Calling tweet feed from Fabric and use the adapter to display it using already determined layouts
        final UserTimeline userTimeline = new UserTimeline.Builder()
                .screenName("Unigames_FIN")
                .build();

        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(getActivity())
                .setTimeline(userTimeline)
                .build();

        setListAdapter(adapter);
    }


}
