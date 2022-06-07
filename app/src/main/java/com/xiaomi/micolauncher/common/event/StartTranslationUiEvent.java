package com.xiaomi.micolauncher.common.event;

import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.skills.common.DirectiveModelHelper;

/* loaded from: classes3.dex */
public class StartTranslationUiEvent {
    private String a;
    private String b;
    private String c;
    private DirectiveModelHelper.DirectiveElement[] d;

    public StartTranslationUiEvent(String str, String str2, String str3) {
        this.a = str;
        this.b = str2;
        this.c = str3;
    }

    public String getSourceText() {
        return this.a;
    }

    public String getTargetText() {
        String firstItemText = ContainerUtil.hasData(this.d) ? this.d[0].getFirstItemText() : null;
        return ContainerUtil.hasData(firstItemText) ? firstItemText : this.b;
    }

    public String getAudioUrl() {
        if (ContainerUtil.hasData(this.d)) {
            return this.d[0].getFirstItemUrl();
        }
        return null;
    }

    public String getDialogId() {
        return this.c;
    }

    public boolean shouldAutoFinish() {
        return ContainerUtil.isEmpty(getAudioUrl());
    }
}
