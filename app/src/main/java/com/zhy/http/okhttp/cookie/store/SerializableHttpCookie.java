package com.zhy.http.okhttp.cookie.store;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import okhttp3.Cookie;

/* loaded from: classes4.dex */
public class SerializableHttpCookie implements Serializable {
    private static final long serialVersionUID = 6374381323722046732L;
    private final transient Cookie a;
    private transient Cookie b;

    public SerializableHttpCookie(Cookie cookie) {
        this.a = cookie;
    }

    public Cookie getCookie() {
        Cookie cookie = this.a;
        Cookie cookie2 = this.b;
        return cookie2 != null ? cookie2 : cookie;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeObject(this.a.name());
        objectOutputStream.writeObject(this.a.value());
        objectOutputStream.writeLong(this.a.expiresAt());
        objectOutputStream.writeObject(this.a.domain());
        objectOutputStream.writeObject(this.a.path());
        objectOutputStream.writeBoolean(this.a.secure());
        objectOutputStream.writeBoolean(this.a.httpOnly());
        objectOutputStream.writeBoolean(this.a.hostOnly());
        objectOutputStream.writeBoolean(this.a.persistent());
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        long readLong = objectInputStream.readLong();
        String str = (String) objectInputStream.readObject();
        String str2 = (String) objectInputStream.readObject();
        boolean readBoolean = objectInputStream.readBoolean();
        boolean readBoolean2 = objectInputStream.readBoolean();
        boolean readBoolean3 = objectInputStream.readBoolean();
        objectInputStream.readBoolean();
        Cookie.Builder expiresAt = new Cookie.Builder().name((String) objectInputStream.readObject()).value((String) objectInputStream.readObject()).expiresAt(readLong);
        Cookie.Builder path = (readBoolean3 ? expiresAt.hostOnlyDomain(str) : expiresAt.domain(str)).path(str2);
        if (readBoolean) {
            path = path.secure();
        }
        if (readBoolean2) {
            path = path.httpOnly();
        }
        this.b = path.build();
    }
}
