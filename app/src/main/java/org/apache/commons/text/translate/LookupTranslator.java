package org.apache.commons.text.translate;

import java.io.IOException;
import java.io.Writer;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/* loaded from: classes5.dex */
public class LookupTranslator extends CharSequenceTranslator {
    private final Map<String, String> b;
    private final HashSet<Character> c;
    private final int d;
    private final int e;

    public LookupTranslator(Map<CharSequence, CharSequence> map) {
        if (map != null) {
            this.b = new HashMap();
            this.c = new HashSet<>();
            int i = Integer.MAX_VALUE;
            int i2 = 0;
            for (Map.Entry<CharSequence, CharSequence> entry : map.entrySet()) {
                this.b.put(entry.getKey().toString(), entry.getValue().toString());
                this.c.add(Character.valueOf(entry.getKey().charAt(0)));
                int length = entry.getKey().length();
                i = length < i ? length : i;
                if (length > i2) {
                    i2 = length;
                }
            }
            this.d = i;
            this.e = i2;
            return;
        }
        throw new InvalidParameterException("lookupMap cannot be null");
    }

    @Override // org.apache.commons.text.translate.CharSequenceTranslator
    public int translate(CharSequence charSequence, int i, Writer writer) throws IOException {
        if (!this.c.contains(Character.valueOf(charSequence.charAt(i)))) {
            return 0;
        }
        int i2 = this.e;
        if (i + i2 > charSequence.length()) {
            i2 = charSequence.length() - i;
        }
        while (i2 >= this.d) {
            String str = this.b.get(charSequence.subSequence(i, i + i2).toString());
            if (str != null) {
                writer.write(str);
                return i2;
            }
            i2--;
        }
        return 0;
    }
}
