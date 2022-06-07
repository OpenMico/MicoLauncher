package io.netty.util;

/* loaded from: classes4.dex */
public final class AttributeKey<T> extends AbstractConstant<AttributeKey<T>> {
    private static final ConstantPool<AttributeKey<Object>> a = new ConstantPool<AttributeKey<Object>>() { // from class: io.netty.util.AttributeKey.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public AttributeKey<Object> newConstant(int i, String str) {
            return new AttributeKey<>(i, str);
        }
    };

    public static <T> AttributeKey<T> valueOf(String str) {
        return (AttributeKey<T>) a.valueOf(str);
    }

    public static boolean exists(String str) {
        return a.exists(str);
    }

    public static <T> AttributeKey<T> newInstance(String str) {
        return (AttributeKey<T>) a.newInstance(str);
    }

    public static <T> AttributeKey<T> valueOf(Class<?> cls, String str) {
        return (AttributeKey<T>) a.valueOf(cls, str);
    }

    private AttributeKey(int i, String str) {
        super(i, str);
    }
}
