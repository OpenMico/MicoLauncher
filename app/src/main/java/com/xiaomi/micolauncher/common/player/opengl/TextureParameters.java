package com.xiaomi.micolauncher.common.player.opengl;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import androidx.annotation.NonNull;
import com.umeng.analytics.pro.ai;
import com.xiaomi.onetrack.a.c;
import org.fourthline.cling.support.messagebox.parser.MessageElement;

/* loaded from: classes3.dex */
public class TextureParameters {
    protected static final String ASSIGN = ":";
    protected static final String HEADER = "///";
    protected static final String SEPARATOR = ";";
    private static final Matrix a = new Matrix();
    private final int b;
    private final int c;
    private final int d;
    private final int e;
    private int f;
    private int g;
    private int h;
    private int i;

    private static String b(int i) {
        switch (i) {
            case 9728:
                return "n";
            case 9729:
                return c.a;
            case 9984:
                return "nn";
            case 9985:
                return "ln";
            case 9987:
                return "ll";
            default:
                return "nl";
        }
    }

    private static String c(int i) {
        return i != 9728 ? c.a : "n";
    }

    private static String d(int i) {
        return i != 33071 ? i != 33648 ? "r" : MessageElement.XPATH_PREFIX : ai.aD;
    }

    static {
        a.postScale(1.0f, -1.0f);
    }

    public TextureParameters() {
        this.b = 9728;
        this.c = 9729;
        this.d = 10497;
        this.e = 10497;
        a(this.b, this.c, this.d, this.e);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public TextureParameters(int i, int i2, int i3, int i4) {
        this.b = i;
        this.c = i2;
        this.d = i3;
        this.e = i4;
        a(i, i2, i3, i4);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public TextureParameters(String str) {
        this();
        a(str);
    }

    public void set(String str, String str2, String str3, String str4) {
        this.f = b(str);
        this.g = c(str2);
        this.h = d(str3);
        this.i = d(str4);
    }

    @NonNull
    public String toString() {
        if (this.f == this.b && this.g == this.c && this.h == this.d && this.i == this.e) {
            return "";
        }
        return "///min:" + getMinShortcut() + SEPARATOR + "mag:" + getMagShortcut() + SEPARATOR + ai.az + ":" + getWrapSShortcut() + SEPARATOR + ai.aF + ":" + getWrapTShortcut() + SEPARATOR;
    }

    public String getMinShortcut() {
        return b(this.f);
    }

    public String getMagShortcut() {
        return c(this.g);
    }

    public String getWrapSShortcut() {
        return d(this.h);
    }

    public String getWrapTShortcut() {
        return d(this.i);
    }

    void a(int i, int i2, int i3, int i4) {
        this.f = i;
        this.g = i2;
        this.h = i3;
        this.i = i4;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(int i) {
        GLES20.glTexParameteri(i, 10241, this.f);
        GLES20.glTexParameteri(i, 10240, this.g);
        GLES20.glTexParameteri(i, 10242, this.h);
        GLES20.glTexParameteri(i, 10243, this.i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(Bitmap bitmap) {
        if (bitmap != null) {
            Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), a, true);
            GLUtils.texImage2D(3553, 0, 6408, createBitmap, 5121, 0);
            createBitmap.recycle();
        }
    }

    void a(String str) {
        String trim;
        int indexOf;
        if (str != null && (indexOf = (trim = str.trim()).indexOf(HEADER)) == 0) {
            for (String str2 : trim.substring(indexOf + 3).split(SEPARATOR)) {
                String[] split = str2.split(":");
                if (split.length == 2) {
                    parseParameter(split[0], split[1]);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public void parseParameter(String str, String str2) {
        char c;
        switch (str.hashCode()) {
            case 115:
                if (str.equals(ai.az)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 116:
                if (str.equals(ai.aF)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 107859:
                if (str.equals("mag")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 108114:
                if (str.equals("min")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 2:
                this.g = c(str2);
                return;
            case 3:
                this.h = d(str2);
                return;
            case 4:
                this.i = d(str2);
                return;
            default:
                this.f = b(str2);
                return;
        }
    }

    private static int b(String str) {
        char c;
        int hashCode = str.hashCode();
        if (hashCode == 108) {
            if (str.equals(c.a)) {
                c = 1;
            }
            c = 65535;
        } else if (hashCode == 110) {
            if (str.equals("n")) {
                c = 0;
            }
            c = 65535;
        } else if (hashCode == 3456) {
            if (str.equals("ll")) {
                c = 4;
            }
            c = 65535;
        } else if (hashCode != 3458) {
            if (hashCode == 3520 && str.equals("nn")) {
                c = 2;
            }
            c = 65535;
        } else {
            if (str.equals("ln")) {
                c = 3;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
                return 9728;
            case 1:
                return 9729;
            case 2:
                return 9984;
            case 3:
                return 9985;
            case 4:
                return 9987;
            default:
                return 9986;
        }
    }

    private static int c(String str) {
        return str.equals("n") ? 9728 : 9729;
    }

    private static int d(String str) {
        char c;
        int hashCode = str.hashCode();
        if (hashCode != 99) {
            if (hashCode == 109 && str.equals(MessageElement.XPATH_PREFIX)) {
                c = 1;
            }
            c = 65535;
        } else {
            if (str.equals(ai.aD)) {
                c = 0;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
                return 33071;
            case 1:
                return 33648;
            default:
                return 10497;
        }
    }
}
