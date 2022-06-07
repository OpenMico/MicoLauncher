package java8.util;

import java.lang.reflect.Field;
import sun.misc.Unsafe;

/* compiled from: UnsafeAccess.java */
/* loaded from: classes5.dex */
class al {
    static final Unsafe a;

    static {
        Field field;
        try {
            try {
                field = Unsafe.class.getDeclaredField("theUnsafe");
            } catch (NoSuchFieldException unused) {
                field = Unsafe.class.getDeclaredField("THE_ONE");
            }
            field.setAccessible(true);
            a = (Unsafe) field.get(null);
        } catch (Exception e) {
            throw new Error(e);
        }
    }
}
