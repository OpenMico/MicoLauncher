package com.xiaomi.micolauncher.module.initialize.steps.qrlogin;

import com.xiaomi.micolauncher.api.model.ThirdPartyResponse;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.module.initialize.steps.qrlogin.-$$Lambda$QRLoginPresenter$EqxhLcwpkRzXw44LchcrQCwD_3Y  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$QRLoginPresenter$EqxhLcwpkRzXw44LchcrQCwD_3Y implements Function {
    public static final /* synthetic */ $$Lambda$QRLoginPresenter$EqxhLcwpkRzXw44LchcrQCwD_3Y INSTANCE = new $$Lambda$QRLoginPresenter$EqxhLcwpkRzXw44LchcrQCwD_3Y();

    private /* synthetic */ $$Lambda$QRLoginPresenter$EqxhLcwpkRzXw44LchcrQCwD_3Y() {
    }

    @Override // io.reactivex.functions.Function
    public final Object apply(Object obj) {
        ThirdPartyResponse.QrLoginResponse a;
        a = QRLoginPresenter.a((ResponseBody) obj);
        return a;
    }
}
