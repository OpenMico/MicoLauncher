package com.google.android.exoplayer2.text.webvtt;

import android.text.TextUtils;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.text.SimpleSubtitleDecoder;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.text.SubtitleDecoderException;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class WebvttDecoder extends SimpleSubtitleDecoder {
    private final ParsableByteArray a = new ParsableByteArray();
    private final b b = new b();

    public WebvttDecoder() {
        super("WebvttDecoder");
    }

    @Override // com.google.android.exoplayer2.text.SimpleSubtitleDecoder
    protected Subtitle decode(byte[] bArr, int i, boolean z) throws SubtitleDecoderException {
        WebvttCueInfo parseCue;
        this.a.reset(bArr, i);
        ArrayList arrayList = new ArrayList();
        try {
            WebvttParserUtil.validateWebvttHeaderLine(this.a);
            do {
            } while (!TextUtils.isEmpty(this.a.readLine()));
            ArrayList arrayList2 = new ArrayList();
            while (true) {
                int a = a(this.a);
                if (a == 0) {
                    return new c(arrayList2);
                }
                if (a == 1) {
                    b(this.a);
                } else if (a == 2) {
                    if (arrayList2.isEmpty()) {
                        this.a.readLine();
                        arrayList.addAll(this.b.a(this.a));
                    } else {
                        throw new SubtitleDecoderException("A style block was found after the first cue.");
                    }
                } else if (a == 3 && (parseCue = WebvttCueParser.parseCue(this.a, arrayList)) != null) {
                    arrayList2.add(parseCue);
                }
            }
        } catch (ParserException e) {
            throw new SubtitleDecoderException(e);
        }
    }

    private static int a(ParsableByteArray parsableByteArray) {
        int i = 0;
        int i2 = -1;
        while (i2 == -1) {
            i = parsableByteArray.getPosition();
            String readLine = parsableByteArray.readLine();
            if (readLine == null) {
                i2 = 0;
            } else if ("STYLE".equals(readLine)) {
                i2 = 2;
            } else {
                i2 = readLine.startsWith("NOTE") ? 1 : 3;
            }
        }
        parsableByteArray.setPosition(i);
        return i2;
    }

    private static void b(ParsableByteArray parsableByteArray) {
        do {
        } while (!TextUtils.isEmpty(parsableByteArray.readLine()));
    }
}
