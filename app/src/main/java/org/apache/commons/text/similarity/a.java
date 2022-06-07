package org.apache.commons.text.similarity;

import java.util.HashMap;
import java.util.Map;

/* compiled from: Counter.java */
/* loaded from: classes5.dex */
final class a {
    public static Map<CharSequence, Integer> a(CharSequence[] charSequenceArr) {
        HashMap hashMap = new HashMap();
        for (CharSequence charSequence : charSequenceArr) {
            if (hashMap.containsKey(charSequence)) {
                hashMap.put(charSequence, Integer.valueOf(((Integer) hashMap.get(charSequence)).intValue() + 1));
            } else {
                hashMap.put(charSequence, 1);
            }
        }
        return hashMap;
    }
}
