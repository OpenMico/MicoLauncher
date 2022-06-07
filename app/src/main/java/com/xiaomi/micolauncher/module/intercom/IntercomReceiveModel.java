package com.xiaomi.micolauncher.module.intercom;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.xiaomi.ai.api.common.Required;
import java.util.List;

/* loaded from: classes3.dex */
public class IntercomReceiveModel implements Parcelable {
    public static final Parcelable.Creator<IntercomReceiveModel> CREATOR = new Parcelable.Creator<IntercomReceiveModel>() { // from class: com.xiaomi.micolauncher.module.intercom.IntercomReceiveModel.1
        /* renamed from: a */
        public IntercomReceiveModel createFromParcel(Parcel parcel) {
            return new IntercomReceiveModel(parcel);
        }

        /* renamed from: a */
        public IntercomReceiveModel[] newArray(int i) {
            return new IntercomReceiveModel[i];
        }
    };
    private int a;
    public long audioDuration;
    public List<a> audioStream;
    public String device_name;
    public String home_id;
    public String home_name;
    public String message_id;
    @Required
    public long receivedTime;
    public String room_id;
    public String room_name;
    public String send_device_category;
    public String send_device_type;
    public String send_type;
    public String sender_device_id;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void setAudioDuration(long j) {
        this.audioDuration = j;
    }

    public void setAudioStream(List<a> list) {
        this.audioStream = list;
    }

    public List<a> getAudioStream() {
        return this.audioStream;
    }

    public IntercomReceiveModel() {
        this.a = -1;
    }

    public IntercomReceiveModel(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, int i, long j) {
        this.a = -1;
        this.send_type = str;
        this.send_device_type = str2;
        this.send_device_category = str3;
        this.sender_device_id = str4;
        this.device_name = str5;
        this.room_name = str6;
        this.room_id = str7;
        this.home_name = str8;
        this.home_id = str9;
        this.message_id = str10;
        this.a = i;
        this.receivedTime = j;
    }

    public int getNotificationId() {
        return this.a;
    }

    public String getFromStr() {
        if (!TextUtils.isEmpty(this.room_name)) {
            return this.room_name;
        }
        if (!TextUtils.isEmpty(this.device_name)) {
            return this.device_name;
        }
        return !TextUtils.isEmpty(this.home_name) ? this.home_name : "家庭";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.audioDuration);
        parcel.writeTypedList(this.audioStream);
        parcel.writeString(this.device_name);
        parcel.writeString(this.send_type);
        parcel.writeString(this.send_device_type);
        parcel.writeString(this.send_device_category);
        parcel.writeString(this.sender_device_id);
        parcel.writeString(this.room_name);
        parcel.writeString(this.room_id);
        parcel.writeString(this.home_name);
        parcel.writeString(this.home_id);
        parcel.writeString(this.message_id);
        parcel.writeInt(this.a);
        parcel.writeLong(this.receivedTime);
    }

    public void readFromParcel(Parcel parcel) {
        this.audioDuration = parcel.readLong();
        this.audioStream = parcel.createTypedArrayList(a.CREATOR);
        this.device_name = parcel.readString();
        this.send_type = parcel.readString();
        this.send_device_type = parcel.readString();
        this.send_device_category = parcel.readString();
        this.sender_device_id = parcel.readString();
        this.room_name = parcel.readString();
        this.room_id = parcel.readString();
        this.home_name = parcel.readString();
        this.home_id = parcel.readString();
        this.message_id = parcel.readString();
        this.a = parcel.readInt();
        this.receivedTime = parcel.readLong();
    }

    protected IntercomReceiveModel(Parcel parcel) {
        this.a = -1;
        this.audioDuration = parcel.readLong();
        this.audioStream = parcel.createTypedArrayList(a.CREATOR);
        this.device_name = parcel.readString();
        this.send_type = parcel.readString();
        this.send_device_type = parcel.readString();
        this.send_device_category = parcel.readString();
        this.sender_device_id = parcel.readString();
        this.room_name = parcel.readString();
        this.room_id = parcel.readString();
        this.home_name = parcel.readString();
        this.home_id = parcel.readString();
        this.message_id = parcel.readString();
        this.a = parcel.readInt();
        this.receivedTime = parcel.readLong();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static class a implements Parcelable {
        public static final Parcelable.Creator<a> CREATOR = new Parcelable.Creator<a>() { // from class: com.xiaomi.micolauncher.module.intercom.IntercomReceiveModel.a.1
            /* renamed from: a */
            public a createFromParcel(Parcel parcel) {
                return new a(parcel);
            }

            /* renamed from: a */
            public a[] newArray(int i) {
                return new a[i];
            }
        };
        @Required
        private String a;
        @Required
        private boolean b;
        @Required
        private int c;
        @Required
        private int d;
        private boolean e;
        private String f;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public String a() {
            return this.a;
        }

        public boolean b() {
            return this.b;
        }

        public boolean c() {
            return this.e;
        }

        public String d() {
            return this.f;
        }

        public a(String str, boolean z, int i, int i2, boolean z2, String str2) {
            this.a = str;
            this.b = z;
            this.c = i;
            this.d = i2;
            this.e = z2;
            this.f = str2;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.a);
            parcel.writeByte(this.b ? (byte) 1 : (byte) 0);
            parcel.writeInt(this.c);
            parcel.writeInt(this.d);
            parcel.writeByte(this.e ? (byte) 1 : (byte) 0);
            parcel.writeString(this.f);
        }

        protected a(Parcel parcel) {
            this.a = parcel.readString();
            boolean z = true;
            this.b = parcel.readByte() != 0;
            this.c = parcel.readInt();
            this.d = parcel.readInt();
            this.e = parcel.readByte() == 0 ? false : z;
            this.f = parcel.readString();
        }
    }
}
