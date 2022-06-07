package com.bumptech.glide.request.target;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.RemoteViews;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.util.Preconditions;

/* loaded from: classes.dex */
public class AppWidgetTarget extends CustomTarget<Bitmap> {
    private final int[] a;
    private final ComponentName b;
    private final RemoteViews c;
    private final Context d;
    private final int e;

    @Override // com.bumptech.glide.request.target.Target
    public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object obj, @Nullable Transition transition) {
        onResourceReady((Bitmap) obj, (Transition<? super Bitmap>) transition);
    }

    public AppWidgetTarget(Context context, int i, int i2, int i3, RemoteViews remoteViews, int... iArr) {
        super(i, i2);
        if (iArr.length != 0) {
            this.d = (Context) Preconditions.checkNotNull(context, "Context can not be null!");
            this.c = (RemoteViews) Preconditions.checkNotNull(remoteViews, "RemoteViews object can not be null!");
            this.a = (int[]) Preconditions.checkNotNull(iArr, "WidgetIds can not be null!");
            this.e = i3;
            this.b = null;
            return;
        }
        throw new IllegalArgumentException("WidgetIds must have length > 0");
    }

    public AppWidgetTarget(Context context, int i, RemoteViews remoteViews, int... iArr) {
        this(context, Integer.MIN_VALUE, Integer.MIN_VALUE, i, remoteViews, iArr);
    }

    public AppWidgetTarget(Context context, int i, int i2, int i3, RemoteViews remoteViews, ComponentName componentName) {
        super(i, i2);
        this.d = (Context) Preconditions.checkNotNull(context, "Context can not be null!");
        this.c = (RemoteViews) Preconditions.checkNotNull(remoteViews, "RemoteViews object can not be null!");
        this.b = (ComponentName) Preconditions.checkNotNull(componentName, "ComponentName can not be null!");
        this.e = i3;
        this.a = null;
    }

    public AppWidgetTarget(Context context, int i, RemoteViews remoteViews, ComponentName componentName) {
        this(context, Integer.MIN_VALUE, Integer.MIN_VALUE, i, remoteViews, componentName);
    }

    private void a() {
        AppWidgetManager instance = AppWidgetManager.getInstance(this.d);
        ComponentName componentName = this.b;
        if (componentName != null) {
            instance.updateAppWidget(componentName, this.c);
        } else {
            instance.updateAppWidget(this.a, this.c);
        }
    }

    public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
        a(bitmap);
    }

    @Override // com.bumptech.glide.request.target.Target
    public void onLoadCleared(@Nullable Drawable drawable) {
        a(null);
    }

    private void a(@Nullable Bitmap bitmap) {
        this.c.setImageViewBitmap(this.e, bitmap);
        a();
    }
}
