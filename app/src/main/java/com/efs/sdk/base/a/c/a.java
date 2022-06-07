package com.efs.sdk.base.a.c;

import android.content.Context;
import android.os.Message;
import android.util.Pair;
import android.webkit.ValueCallback;
import com.efs.sdk.base.observer.IEfsReporterObserver;
import com.efs.sdk.base.processor.action.ILogEncryptAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes.dex */
public final class a {
    public String a;
    public String b;
    public Context c;
    public String h;
    public ILogEncryptAction m;
    public boolean d = true;
    public boolean e = true;
    public boolean f = false;
    private Boolean p = null;
    public boolean g = false;
    public boolean i = false;
    public long j = 5000;
    public long k = 10000;
    public long l = 10000;
    private Map<String, String> q = new HashMap(5);
    public ConcurrentHashMap<Integer, List<ValueCallback<Pair<Message, Message>>>> n = new ConcurrentHashMap<>();
    public List<IEfsReporterObserver> o = new ArrayList(5);

    public final Map<String, String> a() {
        Map<String, String> map = this.q;
        return map == null ? Collections.emptyMap() : map;
    }

    public final void a(Map<String, String> map) {
        if (map != null && map.size() > 0) {
            HashMap hashMap = new HashMap(this.q);
            hashMap.putAll(map);
            this.q = hashMap;
        }
    }

    public final List<ValueCallback<Pair<Message, Message>>> a(int i) {
        if (!this.n.containsKey(Integer.valueOf(i)) || this.n.get(Integer.valueOf(i)) == null) {
            return Collections.emptyList();
        }
        return this.n.get(Integer.valueOf(i));
    }
}
