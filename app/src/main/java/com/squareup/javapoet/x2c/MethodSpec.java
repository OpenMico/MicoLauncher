package com.squareup.javapoet.x2c;

import com.squareup.javapoet.x2c.CodeBlock;
import com.xiaomi.mipush.sdk.Constants;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes2.dex */
public final class MethodSpec {
    public final List<AnnotationSpec> annotations;
    public final CodeBlock code;
    public final CodeBlock defaultValue;
    public final List<TypeName> exceptions;
    public final CodeBlock javadoc;
    public final Set<Modifier> modifiers;
    public final String name;
    public final List<ParameterSpec> parameters;
    public final TypeName returnType;
    public final List<TypeVariableName> typeVariables;
    public final boolean varargs;

    private MethodSpec(Builder builder) {
        CodeBlock build = builder.i.build();
        c.a(build.isEmpty() || !builder.d.contains(Modifier.ABSTRACT), "abstract method %s cannot have code", builder.a);
        c.a(!builder.j || a(builder.g), "last parameter of varargs method %s must be an array", builder.a);
        this.name = (String) c.a(builder.a, "name == null", new Object[0]);
        this.javadoc = builder.b.build();
        this.annotations = c.a(builder.c);
        this.modifiers = c.b(builder.d);
        this.typeVariables = c.a(builder.e);
        this.returnType = builder.f;
        this.parameters = c.a(builder.g);
        this.varargs = builder.j;
        this.exceptions = c.a(builder.h);
        this.defaultValue = builder.k;
        this.code = build;
    }

    private boolean a(List<ParameterSpec> list) {
        return !list.isEmpty() && TypeName.a(list.get(list.size() - 1).type) != null;
    }

    public void a(a aVar, String str, Set<Modifier> set) throws IOException {
        aVar.b(this.javadoc);
        aVar.a(this.annotations, false);
        aVar.a(this.modifiers, set);
        if (!this.typeVariables.isEmpty()) {
            aVar.a(this.typeVariables);
            aVar.b(StringUtils.SPACE);
        }
        if (isConstructor()) {
            aVar.a("$L($Z", str);
        } else {
            aVar.a("$T $L($Z", this.returnType, this.name);
        }
        Iterator<ParameterSpec> it = this.parameters.iterator();
        boolean z = true;
        while (it.hasNext()) {
            ParameterSpec next = it.next();
            if (!z) {
                aVar.b(Constants.ACCEPT_TIME_SEPARATOR_SP).f();
            }
            next.a(aVar, !it.hasNext() && this.varargs);
            z = false;
        }
        aVar.b(")");
        CodeBlock codeBlock = this.defaultValue;
        if (codeBlock != null && !codeBlock.isEmpty()) {
            aVar.b(" default ");
            aVar.c(this.defaultValue);
        }
        if (!this.exceptions.isEmpty()) {
            aVar.f().b("throws");
            boolean z2 = true;
            for (TypeName typeName : this.exceptions) {
                if (!z2) {
                    aVar.b(Constants.ACCEPT_TIME_SEPARATOR_SP);
                }
                aVar.f().a("$T", typeName);
                z2 = false;
            }
        }
        if (hasModifier(Modifier.ABSTRACT)) {
            aVar.b(";\n");
        } else if (hasModifier(Modifier.NATIVE)) {
            aVar.c(this.code);
            aVar.b(";\n");
        } else {
            aVar.b(" {\n");
            aVar.b();
            aVar.c(this.code);
            aVar.c();
            aVar.b("}\n");
        }
    }

    public boolean hasModifier(Modifier modifier) {
        return this.modifiers.contains(modifier);
    }

    public boolean isConstructor() {
        return this.name.equals("<init>");
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
            a(new a(sb), "Constructor", Collections.emptySet());
            return sb.toString();
        } catch (IOException unused) {
            throw new AssertionError();
        }
    }

    public static Builder methodBuilder(String str) {
        return new Builder(str);
    }

    public static Builder constructorBuilder() {
        return new Builder("<init>");
    }

    public static Builder overriding(ExecutableElement executableElement) {
        c.a(executableElement, "method == null", new Object[0]);
        Element enclosingElement = executableElement.getEnclosingElement();
        if (!enclosingElement.getModifiers().contains(Modifier.FINAL)) {
            Set modifiers = executableElement.getModifiers();
            if (modifiers.contains(Modifier.PRIVATE) || modifiers.contains(Modifier.FINAL) || modifiers.contains(Modifier.STATIC)) {
                throw new IllegalArgumentException("cannot override method with modifiers: " + modifiers);
            }
            Builder methodBuilder = methodBuilder(executableElement.getSimpleName().toString());
            methodBuilder.addAnnotation(Override.class);
            LinkedHashSet linkedHashSet = new LinkedHashSet(modifiers);
            linkedHashSet.remove(Modifier.ABSTRACT);
            linkedHashSet.remove(Modifier.DEFAULT);
            methodBuilder.addModifiers(linkedHashSet);
            for (TypeParameterElement typeParameterElement : executableElement.getTypeParameters()) {
                methodBuilder.addTypeVariable(TypeVariableName.get(typeParameterElement.asType()));
            }
            methodBuilder.returns(TypeName.get(executableElement.getReturnType()));
            methodBuilder.addParameters(ParameterSpec.a(executableElement));
            methodBuilder.varargs(executableElement.isVarArgs());
            for (TypeMirror typeMirror : executableElement.getThrownTypes()) {
                methodBuilder.addException(TypeName.get(typeMirror));
            }
            return methodBuilder;
        }
        throw new IllegalArgumentException("Cannot override method on final class " + enclosingElement);
    }

    public static Builder overriding(ExecutableElement executableElement, DeclaredType declaredType, Types types) {
        ExecutableType asMemberOf = types.asMemberOf(declaredType, executableElement);
        List parameterTypes = asMemberOf.getParameterTypes();
        List thrownTypes = asMemberOf.getThrownTypes();
        TypeMirror returnType = asMemberOf.getReturnType();
        Builder overriding = overriding(executableElement);
        overriding.returns(TypeName.get(returnType));
        int size = overriding.g.size();
        for (int i = 0; i < size; i++) {
            ParameterSpec parameterSpec = (ParameterSpec) overriding.g.get(i);
            overriding.g.set(i, parameterSpec.a(TypeName.get((TypeMirror) parameterTypes.get(i)), parameterSpec.name).build());
        }
        overriding.h.clear();
        int size2 = thrownTypes.size();
        for (int i2 = 0; i2 < size2; i2++) {
            overriding.addException(TypeName.get((TypeMirror) thrownTypes.get(i2)));
        }
        return overriding;
    }

    public Builder toBuilder() {
        Builder builder = new Builder(this.name);
        builder.b.add(this.javadoc);
        builder.c.addAll(this.annotations);
        builder.d.addAll(this.modifiers);
        builder.e.addAll(this.typeVariables);
        builder.f = this.returnType;
        builder.g.addAll(this.parameters);
        builder.h.addAll(this.exceptions);
        builder.i.add(this.code);
        builder.j = this.varargs;
        builder.k = this.defaultValue;
        return builder;
    }

    /* loaded from: classes2.dex */
    public static final class Builder {
        private final String a;
        private final CodeBlock.Builder b;
        private final List<AnnotationSpec> c;
        private final List<Modifier> d;
        private List<TypeVariableName> e;
        private TypeName f;
        private final List<ParameterSpec> g;
        private final Set<TypeName> h;
        private final CodeBlock.Builder i;
        private boolean j;
        private CodeBlock k;

        private Builder(String str) {
            this.b = CodeBlock.builder();
            this.c = new ArrayList();
            this.d = new ArrayList();
            this.e = new ArrayList();
            this.g = new ArrayList();
            this.h = new LinkedHashSet();
            this.i = CodeBlock.builder();
            c.a(str, "name == null", new Object[0]);
            c.a(str.equals("<init>") || SourceVersion.isName(str), "not a valid name: %s", str);
            this.a = str;
            this.f = str.equals("<init>") ? null : TypeName.VOID;
        }

        public Builder addJavadoc(String str, Object... objArr) {
            this.b.add(str, objArr);
            return this;
        }

        public Builder addJavadoc(CodeBlock codeBlock) {
            this.b.add(codeBlock);
            return this;
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
            c.a(modifierArr, "modifiers == null", new Object[0]);
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

        public Builder addTypeVariables(Iterable<TypeVariableName> iterable) {
            c.a(iterable != null, "typeVariables == null", new Object[0]);
            for (TypeVariableName typeVariableName : iterable) {
                this.e.add(typeVariableName);
            }
            return this;
        }

        public Builder addTypeVariable(TypeVariableName typeVariableName) {
            this.e.add(typeVariableName);
            return this;
        }

        public Builder returns(TypeName typeName) {
            c.b(!this.a.equals("<init>"), "constructor cannot have return type.", new Object[0]);
            this.f = typeName;
            return this;
        }

        public Builder returns(Type type) {
            return returns(TypeName.get(type));
        }

        public Builder addParameters(Iterable<ParameterSpec> iterable) {
            c.a(iterable != null, "parameterSpecs == null", new Object[0]);
            for (ParameterSpec parameterSpec : iterable) {
                this.g.add(parameterSpec);
            }
            return this;
        }

        public Builder addParameter(ParameterSpec parameterSpec) {
            this.g.add(parameterSpec);
            return this;
        }

        public Builder addParameter(TypeName typeName, String str, Modifier... modifierArr) {
            return addParameter(ParameterSpec.builder(typeName, str, modifierArr).build());
        }

        public Builder addParameter(Type type, String str, Modifier... modifierArr) {
            return addParameter(TypeName.get(type), str, modifierArr);
        }

        public Builder varargs() {
            return varargs(true);
        }

        public Builder varargs(boolean z) {
            this.j = z;
            return this;
        }

        public Builder addExceptions(Iterable<? extends TypeName> iterable) {
            c.a(iterable != null, "exceptions == null", new Object[0]);
            for (TypeName typeName : iterable) {
                this.h.add(typeName);
            }
            return this;
        }

        public Builder addException(TypeName typeName) {
            this.h.add(typeName);
            return this;
        }

        public Builder addException(Type type) {
            return addException(TypeName.get(type));
        }

        public Builder addCode(String str, Object... objArr) {
            this.i.add(str, objArr);
            return this;
        }

        public Builder addNamedCode(String str, Map<String, ?> map) {
            this.i.addNamed(str, map);
            return this;
        }

        public Builder addCode(CodeBlock codeBlock) {
            this.i.add(codeBlock);
            return this;
        }

        public Builder addComment(String str, Object... objArr) {
            CodeBlock.Builder builder = this.i;
            builder.add("// " + str + "\n", objArr);
            return this;
        }

        public Builder defaultValue(String str, Object... objArr) {
            return defaultValue(CodeBlock.of(str, objArr));
        }

        public Builder defaultValue(CodeBlock codeBlock) {
            c.b(this.k == null, "defaultValue was already set", new Object[0]);
            this.k = (CodeBlock) c.a(codeBlock, "codeBlock == null", new Object[0]);
            return this;
        }

        public Builder beginControlFlow(String str, Object... objArr) {
            this.i.beginControlFlow(str, objArr);
            return this;
        }

        public Builder nextControlFlow(String str, Object... objArr) {
            this.i.nextControlFlow(str, objArr);
            return this;
        }

        public Builder endControlFlow() {
            this.i.endControlFlow();
            return this;
        }

        public Builder endControlFlow(String str, Object... objArr) {
            this.i.endControlFlow(str, objArr);
            return this;
        }

        public Builder addStatement(String str, Object... objArr) {
            this.i.addStatement(str, objArr);
            return this;
        }

        public Builder addStatement(CodeBlock codeBlock) {
            this.i.addStatement(codeBlock);
            return this;
        }

        public MethodSpec build() {
            return new MethodSpec(this);
        }
    }
}
