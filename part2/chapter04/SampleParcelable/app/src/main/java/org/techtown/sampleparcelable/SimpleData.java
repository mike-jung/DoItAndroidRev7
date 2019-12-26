package org.techtown.sampleparcelable;

import android.os.Parcel;
import android.os.Parcelable;

public class SimpleData implements Parcelable {

    int number;
    String message;

    public SimpleData(int num, String msg) {
        number = num;
        message = msg;
    }

    public SimpleData(Parcel src) {
        number = src.readInt();
        message = src.readString();
    }

    public static final Creator CREATOR = new Creator() {

        public SimpleData createFromParcel(Parcel in) {
            return new SimpleData(in);
        }

        public SimpleData[] newArray(int size) {
            return new SimpleData[size];
        }

    };

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(number);
        dest.writeString(message);
    }

}
