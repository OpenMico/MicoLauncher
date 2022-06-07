package com.google.android.exoplayer2.text.tx3g;

import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.text.style.UnderlineSpan;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.SimpleSubtitleDecoder;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.text.SubtitleDecoderException;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Charsets;
import java.util.List;

/* loaded from: classes2.dex */
public final class Tx3gDecoder extends SimpleSubtitleDecoder {
    private final ParsableByteArray a = new ParsableByteArray();
    private final boolean b;
    private final int c;
    private final int d;
    private final String e;
    private final float f;
    private final int g;

    public Tx3gDecoder(List<byte[]> list) {
        super("Tx3gDecoder");
        boolean z = true;
        if (list.size() == 1 && (list.get(0).length == 48 || list.get(0).length == 53)) {
            byte[] bArr = list.get(0);
            this.c = bArr[24];
            this.d = ((bArr[26] & 255) << 24) | ((bArr[27] & 255) << 16) | ((bArr[28] & 255) << 8) | (bArr[29] & 255);
            this.e = "Serif".equals(Util.fromUtf8Bytes(bArr, 43, bArr.length - 43)) ? C.SERIF_NAME : C.SANS_SERIF_NAME;
            this.g = bArr[25] * 20;
            this.b = (bArr[0] & 32) == 0 ? false : z;
            if (this.b) {
                this.f = Util.constrainValue(((bArr[11] & 255) | ((bArr[10] & 255) << 8)) / this.g, 0.0f, 0.95f);
            } else {
                this.f = 0.85f;
            }
        } else {
            this.c = 0;
            this.d = -1;
            this.e = C.SANS_SERIF_NAME;
            this.b = false;
            this.f = 0.85f;
            this.g = -1;
        }
    }

    @Override // com.google.android.exoplayer2.text.SimpleSubtitleDecoder
    protected Subtitle decode(byte[] bArr, int i, boolean z) throws SubtitleDecoderException {
        this.a.reset(bArr, i);
        String a = a(this.a);
        if (a.isEmpty()) {
            return a.a;
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(a);
        a(spannableStringBuilder, this.c, 0, 0, spannableStringBuilder.length(), 16711680);
        b(spannableStringBuilder, this.d, -1, 0, spannableStringBuilder.length(), 16711680);
        a(spannableStringBuilder, this.e, 0, spannableStringBuilder.length());
        float f = this.f;
        while (this.a.bytesLeft() >= 8) {
            int position = this.a.getPosition();
            int readInt = this.a.readInt();
            int readInt2 = this.a.readInt();
            boolean z2 = true;
            if (readInt2 == 1937013100) {
                if (this.a.bytesLeft() < 2) {
                    z2 = false;
                }
                a(z2);
                int readUnsignedShort = this.a.readUnsignedShort();
                for (int i2 = 0; i2 < readUnsignedShort; i2++) {
                    a(this.a, spannableStringBuilder);
                }
            } else if (readInt2 == 1952608120 && this.b) {
                if (this.a.bytesLeft() < 2) {
                    z2 = false;
                }
                a(z2);
                f = Util.constrainValue(this.a.readUnsignedShort() / this.g, 0.0f, 0.95f);
            }
            this.a.setPosition(position + readInt);
        }
        return new a(new Cue.Builder().setText(spannableStringBuilder).setLine(f, 0).setLineAnchor(0).build());
    }

    private static String a(ParsableByteArray parsableByteArray) throws SubtitleDecoderException {
        char peekChar;
        a(parsableByteArray.bytesLeft() >= 2);
        int readUnsignedShort = parsableByteArray.readUnsignedShort();
        if (readUnsignedShort == 0) {
            return "";
        }
        if (parsableByteArray.bytesLeft() < 2 || ((peekChar = parsableByteArray.peekChar()) != 65279 && peekChar != 65534)) {
            return parsableByteArray.readString(readUnsignedShort, Charsets.UTF_8);
        }
        return parsableByteArray.readString(readUnsignedShort, Charsets.UTF_16);
    }

    private void a(ParsableByteArray parsableByteArray, SpannableStringBuilder spannableStringBuilder) throws SubtitleDecoderException {
        int i;
        a(parsableByteArray.bytesLeft() >= 12);
        int readUnsignedShort = parsableByteArray.readUnsignedShort();
        int readUnsignedShort2 = parsableByteArray.readUnsignedShort();
        parsableByteArray.skipBytes(2);
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        parsableByteArray.skipBytes(1);
        int readInt = parsableByteArray.readInt();
        if (readUnsignedShort2 > spannableStringBuilder.length()) {
            int length = spannableStringBuilder.length();
            StringBuilder sb = new StringBuilder(68);
            sb.append("Truncating styl end (");
            sb.append(readUnsignedShort2);
            sb.append(") to cueText.length() (");
            sb.append(length);
            sb.append(").");
            Log.w("Tx3gDecoder", sb.toString());
            i = spannableStringBuilder.length();
        } else {
            i = readUnsignedShort2;
        }
        if (readUnsignedShort >= i) {
            StringBuilder sb2 = new StringBuilder(60);
            sb2.append("Ignoring styl with start (");
            sb2.append(readUnsignedShort);
            sb2.append(") >= end (");
            sb2.append(i);
            sb2.append(").");
            Log.w("Tx3gDecoder", sb2.toString());
            return;
        }
        a(spannableStringBuilder, readUnsignedByte, this.c, readUnsignedShort, i, 0);
        b(spannableStringBuilder, readInt, this.d, readUnsignedShort, i, 0);
    }

    private static void a(SpannableStringBuilder spannableStringBuilder, int i, int i2, int i3, int i4, int i5) {
        if (i != i2) {
            int i6 = i5 | 33;
            boolean z = true;
            boolean z2 = (i & 1) != 0;
            boolean z3 = (i & 2) != 0;
            if (z2) {
                if (z3) {
                    spannableStringBuilder.setSpan(new StyleSpan(3), i3, i4, i6);
                } else {
                    spannableStringBuilder.setSpan(new StyleSpan(1), i3, i4, i6);
                }
            } else if (z3) {
                spannableStringBuilder.setSpan(new StyleSpan(2), i3, i4, i6);
            }
            if ((i & 4) == 0) {
                z = false;
            }
            if (z) {
                spannableStringBuilder.setSpan(new UnderlineSpan(), i3, i4, i6);
            }
            if (!z && !z2 && !z3) {
                spannableStringBuilder.setSpan(new StyleSpan(0), i3, i4, i6);
            }
        }
    }

    private static void b(SpannableStringBuilder spannableStringBuilder, int i, int i2, int i3, int i4, int i5) {
        if (i != i2) {
            spannableStringBuilder.setSpan(new ForegroundColorSpan((i >>> 8) | ((i & 255) << 24)), i3, i4, i5 | 33);
        }
    }

    private static void a(SpannableStringBuilder spannableStringBuilder, String str, int i, int i2) {
        if (str != C.SANS_SERIF_NAME) {
            spannableStringBuilder.setSpan(new TypefaceSpan(str), i, i2, 16711713);
        }
    }

    private static void a(boolean z) throws SubtitleDecoderException {
        if (!z) {
            throw new SubtitleDecoderException("Unexpected subtitle format.");
        }
    }
}
