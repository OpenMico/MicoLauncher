package miuix.animation.property;

import android.os.Build;
import android.view.View;
import com.umeng.analytics.pro.ai;
import miuix.animation.R;

/* loaded from: classes5.dex */
public abstract class ViewProperty extends FloatProperty<View> {
    public static final ViewProperty TRANSLATION_X = new ViewProperty("translationX") { // from class: miuix.animation.property.ViewProperty.1
        /* renamed from: a */
        public void setValue(View view, float f) {
            view.setTranslationX(f);
        }

        /* renamed from: b */
        public float getValue(View view) {
            return view.getTranslationX();
        }
    };
    public static final ViewProperty TRANSLATION_Y = new ViewProperty("translationY") { // from class: miuix.animation.property.ViewProperty.12
        /* renamed from: a */
        public void setValue(View view, float f) {
            view.setTranslationY(f);
        }

        /* renamed from: b */
        public float getValue(View view) {
            return view.getTranslationY();
        }
    };
    public static final ViewProperty TRANSLATION_Z = new ViewProperty("translationZ") { // from class: miuix.animation.property.ViewProperty.13
        /* renamed from: a */
        public void setValue(View view, float f) {
            if (Build.VERSION.SDK_INT >= 21) {
                view.setTranslationZ(f);
            }
        }

        /* renamed from: b */
        public float getValue(View view) {
            if (Build.VERSION.SDK_INT >= 21) {
                return view.getTranslationZ();
            }
            return 0.0f;
        }
    };
    public static final ViewProperty SCALE_X = new ViewProperty("scaleX") { // from class: miuix.animation.property.ViewProperty.14
        /* renamed from: a */
        public void setValue(View view, float f) {
            view.setScaleX(f);
        }

        /* renamed from: b */
        public float getValue(View view) {
            return view.getScaleX();
        }
    };
    public static final ViewProperty SCALE_Y = new ViewProperty("scaleY") { // from class: miuix.animation.property.ViewProperty.15
        /* renamed from: a */
        public void setValue(View view, float f) {
            view.setScaleY(f);
        }

        /* renamed from: b */
        public float getValue(View view) {
            return view.getScaleY();
        }
    };
    public static final ViewProperty ROTATION = new ViewProperty("rotation") { // from class: miuix.animation.property.ViewProperty.16
        /* renamed from: a */
        public void setValue(View view, float f) {
            view.setRotation(f);
        }

        /* renamed from: b */
        public float getValue(View view) {
            return view.getRotation();
        }
    };
    public static final ViewProperty ROTATION_X = new ViewProperty("rotationX") { // from class: miuix.animation.property.ViewProperty.17
        /* renamed from: a */
        public void setValue(View view, float f) {
            view.setRotationX(f);
        }

        /* renamed from: b */
        public float getValue(View view) {
            return view.getRotationX();
        }
    };
    public static final ViewProperty ROTATION_Y = new ViewProperty("rotationY") { // from class: miuix.animation.property.ViewProperty.18
        /* renamed from: a */
        public void setValue(View view, float f) {
            view.setRotationY(f);
        }

        /* renamed from: b */
        public float getValue(View view) {
            return view.getRotationY();
        }
    };
    public static final ViewProperty X = new ViewProperty("x") { // from class: miuix.animation.property.ViewProperty.19
        /* renamed from: a */
        public void setValue(View view, float f) {
            view.setX(f);
        }

        /* renamed from: b */
        public float getValue(View view) {
            return view.getX();
        }
    };
    public static final ViewProperty Y = new ViewProperty("y") { // from class: miuix.animation.property.ViewProperty.2
        /* renamed from: a */
        public void setValue(View view, float f) {
            view.setY(f);
        }

        /* renamed from: b */
        public float getValue(View view) {
            return view.getY();
        }
    };
    public static final ViewProperty Z = new ViewProperty(ai.aB) { // from class: miuix.animation.property.ViewProperty.3
        /* renamed from: a */
        public void setValue(View view, float f) {
            if (Build.VERSION.SDK_INT >= 21) {
                view.setZ(f);
            }
        }

        /* renamed from: b */
        public float getValue(View view) {
            if (Build.VERSION.SDK_INT >= 21) {
                return view.getZ();
            }
            return 0.0f;
        }
    };
    public static final ViewProperty HEIGHT = new ViewProperty("height") { // from class: miuix.animation.property.ViewProperty.4
        /* renamed from: a */
        public void setValue(View view, float f) {
            view.getLayoutParams().height = (int) f;
            view.setTag(R.id.miuix_animation_tag_set_height, Float.valueOf(f));
            view.requestLayout();
        }

        /* renamed from: b */
        public float getValue(View view) {
            int height = view.getHeight();
            Float f = (Float) view.getTag(R.id.miuix_animation_tag_set_height);
            if (f != null) {
                return f.floatValue();
            }
            if (height == 0 && ViewProperty.b(view)) {
                height = view.getMeasuredHeight();
            }
            return height;
        }
    };
    public static final ViewProperty WIDTH = new ViewProperty("width") { // from class: miuix.animation.property.ViewProperty.5
        /* renamed from: a */
        public void setValue(View view, float f) {
            view.getLayoutParams().width = (int) f;
            view.setTag(R.id.miuix_animation_tag_set_width, Float.valueOf(f));
            view.requestLayout();
        }

        /* renamed from: b */
        public float getValue(View view) {
            int width = view.getWidth();
            Float f = (Float) view.getTag(R.id.miuix_animation_tag_set_width);
            if (f != null) {
                return f.floatValue();
            }
            if (width == 0 && ViewProperty.b(view)) {
                width = view.getMeasuredWidth();
            }
            return width;
        }
    };
    public static final ViewProperty ALPHA = new ViewProperty("alpha") { // from class: miuix.animation.property.ViewProperty.6
        /* renamed from: a */
        public void setValue(View view, float f) {
            view.setAlpha(f);
        }

        /* renamed from: b */
        public float getValue(View view) {
            return view.getAlpha();
        }
    };
    public static final ViewProperty AUTO_ALPHA = new ViewProperty("autoAlpha") { // from class: miuix.animation.property.ViewProperty.7
        /* renamed from: a */
        public void setValue(View view, float f) {
            view.setAlpha(f);
            boolean z = Math.abs(f) <= 0.00390625f;
            if (view.getVisibility() != 0 && f > 0.0f && !z) {
                view.setVisibility(0);
            } else if (z) {
                view.setVisibility(8);
            }
        }

        /* renamed from: b */
        public float getValue(View view) {
            return view.getAlpha();
        }
    };
    public static final ViewProperty SCROLL_X = new ViewProperty("scrollX") { // from class: miuix.animation.property.ViewProperty.8
        /* renamed from: a */
        public void setValue(View view, float f) {
            view.setScrollX((int) f);
        }

        /* renamed from: b */
        public float getValue(View view) {
            return view.getScrollX();
        }
    };
    public static final ViewProperty SCROLL_Y = new ViewProperty("scrollY") { // from class: miuix.animation.property.ViewProperty.9
        /* renamed from: a */
        public void setValue(View view, float f) {
            view.setScrollY((int) f);
        }

        /* renamed from: b */
        public float getValue(View view) {
            return view.getScrollY();
        }
    };
    public static final ViewProperty FOREGROUND = new ViewProperty("deprecated_foreground") { // from class: miuix.animation.property.ViewProperty.10
        /* renamed from: a */
        public void setValue(View view, float f) {
        }

        /* renamed from: b */
        public float getValue(View view) {
            return 0.0f;
        }
    };
    public static final ViewProperty BACKGROUND = new ViewProperty("deprecated_background") { // from class: miuix.animation.property.ViewProperty.11
        /* renamed from: a */
        public void setValue(View view, float f) {
        }

        /* renamed from: b */
        public float getValue(View view) {
            return 0.0f;
        }
    };

    public ViewProperty(String str) {
        super(str);
    }

    @Override // miuix.animation.property.FloatProperty
    public String toString() {
        return "ViewProperty{mPropertyName='" + this.c + "'}";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean b(View view) {
        return view.getTag(R.id.miuix_animation_tag_init_layout) != null;
    }
}
