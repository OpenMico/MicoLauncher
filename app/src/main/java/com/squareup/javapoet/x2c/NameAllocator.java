package com.squareup.javapoet.x2c;

import com.milink.base.contract.MiLinkKeys;
import com.xiaomi.micolauncher.skills.music.model.lrc.LrcRow;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.lang.model.SourceVersion;

/* loaded from: classes2.dex */
public final class NameAllocator implements Cloneable {
    private final Set<String> a;
    private final Map<Object, String> b;

    public NameAllocator() {
        this(new LinkedHashSet(), new LinkedHashMap());
    }

    private NameAllocator(LinkedHashSet<String> linkedHashSet, LinkedHashMap<Object, String> linkedHashMap) {
        this.a = linkedHashSet;
        this.b = linkedHashMap;
    }

    public String newName(String str) {
        return newName(str, UUID.randomUUID().toString());
    }

    public String newName(String str, Object obj) {
        c.a(str, "suggestion", new Object[0]);
        c.a(obj, MiLinkKeys.PARAM_TAG, new Object[0]);
        String javaIdentifier = toJavaIdentifier(str);
        while (true) {
            if (!SourceVersion.isKeyword(javaIdentifier) && this.a.add(javaIdentifier)) {
                break;
            }
            javaIdentifier = javaIdentifier + "_";
        }
        String put = this.b.put(obj, javaIdentifier);
        if (put == null) {
            return javaIdentifier;
        }
        this.b.put(obj, put);
        throw new IllegalArgumentException("tag " + obj + " cannot be used for both '" + put + "' and '" + javaIdentifier + LrcRow.SINGLE_QUOTE);
    }

    public static String toJavaIdentifier(String str) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < str.length()) {
            int codePointAt = str.codePointAt(i);
            if (i == 0 && !Character.isJavaIdentifierStart(codePointAt) && Character.isJavaIdentifierPart(codePointAt)) {
                sb.append("_");
            }
            sb.appendCodePoint(Character.isJavaIdentifierPart(codePointAt) ? codePointAt : 95);
            i += Character.charCount(codePointAt);
        }
        return sb.toString();
    }

    public String get(Object obj) {
        String str = this.b.get(obj);
        if (str != null) {
            return str;
        }
        throw new IllegalArgumentException("unknown tag: " + obj);
    }

    public NameAllocator clone() {
        return new NameAllocator(new LinkedHashSet(this.a), new LinkedHashMap(this.b));
    }
}
