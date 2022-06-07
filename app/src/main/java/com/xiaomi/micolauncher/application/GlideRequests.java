package com.xiaomi.micolauncher.application;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import androidx.annotation.CheckResult;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.manager.Lifecycle;
import com.bumptech.glide.manager.RequestManagerTreeNode;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import java.io.File;
import java.net.URL;

/* loaded from: classes3.dex */
public class GlideRequests extends RequestManager {
    public GlideRequests(@NonNull Glide glide, @NonNull Lifecycle lifecycle, @NonNull RequestManagerTreeNode requestManagerTreeNode, @NonNull Context context) {
        super(glide, lifecycle, requestManagerTreeNode, context);
    }

    @Override // com.bumptech.glide.RequestManager
    @NonNull
    @CheckResult
    public <ResourceType> GlideRequest<ResourceType> as(@NonNull Class<ResourceType> cls) {
        return new GlideRequest<>(this.glide, this, cls, this.context);
    }

    @Override // com.bumptech.glide.RequestManager
    @NonNull
    public synchronized GlideRequests applyDefaultRequestOptions(@NonNull RequestOptions requestOptions) {
        return (GlideRequests) super.applyDefaultRequestOptions(requestOptions);
    }

    @Override // com.bumptech.glide.RequestManager
    @NonNull
    public synchronized GlideRequests setDefaultRequestOptions(@NonNull RequestOptions requestOptions) {
        return (GlideRequests) super.setDefaultRequestOptions(requestOptions);
    }

    @Override // com.bumptech.glide.RequestManager
    @NonNull
    public GlideRequests addDefaultRequestListener(RequestListener<Object> requestListener) {
        return (GlideRequests) super.addDefaultRequestListener(requestListener);
    }

    @Override // com.bumptech.glide.RequestManager
    @NonNull
    @CheckResult
    public GlideRequest<Bitmap> asBitmap() {
        return (GlideRequest) super.asBitmap();
    }

    @Override // com.bumptech.glide.RequestManager
    @NonNull
    @CheckResult
    public GlideRequest<GifDrawable> asGif() {
        return (GlideRequest) super.asGif();
    }

    @Override // com.bumptech.glide.RequestManager
    @NonNull
    @CheckResult
    public GlideRequest<Drawable> asDrawable() {
        return (GlideRequest) super.asDrawable();
    }

    @Override // com.bumptech.glide.RequestManager
    @NonNull
    @CheckResult
    public GlideRequest<Drawable> load(@Nullable Bitmap bitmap) {
        return (GlideRequest) super.load(bitmap);
    }

    @Override // com.bumptech.glide.RequestManager
    @NonNull
    @CheckResult
    public GlideRequest<Drawable> load(@Nullable Drawable drawable) {
        return (GlideRequest) super.load(drawable);
    }

    @Override // com.bumptech.glide.RequestManager
    @NonNull
    @CheckResult
    public GlideRequest<Drawable> load(@Nullable String str) {
        return (GlideRequest) super.load(str);
    }

    @Override // com.bumptech.glide.RequestManager
    @NonNull
    @CheckResult
    public GlideRequest<Drawable> load(@Nullable Uri uri) {
        return (GlideRequest) super.load(uri);
    }

    @Override // com.bumptech.glide.RequestManager
    @NonNull
    @CheckResult
    public GlideRequest<Drawable> load(@Nullable File file) {
        return (GlideRequest) super.load(file);
    }

    @Override // com.bumptech.glide.RequestManager
    @NonNull
    @CheckResult
    public GlideRequest<Drawable> load(@Nullable @DrawableRes @RawRes Integer num) {
        return (GlideRequest) super.load(num);
    }

    @Override // com.bumptech.glide.RequestManager
    @CheckResult
    @Deprecated
    public GlideRequest<Drawable> load(@Nullable URL url) {
        return (GlideRequest) super.load(url);
    }

    @Override // com.bumptech.glide.RequestManager
    @NonNull
    @CheckResult
    public GlideRequest<Drawable> load(@Nullable byte[] bArr) {
        return (GlideRequest) super.load(bArr);
    }

    @Override // com.bumptech.glide.RequestManager
    @NonNull
    @CheckResult
    public GlideRequest<Drawable> load(@Nullable Object obj) {
        return (GlideRequest) super.load(obj);
    }

    @Override // com.bumptech.glide.RequestManager
    @NonNull
    @CheckResult
    public GlideRequest<File> downloadOnly() {
        return (GlideRequest) super.downloadOnly();
    }

    @Override // com.bumptech.glide.RequestManager
    @NonNull
    @CheckResult
    public GlideRequest<File> download(@Nullable Object obj) {
        return (GlideRequest) super.download(obj);
    }

    @Override // com.bumptech.glide.RequestManager
    @NonNull
    @CheckResult
    public GlideRequest<File> asFile() {
        return (GlideRequest) super.asFile();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.bumptech.glide.RequestManager
    public void setRequestOptions(@NonNull RequestOptions requestOptions) {
        if (requestOptions instanceof GlideOptions) {
            super.setRequestOptions(requestOptions);
        } else {
            super.setRequestOptions(new GlideOptions().apply2((BaseRequestOptions<?>) requestOptions));
        }
    }
}
