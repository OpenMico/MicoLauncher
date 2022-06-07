package com.xiaomi.idm.utils;

import com.google.protobuf.ByteString;
import com.xiaomi.idm.api.ResponseCode;
import com.xiaomi.idm.api.proto.IDMServiceProto;

/* loaded from: classes3.dex */
public class ResponseHelper {
    public static IDMServiceProto.IDMResponse buildResponse(int i) {
        return buildResponse(i, ResponseCode.RequestCode.getResponseMsg(i));
    }

    public static IDMServiceProto.IDMResponse buildResponse(ResponseCode.RequestCode requestCode) {
        return buildResponse(requestCode.getCode(), requestCode.getMsg());
    }

    public static IDMServiceProto.IDMResponse buildResponse(int i, String str) {
        return buildResponse(i, str, null, null);
    }

    public static IDMServiceProto.IDMResponse buildResponse(int i, IDMServiceProto.IDMRequest iDMRequest, byte[] bArr) {
        return buildResponse(i, ResponseCode.RequestCode.getResponseMsg(i), iDMRequest, bArr);
    }

    public static IDMServiceProto.IDMResponse buildResponse(ResponseCode.RequestCode requestCode, IDMServiceProto.IDMRequest iDMRequest, byte[] bArr) {
        return buildResponse(requestCode.getCode(), requestCode.getMsg(), iDMRequest, bArr);
    }

    public static IDMServiceProto.IDMResponse buildResponse(IDMServiceProto.IDMRequest iDMRequest, byte[] bArr) {
        return buildResponse(ResponseCode.RequestCode.REQUEST_SUCCEED.getCode(), null, iDMRequest, bArr);
    }

    public static IDMServiceProto.IDMResponse buildResponse(int i, String str, IDMServiceProto.IDMRequest iDMRequest, byte[] bArr) {
        if (str == null) {
            str = "";
        }
        String requestId = iDMRequest == null ? "" : iDMRequest.getRequestId();
        String uuid = iDMRequest == null ? "" : iDMRequest.getUuid();
        String clientId = iDMRequest == null ? "" : iDMRequest.getClientId();
        if (bArr == null) {
            bArr = new byte[0];
        }
        return IDMServiceProto.IDMResponse.newBuilder().setCode(i).setMsg(str).setRequestId(requestId).setUuid(uuid).setClientId(clientId).setResponse(ByteString.copyFrom(bArr)).build();
    }
}
