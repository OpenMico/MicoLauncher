package retrofit2;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import javax.annotation.Nullable;

/* compiled from: ServiceMethod.java */
/* loaded from: classes6.dex */
public abstract class k<T> {
    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public abstract T a(Object[] objArr);

    public static <T> k<T> a(Retrofit retrofit, Method method) {
        j a = j.a(retrofit, method);
        Type genericReturnType = method.getGenericReturnType();
        if (m.d(genericReturnType)) {
            throw m.a(method, "Method return type must not include a type variable or wildcard: %s", genericReturnType);
        } else if (genericReturnType != Void.TYPE) {
            return d.a(retrofit, method, a);
        } else {
            throw m.a(method, "Service methods cannot return void.", new Object[0]);
        }
    }
}
