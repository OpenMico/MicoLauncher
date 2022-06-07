package com.xiaomi.miplay.mylibrary.mirror;

import android.app.Service;
import android.content.Intent;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;

/* loaded from: classes4.dex */
public class ShotApplication {
    private static ShotApplication d;
    private int a = 0;
    private Intent b = null;
    private MediaProjectionManager c = null;
    private Service e;

    public static ShotApplication getInstance() {
        if (d == null) {
            d = new ShotApplication();
        }
        return d;
    }

    public int getResult() {
        return this.a;
    }

    public Intent getIntent() {
        return this.b;
    }

    public void setService(Service service) {
        this.e = service;
    }

    public MediaProjectionManager getMediaProjectionManager() {
        if (this.c == null) {
            this.c = (MediaProjectionManager) this.e.getApplication().getSystemService("media_projection");
        }
        return this.c;
    }

    public void setResult(int i) {
        this.a = i;
    }

    public void setIntent(Intent intent) {
        this.b = intent;
    }

    public void setMediaProjectionManager(MediaProjectionManager mediaProjectionManager) {
        this.c = mediaProjectionManager;
    }

    public MediaProjection getMediaProjection() {
        return this.c.getMediaProjection(this.a, this.b);
    }
}
