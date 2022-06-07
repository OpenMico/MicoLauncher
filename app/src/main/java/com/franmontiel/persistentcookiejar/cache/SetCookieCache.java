package com.franmontiel.persistentcookiejar.cache;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import okhttp3.Cookie;

/* loaded from: classes.dex */
public class SetCookieCache implements CookieCache {
    private Set<IdentifiableCookie> a = new HashSet();

    @Override // com.franmontiel.persistentcookiejar.cache.CookieCache
    public void addAll(Collection<Cookie> collection) {
        for (IdentifiableCookie identifiableCookie : IdentifiableCookie.a(collection)) {
            this.a.remove(identifiableCookie);
            this.a.add(identifiableCookie);
        }
    }

    @Override // com.franmontiel.persistentcookiejar.cache.CookieCache
    public void clear() {
        this.a.clear();
    }

    @Override // java.lang.Iterable
    public Iterator<Cookie> iterator() {
        return new SetCookieCacheIterator();
    }

    /* loaded from: classes.dex */
    private class SetCookieCacheIterator implements Iterator<Cookie> {
        private Iterator<IdentifiableCookie> b;

        public SetCookieCacheIterator() {
            this.b = SetCookieCache.this.a.iterator();
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.b.hasNext();
        }

        /* renamed from: a */
        public Cookie next() {
            return this.b.next().a();
        }

        @Override // java.util.Iterator
        public void remove() {
            this.b.remove();
        }
    }
}
