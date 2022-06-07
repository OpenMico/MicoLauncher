package com.bumptech.glide.request.target;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.RemoteViews;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.util.Preconditions;

/* loaded from: classes.dex */
public class NotificationTarget extends CustomTarget<Bitmap> {
    private final RemoteViews a;
    private final Context b;
    private final int c;
    private final String d;
    private final Notification e;
    private final int f;

    @Override // com.bumptech.glide.request.target.Target
    public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object obj, @Nullable Transition transition) {
        onResourceReady((Bitmap) obj, (Transition<? super Bitmap>) transition);
    }

    public NotificationTarget(Context context, int i, RemoteViews remoteViews, Notification notification, int i2) {
        this(context, i, remoteViews, notification, i2, null);
    }

    public NotificationTarget(Context context, int i, RemoteViews remoteViews, Notification notification, int i2, String str) {
        this(context, Integer.MIN_VALUE, Integer.MIN_VALUE, i, remoteViews, notification, i2, str);
    }

    public NotificationTarget(Context context, int i, int i2, int i3, RemoteViews remoteViews, Notification notification, int i4, String str) {
        super(i, i2);
        this.b = (Context) Preconditions.checkNotNull(context, "Context must not be null!");
        this.e = (Notification) Preconditions.checkNotNull(notification, "Notification object can not be null!");
        this.a = (RemoteViews) Preconditions.checkNotNull(remoteViews, "RemoteViews object can not be null!");
        this.f = i3;
        this.c = i4;
        this.d = str;
    }

    private void a() {
        ((NotificationManager) Preconditions.checkNotNull((NotificationManager) this.b.getSystemService("notification"))).notify(this.d, this.c, this.e);
    }

    public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
        a(bitmap);
    }

    @Override // com.bumptech.glide.request.target.Target
    public void onLoadCleared(@Nullable Drawable drawable) {
        a(null);
    }

    private void a(@Nullable Bitmap bitmap) {
        this.a.setImageViewBitmap(this.f, bitmap);
        a();
    }
}
