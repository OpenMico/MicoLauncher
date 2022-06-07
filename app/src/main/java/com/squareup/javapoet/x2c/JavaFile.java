package com.squareup.javapoet.x2c;

import com.fasterxml.jackson.core.JsonPointer;
import com.squareup.javapoet.x2c.CodeBlock;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;

/* loaded from: classes2.dex */
public final class JavaFile {
    private static final Appendable a = new Appendable() { // from class: com.squareup.javapoet.x2c.JavaFile.1
        @Override // java.lang.Appendable
        public Appendable append(char c) {
            return this;
        }

        @Override // java.lang.Appendable
        public Appendable append(CharSequence charSequence) {
            return this;
        }

        @Override // java.lang.Appendable
        public Appendable append(CharSequence charSequence, int i, int i2) {
            return this;
        }
    };
    private final Set<String> b;
    private Set<String> c;
    private final String d;
    public final CodeBlock fileComment;
    public final String packageName;
    public final boolean skipJavaLangImports;
    public final TypeSpec typeSpec;

    private JavaFile(Builder builder) {
        this.fileComment = builder.c.build();
        this.packageName = builder.a;
        this.typeSpec = builder.b;
        this.skipJavaLangImports = builder.f;
        this.b = c.b(builder.d);
        this.c = builder.e;
        this.d = builder.g;
    }

    public void writeTo(Appendable appendable) throws IOException {
        a aVar = new a(a, this.d, this.b);
        a(aVar);
        a(new a(appendable, this.d, aVar.g(), this.b));
    }

    public void writeTo(Path path) throws IOException {
        c.a(Files.notExists(path, new LinkOption[0]) || Files.isDirectory(path, new LinkOption[0]), "path %s exists but is not a directory.", path);
        if (!this.packageName.isEmpty()) {
            Path path2 = path;
            for (String str : this.packageName.split("\\.")) {
                path2 = path2.resolve(str);
            }
            Files.createDirectories(path2, new FileAttribute[0]);
            path = path2;
        }
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(Files.newOutputStream(path.resolve(this.typeSpec.name + ".java"), new OpenOption[0]), StandardCharsets.UTF_8);
        Throwable th = null;
        try {
            writeTo(outputStreamWriter);
            outputStreamWriter.close();
        } catch (Throwable th2) {
            if (0 != 0) {
                try {
                    outputStreamWriter.close();
                } catch (Throwable th3) {
                    th.addSuppressed(th3);
                }
            } else {
                outputStreamWriter.close();
            }
            throw th2;
        }
    }

    public void writeTo(File file) throws IOException {
        writeTo(file.toPath());
    }

    public void writeTo(Filer filer) throws IOException {
        String str;
        if (this.packageName.isEmpty()) {
            str = this.typeSpec.name;
        } else {
            str = this.packageName + "." + this.typeSpec.name;
        }
        List<Element> list = this.typeSpec.originatingElements;
        JavaFileObject createSourceFile = filer.createSourceFile(str, (Element[]) list.toArray(new Element[list.size()]));
        try {
            Writer openWriter = createSourceFile.openWriter();
            writeTo(openWriter);
            if (openWriter != null) {
                openWriter.close();
            }
        } catch (Exception e) {
            try {
                createSourceFile.delete();
            } catch (Exception unused) {
            }
            throw e;
        }
    }

    private void a(a aVar) throws IOException {
        aVar.a(this.packageName);
        if (!this.fileComment.isEmpty()) {
            aVar.a(this.fileComment);
        }
        if (!this.packageName.isEmpty()) {
            aVar.a("package $L;\n", this.packageName);
            aVar.b("\n");
        }
        if (!this.b.isEmpty()) {
            Iterator<String> it = this.b.iterator();
            while (it.hasNext()) {
                aVar.a("import static $L;\n", it.next());
            }
            aVar.b("\n");
        }
        Iterator it2 = new TreeSet(aVar.a().values()).iterator();
        int i = 0;
        while (it2.hasNext()) {
            ClassName className = (ClassName) it2.next();
            if (!this.skipJavaLangImports || !className.packageName().equals("java.lang")) {
                aVar.a("import $L;\n", className.withoutAnnotations());
                i++;
            }
        }
        if (i > 0) {
            aVar.b("\n");
        }
        if (!this.c.isEmpty()) {
            Iterator<String> it3 = this.c.iterator();
            while (it3.hasNext()) {
                aVar.a("import $L;\n", it3.next());
            }
            aVar.b("\n");
        }
        this.typeSpec.a(aVar, null, Collections.emptySet());
        aVar.d();
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
        try {
            StringBuilder sb = new StringBuilder();
            writeTo(sb);
            return sb.toString();
        } catch (IOException unused) {
            throw new AssertionError();
        }
    }

    public JavaFileObject toJavaFileObject() {
        String str;
        StringBuilder sb = new StringBuilder();
        if (this.packageName.isEmpty()) {
            str = this.typeSpec.name;
        } else {
            str = this.packageName.replace('.', JsonPointer.SEPARATOR) + JsonPointer.SEPARATOR + this.typeSpec.name;
        }
        sb.append(str);
        sb.append(JavaFileObject.Kind.SOURCE.extension);
        return new SimpleJavaFileObject(URI.create(sb.toString()), JavaFileObject.Kind.SOURCE) { // from class: com.squareup.javapoet.x2c.JavaFile.2
            private final long b = System.currentTimeMillis();
        };
    }

    public static Builder builder(String str, TypeSpec typeSpec) {
        c.a(str, "packageName == null", new Object[0]);
        c.a(typeSpec, "typeSpec == null", new Object[0]);
        return new Builder(str, typeSpec);
    }

    public Builder toBuilder() {
        Builder builder = new Builder(this.packageName, this.typeSpec);
        builder.c.add(this.fileComment);
        builder.f = this.skipJavaLangImports;
        builder.g = this.d;
        return builder;
    }

    /* loaded from: classes2.dex */
    public static final class Builder {
        private final String a;
        private final TypeSpec b;
        private final CodeBlock.Builder c;
        private final Set<String> d;
        private final Set<String> e;
        private boolean f;
        private String g;

        private Builder(String str, TypeSpec typeSpec) {
            this.c = CodeBlock.builder();
            this.d = new TreeSet();
            this.e = new TreeSet();
            this.g = "  ";
            this.a = str;
            this.b = typeSpec;
        }

        public Builder addFileComment(String str, Object... objArr) {
            this.c.add(str, objArr);
            return this;
        }

        public Builder addStaticImport(Enum<?> r4) {
            return addStaticImport(ClassName.get(r4.getDeclaringClass()), r4.name());
        }

        public Builder addImports(TreeSet<String> treeSet) {
            if (treeSet != null) {
                this.e.addAll(treeSet);
            }
            return this;
        }

        public Builder addStaticImport(Class<?> cls, String... strArr) {
            return addStaticImport(ClassName.get(cls), strArr);
        }

        public Builder addStaticImport(ClassName className, String... strArr) {
            c.a(className != null, "className == null", new Object[0]);
            c.a(strArr != null, "names == null", new Object[0]);
            c.a(strArr.length > 0, "names array is empty", new Object[0]);
            int length = strArr.length;
            for (int i = 0; i < length; i++) {
                String str = strArr[i];
                c.a(str != null, "null entry in names array: %s", Arrays.toString(strArr));
                this.d.add(className.d + "." + str);
            }
            return this;
        }

        public Builder skipJavaLangImports(boolean z) {
            this.f = z;
            return this;
        }

        public Builder indent(String str) {
            this.g = str;
            return this;
        }

        public JavaFile build() {
            return new JavaFile(this);
        }
    }
}
