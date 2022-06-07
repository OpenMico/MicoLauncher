package com.google.android.exoplayer2.video.spherical;

import android.graphics.SurfaceTexture;
import android.media.MediaFormat;
import android.opengl.GLES20;
import android.opengl.Matrix;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.GlUtil;
import com.google.android.exoplayer2.util.TimedValueQueue;
import com.google.android.exoplayer2.video.VideoFrameMetadataListener;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: SceneRenderer.java */
/* loaded from: classes2.dex */
public final class d implements VideoFrameMetadataListener, CameraMotionListener {
    private int i;
    private SurfaceTexture j;
    @Nullable
    private byte[] m;
    private final AtomicBoolean a = new AtomicBoolean();
    private final AtomicBoolean b = new AtomicBoolean(true);
    private final c c = new c();
    private final a d = new a();
    private final TimedValueQueue<Long> e = new TimedValueQueue<>();
    private final TimedValueQueue<Projection> f = new TimedValueQueue<>();
    private final float[] g = new float[16];
    private final float[] h = new float[16];
    private volatile int k = 0;
    private int l = -1;

    public void a(int i) {
        this.k = i;
    }

    public SurfaceTexture a() {
        GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
        GlUtil.checkGlError();
        this.c.a();
        GlUtil.checkGlError();
        this.i = GlUtil.createExternalTexture();
        this.j = new SurfaceTexture(this.i);
        this.j.setOnFrameAvailableListener(new SurfaceTexture.OnFrameAvailableListener() { // from class: com.google.android.exoplayer2.video.spherical.-$$Lambda$d$1sVAihiZBBGPyAarS7YgD9MXuoY
            @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
            public final void onFrameAvailable(SurfaceTexture surfaceTexture) {
                d.this.a(surfaceTexture);
            }
        });
        return this.j;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(SurfaceTexture surfaceTexture) {
        this.a.set(true);
    }

    public void a(float[] fArr, boolean z) {
        GLES20.glClear(16384);
        GlUtil.checkGlError();
        if (this.a.compareAndSet(true, false)) {
            ((SurfaceTexture) Assertions.checkNotNull(this.j)).updateTexImage();
            GlUtil.checkGlError();
            if (this.b.compareAndSet(true, false)) {
                Matrix.setIdentityM(this.g, 0);
            }
            long timestamp = this.j.getTimestamp();
            Long poll = this.e.poll(timestamp);
            if (poll != null) {
                this.d.a(this.g, poll.longValue());
            }
            Projection pollFloor = this.f.pollFloor(timestamp);
            if (pollFloor != null) {
                this.c.b(pollFloor);
            }
        }
        Matrix.multiplyMM(this.h, 0, fArr, 0, this.g, 0);
        this.c.a(this.i, this.h, z);
    }

    @Override // com.google.android.exoplayer2.video.VideoFrameMetadataListener
    public void onVideoFrameAboutToBeRendered(long j, long j2, Format format, @Nullable MediaFormat mediaFormat) {
        this.e.add(j2, Long.valueOf(j));
        a(format.projectionData, format.stereoMode, j2);
    }

    @Override // com.google.android.exoplayer2.video.spherical.CameraMotionListener
    public void onCameraMotion(long j, float[] fArr) {
        this.d.a(j, fArr);
    }

    @Override // com.google.android.exoplayer2.video.spherical.CameraMotionListener
    public void onCameraMotionReset() {
        this.e.clear();
        this.d.a();
        this.b.set(true);
    }

    private void a(@Nullable byte[] bArr, int i, long j) {
        byte[] bArr2 = this.m;
        int i2 = this.l;
        this.m = bArr;
        if (i == -1) {
            i = this.k;
        }
        this.l = i;
        if (i2 != this.l || !Arrays.equals(bArr2, this.m)) {
            Projection projection = null;
            byte[] bArr3 = this.m;
            if (bArr3 != null) {
                projection = b.a(bArr3, this.l);
            }
            if (projection == null || !c.a(projection)) {
                projection = Projection.a(this.l);
            }
            this.f.add(j, projection);
        }
    }
}
