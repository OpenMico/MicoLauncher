package com.zhangyue.we.anoprocesser.xml;

import com.squareup.javapoet.x2c.ClassName;
import com.squareup.javapoet.x2c.JavaFile;
import com.squareup.javapoet.x2c.MethodSpec;
import com.squareup.javapoet.x2c.TypeSpec;
import com.zhangyue.we.x2c.BuildConfig;
import java.io.IOException;
import java.util.TreeSet;
import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;

/* loaded from: classes4.dex */
public class LayoutWriter {
    private Filer a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private TreeSet<String> g;

    public LayoutWriter(String str, Filer filer, String str2, String str3, String str4, String str5, TreeSet<String> treeSet) {
        this.c = str;
        this.a = filer;
        this.b = str2;
        this.d = str3;
        this.e = str4;
        this.f = str5;
        this.g = treeSet;
    }

    public String write() {
        TypeSpec build = TypeSpec.classBuilder(this.b).addMethod(MethodSpec.methodBuilder("createView").addParameter(ClassName.get("android.content", "Context", new String[0]), "ctx", new Modifier[0]).addStatement(this.c, new Object[0]).returns(ClassName.get("android.view", "View", new String[0])).addAnnotation(Override.class).addModifiers(Modifier.PUBLIC).build()).addSuperinterface(ClassName.get(BuildConfig.APPLICATION_ID, "IViewCreator", new String[0])).addModifiers(Modifier.PUBLIC).addJavadoc(String.format("WARN!!! dont edit this file\ntranslate from {@link  %s.R.layout.%s}\nautho chengwei \nemail chengwei@zhangyue.com\n", this.d, this.f), new Object[0]).build();
        String str = "com.zhangyue.we.x2c.layouts";
        String str2 = this.e;
        if (str2 != null && str2.length() > 0) {
            str = str + "." + this.e;
        }
        try {
            JavaFile.builder(str, build).addImports(this.g).build().writeTo(this.a);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str + "." + this.b;
    }
}
