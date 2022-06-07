package com.xiaomi.mico.settingslib.core;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class MicoSettingHome {
    @SerializedName("homeId")
    private String a;
    @SerializedName("homeName")
    private String b;
    @SerializedName("selected")
    private boolean c;
    @SerializedName("childSelected")
    private boolean d;
    @SerializedName("roomList")
    private ArrayList<MicoSettingRoom> e;

    public String getHomeId() {
        return this.a;
    }

    public void setHomeId(String str) {
        this.a = str;
    }

    public String getHomeName() {
        return this.b;
    }

    public void setHomeName(String str) {
        this.b = str;
    }

    public boolean isSelected() {
        return this.c;
    }

    public void setSelected(boolean z) {
        this.c = z;
    }

    public boolean isChildSelected() {
        return this.d;
    }

    public void setChildSelected(boolean z) {
        this.d = z;
    }

    public ArrayList<MicoSettingRoom> getRoomList() {
        return this.e;
    }

    public void setRoomList(ArrayList<MicoSettingRoom> arrayList) {
        this.e = arrayList;
    }

    public String toJsonStr() {
        return new Gson().toJson(this);
    }

    public static MicoSettingHome parse(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return (MicoSettingHome) new Gson().fromJson(str, (Class<Object>) MicoSettingHome.class);
    }

    public String toString() {
        return toJsonStr();
    }
}
