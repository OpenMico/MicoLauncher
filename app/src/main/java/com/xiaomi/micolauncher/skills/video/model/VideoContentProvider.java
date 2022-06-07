package com.xiaomi.micolauncher.skills.video.model;

import android.net.Uri;
import java.util.List;

/* loaded from: classes3.dex */
public class VideoContentProvider {
    public String asrQuery;
    public String audioId;
    public String cpId;
    public List<String> episodeIds;
    public String horizontalImageUrl;
    public String id;
    public String imageUrl;
    public boolean isFree;
    public boolean isSelected;
    public String name;
    public String playApp;
    public int resId;
    public StreamInfo streamInfo;
    public VideoPlayerType videoPlayer;

    public String getAudioId() {
        String queryParameter;
        String str = this.audioId;
        if (str != null) {
            return str;
        }
        StreamInfo streamInfo = this.streamInfo;
        if (streamInfo == null || streamInfo.url == null || (queryParameter = Uri.parse(this.streamInfo.url).getQueryParameter("audioId")) == null) {
            return null;
        }
        return queryParameter;
    }
}
