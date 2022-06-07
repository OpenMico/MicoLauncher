package retrofit2;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* loaded from: classes6.dex */
public final class Invocation {
    private final Method a;
    private final List<?> b;

    public static Invocation of(Method method, List<?> list) {
        Objects.requireNonNull(method, "method == null");
        Objects.requireNonNull(list, "arguments == null");
        return new Invocation(method, new ArrayList(list));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Invocation(Method method, List<?> list) {
        this.a = method;
        this.b = Collections.unmodifiableList(list);
    }

    public Method method() {
        return this.a;
    }

    public List<?> arguments() {
        return this.b;
    }

    public String toString() {
        return String.format("%s.%s() %s", this.a.getDeclaringClass().getName(), this.a.getName(), this.b);
    }
}
