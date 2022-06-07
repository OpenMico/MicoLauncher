package androidx.databinding;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* loaded from: classes.dex */
public class DataBindingUtil {
    private static DataBinderMapper a = new DataBinderMapperImpl();
    private static DataBindingComponent b = null;

    private DataBindingUtil() {
    }

    public static void setDefaultComponent(@Nullable DataBindingComponent dataBindingComponent) {
        b = dataBindingComponent;
    }

    @Nullable
    public static DataBindingComponent getDefaultComponent() {
        return b;
    }

    public static <T extends ViewDataBinding> T inflate(@NonNull LayoutInflater layoutInflater, int i, @Nullable ViewGroup viewGroup, boolean z) {
        return (T) inflate(layoutInflater, i, viewGroup, z, b);
    }

    public static <T extends ViewDataBinding> T inflate(@NonNull LayoutInflater layoutInflater, int i, @Nullable ViewGroup viewGroup, boolean z, @Nullable DataBindingComponent dataBindingComponent) {
        int i2 = 0;
        boolean z2 = viewGroup != null && z;
        if (z2) {
            i2 = viewGroup.getChildCount();
        }
        View inflate = layoutInflater.inflate(i, viewGroup, z);
        if (z2) {
            return (T) a(dataBindingComponent, viewGroup, i2, i);
        }
        return (T) a(dataBindingComponent, inflate, i);
    }

    @Nullable
    public static <T extends ViewDataBinding> T bind(@NonNull View view) {
        return (T) bind(view, b);
    }

    @Nullable
    public static <T extends ViewDataBinding> T bind(@NonNull View view, DataBindingComponent dataBindingComponent) {
        T t = (T) getBinding(view);
        if (t != null) {
            return t;
        }
        Object tag = view.getTag();
        if (tag instanceof String) {
            int layoutId = a.getLayoutId((String) tag);
            if (layoutId != 0) {
                return (T) a.getDataBinder(dataBindingComponent, view, layoutId);
            }
            throw new IllegalArgumentException("View is not a binding layout. Tag: " + tag);
        }
        throw new IllegalArgumentException("View is not a binding layout");
    }

    public static <T extends ViewDataBinding> T a(DataBindingComponent dataBindingComponent, View[] viewArr, int i) {
        return (T) a.getDataBinder(dataBindingComponent, viewArr, i);
    }

    public static <T extends ViewDataBinding> T a(DataBindingComponent dataBindingComponent, View view, int i) {
        return (T) a.getDataBinder(dataBindingComponent, view, i);
    }

    @Nullable
    public static <T extends ViewDataBinding> T findBinding(@NonNull View view) {
        while (view != null) {
            T t = (T) ViewDataBinding.a(view);
            if (t != null) {
                return t;
            }
            Object tag = view.getTag();
            if (tag instanceof String) {
                String str = (String) tag;
                if (str.startsWith("layout") && str.endsWith("_0")) {
                    char charAt = str.charAt(6);
                    int indexOf = str.indexOf(47, 7);
                    boolean z = false;
                    if (charAt == '/') {
                        if (indexOf == -1) {
                            z = true;
                        }
                    } else if (charAt == '-' && indexOf != -1 && str.indexOf(47, indexOf + 1) == -1) {
                        z = true;
                    }
                    if (z) {
                        return null;
                    }
                }
            }
            ViewParent parent = view.getParent();
            if (parent instanceof View) {
                view = (View) parent;
            } else {
                view = null;
            }
        }
        return null;
    }

    @Nullable
    public static <T extends ViewDataBinding> T getBinding(@NonNull View view) {
        return (T) ViewDataBinding.a(view);
    }

    public static <T extends ViewDataBinding> T setContentView(@NonNull Activity activity, int i) {
        return (T) setContentView(activity, i, b);
    }

    public static <T extends ViewDataBinding> T setContentView(@NonNull Activity activity, int i, @Nullable DataBindingComponent dataBindingComponent) {
        activity.setContentView(i);
        return (T) a(dataBindingComponent, (ViewGroup) activity.getWindow().getDecorView().findViewById(16908290), 0, i);
    }

    @Nullable
    public static String convertBrIdToString(int i) {
        return a.convertBrIdToString(i);
    }

    private static <T extends ViewDataBinding> T a(DataBindingComponent dataBindingComponent, ViewGroup viewGroup, int i, int i2) {
        int childCount = viewGroup.getChildCount();
        int i3 = childCount - i;
        if (i3 == 1) {
            return (T) a(dataBindingComponent, viewGroup.getChildAt(childCount - 1), i2);
        }
        View[] viewArr = new View[i3];
        for (int i4 = 0; i4 < i3; i4++) {
            viewArr[i4] = viewGroup.getChildAt(i4 + i);
        }
        return (T) a(dataBindingComponent, viewArr, i2);
    }
}
