package org.slf4j.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

/* loaded from: classes4.dex */
public class SubstituteLoggerFactory implements ILoggerFactory {
    final ConcurrentMap<String, SubstituteLogger> a = new ConcurrentHashMap();

    @Override // org.slf4j.ILoggerFactory
    public Logger getLogger(String str) {
        SubstituteLogger substituteLogger = this.a.get(str);
        if (substituteLogger != null) {
            return substituteLogger;
        }
        SubstituteLogger substituteLogger2 = new SubstituteLogger(str);
        SubstituteLogger putIfAbsent = this.a.putIfAbsent(str, substituteLogger2);
        return putIfAbsent != null ? putIfAbsent : substituteLogger2;
    }

    public List<String> getLoggerNames() {
        return new ArrayList(this.a.keySet());
    }

    public List<SubstituteLogger> getLoggers() {
        return new ArrayList(this.a.values());
    }

    public void clear() {
        this.a.clear();
    }
}
