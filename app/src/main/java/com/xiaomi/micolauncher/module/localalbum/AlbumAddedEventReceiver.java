package com.xiaomi.micolauncher.module.localalbum;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.RemoteViews;
import androidx.annotation.GuardedBy;
import com.xiaomi.mico.base.utils.ActivityUtil;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.controllers.ControllerBase;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.event.LocalAlbumFilesUpdated;
import com.xiaomi.micolauncher.common.push.MicoNotification;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import io.reactivex.Flowable;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes3.dex */
public class AlbumAddedEventReceiver extends ControllerBase {
    static final /* synthetic */ boolean a = !AlbumAddedEventReceiver.class.desiredAssertionStatus();
    private static final int b = MicoNotification.generateUniqueId();
    private static final int c = MicoNotification.generateUniqueId();
    private static final int d = (int) TimeUnit.MINUTES.toSeconds(1);
    private final Object e = new Object();
    @GuardedBy("notificationLock")
    private MicoNotification f;

    public AlbumAddedEventReceiver(Context context) {
        super(context);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onFilesReceived(final LocalAlbumFilesUpdated localAlbumFilesUpdated) {
        L.localalbum.i("in bg thread : on files received %s", Integer.valueOf(localAlbumFilesUpdated.getFilesCount()));
        Flowable.fromCallable(new Callable<Object>() { // from class: com.xiaomi.micolauncher.module.localalbum.AlbumAddedEventReceiver.1
            @Override // java.util.concurrent.Callable
            public Object call() {
                synchronized (this) {
                    AlbumAddedEventReceiver.this.a(localAlbumFilesUpdated);
                }
                return null;
            }
        }).delay(1L, TimeUnit.SECONDS).subscribeOn(MicoSchedulers.computation()).subscribe();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(LocalAlbumFilesUpdated localAlbumFilesUpdated) {
        L.localalbum.i("in Rx thread : on files received %s", Integer.valueOf(localAlbumFilesUpdated.getFilesCount()));
        Context context = getContext();
        if (Hardware.isBigScreen()) {
            L.localalbum.i("send broadcast to gallery");
            context.sendBroadcast(new Intent("com.xiaomi.mico.gallery.REFRESH"));
            String string = getContext().getString(R.string.gallery_notification_content_x_photos_received, Integer.valueOf(localAlbumFilesUpdated.getFilesCount()));
            MicoNotification.Builder builder = new MicoNotification.Builder(context);
            builder.setNotificationId(R.string.gallery_notification_content_x_photos_received).setSmallIcon(R.drawable.notification_album).setTitle(context.getString(R.string.notification_title_album)).setText(string).setHowToDeal(context.getString(R.string.gallery_notification_browse)).setPendingIntent(a(context));
            builder.build().show();
            return;
        }
        b b2 = b(localAlbumFilesUpdated);
        if (b2.b) {
            if (a || b2.a != null) {
                synchronized (this.e) {
                    if (this.f != null) {
                        this.f.cancel();
                    }
                    this.f = new MicoNotification.Builder(context).setNotificationId(b).setPendingIntent(b(context)).setRemoteViews(b2.a).setTimeoutAfter(d).build();
                    this.f.show("com.xiaomi.micolauncher.album.AlbumOpenAction", new a());
                }
                return;
            }
            throw new AssertionError();
        }
    }

    @NotNull
    private b b(LocalAlbumFilesUpdated localAlbumFilesUpdated) {
        if (ActivityUtil.isPackageInForeground(getContext(), "com.android.gallery3d")) {
            return new b(null, false);
        }
        RemoteViews remoteViews = new RemoteViews(getContext().getPackageName(), (int) R.layout.notification_family_album);
        remoteViews.setTextViewText(R.id.text, getContext().getString(R.string.gallery_notification_content_x_photos_received, Integer.valueOf(localAlbumFilesUpdated.getFilesCount())));
        return new b(remoteViews, true);
    }

    private PendingIntent a(Context context) {
        return PendingIntent.getActivity(context, 0, new Intent("com.xiaomi.mico.gallery.MAIN"), 0);
    }

    private PendingIntent b(Context context) {
        return PendingIntent.getBroadcast(context, c, new Intent().setAction("com.xiaomi.micolauncher.album.AlbumOpenAction"), 268435456);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class a extends BroadcastReceiver {
        private a() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            synchronized (AlbumAddedEventReceiver.this.e) {
                if (AlbumAddedEventReceiver.this.f != null) {
                    if (TextUtils.equals(intent.getAction(), "com.xiaomi.micolauncher.album.AlbumOpenAction")) {
                        AlbumAddedEventReceiver.this.f = null;
                        AlbumImagesManager.showBlankAlbumPromptOrAlbum(context);
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class b {
        RemoteViews a;
        boolean b;

        b(RemoteViews remoteViews, boolean z) {
            this.a = remoteViews;
            this.b = z;
        }
    }
}
