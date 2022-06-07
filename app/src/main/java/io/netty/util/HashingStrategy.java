package io.netty.util;

/* loaded from: classes4.dex */
public interface HashingStrategy<T> {
    public static final HashingStrategy JAVA_HASHER = new HashingStrategy() { // from class: io.netty.util.HashingStrategy.1
        @Override // io.netty.util.HashingStrategy
        public int hashCode(Object obj) {
            if (obj != null) {
                return obj.hashCode();
            }
            return 0;
        }

        @Override // io.netty.util.HashingStrategy
        public boolean equals(Object obj, Object obj2) {
            return obj == obj2 || (obj != null && obj.equals(obj2));
        }
    };

    boolean equals(T t, T t2);

    int hashCode(T t);
}
