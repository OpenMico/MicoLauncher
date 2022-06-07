package com.xiaomi.micolauncher.common;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.ObjectKey;
import com.elvishew.xlog.Logger;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.DisplayUtils;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.application.GlideOkHttpUtil;
import com.xiaomi.micolauncher.common.transformation.BlurTransformation;
import com.xiaomi.micolauncher.common.transformation.CustomBitmapTransformation;
import com.xiaomi.micolauncher.common.transformation.MicoTransformUtils;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import java.io.File;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes3.dex */
public class GlideUtils {
    private static final SparseArray<Drawable> a = new SparseArray<>();
    private static final CenterCrop b = new CenterCrop();
    private static final CircleCrop c = new CircleCrop();
    private static int d = 200;
    private static int e = 30;

    /* loaded from: classes3.dex */
    public interface a {
        RequestBuilder load();
    }

    /* loaded from: classes3.dex */
    public interface b {
    }

    public static RequestManager a(Activity activity) {
        return Glide.with(activity);
    }

    public static RequestOptions getDefaultRequestOptions() {
        return RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE);
    }

    public static RequestManager a(Fragment fragment) {
        return Glide.with(fragment);
    }

    public static RequestManager a(View view) {
        return Glide.with(view);
    }

    public static void bindImageView(final Activity activity, int i, ImageView imageView) {
        a(activity, new b() { // from class: com.xiaomi.micolauncher.common.-$$Lambda$GlideUtils$ipwLty-O2exlHcfaMYA1_YszmW4
            public final RequestManager createRequestManager() {
                RequestManager a2;
                a2 = GlideUtils.a(activity);
                return a2;
            }
        }, i, imageView);
    }

    public static /* synthetic */ RequestBuilder a(View view, int i) {
        return a(view).load(Integer.valueOf(i));
    }

    public static void bindImageView(final View view, final int i, Target target) {
        a(view, new a() { // from class: com.xiaomi.micolauncher.common.-$$Lambda$GlideUtils$-2obcA1bBfRrtDlqDy0HfmzibWE
            @Override // com.xiaomi.micolauncher.common.GlideUtils.a
            public final RequestBuilder load() {
                RequestBuilder a2;
                a2 = GlideUtils.a(view, i);
                return a2;
            }
        }, false, i, 0, null, target, null);
    }

    public static void bindImageView(final Fragment fragment, int i, ImageView imageView) {
        a(fragment.getContext(), new b() { // from class: com.xiaomi.micolauncher.common.-$$Lambda$GlideUtils$Go_zCLipdbdh0gK4OJoeNzz4awg
            public final RequestManager createRequestManager() {
                RequestManager a2;
                a2 = GlideUtils.a(Fragment.this);
                return a2;
            }
        }, i, imageView);
    }

    public static void bindImageView(Context context, int i, final ImageView imageView) {
        a(context, new b() { // from class: com.xiaomi.micolauncher.common.-$$Lambda$GlideUtils$wdGdQOZ2BsA6Z_SplIkW04YkQBQ
            public final RequestManager createRequestManager() {
                RequestManager a2;
                a2 = GlideUtils.a((View) imageView);
                return a2;
            }
        }, i, imageView);
    }

    public static void bindImageView(final ImageView imageView, int i) {
        a(imageView.getContext(), new b() { // from class: com.xiaomi.micolauncher.common.-$$Lambda$GlideUtils$gci5aXk3DDEIB-OaWdd-3C3dkAQ
            public final RequestManager createRequestManager() {
                RequestManager a2;
                a2 = GlideUtils.a((View) imageView);
                return a2;
            }
        }, i, imageView);
    }

    private static void a(Context context, b bVar, int i, ImageView imageView) {
        a(context, bVar, i, imageView, false);
    }

    public static void bindImageViewWithRoundCorners(Context context, int i, ImageView imageView, int i2) {
        bindImageViewWithRoundCorners(context, i, imageView, i2, false);
    }

    public static /* synthetic */ RequestBuilder b(ImageView imageView, int i) {
        return a((View) imageView).load(Integer.valueOf(i));
    }

    public static void bindImageViewWithRoundCorners(Context context, final int i, final ImageView imageView, int i2, boolean z) {
        a(imageView, new a() { // from class: com.xiaomi.micolauncher.common.-$$Lambda$GlideUtils$axWjn9FObgz4J3--xxgpz2SmDJo
            @Override // com.xiaomi.micolauncher.common.GlideUtils.a
            public final RequestBuilder load() {
                RequestBuilder b2;
                b2 = GlideUtils.b(imageView, i);
                return b2;
            }
        }, z, 0, 0, new MultiTransformation(b, new RoundedCorners(i2)), null, null);
    }

    public static void bindImageViewWithRoundCorners(Context context, final File file, final ImageView imageView, int i) {
        a(imageView, new a() { // from class: com.xiaomi.micolauncher.common.-$$Lambda$GlideUtils$mxHePQ4ZYXULLMIIxEJTt-TNgbw
            @Override // com.xiaomi.micolauncher.common.GlideUtils.a
            public final RequestBuilder load() {
                RequestBuilder c2;
                c2 = GlideUtils.c(imageView, file);
                return c2;
            }
        }, false, 0, 0, new MultiTransformation(b, new RoundedCorners(i)), null, null);
    }

    public static /* synthetic */ RequestBuilder c(ImageView imageView, File file) {
        return a((View) imageView).load(file);
    }

    public static /* synthetic */ RequestBuilder a(ImageView imageView, int i) {
        return a((View) imageView).load(Integer.valueOf(i));
    }

    private static void a(Context context, b bVar, final int i, final ImageView imageView, boolean z) {
        a(imageView, new a() { // from class: com.xiaomi.micolauncher.common.-$$Lambda$GlideUtils$qqs_svx6NeHsxNRUob_HLPF148c
            @Override // com.xiaomi.micolauncher.common.GlideUtils.a
            public final RequestBuilder load() {
                RequestBuilder a2;
                a2 = GlideUtils.a(imageView, i);
                return a2;
            }
        }, z, i, 0, null, null, null);
    }

    public static /* synthetic */ RequestBuilder b(ImageView imageView, File file) {
        return a((View) imageView).load(file).apply((BaseRequestOptions<?>) getDefaultRequestOptions().signature(new ObjectKey(Long.valueOf(file.lastModified()))));
    }

    public static void bindImageView(Context context, final File file, final ImageView imageView) {
        a(imageView, new a() { // from class: com.xiaomi.micolauncher.common.-$$Lambda$GlideUtils$TSn6N5tbYDJCFQsExru9TtiOWzQ
            @Override // com.xiaomi.micolauncher.common.GlideUtils.a
            public final RequestBuilder load() {
                RequestBuilder b2;
                b2 = GlideUtils.b(imageView, file);
                return b2;
            }
        }, false, 0, 0, null, null, null);
    }

    public static /* synthetic */ RequestBuilder a(ImageView imageView, File file) {
        return a((View) imageView).load(file).apply((BaseRequestOptions<?>) getDefaultRequestOptions().signature(new ObjectKey(Long.valueOf(file.lastModified()))));
    }

    public static void bindImageView(final ImageView imageView, final File file, int i, int i2) {
        a(imageView, new a() { // from class: com.xiaomi.micolauncher.common.-$$Lambda$GlideUtils$KhHDqVh54ymwU8xP8YeG4OL73k0
            @Override // com.xiaomi.micolauncher.common.GlideUtils.a
            public final RequestBuilder load() {
                RequestBuilder a2;
                a2 = GlideUtils.a(imageView, file);
                return a2;
            }
        }, true, i2, i2, new MultiTransformation(b, new RoundedCorners(i)), null, null);
    }

    public static /* synthetic */ RequestBuilder b(ImageView imageView, Drawable drawable) {
        return a((View) imageView).load(drawable);
    }

    public static void bindImageView(final ImageView imageView, final Drawable drawable, int i) {
        a(imageView, new a() { // from class: com.xiaomi.micolauncher.common.-$$Lambda$GlideUtils$z_ffXW1N89fJqCGk-3HXRsZ4XeY
            @Override // com.xiaomi.micolauncher.common.GlideUtils.a
            public final RequestBuilder load() {
                RequestBuilder b2;
                b2 = GlideUtils.b(imageView, drawable);
                return b2;
            }
        }, true, 0, 0, new MultiTransformation(b, new RoundedCorners(i)), null, null);
    }

    public static /* synthetic */ RequestBuilder a(ImageView imageView, @NonNull Drawable drawable) {
        return a((View) imageView).load(drawable);
    }

    public static void bindImageView(final ImageView imageView, @NonNull final Drawable drawable) {
        a(imageView, new a() { // from class: com.xiaomi.micolauncher.common.-$$Lambda$GlideUtils$v4bUe4nhCaIjZ5J1yX_srYA3lG0
            @Override // com.xiaomi.micolauncher.common.GlideUtils.a
            public final RequestBuilder load() {
                RequestBuilder a2;
                a2 = GlideUtils.a(imageView, drawable);
                return a2;
            }
        }, false, 0, 0, 0, 0, null, null, Priority.NORMAL);
    }

    public static void bindImageView(Context context, String str, ImageView imageView, int i, int i2, int i3) {
        bindImageView(imageView, str, i, (Transformation<Bitmap>) null, (Target<Bitmap>) null);
    }

    public static void bindImageView(Context context, String str, ImageView imageView, int i) {
        bindImageView(imageView, str, i, (Transformation<Bitmap>) null, (Target<Bitmap>) null);
    }

    public static void bindImageView(Context context, String str, ImageView imageView, Transformation<Bitmap> transformation, int i) {
        bindImageView(imageView, str, i, transformation, (Target<Bitmap>) null);
    }

    public static void bindImageView(Context context, String str, ImageView imageView, int i, int i2, int i3, Target<Bitmap> target) {
        bindImageView(imageView, str, i3, (Transformation<Bitmap>) null, target);
    }

    private static void a(final View view, final a aVar, final boolean z, final int i, final int i2, final int i3, final int i4, final Transformation<Bitmap> transformation, final Target<Bitmap> target, final Priority priority) {
        if (view != null) {
            final Context context = view.getContext();
            if (!a(context)) {
                L.base.i("is invalid context");
            } else if (isGlideQueuedCountMax()) {
                L.base.i("is glide queue max");
                clearCache(context);
            } else {
                UiUtils.waitLayoutComplete(new UiUtils.OnLayoutCompleteListener() { // from class: com.xiaomi.micolauncher.common.-$$Lambda$GlideUtils$lQIAeg3rM4v9jzxes5uwA4hGrc8
                    @Override // com.xiaomi.micolauncher.common.utils.UiUtils.OnLayoutCompleteListener
                    public final void onLayoutComplete(boolean z2) {
                        GlideUtils.a(context, i3, i4, view, i, i2, transformation, priority, z, aVar, target, z2);
                    }
                }, view);
            }
        }
    }

    public static /* synthetic */ void a(Context context, int i, int i2, View view, int i3, int i4, Transformation transformation, Priority priority, boolean z, a aVar, Target target, boolean z2) {
        RequestOptions requestOptions;
        int i5;
        if (!a(context)) {
            L.base.i("is invalid context in layout complete");
            return;
        }
        if (i == 0 || i2 == 0) {
            requestOptions = b(view);
        } else {
            if (i == DisplayUtils.getScreenWidthPixels(view.getContext()) || i2 == DisplayUtils.getScreenHeightPixels(view.getContext())) {
                i5 = (int) (i * 0.8f);
                i2 = (int) (i2 * 0.8f);
            } else {
                i5 = i;
            }
            requestOptions = getDefaultRequestOptions().override(i5, i2);
        }
        if (i3 != 0) {
            requestOptions.placeholder(i3);
        }
        if (i4 != 0) {
            requestOptions.error(i4);
        }
        if (transformation != null) {
            requestOptions.transform(transformation);
        }
        if (priority != null) {
            requestOptions.priority(priority);
        }
        if (!z) {
            requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        }
        RequestBuilder load = aVar.load();
        if (target != null) {
            load.apply((BaseRequestOptions<?>) requestOptions).into((RequestBuilder) target);
        } else {
            load.apply((BaseRequestOptions<?>) requestOptions).into((ImageView) view);
        }
    }

    private static void a(View view, a aVar, boolean z, int i, int i2, Transformation<Bitmap> transformation, Target<Bitmap> target, Priority priority) {
        a(view, aVar, z, i, i2, 0, 0, transformation, target, priority);
    }

    private static void a(final View view, final String str, int i, int i2, Transformation<Bitmap> transformation, final Target<Bitmap> target, Priority priority) {
        if (!ContainerUtil.isEmpty(str)) {
            a(view, new a() { // from class: com.xiaomi.micolauncher.common.-$$Lambda$GlideUtils$U45NM_4V5lptJKv83fJTPwNjaBw
                @Override // com.xiaomi.micolauncher.common.GlideUtils.a
                public final RequestBuilder load() {
                    RequestBuilder a2;
                    a2 = GlideUtils.a(Target.this, view, str);
                    return a2;
                }
            }, true, i, i2, transformation, target, priority);
        }
    }

    public static /* synthetic */ RequestBuilder a(Target target, View view, String str) {
        if (target != null) {
            return a(view).asBitmap().load(str);
        }
        return a(view).load(str);
    }

    public static void bindImageView(View view, String str, int i, Transformation<Bitmap> transformation, Target<Bitmap> target) {
        a(view, str, i, i, transformation, target, null);
    }

    public static void bindImageView(final View view, final String str, int i, int i2, int i3, final DecodeFormat decodeFormat, Transformation<Bitmap> transformation, final Target<Bitmap> target) {
        if (!ContainerUtil.isEmpty(str)) {
            a(view, new a() { // from class: com.xiaomi.micolauncher.common.-$$Lambda$GlideUtils$f-li0AEa8P17mveOxf-JmNAk7bI
                @Override // com.xiaomi.micolauncher.common.GlideUtils.a
                public final RequestBuilder load() {
                    RequestBuilder a2;
                    a2 = GlideUtils.a(Target.this, view, decodeFormat, str);
                    return a2;
                }
            }, true, i, i, i2, i3, transformation, target, null);
        }
    }

    public static /* synthetic */ RequestBuilder a(Target target, View view, DecodeFormat decodeFormat, String str) {
        if (target != null) {
            return a(view).asBitmap().format(decodeFormat).load(str);
        }
        return a(view).load(str);
    }

    public static void bindImageViewCircle(Context context, String str, ImageView imageView, int i, int i2) {
        bindImageView(imageView, str, 0, c, (Target<Bitmap>) null);
    }

    public static void bindImageViewCircle(Context context, String str, ImageView imageView, int i, int i2, int i3) {
        bindImageView(imageView, str, i, c, (Target<Bitmap>) null);
    }

    public static void bindImageViewCircle(View view, String str, int i, int i2, int i3, DecodeFormat decodeFormat, Target<Bitmap> target) {
        if (decodeFormat == null) {
            bindImageView(view, str, i3, i, i2, DecodeFormat.PREFER_RGB_565, c, target);
        } else {
            bindImageView(view, str, i3, i, i2, decodeFormat, c, target);
        }
    }

    public static void bindImageView(Context context, String str, ImageView imageView) {
        bindImageView(imageView, str, 0, (Transformation<Bitmap>) null, (Target<Bitmap>) null);
    }

    public static void bindImageView(ImageView imageView, String str, int i, int i2) {
        bindImageView(imageView, str, 0, (Transformation<Bitmap>) null, (Target<Bitmap>) null);
    }

    public static void bindImageViewWithPriority(ImageView imageView, String str, Priority priority) {
        a(imageView, str, 0, 0, null, null, priority);
    }

    public static void bindImageViewCornersWithListener(Context context, String str, ImageView imageView, int i, int i2, int i3) {
        bindImageView(imageView, str, i, new MultiTransformation(b, new RoundedCorners(UiUtils.getCornerRadius())), (Target<Bitmap>) null);
    }

    public static RequestOptions createVideoRequestOptions(Context context, int i, int i2, int i3) {
        return getDefaultRequestOptions().skipMemoryCache(false).placeholder(R.drawable.video_loading_hold).transform(MicoTransformUtils.getTransformationWithBlurSamplingCorner(context, i3)).override(i, i2);
    }

    public static RequestOptions createVideoRequestOptionsWithTransform(int i, int i2, MultiTransformation<Bitmap> multiTransformation) {
        return getDefaultRequestOptions().skipMemoryCache(false).transform(multiTransformation).override(i, i2);
    }

    public static void bindImageViewWithDesignatedLocation(ImageView imageView, String str, RequestOptions requestOptions) {
        if (a(imageView.getContext())) {
            Glide.with(imageView.getContext()).load(str).apply((BaseRequestOptions<?>) requestOptions).into(imageView);
        }
    }

    public static void bindImageViewWithListener(Context context, String str, ImageView imageView, int i, Target target) {
        bindImageView(imageView, str, i, (Transformation<Bitmap>) null, target);
    }

    public static void bindImageViewWithListener(Context context, String str, ImageView imageView, Target target) {
        bindImageViewWithListener(context, str, imageView, 0, target);
    }

    @Deprecated
    public static void bindImageViewWithDefault(Context context, String str, ImageView imageView, int i) {
        a(context, str, imageView, i, 0);
    }

    public static void bindImageViewWithError(Context context, String str, ImageView imageView, int i) {
        a(context, str, imageView, i, i);
    }

    private static void a(Context context, String str, ImageView imageView, int i, int i2) {
        a(imageView, str, i, i2, null, null, null);
    }

    public static void bindImageViewWithRoundCorners(Context context, String str, ImageView imageView, int i) {
        bindImageView(imageView, str, 0, new MultiTransformation(b, new RoundedCorners(i)), (Target<Bitmap>) null);
    }

    public static void bindImageViewWithRoundCorners(Context context, String str, ImageView imageView, int i, int i2) {
        bindImageView(imageView, str, i2, new MultiTransformation(b, new RoundedCorners(i)), (Target<Bitmap>) null);
    }

    public static void bindImageViewWithRoundCorners(ImageView imageView, String str, int i, int i2, int i3, int i4) {
        bindImageView(imageView, str, i2, new MultiTransformation(b, new RoundedCorners(i)), (Target<Bitmap>) null);
    }

    public static void bindImageViewWithRoundCorners(Context context, String str, ImageView imageView, int i, int i2, int i3, int i4) {
        bindImageView(imageView, str, i2, new MultiTransformation(b, new RoundedCorners(i)), (Target<Bitmap>) null);
    }

    public static void bindImageViewWithRoundCornersNoCrop(Context context, String str, ImageView imageView, int i, int i2) {
        bindImageView(imageView, str, i2, new RoundedCorners(i), (Target<Bitmap>) null);
    }

    public static void bindImageViewWithRoundNoCropUseContext(Context context, String str, ImageView imageView, int i, int i2) {
        bindImageView(imageView, str, i2, new RoundedCorners(i), (Target<Bitmap>) null);
    }

    public static void bindImageViewWithRoundUseContext(Context context, String str, ImageView imageView, int i, int i2, int i3, int i4) {
        RequestOptions requestOptions;
        if (i3 == 0 || i4 == 0) {
            requestOptions = b((View) imageView);
        } else {
            requestOptions = getDefaultRequestOptions().override(i3, i4);
        }
        Glide.with(context).load(str).apply((BaseRequestOptions<?>) ((RequestOptions) requestOptions.transform(b)).transform(new RoundedCorners(i))).placeholder(i2).into(imageView);
    }

    public static void bindImageViewWithBlurUseContext(Context context, String str, ImageView imageView, int i, int i2, int i3, int i4) {
        RequestOptions requestOptions;
        if (i2 == 0 || i3 == 0) {
            requestOptions = b((View) imageView);
        } else {
            requestOptions = getDefaultRequestOptions().override(i2, i3);
        }
        Glide.with(context).asBitmap().load(str).apply((BaseRequestOptions<?>) requestOptions).transform(new CustomBitmapTransformation(i4, 10, 10)).placeholder(i).into(imageView);
    }

    public static void bindImageViewCircleUseContext(Context context, String str, ImageView imageView, int i) {
        bindImageView(imageView, str, i, c, (Target<Bitmap>) null);
    }

    public static void bindImageViewWithRoundUseContext(Context context, String str, ImageView imageView, int i, int i2) {
        bindImageView(imageView, str, i2, new MultiTransformation(b, new RoundedCorners(i)), (Target<Bitmap>) null);
    }

    public static void loadHolderDrawable(Context context, int i) {
        a.put(i, context.getDrawable(i));
    }

    public static Drawable getDrawable(Context context, int i) {
        Drawable drawable = a.get(i);
        if (drawable != null) {
            return drawable;
        }
        Drawable drawable2 = context.getDrawable(i);
        a.put(i, drawable2);
        return drawable2;
    }

    public static void bindImageViewWithDefaultAndRoundCorners(Context context, String str, ImageView imageView, int i, int i2) {
        bindImageView(imageView, str, i, new MultiTransformation(b, new RoundedCorners(i2)), (Target<Bitmap>) null);
    }

    public static void bindImageViewWithRoundCorners(Context context, String str, ImageView imageView, int i, int i2, int i3, int i4, int i5) {
        a(imageView, str, i5, i4, new MultiTransformation(b, new RoundedCorners(i3)), null, null);
    }

    public static void bindImageViewForFadInCard(ImageView imageView, String str, int i, Transformation<Bitmap> transformation, Target<Bitmap> target) {
        bindImageView(imageView, str, i, transformation, target);
    }

    public static void bindImageViewForFadInCard(ImageView imageView, String str, int i, int i2, int i3, Target<Bitmap> target) {
        bindImageView(imageView, str, i, (Transformation<Bitmap>) null, target);
    }

    public static void bindImageView(Context context, ImageView imageView, String str, int i, int i2) {
        bindImageView(imageView, str, 0, new MultiTransformation(new BlurTransformation(context, 5), b, new RoundedCorners(UiUtils.getCornerRadius())), (Target<Bitmap>) null);
    }

    public static void bindImageView(ImageView imageView, String str, int i, int i2, int i3, Target<Bitmap> target) {
        bindImageView(imageView, str, i3, new MultiTransformation(b, new RoundedCorners(UiUtils.getCornerRadius())), target);
    }

    public static void bindImageView(ImageView imageView, String str, int i, Target<Bitmap> target) {
        bindImageView(imageView, str, i, (Transformation<Bitmap>) null, target);
    }

    public static void bindImageView(View view, String str, int i, int i2, Target<Bitmap> target, int i3) {
        bindImageView(view, str, i, getTransformation(i2, i3), target);
    }

    public static void bindImageViewBlurCenterCrop(View view, String str, int i, int i2, Target<Bitmap> target) {
        bindImageView(view, str, i, getTransformationBlurCenterCrop(i2), target);
    }

    public static MultiTransformation<Bitmap> getTransformationBlurCenterCrop(int i) {
        return MicoTransformUtils.getTransformationWithBlurCornerCenterCrop(i, UiUtils.getCornerRadius());
    }

    public static MultiTransformation<Bitmap> getTransformation(int i, int i2) {
        return MicoTransformUtils.getTransformationWithBlurCorner(i, i2);
    }

    @NotNull
    private static RequestOptions b(View view) {
        int i;
        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();
        if (measuredWidth == 0 || measuredHeight == 0) {
            Logger logger = L.base;
            logger.e("getRequestOptionsHooked image view's measured width is " + view.getMeasuredWidth() + ",measured height is " + view.getMeasuredHeight());
            StringBuilder sb = new StringBuilder();
            sb.append("getRequestOptionsHooked ");
            sb.append(view);
            DebugHelper.printStackTrace(sb.toString());
        }
        if (measuredWidth == DisplayUtils.getScreenWidthPixels(view.getContext()) || measuredHeight == DisplayUtils.getScreenHeightPixels(view.getContext())) {
            i = (int) (measuredWidth * 0.8f);
            measuredHeight = (int) (measuredHeight * 0.8f);
        } else {
            i = measuredWidth;
        }
        return getDefaultRequestOptions().override(i, measuredHeight);
    }

    private static boolean a(Context context) {
        return !ActivityLifeCycleManager.isInvalidActivity(context);
    }

    public static void resumeRequests(Context context) {
        if (a(context)) {
            Glide.with(context).resumeRequests();
        }
    }

    public static void pauseRequests(Context context) {
        if (a(context)) {
            Glide.with(context).pauseRequests();
        }
    }

    public static void clearCache(Context context) {
        Glide.get(context).clearMemory();
        OkHttpClient glideConfigOkHttpClient = GlideOkHttpUtil.getGlideConfigOkHttpClient();
        if (glideConfigOkHttpClient != null) {
            L.base.d("glide runningCallsCount %s , readyCallCounts %s", Integer.valueOf(glideConfigOkHttpClient.dispatcher().runningCallsCount()), Integer.valueOf(glideConfigOkHttpClient.dispatcher().queuedCallsCount()));
            for (Call call : glideConfigOkHttpClient.dispatcher().queuedCalls()) {
                call.cancel();
            }
        }
        try {
            Glide.get(context).getRequestManagerRetriever().get(context).onDestroy();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static boolean isGlideQueuedCountMax() {
        OkHttpClient glideConfigOkHttpClient = GlideOkHttpUtil.getGlideConfigOkHttpClient();
        if (glideConfigOkHttpClient == null) {
            return false;
        }
        return glideConfigOkHttpClient.dispatcher().queuedCallsCount() > d || glideConfigOkHttpClient.dispatcher().runningCallsCount() > e;
    }
}
