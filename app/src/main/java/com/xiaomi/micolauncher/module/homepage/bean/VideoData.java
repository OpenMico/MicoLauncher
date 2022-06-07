package com.xiaomi.micolauncher.module.homepage.bean;

import com.google.android.exoplayer2.util.MimeTypes;
import com.google.gson.annotations.SerializedName;
import com.umeng.analytics.pro.ai;
import com.xiaomi.ai.android.track.TraceConstants;
import com.xiaomi.micolauncher.api.model.Picture;
import com.xiaomi.micolauncher.api.model.SkillStore;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface;
import io.realm.internal.RealmObjectProxy;
import java.util.List;
import org.hapjs.features.channel.IChannel;

/* loaded from: classes3.dex */
public class VideoData extends RealmObject implements com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface {
    public static final int IMG_STYLE_VERTICAL = 2;
    @SerializedName("sFrom")
    private String A;
    @SerializedName("isHotPlay")
    private boolean B;
    @SerializedName("imgStyle")
    private int C;
    @SerializedName("isVip")
    private boolean D;
    @SerializedName("sShowStatus")
    private String E;
    @SerializedName("trackKey")
    private String F;
    @SerializedName("duration")
    private long G;
    @SerializedName("lastUpdateTime")
    private long H;
    private String I;
    private String J;
    private String K;
    private String L;
    private String M;
    private String N;
    @SerializedName("pCode")
    private String O;
    @SerializedName("adInfo")
    @Ignore
    Picture.AdInfo a;
    @SerializedName("id")
    private String b;
    @SerializedName("name")
    @PrimaryKey
    private String c;
    @SerializedName("category")
    private String d;
    @SerializedName(SkillStore.SkillDetailSection.TYPE_RATING)
    private double e;
    @SerializedName("latestEpisode")
    private int f;
    @SerializedName("totalEpisode")
    private int g;
    @SerializedName("year")
    private int h;
    @SerializedName("area")
    private String i;
    @SerializedName(MimeTypes.BASE_TYPE_IMAGE)
    private String j;
    @SerializedName("seasonn")
    private int k;
    @SerializedName("mainActor")
    private String l;
    @SerializedName("isShort")
    private boolean m;
    @SerializedName(TraceConstants.Result.CP)
    private String n;
    @SerializedName("cpId")
    private String o;
    @SerializedName("idV2")
    private String p;
    @SerializedName("horizontalImgUrl")
    private String q;
    @SerializedName(IChannel.EXTRA_ERROR_DESC)
    private String r;
    @SerializedName("verticalImgUrl")
    private String s;
    @SerializedName("source")
    private String t;
    @SerializedName("tags")
    @Ignore
    private List<String> u;
    @SerializedName("subCategory")
    private String v;
    @SerializedName("webUrl")
    private String w;
    @SerializedName("audioId")
    private String x;
    @SerializedName("resourceId")
    private String y;
    @SerializedName("sMediaType")
    private String z;

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$adInfoJson() {
        return this.L;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$area() {
        return this.i;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$audioId() {
        return this.x;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$category() {
        return this.d;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$cp() {
        return this.n;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$cpId() {
        return this.o;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$desc() {
        return this.r;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public long realmGet$duration() {
        return this.G;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$expId() {
        return this.K;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$from() {
        return this.A;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$horizontalImgUrl() {
        return this.q;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$id() {
        return this.b;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$idV2() {
        return this.p;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$image() {
        return this.j;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public boolean realmGet$isHotPlay() {
        return this.B;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public boolean realmGet$isShort() {
        return this.m;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public boolean realmGet$isVip() {
        return this.D;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public long realmGet$lastUpdateTime() {
        return this.H;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public int realmGet$latestEpisode() {
        return this.f;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$mainActor() {
        return this.l;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$mediaType() {
        return this.z;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$name() {
        return this.c;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$pCode() {
        return this.O;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public double realmGet$rating() {
        return this.e;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$ratingText() {
        return this.N;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$resourceId() {
        return this.y;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public int realmGet$season() {
        return this.k;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$showStatus() {
        return this.E;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$source() {
        return this.t;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public int realmGet$style() {
        return this.C;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$subCategory() {
        return this.v;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$text() {
        return this.I;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$titleText() {
        return this.M;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public int realmGet$totalEpisode() {
        return this.g;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$traceId() {
        return this.J;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$trackKey() {
        return this.F;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$verticalImgUrl() {
        return this.s;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public String realmGet$webUrl() {
        return this.w;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public int realmGet$year() {
        return this.h;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$adInfoJson(String str) {
        this.L = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$area(String str) {
        this.i = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$audioId(String str) {
        this.x = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$category(String str) {
        this.d = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$cp(String str) {
        this.n = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$cpId(String str) {
        this.o = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$desc(String str) {
        this.r = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$duration(long j) {
        this.G = j;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$expId(String str) {
        this.K = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$from(String str) {
        this.A = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$horizontalImgUrl(String str) {
        this.q = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$id(String str) {
        this.b = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$idV2(String str) {
        this.p = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$image(String str) {
        this.j = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$isHotPlay(boolean z) {
        this.B = z;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$isShort(boolean z) {
        this.m = z;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$isVip(boolean z) {
        this.D = z;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$lastUpdateTime(long j) {
        this.H = j;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$latestEpisode(int i) {
        this.f = i;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$mainActor(String str) {
        this.l = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$mediaType(String str) {
        this.z = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$name(String str) {
        this.c = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$pCode(String str) {
        this.O = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$rating(double d) {
        this.e = d;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$ratingText(String str) {
        this.N = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$resourceId(String str) {
        this.y = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$season(int i) {
        this.k = i;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$showStatus(String str) {
        this.E = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$source(String str) {
        this.t = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$style(int i) {
        this.C = i;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$subCategory(String str) {
        this.v = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$text(String str) {
        this.I = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$titleText(String str) {
        this.M = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$totalEpisode(int i) {
        this.g = i;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$traceId(String str) {
        this.J = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$trackKey(String str) {
        this.F = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$verticalImgUrl(String str) {
        this.s = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$webUrl(String str) {
        this.w = str;
    }

    @Override // io.realm.com_xiaomi_micolauncher_module_homepage_bean_VideoDataRealmProxyInterface
    public void realmSet$year(int i) {
        this.h = i;
    }

    public VideoData() {
        if (this instanceof RealmObjectProxy) {
            ((RealmObjectProxy) this).realm$injectObjectContext();
        }
    }

    public String toString() {
        return "VideoData{name='" + realmGet$name() + "', latestEpisode=" + realmGet$latestEpisode() + ", totalEpisode=" + realmGet$totalEpisode() + ", duration=" + realmGet$duration() + ", imgStyle=" + realmGet$style() + ", rating=" + realmGet$rating() + ", cpId=" + realmGet$cpId() + ", totalEpisode=" + realmGet$totalEpisode() + ", image=" + realmGet$image() + ", desc=" + realmGet$desc() + '}';
    }

    public long getLastUpdateTime() {
        return realmGet$lastUpdateTime();
    }

    public void setLastUpdateTime(long j) {
        realmSet$lastUpdateTime(j);
    }

    public String getRatingText() {
        return realmGet$ratingText();
    }

    public void setRatingText(String str) {
        realmSet$ratingText(str);
    }

    public String getPCode() {
        return realmGet$pCode();
    }

    public void setPCode(String str) {
        realmSet$pCode(str);
    }

    public String getText() {
        return realmGet$text();
    }

    public void setText(String str) {
        realmSet$text(str);
    }

    public String getTitleText() {
        return realmGet$titleText();
    }

    public void setTitleText(String str) {
        realmSet$titleText(str);
    }

    public long getDuration() {
        return realmGet$duration();
    }

    public void setDuration(long j) {
        realmSet$duration(j);
    }

    public boolean isVerticalLayout() {
        return realmGet$style() == 2;
    }

    public int getImgStyle() {
        return realmGet$style();
    }

    public void setImgStyle(int i) {
        realmSet$style(i);
    }

    public String getTrackKey() {
        return realmGet$trackKey();
    }

    public void setTrackKey(String str) {
        realmSet$trackKey(str);
    }

    public Picture.AdInfo getAdInfo() {
        return this.a;
    }

    public void setAdInfo(Picture.AdInfo adInfo) {
        this.a = adInfo;
    }

    public boolean isVip() {
        return realmGet$isVip();
    }

    public void setVip(boolean z) {
        realmSet$isVip(z);
    }

    public boolean isHotPlay() {
        return realmGet$isHotPlay();
    }

    public void setHotPlay(boolean z) {
        realmSet$isHotPlay(z);
    }

    public String getId() {
        return realmGet$id();
    }

    public void setId(String str) {
        realmSet$id(str);
    }

    public String getName() {
        return realmGet$name();
    }

    public void setName(String str) {
        realmSet$name(str);
    }

    public String getCategory() {
        return realmGet$category();
    }

    public void setCategory(String str) {
        realmSet$category(str);
    }

    public double getRating() {
        return realmGet$rating();
    }

    public void setRating(double d) {
        realmSet$rating(d);
    }

    public int getLatestEpisode() {
        return realmGet$latestEpisode();
    }

    public void setLatestEpisode(int i) {
        realmSet$latestEpisode(i);
    }

    public int getTotalEpisode() {
        return realmGet$totalEpisode();
    }

    public void setTotalEpisode(int i) {
        realmSet$totalEpisode(i);
    }

    public int getYear() {
        return realmGet$year();
    }

    public void setYear(int i) {
        realmSet$year(i);
    }

    public String getArea() {
        return realmGet$area();
    }

    public void setArea(String str) {
        realmSet$area(str);
    }

    public String getImage() {
        return realmGet$image();
    }

    public void setImage(String str) {
        realmSet$image(str);
    }

    public int getSeason() {
        return realmGet$season();
    }

    public void setSeason(int i) {
        realmSet$season(i);
    }

    public String getMainActor() {
        return realmGet$mainActor();
    }

    public void setMainActor(String str) {
        realmSet$mainActor(str);
    }

    public boolean isShort() {
        return "shortVideo".equalsIgnoreCase(realmGet$mediaType());
    }

    public boolean isLongVideo() {
        return "longVideo".equalsIgnoreCase(realmGet$mediaType());
    }

    public boolean isAd() {
        return ai.au.equalsIgnoreCase(realmGet$mediaType());
    }

    public boolean isOp() {
        return "op".equalsIgnoreCase(realmGet$mediaType());
    }

    public void setShort(boolean z) {
        realmSet$isShort(z);
    }

    public String getCp() {
        return realmGet$cp();
    }

    public void setCp(String str) {
        realmSet$cp(str);
    }

    public String getCpId() {
        return realmGet$cpId();
    }

    public void setCpId(String str) {
        realmSet$cpId(str);
    }

    public String getIdV2() {
        return realmGet$idV2();
    }

    public void setIdV2(String str) {
        realmSet$idV2(str);
    }

    public String getHorizontalImgUrl() {
        return realmGet$horizontalImgUrl();
    }

    public void setHorizontalImgUrl(String str) {
        realmSet$horizontalImgUrl(str);
    }

    public String getDesc() {
        return realmGet$desc();
    }

    public void setDesc(String str) {
        realmSet$desc(str);
    }

    public String getVerticalImgUrl() {
        return realmGet$verticalImgUrl();
    }

    public void setVerticalImgUrl(String str) {
        realmSet$verticalImgUrl(str);
    }

    public String getSource() {
        return realmGet$source();
    }

    public void setSource(String str) {
        realmSet$source(str);
    }

    public List<String> getTags() {
        return this.u;
    }

    public void setTags(List<String> list) {
        this.u = list;
    }

    public String getSubCategory() {
        return realmGet$subCategory();
    }

    public void setSubCategory(String str) {
        realmSet$subCategory(str);
    }

    public String getWebUrl() {
        return realmGet$webUrl();
    }

    public void setWebUrl(String str) {
        realmSet$webUrl(str);
    }

    public String getAudioId() {
        return realmGet$audioId();
    }

    public void setAudioId(String str) {
        realmSet$audioId(str);
    }

    public String getResourceId() {
        return realmGet$resourceId();
    }

    public void setResourceId(String str) {
        realmSet$resourceId(str);
    }

    public String getMediaType() {
        return realmGet$mediaType();
    }

    public void setMediaType(String str) {
        realmSet$mediaType(str);
    }

    public String getFrom() {
        return realmGet$from();
    }

    public void setFrom(String str) {
        realmSet$from(str);
    }

    public String getTraceId() {
        return realmGet$traceId();
    }

    public void setTraceId(String str) {
        realmSet$traceId(str);
    }

    public String getExpId() {
        return realmGet$expId();
    }

    public void setExpId(String str) {
        realmSet$expId(str);
    }

    public String getAdInfoJson() {
        return realmGet$adInfoJson();
    }

    public void setAdInfoJson(String str) {
        realmSet$adInfoJson(str);
    }
}
