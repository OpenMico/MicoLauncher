package com.bumptech.glide.request.target;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.transition.Transition;

/* loaded from: classes.dex */
public final class PreloadTarget<Z> extends CustomTarget<Z> {
    private static final Handler a = new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: com.bumptech.glide.request.target.PreloadTarget.1
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            if (message.what != 1) {
                return false;
            }
            ((PreloadTarget) message.obj).a();
            return true;
        }
    });
    private final RequestManager b;

    @Override // com.bumptech.glide.request.target.Target
    public void onLoadCleared(@Nullable Drawable drawable) {
    }

    public static <Z> PreloadTarget<Z> obtain(RequestManager requestManager, int i, int i2) {
        return new PreloadTarget<>(requestManager, i, i2);
    }

    private PreloadTarget(RequestManager requestManager, int i, int i2) {
        super(i, i2);
        this.b = requestManager;
    }

    @Override // com.bumptech.glide.request.target.Target
    public void onResourceReady(@NonNull Z z, @Nullable Transition<? super Z> transition) {
        Request request = getRequest();
        if (request != null && request.isComplete()) {
            a.obtainMessage(1, this).sendToTarget();
        }
    }

    void a() {
        this.b.clear(this);
    }
}
