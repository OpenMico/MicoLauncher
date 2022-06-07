package com.xiaomi.idm.api.identify;

import com.google.protobuf.InvalidProtocolBufferException;
import com.xiaomi.mi_connect_sdk.util.LogUtil;
import com.xiaomi.mi_connect_service.proto.IPCParam;

/* loaded from: classes3.dex */
public class IdentifyParam {
    String a;
    String b;
    String c;
    String d;
    String e;
    String f;
    String g;
    String h;

    public String getUserId() {
        return this.a;
    }

    public void setUserId(String str) {
        this.a = str;
    }

    public String getSid() {
        return this.b;
    }

    public void setSid(String str) {
        this.b = str;
    }

    public String getcUserId() {
        return this.c;
    }

    public void setcUserId(String str) {
        this.c = str;
    }

    public String getSsecurity() {
        return this.e;
    }

    public void setSsecurity(String str) {
        this.e = str;
    }

    public String getTimeDiff() {
        return this.f;
    }

    public void setTimeDiff(String str) {
        this.f = str;
    }

    public String getDomain() {
        return this.g;
    }

    public void setDomain(String str) {
        this.g = str;
    }

    public String getServiceToken() {
        return this.d;
    }

    public void setServiceToken(String str) {
        this.d = str;
    }

    public String getAppid() {
        return this.h;
    }

    public void setAppid(String str) {
        this.h = str;
    }

    public IPCParam.IdentifyParam toProto() {
        IPCParam.IdentifyParam.Builder newBuilder = IPCParam.IdentifyParam.newBuilder();
        String str = this.d;
        if (str != null) {
            newBuilder.setServiceToken(str);
        }
        String str2 = this.a;
        if (str2 != null) {
            newBuilder.setUserId(str2);
        }
        String str3 = this.b;
        if (str3 != null) {
            newBuilder.setSid(str3);
        }
        String str4 = this.c;
        if (str4 != null) {
            newBuilder.setCUserId(str4);
        }
        String str5 = this.e;
        if (str5 != null) {
            newBuilder.setSsecurity(str5);
        }
        String str6 = this.g;
        if (str6 != null) {
            newBuilder.setDomain(str6);
        }
        String str7 = this.f;
        if (str7 != null) {
            newBuilder.setTimeDiff(str7);
        }
        String str8 = this.h;
        if (str8 != null) {
            newBuilder.setAppId(str8);
        }
        return newBuilder.build();
    }

    public static IdentifyParam buildFromProto(byte[] bArr) {
        IPCParam.IdentifyParam identifyParam = null;
        if (bArr == null) {
            return null;
        }
        try {
            identifyParam = IPCParam.IdentifyParam.parseFrom(bArr);
        } catch (InvalidProtocolBufferException e) {
            LogUtil.e("IdentifyParam", e.getMessage(), e);
        }
        return buildFromProto(identifyParam);
    }

    public static IdentifyParam buildFromProto(IPCParam.IdentifyParam identifyParam) {
        if (identifyParam == null) {
            return null;
        }
        IdentifyParam identifyParam2 = new IdentifyParam();
        identifyParam2.d = identifyParam.getServiceToken();
        identifyParam2.f = identifyParam.getTimeDiff();
        identifyParam2.g = identifyParam.getDomain();
        identifyParam2.b = identifyParam.getSid();
        identifyParam2.a = identifyParam.getUserId();
        identifyParam2.e = identifyParam.getSsecurity();
        identifyParam2.c = identifyParam.getCUserId();
        identifyParam2.h = identifyParam.getAppId();
        return identifyParam2;
    }
}
