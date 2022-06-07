package com.xiaomi.micolauncher.skills.video.model;

import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.elvishew.xlog.Logger;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xiaomi.ai.api.Template;
import com.xiaomi.ai.api.Video;
import com.xiaomi.ai.api.VideoPlayer;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.ChildVideoPlayList;
import com.xiaomi.micolauncher.api.model.MainPage;
import com.xiaomi.micolauncher.api.model.SkillStore;
import com.xiaomi.micolauncher.api.model.Video;
import com.xiaomi.micolauncher.application.Constants;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.track.TrackConstant;
import com.xiaomi.micolauncher.module.child.childvideo.ChildVideoDataManager;
import com.xiaomi.micolauncher.module.video.db.VideoRealmObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.StringUtils;
import org.hapjs.features.channel.IChannel;

/* loaded from: classes3.dex */
public class VideoItem implements Cloneable {
    public static final String CP_BILI = "bilibili";
    public static final String CP_IQIYI = "iqiyi";
    public static final String CP_MANGGUO_TV = "mangotv";
    public static final String CP_MITV = "mitv";
    public static final String CP_TENCENT_VIDEO = "tencent";
    public static final String CP_VIPKID = "vipkid";
    public static final String CP_YOUKU = "youku";
    public static final String TITLE_FORMAT_STRING = "（第%d集）%s";
    public static final String TYPE_LONG = "LONG";
    private boolean A;
    private int B;
    private int C;
    private long D;
    private int E;
    private List<String> F;
    private List<VideoContentProvider> G;
    private String H;
    private String I;
    private int J;
    private String K;
    private int L;
    private String a;
    private String b;
    private boolean c;
    private String d;
    private String e;
    private String f;
    private String g;
    private JSONObject h;
    private String i;
    private ArrayList<String> j;
    private String k;
    private String l;
    public String lengthType;
    public ObjectNode log;
    private String m;
    private String n;
    private String o;
    public String originDialogId;
    private String p;
    private int q;
    private int r;
    private int s;
    private int t;
    private String u;
    private LinkedList<String> v;
    public VideoContentProvider videoContentProvider;
    public String videoMediaType;
    private LinkedList<String> w;
    private LinkedList<String> x;
    private String y;
    private int z;

    public VideoItem() {
    }

    public VideoItem(int i, String str, String str2, String str3, String str4) {
        this.a = Integer.toString(i);
        this.d = str;
        this.g = str2;
        this.k = str3;
        this.l = str4;
    }

    public VideoItem(int i, String str, String str2, String str3, String str4, String str5) {
        this(i, str, str2, str3, null, str4, str5);
    }

    public VideoItem(int i, String str, String str2, String str3, String str4, String str5, String str6) {
        this.a = Integer.toString(i);
        this.d = str;
        this.g = str2;
        this.k = str3;
        if (!TextUtils.isEmpty(str6)) {
            this.videoContentProvider = new VideoContentProvider();
            StreamInfo streamInfo = new StreamInfo();
            streamInfo.url = str6;
            streamInfo.authentication = true;
            VideoContentProvider videoContentProvider = this.videoContentProvider;
            videoContentProvider.streamInfo = streamInfo;
            videoContentProvider.videoPlayer = VideoPlayerType.MI_PLAYER;
        }
        this.l = str5;
        this.m = str4;
    }

    public VideoItem(String str, String str2, String str3, String str4) {
        this.d = str;
        this.g = str2;
        this.k = str3;
        this.l = str4;
    }

    public VideoItem(String str, StreamInfo streamInfo, String str2, String str3) {
        this.g = str;
        this.k = str2;
        this.l = str3;
        this.videoContentProvider = new VideoContentProvider();
        VideoContentProvider videoContentProvider = this.videoContentProvider;
        videoContentProvider.streamInfo = streamInfo;
        videoContentProvider.videoPlayer = VideoPlayerType.MI_PLAYER;
    }

    public VideoItem(MainPage.Video video) {
        this.g = video.getIqiyi_id();
        this.e = video.getCp();
        this.c = video.isIs_vip();
        if (video.getEpisodes_id() != null) {
            this.h = JSONObject.parseObject(JSON.toJSONString(video.getEpisodes_id()));
        }
        this.k = video.getMedianame();
        this.l = video.getPoster();
        if (!(video.getMitv() == null || video.getMitv().getImages() == null || video.getMitv().getImages().getPoster() == null)) {
            this.n = video.getMitv().getImages().getPoster().getUrl();
        }
        this.o = video.getCateory();
        this.p = video.getType();
        this.q = video.getYear();
        this.r = video.getPlay_length();
        this.s = video.getEpisode_count();
        this.u = String.valueOf(video.getRating());
        if (!TextUtils.isEmpty(video.getDirectors())) {
            this.v = new LinkedList<>(Arrays.asList(video.getDirectors()));
        }
        if (!TextUtils.isEmpty(video.getActors())) {
            this.x = new LinkedList<>(Arrays.asList(video.getActors()));
            this.w = new LinkedList<>(Arrays.asList(video.getActors()));
        }
        this.y = video.getDesc();
    }

    public VideoItem(ChildVideoPlayList.MediaciinfoBean mediaciinfoBean) {
        this.g = String.valueOf(mediaciinfoBean.getMediaid());
        this.k = mediaciinfoBean.getVideoname();
        this.l = VideoModel.getInstance().getSerialImageUrl();
        this.originDialogId = VideoModel.getInstance().getDialogId();
        setCp("mitv");
        this.d = "mitv";
        this.z = mediaciinfoBean.getCi();
        this.A = mediaciinfoBean.getFee() != 1 ? false : true;
        this.C = (int) TimeUnit.SECONDS.toMillis(mediaciinfoBean.getTrial_length());
        this.B = mediaciinfoBean.getTrial_status();
        this.log = VideoModel.getInstance().getLog();
        this.videoMediaType = TrackConstant.MEDIA_TYPE_LONG_VIDEO;
    }

    /* loaded from: classes3.dex */
    public enum TrialStatus {
        STATUS_NO(0),
        STATUS_PART(1),
        STATUS_ALL(2);
        
        public int status;

        TrialStatus(int i) {
            this.status = i;
        }
    }

    public VideoItem(Video.Item item) {
        this.b = item.id;
        this.g = item.cpId;
        this.c = item.isVip;
        if (item.episodesId != null) {
            this.h = JSONObject.parseObject(JSON.toJSONString(item.episodesId));
            this.s = item.episodesId.size();
        } else {
            this.s = item.totalEpisodes;
        }
        this.k = item.name;
        this.l = item.verticalImgUrl;
        this.n = item.getHorizontalImgUrl();
        this.o = item.category;
        this.p = item.type;
        this.q = item.year;
        this.r = item.playLength;
        this.u = item.rating;
        this.e = item.cp;
        if (!TextUtils.isEmpty(item.directors)) {
            this.v = new LinkedList<>(Arrays.asList(item.directors));
        }
        if (!TextUtils.isEmpty(item.actors)) {
            this.x = new LinkedList<>(Arrays.asList(item.actors));
            this.w = new LinkedList<>(Arrays.asList(item.actors));
        }
        this.y = item.desc;
    }

    public VideoItem(Video.ShortVideo shortVideo) {
        this.g = shortVideo.cpId;
        this.e = shortVideo.cp;
        this.d = shortVideo.cp;
        this.b = shortVideo.id;
        this.k = shortVideo.name;
        this.l = shortVideo.horizontalImgUrl;
        this.n = shortVideo.horizontalImgUrl;
        this.o = shortVideo.style;
        this.r = shortVideo.playLength;
        this.videoContentProvider = new VideoContentProvider();
        StreamInfo streamInfo = new StreamInfo();
        streamInfo.url = shortVideo.url;
        streamInfo.authentication = shortVideo.auth;
        VideoContentProvider videoContentProvider = this.videoContentProvider;
        videoContentProvider.streamInfo = streamInfo;
        videoContentProvider.videoPlayer = VideoPlayerType.MI_PLAYER;
    }

    public VideoItem(MainPage.News news) {
        this.a = news.getExtend().getNewsId();
        this.e = news.getExtend().getCp();
        this.d = news.getExtend().getCp();
        this.g = news.getExtend().getNewsId();
        this.k = news.getTitle();
        this.l = "";
        String url = news.getExtend().getUrl();
        if (!TextUtils.isEmpty(url)) {
            this.videoContentProvider = new VideoContentProvider();
            StreamInfo streamInfo = new StreamInfo();
            streamInfo.url = url;
            streamInfo.authentication = true;
            VideoContentProvider videoContentProvider = this.videoContentProvider;
            videoContentProvider.streamInfo = streamInfo;
            videoContentProvider.videoPlayer = VideoPlayerType.MI_PLAYER;
        }
    }

    public VideoItem(VideoRealmObject videoRealmObject) {
        this.b = videoRealmObject.getMetaId();
        this.g = videoRealmObject.getMediaId();
        this.c = videoRealmObject.isVip();
        this.d = videoRealmObject.getCpType();
        this.e = videoRealmObject.getCpType();
        if (videoRealmObject.getEpisodesIds() != null) {
            this.h = JSONObject.parseObject(videoRealmObject.getEpisodesIds());
        }
        this.i = videoRealmObject.getVideoUrl();
        this.k = videoRealmObject.getTitle();
        this.l = videoRealmObject.getImageUrl();
        this.n = videoRealmObject.getHorizontalImageUrl();
        this.o = videoRealmObject.getCategory();
        this.p = videoRealmObject.getType();
        this.q = videoRealmObject.getYear();
        this.r = videoRealmObject.getPlayLength();
        this.s = videoRealmObject.getSetcount();
        this.u = videoRealmObject.getRating();
        this.D = videoRealmObject.getLastUpdateTime();
        if (!TextUtils.isEmpty(videoRealmObject.getDirectors())) {
            this.v = new LinkedList<>(Arrays.asList(videoRealmObject.getDirectors()));
        }
        if (!TextUtils.isEmpty(videoRealmObject.getActors())) {
            this.x = new LinkedList<>(Arrays.asList(videoRealmObject.getActors()));
            this.w = new LinkedList<>(Arrays.asList(videoRealmObject.getActors()));
        }
        this.y = videoRealmObject.getSummary();
        this.z = videoRealmObject.getCi();
        this.K = videoRealmObject.getpCode();
    }

    public long getLastUpdateTime() {
        return this.D;
    }

    public void setLastUpdateTime(long j) {
        this.D = j;
    }

    public void setImageUrl(String str) {
        this.l = str;
    }

    public int getCi() {
        return this.z;
    }

    public boolean isFee() {
        return this.A;
    }

    public void setFee(boolean z) {
        this.A = z;
    }

    public void setCi(int i) {
        this.z = i;
    }

    public String getId() {
        return this.a;
    }

    public boolean isVip() {
        return this.c;
    }

    public void setVip(boolean z) {
        this.c = z;
    }

    public String getCpType() {
        return this.d;
    }

    public String getCp() {
        return this.e;
    }

    public void setCp(String str) {
        this.e = str;
    }

    public void setCpType(String str) {
        this.d = str;
    }

    public String getMediaId() {
        return this.g;
    }

    public void setMediaId(String str) {
        this.g = str;
    }

    public JSONObject getEpisodesIds() {
        return this.h;
    }

    public void setEpisodesIds(JSONObject jSONObject) {
        this.h = jSONObject;
    }

    public void setArea(String str) {
        this.I = str;
    }

    public String getArea() {
        String str = this.I;
        return str == null ? "" : str;
    }

    public String getVideoUrl() {
        return this.i;
    }

    public VideoContentProvider getVideoContentProvider() {
        return this.videoContentProvider;
    }

    public void setVideoUrl(String str) {
        this.i = str;
    }

    public boolean isVipKid() {
        return CP_VIPKID.equals(this.e);
    }

    public boolean isIqiyi() {
        return "iqiyi".equals(this.e);
    }

    public boolean isMiTV() {
        return "mitv".equals(this.e);
    }

    public boolean isBili() {
        return "bilibili".equals(this.e);
    }

    public boolean isYouku() {
        return "youku".equals(this.e);
    }

    public boolean isMangguoTv() {
        return "mangotv".equals(this.e);
    }

    public boolean isTencentVideo() {
        return "tencent".equals(this.e);
    }

    public int getTrialStatus() {
        return this.B;
    }

    public void setTrialStatus(int i) {
        this.B = i;
    }

    public int getTrialLength() {
        return this.C;
    }

    public void setTrialLength(int i) {
        this.C = i;
    }

    public List<String> getEpisodeIds() {
        return this.F;
    }

    public boolean canTrial() {
        return this.B == TrialStatus.STATUS_PART.status;
    }

    public ArrayList<VideoItem> getSerialList() {
        ArrayList<String> arrayList = this.j;
        if (arrayList == null) {
            return null;
        }
        ArrayList<VideoItem> arrayList2 = new ArrayList<>(arrayList.size());
        int i = 0;
        while (i < this.j.size()) {
            try {
                VideoItem videoItem = (VideoItem) clone();
                videoItem.setEpisodeCount(1);
                videoItem.setVideoUrl(this.j.get(i));
                int i2 = i + 1;
                videoItem.setTitle(String.format(Locale.getDefault(), TITLE_FORMAT_STRING, Integer.valueOf(i2), videoItem.getTitle()));
                videoItem.setEpisode(i);
                arrayList2.add(videoItem);
                i = i2;
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
                return arrayList2;
            }
        }
        return arrayList2;
    }

    public String getPlayerType() {
        String str = this.i;
        return str != null ? (!str.startsWith("http") || !this.i.contains(".mp4")) ? (!this.i.contains("xl.ptxl.gitv.tv") || !this.i.contains("getCdnresource")) ? this.i.startsWith("{\"lekan_videoIdx\":") ? Constants.CpType.LEKAN : "none" : Constants.CpType.XINGYU : "none" : "none";
    }

    public boolean isSerial() {
        return this.s > 1;
    }

    public String getTitle() {
        return this.k;
    }

    public void setTitle(String str) {
        this.k = str;
    }

    public String getImageUrl() {
        return this.l;
    }

    public String getVerticalImageUrl() {
        return this.m;
    }

    public String getHorizontalImageUrl() {
        return this.n;
    }

    public String getHorizontalImage() {
        if (!TextUtils.isEmpty(this.n)) {
            return this.n;
        }
        if (!TextUtils.isEmpty(this.l)) {
            return this.l;
        }
        if (!TextUtils.isEmpty(this.m)) {
            return this.m;
        }
        return null;
    }

    public String getRecommendVerticalImage() {
        if (!TextUtils.isEmpty(this.m)) {
            return this.m;
        }
        if (!TextUtils.isEmpty(this.n)) {
            return this.n;
        }
        if (!TextUtils.isEmpty(this.l)) {
            return this.l;
        }
        return null;
    }

    public void setCategory(String str) {
        this.o = str;
    }

    public String getCategory() {
        return this.o;
    }

    public String getType() {
        return this.p;
    }

    public void setType(String str) {
        this.p = str;
    }

    public int getYear() {
        return this.q;
    }

    public void setYear(int i) {
        this.q = i;
    }

    public int getPlayLength() {
        return this.r;
    }

    public void setPlayLength(int i) {
        this.r = i;
    }

    public int getEpisodeCount() {
        return this.s;
    }

    public void setEpisodeCount(int i) {
        this.s = i;
    }

    public int getEpisode() {
        return this.t;
    }

    public void setEpisode(int i) {
        this.t = i;
    }

    public void setPlayUrl(ArrayList<String> arrayList) {
        this.j = arrayList;
    }

    public String getRating() {
        return this.u;
    }

    public void setRating(String str) {
        this.u = str;
    }

    public LinkedList<String> getDirectors() {
        return this.v;
    }

    public void setDirectors(LinkedList<String> linkedList) {
        this.v = linkedList;
    }

    public LinkedList<String> getMainActors() {
        return this.w;
    }

    public void setMainActors(LinkedList<String> linkedList) {
        this.w = linkedList;
    }

    public LinkedList<String> getActors() {
        return this.x;
    }

    public void setActors(LinkedList<String> linkedList) {
        this.x = linkedList;
    }

    public String getSummary() {
        return this.y;
    }

    public void setSummary(String str) {
        this.y = str;
    }

    public int getPosition() {
        return this.E;
    }

    public void setPosition(int i) {
        this.E = i;
    }

    public int getSerialId() {
        if (this.J == 0) {
            loadSerialId();
        }
        return this.J;
    }

    public void setSerialId(int i) {
        this.J = i;
    }

    public int getDurationInMin() {
        return this.L;
    }

    public void setDurationInMin(int i) {
        this.L = i;
    }

    public void saveSerialId() {
        SystemSetting.getUserProfile().setSerialHistory(this.g, 1);
        this.J = 1;
    }

    public List<VideoContentProvider> getCpList() {
        List<VideoContentProvider> list = this.G;
        return list == null ? new ArrayList() : list;
    }

    public void loadSerialId() {
        int serialHistory = SystemSetting.getUserProfile().getSerialHistory(this.g);
        Logger logger = L.video;
        logger.d("loadSerialId, mediaId:" + this.g + " serialId:" + serialHistory);
        setSerialId(serialHistory);
    }

    public void parseCategory(JSONObject jSONObject) {
        setCategory(jSONObject.getString("category"));
    }

    public void parseSummary(JSONObject jSONObject) {
        setSummary(jSONObject.getString(IChannel.EXTRA_ERROR_DESC));
    }

    public void parseRating(JSONObject jSONObject) {
        Float f = jSONObject.getFloat(SkillStore.SkillDetailSection.TYPE_RATING);
        float floatValue = f != null ? f.floatValue() : 0.0f;
        setRating(floatValue + "");
    }

    public void parseUrls(JSONObject jSONObject) {
        ArrayList<String> arrayList = new ArrayList<>();
        int size = jSONObject.size();
        for (int i = 0; i < size; i++) {
            arrayList.add(jSONObject.getString(i + ""));
        }
        setPlayUrl(arrayList);
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x007c A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void parseMainActors(com.alibaba.fastjson.JSONArray r11) {
        /*
            r10 = this;
            if (r11 == 0) goto L_0x0088
            int r0 = r11.size()
            if (r0 <= 0) goto L_0x0088
            java.util.LinkedList r0 = new java.util.LinkedList
            r0.<init>()
            java.util.LinkedList r1 = new java.util.LinkedList
            r1.<init>()
            java.util.LinkedList r2 = new java.util.LinkedList
            r2.<init>()
            r3 = 0
            r4 = r3
        L_0x0019:
            int r5 = r11.size()
            if (r4 >= r5) goto L_0x007f
            com.alibaba.fastjson.JSONObject r5 = r11.getJSONObject(r4)
            java.lang.String r6 = "name"
            java.lang.String r6 = r5.getString(r6)
            java.lang.String r7 = "roleType"
            java.lang.String r5 = r5.getString(r7)
            if (r6 == 0) goto L_0x007c
            int r7 = r6.length()
            if (r7 <= 0) goto L_0x007c
            if (r5 == 0) goto L_0x007c
            r7 = -1
            int r8 = r5.hashCode()
            r9 = -2103237681(0xffffffff82a323cf, float:-2.397125E-37)
            if (r8 == r9) goto L_0x0062
            r9 = 62108117(0x3b3b1d5, float:1.0561503E-36)
            if (r8 == r9) goto L_0x0058
            r9 = 1028670348(0x3d50438c, float:0.050845668)
            if (r8 == r9) goto L_0x004e
            goto L_0x006c
        L_0x004e:
            java.lang.String r8 = "DIRECTOR"
            boolean r5 = r5.equals(r8)
            if (r5 == 0) goto L_0x006c
            r5 = r3
            goto L_0x006d
        L_0x0058:
            java.lang.String r8 = "ACTOR"
            boolean r5 = r5.equals(r8)
            if (r5 == 0) goto L_0x006c
            r5 = 2
            goto L_0x006d
        L_0x0062:
            java.lang.String r8 = "MAIN_ACTOR"
            boolean r5 = r5.equals(r8)
            if (r5 == 0) goto L_0x006c
            r5 = 1
            goto L_0x006d
        L_0x006c:
            r5 = r7
        L_0x006d:
            switch(r5) {
                case 0: goto L_0x0079;
                case 1: goto L_0x0075;
                case 2: goto L_0x0071;
                default: goto L_0x0070;
            }
        L_0x0070:
            goto L_0x007c
        L_0x0071:
            r2.add(r6)
            goto L_0x007c
        L_0x0075:
            r1.add(r6)
            goto L_0x007c
        L_0x0079:
            r0.add(r6)
        L_0x007c:
            int r4 = r4 + 1
            goto L_0x0019
        L_0x007f:
            r10.setDirectors(r0)
            r10.setMainActors(r1)
            r10.setActors(r2)
        L_0x0088:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.micolauncher.skills.video.model.VideoItem.parseMainActors(com.alibaba.fastjson.JSONArray):void");
    }

    public void setAsrQuery(String str) {
        this.f = str;
    }

    public String getAsrQuery() {
        return this.f;
    }

    public void setMetaId(String str) {
        this.b = str;
    }

    public String getMetaId() {
        return this.b;
    }

    public String getpCode() {
        if (this.K == null) {
            this.K = ChildVideoDataManager.PCODE_ERTONG;
        }
        return this.K;
    }

    public void setpCode(String str) {
        this.K = str;
    }

    public boolean isPlaying2() {
        VideoItem currentPlayingVideoItem = VideoModel.getInstance().getCurrentPlayingVideoItem();
        if (currentPlayingVideoItem == null) {
            L.multicp.e("currentPlayingItem is null");
            return false;
        }
        L.multicp.d("Current playing item is %s ,\n current item is %s", currentPlayingVideoItem.toString(), toString());
        return TextUtils.equals(currentPlayingVideoItem.getId(), this.a);
    }

    public boolean isPlaying() {
        return VideoModel.getInstance().getCurrentVideoItem() == this;
    }

    public String detailComposition() {
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(this.u)) {
            sb.append(this.u);
            sb.append("分");
        }
        if (this.q != 0) {
            sb.append(StringUtils.SPACE);
            sb.append(this.q);
        }
        if (!TextUtils.isEmpty(this.I)) {
            sb.append(" | ");
            sb.append(this.I);
        }
        if (this.L > 0) {
            sb.append(" | ");
            sb.append(this.L);
            sb.append("分钟");
        }
        return sb.toString();
    }

    public String toString() {
        return "VideoItem{id=" + this.a + "metaId" + this.b + ", isVip=" + this.c + ", cpType='" + this.d + "', mediaId='" + this.g + "', episodesIds=" + this.h + ", videoUrl='" + this.i + "', title='" + this.k + "', imageUrl='" + this.l + "', horizontalImageUrl='" + this.n + "', category='" + this.o + "', type='" + this.p + "', year=" + this.q + ", playLength=" + this.r + ", episodeCount=" + this.s + ", rating='" + this.u + "', directors=" + this.v + ", mainActors=" + this.w + ", actors=" + this.x + ", summary='" + this.y + "', position=" + this.E + ", serialId=" + this.J + '}';
    }

    public void parseShortVideo(JSONObject jSONObject) {
        this.b = jSONObject.getString("id");
        this.g = jSONObject.getString("cp_id");
        this.k = jSONObject.getString("medianame");
        this.l = jSONObject.getString("posterurl");
        this.n = jSONObject.getString("posterurl");
        this.o = jSONObject.getString("category");
        this.e = jSONObject.getString("cp_type");
        this.d = jSONObject.getString("cp_type");
    }

    public VideoItem translate(Template.VideoItem videoItem, String str) {
        this.k = videoItem.getName();
        this.b = videoItem.getId();
        this.a = videoItem.getId();
        this.lengthType = videoItem.getLengthType().name();
        if (videoItem.getVideoMediaType().isPresent()) {
            this.videoMediaType = videoItem.getVideoMediaType().get().name().toLowerCase();
        }
        if (videoItem.getLog().isPresent()) {
            this.log = videoItem.getLog().get();
            JsonNode jsonNode = this.log.get("audio_id");
            if (jsonNode != null) {
                this.H = jsonNode.asText();
            }
        }
        if (videoItem.getCategory().isPresent()) {
            this.o = videoItem.getCategory().get();
        }
        if (videoItem.getDescription().isPresent()) {
            this.y = videoItem.getDescription().get();
        }
        if (videoItem.getEpisodeNum().isPresent()) {
            this.s = videoItem.getEpisodeNum().get().intValue();
        }
        if (videoItem.getPublishYear().isPresent()) {
            this.q = videoItem.getPublishYear().get().intValue();
        }
        if (videoItem.getRating().isPresent()) {
            this.u = String.valueOf(videoItem.getRating().get());
        }
        if (videoItem.getHorizontalPoster().isPresent()) {
            this.n = videoItem.getHorizontalPoster().get().getSources().get(0).getUrl();
        } else {
            Log.d("MICO.video", "translate getHorizontalImage=null");
        }
        if (videoItem.getVerticalPoster().isPresent()) {
            List<Template.ImageSource> sources = videoItem.getVerticalPoster().get().getSources();
            this.l = sources.get(0).getUrl();
            this.m = sources.get(0).getUrl();
        }
        if (videoItem.getActors().isPresent()) {
            this.v = new LinkedList<>();
            this.w = new LinkedList<>();
            this.x = new LinkedList<>();
            for (Template.VideoActor videoActor : videoItem.getActors().get()) {
                if (videoActor.getRole() == Template.VideoActorRole.DIRECTOR) {
                    this.v.add(videoActor.getName());
                } else if (videoActor.getRole() == Template.VideoActorRole.MAIN_ACTOR) {
                    this.w.add(videoActor.getName());
                } else if (videoActor.getRole() == Template.VideoActorRole.ACTOR) {
                    this.x.add(videoActor.getName());
                }
            }
        }
        this.originDialogId = VideoModel.getInstance().getDialogId();
        if (videoItem.getArea().isPresent()) {
            this.I = videoItem.getArea().get();
        }
        if (videoItem.getDurationInMin().isPresent()) {
            this.L = videoItem.getDurationInMin().get().intValue();
        }
        List<Video.ThirdPartyContentProvider> cp = videoItem.getCp();
        if (ContainerUtil.hasData(cp)) {
            this.G = a(cp, str);
            Video.ThirdPartyContentProvider thirdPartyContentProvider = cp.get(0);
            this.e = thirdPartyContentProvider.getName();
            this.d = thirdPartyContentProvider.getName();
            if (thirdPartyContentProvider.getAudioId().isPresent()) {
                this.H = thirdPartyContentProvider.getAudioId().get();
            }
            if (this.e.equals("tencent")) {
                setAsrQuery(str);
            }
            if (thirdPartyContentProvider.getResourceId().isPresent()) {
                this.g = thirdPartyContentProvider.getResourceId().get();
            }
            if (thirdPartyContentProvider.getEpisodeIds().isPresent()) {
                this.F = thirdPartyContentProvider.getEpisodeIds().get();
            }
            if (thirdPartyContentProvider.isFree().isPresent()) {
                this.c = !thirdPartyContentProvider.isFree().get().booleanValue();
            }
        }
        return this;
    }

    public VideoItem translate(Video.VideoSearchItem videoSearchItem) {
        this.k = videoSearchItem.getName();
        this.b = videoSearchItem.getId();
        this.g = videoSearchItem.getId();
        this.a = videoSearchItem.getId();
        if (videoSearchItem.getExtra().isPresent()) {
            Video.VideoExtra videoExtra = videoSearchItem.getExtra().get();
            if (videoExtra.getCategory().isPresent()) {
                this.o = videoExtra.getCategory().get();
            }
            if (videoExtra.getPublishYear().isPresent()) {
                this.q = videoExtra.getPublishYear().get().intValue();
            }
            if (videoExtra.getRating().isPresent()) {
                this.u = String.valueOf(videoExtra.getRating().get());
            }
            if (videoExtra.isFree().isPresent()) {
                this.c = !videoExtra.isFree().get().booleanValue();
            }
            if (videoExtra.getThirdCp().isPresent()) {
                Video.ThirdPartyContentProvider thirdPartyContentProvider = videoExtra.getThirdCp().get();
                this.e = thirdPartyContentProvider.getName();
                this.d = thirdPartyContentProvider.getName();
                if (thirdPartyContentProvider.getResourceId().isPresent()) {
                    this.g = thirdPartyContentProvider.getResourceId().get();
                }
                if (thirdPartyContentProvider.getEpisodeIds().isPresent()) {
                    this.F = thirdPartyContentProvider.getEpisodeIds().get();
                }
                if (thirdPartyContentProvider.isFree().isPresent()) {
                    this.A = thirdPartyContentProvider.isFree().get().booleanValue();
                }
            } else {
                this.e = "mitv";
                this.d = "mitv";
            }
        }
        this.originDialogId = VideoModel.getInstance().getDialogId();
        if (videoSearchItem.getPoster() != null) {
            List<Template.ImageSource> sources = videoSearchItem.getPoster().getSources();
            this.n = sources.get(0).getUrl();
            this.l = sources.get(0).getUrl();
            this.m = this.l;
        }
        return this;
    }

    public VideoItem translate(VideoPlayer.VideoStream videoStream) {
        if (videoStream == null) {
            return null;
        }
        this.videoContentProvider = new VideoContentProvider();
        this.videoContentProvider.streamInfo = new StreamInfo(videoStream);
        return this;
    }

    public VideoItem translate(VideoPlayer.VideoMeta videoMeta, String str, VideoPlayer.VideoStream videoStream) {
        if (videoMeta.getName().isPresent()) {
            this.k = videoMeta.getName().get();
        }
        if (videoMeta.getLengthType().isPresent()) {
            this.lengthType = videoMeta.getLengthType().get().name();
        }
        if (videoMeta.getVideoMediaType().isPresent()) {
            this.videoMediaType = videoMeta.getVideoMediaType().get().name().toLowerCase();
        }
        if (videoMeta.getLog().isPresent()) {
            this.log = videoMeta.getLog().get();
        }
        if (videoMeta.getCategory().isPresent()) {
            this.o = videoMeta.getCategory().get();
        }
        if (videoMeta.getHorizontalPoster().isPresent()) {
            List<Template.ImageSource> sources = videoMeta.getHorizontalPoster().get().getSources();
            if (ContainerUtil.hasData(sources)) {
                this.n = sources.get(0).getUrl();
            }
        }
        if (videoMeta.getArea().isPresent()) {
            this.I = videoMeta.getArea().get();
        }
        if (videoMeta.getRating().isPresent()) {
            this.u = videoMeta.getRating().get().toString();
        }
        if (videoMeta.getDurationInMin().isPresent()) {
            this.L = videoMeta.getDurationInMin().get().intValue();
        }
        if (videoMeta.getPublishTime().isPresent()) {
            try {
                this.q = Integer.parseInt(videoMeta.getPublishTime().get());
            } catch (NumberFormatException unused) {
                L.video.w("parse publishTime=%s error when VideoItem translate from VideoMeta", videoMeta.getPublishTime().get());
            }
        }
        if (videoMeta.getCpList() != null && videoMeta.getCpList().isPresent()) {
            List<Video.ThirdPartyContentProvider> list = videoMeta.getCpList().get();
            if (ContainerUtil.isEmpty(list)) {
                return null;
            }
            this.G = a(list, str);
            if (ContainerUtil.hasData(this.G)) {
                this.videoContentProvider = this.G.get(0);
            }
            Video.ThirdPartyContentProvider thirdPartyContentProvider = list.get(0);
            this.e = thirdPartyContentProvider.getName();
            this.d = thirdPartyContentProvider.getName();
            if (thirdPartyContentProvider.getResourceId().isPresent()) {
                this.g = thirdPartyContentProvider.getResourceId().get();
            }
            if (thirdPartyContentProvider.getEpisodeIds().isPresent()) {
                this.F = thirdPartyContentProvider.getEpisodeIds().get();
            }
            if (thirdPartyContentProvider.isFree().isPresent()) {
                this.A = thirdPartyContentProvider.isFree().get().booleanValue();
            }
            if (thirdPartyContentProvider.getAudioId().isPresent()) {
                this.H = thirdPartyContentProvider.getAudioId().get();
            }
            if (this.e.equals("tencent")) {
                setAsrQuery(str);
            }
        } else if (videoStream == null) {
            return null;
        } else {
            this.videoContentProvider = new VideoContentProvider();
            this.videoContentProvider.streamInfo = new StreamInfo(videoStream);
        }
        return this;
    }

    private static List<VideoContentProvider> a(List<Video.ThirdPartyContentProvider> list, String str) {
        VideoPlayer.VideoStream videoStream;
        ArrayList arrayList = new ArrayList();
        for (Video.ThirdPartyContentProvider thirdPartyContentProvider : list) {
            VideoContentProvider videoContentProvider = new VideoContentProvider();
            videoContentProvider.name = thirdPartyContentProvider.getName();
            if (thirdPartyContentProvider.getResourceId().isPresent()) {
                videoContentProvider.cpId = thirdPartyContentProvider.getResourceId().get();
            }
            if (thirdPartyContentProvider.getEpisodeIds().isPresent()) {
                videoContentProvider.episodeIds = thirdPartyContentProvider.getEpisodeIds().get();
            }
            if (thirdPartyContentProvider.getIcon().isPresent()) {
                videoContentProvider.imageUrl = thirdPartyContentProvider.getIcon().get().getSources().get(0).getUrl();
            }
            if (thirdPartyContentProvider.getPlayApp().isPresent()) {
                videoContentProvider.resId = a(thirdPartyContentProvider.getPlayApp().get());
            }
            if (thirdPartyContentProvider.getAudioId().isPresent()) {
                videoContentProvider.audioId = thirdPartyContentProvider.getAudioId().get();
            }
            if (thirdPartyContentProvider.getStream().isPresent() && (videoStream = thirdPartyContentProvider.getStream().get()) != null) {
                videoContentProvider.streamInfo = new StreamInfo();
                videoContentProvider.streamInfo.url = videoStream.getUrl();
                videoContentProvider.streamInfo.authentication = videoStream.isAuthentication();
                if (videoStream.getToken().isPresent()) {
                    videoContentProvider.streamInfo.token = videoStream.getToken().get();
                }
                if (videoStream.getOffsetInMs().isPresent()) {
                    videoContentProvider.streamInfo.offsetInMs = videoStream.getOffsetInMs().get().intValue();
                }
            }
            if (thirdPartyContentProvider.getPlayApp().isPresent()) {
                Video.CPAppType cPAppType = thirdPartyContentProvider.getPlayApp().get();
                if (cPAppType == Video.CPAppType.TENCENT_VIDEO) {
                    videoContentProvider.asrQuery = str;
                }
                videoContentProvider.videoPlayer = VideoPlayerType.valueOf(cPAppType.name());
            }
            if (thirdPartyContentProvider.isFree().isPresent()) {
                videoContentProvider.isFree = thirdPartyContentProvider.isFree().get().booleanValue();
            }
            arrayList.add(videoContentProvider);
        }
        if (ContainerUtil.hasData(arrayList)) {
            ((VideoContentProvider) arrayList.get(0)).isSelected = true;
        }
        return arrayList;
    }

    public String getAudioId() {
        String str = this.H;
        return str == null ? "" : str;
    }

    public void setAudioId(String str) {
        this.H = str;
    }

    public String getAudioIdForLog() {
        if (this.H == null) {
            this.H = a();
        }
        return !TextUtils.isEmpty(this.H) ? this.H : "";
    }

    private String a() {
        JsonNode jsonNode;
        ObjectNode objectNode = this.log;
        String asText = (objectNode == null || (jsonNode = objectNode.get("audio_id")) == null) ? null : jsonNode.asText("");
        if (this.videoContentProvider != null && TextUtils.isEmpty(asText)) {
            asText = this.videoContentProvider.getAudioId();
        }
        return asText == null ? "" : asText;
    }

    public String getOriginDialogIdForLog() {
        return TextUtils.isEmpty(this.originDialogId) ? "" : this.originDialogId;
    }

    public String getCpForLog() {
        if (!TextUtils.isEmpty(this.d)) {
            return this.d;
        }
        return !TextUtils.isEmpty(this.e) ? this.e : "";
    }

    public String getCpIdForLog() {
        return !TextUtils.isEmpty(this.g) ? this.g : "";
    }

    public ObjectNode getLogForLog() {
        ObjectNode objectNode = this.log;
        if (objectNode != null) {
            return objectNode;
        }
        return null;
    }

    public String getMediaTypeForLog() {
        if (!TextUtils.isEmpty(this.videoMediaType)) {
            return this.videoMediaType;
        }
        if ("LONG".equals(this.lengthType)) {
            return TrackConstant.MEDIA_TYPE_LONG_VIDEO;
        }
        return TrackConstant.MEDIA_TYPE_SHORT_VIDEO;
    }

    private static int a(@NonNull Video.CPAppType cPAppType) {
        switch (cPAppType) {
            case IQIYI:
                return R.drawable.icon_cps_video_aiqiyi;
            case YOUKU:
                return R.drawable.icon_cps_video_youku;
            case BILIBILI:
                return R.drawable.icon_cps_video_bilibili;
            case MANGGUO_TV:
                return R.drawable.icon_cps_video_mango;
            case TENCENT_VIDEO:
                return R.drawable.icon_cps_video_tencent;
            case MI_PLAYER:
                return R.drawable.icon_cps_video_mi;
            default:
                return 0;
        }
    }
}
