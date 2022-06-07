package com.xiaomi.micolauncher.skills.video.model;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.elvishew.xlog.Logger;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xiaomi.ai.api.Education;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.api.model.ChildVideo;
import com.xiaomi.micolauncher.api.model.Directive;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.skills.video.controller.uievent.VideoPlayNextEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class VideoModel {
    public static String KEY_VIDEO_INDEX = "video_index";
    public static String KEY_VIDEO_ITEM_ID = "video_item_id";
    public static final String URL_IQIYI_PLAYER_PREFIX = "iqiyi://com.qiyi.video.speaker/player?from=xiaoai&command=";
    private volatile VideoMode a;
    private volatile List<VideoItem> b;
    private int c;
    private volatile int d;
    private volatile ArrayList<VideoItem> e;
    private final List<VideoItem> f;
    private List<Education.EduSearchTag> g;
    private String h;
    private String i;
    public volatile boolean isAutoPlay;
    private String j;
    private boolean k;
    private int l;
    public ObjectNode log;
    private volatile ChildVideo.MiTvType m;
    private long n;
    private String o;
    private volatile VideoItem p;
    private List<OnDataChangeListener> q;
    private String r;
    private boolean s;
    private String t;
    private boolean u;

    /* loaded from: classes3.dex */
    public interface OnDataChangeListener {
        void onDataSetChanged();

        void onIndexChanged();
    }

    /* loaded from: classes3.dex */
    public enum ShotType {
        TYPE_MULTI_MULTI,
        TYPE_SINGLE_MULTI,
        TYPE_SINGLE_SINGLE,
        TYPE_NONE
    }

    /* loaded from: classes3.dex */
    public static class a {
        private static final VideoModel a = new VideoModel();
    }

    private VideoModel() {
        this.a = VideoMode.MV;
        this.c = 0;
        this.e = new ArrayList<>();
        this.f = new ArrayList();
        this.g = new ArrayList();
        this.l = 1;
    }

    public static VideoModel getInstance() {
        return a.a;
    }

    public VideoMode getMode() {
        return this.a;
    }

    public synchronized List<VideoItem> getPlayList() {
        return this.b;
    }

    public synchronized void setPlayList(ArrayList<VideoItem> arrayList) {
        this.b = arrayList;
    }

    public synchronized void clearPlayList() {
        if (this.b != null) {
            this.b.clear();
        }
    }

    public synchronized void addPlayList(List<VideoItem> list) {
        if (this.b != null) {
            this.c = this.b.size();
            this.b.addAll(list);
            if (ContainerUtil.hasData(this.q)) {
                for (OnDataChangeListener onDataChangeListener : this.q) {
                    onDataChangeListener.onDataSetChanged();
                }
            }
        } else {
            this.b = list;
        }
    }

    public synchronized int getOldSize() {
        return this.c;
    }

    public synchronized VideoItem getVideoItem(int i) {
        if (ContainerUtil.isOutOfBound(i, this.b)) {
            return null;
        }
        return this.b.get(i);
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0011, code lost:
        if (r3 >= (r2.b.size() - 1)) goto L_0x0015;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized boolean isLastResource(int r3) {
        /*
            r2 = this;
            monitor-enter(r2)
            java.util.List<com.xiaomi.micolauncher.skills.video.model.VideoItem> r0 = r2.b     // Catch: all -> 0x0017
            boolean r0 = com.xiaomi.mico.common.ContainerUtil.hasData(r0)     // Catch: all -> 0x0017
            r1 = 1
            if (r0 == 0) goto L_0x0014
            java.util.List<com.xiaomi.micolauncher.skills.video.model.VideoItem> r0 = r2.b     // Catch: all -> 0x0017
            int r0 = r0.size()     // Catch: all -> 0x0017
            int r0 = r0 - r1
            if (r3 < r0) goto L_0x0014
            goto L_0x0015
        L_0x0014:
            r1 = 0
        L_0x0015:
            monitor-exit(r2)
            return r1
        L_0x0017:
            r3 = move-exception
            monitor-exit(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.micolauncher.skills.video.model.VideoModel.isLastResource(int):boolean");
    }

    public synchronized boolean hasResource() {
        return !ContainerUtil.isEmpty(this.b);
    }

    public boolean isPlayingIndex(int i) {
        return this.d == i;
    }

    public synchronized int nextPlayIndex() {
        if (ContainerUtil.isEmpty(this.b)) {
            return 0;
        }
        return ((this.d + 1) + this.b.size()) % this.b.size();
    }

    public synchronized int prevPlayIndex() {
        if (ContainerUtil.isEmpty(this.b)) {
            return 0;
        }
        return ((this.d - 1) + this.b.size()) % this.b.size();
    }

    public synchronized int playListSize() {
        if (ContainerUtil.isEmpty(this.b)) {
            return 0;
        }
        return this.b.size();
    }

    public VideoItem getRecommendationVideoItem(String str) {
        if (this.e == null || this.e.size() <= 0) {
            return null;
        }
        for (int i = 0; i < this.e.size(); i++) {
            VideoItem videoItem = this.e.get(i);
            if (ContainerUtil.hasData(str) && str.equals(videoItem.getId())) {
                return videoItem;
            }
        }
        return null;
    }

    public ArrayList<VideoItem> getRecommendationList() {
        return this.e;
    }

    public synchronized void setRecommendationList(List<VideoItem> list, String str) {
        this.e.clear();
        if (ContainerUtil.hasData(list)) {
            this.e.addAll(list);
        }
        setDialogId(str);
        setSearchTagList(ContainerUtil.getEmptyList());
    }

    public synchronized void setMultiCpRecommendationList(List<VideoItem> list, String str) {
        this.f.clear();
        if (ContainerUtil.hasData(list)) {
            this.f.addAll(list);
        }
        setDialogId(str);
        setSearchTagList(ContainerUtil.getEmptyList());
    }

    public List<VideoItem> getMultiCpRecommendationList() {
        return this.f;
    }

    public List<Education.EduSearchTag> getSearchTagList() {
        return this.g;
    }

    public void setSearchTagList(List<Education.EduSearchTag> list) {
        this.g = list;
    }

    public void setPlayList(VideoMode videoMode, List<VideoItem> list) {
        this.a = videoMode;
        this.b = list;
        this.d = 0;
    }

    public void setMode(VideoMode videoMode) {
        this.a = videoMode;
        this.d = 0;
    }

    public ChildVideo.MiTvType getMiTvType() {
        if (this.m == null) {
            return ChildVideo.MiTvType.CHILD_VIDEO;
        }
        return this.m;
    }

    public void setMiTvType(ChildVideo.MiTvType miTvType) {
        this.m = miTvType;
    }

    public void setMiTvHasNext(boolean z) {
        this.k = z;
    }

    public synchronized boolean getMiTvHasNext() {
        return this.k;
    }

    public synchronized int getPage() {
        return this.l;
    }

    public synchronized void setPage(int i) {
        this.l = i;
    }

    public String getSerialId() {
        return this.i;
    }

    public void setSerialId(String str) {
        this.i = str;
    }

    public String getSerialImageUrl() {
        return this.j;
    }

    public void setSerialImageUrl(String str) {
        this.j = str;
    }

    public String getDialogOrigin() {
        return this.o;
    }

    public void setDialogOrigin(String str) {
        this.o = str;
    }

    public void setPlayIndex(int i) {
        Logger logger = L.video;
        logger.d("setPlayIndex=" + i);
        if (ContainerUtil.hasData(this.q)) {
            for (OnDataChangeListener onDataChangeListener : this.q) {
                if (this.d != i) {
                    onDataChangeListener.onIndexChanged();
                }
            }
        }
        if (ContainerUtil.isEmpty(this.b)) {
            this.d = i;
        } else if (i < 0) {
            this.d = 0;
        } else if (i >= this.b.size()) {
            this.d = this.b.size() - 1;
        } else {
            this.d = i;
        }
    }

    public int getPlayIndex() {
        return this.d;
    }

    public VideoItem getCurrentVideoItem() {
        if (this.b != null && this.d >= 0 && this.d < this.b.size()) {
            return this.b.get(this.d);
        }
        return null;
    }

    public VideoItem getCurrentPlayingVideoItem() {
        return this.p;
    }

    public synchronized void setCurrentPlayingVideoItem(VideoItem videoItem) {
        this.p = videoItem;
    }

    public void playVideo(int i, int i2, boolean z) {
        VideoItem videoItem = getVideoItem(i);
        if (videoItem != null) {
            VideoPlayNextEvent videoPlayNextEvent = new VideoPlayNextEvent();
            videoPlayNextEvent.videoItem = videoItem;
            videoPlayNextEvent.ci = i2;
            videoPlayNextEvent.index = i;
            videoPlayNextEvent.isReport = z;
            EventBusRegistry.getEventBus().post(videoPlayNextEvent);
        }
    }

    public void clear() {
        if (this.b != null) {
            this.b.clear();
        }
        this.d = 0;
        this.l = 1;
        this.k = false;
    }

    public String getRequestId() {
        return this.h;
    }

    public boolean extractVideoList(Directive directive, int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(directive);
        return extractVideoList(arrayList, i);
    }

    public boolean extractVideoList(List<Directive> list, int i) {
        ArrayList arrayList = new ArrayList();
        Iterator<Directive> it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Directive next = it.next();
            if ("media".equals(next.type)) {
                for (int i2 = 0; i2 < next.items.size(); i2++) {
                    Directive.DirectiveItem directiveItem = next.items.get(i2);
                    if (directiveItem instanceof Directive.Resource) {
                        Directive.Resource resource = (Directive.Resource) directiveItem;
                        String str = resource.cp;
                        String str2 = resource.id;
                        String str3 = resource.cover;
                        arrayList.add(new VideoItem(i + 1, str, str2, resource.title, str3 == null ? "https://s01.mifile.cn/i/logo-footer.png?v2" : str3));
                    }
                }
            }
        }
        if (arrayList.size() <= 0) {
            return false;
        }
        this.b.addAll(arrayList);
        return true;
    }

    public boolean extractMVVideoList(Directive directive, int i) {
        Directive.Resource resource;
        Directive.ResourceExtend resourceExtend;
        ArrayList arrayList = new ArrayList();
        if ("media".equals(directive.type)) {
            for (int i2 = 0; i2 < directive.items.size(); i2++) {
                Directive.DirectiveItem directiveItem = directive.items.get(i2);
                if ((directiveItem instanceof Directive.Resource) && (resourceExtend = (resource = (Directive.Resource) directiveItem).extend) != null && !TextUtils.isEmpty(resourceExtend.mvId)) {
                    arrayList.add(new VideoItem(i + i2, resource.cp, resourceExtend.mvId, TextUtils.isEmpty(resourceExtend.originSong) ? resource.title : resourceExtend.originSong, resource.cover));
                }
            }
        }
        if (arrayList.size() <= 0) {
            return false;
        }
        this.b.addAll(arrayList);
        return true;
    }

    public boolean isLongVideo(VideoItem videoItem) {
        return !ThirdPartyAppProxy.getInstance().isCurrentProcessorIsMicoProcessor() || videoItem.isMiTV();
    }

    public boolean isLongVideo() {
        return getMode() == VideoMode.MITV_SERIAL;
    }

    public void setDialogId(String str) {
        this.r = str;
    }

    public ObjectNode getLog() {
        return this.log;
    }

    public void setLog(ObjectNode objectNode) {
        this.log = objectNode;
    }

    public void setNeedLoadMore(boolean z) {
        this.s = z;
    }

    public void setLoadMoreToken(String str) {
        this.t = str;
    }

    public String getLoadMoreToken() {
        return this.t;
    }

    public String getDialogId() {
        return this.r;
    }

    public void setDependence(boolean z) {
        this.u = z;
    }

    public boolean isDependence() {
        return this.u;
    }

    public boolean isNeedLoadMore() {
        return this.s;
    }

    public void addOnDataChangeListener(@NonNull OnDataChangeListener onDataChangeListener) {
        if (this.q == null) {
            this.q = new ArrayList();
        }
        if (!this.q.contains(onDataChangeListener)) {
            this.q.add(onDataChangeListener);
        }
    }

    public void removeOnDataChangeListener(OnDataChangeListener onDataChangeListener) {
        if (ContainerUtil.hasData(this.q)) {
            this.q.remove(onDataChangeListener);
        }
    }

    public synchronized ShotType getShotType() {
        if (!this.isAutoPlay) {
            return ShotType.TYPE_NONE;
        }
        int size = getMultiCpRecommendationList().size();
        if (size <= 0) {
            return ShotType.TYPE_NONE;
        } else if (size == 1) {
            return getMultiCpRecommendationList().get(0).getCpList().size() > 1 ? ShotType.TYPE_SINGLE_MULTI : ShotType.TYPE_SINGLE_SINGLE;
        } else {
            return ShotType.TYPE_MULTI_MULTI;
        }
    }

    public long getCmdVideoDuration() {
        return this.n;
    }

    public void setCmdVideoDuration(long j) {
        this.n = j;
    }
}
