package com.bumptech.glide;

import android.app.Activity;
import android.content.ComponentCallbacks2;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.View;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.gifdecoder.GifDecoder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.ResourceEncoder;
import com.bumptech.glide.load.data.InputStreamRewinder;
import com.bumptech.glide.load.data.ParcelFileDescriptorRewinder;
import com.bumptech.glide.load.engine.Engine;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.MemoryCache;
import com.bumptech.glide.load.engine.prefill.BitmapPreFiller;
import com.bumptech.glide.load.engine.prefill.PreFillType;
import com.bumptech.glide.load.model.AssetUriLoader;
import com.bumptech.glide.load.model.ByteArrayLoader;
import com.bumptech.glide.load.model.ByteBufferEncoder;
import com.bumptech.glide.load.model.ByteBufferFileLoader;
import com.bumptech.glide.load.model.DataUrlLoader;
import com.bumptech.glide.load.model.FileLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.MediaStoreFileLoader;
import com.bumptech.glide.load.model.ResourceLoader;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.model.StringLoader;
import com.bumptech.glide.load.model.UnitModelLoader;
import com.bumptech.glide.load.model.UriLoader;
import com.bumptech.glide.load.model.UrlUriLoader;
import com.bumptech.glide.load.model.stream.HttpGlideUrlLoader;
import com.bumptech.glide.load.model.stream.MediaStoreImageThumbLoader;
import com.bumptech.glide.load.model.stream.MediaStoreVideoThumbLoader;
import com.bumptech.glide.load.model.stream.QMediaStoreUriLoader;
import com.bumptech.glide.load.model.stream.UrlLoader;
import com.bumptech.glide.load.resource.bitmap.BitmapDrawableDecoder;
import com.bumptech.glide.load.resource.bitmap.BitmapDrawableEncoder;
import com.bumptech.glide.load.resource.bitmap.BitmapEncoder;
import com.bumptech.glide.load.resource.bitmap.ByteBufferBitmapDecoder;
import com.bumptech.glide.load.resource.bitmap.ByteBufferBitmapImageDecoderResourceDecoder;
import com.bumptech.glide.load.resource.bitmap.DefaultImageHeaderParser;
import com.bumptech.glide.load.resource.bitmap.Downsampler;
import com.bumptech.glide.load.resource.bitmap.ExifInterfaceImageHeaderParser;
import com.bumptech.glide.load.resource.bitmap.HardwareConfigState;
import com.bumptech.glide.load.resource.bitmap.InputStreamBitmapImageDecoderResourceDecoder;
import com.bumptech.glide.load.resource.bitmap.ParcelFileDescriptorBitmapDecoder;
import com.bumptech.glide.load.resource.bitmap.ResourceBitmapDecoder;
import com.bumptech.glide.load.resource.bitmap.StreamBitmapDecoder;
import com.bumptech.glide.load.resource.bitmap.UnitBitmapDecoder;
import com.bumptech.glide.load.resource.bitmap.VideoDecoder;
import com.bumptech.glide.load.resource.bytes.ByteBufferRewinder;
import com.bumptech.glide.load.resource.drawable.AnimatedWebpDecoder;
import com.bumptech.glide.load.resource.drawable.ResourceDrawableDecoder;
import com.bumptech.glide.load.resource.drawable.UnitDrawableDecoder;
import com.bumptech.glide.load.resource.file.FileDecoder;
import com.bumptech.glide.load.resource.gif.ByteBufferGifDecoder;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawableEncoder;
import com.bumptech.glide.load.resource.gif.GifFrameResourceDecoder;
import com.bumptech.glide.load.resource.gif.StreamGifDecoder;
import com.bumptech.glide.load.resource.transcode.BitmapBytesTranscoder;
import com.bumptech.glide.load.resource.transcode.BitmapDrawableTranscoder;
import com.bumptech.glide.load.resource.transcode.DrawableBytesTranscoder;
import com.bumptech.glide.load.resource.transcode.GifDrawableBytesTranscoder;
import com.bumptech.glide.manager.ConnectivityMonitorFactory;
import com.bumptech.glide.manager.RequestManagerRetriever;
import com.bumptech.glide.module.GlideModule;
import com.bumptech.glide.module.ManifestParser;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ImageViewTargetFactory;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.util.Preconditions;
import com.bumptech.glide.util.Util;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public class Glide implements ComponentCallbacks2 {
    @GuardedBy("Glide.class")
    private static volatile Glide a;
    private static volatile boolean b;
    private final Engine c;
    private final BitmapPool d;
    private final MemoryCache e;
    private final GlideContext f;
    private final ArrayPool h;
    private final RequestManagerRetriever i;
    private final ConnectivityMonitorFactory j;
    private final RequestOptionsFactory l;
    @Nullable
    @GuardedBy("this")
    private BitmapPreFiller n;
    @GuardedBy("managers")
    private final List<RequestManager> k = new ArrayList();
    private MemoryCategory m = MemoryCategory.NORMAL;
    private final Registry g = new Registry();

    /* loaded from: classes.dex */
    public interface RequestOptionsFactory {
        @NonNull
        RequestOptions build();
    }

    @Override // android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
    }

    @Nullable
    public static File getPhotoCacheDir(@NonNull Context context) {
        return getPhotoCacheDir(context, DiskCache.Factory.DEFAULT_DISK_CACHE_DIR);
    }

    @Nullable
    public static File getPhotoCacheDir(@NonNull Context context, @NonNull String str) {
        File cacheDir = context.getCacheDir();
        if (cacheDir != null) {
            File file = new File(cacheDir, str);
            if (file.isDirectory() || file.mkdirs()) {
                return file;
            }
            return null;
        }
        if (Log.isLoggable("Glide", 6)) {
            Log.e("Glide", "default disk cache dir is null");
        }
        return null;
    }

    @NonNull
    public static Glide get(@NonNull Context context) {
        if (a == null) {
            GeneratedAppGlideModule a2 = a(context.getApplicationContext());
            synchronized (Glide.class) {
                if (a == null) {
                    a(context, a2);
                }
            }
        }
        return a;
    }

    @GuardedBy("Glide.class")
    private static void a(@NonNull Context context, @Nullable GeneratedAppGlideModule generatedAppGlideModule) {
        if (!b) {
            b = true;
            b(context, generatedAppGlideModule);
            b = false;
            return;
        }
        throw new IllegalStateException("You cannot call Glide.get() in registerComponents(), use the provided Glide instance instead");
    }

    @VisibleForTesting
    @Deprecated
    public static synchronized void init(Glide glide) {
        synchronized (Glide.class) {
            if (a != null) {
                tearDown();
            }
            a = glide;
        }
    }

    @VisibleForTesting
    public static void init(@NonNull Context context, @NonNull GlideBuilder glideBuilder) {
        GeneratedAppGlideModule a2 = a(context);
        synchronized (Glide.class) {
            if (a != null) {
                tearDown();
            }
            a(context, glideBuilder, a2);
        }
    }

    @VisibleForTesting
    public static void enableHardwareBitmaps() {
        HardwareConfigState.getInstance().unblockHardwareBitmaps();
    }

    @VisibleForTesting
    public static void tearDown() {
        synchronized (Glide.class) {
            if (a != null) {
                a.getContext().getApplicationContext().unregisterComponentCallbacks(a);
                a.c.shutdown();
            }
            a = null;
        }
    }

    @GuardedBy("Glide.class")
    private static void b(@NonNull Context context, @Nullable GeneratedAppGlideModule generatedAppGlideModule) {
        a(context, new GlideBuilder(), generatedAppGlideModule);
    }

    @GuardedBy("Glide.class")
    private static void a(@NonNull Context context, @NonNull GlideBuilder glideBuilder, @Nullable GeneratedAppGlideModule generatedAppGlideModule) {
        Context applicationContext = context.getApplicationContext();
        List<GlideModule> emptyList = Collections.emptyList();
        if (generatedAppGlideModule == null || generatedAppGlideModule.isManifestParsingEnabled()) {
            emptyList = new ManifestParser(applicationContext).parse();
        }
        if (generatedAppGlideModule != null && !generatedAppGlideModule.a().isEmpty()) {
            Set<Class<?>> a2 = generatedAppGlideModule.a();
            Iterator<GlideModule> it = emptyList.iterator();
            while (it.hasNext()) {
                GlideModule next = it.next();
                if (a2.contains(next.getClass())) {
                    if (Log.isLoggable("Glide", 3)) {
                        Log.d("Glide", "AppGlideModule excludes manifest GlideModule: " + next);
                    }
                    it.remove();
                }
            }
        }
        if (Log.isLoggable("Glide", 3)) {
            Iterator<GlideModule> it2 = emptyList.iterator();
            while (it2.hasNext()) {
                Log.d("Glide", "Discovered GlideModule from manifest: " + it2.next().getClass());
            }
        }
        glideBuilder.a(generatedAppGlideModule != null ? generatedAppGlideModule.b() : null);
        for (GlideModule glideModule : emptyList) {
            glideModule.applyOptions(applicationContext, glideBuilder);
        }
        if (generatedAppGlideModule != null) {
            generatedAppGlideModule.applyOptions(applicationContext, glideBuilder);
        }
        Glide a3 = glideBuilder.a(applicationContext);
        for (GlideModule glideModule2 : emptyList) {
            try {
                glideModule2.registerComponents(applicationContext, a3, a3.g);
            } catch (AbstractMethodError e) {
                throw new IllegalStateException("Attempting to register a Glide v3 module. If you see this, you or one of your dependencies may be including Glide v3 even though you're using Glide v4. You'll need to find and remove (or update) the offending dependency. The v3 module name is: " + glideModule2.getClass().getName(), e);
            }
        }
        if (generatedAppGlideModule != null) {
            generatedAppGlideModule.registerComponents(applicationContext, a3, a3.g);
        }
        applicationContext.registerComponentCallbacks(a3);
        a = a3;
    }

    @Nullable
    private static GeneratedAppGlideModule a(Context context) {
        try {
            return (GeneratedAppGlideModule) Class.forName("com.bumptech.glide.GeneratedAppGlideModuleImpl").getDeclaredConstructor(Context.class).newInstance(context.getApplicationContext());
        } catch (ClassNotFoundException unused) {
            if (Log.isLoggable("Glide", 5)) {
                Log.w("Glide", "Failed to find GeneratedAppGlideModule. You should include an annotationProcessor compile dependency on com.github.bumptech.glide:compiler in your application and a @GlideModule annotated AppGlideModule implementation or LibraryGlideModules will be silently ignored");
            }
            return null;
        } catch (IllegalAccessException e) {
            a(e);
            return null;
        } catch (InstantiationException e2) {
            a(e2);
            return null;
        } catch (NoSuchMethodException e3) {
            a(e3);
            return null;
        } catch (InvocationTargetException e4) {
            a(e4);
            return null;
        }
    }

    private static void a(Exception exc) {
        throw new IllegalStateException("GeneratedAppGlideModuleImpl is implemented incorrectly. If you've manually implemented this class, remove your implementation. The Annotation processor will generate a correct implementation.", exc);
    }

    public Glide(@NonNull Context context, @NonNull Engine engine, @NonNull MemoryCache memoryCache, @NonNull BitmapPool bitmapPool, @NonNull ArrayPool arrayPool, @NonNull RequestManagerRetriever requestManagerRetriever, @NonNull ConnectivityMonitorFactory connectivityMonitorFactory, int i, @NonNull RequestOptionsFactory requestOptionsFactory, @NonNull Map<Class<?>, TransitionOptions<?, ?>> map, @NonNull List<RequestListener<Object>> list, GlideExperiments glideExperiments) {
        ResourceDecoder resourceDecoder;
        ResourceDecoder resourceDecoder2;
        ResourceDrawableDecoder resourceDrawableDecoder;
        this.c = engine;
        this.d = bitmapPool;
        this.h = arrayPool;
        this.e = memoryCache;
        this.i = requestManagerRetriever;
        this.j = connectivityMonitorFactory;
        this.l = requestOptionsFactory;
        Resources resources = context.getResources();
        this.g.register(new DefaultImageHeaderParser());
        if (Build.VERSION.SDK_INT >= 27) {
            this.g.register(new ExifInterfaceImageHeaderParser());
        }
        List<ImageHeaderParser> imageHeaderParsers = this.g.getImageHeaderParsers();
        ByteBufferGifDecoder byteBufferGifDecoder = new ByteBufferGifDecoder(context, imageHeaderParsers, bitmapPool, arrayPool);
        ResourceDecoder<ParcelFileDescriptor, Bitmap> parcel = VideoDecoder.parcel(bitmapPool);
        Downsampler downsampler = new Downsampler(this.g.getImageHeaderParsers(), resources.getDisplayMetrics(), bitmapPool, arrayPool);
        if (Build.VERSION.SDK_INT < 28 || !glideExperiments.isEnabled(GlideBuilder.b.class)) {
            resourceDecoder = new ByteBufferBitmapDecoder(downsampler);
            resourceDecoder2 = new StreamBitmapDecoder(downsampler, arrayPool);
        } else {
            resourceDecoder2 = new InputStreamBitmapImageDecoderResourceDecoder();
            resourceDecoder = new ByteBufferBitmapImageDecoderResourceDecoder();
        }
        if (Build.VERSION.SDK_INT >= 28 && glideExperiments.isEnabled(GlideBuilder.a.class)) {
            this.g.append("Animation", InputStream.class, Drawable.class, AnimatedWebpDecoder.streamDecoder(imageHeaderParsers, arrayPool));
            this.g.append("Animation", ByteBuffer.class, Drawable.class, AnimatedWebpDecoder.byteBufferDecoder(imageHeaderParsers, arrayPool));
        }
        ResourceDrawableDecoder resourceDrawableDecoder2 = new ResourceDrawableDecoder(context);
        ResourceLoader.StreamFactory streamFactory = new ResourceLoader.StreamFactory(resources);
        ResourceLoader.UriFactory uriFactory = new ResourceLoader.UriFactory(resources);
        ResourceLoader.FileDescriptorFactory fileDescriptorFactory = new ResourceLoader.FileDescriptorFactory(resources);
        ResourceLoader.AssetFileDescriptorFactory assetFileDescriptorFactory = new ResourceLoader.AssetFileDescriptorFactory(resources);
        BitmapEncoder bitmapEncoder = new BitmapEncoder(arrayPool);
        BitmapBytesTranscoder bitmapBytesTranscoder = new BitmapBytesTranscoder();
        GifDrawableBytesTranscoder gifDrawableBytesTranscoder = new GifDrawableBytesTranscoder();
        ContentResolver contentResolver = context.getContentResolver();
        this.g.append(ByteBuffer.class, new ByteBufferEncoder()).append(InputStream.class, new StreamEncoder(arrayPool)).append(Registry.BUCKET_BITMAP, ByteBuffer.class, Bitmap.class, resourceDecoder).append(Registry.BUCKET_BITMAP, InputStream.class, Bitmap.class, resourceDecoder2);
        if (ParcelFileDescriptorRewinder.isSupported()) {
            resourceDrawableDecoder = resourceDrawableDecoder2;
            this.g.append(Registry.BUCKET_BITMAP, ParcelFileDescriptor.class, Bitmap.class, new ParcelFileDescriptorBitmapDecoder(downsampler));
        } else {
            resourceDrawableDecoder = resourceDrawableDecoder2;
        }
        this.g.append(Registry.BUCKET_BITMAP, ParcelFileDescriptor.class, Bitmap.class, parcel).append(Registry.BUCKET_BITMAP, AssetFileDescriptor.class, Bitmap.class, VideoDecoder.asset(bitmapPool)).append(Bitmap.class, Bitmap.class, UnitModelLoader.Factory.getInstance()).append(Registry.BUCKET_BITMAP, Bitmap.class, Bitmap.class, new UnitBitmapDecoder()).append(Bitmap.class, (ResourceEncoder) bitmapEncoder).append(Registry.BUCKET_BITMAP_DRAWABLE, ByteBuffer.class, BitmapDrawable.class, new BitmapDrawableDecoder(resources, resourceDecoder)).append(Registry.BUCKET_BITMAP_DRAWABLE, InputStream.class, BitmapDrawable.class, new BitmapDrawableDecoder(resources, resourceDecoder2)).append(Registry.BUCKET_BITMAP_DRAWABLE, ParcelFileDescriptor.class, BitmapDrawable.class, new BitmapDrawableDecoder(resources, parcel)).append(BitmapDrawable.class, (ResourceEncoder) new BitmapDrawableEncoder(bitmapPool, bitmapEncoder)).append("Animation", InputStream.class, GifDrawable.class, new StreamGifDecoder(imageHeaderParsers, byteBufferGifDecoder, arrayPool)).append("Animation", ByteBuffer.class, GifDrawable.class, byteBufferGifDecoder).append(GifDrawable.class, (ResourceEncoder) new GifDrawableEncoder()).append(GifDecoder.class, GifDecoder.class, UnitModelLoader.Factory.getInstance()).append(Registry.BUCKET_BITMAP, GifDecoder.class, Bitmap.class, new GifFrameResourceDecoder(bitmapPool)).append(Uri.class, Drawable.class, resourceDrawableDecoder).append(Uri.class, Bitmap.class, new ResourceBitmapDecoder(resourceDrawableDecoder, bitmapPool)).register(new ByteBufferRewinder.Factory()).append(File.class, ByteBuffer.class, new ByteBufferFileLoader.Factory()).append(File.class, InputStream.class, new FileLoader.StreamFactory()).append(File.class, File.class, new FileDecoder()).append(File.class, ParcelFileDescriptor.class, new FileLoader.FileDescriptorFactory()).append(File.class, File.class, UnitModelLoader.Factory.getInstance()).register(new InputStreamRewinder.Factory(arrayPool));
        if (ParcelFileDescriptorRewinder.isSupported()) {
            this.g.register(new ParcelFileDescriptorRewinder.Factory());
        }
        this.g.append(Integer.TYPE, InputStream.class, streamFactory).append(Integer.TYPE, ParcelFileDescriptor.class, fileDescriptorFactory).append(Integer.class, InputStream.class, streamFactory).append(Integer.class, ParcelFileDescriptor.class, fileDescriptorFactory).append(Integer.class, Uri.class, uriFactory).append(Integer.TYPE, AssetFileDescriptor.class, assetFileDescriptorFactory).append(Integer.class, AssetFileDescriptor.class, assetFileDescriptorFactory).append(Integer.TYPE, Uri.class, uriFactory).append(String.class, InputStream.class, new DataUrlLoader.StreamFactory()).append(Uri.class, InputStream.class, new DataUrlLoader.StreamFactory()).append(String.class, InputStream.class, new StringLoader.StreamFactory()).append(String.class, ParcelFileDescriptor.class, new StringLoader.FileDescriptorFactory()).append(String.class, AssetFileDescriptor.class, new StringLoader.AssetFileDescriptorFactory()).append(Uri.class, InputStream.class, new AssetUriLoader.StreamFactory(context.getAssets())).append(Uri.class, AssetFileDescriptor.class, new AssetUriLoader.FileDescriptorFactory(context.getAssets())).append(Uri.class, InputStream.class, new MediaStoreImageThumbLoader.Factory(context)).append(Uri.class, InputStream.class, new MediaStoreVideoThumbLoader.Factory(context));
        if (Build.VERSION.SDK_INT >= 29) {
            this.g.append(Uri.class, InputStream.class, new QMediaStoreUriLoader.InputStreamFactory(context));
            this.g.append(Uri.class, ParcelFileDescriptor.class, new QMediaStoreUriLoader.FileDescriptorFactory(context));
        }
        this.g.append(Uri.class, InputStream.class, new UriLoader.StreamFactory(contentResolver)).append(Uri.class, ParcelFileDescriptor.class, new UriLoader.FileDescriptorFactory(contentResolver)).append(Uri.class, AssetFileDescriptor.class, new UriLoader.AssetFileDescriptorFactory(contentResolver)).append(Uri.class, InputStream.class, new UrlUriLoader.StreamFactory()).append(URL.class, InputStream.class, new UrlLoader.StreamFactory()).append(Uri.class, File.class, new MediaStoreFileLoader.Factory(context)).append(GlideUrl.class, InputStream.class, new HttpGlideUrlLoader.Factory()).append(byte[].class, ByteBuffer.class, new ByteArrayLoader.ByteBufferFactory()).append(byte[].class, InputStream.class, new ByteArrayLoader.StreamFactory()).append(Uri.class, Uri.class, UnitModelLoader.Factory.getInstance()).append(Drawable.class, Drawable.class, UnitModelLoader.Factory.getInstance()).append(Drawable.class, Drawable.class, new UnitDrawableDecoder()).register(Bitmap.class, BitmapDrawable.class, new BitmapDrawableTranscoder(resources)).register(Bitmap.class, byte[].class, bitmapBytesTranscoder).register(Drawable.class, byte[].class, new DrawableBytesTranscoder(bitmapPool, bitmapBytesTranscoder, gifDrawableBytesTranscoder)).register(GifDrawable.class, byte[].class, gifDrawableBytesTranscoder);
        if (Build.VERSION.SDK_INT >= 23) {
            ResourceDecoder<ByteBuffer, Bitmap> byteBuffer = VideoDecoder.byteBuffer(bitmapPool);
            this.g.append(ByteBuffer.class, Bitmap.class, byteBuffer);
            this.g.append(ByteBuffer.class, BitmapDrawable.class, new BitmapDrawableDecoder(resources, byteBuffer));
        }
        this.f = new GlideContext(context, arrayPool, this.g, new ImageViewTargetFactory(), requestOptionsFactory, map, list, engine, glideExperiments, i);
    }

    @NonNull
    public BitmapPool getBitmapPool() {
        return this.d;
    }

    @NonNull
    public ArrayPool getArrayPool() {
        return this.h;
    }

    @NonNull
    public Context getContext() {
        return this.f.getBaseContext();
    }

    public ConnectivityMonitorFactory a() {
        return this.j;
    }

    @NonNull
    public GlideContext b() {
        return this.f;
    }

    public synchronized void preFillBitmapPool(@NonNull PreFillType.Builder... builderArr) {
        if (this.n == null) {
            this.n = new BitmapPreFiller(this.e, this.d, (DecodeFormat) this.l.build().getOptions().get(Downsampler.DECODE_FORMAT));
        }
        this.n.preFill(builderArr);
    }

    public void clearMemory() {
        Util.assertMainThread();
        this.e.clearMemory();
        this.d.clearMemory();
        this.h.clearMemory();
    }

    public void trimMemory(int i) {
        Util.assertMainThread();
        synchronized (this.k) {
            for (RequestManager requestManager : this.k) {
                requestManager.onTrimMemory(i);
            }
        }
        this.e.trimMemory(i);
        this.d.trimMemory(i);
        this.h.trimMemory(i);
    }

    public void clearDiskCache() {
        Util.assertBackgroundThread();
        this.c.clearDiskCache();
    }

    @NonNull
    public RequestManagerRetriever getRequestManagerRetriever() {
        return this.i;
    }

    @NonNull
    public MemoryCategory setMemoryCategory(@NonNull MemoryCategory memoryCategory) {
        Util.assertMainThread();
        this.e.setSizeMultiplier(memoryCategory.getMultiplier());
        this.d.setSizeMultiplier(memoryCategory.getMultiplier());
        MemoryCategory memoryCategory2 = this.m;
        this.m = memoryCategory;
        return memoryCategory2;
    }

    @NonNull
    private static RequestManagerRetriever b(@Nullable Context context) {
        Preconditions.checkNotNull(context, "You cannot start a load on a not yet attached View or a Fragment where getActivity() returns null (which usually occurs when getActivity() is called before the Fragment is attached or after the Fragment is destroyed).");
        return get(context).getRequestManagerRetriever();
    }

    @NonNull
    public static RequestManager with(@NonNull Context context) {
        return b(context).get(context);
    }

    @NonNull
    public static RequestManager with(@NonNull Activity activity) {
        return b(activity).get(activity);
    }

    @NonNull
    public static RequestManager with(@NonNull FragmentActivity fragmentActivity) {
        return b(fragmentActivity).get(fragmentActivity);
    }

    @NonNull
    public static RequestManager with(@NonNull Fragment fragment) {
        return b(fragment.getContext()).get(fragment);
    }

    @NonNull
    @Deprecated
    public static RequestManager with(@NonNull android.app.Fragment fragment) {
        return b(fragment.getActivity()).get(fragment);
    }

    @NonNull
    public static RequestManager with(@NonNull View view) {
        return b(view.getContext()).get(view);
    }

    @NonNull
    public Registry getRegistry() {
        return this.g;
    }

    public boolean a(@NonNull Target<?> target) {
        synchronized (this.k) {
            for (RequestManager requestManager : this.k) {
                if (requestManager.a(target)) {
                    return true;
                }
            }
            return false;
        }
    }

    public void a(RequestManager requestManager) {
        synchronized (this.k) {
            if (!this.k.contains(requestManager)) {
                this.k.add(requestManager);
            } else {
                throw new IllegalStateException("Cannot register already registered manager");
            }
        }
    }

    public void b(RequestManager requestManager) {
        synchronized (this.k) {
            if (this.k.contains(requestManager)) {
                this.k.remove(requestManager);
            } else {
                throw new IllegalStateException("Cannot unregister not yet registered manager");
            }
        }
    }

    @Override // android.content.ComponentCallbacks2
    public void onTrimMemory(int i) {
        trimMemory(i);
    }

    @Override // android.content.ComponentCallbacks
    public void onLowMemory() {
        clearMemory();
    }
}
