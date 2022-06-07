package com.google.android.material.bottomsheet;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AppCompatDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.material.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.uc.crashsdk.export.LogType;

/* loaded from: classes2.dex */
public class BottomSheetDialog extends AppCompatDialog {
    boolean a;
    boolean b;
    private BottomSheetBehavior<FrameLayout> c;
    private FrameLayout d;
    private CoordinatorLayout e;
    private FrameLayout f;
    private boolean g;
    private boolean h;
    private BottomSheetBehavior.BottomSheetCallback i;
    private boolean j;
    @NonNull
    private BottomSheetBehavior.BottomSheetCallback k;

    public BottomSheetDialog(@NonNull Context context) {
        this(context, 0);
        this.j = getContext().getTheme().obtainStyledAttributes(new int[]{R.attr.enableEdgeToEdge}).getBoolean(0, false);
    }

    public BottomSheetDialog(@NonNull Context context, @StyleRes int i) {
        super(context, a(context, i));
        this.b = true;
        this.g = true;
        this.k = new BottomSheetBehavior.BottomSheetCallback() { // from class: com.google.android.material.bottomsheet.BottomSheetDialog.5
            @Override // com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
            public void onSlide(@NonNull View view, float f) {
            }

            @Override // com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
            public void onStateChanged(@NonNull View view, int i2) {
                if (i2 == 5) {
                    BottomSheetDialog.this.cancel();
                }
            }
        };
        supportRequestWindowFeature(1);
        this.j = getContext().getTheme().obtainStyledAttributes(new int[]{R.attr.enableEdgeToEdge}).getBoolean(0, false);
    }

    protected BottomSheetDialog(@NonNull Context context, boolean z, DialogInterface.OnCancelListener onCancelListener) {
        super(context, z, onCancelListener);
        this.b = true;
        this.g = true;
        this.k = new BottomSheetBehavior.BottomSheetCallback() { // from class: com.google.android.material.bottomsheet.BottomSheetDialog.5
            @Override // com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
            public void onSlide(@NonNull View view, float f) {
            }

            @Override // com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
            public void onStateChanged(@NonNull View view, int i2) {
                if (i2 == 5) {
                    BottomSheetDialog.this.cancel();
                }
            }
        };
        supportRequestWindowFeature(1);
        this.b = z;
        this.j = getContext().getTheme().obtainStyledAttributes(new int[]{R.attr.enableEdgeToEdge}).getBoolean(0, false);
    }

    @Override // androidx.appcompat.app.AppCompatDialog, android.app.Dialog
    public void setContentView(@LayoutRes int i) {
        super.setContentView(a(i, null, null));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatDialog, android.app.Dialog
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Window window = getWindow();
        if (window != null) {
            if (Build.VERSION.SDK_INT >= 21) {
                window.setStatusBarColor(0);
                window.addFlags(Integer.MIN_VALUE);
                if (Build.VERSION.SDK_INT < 23) {
                    window.addFlags(67108864);
                }
            }
            window.setLayout(-1, -1);
        }
    }

    @Override // androidx.appcompat.app.AppCompatDialog, android.app.Dialog
    public void setContentView(View view) {
        super.setContentView(a(0, view, null));
    }

    @Override // androidx.appcompat.app.AppCompatDialog, android.app.Dialog
    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        super.setContentView(a(0, view, layoutParams));
    }

    @Override // android.app.Dialog
    public void setCancelable(boolean z) {
        super.setCancelable(z);
        if (this.b != z) {
            this.b = z;
            BottomSheetBehavior<FrameLayout> bottomSheetBehavior = this.c;
            if (bottomSheetBehavior != null) {
                bottomSheetBehavior.setHideable(z);
            }
        }
    }

    @Override // android.app.Dialog
    protected void onStart() {
        super.onStart();
        BottomSheetBehavior<FrameLayout> bottomSheetBehavior = this.c;
        if (bottomSheetBehavior != null && bottomSheetBehavior.getState() == 5) {
            this.c.setState(4);
        }
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        if (window != null && Build.VERSION.SDK_INT >= 21) {
            boolean z = this.j && Color.alpha(window.getNavigationBarColor()) < 255;
            FrameLayout frameLayout = this.d;
            if (frameLayout != null) {
                frameLayout.setFitsSystemWindows(!z);
            }
            CoordinatorLayout coordinatorLayout = this.e;
            if (coordinatorLayout != null) {
                coordinatorLayout.setFitsSystemWindows(!z);
            }
            if (z) {
                window.getDecorView().setSystemUiVisibility(LogType.UNEXP_OTHER);
            }
        }
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void cancel() {
        BottomSheetBehavior<FrameLayout> behavior = getBehavior();
        if (!this.a || behavior.getState() == 5) {
            super.cancel();
        } else {
            behavior.setState(5);
        }
    }

    @Override // android.app.Dialog
    public void setCanceledOnTouchOutside(boolean z) {
        super.setCanceledOnTouchOutside(z);
        if (z && !this.b) {
            this.b = true;
        }
        this.g = z;
        this.h = true;
    }

    @NonNull
    public BottomSheetBehavior<FrameLayout> getBehavior() {
        if (this.c == null) {
            c();
        }
        return this.c;
    }

    public void setDismissWithAnimation(boolean z) {
        this.a = z;
    }

    public boolean getDismissWithAnimation() {
        return this.a;
    }

    public boolean getEdgeToEdgeEnabled() {
        return this.j;
    }

    private FrameLayout c() {
        if (this.d == null) {
            this.d = (FrameLayout) View.inflate(getContext(), R.layout.design_bottom_sheet_dialog, null);
            this.e = (CoordinatorLayout) this.d.findViewById(R.id.coordinator);
            this.f = (FrameLayout) this.d.findViewById(R.id.design_bottom_sheet);
            this.c = BottomSheetBehavior.from(this.f);
            this.c.addBottomSheetCallback(this.k);
            this.c.setHideable(this.b);
        }
        return this.d;
    }

    private View a(int i, @Nullable View view, @Nullable ViewGroup.LayoutParams layoutParams) {
        c();
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) this.d.findViewById(R.id.coordinator);
        if (i != 0 && view == null) {
            view = getLayoutInflater().inflate(i, (ViewGroup) coordinatorLayout, false);
        }
        if (this.j) {
            ViewCompat.setOnApplyWindowInsetsListener(this.f, new OnApplyWindowInsetsListener() { // from class: com.google.android.material.bottomsheet.BottomSheetDialog.1
                @Override // androidx.core.view.OnApplyWindowInsetsListener
                public WindowInsetsCompat onApplyWindowInsets(View view2, WindowInsetsCompat windowInsetsCompat) {
                    if (BottomSheetDialog.this.i != null) {
                        BottomSheetDialog.this.c.removeBottomSheetCallback(BottomSheetDialog.this.i);
                    }
                    if (windowInsetsCompat != null) {
                        BottomSheetDialog bottomSheetDialog = BottomSheetDialog.this;
                        bottomSheetDialog.i = new a(bottomSheetDialog.f, windowInsetsCompat);
                        BottomSheetDialog.this.c.addBottomSheetCallback(BottomSheetDialog.this.i);
                    }
                    return windowInsetsCompat;
                }
            });
        }
        this.f.removeAllViews();
        if (layoutParams == null) {
            this.f.addView(view);
        } else {
            this.f.addView(view, layoutParams);
        }
        coordinatorLayout.findViewById(R.id.touch_outside).setOnClickListener(new View.OnClickListener() { // from class: com.google.android.material.bottomsheet.BottomSheetDialog.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (BottomSheetDialog.this.b && BottomSheetDialog.this.isShowing() && BottomSheetDialog.this.a()) {
                    BottomSheetDialog.this.cancel();
                }
            }
        });
        ViewCompat.setAccessibilityDelegate(this.f, new AccessibilityDelegateCompat() { // from class: com.google.android.material.bottomsheet.BottomSheetDialog.3
            @Override // androidx.core.view.AccessibilityDelegateCompat
            public void onInitializeAccessibilityNodeInfo(View view2, @NonNull AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                super.onInitializeAccessibilityNodeInfo(view2, accessibilityNodeInfoCompat);
                if (BottomSheetDialog.this.b) {
                    accessibilityNodeInfoCompat.addAction(1048576);
                    accessibilityNodeInfoCompat.setDismissable(true);
                    return;
                }
                accessibilityNodeInfoCompat.setDismissable(false);
            }

            @Override // androidx.core.view.AccessibilityDelegateCompat
            public boolean performAccessibilityAction(View view2, int i2, Bundle bundle) {
                if (i2 != 1048576 || !BottomSheetDialog.this.b) {
                    return super.performAccessibilityAction(view2, i2, bundle);
                }
                BottomSheetDialog.this.cancel();
                return true;
            }
        });
        this.f.setOnTouchListener(new View.OnTouchListener() { // from class: com.google.android.material.bottomsheet.BottomSheetDialog.4
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view2, MotionEvent motionEvent) {
                return true;
            }
        });
        return this.d;
    }

    boolean a() {
        if (!this.h) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(new int[]{16843611});
            this.g = obtainStyledAttributes.getBoolean(0, true);
            obtainStyledAttributes.recycle();
            this.h = true;
        }
        return this.g;
    }

    private static int a(@NonNull Context context, int i) {
        if (i != 0) {
            return i;
        }
        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(R.attr.bottomSheetDialogTheme, typedValue, true)) {
            return typedValue.resourceId;
        }
        return R.style.Theme_Design_Light_BottomSheetDialog;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b() {
        this.c.removeBottomSheetCallback(this.k);
    }

    /* loaded from: classes2.dex */
    private static class a extends BottomSheetBehavior.BottomSheetCallback {
        private final boolean a;
        private final boolean b;
        private final WindowInsetsCompat c;

        private a(@NonNull View view, @NonNull WindowInsetsCompat windowInsetsCompat) {
            ColorStateList colorStateList;
            this.c = windowInsetsCompat;
            this.b = Build.VERSION.SDK_INT >= 23 && (view.getSystemUiVisibility() & 8192) != 0;
            MaterialShapeDrawable a = BottomSheetBehavior.from(view).a();
            if (a != null) {
                colorStateList = a.getFillColor();
            } else {
                colorStateList = ViewCompat.getBackgroundTintList(view);
            }
            if (colorStateList != null) {
                this.a = MaterialColors.isColorLight(colorStateList.getDefaultColor());
            } else if (view.getBackground() instanceof ColorDrawable) {
                this.a = MaterialColors.isColorLight(((ColorDrawable) view.getBackground()).getColor());
            } else {
                this.a = this.b;
            }
        }

        @Override // com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
        public void onStateChanged(@NonNull View view, int i) {
            a(view);
        }

        @Override // com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
        public void onSlide(@NonNull View view, float f) {
            a(view);
        }

        private void a(View view) {
            if (view.getTop() < this.c.getSystemWindowInsetTop()) {
                BottomSheetDialog.setLightStatusBar(view, this.a);
                view.setPadding(view.getPaddingLeft(), this.c.getSystemWindowInsetTop() - view.getTop(), view.getPaddingRight(), view.getPaddingBottom());
            } else if (view.getTop() != 0) {
                BottomSheetDialog.setLightStatusBar(view, this.b);
                view.setPadding(view.getPaddingLeft(), 0, view.getPaddingRight(), view.getPaddingBottom());
            }
        }
    }

    public static void setLightStatusBar(@NonNull View view, boolean z) {
        if (Build.VERSION.SDK_INT >= 23) {
            int systemUiVisibility = view.getSystemUiVisibility();
            view.setSystemUiVisibility(z ? systemUiVisibility | 8192 : systemUiVisibility & (-8193));
        }
    }
}
