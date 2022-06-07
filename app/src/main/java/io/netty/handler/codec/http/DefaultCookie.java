package io.netty.handler.codec.http;

import com.xiaomi.onetrack.OneTrack;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

@Deprecated
/* loaded from: classes4.dex */
public class DefaultCookie extends io.netty.handler.codec.http.cookie.DefaultCookie implements Cookie {
    private String a;
    private String b;
    private boolean c;
    private Set<Integer> d = Collections.emptySet();
    private Set<Integer> e = this.d;
    private int f;

    public DefaultCookie(String str, String str2) {
        super(str, str2);
    }

    @Override // io.netty.handler.codec.http.Cookie
    @Deprecated
    public String getName() {
        return name();
    }

    @Override // io.netty.handler.codec.http.Cookie
    @Deprecated
    public String getValue() {
        return value();
    }

    @Override // io.netty.handler.codec.http.Cookie
    @Deprecated
    public String getDomain() {
        return domain();
    }

    @Override // io.netty.handler.codec.http.Cookie
    @Deprecated
    public String getPath() {
        return path();
    }

    @Override // io.netty.handler.codec.http.Cookie
    @Deprecated
    public String getComment() {
        return comment();
    }

    @Override // io.netty.handler.codec.http.Cookie
    @Deprecated
    public String comment() {
        return this.a;
    }

    @Override // io.netty.handler.codec.http.Cookie
    @Deprecated
    public void setComment(String str) {
        this.a = validateValue(OneTrack.Event.COMMENT, str);
    }

    @Override // io.netty.handler.codec.http.Cookie
    @Deprecated
    public String getCommentUrl() {
        return commentUrl();
    }

    @Override // io.netty.handler.codec.http.Cookie
    @Deprecated
    public String commentUrl() {
        return this.b;
    }

    @Override // io.netty.handler.codec.http.Cookie
    @Deprecated
    public void setCommentUrl(String str) {
        this.b = validateValue("commentUrl", str);
    }

    @Override // io.netty.handler.codec.http.Cookie
    @Deprecated
    public boolean isDiscard() {
        return this.c;
    }

    @Override // io.netty.handler.codec.http.Cookie
    @Deprecated
    public void setDiscard(boolean z) {
        this.c = z;
    }

    @Override // io.netty.handler.codec.http.Cookie
    @Deprecated
    public Set<Integer> getPorts() {
        return ports();
    }

    @Override // io.netty.handler.codec.http.Cookie
    @Deprecated
    public Set<Integer> ports() {
        if (this.e == null) {
            this.e = Collections.unmodifiableSet(this.d);
        }
        return this.e;
    }

    @Override // io.netty.handler.codec.http.Cookie
    @Deprecated
    public void setPorts(int... iArr) {
        if (iArr != null) {
            int[] iArr2 = (int[]) iArr.clone();
            if (iArr2.length == 0) {
                Set<Integer> emptySet = Collections.emptySet();
                this.d = emptySet;
                this.e = emptySet;
                return;
            }
            TreeSet treeSet = new TreeSet();
            for (int i : iArr2) {
                if (i <= 0 || i > 65535) {
                    throw new IllegalArgumentException("port out of range: " + i);
                }
                treeSet.add(Integer.valueOf(i));
            }
            this.d = treeSet;
            this.e = null;
            return;
        }
        throw new NullPointerException("ports");
    }

    @Override // io.netty.handler.codec.http.Cookie
    @Deprecated
    public void setPorts(Iterable<Integer> iterable) {
        TreeSet treeSet = new TreeSet();
        for (Integer num : iterable) {
            int intValue = num.intValue();
            if (intValue <= 0 || intValue > 65535) {
                throw new IllegalArgumentException("port out of range: " + intValue);
            }
            treeSet.add(Integer.valueOf(intValue));
        }
        if (treeSet.isEmpty()) {
            Set<Integer> emptySet = Collections.emptySet();
            this.d = emptySet;
            this.e = emptySet;
            return;
        }
        this.d = treeSet;
        this.e = null;
    }

    @Override // io.netty.handler.codec.http.Cookie
    @Deprecated
    public long getMaxAge() {
        return maxAge();
    }

    @Override // io.netty.handler.codec.http.Cookie
    @Deprecated
    public int getVersion() {
        return version();
    }

    @Override // io.netty.handler.codec.http.Cookie
    @Deprecated
    public int version() {
        return this.f;
    }

    @Override // io.netty.handler.codec.http.Cookie
    @Deprecated
    public void setVersion(int i) {
        this.f = i;
    }
}
