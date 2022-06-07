package com.xiaomi.mico.settingslib.core;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/* loaded from: classes3.dex */
public class MicoSettingRoom {
    @SerializedName("roomId")
    private String a;
    @SerializedName("roomName")
    private String b;
    @SerializedName("selected")
    private boolean c;
    @SerializedName("isCurrentSpeakerRoom")
    private boolean d;
    @SerializedName("configured")
    private boolean e = true;

    public String getRoomId() {
        return this.a;
    }

    public void setRoomId(String str) {
        this.a = str;
    }

    public String getRoomName() {
        return this.b;
    }

    public void setRoomName(String str) {
        this.b = str;
    }

    public boolean isSelected() {
        return this.c;
    }

    public void setSelected(boolean z) {
        this.c = z;
    }

    public boolean isCurrentSpeakerRoom() {
        return this.d;
    }

    public void setCurrentSpeakerRoom(boolean z) {
        this.d = z;
    }

    public boolean isConfigured() {
        return this.e;
    }

    public void setConfigured(boolean z) {
        this.e = z;
    }

    public String toJsonStr() {
        return new Gson().toJson(this);
    }

    public static MicoSettingRoom parse(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return (MicoSettingRoom) new Gson().fromJson(str, (Class<Object>) MicoSettingRoom.class);
    }

    public String toString() {
        return toJsonStr();
    }
}
