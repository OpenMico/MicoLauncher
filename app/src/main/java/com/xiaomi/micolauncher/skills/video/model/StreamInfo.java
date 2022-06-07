package com.xiaomi.micolauncher.skills.video.model;

import android.text.TextUtils;
import com.xiaomi.ai.api.AudioPlayer;
import com.xiaomi.ai.api.VideoPlayer;
import com.xiaomi.micolauncher.skills.personalize.manager.StreamHelper;
import java.io.Serializable;

/* loaded from: classes3.dex */
public class StreamInfo implements Serializable {
    public boolean authentication;
    public transient int durationInMs;
    public transient int offsetInMs;
    public boolean redirect;
    public String token;
    public String url;

    public StreamInfo() {
    }

    public StreamInfo(AudioPlayer.Stream stream) {
        this.url = stream.getUrl();
        if (stream.getToken().isPresent()) {
            this.token = stream.getToken().get();
        }
        this.durationInMs = stream.getDurationInMs();
        this.offsetInMs = stream.getOffsetInMs();
        this.authentication = stream.isAuthentication();
        if (stream.isRedirect().isPresent()) {
            this.redirect = stream.isRedirect().get().booleanValue();
        }
    }

    public StreamInfo(VideoPlayer.VideoStream videoStream) {
        this.url = videoStream.getUrl();
        if (videoStream.getToken().isPresent()) {
            this.token = videoStream.getToken().get();
        }
        if (videoStream.getOffsetInMs().isPresent()) {
            this.offsetInMs = videoStream.getOffsetInMs().get().intValue();
        }
        this.authentication = videoStream.isAuthentication();
    }

    public String getStreamUrl(String str) {
        if (!this.authentication) {
            return this.url;
        }
        return StreamHelper.buildUri(this.url, str, this.token);
    }

    public boolean isValid() {
        return !TextUtils.isEmpty(this.url);
    }
}
