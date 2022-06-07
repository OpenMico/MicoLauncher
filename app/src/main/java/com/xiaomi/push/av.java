package com.xiaomi.push;

import java.util.LinkedList;

/* loaded from: classes4.dex */
public class av {
    private LinkedList<a> a = new LinkedList<>();

    /* loaded from: classes4.dex */
    public static class a {
        private static final av b = new av();
        public int a;

        /* renamed from: a  reason: collision with other field name */
        public Object f12a;

        /* renamed from: a  reason: collision with other field name */
        public String f13a;

        a(int i, Object obj) {
            this.a = i;
            this.f12a = obj;
        }
    }

    public static av a() {
        return a.b;
    }

    private void b() {
        if (this.a.size() > 100) {
            this.a.removeFirst();
        }
    }

    /* renamed from: a  reason: collision with other method in class */
    public synchronized int m759a() {
        return this.a.size();
    }

    /* renamed from: a  reason: collision with other method in class */
    public synchronized LinkedList<a> m760a() {
        LinkedList<a> linkedList;
        linkedList = this.a;
        this.a = new LinkedList<>();
        return linkedList;
    }

    public synchronized void a(Object obj) {
        this.a.add(new a(0, obj));
        b();
    }
}
