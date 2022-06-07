package com.xiaomi.passport.uicontroller;

import com.xiaomi.accountsdk.futureservice.ClientFuture;

/* loaded from: classes4.dex */
public abstract class UIControllerFuture<ModelDataType, UIDataType> extends ClientFuture<ModelDataType, UIDataType> {

    /* loaded from: classes4.dex */
    public interface UICallback<UIDataType> extends ClientFuture.ClientCallback<UIDataType> {
    }

    protected abstract UIDataType convertModelDataToUIData(ModelDataType modeldatatype) throws Throwable;

    /* JADX INFO: Access modifiers changed from: protected */
    public UIControllerFuture(UICallback<UIDataType> uICallback) {
        super(uICallback);
    }

    @Override // com.xiaomi.accountsdk.futureservice.ClientFuture
    protected final UIDataType convertServerDataToClientData(ModelDataType modeldatatype) throws Throwable {
        return convertModelDataToUIData(modeldatatype);
    }
}
