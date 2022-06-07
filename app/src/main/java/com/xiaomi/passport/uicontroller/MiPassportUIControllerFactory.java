package com.xiaomi.passport.uicontroller;

import android.content.Context;

/* loaded from: classes4.dex */
public interface MiPassportUIControllerFactory {
    public static final MiPassportUIControllerFactory DEFAULT_IMPL = new MiPassportUIControllerFactory() { // from class: com.xiaomi.passport.uicontroller.MiPassportUIControllerFactory.1
        @Override // com.xiaomi.passport.uicontroller.MiPassportUIControllerFactory
        public MiPassportUIController newMUiController(Context context, String str, String str2) {
            return new MiPassportUIController(context, str, str2);
        }
    };

    MiPassportUIController newMUiController(Context context, String str, String str2);
}
