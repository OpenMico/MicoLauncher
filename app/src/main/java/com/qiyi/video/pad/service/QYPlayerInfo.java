package com.qiyi.video.pad.service;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

/* loaded from: classes2.dex */
public class QYPlayerInfo implements Parcelable {
    public static final Parcelable.Creator<QYPlayerInfo> CREATOR = new Parcelable.Creator<QYPlayerInfo>() { // from class: com.qiyi.video.pad.service.QYPlayerInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public QYPlayerInfo createFromParcel(Parcel parcel) {
            return new QYPlayerInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public QYPlayerInfo[] newArray(int i) {
            return new QYPlayerInfo[i];
        }
    };
    public static final int ERROR = 8;
    public static final int GETADDR = 0;
    public static final int GETADDR_SUCCESS = 1;
    public static final int ONCOMPLETE = 9;
    public static final int ONPREPARE = 2;
    public static final int PAGE_HOMEACTIVITY = 0;
    public static final int PAGE_OTHER = 3;
    public static final int PAGE_PLAYERACTIVITY = 1;
    public static final int PAGE_RADIOSTATIONACTIVITY = 2;
    public static final int PAUSE = 6;
    public static final int PLAYER_UI_CLICK_EXIT_CAST_BTN = 1009;
    public static final int PLAYER_UI_CLICK_STRAT_CAST_BTN = 1008;
    public static final int PLAYER_UI_END_CAST = 1011;
    public static final int PLAYER_UI_ON_DESTROY = 1007;
    public static final int PLAYER_UI_ON_RESUME = 1012;
    public static final int PLAYER_UI_START_CAST = 1010;
    public static final int PLAYER_UI_START_PLAY = 1013;
    public static final int PLAYING = 5;
    public static final int PLAYING_MID_ADS = 4;
    public static final int PLAYING_PRE_ADS = 3;
    public static final int USERPAUSE = 7;
    public String albumName;
    public String album_id;
    public List<String> casts;
    public List<String> directors;
    public int duration;
    public String endTime;
    public int order;
    public int page;
    public int playerSize;
    public int player_status;
    public int position;
    public String poster;
    public String qyscore;
    public boolean skip;
    public int solo;
    public int speed;
    public String startTime;
    public int stream;
    public List<String> stream_list;
    public String tags;
    public String tv_id;
    public String videoName;

    /* loaded from: classes2.dex */
    public @interface PlayerStatus {
    }

    /* loaded from: classes2.dex */
    public @interface PlayerUIState {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public QYPlayerInfo() {
    }

    protected QYPlayerInfo(Parcel parcel) {
        this.album_id = parcel.readString();
        this.tv_id = parcel.readString();
        this.videoName = parcel.readString();
        this.solo = parcel.readInt();
        this.player_status = parcel.readInt();
        this.position = parcel.readInt();
        this.duration = parcel.readInt();
        this.speed = parcel.readInt();
        this.skip = parcel.readByte() != 0;
        this.stream = parcel.readInt();
        this.stream_list = parcel.createStringArrayList();
        this.albumName = parcel.readString();
        this.poster = parcel.readString();
        this.directors = parcel.createStringArrayList();
        this.casts = parcel.createStringArrayList();
        this.tags = parcel.readString();
        this.qyscore = parcel.readString();
        this.startTime = parcel.readString();
        this.endTime = parcel.readString();
        this.order = parcel.readInt();
        this.playerSize = parcel.readInt();
        this.page = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.album_id);
        parcel.writeString(this.tv_id);
        parcel.writeString(this.videoName);
        parcel.writeInt(this.solo);
        parcel.writeInt(this.player_status);
        parcel.writeInt(this.position);
        parcel.writeInt(this.duration);
        parcel.writeInt(this.speed);
        parcel.writeByte(this.skip ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.stream);
        parcel.writeStringList(this.stream_list);
        parcel.writeString(this.albumName);
        parcel.writeString(this.poster);
        parcel.writeStringList(this.directors);
        parcel.writeStringList(this.casts);
        parcel.writeString(this.tags);
        parcel.writeString(this.qyscore);
        parcel.writeString(this.startTime);
        parcel.writeString(this.endTime);
        parcel.writeInt(this.order);
        parcel.writeInt(this.playerSize);
        parcel.writeInt(this.page);
    }
}
