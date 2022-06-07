package com.xiaomi.micolauncher.common.track;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xiaomi.ai.api.TrackLogV3;
import com.xiaomi.micolauncher.common.model.IPoolObject;
import com.xiaomi.micolauncher.common.model.ObjectPool;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;

/* loaded from: classes3.dex */
public class ExtendJsonWrapper implements IPoolObject {
    private static ObjectPool ac = new ObjectPool();
    private String A;
    private String B;
    private TrackLogV3.ScreenBoxPlayFinishType C;
    private TrackLogV3.ScreenBoxPlayEventType D;
    private TrackLogV3.ScreenBoxStatus E;
    private TrackLogV3.ScreenBoxExtendType F;
    private String G;
    private ObjectNode H;
    private String I;
    private String J;
    private String K;
    private String L;
    private String M;
    private String N;
    private String O;
    private String Q;
    private String R;
    private String S;
    private String T;
    private String U;
    private String V;
    private String W;
    private String X;
    private String Z;
    private String a;
    private Double aa;
    private String ab;
    private String ad;
    private String ae;
    private String af;
    private String ag;
    private String ah;
    private String ai;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private String g;
    private String h;
    private String i;
    private String j;
    private String n;
    private String o;
    private String p;
    private String q;
    private String r;
    private String s;
    private boolean w;
    private boolean x;
    private String y;
    private int k = -1;
    private long l = -1;
    private long m = -1;
    private long t = -1;
    private long u = -1;
    private long v = -1;
    private int z = -1;
    private int P = -1;
    private int Y = -1;

    static {
        ac.createPool();
    }

    public static ExtendJsonWrapper obtain() {
        IPoolObject object = ac.getObject();
        if (object == null) {
            return new ExtendJsonWrapper();
        }
        ExtendJsonWrapper extendJsonWrapper = (ExtendJsonWrapper) object;
        extendJsonWrapper.a();
        return extendJsonWrapper;
    }

    @Override // com.xiaomi.micolauncher.common.model.IPoolObject
    public String poolObjectKey() {
        if (this.ad == null) {
            this.ad = String.valueOf(System.currentTimeMillis());
        }
        return this.ad;
    }

    @Override // com.xiaomi.micolauncher.common.model.IPoolObject
    public void resetDataStatus() {
        a();
    }

    @Override // com.xiaomi.micolauncher.common.model.IPoolObject
    public void recycle() {
        ac.returnObject(this);
    }

    private void a() {
        this.a = null;
        this.b = null;
        this.c = null;
        this.d = null;
        this.e = null;
        this.f = null;
        this.g = null;
        this.h = null;
        this.i = null;
        this.j = null;
        this.k = -1;
        this.l = -1L;
        this.m = -1L;
        this.n = null;
        this.o = null;
        this.p = null;
        this.q = null;
        this.r = null;
        this.s = null;
        this.t = -1L;
        this.u = -1L;
        this.v = -1L;
        this.x = false;
        this.y = null;
        this.z = -1;
        this.A = null;
        this.B = null;
        this.C = null;
        this.D = null;
        this.E = null;
        this.F = null;
        this.G = null;
        this.H = null;
        this.I = null;
        this.J = null;
        this.K = null;
        this.L = null;
        this.M = null;
        this.N = null;
        this.O = null;
        this.P = -1;
        this.Q = null;
        this.R = null;
        this.S = null;
        this.T = null;
        this.U = null;
        this.V = null;
        this.W = null;
        this.X = null;
        this.Y = -1;
        this.Z = null;
        this.aa = null;
        this.ab = null;
        this.ae = null;
        this.af = null;
        this.ag = null;
        this.ai = null;
        this.ah = null;
    }

    public void setContentMode() {
        if (ChildModeManager.getManager().isChildMode()) {
            this.j = "kid";
        } else {
            this.j = "normal";
        }
    }

    public void setContentMode(String str) {
        this.j = str;
    }

    public ExtendJsonWrapper setMetaId(String str) {
        this.a = str;
        return this;
    }

    public ExtendJsonWrapper setResourceId(String str) {
        this.b = str;
        return this;
    }

    public ExtendJsonWrapper setFrom(String str) {
        this.c = str;
        return this;
    }

    public ExtendJsonWrapper setMediaType(String str) {
        this.d = str;
        return this;
    }

    public ExtendJsonWrapper setClickType(String str) {
        this.e = str;
        return this;
    }

    public ExtendJsonWrapper setPlayType(String str) {
        this.f = str;
        return this;
    }

    public ExtendJsonWrapper setCpId(String str) {
        this.g = str;
        return this;
    }

    public ExtendJsonWrapper setCpName(String str) {
        this.h = str;
        return this;
    }

    public ExtendJsonWrapper setDialogId(String str) {
        this.i = str;
        return this;
    }

    public ExtendJsonWrapper setOffset(int i) {
        this.k = i;
        return this;
    }

    public ExtendJsonWrapper setDuration(long j) {
        this.l = j;
        return this;
    }

    public ExtendJsonWrapper setPosition(long j) {
        this.m = j;
        return this;
    }

    public ExtendJsonWrapper setControlType(String str) {
        this.n = str;
        return this;
    }

    public ExtendJsonWrapper setCategory(String str) {
        this.o = str;
        return this;
    }

    public String getElementId() {
        return this.s;
    }

    public ExtendJsonWrapper setElementId(String str) {
        this.s = str;
        return this;
    }

    public ExtendJsonWrapper setDialogOrigin(String str) {
        this.p = str;
        return this;
    }

    public ExtendJsonWrapper setShortvideoType(String str) {
        this.q = str;
        return this;
    }

    public ExtendJsonWrapper setImpArea(String str) {
        this.r = str;
        return this;
    }

    public ExtendJsonWrapper setPlayStartTime(long j) {
        this.t = j;
        return this;
    }

    public ExtendJsonWrapper setPlayPosition(long j) {
        this.v = j;
        return this;
    }

    public ExtendJsonWrapper setInterrupted(boolean z) {
        this.w = z;
        this.x = true;
        return this;
    }

    public ExtendJsonWrapper setAudioId(String str) {
        this.A = str;
        return this;
    }

    public ExtendJsonWrapper setAlbumId(String str) {
        this.y = str;
        return this;
    }

    public ExtendJsonWrapper setExitType(String str) {
        this.af = str;
        return this;
    }

    public ExtendJsonWrapper setEpisodeIndex(int i) {
        this.z = i;
        return this;
    }

    public ExtendJsonWrapper setAudioSource(String str) {
        this.B = str;
        return this;
    }

    public ExtendJsonWrapper setPlayFinishType(TrackLogV3.ScreenBoxPlayFinishType screenBoxPlayFinishType) {
        this.C = screenBoxPlayFinishType;
        return this;
    }

    public ExtendJsonWrapper setPlayEventType(TrackLogV3.ScreenBoxPlayEventType screenBoxPlayEventType) {
        this.D = screenBoxPlayEventType;
        return this;
    }

    public ExtendJsonWrapper setControlStatus(TrackLogV3.ScreenBoxStatus screenBoxStatus) {
        this.E = screenBoxStatus;
        return this;
    }

    public ExtendJsonWrapper setExtendType(TrackLogV3.ScreenBoxExtendType screenBoxExtendType) {
        this.F = screenBoxExtendType;
        return this;
    }

    public ExtendJsonWrapper setPlayEndTime(long j) {
        this.u = j;
        return this;
    }

    public ExtendJsonWrapper setOriginId(String str) {
        this.G = str;
        return this;
    }

    public ExtendJsonWrapper setLogExtension(ObjectNode objectNode) {
        this.H = objectNode;
        return this;
    }

    public ExtendJsonWrapper setCpAlbumId(String str) {
        this.I = str;
        return this;
    }

    public String getCpAlbumId() {
        return this.I;
    }

    public ExtendJsonWrapper setSwitchType(String str) {
        this.J = str;
        return this;
    }

    public ExtendJsonWrapper setAudioType(String str) {
        this.K = str;
        return this;
    }

    public ExtendJsonWrapper setTraceId(String str) {
        this.L = str;
        return this;
    }

    public ExtendJsonWrapper setQueryText(String str) {
        this.M = str;
        return this;
    }

    public ExtendJsonWrapper setSendQuery(String str) {
        this.N = str;
        return this;
    }

    public ExtendJsonWrapper setQueryId(String str) {
        this.O = str;
        return this;
    }

    public ExtendJsonWrapper setCutIn(boolean z) {
        this.P = z ? 1 : 0;
        return this;
    }

    public ExtendJsonWrapper setDomain(String str) {
        this.Q = str;
        return this;
    }

    public ExtendJsonWrapper setDebugInfo(String str) {
        this.R = str;
        return this;
    }

    public ExtendJsonWrapper setAppName(String str) {
        this.S = str;
        return this;
    }

    public ExtendJsonWrapper setQuerySource(String str) {
        this.T = str;
        return this;
    }

    public ExtendJsonWrapper setEid(String str) {
        this.U = str;
        return this;
    }

    public ExtendJsonWrapper setTab(String str) {
        this.V = str;
        return this;
    }

    public ExtendJsonWrapper setSubTab(String str) {
        this.X = str;
        return this;
    }

    public ExtendJsonWrapper setTabOffset(int i) {
        this.Y = i;
        return this;
    }

    public ExtendJsonWrapper setRecallType(String str) {
        this.W = str;
        return this;
    }

    public ExtendJsonWrapper setButtonContent(String str) {
        this.Z = str;
        return this;
    }

    public ExtendJsonWrapper setPrice(double d) {
        this.aa = Double.valueOf(d);
        return this;
    }

    public ExtendJsonWrapper setProduct(String str) {
        this.ab = str;
        return this;
    }

    public ExtendJsonWrapper setModuleName(String str) {
        this.ae = str;
        return this;
    }

    public String getFloatType() {
        String str = this.ag;
        return str == null ? "" : str;
    }

    public void setFloatType(String str) {
        this.ag = str;
    }

    public String getPageType() {
        String str = this.ah;
        return str == null ? "" : str;
    }

    public void setPageType(String str) {
        this.ah = str;
    }

    public String getStatus() {
        String str = this.ai;
        return str == null ? "" : str;
    }

    public void setStatus(String str) {
        this.ai = str;
    }

    public TrackLogV3.ScreenBoxExtendJson getEventExtendJson() {
        TrackLogV3.ScreenBoxExtendJson screenBoxExtendJson = new TrackLogV3.ScreenBoxExtendJson();
        String str = this.a;
        if (str != null) {
            screenBoxExtendJson.setMetaId(str);
        }
        String str2 = this.b;
        if (str2 != null) {
            screenBoxExtendJson.setResourceId(str2);
        }
        String str3 = this.c;
        if (str3 != null) {
            screenBoxExtendJson.setFrom(str3);
        }
        String str4 = this.d;
        if (str4 != null) {
            screenBoxExtendJson.setMediaType(str4);
        }
        String str5 = this.e;
        if (str5 != null) {
            screenBoxExtendJson.setClickType(str5);
        }
        String str6 = this.f;
        if (str6 != null) {
            screenBoxExtendJson.setPlayType(str6);
        }
        long j = this.l;
        if (j >= 0) {
            screenBoxExtendJson.setDuration(j);
        }
        long j2 = this.m;
        if (j2 >= 0) {
            screenBoxExtendJson.setPosition(j2);
        }
        String str7 = this.g;
        if (str7 != null) {
            screenBoxExtendJson.setCpId(str7);
        }
        String str8 = this.h;
        if (str8 != null) {
            screenBoxExtendJson.setCpName(str8);
        }
        int i = this.k;
        if (i >= 0) {
            screenBoxExtendJson.setOffset(i);
        }
        String str9 = this.i;
        if (str9 != null) {
            screenBoxExtendJson.setDialogId(str9);
        }
        String str10 = this.j;
        if (str10 == null) {
            screenBoxExtendJson.setContentMode(str10);
        }
        String str11 = this.n;
        if (str11 != null) {
            screenBoxExtendJson.setControlType(str11);
        }
        String str12 = this.o;
        if (str12 != null) {
            screenBoxExtendJson.setCategory(str12);
        }
        String str13 = this.p;
        if (str13 != null) {
            screenBoxExtendJson.setDialogOrigin(str13);
        }
        String str14 = this.q;
        if (str14 != null) {
            screenBoxExtendJson.setShortvideoType(str14);
        }
        String str15 = this.r;
        if (str15 != null) {
            screenBoxExtendJson.setImpArea(str15);
        }
        String str16 = this.s;
        if (str16 != null) {
            screenBoxExtendJson.setElementId(str16);
        }
        long j3 = this.t;
        if (j3 >= 0) {
            screenBoxExtendJson.setPlayStartTime(j3);
        }
        long j4 = this.u;
        if (j4 >= 0) {
            screenBoxExtendJson.setPlayEndTime(j4);
        }
        long j5 = this.v;
        if (j5 >= 0) {
            screenBoxExtendJson.setPlayPosition(j5);
        }
        if (this.x) {
            screenBoxExtendJson.setIsInterrupted(this.w);
        }
        String str17 = this.y;
        if (str17 != null) {
            screenBoxExtendJson.setAlbumId(str17);
        }
        int i2 = this.z;
        if (i2 >= 0) {
            screenBoxExtendJson.setEpisodeIndex(i2);
        }
        String str18 = this.A;
        if (str18 != null) {
            screenBoxExtendJson.setAudioId(str18);
        }
        String str19 = this.B;
        if (str19 != null) {
            screenBoxExtendJson.setAudioSource(str19);
        }
        TrackLogV3.ScreenBoxPlayEventType screenBoxPlayEventType = this.D;
        if (screenBoxPlayEventType != null) {
            screenBoxExtendJson.setPlayEventType(screenBoxPlayEventType.name().toLowerCase());
        }
        TrackLogV3.ScreenBoxPlayFinishType screenBoxPlayFinishType = this.C;
        if (screenBoxPlayFinishType != null) {
            screenBoxExtendJson.setPlayFinishType(screenBoxPlayFinishType);
        }
        TrackLogV3.ScreenBoxStatus screenBoxStatus = this.E;
        if (screenBoxStatus != null) {
            screenBoxExtendJson.setStatus(screenBoxStatus);
        }
        TrackLogV3.ScreenBoxExtendType screenBoxExtendType = this.F;
        if (screenBoxExtendType != null) {
            screenBoxExtendJson.setExtendType(screenBoxExtendType);
        }
        String str20 = this.G;
        if (str20 != null) {
            screenBoxExtendJson.setOriginId(str20);
        }
        ObjectNode objectNode = this.H;
        if (objectNode != null) {
            screenBoxExtendJson.setLogExtension(objectNode);
        }
        String str21 = this.I;
        if (str21 != null) {
            screenBoxExtendJson.setCpAlbumId(str21);
        }
        String str22 = this.J;
        if (str22 != null) {
            screenBoxExtendJson.setSwitchType(str22);
        }
        String str23 = this.K;
        if (str23 != null) {
            screenBoxExtendJson.setAudioType(str23);
        }
        String str24 = this.L;
        if (str24 != null) {
            screenBoxExtendJson.setTraceId(str24);
        }
        String str25 = this.M;
        if (str25 != null) {
            screenBoxExtendJson.setQueryText(str25);
        }
        String str26 = this.N;
        if (str26 != null) {
            screenBoxExtendJson.setSendQuery(str26);
        }
        String str27 = this.O;
        if (str27 != null) {
            screenBoxExtendJson.setQueryId(str27);
        }
        int i3 = this.P;
        if (i3 > 0) {
            boolean z = true;
            if (i3 != 1) {
                z = false;
            }
            screenBoxExtendJson.setIsCutIn(z);
        }
        String str28 = this.Q;
        if (str28 != null) {
            screenBoxExtendJson.setDomain(str28);
        }
        String str29 = this.R;
        if (str29 != null) {
            screenBoxExtendJson.setDebugInfo(str29);
        }
        String str30 = this.S;
        if (str30 != null) {
            screenBoxExtendJson.setAppName(str30);
        }
        String str31 = this.T;
        if (str31 != null) {
            screenBoxExtendJson.setQuerySource(str31);
        }
        String str32 = this.U;
        if (str32 != null) {
            screenBoxExtendJson.setExpId(str32);
        }
        String str33 = this.V;
        if (str33 != null) {
            screenBoxExtendJson.setTab(str33);
        }
        int i4 = this.Y;
        if (i4 > 0) {
            screenBoxExtendJson.setTabOffset(i4);
        }
        String str34 = this.W;
        if (str34 != null) {
            screenBoxExtendJson.setRecallType(str34);
        }
        String str35 = this.Z;
        if (str35 != null) {
            screenBoxExtendJson.setButtonContent(str35);
        }
        Double d = this.aa;
        if (d != null) {
            screenBoxExtendJson.setPrice(d.doubleValue());
        }
        String str36 = this.ab;
        if (str36 != null) {
            screenBoxExtendJson.setProduct(str36);
        }
        String str37 = this.X;
        if (str37 != null) {
            screenBoxExtendJson.setSubTab(str37);
        }
        String str38 = this.ae;
        if (str38 != null) {
            screenBoxExtendJson.setModuleName(str38);
        }
        String str39 = this.af;
        if (str39 != null) {
            screenBoxExtendJson.setExitType(str39);
        }
        String str40 = this.ag;
        if (str40 != null) {
            screenBoxExtendJson.setFloatType(str40);
        }
        String str41 = this.ah;
        if (str41 != null) {
            screenBoxExtendJson.setPageType(str41);
        }
        return screenBoxExtendJson;
    }
}
