package com.google.android.exoplayer2.text.webvtt;

import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.SimpleSubtitleDecoder;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.text.SubtitleDecoderException;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;
import java.util.Collections;

/* loaded from: classes2.dex */
public final class Mp4WebvttDecoder extends SimpleSubtitleDecoder {
    private final ParsableByteArray a = new ParsableByteArray();

    public Mp4WebvttDecoder() {
        super("Mp4WebvttDecoder");
    }

    @Override // com.google.android.exoplayer2.text.SimpleSubtitleDecoder
    protected Subtitle decode(byte[] bArr, int i, boolean z) throws SubtitleDecoderException {
        this.a.reset(bArr, i);
        ArrayList arrayList = new ArrayList();
        while (this.a.bytesLeft() > 0) {
            if (this.a.bytesLeft() >= 8) {
                int readInt = this.a.readInt();
                if (this.a.readInt() == 1987343459) {
                    arrayList.add(a(this.a, readInt - 8));
                } else {
                    this.a.skipBytes(readInt - 8);
                }
            } else {
                throw new SubtitleDecoderException("Incomplete Mp4Webvtt Top Level box header found.");
            }
        }
        return new a(arrayList);
    }

    private static Cue a(ParsableByteArray parsableByteArray, int i) throws SubtitleDecoderException {
        CharSequence charSequence = null;
        Cue.Builder builder = null;
        while (i > 0) {
            if (i >= 8) {
                int readInt = parsableByteArray.readInt();
                int readInt2 = parsableByteArray.readInt();
                int i2 = readInt - 8;
                String fromUtf8Bytes = Util.fromUtf8Bytes(parsableByteArray.getData(), parsableByteArray.getPosition(), i2);
                parsableByteArray.skipBytes(i2);
                i = (i - 8) - i2;
                if (readInt2 == 1937011815) {
                    builder = WebvttCueParser.a(fromUtf8Bytes);
                } else if (readInt2 == 1885436268) {
                    charSequence = WebvttCueParser.a((String) null, fromUtf8Bytes.trim(), Collections.emptyList());
                }
            } else {
                throw new SubtitleDecoderException("Incomplete vtt cue box header found.");
            }
        }
        if (charSequence == null) {
            charSequence = "";
        }
        if (builder != null) {
            return builder.setText(charSequence).build();
        }
        return WebvttCueParser.a(charSequence);
    }
}
