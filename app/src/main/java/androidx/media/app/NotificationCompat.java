package androidx.media.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.media.session.MediaSession;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.media.session.MediaSessionCompat;
import android.widget.RemoteViews;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.core.app.BundleCompat;
import androidx.core.app.NotificationBuilderWithBuilderAccessor;
import androidx.core.app.NotificationCompat;
import androidx.media.R;

/* loaded from: classes.dex */
public class NotificationCompat {
    private NotificationCompat() {
    }

    /* loaded from: classes.dex */
    public static class MediaStyle extends NotificationCompat.Style {
        int[] d = null;
        MediaSessionCompat.Token e;
        boolean f;
        PendingIntent g;

        public static MediaSessionCompat.Token getMediaSession(Notification notification) {
            Bundle extras = androidx.core.app.NotificationCompat.getExtras(notification);
            if (extras == null) {
                return null;
            }
            if (Build.VERSION.SDK_INT >= 21) {
                Parcelable parcelable = extras.getParcelable(androidx.core.app.NotificationCompat.EXTRA_MEDIA_SESSION);
                if (parcelable != null) {
                    return MediaSessionCompat.Token.fromToken(parcelable);
                }
                return null;
            }
            IBinder binder = BundleCompat.getBinder(extras, androidx.core.app.NotificationCompat.EXTRA_MEDIA_SESSION);
            if (binder == null) {
                return null;
            }
            Parcel obtain = Parcel.obtain();
            obtain.writeStrongBinder(binder);
            obtain.setDataPosition(0);
            MediaSessionCompat.Token createFromParcel = MediaSessionCompat.Token.CREATOR.createFromParcel(obtain);
            obtain.recycle();
            return createFromParcel;
        }

        public MediaStyle() {
        }

        public MediaStyle(NotificationCompat.Builder builder) {
            setBuilder(builder);
        }

        public MediaStyle setShowActionsInCompactView(int... iArr) {
            this.d = iArr;
            return this;
        }

        public MediaStyle setMediaSession(MediaSessionCompat.Token token) {
            this.e = token;
            return this;
        }

        public MediaStyle setShowCancelButton(boolean z) {
            if (Build.VERSION.SDK_INT < 21) {
                this.f = z;
            }
            return this;
        }

        public MediaStyle setCancelButtonIntent(PendingIntent pendingIntent) {
            this.g = pendingIntent;
            return this;
        }

        @Override // androidx.core.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY})
        public void apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (Build.VERSION.SDK_INT >= 21) {
                b.a(notificationBuilderWithBuilderAccessor.getBuilder(), b.a(b.a(), this.d, this.e));
            } else if (this.f) {
                notificationBuilderWithBuilderAccessor.getBuilder().setOngoing(true);
            }
        }

        @Override // androidx.core.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY})
        public RemoteViews makeContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (Build.VERSION.SDK_INT >= 21) {
                return null;
            }
            return b();
        }

        RemoteViews b() {
            RemoteViews applyStandardTemplate = applyStandardTemplate(false, a(), true);
            int size = this.mBuilder.mActions.size();
            int[] iArr = this.d;
            int min = iArr == null ? 0 : Math.min(iArr.length, 3);
            applyStandardTemplate.removeAllViews(R.id.media_actions);
            if (min > 0) {
                for (int i = 0; i < min; i++) {
                    if (i < size) {
                        applyStandardTemplate.addView(R.id.media_actions, a(this.mBuilder.mActions.get(this.d[i])));
                    } else {
                        throw new IllegalArgumentException(String.format("setShowActionsInCompactView: action %d out of bounds (max %d)", Integer.valueOf(i), Integer.valueOf(size - 1)));
                    }
                }
            }
            if (this.f) {
                applyStandardTemplate.setViewVisibility(R.id.end_padder, 8);
                applyStandardTemplate.setViewVisibility(R.id.cancel_action, 0);
                applyStandardTemplate.setOnClickPendingIntent(R.id.cancel_action, this.g);
                applyStandardTemplate.setInt(R.id.cancel_action, "setAlpha", this.mBuilder.mContext.getResources().getInteger(R.integer.cancel_button_image_alpha));
            } else {
                applyStandardTemplate.setViewVisibility(R.id.end_padder, 0);
                applyStandardTemplate.setViewVisibility(R.id.cancel_action, 8);
            }
            return applyStandardTemplate;
        }

        private RemoteViews a(NotificationCompat.Action action) {
            boolean z = action.getActionIntent() == null;
            RemoteViews remoteViews = new RemoteViews(this.mBuilder.mContext.getPackageName(), R.layout.notification_media_action);
            remoteViews.setImageViewResource(R.id.action0, action.getIcon());
            if (!z) {
                remoteViews.setOnClickPendingIntent(R.id.action0, action.getActionIntent());
            }
            if (Build.VERSION.SDK_INT >= 15) {
                a.a(remoteViews, R.id.action0, action.getTitle());
            }
            return remoteViews;
        }

        int a() {
            return R.layout.notification_template_media;
        }

        @Override // androidx.core.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY})
        public RemoteViews makeBigContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (Build.VERSION.SDK_INT >= 21) {
                return null;
            }
            return c();
        }

        RemoteViews c() {
            int min = Math.min(this.mBuilder.mActions.size(), 5);
            RemoteViews applyStandardTemplate = applyStandardTemplate(false, a(min), false);
            applyStandardTemplate.removeAllViews(R.id.media_actions);
            if (min > 0) {
                for (int i = 0; i < min; i++) {
                    applyStandardTemplate.addView(R.id.media_actions, a(this.mBuilder.mActions.get(i)));
                }
            }
            if (this.f) {
                applyStandardTemplate.setViewVisibility(R.id.cancel_action, 0);
                applyStandardTemplate.setInt(R.id.cancel_action, "setAlpha", this.mBuilder.mContext.getResources().getInteger(R.integer.cancel_button_image_alpha));
                applyStandardTemplate.setOnClickPendingIntent(R.id.cancel_action, this.g);
            } else {
                applyStandardTemplate.setViewVisibility(R.id.cancel_action, 8);
            }
            return applyStandardTemplate;
        }

        int a(int i) {
            if (i <= 3) {
                return R.layout.notification_template_big_media_narrow;
            }
            return R.layout.notification_template_big_media;
        }
    }

    /* loaded from: classes.dex */
    public static class DecoratedMediaCustomViewStyle extends MediaStyle {
        @Override // androidx.media.app.NotificationCompat.MediaStyle, androidx.core.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY})
        public void apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (Build.VERSION.SDK_INT >= 24) {
                b.a(notificationBuilderWithBuilderAccessor.getBuilder(), b.a(c.a(), this.d, this.e));
            } else {
                super.apply(notificationBuilderWithBuilderAccessor);
            }
        }

        @Override // androidx.media.app.NotificationCompat.MediaStyle, androidx.core.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY})
        public RemoteViews makeContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (Build.VERSION.SDK_INT >= 24) {
                return null;
            }
            boolean z = true;
            boolean z2 = this.mBuilder.getContentView() != null;
            if (Build.VERSION.SDK_INT >= 21) {
                if (!z2 && this.mBuilder.getBigContentView() == null) {
                    z = false;
                }
                if (z) {
                    RemoteViews b = b();
                    if (z2) {
                        buildIntoRemoteViews(b, this.mBuilder.getContentView());
                    }
                    a(b);
                    return b;
                }
            } else {
                RemoteViews b2 = b();
                if (z2) {
                    buildIntoRemoteViews(b2, this.mBuilder.getContentView());
                    return b2;
                }
            }
            return null;
        }

        @Override // androidx.media.app.NotificationCompat.MediaStyle
        int a() {
            if (this.mBuilder.getContentView() != null) {
                return R.layout.notification_template_media_custom;
            }
            return super.a();
        }

        @Override // androidx.media.app.NotificationCompat.MediaStyle, androidx.core.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY})
        public RemoteViews makeBigContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            RemoteViews remoteViews;
            if (Build.VERSION.SDK_INT >= 24) {
                return null;
            }
            if (this.mBuilder.getBigContentView() != null) {
                remoteViews = this.mBuilder.getBigContentView();
            } else {
                remoteViews = this.mBuilder.getContentView();
            }
            if (remoteViews == null) {
                return null;
            }
            RemoteViews c = c();
            buildIntoRemoteViews(c, remoteViews);
            if (Build.VERSION.SDK_INT >= 21) {
                a(c);
            }
            return c;
        }

        @Override // androidx.media.app.NotificationCompat.MediaStyle
        int a(int i) {
            if (i <= 3) {
                return R.layout.notification_template_big_media_narrow_custom;
            }
            return R.layout.notification_template_big_media_custom;
        }

        @Override // androidx.core.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY})
        public RemoteViews makeHeadsUpContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            RemoteViews remoteViews;
            if (Build.VERSION.SDK_INT >= 24) {
                return null;
            }
            if (this.mBuilder.getHeadsUpContentView() != null) {
                remoteViews = this.mBuilder.getHeadsUpContentView();
            } else {
                remoteViews = this.mBuilder.getContentView();
            }
            if (remoteViews == null) {
                return null;
            }
            RemoteViews c = c();
            buildIntoRemoteViews(c, remoteViews);
            if (Build.VERSION.SDK_INT >= 21) {
                a(c);
            }
            return c;
        }

        private void a(RemoteViews remoteViews) {
            int i;
            if (this.mBuilder.getColor() != 0) {
                i = this.mBuilder.getColor();
            } else {
                i = this.mBuilder.mContext.getResources().getColor(R.color.notification_material_background_media_default_color);
            }
            remoteViews.setInt(R.id.status_bar_latest_event_content, "setBackgroundColor", i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(15)
    /* loaded from: classes.dex */
    public static class a {
        @DoNotInline
        static void a(RemoteViews remoteViews, int i, CharSequence charSequence) {
            remoteViews.setContentDescription(i, charSequence);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(21)
    /* loaded from: classes.dex */
    public static class b {
        @DoNotInline
        static void a(Notification.Builder builder, Notification.MediaStyle mediaStyle) {
            builder.setStyle(mediaStyle);
        }

        @DoNotInline
        static Notification.MediaStyle a() {
            return new Notification.MediaStyle();
        }

        @DoNotInline
        static Notification.MediaStyle a(Notification.MediaStyle mediaStyle, int[] iArr, MediaSessionCompat.Token token) {
            if (iArr != null) {
                a(mediaStyle, iArr);
            }
            if (token != null) {
                a(mediaStyle, (MediaSession.Token) token.getToken());
            }
            return mediaStyle;
        }

        @DoNotInline
        static void a(Notification.MediaStyle mediaStyle, int... iArr) {
            mediaStyle.setShowActionsInCompactView(iArr);
        }

        @DoNotInline
        static void a(Notification.MediaStyle mediaStyle, MediaSession.Token token) {
            mediaStyle.setMediaSession(token);
        }
    }

    @RequiresApi(24)
    /* loaded from: classes.dex */
    private static class c {
        @DoNotInline
        static Notification.DecoratedMediaCustomViewStyle a() {
            return new Notification.DecoratedMediaCustomViewStyle();
        }
    }
}
