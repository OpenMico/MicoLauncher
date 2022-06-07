package com.google.android.exoplayer2.source.dash;

import android.os.SystemClock;
import android.util.Pair;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.exoplayer2.source.dash.manifest.BaseUrl;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.Iterables;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

/* loaded from: classes2.dex */
public final class BaseUrlExclusionList {
    private final Map<String, Long> a;
    private final Map<Integer, Long> b;
    private final Map<List<Pair<String, Integer>>, BaseUrl> c;
    private final Random d;

    public BaseUrlExclusionList() {
        this(new Random());
    }

    @VisibleForTesting
    BaseUrlExclusionList(Random random) {
        this.c = new HashMap();
        this.d = random;
        this.a = new HashMap();
        this.b = new HashMap();
    }

    public void exclude(BaseUrl baseUrl, long j) {
        long elapsedRealtime = SystemClock.elapsedRealtime() + j;
        a(baseUrl.serviceLocation, elapsedRealtime, this.a);
        a(Integer.valueOf(baseUrl.priority), elapsedRealtime, this.b);
    }

    @Nullable
    public BaseUrl selectBaseUrl(List<BaseUrl> list) {
        List<BaseUrl> a = a(list);
        if (a.size() < 2) {
            return (BaseUrl) Iterables.getFirst(a, null);
        }
        Collections.sort(a, $$Lambda$BaseUrlExclusionList$QNCV_MhUsXRn_h2oDvZNyRzDSf0.INSTANCE);
        ArrayList arrayList = new ArrayList();
        int i = a.get(0).priority;
        int i2 = 0;
        while (true) {
            if (i2 >= a.size()) {
                break;
            }
            BaseUrl baseUrl = a.get(i2);
            if (i == baseUrl.priority) {
                arrayList.add(new Pair(baseUrl.serviceLocation, Integer.valueOf(baseUrl.weight)));
                i2++;
            } else if (arrayList.size() == 1) {
                return a.get(0);
            }
        }
        BaseUrl baseUrl2 = this.c.get(arrayList);
        if (baseUrl2 != null) {
            return baseUrl2;
        }
        BaseUrl b = b(a.subList(0, arrayList.size()));
        this.c.put(arrayList, b);
        return b;
    }

    public int getPriorityCountAfterExclusion(List<BaseUrl> list) {
        HashSet hashSet = new HashSet();
        List<BaseUrl> a = a(list);
        for (int i = 0; i < a.size(); i++) {
            hashSet.add(Integer.valueOf(a.get(i).priority));
        }
        return hashSet.size();
    }

    public static int getPriorityCount(List<BaseUrl> list) {
        HashSet hashSet = new HashSet();
        for (int i = 0; i < list.size(); i++) {
            hashSet.add(Integer.valueOf(list.get(i).priority));
        }
        return hashSet.size();
    }

    public void reset() {
        this.a.clear();
        this.b.clear();
        this.c.clear();
    }

    private List<BaseUrl> a(List<BaseUrl> list) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        a(elapsedRealtime, this.a);
        a(elapsedRealtime, this.b);
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            BaseUrl baseUrl = list.get(i);
            if (!this.a.containsKey(baseUrl.serviceLocation) && !this.b.containsKey(Integer.valueOf(baseUrl.priority))) {
                arrayList.add(baseUrl);
            }
        }
        return arrayList;
    }

    private BaseUrl b(List<BaseUrl> list) {
        int i = 0;
        for (int i2 = 0; i2 < list.size(); i2++) {
            i += list.get(i2).weight;
        }
        int nextInt = this.d.nextInt(i);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            BaseUrl baseUrl = list.get(i4);
            i3 += baseUrl.weight;
            if (nextInt < i3) {
                return baseUrl;
            }
        }
        return (BaseUrl) Iterables.getLast(list);
    }

    private static <T> void a(T t, long j, Map<T, Long> map) {
        if (map.containsKey(t)) {
            j = Math.max(j, ((Long) Util.castNonNull(map.get(t))).longValue());
        }
        map.put(t, Long.valueOf(j));
    }

    private static <T> void a(long j, Map<T, Long> map) {
        ArrayList arrayList = new ArrayList();
        for (Map.Entry<T, Long> entry : map.entrySet()) {
            if (entry.getValue().longValue() <= j) {
                arrayList.add(entry.getKey());
            }
        }
        for (int i = 0; i < arrayList.size(); i++) {
            map.remove(arrayList.get(i));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int a(BaseUrl baseUrl, BaseUrl baseUrl2) {
        int compare = Integer.compare(baseUrl.priority, baseUrl2.priority);
        return compare != 0 ? compare : baseUrl.serviceLocation.compareTo(baseUrl2.serviceLocation);
    }
}
