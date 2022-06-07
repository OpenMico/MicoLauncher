package androidx.loader.content;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.DebugUtils;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* loaded from: classes.dex */
public class Loader<D> {
    int n;
    OnLoadCompleteListener<D> o;
    OnLoadCanceledListener<D> p;
    Context q;
    boolean r = false;
    boolean s = false;
    boolean t = true;
    boolean u = false;
    boolean v = false;

    /* loaded from: classes.dex */
    public interface OnLoadCanceledListener<D> {
        void onLoadCanceled(@NonNull Loader<D> loader);
    }

    /* loaded from: classes.dex */
    public interface OnLoadCompleteListener<D> {
        void onLoadComplete(@NonNull Loader<D> loader, @Nullable D d);
    }

    @MainThread
    protected void onAbandon() {
    }

    @MainThread
    protected boolean onCancelLoad() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @MainThread
    public void onForceLoad() {
    }

    @MainThread
    protected void onReset() {
    }

    @MainThread
    protected void onStartLoading() {
    }

    @MainThread
    protected void onStopLoading() {
    }

    /* loaded from: classes.dex */
    public final class ForceLoadContentObserver extends ContentObserver {
        @Override // android.database.ContentObserver
        public boolean deliverSelfNotifications() {
            return true;
        }

        public ForceLoadContentObserver() {
            super(new Handler());
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            Loader.this.onContentChanged();
        }
    }

    public Loader(@NonNull Context context) {
        this.q = context.getApplicationContext();
    }

    @MainThread
    public void deliverResult(@Nullable D d) {
        OnLoadCompleteListener<D> onLoadCompleteListener = this.o;
        if (onLoadCompleteListener != null) {
            onLoadCompleteListener.onLoadComplete(this, d);
        }
    }

    @MainThread
    public void deliverCancellation() {
        OnLoadCanceledListener<D> onLoadCanceledListener = this.p;
        if (onLoadCanceledListener != null) {
            onLoadCanceledListener.onLoadCanceled(this);
        }
    }

    @NonNull
    public Context getContext() {
        return this.q;
    }

    public int getId() {
        return this.n;
    }

    @MainThread
    public void registerListener(int i, @NonNull OnLoadCompleteListener<D> onLoadCompleteListener) {
        if (this.o == null) {
            this.o = onLoadCompleteListener;
            this.n = i;
            return;
        }
        throw new IllegalStateException("There is already a listener registered");
    }

    @MainThread
    public void unregisterListener(@NonNull OnLoadCompleteListener<D> onLoadCompleteListener) {
        OnLoadCompleteListener<D> onLoadCompleteListener2 = this.o;
        if (onLoadCompleteListener2 == null) {
            throw new IllegalStateException("No listener register");
        } else if (onLoadCompleteListener2 == onLoadCompleteListener) {
            this.o = null;
        } else {
            throw new IllegalArgumentException("Attempting to unregister the wrong listener");
        }
    }

    @MainThread
    public void registerOnLoadCanceledListener(@NonNull OnLoadCanceledListener<D> onLoadCanceledListener) {
        if (this.p == null) {
            this.p = onLoadCanceledListener;
            return;
        }
        throw new IllegalStateException("There is already a listener registered");
    }

    @MainThread
    public void unregisterOnLoadCanceledListener(@NonNull OnLoadCanceledListener<D> onLoadCanceledListener) {
        OnLoadCanceledListener<D> onLoadCanceledListener2 = this.p;
        if (onLoadCanceledListener2 == null) {
            throw new IllegalStateException("No listener register");
        } else if (onLoadCanceledListener2 == onLoadCanceledListener) {
            this.p = null;
        } else {
            throw new IllegalArgumentException("Attempting to unregister the wrong listener");
        }
    }

    public boolean isStarted() {
        return this.r;
    }

    public boolean isAbandoned() {
        return this.s;
    }

    public boolean isReset() {
        return this.t;
    }

    @MainThread
    public final void startLoading() {
        this.r = true;
        this.t = false;
        this.s = false;
        onStartLoading();
    }

    @MainThread
    public boolean cancelLoad() {
        return onCancelLoad();
    }

    @MainThread
    public void forceLoad() {
        onForceLoad();
    }

    @MainThread
    public void stopLoading() {
        this.r = false;
        onStopLoading();
    }

    @MainThread
    public void abandon() {
        this.s = true;
        onAbandon();
    }

    @MainThread
    public void reset() {
        onReset();
        this.t = true;
        this.r = false;
        this.s = false;
        this.u = false;
        this.v = false;
    }

    public boolean takeContentChanged() {
        boolean z = this.u;
        this.u = false;
        this.v |= z;
        return z;
    }

    public void commitContentChanged() {
        this.v = false;
    }

    public void rollbackContentChanged() {
        if (this.v) {
            onContentChanged();
        }
    }

    @MainThread
    public void onContentChanged() {
        if (this.r) {
            forceLoad();
        } else {
            this.u = true;
        }
    }

    @NonNull
    public String dataToString(@Nullable D d) {
        StringBuilder sb = new StringBuilder(64);
        DebugUtils.buildShortClassTag(d, sb);
        sb.append("}");
        return sb.toString();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(64);
        DebugUtils.buildShortClassTag(this, sb);
        sb.append(" id=");
        sb.append(this.n);
        sb.append("}");
        return sb.toString();
    }

    @Deprecated
    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.print(str);
        printWriter.print("mId=");
        printWriter.print(this.n);
        printWriter.print(" mListener=");
        printWriter.println(this.o);
        if (this.r || this.u || this.v) {
            printWriter.print(str);
            printWriter.print("mStarted=");
            printWriter.print(this.r);
            printWriter.print(" mContentChanged=");
            printWriter.print(this.u);
            printWriter.print(" mProcessingChange=");
            printWriter.println(this.v);
        }
        if (this.s || this.t) {
            printWriter.print(str);
            printWriter.print("mAbandoned=");
            printWriter.print(this.s);
            printWriter.print(" mReset=");
            printWriter.println(this.t);
        }
    }
}
