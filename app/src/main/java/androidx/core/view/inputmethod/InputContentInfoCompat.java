package androidx.core.view.inputmethod;

import android.content.ClipDescription;
import android.net.Uri;
import android.os.Build;
import android.view.inputmethod.InputContentInfo;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/* loaded from: classes.dex */
public final class InputContentInfoCompat {
    private final c a;

    /* loaded from: classes.dex */
    private interface c {
        @NonNull
        Uri a();

        @NonNull
        ClipDescription b();

        @Nullable
        Uri c();

        @Nullable
        Object d();

        void e();

        void f();
    }

    /* loaded from: classes.dex */
    private static final class b implements c {
        @NonNull
        private final Uri a;
        @NonNull
        private final ClipDescription b;
        @Nullable
        private final Uri c;

        @Override // androidx.core.view.inputmethod.InputContentInfoCompat.c
        @Nullable
        public Object d() {
            return null;
        }

        @Override // androidx.core.view.inputmethod.InputContentInfoCompat.c
        public void e() {
        }

        @Override // androidx.core.view.inputmethod.InputContentInfoCompat.c
        public void f() {
        }

        b(@NonNull Uri uri, @NonNull ClipDescription clipDescription, @Nullable Uri uri2) {
            this.a = uri;
            this.b = clipDescription;
            this.c = uri2;
        }

        @Override // androidx.core.view.inputmethod.InputContentInfoCompat.c
        @NonNull
        public Uri a() {
            return this.a;
        }

        @Override // androidx.core.view.inputmethod.InputContentInfoCompat.c
        @NonNull
        public ClipDescription b() {
            return this.b;
        }

        @Override // androidx.core.view.inputmethod.InputContentInfoCompat.c
        @Nullable
        public Uri c() {
            return this.c;
        }
    }

    @RequiresApi(25)
    /* loaded from: classes.dex */
    private static final class a implements c {
        @NonNull
        final InputContentInfo a;

        a(@NonNull Object obj) {
            this.a = (InputContentInfo) obj;
        }

        a(@NonNull Uri uri, @NonNull ClipDescription clipDescription, @Nullable Uri uri2) {
            this.a = new InputContentInfo(uri, clipDescription, uri2);
        }

        @Override // androidx.core.view.inputmethod.InputContentInfoCompat.c
        @NonNull
        public Uri a() {
            return this.a.getContentUri();
        }

        @Override // androidx.core.view.inputmethod.InputContentInfoCompat.c
        @NonNull
        public ClipDescription b() {
            return this.a.getDescription();
        }

        @Override // androidx.core.view.inputmethod.InputContentInfoCompat.c
        @Nullable
        public Uri c() {
            return this.a.getLinkUri();
        }

        @Override // androidx.core.view.inputmethod.InputContentInfoCompat.c
        @Nullable
        public Object d() {
            return this.a;
        }

        @Override // androidx.core.view.inputmethod.InputContentInfoCompat.c
        public void e() {
            this.a.requestPermission();
        }

        @Override // androidx.core.view.inputmethod.InputContentInfoCompat.c
        public void f() {
            this.a.releasePermission();
        }
    }

    public InputContentInfoCompat(@NonNull Uri uri, @NonNull ClipDescription clipDescription, @Nullable Uri uri2) {
        if (Build.VERSION.SDK_INT >= 25) {
            this.a = new a(uri, clipDescription, uri2);
        } else {
            this.a = new b(uri, clipDescription, uri2);
        }
    }

    private InputContentInfoCompat(@NonNull c cVar) {
        this.a = cVar;
    }

    @NonNull
    public Uri getContentUri() {
        return this.a.a();
    }

    @NonNull
    public ClipDescription getDescription() {
        return this.a.b();
    }

    @Nullable
    public Uri getLinkUri() {
        return this.a.c();
    }

    @Nullable
    public static InputContentInfoCompat wrap(@Nullable Object obj) {
        if (obj != null && Build.VERSION.SDK_INT >= 25) {
            return new InputContentInfoCompat(new a(obj));
        }
        return null;
    }

    @Nullable
    public Object unwrap() {
        return this.a.d();
    }

    public void requestPermission() {
        this.a.e();
    }

    public void releasePermission() {
        this.a.f();
    }
}
