package com.fasterxml.jackson.core.util;

import com.alibaba.fastjson.asm.Opcodes;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes.dex */
public final class InternCache extends ConcurrentHashMap<String, String> {
    public static final InternCache instance = new InternCache();
    private static final long serialVersionUID = 1;
    private final Object lock = new Object();

    private InternCache() {
        super(Opcodes.GETFIELD, 0.8f, 4);
    }

    public String intern(String str) {
        String str2 = get(str);
        if (str2 != null) {
            return str2;
        }
        if (size() >= 180) {
            synchronized (this.lock) {
                if (size() >= 180) {
                    clear();
                }
            }
        }
        String intern = str.intern();
        put(intern, intern);
        return intern;
    }
}
