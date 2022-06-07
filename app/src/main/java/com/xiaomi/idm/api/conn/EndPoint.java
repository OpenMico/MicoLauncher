package com.xiaomi.idm.api.conn;

import com.xiaomi.idm.api.proto.IDMServiceProto;

/* loaded from: classes3.dex */
public class EndPoint {
    public static final int MIRROR_V1_MC_VERSION = 258;
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private int f;
    private int g;

    public String getIdhash() {
        return this.a;
    }

    public void setIdhash(String str) {
        this.a = str;
    }

    public String getName() {
        return this.b;
    }

    public void setName(String str) {
        this.b = str;
    }

    public String getMac() {
        return this.c;
    }

    public void setMac(String str) {
        this.c = str;
    }

    public String getIp() {
        return this.d;
    }

    public void setIp(String str) {
        this.d = str;
    }

    public String getBdAddr() {
        return this.e;
    }

    public void setBdAddr(String str) {
        this.e = str;
    }

    public int getMcVersion() {
        return this.f;
    }

    public void setMcVersion(int i) {
        this.f = i;
    }

    public int getVerifyStatus() {
        return this.g;
    }

    public void setVerifyStatus(int i) {
        this.g = i;
    }

    public IDMServiceProto.Endpoint toProto() {
        IDMServiceProto.Endpoint.Builder newBuilder = IDMServiceProto.Endpoint.newBuilder();
        String str = this.d;
        if (str != null) {
            newBuilder.setIp(str);
        }
        String str2 = this.e;
        if (str2 != null) {
            newBuilder.setBdAddr(str2);
        }
        String str3 = this.a;
        if (str3 != null) {
            newBuilder.setIdhash(str3);
        }
        String str4 = this.c;
        if (str4 != null) {
            newBuilder.setMac(str4);
        }
        String str5 = this.b;
        if (str5 != null) {
            newBuilder.setName(str5);
        }
        newBuilder.setMcVersion(this.f);
        newBuilder.setVerifyStatus(this.g);
        return newBuilder.build();
    }

    public static EndPoint buildFromProto(IDMServiceProto.Endpoint endpoint) {
        if (endpoint == null) {
            return null;
        }
        EndPoint endPoint = new EndPoint();
        endPoint.setIp(endpoint.getIp());
        endPoint.setBdAddr(endpoint.getBdAddr());
        endPoint.setIdhash(endpoint.getIdhash());
        endPoint.setMac(endpoint.getMac());
        endPoint.setName(endpoint.getName());
        endPoint.setMcVersion(endpoint.getMcVersion());
        endPoint.setVerifyStatus(endpoint.getVerifyStatus());
        return endPoint;
    }

    public String toString() {
        return "EndPoint{ip = '" + this.d + "', bdAddress = '" + this.e + "', idhash = '" + this.a + "', mac = '" + this.c + "', name = " + this.b + "', mcVersion = " + this.f + "', verifyStatus = " + this.g + "'}";
    }
}
