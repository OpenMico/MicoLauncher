package androidx.databinding;

import android.view.View;
import android.view.ViewStub;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* loaded from: classes.dex */
public class ViewStubProxy {
    private ViewStub a;
    private ViewDataBinding b;
    private View c;
    private ViewStub.OnInflateListener d;
    private ViewDataBinding e;
    private ViewStub.OnInflateListener f = new ViewStub.OnInflateListener() { // from class: androidx.databinding.ViewStubProxy.1
        @Override // android.view.ViewStub.OnInflateListener
        public void onInflate(ViewStub viewStub, View view) {
            ViewStubProxy.this.c = view;
            ViewStubProxy viewStubProxy = ViewStubProxy.this;
            viewStubProxy.b = DataBindingUtil.a(viewStubProxy.e.mBindingComponent, view, viewStub.getLayoutResource());
            ViewStubProxy.this.a = null;
            if (ViewStubProxy.this.d != null) {
                ViewStubProxy.this.d.onInflate(viewStub, view);
                ViewStubProxy.this.d = null;
            }
            ViewStubProxy.this.e.invalidateAll();
            ViewStubProxy.this.e.a();
        }
    };

    public ViewStubProxy(@NonNull ViewStub viewStub) {
        this.a = viewStub;
        this.a.setOnInflateListener(this.f);
    }

    public void setContainingBinding(@NonNull ViewDataBinding viewDataBinding) {
        this.e = viewDataBinding;
    }

    public boolean isInflated() {
        return this.c != null;
    }

    public View getRoot() {
        return this.c;
    }

    @Nullable
    public ViewDataBinding getBinding() {
        return this.b;
    }

    @Nullable
    public ViewStub getViewStub() {
        return this.a;
    }

    public void setOnInflateListener(@Nullable ViewStub.OnInflateListener onInflateListener) {
        if (this.a != null) {
            this.d = onInflateListener;
        }
    }
}
