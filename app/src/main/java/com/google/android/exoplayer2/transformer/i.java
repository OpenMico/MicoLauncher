package com.google.android.exoplayer2.transformer;

import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.BaseRenderer;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.RendererCapabilities;
import com.google.android.exoplayer2.util.MediaClock;
import com.google.android.exoplayer2.util.MimeTypes;

/* compiled from: TransformerBaseRenderer.java */
@RequiresApi(18)
/* loaded from: classes2.dex */
abstract class i extends BaseRenderer {
    protected final b a;
    protected final j b;
    protected final g c;
    protected boolean d;

    public i(int i, b bVar, j jVar, g gVar) {
        super(i);
        this.a = bVar;
        this.b = jVar;
        this.c = gVar;
    }

    @Override // com.google.android.exoplayer2.RendererCapabilities
    public final int supportsFormat(Format format) {
        String str = format.sampleMimeType;
        if (MimeTypes.getTrackType(str) != getTrackType()) {
            return RendererCapabilities.create(0);
        }
        if (this.a.a(str)) {
            return RendererCapabilities.create(4);
        }
        return RendererCapabilities.create(1);
    }

    @Override // com.google.android.exoplayer2.Renderer
    public final boolean isReady() {
        return isSourceReady();
    }

    @Override // com.google.android.exoplayer2.BaseRenderer, com.google.android.exoplayer2.Renderer
    public final MediaClock getMediaClock() {
        return this.b;
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    protected final void onEnabled(boolean z, boolean z2) {
        this.a.a();
        this.b.a(getTrackType(), 0L);
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    protected final void onStarted() {
        this.d = true;
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    protected final void onStopped() {
        this.d = false;
    }
}
