package com.xiaomi.micolauncher.skills.joke;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/* loaded from: classes3.dex */
public class JokeItem implements Parcelable {
    public static final Parcelable.Creator<JokeItem> CREATOR = new Parcelable.Creator<JokeItem>() { // from class: com.xiaomi.micolauncher.skills.joke.JokeItem.1
        /* renamed from: a */
        public JokeItem createFromParcel(Parcel parcel) {
            return new JokeItem(parcel);
        }

        /* renamed from: a */
        public JokeItem[] newArray(int i) {
            return new JokeItem[i];
        }
    };
    public String id;
    public String text;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public JokeItem() {
    }

    protected JokeItem(Parcel parcel) {
        this.id = parcel.readString();
        this.text = parcel.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.text);
    }

    public boolean isLegal() {
        return !TextUtils.isEmpty(this.text);
    }

    public String toString() {
        return "JokeItem{id='" + this.id + "', text='" + this.text + "'}";
    }
}
