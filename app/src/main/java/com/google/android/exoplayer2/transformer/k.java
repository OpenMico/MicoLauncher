package com.google.android.exoplayer2.transformer;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.util.Assertions;
import java.nio.ByteBuffer;

/* compiled from: TransformerVideoRenderer.java */
@RequiresApi(18)
/* loaded from: classes2.dex */
final class k extends i {
    private final DecoderInputBuffer e = new DecoderInputBuffer(2);
    @Nullable
    private c f;
    private boolean g;
    private boolean h;
    private boolean i;

    @Override // com.google.android.exoplayer2.Renderer, com.google.android.exoplayer2.RendererCapabilities
    public String getName() {
        return "TransformerVideoRenderer";
    }

    public k(b bVar, j jVar, g gVar) {
        super(2, bVar, jVar, gVar);
    }

    @Override // com.google.android.exoplayer2.Renderer
    public void render(long j, long j2) {
        if (this.d && !isEnded()) {
            if (!this.g) {
                FormatHolder formatHolder = getFormatHolder();
                if (readSource(formatHolder, this.e, 2) == -5) {
                    Format format = (Format) Assertions.checkNotNull(formatHolder.format);
                    this.g = true;
                    if (this.c.c) {
                        this.f = new d(format);
                    }
                    this.a.a(format);
                } else {
                    return;
                }
            }
            do {
                if (this.h || a()) {
                    this.h = !this.a.a(getTrackType(), this.e.data, this.e.isKeyFrame(), this.e.timeUs);
                } else {
                    return;
                }
            } while (!this.h);
        }
    }

    @Override // com.google.android.exoplayer2.Renderer
    public boolean isEnded() {
        return this.i;
    }

    private boolean a() {
        this.e.clear();
        int readSource = readSource(getFormatHolder(), this.e, 0);
        if (readSource == -5) {
            throw new IllegalStateException("Format changes are not supported.");
        } else if (readSource == -3) {
            return false;
        } else {
            if (this.e.isEndOfStream()) {
                this.i = true;
                this.a.a(getTrackType());
                return false;
            }
            this.b.a(getTrackType(), this.e.timeUs);
            ((ByteBuffer) Assertions.checkNotNull(this.e.data)).flip();
            c cVar = this.f;
            if (cVar != null) {
                cVar.a(this.e);
            }
            return true;
        }
    }
}
