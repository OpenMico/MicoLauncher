package com.xiaomi.micolauncher.api.converter.handler;

import com.xiaomi.micolauncher.api.ApiError;
import com.xiaomi.micolauncher.api.ErrorCode;
import com.xiaomi.micolauncher.api.MinaResponse;

/* loaded from: classes3.dex */
public class MinaHandler implements IHandler {
    @Override // com.xiaomi.micolauncher.api.converter.handler.IHandler
    public Object getResponse(Object obj) throws RuntimeException {
        if (obj instanceof MinaResponse) {
            MinaResponse minaResponse = (MinaResponse) obj;
            if (minaResponse.code > 0) {
                throw new ApiError(minaResponse.code, minaResponse.message);
            } else if (minaResponse.data != 0) {
                return minaResponse.data;
            } else {
                throw new ApiError(ErrorCode.INVALIDATE_DATA_FORMAT.getCode(), "data is null");
            }
        } else {
            throw new ApiError(ErrorCode.INVALIDATE_DATA_FORMAT.getCode(), "");
        }
    }
}
