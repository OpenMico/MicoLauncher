package com.squareup.javapoet.x2c;

import com.xiaomi.onetrack.api.b;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.util.SimpleAnnotationValueVisitor8;

/* loaded from: classes2.dex */
public final class AnnotationSpec {
    public final Map<String, List<CodeBlock>> members;
    public final TypeName type;

    private AnnotationSpec(Builder builder) {
        this.type = builder.a;
        this.members = c.a(builder.b);
    }

    public void a(a aVar, boolean z) throws IOException {
        String str = z ? "" : "\n";
        String str2 = z ? ", " : ",\n";
        if (this.members.isEmpty()) {
            aVar.a("@$T", this.type);
        } else if (this.members.size() != 1 || !this.members.containsKey(b.p)) {
            aVar.a("@$T(" + str, this.type);
            aVar.a(2);
            Iterator<Map.Entry<String, List<CodeBlock>>> it = this.members.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, List<CodeBlock>> next = it.next();
                aVar.a("$L = ", next.getKey());
                a(aVar, str, str2, next.getValue());
                if (it.hasNext()) {
                    aVar.b(str2);
                }
            }
            aVar.b(2);
            aVar.b(str + ")");
        } else {
            aVar.a("@$T(", this.type);
            a(aVar, str, str2, this.members.get(b.p));
            aVar.b(")");
        }
    }

    private void a(a aVar, String str, String str2, List<CodeBlock> list) throws IOException {
        boolean z = true;
        if (list.size() == 1) {
            aVar.a(2);
            aVar.c(list.get(0));
            aVar.b(2);
            return;
        }
        aVar.b("{" + str);
        aVar.a(2);
        for (CodeBlock codeBlock : list) {
            if (!z) {
                aVar.b(str2);
            }
            aVar.c(codeBlock);
            z = false;
        }
        aVar.b(2);
        aVar.b(str + "}");
    }

    public static AnnotationSpec get(Annotation annotation) {
        return get(annotation, false);
    }

    public static AnnotationSpec get(Annotation annotation, boolean z) {
        Builder builder = builder(annotation.annotationType());
        try {
            Method[] declaredMethods = annotation.annotationType().getDeclaredMethods();
            Arrays.sort(declaredMethods, Comparator.comparing($$Lambda$41wj67R9eYOUlhE05A43dLwOQSw.INSTANCE));
            for (Method method : declaredMethods) {
                Object invoke = method.invoke(annotation, new Object[0]);
                if (z || !Objects.deepEquals(invoke, method.getDefaultValue())) {
                    if (invoke.getClass().isArray()) {
                        for (int i = 0; i < Array.getLength(invoke); i++) {
                            builder.a(method.getName(), Array.get(invoke, i));
                        }
                    } else if (invoke instanceof Annotation) {
                        builder.addMember(method.getName(), "$L", get((Annotation) invoke));
                    } else {
                        builder.a(method.getName(), invoke);
                    }
                }
            }
            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException("Reflecting " + annotation + " failed!", e);
        }
    }

    public static AnnotationSpec get(AnnotationMirror annotationMirror) {
        Builder builder = builder(ClassName.get(annotationMirror.getAnnotationType().asElement()));
        a aVar = new a(builder);
        for (ExecutableElement executableElement : annotationMirror.getElementValues().keySet()) {
            ((AnnotationValue) annotationMirror.getElementValues().get(executableElement)).accept(aVar, executableElement.getSimpleName().toString());
        }
        return builder.build();
    }

    public static Builder builder(ClassName className) {
        c.a(className, "type == null", new Object[0]);
        return new Builder(className);
    }

    public static Builder builder(Class<?> cls) {
        return builder(ClassName.get(cls));
    }

    public Builder toBuilder() {
        Builder builder = new Builder(this.type);
        for (Map.Entry<String, List<CodeBlock>> entry : this.members.entrySet()) {
            builder.b.put(entry.getKey(), new ArrayList(entry.getValue()));
        }
        return builder;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            return toString().equals(obj.toString());
        }
        return false;
    }

    public int hashCode() {
        return toString().hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        try {
            new a(sb).a("$L", this);
            return sb.toString();
        } catch (IOException unused) {
            throw new AssertionError();
        }
    }

    /* loaded from: classes2.dex */
    public static final class Builder {
        private final TypeName a;
        private final Map<String, List<CodeBlock>> b;

        private Builder(TypeName typeName) {
            this.b = new LinkedHashMap();
            this.a = typeName;
        }

        public Builder addMember(String str, String str2, Object... objArr) {
            return addMember(str, CodeBlock.of(str2, objArr));
        }

        public Builder addMember(String str, CodeBlock codeBlock) {
            c.a(str, "name == null", new Object[0]);
            c.a(SourceVersion.isName(str), "not a valid name: %s", str);
            this.b.computeIfAbsent(str, $$Lambda$AnnotationSpec$Builder$_zpNR96PqIbuFYri4C2HgdECoF8.INSTANCE).add(codeBlock);
            return this;
        }

        public static /* synthetic */ List a(String str) {
            return new ArrayList();
        }

        Builder a(String str, Object obj) {
            c.a(str, "memberName == null", new Object[0]);
            c.a(obj, "value == null, constant non-null value expected for %s", str);
            c.a(SourceVersion.isName(str), "not a valid name: %s", str);
            return obj instanceof Class ? addMember(str, "$T.class", obj) : obj instanceof Enum ? addMember(str, "$T.$L", obj.getClass(), ((Enum) obj).name()) : obj instanceof String ? addMember(str, "$S", obj) : obj instanceof Float ? addMember(str, "$Lf", obj) : obj instanceof Character ? addMember(str, "'$L'", c.a(((Character) obj).charValue())) : addMember(str, "$L", obj);
        }

        public AnnotationSpec build() {
            return new AnnotationSpec(this);
        }
    }

    /* loaded from: classes2.dex */
    private static class a extends SimpleAnnotationValueVisitor8<Builder, String> {
        final Builder a;

        a(Builder builder) {
            super(builder);
            this.a = builder;
        }
    }
}
