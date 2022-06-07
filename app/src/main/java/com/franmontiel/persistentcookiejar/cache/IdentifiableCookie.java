package com.franmontiel.persistentcookiejar.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import okhttp3.Cookie;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class IdentifiableCookie {
    private Cookie a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static List<IdentifiableCookie> a(Collection<Cookie> collection) {
        ArrayList arrayList = new ArrayList(collection.size());
        for (Cookie cookie : collection) {
            arrayList.add(new IdentifiableCookie(cookie));
        }
        return arrayList;
    }

    IdentifiableCookie(Cookie cookie) {
        this.a = cookie;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Cookie a() {
        return this.a;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof IdentifiableCookie)) {
            return false;
        }
        IdentifiableCookie identifiableCookie = (IdentifiableCookie) obj;
        return identifiableCookie.a.name().equals(this.a.name()) && identifiableCookie.a.domain().equals(this.a.domain()) && identifiableCookie.a.path().equals(this.a.path()) && identifiableCookie.a.secure() == this.a.secure() && identifiableCookie.a.hostOnly() == this.a.hostOnly();
    }

    public int hashCode() {
        return ((((((((527 + this.a.name().hashCode()) * 31) + this.a.domain().hashCode()) * 31) + this.a.path().hashCode()) * 31) + (!this.a.secure())) * 31) + (!this.a.hostOnly());
    }
}
