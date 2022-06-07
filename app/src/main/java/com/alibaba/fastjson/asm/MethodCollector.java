package com.alibaba.fastjson.asm;

import io.netty.util.internal.StringUtil;

/* loaded from: classes.dex */
public class MethodCollector {
    protected boolean debugInfoPresent;
    private final int ignoreCount;
    private final int paramCount;
    private final StringBuilder result = new StringBuilder();
    private int currentParameter = 0;

    /* JADX INFO: Access modifiers changed from: protected */
    public MethodCollector(int i, int i2) {
        this.ignoreCount = i;
        this.paramCount = i2;
        boolean z = false;
        this.debugInfoPresent = i2 == 0 ? true : z;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void visitLocalVariable(String str, int i) {
        int i2 = this.ignoreCount;
        if (i >= i2 && i < i2 + this.paramCount) {
            if (!str.equals("arg" + this.currentParameter)) {
                this.debugInfoPresent = true;
            }
            this.result.append(StringUtil.COMMA);
            this.result.append(str);
            this.currentParameter++;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String getResult() {
        return this.result.length() != 0 ? this.result.substring(1) : "";
    }
}
