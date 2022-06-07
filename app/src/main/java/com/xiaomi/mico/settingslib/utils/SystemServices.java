package com.xiaomi.mico.settingslib.utils;

import android.content.Context;
import com.xiaomi.smarthome.library.common.network.Network;

/* loaded from: classes3.dex */
public class SystemServices {
    public static final LazyValue<Context, Object> WIFI_MANAGER = new LazyValue<Context, Object>() { // from class: com.xiaomi.mico.settingslib.utils.SystemServices.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public Object onInit(Context context) {
            return SystemServices.b(context, Network.NETWORK_TYPE_WIFI);
        }
    };
    public static final LazyValue<Context, Object> UI_MODE_SERVICE = new LazyValue<Context, Object>() { // from class: com.xiaomi.mico.settingslib.utils.SystemServices.2
        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public Object onInit(Context context) {
            return SystemServices.b(context, "uimode");
        }
    };
    public static final LazyValue<Context, Object> AUDIO_SERVICE = new LazyValue<Context, Object>() { // from class: com.xiaomi.mico.settingslib.utils.SystemServices.3
        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public Object onInit(Context context) {
            return SystemServices.b(context, "audio");
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public static final Object b(Context context, String str) {
        return context.getSystemService(str);
    }
}
