package io.realm.internal.objectstore;

import io.realm.ImportFlag;
import io.realm.MutableRealmInteger;
import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.internal.NativeContext;
import io.realm.internal.OsSharedRealm;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Table;
import io.realm.internal.UncheckedRow;
import java.io.Closeable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/* loaded from: classes5.dex */
public class OsObjectBuilder implements Closeable {
    private static a<? extends RealmModel> f = new a<RealmModel>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.1
        public void a(long j2, RealmModel realmModel) {
            OsObjectBuilder.nativeAddIntegerListItem(j2, ((UncheckedRow) ((RealmObjectProxy) realmModel).realmGet$proxyState().getRow$realm()).getNativePtr());
        }
    };
    private static a<String> g = new a<String>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.5
        public void a(long j2, String str) {
            OsObjectBuilder.nativeAddStringListItem(j2, str);
        }
    };
    private static a<Byte> h = new a<Byte>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.6
        public void a(long j2, Byte b) {
            OsObjectBuilder.nativeAddIntegerListItem(j2, b.longValue());
        }
    };
    private static a<Short> i = new a<Short>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.7
        public void a(long j2, Short sh) {
            OsObjectBuilder.nativeAddIntegerListItem(j2, sh.shortValue());
        }
    };
    private static a<Integer> j = new a<Integer>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.8
        public void a(long j2, Integer num) {
            OsObjectBuilder.nativeAddIntegerListItem(j2, num.intValue());
        }
    };
    private static a<Long> k = new a<Long>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.9
        public void a(long j2, Long l2) {
            OsObjectBuilder.nativeAddIntegerListItem(j2, l2.longValue());
        }
    };
    private static a<Boolean> l = new a<Boolean>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.10
        public void a(long j2, Boolean bool) {
            OsObjectBuilder.nativeAddBooleanListItem(j2, bool.booleanValue());
        }
    };
    private static a<Float> m = new a<Float>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.11
        public void a(long j2, Float f2) {
            OsObjectBuilder.nativeAddFloatListItem(j2, f2.floatValue());
        }
    };
    private static a<Double> n = new a<Double>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.12
        public void a(long j2, Double d) {
            OsObjectBuilder.nativeAddDoubleListItem(j2, d.doubleValue());
        }
    };
    private static a<Date> o = new a<Date>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.2
        public void a(long j2, Date date) {
            OsObjectBuilder.nativeAddDateListItem(j2, date.getTime());
        }
    };
    private static a<byte[]> p = new a<byte[]>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.3
        public void a(long j2, byte[] bArr) {
            OsObjectBuilder.nativeAddByteArrayListItem(j2, bArr);
        }
    };
    private static a<MutableRealmInteger> q = new a<MutableRealmInteger>() { // from class: io.realm.internal.objectstore.OsObjectBuilder.4
        public void a(long j2, MutableRealmInteger mutableRealmInteger) {
            Long l2 = mutableRealmInteger.get();
            if (l2 == null) {
                OsObjectBuilder.nativeAddNullListItem(j2);
            } else {
                OsObjectBuilder.nativeAddIntegerListItem(j2, l2.longValue());
            }
        }
    };
    private final Table a;
    private final long b;
    private final long c = nativeCreateBuilder();
    private final long d;
    private final NativeContext e;
    private final boolean r;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public interface a<T> {
        void a(long j, T t);
    }

    private static native void nativeAddBoolean(long j2, long j3, boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAddBooleanListItem(long j2, boolean z);

    private static native void nativeAddByteArray(long j2, long j3, byte[] bArr);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAddByteArrayListItem(long j2, byte[] bArr);

    private static native void nativeAddDate(long j2, long j3, long j4);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAddDateListItem(long j2, long j3);

    private static native void nativeAddDouble(long j2, long j3, double d);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAddDoubleListItem(long j2, double d);

    private static native void nativeAddFloat(long j2, long j3, float f2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAddFloatListItem(long j2, float f2);

    private static native void nativeAddInteger(long j2, long j3, long j4);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAddIntegerListItem(long j2, long j3);

    private static native void nativeAddNull(long j2, long j3);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAddNullListItem(long j2);

    private static native void nativeAddObject(long j2, long j3, long j4);

    private static native void nativeAddObjectList(long j2, long j3, long[] jArr);

    private static native void nativeAddString(long j2, long j3, String str);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAddStringListItem(long j2, String str);

    private static native long nativeCreateBuilder();

    private static native long nativeCreateOrUpdate(long j2, long j3, long j4, boolean z, boolean z2);

    private static native void nativeDestroyBuilder(long j2);

    private static native long nativeStartList(long j2);

    private static native void nativeStopList(long j2, long j3, long j4);

    public OsObjectBuilder(Table table, Set<ImportFlag> set) {
        OsSharedRealm sharedRealm = table.getSharedRealm();
        this.b = sharedRealm.getNativePtr();
        this.a = table;
        this.a.getColumnNames();
        this.d = table.getNativePtr();
        this.e = sharedRealm.context;
        this.r = set.contains(ImportFlag.CHECK_SAME_VALUES_BEFORE_SET);
    }

    public void addInteger(long j2, Byte b) {
        if (b == null) {
            nativeAddNull(this.c, j2);
        } else {
            nativeAddInteger(this.c, j2, b.byteValue());
        }
    }

    public void addInteger(long j2, Short sh) {
        if (sh == null) {
            nativeAddNull(this.c, j2);
        } else {
            nativeAddInteger(this.c, j2, sh.shortValue());
        }
    }

    public void addInteger(long j2, Integer num) {
        if (num == null) {
            nativeAddNull(this.c, j2);
        } else {
            nativeAddInteger(this.c, j2, num.intValue());
        }
    }

    public void addInteger(long j2, Long l2) {
        if (l2 == null) {
            nativeAddNull(this.c, j2);
        } else {
            nativeAddInteger(this.c, j2, l2.longValue());
        }
    }

    public void addMutableRealmInteger(long j2, MutableRealmInteger mutableRealmInteger) {
        if (mutableRealmInteger == null || mutableRealmInteger.get() == null) {
            nativeAddNull(this.c, j2);
        } else {
            nativeAddInteger(this.c, j2, mutableRealmInteger.get().longValue());
        }
    }

    public void addString(long j2, String str) {
        if (str == null) {
            nativeAddNull(this.c, j2);
        } else {
            nativeAddString(this.c, j2, str);
        }
    }

    public void addFloat(long j2, Float f2) {
        if (f2 == null) {
            nativeAddNull(this.c, j2);
        } else {
            nativeAddFloat(this.c, j2, f2.floatValue());
        }
    }

    public void addDouble(long j2, Double d) {
        if (d == null) {
            nativeAddNull(this.c, j2);
        } else {
            nativeAddDouble(this.c, j2, d.doubleValue());
        }
    }

    public void addBoolean(long j2, Boolean bool) {
        if (bool == null) {
            nativeAddNull(this.c, j2);
        } else {
            nativeAddBoolean(this.c, j2, bool.booleanValue());
        }
    }

    public void addDate(long j2, Date date) {
        if (date == null) {
            nativeAddNull(this.c, j2);
        } else {
            nativeAddDate(this.c, j2, date.getTime());
        }
    }

    public void addByteArray(long j2, byte[] bArr) {
        if (bArr == null) {
            nativeAddNull(this.c, j2);
        } else {
            nativeAddByteArray(this.c, j2, bArr);
        }
    }

    public void addNull(long j2) {
        nativeAddNull(this.c, j2);
    }

    public void addObject(long j2, RealmModel realmModel) {
        if (realmModel == null) {
            nativeAddNull(this.c, j2);
        } else {
            nativeAddObject(this.c, j2, ((UncheckedRow) ((RealmObjectProxy) realmModel).realmGet$proxyState().getRow$realm()).getNativePtr());
        }
    }

    private <T> void a(long j2, long j3, List<T> list, a<T> aVar) {
        if (list != null) {
            long nativeStartList = nativeStartList(list.size());
            for (int i2 = 0; i2 < list.size(); i2++) {
                T t = list.get(i2);
                if (t == null) {
                    nativeAddNullListItem(nativeStartList);
                } else {
                    aVar.a(nativeStartList, t);
                }
            }
            nativeStopList(j2, j3, nativeStartList);
            return;
        }
        b(j3);
    }

    public <T extends RealmModel> void addObjectList(long j2, RealmList<T> realmList) {
        if (realmList != null) {
            long[] jArr = new long[realmList.size()];
            for (int i2 = 0; i2 < realmList.size(); i2++) {
                RealmObjectProxy realmObjectProxy = (RealmObjectProxy) realmList.get(i2);
                if (realmObjectProxy != null) {
                    jArr[i2] = ((UncheckedRow) realmObjectProxy.realmGet$proxyState().getRow$realm()).getNativePtr();
                } else {
                    throw new IllegalArgumentException("Null values are not allowed in RealmLists containing Realm models");
                }
            }
            nativeAddObjectList(this.c, j2, jArr);
            return;
        }
        nativeAddObjectList(this.c, j2, new long[0]);
    }

    public void addStringList(long j2, RealmList<String> realmList) {
        a(this.c, j2, realmList, g);
    }

    public void addByteList(long j2, RealmList<Byte> realmList) {
        a(this.c, j2, realmList, h);
    }

    public void addShortList(long j2, RealmList<Short> realmList) {
        a(this.c, j2, realmList, i);
    }

    public void addIntegerList(long j2, RealmList<Integer> realmList) {
        a(this.c, j2, realmList, j);
    }

    public void addLongList(long j2, RealmList<Long> realmList) {
        a(this.c, j2, realmList, k);
    }

    public void addBooleanList(long j2, RealmList<Boolean> realmList) {
        a(this.c, j2, realmList, l);
    }

    public void addFloatList(long j2, RealmList<Float> realmList) {
        a(this.c, j2, realmList, m);
    }

    public void addDoubleList(long j2, RealmList<Double> realmList) {
        a(this.c, j2, realmList, n);
    }

    public void addDateList(long j2, RealmList<Date> realmList) {
        a(this.c, j2, realmList, o);
    }

    public void addByteArrayList(long j2, RealmList<byte[]> realmList) {
        a(this.c, j2, realmList, p);
    }

    public void addMutableRealmIntegerList(long j2, RealmList<MutableRealmInteger> realmList) {
        a(this.c, j2, realmList, q);
    }

    private void b(long j2) {
        nativeStopList(this.c, j2, nativeStartList(0L));
    }

    public void updateExistingObject() {
        try {
            nativeCreateOrUpdate(this.b, this.d, this.c, true, this.r);
        } finally {
            close();
        }
    }

    public UncheckedRow createNewObject() {
        try {
            return new UncheckedRow(this.e, this.a, nativeCreateOrUpdate(this.b, this.d, this.c, false, false));
        } finally {
            close();
        }
    }

    public long getNativePtr() {
        return this.c;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        nativeDestroyBuilder(this.c);
    }
}
