package hantizlabs.unigamesesportapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Corentin on 15/10/2016.
 */

public class BracketAdapter extends RecyclerView.Adapter<BracketAdapter.ViewHolder> {
    public ArrayList<Match> listMatch;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView nameTeam1, nameTeam2, scoreTeam1, scoreTeam2, dateMatch, timeMatch;
        public ViewHolder(View v) {
            super(v);
            nameTeam1 = (TextView) v.findViewById(R.id.nameTeam1);
            nameTeam2 = (TextView) v.findViewById(R.id.nameTeam2);
            scoreTeam1 = (TextView) v.findViewById(R.id.scoreTeam1);
            scoreTeam2 = (TextView) v.findViewById(R.id.scoreTeam2);
            dateMatch = (TextView) v.findViewById(R.id.dateMatch);
            timeMatch = (TextView) v.findViewById(R.id.timeMatch);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public BracketAdapter(ArrayList<Match> listMatch) {
        this.listMatch = listMatch;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public BracketAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.match_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        // TODO
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Match currentMatch = listMatch.get(position);
        Log.d("position", String.valueOf(position));
        holder.dateMatch.setText(currentMatch.getDate());
        holder.timeMatch.setText(currentMatch.getTime());
        holder.nameTeam1.setText(currentMatch.getOpponent1());
        holder.nameTeam2.setText(currentMatch.getOpponent2());
        holder.scoreTeam1.setText(currentMatch.getOpponent1score());
        holder.scoreTeam2.setText(currentMatch.getOpponent2score());
        Log.d("display", currentMatch.getOpponent1() + currentMatch.getOpponent2());

    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        try {
            return listMatch.size();
        }catch(Exception ex){
            return 0;
        }
    }
}
