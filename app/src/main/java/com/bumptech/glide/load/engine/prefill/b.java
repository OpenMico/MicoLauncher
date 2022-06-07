package com.bumptech.glide.load.engine.prefill;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: PreFillQueue.java */
/* loaded from: classes.dex */
public final class b {
    private final Map<PreFillType, Integer> a;
    private final List<PreFillType> b;
    private int c;
    private int d;

    public b(Map<PreFillType, Integer> map) {
        this.a = map;
        this.b = new ArrayList(map.keySet());
        for (Integer num : map.values()) {
            this.c += num.intValue();
        }
    }

    public PreFillType a() {
        PreFillType preFillType = this.b.get(this.d);
        Integer num = this.a.get(preFillType);
        if (num.intValue() == 1) {
            this.a.remove(preFillType);
            this.b.remove(this.d);
        } else {
            this.a.put(preFillType, Integer.valueOf(num.intValue() - 1));
        }
        this.c--;
        this.d = this.b.isEmpty() ? 0 : (this.d + 1) % this.b.size();
        return preFillType;
    }

    public boolean b() {
        return this.c == 0;
    }
}
