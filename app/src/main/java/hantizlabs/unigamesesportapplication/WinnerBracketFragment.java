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
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static hantizlabs.unigamesesportapplication.Match.Bracket.LOSERS;
import static hantizlabs.unigamesesportapplication.Match.Bracket.WINNERS;

/**
 * Winner Bracket Fragment
 */
public class WinnerBracketFragment extends Fragment {
    private PagerAdapter pagerAdapter;
    private List<Match> returnedListMatch = new ArrayList<>();
    boolean isAsyncTaskFinished = false;

    public List<Match> getReturnedListMatch() {
        return returnedListMatch;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.winner_bracket_layout, container, false);
        new TaskTournament().execute();

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Round 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Round 2"));
        tabLayout.addTab(tabLayout.newTab().setText("Round 3"));
        tabLayout.addTab(tabLayout.newTab().setText("Quarterfinal"));
        tabLayout.addTab(tabLayout.newTab().setText("Semifinal"));
        tabLayout.addTab(tabLayout.newTab().setText("Final"));
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);

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
                activity.getSupportActionBar().setTitle("Winners' bracket");
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

        PagerAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }

        @Override
        public Fragment getItem(int position) {
            Bundle args;
            List<Match> listMatchToPass;
            List<bracketDataFragment> tabs = new ArrayList<>();

            for(int i=1; i<=6; i++) {
                bracketDataFragment tab = new bracketDataFragment();
                args = new Bundle();
                args.putString("levelRound", "wround"+i);
                listMatchToPass = fillListFragment(i);
                args.putParcelableArrayList("passedList", (ArrayList<? extends Parcelable>) listMatchToPass);
                tab.setArguments(args);
                tabs.add(tab);
            }
            if(position < 6) return tabs.get(position);
            else return null;
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

        TaskTournament() {}

        protected String doInBackground(URL... urls) {
            URL retrieveURL;
            //
            try {
                retrieveURL = new URL("https://api.toornament.com/v1/tournaments/57ee13fe140ba0cd2a8b4593/matches?api_key=s9D-UXBYy9qqZz4Mk8Bs55UbFqQkIRikoIuFdUGHQLk");
                urlConnection = (HttpURLConnection) retrieveURL.openConnection();
                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    returnedListMatch = readJsonStream(in);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("JSON", "exception caught");
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }

            return null;
        }

        protected void onPostExecute(String result) {
            isAsyncTaskFinished = true;
            pagerAdapter.notifyDataSetChanged();
        }
    }

    public ArrayList<Match> fillListFragment(int roundNumber) {
        ArrayList<Match> filledListMatch = new ArrayList<>();
        Log.d("returnedListMatch", String.valueOf(returnedListMatch.size()));
        for(int i = 0; i < returnedListMatch.size(); i++){
            if(returnedListMatch.get(i).getRound() == roundNumber && returnedListMatch.get(i).getBracket() == WINNERS) {
                filledListMatch.add(returnedListMatch.get(i));
            }
        }

        return filledListMatch;
    }

    public List<Match> readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readMatchesArray(reader);
        } finally {
            reader.close();
        }
    }

    public List<Match> readMatchesArray(JsonReader reader) throws IOException {
        List<Match> matches = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            matches.add(readMatch(reader));
        }
        reader.endArray();
        return matches;
    }

    public Match readMatch(JsonReader reader) throws IOException {
        String status = null, date = null, time = null;
        Match.Bracket bracket = null;
        int id = -1, round = -1;
        Match.Team team1 = null, team2 = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if(reader.peek() != JsonToken.NULL) {
                switch (name) {
                    case "number":
                        id = reader.nextInt();
                        break;
                    case "status":
                        status = reader.nextString().toUpperCase();
                        break;
                    case "group_number":
                        int groupTemp = reader.nextInt();
                        if (groupTemp == 1) {
                            bracket = WINNERS;
                        } else if (groupTemp == 2) {
                            bracket = LOSERS;
                        }
                        break;
                    case "round_number":
                        round = reader.nextInt();
                        break;
                    case "date":
                        String tempDateRaw = reader.nextString();
                        String[] tempDate = tempDateRaw.split("T");
                        String[] tempTime = tempDate[1].split(":");

                        date = tempDate[0];
                        time = tempTime[0] + ":" + tempTime[1];
                        break;
                    case "opponents":
                        int counter = 0;
                        reader.beginArray();
                        while (reader.hasNext()) {
                            if (counter == 0) {
                                team1 = readTeam(reader);
                            } else {
                                team2 = readTeam(reader);
                            }
                            counter++;
                        }
                        reader.endArray();
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }
            else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new Match(team1, team2, id, round, bracket, status, date, time);
    }

    public Match.Team readTeam(JsonReader reader) throws IOException {
        String teamName = null;
        int score = -1;
        boolean winner = false;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if(reader.peek() != JsonToken.NULL) {
                switch (name) {
                    case "participant":
                        if(reader.peek() == JsonToken.STRING) {
                            teamName = reader.nextString();
                        }
                        else if(reader.peek() == JsonToken.BEGIN_OBJECT) {
                            reader.beginObject();
                            while(reader.hasNext()) {
                                String nameInParticipant = reader.nextName();
                                if(nameInParticipant.equals("name")) {
                                    teamName = reader.nextString();
                                }
                                else {
                                    reader.skipValue();
                                }
                            }
                            reader.endObject();
                        }
                        break;
                    case "score":
                        score = reader.nextInt();
                        break;
                    case "result":
                        winner = reader.nextInt() == 1;
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }
            else {
                reader.skipValue();
            }
        }
        reader.endObject();

        return new Match.Team(teamName, score, winner);
    }
}