package androidx.fragment.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import androidx.annotation.LayoutRes;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleRes;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewTreeLifecycleOwner;
import androidx.lifecycle.ViewTreeViewModelStoreOwner;
import androidx.savedstate.ViewTreeSavedStateRegistryOwner;

/* loaded from: classes.dex */
public class DialogFragment extends Fragment implements DialogInterface.OnCancelListener, DialogInterface.OnDismissListener {
    public static final int STYLE_NORMAL = 0;
    public static final int STYLE_NO_FRAME = 2;
    public static final int STYLE_NO_INPUT = 3;
    public static final int STYLE_NO_TITLE = 1;
    private Handler a;
    private boolean j;
    @Nullable
    private Dialog l;
    private boolean m;
    private boolean n;
    private boolean o;
    private Runnable b = new Runnable() { // from class: androidx.fragment.app.DialogFragment.1
        @Override // java.lang.Runnable
        @SuppressLint({"SyntheticAccessor"})
        public void run() {
            DialogFragment.this.d.onDismiss(DialogFragment.this.l);
        }
    };
    private DialogInterface.OnCancelListener c = new DialogInterface.OnCancelListener() { // from class: androidx.fragment.app.DialogFragment.2
        @Override // android.content.DialogInterface.OnCancelListener
        @SuppressLint({"SyntheticAccessor"})
        public void onCancel(@Nullable DialogInterface dialogInterface) {
            if (DialogFragment.this.l != null) {
                DialogFragment dialogFragment = DialogFragment.this;
                dialogFragment.onCancel(dialogFragment.l);
            }
        }
    };
    private DialogInterface.OnDismissListener d = new DialogInterface.OnDismissListener() { // from class: androidx.fragment.app.DialogFragment.3
        @Override // android.content.DialogInterface.OnDismissListener
        @SuppressLint({"SyntheticAccessor"})
        public void onDismiss(@Nullable DialogInterface dialogInterface) {
            if (DialogFragment.this.l != null) {
                DialogFragment dialogFragment = DialogFragment.this;
                dialogFragment.onDismiss(dialogFragment.l);
            }
        }
    };
    private int e = 0;
    private int f = 0;
    private boolean g = true;
    private boolean h = true;
    private int i = -1;
    private Observer<LifecycleOwner> k = new Observer<LifecycleOwner>() { // from class: androidx.fragment.app.DialogFragment.4
        @SuppressLint({"SyntheticAccessor"})
        /* renamed from: a */
        public void onChanged(LifecycleOwner lifecycleOwner) {
            if (lifecycleOwner != null && DialogFragment.this.h) {
                View requireView = DialogFragment.this.requireView();
                if (requireView.getParent() != null) {
                    throw new IllegalStateException("DialogFragment can not be attached to a container view");
                } else if (DialogFragment.this.l != null) {
                    if (FragmentManager.a(3)) {
                        Log.d("FragmentManager", "DialogFragment " + this + " setting the content view on " + DialogFragment.this.l);
                    }
                    DialogFragment.this.l.setContentView(requireView);
                }
            }
        }
    };
    private boolean p = false;

    @Override // android.content.DialogInterface.OnCancelListener
    public void onCancel(@NonNull DialogInterface dialogInterface) {
    }

    public DialogFragment() {
    }

    public DialogFragment(@LayoutRes int i) {
        super(i);
    }

    public void setStyle(int i, @StyleRes int i2) {
        if (FragmentManager.a(2)) {
            Log.d("FragmentManager", "Setting style and theme for DialogFragment " + this + " to " + i + ", " + i2);
        }
        this.e = i;
        int i3 = this.e;
        if (i3 == 2 || i3 == 3) {
            this.f = 16973913;
        }
        if (i2 != 0) {
            this.f = i2;
        }
    }

    public void show(@NonNull FragmentManager fragmentManager, @Nullable String str) {
        this.n = false;
        this.o = true;
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        beginTransaction.add(this, str);
        beginTransaction.commit();
    }

    public int show(@NonNull FragmentTransaction fragmentTransaction, @Nullable String str) {
        this.n = false;
        this.o = true;
        fragmentTransaction.add(this, str);
        this.m = false;
        this.i = fragmentTransaction.commit();
        return this.i;
    }

    public void showNow(@NonNull FragmentManager fragmentManager, @Nullable String str) {
        this.n = false;
        this.o = true;
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        beginTransaction.add(this, str);
        beginTransaction.commitNow();
    }

    public void dismiss() {
        a(false, false);
    }

    public void dismissAllowingStateLoss() {
        a(true, false);
    }

    private void a(boolean z, boolean z2) {
        if (!this.n) {
            this.n = true;
            this.o = false;
            Dialog dialog = this.l;
            if (dialog != null) {
                dialog.setOnDismissListener(null);
                this.l.dismiss();
                if (!z2) {
                    if (Looper.myLooper() == this.a.getLooper()) {
                        onDismiss(this.l);
                    } else {
                        this.a.post(this.b);
                    }
                }
            }
            this.m = true;
            if (this.i >= 0) {
                getParentFragmentManager().popBackStack(this.i, 1);
                this.i = -1;
                return;
            }
            FragmentTransaction beginTransaction = getParentFragmentManager().beginTransaction();
            beginTransaction.remove(this);
            if (z) {
                beginTransaction.commitAllowingStateLoss();
            } else {
                beginTransaction.commit();
            }
        }
    }

    @Nullable
    public Dialog getDialog() {
        return this.l;
    }

    @NonNull
    public final Dialog requireDialog() {
        Dialog dialog = getDialog();
        if (dialog != null) {
            return dialog;
        }
        throw new IllegalStateException("DialogFragment " + this + " does not have a Dialog.");
    }

    @StyleRes
    public int getTheme() {
        return this.f;
    }

    public void setCancelable(boolean z) {
        this.g = z;
        Dialog dialog = this.l;
        if (dialog != null) {
            dialog.setCancelable(z);
        }
    }

    public boolean isCancelable() {
        return this.g;
    }

    public void setShowsDialog(boolean z) {
        this.h = z;
    }

    public boolean getShowsDialog() {
        return this.h;
    }

    @Override // androidx.fragment.app.Fragment
    @MainThread
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        getViewLifecycleOwnerLiveData().observeForever(this.k);
        if (!this.o) {
            this.n = false;
        }
    }

    @Override // androidx.fragment.app.Fragment
    @MainThread
    public void onDetach() {
        super.onDetach();
        if (!this.o && !this.n) {
            this.n = true;
        }
        getViewLifecycleOwnerLiveData().removeObserver(this.k);
    }

    @Override // androidx.fragment.app.Fragment
    @MainThread
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        this.a = new Handler();
        this.h = this.mContainerId == 0;
        if (bundle != null) {
            this.e = bundle.getInt("android:style", 0);
            this.f = bundle.getInt("android:theme", 0);
            this.g = bundle.getBoolean("android:cancelable", true);
            this.h = bundle.getBoolean("android:showsDialog", this.h);
            this.i = bundle.getInt("android:backStackId", -1);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void performCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        Bundle bundle2;
        super.performCreateView(layoutInflater, viewGroup, bundle);
        if (this.mView == null && this.l != null && bundle != null && (bundle2 = bundle.getBundle("android:savedDialogState")) != null) {
            this.l.onRestoreInstanceState(bundle2);
        }
    }

    @Override // androidx.fragment.app.Fragment
    @NonNull
    public FragmentContainer createFragmentContainer() {
        final FragmentContainer createFragmentContainer = super.createFragmentContainer();
        return new FragmentContainer() { // from class: androidx.fragment.app.DialogFragment.5
            @Override // androidx.fragment.app.FragmentContainer
            @Nullable
            public View onFindViewById(int i) {
                if (createFragmentContainer.onHasView()) {
                    return createFragmentContainer.onFindViewById(i);
                }
                return DialogFragment.this.a(i);
            }

            @Override // androidx.fragment.app.FragmentContainer
            public boolean onHasView() {
                return createFragmentContainer.onHasView() || DialogFragment.this.a();
            }
        };
    }

    @Nullable
    View a(int i) {
        Dialog dialog = this.l;
        if (dialog != null) {
            return dialog.findViewById(i);
        }
        return null;
    }

    boolean a() {
        return this.p;
    }

    @Override // androidx.fragment.app.Fragment
    @NonNull
    public LayoutInflater onGetLayoutInflater(@Nullable Bundle bundle) {
        LayoutInflater onGetLayoutInflater = super.onGetLayoutInflater(bundle);
        if (!this.h || this.j) {
            if (FragmentManager.a(2)) {
                String str = "getting layout inflater for DialogFragment " + this;
                if (!this.h) {
                    Log.d("FragmentManager", "mShowsDialog = false: " + str);
                } else {
                    Log.d("FragmentManager", "mCreatingDialog = true: " + str);
                }
            }
            return onGetLayoutInflater;
        }
        a(bundle);
        if (FragmentManager.a(2)) {
            Log.d("FragmentManager", "get layout inflater for DialogFragment " + this + " from dialog context");
        }
        Dialog dialog = this.l;
        return dialog != null ? onGetLayoutInflater.cloneInContext(dialog.getContext()) : onGetLayoutInflater;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void setupDialog(@NonNull Dialog dialog, int i) {
        switch (i) {
            case 1:
            case 2:
                break;
            default:
                return;
            case 3:
                Window window = dialog.getWindow();
                if (window != null) {
                    window.addFlags(24);
                    break;
                }
                break;
        }
        dialog.requestWindowFeature(1);
    }

    @NonNull
    @MainThread
    public Dialog onCreateDialog(@Nullable Bundle bundle) {
        if (FragmentManager.a(3)) {
            Log.d("FragmentManager", "onCreateDialog called for DialogFragment " + this);
        }
        return new Dialog(requireContext(), getTheme());
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public void onDismiss(@NonNull DialogInterface dialogInterface) {
        if (!this.m) {
            if (FragmentManager.a(3)) {
                Log.d("FragmentManager", "onDismiss called for DialogFragment " + this);
            }
            a(true, true);
        }
    }

    private void a(@Nullable Bundle bundle) {
        if (this.h && !this.p) {
            try {
                this.j = true;
                this.l = onCreateDialog(bundle);
                if (this.h) {
                    setupDialog(this.l, this.e);
                    Context context = getContext();
                    if (context instanceof Activity) {
                        this.l.setOwnerActivity((Activity) context);
                    }
                    this.l.setCancelable(this.g);
                    this.l.setOnCancelListener(this.c);
                    this.l.setOnDismissListener(this.d);
                    this.p = true;
                } else {
                    this.l = null;
                }
            } finally {
                this.j = false;
            }
        }
    }

    @Override // androidx.fragment.app.Fragment
    @MainThread
    public void onViewStateRestored(@Nullable Bundle bundle) {
        Bundle bundle2;
        super.onViewStateRestored(bundle);
        if (this.l != null && bundle != null && (bundle2 = bundle.getBundle("android:savedDialogState")) != null) {
            this.l.onRestoreInstanceState(bundle2);
        }
    }

    @Override // androidx.fragment.app.Fragment
    @MainThread
    public void onStart() {
        super.onStart();
        Dialog dialog = this.l;
        if (dialog != null) {
            this.m = false;
            dialog.show();
            View decorView = this.l.getWindow().getDecorView();
            ViewTreeLifecycleOwner.set(decorView, this);
            ViewTreeViewModelStoreOwner.set(decorView, this);
            ViewTreeSavedStateRegistryOwner.set(decorView, this);
        }
    }

    @Override // androidx.fragment.app.Fragment
    @MainThread
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        Dialog dialog = this.l;
        if (dialog != null) {
            Bundle onSaveInstanceState = dialog.onSaveInstanceState();
            onSaveInstanceState.putBoolean("android:dialogShowing", false);
            bundle.putBundle("android:savedDialogState", onSaveInstanceState);
        }
        int i = this.e;
        if (i != 0) {
            bundle.putInt("android:style", i);
        }
        int i2 = this.f;
        if (i2 != 0) {
            bundle.putInt("android:theme", i2);
        }
        boolean z = this.g;
        if (!z) {
            bundle.putBoolean("android:cancelable", z);
        }
        boolean z2 = this.h;
        if (!z2) {
            bundle.putBoolean("android:showsDialog", z2);
        }
        int i3 = this.i;
        if (i3 != -1) {
            bundle.putInt("android:backStackId", i3);
        }
    }

    @Override // androidx.fragment.app.Fragment
    @MainThread
    public void onStop() {
        super.onStop();
        Dialog dialog = this.l;
        if (dialog != null) {
            dialog.hide();
        }
    }

    @Override // androidx.fragment.app.Fragment
    @MainThread
    public void onDestroyView() {
        super.onDestroyView();
        Dialog dialog = this.l;
        if (dialog != null) {
            this.m = true;
            dialog.setOnDismissListener(null);
            this.l.dismiss();
            if (!this.n) {
                onDismiss(this.l);
            }
            this.l = null;
            this.p = false;
        }
    }
}
