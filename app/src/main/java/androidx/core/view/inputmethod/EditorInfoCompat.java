package androidx.core.view.inputmethod;

import android.os.Build;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.inputmethod.EditorInfo;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.util.Preconditions;
import com.xiaomi.micolauncher.common.player.policy.IAudioPolicy;

/* loaded from: classes.dex */
public final class EditorInfoCompat {
    public static final int IME_FLAG_FORCE_ASCII = Integer.MIN_VALUE;
    public static final int IME_FLAG_NO_PERSONALIZED_LEARNING = 16777216;
    private static final String[] a = new String[0];

    private static boolean a(int i) {
        int i2 = i & IAudioPolicy.AUDIO_SOURCE_TYPE_ALL;
        return i2 == 129 || i2 == 225 || i2 == 18;
    }

    public static void setContentMimeTypes(@NonNull EditorInfo editorInfo, @Nullable String[] strArr) {
        if (Build.VERSION.SDK_INT >= 25) {
            editorInfo.contentMimeTypes = strArr;
            return;
        }
        if (editorInfo.extras == null) {
            editorInfo.extras = new Bundle();
        }
        editorInfo.extras.putStringArray("androidx.core.view.inputmethod.EditorInfoCompat.CONTENT_MIME_TYPES", strArr);
        editorInfo.extras.putStringArray("android.support.v13.view.inputmethod.EditorInfoCompat.CONTENT_MIME_TYPES", strArr);
    }

    @NonNull
    public static String[] getContentMimeTypes(EditorInfo editorInfo) {
        if (Build.VERSION.SDK_INT >= 25) {
            String[] strArr = editorInfo.contentMimeTypes;
            return strArr != null ? strArr : a;
        } else if (editorInfo.extras == null) {
            return a;
        } else {
            String[] stringArray = editorInfo.extras.getStringArray("androidx.core.view.inputmethod.EditorInfoCompat.CONTENT_MIME_TYPES");
            if (stringArray == null) {
                stringArray = editorInfo.extras.getStringArray("android.support.v13.view.inputmethod.EditorInfoCompat.CONTENT_MIME_TYPES");
            }
            return stringArray != null ? stringArray : a;
        }
    }

    public static void setInitialSurroundingText(@NonNull EditorInfo editorInfo, @NonNull CharSequence charSequence) {
        if (Build.VERSION.SDK_INT >= 30) {
            a.a(editorInfo, charSequence, 0);
        } else {
            setInitialSurroundingSubText(editorInfo, charSequence, 0);
        }
    }

    public static void setInitialSurroundingSubText(@NonNull EditorInfo editorInfo, @NonNull CharSequence charSequence, int i) {
        int i2;
        int i3;
        Preconditions.checkNotNull(charSequence);
        if (Build.VERSION.SDK_INT >= 30) {
            a.a(editorInfo, charSequence, i);
            return;
        }
        if (editorInfo.initialSelStart > editorInfo.initialSelEnd) {
            i2 = editorInfo.initialSelEnd - i;
        } else {
            i2 = editorInfo.initialSelStart - i;
        }
        if (editorInfo.initialSelStart > editorInfo.initialSelEnd) {
            i3 = editorInfo.initialSelStart - i;
        } else {
            i3 = editorInfo.initialSelEnd - i;
        }
        int length = charSequence.length();
        if (i < 0 || i2 < 0 || i3 > length) {
            b(editorInfo, null, 0, 0);
        } else if (a(editorInfo.inputType)) {
            b(editorInfo, null, 0, 0);
        } else if (length <= 2048) {
            b(editorInfo, charSequence, i2, i3);
        } else {
            a(editorInfo, charSequence, i2, i3);
        }
    }

    private static void a(EditorInfo editorInfo, CharSequence charSequence, int i, int i2) {
        CharSequence charSequence2;
        int i3 = i2 - i;
        int i4 = i3 > 1024 ? 0 : i3;
        int i5 = 2048 - i4;
        int min = Math.min(charSequence.length() - i2, i5 - Math.min(i, (int) (i5 * 0.8d)));
        int min2 = Math.min(i, i5 - min);
        int i6 = i - min2;
        if (a(charSequence, i6, 0)) {
            i6++;
            min2--;
        }
        if (a(charSequence, (i2 + min) - 1, 1)) {
            min--;
        }
        int i7 = min2 + i4 + min;
        if (i4 != i3) {
            charSequence2 = TextUtils.concat(charSequence.subSequence(i6, i6 + min2), charSequence.subSequence(i2, min + i2));
        } else {
            charSequence2 = charSequence.subSequence(i6, i7 + i6);
        }
        int i8 = min2 + 0;
        b(editorInfo, charSequence2, i8, i4 + i8);
    }

    @Nullable
    public static CharSequence getInitialTextBeforeCursor(@NonNull EditorInfo editorInfo, int i, int i2) {
        CharSequence charSequence;
        if (Build.VERSION.SDK_INT >= 30) {
            return a.a(editorInfo, i, i2);
        }
        if (editorInfo.extras == null || (charSequence = editorInfo.extras.getCharSequence("androidx.core.view.inputmethod.EditorInfoCompat.CONTENT_SURROUNDING_TEXT")) == null) {
            return null;
        }
        int i3 = editorInfo.extras.getInt("androidx.core.view.inputmethod.EditorInfoCompat.CONTENT_SELECTION_HEAD");
        int min = Math.min(i, i3);
        if ((i2 & 1) != 0) {
            return charSequence.subSequence(i3 - min, i3);
        }
        return TextUtils.substring(charSequence, i3 - min, i3);
    }

    @Nullable
    public static CharSequence getInitialSelectedText(@NonNull EditorInfo editorInfo, int i) {
        CharSequence charSequence;
        if (Build.VERSION.SDK_INT >= 30) {
            return a.a(editorInfo, i);
        }
        if (editorInfo.extras == null) {
            return null;
        }
        int i2 = editorInfo.initialSelStart > editorInfo.initialSelEnd ? editorInfo.initialSelEnd : editorInfo.initialSelStart;
        int i3 = editorInfo.initialSelStart > editorInfo.initialSelEnd ? editorInfo.initialSelStart : editorInfo.initialSelEnd;
        int i4 = editorInfo.extras.getInt("androidx.core.view.inputmethod.EditorInfoCompat.CONTENT_SELECTION_HEAD");
        int i5 = editorInfo.extras.getInt("androidx.core.view.inputmethod.EditorInfoCompat.CONTENT_SELECTION_END");
        int i6 = i3 - i2;
        if (editorInfo.initialSelStart < 0 || editorInfo.initialSelEnd < 0 || i5 - i4 != i6 || (charSequence = editorInfo.extras.getCharSequence("androidx.core.view.inputmethod.EditorInfoCompat.CONTENT_SURROUNDING_TEXT")) == null) {
            return null;
        }
        if ((i & 1) != 0) {
            return charSequence.subSequence(i4, i5);
        }
        return TextUtils.substring(charSequence, i4, i5);
    }

    @Nullable
    public static CharSequence getInitialTextAfterCursor(@NonNull EditorInfo editorInfo, int i, int i2) {
        CharSequence charSequence;
        if (Build.VERSION.SDK_INT >= 30) {
            return a.b(editorInfo, i, i2);
        }
        if (editorInfo.extras == null || (charSequence = editorInfo.extras.getCharSequence("androidx.core.view.inputmethod.EditorInfoCompat.CONTENT_SURROUNDING_TEXT")) == null) {
            return null;
        }
        int i3 = editorInfo.extras.getInt("androidx.core.view.inputmethod.EditorInfoCompat.CONTENT_SELECTION_END");
        int min = Math.min(i, charSequence.length() - i3);
        if ((i2 & 1) != 0) {
            return charSequence.subSequence(i3, min + i3);
        }
        return TextUtils.substring(charSequence, i3, min + i3);
    }

    private static boolean a(CharSequence charSequence, int i, int i2) {
        switch (i2) {
            case 0:
                return Character.isLowSurrogate(charSequence.charAt(i));
            case 1:
                return Character.isHighSurrogate(charSequence.charAt(i));
            default:
                return false;
        }
    }

    private static void b(EditorInfo editorInfo, CharSequence charSequence, int i, int i2) {
        if (editorInfo.extras == null) {
            editorInfo.extras = new Bundle();
        }
        editorInfo.extras.putCharSequence("androidx.core.view.inputmethod.EditorInfoCompat.CONTENT_SURROUNDING_TEXT", charSequence != null ? new SpannableStringBuilder(charSequence) : null);
        editorInfo.extras.putInt("androidx.core.view.inputmethod.EditorInfoCompat.CONTENT_SELECTION_HEAD", i);
        editorInfo.extras.putInt("androidx.core.view.inputmethod.EditorInfoCompat.CONTENT_SELECTION_END", i2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int a(EditorInfo editorInfo) {
        if (Build.VERSION.SDK_INT >= 25) {
            return 1;
        }
        if (editorInfo.extras == null) {
            return 0;
        }
        boolean containsKey = editorInfo.extras.containsKey("androidx.core.view.inputmethod.EditorInfoCompat.CONTENT_MIME_TYPES");
        boolean containsKey2 = editorInfo.extras.containsKey("android.support.v13.view.inputmethod.EditorInfoCompat.CONTENT_MIME_TYPES");
        if (containsKey && containsKey2) {
            return 4;
        }
        if (containsKey) {
            return 3;
        }
        return containsKey2 ? 2 : 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(30)
    /* loaded from: classes.dex */
    public static class a {
        static void a(@NonNull EditorInfo editorInfo, CharSequence charSequence, int i) {
            editorInfo.setInitialSurroundingSubText(charSequence, i);
        }

        static CharSequence a(@NonNull EditorInfo editorInfo, int i, int i2) {
            return editorInfo.getInitialTextBeforeCursor(i, i2);
        }

        static CharSequence a(@NonNull EditorInfo editorInfo, int i) {
            return editorInfo.getInitialSelectedText(i);
        }

        static CharSequence b(@NonNull EditorInfo editorInfo, int i, int i2) {
            return editorInfo.getInitialTextAfterCursor(i, i2);
        }
    }
}
