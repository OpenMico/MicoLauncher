package org.greenrobot.eventbus;

import java.util.ArrayList;
import java.util.List;

/* compiled from: PendingPost.java */
/* loaded from: classes5.dex */
public final class c {
    private static final List<c> d = new ArrayList();
    Object a;
    g b;
    c c;

    private c(Object obj, g gVar) {
        this.a = obj;
        this.b = gVar;
    }

    public static c a(g gVar, Object obj) {
        synchronized (d) {
            int size = d.size();
            if (size <= 0) {
                return new c(obj, gVar);
            }
            c remove = d.remove(size - 1);
            remove.a = obj;
            remove.b = gVar;
            remove.c = null;
            return remove;
        }
    }

    public static void a(c cVar) {
        cVar.a = null;
        cVar.b = null;
        cVar.c = null;
        synchronized (d) {
            if (d.size() < 10000) {
                d.add(cVar);
            }
        }
    }
}
