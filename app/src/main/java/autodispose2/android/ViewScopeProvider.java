package autodispose2.android;

import android.view.View;
import autodispose2.ScopeProvider;
import io.reactivex.rxjava3.core.CompletableSource;

/* loaded from: classes.dex */
public final class ViewScopeProvider implements ScopeProvider {
    private final View a;

    public static ScopeProvider from(View view) {
        if (view != null) {
            return new ViewScopeProvider(view);
        }
        throw new NullPointerException("view == null");
    }

    private ViewScopeProvider(View view) {
        this.a = view;
    }

    @Override // autodispose2.ScopeProvider
    public CompletableSource requestScope() {
        return new a(this.a);
    }
}
