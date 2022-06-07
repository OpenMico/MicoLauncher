package com.bumptech.glide.load.resource.drawable;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import java.util.List;

/* loaded from: classes.dex */
public class ResourceDrawableDecoder implements ResourceDecoder<Uri, Drawable> {
    private final Context a;

    public ResourceDrawableDecoder(Context context) {
        this.a = context.getApplicationContext();
    }

    public boolean handles(@NonNull Uri uri, @NonNull Options options) {
        return uri.getScheme().equals("android.resource");
    }

    @Nullable
    public Resource<Drawable> decode(@NonNull Uri uri, int i, int i2, @NonNull Options options) {
        Context a = a(uri, uri.getAuthority());
        return a.a(DrawableDecoderCompat.getDrawable(this.a, a, a(a, uri)));
    }

    @NonNull
    private Context a(Uri uri, String str) {
        if (str.equals(this.a.getPackageName())) {
            return this.a;
        }
        try {
            return this.a.createPackageContext(str, 0);
        } catch (PackageManager.NameNotFoundException e) {
            if (str.contains(this.a.getPackageName())) {
                return this.a;
            }
            throw new IllegalArgumentException("Failed to obtain context or unrecognized Uri format for: " + uri, e);
        }
    }

    @DrawableRes
    private int a(Context context, Uri uri) {
        List<String> pathSegments = uri.getPathSegments();
        if (pathSegments.size() == 2) {
            return b(context, uri);
        }
        if (pathSegments.size() == 1) {
            return a(uri);
        }
        throw new IllegalArgumentException("Unrecognized Uri format: " + uri);
    }

    @DrawableRes
    private int b(Context context, Uri uri) {
        List<String> pathSegments = uri.getPathSegments();
        String authority = uri.getAuthority();
        String str = pathSegments.get(0);
        String str2 = pathSegments.get(1);
        int identifier = context.getResources().getIdentifier(str2, str, authority);
        if (identifier == 0) {
            identifier = Resources.getSystem().getIdentifier(str2, str, "android");
        }
        if (identifier != 0) {
            return identifier;
        }
        throw new IllegalArgumentException("Failed to find resource id for: " + uri);
    }

    @DrawableRes
    private int a(Uri uri) {
        try {
            return Integer.parseInt(uri.getPathSegments().get(0));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Unrecognized Uri format: " + uri, e);
        }
    }
}
