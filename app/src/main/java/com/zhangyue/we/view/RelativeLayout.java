package com.zhangyue.we.view;

import java.util.TreeSet;

/* loaded from: classes4.dex */
public class RelativeLayout implements ITranslator {
    private TreeSet<String> a;
    private String b;

    @Override // com.zhangyue.we.view.ITranslator
    public void onAttributeEnd(StringBuilder sb) {
    }

    public RelativeLayout(TreeSet<String> treeSet, String str) {
        this.a = treeSet;
        this.b = str;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.zhangyue.we.view.ITranslator
    public boolean translate(StringBuilder sb, String str, String str2) {
        char c;
        switch (str.hashCode()) {
            case -2142279753:
                if (str.equals("android:layout_alignParentRight")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -2141033987:
                if (str.equals("android:layout_alignParentStart")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case -2023790909:
                if (str.equals("android:layout_centerHorizontal")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -1863056972:
                if (str.equals("android:layout_toStartOf")) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case -1710596651:
                if (str.equals("android:layout_centerVertical")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -1691625162:
                if (str.equals("android:layout_alignParentEnd")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case -1691610704:
                if (str.equals("android:layout_alignParentTop")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -900572404:
                if (str.equals("android:layout_alignParentLeft")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -830731390:
                if (str.equals("android:layout_alignLeft")) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            case -190396274:
                if (str.equals("android:layout_centerInParent")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 22791681:
                if (str.equals("android:layout_alignRight")) {
                    c = 17;
                    break;
                }
                c = 65535;
                break;
            case 24037447:
                if (str.equals("android:layout_alignStart")) {
                    c = 19;
                    break;
                }
                c = 65535;
                break;
            case 254415590:
                if (str.equals("android:layout_alignBottom")) {
                    c = 18;
                    break;
                }
                c = 65535;
                break;
            case 527385088:
                if (str.equals("android:layout_alignEnd")) {
                    c = 20;
                    break;
                }
                c = 65535;
                break;
            case 527399546:
                if (str.equals("android:layout_alignTop")) {
                    c = 16;
                    break;
                }
                c = 65535;
                break;
            case 860931757:
                if (str.equals("android:layout_toEndOf")) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case 1111267587:
                if (str.equals("android:layout_toLeftOf")) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case 1234397555:
                if (str.equals("android:layout_above")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case 1234729198:
                if (str.equals("android:layout_toRightOf")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case 1235407367:
                if (str.equals("android:layout_below")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case 1856677872:
                if (str.equals("android:layout_alignParentBottom")) {
                    c = 6;
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
                return u(sb, str2);
            case 1:
                return s(sb, str2);
            case 2:
                return t(sb, str2);
            case 3:
                return m(sb, str2);
            case 4:
                return n(sb, str2);
            case 5:
                return o(sb, str2);
            case 6:
                return p(sb, str2);
            case 7:
                return q(sb, str2);
            case '\b':
                return r(sb, str2);
            case '\t':
                return k(sb, str2);
            case '\n':
                return l(sb, str2);
            case 11:
                return h(sb, str2);
            case '\f':
                return g(sb, str2);
            case '\r':
                return j(sb, str2);
            case 14:
                return i(sb, str2);
            case 15:
                return a(sb, str2);
            case 16:
                return c(sb, str2);
            case 17:
                return d(sb, str2);
            case 18:
                return f(sb, str2);
            case 19:
                return b(sb, str2);
            case 20:
                return e(sb, str2);
            default:
                return false;
        }
    }

    private boolean a(StringBuilder sb, String str) {
        a(sb, "RelativeLayout.ALIGN_LEFT", a(str));
        this.a.add("android.widget.RelativeLayout");
        return true;
    }

    private boolean b(StringBuilder sb, String str) {
        a(sb, "RelativeLayout.ALIGN_START", a(str));
        this.a.add("android.widget.RelativeLayout");
        return true;
    }

    private boolean c(StringBuilder sb, String str) {
        a(sb, "RelativeLayout.ALIGN_TOP", a(str));
        this.a.add("android.widget.RelativeLayout");
        return true;
    }

    private boolean d(StringBuilder sb, String str) {
        a(sb, "RelativeLayout.ALIGN_RIGHT", a(str));
        this.a.add("android.widget.RelativeLayout");
        return true;
    }

    private boolean e(StringBuilder sb, String str) {
        a(sb, "RelativeLayout.ALIGN_END", a(str));
        this.a.add("android.widget.RelativeLayout");
        return true;
    }

    private boolean f(StringBuilder sb, String str) {
        a(sb, "RelativeLayout.ALIGN_BOTTOM", a(str));
        this.a.add("android.widget.RelativeLayout");
        return true;
    }

    private boolean g(StringBuilder sb, String str) {
        a(sb, "RelativeLayout.RIGHT_OF", a(str));
        this.a.add("android.widget.RelativeLayout");
        return true;
    }

    private boolean h(StringBuilder sb, String str) {
        a(sb, "RelativeLayout.LEFT_OF", a(str));
        this.a.add("android.widget.RelativeLayout");
        return true;
    }

    private boolean i(StringBuilder sb, String str) {
        a(sb, "RelativeLayout.END_OF", a(str));
        this.a.add("android.widget.RelativeLayout");
        return true;
    }

    private boolean j(StringBuilder sb, String str) {
        a(sb, "RelativeLayout.START_OF", a(str));
        this.a.add("android.widget.RelativeLayout");
        return true;
    }

    private boolean k(StringBuilder sb, String str) {
        a(sb, "RelativeLayout.ABOVE", a(str));
        this.a.add("android.widget.RelativeLayout");
        return true;
    }

    private boolean l(StringBuilder sb, String str) {
        a(sb, "RelativeLayout.BELOW", a(str));
        this.a.add("android.widget.RelativeLayout");
        return true;
    }

    private boolean m(StringBuilder sb, String str) {
        a(sb, "RelativeLayout.ALIGN_PARENT_LEFT", a(str));
        this.a.add("android.widget.RelativeLayout");
        return true;
    }

    private boolean n(StringBuilder sb, String str) {
        a(sb, "RelativeLayout.ALIGN_PARENT_TOP", a(str));
        this.a.add("android.widget.RelativeLayout");
        return true;
    }

    private boolean o(StringBuilder sb, String str) {
        a(sb, "RelativeLayout.ALIGN_PARENT_RIGHT", a(str));
        this.a.add("android.widget.RelativeLayout");
        return true;
    }

    private boolean p(StringBuilder sb, String str) {
        a(sb, "RelativeLayout.ALIGN_PARENT_BOTTOM", a(str));
        this.a.add("android.widget.RelativeLayout");
        return true;
    }

    private boolean q(StringBuilder sb, String str) {
        a(sb, "RelativeLayout.ALIGN_PARENT_START", a(str));
        this.a.add("android.widget.RelativeLayout");
        return true;
    }

    private boolean r(StringBuilder sb, String str) {
        a(sb, "RelativeLayout.ALIGN_PARENT_END", a(str));
        this.a.add("android.widget.RelativeLayout");
        return true;
    }

    private boolean s(StringBuilder sb, String str) {
        a(sb, "RelativeLayout.CENTER_VERTICAL", a(str));
        this.a.add("android.widget.RelativeLayout");
        return true;
    }

    private boolean t(StringBuilder sb, String str) {
        a(sb, "RelativeLayout.CENTER_HORIZONTAL", a(str));
        this.a.add("android.widget.RelativeLayout");
        return true;
    }

    private boolean u(StringBuilder sb, String str) {
        a(sb, "RelativeLayout.CENTER_IN_PARENT", a(str));
        this.a.add("android.widget.RelativeLayout");
        return true;
    }

    private void a(StringBuilder sb, String str, String str2) {
        String str3 = this.b;
        if (str3 != null) {
            sb.append(String.format("%s.addRule(%s,%s);\n", str3, str, str2));
        }
    }

    private String a(String str) {
        if (str.equals("true")) {
            this.a.add("android.widget.RelativeLayout");
            return "RelativeLayout.TRUE";
        } else if (str.equals("false")) {
            return "0";
        } else {
            return "R.id." + str.substring(str.indexOf("/") + 1);
        }
    }
}
