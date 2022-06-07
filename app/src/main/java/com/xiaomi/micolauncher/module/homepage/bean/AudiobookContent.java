package com.xiaomi.micolauncher.module.homepage.bean;

import com.google.gson.annotations.SerializedName;
import com.xiaomi.ai.android.track.TraceConstants;

/* loaded from: classes3.dex */
public class AudiobookContent {
    public static final int TYPE_ALBUM = 0;
    public static final int TYPE_RADIO = 1;
    public static final int TYPE_SOUND = 2;
    public static final String VALUE_ALBUM = "album";
    public static final String VALUE_RADIO = "radio";
    public static final String VALUE_SOUND = "sound";
    @SerializedName("id")
    private String a;
    @SerializedName(TraceConstants.Result.CP)
    private String b;
    @SerializedName("cpName")
    private String c;
    @SerializedName("eid")
    private String d;
    @SerializedName("cpAlbumTitle")
    private String e;
    @SerializedName("cpAlbumId")
    private String f;
    @SerializedName("lastSoundTitle")
    private String g;
    @SerializedName("cover")
    private String h;
    @SerializedName("actionURL")
    private String i;
    @SerializedName("resourceType")
    private String j;
    @SerializedName("category")
    private String k;
    @SerializedName("score")
    private float l;
    @SerializedName("author")
    private String m;
    @SerializedName("title")
    private String n;
    private String[] o;

    public AudiobookContent(String str, String str2, String str3, String[] strArr, String str4, String str5, String str6) {
        this.e = str;
        this.n = str2;
        this.h = str3;
        this.o = strArr;
        this.m = str4;
        this.i = str5;
        this.d = str6;
    }

    public String getCpAlbumTitle() {
        return this.e;
    }

    public void setCpAlbumTitle(String str) {
        this.e = str;
    }

    public String getTitle() {
        return this.n;
    }

    public void setTitle(String str) {
        this.n = str;
    }

    public String getCover() {
        return this.h;
    }

    public void setCover(String str) {
        this.h = str;
    }

    public String[] getTips() {
        return this.o;
    }

    public void setTips(String[] strArr) {
        this.o = strArr;
    }

    public String getAuthor() {
        return this.m;
    }

    public void setAuthor(String str) {
        this.m = str;
    }

    public String getActionURL() {
        return this.i;
    }

    public void setActionURL(String str) {
        this.i = str;
    }

    public String getId() {
        return this.a;
    }

    public void setId(String str) {
        this.a = str;
    }

    public String getCp() {
        return this.b;
    }

    public void setCp(String str) {
        this.b = str;
    }

    public String getCpName() {
        return this.c;
    }

    public void setCpName(String str) {
        this.c = str;
    }

    public String getResourceType() {
        return this.j;
    }

    public int getTypeId() {
        return convert2Type(this.j);
    }

    public static int convert2Type(String str) {
        if (str == null) {
            return 0;
        }
        char c = 65535;
        int hashCode = str.hashCode();
        if (hashCode != 92896879) {
            if (hashCode != 108270587) {
                if (hashCode == 109627663 && str.equals("sound")) {
                    c = 2;
                }
            } else if (str.equals("radio")) {
                c = 1;
            }
        } else if (str.equals("album")) {
            c = 0;
        }
        switch (c) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            default:
                return 0;
        }
    }

    public void setResourceType(String str) {
        this.j = str;
    }

    public String getCategory() {
        return this.k;
    }

    public void setCategory(String str) {
        this.k = str;
    }

    public String getLastSoundTitle() {
        return this.g;
    }

    public void setLastSoundTitle(String str) {
        this.g = str;
    }

    public float getScore() {
        return this.l;
    }

    public void setScore(float f) {
        this.l = f;
    }

    public String getEid() {
        return this.d;
    }

    public void setEid(String str) {
        this.d = str;
    }

    public String getCpAlbumId() {
        return this.f;
    }

    public void setCpAlbumId(String str) {
        this.f = str;
    }

    public String toString() {
        return "AudiobookContent{cpAlbumTitle='" + this.e + "', lastSoundTitle='" + this.g + "'}";
    }
}
