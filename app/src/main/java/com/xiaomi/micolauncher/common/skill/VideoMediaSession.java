package com.xiaomi.micolauncher.common.skill;

import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import com.elvishew.xlog.Logger;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;

/* loaded from: classes3.dex */
public class VideoMediaSession {
    private static volatile VideoMediaSession a;
    private VideoItem d;
    private final PlaybackState.Builder c = new PlaybackState.Builder().setActions(516);
    private final MediaSession b = new MediaSession(MicoApplication.getGlobalContext(), "MICO.player.video");
    private boolean e = false;

    private VideoMediaSession() {
        L.video.d("[VideoMediaSession]: VideoMediaSession");
    }

    private void a(int i) {
        Logger logger = L.video;
        logger.d("[VideoMediaSession]: setPlayState.state=" + i);
        switch (i) {
            case 1:
            case 2:
                if (this.b.isActive()) {
                    this.b.setActive(false);
                    break;
                }
                break;
            case 3:
                if (!this.b.isActive()) {
                    this.b.setActive(true);
                    break;
                }
                break;
        }
        this.c.setState(i, 0L, 0.0f);
        this.b.setPlaybackState(this.c.build());
    }

    public static VideoMediaSession getInstance() {
        if (a == null) {
            synchronized (VideoMediaSession.class) {
                a = new VideoMediaSession();
            }
        }
        return a;
    }

    public void setPlaying() {
        setPlaying(false);
    }

    public void setPlaying(boolean z) {
        a(3);
        this.e = z;
    }

    public void setPlayerString(VideoItem videoItem) {
        this.d = videoItem;
    }

    public VideoItem getCurrentPlayingItem() {
        return this.d;
    }

    public void setPaused() {
        a(2);
    }

    public void setStopped() {
        a(1);
        this.d = null;
    }

    public boolean isPlaying() {
        return getPlayStatus() == 1;
    }

    public boolean isInPlayingPage() {
        return a(false) == 1;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:25:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int a(boolean r9) {
        /*
            r8 = this;
            com.xiaomi.micolauncher.skills.video.model.IqiyiModel r0 = com.xiaomi.micolauncher.skills.video.model.IqiyiModel.getInstance()
            com.qiyi.video.pad.service.QYPlayerInfo r0 = r0.getPlayerInfo()
            r1 = 2
            r2 = 0
            r3 = 1
            if (r0 == 0) goto L_0x002c
            com.elvishew.xlog.Logger r4 = com.xiaomi.micolauncher.common.L.video
            java.lang.String r5 = "iqiyi: getPlayStatus.play_status=%s"
            java.lang.Object[] r6 = new java.lang.Object[r3]
            int r7 = r0.player_status
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)
            r6[r2] = r7
            r4.d(r5, r6)
            int r4 = r0.player_status
            r5 = 5
            if (r4 != r5) goto L_0x0025
            r0 = r3
            goto L_0x002d
        L_0x0025:
            int r0 = r0.player_status
            r4 = 6
            if (r0 != r4) goto L_0x002c
            r0 = r1
            goto L_0x002d
        L_0x002c:
            r0 = r2
        L_0x002d:
            if (r3 == r0) goto L_0x0062
            android.media.session.MediaSession r4 = r8.b
            android.media.session.MediaController r4 = r4.getController()
            android.media.session.PlaybackState r4 = r4.getPlaybackState()
            com.elvishew.xlog.Logger r5 = com.xiaomi.micolauncher.common.L.video
            java.lang.String r6 = "mMediaSession.playbackState=%s, shortVideo=%s"
            java.lang.Object[] r1 = new java.lang.Object[r1]
            if (r4 != 0) goto L_0x0044
            java.lang.String r7 = "null"
            goto L_0x0045
        L_0x0044:
            r7 = r4
        L_0x0045:
            r1[r2] = r7
            boolean r2 = r8.e
            java.lang.Boolean r2 = java.lang.Boolean.valueOf(r2)
            r1[r3] = r2
            r5.d(r6, r1)
            if (r4 == 0) goto L_0x0062
            if (r9 == 0) goto L_0x005a
            boolean r9 = r8.e
            if (r9 == 0) goto L_0x0062
        L_0x005a:
            int r9 = r4.getState()
            r1 = 3
            if (r9 != r1) goto L_0x0062
            r0 = r3
        L_0x0062:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.micolauncher.common.skill.VideoMediaSession.a(boolean):int");
    }

    public int getPlayStatus() {
        return a(true);
    }
}
