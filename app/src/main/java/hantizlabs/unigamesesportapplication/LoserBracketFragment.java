package hantizlabs.unigamesesportapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Corentin on 14/10/2016.
 */

public class LoserBracketFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    TextView test;
    JSONObject resultTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.loser_bracket_layout, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        //mLayoutManager = new LinearLayoutManager(this);
        //mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        //mAdapter = new BracketAdapter(myDataset);
        //mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    public void getTournamentData(){
        new LoserBracketFragment.TaskTournament().execute();
    }

    private class TaskTournament extends AsyncTask<URL, Integer, JSONObject> {

        HttpURLConnection urlConnection = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        public TaskTournament(){

        }

        protected JSONObject doInBackground(URL... urls) {

            String result = "";
            JSONObject JSONresult;
            JsonParser JSONparser = new JsonParser();

            try {
                //Creation of URL
                URL retrieveURL = new URL("https://api.toornament.com/v1/tournaments/57ee13fe140ba0cd2a8b4593?api_key=s9D-UXBYy9qqZz4Mk8Bs55UbFqQkIRikoIuFdUGHQLk");
                //Creation of connection
                urlConnection = (HttpURLConnection) retrieveURL.openConnection();
                //urlConnection.setRequestProperty("X-Api-Key","s9D-UXBYy9qqZz4Mk8Bs55UbFqQkIRikoIuFdUGHQLk");
                //Opening the stream
                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    Log.d("JSON","connection HTTP OK");
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        result += line;
                        Log.d("JSON","resultFromHTTP");
                    }
                    if(result != null){
                        Log.d("Result JSON :",result);
                    }else{
                        Log.d("JSON","No result !");
                    }
                    resultTask = new JSONObject(result);
                    return resultTask;
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("JSON", "exception catched");
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                    Log.d("JSON", "disconnection");
                }

                return resultTask;
            }

        }

        protected void onProgressUpdate(Integer... progress) {

            Log.d("JSON", "task in progress");

        }

        protected void onPostExecute(JSONObject result) {
            resultTask = result;
            Log.d("JSON", "task finished");
            //test.setText(resultTask);
        }
    }
}
