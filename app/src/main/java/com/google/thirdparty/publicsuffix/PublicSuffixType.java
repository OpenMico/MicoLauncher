package com.google.thirdparty.publicsuffix;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import io.netty.util.internal.StringUtil;

@Beta
@GwtCompatible
/* loaded from: classes2.dex */
public enum PublicSuffixType {
    PRIVATE(':', StringUtil.COMMA),
    REGISTRY('!', '?');
    
    private final char innerNodeCode;
    private final char leafNodeCode;

    PublicSuffixType(char c, char c2) {
        this.innerNodeCode = c;
        this.leafNodeCode = c2;
    }

    char a() {
        return this.leafNodeCode;
    }

    char b() {
        return this.innerNodeCode;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static PublicSuffixType a(char c) {
        PublicSuffixType[] values = values();
        for (PublicSuffixType publicSuffixType : values) {
            if (publicSuffixType.b() == c || publicSuffixType.a() == c) {
                return publicSuffixType;
            }
        }
        throw new IllegalArgumentException("No enum corresponding to given code: " + c);
    }
}
