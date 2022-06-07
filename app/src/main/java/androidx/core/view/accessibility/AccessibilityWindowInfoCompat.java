package androidx.core.view.accessibility;

import android.graphics.Rect;
import android.os.Build;
import android.view.accessibility.AccessibilityWindowInfo;
import androidx.annotation.NonNull;

/* loaded from: classes.dex */
public class AccessibilityWindowInfoCompat {
    public static final int TYPE_ACCESSIBILITY_OVERLAY = 4;
    public static final int TYPE_APPLICATION = 1;
    public static final int TYPE_INPUT_METHOD = 2;
    public static final int TYPE_SPLIT_SCREEN_DIVIDER = 5;
    public static final int TYPE_SYSTEM = 3;
    private Object a;

    private static String a(int i) {
        switch (i) {
            case 1:
                return "TYPE_APPLICATION";
            case 2:
                return "TYPE_INPUT_METHOD";
            case 3:
                return "TYPE_SYSTEM";
            case 4:
                return "TYPE_ACCESSIBILITY_OVERLAY";
            default:
                return "<UNKNOWN>";
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static AccessibilityWindowInfoCompat a(Object obj) {
        if (obj != null) {
            return new AccessibilityWindowInfoCompat(obj);
        }
        return null;
    }

    private AccessibilityWindowInfoCompat(Object obj) {
        this.a = obj;
    }

    public int getType() {
        if (Build.VERSION.SDK_INT >= 21) {
            return ((AccessibilityWindowInfo) this.a).getType();
        }
        return -1;
    }

    public int getLayer() {
        if (Build.VERSION.SDK_INT >= 21) {
            return ((AccessibilityWindowInfo) this.a).getLayer();
        }
        return -1;
    }

    public AccessibilityNodeInfoCompat getRoot() {
        if (Build.VERSION.SDK_INT >= 21) {
            return AccessibilityNodeInfoCompat.a(((AccessibilityWindowInfo) this.a).getRoot());
        }
        return null;
    }

    public AccessibilityWindowInfoCompat getParent() {
        if (Build.VERSION.SDK_INT >= 21) {
            return a(((AccessibilityWindowInfo) this.a).getParent());
        }
        return null;
    }

    public int getId() {
        if (Build.VERSION.SDK_INT >= 21) {
            return ((AccessibilityWindowInfo) this.a).getId();
        }
        return -1;
    }

    public void getBoundsInScreen(Rect rect) {
        if (Build.VERSION.SDK_INT >= 21) {
            ((AccessibilityWindowInfo) this.a).getBoundsInScreen(rect);
        }
    }

    public boolean isActive() {
        if (Build.VERSION.SDK_INT >= 21) {
            return ((AccessibilityWindowInfo) this.a).isActive();
        }
        return true;
    }

    public boolean isFocused() {
        if (Build.VERSION.SDK_INT >= 21) {
            return ((AccessibilityWindowInfo) this.a).isFocused();
        }
        return true;
    }

    public boolean isAccessibilityFocused() {
        if (Build.VERSION.SDK_INT >= 21) {
            return ((AccessibilityWindowInfo) this.a).isAccessibilityFocused();
        }
        return true;
    }

    public int getChildCount() {
        if (Build.VERSION.SDK_INT >= 21) {
            return ((AccessibilityWindowInfo) this.a).getChildCount();
        }
        return 0;
    }

    public AccessibilityWindowInfoCompat getChild(int i) {
        if (Build.VERSION.SDK_INT >= 21) {
            return a(((AccessibilityWindowInfo) this.a).getChild(i));
        }
        return null;
    }

    public CharSequence getTitle() {
        if (Build.VERSION.SDK_INT >= 24) {
            return ((AccessibilityWindowInfo) this.a).getTitle();
        }
        return null;
    }

    public AccessibilityNodeInfoCompat getAnchor() {
        if (Build.VERSION.SDK_INT >= 24) {
            return AccessibilityNodeInfoCompat.a(((AccessibilityWindowInfo) this.a).getAnchor());
        }
        return null;
    }

    public static AccessibilityWindowInfoCompat obtain() {
        if (Build.VERSION.SDK_INT >= 21) {
            return a(AccessibilityWindowInfo.obtain());
        }
        return null;
    }

    public static AccessibilityWindowInfoCompat obtain(AccessibilityWindowInfoCompat accessibilityWindowInfoCompat) {
        if (Build.VERSION.SDK_INT < 21 || accessibilityWindowInfoCompat == null) {
            return null;
        }
        return a(AccessibilityWindowInfo.obtain((AccessibilityWindowInfo) accessibilityWindowInfoCompat.a));
    }

    public void recycle() {
        if (Build.VERSION.SDK_INT >= 21) {
            ((AccessibilityWindowInfo) this.a).recycle();
        }
    }

    public int hashCode() {
        Object obj = this.a;
        if (obj == null) {
            return 0;
        }
        return obj.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AccessibilityWindowInfoCompat)) {
            return false;
        }
        AccessibilityWindowInfoCompat accessibilityWindowInfoCompat = (AccessibilityWindowInfoCompat) obj;
        Object obj2 = this.a;
        if (obj2 == null) {
            if (accessibilityWindowInfoCompat.a != null) {
                return false;
            }
        } else if (!obj2.equals(accessibilityWindowInfoCompat.a)) {
            return false;
        }
        return true;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Rect rect = new Rect();
        getBoundsInScreen(rect);
        sb.append("AccessibilityWindowInfo[");
        sb.append("id=");
        sb.append(getId());
        sb.append(", type=");
        sb.append(a(getType()));
        sb.append(", layer=");
        sb.append(getLayer());
        sb.append(", bounds=");
        sb.append(rect);
        sb.append(", focused=");
        sb.append(isFocused());
        sb.append(", active=");
        sb.append(isActive());
        sb.append(", hasParent=");
        boolean z = true;
        sb.append(getParent() != null);
        sb.append(", hasChildren=");
        if (getChildCount() <= 0) {
            z = false;
        }
        sb.append(z);
        sb.append(']');
        return sb.toString();
    }
}
