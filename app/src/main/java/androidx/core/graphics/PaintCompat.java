package androidx.core.graphics;

import android.graphics.BlendMode;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import org.fourthline.cling.support.messagebox.parser.MessageElement;

/* loaded from: classes.dex */
public final class PaintCompat {
    private static final ThreadLocal<Pair<Rect, Rect>> a = new ThreadLocal<>();

    public static boolean hasGlyph(@NonNull Paint paint, @NonNull String str) {
        if (Build.VERSION.SDK_INT >= 23) {
            return paint.hasGlyph(str);
        }
        int length = str.length();
        if (length == 1 && Character.isWhitespace(str.charAt(0))) {
            return true;
        }
        float measureText = paint.measureText("\udfffd");
        float measureText2 = paint.measureText(MessageElement.XPATH_PREFIX);
        float measureText3 = paint.measureText(str);
        float f = 0.0f;
        if (measureText3 == 0.0f) {
            return false;
        }
        if (str.codePointCount(0, str.length()) > 1) {
            if (measureText3 > measureText2 * 2.0f) {
                return false;
            }
            int i = 0;
            while (i < length) {
                int charCount = Character.charCount(str.codePointAt(i)) + i;
                f += paint.measureText(str, i, charCount);
                i = charCount;
            }
            if (measureText3 >= f) {
                return false;
            }
        }
        if (measureText3 != measureText) {
            return true;
        }
        Pair<Rect, Rect> a2 = a();
        paint.getTextBounds("\udfffd", 0, 2, a2.first);
        paint.getTextBounds(str, 0, length, a2.second);
        return !a2.first.equals(a2.second);
    }

    public static boolean setBlendMode(@NonNull Paint paint, @Nullable BlendModeCompat blendModeCompat) {
        PorterDuffXfermode porterDuffXfermode = null;
        BlendMode blendMode = null;
        if (Build.VERSION.SDK_INT >= 29) {
            if (blendModeCompat != null) {
                blendMode = a.a(blendModeCompat);
            }
            paint.setBlendMode(blendMode);
            return true;
        } else if (blendModeCompat != null) {
            PorterDuff.Mode b = a.b(blendModeCompat);
            if (b != null) {
                porterDuffXfermode = new PorterDuffXfermode(b);
            }
            paint.setXfermode(porterDuffXfermode);
            return b != null;
        } else {
            paint.setXfermode(null);
            return true;
        }
    }

    private static Pair<Rect, Rect> a() {
        Pair<Rect, Rect> pair = a.get();
        if (pair == null) {
            Pair<Rect, Rect> pair2 = new Pair<>(new Rect(), new Rect());
            a.set(pair2);
            return pair2;
        }
        pair.first.setEmpty();
        pair.second.setEmpty();
        return pair;
    }

    private PaintCompat() {
    }
}
