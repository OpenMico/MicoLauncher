package androidx.appcompat.app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.lang.reflect.Method;

/* compiled from: ActionBarDrawerToggleHoneycomb.java */
/* loaded from: classes.dex */
class a {
    private static final int[] a = {16843531};

    public static C0002a a(Activity activity, Drawable drawable, int i) {
        C0002a aVar = new C0002a(activity);
        if (aVar.a != null) {
            try {
                ActionBar actionBar = activity.getActionBar();
                aVar.a.invoke(actionBar, drawable);
                aVar.b.invoke(actionBar, Integer.valueOf(i));
            } catch (Exception e) {
                Log.w("ActionBarDrawerToggleHC", "Couldn't set home-as-up indicator via JB-MR2 API", e);
            }
        } else if (aVar.c != null) {
            aVar.c.setImageDrawable(drawable);
        } else {
            Log.w("ActionBarDrawerToggleHC", "Couldn't set home-as-up indicator");
        }
        return aVar;
    }

    public static C0002a a(C0002a aVar, Activity activity, int i) {
        if (aVar == null) {
            aVar = new C0002a(activity);
        }
        if (aVar.a != null) {
            try {
                ActionBar actionBar = activity.getActionBar();
                aVar.b.invoke(actionBar, Integer.valueOf(i));
                if (Build.VERSION.SDK_INT <= 19) {
                    actionBar.setSubtitle(actionBar.getSubtitle());
                }
            } catch (Exception e) {
                Log.w("ActionBarDrawerToggleHC", "Couldn't set content description via JB-MR2 API", e);
            }
        }
        return aVar;
    }

    public static Drawable a(Activity activity) {
        TypedArray obtainStyledAttributes = activity.obtainStyledAttributes(a);
        Drawable drawable = obtainStyledAttributes.getDrawable(0);
        obtainStyledAttributes.recycle();
        return drawable;
    }

    /* compiled from: ActionBarDrawerToggleHoneycomb.java */
    /* renamed from: androidx.appcompat.app.a$a  reason: collision with other inner class name */
    /* loaded from: classes.dex */
    static class C0002a {
        public Method a;
        public Method b;
        public ImageView c;

        C0002a(Activity activity) {
            try {
                this.a = ActionBar.class.getDeclaredMethod("setHomeAsUpIndicator", Drawable.class);
                this.b = ActionBar.class.getDeclaredMethod("setHomeActionContentDescription", Integer.TYPE);
            } catch (NoSuchMethodException unused) {
                View findViewById = activity.findViewById(16908332);
                if (findViewById != null) {
                    ViewGroup viewGroup = (ViewGroup) findViewById.getParent();
                    if (viewGroup.getChildCount() == 2) {
                        View childAt = viewGroup.getChildAt(0);
                        View childAt2 = childAt.getId() != 16908332 ? childAt : viewGroup.getChildAt(1);
                        if (childAt2 instanceof ImageView) {
                            this.c = (ImageView) childAt2;
                        }
                    }
                }
            }
        }
    }
}
