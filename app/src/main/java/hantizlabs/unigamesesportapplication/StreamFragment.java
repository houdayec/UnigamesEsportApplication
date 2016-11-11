package hantizlabs.unigamesesportapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.net.Uri;
import android.widget.MediaController;
import android.widget.VideoView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Ian on 26/10/2016.
 */

public class StreamFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= (View)inflater.inflate(R.layout.stream_video, container, false);
        VideoView vidView = (VideoView) view.findViewById(R.id.myVideo);
        //Link to retrieve the twitch stream as a video file to give it available directly in the code
        //As there is no twitch api to get directly the stream and implement it, it's the best solution
        String vidAddress = "http://video-edge-749280.fra01.hls.ttvnw.net/transcode-x2-8b76a0/monstercat_23512258496_538187046/low/index-live.m3u8?token=id=8154807692020292898,bid=23512258496,exp=1477732910,node=video-edge-749280.fra01,nname=video-edge-749280.fra01,fmt=low&sig=3135c289e5d6ce3d4b6f185c90655fa84b0701c5";
        Uri vidUri = Uri.parse(vidAddress);
        vidView.setVideoURI(vidUri);
        MediaController vidControl = new MediaController(this.getContext());
        vidControl.setAnchorView(vidView);
        vidView.setMediaController(vidControl);
        vidView.start();

        return  view;
    }
    @Override
    public void onResume() {
        super.onResume();

        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = ((AppCompatActivity) getActivity());
            if (activity.getSupportActionBar() != null)
                activity.getSupportActionBar().setTitle("Livestream");
        }
    }
    }


