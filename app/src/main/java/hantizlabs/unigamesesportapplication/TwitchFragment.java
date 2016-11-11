package hantizlabs.unigamesesportapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by Ian on 23/09/2016.
 */
public class TwitchFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.twitch_fragment, container, false);

        //Displaying the stream in general way to have choice between app to watch the stream
        String url = "http://twitch.tv/unigames_fin/embed";
        WebView mWebView;
        mWebView = (WebView) rootView.findViewById(R.id.webViewTwitch);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        mWebView.loadUrl(url);

        return rootView;
    }
}
