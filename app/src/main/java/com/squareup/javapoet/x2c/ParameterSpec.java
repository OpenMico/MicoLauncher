package com.squareup.javapoet.x2c;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;

/* loaded from: classes2.dex */
public final class ParameterSpec {
    public final List<AnnotationSpec> annotations;
    public final Set<Modifier> modifiers;
    public final String name;
    public final TypeName type;

    private ParameterSpec(Builder builder) {
        this.name = (String) c.a(builder.b, "name == null", new Object[0]);
        this.annotations = c.a(builder.c);
        this.modifiers = c.b(builder.d);
        this.type = (TypeName) c.a(builder.a, "type == null", new Object[0]);
    }

    public boolean hasModifier(Modifier modifier) {
        return this.modifiers.contains(modifier);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(a aVar, boolean z) throws IOException {
        aVar.a(this.annotations, true);
        aVar.a(this.modifiers);
        if (z) {
            TypeName.a(this.type).a(aVar, true);
        } else {
            this.type.a(aVar);
        }
        aVar.a(" $L", this.name);
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
            a(new a(sb), false);
            return sb.toString();
        } catch (IOException unused) {
            throw new AssertionError();
        }
    }

    public static ParameterSpec get(VariableElement variableElement) {
        return builder(TypeName.get(variableElement.asType()), variableElement.getSimpleName().toString(), new Modifier[0]).addModifiers(variableElement.getModifiers()).build();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static List<ParameterSpec> a(ExecutableElement executableElement) {
        ArrayList arrayList = new ArrayList();
        for (VariableElement variableElement : executableElement.getParameters()) {
            arrayList.add(get(variableElement));
        }
        return arrayList;
    }

    public static Builder builder(TypeName typeName, String str, Modifier... modifierArr) {
        c.a(typeName, "type == null", new Object[0]);
        c.a(SourceVersion.isName(str), "not a valid name: %s", str);
        return new Builder(typeName, str).addModifiers(modifierArr);
    }

    public static Builder builder(Type type, String str, Modifier... modifierArr) {
        return builder(TypeName.get(type), str, modifierArr);
    }

    public Builder toBuilder() {
        return a(this.type, this.name);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Builder a(TypeName typeName, String str) {
        Builder builder = new Builder(typeName, str);
        builder.c.addAll(this.annotations);
        builder.d.addAll(this.modifiers);
        return builder;
    }

    /* loaded from: classes2.dex */
    public static final class Builder {
        private final TypeName a;
        private final String b;
        private final List<AnnotationSpec> c;
        private final List<Modifier> d;

        private Builder(TypeName typeName, String str) {
            this.c = new ArrayList();
            this.d = new ArrayList();
            this.a = typeName;
            this.b = str;
        }

        public Builder addAnnotations(Iterable<AnnotationSpec> iterable) {
            c.a(iterable != null, "annotationSpecs == null", new Object[0]);
            for (AnnotationSpec annotationSpec : iterable) {
                this.c.add(annotationSpec);
            }
            return this;
        }

        public Builder addAnnotation(AnnotationSpec annotationSpec) {
            this.c.add(annotationSpec);
            return this;
        }

        public Builder addAnnotation(ClassName className) {
            this.c.add(AnnotationSpec.builder(className).build());
            return this;
        }

        public Builder addAnnotation(Class<?> cls) {
            return addAnnotation(ClassName.get(cls));
        }

        public Builder addModifiers(Modifier... modifierArr) {
            Collections.addAll(this.d, modifierArr);
            return this;
        }

        public Builder addModifiers(Iterable<Modifier> iterable) {
            c.a(iterable, "modifiers == null", new Object[0]);
            for (Modifier modifier : iterable) {
                this.d.add(modifier);
            }
            return this;
        }

        public ParameterSpec build() {
            return new ParameterSpec(this);
        }
    }
}
