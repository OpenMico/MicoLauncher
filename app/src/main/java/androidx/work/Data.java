package androidx.work;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public final class Data {
    @SuppressLint({"MinMaxConstant"})
    public static final int MAX_DATA_BYTES = 10240;
    Map<String, Object> a;
    private static final String b = Logger.tagWithPrefix("Data");
    public static final Data EMPTY = new Builder().build();

    Data() {
    }

    public Data(@NonNull Data data) {
        this.a = new HashMap(data.a);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public Data(@NonNull Map<String, ?> map) {
        this.a = new HashMap(map);
    }

    public boolean getBoolean(@NonNull String str, boolean z) {
        Object obj = this.a.get(str);
        return obj instanceof Boolean ? ((Boolean) obj).booleanValue() : z;
    }

    @Nullable
    public boolean[] getBooleanArray(@NonNull String str) {
        Object obj = this.a.get(str);
        if (obj instanceof Boolean[]) {
            return convertToPrimitiveArray((Boolean[]) obj);
        }
        return null;
    }

    public byte getByte(@NonNull String str, byte b2) {
        Object obj = this.a.get(str);
        return obj instanceof Byte ? ((Byte) obj).byteValue() : b2;
    }

    @Nullable
    public byte[] getByteArray(@NonNull String str) {
        Object obj = this.a.get(str);
        if (obj instanceof Byte[]) {
            return convertToPrimitiveArray((Byte[]) obj);
        }
        return null;
    }

    public int getInt(@NonNull String str, int i) {
        Object obj = this.a.get(str);
        return obj instanceof Integer ? ((Integer) obj).intValue() : i;
    }

    @Nullable
    public int[] getIntArray(@NonNull String str) {
        Object obj = this.a.get(str);
        if (obj instanceof Integer[]) {
            return convertToPrimitiveArray((Integer[]) obj);
        }
        return null;
    }

    public long getLong(@NonNull String str, long j) {
        Object obj = this.a.get(str);
        return obj instanceof Long ? ((Long) obj).longValue() : j;
    }

    @Nullable
    public long[] getLongArray(@NonNull String str) {
        Object obj = this.a.get(str);
        if (obj instanceof Long[]) {
            return convertToPrimitiveArray((Long[]) obj);
        }
        return null;
    }

    public float getFloat(@NonNull String str, float f) {
        Object obj = this.a.get(str);
        return obj instanceof Float ? ((Float) obj).floatValue() : f;
    }

    @Nullable
    public float[] getFloatArray(@NonNull String str) {
        Object obj = this.a.get(str);
        if (obj instanceof Float[]) {
            return convertToPrimitiveArray((Float[]) obj);
        }
        return null;
    }

    public double getDouble(@NonNull String str, double d) {
        Object obj = this.a.get(str);
        return obj instanceof Double ? ((Double) obj).doubleValue() : d;
    }

    @Nullable
    public double[] getDoubleArray(@NonNull String str) {
        Object obj = this.a.get(str);
        if (obj instanceof Double[]) {
            return convertToPrimitiveArray((Double[]) obj);
        }
        return null;
    }

    @Nullable
    public String getString(@NonNull String str) {
        Object obj = this.a.get(str);
        if (obj instanceof String) {
            return (String) obj;
        }
        return null;
    }

    @Nullable
    public String[] getStringArray(@NonNull String str) {
        Object obj = this.a.get(str);
        if (obj instanceof String[]) {
            return (String[]) obj;
        }
        return null;
    }

    @NonNull
    public Map<String, Object> getKeyValueMap() {
        return Collections.unmodifiableMap(this.a);
    }

    @NonNull
    public byte[] toByteArray() {
        return toByteArrayInternal(this);
    }

    public <T> boolean hasKeyWithValueOfType(@NonNull String str, @NonNull Class<T> cls) {
        Object obj = this.a.get(str);
        return obj != null && cls.isAssignableFrom(obj.getClass());
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    @VisibleForTesting
    public int size() {
        return this.a.size();
    }

    /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
        jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 1, insn: 0x006c: MOVE  (r2 I:??[OBJECT, ARRAY]) = (r1 I:??[OBJECT, ARRAY]), block:B:24:0x006c
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
        	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
        	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:41)
        */
    @androidx.annotation.NonNull
    @androidx.room.TypeConverter
    @androidx.annotation.RestrictTo({androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP})
    public static byte[] toByteArrayInternal(
    /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
        jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 1, insn: 0x006c: MOVE  (r2 I:??[OBJECT, ARRAY]) = (r1 I:??[OBJECT, ARRAY]), block:B:24:0x006c
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
        	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
        */
    /*  JADX ERROR: Method generation error
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r4v0 ??
        	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:233)
        	at jadx.core.codegen.MethodGen.addMethodArguments(MethodGen.java:209)
        	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:162)
        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:364)
        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:304)
        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:270)
        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(Unknown Source)
        	at java.base/java.util.ArrayList.forEach(Unknown Source)
        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(Unknown Source)
        	at java.base/java.util.stream.Sink$ChainedReference.end(Unknown Source)
        */

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0050 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r0v0, types: [int] */
    /* JADX WARN: Type inference failed for: r0v3, types: [java.util.Map] */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r0v8 */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v4, types: [java.io.ByteArrayInputStream, java.io.InputStream] */
    /* JADX WARN: Unknown variable types count: 1 */
    @androidx.annotation.NonNull
    @androidx.room.TypeConverter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static androidx.work.Data fromByteArray(@androidx.annotation.NonNull byte[] r6) {
        /*
            int r0 = r6.length
            r1 = 10240(0x2800, float:1.4349E-41)
            if (r0 > r1) goto L_0x008a
            java.util.HashMap r0 = new java.util.HashMap
            r0.<init>()
            java.io.ByteArrayInputStream r1 = new java.io.ByteArrayInputStream
            r1.<init>(r6)
            r6 = 0
            java.io.ObjectInputStream r2 = new java.io.ObjectInputStream     // Catch: IOException -> 0x0043, ClassNotFoundException -> 0x0041, all -> 0x003d
            r2.<init>(r1)     // Catch: IOException -> 0x0043, ClassNotFoundException -> 0x0041, all -> 0x003d
            int r6 = r2.readInt()     // Catch: IOException -> 0x003b, ClassNotFoundException -> 0x0039, all -> 0x006e
        L_0x0019:
            if (r6 <= 0) goto L_0x0029
            java.lang.String r3 = r2.readUTF()     // Catch: IOException -> 0x003b, ClassNotFoundException -> 0x0039, all -> 0x006e
            java.lang.Object r4 = r2.readObject()     // Catch: IOException -> 0x003b, ClassNotFoundException -> 0x0039, all -> 0x006e
            r0.put(r3, r4)     // Catch: IOException -> 0x003b, ClassNotFoundException -> 0x0039, all -> 0x006e
            int r6 = r6 + (-1)
            goto L_0x0019
        L_0x0029:
            r2.close()     // Catch: IOException -> 0x002d
            goto L_0x0035
        L_0x002d:
            r6 = move-exception
            java.lang.String r2 = androidx.work.Data.b
            java.lang.String r3 = "Error in Data#fromByteArray: "
            android.util.Log.e(r2, r3, r6)
        L_0x0035:
            r1.close()     // Catch: IOException -> 0x0060
            goto L_0x0068
        L_0x0039:
            r6 = move-exception
            goto L_0x0047
        L_0x003b:
            r6 = move-exception
            goto L_0x0047
        L_0x003d:
            r0 = move-exception
            r2 = r6
            r6 = r0
            goto L_0x006f
        L_0x0041:
            r2 = move-exception
            goto L_0x0044
        L_0x0043:
            r2 = move-exception
        L_0x0044:
            r5 = r2
            r2 = r6
            r6 = r5
        L_0x0047:
            java.lang.String r3 = androidx.work.Data.b     // Catch: all -> 0x006e
            java.lang.String r4 = "Error in Data#fromByteArray: "
            android.util.Log.e(r3, r4, r6)     // Catch: all -> 0x006e
            if (r2 == 0) goto L_0x005c
            r2.close()     // Catch: IOException -> 0x0054
            goto L_0x005c
        L_0x0054:
            r6 = move-exception
            java.lang.String r2 = androidx.work.Data.b
            java.lang.String r3 = "Error in Data#fromByteArray: "
            android.util.Log.e(r2, r3, r6)
        L_0x005c:
            r1.close()     // Catch: IOException -> 0x0060
            goto L_0x0068
        L_0x0060:
            r6 = move-exception
            java.lang.String r1 = androidx.work.Data.b
            java.lang.String r2 = "Error in Data#fromByteArray: "
            android.util.Log.e(r1, r2, r6)
        L_0x0068:
            androidx.work.Data r6 = new androidx.work.Data
            r6.<init>(r0)
            return r6
        L_0x006e:
            r6 = move-exception
        L_0x006f:
            if (r2 == 0) goto L_0x007d
            r2.close()     // Catch: IOException -> 0x0075
            goto L_0x007d
        L_0x0075:
            r0 = move-exception
            java.lang.String r2 = androidx.work.Data.b
            java.lang.String r3 = "Error in Data#fromByteArray: "
            android.util.Log.e(r2, r3, r0)
        L_0x007d:
            r1.close()     // Catch: IOException -> 0x0081
            goto L_0x0089
        L_0x0081:
            r0 = move-exception
            java.lang.String r1 = androidx.work.Data.b
            java.lang.String r2 = "Error in Data#fromByteArray: "
            android.util.Log.e(r1, r2, r0)
        L_0x0089:
            throw r6
        L_0x008a:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r0 = "Data cannot occupy more than 10240 bytes when serialized"
            r6.<init>(r0)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.work.Data.fromByteArray(byte[]):androidx.work.Data");
    }

    public boolean equals(Object obj) {
        boolean z;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Data data = (Data) obj;
        Set<String> keySet = this.a.keySet();
        if (!keySet.equals(data.a.keySet())) {
            return false;
        }
        for (String str : keySet) {
            Object obj2 = this.a.get(str);
            Object obj3 = data.a.get(str);
            if (obj2 == null || obj3 == null) {
                if (obj2 == obj3) {
                    z = true;
                    continue;
                } else {
                    z = false;
                    continue;
                }
            } else if (!(obj2 instanceof Object[]) || !(obj3 instanceof Object[])) {
                z = obj2.equals(obj3);
                continue;
            } else {
                z = Arrays.deepEquals((Object[]) obj2, (Object[]) obj3);
                continue;
            }
            if (!z) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        return this.a.hashCode() * 31;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder("Data {");
        if (!this.a.isEmpty()) {
            for (String str : this.a.keySet()) {
                sb.append(str);
                sb.append(" : ");
                Object obj = this.a.get(str);
                if (obj instanceof Object[]) {
                    sb.append(Arrays.toString((Object[]) obj));
                } else {
                    sb.append(obj);
                }
                sb.append(", ");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static Boolean[] convertPrimitiveBooleanArray(@NonNull boolean[] zArr) {
        Boolean[] boolArr = new Boolean[zArr.length];
        for (int i = 0; i < zArr.length; i++) {
            boolArr[i] = Boolean.valueOf(zArr[i]);
        }
        return boolArr;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static Byte[] convertPrimitiveByteArray(@NonNull byte[] bArr) {
        Byte[] bArr2 = new Byte[bArr.length];
        for (int i = 0; i < bArr.length; i++) {
            bArr2[i] = Byte.valueOf(bArr[i]);
        }
        return bArr2;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static Integer[] convertPrimitiveIntArray(@NonNull int[] iArr) {
        Integer[] numArr = new Integer[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            numArr[i] = Integer.valueOf(iArr[i]);
        }
        return numArr;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static Long[] convertPrimitiveLongArray(@NonNull long[] jArr) {
        Long[] lArr = new Long[jArr.length];
        for (int i = 0; i < jArr.length; i++) {
            lArr[i] = Long.valueOf(jArr[i]);
        }
        return lArr;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static Float[] convertPrimitiveFloatArray(@NonNull float[] fArr) {
        Float[] fArr2 = new Float[fArr.length];
        for (int i = 0; i < fArr.length; i++) {
            fArr2[i] = Float.valueOf(fArr[i]);
        }
        return fArr2;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static Double[] convertPrimitiveDoubleArray(@NonNull double[] dArr) {
        Double[] dArr2 = new Double[dArr.length];
        for (int i = 0; i < dArr.length; i++) {
            dArr2[i] = Double.valueOf(dArr[i]);
        }
        return dArr2;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static boolean[] convertToPrimitiveArray(@NonNull Boolean[] boolArr) {
        boolean[] zArr = new boolean[boolArr.length];
        for (int i = 0; i < boolArr.length; i++) {
            zArr[i] = boolArr[i].booleanValue();
        }
        return zArr;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static byte[] convertToPrimitiveArray(@NonNull Byte[] bArr) {
        byte[] bArr2 = new byte[bArr.length];
        for (int i = 0; i < bArr.length; i++) {
            bArr2[i] = bArr[i].byteValue();
        }
        return bArr2;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static int[] convertToPrimitiveArray(@NonNull Integer[] numArr) {
        int[] iArr = new int[numArr.length];
        for (int i = 0; i < numArr.length; i++) {
            iArr[i] = numArr[i].intValue();
        }
        return iArr;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static long[] convertToPrimitiveArray(@NonNull Long[] lArr) {
        long[] jArr = new long[lArr.length];
        for (int i = 0; i < lArr.length; i++) {
            jArr[i] = lArr[i].longValue();
        }
        return jArr;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static float[] convertToPrimitiveArray(@NonNull Float[] fArr) {
        float[] fArr2 = new float[fArr.length];
        for (int i = 0; i < fArr.length; i++) {
            fArr2[i] = fArr[i].floatValue();
        }
        return fArr2;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static double[] convertToPrimitiveArray(@NonNull Double[] dArr) {
        double[] dArr2 = new double[dArr.length];
        for (int i = 0; i < dArr.length; i++) {
            dArr2[i] = dArr[i].doubleValue();
        }
        return dArr2;
    }

    /* loaded from: classes.dex */
    public static final class Builder {
        private Map<String, Object> a = new HashMap();

        @NonNull
        public Builder putBoolean(@NonNull String str, boolean z) {
            this.a.put(str, Boolean.valueOf(z));
            return this;
        }

        @NonNull
        public Builder putBooleanArray(@NonNull String str, @NonNull boolean[] zArr) {
            this.a.put(str, Data.convertPrimitiveBooleanArray(zArr));
            return this;
        }

        @NonNull
        public Builder putByte(@NonNull String str, byte b) {
            this.a.put(str, Byte.valueOf(b));
            return this;
        }

        @NonNull
        public Builder putByteArray(@NonNull String str, @NonNull byte[] bArr) {
            this.a.put(str, Data.convertPrimitiveByteArray(bArr));
            return this;
        }

        @NonNull
        public Builder putInt(@NonNull String str, int i) {
            this.a.put(str, Integer.valueOf(i));
            return this;
        }

        @NonNull
        public Builder putIntArray(@NonNull String str, @NonNull int[] iArr) {
            this.a.put(str, Data.convertPrimitiveIntArray(iArr));
            return this;
        }

        @NonNull
        public Builder putLong(@NonNull String str, long j) {
            this.a.put(str, Long.valueOf(j));
            return this;
        }

        @NonNull
        public Builder putLongArray(@NonNull String str, @NonNull long[] jArr) {
            this.a.put(str, Data.convertPrimitiveLongArray(jArr));
            return this;
        }

        @NonNull
        public Builder putFloat(@NonNull String str, float f) {
            this.a.put(str, Float.valueOf(f));
            return this;
        }

        @NonNull
        public Builder putFloatArray(@NonNull String str, @NonNull float[] fArr) {
            this.a.put(str, Data.convertPrimitiveFloatArray(fArr));
            return this;
        }

        @NonNull
        public Builder putDouble(@NonNull String str, double d) {
            this.a.put(str, Double.valueOf(d));
            return this;
        }

        @NonNull
        public Builder putDoubleArray(@NonNull String str, @NonNull double[] dArr) {
            this.a.put(str, Data.convertPrimitiveDoubleArray(dArr));
            return this;
        }

        @NonNull
        public Builder putString(@NonNull String str, @Nullable String str2) {
            this.a.put(str, str2);
            return this;
        }

        @NonNull
        public Builder putStringArray(@NonNull String str, @NonNull String[] strArr) {
            this.a.put(str, strArr);
            return this;
        }

        @NonNull
        public Builder putAll(@NonNull Data data) {
            putAll(data.a);
            return this;
        }

        @NonNull
        public Builder putAll(@NonNull Map<String, Object> map) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                put(entry.getKey(), entry.getValue());
            }
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder put(@NonNull String str, @Nullable Object obj) {
            if (obj == null) {
                this.a.put(str, null);
            } else {
                Class<?> cls = obj.getClass();
                if (cls == Boolean.class || cls == Byte.class || cls == Integer.class || cls == Long.class || cls == Float.class || cls == Double.class || cls == String.class || cls == Boolean[].class || cls == Byte[].class || cls == Integer[].class || cls == Long[].class || cls == Float[].class || cls == Double[].class || cls == String[].class) {
                    this.a.put(str, obj);
                } else if (cls == boolean[].class) {
                    this.a.put(str, Data.convertPrimitiveBooleanArray((boolean[]) obj));
                } else if (cls == byte[].class) {
                    this.a.put(str, Data.convertPrimitiveByteArray((byte[]) obj));
                } else if (cls == int[].class) {
                    this.a.put(str, Data.convertPrimitiveIntArray((int[]) obj));
                } else if (cls == long[].class) {
                    this.a.put(str, Data.convertPrimitiveLongArray((long[]) obj));
                } else if (cls == float[].class) {
                    this.a.put(str, Data.convertPrimitiveFloatArray((float[]) obj));
                } else if (cls == double[].class) {
                    this.a.put(str, Data.convertPrimitiveDoubleArray((double[]) obj));
                } else {
                    throw new IllegalArgumentException(String.format("Key %s has invalid type %s", str, cls));
                }
            }
            return this;
        }

        @NonNull
        public Data build() {
            Data data = new Data(this.a);
            Data.toByteArrayInternal(data);
            return data;
        }
    }
}
