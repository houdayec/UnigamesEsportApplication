package hantizlabs.unigamesesportapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Corentin on 15/10/2016.
 * Custom Adapter for brackets
 */

class BracketAdapter extends RecyclerView.Adapter<BracketAdapter.ViewHolder> {
    private ArrayList<Match> listMatch;

    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView nameTeam1, nameTeam2, scoreTeam1, scoreTeam2, dateMatch, timeMatch;

        ViewHolder(View v) {
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
    BracketAdapter(ArrayList<Match> listMatch) {
        this.listMatch = listMatch;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public BracketAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(v);
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Match currentMatch = listMatch.get(position);
        holder.dateMatch.setText(currentMatch.getDate());
        holder.timeMatch.setText(currentMatch.getTime());
        holder.nameTeam1.setText(currentMatch.getOpponent1());
        holder.nameTeam2.setText(currentMatch.getOpponent2());
        holder.scoreTeam1.setText(currentMatch.getOpponent1score());
        holder.scoreTeam2.setText(currentMatch.getOpponent2score());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        try {
            return listMatch.size();
        } catch (Exception ex) {
            return 0;
        }
    }
}
