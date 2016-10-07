package hantizlabs.unigamesesportapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Ian on 23/09/2016.
 */
public class BracketFragment extends Fragment{

    TextView test;
    String resultTask = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.bracket_layout, container, false);
        test = (TextView) rootView.findViewById(R.id.textView);
        Toast.makeText(rootView.getContext(), "onCreate reached", Toast.LENGTH_LONG).show();
        getTournamentData();
        return rootView;
    }

    public void getTournamentData(){
        new TaskTournament().execute();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private class TaskTournament extends AsyncTask<URL, Integer, String> {

        HttpURLConnection urlConnection = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        public TaskTournament(){

        }

        protected String doInBackground(URL... urls) {

            StringBuilder result = new StringBuilder();

            try {
                //Creation of URL
                URL retrieveURL = new URL("https://api.toornament.com/v1/tournaments/57ee13fe140ba0cd2a8b4593");
                //Creation of connection
                urlConnection = (HttpURLConnection) retrieveURL.openConnection();
                urlConnection.setRequestProperty("X-Api-Key","s9D-UXBYy9qqZz4Mk8Bs55UbFqQkIRikoIuFdUGHQLk");
                //Opening the stream
                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    Log.d("JSON","connection HTTP OK");
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                        Log.d("JSON","resultFromHTTP");
                    }

                    return line;
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("JSON", "exception catched");
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                    Log.d("JSON", "disconnection");
                }

                return null;
            }
        }

        protected void onProgressUpdate(Integer... progress) {

            Log.d("JSON", "task in progress");

        }

        protected void onPostExecute(String result) {
            resultTask = result;
            Log.d("JSON", "task finished");
            test.setText(resultTask);
        }
    }
}
