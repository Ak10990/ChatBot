package com.hiq.freedomvision.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by akanksha on 21/10/16.
 */

public class QueParse implements Parcelable {

    private String question;
    private String heading;
    private int optionsType;
    private List<String> options;

    public QueParse(String question, String heading, int optionsType, List<String> options) {
        this.question = question;
        this.heading = heading;
        this.optionsType = optionsType;
        this.options = options;
    }

    protected QueParse(Parcel in) {
        question = in.readString();
        heading = in.readString();
        in.readStringList(options);
    }

    public String getQuestion() {
        return question;
    }

    public String getHeading() {
        return heading;
    }

    public int getOptionsType() {
        return optionsType;
    }

    public List<String> getOptions() {
        return options;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeStringList(options);
    }

    public static final Creator<QueParse> CREATOR = new Creator<QueParse>() {
        @Override
        public QueParse createFromParcel(Parcel in) {
            return new QueParse(in);
        }

        @Override
        public QueParse[] newArray(int size) {
            return new QueParse[size];
        }
    };
}
