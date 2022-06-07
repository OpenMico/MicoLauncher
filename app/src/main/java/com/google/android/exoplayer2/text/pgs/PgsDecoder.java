package com.google.android.exoplayer2.text.pgs;

import android.graphics.Bitmap;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.SimpleSubtitleDecoder;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.text.SubtitleDecoderException;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.zip.Inflater;

/* loaded from: classes2.dex */
public final class PgsDecoder extends SimpleSubtitleDecoder {
    private final ParsableByteArray a = new ParsableByteArray();
    private final ParsableByteArray b = new ParsableByteArray();
    private final a c = new a();
    @Nullable
    private Inflater d;

    public PgsDecoder() {
        super("PgsDecoder");
    }

    @Override // com.google.android.exoplayer2.text.SimpleSubtitleDecoder
    protected Subtitle decode(byte[] bArr, int i, boolean z) throws SubtitleDecoderException {
        this.a.reset(bArr, i);
        a(this.a);
        this.c.b();
        ArrayList arrayList = new ArrayList();
        while (this.a.bytesLeft() >= 3) {
            Cue a2 = a(this.a, this.c);
            if (a2 != null) {
                arrayList.add(a2);
            }
        }
        return new a(Collections.unmodifiableList(arrayList));
    }

    private void a(ParsableByteArray parsableByteArray) {
        if (parsableByteArray.bytesLeft() > 0 && parsableByteArray.peekUnsignedByte() == 120) {
            if (this.d == null) {
                this.d = new Inflater();
            }
            if (Util.inflate(parsableByteArray, this.b, this.d)) {
                parsableByteArray.reset(this.b.getData(), this.b.limit());
            }
        }
    }

    @Nullable
    private static Cue a(ParsableByteArray parsableByteArray, a aVar) {
        int limit = parsableByteArray.limit();
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        int readUnsignedShort = parsableByteArray.readUnsignedShort();
        int position = parsableByteArray.getPosition() + readUnsignedShort;
        Cue cue = null;
        if (position > limit) {
            parsableByteArray.setPosition(limit);
            return null;
        }
        if (readUnsignedByte != 128) {
            switch (readUnsignedByte) {
                case 20:
                    aVar.a(parsableByteArray, readUnsignedShort);
                    break;
                case 21:
                    aVar.b(parsableByteArray, readUnsignedShort);
                    break;
                case 22:
                    aVar.c(parsableByteArray, readUnsignedShort);
                    break;
            }
        } else {
            cue = aVar.a();
            aVar.b();
        }
        parsableByteArray.setPosition(position);
        return cue;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class a {
        private final ParsableByteArray a = new ParsableByteArray();
        private final int[] b = new int[256];
        private boolean c;
        private int d;
        private int e;
        private int f;
        private int g;
        private int h;
        private int i;

        /* JADX INFO: Access modifiers changed from: private */
        public void a(ParsableByteArray parsableByteArray, int i) {
            if (i % 5 == 2) {
                parsableByteArray.skipBytes(2);
                Arrays.fill(this.b, 0);
                int i2 = i / 5;
                for (int i3 = 0; i3 < i2; i3++) {
                    int readUnsignedByte = parsableByteArray.readUnsignedByte();
                    int readUnsignedByte2 = parsableByteArray.readUnsignedByte();
                    int readUnsignedByte3 = parsableByteArray.readUnsignedByte();
                    int readUnsignedByte4 = parsableByteArray.readUnsignedByte();
                    int readUnsignedByte5 = parsableByteArray.readUnsignedByte();
                    double d = readUnsignedByte2;
                    double d2 = readUnsignedByte3 - 128;
                    double d3 = readUnsignedByte4 - 128;
                    this.b[readUnsignedByte] = Util.constrainValue((int) (d + (d3 * 1.772d)), 0, 255) | (Util.constrainValue((int) ((d - (0.34414d * d3)) - (d2 * 0.71414d)), 0, 255) << 8) | (readUnsignedByte5 << 24) | (Util.constrainValue((int) ((1.402d * d2) + d), 0, 255) << 16);
                }
                this.c = true;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(ParsableByteArray parsableByteArray, int i) {
            int readUnsignedInt24;
            if (i >= 4) {
                parsableByteArray.skipBytes(3);
                int i2 = i - 4;
                if ((parsableByteArray.readUnsignedByte() & 128) != 0) {
                    if (i2 >= 7 && (readUnsignedInt24 = parsableByteArray.readUnsignedInt24()) >= 4) {
                        this.h = parsableByteArray.readUnsignedShort();
                        this.i = parsableByteArray.readUnsignedShort();
                        this.a.reset(readUnsignedInt24 - 4);
                        i2 -= 7;
                    } else {
                        return;
                    }
                }
                int position = this.a.getPosition();
                int limit = this.a.limit();
                if (position < limit && i2 > 0) {
                    int min = Math.min(i2, limit - position);
                    parsableByteArray.readBytes(this.a.getData(), position, min);
                    this.a.setPosition(position + min);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void c(ParsableByteArray parsableByteArray, int i) {
            if (i >= 19) {
                this.d = parsableByteArray.readUnsignedShort();
                this.e = parsableByteArray.readUnsignedShort();
                parsableByteArray.skipBytes(11);
                this.f = parsableByteArray.readUnsignedShort();
                this.g = parsableByteArray.readUnsignedShort();
            }
        }

        @Nullable
        public Cue a() {
            if (this.d == 0 || this.e == 0 || this.h == 0 || this.i == 0 || this.a.limit() == 0 || this.a.getPosition() != this.a.limit() || !this.c) {
                return null;
            }
            this.a.setPosition(0);
            int[] iArr = new int[this.h * this.i];
            int i = 0;
            while (i < iArr.length) {
                int readUnsignedByte = this.a.readUnsignedByte();
                if (readUnsignedByte != 0) {
                    i++;
                    iArr[i] = this.b[readUnsignedByte];
                } else {
                    int readUnsignedByte2 = this.a.readUnsignedByte();
                    if (readUnsignedByte2 != 0) {
                        int readUnsignedByte3 = ((readUnsignedByte2 & 64) == 0 ? readUnsignedByte2 & 63 : ((readUnsignedByte2 & 63) << 8) | this.a.readUnsignedByte()) + i;
                        Arrays.fill(iArr, i, readUnsignedByte3, (readUnsignedByte2 & 128) == 0 ? 0 : this.b[this.a.readUnsignedByte()]);
                        i = readUnsignedByte3;
                    }
                }
            }
            return new Cue.Builder().setBitmap(Bitmap.createBitmap(iArr, this.h, this.i, Bitmap.Config.ARGB_8888)).setPosition(this.f / this.d).setPositionAnchor(0).setLine(this.g / this.e, 0).setLineAnchor(0).setSize(this.h / this.d).setBitmapHeight(this.i / this.e).build();
        }

        public void b() {
            this.d = 0;
            this.e = 0;
            this.f = 0;
            this.g = 0;
            this.h = 0;
            this.i = 0;
            this.a.reset(0);
            this.c = false;
        }
    }
}
