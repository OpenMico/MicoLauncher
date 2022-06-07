package com.google.android.exoplayer2.metadata;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.BaseRenderer;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.RendererCapabilities;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public final class MetadataRenderer extends BaseRenderer implements Handler.Callback {
    private final MetadataDecoderFactory a;
    private final MetadataOutput b;
    @Nullable
    private final Handler c;
    private final MetadataInputBuffer d;
    @Nullable
    private MetadataDecoder e;
    private boolean f;
    private boolean g;
    private long h;
    private long i;
    @Nullable
    private Metadata j;

    @Override // com.google.android.exoplayer2.Renderer, com.google.android.exoplayer2.RendererCapabilities
    public String getName() {
        return "MetadataRenderer";
    }

    @Override // com.google.android.exoplayer2.Renderer
    public boolean isReady() {
        return true;
    }

    public MetadataRenderer(MetadataOutput metadataOutput, @Nullable Looper looper) {
        this(metadataOutput, looper, MetadataDecoderFactory.DEFAULT);
    }

    public MetadataRenderer(MetadataOutput metadataOutput, @Nullable Looper looper, MetadataDecoderFactory metadataDecoderFactory) {
        super(5);
        this.b = (MetadataOutput) Assertions.checkNotNull(metadataOutput);
        this.c = looper == null ? null : Util.createHandler(looper, this);
        this.a = (MetadataDecoderFactory) Assertions.checkNotNull(metadataDecoderFactory);
        this.d = new MetadataInputBuffer();
        this.i = C.TIME_UNSET;
    }

    @Override // com.google.android.exoplayer2.RendererCapabilities
    public int supportsFormat(Format format) {
        if (!this.a.supportsFormat(format)) {
            return RendererCapabilities.create(0);
        }
        return RendererCapabilities.create(format.exoMediaCryptoType == null ? 4 : 2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.BaseRenderer
    public void onStreamChanged(Format[] formatArr, long j, long j2) {
        this.e = this.a.createDecoder(formatArr[0]);
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    protected void onPositionReset(long j, boolean z) {
        this.j = null;
        this.i = C.TIME_UNSET;
        this.f = false;
        this.g = false;
    }

    @Override // com.google.android.exoplayer2.Renderer
    public void render(long j, long j2) {
        boolean z = true;
        while (z) {
            a();
            z = a(j);
        }
    }

    private void a(Metadata metadata, List<Metadata.Entry> list) {
        for (int i = 0; i < metadata.length(); i++) {
            Format wrappedMetadataFormat = metadata.get(i).getWrappedMetadataFormat();
            if (wrappedMetadataFormat == null || !this.a.supportsFormat(wrappedMetadataFormat)) {
                list.add(metadata.get(i));
            } else {
                MetadataDecoder createDecoder = this.a.createDecoder(wrappedMetadataFormat);
                byte[] bArr = (byte[]) Assertions.checkNotNull(metadata.get(i).getWrappedMetadataBytes());
                this.d.clear();
                this.d.ensureSpaceForWrite(bArr.length);
                ((ByteBuffer) Util.castNonNull(this.d.data)).put(bArr);
                this.d.flip();
                Metadata decode = createDecoder.decode(this.d);
                if (decode != null) {
                    a(decode, list);
                }
            }
        }
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    protected void onDisabled() {
        this.j = null;
        this.i = C.TIME_UNSET;
        this.e = null;
    }

    @Override // com.google.android.exoplayer2.Renderer
    public boolean isEnded() {
        return this.g;
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        if (message.what == 0) {
            b((Metadata) message.obj);
            return true;
        }
        throw new IllegalStateException();
    }

    private void a() {
        if (!this.f && this.j == null) {
            this.d.clear();
            FormatHolder formatHolder = getFormatHolder();
            int readSource = readSource(formatHolder, this.d, 0);
            if (readSource == -4) {
                if (this.d.isEndOfStream()) {
                    this.f = true;
                    return;
                }
                MetadataInputBuffer metadataInputBuffer = this.d;
                metadataInputBuffer.subsampleOffsetUs = this.h;
                metadataInputBuffer.flip();
                Metadata decode = ((MetadataDecoder) Util.castNonNull(this.e)).decode(this.d);
                if (decode != null) {
                    ArrayList arrayList = new ArrayList(decode.length());
                    a(decode, arrayList);
                    if (!arrayList.isEmpty()) {
                        this.j = new Metadata(arrayList);
                        this.i = this.d.timeUs;
                    }
                }
            } else if (readSource == -5) {
                this.h = ((Format) Assertions.checkNotNull(formatHolder.format)).subsampleOffsetUs;
            }
        }
    }

    private boolean a(long j) {
        boolean z;
        Metadata metadata = this.j;
        if (metadata == null || this.i > j) {
            z = false;
        } else {
            a(metadata);
            this.j = null;
            this.i = C.TIME_UNSET;
            z = true;
        }
        if (this.f && this.j == null) {
            this.g = true;
        }
        return z;
    }

    private void a(Metadata metadata) {
        Handler handler = this.c;
        if (handler != null) {
            handler.obtainMessage(0, metadata).sendToTarget();
        } else {
            b(metadata);
        }
    }

    private void b(Metadata metadata) {
        this.b.onMetadata(metadata);
    }
}
