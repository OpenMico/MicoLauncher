package com.google.android.exoplayer2.extractor;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.extractor.amr.AmrExtractor;
import com.google.android.exoplayer2.extractor.flac.FlacExtractor;
import com.google.android.exoplayer2.extractor.flv.FlvExtractor;
import com.google.android.exoplayer2.extractor.jpeg.JpegExtractor;
import com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor;
import com.google.android.exoplayer2.extractor.mp3.Mp3Extractor;
import com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor;
import com.google.android.exoplayer2.extractor.mp4.Mp4Extractor;
import com.google.android.exoplayer2.extractor.ogg.OggExtractor;
import com.google.android.exoplayer2.extractor.ts.Ac3Extractor;
import com.google.android.exoplayer2.extractor.ts.Ac4Extractor;
import com.google.android.exoplayer2.extractor.ts.AdtsExtractor;
import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import com.google.android.exoplayer2.extractor.ts.TsExtractor;
import com.google.android.exoplayer2.extractor.wav.WavExtractor;
import com.google.android.exoplayer2.util.FileTypes;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public final class DefaultExtractorsFactory implements ExtractorsFactory {
    private static final int[] a = {5, 4, 12, 8, 3, 10, 9, 11, 6, 2, 0, 1, 7, 14};
    @Nullable
    private static final Constructor<? extends Extractor> b;
    private boolean c;
    private int d;
    private int e;
    private int f;
    private int g;
    private int h;
    private int i;
    private int j;
    private int l;
    private int k = 1;
    private int m = TsExtractor.DEFAULT_TIMESTAMP_SEARCH_BYTES;

    static {
        Constructor<? extends Extractor> constructor = null;
        try {
            if (Boolean.TRUE.equals(Class.forName("com.google.android.exoplayer2.ext.flac.FlacLibrary").getMethod("isAvailable", new Class[0]).invoke(null, new Object[0]))) {
                constructor = Class.forName("com.google.android.exoplayer2.ext.flac.FlacExtractor").asSubclass(Extractor.class).getConstructor(Integer.TYPE);
            }
        } catch (ClassNotFoundException unused) {
        } catch (Exception e) {
            throw new RuntimeException("Error instantiating FLAC extension", e);
        }
        b = constructor;
    }

    public synchronized DefaultExtractorsFactory setConstantBitrateSeekingEnabled(boolean z) {
        this.c = z;
        return this;
    }

    public synchronized DefaultExtractorsFactory setAdtsExtractorFlags(int i) {
        this.d = i;
        return this;
    }

    public synchronized DefaultExtractorsFactory setAmrExtractorFlags(int i) {
        this.e = i;
        return this;
    }

    public synchronized DefaultExtractorsFactory setFlacExtractorFlags(int i) {
        this.f = i;
        return this;
    }

    public synchronized DefaultExtractorsFactory setMatroskaExtractorFlags(int i) {
        this.g = i;
        return this;
    }

    public synchronized DefaultExtractorsFactory setMp4ExtractorFlags(int i) {
        this.h = i;
        return this;
    }

    public synchronized DefaultExtractorsFactory setFragmentedMp4ExtractorFlags(int i) {
        this.i = i;
        return this;
    }

    public synchronized DefaultExtractorsFactory setMp3ExtractorFlags(int i) {
        this.j = i;
        return this;
    }

    public synchronized DefaultExtractorsFactory setTsExtractorMode(int i) {
        this.k = i;
        return this;
    }

    public synchronized DefaultExtractorsFactory setTsExtractorFlags(int i) {
        this.l = i;
        return this;
    }

    public synchronized DefaultExtractorsFactory setTsExtractorTimestampSearchBytes(int i) {
        this.m = i;
        return this;
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorsFactory
    public synchronized Extractor[] createExtractors() {
        return createExtractors(Uri.EMPTY, new HashMap());
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorsFactory
    public synchronized Extractor[] createExtractors(Uri uri, Map<String, List<String>> map) {
        ArrayList arrayList;
        arrayList = new ArrayList(14);
        int inferFileTypeFromResponseHeaders = FileTypes.inferFileTypeFromResponseHeaders(map);
        if (inferFileTypeFromResponseHeaders != -1) {
            a(inferFileTypeFromResponseHeaders, arrayList);
        }
        int inferFileTypeFromUri = FileTypes.inferFileTypeFromUri(uri);
        if (!(inferFileTypeFromUri == -1 || inferFileTypeFromUri == inferFileTypeFromResponseHeaders)) {
            a(inferFileTypeFromUri, arrayList);
        }
        int[] iArr = a;
        for (int i : iArr) {
            if (!(i == inferFileTypeFromResponseHeaders || i == inferFileTypeFromUri)) {
                a(i, arrayList);
            }
        }
        return (Extractor[]) arrayList.toArray(new Extractor[arrayList.size()]);
    }

    private void a(int i, List<Extractor> list) {
        switch (i) {
            case 0:
                list.add(new Ac3Extractor());
                return;
            case 1:
                list.add(new Ac4Extractor());
                return;
            case 2:
                list.add(new AdtsExtractor(this.d | (this.c ? 1 : 0)));
                return;
            case 3:
                list.add(new AmrExtractor(this.e | (this.c ? 1 : 0)));
                return;
            case 4:
                Constructor<? extends Extractor> constructor = b;
                if (constructor != null) {
                    try {
                        list.add((Extractor) constructor.newInstance(Integer.valueOf(this.f)));
                        return;
                    } catch (Exception e) {
                        throw new IllegalStateException("Unexpected error creating FLAC extractor", e);
                    }
                } else {
                    list.add(new FlacExtractor(this.f));
                    return;
                }
            case 5:
                list.add(new FlvExtractor());
                return;
            case 6:
                list.add(new MatroskaExtractor(this.g));
                return;
            case 7:
                list.add(new Mp3Extractor(this.j | (this.c ? 1 : 0)));
                return;
            case 8:
                list.add(new FragmentedMp4Extractor(this.i));
                list.add(new Mp4Extractor(this.h));
                return;
            case 9:
                list.add(new OggExtractor());
                return;
            case 10:
                list.add(new PsExtractor());
                return;
            case 11:
                list.add(new TsExtractor(this.k, this.l, this.m));
                return;
            case 12:
                list.add(new WavExtractor());
                return;
            case 13:
            default:
                return;
            case 14:
                list.add(new JpegExtractor());
                return;
        }
    }
}
