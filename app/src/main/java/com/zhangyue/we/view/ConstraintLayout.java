package com.zhangyue.we.view;

import androidx.exifinterface.media.ExifInterface;
import com.alibaba.fastjson.parser.JSONLexer;
import com.xiaomi.onetrack.api.c;
import java.util.TreeSet;
import kotlin.text.Typography;

/* loaded from: classes4.dex */
public class ConstraintLayout implements ITranslator {
    private TreeSet<String> a;
    private String b;
    private boolean c;

    private void a(StringBuilder sb, Object obj, String str, Object obj2) {
    }

    public ConstraintLayout(TreeSet<String> treeSet, String str) {
        this.a = treeSet;
        this.b = str;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.zhangyue.we.view.ITranslator
    public boolean translate(StringBuilder sb, String str, String str2) {
        char c;
        switch (str.hashCode()) {
            case -1924873158:
                if (str.equals("app:layout_constraintEnd_toEndOf")) {
                    c = ' ';
                    break;
                }
                c = 65535;
                break;
            case -1820562763:
                if (str.equals("app:layout_constraintRight_toLeftOf")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -1778425573:
                if (str.equals("app:layout_constraintCircleRadius")) {
                    c = 21;
                    break;
                }
                c = 65535;
                break;
            case -1773032152:
                if (str.equals("app:layout_constraintVertical_weight")) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            case -1765237630:
                if (str.equals("app:layout_constraintHeight_default")) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case -1758889623:
                if (str.equals("app:layout_constraintVertical_bias")) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case -1713363327:
                if (str.equals("app:layout_constrainedHeight")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case -1641198661:
                if (str.equals("app:layout_constraintHorizontal_bias")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case -1596698838:
                if (str.equals("app:layout_constraintCircleAngle")) {
                    c = 20;
                    break;
                }
                c = 65535;
                break;
            case -1389078926:
                if (str.equals("app:layout_constraintWidth_max")) {
                    c = '\'';
                    break;
                }
                c = 65535;
                break;
            case -1389078688:
                if (str.equals("app:layout_constraintWidth_min")) {
                    c = Typography.amp;
                    break;
                }
                c = 65535;
                break;
            case -1350940942:
                if (str.equals("app:layout_constraintHorizontal_chainStyle")) {
                    c = 18;
                    break;
                }
                c = 65535;
                break;
            case -1328696283:
                if (str.equals("app:layout_constraintHeight_max")) {
                    c = ')';
                    break;
                }
                c = 65535;
                break;
            case -1328696045:
                if (str.equals("app:layout_constraintHeight_min")) {
                    c = '(';
                    break;
                }
                c = 65535;
                break;
            case -1322041461:
                if (str.equals("app:layout_editor_absoluteX")) {
                    c = 29;
                    break;
                }
                c = 65535;
                break;
            case -1322041460:
                if (str.equals("app:layout_editor_absoluteY")) {
                    c = 28;
                    break;
                }
                c = 65535;
                break;
            case -1318005294:
                if (str.equals("app:layout_goneMarginEnd")) {
                    c = JSONLexer.EOI;
                    break;
                }
                c = 65535;
                break;
            case -1317990836:
                if (str.equals("app:layout_goneMarginTop")) {
                    c = 25;
                    break;
                }
                c = 65535;
                break;
            case -1242147447:
                if (str.equals("app:layout_constraintGuide_percent")) {
                    c = '%';
                    break;
                }
                c = 65535;
                break;
            case -1095709911:
                if (str.equals("app:layout_constraintCircle")) {
                    c = 19;
                    break;
                }
                c = 65535;
                break;
            case -1033207912:
                if (str.equals("app:layout_constraintBottom_toBottomOf")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -856018631:
                if (str.equals("app:layout_constraintLeft_toRightOf")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -595491540:
                if (str.equals("app:layout_constrainedWidth")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case -548712369:
                if (str.equals("app:layout_constraintWidth_default")) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case -341167366:
                if (str.equals("app:layout_constraintHorizontal_weight")) {
                    c = 16;
                    break;
                }
                c = 65535;
                break;
            case -189065772:
                if (str.equals("app:layout_goneMarginBottom")) {
                    c = 22;
                    break;
                }
                c = 65535;
                break;
            case 305998470:
                if (str.equals("app:layout_constraintHeight_percent")) {
                    c = '*';
                    break;
                }
                c = 65535;
                break;
            case 351087448:
                if (str.equals("app:layout_constraintLeft_toLeftOf")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 424127827:
                if (str.equals("app:layout_goneMarginRight")) {
                    c = 24;
                    break;
                }
                c = 65535;
                break;
            case 425373593:
                if (str.equals("app:layout_goneMarginStart")) {
                    c = 27;
                    break;
                }
                c = 65535;
                break;
            case 513141485:
                if (str.equals("app:layout_constraintGuide_begin")) {
                    c = '#';
                    break;
                }
                c = 65535;
                break;
            case 542301564:
                if (str.equals("app:layout_constraintRight_toRightOf")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 557494296:
                if (str.equals("app:layout_constraintBaseline_toBaselineOf")) {
                    c = 30;
                    break;
                }
                c = 65535;
                break;
            case 595481345:
                if (str.equals("app:layout_constraintStart_toEndOf")) {
                    c = 31;
                    break;
                }
                c = 65535;
                break;
            case 737134856:
                if (str.equals("app:layout_constraintStart_toStartOf")) {
                    c = '\"';
                    break;
                }
                c = 65535;
                break;
            case 788391086:
                if (str.equals("app:layout_constraintTop_toTopOf")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case 880983071:
                if (str.equals("app:layout_constraintGuide_end")) {
                    c = '$';
                    break;
                }
                c = 65535;
                break;
            case 920915840:
                if (str.equals("app:layout_constraintTop_toBottomOf")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 1038012417:
                if (str.equals("app:layout_constraintEnd_toStartOf")) {
                    c = '!';
                    break;
                }
                c = 65535;
                break;
            case 1187055518:
                if (str.equals("app:layout_constraintDimensionRatio")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 1522523731:
                if (str.equals("app:layout_constraintWidth_percent")) {
                    c = '+';
                    break;
                }
                c = 65535;
                break;
            case 1750923808:
                if (str.equals("app:layout_constraintVertical_chainStyle")) {
                    c = 17;
                    break;
                }
                c = 65535;
                break;
            case 2091708912:
                if (str.equals("app:layout_goneMarginLeft")) {
                    c = 23;
                    break;
                }
                c = 65535;
                break;
            case 2140351382:
                if (str.equals("app:layout_constraintBottom_toTopOf")) {
                    c = 7;
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
                return R(sb, str2);
            case 1:
                return P(sb, str2);
            case 2:
                return Q(sb, str2);
            case 3:
                return N(sb, str2);
            case 4:
                return O(sb, str2);
            case 5:
                return M(sb, str2);
            case 6:
                return L(sb, str2);
            case 7:
                return K(sb, str2);
            case '\b':
                return J(sb, str2);
            case '\t':
                return I(sb, str2);
            case '\n':
                return H(sb, str2);
            case 11:
                return G(sb, str2);
            case '\f':
                return F(sb, str2);
            case '\r':
                return E(sb, str2);
            case 14:
                return D(sb, str2);
            case 15:
                return C(sb, str2);
            case 16:
                return B(sb, str2);
            case 17:
                return A(sb, str2);
            case 18:
                return z(sb, str2);
            case 19:
                return y(sb, str2);
            case 20:
                return x(sb, str2);
            case 21:
                return w(sb, str2);
            case 22:
                return v(sb, str2);
            case 23:
                return u(sb, str2);
            case 24:
                return t(sb, str2);
            case 25:
                return s(sb, str2);
            case 26:
                return r(sb, str2);
            case 27:
                return q(sb, str2);
            case 28:
                return p(sb, str2);
            case 29:
                return o(sb, str2);
            case 30:
                return n(sb, str2);
            case 31:
                return m(sb, str2);
            case ' ':
                return l(sb, str2);
            case '!':
                return k(sb, str2);
            case '\"':
                return j(sb, str2);
            case '#':
                return i(sb, str2);
            case '$':
                return h(sb, str2);
            case '%':
                return g(sb, str2);
            case '&':
                return f(sb, str2);
            case '\'':
                return e(sb, str2);
            case '(':
                return d(sb, str2);
            case ')':
                return c(sb, str2);
            case '*':
                return b(sb, str2);
            case '+':
                return a(sb, str2);
            default:
                return false;
        }
    }

    @Override // com.zhangyue.we.view.ITranslator
    public void onAttributeEnd(StringBuilder sb) {
        if (this.c) {
            sb.append(String.format("%s.validate();\n", this.b));
        }
    }

    private boolean a(StringBuilder sb, String str) {
        sb.append(String.format("%s.matchConstraintPercentWidth = %s ;\n", this.b, e(str)));
        this.c = true;
        return true;
    }

    private boolean b(StringBuilder sb, String str) {
        sb.append(String.format("%s.matchConstraintPercentHeight = %s ;\n", this.b, e(str)));
        this.c = true;
        return true;
    }

    private boolean c(StringBuilder sb, String str) {
        sb.append(String.format("%s.matchConstraintMaxHeight = %s ;\n", this.b, View.getWH(str)));
        this.c = true;
        return true;
    }

    private boolean d(StringBuilder sb, String str) {
        sb.append(String.format("%s.matchConstraintMinHeight = %s ;\n", this.b, View.getWH(str)));
        this.c = true;
        return true;
    }

    private boolean e(StringBuilder sb, String str) {
        sb.append(String.format("%s.matchConstraintMaxWidth = %s ;\n", this.b, View.getWH(str)));
        this.c = true;
        return true;
    }

    private boolean f(StringBuilder sb, String str) {
        sb.append(String.format("%s.matchConstraintMinWidth = %s ;\n", this.b, View.getWH(str)));
        this.c = true;
        return true;
    }

    private boolean g(StringBuilder sb, String str) {
        sb.append(String.format("%s.guidePercent = %s ;\n", this.b, View.getFloat(str)));
        this.c = true;
        return true;
    }

    private boolean h(StringBuilder sb, String str) {
        sb.append(String.format("%s.guideEnd = %s ;\n", this.b, View.getDimen(str)));
        this.c = true;
        return true;
    }

    private boolean i(StringBuilder sb, String str) {
        sb.append(String.format("%s.guideBegin = %s ;\n", this.b, View.getDimen(str)));
        this.c = true;
        return true;
    }

    private boolean j(StringBuilder sb, String str) {
        sb.append(String.format("%s.startToStart = %s ;\n", this.b, c(str)));
        this.c = true;
        return true;
    }

    private boolean k(StringBuilder sb, String str) {
        sb.append(String.format("%s.endToStart = %s ;\n", this.b, c(str)));
        this.c = true;
        return true;
    }

    private boolean l(StringBuilder sb, String str) {
        sb.append(String.format("%s.endToEnd = %s ;\n", this.b, c(str)));
        this.c = true;
        return true;
    }

    private boolean m(StringBuilder sb, String str) {
        sb.append(String.format("%s.startToEnd = %s ;\n", this.b, c(str)));
        this.c = true;
        return true;
    }

    private boolean n(StringBuilder sb, String str) {
        sb.append(String.format("%s.baselineToBaseline = %s ;\n", this.b, c(str)));
        this.c = true;
        return true;
    }

    private boolean o(StringBuilder sb, String str) {
        sb.append(String.format("%s.editorAbsoluteX = %s ;\n", this.b, View.getDimen(str)));
        this.c = true;
        return true;
    }

    private boolean p(StringBuilder sb, String str) {
        sb.append(String.format("%s.editorAbsoluteY = %s ;\n", this.b, View.getDimen(str)));
        this.c = true;
        return true;
    }

    private boolean q(StringBuilder sb, String str) {
        sb.append(String.format("%s.goneStartMargin = %s ;\n", this.b, View.getDimen(str)));
        this.c = true;
        return true;
    }

    private boolean r(StringBuilder sb, String str) {
        sb.append(String.format("%s.goneEndMargin = %s ;\n", this.b, View.getDimen(str)));
        this.c = true;
        return true;
    }

    private boolean s(StringBuilder sb, String str) {
        sb.append(String.format("%s.goneTopMargin = %s ;\n", this.b, View.getDimen(str)));
        this.c = true;
        return true;
    }

    private boolean t(StringBuilder sb, String str) {
        sb.append(String.format("%s.goneRightMargin = %s ;\n", this.b, View.getDimen(str)));
        this.c = true;
        return true;
    }

    private boolean u(StringBuilder sb, String str) {
        sb.append(String.format("%s.goneLeftMargin = %s ;\n", this.b, View.getDimen(str)));
        this.c = true;
        return true;
    }

    private boolean v(StringBuilder sb, String str) {
        sb.append(String.format("%s.goneBottomMargin = %s ;\n", this.b, View.getDimen(str)));
        this.c = true;
        return true;
    }

    private boolean w(StringBuilder sb, String str) {
        sb.append(String.format("%s.circleRadius = %s ;\n", this.b, View.getDimen(str)));
        this.c = true;
        return true;
    }

    private boolean x(StringBuilder sb, String str) {
        sb.append(String.format("%s.circleAngle = %s ;\n", this.b, d(str)));
        this.c = true;
        return true;
    }

    private boolean y(StringBuilder sb, String str) {
        sb.append(String.format("%s.circleConstraint = %s ;\n", this.b, c(str)));
        this.c = true;
        return true;
    }

    private boolean z(StringBuilder sb, String str) {
        sb.append(String.format("%s.horizontalChainStyle = %s ;\n", this.b, b(str)));
        this.c = true;
        return true;
    }

    private boolean A(StringBuilder sb, String str) {
        sb.append(String.format("%s.verticalChainStyle = %s ;\n", this.b, b(str)));
        this.c = true;
        return true;
    }

    private boolean B(StringBuilder sb, String str) {
        sb.append(String.format("%s.horizontalWeight = %s ;\n", this.b, View.getFloat(str)));
        this.c = true;
        return true;
    }

    private boolean C(StringBuilder sb, String str) {
        sb.append(String.format("%s.verticalWeight = %s ;\n", this.b, View.getFloat(str)));
        this.c = true;
        return true;
    }

    private boolean D(StringBuilder sb, String str) {
        sb.append(String.format("%s.matchConstraintDefaultWidth = %s ;\n", this.b, a(str)));
        this.c = true;
        return true;
    }

    private boolean E(StringBuilder sb, String str) {
        sb.append(String.format("%s.matchConstraintDefaultHeight = %s ;\n", this.b, a(str)));
        this.c = true;
        return true;
    }

    private boolean F(StringBuilder sb, String str) {
        sb.append(String.format("%s.horizontalBias = %s ;\n", this.b, View.getFloat(str)));
        this.c = true;
        return true;
    }

    private boolean G(StringBuilder sb, String str) {
        sb.append(String.format("%s.verticalBias = %s ;\n", this.b, View.getFloat(str)));
        this.c = true;
        return true;
    }

    private boolean H(StringBuilder sb, String str) {
        sb.append(String.format("%s.constrainedWidth = %s ;\n", this.b, View.getBoolean(str)));
        this.c = true;
        return true;
    }

    private boolean I(StringBuilder sb, String str) {
        sb.append(String.format("%s.constrainedHeight = %s ;\n", this.b, View.getBoolean(str)));
        this.c = true;
        return true;
    }

    private boolean J(StringBuilder sb, String str) {
        sb.append(String.format("%s.topToTop = %s ;\n", this.b, c(str)));
        this.c = true;
        return true;
    }

    private boolean K(StringBuilder sb, String str) {
        sb.append(String.format("%s.bottomToTop = %s ;\n", this.b, c(str)));
        this.c = true;
        return true;
    }

    private boolean L(StringBuilder sb, String str) {
        sb.append(String.format("%s.rightToLeft = %s ;\n", this.b, c(str)));
        this.c = true;
        return true;
    }

    private boolean M(StringBuilder sb, String str) {
        sb.append(String.format("%s.leftToRight = %s ;\n", this.b, c(str)));
        this.c = true;
        return true;
    }

    private boolean N(StringBuilder sb, String str) {
        sb.append(String.format("%s.topToBottom = %s ;\n", this.b, c(str)));
        this.c = true;
        return true;
    }

    private boolean O(StringBuilder sb, String str) {
        sb.append(String.format("%s.bottomToBottom = %s ;\n", this.b, c(str)));
        this.c = true;
        return true;
    }

    private boolean P(StringBuilder sb, String str) {
        sb.append(String.format("%s.leftToLeft = %s ;\n", this.b, c(str)));
        this.c = true;
        return true;
    }

    private boolean Q(StringBuilder sb, String str) {
        sb.append(String.format("%s.rightToRight = %s ;\n", this.b, c(str)));
        this.c = true;
        return true;
    }

    private boolean R(StringBuilder sb, String str) {
        int i = 0;
        sb.append(String.format("%s.dimensionRatio=\"%s\";\n", this.b, str));
        int length = str.length();
        int indexOf = str.indexOf(44);
        char c = 65535;
        if (indexOf > 0 && indexOf < length - 1) {
            String substring = str.substring(0, indexOf);
            if (substring.equalsIgnoreCase(ExifInterface.LONGITUDE_WEST)) {
                a(sb, this.b, "dimensionRatioSide", 0);
                c = 0;
            } else if (substring.equalsIgnoreCase(c.b)) {
                a(sb, this.b, "dimensionRatioSide", 1);
                c = 1;
            }
            i = indexOf + 1;
        }
        int indexOf2 = str.indexOf(58);
        if (indexOf2 < 0 || indexOf2 >= length - 1) {
            String substring2 = str.substring(i);
            if (substring2.length() > 0) {
                a(sb, this.b, "dimensionRatioValue", Float.parseFloat(substring2) + "f");
            }
            this.c = true;
            return true;
        }
        String substring3 = str.substring(i, indexOf2);
        String substring4 = str.substring(indexOf2 + 1);
        if (substring3.length() > 0 && substring4.length() > 0) {
            float parseFloat = Float.parseFloat(substring3);
            float parseFloat2 = Float.parseFloat(substring4);
            if (parseFloat > 0.0f && parseFloat2 > 0.0f) {
                if (c == 1) {
                    a(sb, this.b, "dimensionRatioValue", Math.abs(parseFloat2 / parseFloat) + "f");
                } else {
                    a(sb, this.b, "dimensionRatioValue", Math.abs(parseFloat / parseFloat2) + "f");
                }
            }
        }
        this.c = true;
        return true;
        this.c = true;
        return true;
    }

    private String a(String str) {
        char c;
        int hashCode = str.hashCode();
        if (hashCode != -895684237) {
            if (hashCode == 3657802 && str.equals("wrap")) {
                c = 1;
            }
            c = 65535;
        } else {
            if (str.equals("spread")) {
                c = 0;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
                return "ConstraintLayout.LayoutParams.MATCH_CONSTRAINT_SPREAD";
            case 1:
                return "ConstraintLayout.LayoutParams.MATCH_CONSTRAINT_WRAP";
            default:
                return "ConstraintLayout.LayoutParams.MATCH_CONSTRAINT_PERCENT";
        }
    }

    private String b(String str) {
        char c;
        int hashCode = str.hashCode();
        if (hashCode != -895684237) {
            if (hashCode == 1311368264 && str.equals("spread_inside")) {
                c = 1;
            }
            c = 65535;
        } else {
            if (str.equals("spread")) {
                c = 0;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
                return "ConstraintLayout.LayoutParams.CHAIN_SPREAD";
            case 1:
                return "ConstraintLayout.LayoutParams.CHAIN_SPREAD_INSIDE";
            default:
                return "ConstraintLayout.LayoutParams.CHAIN_PACKED";
        }
    }

    private String c(String str) {
        if (!str.contains("id/")) {
            return "0";
        }
        return "R.id." + str.substring(str.lastIndexOf("/") + 1);
    }

    private String d(String str) {
        float parseFloat = Float.parseFloat(str) % 360.0f;
        if (parseFloat < 0.0f) {
            parseFloat = (360.0f - parseFloat) % 360.0f;
        }
        return String.valueOf(parseFloat) + "f";
    }

    private String e(String str) {
        Float valueOf = Float.valueOf(Float.parseFloat(str));
        return String.valueOf(Math.max(0.0f, valueOf.floatValue())) + "f";
    }
}
