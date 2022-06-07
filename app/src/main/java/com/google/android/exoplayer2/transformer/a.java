package com.google.android.exoplayer2.transformer;

import android.media.MediaCodec;
import android.media.MediaFormat;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.mediacodec.MediaCodecAdapter;
import com.google.android.exoplayer2.mediacodec.MediaCodecInfo;
import com.google.android.exoplayer2.mediacodec.SynchronousMediaCodecAdapter;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MediaFormatUtil;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.nio.ByteBuffer;
import org.checkerframework.checker.nullness.qual.EnsuresNonNullIf;

/* compiled from: MediaCodecAdapterWrapper.java */
/* loaded from: classes2.dex */
final class a {
    private final MediaCodecAdapter b;
    private Format c;
    @Nullable
    private ByteBuffer d;
    private boolean g;
    private boolean h;
    private final MediaCodec.BufferInfo a = new MediaCodec.BufferInfo();
    private int e = -1;
    private int f = -1;

    /* compiled from: MediaCodecAdapterWrapper.java */
    /* renamed from: com.google.android.exoplayer2.transformer.a$a  reason: collision with other inner class name */
    /* loaded from: classes2.dex */
    private static class C0072a extends SynchronousMediaCodecAdapter.Factory {
        private final boolean a;

        public C0072a(boolean z) {
            this.a = z;
        }

        @Override // com.google.android.exoplayer2.mediacodec.SynchronousMediaCodecAdapter.Factory
        protected MediaCodec createCodec(MediaCodecAdapter.Configuration configuration) throws IOException {
            String str = (String) Assertions.checkNotNull(configuration.mediaFormat.getString("mime"));
            if (this.a) {
                return MediaCodec.createDecoderByType((String) Assertions.checkNotNull(str));
            }
            return MediaCodec.createEncoderByType((String) Assertions.checkNotNull(str));
        }
    }

    private static MediaCodecInfo g() {
        return MediaCodecInfo.newInstance("name-placeholder", "mime-type-placeholder", "mime-type-placeholder", null, false, false, false, false, false);
    }

    public static a a(Format format) throws IOException {
        MediaCodecAdapter mediaCodecAdapter = null;
        try {
            MediaFormat createAudioFormat = MediaFormat.createAudioFormat((String) Assertions.checkNotNull(format.sampleMimeType), format.sampleRate, format.channelCount);
            MediaFormatUtil.maybeSetInteger(createAudioFormat, "max-input-size", format.maxInputSize);
            MediaFormatUtil.setCsdBuffers(createAudioFormat, format.initializationData);
            mediaCodecAdapter = new C0072a(true).createAdapter(new MediaCodecAdapter.Configuration(g(), createAudioFormat, format, null, null, 0));
            return new a(mediaCodecAdapter);
        } catch (Exception e) {
            if (mediaCodecAdapter != null) {
                mediaCodecAdapter.release();
            }
            throw e;
        }
    }

    public static a b(Format format) throws IOException {
        MediaCodecAdapter mediaCodecAdapter = null;
        try {
            MediaFormat createAudioFormat = MediaFormat.createAudioFormat((String) Assertions.checkNotNull(format.sampleMimeType), format.sampleRate, format.channelCount);
            createAudioFormat.setInteger("bitrate", format.bitrate);
            mediaCodecAdapter = new C0072a(false).createAdapter(new MediaCodecAdapter.Configuration(g(), createAudioFormat, format, null, null, 1));
            return new a(mediaCodecAdapter);
        } catch (Exception e) {
            if (mediaCodecAdapter != null) {
                mediaCodecAdapter.release();
            }
            throw e;
        }
    }

    private a(MediaCodecAdapter mediaCodecAdapter) {
        this.b = mediaCodecAdapter;
    }

    @EnsuresNonNullIf(expression = {"#1.data"}, result = true)
    public boolean a(DecoderInputBuffer decoderInputBuffer) {
        if (this.g) {
            return false;
        }
        if (this.e < 0) {
            this.e = this.b.dequeueInputBufferIndex();
            int i = this.e;
            if (i < 0) {
                return false;
            }
            decoderInputBuffer.data = this.b.getInputBuffer(i);
            decoderInputBuffer.clear();
        }
        Assertions.checkNotNull(decoderInputBuffer.data);
        return true;
    }

    public void b(DecoderInputBuffer decoderInputBuffer) {
        int i;
        int i2;
        int i3;
        Assertions.checkState(!this.g, "Input buffer can not be queued after the input stream has ended.");
        if (decoderInputBuffer.data == null || !decoderInputBuffer.data.hasRemaining()) {
            i2 = 0;
            i = 0;
        } else {
            i2 = decoderInputBuffer.data.position();
            i = decoderInputBuffer.data.remaining();
        }
        if (decoderInputBuffer.isEndOfStream()) {
            this.g = true;
            i3 = 4;
        } else {
            i3 = 0;
        }
        this.b.queueInputBuffer(this.e, i2, i, decoderInputBuffer.timeUs, i3);
        this.e = -1;
        decoderInputBuffer.data = null;
    }

    @Nullable
    public Format a() {
        h();
        return this.c;
    }

    @Nullable
    public ByteBuffer b() {
        if (h()) {
            return this.d;
        }
        return null;
    }

    @Nullable
    public MediaCodec.BufferInfo c() {
        if (h()) {
            return this.a;
        }
        return null;
    }

    public void d() {
        this.d = null;
        this.b.releaseOutputBuffer(this.f, false);
        this.f = -1;
    }

    public boolean e() {
        return this.h && this.f == -1;
    }

    public void f() {
        this.d = null;
        this.b.release();
    }

    private boolean h() {
        if (this.f >= 0) {
            return true;
        }
        if (this.h) {
            return false;
        }
        this.f = this.b.dequeueOutputBufferIndex(this.a);
        int i = this.f;
        if (i < 0) {
            if (i == -2) {
                this.c = a(this.b.getOutputFormat());
            }
            return false;
        }
        if ((this.a.flags & 4) != 0) {
            this.h = true;
            if (this.a.size == 0) {
                d();
                return false;
            }
        }
        if ((this.a.flags & 2) != 0) {
            d();
            return false;
        }
        this.d = (ByteBuffer) Assertions.checkNotNull(this.b.getOutputBuffer(this.f));
        this.d.position(this.a.offset);
        this.d.limit(this.a.offset + this.a.size);
        return true;
    }

    private static Format a(MediaFormat mediaFormat) {
        ImmutableList.Builder builder = new ImmutableList.Builder();
        int i = 0;
        while (true) {
            StringBuilder sb = new StringBuilder(15);
            sb.append("csd-");
            sb.append(i);
            ByteBuffer byteBuffer = mediaFormat.getByteBuffer(sb.toString());
            if (byteBuffer == null) {
                break;
            }
            byte[] bArr = new byte[byteBuffer.remaining()];
            byteBuffer.get(bArr);
            builder.add((ImmutableList.Builder) bArr);
            i++;
        }
        String string = mediaFormat.getString("mime");
        Format.Builder initializationData = new Format.Builder().setSampleMimeType(mediaFormat.getString("mime")).setInitializationData(builder.build());
        if (MimeTypes.isVideo(string)) {
            initializationData.setWidth(mediaFormat.getInteger("width")).setHeight(mediaFormat.getInteger("height"));
        } else if (MimeTypes.isAudio(string)) {
            initializationData.setChannelCount(mediaFormat.getInteger("channel-count")).setSampleRate(mediaFormat.getInteger("sample-rate")).setPcmEncoding(2);
        }
        return initializationData.build();
    }
}
