package com.zhangyue.we.view;

import androidx.databinding.ViewDataBinding;
import com.alibaba.fastjson.parser.JSONLexer;
import com.umeng.analytics.pro.ai;
import com.zhangyue.we.anoprocesser.Log;
import com.zhangyue.we.anoprocesser.xml.LayoutManager;
import com.zhangyue.we.anoprocesser.xml.Style;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;
import org.xml.sax.Attributes;

/* loaded from: classes4.dex */
public class View implements ITranslator {
    private View a;
    private ArrayList<View> b;
    private String c;
    private String d;
    private String e;
    private String f;
    private String g;
    private Attributes h;
    private String i;
    private HashMap<String, String> j;
    private int k;
    private String l;
    private String m;
    protected String mLayoutParamsObj;
    private boolean s;
    private String t;
    private String u;
    private int v;
    private String n = "0";
    private String o = "0";
    private String p = "0";
    private String q = "0";
    private String r = "0";
    protected TreeSet<String> mImports = new TreeSet<>();

    @Override // com.zhangyue.we.view.ITranslator
    public void onAttributeEnd(StringBuilder sb) {
    }

    public View(String str, String str2, Attributes attributes) {
        this.i = str;
        this.e = a(str2);
        this.d = str2;
        this.h = attributes;
        this.mImports.add("android.content.res.Resources");
        this.mImports.add("android.view.View");
        this.mImports.add("android.util.TypedValue");
        this.mImports.add("android.graphics.Color");
        this.mImports.add("android.view.ViewGroup");
        this.mImports.add("android.graphics.drawable.ColorDrawable");
        this.mImports.add("com.zhangyue.we.x2c.X2CUtils");
        this.mImports.add(String.format("%s.R", this.i));
    }

    public void setDirName(String str) {
        this.t = str;
    }

    public void setIsDataBinding(boolean z) {
        this.s = z;
    }

    public void setLayoutName(String str) {
        this.u = str;
    }

    public void setParent(View view) {
        this.a = view;
        if (view != null) {
            view.addChilden(this);
        }
        this.c = a(this.h);
    }

    public void addChilden(View view) {
        if (this.b == null) {
            this.b = new ArrayList<>();
        }
        this.b.add(view);
    }

    public void translate(StringBuilder sb) {
        sb.append(this.c);
        ArrayList<View> arrayList = this.b;
        if (arrayList != null) {
            Iterator<View> it = arrayList.iterator();
            while (it.hasNext()) {
                View next = it.next();
                next.translate(sb);
                this.mImports.addAll(next.mImports);
            }
        }
    }

    public TreeSet<String> getImports() {
        return this.mImports;
    }

    private String a(String str) {
        if (!str.contains(".")) {
            char c = 65535;
            switch (str.hashCode()) {
                case -1650269616:
                    if (str.equals("fragment")) {
                        c = 4;
                        break;
                    }
                    break;
                case -1406842887:
                    if (str.equals("WebView")) {
                        c = 3;
                        break;
                    }
                    break;
                case -1013307840:
                    if (str.equals("TextureView")) {
                        c = 5;
                        break;
                    }
                    break;
                case 2666181:
                    if (str.equals("View")) {
                        c = 1;
                        break;
                    }
                    break;
                case 265037010:
                    if (str.equals("SurfaceView")) {
                        c = 6;
                        break;
                    }
                    break;
                case 1260470547:
                    if (str.equals("ViewStub")) {
                        c = 2;
                        break;
                    }
                    break;
                case 1942574248:
                    if (str.equals("include")) {
                        c = 0;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                case 1:
                    str = "android.view.View";
                    break;
                case 2:
                    str = "android.view.ViewStub";
                    break;
                case 3:
                    str = "android.webkit.WebView";
                    break;
                case 4:
                    str = "android.widget.FrameLayout";
                    break;
                case 5:
                    str = "android.view.TextureView";
                    break;
                case 6:
                    str = "android.view.SurfaceView";
                    break;
                default:
                    str = "android.widget." + str;
                    break;
            }
        }
        this.mImports.add(str);
        return str.substring(str.lastIndexOf(".") + 1);
    }

    public String getObjName() {
        if (this.f == null) {
            View d = d();
            this.f = this.e.substring(0, 1).toLowerCase() + this.e.substring(1) + d.k;
            StringBuilder sb = new StringBuilder();
            sb.append("layoutParam");
            sb.append(d.k);
            this.mLayoutParamsObj = sb.toString();
            d.k++;
        }
        return this.f;
    }

    public String getLayoutParams() {
        if (this.g == null) {
            this.g = this.e + ".LayoutParams";
        }
        return this.g;
    }

    private String a(Attributes attributes) {
        StringBuilder sb = new StringBuilder();
        if (this.a == null) {
            sb.append("\tResources res = ctx.getResources();\n\n");
        }
        String objName = getObjName();
        if (this.d.equals("include")) {
            sb.append(String.format("%s %s =(View) new %s().createView(ctx);\n", this.e, objName, LayoutManager.instance().translate(c())));
        } else {
            String str = this.e;
            sb.append(String.format("%s %s = new %s(ctx);\n", str, objName, str));
        }
        this.j = e();
        String b = b(a());
        String c = c(b());
        View view = this.a;
        if (view != null) {
            String layoutParams = view.getLayoutParams();
            sb.append(String.format("%s %s = new %s(%s,%s);\n", layoutParams, this.mLayoutParamsObj, layoutParams, b, c));
        } else {
            String layoutParams2 = getLayoutParams();
            sb.append(String.format("%s %s = new %s(%s,%s);\n", layoutParams2, this.mLayoutParamsObj, layoutParams2, b, c));
        }
        ArrayList<ITranslator> f = f();
        HashMap<String, String> hashMap = this.j;
        if (hashMap != null) {
            for (String str2 : hashMap.keySet()) {
                Iterator<ITranslator> it = f.iterator();
                while (it.hasNext() && !it.next().translate(sb, str2, this.j.get(str2))) {
                }
            }
        }
        int length = attributes.getLength();
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            String qName = attributes.getQName(i);
            String value = attributes.getValue(i);
            if (value.startsWith("@{")) {
                this.s = true;
                break;
            }
            Iterator<ITranslator> it2 = f.iterator();
            while (it2.hasNext() && !it2.next().translate(sb, qName, value)) {
            }
            i++;
        }
        Iterator<ITranslator> it3 = f.iterator();
        while (it3.hasNext()) {
            it3.next().onAttributeEnd(sb);
        }
        if (this.a != null) {
            sb.append(String.format("%s.setLayoutParams(%s);\n", objName, this.mLayoutParamsObj));
            sb.append(String.format("%s.addView(%s);\n", this.a.getObjName(), objName));
        } else {
            sb.append(String.format("%s.setLayoutParams(%s);\n", objName, this.mLayoutParamsObj));
        }
        if (!this.n.equals("0")) {
            sb.append(getObjName());
            String str3 = this.n;
            sb.append(String.format(".setPadding(%s,%s,%s,%s);\n", str3, str3, str3, str3));
        } else if (!this.o.equals("0") || !this.p.equals("0") || !this.q.equals("0") || !this.r.equals("0")) {
            sb.append(getObjName());
            sb.append(String.format(".setPadding(%s,%s,%s,%s);\n", this.o, this.p, this.q, this.r));
        }
        if (this.d.equals("fragment")) {
            if (this.l == null) {
                Log.e("fragment label must set android:id");
            }
            if (this.m == null) {
                Log.e("fragment label must set android:name");
            }
            this.mImports.add("android.app.FragmentManager");
            this.mImports.add("android.app.FragmentTransaction");
            this.mImports.add("android.app.Activity");
            this.mImports.add("java.lang.reflect.Method");
            this.mImports.add(this.m);
            String str4 = this.m;
            sb.append(String.format("((Activity) ctx).getFragmentManager()\n\t\t\t\t.beginTransaction()\n\t\t\t\t.replace(%s, new %s())\n\t\t\t\t.commit();\n", this.l, str4.substring(str4.lastIndexOf(".") + 1)));
            String str5 = "fm" + d().k;
            sb.append(String.format("FragmentManager %s = ((Activity)ctx).getFragmentManager();\n", str5));
            sb.append(String.format("Class clz = %s.getClass();\n", str5));
            sb.append("Method method;\n");
            sb.append("while (clz != null) {\n");
            sb.append("\ttry {\n");
            sb.append("\t\tmethod = clz.getDeclaredMethod(\"execPendingActions\");\n ");
            sb.append("\t\tif (method != null) {\n");
            sb.append("\t\t\tmethod.setAccessible(true);\n");
            sb.append(String.format("\t\t\tmethod.invoke(%s);\n", str5));
            sb.append("\t\t\tbreak;\n");
            sb.append("\t\t} else {\n");
            sb.append("\t\t\tclz = clz.getSuperclass();\n");
            sb.append("\t\t }\n");
            sb.append("\t} catch (Exception e) {\n");
            sb.append("\t\tclz = clz.getSuperclass();\n");
            sb.append("\t}\n");
            sb.append("}\n");
            sb.append("\n");
        }
        if (this.s) {
            if (this.a == null) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(this.t);
                sb2.append("/");
                sb2.append(this.u);
                sb2.append("_");
                int i2 = this.v;
                this.v = i2 + 1;
                sb2.append(i2);
                r(sb, sb2.toString());
            } else {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(ViewDataBinding.BINDING_TAG_PREFIX);
                View d = d();
                int i3 = d.v;
                d.v = i3 + 1;
                sb3.append(i3);
                r(sb, sb3.toString());
            }
        }
        sb.append("\n");
        return sb.toString();
    }

    private String a() {
        Attributes attributes = this.h;
        if (attributes == null) {
            return "";
        }
        String value = attributes.getValue("android:layout_width");
        if (value != null) {
            return value;
        }
        HashMap<String, String> hashMap = this.j;
        if (hashMap != null) {
            return hashMap.get("android:layout_width");
        }
        return null;
    }

    private String b() {
        Attributes attributes = this.h;
        if (attributes == null) {
            return "";
        }
        String value = attributes.getValue("android:layout_height");
        if (value != null) {
            return value;
        }
        HashMap<String, String> hashMap = this.j;
        if (hashMap != null) {
            return hashMap.get("android:layout_height");
        }
        return null;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.zhangyue.we.view.ITranslator
    public boolean translate(StringBuilder sb, String str, String str2) {
        char c;
        switch (str.hashCode()) {
            case -2086904019:
                if (str.equals("android:paddingLeft")) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            case -2025228987:
                if (str.equals("android:orientation")) {
                    c = 21;
                    break;
                }
                c = 65535;
                break;
            case -1974691164:
                if (str.equals("android:layout_gravity")) {
                    c = '#';
                    break;
                }
                c = 65535;
                break;
            case -1753338733:
                if (str.equals("android:alpha")) {
                    c = '$';
                    break;
                }
                c = 65535;
                break;
            case -1663659198:
                if (str.equals("android:ellipsize")) {
                    c = 27;
                    break;
                }
                c = 65535;
                break;
            case -1618184385:
                if (str.equals("android:minWidth")) {
                    c = ' ';
                    break;
                }
                c = 65535;
                break;
            case -1246038151:
                if (str.equals("android:textSize")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1245969729:
                if (str.equals("android:layout_marginLeft")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -1229799930:
                if (str.equals("android:padding")) {
                    c = 19;
                    break;
                }
                c = 65535;
                break;
            case -1026014026:
                if (str.equals("android:name")) {
                    c = '%';
                    break;
                }
                c = 65535;
                break;
            case -1025831080:
                if (str.equals("android:text")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -898609931:
                if (str.equals("android:paddingEnd")) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case -898595473:
                if (str.equals("android:paddingTop")) {
                    c = 17;
                    break;
                }
                c = 65535;
                break;
            case -801427367:
                if (str.equals("android:scaleType")) {
                    c = 23;
                    break;
                }
                c = 65535;
                break;
            case -263854154:
                if (str.equals("android:paddingRight")) {
                    c = 16;
                    break;
                }
                c = 65535;
                break;
            case -262608388:
                if (str.equals("android:paddingStart")) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case -142937021:
                if (str.equals("android:gravity")) {
                    c = 20;
                    break;
                }
                c = 65535;
                break;
            case -45679976:
                if (str.equals("android:layout_margin")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -41670703:
                if (str.equals("android:paddingBottom")) {
                    c = 18;
                    break;
                }
                c = 65535;
                break;
            case 12912363:
                if (str.equals("android:textColor")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 27850041:
                if (str.equals("android:textStyle")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 35305060:
                if (str.equals("android:layout_marginRight")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case 36550826:
                if (str.equals("android:layout_marginStart")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 147862694:
                if (str.equals("android:maxLines")) {
                    c = 29;
                    break;
                }
                c = 65535;
                break;
            case 158012269:
                if (str.equals("android:maxWidth")) {
                    c = 31;
                    break;
                }
                c = 65535;
                break;
            case 170418272:
                if (str.equals("android:maxHeight")) {
                    c = 30;
                    break;
                }
                c = 65535;
                break;
            case 244037474:
                if (str.equals("android:layout_weight")) {
                    c = '\"';
                    break;
                }
                c = 65535;
                break;
            case 508594508:
                if (str.equals("android:lineSpacingExtra")) {
                    c = 28;
                    break;
                }
                c = 65535;
                break;
            case 642330339:
                if (str.equals("android:layout_marginBottom")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case 652537635:
                if (str.equals("android:layout_marginEnd")) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case 652552093:
                if (str.equals("android:layout_marginTop")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case 722953734:
                if (str.equals("android:id")) {
                    c = 22;
                    break;
                }
                c = 65535;
                break;
            case 936739417:
                if (str.equals("android:src")) {
                    c = 24;
                    break;
                }
                c = 65535;
                break;
            case 936739855:
                if (str.equals("android:tag")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case 942896846:
                if (str.equals("android:minHeight")) {
                    c = '!';
                    break;
                }
                c = 65535;
                break;
            case 952606973:
                if (str.equals("android:visibility")) {
                    c = 25;
                    break;
                }
                c = 65535;
                break;
            case 1128284635:
                if (str.equals("android:clipToPadding")) {
                    c = JSONLexer.EOI;
                    break;
                }
                c = 65535;
                break;
            case 1974047513:
                if (str.equals("android:background")) {
                    c = 3;
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
                return w(sb, str2);
            case 1:
                return p(sb, str2);
            case 2:
                return q(sb, str2);
            case 3:
                return y(sb, str2);
            case 4:
                return b(sb, str2);
            case 5:
                return a(sb, str2);
            case 6:
                return s(sb, str2);
            case 7:
                return s(sb, str2);
            case '\b':
                return r(sb, str2);
            case '\t':
                return t(sb, str2);
            case '\n':
                return u(sb, str2);
            case 11:
                return u(sb, str2);
            case '\f':
                return v(sb, str2);
            case '\r':
                this.o = getWH(str2);
                return true;
            case 14:
                this.q = getWH(str2);
                return true;
            case 15:
                this.o = getWH(str2);
                return true;
            case 16:
                this.q = getWH(str2);
                return true;
            case 17:
                this.p = getWH(str2);
                return true;
            case 18:
                this.r = getWH(str2);
                return true;
            case 19:
                this.n = getWH(str2);
                return true;
            case 20:
                return A(sb, str2);
            case 21:
                return z(sb, str2);
            case 22:
                return B(sb, str2);
            case 23:
                return f(sb, str2);
            case 24:
                return x(sb, str2);
            case 25:
                return o(sb, str2);
            case 26:
                return n(sb, str2);
            case 27:
                return m(sb, str2);
            case 28:
                return l(sb, str2);
            case 29:
                return g(sb, str2);
            case 30:
                return h(sb, str2);
            case 31:
                return i(sb, str2);
            case ' ':
                return j(sb, str2);
            case '!':
                return k(sb, str2);
            case '\"':
                return e(sb, str2);
            case '#':
                return d(sb, str2);
            case '$':
                return c(sb, str2);
            case '%':
                this.m = str2;
                return true;
            default:
                return false;
        }
    }

    private boolean a(StringBuilder sb, String str) {
        String str2 = this.mLayoutParamsObj;
        if (str2 != null) {
            sb.append(String.format("%s.setMargins(%s,%s,%s,%s);\n", str2, getWH(str), getWH(str), getWH(str), getWH(str)));
        }
        return true;
    }

    private boolean b(StringBuilder sb, String str) {
        this.mImports.add("android.graphics.Typeface");
        sb.append(String.format("%s.setTypeface(%s);\n", getObjName(), i(str)));
        return true;
    }

    private boolean c(StringBuilder sb, String str) {
        sb.append(String.format("%s.setAlpha(%s);\n", getObjName(), getFloat(str)));
        return true;
    }

    private boolean d(StringBuilder sb, String str) {
        String str2 = this.mLayoutParamsObj;
        if (str2 != null) {
            sb.append(String.format("%s.gravity= %s ;\n", str2, d(str)));
        }
        return true;
    }

    private boolean e(StringBuilder sb, String str) {
        String str2 = this.mLayoutParamsObj;
        if (str2 != null) {
            sb.append(String.format("%s.weight= %sf ;\n", str2, str));
        }
        return true;
    }

    private boolean f(StringBuilder sb, String str) {
        this.mImports.add("android.widget.ImageView.ScaleType");
        sb.append(String.format("%s.setScaleType(%s);\n", getObjName(), j(str)));
        return true;
    }

    private boolean g(StringBuilder sb, String str) {
        sb.append(String.format("%s.setMaxLines(%s);\n", getObjName(), str));
        return true;
    }

    private boolean h(StringBuilder sb, String str) {
        sb.append(String.format("%s.setMaxHeight(%s);\n", getObjName(), getDimen(str)));
        return true;
    }

    private boolean i(StringBuilder sb, String str) {
        sb.append(String.format("%s.setMaxWidth(%s);\n", getObjName(), getDimen(str)));
        return true;
    }

    private boolean j(StringBuilder sb, String str) {
        sb.append(String.format("%s.setMinimumWidth(%s);\n", getObjName(), getDimen(str)));
        return true;
    }

    private boolean k(StringBuilder sb, String str) {
        sb.append(String.format("%s.setMinimumHeight(%s);\n", getObjName(), getDimen(str)));
        return true;
    }

    private boolean l(StringBuilder sb, String str) {
        sb.append(String.format("%s.setLineSpacing(%s,1);\n", getObjName(), getDimen(str)));
        return true;
    }

    private boolean m(StringBuilder sb, String str) {
        this.mImports.add("android.text.TextUtils");
        sb.append(String.format("%s.setEllipsize(%s);\n", getObjName(), h(str)));
        return true;
    }

    private boolean n(StringBuilder sb, String str) {
        sb.append(String.format("%s.setClipToPadding(%s);\n", getObjName(), str));
        return true;
    }

    private boolean o(StringBuilder sb, String str) {
        sb.append(String.format("%s.setVisibility(%s);\n", getObjName(), g(str)));
        return true;
    }

    private boolean p(StringBuilder sb, String str) {
        sb.append(String.format("%s.setTextColor(%s);\n", getObjName(), getColor(str)));
        return true;
    }

    private boolean q(StringBuilder sb, String str) {
        sb.append(String.format("%s.setText(%s);\n", getObjName(), getString(str)));
        return true;
    }

    private boolean r(StringBuilder sb, String str) {
        if (str.startsWith("@id")) {
            sb.append(String.format("%s.setTag(R.id.%s);\n", getObjName(), str.substring(str.indexOf("/") + 1)));
        } else if (str.startsWith("@android:id")) {
            sb.append(String.format("%s.setTag(android.R.id.%s);\n", getObjName(), str.substring(str.indexOf("/") + 1)));
        } else {
            sb.append(String.format("%s.setTag(%s);\n", getObjName(), getString(str)));
        }
        return true;
    }

    private boolean s(StringBuilder sb, String str) {
        String str2 = this.mLayoutParamsObj;
        if (str2 != null) {
            sb.append(String.format("%s.leftMargin= %s ;\n", str2, getWH(str)));
        }
        return true;
    }

    private boolean t(StringBuilder sb, String str) {
        String str2 = this.mLayoutParamsObj;
        if (str2 != null) {
            sb.append(String.format("%s.topMargin= %s ;\n", str2, getWH(str)));
        }
        return true;
    }

    private boolean u(StringBuilder sb, String str) {
        String str2 = this.mLayoutParamsObj;
        if (str2 != null) {
            sb.append(String.format("%s.rightMargin= %s ;\n", str2, getWH(str)));
        }
        return true;
    }

    private boolean v(StringBuilder sb, String str) {
        String str2 = this.mLayoutParamsObj;
        if (str2 != null) {
            sb.append(String.format("%s.bottomMargin= %s ;\n", str2, getWH(str)));
        }
        return true;
    }

    private boolean w(StringBuilder sb, String str) {
        String str2;
        String str3;
        if (str.startsWith("@")) {
            str3 = "TypedValue.COMPLEX_UNIT_PX";
            str2 = String.format("res.getDimension(R.dimen.%s)", str.substring(str.indexOf("/") + 1));
        } else if (str.endsWith("dp") || str.endsWith("dip")) {
            str3 = "TypedValue.COMPLEX_UNIT_DIP";
            str2 = str.substring(0, str.indexOf("d"));
        } else if (str.endsWith("sp")) {
            str3 = "TypedValue.COMPLEX_UNIT_SP";
            str2 = str.substring(0, str.indexOf(ai.az));
        } else {
            str3 = "TypedValue.COMPLEX_UNIT_PX";
            str2 = str.substring(0, str.indexOf(ai.av));
        }
        sb.append(String.format("%s.setTextSize(%s,%s);\n", getObjName(), str3, str2));
        this.mImports.add("android.util.TypedValue");
        return true;
    }

    private boolean x(StringBuilder sb, String str) {
        if (str.startsWith("#") || str.startsWith("@color") || str.startsWith("@android:color")) {
            sb.append(String.format("%s.setImageDrawable(new ColorDrawable(%s));\n", getObjName(), getColor(str)));
        } else if (str.equals("null")) {
            sb.append(String.format("%s.setImageDrawable(%s);\n", getObjName(), "null"));
        } else if (str.startsWith("?")) {
            sb.append(String.format("%s.setImageResource(X2CUtils.getResourceIdFromAttr(ctx, %s));\n", getObjName(), getUnknown(str)));
        } else {
            sb.append(String.format("%s.setImageResource(%s);\n", getObjName(), getDrawable(str)));
        }
        return true;
    }

    private boolean y(StringBuilder sb, String str) {
        if (str.startsWith("#") || str.startsWith("@color") || str.startsWith("@android:color")) {
            sb.append(String.format("%s.setBackgroundColor(%s);\n", getObjName(), getColor(str)));
        } else if (str.equals("@null")) {
            sb.append(String.format("%s.setBackgroundResource(0);\n", getObjName()));
        } else if (str.startsWith("?")) {
            sb.append(String.format("%s.setBackgroundResource(X2CUtils.getResourceIdFromAttr(ctx, %s));\n", getObjName(), getUnknown(str)));
        } else {
            sb.append(String.format("%s.setBackgroundResource(%s);\n", getObjName(), getDrawable(str)));
        }
        return true;
    }

    private boolean z(StringBuilder sb, String str) {
        sb.append(String.format("%s.setOrientation(%s);\n", getObjName(), f(str)));
        return true;
    }

    private boolean A(StringBuilder sb, String str) {
        sb.append(String.format("%s.setGravity(%s);\n", getObjName(), d(str)));
        return true;
    }

    private boolean B(StringBuilder sb, String str) {
        if (str.startsWith("@android:id")) {
            this.l = "android.R.id." + str.substring(str.indexOf("/") + 1);
            sb.append(String.format("%s.setId(%s);\n", getObjName(), this.l));
        } else {
            this.l = "R.id." + str.substring(str.indexOf("/") + 1);
            sb.append(String.format("%s.setId(%s);\n", getObjName(), this.l));
        }
        return true;
    }

    private String b(String str) {
        if (str != null) {
            return getWH(str);
        }
        this.mImports.add("android.view.ViewGroup");
        return "ViewGroup.LayoutParams.MATCH_PARENT";
    }

    private String c(String str) {
        if (str != null) {
            return getWH(str);
        }
        this.mImports.add("android.view.ViewGroup");
        return "ViewGroup.LayoutParams.WRAP_CONTENT";
    }

    public static String getWH(String str) {
        if (str == null) {
            return "0";
        }
        char c = 65535;
        int hashCode = str.hashCode();
        if (hashCode != 3657802) {
            if (hashCode != 343327108) {
                if (hashCode != 1261922022) {
                    if (hashCode == 1386124388 && str.equals("match_parent")) {
                        c = 1;
                    }
                } else if (str.equals("fill_parent")) {
                    c = 0;
                }
            } else if (str.equals("wrap_content")) {
                c = 3;
            }
        } else if (str.equals("wrap")) {
            c = 2;
        }
        switch (c) {
            case 0:
                return "ViewGroup.LayoutParams.FILL_PARENT";
            case 1:
                return "ViewGroup.LayoutParams.MATCH_PARENT";
            case 2:
            case 3:
                return "ViewGroup.LayoutParams.WRAP_CONTENT";
            default:
                return getDimen(str);
        }
    }

    public static String getDimen(String str) {
        String str2;
        String str3;
        if (str.startsWith("@dimen")) {
            return String.format("(int)res.getDimension(R.dimen.%s)", str.substring(str.indexOf("/") + 1));
        }
        if (str.startsWith("@android:dimen")) {
            return String.format("(int)res.getDimension(android.R.dimen.%s)", str.substring(str.indexOf("/") + 1));
        }
        if (str.endsWith("dp") || str.endsWith("dip")) {
            str3 = "TypedValue.COMPLEX_UNIT_DIP";
            str2 = str.substring(0, str.indexOf("d"));
        } else if (str.endsWith("sp")) {
            str3 = "TypedValue.COMPLEX_UNIT_SP";
            str2 = str.substring(0, str.indexOf(ai.az));
        } else {
            str3 = "TypedValue.COMPLEX_UNIT_PX";
            str2 = str.substring(0, str.indexOf(ai.av));
        }
        return String.format("(int)(TypedValue.applyDimension(%s,%s,res.getDisplayMetrics()))", str3, str2);
    }

    public static String getColor(String str) {
        if (str.equals("#000")) {
            return "Color.parseColor(\"#000000\")";
        }
        if (str.equals("#FFF")) {
            return "Color.parseColor(\"#FFFFFF\")";
        }
        if (str.startsWith("#")) {
            return "Color.parseColor(\"" + str + "\")";
        } else if (str.startsWith("@android:color")) {
            return "res.getColor(android.R.color." + str.substring(str.indexOf("/") + 1) + ")";
        } else if (!str.startsWith("@color")) {
            return "0";
        } else {
            return "res.getColor(R.color." + str.substring(str.indexOf("/") + 1) + ")";
        }
    }

    public static String getId(String str) {
        if (!str.contains("id/")) {
            return "0";
        }
        return "R.id." + str.substring(str.lastIndexOf("/") + 1);
    }

    public static String getFloat(String str) {
        return str + "f";
    }

    public static String getBoolean(String str) {
        if (!str.startsWith("@")) {
            return str;
        }
        return String.format("res.getBoolean(R.bool.%s)", new Object[0]) + str.substring(str.indexOf("/") + 1);
    }

    public static String getString(String str) {
        if (str.startsWith("@string")) {
            return "R.string." + str.substring(str.indexOf("/") + 1);
        } else if (!str.startsWith("@android:string")) {
            return String.format("\"%s\"", str);
        } else {
            return "android.R.string." + str.substring(str.indexOf("/") + 1);
        }
    }

    public static String getDrawable(String str) {
        if (str.startsWith("@drawable")) {
            return "R.drawable." + str.substring(str.indexOf("/") + 1);
        } else if (str.startsWith("@android:drawable")) {
            return "android.R.drawable." + str.substring(str.indexOf("/") + 1);
        } else if (str.startsWith("@mipmap")) {
            return "R.mipmap." + str.substring(str.indexOf("/") + 1);
        } else if (!str.startsWith("@android:mipmap")) {
            return str;
        } else {
            return "android.R.mipmap." + str.substring(str.indexOf("/") + 1);
        }
    }

    public static String getUnknown(String str) {
        if (str.startsWith("?android:attr")) {
            return "android.R.attr." + str.substring(str.indexOf("/") + 1);
        }
        return "R.attr." + str.substring(str.indexOf("/") + 1);
    }

    private String d(String str) {
        String[] split = str.split("\\|");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < split.length; i++) {
            sb.append(e(split[i]));
            if (i < split.length - 1) {
                sb.append("|");
            }
        }
        return sb.toString();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private String e(String str) {
        char c;
        this.mImports.add("android.view.Gravity");
        switch (str.hashCode()) {
            case -1633016142:
                if (str.equals("fill_vertical")) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case -1383228885:
                if (str.equals("bottom")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -1364013995:
                if (str.equals("center")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -831189901:
                if (str.equals("clip_horizontal")) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case -483365792:
                if (str.equals("fill_horizontal")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case -348726240:
                if (str.equals("center_vertical")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case -55726203:
                if (str.equals("clip_vertical")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case 100571:
                if (str.equals("end")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 115029:
                if (str.equals("top")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 3143043:
                if (str.equals("fill")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case 3317767:
                if (str.equals("left")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 108511772:
                if (str.equals("right")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 109757538:
                if (str.equals("start")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1063616078:
                if (str.equals("center_horizontal")) {
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
                return "Gravity.END";
            case 1:
                return "Gravity.START";
            case 2:
                return "Gravity.LEFT";
            case 3:
                return "Gravity.TOP";
            case 4:
                return "Gravity.RIGHT";
            case 5:
                return "Gravity.BOTTOM";
            case 6:
                return "Gravity.CENTER";
            case 7:
                return "Gravity.CENTER_VERTICAL";
            case '\b':
                return "Gravity.CENTER_HORIZONTAL";
            case '\t':
                return "Gravity.FILL";
            case '\n':
                return "Gravity.FILL_HORIZONTAL";
            case 11:
                return "Gravity.FILL_VERTICAL";
            case '\f':
                return "Gravity.CLIP_VERTICAL";
            case '\r':
                return "Gravity.CLIP_HORIZONTAL";
            default:
                return "Gravity.LEFT";
        }
    }

    private String f(String str) {
        this.mImports.add("android.widget.LinearLayout");
        return ((str.hashCode() == -1984141450 && str.equals("vertical")) ? (char) 0 : (char) 65535) != 0 ? "LinearLayout.HORIZONTAL" : "LinearLayout.VERTICAL";
    }

    private String c() {
        String value = this.h.getValue("layout");
        return value.substring(value.lastIndexOf("/") + 1);
    }

    private View d() {
        View view = this;
        while (true) {
            View view2 = view.a;
            if (view2 == null) {
                return view;
            }
            view = view2;
        }
    }

    private HashMap<String, String> e() {
        LayoutManager instance;
        Style style;
        Attributes attributes = this.h;
        if (attributes == null || this.j != null) {
            return this.j;
        }
        String value = attributes.getValue("style");
        if (!(value == null || !value.startsWith("@") || (style = (instance = LayoutManager.instance()).getStyle(value.substring(value.lastIndexOf("/") + 1))) == null)) {
            this.j = style.attribute;
            while (style.parent != null && (style = instance.getStyle(style.parent)) != null) {
                for (String str : style.attribute.keySet()) {
                    if (!this.j.containsKey(str)) {
                        this.j.put(str, style.attribute.get(str));
                    }
                }
            }
        }
        return this.j;
    }

    private String g(String str) {
        char c;
        int hashCode = str.hashCode();
        if (hashCode != -1901805651) {
            if (hashCode == 3178655 && str.equals("gone")) {
                c = 0;
            }
            c = 65535;
        } else {
            if (str.equals("invisible")) {
                c = 1;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
                return "View.GONE";
            case 1:
                return "View.INVISIBLE";
            default:
                return "View.VISIBLE";
        }
    }

    private String h(String str) {
        char c;
        int hashCode = str.hashCode();
        if (hashCode == -1074341483) {
            if (str.equals("middle")) {
                c = 1;
            }
            c = 65535;
        } else if (hashCode != 109757538) {
            if (hashCode == 839444514 && str.equals("marquee")) {
                c = 2;
            }
            c = 65535;
        } else {
            if (str.equals("start")) {
                c = 0;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
                return "TextUtils.TruncateAt.START";
            case 1:
                return "TextUtils.TruncateAt.MIDDLE";
            case 2:
                return "TextUtils.TruncateAt.MARQUEE";
            default:
                return "TextUtils.TruncateAt.END";
        }
    }

    private String i(String str) {
        char c;
        int hashCode = str.hashCode();
        if (hashCode != -1178781136) {
            if (hashCode == 3029637 && str.equals("bold")) {
                c = 0;
            }
            c = 65535;
        } else {
            if (str.equals("italic")) {
                c = 1;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
                return "Typeface.DEFAULT_BOLD";
            case 1:
                return "Typeface.ITALIC";
            default:
                return "Typeface.DEFAULT";
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private String j(String str) {
        char c;
        switch (str.hashCode()) {
            case -1364013995:
                if (str.equals("center")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -1274298614:
                if (str.equals("fitEnd")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -1081239615:
                if (str.equals("matrix")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -522179887:
                if (str.equals("fitStart")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -340708175:
                if (str.equals("centerInside")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 520762310:
                if (str.equals("fitCenter")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1161480325:
                if (str.equals("centerCrop")) {
                    c = 5;
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
                return "ScaleType.MATRIX";
            case 1:
                return "ScaleType.FIT_START";
            case 2:
                return "ScaleType.FIT_CENTER";
            case 3:
                return "ScaleType.FIT_END";
            case 4:
                return "ScaleType.CENTER";
            case 5:
                return "ScaleType.CENTER_CROP";
            case 6:
                return "ScaleType.CENTER_INSIDE";
            default:
                return "ScaleType.FIT_XY";
        }
    }

    private ArrayList<ITranslator> f() {
        ArrayList<ITranslator> arrayList = new ArrayList<>();
        arrayList.add(this);
        arrayList.add(new ConstraintLayout(this.mImports, this.mLayoutParamsObj));
        arrayList.add(new RelativeLayout(this.mImports, this.mLayoutParamsObj));
        arrayList.add(new CustomAttr(this.mImports, this.mLayoutParamsObj, this.f));
        return arrayList;
    }
}
