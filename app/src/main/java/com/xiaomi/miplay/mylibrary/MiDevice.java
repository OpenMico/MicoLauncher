package com.xiaomi.miplay.mylibrary;

import com.xiaomi.idm.api.proto.IDMServiceProto;

/* loaded from: classes4.dex */
public class MiDevice {
    private static final String a = "MiDevice";
    private String A;
    private int B;
    private String C;
    private String D;
    private String E;
    private String F;
    private boolean G;
    private IDMServiceProto.IDMService H;
    private String I;
    private boolean J;
    private String K;
    private String b;
    private String c;
    private String d;
    private int e;
    private String f;
    private String g;
    private boolean h;
    private String i;
    private String j;
    private String k;
    private String l;
    private String m;
    private String n;
    private String o;
    private String p;
    private String q;
    private String r;
    private int s;
    private float t;
    private float u;
    private int v;
    private int w;
    private int x;
    private String y;
    private int z;

    public int getDeviceConnectState() {
        return this.w;
    }

    public void setDeviceConnectState(int i) {
        this.w = i;
    }

    public int getMirrorMode() {
        return this.x;
    }

    public void setMirrorMode(int i) {
        this.x = i;
    }

    public String getMiotDid() {
        return this.m;
    }

    public void setMiotDid(String str) {
        this.m = str;
    }

    public String getMiName() {
        return this.n;
    }

    public void setMiName(String str) {
        this.n = str;
    }

    public String getMiAlias() {
        return this.o;
    }

    public void setMiAlias(String str) {
        this.o = str;
    }

    public String getHouse_Id() {
        return this.p;
    }

    public void setHouse_Id(String str) {
        this.p = str;
    }

    public String getRoom_Id() {
        return this.q;
    }

    public void setRoom_Id(String str) {
        this.q = str;
    }

    public String getRoom_Alias() {
        return this.r;
    }

    public void setRoom_Alias(String str) {
        this.r = str;
    }

    public int getStatus() {
        return this.s;
    }

    public void setStatus(int i) {
        this.s = i;
    }

    public boolean isBeReport() {
        return this.h;
    }

    public void setBeReport(boolean z) {
        this.h = z;
    }

    public String getGroupName() {
        return this.k;
    }

    public void setGroupName(String str) {
        this.k = str;
    }

    public String getBluetoothMac() {
        return this.f;
    }

    public void setBluetoothMac(String str) {
        this.f = str;
    }

    public String getAccountId() {
        return this.g;
    }

    public void setAccountId(String str) {
        this.g = str;
    }

    public String getChannel() {
        return this.i;
    }

    public void setChannel(String str) {
        this.i = str;
    }

    public String getGroupId() {
        return this.j;
    }

    public void setGroupId(String str) {
        this.j = str;
    }

    public String getRoomName() {
        return this.l;
    }

    public void setRoomName(String str) {
        this.l = str;
    }

    public String getIdhash() {
        return this.K;
    }

    public void setIdhash(String str) {
        this.K = str;
    }

    public String getName() {
        return this.b;
    }

    public void setName(String str) {
        this.b = str;
    }

    public String getIp() {
        return this.c;
    }

    public void setIp(String str) {
        this.c = str;
    }

    public String getMac() {
        return this.d;
    }

    public void setMac(String str) {
        this.d = str;
    }

    public int getPort() {
        return this.e;
    }

    public void setPort(int i) {
        this.e = i;
    }

    public float getAltitude() {
        return this.t;
    }

    public void setAltitude(float f) {
        this.t = f;
    }

    public float getAzimuth() {
        return this.u;
    }

    public void setAzimuth(float f) {
        this.u = f;
    }

    public int getDistance() {
        return this.v;
    }

    public void setDistance(int i) {
        this.v = i;
    }

    public String getUuid() {
        return this.y;
    }

    public void setUuid(String str) {
        this.y = str;
    }

    public int getDeviceType() {
        return this.z;
    }

    public void setDeviceType(int i) {
        this.z = i;
    }

    public boolean isVerifySameAccount() {
        return this.G;
    }

    public void setVerifySameAccount(boolean z) {
        this.G = z;
    }

    public IDMServiceProto.IDMService getServiceProto() {
        return this.H;
    }

    public void setServiceProto(IDMServiceProto.IDMService iDMService) {
        this.H = iDMService;
    }

    public String getNeedAblum() {
        return this.C;
    }

    public void setNeedAblum(String str) {
        this.C = str;
    }

    public String getNeedLrc() {
        return this.D;
    }

    public void setNeedLrc(String str) {
        this.D = str;
    }

    public String getNeedPos() {
        return this.E;
    }

    public void setNeedPos(String str) {
        this.E = str;
    }

    public String getCanRevCtrl() {
        return this.F;
    }

    public void setCanRevCtrl(String str) {
        this.F = str;
    }

    public boolean isHaveSupportField() {
        return this.J;
    }

    public void setHaveSupportField(boolean z) {
        this.J = z;
    }

    public boolean equals(Object obj) {
        if (obj instanceof MiDevice) {
            return ((MiDevice) obj).getMac().equals(this.d);
        }
        return false;
    }

    public String getIsMaster() {
        return this.A;
    }

    public void setIsMaster(String str) {
        this.A = str;
    }

    public int getVerifyStatus() {
        return this.B;
    }

    public void setVerifyStatus(int i) {
        this.B = i;
    }

    public String getSupportMpAbility() {
        return this.I;
    }

    public void setSupportMpAbility(String str) {
        this.I = str;
    }

    public String toString() {
        return "MiDevice{name='" + this.b + "', ip='" + this.c + "', mac='" + this.d + "', port=" + this.e + ", bluetoothMac='" + this.f + "', accountId='" + this.g + "', isBeReport=" + this.h + ", channel='" + this.i + "', groupId='" + this.j + "', groupName='" + this.k + "', roomName='" + this.l + "', miotDid='" + this.m + "', miName='" + this.n + "', miAlias='" + this.o + "', house_Id='" + this.p + "', room_Id='" + this.q + "', room_Alias='" + this.r + "', status=" + this.s + ", altitude=" + this.t + ", azimuth=" + this.u + ", distance=" + this.v + ", deviceConnectState=" + this.w + ", mirrorMode=" + this.x + ", uuid='" + this.y + "', deviceType=" + this.z + ", isMaster='" + this.A + "', verifyStatus=" + this.B + ", needAblum='" + this.C + "', needLrc='" + this.D + "', needPos='" + this.E + "', canRevCtrl='" + this.F + "', mVerifySameAccount=" + this.G + ", idhash='" + this.K + "', isSupportMpAbility='" + this.I + "', haveSupportField=" + this.J + '}';
    }

    public MiDevice() {
        this.s = 0;
        this.t = 0.0f;
        this.u = 0.0f;
        this.v = 0;
        this.w = 0;
        this.x = 0;
        this.C = "";
        this.D = "";
        this.E = "";
        this.F = "";
        this.G = false;
        this.J = false;
    }

    public MiDevice(MiDevice miDevice) {
        this.s = 0;
        this.t = 0.0f;
        this.u = 0.0f;
        this.v = 0;
        this.w = 0;
        this.x = 0;
        this.C = "";
        this.D = "";
        this.E = "";
        this.F = "";
        this.G = false;
        this.J = false;
        this.b = miDevice.b;
        this.c = miDevice.c;
        this.d = miDevice.d;
        this.e = miDevice.e;
        this.f = miDevice.f;
        this.g = miDevice.g;
        this.h = miDevice.h;
        this.i = miDevice.i;
        this.j = miDevice.j;
        this.k = miDevice.k;
        this.l = miDevice.l;
        this.m = miDevice.m;
        this.n = miDevice.n;
        this.o = miDevice.o;
        this.p = miDevice.p;
        this.q = miDevice.q;
        this.r = miDevice.r;
        this.s = miDevice.s;
        this.K = miDevice.K;
        this.t = miDevice.t;
        this.u = miDevice.u;
        this.v = miDevice.v;
        this.w = miDevice.w;
        this.x = miDevice.x;
        this.y = miDevice.y;
    }

    public void updateUuid(String str) {
        Logger.d(a, "updateUuid.", new Object[0]);
        this.y = str;
    }

    public void update(MiDevice miDevice) {
        if (miDevice != null) {
            this.b = miDevice.b;
            this.c = miDevice.c;
            this.d = miDevice.d;
            this.e = miDevice.e;
            this.f = miDevice.f;
            this.g = miDevice.g;
            this.h = miDevice.h;
            this.i = miDevice.i;
            this.j = miDevice.j;
            this.k = miDevice.k;
            this.l = miDevice.l;
            this.m = miDevice.m;
            this.n = miDevice.n;
            this.o = miDevice.o;
            this.p = miDevice.p;
            this.q = miDevice.q;
            this.r = miDevice.r;
            this.s = miDevice.s;
            this.K = miDevice.K;
            this.t = miDevice.t;
            this.u = miDevice.u;
            this.v = miDevice.v;
            this.w = miDevice.w;
            this.x = miDevice.x;
            this.y = miDevice.y;
        }
    }
}
