package com.xiaomi.micolauncher.module.skill.bean;

import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

/* loaded from: classes3.dex */
public class SkillApp implements Parcelable {
    public static final Parcelable.Creator<SkillApp> CREATOR = new Parcelable.Creator<SkillApp>() { // from class: com.xiaomi.micolauncher.module.skill.bean.SkillApp.1
        /* renamed from: a */
        public SkillApp createFromParcel(Parcel parcel) {
            return new SkillApp(parcel);
        }

        /* renamed from: a */
        public SkillApp[] newArray(int i) {
            return new SkillApp[i];
        }
    };
    private int a;
    @SerializedName("url")
    private String b;
    @SerializedName("needNetwork")
    private boolean c;
    @SerializedName("statName")
    private String d;
    @SerializedName("isVideoApp")
    private boolean e;
    @SerializedName("iconName")
    private String f;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SkillApp(int i, String str, boolean z, String str2) {
        this.a = i;
        this.b = str;
        this.c = z;
        this.d = str2;
    }

    public SkillApp(int i, String str, boolean z, String str2, boolean z2) {
        this.a = i;
        this.b = str;
        this.c = z;
        this.d = str2;
        this.e = z2;
    }

    public int getIconRes() {
        return this.a;
    }

    public String getUrl() {
        return this.b;
    }

    public boolean isNeedNetwork() {
        return this.c;
    }

    public String getStatName() {
        return this.d;
    }

    public boolean isVideoApp() {
        return this.e;
    }

    public String getIconName() {
        return this.f;
    }

    public void setIconName(String str) {
        this.f = str;
    }

    public void getResId(Resources resources) {
        if (this.a == 0) {
            this.a = resources.getIdentifier(this.f, "drawable", "com.xiaomi.micolauncher");
        }
    }

    protected SkillApp(Parcel parcel) {
        this.a = parcel.readInt();
        this.b = parcel.readString();
        boolean z = true;
        this.c = parcel.readInt() != 0;
        this.d = parcel.readString();
        this.e = parcel.readInt() == 0 ? false : z;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.a);
        parcel.writeString(this.b);
        parcel.writeInt(this.c ? 1 : 0);
        parcel.writeString(this.d);
        parcel.writeInt(this.e ? 1 : 0);
    }
}
