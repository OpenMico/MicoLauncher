package com.squareup.javapoet.x2c;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Modifier;
import org.apache.commons.lang3.StringUtils;

/* compiled from: CodeWriter.java */
/* loaded from: classes2.dex */
public final class a {
    private static final String b = new String();
    int a;
    private final String c;
    private final b d;
    private int e;
    private boolean f;
    private boolean g;
    private String h;
    private final List<TypeSpec> i;
    private final Set<String> j;
    private final Set<String> k;
    private final Map<String, ClassName> l;
    private final Map<String, ClassName> m;
    private final Set<String> n;
    private boolean o;

    public a(Appendable appendable) {
        this(appendable, "  ", Collections.emptySet());
    }

    public a(Appendable appendable, String str, Set<String> set) {
        this(appendable, str, Collections.emptyMap(), set);
    }

    public a(Appendable appendable, String str, Map<String, ClassName> map, Set<String> set) {
        this.f = false;
        this.g = false;
        this.h = b;
        this.i = new ArrayList();
        this.m = new LinkedHashMap();
        this.n = new LinkedHashSet();
        this.a = -1;
        this.d = new b(appendable, str, 100);
        this.c = (String) c.a(str, "indent == null", new Object[0]);
        this.l = (Map) c.a(map, "importedTypes == null", new Object[0]);
        this.k = (Set) c.a(set, "staticImports == null", new Object[0]);
        this.j = new LinkedHashSet();
        for (String str2 : set) {
            this.j.add(str2.substring(0, str2.lastIndexOf(46)));
        }
    }

    public Map<String, ClassName> a() {
        return this.l;
    }

    public a b() {
        return a(1);
    }

    public a a(int i) {
        this.e += i;
        return this;
    }

    public a c() {
        return b(1);
    }

    public a b(int i) {
        c.a(this.e - i >= 0, "cannot unindent %s from %s", Integer.valueOf(i), Integer.valueOf(this.e));
        this.e -= i;
        return this;
    }

    public a a(String str) {
        c.b(this.h == b, "package already set: %s", this.h);
        this.h = (String) c.a(str, "packageName == null", new Object[0]);
        return this;
    }

    public a d() {
        c.b(this.h != b, "package not set", new Object[0]);
        this.h = b;
        return this;
    }

    public a a(TypeSpec typeSpec) {
        this.i.add(typeSpec);
        return this;
    }

    public a e() {
        List<TypeSpec> list = this.i;
        list.remove(list.size() - 1);
        return this;
    }

    public void a(CodeBlock codeBlock) throws IOException {
        this.o = true;
        this.g = true;
        try {
            c(codeBlock);
            b("\n");
        } finally {
            this.g = false;
        }
    }

    /* JADX WARN: Finally extract failed */
    public void b(CodeBlock codeBlock) throws IOException {
        if (!codeBlock.isEmpty()) {
            b("/**\n");
            this.f = true;
            try {
                c(codeBlock);
                this.f = false;
                b(" */\n");
            } catch (Throwable th) {
                this.f = false;
                throw th;
            }
        }
    }

    public void a(List<AnnotationSpec> list, boolean z) throws IOException {
        for (AnnotationSpec annotationSpec : list) {
            annotationSpec.a(this, z);
            b(z ? StringUtils.SPACE : "\n");
        }
    }

    public void a(Set<Modifier> set, Set<Modifier> set2) throws IOException {
        if (!set.isEmpty()) {
            Iterator it = EnumSet.copyOf(set).iterator();
            while (it.hasNext()) {
                Modifier modifier = (Modifier) it.next();
                if (!set2.contains(modifier)) {
                    c(modifier.name().toLowerCase(Locale.US));
                    c(StringUtils.SPACE);
                }
            }
        }
    }

    public void a(Set<Modifier> set) throws IOException {
        a(set, Collections.emptySet());
    }

    public void a(List<TypeVariableName> list) throws IOException {
        if (!list.isEmpty()) {
            b("<");
            boolean z = true;
            for (TypeVariableName typeVariableName : list) {
                if (!z) {
                    b(", ");
                }
                a(typeVariableName.annotations, true);
                a("$L", typeVariableName.name);
                boolean z2 = true;
                for (TypeName typeName : typeVariableName.bounds) {
                    a(z2 ? " extends $T" : " & $T", typeName);
                    z2 = false;
                }
                z = false;
            }
            b(">");
        }
    }

    public a b(String str) throws IOException {
        return c(str);
    }

    public a a(String str, Object... objArr) throws IOException {
        return c(CodeBlock.of(str, objArr));
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public a c(CodeBlock codeBlock) throws IOException {
        char c;
        ListIterator<String> listIterator = codeBlock.a.listIterator();
        ClassName className = null;
        int i = 0;
        while (listIterator.hasNext()) {
            String next = listIterator.next();
            boolean z = true;
            switch (next.hashCode()) {
                case 1152:
                    if (next.equals("$$")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 1176:
                    if (next.equals("$<")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case 1178:
                    if (next.equals("$>")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 1192:
                    if (next.equals("$L")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 1194:
                    if (next.equals("$N")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1199:
                    if (next.equals("$S")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 1200:
                    if (next.equals("$T")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 1203:
                    if (next.equals("$W")) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                case 1206:
                    if (next.equals("$Z")) {
                        c = '\n';
                        break;
                    }
                    c = 65535;
                    break;
                case 1207:
                    if (next.equals("$[")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 1209:
                    if (next.equals("$]")) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    i++;
                    a(codeBlock.b.get(i));
                    break;
                case 1:
                    i++;
                    c((String) codeBlock.b.get(i));
                    break;
                case 2:
                    i++;
                    String str = (String) codeBlock.b.get(i);
                    c(str != null ? c.a(str, this.c) : "null");
                    break;
                case 3:
                    int i2 = i + 1;
                    TypeName typeName = (TypeName) codeBlock.b.get(i);
                    if ((typeName instanceof ClassName) && listIterator.hasNext() && !codeBlock.a.get(listIterator.nextIndex()).startsWith("$")) {
                        ClassName className2 = (ClassName) typeName;
                        if (this.j.contains(className2.d)) {
                            if (className != null) {
                                z = false;
                            }
                            c.b(z, "pending type for static import?!", new Object[0]);
                            className = className2;
                            i = i2;
                            break;
                        }
                    }
                    typeName.a(this);
                    i = i2;
                    break;
                case 4:
                    c("$");
                    break;
                case 5:
                    b();
                    break;
                case 6:
                    c();
                    break;
                case 7:
                    if (this.a != -1) {
                        z = false;
                    }
                    c.b(z, "statement enter $[ followed by statement enter $[", new Object[0]);
                    this.a = 0;
                    break;
                case '\b':
                    if (this.a == -1) {
                        z = false;
                    }
                    c.b(z, "statement exit $] has no matching statement enter $[", new Object[0]);
                    if (this.a > 0) {
                        b(2);
                    }
                    this.a = -1;
                    break;
                case '\t':
                    this.d.a(this.e + 2);
                    break;
                case '\n':
                    this.d.b(this.e + 2);
                    break;
                default:
                    if (className != null) {
                        if (next.startsWith(".") && a(className.d, next)) {
                            className = null;
                            break;
                        } else {
                            className.a(this);
                            className = null;
                        }
                    }
                    c(next);
                    break;
            }
        }
        return this;
    }

    public a f() throws IOException {
        this.d.a(this.e + 2);
        return this;
    }

    private static String d(String str) {
        c.a(Character.isJavaIdentifierStart(str.charAt(0)), "not an identifier: %s", str);
        for (int i = 1; i <= str.length(); i++) {
            if (!SourceVersion.isIdentifier(str.substring(0, i))) {
                return str.substring(0, i - 1);
            }
        }
        return str;
    }

    private boolean a(String str, String str2) throws IOException {
        String substring = str2.substring(1);
        if (substring.isEmpty() || !Character.isJavaIdentifierStart(substring.charAt(0))) {
            return false;
        }
        String str3 = str + "." + d(substring);
        String str4 = str + ".*";
        if (!this.k.contains(str3) && !this.k.contains(str4)) {
            return false;
        }
        c(substring);
        return true;
    }

    private void a(Object obj) throws IOException {
        if (obj instanceof TypeSpec) {
            ((TypeSpec) obj).a(this, null, Collections.emptySet());
        } else if (obj instanceof AnnotationSpec) {
            ((AnnotationSpec) obj).a(this, true);
        } else if (obj instanceof CodeBlock) {
            c((CodeBlock) obj);
        } else {
            c(String.valueOf(obj));
        }
    }

    public String a(ClassName className) {
        boolean z = false;
        for (ClassName className2 = className; className2 != null; className2 = className2.enclosingClassName()) {
            ClassName e = e(className2.simpleName());
            z = e != null;
            if (e != null && Objects.equals(e.d, className2.d)) {
                return String.join(".", className.simpleNames().subList(className2.simpleNames().size() - 1, className.simpleNames().size()));
            }
        }
        if (z) {
            return className.d;
        }
        if (Objects.equals(this.h, className.packageName())) {
            this.n.add(className.topLevelClassName().simpleName());
            return String.join(".", className.simpleNames());
        }
        if (!this.f) {
            b(className);
        }
        return className.d;
    }

    private void b(ClassName className) {
        ClassName className2;
        String simpleName;
        ClassName put;
        if (!className.packageName().isEmpty() && (put = this.m.put((simpleName = (className2 = className.topLevelClassName()).simpleName()), className2)) != null) {
            this.m.put(simpleName, put);
        }
    }

    private ClassName e(String str) {
        for (int size = this.i.size() - 1; size >= 0; size--) {
            for (TypeSpec typeSpec : this.i.get(size).typeSpecs) {
                if (Objects.equals(typeSpec.name, str)) {
                    return a(size, str);
                }
            }
        }
        if (this.i.size() > 0 && Objects.equals(this.i.get(0).name, str)) {
            return ClassName.get(this.h, str, new String[0]);
        }
        ClassName className = this.l.get(str);
        if (className != null) {
            return className;
        }
        return null;
    }

    private ClassName a(int i, String str) {
        ClassName className = ClassName.get(this.h, this.i.get(0).name, new String[0]);
        for (int i2 = 1; i2 <= i; i2++) {
            className = className.nestedClass(this.i.get(i2).name);
        }
        return className.nestedClass(str);
    }

    public a c(String str) throws IOException {
        String[] split = str.split("\n", -1);
        int length = split.length;
        int i = 0;
        boolean z = true;
        while (i < length) {
            String str2 = split[i];
            if (!z) {
                if ((this.f || this.g) && this.o) {
                    h();
                    this.d.a(this.f ? " *" : "//");
                }
                this.d.a("\n");
                this.o = true;
                int i2 = this.a;
                if (i2 != -1) {
                    if (i2 == 0) {
                        a(2);
                    }
                    this.a++;
                }
            }
            if (!str2.isEmpty()) {
                if (this.o) {
                    h();
                    if (this.f) {
                        this.d.a(" * ");
                    } else if (this.g) {
                        this.d.a("// ");
                    }
                }
                this.d.a(str2);
                this.o = false;
            }
            i++;
            z = false;
        }
        return this;
    }

    private void h() throws IOException {
        for (int i = 0; i < this.e; i++) {
            this.d.a(this.c);
        }
    }

    public Map<String, ClassName> g() {
        LinkedHashMap linkedHashMap = new LinkedHashMap(this.m);
        linkedHashMap.keySet().removeAll(this.n);
        return linkedHashMap;
    }
}
