package com.xiaomi.micolauncher.module.localalbum;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.ubus.storage.LocalAlbumStorage;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/* loaded from: classes3.dex */
public class AlbumImagesManager {
    public static void showBlankAlbumPromptOrAlbum(final Context context) {
        if (Hardware.isBigScreen()) {
            b(context);
        } else {
            Observable.fromCallable($$Lambda$AlbumImagesManager$xrlEidy2Usp7SUY21bK6x3s3sg.INSTANCE).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.computation()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.localalbum.-$$Lambda$AlbumImagesManager$HTIVmP0Fgi6MQ9zZ1yd4xcNac_k
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    AlbumImagesManager.a(context, (Boolean) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Boolean a() throws Exception {
        return Boolean.valueOf(ContainerUtil.hasData(LocalAlbumStorage.getInstance().getDownloadedFiles()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Context context, Boolean bool) throws Exception {
        if (bool.booleanValue()) {
            c(context);
        } else {
            a(context);
        }
    }

    private static void a(Context context) {
        ActivityLifeCycleManager.startActivityQuietly(context, new Intent(context, AlbumBlankActivity.class));
    }

    private static void b(Context context) {
        ActivityLifeCycleManager.startActivityQuietly(context, new Intent("com.xiaomi.mico.gallery.MAIN"));
    }

    private static void c(Context context) {
        Intent d = d(context);
        if (d == null) {
            L.localalbum.e("failed to get intent for browse album folder");
        } else {
            ActivityLifeCycleManager.startActivityQuietly(context, d);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v0, types: [android.content.Context] */
    /* JADX WARN: Type inference failed for: r12v1, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r12v4 */
    @Nullable
    private static Intent d(Context context) {
        Throwable th;
        String[] strArr;
        Cursor cursor;
        SQLException e;
        try {
            L.localalbum.d("browser folder with gallery %s", LocalAlbumStorage.LOCAL_ALBUM_FILES_DIR_NAME);
            strArr = new String[]{"bucket_display_name", "bucket_id"};
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, strArr, "bucket_display_name=?", new String[]{LocalAlbumStorage.LOCAL_ALBUM_FILES_DIR_NAME}, null);
            try {
                if (cursor == null) {
                    L.localalbum.e("cannot find %s, enter gallery failed", LocalAlbumStorage.LOCAL_ALBUM_FILES_DIR_NAME);
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                    return null;
                } else if (!cursor.moveToFirst()) {
                    L.localalbum.e("move cursor index failed, cannot find %s, enter gallery failed", LocalAlbumStorage.LOCAL_ALBUM_FILES_DIR_NAME);
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                    return null;
                } else {
                    String string = cursor.getString(cursor.getColumnIndex("bucket_id"));
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                    Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    L.localalbum.d("bucketId %s", string);
                    if (TextUtils.isEmpty(string)) {
                        return null;
                    }
                    return new Intent("android.intent.action.VIEW", uri.buildUpon().authority("media").appendQueryParameter("bucketId", string).build()).putExtra("return-to-caller-on-leaf-album", true);
                }
            } catch (SQLException e2) {
                e = e2;
                L.localalbum.e("failed to query media store", e);
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
                return null;
            }
        } catch (SQLException e3) {
            e = e3;
            cursor = null;
        } catch (Throwable th3) {
            th = th3;
            context = 0;
            if (context != 0 && !context.isClosed()) {
                context.close();
            }
            throw th;
        }
    }
}
