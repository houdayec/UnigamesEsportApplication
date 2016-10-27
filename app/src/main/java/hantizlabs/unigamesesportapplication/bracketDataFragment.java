package hantizlabs.unigamesesportapplication;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.*;


public class bracketDataFragment extends Fragment {


    TextView test;
    String resultTask = "";
    String levelRound;
    ArrayList<Match> passedListMatch = new ArrayList<Match>();
    BracketAdapter bracketAdapter;
    RecyclerView matchList;

    public bracketDataFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bracket_data, container, false);
        //test = (TextView) rootView.findViewById(R.id.textView);

        WinnerBracketFragment test = new WinnerBracketFragment();
        Log.d("use getter",test.getReturnedListMatch().toString());

        Bundle args = getArguments();
        //levelRound = args.getString("levelRound");
        //Log.d("LevelRound onCreateView", String.valueOf(levelRound));
        passedListMatch = (ArrayList<Match>) args.get("passedList");

        try {
            Log.d("lentgh passedListMatch", String.valueOf(passedListMatch.size()));
        }catch(Exception ex){

        }

            //Log.d("length passedlistmatch", "null");

        //test.setText(levelRound);
        matchList = (RecyclerView) rootView.findViewById(R.id.matchList);
        //returnedListMatch.add(new Match("Hantiz", "Ectazy"));
        //returnedListMatch.add(new Match("COD", "Battlefield"));
        //Log.d("size of list ::", String.valueOf(passedListMatch.size()));
        bracketAdapter = new BracketAdapter(passedListMatch);
        matchList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        matchList.setAdapter(bracketAdapter);
        //llm.setOrientation(LinearLayoutManager.VERTICAL);
        matchList.setLayoutManager(llm);
        matchList.setItemAnimator(new DefaultItemAnimator());
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //new bracketDataFragment.TaskTournament().execute();
        //setCustomAdapter(passedListMatch);
    }

    private boolean setCustomAdapter(ArrayList<Match> dataList){
        try{
            //Now we create the adapter, and fill up the recycler view
            bracketAdapter = new BracketAdapter(dataList);
            Log.d("Size of dataList", String.valueOf(dataList.size()));
            //matchList.setAdapter(bracketAdapter);
            matchList.swapAdapter(bracketAdapter, false);
            return true;
        }catch(Exception ex){
            Log.d("Exception", ex.getMessage());
            return false;
        }
    }

    /*public void getTournamentData(){
        new bracketDataFragment.TaskTournament().execute();
    }*/





}
