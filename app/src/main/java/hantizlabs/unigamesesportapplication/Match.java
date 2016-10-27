package hantizlabs.unigamesesportapplication;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Corentin on 25/10/2016.
 */

public class Match implements Parcelable{

    public String date;

    public String time;

    public String opponent1;

    public String opponent2;

    public String opponent1score;

    public String opponent2score;

    public String status;

    public String round;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOpponent1() {
        return opponent1;
    }

    public void setOpponent1(String opponent1) {
        this.opponent1 = opponent1;
    }

    public String getOpponent1score() {
        return opponent1score;
    }

    public void setOpponent1score(String opponent1score) {
        this.opponent1score = opponent1score;
    }

    public String getOpponent2score() {
        return opponent2score;
    }

    public void setOpponent2score(String opponent2score) {
        this.opponent2score = opponent2score;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public String getOpponent2() {
        return opponent2;
    }

    public void setOpponent2(String opponent2) {
        this.opponent2 = opponent2;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Match(String opponent1, String opponent2) {
        this.opponent1 = opponent1;
        this.opponent2 = opponent2;
    }

    public Match(){

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
        dest.writeString(round);

    }

    public int describeContents() {
        return 0;
    }
}
