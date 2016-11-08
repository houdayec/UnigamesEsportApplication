package hantizlabs.unigamesesportapplication;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Corentin on 25/10/2016.
 */

class Match implements Parcelable {
    static class Team {
        private String teamName;
        private int score;
        private boolean winner;

        Team(String teamName, int score, boolean winner) {
            this.teamName = teamName;
            this.score = score;
            this.winner = winner;
        }

        int getScore() {
            return score;
        }

        String getTeamName() {
            return teamName;
        }

        public boolean isWinner() {
            return winner;
        }
    }

    private String date;
    private String time;
    private String opponent1;
    private String opponent2;
    private String opponent1score;
    private String opponent2score;
    private String status;
    private int round;
    private int id;
    private Bracket bracket;

    Match(Team team1, Team team2, int id, int round, Bracket bracket, String status, String date, String time) {
        this.opponent1 = team1.getTeamName();
        this.opponent1score = Integer.toString(team1.getScore());
        this.opponent2 = team2.getTeamName();
        this.opponent2score = Integer.toString(team2.getScore());
        this.status = status;
        this.round = round;
        this.bracket = bracket;
        this.date = date;
        this.time = time;
        this.id = id;
    }

    private Match(Parcel in) {
        id = in.readInt();
        date = in.readString();
        time = in.readString();
        opponent1 = in.readString();
        opponent2 = in.readString();
        opponent1score = in.readString();
        opponent2score = in.readString();
        status = in.readString();
        round = in.readInt();
    }

    public static final Creator<Match> CREATOR = new Creator<Match>() {
        @Override
        public Match createFromParcel(Parcel in) {
            return new Match(in);
        }

        @Override
        public Match[] newArray(int size) {
            return new Match[size];
        }
    };

    Bracket getBracket() {
        return bracket;
    }

    String getTime() {
        return time;
    }

    String getOpponent1() {
        return opponent1;
    }

    String getOpponent1score() {
        return opponent1score;
    }

    String getOpponent2score() {
        return opponent2score;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    int getRound() {
        return round;
    }

    String getOpponent2() {
        return opponent2;
    }

    String getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(time);
        dest.writeString(opponent1);
        dest.writeString(opponent2);
        dest.writeString(opponent1score);
        dest.writeString(opponent2score);
        dest.writeString(status);
        dest.writeInt(round);
    }

    public int describeContents() {
        return 0;
    }
}
