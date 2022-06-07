package com.umeng.analytics.pro;

import com.umeng.analytics.pro.bj;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/* compiled from: TDeserializer.java */
/* loaded from: classes2.dex */
public class at {
    private final bp a;
    private final cc b;

    public at() {
        this(new bj.a());
    }

    public at(br brVar) {
        this.b = new cc();
        this.a = brVar.a(this.b);
    }

    public void a(aq aqVar, byte[] bArr) throws aw {
        try {
            this.b.a(bArr);
            aqVar.read(this.a);
        } finally {
            this.b.e();
            this.a.B();
        }
    }

    public void a(aq aqVar, String str, String str2) throws aw {
        try {
            try {
                a(aqVar, str.getBytes(str2));
            } catch (UnsupportedEncodingException unused) {
                throw new aw("JVM DOES NOT SUPPORT ENCODING: " + str2);
            }
        } finally {
            this.a.B();
        }
    }

    public void a(aq aqVar, byte[] bArr, ax axVar, ax... axVarArr) throws aw {
        try {
            try {
                if (j(bArr, axVar, axVarArr) != null) {
                    aqVar.read(this.a);
                }
            } catch (Exception e) {
                throw new aw(e);
            }
        } finally {
            this.b.e();
            this.a.B();
        }
    }

    public Boolean a(byte[] bArr, ax axVar, ax... axVarArr) throws aw {
        return (Boolean) a((byte) 2, bArr, axVar, axVarArr);
    }

    public Byte b(byte[] bArr, ax axVar, ax... axVarArr) throws aw {
        return (Byte) a((byte) 3, bArr, axVar, axVarArr);
    }

    public Double c(byte[] bArr, ax axVar, ax... axVarArr) throws aw {
        return (Double) a((byte) 4, bArr, axVar, axVarArr);
    }

    public Short d(byte[] bArr, ax axVar, ax... axVarArr) throws aw {
        return (Short) a((byte) 6, bArr, axVar, axVarArr);
    }

    public Integer e(byte[] bArr, ax axVar, ax... axVarArr) throws aw {
        return (Integer) a((byte) 8, bArr, axVar, axVarArr);
    }

    public Long f(byte[] bArr, ax axVar, ax... axVarArr) throws aw {
        return (Long) a((byte) 10, bArr, axVar, axVarArr);
    }

    public String g(byte[] bArr, ax axVar, ax... axVarArr) throws aw {
        return (String) a((byte) 11, bArr, axVar, axVarArr);
    }

    public ByteBuffer h(byte[] bArr, ax axVar, ax... axVarArr) throws aw {
        return (ByteBuffer) a((byte) 100, bArr, axVar, axVarArr);
    }

    public Short i(byte[] bArr, ax axVar, ax... axVarArr) throws aw {
        Short sh;
        try {
            try {
                if (j(bArr, axVar, axVarArr) != null) {
                    this.a.j();
                    sh = Short.valueOf(this.a.l().c);
                } else {
                    sh = null;
                }
                return sh;
            } catch (Exception e) {
                throw new aw(e);
            }
        } finally {
            this.b.e();
            this.a.B();
        }
    }

    private Object a(byte b, byte[] bArr, ax axVar, ax... axVarArr) throws aw {
        Object obj;
        try {
            try {
                bk j = j(bArr, axVar, axVarArr);
                if (j != null) {
                    if (b != 6) {
                        if (b != 8) {
                            if (b != 100) {
                                switch (b) {
                                    case 2:
                                        if (j.b == 2) {
                                            obj = Boolean.valueOf(this.a.t());
                                            break;
                                        }
                                        break;
                                    case 3:
                                        if (j.b == 3) {
                                            obj = Byte.valueOf(this.a.u());
                                            break;
                                        }
                                        break;
                                    case 4:
                                        if (j.b == 4) {
                                            obj = Double.valueOf(this.a.y());
                                            break;
                                        }
                                        break;
                                    default:
                                        switch (b) {
                                            case 10:
                                                if (j.b == 10) {
                                                    obj = Long.valueOf(this.a.x());
                                                    break;
                                                }
                                                break;
                                            case 11:
                                                if (j.b == 11) {
                                                    obj = this.a.z();
                                                    break;
                                                }
                                                break;
                                        }
                                }
                            } else if (j.b == 11) {
                                obj = this.a.A();
                            }
                        } else if (j.b == 8) {
                            obj = Integer.valueOf(this.a.w());
                        }
                    } else if (j.b == 6) {
                        obj = Short.valueOf(this.a.v());
                    }
                    return obj;
                }
                obj = null;
                return obj;
            } catch (Exception e) {
                throw new aw(e);
            }
        } finally {
            this.b.e();
            this.a.B();
        }
    }

    private bk j(byte[] bArr, ax axVar, ax... axVarArr) throws aw {
        this.b.a(bArr);
        ax[] axVarArr2 = new ax[axVarArr.length + 1];
        int i = 0;
        axVarArr2[0] = axVar;
        int i2 = 0;
        while (i2 < axVarArr.length) {
            int i3 = i2 + 1;
            axVarArr2[i3] = axVarArr[i2];
            i2 = i3;
        }
        this.a.j();
        bk bkVar = null;
        while (i < axVarArr2.length) {
            bkVar = this.a.l();
            if (bkVar.b == 0 || bkVar.c > axVarArr2[i].a()) {
                return null;
            }
            if (bkVar.c != axVarArr2[i].a()) {
                bs.a(this.a, bkVar.b);
                this.a.m();
            } else {
                i++;
                if (i < axVarArr2.length) {
                    this.a.j();
                }
            }
        }
        return bkVar;
    }

    public void a(aq aqVar, String str) throws aw {
        a(aqVar, str.getBytes());
    }
}
