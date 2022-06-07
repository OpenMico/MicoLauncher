package com.xiaomi.micolauncher.module.initialize.steps.qrlogin;

import android.graphics.Bitmap;
import com.xiaomi.micolauncher.api.model.ThirdPartyResponse;

/* loaded from: classes3.dex */
public interface IQRLoginView {
    void hideProgressbar();

    void onAuthSuccess(ThirdPartyResponse.QrLoginResultResponse qrLoginResultResponse);

    void onTimeout();

    void showProgressbar();

    void showQr(Bitmap bitmap);
}
