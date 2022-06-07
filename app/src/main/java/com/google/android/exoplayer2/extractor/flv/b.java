package com.google.android.exoplayer2.extractor.flv;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.extractor.DummyTrackOutput;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: ScriptTagPayloadReader.java */
/* loaded from: classes2.dex */
final class b extends TagPayloadReader {
    private long b = C.TIME_UNSET;
    private long[] c = new long[0];
    private long[] d = new long[0];

    @Override // com.google.android.exoplayer2.extractor.flv.TagPayloadReader
    protected boolean a(ParsableByteArray parsableByteArray) {
        return true;
    }

    public b() {
        super(new DummyTrackOutput());
    }

    public long a() {
        return this.b;
    }

    public long[] b() {
        return this.c;
    }

    public long[] c() {
        return this.d;
    }

    @Override // com.google.android.exoplayer2.extractor.flv.TagPayloadReader
    protected boolean a(ParsableByteArray parsableByteArray, long j) {
        if (!(b(parsableByteArray) == 2 && "onMetaData".equals(e(parsableByteArray)) && b(parsableByteArray) == 8)) {
            return false;
        }
        HashMap<String, Object> h = h(parsableByteArray);
        Object obj = h.get("duration");
        if (obj instanceof Double) {
            double doubleValue = ((Double) obj).doubleValue();
            if (doubleValue > 0.0d) {
                this.b = (long) (doubleValue * 1000000.0d);
            }
        }
        Object obj2 = h.get("keyframes");
        if (obj2 instanceof Map) {
            Map map = (Map) obj2;
            Object obj3 = map.get("filepositions");
            Object obj4 = map.get("times");
            if ((obj3 instanceof List) && (obj4 instanceof List)) {
                List list = (List) obj3;
                List list2 = (List) obj4;
                int size = list2.size();
                this.c = new long[size];
                this.d = new long[size];
                for (int i = 0; i < size; i++) {
                    Object obj5 = list.get(i);
                    Object obj6 = list2.get(i);
                    if (!(obj6 instanceof Double) || !(obj5 instanceof Double)) {
                        this.c = new long[0];
                        this.d = new long[0];
                        break;
                    }
                    this.c[i] = (long) (((Double) obj6).doubleValue() * 1000000.0d);
                    this.d[i] = ((Double) obj5).longValue();
                }
            }
        }
        return false;
    }

    private static int b(ParsableByteArray parsableByteArray) {
        return parsableByteArray.readUnsignedByte();
    }

    private static Boolean c(ParsableByteArray parsableByteArray) {
        boolean z = true;
        if (parsableByteArray.readUnsignedByte() != 1) {
            z = false;
        }
        return Boolean.valueOf(z);
    }

    private static Double d(ParsableByteArray parsableByteArray) {
        return Double.valueOf(Double.longBitsToDouble(parsableByteArray.readLong()));
    }

    private static String e(ParsableByteArray parsableByteArray) {
        int readUnsignedShort = parsableByteArray.readUnsignedShort();
        int position = parsableByteArray.getPosition();
        parsableByteArray.skipBytes(readUnsignedShort);
        return new String(parsableByteArray.getData(), position, readUnsignedShort);
    }

    private static ArrayList<Object> f(ParsableByteArray parsableByteArray) {
        int readUnsignedIntToInt = parsableByteArray.readUnsignedIntToInt();
        ArrayList<Object> arrayList = new ArrayList<>(readUnsignedIntToInt);
        for (int i = 0; i < readUnsignedIntToInt; i++) {
            Object a = a(parsableByteArray, b(parsableByteArray));
            if (a != null) {
                arrayList.add(a);
            }
        }
        return arrayList;
    }

    private static HashMap<String, Object> g(ParsableByteArray parsableByteArray) {
        HashMap<String, Object> hashMap = new HashMap<>();
        while (true) {
            String e = e(parsableByteArray);
            int b = b(parsableByteArray);
            if (b == 9) {
                return hashMap;
            }
            Object a = a(parsableByteArray, b);
            if (a != null) {
                hashMap.put(e, a);
            }
        }
    }

    private static HashMap<String, Object> h(ParsableByteArray parsableByteArray) {
        int readUnsignedIntToInt = parsableByteArray.readUnsignedIntToInt();
        HashMap<String, Object> hashMap = new HashMap<>(readUnsignedIntToInt);
        for (int i = 0; i < readUnsignedIntToInt; i++) {
            String e = e(parsableByteArray);
            Object a = a(parsableByteArray, b(parsableByteArray));
            if (a != null) {
                hashMap.put(e, a);
            }
        }
        return hashMap;
    }

    private static Date i(ParsableByteArray parsableByteArray) {
        Date date = new Date((long) d(parsableByteArray).doubleValue());
        parsableByteArray.skipBytes(2);
        return date;
    }

    @Nullable
    private static Object a(ParsableByteArray parsableByteArray, int i) {
        if (i == 8) {
            return h(parsableByteArray);
        }
        switch (i) {
            case 0:
                return d(parsableByteArray);
            case 1:
                return c(parsableByteArray);
            case 2:
                return e(parsableByteArray);
            case 3:
                return g(parsableByteArray);
            default:
                switch (i) {
                    case 10:
                        return f(parsableByteArray);
                    case 11:
                        return i(parsableByteArray);
                    default:
                        return null;
                }
        }
    }
}
