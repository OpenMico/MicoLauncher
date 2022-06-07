package androidx.databinding.adapters;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import androidx.annotation.RestrictTo;
import androidx.databinding.BindingAdapter;
import androidx.databinding.BindingMethod;
import androidx.databinding.BindingMethods;
import androidx.databinding.library.baseAdapters.R;

@BindingMethods({@BindingMethod(attribute = "android:backgroundTint", method = "setBackgroundTintList", type = View.class), @BindingMethod(attribute = "android:fadeScrollbars", method = "setScrollbarFadingEnabled", type = View.class), @BindingMethod(attribute = "android:getOutline", method = "setOutlineProvider", type = View.class), @BindingMethod(attribute = "android:nextFocusForward", method = "setNextFocusForwardId", type = View.class), @BindingMethod(attribute = "android:nextFocusLeft", method = "setNextFocusLeftId", type = View.class), @BindingMethod(attribute = "android:nextFocusRight", method = "setNextFocusRightId", type = View.class), @BindingMethod(attribute = "android:nextFocusUp", method = "setNextFocusUpId", type = View.class), @BindingMethod(attribute = "android:nextFocusDown", method = "setNextFocusDownId", type = View.class), @BindingMethod(attribute = "android:requiresFadingEdge", method = "setVerticalFadingEdgeEnabled", type = View.class), @BindingMethod(attribute = "android:scrollbarDefaultDelayBeforeFade", method = "setScrollBarDefaultDelayBeforeFade", type = View.class), @BindingMethod(attribute = "android:scrollbarFadeDuration", method = "setScrollBarFadeDuration", type = View.class), @BindingMethod(attribute = "android:scrollbarSize", method = "setScrollBarSize", type = View.class), @BindingMethod(attribute = "android:scrollbarStyle", method = "setScrollBarStyle", type = View.class), @BindingMethod(attribute = "android:transformPivotX", method = "setPivotX", type = View.class), @BindingMethod(attribute = "android:transformPivotY", method = "setPivotY", type = View.class), @BindingMethod(attribute = "android:onDrag", method = "setOnDragListener", type = View.class), @BindingMethod(attribute = "android:onClick", method = "setOnClickListener", type = View.class), @BindingMethod(attribute = "android:onApplyWindowInsets", method = "setOnApplyWindowInsetsListener", type = View.class), @BindingMethod(attribute = "android:onCreateContextMenu", method = "setOnCreateContextMenuListener", type = View.class), @BindingMethod(attribute = "android:onFocusChange", method = "setOnFocusChangeListener", type = View.class), @BindingMethod(attribute = "android:onGenericMotion", method = "setOnGenericMotionListener", type = View.class), @BindingMethod(attribute = "android:onHover", method = "setOnHoverListener", type = View.class), @BindingMethod(attribute = "android:onKey", method = "setOnKeyListener", type = View.class), @BindingMethod(attribute = "android:onLongClick", method = "setOnLongClickListener", type = View.class), @BindingMethod(attribute = "android:onSystemUiVisibilityChange", method = "setOnSystemUiVisibilityChangeListener", type = View.class), @BindingMethod(attribute = "android:onTouch", method = "setOnTouchListener", type = View.class)})
@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public class ViewBindingAdapter {
    public static final int FADING_EDGE_HORIZONTAL = 1;
    public static final int FADING_EDGE_NONE = 0;
    public static final int FADING_EDGE_VERTICAL = 2;

    @TargetApi(12)
    /* loaded from: classes.dex */
    public interface OnViewAttachedToWindow {
        void onViewAttachedToWindow(View view);
    }

    @TargetApi(12)
    /* loaded from: classes.dex */
    public interface OnViewDetachedFromWindow {
        void onViewDetachedFromWindow(View view);
    }

    private static int a(float f) {
        int i = (int) (0.5f + f);
        if (i != 0) {
            return i;
        }
        int i2 = (f > 0.0f ? 1 : (f == 0.0f ? 0 : -1));
        if (i2 == 0) {
            return 0;
        }
        return i2 > 0 ? 1 : -1;
    }

    @BindingAdapter({"android:padding"})
    public static void setPadding(View view, float f) {
        int a = a(f);
        view.setPadding(a, a, a, a);
    }

    @BindingAdapter({"android:paddingBottom"})
    public static void setPaddingBottom(View view, float f) {
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), a(f));
    }

    @BindingAdapter({"android:paddingEnd"})
    public static void setPaddingEnd(View view, float f) {
        int a = a(f);
        if (Build.VERSION.SDK_INT >= 17) {
            view.setPaddingRelative(view.getPaddingStart(), view.getPaddingTop(), a, view.getPaddingBottom());
        } else {
            view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), a, view.getPaddingBottom());
        }
    }

    @BindingAdapter({"android:paddingLeft"})
    public static void setPaddingLeft(View view, float f) {
        view.setPadding(a(f), view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());
    }

    @BindingAdapter({"android:paddingRight"})
    public static void setPaddingRight(View view, float f) {
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), a(f), view.getPaddingBottom());
    }

    @BindingAdapter({"android:paddingStart"})
    public static void setPaddingStart(View view, float f) {
        int a = a(f);
        if (Build.VERSION.SDK_INT >= 17) {
            view.setPaddingRelative(a, view.getPaddingTop(), view.getPaddingEnd(), view.getPaddingBottom());
        } else {
            view.setPadding(a, view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());
        }
    }

    @BindingAdapter({"android:paddingTop"})
    public static void setPaddingTop(View view, float f) {
        view.setPadding(view.getPaddingLeft(), a(f), view.getPaddingRight(), view.getPaddingBottom());
    }

    @BindingAdapter({"android:requiresFadingEdge"})
    public static void setRequiresFadingEdge(View view, int i) {
        boolean z = false;
        boolean z2 = (i & 2) != 0;
        if ((i & 1) != 0) {
            z = true;
        }
        view.setVerticalFadingEdgeEnabled(z2);
        view.setHorizontalFadingEdgeEnabled(z);
    }

    @BindingAdapter({"android:onClickListener", "android:clickable"})
    public static void setClickListener(View view, View.OnClickListener onClickListener, boolean z) {
        view.setOnClickListener(onClickListener);
        view.setClickable(z);
    }

    @BindingAdapter({"android:onClick", "android:clickable"})
    public static void setOnClick(View view, View.OnClickListener onClickListener, boolean z) {
        view.setOnClickListener(onClickListener);
        view.setClickable(z);
    }

    @BindingAdapter({"android:onLongClickListener", "android:longClickable"})
    public static void setOnLongClickListener(View view, View.OnLongClickListener onLongClickListener, boolean z) {
        view.setOnLongClickListener(onLongClickListener);
        view.setLongClickable(z);
    }

    @BindingAdapter({"android:onLongClick", "android:longClickable"})
    public static void setOnLongClick(View view, View.OnLongClickListener onLongClickListener, boolean z) {
        view.setOnLongClickListener(onLongClickListener);
        view.setLongClickable(z);
    }

    @BindingAdapter(requireAll = false, value = {"android:onViewDetachedFromWindow", "android:onViewAttachedToWindow"})
    public static void setOnAttachStateChangeListener(View view, final OnViewDetachedFromWindow onViewDetachedFromWindow, final OnViewAttachedToWindow onViewAttachedToWindow) {
        View.OnAttachStateChangeListener onAttachStateChangeListener = (onViewDetachedFromWindow == null && onViewAttachedToWindow == null) ? null : new View.OnAttachStateChangeListener() { // from class: androidx.databinding.adapters.ViewBindingAdapter.1
            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(View view2) {
                OnViewAttachedToWindow onViewAttachedToWindow2 = OnViewAttachedToWindow.this;
                if (onViewAttachedToWindow2 != null) {
                    onViewAttachedToWindow2.onViewAttachedToWindow(view2);
                }
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(View view2) {
                OnViewDetachedFromWindow onViewDetachedFromWindow2 = onViewDetachedFromWindow;
                if (onViewDetachedFromWindow2 != null) {
                    onViewDetachedFromWindow2.onViewDetachedFromWindow(view2);
                }
            }
        };
        View.OnAttachStateChangeListener onAttachStateChangeListener2 = (View.OnAttachStateChangeListener) ListenerUtil.trackListener(view, onAttachStateChangeListener, R.id.onAttachStateChangeListener);
        if (onAttachStateChangeListener2 != null) {
            view.removeOnAttachStateChangeListener(onAttachStateChangeListener2);
        }
        if (onAttachStateChangeListener != null) {
            view.addOnAttachStateChangeListener(onAttachStateChangeListener);
        }
    }

    @BindingAdapter({"android:onLayoutChange"})
    public static void setOnLayoutChangeListener(View view, View.OnLayoutChangeListener onLayoutChangeListener, View.OnLayoutChangeListener onLayoutChangeListener2) {
        if (onLayoutChangeListener != null) {
            view.removeOnLayoutChangeListener(onLayoutChangeListener);
        }
        if (onLayoutChangeListener2 != null) {
            view.addOnLayoutChangeListener(onLayoutChangeListener2);
        }
    }

    @BindingAdapter({"android:background"})
    public static void setBackground(View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= 16) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }
}
