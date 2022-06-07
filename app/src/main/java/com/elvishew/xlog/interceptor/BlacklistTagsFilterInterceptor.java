package com.elvishew.xlog.interceptor;

import com.elvishew.xlog.LogItem;
import java.util.Arrays;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: MicoLauncher.apk:classes.jar:com/elvishew/xlog/interceptor/BlacklistTagsFilterInterceptor.class */
public class BlacklistTagsFilterInterceptor extends AbstractFilterInterceptor {
    private Iterable<String> blacklistTags;

    public BlacklistTagsFilterInterceptor(String... blacklistTags) {
        this(Arrays.asList(blacklistTags));
    }

    public BlacklistTagsFilterInterceptor(Iterable<String> blacklistTags) {
        if (blacklistTags == null) {
            throw new NullPointerException();
        }
        this.blacklistTags = blacklistTags;
    }

    @Override // com.elvishew.xlog.interceptor.AbstractFilterInterceptor
    protected boolean reject(LogItem log) {
        if (this.blacklistTags == null) {
            return false;
        }
        for (String disabledTag : this.blacklistTags) {
            if (log.tag.equals(disabledTag)) {
                return true;
            }
        }
        return false;
    }
}
