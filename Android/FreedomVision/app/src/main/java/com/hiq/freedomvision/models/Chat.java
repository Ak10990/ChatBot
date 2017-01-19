package com.hiq.freedomvision.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.hiq.freedomvision.utils.DateUtils;

import java.util.Date;

/**
 * Created by Akanksha on 26/4/16.
 * Chat Parcelable
 */
public class Chat implements Parcelable {

    private long id;
    private Date date;
    private int userType;
    private String body;

    public Chat(long id, Date date, int userType, String body) {
        this.id = id;
        this.date = date;
        this.userType = userType;
        this.body = body;
    }

    protected Chat(Parcel in) {
        id = in.readLong();
        date = DateUtils.getDateTime(in.readString());
        userType = in.readInt();
        body = in.readString();
    }

    public long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public int getFromTo() {
        return userType;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", date=" + date +
                ", fromTo='" + userType + '\'' +
                ", body='" + body + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(date.toString());
        dest.writeInt(userType);
        dest.writeString(body);
    }

    public static final Creator<Chat> CREATOR = new Creator<Chat>() {
        @Override
        public Chat createFromParcel(Parcel in) {
            return new Chat(in);
        }

        @Override
        public Chat[] newArray(int size) {
            return new Chat[size];
        }
    };
}