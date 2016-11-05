package hantizlabs.unigamesesportapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Ian on 23/09/2016.
 */
public class WinnerBracketFragment extends Fragment{

    TextView test;
    String resultTask = "";
    BracketAdapter bracketAdapter;
    PagerAdapter pagerAdapter;
    List<Match> returnedListMatch = new ArrayList<Match>();
    boolean isAsyncTaskFinished = false;
    JSONArray jsonArray;
    HashMap<String, String> map;
    String round ="";
    JSONArray allOpponents;
    String modifiedDate[];
    String modifiedTime[];
    String hour;
    String minutes;
    Match currentMatch;

    public List<Match> getReturnedListMatch() {
        return returnedListMatch;
    }

    public void setReturnedListMatch(List<Match> returnedListMatch) {
        this.returnedListMatch = returnedListMatch;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.winner_bracket_layout, container, false);
        test = (TextView) rootView.findViewById(R.id.textView);
        new TaskTournament().execute();
        /*do{
            //Log.d("Wait for finish", String.valueOf(Calendar.getInstance().get(Calendar.SECOND)));
        }while(isAsyncTaskFinished != true);*/
        //Fake populate
        returnedListMatch.add(new Match("Mat","Corentin"));
        returnedListMatch.add(new Match("COD","Battlefield"));
        Toast.makeText(rootView.getContext(), "onCreate reached", Toast.LENGTH_LONG).show();
        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Round 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Round 2"));
        tabLayout.addTab(tabLayout.newTab().setText("Little final"));
        tabLayout.addTab(tabLayout.newTab().setText("Semi final"));
        tabLayout.addTab(tabLayout.newTab().setText("Final"));
        final ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.pager);
        pagerAdapter = new PagerAdapter(getFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        viewPager.setCurrentItem(0);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

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
                activity.getSupportActionBar().setTitle("Winners Bracket");
        }
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public class PagerAdapter extends FragmentStatePagerAdapter {
        int mNumOfTabs;

        public PagerAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }

        @Override
        public Fragment getItem(int position) {
            Bundle args;
            List<Match> listMatchToPass;
            switch (position) {
                case 0:
                    bracketDataFragment tab1 = new bracketDataFragment();
                    args = new Bundle();
                    args.putString("levelRound", "wround1");
                    listMatchToPass = fillListFragment(1);
                    Log.d("listMatchToPass", String.valueOf(listMatchToPass.size()));
                    args.putParcelableArrayList("passedList", (ArrayList<? extends Parcelable>) listMatchToPass);
                    tab1.setArguments(args);
                    return tab1;
                case 1:
                    bracketDataFragment tab2 = new bracketDataFragment();
                    args = new Bundle();
                    //args.putString("levelRound", "wround2");
                    args.putString("levelRound", "wround1");
                    tab2.setArguments(args);
                    return tab2;
                case 2:
                    bracketDataFragment tab3 = new bracketDataFragment();
                    args = new Bundle();
                    //args.putString("levelRound", "wlittleFinal");
                    args.putString("levelRound", "wround1");
                    tab3.setArguments(args);
                    return tab3;
                case 3:
                    bracketDataFragment tab4 = new bracketDataFragment();
                    args = new Bundle();
                    //args.putString("levelRound", "wsemiFinal");
                    args.putString("levelRound", "wround1");
                    tab4.setArguments(args);
                    return tab4;
                case 4:
                    bracketDataFragment tab5 = new bracketDataFragment();
                    args = new Bundle();
                    //args.putString("levelRound", "wfinal");
                    args.putString("levelRound", "wround1");
                    tab5.setArguments(args);
                    return tab5;
                default:
                    return null;
            }
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }
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
            Log.d("attempt", "one try");
            String result = "";
            URL retrieveURL;

            try {


                //Creation of URL - og one 57ee13fe140ba0cd2a8b4593
                //57d6a659140ba0754b8b456a


                retrieveURL = new URL("https://api.toornament.com/v1/tournaments/57ee13fe140ba0cd2a8b4593/matches?api_key=s9D-UXBYy9qqZz4Mk8Bs55UbFqQkIRikoIuFdUGHQLk");
                //Creation of connection
                urlConnection = (HttpURLConnection) retrieveURL.openConnection();
                //urlConnection.setRequestProperty("X-Api-Key","s9D-UXBYy9qqZz4Mk8Bs55UbFqQkIRikoIuFdUGHQLk");
                //Opening the stream
                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    Log.d("JSON", "connection HTTP OK");
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                    String line;
                    StringBuilder tempResult = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        tempResult.append(line);
                        Log.d("JSON", "resultFromHTTP");
                    }
                    result = tempResult.toString();
                    if (result != null) {
                        Log.d("Result JSON :", result);
                    } else {
                        Log.d("JSON", "No result !");
                    }
                    Log.d("return list match", String.valueOf(returnedListMatch.size()));

                    //return returnedListMatch;

                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("JSON", "exception catched");
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                    Log.d("JSON", "disconnection");
                }

                return result;
            }


        }
        protected void onProgressUpdate(Integer... progress) {

            Log.d("JSON", "task in progress");

        }

        protected void onPostExecute(String result) {

            //resultTask = result;

            //returnedListMatch = listMatch;
            decapsulateData(result);
            isAsyncTaskFinished = true;
            pagerAdapter.notifyDataSetChanged();
            Log.d("JSON", "task finished");

            //test.setText(resultTask);
        }
    }

    public ArrayList<Match> fillListFragment(int roundNumber){

        ArrayList<Match> filledListMatch = new ArrayList<Match>();
        Log.d("returnedListMatch", String.valueOf(returnedListMatch.size()));
        for(int i = 0; i < returnedListMatch.size(); i++){
            Log.d("match from returned", returnedListMatch.get(i).toString());
            filledListMatch.add(returnedListMatch.get(i));
        }

        return filledListMatch;
    }

    public List<Match> decapsulateData(String result){
        try {

            jsonArray = new JSONArray(result);

            //test.setText("Round : " + jsonArray.getJSONObject(0).getString("round_number").toString());
            for(int i=0; i < jsonArray.length(); i++) {
                Log.d("One result", "beginning of decapsulation");
                //map = new HashMap<String, String>();
                //We verify that it's a winner bracket match (code 1)
                if(jsonArray.getJSONObject(i).getString("group_number").toString().equals("1")){
                    Log.d("step 1", "done");
                    //Compare to the round passed in parameters
                    //We select only matches corresponding to the passed round.
                    if (jsonArray.getJSONObject(i).getString("round_number").toString().equals("1")) {
                        Log.d("step 2", "done");
                        currentMatch = new Match();
                        //map.put("round_number", jsonArray.getJSONObject(i).getString("round_number"));
                        //We modify the date into good format one part is date other one time
                        try {

                            modifiedDate = jsonArray.getJSONObject(i).getString("date").toString().split("T");
                            //map.put("date", modifiedDate[0]);
                            currentMatch.date = modifiedDate[0];
                            //Now we modify the time
                            //Log.d("date :", modifiedDate[0]);
                            //Log.d("time :", modifiedDate[1]);
                            modifiedTime = modifiedDate[1].split(":");
                            hour = modifiedTime[0];
                            minutes = modifiedTime[1];
                            //map.put("time", hour + "h" + minutes);
                            currentMatch.time = hour + "h" + minutes;
                        }catch(Exception ex){
                            currentMatch.date = "unknown";
                            currentMatch.time = "unknown";
                        }
                        currentMatch.status = jsonArray.getJSONObject(i).getString("status").toString();

                        allOpponents = jsonArray.getJSONObject(i).getJSONArray("opponents");

                        for (int j = 0; i < allOpponents.length(); ++j) {
                            JSONObject opponent = allOpponents.getJSONObject(i);
                            //map.put("opponent" + j + 1 + "name", opponent.getString("participant"));
                            //map.put("opponent" + j + 1 + "score", opponent.getString("score"));
                            if (j == 0) {
                                currentMatch.opponent1 = opponent.getString("participant");
                                currentMatch.opponent1score = opponent.getString("score");
                            } else if (j == 1) {
                                currentMatch.opponent2 = opponent.getString("participant");
                                currentMatch.opponent2score = opponent.getString("score");
                            }
                        }

                        returnedListMatch.add(currentMatch);
                    } else {
                        //Log.d("Round", "Not corresponding to passed round");
                        break;
                    }



                } else Log.d("step 1", "wrong stage");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("size return decap", String.valueOf(returnedListMatch.size()));
        return returnedListMatch;
    }


}
