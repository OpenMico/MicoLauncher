package com.zhangyue.we.anoprocesser.xml;

import com.squareup.javapoet.x2c.ClassName;
import com.squareup.javapoet.x2c.JavaFile;
import com.squareup.javapoet.x2c.MethodSpec;
import com.squareup.javapoet.x2c.TypeSpec;
import com.umeng.analytics.pro.ai;
import com.umeng.analytics.pro.c;
import com.zhangyue.we.anoprocesser.Util;
import com.zhangyue.we.x2c.BuildConfig;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;
import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;

/* loaded from: classes4.dex */
public class MapWriter {
    private int a;
    private ArrayList<File> b;
    private ArrayList<String> c;
    private Filer d;

    /* JADX INFO: Access modifiers changed from: package-private */
    public MapWriter(int i, ArrayList<File> arrayList, ArrayList<String> arrayList2, Filer filer) {
        this.a = i;
        this.b = arrayList;
        this.c = arrayList2;
        this.d = filer;
    }

    public void write() {
        ArrayList<File> arrayList;
        ArrayList<String> arrayList2 = this.c;
        if (arrayList2 != null && arrayList2.size() != 0 && (arrayList = this.b) != null && arrayList.size() != 0) {
            TreeSet<String> treeSet = new TreeSet<>();
            StringBuilder sb = new StringBuilder();
            if (this.b.size() == 1 && this.c.size() == 1) {
                sb.append(String.format("return new %s().createView(context)", this.c.get(0)));
            } else {
                sb.append("View view = null ;");
                sb.append("\nint sdk = Build.VERSION.SDK_INT;");
                treeSet.add("android.os.Build");
                for (int i = 0; i < this.c.size(); i++) {
                    if (i == this.c.size() - 1) {
                        sb.append(" else {");
                    } else {
                        String layoutCategory = Util.getLayoutCategory(this.b.get(i));
                        if (layoutCategory.equals("land")) {
                            sb.append("\nint orientation = context.getResources().getConfiguration().orientation;");
                            sb.append("\nboolean isLandscape = orientation == Configuration.ORIENTATION_LANDSCAPE;");
                            sb.append("\nif (isLandscape) {");
                            treeSet.add("android.content.res.Configuration");
                        } else if (layoutCategory.startsWith(ai.aC)) {
                            sb.append(String.format(" else if (sdk >= %s) {", layoutCategory.substring(layoutCategory.lastIndexOf(ai.aC) + 1)));
                        }
                    }
                    sb.append(String.format("\n\tview = new %s().createView(context);\n}", this.c.get(i)));
                }
                sb.append("\nreturn view");
            }
            try {
                JavaFile.builder(BuildConfig.APPLICATION_ID, TypeSpec.classBuilder(String.format("X2C%s_%s", Integer.valueOf(this.a), this.b.get(0).getName().substring(0, this.b.get(0).getName().indexOf(".")))).addSuperinterface(ClassName.get(BuildConfig.APPLICATION_ID, "IViewCreator", new String[0])).addModifiers(Modifier.PUBLIC).addMethod(MethodSpec.methodBuilder("createView").addParameter(ClassName.get("android.content", "Context", new String[0]), c.R, new Modifier[0]).addStatement(sb.toString(), new Object[0]).returns(ClassName.get("android.view", "View", new String[0])).addAnnotation(Override.class).addModifiers(Modifier.PUBLIC).build()).addJavadoc(String.format("WARN!!! don't edit this file\n\nauthor chengwei \nemail chengwei@zhangyue.com\n", new Object[0]), new Object[0]).build()).addImports(treeSet).build().writeTo(this.d);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
