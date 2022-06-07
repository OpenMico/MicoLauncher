package io.reactivex.disposables;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.internal.util.ExceptionHelper;

/* compiled from: ActionDisposable.java */
/* loaded from: classes4.dex */
final class a extends c<Action> {
    private static final long serialVersionUID = -8219729196779211169L;

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(Action action) {
        super(action);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void a(@NonNull Action action) {
        try {
            action.run();
        } catch (Throwable th) {
            throw ExceptionHelper.wrapOrThrow(th);
        }
    }
}
