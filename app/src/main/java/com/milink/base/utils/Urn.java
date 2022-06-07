package com.milink.base.utils;

import androidx.annotation.NonNull;
import com.xiaomi.mipush.sdk.Constants;
import java.util.Objects;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes2.dex */
public class Urn implements Comparable<Urn> {
    private final String a;
    private final String b;
    private String c;

    public Urn(@NonNull String str, @NonNull String str2) {
        if (!Pattern.matches("[a-zA-Z0-9][a-zA-Z0-9\\-]{0,31}", str) || !Pattern.matches("[a-zA-Z0-9()+,\\-.:=@;$_!*'%/?#]+", str2)) {
            throw new IllegalStateException("not valid URN");
        }
        this.a = str.toLowerCase();
        this.b = str2;
    }

    public static Urn parse(@NonNull String str) {
        String trim = ((String) Objects.requireNonNull(str)).replace(StringUtils.SPACE, "").trim();
        if (trim.length() < 7) {
            throw new IllegalArgumentException("URN length less 7");
        } else if (trim.substring(0, 4).equalsIgnoreCase("urn:")) {
            String substring = trim.substring(4);
            int indexOf = substring.indexOf(58);
            if (indexOf > 0) {
                return new Urn(substring.substring(0, indexOf), substring.substring(indexOf + 1));
            }
            throw new IllegalArgumentException("URN must like urn:nid:nss");
        } else {
            throw new IllegalArgumentException("URN must begin with 'urn:'");
        }
    }

    @NonNull
    public String toString() {
        if (this.c == null) {
            this.c = "urn:" + this.a + Constants.COLON_SEPARATOR + this.b;
        }
        return this.c;
    }

    @NonNull
    public String getNID() {
        return this.a;
    }

    @NonNull
    public String getNSS() {
        return this.b;
    }

    public int compareTo(Urn urn) {
        if (urn != null) {
            int compareTo = this.a.compareTo(urn.getNID());
            return compareTo != 0 ? compareTo : this.b.compareTo(urn.getNSS());
        }
        throw new NullPointerException();
    }
}
