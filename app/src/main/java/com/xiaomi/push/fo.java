package com.xiaomi.push;

import java.util.Map;

/* loaded from: classes4.dex */
public class fo implements Cloneable {
    public static String a = "wcc-ml-test10.bj";
    public static final String b = af.a;
    public static String c = null;
    private String d;
    private String e;
    private int f;
    private boolean g = fn.a;
    private boolean h = true;
    private String i;
    private fr j;

    public fo(Map<String, Integer> map, int i, String str, fr frVar) {
        a(map, i, str, frVar);
    }

    public static final String a() {
        String str = c;
        return str != null ? str : ac.m754a() ? "sandbox.xmpush.xiaomi.com" : ac.b() ? b : "app.chat.xiaomi.net";
    }

    public static final void a(String str) {
        c = str;
    }

    private void a(Map<String, Integer> map, int i, String str, fr frVar) {
        this.f = i;
        this.d = str;
        this.j = frVar;
    }

    /* renamed from: a  reason: collision with other method in class */
    public int mo923a() {
        return this.f;
    }

    public void a(boolean z) {
        this.g = z;
    }

    /* renamed from: a  reason: collision with other method in class */
    public boolean m924a() {
        return this.g;
    }

    /* renamed from: a  reason: collision with other method in class */
    public byte[] m925a() {
        return null;
    }

    public String b() {
        return this.i;
    }

    public void b(String str) {
        this.i = str;
    }

    public String c() {
        if (this.e == null) {
            this.e = a();
        }
        return this.e;
    }

    public void c(String str) {
        this.e = str;
    }
}
