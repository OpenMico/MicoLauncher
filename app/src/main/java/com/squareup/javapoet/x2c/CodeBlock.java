package com.squareup.javapoet.x2c;

import com.squareup.javapoet.x2c.CodeBlock;
import com.umeng.analytics.pro.ai;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.StreamSupport;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;

/* loaded from: classes2.dex */
public final class CodeBlock {
    private static final Pattern c = Pattern.compile("\\$(?<argumentName>[\\w_]+):(?<typeChar>[\\w]).*");
    private static final Pattern d = Pattern.compile("[a-z]+[\\w_]*");
    final List<String> a;
    final List<Object> b;

    private CodeBlock(Builder builder) {
        this.a = c.a(builder.a);
        this.b = c.a(builder.b);
    }

    public boolean isEmpty() {
        return this.a.isEmpty();
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
            new a(sb).c(this);
            return sb.toString();
        } catch (IOException unused) {
            throw new AssertionError();
        }
    }

    public static CodeBlock of(String str, Object... objArr) {
        return new Builder().add(str, objArr).build();
    }

    public static CodeBlock join(Iterable<CodeBlock> iterable, String str) {
        return (CodeBlock) StreamSupport.stream(iterable.spliterator(), false).collect(joining(str));
    }

    public static Collector<CodeBlock, ?, CodeBlock> joining(final String str) {
        return Collector.of(new Supplier() { // from class: com.squareup.javapoet.x2c.-$$Lambda$CodeBlock$jC2ZoRaV5IaBZUnI3JYsjBmGNdQ
            @Override // java.util.function.Supplier
            public final Object get() {
                CodeBlock.a a2;
                a2 = CodeBlock.a(str);
                return a2;
            }
        }, $$Lambda$7d1HFs8GqQPaYsuMSdId2ywPMc.INSTANCE, $$Lambda$UAamEXqQD4jSdxFGXTGwH1T2Isc.INSTANCE, $$Lambda$jYqhaMkLEoQGUcUi2JTyBwJoyig.INSTANCE, new Collector.Characteristics[0]);
    }

    public static /* synthetic */ a a(String str) {
        return new a(str, builder());
    }

    public static Collector<CodeBlock, ?, CodeBlock> joining(final String str, String str2, final String str3) {
        final Builder add = builder().add("$N", str2);
        return Collector.of(new Supplier() { // from class: com.squareup.javapoet.x2c.-$$Lambda$CodeBlock$Fov4rjU3b3drPLUE3v-Sob4HpqE
            @Override // java.util.function.Supplier
            public final Object get() {
                CodeBlock.a a2;
                a2 = CodeBlock.a(str, add);
                return a2;
            }
        }, $$Lambda$7d1HFs8GqQPaYsuMSdId2ywPMc.INSTANCE, $$Lambda$UAamEXqQD4jSdxFGXTGwH1T2Isc.INSTANCE, new Function() { // from class: com.squareup.javapoet.x2c.-$$Lambda$CodeBlock$K2WsUkkxWyUFttocCfV9wuoCrM0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                CodeBlock a2;
                a2 = CodeBlock.a(CodeBlock.Builder.this, str3, (CodeBlock.a) obj);
                return a2;
            }
        }, new Collector.Characteristics[0]);
    }

    public static /* synthetic */ a a(String str, Builder builder) {
        return new a(str, builder);
    }

    public static /* synthetic */ CodeBlock a(Builder builder, String str, a aVar) {
        builder.add(of("$N", str));
        return aVar.a();
    }

    public static Builder builder() {
        return new Builder();
    }

    public Builder toBuilder() {
        Builder builder = new Builder();
        builder.a.addAll(this.a);
        builder.b.addAll(this.b);
        return builder;
    }

    /* loaded from: classes2.dex */
    public static final class Builder {
        final List<String> a;
        final List<Object> b;

        private boolean a(char c) {
            return c == '$' || c == '>' || c == '<' || c == '[' || c == ']' || c == 'W' || c == 'Z';
        }

        private Object b(Object obj) {
            return obj;
        }

        private Builder() {
            this.a = new ArrayList();
            this.b = new ArrayList();
        }

        public boolean isEmpty() {
            return this.a.isEmpty();
        }

        public Builder addNamed(String str, Map<String, ?> map) {
            for (String str2 : map.keySet()) {
                c.a(CodeBlock.d.matcher(str2).matches(), "argument '%s' must start with a lowercase character", str2);
            }
            int i = 0;
            while (true) {
                if (i >= str.length()) {
                    break;
                }
                int indexOf = str.indexOf("$", i);
                if (indexOf == -1) {
                    this.a.add(str.substring(i, str.length()));
                    break;
                }
                if (i != indexOf) {
                    this.a.add(str.substring(i, indexOf));
                    i = indexOf;
                }
                Matcher matcher = null;
                int indexOf2 = str.indexOf(58, i);
                if (indexOf2 != -1) {
                    matcher = CodeBlock.c.matcher(str.substring(i, Math.min(indexOf2 + 2, str.length())));
                }
                if (matcher == null || !matcher.lookingAt()) {
                    c.a(i < str.length() - 1, "dangling $ at end", new Object[0]);
                    int i2 = i + 1;
                    c.a(a(str.charAt(i2)), "unknown format $%s at %s in '%s'", Character.valueOf(str.charAt(i2)), Integer.valueOf(i2), str);
                    int i3 = i + 2;
                    this.a.add(str.substring(i, i3));
                    i = i3;
                } else {
                    String group = matcher.group("argumentName");
                    c.a(map.containsKey(group), "Missing named argument for $%s", group);
                    char charAt = matcher.group("typeChar").charAt(0);
                    a(str, charAt, map.get(group));
                    this.a.add("$" + charAt);
                    i += matcher.regionEnd();
                }
            }
            return this;
        }

        public Builder add(String str, Object... objArr) {
            int i;
            char charAt;
            int[] iArr = new int[objArr.length];
            int i2 = 0;
            boolean z = false;
            int i3 = 0;
            boolean z2 = false;
            while (true) {
                boolean z3 = true;
                if (i2 >= str.length()) {
                    break;
                } else if (str.charAt(i2) != '$') {
                    int indexOf = str.indexOf(36, i2 + 1);
                    if (indexOf == -1) {
                        indexOf = str.length();
                    }
                    this.a.add(str.substring(i2, indexOf));
                    i2 = indexOf;
                } else {
                    int i4 = i2 + 1;
                    int i5 = i4;
                    while (true) {
                        c.a(i5 < str.length(), "dangling format characters in '%s'", str);
                        i = i5 + 1;
                        charAt = str.charAt(i5);
                        if (charAt < '0' || charAt > '9') {
                            break;
                        }
                        i5 = i;
                    }
                    int i6 = i - 1;
                    if (a(charAt)) {
                        if (i4 != i6) {
                            z3 = false;
                        }
                        c.a(z3, "$$, $>, $<, $[, $], $W, and $Z may not have an index", new Object[0]);
                        this.a.add("$" + charAt);
                        i2 = i;
                    } else {
                        if (i4 < i6) {
                            int parseInt = Integer.parseInt(str.substring(i4, i6)) - 1;
                            if (objArr.length > 0) {
                                int length = parseInt % objArr.length;
                                iArr[length] = iArr[length] + 1;
                            }
                            i3 = i3;
                            i3 = parseInt;
                            z2 = true;
                        } else {
                            i3++;
                            z = true;
                        }
                        c.a(i3 >= 0 && i3 < objArr.length, "index %d for '%s' not in range (received %s arguments)", Integer.valueOf(i3 + 1), str.substring(i4 - 1, i6 + 1), Integer.valueOf(objArr.length));
                        c.a(!z2 || !z, "cannot mix indexed and positional parameters", new Object[0]);
                        a(str, charAt, objArr[i3]);
                        this.a.add("$" + charAt);
                        i2 = i;
                    }
                }
            }
            if (z) {
                c.a(i3 >= objArr.length, "unused arguments: expected %s, received %s", Integer.valueOf(i3), Integer.valueOf(objArr.length));
            }
            if (z2) {
                ArrayList arrayList = new ArrayList();
                for (int i7 = 0; i7 < objArr.length; i7++) {
                    if (iArr[i7] == 0) {
                        arrayList.add("$" + (i7 + 1));
                    }
                }
                c.a(arrayList.isEmpty(), "unused argument%s: %s", arrayList.size() == 1 ? "" : ai.az, String.join(", ", arrayList));
            }
            return this;
        }

        private void a(String str, char c, Object obj) {
            if (c == 'L') {
                this.b.add(b(obj));
            } else if (c != 'N') {
                switch (c) {
                    case 'S':
                        this.b.add(c(obj));
                        return;
                    case 'T':
                        this.b.add(d(obj));
                        return;
                    default:
                        throw new IllegalArgumentException(String.format("invalid format string: '%s'", str));
                }
            } else {
                this.b.add(a(obj));
            }
        }

        private String a(Object obj) {
            if (obj instanceof CharSequence) {
                return obj.toString();
            }
            if (obj instanceof ParameterSpec) {
                return ((ParameterSpec) obj).name;
            }
            if (obj instanceof FieldSpec) {
                return ((FieldSpec) obj).name;
            }
            if (obj instanceof MethodSpec) {
                return ((MethodSpec) obj).name;
            }
            if (obj instanceof TypeSpec) {
                return ((TypeSpec) obj).name;
            }
            throw new IllegalArgumentException("expected name but was " + obj);
        }

        private String c(Object obj) {
            if (obj != null) {
                return String.valueOf(obj);
            }
            return null;
        }

        private TypeName d(Object obj) {
            if (obj instanceof TypeName) {
                return (TypeName) obj;
            }
            if (obj instanceof TypeMirror) {
                return TypeName.get((TypeMirror) obj);
            }
            if (obj instanceof Element) {
                return TypeName.get(((Element) obj).asType());
            }
            if (obj instanceof Type) {
                return TypeName.get((Type) obj);
            }
            throw new IllegalArgumentException("expected type but was " + obj);
        }

        public Builder beginControlFlow(String str, Object... objArr) {
            add(str + " {\n", objArr);
            indent();
            return this;
        }

        public Builder nextControlFlow(String str, Object... objArr) {
            unindent();
            add("} " + str + " {\n", objArr);
            indent();
            return this;
        }

        public Builder endControlFlow() {
            unindent();
            add("}\n", new Object[0]);
            return this;
        }

        public Builder endControlFlow(String str, Object... objArr) {
            unindent();
            add("} " + str + ";\n", objArr);
            return this;
        }

        public Builder addStatement(String str, Object... objArr) {
            add("$[", new Object[0]);
            add(str, objArr);
            add(";\n$]", new Object[0]);
            return this;
        }

        public Builder addStatement(CodeBlock codeBlock) {
            return addStatement("$L", codeBlock);
        }

        public Builder add(CodeBlock codeBlock) {
            this.a.addAll(codeBlock.a);
            this.b.addAll(codeBlock.b);
            return this;
        }

        public Builder indent() {
            this.a.add("$>");
            return this;
        }

        public Builder unindent() {
            this.a.add("$<");
            return this;
        }

        public CodeBlock build() {
            return new CodeBlock(this);
        }
    }

    /* loaded from: classes2.dex */
    public static final class a {
        private final String a;
        private final Builder b;
        private boolean c = true;

        a(String str, Builder builder) {
            this.a = str;
            this.b = builder;
        }

        public a a(CodeBlock codeBlock) {
            if (!this.c) {
                this.b.add(this.a, new Object[0]);
            }
            this.c = false;
            this.b.add(codeBlock);
            return this;
        }

        public a a(a aVar) {
            CodeBlock build = aVar.b.build();
            if (!build.isEmpty()) {
                a(build);
            }
            return this;
        }

        public CodeBlock a() {
            return this.b.build();
        }
    }
}
