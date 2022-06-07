package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.widget.SpinnerAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;

/* loaded from: classes.dex */
public interface ThemedSpinnerAdapter extends SpinnerAdapter {
    @Nullable
    Resources.Theme getDropDownViewTheme();

    void setDropDownViewTheme(@Nullable Resources.Theme theme);

    /* loaded from: classes.dex */
    public static final class Helper {
        private final Context a;
        private final LayoutInflater b;
        private LayoutInflater c;

        public Helper(@NonNull Context context) {
            this.a = context;
            this.b = LayoutInflater.from(context);
        }

        public void setDropDownViewTheme(@Nullable Resources.Theme theme) {
            if (theme == null) {
                this.c = null;
            } else if (theme == this.a.getTheme()) {
                this.c = this.b;
            } else {
                this.c = LayoutInflater.from(new ContextThemeWrapper(this.a, theme));
            }
        }

        @Nullable
        public Resources.Theme getDropDownViewTheme() {
            LayoutInflater layoutInflater = this.c;
            if (layoutInflater == null) {
                return null;
            }
            return layoutInflater.getContext().getTheme();
        }

        @NonNull
        public LayoutInflater getDropDownViewInflater() {
            LayoutInflater layoutInflater = this.c;
            return layoutInflater != null ? layoutInflater : this.b;
        }
    }
}
