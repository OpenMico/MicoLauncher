package com.xiaomi.push;

import java.util.Comparator;
import org.apache.http.NameValuePair;

/* loaded from: classes4.dex */
final class cj implements Comparator<NameValuePair> {
    /* renamed from: a */
    public int compare(NameValuePair nameValuePair, NameValuePair nameValuePair2) {
        return nameValuePair.getName().compareTo(nameValuePair2.getName());
    }
}
