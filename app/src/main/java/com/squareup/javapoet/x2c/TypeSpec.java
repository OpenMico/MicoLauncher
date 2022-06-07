package com.squareup.javapoet.x2c;

import com.squareup.javapoet.x2c.CodeBlock;
import com.xiaomi.mipush.sdk.Constants;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

/* loaded from: classes2.dex */
public final class TypeSpec {
    static final /* synthetic */ boolean a = !TypeSpec.class.desiredAssertionStatus();
    public final List<AnnotationSpec> annotations;
    public final CodeBlock anonymousTypeArguments;
    public final Map<String, TypeSpec> enumConstants;
    public final List<FieldSpec> fieldSpecs;
    public final CodeBlock initializerBlock;
    public final CodeBlock javadoc;
    public final Kind kind;
    public final List<MethodSpec> methodSpecs;
    public final Set<Modifier> modifiers;
    public final String name;
    public final List<Element> originatingElements;
    public final CodeBlock staticBlock;
    public final TypeName superclass;
    public final List<TypeName> superinterfaces;
    public final List<TypeSpec> typeSpecs;
    public final List<TypeVariableName> typeVariables;

    private TypeSpec(Builder builder) {
        this.kind = builder.a;
        this.name = builder.b;
        this.anonymousTypeArguments = builder.c;
        this.javadoc = builder.d.build();
        this.annotations = c.a(builder.e);
        this.modifiers = c.b(builder.f);
        this.typeVariables = c.a(builder.g);
        this.superclass = builder.h;
        this.superinterfaces = c.a(builder.i);
        this.enumConstants = c.b(builder.j);
        this.fieldSpecs = c.a(builder.k);
        this.staticBlock = builder.l.build();
        this.initializerBlock = builder.m.build();
        this.methodSpecs = c.a(builder.n);
        this.typeSpecs = c.a(builder.o);
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(builder.p);
        for (TypeSpec typeSpec : builder.o) {
            arrayList.addAll(typeSpec.originatingElements);
        }
        this.originatingElements = c.a(arrayList);
    }

    private TypeSpec(TypeSpec typeSpec) {
        if (a || typeSpec.anonymousTypeArguments == null) {
            this.kind = typeSpec.kind;
            this.name = typeSpec.name;
            this.anonymousTypeArguments = null;
            this.javadoc = typeSpec.javadoc;
            this.annotations = Collections.emptyList();
            this.modifiers = Collections.emptySet();
            this.typeVariables = Collections.emptyList();
            this.superclass = null;
            this.superinterfaces = Collections.emptyList();
            this.enumConstants = Collections.emptyMap();
            this.fieldSpecs = Collections.emptyList();
            this.staticBlock = typeSpec.staticBlock;
            this.initializerBlock = typeSpec.initializerBlock;
            this.methodSpecs = Collections.emptyList();
            this.typeSpecs = Collections.emptyList();
            this.originatingElements = Collections.emptyList();
            return;
        }
        throw new AssertionError();
    }

    public boolean hasModifier(Modifier modifier) {
        return this.modifiers.contains(modifier);
    }

    public static Builder classBuilder(String str) {
        return new Builder(Kind.CLASS, (String) c.a(str, "name == null", new Object[0]), null);
    }

    public static Builder classBuilder(ClassName className) {
        return classBuilder(((ClassName) c.a(className, "className == null", new Object[0])).simpleName());
    }

    public static Builder interfaceBuilder(String str) {
        return new Builder(Kind.INTERFACE, (String) c.a(str, "name == null", new Object[0]), null);
    }

    public static Builder interfaceBuilder(ClassName className) {
        return interfaceBuilder(((ClassName) c.a(className, "className == null", new Object[0])).simpleName());
    }

    public static Builder enumBuilder(String str) {
        return new Builder(Kind.ENUM, (String) c.a(str, "name == null", new Object[0]), null);
    }

    public static Builder enumBuilder(ClassName className) {
        return enumBuilder(((ClassName) c.a(className, "className == null", new Object[0])).simpleName());
    }

    public static Builder anonymousClassBuilder(String str, Object... objArr) {
        return anonymousClassBuilder(CodeBlock.builder().add(str, objArr).build());
    }

    public static Builder anonymousClassBuilder(CodeBlock codeBlock) {
        return new Builder(Kind.CLASS, null, codeBlock);
    }

    public static Builder annotationBuilder(String str) {
        return new Builder(Kind.ANNOTATION, (String) c.a(str, "name == null", new Object[0]), null);
    }

    public static Builder annotationBuilder(ClassName className) {
        return annotationBuilder(((ClassName) c.a(className, "className == null", new Object[0])).simpleName());
    }

    public Builder toBuilder() {
        Builder builder = new Builder(this.kind, this.name, this.anonymousTypeArguments);
        builder.d.add(this.javadoc);
        builder.e.addAll(this.annotations);
        builder.f.addAll(this.modifiers);
        builder.g.addAll(this.typeVariables);
        builder.h = this.superclass;
        builder.i.addAll(this.superinterfaces);
        builder.j.putAll(this.enumConstants);
        builder.k.addAll(this.fieldSpecs);
        builder.n.addAll(this.methodSpecs);
        builder.o.addAll(this.typeSpecs);
        builder.m.add(this.initializerBlock);
        builder.l.add(this.staticBlock);
        return builder;
    }

    public void a(a aVar, String str, Set<Modifier> set) throws IOException {
        List<TypeName> list;
        List<TypeName> list2;
        int i = aVar.a;
        aVar.a = -1;
        boolean z = true;
        try {
            if (str != null) {
                aVar.b(this.javadoc);
                aVar.a(this.annotations, false);
                aVar.a("$L", str);
                if (!this.anonymousTypeArguments.a.isEmpty()) {
                    aVar.b("(");
                    aVar.c(this.anonymousTypeArguments);
                    aVar.b(")");
                }
                if (!this.fieldSpecs.isEmpty() || !this.methodSpecs.isEmpty() || !this.typeSpecs.isEmpty()) {
                    aVar.b(" {\n");
                } else {
                    return;
                }
            } else if (this.anonymousTypeArguments != null) {
                aVar.a("new $T(", !this.superinterfaces.isEmpty() ? this.superinterfaces.get(0) : this.superclass);
                aVar.c(this.anonymousTypeArguments);
                aVar.b(") {\n");
            } else {
                aVar.a(new TypeSpec(this));
                aVar.b(this.javadoc);
                aVar.a(this.annotations, false);
                aVar.a(this.modifiers, c.a(set, this.kind.asMemberModifiers));
                if (this.kind == Kind.ANNOTATION) {
                    aVar.a("$L $L", "@interface", this.name);
                } else {
                    aVar.a("$L $L", this.kind.name().toLowerCase(Locale.US), this.name);
                }
                aVar.a(this.typeVariables);
                if (this.kind == Kind.INTERFACE) {
                    list = this.superinterfaces;
                    list2 = Collections.emptyList();
                } else {
                    if (this.superclass.equals(ClassName.OBJECT)) {
                        list = Collections.emptyList();
                    } else {
                        list = Collections.singletonList(this.superclass);
                    }
                    list2 = this.superinterfaces;
                }
                if (!list.isEmpty()) {
                    aVar.b(" extends");
                    boolean z2 = true;
                    for (TypeName typeName : list) {
                        if (!z2) {
                            aVar.b(Constants.ACCEPT_TIME_SEPARATOR_SP);
                        }
                        aVar.a(" $T", typeName);
                        z2 = false;
                    }
                }
                if (!list2.isEmpty()) {
                    aVar.b(" implements");
                    boolean z3 = true;
                    for (TypeName typeName2 : list2) {
                        if (!z3) {
                            aVar.b(Constants.ACCEPT_TIME_SEPARATOR_SP);
                        }
                        aVar.a(" $T", typeName2);
                        z3 = false;
                    }
                }
                aVar.e();
                aVar.b(" {\n");
            }
            aVar.a(this);
            aVar.b();
            Iterator<Map.Entry<String, TypeSpec>> it = this.enumConstants.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, TypeSpec> next = it.next();
                if (!z) {
                    aVar.b("\n");
                }
                next.getValue().a(aVar, next.getKey(), Collections.emptySet());
                if (it.hasNext()) {
                    aVar.b(",\n");
                } else {
                    if (this.fieldSpecs.isEmpty() && this.methodSpecs.isEmpty() && this.typeSpecs.isEmpty()) {
                        aVar.b("\n");
                    }
                    aVar.b(";\n");
                }
                z = false;
            }
            for (FieldSpec fieldSpec : this.fieldSpecs) {
                if (fieldSpec.hasModifier(Modifier.STATIC)) {
                    if (!z) {
                        aVar.b("\n");
                    }
                    fieldSpec.a(aVar, this.kind.implicitFieldModifiers);
                    z = false;
                }
            }
            if (!this.staticBlock.isEmpty()) {
                if (!z) {
                    aVar.b("\n");
                }
                aVar.c(this.staticBlock);
                z = false;
            }
            for (FieldSpec fieldSpec2 : this.fieldSpecs) {
                if (!fieldSpec2.hasModifier(Modifier.STATIC)) {
                    if (!z) {
                        aVar.b("\n");
                    }
                    fieldSpec2.a(aVar, this.kind.implicitFieldModifiers);
                    z = false;
                }
            }
            if (!this.initializerBlock.isEmpty()) {
                if (!z) {
                    aVar.b("\n");
                }
                aVar.c(this.initializerBlock);
                z = false;
            }
            for (MethodSpec methodSpec : this.methodSpecs) {
                if (methodSpec.isConstructor()) {
                    if (!z) {
                        aVar.b("\n");
                    }
                    methodSpec.a(aVar, this.name, this.kind.implicitMethodModifiers);
                    z = false;
                }
            }
            for (MethodSpec methodSpec2 : this.methodSpecs) {
                if (!methodSpec2.isConstructor()) {
                    if (!z) {
                        aVar.b("\n");
                    }
                    methodSpec2.a(aVar, this.name, this.kind.implicitMethodModifiers);
                    z = false;
                }
            }
            for (TypeSpec typeSpec : this.typeSpecs) {
                if (!z) {
                    aVar.b("\n");
                }
                typeSpec.a(aVar, null, this.kind.implicitTypeModifiers);
                z = false;
            }
            aVar.c();
            aVar.e();
            aVar.b("}");
            if (str == null && this.anonymousTypeArguments == null) {
                aVar.b("\n");
            }
        } finally {
            aVar.a = i;
        }
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
            a(new a(sb), null, Collections.emptySet());
            return sb.toString();
        } catch (IOException unused) {
            throw new AssertionError();
        }
    }

    /* loaded from: classes2.dex */
    public enum Kind {
        CLASS(Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), Collections.emptySet()),
        INTERFACE(c.b(Arrays.asList(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)), c.b(Arrays.asList(Modifier.PUBLIC, Modifier.ABSTRACT)), c.b(Arrays.asList(Modifier.PUBLIC, Modifier.STATIC)), c.b(Collections.singletonList(Modifier.STATIC))),
        ENUM(Collections.emptySet(), Collections.emptySet(), Collections.emptySet(), Collections.singleton(Modifier.STATIC)),
        ANNOTATION(c.b(Arrays.asList(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)), c.b(Arrays.asList(Modifier.PUBLIC, Modifier.ABSTRACT)), c.b(Arrays.asList(Modifier.PUBLIC, Modifier.STATIC)), c.b(Collections.singletonList(Modifier.STATIC)));
        
        private final Set<Modifier> asMemberModifiers;
        private final Set<Modifier> implicitFieldModifiers;
        private final Set<Modifier> implicitMethodModifiers;
        private final Set<Modifier> implicitTypeModifiers;

        Kind(Set set, Set set2, Set set3, Set set4) {
            this.implicitFieldModifiers = set;
            this.implicitMethodModifiers = set2;
            this.implicitTypeModifiers = set3;
            this.asMemberModifiers = set4;
        }
    }

    /* loaded from: classes2.dex */
    public static final class Builder {
        private final Kind a;
        private final String b;
        private final CodeBlock c;
        private final CodeBlock.Builder d;
        private final List<AnnotationSpec> e;
        private final List<Modifier> f;
        private final List<TypeVariableName> g;
        private TypeName h;
        private final List<TypeName> i;
        private final Map<String, TypeSpec> j;
        private final List<FieldSpec> k;
        private final CodeBlock.Builder l;
        private final CodeBlock.Builder m;
        private final List<MethodSpec> n;
        private final List<TypeSpec> o;
        private final List<Element> p;

        private Builder(Kind kind, String str, CodeBlock codeBlock) {
            this.d = CodeBlock.builder();
            this.e = new ArrayList();
            this.f = new ArrayList();
            this.g = new ArrayList();
            this.h = ClassName.OBJECT;
            this.i = new ArrayList();
            this.j = new LinkedHashMap();
            this.k = new ArrayList();
            this.l = CodeBlock.builder();
            this.m = CodeBlock.builder();
            this.n = new ArrayList();
            this.o = new ArrayList();
            this.p = new ArrayList();
            c.a(str == null || SourceVersion.isName(str), "not a valid name: %s", str);
            this.a = kind;
            this.b = str;
            this.c = codeBlock;
        }

        public Builder addJavadoc(String str, Object... objArr) {
            this.d.add(str, objArr);
            return this;
        }

        public Builder addJavadoc(CodeBlock codeBlock) {
            this.d.add(codeBlock);
            return this;
        }

        public Builder addAnnotations(Iterable<AnnotationSpec> iterable) {
            c.a(iterable != null, "annotationSpecs == null", new Object[0]);
            for (AnnotationSpec annotationSpec : iterable) {
                this.e.add(annotationSpec);
            }
            return this;
        }

        public Builder addAnnotation(AnnotationSpec annotationSpec) {
            c.a(annotationSpec, "annotationSpec == null", new Object[0]);
            this.e.add(annotationSpec);
            return this;
        }

        public Builder addAnnotation(ClassName className) {
            return addAnnotation(AnnotationSpec.builder(className).build());
        }

        public Builder addAnnotation(Class<?> cls) {
            return addAnnotation(ClassName.get(cls));
        }

        public Builder addModifiers(Modifier... modifierArr) {
            c.b(this.c == null, "forbidden on anonymous types.", new Object[0]);
            int length = modifierArr.length;
            for (int i = 0; i < length; i++) {
                Modifier modifier = modifierArr[i];
                c.a(modifier != null, "modifiers contain null", new Object[0]);
                this.f.add(modifier);
            }
            return this;
        }

        public Builder addTypeVariables(Iterable<TypeVariableName> iterable) {
            boolean z = true;
            c.b(this.c == null, "forbidden on anonymous types.", new Object[0]);
            if (iterable == null) {
                z = false;
            }
            c.a(z, "typeVariables == null", new Object[0]);
            for (TypeVariableName typeVariableName : iterable) {
                this.g.add(typeVariableName);
            }
            return this;
        }

        public Builder addTypeVariable(TypeVariableName typeVariableName) {
            c.b(this.c == null, "forbidden on anonymous types.", new Object[0]);
            this.g.add(typeVariableName);
            return this;
        }

        public Builder superclass(TypeName typeName) {
            boolean z = this.a == Kind.CLASS;
            c.b(z, "only classes have super classes, not " + this.a, new Object[0]);
            boolean z2 = this.h == ClassName.OBJECT;
            c.b(z2, "superclass already set to " + this.h, new Object[0]);
            c.a(typeName.isPrimitive() ^ true, "superclass may not be a primitive", new Object[0]);
            this.h = typeName;
            return this;
        }

        public Builder superclass(Type type) {
            return superclass(TypeName.get(type));
        }

        public Builder addSuperinterfaces(Iterable<? extends TypeName> iterable) {
            c.a(iterable != null, "superinterfaces == null", new Object[0]);
            for (TypeName typeName : iterable) {
                addSuperinterface(typeName);
            }
            return this;
        }

        public Builder addSuperinterface(TypeName typeName) {
            c.a(typeName != null, "superinterface == null", new Object[0]);
            this.i.add(typeName);
            return this;
        }

        public Builder addSuperinterface(Type type) {
            return addSuperinterface(TypeName.get(type));
        }

        public Builder addEnumConstant(String str) {
            return addEnumConstant(str, TypeSpec.anonymousClassBuilder("", new Object[0]).build());
        }

        public Builder addEnumConstant(String str, TypeSpec typeSpec) {
            c.b(this.a == Kind.ENUM, "%s is not enum", this.b);
            c.a(typeSpec.anonymousTypeArguments != null, "enum constants must have anonymous type arguments", new Object[0]);
            c.a(SourceVersion.isName(str), "not a valid enum constant: %s", str);
            this.j.put(str, typeSpec);
            return this;
        }

        public Builder addFields(Iterable<FieldSpec> iterable) {
            c.a(iterable != null, "fieldSpecs == null", new Object[0]);
            for (FieldSpec fieldSpec : iterable) {
                addField(fieldSpec);
            }
            return this;
        }

        public Builder addField(FieldSpec fieldSpec) {
            if (this.a == Kind.INTERFACE || this.a == Kind.ANNOTATION) {
                c.a(fieldSpec.modifiers, Modifier.PUBLIC, Modifier.PRIVATE);
                EnumSet of = EnumSet.of(Modifier.STATIC, Modifier.FINAL);
                c.b(fieldSpec.modifiers.containsAll(of), "%s %s.%s requires modifiers %s", this.a, this.b, fieldSpec.name, of);
            }
            this.k.add(fieldSpec);
            return this;
        }

        public Builder addField(TypeName typeName, String str, Modifier... modifierArr) {
            return addField(FieldSpec.builder(typeName, str, modifierArr).build());
        }

        public Builder addField(Type type, String str, Modifier... modifierArr) {
            return addField(TypeName.get(type), str, modifierArr);
        }

        public Builder addStaticBlock(CodeBlock codeBlock) {
            this.l.beginControlFlow("static", new Object[0]).add(codeBlock).endControlFlow();
            return this;
        }

        public Builder addInitializerBlock(CodeBlock codeBlock) {
            if (this.a == Kind.CLASS || this.a == Kind.ENUM) {
                this.m.add("{\n", new Object[0]).indent().add(codeBlock).unindent().add("}\n", new Object[0]);
                return this;
            }
            throw new UnsupportedOperationException(this.a + " can't have initializer blocks");
        }

        public Builder addMethods(Iterable<MethodSpec> iterable) {
            c.a(iterable != null, "methodSpecs == null", new Object[0]);
            for (MethodSpec methodSpec : iterable) {
                addMethod(methodSpec);
            }
            return this;
        }

        public Builder addMethod(MethodSpec methodSpec) {
            if (this.a == Kind.INTERFACE) {
                c.a(methodSpec.modifiers, Modifier.ABSTRACT, Modifier.STATIC, Modifier.DEFAULT);
                c.a(methodSpec.modifiers, Modifier.PUBLIC, Modifier.PRIVATE);
            } else if (this.a == Kind.ANNOTATION) {
                c.b(methodSpec.modifiers.equals(this.a.implicitMethodModifiers), "%s %s.%s requires modifiers %s", this.a, this.b, methodSpec.name, this.a.implicitMethodModifiers);
            }
            if (this.a != Kind.ANNOTATION) {
                c.b(methodSpec.defaultValue == null, "%s %s.%s cannot have a default value", this.a, this.b, methodSpec.name);
            }
            if (this.a != Kind.INTERFACE) {
                c.b(!methodSpec.hasModifier(Modifier.DEFAULT), "%s %s.%s cannot be default", this.a, this.b, methodSpec.name);
            }
            this.n.add(methodSpec);
            return this;
        }

        public Builder addTypes(Iterable<TypeSpec> iterable) {
            c.a(iterable != null, "typeSpecs == null", new Object[0]);
            for (TypeSpec typeSpec : iterable) {
                addType(typeSpec);
            }
            return this;
        }

        public Builder addType(TypeSpec typeSpec) {
            c.a(typeSpec.modifiers.containsAll(this.a.implicitTypeModifiers), "%s %s.%s requires modifiers %s", this.a, this.b, typeSpec.name, this.a.implicitTypeModifiers);
            this.o.add(typeSpec);
            return this;
        }

        public Builder addOriginatingElement(Element element) {
            this.p.add(element);
            return this;
        }

        public TypeSpec build() {
            boolean z = true;
            c.a(this.a != Kind.ENUM || !this.j.isEmpty(), "at least one enum constant is required for %s", this.b);
            boolean z2 = this.f.contains(Modifier.ABSTRACT) || this.a != Kind.CLASS;
            for (MethodSpec methodSpec : this.n) {
                c.a(z2 || !methodSpec.hasModifier(Modifier.ABSTRACT), "non-abstract type %s cannot declare abstract method %s", this.b, methodSpec.name);
            }
            int size = (!this.h.equals(ClassName.OBJECT)) + this.i.size();
            if (this.c != null && size > 1) {
                z = false;
            }
            c.a(z, "anonymous type has too many supertypes", new Object[0]);
            return new TypeSpec(this);
        }
    }
}
