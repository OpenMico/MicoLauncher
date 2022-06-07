package io.netty.channel.group;

import com.xiaomi.smarthome.setting.ServerSetting;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* compiled from: CombinedIterator.java */
/* loaded from: classes4.dex */
final class a<E> implements Iterator<E> {
    private final Iterator<E> a;
    private final Iterator<E> b;
    private Iterator<E> c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(Iterator<E> it, Iterator<E> it2) {
        if (it == null) {
            throw new NullPointerException("i1");
        } else if (it2 != null) {
            this.a = it;
            this.b = it2;
            this.c = it;
        } else {
            throw new NullPointerException(ServerSetting.SERVER_IN_TRUE);
        }
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        while (!this.c.hasNext()) {
            if (this.c != this.a) {
                return false;
            }
            this.c = this.b;
        }
        return true;
    }

    @Override // java.util.Iterator
    public E next() {
        while (true) {
            try {
                return this.c.next();
            } catch (NoSuchElementException e) {
                if (this.c == this.a) {
                    this.c = this.b;
                } else {
                    throw e;
                }
            }
        }
    }

    @Override // java.util.Iterator
    public void remove() {
        this.c.remove();
    }
}
