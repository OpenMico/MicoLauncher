package com.squareup.javapoet.x2c;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.SimpleElementVisitor8;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes2.dex */
public final class ClassName extends TypeName implements Comparable<ClassName> {
    public static final ClassName OBJECT = get((Class<?>) Object.class);
    final String a;
    final ClassName b;
    final String c;
    final String d;

    private ClassName(String str, ClassName className, String str2) {
        this(str, className, str2, Collections.emptyList());
    }

    private ClassName(String str, ClassName className, String str2, List<AnnotationSpec> list) {
        super(list);
        this.a = str;
        this.b = className;
        this.c = str2;
        if (className != null) {
            str2 = className.d + '.' + str2;
        } else if (!str.isEmpty()) {
            str2 = str + '.' + str2;
        }
        this.d = str2;
    }

    @Override // com.squareup.javapoet.x2c.TypeName
    public ClassName annotated(List<AnnotationSpec> list) {
        return new ClassName(this.a, this.b, this.c, concatAnnotations(list));
    }

    @Override // com.squareup.javapoet.x2c.TypeName
    public ClassName withoutAnnotations() {
        if (!isAnnotated()) {
            return this;
        }
        ClassName className = this.b;
        return new ClassName(this.a, className != null ? className.withoutAnnotations() : null, this.c);
    }

    @Override // com.squareup.javapoet.x2c.TypeName
    public boolean isAnnotated() {
        ClassName className;
        return super.isAnnotated() || ((className = this.b) != null && className.isAnnotated());
    }

    public String packageName() {
        return this.a;
    }

    public ClassName enclosingClassName() {
        return this.b;
    }

    public ClassName topLevelClassName() {
        ClassName className = this.b;
        return className != null ? className.topLevelClassName() : this;
    }

    public String reflectionName() {
        if (this.b != null) {
            return this.b.reflectionName() + '$' + this.c;
        } else if (this.a.isEmpty()) {
            return this.c;
        } else {
            return this.a + '.' + this.c;
        }
    }

    public List<String> simpleNames() {
        ArrayList arrayList = new ArrayList();
        if (this.b != null) {
            arrayList.addAll(enclosingClassName().simpleNames());
        }
        arrayList.add(this.c);
        return arrayList;
    }

    public ClassName peerClass(String str) {
        return new ClassName(this.a, this.b, str);
    }

    public ClassName nestedClass(String str) {
        return new ClassName(this.a, this, str);
    }

    public String simpleName() {
        return this.c;
    }

    public static ClassName get(Class<?> cls) {
        c.a(cls, "clazz == null", new Object[0]);
        c.a(!cls.isPrimitive(), "primitive types cannot be represented as a ClassName", new Object[0]);
        c.a(!Void.TYPE.equals(cls), "'void' type cannot be represented as a ClassName", new Object[0]);
        c.a(!cls.isArray(), "array types cannot be represented as a ClassName", new Object[0]);
        String str = "";
        while (cls.isAnonymousClass()) {
            int lastIndexOf = cls.getName().lastIndexOf(36);
            str = cls.getName().substring(lastIndexOf) + str;
            cls = cls.getEnclosingClass();
        }
        String str2 = cls.getSimpleName() + str;
        if (cls.getEnclosingClass() != null) {
            return get(cls.getEnclosingClass()).nestedClass(str2);
        }
        int lastIndexOf2 = cls.getName().lastIndexOf(46);
        return new ClassName(lastIndexOf2 != -1 ? cls.getName().substring(0, lastIndexOf2) : null, null, str2);
    }

    public static ClassName bestGuess(String str) {
        int i = 0;
        while (i < str.length() && Character.isLowerCase(str.codePointAt(i))) {
            i = str.indexOf(46, i) + 1;
            c.a(i != 0, "couldn't make a guess for %s", str);
        }
        String substring = i == 0 ? "" : str.substring(0, i - 1);
        ClassName className = null;
        String[] split = str.substring(i).split("\\.", -1);
        int length = split.length;
        int i2 = 0;
        while (i2 < length) {
            String str2 = split[i2];
            c.a(!str2.isEmpty() && Character.isUpperCase(str2.codePointAt(0)), "couldn't make a guess for %s", str);
            i2++;
            className = new ClassName(substring, className, str2);
        }
        return className;
    }

    public static ClassName get(String str, String str2, String... strArr) {
        ClassName className = new ClassName(str, null, str2);
        for (String str3 : strArr) {
            className = className.nestedClass(str3);
        }
        return className;
    }

    public static ClassName get(final TypeElement typeElement) {
        c.a(typeElement, "element == null", new Object[0]);
        final String obj = typeElement.getSimpleName().toString();
        return (ClassName) typeElement.getEnclosingElement().accept(new SimpleElementVisitor8<ClassName, Void>() { // from class: com.squareup.javapoet.x2c.ClassName.1
        }, (Object) null);
    }

    public int compareTo(ClassName className) {
        return this.d.compareTo(className.d);
    }

    @Override // com.squareup.javapoet.x2c.TypeName
    public a a(a aVar) throws IOException {
        String str;
        boolean z = false;
        for (ClassName className : a()) {
            if (z) {
                aVar.b(".");
                str = className.c;
            } else if (className.isAnnotated() || className == this) {
                str = aVar.a(className);
                int lastIndexOf = str.lastIndexOf(46);
                if (lastIndexOf != -1) {
                    int i = lastIndexOf + 1;
                    aVar.c(str.substring(0, i));
                    str = str.substring(i);
                    z = true;
                }
            }
            if (className.isAnnotated()) {
                if (z) {
                    aVar.b(StringUtils.SPACE);
                }
                className.b(aVar);
            }
            aVar.b(str);
            z = true;
        }
        return aVar;
    }

    private List<ClassName> a() {
        ArrayList arrayList = new ArrayList();
        for (ClassName className = this; className != null; className = className.b) {
            arrayList.add(className);
        }
        Collections.reverse(arrayList);
        return arrayList;
    }
}
