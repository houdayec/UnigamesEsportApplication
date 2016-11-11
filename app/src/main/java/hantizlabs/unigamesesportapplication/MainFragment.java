package hantizlabs.unigamesesportapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;

/**
 * Created by Ian on 23/09/2016.
 */
public class MainFragment extends Fragment implements View.OnClickListener {

    Button buttonTicket;
    Button buttonTrophy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);

        FloatingActionButton fabNavigate = (FloatingActionButton) rootView.findViewById(R.id.fabWebsite);
        fabNavigate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://unigames.fi"));
                startActivity(intent);
            }
        });



        buttonTicket = (Button) rootView.findViewById(R.id.buttonTicket);
        buttonTrophy = (Button) rootView.findViewById(R.id.buttonTrophy);

        WebView view = (WebView) rootView.findViewById(R.id.webViewEvent);
        String text;
        text = "<html><body><p align=\"justify\">";
        text+= "Unigames is a new gaming event for all university students in Helsinki metropolitan area.\n" +
                "        We challenge you and your friends in your favorite games! The stake is nothing more and nothing less than glory,\n" +
                "        topped with awesome prizes! For the most enthusiastic gamers we offer a corner to sleep in and a spot for\n" +
                "        a computer in a LAN-party for the whole weekend. The grande finale is the League of Legends championship tournament,\n" +
                "        where the true sportsmanship is measured! There will be blood, sweat, and salt!";
        text+= "</p></body></html>";
        view.loadData(text, "text/html", "utf-8");

        WebView view2 = (WebView) rootView.findViewById(R.id.webViewLAN);
        String text2;
        text2 = "<html><body><p align=\"justify\">";
        text2+= "The heart of the two days party are of course you ! Bring your battle stations, grab your friends and be ready to have fun!\n" +
                "        The ticket includes one gaming place, right to use the sleeping area and limitless internet at the venue. Naturally access to the whole event, not just the LAN area, is included.\n" +
                "        More information coming soon!";
        text2+= "</p></body></html>";
        view2.loadData(text2, "text/html", "utf-8");


        /*
        //Loading the webview to justify content
        String htmlText = " %s ";
        String dataForWebView1 = "Unigames is a new gaming event for all university students in Helsinki metropolitan area.\n" +
                "        We challenge you and your friends in your favorite games! The stake is nothing more and nothing less than glory,\n" +
                "        topped with awesome prizes! For the most enthusiastic gamers we offer a corner to sleep in and a spot for\n" +
                "        a computer in a LAN-party for the whole weekend. The grande finale is the League of Legends championship tournament,\n" +
                "        where the true sportsmanship is measured! There will be blood, sweat, and salt!";
        String dataForWebView2 = "The heart of the two day party are of course you – the game enthusiasts who live to play! Bring your battle stations, grab your friends and be ready to have fun!\n" +
                "        The ticket includes one gaming place, right to use the sleeping area and limitless internet at the venue. Naturally access to the whole event, not just the LAN area, is included.\n" +
                "        More information coming soon!";
        WebView wv1 = (WebView) rootView.findViewById(R.id.webView1);
        WebView wv2 = (WebView) rootView.findViewById(R.id.webView2);

        //Pushing the content in webviews
        wv1.loadData(String.format(htmlText, dataForWebView1), "text/html", "utf-8");
        wv2.loadData(String.format(htmlText, dataForWebView2), "text/html", "utf-8");
        */
        return rootView;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        boolean testConnection = isConnected(getContext());
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.buttonTicket:
                buttonTicket.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse("https://bailataan.fi/en/#!/events/6cff8b31-3716-4065-aadd-0924f514e23e"); // missing 'http://' will cause crashed
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                });
                break;

            case R.id.buttonTrophy:
                buttonTrophy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse("https://docs.google.com/forms/d/e/1FAIpQLSdHdzv9LFDLW98f7eX6TNpK1Fmm1h0ipX9-9h3CUBDt-00-mg/viewform?embedded=true"); // missing 'http://' will cause crashed
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);;
                    }
                });
                break;

        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = ((AppCompatActivity) getActivity());
            if (activity.getSupportActionBar() != null)
                activity.getSupportActionBar().setTitle("Unigames 2016");
        }
    }

    public boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifiInfo != null && wifiInfo.isConnected()) || (mobileInfo != null && mobileInfo.isConnected())) {
            return true;
        } else {
            showDialog();
            return false;
        }
    }

    private void showDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("You need an internet connection to run the application.")
                .setCancelable(false)
                .setPositiveButton("Connect to WIFI", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        System.exit(0);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
