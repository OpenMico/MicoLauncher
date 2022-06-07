package com.squareup.javapoet.x2c;

import com.squareup.javapoet.x2c.CodeBlock;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Modifier;

/* loaded from: classes2.dex */
public final class FieldSpec {
    public final List<AnnotationSpec> annotations;
    public final CodeBlock initializer;
    public final CodeBlock javadoc;
    public final Set<Modifier> modifiers;
    public final String name;
    public final TypeName type;

    private FieldSpec(Builder builder) {
        CodeBlock codeBlock;
        this.type = (TypeName) c.a(builder.a, "type == null", new Object[0]);
        this.name = (String) c.a(builder.b, "name == null", new Object[0]);
        this.javadoc = builder.c.build();
        this.annotations = c.a(builder.d);
        this.modifiers = c.b(builder.e);
        if (builder.f == null) {
            codeBlock = CodeBlock.builder().build();
        } else {
            codeBlock = builder.f;
        }
        this.initializer = codeBlock;
    }

    public boolean hasModifier(Modifier modifier) {
        return this.modifiers.contains(modifier);
    }

    public void a(a aVar, Set<Modifier> set) throws IOException {
        aVar.b(this.javadoc);
        aVar.a(this.annotations, false);
        aVar.a(this.modifiers, set);
        aVar.a("$T $L", this.type, this.name);
        if (!this.initializer.isEmpty()) {
            aVar.b(" = ");
            aVar.c(this.initializer);
        }
        aVar.b(";\n");
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
            a(new a(sb), Collections.emptySet());
            return sb.toString();
        } catch (IOException unused) {
            throw new AssertionError();
        }
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
        CodeBlock codeBlock = null;
        Builder builder = new Builder(this.type, this.name);
        builder.c.add(this.javadoc);
        builder.d.addAll(this.annotations);
        builder.e.addAll(this.modifiers);
        if (!this.initializer.isEmpty()) {
            codeBlock = this.initializer;
        }
        builder.f = codeBlock;
        return builder;
    }

    /* loaded from: classes2.dex */
    public static final class Builder {
        private final TypeName a;
        private final String b;
        private final CodeBlock.Builder c;
        private final List<AnnotationSpec> d;
        private final List<Modifier> e;
        private CodeBlock f;

        private Builder(TypeName typeName, String str) {
            this.c = CodeBlock.builder();
            this.d = new ArrayList();
            this.e = new ArrayList();
            this.f = null;
            this.a = typeName;
            this.b = str;
        }

        public Builder addJavadoc(String str, Object... objArr) {
            this.c.add(str, objArr);
            return this;
        }

        public Builder addJavadoc(CodeBlock codeBlock) {
            this.c.add(codeBlock);
            return this;
        }

        public Builder addAnnotations(Iterable<AnnotationSpec> iterable) {
            c.a(iterable != null, "annotationSpecs == null", new Object[0]);
            for (AnnotationSpec annotationSpec : iterable) {
                this.d.add(annotationSpec);
            }
            return this;
        }

        public Builder addAnnotation(AnnotationSpec annotationSpec) {
            this.d.add(annotationSpec);
            return this;
        }

        public Builder addAnnotation(ClassName className) {
            this.d.add(AnnotationSpec.builder(className).build());
            return this;
        }

        public Builder addAnnotation(Class<?> cls) {
            return addAnnotation(ClassName.get(cls));
        }

        public Builder addModifiers(Modifier... modifierArr) {
            Collections.addAll(this.e, modifierArr);
            return this;
        }

        public Builder initializer(String str, Object... objArr) {
            return initializer(CodeBlock.of(str, objArr));
        }

        public Builder initializer(CodeBlock codeBlock) {
            c.b(this.f == null, "initializer was already set", new Object[0]);
            this.f = (CodeBlock) c.a(codeBlock, "codeBlock == null", new Object[0]);
            return this;
        }

        public FieldSpec build() {
            return new FieldSpec(this);
        }
    }
}
