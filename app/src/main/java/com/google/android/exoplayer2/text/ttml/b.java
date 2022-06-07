package com.google.android.exoplayer2.text.ttml;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.SpannableStringBuilder;
import android.util.Base64;
import android.util.Pair;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.util.Assertions;
import com.umeng.analytics.pro.ai;
import com.xiaomi.micolauncher.skills.music.model.PlayerEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

/* compiled from: TtmlNode.java */
/* loaded from: classes2.dex */
public final class b {
    @Nullable
    public final String a;
    @Nullable
    public final String b;
    public final boolean c;
    public final long d;
    public final long e;
    @Nullable
    public final TtmlStyle f;
    public final String g;
    @Nullable
    public final String h;
    @Nullable
    public final b i;
    @Nullable
    private final String[] j;
    private final HashMap<String, Integer> k;
    private final HashMap<String, Integer> l;
    private List<b> m;

    public static b a(String str) {
        return new b(null, d.a(str), C.TIME_UNSET, C.TIME_UNSET, null, null, "", null, null);
    }

    public static b a(@Nullable String str, long j, long j2, @Nullable TtmlStyle ttmlStyle, @Nullable String[] strArr, String str2, @Nullable String str3, @Nullable b bVar) {
        return new b(str, null, j, j2, ttmlStyle, strArr, str2, str3, bVar);
    }

    private b(@Nullable String str, @Nullable String str2, long j, long j2, @Nullable TtmlStyle ttmlStyle, @Nullable String[] strArr, String str3, @Nullable String str4, @Nullable b bVar) {
        this.a = str;
        this.b = str2;
        this.h = str4;
        this.f = ttmlStyle;
        this.j = strArr;
        this.c = str2 != null;
        this.d = j;
        this.e = j2;
        this.g = (String) Assertions.checkNotNull(str3);
        this.i = bVar;
        this.k = new HashMap<>();
        this.l = new HashMap<>();
    }

    public boolean a(long j) {
        return (this.d == C.TIME_UNSET && this.e == C.TIME_UNSET) || (this.d <= j && this.e == C.TIME_UNSET) || ((this.d == C.TIME_UNSET && j < this.e) || (this.d <= j && j < this.e));
    }

    public void a(b bVar) {
        if (this.m == null) {
            this.m = new ArrayList();
        }
        this.m.add(bVar);
    }

    public b a(int i) {
        List<b> list = this.m;
        if (list != null) {
            return list.get(i);
        }
        throw new IndexOutOfBoundsException();
    }

    public int a() {
        List<b> list = this.m;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public long[] b() {
        TreeSet<Long> treeSet = new TreeSet<>();
        int i = 0;
        a(treeSet, false);
        long[] jArr = new long[treeSet.size()];
        Iterator<Long> it = treeSet.iterator();
        while (it.hasNext()) {
            i++;
            jArr[i] = it.next().longValue();
        }
        return jArr;
    }

    private void a(TreeSet<Long> treeSet, boolean z) {
        boolean equals = ai.av.equals(this.a);
        boolean equals2 = "div".equals(this.a);
        if (z || equals || (equals2 && this.h != null)) {
            long j = this.d;
            if (j != C.TIME_UNSET) {
                treeSet.add(Long.valueOf(j));
            }
            long j2 = this.e;
            if (j2 != C.TIME_UNSET) {
                treeSet.add(Long.valueOf(j2));
            }
        }
        if (this.m != null) {
            for (int i = 0; i < this.m.size(); i++) {
                this.m.get(i).a(treeSet, z || equals);
            }
        }
    }

    @Nullable
    public String[] c() {
        return this.j;
    }

    public List<Cue> a(long j, Map<String, TtmlStyle> map, Map<String, c> map2, Map<String, String> map3) {
        List<Pair<String, String>> arrayList = new ArrayList<>();
        a(j, this.g, arrayList);
        TreeMap treeMap = new TreeMap();
        a(j, false, this.g, (Map<String, Cue.Builder>) treeMap);
        a(j, map, map2, this.g, treeMap);
        ArrayList arrayList2 = new ArrayList();
        for (Pair<String, String> pair : arrayList) {
            String str = map3.get(pair.second);
            if (str != null) {
                byte[] decode = Base64.decode(str, 0);
                Bitmap decodeByteArray = BitmapFactory.decodeByteArray(decode, 0, decode.length);
                c cVar = (c) Assertions.checkNotNull(map2.get(pair.first));
                arrayList2.add(new Cue.Builder().setBitmap(decodeByteArray).setPosition(cVar.b).setPositionAnchor(0).setLine(cVar.c, 0).setLineAnchor(cVar.e).setSize(cVar.f).setBitmapHeight(cVar.g).setVerticalType(cVar.j).build());
            }
        }
        for (Map.Entry entry : treeMap.entrySet()) {
            c cVar2 = (c) Assertions.checkNotNull(map2.get(entry.getKey()));
            Cue.Builder builder = (Cue.Builder) entry.getValue();
            a((SpannableStringBuilder) Assertions.checkNotNull(builder.getText()));
            builder.setLine(cVar2.c, cVar2.d);
            builder.setLineAnchor(cVar2.e);
            builder.setPosition(cVar2.b);
            builder.setSize(cVar2.f);
            builder.setTextSize(cVar2.i, cVar2.h);
            builder.setVerticalType(cVar2.j);
            arrayList2.add(builder.build());
        }
        return arrayList2;
    }

    private void a(long j, String str, List<Pair<String, String>> list) {
        String str2;
        if (!"".equals(this.g)) {
            str = this.g;
        }
        if (!a(j) || !"div".equals(this.a) || (str2 = this.h) == null) {
            for (int i = 0; i < a(); i++) {
                a(i).a(j, str, list);
            }
            return;
        }
        list.add(new Pair<>(str, str2));
    }

    private void a(long j, boolean z, String str, Map<String, Cue.Builder> map) {
        this.k.clear();
        this.l.clear();
        if (!PlayerEvent.METADATA.equals(this.a)) {
            if (!"".equals(this.g)) {
                str = this.g;
            }
            if (this.c && z) {
                a(str, map).append((CharSequence) Assertions.checkNotNull(this.b));
            } else if ("br".equals(this.a) && z) {
                a(str, map).append('\n');
            } else if (a(j)) {
                for (Map.Entry<String, Cue.Builder> entry : map.entrySet()) {
                    this.k.put(entry.getKey(), Integer.valueOf(((CharSequence) Assertions.checkNotNull(entry.getValue().getText())).length()));
                }
                boolean equals = ai.av.equals(this.a);
                for (int i = 0; i < a(); i++) {
                    a(i).a(j, z || equals, str, map);
                }
                if (equals) {
                    d.a(a(str, map));
                }
                for (Map.Entry<String, Cue.Builder> entry2 : map.entrySet()) {
                    this.l.put(entry2.getKey(), Integer.valueOf(((CharSequence) Assertions.checkNotNull(entry2.getValue().getText())).length()));
                }
            }
        }
    }

    private static SpannableStringBuilder a(String str, Map<String, Cue.Builder> map) {
        if (!map.containsKey(str)) {
            Cue.Builder builder = new Cue.Builder();
            builder.setText(new SpannableStringBuilder());
            map.put(str, builder);
        }
        return (SpannableStringBuilder) Assertions.checkNotNull(map.get(str).getText());
    }

    private void a(long j, Map<String, TtmlStyle> map, Map<String, c> map2, String str, Map<String, Cue.Builder> map3) {
        int i;
        if (a(j)) {
            String str2 = "".equals(this.g) ? str : this.g;
            Iterator<Map.Entry<String, Integer>> it = this.l.entrySet().iterator();
            while (true) {
                i = 0;
                if (it.hasNext()) {
                    Map.Entry<String, Integer> next = it.next();
                    String key = next.getKey();
                    if (this.k.containsKey(key)) {
                        i = this.k.get(key).intValue();
                    }
                    int intValue = next.getValue().intValue();
                    if (i != intValue) {
                        a(map, (Cue.Builder) Assertions.checkNotNull(map3.get(key)), i, intValue, ((c) Assertions.checkNotNull(map2.get(str2))).j);
                    }
                }
            }
            while (i < a()) {
                a(i).a(j, map, map2, str2, map3);
                i++;
            }
        }
    }

    private void a(Map<String, TtmlStyle> map, Cue.Builder builder, int i, int i2, int i3) {
        SpannableStringBuilder spannableStringBuilder;
        TtmlStyle a = d.a(this.f, this.j, map);
        SpannableStringBuilder spannableStringBuilder2 = (SpannableStringBuilder) builder.getText();
        if (spannableStringBuilder2 == null) {
            SpannableStringBuilder spannableStringBuilder3 = new SpannableStringBuilder();
            builder.setText(spannableStringBuilder3);
            spannableStringBuilder = spannableStringBuilder3;
        } else {
            spannableStringBuilder = spannableStringBuilder2;
        }
        if (a != null) {
            d.a(spannableStringBuilder, i, i2, a, this.i, map, i3);
            if (ai.av.equals(this.a)) {
                if (a.i() != Float.MAX_VALUE) {
                    builder.setShearDegrees((a.i() * (-90.0f)) / 100.0f);
                }
                if (a.m() != null) {
                    builder.setTextAlignment(a.m());
                }
                if (a.n() != null) {
                    builder.setMultiRowAlignment(a.n());
                }
            }
        }
    }

    private static void a(SpannableStringBuilder spannableStringBuilder) {
        a[] aVarArr = (a[]) spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), a.class);
        for (a aVar : aVarArr) {
            spannableStringBuilder.replace(spannableStringBuilder.getSpanStart(aVar), spannableStringBuilder.getSpanEnd(aVar), "");
        }
        for (int i = 0; i < spannableStringBuilder.length(); i++) {
            if (spannableStringBuilder.charAt(i) == ' ') {
                int i2 = i + 1;
                int i3 = i2;
                while (i3 < spannableStringBuilder.length() && spannableStringBuilder.charAt(i3) == ' ') {
                    i3++;
                }
                int i4 = i3 - i2;
                if (i4 > 0) {
                    spannableStringBuilder.delete(i, i4 + i);
                }
            }
        }
        if (spannableStringBuilder.length() > 0 && spannableStringBuilder.charAt(0) == ' ') {
            spannableStringBuilder.delete(0, 1);
        }
        for (int i5 = 0; i5 < spannableStringBuilder.length() - 1; i5++) {
            if (spannableStringBuilder.charAt(i5) == '\n') {
                int i6 = i5 + 1;
                if (spannableStringBuilder.charAt(i6) == ' ') {
                    spannableStringBuilder.delete(i6, i5 + 2);
                }
            }
        }
        if (spannableStringBuilder.length() > 0 && spannableStringBuilder.charAt(spannableStringBuilder.length() - 1) == ' ') {
            spannableStringBuilder.delete(spannableStringBuilder.length() - 1, spannableStringBuilder.length());
        }
        for (int i7 = 0; i7 < spannableStringBuilder.length() - 1; i7++) {
            if (spannableStringBuilder.charAt(i7) == ' ') {
                int i8 = i7 + 1;
                if (spannableStringBuilder.charAt(i8) == '\n') {
                    spannableStringBuilder.delete(i7, i8);
                }
            }
        }
        if (spannableStringBuilder.length() > 0 && spannableStringBuilder.charAt(spannableStringBuilder.length() - 1) == '\n') {
            spannableStringBuilder.delete(spannableStringBuilder.length() - 1, spannableStringBuilder.length());
        }
    }
}
