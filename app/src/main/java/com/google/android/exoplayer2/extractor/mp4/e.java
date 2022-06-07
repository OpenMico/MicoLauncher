package com.google.android.exoplayer2.extractor.mp4;

import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.mp4.SlowMotionData;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.common.base.Splitter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* compiled from: SefReader.java */
/* loaded from: classes2.dex */
final class e {
    private static final Splitter a = Splitter.on(':');
    private static final Splitter b = Splitter.on('*');
    private final List<a> c = new ArrayList();
    private int d = 0;
    private int e;

    public void a() {
        this.c.clear();
        this.d = 0;
    }

    public int a(ExtractorInput extractorInput, PositionHolder positionHolder, List<Metadata.Entry> list) throws IOException {
        long j = 0;
        switch (this.d) {
            case 0:
                long length = extractorInput.getLength();
                if (length != -1 && length >= 8) {
                    j = length - 8;
                }
                positionHolder.position = j;
                this.d = 1;
                break;
            case 1:
                a(extractorInput, positionHolder);
                break;
            case 2:
                b(extractorInput, positionHolder);
                break;
            case 3:
                a(extractorInput, list);
                positionHolder.position = 0L;
                break;
            default:
                throw new IllegalStateException();
        }
        return 1;
    }

    private void a(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
        ParsableByteArray parsableByteArray = new ParsableByteArray(8);
        extractorInput.readFully(parsableByteArray.getData(), 0, 8);
        this.e = parsableByteArray.readLittleEndianInt() + 8;
        if (parsableByteArray.readInt() != 1397048916) {
            positionHolder.position = 0L;
            return;
        }
        positionHolder.position = extractorInput.getPosition() - (this.e - 12);
        this.d = 2;
    }

    private void b(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
        long length = extractorInput.getLength();
        int i = (this.e - 12) - 8;
        ParsableByteArray parsableByteArray = new ParsableByteArray(i);
        extractorInput.readFully(parsableByteArray.getData(), 0, i);
        for (int i2 = 0; i2 < i / 12; i2++) {
            parsableByteArray.skipBytes(2);
            short readLittleEndianShort = parsableByteArray.readLittleEndianShort();
            switch (readLittleEndianShort) {
                case 2192:
                case 2816:
                case 2817:
                case 2819:
                case 2820:
                    this.c.add(new a(readLittleEndianShort, (length - this.e) - parsableByteArray.readLittleEndianInt(), parsableByteArray.readLittleEndianInt()));
                    break;
                default:
                    parsableByteArray.skipBytes(8);
                    break;
            }
        }
        if (this.c.isEmpty()) {
            positionHolder.position = 0L;
            return;
        }
        this.d = 3;
        positionHolder.position = this.c.get(0).b;
    }

    private void a(ExtractorInput extractorInput, List<Metadata.Entry> list) throws IOException {
        long position = extractorInput.getPosition();
        int length = (int) ((extractorInput.getLength() - extractorInput.getPosition()) - this.e);
        ParsableByteArray parsableByteArray = new ParsableByteArray(length);
        extractorInput.readFully(parsableByteArray.getData(), 0, length);
        for (int i = 0; i < this.c.size(); i++) {
            a aVar = this.c.get(i);
            parsableByteArray.setPosition((int) (aVar.b - position));
            parsableByteArray.skipBytes(4);
            int readLittleEndianInt = parsableByteArray.readLittleEndianInt();
            int a2 = a(parsableByteArray.readString(readLittleEndianInt));
            int i2 = aVar.c - (readLittleEndianInt + 8);
            switch (a2) {
                case 2192:
                    list.add(a(parsableByteArray, i2));
                    break;
                case 2816:
                case 2817:
                case 2819:
                case 2820:
                    break;
                default:
                    throw new IllegalStateException();
            }
        }
    }

    private static SlowMotionData a(ParsableByteArray parsableByteArray, int i) throws ParserException {
        ArrayList arrayList = new ArrayList();
        List<String> splitToList = b.splitToList(parsableByteArray.readString(i));
        for (int i2 = 0; i2 < splitToList.size(); i2++) {
            List<String> splitToList2 = a.splitToList(splitToList.get(i2));
            if (splitToList2.size() == 3) {
                try {
                    arrayList.add(new SlowMotionData.Segment(Long.parseLong(splitToList2.get(0)), Long.parseLong(splitToList2.get(1)), 1 << (Integer.parseInt(splitToList2.get(2)) - 1)));
                } catch (NumberFormatException e) {
                    throw ParserException.createForMalformedContainer(null, e);
                }
            } else {
                throw ParserException.createForMalformedContainer(null, null);
            }
        }
        return new SlowMotionData(arrayList);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static int a(String str) throws ParserException {
        char c;
        switch (str.hashCode()) {
            case -1711564334:
                if (str.equals("SlowMotion_Data")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1332107749:
                if (str.equals("Super_SlowMotion_Edit_Data")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -1251387154:
                if (str.equals("Super_SlowMotion_Data")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -830665521:
                if (str.equals("Super_SlowMotion_Deflickering_On")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 1760745220:
                if (str.equals("Super_SlowMotion_BGM")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return 2192;
            case 1:
                return 2816;
            case 2:
                return 2817;
            case 3:
                return 2819;
            case 4:
                return 2820;
            default:
                throw ParserException.createForMalformedContainer("Invalid SEF name", null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: SefReader.java */
    /* loaded from: classes2.dex */
    public static final class a {
        public final int a;
        public final long b;
        public final int c;

        public a(int i, long j, int i2) {
            this.a = i;
            this.b = j;
            this.c = i2;
        }
    }
}
