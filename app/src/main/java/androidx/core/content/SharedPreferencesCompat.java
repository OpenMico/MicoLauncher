package androidx.core.content;

import android.content.SharedPreferences;
import androidx.annotation.NonNull;

@Deprecated
/* loaded from: classes.dex */
public final class SharedPreferencesCompat {

    @Deprecated
    /* loaded from: classes.dex */
    public static final class EditorCompat {
        private static EditorCompat a;
        private final a b = new a();

        /* loaded from: classes.dex */
        private static class a {
            a() {
            }

            public void a(@NonNull SharedPreferences.Editor editor) {
                try {
                    editor.apply();
                } catch (AbstractMethodError unused) {
                    editor.commit();
                }
            }
        }

        private EditorCompat() {
        }

        @Deprecated
        public static EditorCompat getInstance() {
            if (a == null) {
                a = new EditorCompat();
            }
            return a;
        }

        @Deprecated
        public void apply(@NonNull SharedPreferences.Editor editor) {
            this.b.a(editor);
        }
    }

    private SharedPreferencesCompat() {
    }
}
