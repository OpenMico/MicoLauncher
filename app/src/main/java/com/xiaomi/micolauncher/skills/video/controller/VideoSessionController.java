package com.xiaomi.micolauncher.skills.video.controller;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.xiaomi.micolauncher.api.model.Remote;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class VideoSessionController {
    public static final int PAUSE_AFTER_FINISH = -2;
    private long a;
    private Handler b;
    private Context c;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class a {
        private static final VideoSessionController a = new VideoSessionController();
    }

    public static VideoSessionController getInstance() {
        return a.a;
    }

    public boolean hasPlayerShutdownTimer() {
        long j = this.a;
        return j > 0 || j == -2;
    }

    public VideoSessionController setContext(Context context) {
        this.c = context;
        return this;
    }

    public boolean setPlayerShutdownTimer(Remote.Request.PlayerShutdown playerShutdown) {
        if (Remote.Request.PlayerShutdown.ACTION_CANCEL.equals(playerShutdown.action)) {
            if (this.a != -2) {
                a();
            }
            this.a = 0L;
            return true;
        } else if (Remote.Request.PlayerShutdown.ACTION_PAUSE_LATER.equals(playerShutdown.action)) {
            long interval = playerShutdown.getInterval();
            this.a = (System.currentTimeMillis() / 1000) + interval;
            a(1, interval * 1000);
            return true;
        } else if (!Remote.Request.PlayerShutdown.ACTION_PAUSE_AFTER_FINISH.equals(playerShutdown.action)) {
            return false;
        } else {
            this.a = -2L;
            a();
            return true;
        }
    }

    private void a() {
        L.player.i("VideoSessionController player shutdown cancel");
        Handler handler = this.b;
        if (handler != null) {
            handler.removeMessages(1);
            this.b = null;
        }
    }

    private void a(int i, long j) {
        L.player.i("VideoSessionController player shutdown %d", Long.valueOf(j));
        if (this.b == null) {
            this.b = new Handler(Looper.getMainLooper()) { // from class: com.xiaomi.micolauncher.skills.video.controller.VideoSessionController.1
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    if (message.what == 1) {
                        if (VideoSessionController.this.c != null) {
                            ThirdPartyAppProxy.getInstance().stop(VideoSessionController.this.c);
                            ThirdPartyAppProxy.getInstance().quit(VideoSessionController.this.c);
                        }
                        VideoSessionController.this.a = 0L;
                        VideoSessionController.this.b = null;
                    }
                }
            };
        }
        this.b.removeMessages(i);
        this.b.sendEmptyMessageDelayed(i, j);
    }

    public void clearStopTime(Context context) {
        Remote.Request.PlayerShutdown playerShutdown = new Remote.Request.PlayerShutdown();
        playerShutdown.action = Remote.Request.PlayerShutdown.ACTION_CANCEL;
        getInstance().setContext(context).setPlayerShutdownTimer(playerShutdown);
    }

    public void setPlayerShutdownTimer(Context context, long j) {
        Remote.Request.PlayerShutdown playerShutdown = new Remote.Request.PlayerShutdown();
        playerShutdown.action = Remote.Request.PlayerShutdown.ACTION_PAUSE_LATER;
        playerShutdown.interval = TimeUnit.MILLISECONDS.toSeconds(j);
        getInstance().setContext(context).setPlayerShutdownTimer(playerShutdown);
    }
}
