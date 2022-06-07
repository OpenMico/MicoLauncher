package com.xiaomi.miot.support;

import android.content.Context;
import android.widget.ImageView;
import androidx.annotation.UiThread;

/* loaded from: classes2.dex */
public interface MiotProvider {
    @UiThread
    void displayImage(ImageView imageView, String str);

    String getCUserId(Context context);

    HostDeviceInfo getHostDeviceInfo(Context context);

    String getNickName(Context context);

    ServiceTokenInfo getServiceToken(Context context, String str);

    String getUserId(Context context);

    @UiThread
    void onDevicesCountChanged(int i, int i2);
}
