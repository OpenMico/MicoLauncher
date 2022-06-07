package com.google.protobuf;

import com.google.protobuf.ByteString;
import com.google.protobuf.Internal;
import com.google.protobuf.MapEntryLite;
import com.google.protobuf.WireFormat;
import com.google.protobuf.Writer;
import com.google.protobuf.c;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import sun.misc.Unsafe;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: MessageSchema.java */
/* loaded from: classes2.dex */
public final class z<T> implements am<T> {
    private static final int[] a = new int[0];
    private static final Unsafe b = at.c();
    private final int[] c;
    private final Object[] d;
    private final int e;
    private final int f;
    private final MessageLite g;
    private final boolean h;
    private final boolean i;
    private final boolean j;
    private final boolean k;
    private final int[] l;
    private final int m;
    private final int n;
    private final ab o;
    private final q p;
    private final ar<?, ?> q;
    private final j<?> r;
    private final t s;

    private static int g(int i) {
        return (i & 267386880) >>> 20;
    }

    private static boolean h(int i) {
        return (i & 268435456) != 0;
    }

    private static boolean i(int i) {
        return (i & 536870912) != 0;
    }

    private static long j(int i) {
        return i & 1048575;
    }

    private z(int[] iArr, Object[] objArr, int i, int i2, MessageLite messageLite, boolean z, boolean z2, int[] iArr2, int i3, int i4, ab abVar, q qVar, ar<?, ?> arVar, j<?> jVar, t tVar) {
        this.c = iArr;
        this.d = objArr;
        this.e = i;
        this.f = i2;
        this.i = messageLite instanceof GeneratedMessageLite;
        this.j = z;
        this.h = jVar != null && jVar.a(messageLite);
        this.k = z2;
        this.l = iArr2;
        this.m = i3;
        this.n = i4;
        this.o = abVar;
        this.p = qVar;
        this.q = arVar;
        this.r = jVar;
        this.g = messageLite;
        this.s = tVar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> z<T> a(Class<T> cls, w wVar, ab abVar, q qVar, ar<?, ?> arVar, j<?> jVar, t tVar) {
        if (wVar instanceof aj) {
            return a((aj) wVar, abVar, qVar, arVar, jVar, tVar);
        }
        return a((StructuralMessageInfo) wVar, abVar, qVar, arVar, jVar, tVar);
    }

    static <T> z<T> a(aj ajVar, ab abVar, q qVar, ar<?, ?> arVar, j<?> jVar, t tVar) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int[] iArr;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        Field field;
        char charAt;
        Field field2;
        Field field3;
        int i15;
        char charAt2;
        int i16;
        char charAt3;
        int i17;
        char charAt4;
        int i18;
        int i19;
        int i20;
        int i21;
        int i22;
        int i23;
        int i24;
        char charAt5;
        int i25;
        char charAt6;
        int i26;
        char charAt7;
        char charAt8;
        char charAt9;
        char charAt10;
        char charAt11;
        char charAt12;
        char charAt13;
        char charAt14;
        boolean z = ajVar.a() == ProtoSyntax.PROTO3;
        String d = ajVar.d();
        int length = d.length();
        int charAt15 = d.charAt(0);
        char c = 55296;
        if (charAt15 >= 55296) {
            int i27 = charAt15 & 8191;
            int i28 = 1;
            int i29 = 13;
            while (true) {
                i = i28 + 1;
                charAt14 = d.charAt(i28);
                if (charAt14 < 55296) {
                    break;
                }
                i27 |= (charAt14 & 8191) << i29;
                i29 += 13;
                i28 = i;
            }
            charAt15 = (charAt14 << i29) | i27;
        } else {
            i = 1;
        }
        int i30 = i + 1;
        int charAt16 = d.charAt(i);
        if (charAt16 >= 55296) {
            int i31 = charAt16 & 8191;
            int i32 = 13;
            while (true) {
                i2 = i30 + 1;
                charAt13 = d.charAt(i30);
                if (charAt13 < 55296) {
                    break;
                }
                i31 |= (charAt13 & 8191) << i32;
                i32 += 13;
                i30 = i2;
            }
            charAt16 = i31 | (charAt13 << i32);
        } else {
            i2 = i30;
        }
        if (charAt16 == 0) {
            i8 = 0;
            i7 = 0;
            i6 = 0;
            i5 = 0;
            i4 = 0;
            i3 = 0;
            iArr = a;
            i9 = 0;
        } else {
            int i33 = i2 + 1;
            char charAt17 = d.charAt(i2);
            if (charAt17 >= 55296) {
                int i34 = charAt17 & 8191;
                int i35 = 13;
                while (true) {
                    i18 = i33 + 1;
                    charAt12 = d.charAt(i33);
                    if (charAt12 < 55296) {
                        break;
                    }
                    i34 |= (charAt12 & 8191) << i35;
                    i35 += 13;
                    i33 = i18;
                }
                i19 = (charAt12 << i35) | i34;
            } else {
                i18 = i33;
                i19 = charAt17;
            }
            int i36 = i18 + 1;
            int charAt18 = d.charAt(i18);
            if (charAt18 >= 55296) {
                int i37 = charAt18 & 8191;
                int i38 = 13;
                while (true) {
                    i20 = i36 + 1;
                    charAt11 = d.charAt(i36);
                    if (charAt11 < 55296) {
                        break;
                    }
                    i37 |= (charAt11 & 8191) << i38;
                    i38 += 13;
                    i36 = i20;
                }
                charAt18 = i37 | (charAt11 << i38);
            } else {
                i20 = i36;
            }
            int i39 = i20 + 1;
            i9 = d.charAt(i20);
            if (i9 >= 55296) {
                int i40 = i9 & 8191;
                int i41 = 13;
                while (true) {
                    i21 = i39 + 1;
                    charAt10 = d.charAt(i39);
                    if (charAt10 < 55296) {
                        break;
                    }
                    i40 |= (charAt10 & 8191) << i41;
                    i41 += 13;
                    i39 = i21;
                }
                i9 = (charAt10 << i41) | i40;
            } else {
                i21 = i39;
            }
            int i42 = i21 + 1;
            i8 = d.charAt(i21);
            if (i8 >= 55296) {
                int i43 = i8 & 8191;
                int i44 = 13;
                while (true) {
                    i22 = i42 + 1;
                    charAt9 = d.charAt(i42);
                    if (charAt9 < 55296) {
                        break;
                    }
                    i43 |= (charAt9 & 8191) << i44;
                    i44 += 13;
                    i42 = i22;
                }
                i8 = (charAt9 << i44) | i43;
            } else {
                i22 = i42;
            }
            int i45 = i22 + 1;
            i6 = d.charAt(i22);
            if (i6 >= 55296) {
                int i46 = i6 & 8191;
                int i47 = 13;
                while (true) {
                    i23 = i45 + 1;
                    charAt8 = d.charAt(i45);
                    if (charAt8 < 55296) {
                        break;
                    }
                    i46 |= (charAt8 & 8191) << i47;
                    i47 += 13;
                    i45 = i23;
                }
                i6 = (charAt8 << i47) | i46;
            } else {
                i23 = i45;
            }
            int i48 = i23 + 1;
            i5 = d.charAt(i23);
            if (i5 >= 55296) {
                int i49 = i5 & 8191;
                int i50 = 13;
                while (true) {
                    i26 = i48 + 1;
                    charAt7 = d.charAt(i48);
                    if (charAt7 < 55296) {
                        break;
                    }
                    i49 |= (charAt7 & 8191) << i50;
                    i50 += 13;
                    i48 = i26;
                }
                i5 = (charAt7 << i50) | i49;
                i48 = i26;
            }
            int i51 = i48 + 1;
            int charAt19 = d.charAt(i48);
            if (charAt19 >= 55296) {
                int i52 = charAt19 & 8191;
                int i53 = 13;
                while (true) {
                    i25 = i51 + 1;
                    charAt6 = d.charAt(i51);
                    if (charAt6 < 55296) {
                        break;
                    }
                    i52 |= (charAt6 & 8191) << i53;
                    i53 += 13;
                    i51 = i25;
                }
                charAt19 = i52 | (charAt6 << i53);
                i51 = i25;
            }
            i2 = i51 + 1;
            i4 = d.charAt(i51);
            if (i4 >= 55296) {
                int i54 = i4 & 8191;
                int i55 = 13;
                int i56 = i2;
                while (true) {
                    i24 = i56 + 1;
                    charAt5 = d.charAt(i56);
                    if (charAt5 < 55296) {
                        break;
                    }
                    i54 |= (charAt5 & 8191) << i55;
                    i55 += 13;
                    i56 = i24;
                }
                i4 = i54 | (charAt5 << i55);
                i2 = i24;
            }
            i7 = (i19 * 2) + charAt18;
            i3 = i19;
            iArr = new int[i4 + i5 + charAt19];
        }
        Unsafe unsafe = b;
        Object[] e = ajVar.e();
        Class<?> cls = ajVar.c().getClass();
        int[] iArr2 = new int[i6 * 3];
        Object[] objArr = new Object[i6 * 2];
        int i57 = i5 + i4;
        int i58 = i57;
        int i59 = i4;
        int i60 = 0;
        int i61 = 0;
        while (i2 < length) {
            int i62 = i2 + 1;
            int charAt20 = d.charAt(i2);
            if (charAt20 >= c) {
                int i63 = charAt20 & 8191;
                int i64 = 13;
                int i65 = i62;
                while (true) {
                    i17 = i65 + 1;
                    charAt4 = d.charAt(i65);
                    if (charAt4 < c) {
                        break;
                    }
                    i63 |= (charAt4 & 8191) << i64;
                    i64 += 13;
                    i65 = i17;
                }
                charAt20 = i63 | (charAt4 << i64);
                i10 = i17;
            } else {
                i10 = i62;
            }
            int i66 = i10 + 1;
            int charAt21 = d.charAt(i10);
            char c2 = 55296;
            if (charAt21 >= 55296) {
                int i67 = charAt21 & 8191;
                int i68 = 13;
                int i69 = i66;
                while (true) {
                    i16 = i69 + 1;
                    charAt3 = d.charAt(i69);
                    if (charAt3 < c2) {
                        break;
                    }
                    i67 |= (charAt3 & 8191) << i68;
                    i68 += 13;
                    i69 = i16;
                    c2 = 55296;
                }
                charAt21 = i67 | (charAt3 << i68);
                i11 = i16;
            } else {
                i11 = i66;
            }
            int i70 = charAt21 & 255;
            if ((charAt21 & 1024) != 0) {
                i60++;
                iArr[i60] = i61;
            }
            if (i70 >= 51) {
                i2 = i11 + 1;
                int charAt22 = d.charAt(i11);
                char c3 = 55296;
                if (charAt22 >= 55296) {
                    int i71 = charAt22 & 8191;
                    int i72 = 13;
                    while (true) {
                        i15 = i2 + 1;
                        charAt2 = d.charAt(i2);
                        if (charAt2 < c3) {
                            break;
                        }
                        i71 |= (charAt2 & 8191) << i72;
                        i72 += 13;
                        i2 = i15;
                        c3 = 55296;
                    }
                    charAt22 = i71 | (charAt2 << i72);
                    i2 = i15;
                }
                int i73 = i70 - 51;
                if (i73 == 9 || i73 == 17) {
                    i7++;
                    objArr[((i61 / 3) * 2) + 1] = e[i7];
                } else if (i73 == 12 && (charAt15 & 1) == 1) {
                    i7++;
                    objArr[((i61 / 3) * 2) + 1] = e[i7];
                } else {
                    i7 = i7;
                }
                int i74 = charAt22 * 2;
                Object obj = e[i74];
                if (obj instanceof Field) {
                    field2 = (Field) obj;
                } else {
                    field2 = a(cls, (String) obj);
                    e[i74] = field2;
                }
                i12 = (int) unsafe.objectFieldOffset(field2);
                int i75 = i74 + 1;
                Object obj2 = e[i75];
                if (obj2 instanceof Field) {
                    field3 = (Field) obj2;
                } else {
                    field3 = a(cls, (String) obj2);
                    e[i75] = field3;
                }
                i13 = (int) unsafe.objectFieldOffset(field3);
                d = d;
                charAt15 = charAt15;
                i8 = i8;
                i14 = 0;
                cls = cls;
            } else {
                i7++;
                Field a2 = a(cls, (String) e[i7]);
                if (i70 == 9 || i70 == 17) {
                    i8 = i8;
                    objArr[((i61 / 3) * 2) + 1] = a2.getType();
                } else if (i70 == 27 || i70 == 49) {
                    i8 = i8;
                    i7++;
                    objArr[((i61 / 3) * 2) + 1] = e[i7];
                } else if (i70 == 12 || i70 == 30 || i70 == 44) {
                    i8 = i8;
                    if ((charAt15 & 1) == 1) {
                        i7++;
                        objArr[((i61 / 3) * 2) + 1] = e[i7];
                    }
                } else if (i70 == 50) {
                    int i76 = i59 + 1;
                    iArr[i59] = i61;
                    int i77 = (i61 / 3) * 2;
                    int i78 = i7 + 1;
                    objArr[i77] = e[i7];
                    if ((charAt21 & 2048) != 0) {
                        i7 = i78 + 1;
                        objArr[i77 + 1] = e[i78];
                        i8 = i8;
                        i59 = i76;
                    } else {
                        i59 = i76;
                        i7 = i78;
                        i8 = i8;
                    }
                } else {
                    i8 = i8;
                }
                i12 = (int) unsafe.objectFieldOffset(a2);
                if ((charAt15 & 1) != 1 || i70 > 17) {
                    d = d;
                    charAt15 = charAt15;
                    cls = cls;
                    i2 = i11;
                    i14 = 0;
                    i13 = 0;
                } else {
                    int i79 = i11 + 1;
                    int charAt23 = d.charAt(i11);
                    if (charAt23 >= 55296) {
                        int i80 = charAt23 & 8191;
                        int i81 = 13;
                        while (true) {
                            i2 = i79 + 1;
                            charAt = d.charAt(i79);
                            if (charAt < 55296) {
                                break;
                            }
                            i80 |= (charAt & 8191) << i81;
                            i81 += 13;
                            i79 = i2;
                        }
                        charAt23 = i80 | (charAt << i81);
                    } else {
                        i2 = i79;
                    }
                    int i82 = (i3 * 2) + (charAt23 / 32);
                    Object obj3 = e[i82];
                    d = d;
                    if (obj3 instanceof Field) {
                        field = (Field) obj3;
                        charAt15 = charAt15;
                        cls = cls;
                    } else {
                        field = a(cls, (String) obj3);
                        e[i82] = field;
                        charAt15 = charAt15;
                        cls = cls;
                    }
                    i13 = (int) unsafe.objectFieldOffset(field);
                    i14 = charAt23 % 32;
                }
                if (i70 >= 18 && i70 <= 49) {
                    i58++;
                    iArr[i58] = i12;
                }
            }
            int i83 = i61 + 1;
            iArr2[i61] = charAt20;
            int i84 = i83 + 1;
            iArr2[i83] = ((charAt21 & 256) != 0 ? 268435456 : 0) | ((charAt21 & 512) != 0 ? 536870912 : 0) | (i70 << 20) | i12;
            i61 = i84 + 1;
            iArr2[i84] = (i14 << 20) | i13;
            i57 = i57;
            length = length;
            i4 = i4;
            z = z;
            c = 55296;
        }
        return new z<>(iArr2, objArr, i9, i8, ajVar.c(), z, false, iArr, i4, i57, abVar, qVar, arVar, jVar, tVar);
    }

    private static Field a(Class<?> cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (NoSuchFieldException unused) {
            Field[] declaredFields = cls.getDeclaredFields();
            for (Field field : declaredFields) {
                if (str.equals(field.getName())) {
                    return field;
                }
            }
            throw new RuntimeException("Field " + str + " for " + cls.getName() + " not found. Known fields are " + Arrays.toString(declaredFields));
        }
    }

    static <T> z<T> a(StructuralMessageInfo structuralMessageInfo, ab abVar, q qVar, ar<?, ?> arVar, j<?> jVar, t tVar) {
        int i;
        int i2;
        int i3;
        boolean z = structuralMessageInfo.a() == ProtoSyntax.PROTO3;
        FieldInfo[] e = structuralMessageInfo.e();
        if (e.length == 0) {
            i2 = 0;
            i = 0;
        } else {
            i2 = e[0].a();
            i = e[e.length - 1].a();
        }
        int length = e.length;
        int[] iArr = new int[length * 3];
        Object[] objArr = new Object[length * 2];
        int i4 = 0;
        int i5 = 0;
        for (FieldInfo fieldInfo : e) {
            if (fieldInfo.c() == FieldType.MAP) {
                i4++;
            } else if (fieldInfo.c().id() >= 18 && fieldInfo.c().id() <= 49) {
                i5++;
            }
        }
        int[] iArr2 = null;
        int[] iArr3 = i4 > 0 ? new int[i4] : null;
        if (i5 > 0) {
            iArr2 = new int[i5];
        }
        int[] d = structuralMessageInfo.d();
        if (d == null) {
            d = a;
        }
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        int i9 = 0;
        int i10 = 0;
        while (i6 < e.length) {
            FieldInfo fieldInfo2 = e[i6];
            int a2 = fieldInfo2.a();
            a(fieldInfo2, iArr, i7, z, objArr);
            if (i8 < d.length && d[i8] == a2) {
                i8++;
                d[i8] = i7;
            }
            if (fieldInfo2.c() == FieldType.MAP) {
                i9++;
                iArr3[i9] = i7;
                i3 = i7;
            } else if (fieldInfo2.c().id() < 18 || fieldInfo2.c().id() > 49) {
                i3 = i7;
            } else {
                i10++;
                i3 = i7;
                iArr2[i10] = (int) at.a(fieldInfo2.b());
            }
            i6++;
            i7 = i3 + 3;
        }
        if (iArr3 == null) {
            iArr3 = a;
        }
        if (iArr2 == null) {
            iArr2 = a;
        }
        int[] iArr4 = new int[d.length + iArr3.length + iArr2.length];
        System.arraycopy(d, 0, iArr4, 0, d.length);
        System.arraycopy(iArr3, 0, iArr4, d.length, iArr3.length);
        System.arraycopy(iArr2, 0, iArr4, d.length + iArr3.length, iArr2.length);
        return new z<>(iArr, objArr, i2, i, structuralMessageInfo.c(), z, true, iArr4, d.length, d.length + iArr3.length, abVar, qVar, arVar, jVar, tVar);
    }

    private static void a(FieldInfo fieldInfo, int[] iArr, int i, boolean z, Object[] objArr) {
        int i2;
        int i3;
        int i4;
        int i5;
        af d = fieldInfo.d();
        int i6 = 0;
        if (d != null) {
            i3 = fieldInfo.c().id() + 51;
            i4 = (int) at.a(d.b());
            i2 = (int) at.a(d.a());
            i5 = 0;
        } else {
            FieldType c = fieldInfo.c();
            i4 = (int) at.a(fieldInfo.b());
            i3 = c.id();
            if (!z && !c.isList() && !c.isMap()) {
                i2 = (int) at.a(fieldInfo.f());
                i5 = Integer.numberOfTrailingZeros(fieldInfo.h());
            } else if (fieldInfo.k() == null) {
                i2 = 0;
                i5 = 0;
            } else {
                i2 = (int) at.a(fieldInfo.k());
                i5 = 0;
            }
        }
        iArr[i] = fieldInfo.a();
        int i7 = i + 1;
        int i8 = fieldInfo.j() ? 536870912 : 0;
        if (fieldInfo.i()) {
            i6 = 268435456;
        }
        iArr[i7] = i6 | i8 | (i3 << 20) | i4;
        iArr[i + 2] = i2 | (i5 << 20);
        Class<?> l = fieldInfo.l();
        if (fieldInfo.g() != null) {
            int i9 = (i / 3) * 2;
            objArr[i9] = fieldInfo.g();
            if (l != null) {
                objArr[i9 + 1] = l;
            } else if (fieldInfo.e() != null) {
                objArr[i9 + 1] = fieldInfo.e();
            }
        } else if (l != null) {
            objArr[((i / 3) * 2) + 1] = l;
        } else if (fieldInfo.e() != null) {
            objArr[((i / 3) * 2) + 1] = fieldInfo.e();
        }
    }

    @Override // com.google.protobuf.am
    public T a() {
        return (T) this.o.a(this.g);
    }

    @Override // com.google.protobuf.am
    public boolean a(T t, T t2) {
        int length = this.c.length;
        for (int i = 0; i < length; i += 3) {
            if (!a(t, t2, i)) {
                return false;
            }
        }
        if (!this.q.b(t).equals(this.q.b(t2))) {
            return false;
        }
        if (this.h) {
            return this.r.a(t).equals(this.r.a(t2));
        }
        return true;
    }

    private boolean a(T t, T t2, int i) {
        int e = e(i);
        long j = j(e);
        switch (g(e)) {
            case 0:
                return e(t, t2, i) && Double.doubleToLongBits(at.e(t, j)) == Double.doubleToLongBits(at.e(t2, j));
            case 1:
                return e(t, t2, i) && Float.floatToIntBits(at.d(t, j)) == Float.floatToIntBits(at.d(t2, j));
            case 2:
                return e(t, t2, i) && at.b(t, j) == at.b(t2, j);
            case 3:
                return e(t, t2, i) && at.b(t, j) == at.b(t2, j);
            case 4:
                return e(t, t2, i) && at.a(t, j) == at.a(t2, j);
            case 5:
                return e(t, t2, i) && at.b(t, j) == at.b(t2, j);
            case 6:
                return e(t, t2, i) && at.a(t, j) == at.a(t2, j);
            case 7:
                return e(t, t2, i) && at.c(t, j) == at.c(t2, j);
            case 8:
                return e(t, t2, i) && ao.a(at.f(t, j), at.f(t2, j));
            case 9:
                return e(t, t2, i) && ao.a(at.f(t, j), at.f(t2, j));
            case 10:
                return e(t, t2, i) && ao.a(at.f(t, j), at.f(t2, j));
            case 11:
                return e(t, t2, i) && at.a(t, j) == at.a(t2, j);
            case 12:
                return e(t, t2, i) && at.a(t, j) == at.a(t2, j);
            case 13:
                return e(t, t2, i) && at.a(t, j) == at.a(t2, j);
            case 14:
                return e(t, t2, i) && at.b(t, j) == at.b(t2, j);
            case 15:
                return e(t, t2, i) && at.a(t, j) == at.a(t2, j);
            case 16:
                return e(t, t2, i) && at.b(t, j) == at.b(t2, j);
            case 17:
                return e(t, t2, i) && ao.a(at.f(t, j), at.f(t2, j));
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
                return ao.a(at.f(t, j), at.f(t2, j));
            case 50:
                return ao.a(at.f(t, j), at.f(t2, j));
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
                return f(t, t2, i) && ao.a(at.f(t, j), at.f(t2, j));
            default:
                return true;
        }
    }

    @Override // com.google.protobuf.am
    public int a(T t) {
        int length = this.c.length;
        int i = 0;
        for (int i2 = 0; i2 < length; i2 += 3) {
            int e = e(i2);
            int d = d(i2);
            long j = j(e);
            int i3 = 37;
            switch (g(e)) {
                case 0:
                    i = (i * 53) + Internal.hashLong(Double.doubleToLongBits(at.e(t, j)));
                    break;
                case 1:
                    i = (i * 53) + Float.floatToIntBits(at.d(t, j));
                    break;
                case 2:
                    i = (i * 53) + Internal.hashLong(at.b(t, j));
                    break;
                case 3:
                    i = (i * 53) + Internal.hashLong(at.b(t, j));
                    break;
                case 4:
                    i = (i * 53) + at.a(t, j);
                    break;
                case 5:
                    i = (i * 53) + Internal.hashLong(at.b(t, j));
                    break;
                case 6:
                    i = (i * 53) + at.a(t, j);
                    break;
                case 7:
                    i = (i * 53) + Internal.hashBoolean(at.c(t, j));
                    break;
                case 8:
                    i = (i * 53) + ((String) at.f(t, j)).hashCode();
                    break;
                case 9:
                    Object f = at.f(t, j);
                    if (f != null) {
                        i3 = f.hashCode();
                    }
                    i = (i * 53) + i3;
                    break;
                case 10:
                    i = (i * 53) + at.f(t, j).hashCode();
                    break;
                case 11:
                    i = (i * 53) + at.a(t, j);
                    break;
                case 12:
                    i = (i * 53) + at.a(t, j);
                    break;
                case 13:
                    i = (i * 53) + at.a(t, j);
                    break;
                case 14:
                    i = (i * 53) + Internal.hashLong(at.b(t, j));
                    break;
                case 15:
                    i = (i * 53) + at.a(t, j);
                    break;
                case 16:
                    i = (i * 53) + Internal.hashLong(at.b(t, j));
                    break;
                case 17:
                    Object f2 = at.f(t, j);
                    if (f2 != null) {
                        i3 = f2.hashCode();
                    }
                    i = (i * 53) + i3;
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    i = (i * 53) + at.f(t, j).hashCode();
                    break;
                case 50:
                    i = (i * 53) + at.f(t, j).hashCode();
                    break;
                case 51:
                    if (c((z<T>) t, d, i2)) {
                        i = (i * 53) + Internal.hashLong(Double.doubleToLongBits(g(t, j)));
                        break;
                    } else {
                        break;
                    }
                case 52:
                    if (c((z<T>) t, d, i2)) {
                        i = (i * 53) + Float.floatToIntBits(h(t, j));
                        break;
                    } else {
                        break;
                    }
                case 53:
                    if (c((z<T>) t, d, i2)) {
                        i = (i * 53) + Internal.hashLong(j(t, j));
                        break;
                    } else {
                        break;
                    }
                case 54:
                    if (c((z<T>) t, d, i2)) {
                        i = (i * 53) + Internal.hashLong(j(t, j));
                        break;
                    } else {
                        break;
                    }
                case 55:
                    if (c((z<T>) t, d, i2)) {
                        i = (i * 53) + i(t, j);
                        break;
                    } else {
                        break;
                    }
                case 56:
                    if (c((z<T>) t, d, i2)) {
                        i = (i * 53) + Internal.hashLong(j(t, j));
                        break;
                    } else {
                        break;
                    }
                case 57:
                    if (c((z<T>) t, d, i2)) {
                        i = (i * 53) + i(t, j);
                        break;
                    } else {
                        break;
                    }
                case 58:
                    if (c((z<T>) t, d, i2)) {
                        i = (i * 53) + Internal.hashBoolean(k(t, j));
                        break;
                    } else {
                        break;
                    }
                case 59:
                    if (c((z<T>) t, d, i2)) {
                        i = (i * 53) + ((String) at.f(t, j)).hashCode();
                        break;
                    } else {
                        break;
                    }
                case 60:
                    if (c((z<T>) t, d, i2)) {
                        i = (i * 53) + at.f(t, j).hashCode();
                        break;
                    } else {
                        break;
                    }
                case 61:
                    if (c((z<T>) t, d, i2)) {
                        i = (i * 53) + at.f(t, j).hashCode();
                        break;
                    } else {
                        break;
                    }
                case 62:
                    if (c((z<T>) t, d, i2)) {
                        i = (i * 53) + i(t, j);
                        break;
                    } else {
                        break;
                    }
                case 63:
                    if (c((z<T>) t, d, i2)) {
                        i = (i * 53) + i(t, j);
                        break;
                    } else {
                        break;
                    }
                case 64:
                    if (c((z<T>) t, d, i2)) {
                        i = (i * 53) + i(t, j);
                        break;
                    } else {
                        break;
                    }
                case 65:
                    if (c((z<T>) t, d, i2)) {
                        i = (i * 53) + Internal.hashLong(j(t, j));
                        break;
                    } else {
                        break;
                    }
                case 66:
                    if (c((z<T>) t, d, i2)) {
                        i = (i * 53) + i(t, j);
                        break;
                    } else {
                        break;
                    }
                case 67:
                    if (c((z<T>) t, d, i2)) {
                        i = (i * 53) + Internal.hashLong(j(t, j));
                        break;
                    } else {
                        break;
                    }
                case 68:
                    if (c((z<T>) t, d, i2)) {
                        i = (i * 53) + at.f(t, j).hashCode();
                        break;
                    } else {
                        break;
                    }
            }
        }
        int hashCode = (i * 53) + this.q.b(t).hashCode();
        return this.h ? (hashCode * 53) + this.r.a(t).hashCode() : hashCode;
    }

    @Override // com.google.protobuf.am
    public void b(T t, T t2) {
        if (t2 != null) {
            for (int i = 0; i < this.c.length; i += 3) {
                b(t, t2, i);
            }
            ao.a(this.q, t, t2);
            if (this.h) {
                ao.a(this.r, t, t2);
                return;
            }
            return;
        }
        throw new NullPointerException();
    }

    private void b(T t, T t2, int i) {
        int e = e(i);
        long j = j(e);
        int d = d(i);
        switch (g(e)) {
            case 0:
                if (a((z<T>) t2, i)) {
                    at.a(t, j, at.e(t2, j));
                    b((z<T>) t, i);
                    return;
                }
                return;
            case 1:
                if (a((z<T>) t2, i)) {
                    at.a((Object) t, j, at.d(t2, j));
                    b((z<T>) t, i);
                    return;
                }
                return;
            case 2:
                if (a((z<T>) t2, i)) {
                    at.a((Object) t, j, at.b(t2, j));
                    b((z<T>) t, i);
                    return;
                }
                return;
            case 3:
                if (a((z<T>) t2, i)) {
                    at.a((Object) t, j, at.b(t2, j));
                    b((z<T>) t, i);
                    return;
                }
                return;
            case 4:
                if (a((z<T>) t2, i)) {
                    at.a((Object) t, j, at.a(t2, j));
                    b((z<T>) t, i);
                    return;
                }
                return;
            case 5:
                if (a((z<T>) t2, i)) {
                    at.a((Object) t, j, at.b(t2, j));
                    b((z<T>) t, i);
                    return;
                }
                return;
            case 6:
                if (a((z<T>) t2, i)) {
                    at.a((Object) t, j, at.a(t2, j));
                    b((z<T>) t, i);
                    return;
                }
                return;
            case 7:
                if (a((z<T>) t2, i)) {
                    at.a(t, j, at.c(t2, j));
                    b((z<T>) t, i);
                    return;
                }
                return;
            case 8:
                if (a((z<T>) t2, i)) {
                    at.a(t, j, at.f(t2, j));
                    b((z<T>) t, i);
                    return;
                }
                return;
            case 9:
                c(t, t2, i);
                return;
            case 10:
                if (a((z<T>) t2, i)) {
                    at.a(t, j, at.f(t2, j));
                    b((z<T>) t, i);
                    return;
                }
                return;
            case 11:
                if (a((z<T>) t2, i)) {
                    at.a((Object) t, j, at.a(t2, j));
                    b((z<T>) t, i);
                    return;
                }
                return;
            case 12:
                if (a((z<T>) t2, i)) {
                    at.a((Object) t, j, at.a(t2, j));
                    b((z<T>) t, i);
                    return;
                }
                return;
            case 13:
                if (a((z<T>) t2, i)) {
                    at.a((Object) t, j, at.a(t2, j));
                    b((z<T>) t, i);
                    return;
                }
                return;
            case 14:
                if (a((z<T>) t2, i)) {
                    at.a((Object) t, j, at.b(t2, j));
                    b((z<T>) t, i);
                    return;
                }
                return;
            case 15:
                if (a((z<T>) t2, i)) {
                    at.a((Object) t, j, at.a(t2, j));
                    b((z<T>) t, i);
                    return;
                }
                return;
            case 16:
                if (a((z<T>) t2, i)) {
                    at.a((Object) t, j, at.b(t2, j));
                    b((z<T>) t, i);
                    return;
                }
                return;
            case 17:
                c(t, t2, i);
                return;
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
                this.p.a(t, t2, j);
                return;
            case 50:
                ao.a(this.s, t, t2, j);
                return;
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
                if (c((z<T>) t2, d, i)) {
                    at.a(t, j, at.f(t2, j));
                    d((z<T>) t, d, i);
                    return;
                }
                return;
            case 60:
                d(t, t2, i);
                return;
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
                if (c((z<T>) t2, d, i)) {
                    at.a(t, j, at.f(t2, j));
                    d((z<T>) t, d, i);
                    return;
                }
                return;
            case 68:
                d(t, t2, i);
                return;
            default:
                return;
        }
    }

    private void c(T t, T t2, int i) {
        long j = j(e(i));
        if (a((z<T>) t2, i)) {
            Object f = at.f(t, j);
            Object f2 = at.f(t2, j);
            if (f != null && f2 != null) {
                at.a(t, j, Internal.a(f, f2));
                b((z<T>) t, i);
            } else if (f2 != null) {
                at.a(t, j, f2);
                b((z<T>) t, i);
            }
        }
    }

    private void d(T t, T t2, int i) {
        int e = e(i);
        int d = d(i);
        long j = j(e);
        if (c((z<T>) t2, d, i)) {
            Object f = at.f(t, j);
            Object f2 = at.f(t2, j);
            if (f != null && f2 != null) {
                at.a(t, j, Internal.a(f, f2));
                d((z<T>) t, d, i);
            } else if (f2 != null) {
                at.a(t, j, f2);
                d((z<T>) t, d, i);
            }
        }
    }

    @Override // com.google.protobuf.am
    public int b(T t) {
        return this.j ? g((z<T>) t) : f((z<T>) t);
    }

    private int f(T t) {
        int i;
        int i2;
        Unsafe unsafe = b;
        int i3 = -1;
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 0; i6 < this.c.length; i6 += 3) {
            int e = e(i6);
            int d = d(i6);
            int g = g(e);
            if (g <= 17) {
                i2 = this.c[i6 + 2];
                int i7 = 1048575 & i2;
                i = 1 << (i2 >>> 20);
                if (i7 != i3) {
                    i5 = unsafe.getInt(t, i7);
                    i3 = i7;
                }
            } else if (!this.k || g < FieldType.DOUBLE_LIST_PACKED.id() || g > FieldType.SINT64_LIST_PACKED.id()) {
                i2 = 0;
                i = 0;
            } else {
                i2 = this.c[i6 + 2] & 1048575;
                i = 0;
            }
            long j = j(e);
            switch (g) {
                case 0:
                    if ((i5 & i) != 0) {
                        i4 += CodedOutputStream.computeDoubleSize(d, 0.0d);
                        break;
                    } else {
                        break;
                    }
                case 1:
                    if ((i5 & i) != 0) {
                        i4 += CodedOutputStream.computeFloatSize(d, 0.0f);
                        break;
                    } else {
                        break;
                    }
                case 2:
                    if ((i5 & i) != 0) {
                        i4 += CodedOutputStream.computeInt64Size(d, unsafe.getLong(t, j));
                        break;
                    } else {
                        break;
                    }
                case 3:
                    if ((i5 & i) != 0) {
                        i4 += CodedOutputStream.computeUInt64Size(d, unsafe.getLong(t, j));
                        break;
                    } else {
                        break;
                    }
                case 4:
                    if ((i5 & i) != 0) {
                        i4 += CodedOutputStream.computeInt32Size(d, unsafe.getInt(t, j));
                        break;
                    } else {
                        break;
                    }
                case 5:
                    if ((i5 & i) != 0) {
                        i4 += CodedOutputStream.computeFixed64Size(d, 0L);
                        break;
                    } else {
                        break;
                    }
                case 6:
                    if ((i5 & i) != 0) {
                        i4 += CodedOutputStream.computeFixed32Size(d, 0);
                        break;
                    } else {
                        break;
                    }
                case 7:
                    if ((i5 & i) != 0) {
                        i4 += CodedOutputStream.computeBoolSize(d, true);
                        break;
                    } else {
                        break;
                    }
                case 8:
                    if ((i5 & i) != 0) {
                        Object object = unsafe.getObject(t, j);
                        if (object instanceof ByteString) {
                            i4 += CodedOutputStream.computeBytesSize(d, (ByteString) object);
                            break;
                        } else {
                            i4 += CodedOutputStream.computeStringSize(d, (String) object);
                            break;
                        }
                    } else {
                        break;
                    }
                case 9:
                    if ((i5 & i) != 0) {
                        i4 += ao.a(d, unsafe.getObject(t, j), a(i6));
                        break;
                    } else {
                        break;
                    }
                case 10:
                    if ((i5 & i) != 0) {
                        i4 += CodedOutputStream.computeBytesSize(d, (ByteString) unsafe.getObject(t, j));
                        break;
                    } else {
                        break;
                    }
                case 11:
                    if ((i5 & i) != 0) {
                        i4 += CodedOutputStream.computeUInt32Size(d, unsafe.getInt(t, j));
                        break;
                    } else {
                        break;
                    }
                case 12:
                    if ((i5 & i) != 0) {
                        i4 += CodedOutputStream.computeEnumSize(d, unsafe.getInt(t, j));
                        break;
                    } else {
                        break;
                    }
                case 13:
                    if ((i5 & i) != 0) {
                        i4 += CodedOutputStream.computeSFixed32Size(d, 0);
                        break;
                    } else {
                        break;
                    }
                case 14:
                    if ((i5 & i) != 0) {
                        i4 += CodedOutputStream.computeSFixed64Size(d, 0L);
                        break;
                    } else {
                        break;
                    }
                case 15:
                    if ((i5 & i) != 0) {
                        i4 += CodedOutputStream.computeSInt32Size(d, unsafe.getInt(t, j));
                        break;
                    } else {
                        break;
                    }
                case 16:
                    if ((i5 & i) != 0) {
                        i4 += CodedOutputStream.computeSInt64Size(d, unsafe.getLong(t, j));
                        break;
                    } else {
                        break;
                    }
                case 17:
                    if ((i5 & i) != 0) {
                        i4 += CodedOutputStream.d(d, (MessageLite) unsafe.getObject(t, j), a(i6));
                        break;
                    } else {
                        break;
                    }
                case 18:
                    i4 += ao.i(d, (List) unsafe.getObject(t, j), false);
                    break;
                case 19:
                    i4 += ao.h(d, (List) unsafe.getObject(t, j), false);
                    break;
                case 20:
                    i4 += ao.a(d, (List<Long>) unsafe.getObject(t, j), false);
                    break;
                case 21:
                    i4 += ao.b(d, (List<Long>) unsafe.getObject(t, j), false);
                    break;
                case 22:
                    i4 += ao.e(d, (List) unsafe.getObject(t, j), false);
                    break;
                case 23:
                    i4 += ao.i(d, (List) unsafe.getObject(t, j), false);
                    break;
                case 24:
                    i4 += ao.h(d, (List) unsafe.getObject(t, j), false);
                    break;
                case 25:
                    i4 += ao.j(d, (List) unsafe.getObject(t, j), false);
                    break;
                case 26:
                    i4 += ao.a(d, (List) unsafe.getObject(t, j));
                    break;
                case 27:
                    i4 += ao.a(d, (List<?>) unsafe.getObject(t, j), a(i6));
                    break;
                case 28:
                    i4 += ao.b(d, (List) unsafe.getObject(t, j));
                    break;
                case 29:
                    i4 += ao.f(d, (List) unsafe.getObject(t, j), false);
                    break;
                case 30:
                    i4 += ao.d(d, (List) unsafe.getObject(t, j), false);
                    break;
                case 31:
                    i4 += ao.h(d, (List) unsafe.getObject(t, j), false);
                    break;
                case 32:
                    i4 += ao.i(d, (List) unsafe.getObject(t, j), false);
                    break;
                case 33:
                    i4 += ao.g(d, (List) unsafe.getObject(t, j), false);
                    break;
                case 34:
                    i4 += ao.c(d, (List) unsafe.getObject(t, j), false);
                    break;
                case 35:
                    int i8 = ao.i((List) unsafe.getObject(t, j));
                    if (i8 <= 0) {
                        break;
                    } else {
                        if (this.k) {
                            unsafe.putInt(t, i2, i8);
                        }
                        i4 += CodedOutputStream.computeTagSize(d) + CodedOutputStream.computeUInt32SizeNoTag(i8) + i8;
                        break;
                    }
                case 36:
                    int h = ao.h((List) unsafe.getObject(t, j));
                    if (h <= 0) {
                        break;
                    } else {
                        if (this.k) {
                            unsafe.putInt(t, i2, h);
                        }
                        i4 += CodedOutputStream.computeTagSize(d) + CodedOutputStream.computeUInt32SizeNoTag(h) + h;
                        break;
                    }
                case 37:
                    int a2 = ao.a((List) unsafe.getObject(t, j));
                    if (a2 <= 0) {
                        break;
                    } else {
                        if (this.k) {
                            unsafe.putInt(t, i2, a2);
                        }
                        i4 += CodedOutputStream.computeTagSize(d) + CodedOutputStream.computeUInt32SizeNoTag(a2) + a2;
                        break;
                    }
                case 38:
                    int b2 = ao.b((List) unsafe.getObject(t, j));
                    if (b2 <= 0) {
                        break;
                    } else {
                        if (this.k) {
                            unsafe.putInt(t, i2, b2);
                        }
                        i4 += CodedOutputStream.computeTagSize(d) + CodedOutputStream.computeUInt32SizeNoTag(b2) + b2;
                        break;
                    }
                case 39:
                    int e2 = ao.e((List) unsafe.getObject(t, j));
                    if (e2 <= 0) {
                        break;
                    } else {
                        if (this.k) {
                            unsafe.putInt(t, i2, e2);
                        }
                        i4 += CodedOutputStream.computeTagSize(d) + CodedOutputStream.computeUInt32SizeNoTag(e2) + e2;
                        break;
                    }
                case 40:
                    int i9 = ao.i((List) unsafe.getObject(t, j));
                    if (i9 <= 0) {
                        break;
                    } else {
                        if (this.k) {
                            unsafe.putInt(t, i2, i9);
                        }
                        i4 += CodedOutputStream.computeTagSize(d) + CodedOutputStream.computeUInt32SizeNoTag(i9) + i9;
                        break;
                    }
                case 41:
                    int h2 = ao.h((List) unsafe.getObject(t, j));
                    if (h2 <= 0) {
                        break;
                    } else {
                        if (this.k) {
                            unsafe.putInt(t, i2, h2);
                        }
                        i4 += CodedOutputStream.computeTagSize(d) + CodedOutputStream.computeUInt32SizeNoTag(h2) + h2;
                        break;
                    }
                case 42:
                    int j2 = ao.j((List) unsafe.getObject(t, j));
                    if (j2 <= 0) {
                        break;
                    } else {
                        if (this.k) {
                            unsafe.putInt(t, i2, j2);
                        }
                        i4 += CodedOutputStream.computeTagSize(d) + CodedOutputStream.computeUInt32SizeNoTag(j2) + j2;
                        break;
                    }
                case 43:
                    int f = ao.f((List) unsafe.getObject(t, j));
                    if (f <= 0) {
                        break;
                    } else {
                        if (this.k) {
                            unsafe.putInt(t, i2, f);
                        }
                        i4 += CodedOutputStream.computeTagSize(d) + CodedOutputStream.computeUInt32SizeNoTag(f) + f;
                        break;
                    }
                case 44:
                    int d2 = ao.d((List) unsafe.getObject(t, j));
                    if (d2 <= 0) {
                        break;
                    } else {
                        if (this.k) {
                            unsafe.putInt(t, i2, d2);
                        }
                        i4 += CodedOutputStream.computeTagSize(d) + CodedOutputStream.computeUInt32SizeNoTag(d2) + d2;
                        break;
                    }
                case 45:
                    int h3 = ao.h((List) unsafe.getObject(t, j));
                    if (h3 <= 0) {
                        break;
                    } else {
                        if (this.k) {
                            unsafe.putInt(t, i2, h3);
                        }
                        i4 += CodedOutputStream.computeTagSize(d) + CodedOutputStream.computeUInt32SizeNoTag(h3) + h3;
                        break;
                    }
                case 46:
                    int i10 = ao.i((List) unsafe.getObject(t, j));
                    if (i10 <= 0) {
                        break;
                    } else {
                        if (this.k) {
                            unsafe.putInt(t, i2, i10);
                        }
                        i4 += CodedOutputStream.computeTagSize(d) + CodedOutputStream.computeUInt32SizeNoTag(i10) + i10;
                        break;
                    }
                case 47:
                    int g2 = ao.g((List) unsafe.getObject(t, j));
                    if (g2 <= 0) {
                        break;
                    } else {
                        if (this.k) {
                            unsafe.putInt(t, i2, g2);
                        }
                        i4 += CodedOutputStream.computeTagSize(d) + CodedOutputStream.computeUInt32SizeNoTag(g2) + g2;
                        break;
                    }
                case 48:
                    int c = ao.c((List) unsafe.getObject(t, j));
                    if (c <= 0) {
                        break;
                    } else {
                        if (this.k) {
                            unsafe.putInt(t, i2, c);
                        }
                        i4 += CodedOutputStream.computeTagSize(d) + CodedOutputStream.computeUInt32SizeNoTag(c) + c;
                        break;
                    }
                case 49:
                    i4 += ao.b(d, (List) unsafe.getObject(t, j), a(i6));
                    break;
                case 50:
                    i4 += this.s.a(d, unsafe.getObject(t, j), b(i6));
                    break;
                case 51:
                    if (c((z<T>) t, d, i6)) {
                        i4 += CodedOutputStream.computeDoubleSize(d, 0.0d);
                        break;
                    } else {
                        break;
                    }
                case 52:
                    if (c((z<T>) t, d, i6)) {
                        i4 += CodedOutputStream.computeFloatSize(d, 0.0f);
                        break;
                    } else {
                        break;
                    }
                case 53:
                    if (c((z<T>) t, d, i6)) {
                        i4 += CodedOutputStream.computeInt64Size(d, j(t, j));
                        break;
                    } else {
                        break;
                    }
                case 54:
                    if (c((z<T>) t, d, i6)) {
                        i4 += CodedOutputStream.computeUInt64Size(d, j(t, j));
                        break;
                    } else {
                        break;
                    }
                case 55:
                    if (c((z<T>) t, d, i6)) {
                        i4 += CodedOutputStream.computeInt32Size(d, i(t, j));
                        break;
                    } else {
                        break;
                    }
                case 56:
                    if (c((z<T>) t, d, i6)) {
                        i4 += CodedOutputStream.computeFixed64Size(d, 0L);
                        break;
                    } else {
                        break;
                    }
                case 57:
                    if (c((z<T>) t, d, i6)) {
                        i4 += CodedOutputStream.computeFixed32Size(d, 0);
                        break;
                    } else {
                        break;
                    }
                case 58:
                    if (c((z<T>) t, d, i6)) {
                        i4 += CodedOutputStream.computeBoolSize(d, true);
                        break;
                    } else {
                        break;
                    }
                case 59:
                    if (c((z<T>) t, d, i6)) {
                        Object object2 = unsafe.getObject(t, j);
                        if (object2 instanceof ByteString) {
                            i4 += CodedOutputStream.computeBytesSize(d, (ByteString) object2);
                            break;
                        } else {
                            i4 += CodedOutputStream.computeStringSize(d, (String) object2);
                            break;
                        }
                    } else {
                        break;
                    }
                case 60:
                    if (c((z<T>) t, d, i6)) {
                        i4 += ao.a(d, unsafe.getObject(t, j), a(i6));
                        break;
                    } else {
                        break;
                    }
                case 61:
                    if (c((z<T>) t, d, i6)) {
                        i4 += CodedOutputStream.computeBytesSize(d, (ByteString) unsafe.getObject(t, j));
                        break;
                    } else {
                        break;
                    }
                case 62:
                    if (c((z<T>) t, d, i6)) {
                        i4 += CodedOutputStream.computeUInt32Size(d, i(t, j));
                        break;
                    } else {
                        break;
                    }
                case 63:
                    if (c((z<T>) t, d, i6)) {
                        i4 += CodedOutputStream.computeEnumSize(d, i(t, j));
                        break;
                    } else {
                        break;
                    }
                case 64:
                    if (c((z<T>) t, d, i6)) {
                        i4 += CodedOutputStream.computeSFixed32Size(d, 0);
                        break;
                    } else {
                        break;
                    }
                case 65:
                    if (c((z<T>) t, d, i6)) {
                        i4 += CodedOutputStream.computeSFixed64Size(d, 0L);
                        break;
                    } else {
                        break;
                    }
                case 66:
                    if (c((z<T>) t, d, i6)) {
                        i4 += CodedOutputStream.computeSInt32Size(d, i(t, j));
                        break;
                    } else {
                        break;
                    }
                case 67:
                    if (c((z<T>) t, d, i6)) {
                        i4 += CodedOutputStream.computeSInt64Size(d, j(t, j));
                        break;
                    } else {
                        break;
                    }
                case 68:
                    if (c((z<T>) t, d, i6)) {
                        i4 += CodedOutputStream.d(d, (MessageLite) unsafe.getObject(t, j), a(i6));
                        break;
                    } else {
                        break;
                    }
            }
        }
        int a3 = i4 + a(this.q, (ar) t);
        return this.h ? a3 + this.r.a(t).j() : a3;
    }

    private int g(T t) {
        Unsafe unsafe = b;
        int i = 0;
        for (int i2 = 0; i2 < this.c.length; i2 += 3) {
            int e = e(i2);
            int g = g(e);
            int d = d(i2);
            long j = j(e);
            int i3 = (g < FieldType.DOUBLE_LIST_PACKED.id() || g > FieldType.SINT64_LIST_PACKED.id()) ? 0 : this.c[i2 + 2] & 1048575;
            switch (g) {
                case 0:
                    if (a((z<T>) t, i2)) {
                        i += CodedOutputStream.computeDoubleSize(d, 0.0d);
                        break;
                    } else {
                        break;
                    }
                case 1:
                    if (a((z<T>) t, i2)) {
                        i += CodedOutputStream.computeFloatSize(d, 0.0f);
                        break;
                    } else {
                        break;
                    }
                case 2:
                    if (a((z<T>) t, i2)) {
                        i += CodedOutputStream.computeInt64Size(d, at.b(t, j));
                        break;
                    } else {
                        break;
                    }
                case 3:
                    if (a((z<T>) t, i2)) {
                        i += CodedOutputStream.computeUInt64Size(d, at.b(t, j));
                        break;
                    } else {
                        break;
                    }
                case 4:
                    if (a((z<T>) t, i2)) {
                        i += CodedOutputStream.computeInt32Size(d, at.a(t, j));
                        break;
                    } else {
                        break;
                    }
                case 5:
                    if (a((z<T>) t, i2)) {
                        i += CodedOutputStream.computeFixed64Size(d, 0L);
                        break;
                    } else {
                        break;
                    }
                case 6:
                    if (a((z<T>) t, i2)) {
                        i += CodedOutputStream.computeFixed32Size(d, 0);
                        break;
                    } else {
                        break;
                    }
                case 7:
                    if (a((z<T>) t, i2)) {
                        i += CodedOutputStream.computeBoolSize(d, true);
                        break;
                    } else {
                        break;
                    }
                case 8:
                    if (a((z<T>) t, i2)) {
                        Object f = at.f(t, j);
                        if (f instanceof ByteString) {
                            i += CodedOutputStream.computeBytesSize(d, (ByteString) f);
                            break;
                        } else {
                            i += CodedOutputStream.computeStringSize(d, (String) f);
                            break;
                        }
                    } else {
                        break;
                    }
                case 9:
                    if (a((z<T>) t, i2)) {
                        i += ao.a(d, at.f(t, j), a(i2));
                        break;
                    } else {
                        break;
                    }
                case 10:
                    if (a((z<T>) t, i2)) {
                        i += CodedOutputStream.computeBytesSize(d, (ByteString) at.f(t, j));
                        break;
                    } else {
                        break;
                    }
                case 11:
                    if (a((z<T>) t, i2)) {
                        i += CodedOutputStream.computeUInt32Size(d, at.a(t, j));
                        break;
                    } else {
                        break;
                    }
                case 12:
                    if (a((z<T>) t, i2)) {
                        i += CodedOutputStream.computeEnumSize(d, at.a(t, j));
                        break;
                    } else {
                        break;
                    }
                case 13:
                    if (a((z<T>) t, i2)) {
                        i += CodedOutputStream.computeSFixed32Size(d, 0);
                        break;
                    } else {
                        break;
                    }
                case 14:
                    if (a((z<T>) t, i2)) {
                        i += CodedOutputStream.computeSFixed64Size(d, 0L);
                        break;
                    } else {
                        break;
                    }
                case 15:
                    if (a((z<T>) t, i2)) {
                        i += CodedOutputStream.computeSInt32Size(d, at.a(t, j));
                        break;
                    } else {
                        break;
                    }
                case 16:
                    if (a((z<T>) t, i2)) {
                        i += CodedOutputStream.computeSInt64Size(d, at.b(t, j));
                        break;
                    } else {
                        break;
                    }
                case 17:
                    if (a((z<T>) t, i2)) {
                        i += CodedOutputStream.d(d, (MessageLite) at.f(t, j), a(i2));
                        break;
                    } else {
                        break;
                    }
                case 18:
                    i += ao.i(d, a(t, j), false);
                    break;
                case 19:
                    i += ao.h(d, a(t, j), false);
                    break;
                case 20:
                    i += ao.a(d, (List<Long>) a(t, j), false);
                    break;
                case 21:
                    i += ao.b(d, (List<Long>) a(t, j), false);
                    break;
                case 22:
                    i += ao.e(d, a(t, j), false);
                    break;
                case 23:
                    i += ao.i(d, a(t, j), false);
                    break;
                case 24:
                    i += ao.h(d, a(t, j), false);
                    break;
                case 25:
                    i += ao.j(d, a(t, j), false);
                    break;
                case 26:
                    i += ao.a(d, a(t, j));
                    break;
                case 27:
                    i += ao.a(d, a(t, j), a(i2));
                    break;
                case 28:
                    i += ao.b(d, a(t, j));
                    break;
                case 29:
                    i += ao.f(d, a(t, j), false);
                    break;
                case 30:
                    i += ao.d(d, a(t, j), false);
                    break;
                case 31:
                    i += ao.h(d, a(t, j), false);
                    break;
                case 32:
                    i += ao.i(d, a(t, j), false);
                    break;
                case 33:
                    i += ao.g(d, a(t, j), false);
                    break;
                case 34:
                    i += ao.c(d, a(t, j), false);
                    break;
                case 35:
                    int i4 = ao.i((List) unsafe.getObject(t, j));
                    if (i4 <= 0) {
                        break;
                    } else {
                        if (this.k) {
                            unsafe.putInt(t, i3, i4);
                        }
                        i += CodedOutputStream.computeTagSize(d) + CodedOutputStream.computeUInt32SizeNoTag(i4) + i4;
                        break;
                    }
                case 36:
                    int h = ao.h((List) unsafe.getObject(t, j));
                    if (h <= 0) {
                        break;
                    } else {
                        if (this.k) {
                            unsafe.putInt(t, i3, h);
                        }
                        i += CodedOutputStream.computeTagSize(d) + CodedOutputStream.computeUInt32SizeNoTag(h) + h;
                        break;
                    }
                case 37:
                    int a2 = ao.a((List) unsafe.getObject(t, j));
                    if (a2 <= 0) {
                        break;
                    } else {
                        if (this.k) {
                            unsafe.putInt(t, i3, a2);
                        }
                        i += CodedOutputStream.computeTagSize(d) + CodedOutputStream.computeUInt32SizeNoTag(a2) + a2;
                        break;
                    }
                case 38:
                    int b2 = ao.b((List) unsafe.getObject(t, j));
                    if (b2 <= 0) {
                        break;
                    } else {
                        if (this.k) {
                            unsafe.putInt(t, i3, b2);
                        }
                        i += CodedOutputStream.computeTagSize(d) + CodedOutputStream.computeUInt32SizeNoTag(b2) + b2;
                        break;
                    }
                case 39:
                    int e2 = ao.e((List) unsafe.getObject(t, j));
                    if (e2 <= 0) {
                        break;
                    } else {
                        if (this.k) {
                            unsafe.putInt(t, i3, e2);
                        }
                        i += CodedOutputStream.computeTagSize(d) + CodedOutputStream.computeUInt32SizeNoTag(e2) + e2;
                        break;
                    }
                case 40:
                    int i5 = ao.i((List) unsafe.getObject(t, j));
                    if (i5 <= 0) {
                        break;
                    } else {
                        if (this.k) {
                            unsafe.putInt(t, i3, i5);
                        }
                        i += CodedOutputStream.computeTagSize(d) + CodedOutputStream.computeUInt32SizeNoTag(i5) + i5;
                        break;
                    }
                case 41:
                    int h2 = ao.h((List) unsafe.getObject(t, j));
                    if (h2 <= 0) {
                        break;
                    } else {
                        if (this.k) {
                            unsafe.putInt(t, i3, h2);
                        }
                        i += CodedOutputStream.computeTagSize(d) + CodedOutputStream.computeUInt32SizeNoTag(h2) + h2;
                        break;
                    }
                case 42:
                    int j2 = ao.j((List) unsafe.getObject(t, j));
                    if (j2 <= 0) {
                        break;
                    } else {
                        if (this.k) {
                            unsafe.putInt(t, i3, j2);
                        }
                        i += CodedOutputStream.computeTagSize(d) + CodedOutputStream.computeUInt32SizeNoTag(j2) + j2;
                        break;
                    }
                case 43:
                    int f2 = ao.f((List) unsafe.getObject(t, j));
                    if (f2 <= 0) {
                        break;
                    } else {
                        if (this.k) {
                            unsafe.putInt(t, i3, f2);
                        }
                        i += CodedOutputStream.computeTagSize(d) + CodedOutputStream.computeUInt32SizeNoTag(f2) + f2;
                        break;
                    }
                case 44:
                    int d2 = ao.d((List) unsafe.getObject(t, j));
                    if (d2 <= 0) {
                        break;
                    } else {
                        if (this.k) {
                            unsafe.putInt(t, i3, d2);
                        }
                        i += CodedOutputStream.computeTagSize(d) + CodedOutputStream.computeUInt32SizeNoTag(d2) + d2;
                        break;
                    }
                case 45:
                    int h3 = ao.h((List) unsafe.getObject(t, j));
                    if (h3 <= 0) {
                        break;
                    } else {
                        if (this.k) {
                            unsafe.putInt(t, i3, h3);
                        }
                        i += CodedOutputStream.computeTagSize(d) + CodedOutputStream.computeUInt32SizeNoTag(h3) + h3;
                        break;
                    }
                case 46:
                    int i6 = ao.i((List) unsafe.getObject(t, j));
                    if (i6 <= 0) {
                        break;
                    } else {
                        if (this.k) {
                            unsafe.putInt(t, i3, i6);
                        }
                        i += CodedOutputStream.computeTagSize(d) + CodedOutputStream.computeUInt32SizeNoTag(i6) + i6;
                        break;
                    }
                case 47:
                    int g2 = ao.g((List) unsafe.getObject(t, j));
                    if (g2 <= 0) {
                        break;
                    } else {
                        if (this.k) {
                            unsafe.putInt(t, i3, g2);
                        }
                        i += CodedOutputStream.computeTagSize(d) + CodedOutputStream.computeUInt32SizeNoTag(g2) + g2;
                        break;
                    }
                case 48:
                    int c = ao.c((List) unsafe.getObject(t, j));
                    if (c <= 0) {
                        break;
                    } else {
                        if (this.k) {
                            unsafe.putInt(t, i3, c);
                        }
                        i += CodedOutputStream.computeTagSize(d) + CodedOutputStream.computeUInt32SizeNoTag(c) + c;
                        break;
                    }
                case 49:
                    i += ao.b(d, (List<MessageLite>) a(t, j), a(i2));
                    break;
                case 50:
                    i += this.s.a(d, at.f(t, j), b(i2));
                    break;
                case 51:
                    if (c((z<T>) t, d, i2)) {
                        i += CodedOutputStream.computeDoubleSize(d, 0.0d);
                        break;
                    } else {
                        break;
                    }
                case 52:
                    if (c((z<T>) t, d, i2)) {
                        i += CodedOutputStream.computeFloatSize(d, 0.0f);
                        break;
                    } else {
                        break;
                    }
                case 53:
                    if (c((z<T>) t, d, i2)) {
                        i += CodedOutputStream.computeInt64Size(d, j(t, j));
                        break;
                    } else {
                        break;
                    }
                case 54:
                    if (c((z<T>) t, d, i2)) {
                        i += CodedOutputStream.computeUInt64Size(d, j(t, j));
                        break;
                    } else {
                        break;
                    }
                case 55:
                    if (c((z<T>) t, d, i2)) {
                        i += CodedOutputStream.computeInt32Size(d, i(t, j));
                        break;
                    } else {
                        break;
                    }
                case 56:
                    if (c((z<T>) t, d, i2)) {
                        i += CodedOutputStream.computeFixed64Size(d, 0L);
                        break;
                    } else {
                        break;
                    }
                case 57:
                    if (c((z<T>) t, d, i2)) {
                        i += CodedOutputStream.computeFixed32Size(d, 0);
                        break;
                    } else {
                        break;
                    }
                case 58:
                    if (c((z<T>) t, d, i2)) {
                        i += CodedOutputStream.computeBoolSize(d, true);
                        break;
                    } else {
                        break;
                    }
                case 59:
                    if (c((z<T>) t, d, i2)) {
                        Object f3 = at.f(t, j);
                        if (f3 instanceof ByteString) {
                            i += CodedOutputStream.computeBytesSize(d, (ByteString) f3);
                            break;
                        } else {
                            i += CodedOutputStream.computeStringSize(d, (String) f3);
                            break;
                        }
                    } else {
                        break;
                    }
                case 60:
                    if (c((z<T>) t, d, i2)) {
                        i += ao.a(d, at.f(t, j), a(i2));
                        break;
                    } else {
                        break;
                    }
                case 61:
                    if (c((z<T>) t, d, i2)) {
                        i += CodedOutputStream.computeBytesSize(d, (ByteString) at.f(t, j));
                        break;
                    } else {
                        break;
                    }
                case 62:
                    if (c((z<T>) t, d, i2)) {
                        i += CodedOutputStream.computeUInt32Size(d, i(t, j));
                        break;
                    } else {
                        break;
                    }
                case 63:
                    if (c((z<T>) t, d, i2)) {
                        i += CodedOutputStream.computeEnumSize(d, i(t, j));
                        break;
                    } else {
                        break;
                    }
                case 64:
                    if (c((z<T>) t, d, i2)) {
                        i += CodedOutputStream.computeSFixed32Size(d, 0);
                        break;
                    } else {
                        break;
                    }
                case 65:
                    if (c((z<T>) t, d, i2)) {
                        i += CodedOutputStream.computeSFixed64Size(d, 0L);
                        break;
                    } else {
                        break;
                    }
                case 66:
                    if (c((z<T>) t, d, i2)) {
                        i += CodedOutputStream.computeSInt32Size(d, i(t, j));
                        break;
                    } else {
                        break;
                    }
                case 67:
                    if (c((z<T>) t, d, i2)) {
                        i += CodedOutputStream.computeSInt64Size(d, j(t, j));
                        break;
                    } else {
                        break;
                    }
                case 68:
                    if (c((z<T>) t, d, i2)) {
                        i += CodedOutputStream.d(d, (MessageLite) at.f(t, j), a(i2));
                        break;
                    } else {
                        break;
                    }
            }
        }
        return i + a(this.q, (ar) t);
    }

    private <UT, UB> int a(ar<UT, UB> arVar, T t) {
        return arVar.f(arVar.b(t));
    }

    private static List<?> a(Object obj, long j) {
        return (List) at.f(obj, j);
    }

    @Override // com.google.protobuf.am
    public void a(T t, Writer writer) throws IOException {
        if (writer.a() == Writer.FieldOrder.DESCENDING) {
            d((z<T>) t, writer);
        } else if (this.j) {
            c((z<T>) t, writer);
        } else {
            b((z<T>) t, writer);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x002e  */
    /* JADX WARN: Removed duplicated region for block: B:191:0x058f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void b(T r18, com.google.protobuf.Writer r19) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 1592
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.z.b(java.lang.Object, com.google.protobuf.Writer):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0026  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x0589  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void c(T r13, com.google.protobuf.Writer r14) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 1586
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.z.c(java.lang.Object, com.google.protobuf.Writer):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x002a  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x058e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void d(T r11, com.google.protobuf.Writer r12) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 1586
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.z.d(java.lang.Object, com.google.protobuf.Writer):void");
    }

    private <K, V> void a(Writer writer, int i, Object obj, int i2) throws IOException {
        if (obj != null) {
            writer.a(i, this.s.f(b(i2)), this.s.b(obj));
        }
    }

    private <UT, UB> void a(ar<UT, UB> arVar, T t, Writer writer) throws IOException {
        arVar.a((ar<UT, UB>) arVar.b(t), writer);
    }

    @Override // com.google.protobuf.am
    public void a(T t, ak akVar, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        if (extensionRegistryLite != null) {
            a(this.q, this.r, (j) t, akVar, extensionRegistryLite);
            return;
        }
        throw new NullPointerException();
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x0076, code lost:
        r0 = r16.m;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x007a, code lost:
        if (r0 >= r16.n) goto L_0x0087;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x007c, code lost:
        r13 = a((java.lang.Object) r19, r16.l[r0], (int) r13, (com.google.protobuf.ar<UT, int>) r17);
        r0 = r0 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:359:?, code lost:
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0087, code lost:
        if (r13 == null) goto L_?;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0089, code lost:
        r17.b((java.lang.Object) r19, (T) r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x008c, code lost:
        return;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private <UT, UB, ET extends com.google.protobuf.FieldSet.FieldDescriptorLite<ET>> void a(com.google.protobuf.ar<UT, UB> r17, com.google.protobuf.j<ET> r18, T r19, com.google.protobuf.ak r20, com.google.protobuf.ExtensionRegistryLite r21) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 1718
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.z.a(com.google.protobuf.ar, com.google.protobuf.j, java.lang.Object, com.google.protobuf.ak, com.google.protobuf.ExtensionRegistryLite):void");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static UnknownFieldSetLite c(Object obj) {
        GeneratedMessageLite generatedMessageLite = (GeneratedMessageLite) obj;
        UnknownFieldSetLite unknownFieldSetLite = generatedMessageLite.unknownFields;
        if (unknownFieldSetLite != UnknownFieldSetLite.getDefaultInstance()) {
            return unknownFieldSetLite;
        }
        UnknownFieldSetLite a2 = UnknownFieldSetLite.a();
        generatedMessageLite.unknownFields = a2;
        return a2;
    }

    private int a(byte[] bArr, int i, int i2, WireFormat.FieldType fieldType, Class<?> cls, c.a aVar) throws IOException {
        switch (fieldType) {
            case BOOL:
                int b2 = c.b(bArr, i, aVar);
                aVar.c = Boolean.valueOf(aVar.b != 0);
                return b2;
            case BYTES:
                return c.e(bArr, i, aVar);
            case DOUBLE:
                aVar.c = Double.valueOf(c.c(bArr, i));
                return i + 8;
            case FIXED32:
            case SFIXED32:
                aVar.c = Integer.valueOf(c.a(bArr, i));
                return i + 4;
            case FIXED64:
            case SFIXED64:
                aVar.c = Long.valueOf(c.b(bArr, i));
                return i + 8;
            case FLOAT:
                aVar.c = Float.valueOf(c.d(bArr, i));
                return i + 4;
            case ENUM:
            case INT32:
            case UINT32:
                int a2 = c.a(bArr, i, aVar);
                aVar.c = Integer.valueOf(aVar.a);
                return a2;
            case INT64:
            case UINT64:
                int b3 = c.b(bArr, i, aVar);
                aVar.c = Long.valueOf(aVar.b);
                return b3;
            case MESSAGE:
                return c.a(ah.a().a((Class) cls), bArr, i, i2, aVar);
            case SINT32:
                int a3 = c.a(bArr, i, aVar);
                aVar.c = Integer.valueOf(CodedInputStream.decodeZigZag32(aVar.a));
                return a3;
            case SINT64:
                int b4 = c.b(bArr, i, aVar);
                aVar.c = Long.valueOf(CodedInputStream.decodeZigZag64(aVar.b));
                return b4;
            case STRING:
                return c.d(bArr, i, aVar);
            default:
                throw new RuntimeException("unsupported field type.");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v1, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r12v2 */
    /* JADX WARN: Type inference failed for: r12v3, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r13v1, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r13v2 */
    /* JADX WARN: Type inference failed for: r13v3, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r19v0, types: [java.util.Map, java.util.Map<K, V>] */
    /* JADX WARN: Type inference failed for: r1v10, types: [int] */
    private <K, V> int a(byte[] bArr, int i, int i2, MapEntryLite.a<K, V> aVar, Map<K, V> map, c.a aVar2) throws IOException {
        int i3;
        int a2 = c.a(bArr, i, aVar2);
        int i4 = aVar2.a;
        if (i4 < 0 || i4 > i2 - a2) {
            throw InvalidProtocolBufferException.a();
        }
        int i5 = a2 + i4;
        K k = aVar.b;
        V v = aVar.d;
        while (a2 < i5) {
            int i6 = a2 + 1;
            byte b2 = bArr[a2];
            if (b2 < 0) {
                i3 = c.a((int) b2, bArr, i6, aVar2);
                b2 = aVar2.a;
            } else {
                i3 = i6;
            }
            int i7 = b2 & 7;
            switch (b2 >>> 3) {
                case 1:
                    if (i7 != aVar.a.getWireType()) {
                        break;
                    } else {
                        a2 = a(bArr, i3, i2, aVar.a, (Class<?>) null, aVar2);
                        k = aVar2.c;
                        continue;
                    }
                case 2:
                    if (i7 != aVar.c.getWireType()) {
                        break;
                    } else {
                        a2 = a(bArr, i3, i2, aVar.c, aVar.d.getClass(), aVar2);
                        v = aVar2.c;
                        continue;
                    }
            }
            a2 = c.a(b2, bArr, i3, i2, aVar2);
        }
        if (a2 == i5) {
            map.put(k, v);
            return i5;
        }
        throw InvalidProtocolBufferException.i();
    }

    private int a(T t, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, long j, int i7, long j2, c.a aVar) throws IOException {
        int i8;
        Internal.ProtobufList protobufList = (Internal.ProtobufList) b.getObject(t, j2);
        if (!protobufList.isModifiable()) {
            int size = protobufList.size();
            protobufList = protobufList.mutableCopyWithCapacity(size == 0 ? 10 : size * 2);
            b.putObject(t, j2, protobufList);
        }
        switch (i7) {
            case 18:
            case 35:
                if (i5 == 2) {
                    return c.f(bArr, i, protobufList, aVar);
                }
                if (i5 == 1) {
                    return c.f(i3, bArr, i, i2, protobufList, aVar);
                }
                break;
            case 19:
            case 36:
                if (i5 == 2) {
                    return c.e(bArr, i, protobufList, aVar);
                }
                if (i5 == 5) {
                    return c.e(i3, bArr, i, i2, protobufList, aVar);
                }
                break;
            case 20:
            case 21:
            case 37:
            case 38:
                if (i5 == 2) {
                    return c.b(bArr, i, protobufList, aVar);
                }
                if (i5 == 0) {
                    return c.b(i3, bArr, i, i2, protobufList, aVar);
                }
                break;
            case 22:
            case 29:
            case 39:
            case 43:
                if (i5 == 2) {
                    return c.a(bArr, i, protobufList, aVar);
                }
                if (i5 == 0) {
                    return c.a(i3, bArr, i, i2, protobufList, aVar);
                }
                break;
            case 23:
            case 32:
            case 40:
            case 46:
                if (i5 == 2) {
                    return c.d(bArr, i, protobufList, aVar);
                }
                if (i5 == 1) {
                    return c.d(i3, bArr, i, i2, protobufList, aVar);
                }
                break;
            case 24:
            case 31:
            case 41:
            case 45:
                if (i5 == 2) {
                    return c.c(bArr, i, protobufList, aVar);
                }
                if (i5 == 5) {
                    return c.c(i3, bArr, i, i2, protobufList, aVar);
                }
                break;
            case 25:
            case 42:
                if (i5 == 2) {
                    return c.g(bArr, i, protobufList, aVar);
                }
                if (i5 == 0) {
                    return c.g(i3, bArr, i, i2, protobufList, aVar);
                }
                break;
            case 26:
                if (i5 == 2) {
                    if ((j & 536870912) == 0) {
                        return c.j(i3, bArr, i, i2, protobufList, aVar);
                    }
                    return c.k(i3, bArr, i, i2, protobufList, aVar);
                }
                break;
            case 27:
                if (i5 == 2) {
                    return c.a(a(i6), i3, bArr, i, i2, protobufList, aVar);
                }
                break;
            case 28:
                if (i5 == 2) {
                    return c.l(i3, bArr, i, i2, protobufList, aVar);
                }
                break;
            case 30:
            case 44:
                if (i5 == 2) {
                    i8 = c.a(bArr, i, protobufList, aVar);
                } else if (i5 == 0) {
                    i8 = c.a(i3, bArr, i, i2, protobufList, aVar);
                }
                GeneratedMessageLite generatedMessageLite = (GeneratedMessageLite) t;
                UnknownFieldSetLite unknownFieldSetLite = generatedMessageLite.unknownFields;
                if (unknownFieldSetLite == UnknownFieldSetLite.getDefaultInstance()) {
                    unknownFieldSetLite = null;
                }
                UnknownFieldSetLite unknownFieldSetLite2 = (UnknownFieldSetLite) ao.a(i4, (List<Integer>) protobufList, c(i6), unknownFieldSetLite, (ar<UT, UnknownFieldSetLite>) this.q);
                if (unknownFieldSetLite2 != null) {
                    generatedMessageLite.unknownFields = unknownFieldSetLite2;
                }
                return i8;
            case 33:
            case 47:
                if (i5 == 2) {
                    return c.h(bArr, i, protobufList, aVar);
                }
                if (i5 == 0) {
                    return c.h(i3, bArr, i, i2, protobufList, aVar);
                }
                break;
            case 34:
            case 48:
                if (i5 == 2) {
                    return c.i(bArr, i, protobufList, aVar);
                }
                if (i5 == 0) {
                    return c.i(i3, bArr, i, i2, protobufList, aVar);
                }
                break;
            case 49:
                if (i5 == 3) {
                    return c.b(a(i6), i3, bArr, i, i2, protobufList, aVar);
                }
                break;
        }
        return i;
    }

    private <K, V> int a(T t, byte[] bArr, int i, int i2, int i3, long j, c.a aVar) throws IOException {
        Unsafe unsafe = b;
        Object b2 = b(i3);
        Object object = unsafe.getObject(t, j);
        if (this.s.c(object)) {
            Object e = this.s.e(b2);
            this.s.a(e, object);
            unsafe.putObject(t, j, e);
            object = e;
        }
        return a(bArr, i, i2, this.s.f(b2), this.s.a(object), aVar);
    }

    private int a(T t, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, int i7, long j, int i8, c.a aVar) throws IOException {
        Unsafe unsafe = b;
        long j2 = this.c[i8 + 2] & 1048575;
        switch (i7) {
            case 51:
                if (i5 == 1) {
                    unsafe.putObject(t, j, Double.valueOf(c.c(bArr, i)));
                    int i9 = i + 8;
                    unsafe.putInt(t, j2, i4);
                    return i9;
                }
                break;
            case 52:
                if (i5 == 5) {
                    unsafe.putObject(t, j, Float.valueOf(c.d(bArr, i)));
                    int i10 = i + 4;
                    unsafe.putInt(t, j2, i4);
                    return i10;
                }
                break;
            case 53:
            case 54:
                if (i5 == 0) {
                    int b2 = c.b(bArr, i, aVar);
                    unsafe.putObject(t, j, Long.valueOf(aVar.b));
                    unsafe.putInt(t, j2, i4);
                    return b2;
                }
                break;
            case 55:
            case 62:
                if (i5 == 0) {
                    int a2 = c.a(bArr, i, aVar);
                    unsafe.putObject(t, j, Integer.valueOf(aVar.a));
                    unsafe.putInt(t, j2, i4);
                    return a2;
                }
                break;
            case 56:
            case 65:
                if (i5 == 1) {
                    unsafe.putObject(t, j, Long.valueOf(c.b(bArr, i)));
                    int i11 = i + 8;
                    unsafe.putInt(t, j2, i4);
                    return i11;
                }
                break;
            case 57:
            case 64:
                if (i5 == 5) {
                    unsafe.putObject(t, j, Integer.valueOf(c.a(bArr, i)));
                    int i12 = i + 4;
                    unsafe.putInt(t, j2, i4);
                    return i12;
                }
                break;
            case 58:
                if (i5 == 0) {
                    int b3 = c.b(bArr, i, aVar);
                    unsafe.putObject(t, j, Boolean.valueOf(aVar.b != 0));
                    unsafe.putInt(t, j2, i4);
                    return b3;
                }
                break;
            case 59:
                if (i5 == 2) {
                    int a3 = c.a(bArr, i, aVar);
                    int i13 = aVar.a;
                    if (i13 == 0) {
                        unsafe.putObject(t, j, "");
                    } else if ((i6 & 536870912) == 0 || au.a(bArr, a3, a3 + i13)) {
                        unsafe.putObject(t, j, new String(bArr, a3, i13, Internal.a));
                        a3 += i13;
                    } else {
                        throw InvalidProtocolBufferException.j();
                    }
                    unsafe.putInt(t, j2, i4);
                    return a3;
                }
                break;
            case 60:
                if (i5 == 2) {
                    int a4 = c.a(a(i8), bArr, i, i2, aVar);
                    Object object = unsafe.getInt(t, j2) == i4 ? unsafe.getObject(t, j) : null;
                    if (object == null) {
                        unsafe.putObject(t, j, aVar.c);
                    } else {
                        unsafe.putObject(t, j, Internal.a(object, aVar.c));
                    }
                    unsafe.putInt(t, j2, i4);
                    return a4;
                }
                break;
            case 61:
                if (i5 == 2) {
                    int e = c.e(bArr, i, aVar);
                    unsafe.putObject(t, j, aVar.c);
                    unsafe.putInt(t, j2, i4);
                    return e;
                }
                break;
            case 63:
                if (i5 == 0) {
                    int a5 = c.a(bArr, i, aVar);
                    int i14 = aVar.a;
                    Internal.EnumVerifier c = c(i8);
                    if (c == null || c.isInRange(i14)) {
                        unsafe.putObject(t, j, Integer.valueOf(i14));
                        unsafe.putInt(t, j2, i4);
                    } else {
                        c(t).a(i3, Long.valueOf(i14));
                    }
                    return a5;
                }
                break;
            case 66:
                if (i5 == 0) {
                    int a6 = c.a(bArr, i, aVar);
                    unsafe.putObject(t, j, Integer.valueOf(CodedInputStream.decodeZigZag32(aVar.a)));
                    unsafe.putInt(t, j2, i4);
                    return a6;
                }
                break;
            case 67:
                if (i5 == 0) {
                    int b4 = c.b(bArr, i, aVar);
                    unsafe.putObject(t, j, Long.valueOf(CodedInputStream.decodeZigZag64(aVar.b)));
                    unsafe.putInt(t, j2, i4);
                    return b4;
                }
                break;
            case 68:
                if (i5 == 3) {
                    int a7 = c.a(a(i8), bArr, i, i2, (i3 & (-8)) | 4, aVar);
                    Object object2 = unsafe.getInt(t, j2) == i4 ? unsafe.getObject(t, j) : null;
                    if (object2 == null) {
                        unsafe.putObject(t, j, aVar.c);
                    } else {
                        unsafe.putObject(t, j, Internal.a(object2, aVar.c));
                    }
                    unsafe.putInt(t, j2, i4);
                    return a7;
                }
                break;
        }
        return i;
    }

    private am a(int i) {
        int i2 = (i / 3) * 2;
        am amVar = (am) this.d[i2];
        if (amVar != null) {
            return amVar;
        }
        am<T> a2 = ah.a().a((Class) ((Class) this.d[i2 + 1]));
        this.d[i2] = a2;
        return a2;
    }

    private Object b(int i) {
        return this.d[(i / 3) * 2];
    }

    private Internal.EnumVerifier c(int i) {
        return (Internal.EnumVerifier) this.d[((i / 3) * 2) + 1];
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int a(T t, byte[] bArr, int i, int i2, int i3, c.a aVar) throws IOException {
        Unsafe unsafe;
        int i4;
        z<T> zVar;
        int i5;
        int i6;
        int i7;
        int i8;
        T t2;
        byte b2;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        char c;
        z<T> zVar2 = this;
        T t3 = t;
        byte[] bArr2 = bArr;
        int i17 = i2;
        int i18 = i3;
        c.a aVar2 = aVar;
        Unsafe unsafe2 = b;
        int i19 = i;
        int i20 = 0;
        int i21 = 0;
        int i22 = 0;
        int i23 = -1;
        int i24 = -1;
        while (true) {
            if (i19 < i17) {
                int i25 = i19 + 1;
                byte b3 = bArr2[i19];
                if (b3 < 0) {
                    i9 = c.a((int) b3, bArr2, i25, aVar2);
                    b2 = aVar2.a;
                } else {
                    b2 = b3;
                    i9 = i25;
                }
                int i26 = b2 >>> 3;
                int i27 = b2 & 7;
                if (i26 > i23) {
                    i10 = zVar2.a(i26, i20 / 3);
                    i11 = -1;
                } else {
                    i10 = zVar2.k(i26);
                    i11 = -1;
                }
                if (i10 == i11) {
                    i23 = i26;
                    i5 = i9;
                    i12 = b2;
                    i13 = i22;
                    i14 = i24;
                    unsafe = unsafe2;
                    i15 = i18;
                    i20 = 0;
                } else {
                    int i28 = zVar2.c[i10 + 1];
                    int g = g(i28);
                    long j = j(i28);
                    if (g <= 17) {
                        int i29 = zVar2.c[i10 + 2];
                        int i30 = 1 << (i29 >>> 20);
                        int i31 = i29 & 1048575;
                        if (i31 != i24) {
                            c = 65535;
                            if (i24 != -1) {
                                i16 = i10;
                                unsafe2.putInt(t3, i24, i22);
                            } else {
                                i16 = i10;
                            }
                            i22 = unsafe2.getInt(t3, i31);
                            i24 = i31;
                        } else {
                            i16 = i10;
                            c = 65535;
                        }
                        switch (g) {
                            case 0:
                                i20 = i16;
                                i23 = i26;
                                bArr2 = bArr;
                                i5 = i9;
                                i12 = b2;
                                if (i27 == 1) {
                                    at.a(t3, j, c.c(bArr2, i5));
                                    i19 = i5 + 8;
                                    i22 |= i30;
                                    i20 = i20;
                                    i21 = i12;
                                    i23 = i23;
                                    i18 = i3;
                                    i17 = i2;
                                    break;
                                } else {
                                    i13 = i22;
                                    i14 = i24;
                                    unsafe = unsafe2;
                                    i15 = i3;
                                    break;
                                }
                            case 1:
                                i20 = i16;
                                i23 = i26;
                                bArr2 = bArr;
                                i5 = i9;
                                i12 = b2;
                                if (i27 == 5) {
                                    at.a((Object) t3, j, c.d(bArr2, i5));
                                    i19 = i5 + 4;
                                    i22 |= i30;
                                    i20 = i20;
                                    i21 = i12;
                                    i23 = i23;
                                    i18 = i3;
                                    i17 = i2;
                                    break;
                                } else {
                                    i13 = i22;
                                    i14 = i24;
                                    unsafe = unsafe2;
                                    i15 = i3;
                                    break;
                                }
                            case 2:
                            case 3:
                                i20 = i16;
                                i23 = i26;
                                bArr2 = bArr;
                                i5 = i9;
                                i12 = b2;
                                if (i27 == 0) {
                                    int b4 = c.b(bArr2, i5, aVar2);
                                    unsafe2.putLong(t, j, aVar2.b);
                                    i22 |= i30;
                                    i20 = i20;
                                    i19 = b4;
                                    i21 = i12;
                                    i23 = i23;
                                    i18 = i3;
                                    i17 = i2;
                                    break;
                                } else {
                                    i13 = i22;
                                    i14 = i24;
                                    unsafe = unsafe2;
                                    i15 = i3;
                                    break;
                                }
                            case 4:
                            case 11:
                                i20 = i16;
                                i23 = i26;
                                bArr2 = bArr;
                                i5 = i9;
                                i12 = b2;
                                if (i27 == 0) {
                                    i19 = c.a(bArr2, i5, aVar2);
                                    unsafe2.putInt(t3, j, aVar2.a);
                                    i22 |= i30;
                                    i20 = i20;
                                    i21 = i12;
                                    i23 = i23;
                                    i18 = i3;
                                    i17 = i2;
                                    break;
                                } else {
                                    i13 = i22;
                                    i14 = i24;
                                    unsafe = unsafe2;
                                    i15 = i3;
                                    break;
                                }
                            case 5:
                            case 14:
                                i20 = i16;
                                i23 = i26;
                                bArr2 = bArr;
                                i12 = b2;
                                if (i27 == 1) {
                                    unsafe2.putLong(t, j, c.b(bArr2, i9));
                                    i19 = i9 + 8;
                                    i22 |= i30;
                                    i20 = i20;
                                    i21 = i12;
                                    i23 = i23;
                                    i18 = i3;
                                    i17 = i2;
                                    break;
                                } else {
                                    i5 = i9;
                                    i13 = i22;
                                    i14 = i24;
                                    unsafe = unsafe2;
                                    i15 = i3;
                                    break;
                                }
                            case 6:
                            case 13:
                                i20 = i16;
                                i23 = i26;
                                bArr2 = bArr;
                                i12 = b2;
                                if (i27 == 5) {
                                    unsafe2.putInt(t3, j, c.a(bArr2, i9));
                                    i19 = i9 + 4;
                                    i22 |= i30;
                                    i20 = i20;
                                    i21 = i12;
                                    i23 = i23;
                                    i17 = i2;
                                    i18 = i3;
                                    break;
                                } else {
                                    i5 = i9;
                                    i13 = i22;
                                    i14 = i24;
                                    unsafe = unsafe2;
                                    i15 = i3;
                                    break;
                                }
                            case 7:
                                i20 = i16;
                                i23 = i26;
                                bArr2 = bArr;
                                i12 = b2;
                                if (i27 == 0) {
                                    i19 = c.b(bArr2, i9, aVar2);
                                    at.a(t3, j, aVar2.b != 0);
                                    i22 |= i30;
                                    i20 = i20;
                                    i21 = i12;
                                    i23 = i23;
                                    i17 = i2;
                                    i18 = i3;
                                    break;
                                } else {
                                    i5 = i9;
                                    i13 = i22;
                                    i14 = i24;
                                    unsafe = unsafe2;
                                    i15 = i3;
                                    break;
                                }
                            case 8:
                                i20 = i16;
                                i23 = i26;
                                bArr2 = bArr;
                                i12 = b2;
                                if (i27 == 2) {
                                    if ((i28 & 536870912) == 0) {
                                        i19 = c.c(bArr2, i9, aVar2);
                                    } else {
                                        i19 = c.d(bArr2, i9, aVar2);
                                    }
                                    unsafe2.putObject(t3, j, aVar2.c);
                                    i22 |= i30;
                                    i20 = i20;
                                    i21 = i12;
                                    i23 = i23;
                                    i17 = i2;
                                    i18 = i3;
                                    break;
                                } else {
                                    i5 = i9;
                                    i13 = i22;
                                    i14 = i24;
                                    unsafe = unsafe2;
                                    i15 = i3;
                                    break;
                                }
                            case 9:
                                i20 = i16;
                                i12 = b2;
                                i23 = i26;
                                bArr2 = bArr;
                                if (i27 == 2) {
                                    i19 = c.a(zVar2.a(i20), bArr2, i9, i2, aVar2);
                                    if ((i22 & i30) == 0) {
                                        unsafe2.putObject(t3, j, aVar2.c);
                                    } else {
                                        unsafe2.putObject(t3, j, Internal.a(unsafe2.getObject(t3, j), aVar2.c));
                                    }
                                    i22 |= i30;
                                    i20 = i20;
                                    i21 = i12;
                                    i23 = i23;
                                    i17 = i2;
                                    i18 = i3;
                                    break;
                                } else {
                                    i5 = i9;
                                    i13 = i22;
                                    i14 = i24;
                                    unsafe = unsafe2;
                                    i15 = i3;
                                    break;
                                }
                            case 10:
                                i20 = i16;
                                i12 = b2;
                                i23 = i26;
                                bArr2 = bArr;
                                if (i27 == 2) {
                                    i19 = c.e(bArr2, i9, aVar2);
                                    unsafe2.putObject(t3, j, aVar2.c);
                                    i22 |= i30;
                                    i20 = i20;
                                    i21 = i12;
                                    i23 = i23;
                                    i18 = i3;
                                    i17 = i2;
                                    break;
                                } else {
                                    i5 = i9;
                                    i13 = i22;
                                    i14 = i24;
                                    unsafe = unsafe2;
                                    i15 = i3;
                                    break;
                                }
                            case 12:
                                i20 = i16;
                                i12 = b2;
                                i23 = i26;
                                bArr2 = bArr;
                                if (i27 == 0) {
                                    i19 = c.a(bArr2, i9, aVar2);
                                    int i32 = aVar2.a;
                                    Internal.EnumVerifier c2 = zVar2.c(i20);
                                    if (c2 != null && !c2.isInRange(i32)) {
                                        c(t).a(i12, Long.valueOf(i32));
                                        i20 = i20;
                                        i21 = i12;
                                        i23 = i23;
                                        i18 = i3;
                                        i17 = i2;
                                        break;
                                    } else {
                                        unsafe2.putInt(t3, j, i32);
                                        i22 |= i30;
                                        i20 = i20;
                                        i21 = i12;
                                        i23 = i23;
                                        i18 = i3;
                                        i17 = i2;
                                        break;
                                    }
                                } else {
                                    i5 = i9;
                                    i13 = i22;
                                    i14 = i24;
                                    unsafe = unsafe2;
                                    i15 = i3;
                                    break;
                                }
                                break;
                            case 15:
                                i20 = i16;
                                i12 = b2;
                                i23 = i26;
                                bArr2 = bArr;
                                if (i27 == 0) {
                                    i19 = c.a(bArr2, i9, aVar2);
                                    unsafe2.putInt(t3, j, CodedInputStream.decodeZigZag32(aVar2.a));
                                    i22 |= i30;
                                    i20 = i20;
                                    i21 = i12;
                                    i23 = i23;
                                    i18 = i3;
                                    i17 = i2;
                                    break;
                                } else {
                                    i5 = i9;
                                    i13 = i22;
                                    i14 = i24;
                                    unsafe = unsafe2;
                                    i15 = i3;
                                    break;
                                }
                            case 16:
                                i20 = i16;
                                i12 = b2;
                                i23 = i26;
                                if (i27 == 0) {
                                    bArr2 = bArr;
                                    int b5 = c.b(bArr2, i9, aVar2);
                                    unsafe2.putLong(t, j, CodedInputStream.decodeZigZag64(aVar2.b));
                                    i22 |= i30;
                                    i20 = i20;
                                    i19 = b5;
                                    i21 = i12;
                                    i23 = i23;
                                    i18 = i3;
                                    i17 = i2;
                                    break;
                                } else {
                                    i5 = i9;
                                    i13 = i22;
                                    i14 = i24;
                                    unsafe = unsafe2;
                                    i15 = i3;
                                    break;
                                }
                            case 17:
                                if (i27 == 3) {
                                    i19 = c.a(zVar2.a(i16), bArr, i9, i2, (i26 << 3) | 4, aVar);
                                    if ((i22 & i30) == 0) {
                                        unsafe2.putObject(t3, j, aVar2.c);
                                    } else {
                                        unsafe2.putObject(t3, j, Internal.a(unsafe2.getObject(t3, j), aVar2.c));
                                    }
                                    i22 |= i30;
                                    i20 = i16;
                                    i21 = b2;
                                    i23 = i26;
                                    i18 = i3;
                                    bArr2 = bArr;
                                    i17 = i2;
                                    break;
                                } else {
                                    i20 = i16;
                                    i12 = b2;
                                    i23 = i26;
                                    i5 = i9;
                                    i13 = i22;
                                    i14 = i24;
                                    unsafe = unsafe2;
                                    i15 = i3;
                                    break;
                                }
                            default:
                                i5 = i9;
                                i20 = i16;
                                i23 = i26;
                                i12 = b2;
                                i13 = i22;
                                i14 = i24;
                                unsafe = unsafe2;
                                i15 = i3;
                                break;
                        }
                    } else {
                        i23 = i26;
                        bArr2 = bArr;
                        if (g != 27) {
                            i20 = i10;
                            i13 = i22;
                            if (g <= 49) {
                                i14 = i24;
                                unsafe = unsafe2;
                                i19 = a((z<T>) t, bArr, i9, i2, b2, i23, i27, i20, i28, g, j, aVar);
                                if (i19 != i9) {
                                    bArr2 = bArr;
                                    i23 = i23;
                                    i21 = b2;
                                    i24 = i14;
                                    i20 = i20;
                                    i22 = i13;
                                    unsafe2 = unsafe;
                                    aVar2 = aVar;
                                    i18 = i3;
                                    i17 = i2;
                                    t3 = t;
                                    zVar2 = this;
                                } else {
                                    i5 = i19;
                                    i12 = b2;
                                    i15 = i3;
                                }
                            } else {
                                unsafe = unsafe2;
                                i5 = i9;
                                i12 = b2;
                                i14 = i24;
                                if (g != 50) {
                                    i19 = a((z<T>) t, bArr, i5, i2, i12, i23, i27, i28, g, j, i20, aVar);
                                    if (i19 != i5) {
                                        bArr2 = bArr;
                                        i23 = i23;
                                        i21 = i12;
                                        i24 = i14;
                                        i20 = i20;
                                        i22 = i13;
                                        unsafe2 = unsafe;
                                        aVar2 = aVar;
                                        i18 = i3;
                                        i17 = i2;
                                        t3 = t;
                                        zVar2 = this;
                                    } else {
                                        i5 = i19;
                                        i12 = i12;
                                        i15 = i3;
                                    }
                                } else if (i27 == 2) {
                                    i19 = a((z<T>) t, bArr, i5, i2, i20, j, aVar);
                                    if (i19 != i5) {
                                        bArr2 = bArr;
                                        i23 = i23;
                                        i21 = i12;
                                        i24 = i14;
                                        i20 = i20;
                                        i22 = i13;
                                        unsafe2 = unsafe;
                                        aVar2 = aVar;
                                        i18 = i3;
                                        i17 = i2;
                                        t3 = t;
                                        zVar2 = this;
                                    } else {
                                        i5 = i19;
                                        i12 = i12;
                                        i15 = i3;
                                    }
                                } else {
                                    i15 = i3;
                                }
                            }
                        } else if (i27 == 2) {
                            Internal.ProtobufList protobufList = (Internal.ProtobufList) unsafe2.getObject(t3, j);
                            if (!protobufList.isModifiable()) {
                                int size = protobufList.size();
                                protobufList = protobufList.mutableCopyWithCapacity(size == 0 ? 10 : size * 2);
                                unsafe2.putObject(t3, j, protobufList);
                            }
                            i19 = c.a(zVar2.a(i10), b2, bArr, i9, i2, protobufList, aVar);
                            i21 = b2;
                            i23 = i23;
                            i20 = i10;
                            i22 = i22;
                            i18 = i3;
                            i17 = i2;
                        } else {
                            i20 = i10;
                            i13 = i22;
                            i14 = i24;
                            unsafe = unsafe2;
                            i5 = i9;
                            i12 = b2;
                            i15 = i3;
                        }
                    }
                }
                if (i12 != i15 || i15 == 0) {
                    if (this.h) {
                        aVar2 = aVar;
                        if (aVar2.d != ExtensionRegistryLite.getEmptyRegistry()) {
                            i19 = c.a(i12, bArr, i5, i2, (Object) t, this.g, (ar<UnknownFieldSetLite, UnknownFieldSetLite>) this.q, aVar);
                            bArr2 = bArr;
                            i21 = i12;
                            zVar2 = this;
                            i24 = i14;
                            i22 = i13;
                            i17 = i2;
                            t3 = t;
                            i18 = i15;
                            unsafe2 = unsafe;
                        }
                    } else {
                        aVar2 = aVar;
                    }
                    i19 = c.a(i12, bArr, i5, i2, c(t), aVar);
                    bArr2 = bArr;
                    i21 = i12;
                    zVar2 = this;
                    i24 = i14;
                    i22 = i13;
                    i17 = i2;
                    t3 = t;
                    i18 = i15;
                    unsafe2 = unsafe;
                } else {
                    i4 = i15;
                    i21 = i12;
                    i6 = i14;
                    i7 = i13;
                    i8 = -1;
                    zVar = this;
                }
            } else {
                unsafe = unsafe2;
                i4 = i18;
                zVar = zVar2;
                i5 = i19;
                i6 = i24;
                i7 = i22;
                i8 = -1;
            }
        }
        if (i6 != i8) {
            long j2 = i6;
            t2 = t;
            unsafe.putInt(t2, j2, i7);
        } else {
            t2 = t;
        }
        UnknownFieldSetLite unknownFieldSetLite = null;
        for (int i33 = zVar.m; i33 < zVar.n; i33++) {
            unknownFieldSetLite = (UnknownFieldSetLite) zVar.a((Object) t2, zVar.l[i33], (int) unknownFieldSetLite, (ar<UT, int>) zVar.q);
        }
        if (unknownFieldSetLite != null) {
            zVar.q.b((Object) t2, (T) unknownFieldSetLite);
        }
        if (i4 == 0) {
            if (i5 != i2) {
                throw InvalidProtocolBufferException.i();
            }
        } else if (i5 > i2 || i21 != i4) {
            throw InvalidProtocolBufferException.i();
        }
        return i5;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v12, types: [int] */
    private int b(T t, byte[] bArr, int i, int i2, c.a aVar) throws IOException {
        byte b2;
        int i3;
        int i4;
        int i5;
        z<T> zVar = this;
        T t2 = t;
        byte[] bArr2 = bArr;
        int i6 = i2;
        c.a aVar2 = aVar;
        Unsafe unsafe = b;
        int i7 = -1;
        int i8 = i;
        int i9 = -1;
        int i10 = 0;
        while (i8 < i6) {
            int i11 = i8 + 1;
            byte b3 = bArr2[i8];
            if (b3 < 0) {
                i3 = c.a((int) b3, bArr2, i11, aVar2);
                b2 = aVar2.a;
            } else {
                b2 = b3;
                i3 = i11;
            }
            int i12 = b2 >>> 3;
            int i13 = b2 & 7;
            if (i12 > i9) {
                i4 = zVar.a(i12, i10 / 3);
            } else {
                i4 = zVar.k(i12);
            }
            if (i4 == i7) {
                i9 = i12;
                i5 = i3;
                unsafe = unsafe;
                i7 = i7;
                i10 = 0;
            } else {
                int i14 = zVar.c[i4 + 1];
                int g = g(i14);
                long j = j(i14);
                if (g <= 17) {
                    boolean z = true;
                    switch (g) {
                        case 0:
                            if (i13 != 1) {
                                i9 = i12;
                                i5 = i3;
                                unsafe = unsafe;
                                i10 = i4;
                                i7 = -1;
                                break;
                            } else {
                                at.a(t2, j, c.c(bArr2, i3));
                                i8 = i3 + 8;
                                i9 = i12;
                                i10 = i4;
                                i7 = -1;
                                continue;
                            }
                        case 1:
                            if (i13 != 5) {
                                i9 = i12;
                                i5 = i3;
                                unsafe = unsafe;
                                i10 = i4;
                                i7 = -1;
                                break;
                            } else {
                                at.a((Object) t2, j, c.d(bArr2, i3));
                                i8 = i3 + 4;
                                i9 = i12;
                                i10 = i4;
                                i7 = -1;
                                continue;
                            }
                        case 2:
                        case 3:
                            if (i13 != 0) {
                                i9 = i12;
                                i5 = i3;
                                unsafe = unsafe;
                                i10 = i4;
                                i7 = -1;
                                break;
                            } else {
                                int b4 = c.b(bArr2, i3, aVar2);
                                unsafe.putLong(t, j, aVar2.b);
                                i8 = b4;
                                i9 = i12;
                                i10 = i4;
                                i7 = -1;
                                continue;
                            }
                        case 4:
                        case 11:
                            if (i13 != 0) {
                                i9 = i12;
                                i5 = i3;
                                unsafe = unsafe;
                                i10 = i4;
                                i7 = -1;
                                break;
                            } else {
                                i8 = c.a(bArr2, i3, aVar2);
                                unsafe.putInt(t2, j, aVar2.a);
                                i9 = i12;
                                i10 = i4;
                                i7 = -1;
                                continue;
                            }
                        case 5:
                        case 14:
                            if (i13 != 1) {
                                i10 = i4;
                                i9 = i12;
                                i5 = i3;
                                unsafe = unsafe;
                                i7 = -1;
                                break;
                            } else {
                                unsafe.putLong(t, j, c.b(bArr2, i3));
                                i8 = i3 + 8;
                                i9 = i12;
                                i10 = i4;
                                i7 = -1;
                                continue;
                            }
                        case 6:
                        case 13:
                            if (i13 != 5) {
                                i10 = i4;
                                i9 = i12;
                                i5 = i3;
                                unsafe = unsafe;
                                i7 = -1;
                                break;
                            } else {
                                unsafe.putInt(t2, j, c.a(bArr2, i3));
                                i8 = i3 + 4;
                                i10 = i4;
                                i9 = i12;
                                i7 = -1;
                                continue;
                            }
                        case 7:
                            if (i13 != 0) {
                                i10 = i4;
                                i9 = i12;
                                i5 = i3;
                                unsafe = unsafe;
                                i7 = -1;
                                break;
                            } else {
                                int b5 = c.b(bArr2, i3, aVar2);
                                if (aVar2.b == 0) {
                                    z = false;
                                }
                                at.a(t2, j, z);
                                i8 = b5;
                                i10 = i4;
                                i9 = i12;
                                i7 = -1;
                                continue;
                            }
                        case 8:
                            if (i13 != 2) {
                                i10 = i4;
                                i9 = i12;
                                i5 = i3;
                                unsafe = unsafe;
                                i7 = -1;
                                break;
                            } else {
                                if ((536870912 & i14) == 0) {
                                    i8 = c.c(bArr2, i3, aVar2);
                                } else {
                                    i8 = c.d(bArr2, i3, aVar2);
                                }
                                unsafe.putObject(t2, j, aVar2.c);
                                i10 = i4;
                                i9 = i12;
                                i7 = -1;
                                continue;
                            }
                        case 9:
                            if (i13 != 2) {
                                i10 = i4;
                                i9 = i12;
                                i5 = i3;
                                unsafe = unsafe;
                                i7 = -1;
                                break;
                            } else {
                                i8 = c.a(zVar.a(i4), bArr2, i3, i6, aVar2);
                                Object object = unsafe.getObject(t2, j);
                                if (object == null) {
                                    unsafe.putObject(t2, j, aVar2.c);
                                } else {
                                    unsafe.putObject(t2, j, Internal.a(object, aVar2.c));
                                }
                                i10 = i4;
                                i9 = i12;
                                i7 = -1;
                                continue;
                            }
                        case 10:
                            if (i13 != 2) {
                                i10 = i4;
                                i9 = i12;
                                i5 = i3;
                                unsafe = unsafe;
                                i7 = -1;
                                break;
                            } else {
                                i8 = c.e(bArr2, i3, aVar2);
                                unsafe.putObject(t2, j, aVar2.c);
                                i10 = i4;
                                i9 = i12;
                                i7 = -1;
                                continue;
                            }
                        case 12:
                            if (i13 != 0) {
                                i9 = i12;
                                i5 = i3;
                                unsafe = unsafe;
                                i10 = i4;
                                i7 = -1;
                                break;
                            } else {
                                i8 = c.a(bArr2, i3, aVar2);
                                unsafe.putInt(t2, j, aVar2.a);
                                i9 = i12;
                                i10 = i4;
                                i7 = -1;
                                continue;
                            }
                        case 15:
                            if (i13 != 0) {
                                i9 = i12;
                                i5 = i3;
                                unsafe = unsafe;
                                i10 = i4;
                                i7 = -1;
                                break;
                            } else {
                                i8 = c.a(bArr2, i3, aVar2);
                                unsafe.putInt(t2, j, CodedInputStream.decodeZigZag32(aVar2.a));
                                i9 = i12;
                                i10 = i4;
                                i7 = -1;
                                continue;
                            }
                        case 16:
                            if (i13 != 0) {
                                i10 = i4;
                                i9 = i12;
                                i5 = i3;
                                unsafe = unsafe;
                                i7 = -1;
                                break;
                            } else {
                                int b6 = c.b(bArr2, i3, aVar2);
                                unsafe.putLong(t, j, CodedInputStream.decodeZigZag64(aVar2.b));
                                i8 = b6;
                                i9 = i12;
                                i10 = i4;
                                i7 = -1;
                                continue;
                            }
                        default:
                            i10 = i4;
                            i9 = i12;
                            i5 = i3;
                            unsafe = unsafe;
                            i7 = -1;
                            break;
                    }
                } else if (g != 27) {
                    i10 = i4;
                    if (g <= 49) {
                        i9 = i12;
                        unsafe = unsafe;
                        i7 = -1;
                        i8 = a((z<T>) t, bArr, i3, i2, b2, i12, i13, i10, i14, g, j, aVar);
                        if (i8 != i3) {
                            t2 = t;
                            bArr2 = bArr;
                            aVar2 = aVar;
                            unsafe = unsafe;
                            i10 = i10;
                            i9 = i9;
                            i7 = -1;
                            i6 = i2;
                            zVar = this;
                        } else {
                            i5 = i8;
                        }
                    } else {
                        i9 = i12;
                        i5 = i3;
                        unsafe = unsafe;
                        i7 = -1;
                        if (g != 50) {
                            i8 = a((z<T>) t, bArr, i5, i2, b2, i9, i13, i14, g, j, i10, aVar);
                            if (i8 != i5) {
                                t2 = t;
                                bArr2 = bArr;
                                aVar2 = aVar;
                                unsafe = unsafe;
                                i10 = i10;
                                i9 = i9;
                                i7 = -1;
                                i6 = i2;
                                zVar = this;
                            } else {
                                i5 = i8;
                            }
                        } else if (i13 == 2) {
                            i8 = a((z<T>) t, bArr, i5, i2, i10, j, aVar);
                            if (i8 != i5) {
                                t2 = t;
                                bArr2 = bArr;
                                aVar2 = aVar;
                                unsafe = unsafe;
                                i10 = i10;
                                i9 = i9;
                                i7 = -1;
                                i6 = i2;
                                zVar = this;
                            } else {
                                i5 = i8;
                            }
                        }
                    }
                } else if (i13 == 2) {
                    Internal.ProtobufList protobufList = (Internal.ProtobufList) unsafe.getObject(t2, j);
                    if (!protobufList.isModifiable()) {
                        int size = protobufList.size();
                        protobufList = protobufList.mutableCopyWithCapacity(size == 0 ? 10 : size * 2);
                        unsafe.putObject(t2, j, protobufList);
                    }
                    i8 = c.a(zVar.a(i4), b2, bArr, i3, i2, protobufList, aVar);
                    i9 = i12;
                    i10 = i4;
                    i7 = -1;
                } else {
                    i10 = i4;
                    i9 = i12;
                    i5 = i3;
                    unsafe = unsafe;
                    i7 = -1;
                }
            }
            i8 = c.a(b2, bArr, i5, i2, c(t), aVar);
            t2 = t;
            bArr2 = bArr;
            aVar2 = aVar;
            i6 = i2;
            zVar = this;
        }
        if (i8 == i6) {
            return i8;
        }
        throw InvalidProtocolBufferException.i();
    }

    @Override // com.google.protobuf.am
    public void a(T t, byte[] bArr, int i, int i2, c.a aVar) throws IOException {
        if (this.j) {
            b(t, bArr, i, i2, aVar);
        } else {
            a((z<T>) t, bArr, i, i2, 0, aVar);
        }
    }

    @Override // com.google.protobuf.am
    public void d(T t) {
        int i;
        int i2 = this.m;
        while (true) {
            i = this.n;
            if (i2 >= i) {
                break;
            }
            long j = j(e(this.l[i2]));
            Object f = at.f(t, j);
            if (f != null) {
                at.a(t, j, this.s.d(f));
            }
            i2++;
        }
        int length = this.l.length;
        while (i < length) {
            this.p.b(t, this.l[i]);
            i++;
        }
        this.q.d(t);
        if (this.h) {
            this.r.c(t);
        }
    }

    private final <K, V> void a(Object obj, int i, Object obj2, ExtensionRegistryLite extensionRegistryLite, ak akVar) throws IOException {
        long j = j(e(i));
        Object f = at.f(obj, j);
        if (f == null) {
            f = this.s.e(obj2);
            at.a(obj, j, f);
        } else if (this.s.c(f)) {
            Object e = this.s.e(obj2);
            this.s.a(e, f);
            at.a(obj, j, e);
            f = e;
        }
        akVar.a(this.s.a(f), this.s.f(obj2), extensionRegistryLite);
    }

    private final <UT, UB> UB a(Object obj, int i, UB ub, ar<UT, UB> arVar) {
        Internal.EnumVerifier c;
        int d = d(i);
        Object f = at.f(obj, j(e(i)));
        return (f == null || (c = c(i)) == null) ? ub : (UB) a(i, d, this.s.a(f), c, (Internal.EnumVerifier) ub, (ar<UT, Internal.EnumVerifier>) arVar);
    }

    private final <K, V, UT, UB> UB a(int i, int i2, Map<K, V> map, Internal.EnumVerifier enumVerifier, UB ub, ar<UT, UB> arVar) {
        MapEntryLite.a<?, ?> f = this.s.f(b(i));
        Iterator<Map.Entry<K, V>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<K, V> next = it.next();
            if (!enumVerifier.isInRange(((Integer) next.getValue()).intValue())) {
                if (ub == null) {
                    ub = arVar.a();
                }
                ByteString.e b2 = ByteString.b(MapEntryLite.a(f, next.getKey(), next.getValue()));
                try {
                    MapEntryLite.a(b2.b(), f, next.getKey(), next.getValue());
                    arVar.a((ar<UT, UB>) ub, i2, b2.a());
                    it.remove();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return ub;
    }

    /* JADX WARN: Code restructure failed: missing block: B:62:0x008d, code lost:
        continue;
     */
    @Override // com.google.protobuf.am
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean e(T r13) {
        /*
            r12 = this;
            r0 = 0
            r1 = -1
            r3 = r0
            r2 = r1
            r1 = r3
        L_0x0005:
            int r4 = r12.m
            r5 = 1
            if (r1 >= r4) goto L_0x0091
            int[] r4 = r12.l
            r4 = r4[r1]
            int r6 = r12.d(r4)
            int r7 = r12.e(r4)
            boolean r8 = r12.j
            if (r8 != 0) goto L_0x0033
            int[] r8 = r12.c
            int r9 = r4 + 2
            r8 = r8[r9]
            r9 = 1048575(0xfffff, float:1.469367E-39)
            r9 = r9 & r8
            int r8 = r8 >>> 20
            int r5 = r5 << r8
            if (r9 == r2) goto L_0x0034
            sun.misc.Unsafe r2 = com.google.protobuf.z.b
            long r10 = (long) r9
            int r2 = r2.getInt(r13, r10)
            r3 = r2
            r2 = r9
            goto L_0x0034
        L_0x0033:
            r5 = r0
        L_0x0034:
            boolean r8 = h(r7)
            if (r8 == 0) goto L_0x0041
            boolean r8 = r12.a(r13, r4, r3, r5)
            if (r8 != 0) goto L_0x0041
            return r0
        L_0x0041:
            int r8 = g(r7)
            r9 = 9
            if (r8 == r9) goto L_0x007c
            r9 = 17
            if (r8 == r9) goto L_0x007c
            r5 = 27
            if (r8 == r5) goto L_0x0075
            r5 = 60
            if (r8 == r5) goto L_0x0064
            r5 = 68
            if (r8 == r5) goto L_0x0064
            switch(r8) {
                case 49: goto L_0x0075;
                case 50: goto L_0x005d;
                default: goto L_0x005c;
            }
        L_0x005c:
            goto L_0x008d
        L_0x005d:
            boolean r4 = r12.b(r13, r7, r4)
            if (r4 != 0) goto L_0x008d
            return r0
        L_0x0064:
            boolean r5 = r12.c(r13, r6, r4)
            if (r5 == 0) goto L_0x008d
            com.google.protobuf.am r4 = r12.a(r4)
            boolean r4 = a(r13, r7, r4)
            if (r4 != 0) goto L_0x008d
            return r0
        L_0x0075:
            boolean r4 = r12.a(r13, r7, r4)
            if (r4 != 0) goto L_0x008d
            return r0
        L_0x007c:
            boolean r5 = r12.a(r13, r4, r3, r5)
            if (r5 == 0) goto L_0x008d
            com.google.protobuf.am r4 = r12.a(r4)
            boolean r4 = a(r13, r7, r4)
            if (r4 != 0) goto L_0x008d
            return r0
        L_0x008d:
            int r1 = r1 + 1
            goto L_0x0005
        L_0x0091:
            boolean r1 = r12.h
            if (r1 == 0) goto L_0x00a2
            com.google.protobuf.j<?> r1 = r12.r
            com.google.protobuf.FieldSet r13 = r1.a(r13)
            boolean r13 = r13.i()
            if (r13 != 0) goto L_0x00a2
            return r0
        L_0x00a2:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.z.e(java.lang.Object):boolean");
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static boolean a(Object obj, int i, am amVar) {
        return amVar.e(at.f(obj, j(i)));
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <N> boolean a(Object obj, int i, int i2) {
        List list = (List) at.f(obj, j(i));
        if (list.isEmpty()) {
            return true;
        }
        am a2 = a(i2);
        for (int i3 = 0; i3 < list.size(); i3++) {
            if (!a2.e(list.get(i3))) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v11 */
    /* JADX WARN: Type inference failed for: r5v6 */
    /* JADX WARN: Type inference failed for: r5v8, types: [com.google.protobuf.am] */
    private boolean b(T t, int i, int i2) {
        Map<?, ?> b2 = this.s.b(at.f(t, j(i)));
        if (b2.isEmpty()) {
            return true;
        }
        if (this.s.f(b(i2)).c.getJavaType() != WireFormat.JavaType.MESSAGE) {
            return true;
        }
        am<T> amVar = 0;
        for (Object obj : b2.values()) {
            if (amVar == null) {
                amVar = ah.a().a((Class) obj.getClass());
            }
            boolean e = amVar.e(obj);
            amVar = amVar;
            if (!e) {
                return false;
            }
        }
        return true;
    }

    private void a(int i, Object obj, Writer writer) throws IOException {
        if (obj instanceof String) {
            writer.a(i, (String) obj);
        } else {
            writer.a(i, (ByteString) obj);
        }
    }

    private void a(Object obj, int i, ak akVar) throws IOException {
        if (i(i)) {
            at.a(obj, j(i), akVar.m());
        } else if (this.i) {
            at.a(obj, j(i), akVar.l());
        } else {
            at.a(obj, j(i), akVar.n());
        }
    }

    private void b(Object obj, int i, ak akVar) throws IOException {
        if (i(i)) {
            akVar.j(this.p.a(obj, j(i)));
        } else {
            akVar.i(this.p.a(obj, j(i)));
        }
    }

    private <E> void a(Object obj, int i, ak akVar, am<E> amVar, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        akVar.a(this.p.a(obj, j(i)), amVar, extensionRegistryLite);
    }

    private <E> void a(Object obj, long j, ak akVar, am<E> amVar, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        akVar.b(this.p.a(obj, j), amVar, extensionRegistryLite);
    }

    private int d(int i) {
        return this.c[i];
    }

    private int e(int i) {
        return this.c[i + 1];
    }

    private int f(int i) {
        return this.c[i + 2];
    }

    private static <T> double b(T t, long j) {
        return at.e(t, j);
    }

    private static <T> float c(T t, long j) {
        return at.d(t, j);
    }

    private static <T> int d(T t, long j) {
        return at.a(t, j);
    }

    private static <T> long e(T t, long j) {
        return at.b(t, j);
    }

    private static <T> boolean f(T t, long j) {
        return at.c(t, j);
    }

    private static <T> double g(T t, long j) {
        return ((Double) at.f(t, j)).doubleValue();
    }

    private static <T> float h(T t, long j) {
        return ((Float) at.f(t, j)).floatValue();
    }

    private static <T> int i(T t, long j) {
        return ((Integer) at.f(t, j)).intValue();
    }

    private static <T> long j(T t, long j) {
        return ((Long) at.f(t, j)).longValue();
    }

    private static <T> boolean k(T t, long j) {
        return ((Boolean) at.f(t, j)).booleanValue();
    }

    private boolean e(T t, T t2, int i) {
        return a((z<T>) t, i) == a((z<T>) t2, i);
    }

    private boolean a(T t, int i, int i2, int i3) {
        if (this.j) {
            return a((z<T>) t, i);
        }
        return (i2 & i3) != 0;
    }

    private boolean a(T t, int i) {
        if (this.j) {
            int e = e(i);
            long j = j(e);
            switch (g(e)) {
                case 0:
                    return at.e(t, j) != 0.0d;
                case 1:
                    return at.d(t, j) != 0.0f;
                case 2:
                    return at.b(t, j) != 0;
                case 3:
                    return at.b(t, j) != 0;
                case 4:
                    return at.a(t, j) != 0;
                case 5:
                    return at.b(t, j) != 0;
                case 6:
                    return at.a(t, j) != 0;
                case 7:
                    return at.c(t, j);
                case 8:
                    Object f = at.f(t, j);
                    if (f instanceof String) {
                        return !((String) f).isEmpty();
                    }
                    if (f instanceof ByteString) {
                        return !ByteString.EMPTY.equals(f);
                    }
                    throw new IllegalArgumentException();
                case 9:
                    return at.f(t, j) != null;
                case 10:
                    return !ByteString.EMPTY.equals(at.f(t, j));
                case 11:
                    return at.a(t, j) != 0;
                case 12:
                    return at.a(t, j) != 0;
                case 13:
                    return at.a(t, j) != 0;
                case 14:
                    return at.b(t, j) != 0;
                case 15:
                    return at.a(t, j) != 0;
                case 16:
                    return at.b(t, j) != 0;
                case 17:
                    return at.f(t, j) != null;
                default:
                    throw new IllegalArgumentException();
            }
        } else {
            int f2 = f(i);
            return (at.a(t, (long) (f2 & 1048575)) & (1 << (f2 >>> 20))) != 0;
        }
    }

    private void b(T t, int i) {
        if (!this.j) {
            int f = f(i);
            long j = f & 1048575;
            at.a((Object) t, j, at.a(t, j) | (1 << (f >>> 20)));
        }
    }

    private boolean c(T t, int i, int i2) {
        return at.a(t, (long) (f(i2) & 1048575)) == i;
    }

    private boolean f(T t, T t2, int i) {
        long f = f(i) & 1048575;
        return at.a(t, f) == at.a(t2, f);
    }

    private void d(T t, int i, int i2) {
        at.a((Object) t, f(i2) & 1048575, i);
    }

    private int k(int i) {
        if (i < this.e || i > this.f) {
            return -1;
        }
        return b(i, 0);
    }

    private int a(int i, int i2) {
        if (i < this.e || i > this.f) {
            return -1;
        }
        return b(i, i2);
    }

    private int b(int i, int i2) {
        int length = (this.c.length / 3) - 1;
        while (i2 <= length) {
            int i3 = (length + i2) >>> 1;
            int i4 = i3 * 3;
            int d = d(i4);
            if (i == d) {
                return i4;
            }
            if (i < d) {
                length = i3 - 1;
            } else {
                i2 = i3 + 1;
            }
        }
        return -1;
    }
}
