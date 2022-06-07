package miuix.animation.property;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import miuix.animation.R;

/* loaded from: classes5.dex */
public class ViewPropertyExt {
    public static final ForegroundProperty FOREGROUND = new ForegroundProperty();
    public static final BackgroundProperty BACKGROUND = new BackgroundProperty();

    private ViewPropertyExt() {
    }

    /* loaded from: classes5.dex */
    public static class ForegroundProperty extends ViewProperty implements IIntValueProperty<View> {
        public float getValue(View view) {
            return 0.0f;
        }

        public void setValue(View view, float f) {
        }

        private ForegroundProperty() {
            super("foreground");
        }

        public int getIntValue(View view) {
            Object tag = view.getTag(R.id.miuix_animation_tag_foreground_color);
            if (tag instanceof Integer) {
                return ((Integer) tag).intValue();
            }
            return 0;
        }

        public void setIntValue(View view, int i) {
            Drawable foreground;
            view.setTag(R.id.miuix_animation_tag_foreground_color, Integer.valueOf(i));
            if (Build.VERSION.SDK_INT >= 23 && (foreground = view.getForeground()) != null) {
                foreground.invalidateSelf();
            }
        }
    }

    /* loaded from: classes5.dex */
    public static class BackgroundProperty extends ViewProperty implements IIntValueProperty<View> {
        public float getValue(View view) {
            return 0.0f;
        }

        public void setValue(View view, float f) {
        }

        private BackgroundProperty() {
            super("background");
        }

        public void setIntValue(View view, int i) {
            view.setBackgroundColor(i);
        }

        public int getIntValue(View view) {
            Drawable background = view.getBackground();
            if (background instanceof ColorDrawable) {
                return ((ColorDrawable) background).getColor();
            }
            return 0;
        }
    }
}
