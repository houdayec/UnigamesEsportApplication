package hantizlabs.unigamesesportapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import org.json.JSONObject;
import java.net.URL;

/**
 * Created by Corentin on 07/10/2016.
 */

public class TaskTournament extends AsyncTask<URL, Integer, String> {

    HttpURLConnection urlConnection = null;
    private Context mContext;
    private AlertDialog mDialog;

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
            urlConnection.setRequestProperty("X-Api-Key", "s9D-UXBYy9qqZz4Mk8Bs55UbFqQkIRikoIuFdUGHQLk");
            //Opening the stream
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                return line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }

            return null;
        }
    }

    protected void onProgressUpdate(Integer... progress) {

        //TO DO

    }

    protected void onPostExecute(String result) {
    }
}
