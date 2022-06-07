package com.google.android.exoplayer2.transformer;

import android.media.MediaCodec;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.audio.AudioProcessor;
import com.google.android.exoplayer2.audio.SonicAudioProcessor;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.util.Assertions;
import java.io.IOException;
import java.nio.ByteBuffer;

/* compiled from: TransformerAudioRenderer.java */
@RequiresApi(18)
/* loaded from: classes2.dex */
final class h extends i {
    @Nullable
    private a h;
    @Nullable
    private a i;
    @Nullable
    private f j;
    @Nullable
    private Format k;
    @Nullable
    private AudioProcessor.AudioFormat l;
    private boolean p;
    private boolean q;
    private boolean r;
    private final DecoderInputBuffer e = new DecoderInputBuffer(0);
    private final DecoderInputBuffer f = new DecoderInputBuffer(0);
    private final SonicAudioProcessor g = new SonicAudioProcessor();
    private ByteBuffer m = AudioProcessor.EMPTY_BUFFER;
    private long n = 0;
    private float o = -1.0f;

    @Override // com.google.android.exoplayer2.Renderer, com.google.android.exoplayer2.RendererCapabilities
    public String getName() {
        return "TransformerAudioRenderer";
    }

    public h(b bVar, j jVar, g gVar) {
        super(1, bVar, jVar, gVar);
    }

    @Override // com.google.android.exoplayer2.Renderer
    public boolean isEnded() {
        return this.p;
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    protected void onReset() {
        this.e.clear();
        this.e.data = null;
        this.f.clear();
        this.f.data = null;
        this.g.reset();
        a aVar = this.h;
        if (aVar != null) {
            aVar.f();
            this.h = null;
        }
        a aVar2 = this.i;
        if (aVar2 != null) {
            aVar2.f();
            this.i = null;
        }
        this.j = null;
        this.k = null;
        this.l = null;
        this.m = AudioProcessor.EMPTY_BUFFER;
        this.n = 0L;
        this.o = -1.0f;
        this.p = false;
        this.q = false;
        this.r = false;
    }

    @Override // com.google.android.exoplayer2.Renderer
    public void render(long j, long j2) throws ExoPlaybackException {
        if (this.d && !isEnded() && h()) {
            if (g()) {
                do {
                } while (a());
                if (this.g.isActive()) {
                    do {
                    } while (c());
                    do {
                    } while (d());
                } else {
                    do {
                    } while (b());
                }
                do {
                } while (e());
            }
            do {
            } while (e());
        }
    }

    private boolean a() {
        a aVar = (a) Assertions.checkNotNull(this.i);
        if (!this.q) {
            Format a = aVar.a();
            if (a == null) {
                return false;
            }
            this.q = true;
            this.a.a(a);
        }
        if (aVar.e()) {
            this.a.a(getTrackType());
            this.p = true;
            return false;
        }
        ByteBuffer b = aVar.b();
        if (b == null || !this.a.a(getTrackType(), b, true, ((MediaCodec.BufferInfo) Assertions.checkNotNull(aVar.c())).presentationTimeUs)) {
            return false;
        }
        aVar.d();
        return true;
    }

    private boolean b() {
        a aVar = (a) Assertions.checkNotNull(this.h);
        if (!((a) Assertions.checkNotNull(this.i)).a(this.f)) {
            return false;
        }
        if (aVar.e()) {
            f();
            return false;
        }
        ByteBuffer b = aVar.b();
        if (b == null) {
            return false;
        }
        if (a((MediaCodec.BufferInfo) Assertions.checkNotNull(aVar.c()))) {
            a(this.o);
            return false;
        }
        a(b);
        if (b.hasRemaining()) {
            return true;
        }
        aVar.d();
        return true;
    }

    private boolean c() {
        if (!((a) Assertions.checkNotNull(this.i)).a(this.f)) {
            return false;
        }
        if (!this.m.hasRemaining()) {
            this.m = this.g.getOutput();
            if (!this.m.hasRemaining()) {
                if (((a) Assertions.checkNotNull(this.h)).e() && this.g.isEnded()) {
                    f();
                }
                return false;
            }
        }
        a(this.m);
        return true;
    }

    private boolean d() {
        a aVar = (a) Assertions.checkNotNull(this.h);
        if (this.r) {
            if (this.g.isEnded() && !this.m.hasRemaining()) {
                a(this.o);
                this.r = false;
            }
            return false;
        } else if (this.m.hasRemaining()) {
            return false;
        } else {
            if (aVar.e()) {
                this.g.queueEndOfStream();
                return false;
            }
            Assertions.checkState(!this.g.isEnded());
            ByteBuffer b = aVar.b();
            if (b == null) {
                return false;
            }
            if (a((MediaCodec.BufferInfo) Assertions.checkNotNull(aVar.c()))) {
                this.g.queueEndOfStream();
                this.r = true;
                return false;
            }
            this.g.queueInput(b);
            if (!b.hasRemaining()) {
                aVar.d();
            }
            return true;
        }
    }

    private boolean e() {
        a aVar = (a) Assertions.checkNotNull(this.h);
        if (!aVar.a(this.e)) {
            return false;
        }
        this.e.clear();
        switch (readSource(getFormatHolder(), this.e, 0)) {
            case -5:
                throw new IllegalStateException("Format changes are not supported.");
            case -4:
                this.b.a(getTrackType(), this.e.timeUs);
                this.e.flip();
                aVar.b(this.e);
                return !this.e.isEndOfStream();
            default:
                return false;
        }
    }

    private void a(ByteBuffer byteBuffer) {
        AudioProcessor.AudioFormat audioFormat = (AudioProcessor.AudioFormat) Assertions.checkNotNull(this.l);
        ByteBuffer byteBuffer2 = (ByteBuffer) Assertions.checkNotNull(this.f.data);
        int limit = byteBuffer.limit();
        byteBuffer.limit(Math.min(limit, byteBuffer.position() + byteBuffer2.capacity()));
        byteBuffer2.put(byteBuffer);
        DecoderInputBuffer decoderInputBuffer = this.f;
        long j = this.n;
        decoderInputBuffer.timeUs = j;
        this.n = j + a(byteBuffer2.position(), audioFormat.bytesPerFrame, audioFormat.sampleRate);
        this.f.setFlags(0);
        this.f.flip();
        byteBuffer.limit(limit);
        ((a) Assertions.checkNotNull(this.i)).b(this.f);
    }

    private void f() {
        a aVar = (a) Assertions.checkNotNull(this.i);
        Assertions.checkState(((ByteBuffer) Assertions.checkNotNull(this.f.data)).position() == 0);
        this.f.addFlag(4);
        this.f.flip();
        aVar.b(this.f);
    }

    private boolean g() throws ExoPlaybackException {
        if (this.i != null) {
            return true;
        }
        Format a = ((a) Assertions.checkNotNull(this.h)).a();
        if (a == null) {
            return false;
        }
        AudioProcessor.AudioFormat audioFormat = new AudioProcessor.AudioFormat(a.sampleRate, a.channelCount, a.pcmEncoding);
        if (this.c.c) {
            try {
                audioFormat = this.g.configure(audioFormat);
                a(this.o);
            } catch (AudioProcessor.UnhandledAudioFormatException e) {
                throw a(e, 1000);
            }
        }
        try {
            this.i = a.b(new Format.Builder().setSampleMimeType(((Format) Assertions.checkNotNull(this.k)).sampleMimeType).setSampleRate(audioFormat.sampleRate).setChannelCount(audioFormat.channelCount).setAverageBitrate(131072).build());
            this.l = audioFormat;
            return true;
        } catch (IOException e2) {
            throw a(e2, 1000);
        }
    }

    private boolean h() throws ExoPlaybackException {
        if (this.h != null) {
            return true;
        }
        FormatHolder formatHolder = getFormatHolder();
        if (readSource(formatHolder, this.e, 2) != -5) {
            return false;
        }
        this.k = (Format) Assertions.checkNotNull(formatHolder.format);
        try {
            this.h = a.a(this.k);
            this.j = new e(this.k);
            this.o = this.j.a(0L);
            return true;
        } catch (IOException e) {
            throw a(e, 1000);
        }
    }

    private boolean a(MediaCodec.BufferInfo bufferInfo) {
        boolean z = false;
        if (!this.c.c) {
            return false;
        }
        float a = ((f) Assertions.checkNotNull(this.j)).a(bufferInfo.presentationTimeUs);
        if (a != this.o) {
            z = true;
        }
        this.o = a;
        return z;
    }

    private void a(float f) {
        this.g.setSpeed(f);
        this.g.setPitch(f);
        this.g.flush();
    }

    private ExoPlaybackException a(Throwable th, int i) {
        return ExoPlaybackException.createForRenderer(th, "TransformerAudioRenderer", getIndex(), this.k, 4, false, i);
    }

    private static long a(long j, int i, int i2) {
        return ((j / i) * 1000000) / i2;
    }
}
