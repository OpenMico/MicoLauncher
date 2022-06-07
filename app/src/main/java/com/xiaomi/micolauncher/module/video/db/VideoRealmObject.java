package com.xiaomi.micolauncher.module.video.db;

import com.alibaba.fastjson.JSONObject;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;
import io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface;
import io.realm.internal.RealmObjectProxy;
import java.util.Iterator;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes3.dex */
public class VideoRealmObject extends RealmObject implements com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface {
    @PrimaryKey
    @Required
    private String a;
    private String b;
    private boolean c;
    private String d;
    private String e;
    private String f;
    private String g;
    private String h;
    private String i;
    private String j;
    private String k;
    private String l;
    private int m;
    private int n;
    private int o;
    private String p;
    private String q;
    private String r;
    private String s;
    private String t;
    private int u;
    private long v;
    private String w;

    @Override // io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public String realmGet$actors() {
        return this.s;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public String realmGet$category() {
        return this.k;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public int realmGet$ci() {
        return this.u;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public String realmGet$cpType() {
        return this.d;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public String realmGet$directors() {
        return this.q;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public String realmGet$episodesIds() {
        return this.e;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public String realmGet$horizontalImageUrl() {
        return this.j;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public String realmGet$imageUrl() {
        return this.i;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public boolean realmGet$isVip() {
        return this.c;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public String realmGet$lastEpisod() {
        return this.f;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public long realmGet$lastUpdateTime() {
        return this.v;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public String realmGet$mainActors() {
        return this.r;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public String realmGet$mediaId() {
        return this.a;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public String realmGet$metaId() {
        return this.b;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public String realmGet$pCode() {
        return this.w;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public int realmGet$playLength() {
        return this.n;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public String realmGet$rating() {
        return this.p;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public int realmGet$setcount() {
        return this.o;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public String realmGet$summary() {
        return this.t;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public String realmGet$title() {
        return this.h;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public String realmGet$type() {
        return this.l;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public String realmGet$videoUrl() {
        return this.g;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public int realmGet$year() {
        return this.m;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public void realmSet$actors(String str) {
        this.s = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public void realmSet$category(String str) {
        this.k = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public void realmSet$ci(int i) {
        this.u = i;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public void realmSet$cpType(String str) {
        this.d = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public void realmSet$directors(String str) {
        this.q = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public void realmSet$episodesIds(String str) {
        this.e = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public void realmSet$horizontalImageUrl(String str) {
        this.j = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public void realmSet$imageUrl(String str) {
        this.i = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public void realmSet$isVip(boolean z) {
        this.c = z;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public void realmSet$lastEpisod(String str) {
        this.f = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public void realmSet$lastUpdateTime(long j) {
        this.v = j;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public void realmSet$mainActors(String str) {
        this.r = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public void realmSet$mediaId(String str) {
        this.a = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public void realmSet$metaId(String str) {
        this.b = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public void realmSet$pCode(String str) {
        this.w = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public void realmSet$playLength(int i) {
        this.n = i;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public void realmSet$rating(String str) {
        this.p = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public void realmSet$setcount(int i) {
        this.o = i;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public void realmSet$summary(String str) {
        this.t = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public void realmSet$title(String str) {
        this.h = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public void realmSet$type(String str) {
        this.l = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public void realmSet$videoUrl(String str) {
        this.g = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_video_db_VideoRealmObjectRealmProxyInterface
    public void realmSet$year(int i) {
        this.m = i;
    }

    public VideoRealmObject() {
        if (this instanceof RealmObjectProxy) {
            ((RealmObjectProxy) this).realm$injectObjectContext();
        }
    }

    public VideoRealmObject(VideoItem videoItem) {
        if (this instanceof RealmObjectProxy) {
            ((RealmObjectProxy) this).realm$injectObjectContext();
        }
        realmSet$metaId(videoItem.getMetaId());
        realmSet$mediaId(videoItem.getMediaId());
        realmSet$isVip(videoItem.isVip());
        realmSet$cpType(videoItem.getCp());
        realmSet$episodesIds(JSONObject.toJSONString(videoItem.getEpisodesIds()));
        realmSet$videoUrl(videoItem.getVideoUrl());
        realmSet$title(videoItem.getTitle());
        realmSet$imageUrl(videoItem.getImageUrl());
        realmSet$horizontalImageUrl(videoItem.getHorizontalImageUrl());
        realmSet$category(videoItem.getCategory());
        realmSet$type(videoItem.getType());
        realmSet$year(videoItem.getYear());
        realmSet$playLength(videoItem.getPlayLength());
        realmSet$setcount(videoItem.getEpisodeCount());
        realmSet$rating(videoItem.getRating());
        realmSet$summary(videoItem.getSummary());
        realmSet$ci(videoItem.getCi());
        realmSet$pCode(videoItem.getpCode());
        if (videoItem.getDirectors() != null && videoItem.getDirectors().size() > 0) {
            StringBuilder sb = new StringBuilder();
            Iterator<String> it = videoItem.getDirectors().iterator();
            while (it.hasNext()) {
                sb.append(it.next());
                sb.append(StringUtils.SPACE);
            }
            realmSet$directors(sb.toString());
        }
        if (videoItem.getMainActors() != null && videoItem.getMainActors().size() > 0) {
            StringBuilder sb2 = new StringBuilder();
            Iterator<String> it2 = videoItem.getMainActors().iterator();
            while (it2.hasNext()) {
                sb2.append(it2.next());
                sb2.append(StringUtils.SPACE);
            }
            realmSet$mainActors(sb2.toString());
        }
        if (videoItem.getActors() != null && videoItem.getActors().size() > 0) {
            StringBuilder sb3 = new StringBuilder();
            Iterator<String> it3 = videoItem.getActors().iterator();
            while (it3.hasNext()) {
                sb3.append(it3.next());
                sb3.append(StringUtils.SPACE);
            }
            realmSet$actors(sb3.toString());
        }
    }

    public int getCi() {
        return realmGet$ci();
    }

    public void setCi(int i) {
        realmSet$ci(i);
    }

    public String getMediaId() {
        return realmGet$mediaId();
    }

    public void setMediaId(String str) {
        realmSet$mediaId(str);
    }

    public String getMetaId() {
        return realmGet$metaId();
    }

    public boolean isVip() {
        return realmGet$isVip();
    }

    public void setVip(boolean z) {
        realmSet$isVip(z);
    }

    public void setHorizontalImageUrl(String str) {
        realmSet$horizontalImageUrl(str);
    }

    public String getCpType() {
        return realmGet$cpType();
    }

    public void setCpType(String str) {
        realmSet$cpType(str);
    }

    public String getEpisodesIds() {
        return realmGet$episodesIds();
    }

    public void setEpisodesIds(String str) {
        realmSet$episodesIds(str);
    }

    public String getLastEpisod() {
        return realmGet$lastEpisod();
    }

    public void setLastEpisod(String str) {
        realmSet$lastEpisod(str);
    }

    public String getVideoUrl() {
        return realmGet$videoUrl();
    }

    public void setVideoUrl(String str) {
        realmSet$videoUrl(str);
    }

    public String getTitle() {
        return realmGet$title();
    }

    public void setTitle(String str) {
        realmSet$title(str);
    }

    public String getImageUrl() {
        return realmGet$imageUrl();
    }

    public String getHorizontalImageUrl() {
        return realmGet$horizontalImageUrl();
    }

    public void setImageUrl(String str) {
        realmSet$imageUrl(str);
    }

    public String getCategory() {
        return realmGet$category();
    }

    public void setCategory(String str) {
        realmSet$category(str);
    }

    public String getType() {
        return realmGet$type();
    }

    public void setType(String str) {
        realmSet$type(str);
    }

    public int getYear() {
        return realmGet$year();
    }

    public void setYear(int i) {
        realmSet$year(i);
    }

    public int getPlayLength() {
        return realmGet$playLength();
    }

    public void setPlayLength(int i) {
        realmSet$playLength(i);
    }

    public int getSetcount() {
        return realmGet$setcount();
    }

    public void setSetcount(int i) {
        realmSet$setcount(i);
    }

    public String getRating() {
        return realmGet$rating();
    }

    public void setRating(String str) {
        realmSet$rating(str);
    }

    public String getDirectors() {
        return realmGet$directors();
    }

    public void setDirectors(String str) {
        realmSet$directors(str);
    }

    public String getMainActors() {
        return realmGet$mainActors();
    }

    public void setMainActors(String str) {
        realmSet$mainActors(str);
    }

    public String getActors() {
        return realmGet$actors();
    }

    public void setActors(String str) {
        realmSet$actors(str);
    }

    public String getSummary() {
        return realmGet$summary();
    }

    public void setSummary(String str) {
        realmSet$summary(str);
    }

    public long getLastUpdateTime() {
        return realmGet$lastUpdateTime();
    }

    public void setLastUpdateTime(long j) {
        realmSet$lastUpdateTime(j);
    }

    public String getpCode() {
        return realmGet$pCode();
    }

    public void setpCode(String str) {
        realmSet$pCode(str);
    }
}
