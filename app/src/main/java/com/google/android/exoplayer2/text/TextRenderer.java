package com.google.android.exoplayer2.text;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.BaseRenderer;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.RendererCapabilities;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.util.Collections;
import java.util.List;

/* loaded from: classes2.dex */
public final class TextRenderer extends BaseRenderer implements Handler.Callback {
    @Nullable
    private final Handler a;
    private final TextOutput b;
    private final SubtitleDecoderFactory c;
    private final FormatHolder d;
    private boolean e;
    private boolean f;
    private boolean g;
    private int h;
    @Nullable
    private Format i;
    @Nullable
    private SubtitleDecoder j;
    @Nullable
    private SubtitleInputBuffer k;
    @Nullable
    private SubtitleOutputBuffer l;
    @Nullable
    private SubtitleOutputBuffer m;
    private int n;
    private long o;

    @Override // com.google.android.exoplayer2.Renderer, com.google.android.exoplayer2.RendererCapabilities
    public String getName() {
        return "TextRenderer";
    }

    @Override // com.google.android.exoplayer2.Renderer
    public boolean isReady() {
        return true;
    }

    public TextRenderer(TextOutput textOutput, @Nullable Looper looper) {
        this(textOutput, looper, SubtitleDecoderFactory.DEFAULT);
    }

    public TextRenderer(TextOutput textOutput, @Nullable Looper looper, SubtitleDecoderFactory subtitleDecoderFactory) {
        super(3);
        this.b = (TextOutput) Assertions.checkNotNull(textOutput);
        this.a = looper == null ? null : Util.createHandler(looper, this);
        this.c = subtitleDecoderFactory;
        this.d = new FormatHolder();
        this.o = C.TIME_UNSET;
    }

    @Override // com.google.android.exoplayer2.RendererCapabilities
    public int supportsFormat(Format format) {
        if (this.c.supportsFormat(format)) {
            return RendererCapabilities.create(format.exoMediaCryptoType == null ? 4 : 2);
        } else if (MimeTypes.isText(format.sampleMimeType)) {
            return RendererCapabilities.create(1);
        } else {
            return RendererCapabilities.create(0);
        }
    }

    public void setFinalStreamEndPositionUs(long j) {
        Assertions.checkState(isCurrentStreamFinal());
        this.o = j;
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    public void onStreamChanged(Format[] formatArr, long j, long j2) {
        this.i = formatArr[0];
        if (this.j != null) {
            this.h = 1;
        } else {
            c();
        }
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    protected void onPositionReset(long j, boolean z) {
        f();
        this.e = false;
        this.f = false;
        this.o = C.TIME_UNSET;
        if (this.h != 0) {
            d();
            return;
        }
        a();
        ((SubtitleDecoder) Assertions.checkNotNull(this.j)).flush();
    }

    @Override // com.google.android.exoplayer2.Renderer
    public void render(long j, long j2) {
        boolean z;
        if (isCurrentStreamFinal()) {
            long j3 = this.o;
            if (j3 != C.TIME_UNSET && j >= j3) {
                a();
                this.f = true;
            }
        }
        if (!this.f) {
            if (this.m == null) {
                ((SubtitleDecoder) Assertions.checkNotNull(this.j)).setPositionUs(j);
                try {
                    this.m = ((SubtitleDecoder) Assertions.checkNotNull(this.j)).dequeueOutputBuffer();
                } catch (SubtitleDecoderException e) {
                    a(e);
                    return;
                }
            }
            if (getState() == 2) {
                if (this.l != null) {
                    long e2 = e();
                    z = false;
                    while (e2 <= j) {
                        this.n++;
                        e2 = e();
                        z = true;
                    }
                } else {
                    z = false;
                }
                SubtitleOutputBuffer subtitleOutputBuffer = this.m;
                if (subtitleOutputBuffer != null) {
                    if (subtitleOutputBuffer.isEndOfStream()) {
                        if (!z && e() == Long.MAX_VALUE) {
                            if (this.h == 2) {
                                d();
                            } else {
                                a();
                                this.f = true;
                            }
                        }
                    } else if (subtitleOutputBuffer.timeUs <= j) {
                        SubtitleOutputBuffer subtitleOutputBuffer2 = this.l;
                        if (subtitleOutputBuffer2 != null) {
                            subtitleOutputBuffer2.release();
                        }
                        this.n = subtitleOutputBuffer.getNextEventTimeIndex(j);
                        this.l = subtitleOutputBuffer;
                        this.m = null;
                        z = true;
                    }
                }
                if (z) {
                    Assertions.checkNotNull(this.l);
                    a(this.l.getCues(j));
                }
                if (this.h != 2) {
                    while (!this.e) {
                        try {
                            SubtitleInputBuffer subtitleInputBuffer = this.k;
                            if (subtitleInputBuffer == null) {
                                subtitleInputBuffer = ((SubtitleDecoder) Assertions.checkNotNull(this.j)).dequeueInputBuffer();
                                if (subtitleInputBuffer != null) {
                                    this.k = subtitleInputBuffer;
                                } else {
                                    return;
                                }
                            }
                            if (this.h == 1) {
                                subtitleInputBuffer.setFlags(4);
                                ((SubtitleDecoder) Assertions.checkNotNull(this.j)).queueInputBuffer(subtitleInputBuffer);
                                this.k = null;
                                this.h = 2;
                                return;
                            }
                            int readSource = readSource(this.d, subtitleInputBuffer, 0);
                            if (readSource == -4) {
                                if (subtitleInputBuffer.isEndOfStream()) {
                                    this.e = true;
                                    this.g = false;
                                } else {
                                    Format format = this.d.format;
                                    if (format != null) {
                                        subtitleInputBuffer.subsampleOffsetUs = format.subsampleOffsetUs;
                                        subtitleInputBuffer.flip();
                                        this.g &= !subtitleInputBuffer.isKeyFrame();
                                    } else {
                                        return;
                                    }
                                }
                                if (!this.g) {
                                    ((SubtitleDecoder) Assertions.checkNotNull(this.j)).queueInputBuffer(subtitleInputBuffer);
                                    this.k = null;
                                }
                            } else if (readSource == -3) {
                                return;
                            }
                        } catch (SubtitleDecoderException e3) {
                            a(e3);
                            return;
                        }
                    }
                }
            }
        }
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    protected void onDisabled() {
        this.i = null;
        this.o = C.TIME_UNSET;
        f();
        b();
    }

    @Override // com.google.android.exoplayer2.Renderer
    public boolean isEnded() {
        return this.f;
    }

    private void a() {
        this.k = null;
        this.n = -1;
        SubtitleOutputBuffer subtitleOutputBuffer = this.l;
        if (subtitleOutputBuffer != null) {
            subtitleOutputBuffer.release();
            this.l = null;
        }
        SubtitleOutputBuffer subtitleOutputBuffer2 = this.m;
        if (subtitleOutputBuffer2 != null) {
            subtitleOutputBuffer2.release();
            this.m = null;
        }
    }

    private void b() {
        a();
        ((SubtitleDecoder) Assertions.checkNotNull(this.j)).release();
        this.j = null;
        this.h = 0;
    }

    private void c() {
        this.g = true;
        this.j = this.c.createDecoder((Format) Assertions.checkNotNull(this.i));
    }

    private void d() {
        b();
        c();
    }

    private long e() {
        if (this.n == -1) {
            return Long.MAX_VALUE;
        }
        Assertions.checkNotNull(this.l);
        if (this.n >= this.l.getEventTimeCount()) {
            return Long.MAX_VALUE;
        }
        return this.l.getEventTime(this.n);
    }

    private void a(List<Cue> list) {
        Handler handler = this.a;
        if (handler != null) {
            handler.obtainMessage(0, list).sendToTarget();
        } else {
            b(list);
        }
    }

    private void f() {
        a(Collections.emptyList());
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        if (message.what == 0) {
            b((List) message.obj);
            return true;
        }
        throw new IllegalStateException();
    }

    private void b(List<Cue> list) {
        this.b.onCues(list);
    }

    private void a(SubtitleDecoderException subtitleDecoderException) {
        String valueOf = String.valueOf(this.i);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 39);
        sb.append("Subtitle decoding failed. streamFormat=");
        sb.append(valueOf);
        Log.e("TextRenderer", sb.toString(), subtitleDecoderException);
        f();
        d();
    }
}
