package com.google.android.exoplayer2.video.spherical;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.BaseRenderer;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.RendererCapabilities;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public final class CameraMotionRenderer extends BaseRenderer {
    private final DecoderInputBuffer a = new DecoderInputBuffer(1);
    private final ParsableByteArray b = new ParsableByteArray();
    private long c;
    @Nullable
    private CameraMotionListener d;
    private long e;

    @Override // com.google.android.exoplayer2.Renderer, com.google.android.exoplayer2.RendererCapabilities
    public String getName() {
        return "CameraMotionRenderer";
    }

    @Override // com.google.android.exoplayer2.Renderer
    public boolean isReady() {
        return true;
    }

    public CameraMotionRenderer() {
        super(6);
    }

    @Override // com.google.android.exoplayer2.RendererCapabilities
    public int supportsFormat(Format format) {
        if (MimeTypes.APPLICATION_CAMERA_MOTION.equals(format.sampleMimeType)) {
            return RendererCapabilities.create(4);
        }
        return RendererCapabilities.create(0);
    }

    @Override // com.google.android.exoplayer2.BaseRenderer, com.google.android.exoplayer2.PlayerMessage.Target
    public void handleMessage(int i, @Nullable Object obj) throws ExoPlaybackException {
        if (i == 7) {
            this.d = (CameraMotionListener) obj;
        } else {
            super.handleMessage(i, obj);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.BaseRenderer
    public void onStreamChanged(Format[] formatArr, long j, long j2) {
        this.c = j2;
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    protected void onPositionReset(long j, boolean z) {
        this.e = Long.MIN_VALUE;
        a();
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    protected void onDisabled() {
        a();
    }

    @Override // com.google.android.exoplayer2.Renderer
    public void render(long j, long j2) {
        while (!hasReadStreamToEnd() && this.e < 100000 + j) {
            this.a.clear();
            if (readSource(getFormatHolder(), this.a, 0) == -4 && !this.a.isEndOfStream()) {
                this.e = this.a.timeUs;
                if (this.d != null && !this.a.isDecodeOnly()) {
                    this.a.flip();
                    float[] a = a((ByteBuffer) Util.castNonNull(this.a.data));
                    if (a != null) {
                        ((CameraMotionListener) Util.castNonNull(this.d)).onCameraMotion(this.e - this.c, a);
                    }
                }
            } else {
                return;
            }
        }
    }

    @Override // com.google.android.exoplayer2.Renderer
    public boolean isEnded() {
        return hasReadStreamToEnd();
    }

    @Nullable
    private float[] a(ByteBuffer byteBuffer) {
        if (byteBuffer.remaining() != 16) {
            return null;
        }
        this.b.reset(byteBuffer.array(), byteBuffer.limit());
        this.b.setPosition(byteBuffer.arrayOffset() + 4);
        float[] fArr = new float[3];
        for (int i = 0; i < 3; i++) {
            fArr[i] = Float.intBitsToFloat(this.b.readLittleEndianInt());
        }
        return fArr;
    }

    private void a() {
        CameraMotionListener cameraMotionListener = this.d;
        if (cameraMotionListener != null) {
            cameraMotionListener.onCameraMotionReset();
        }
    }
}
