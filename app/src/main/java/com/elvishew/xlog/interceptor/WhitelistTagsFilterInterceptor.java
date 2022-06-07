package com.elvishew.xlog.interceptor;

import com.elvishew.xlog.LogItem;
import java.util.Arrays;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: MicoLauncher.apk:classes.jar:com/elvishew/xlog/interceptor/WhitelistTagsFilterInterceptor.class */
public class WhitelistTagsFilterInterceptor extends AbstractFilterInterceptor {
    private Iterable<String> whitelistTags;

    public WhitelistTagsFilterInterceptor(String... whitelistTags) {
        this(Arrays.asList(whitelistTags));
    }

    public WhitelistTagsFilterInterceptor(Iterable<String> whitelistTags) {
        if (whitelistTags == null) {
            throw new NullPointerException();
        }
        this.whitelistTags = whitelistTags;
    }

    @Override // com.elvishew.xlog.interceptor.AbstractFilterInterceptor
    protected boolean reject(LogItem log) {
        if (this.whitelistTags == null) {
            return true;
        }
        for (String enabledTag : this.whitelistTags) {
            if (log.tag.equals(enabledTag)) {
                return false;
            }
        }
        return true;
    }
}
