package com.google.android.exoplayer2.ui;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.media.session.MediaSessionCompat;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.media.app.NotificationCompat;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ControlDispatcher;
import com.google.android.exoplayer2.DefaultControlDispatcher;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.NotificationUtil;
import com.google.android.exoplayer2.util.Util;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class PlayerNotificationManager {
    public static final String ACTION_FAST_FORWARD = "com.google.android.exoplayer.ffwd";
    public static final String ACTION_NEXT = "com.google.android.exoplayer.next";
    public static final String ACTION_PAUSE = "com.google.android.exoplayer.pause";
    public static final String ACTION_PLAY = "com.google.android.exoplayer.play";
    public static final String ACTION_PREVIOUS = "com.google.android.exoplayer.prev";
    public static final String ACTION_REWIND = "com.google.android.exoplayer.rewind";
    public static final String ACTION_STOP = "com.google.android.exoplayer.stop";
    public static final String EXTRA_INSTANCE_ID = "INSTANCE_ID";
    private static int a;
    private boolean A;
    private boolean D;
    private boolean E;
    private boolean G;
    @DrawableRes
    private int L;
    @Nullable
    private String P;
    private final Context b;
    private final String c;
    private final int d;
    private final MediaDescriptionAdapter e;
    @Nullable
    private final NotificationListener f;
    @Nullable
    private final CustomActionReceiver g;
    private final NotificationManagerCompat i;
    private final Map<String, NotificationCompat.Action> m;
    private final Map<String, NotificationCompat.Action> n;
    private final PendingIntent o;
    private final int p;
    @Nullable
    private NotificationCompat.Builder q;
    @Nullable
    private List<NotificationCompat.Action> r;
    @Nullable
    private Player s;
    private boolean u;
    private int v;
    @Nullable
    private MediaSessionCompat.Token w;
    private boolean z;
    private ControlDispatcher t = new DefaultControlDispatcher();
    private final Handler h = Util.createHandler(Looper.getMainLooper(), new Handler.Callback() { // from class: com.google.android.exoplayer2.ui.-$$Lambda$PlayerNotificationManager$U60PfntLnBEAtUZPb3FIGVTHsPg
        @Override // android.os.Handler.Callback
        public final boolean handleMessage(Message message) {
            boolean a2;
            a2 = PlayerNotificationManager.this.a(message);
            return a2;
        }
    });
    private final Player.Listener k = new b();
    private final a l = new a();
    private final IntentFilter j = new IntentFilter();
    private boolean x = true;
    private boolean y = true;
    private boolean F = true;
    private boolean B = true;
    private boolean C = true;
    private boolean I = true;
    private boolean O = true;
    private int K = 0;
    private int J = 0;
    private int N = -1;
    private int H = 1;
    private int M = 1;

    /* loaded from: classes2.dex */
    public interface CustomActionReceiver {
        Map<String, NotificationCompat.Action> createCustomActions(Context context, int i);

        List<String> getCustomActions(Player player);

        void onCustomAction(Player player, String str, Intent intent);
    }

    /* loaded from: classes2.dex */
    public interface MediaDescriptionAdapter {
        @Nullable
        PendingIntent createCurrentContentIntent(Player player);

        @Nullable
        CharSequence getCurrentContentText(Player player);

        CharSequence getCurrentContentTitle(Player player);

        @Nullable
        Bitmap getCurrentLargeIcon(Player player, BitmapCallback bitmapCallback);

        @Nullable
        default CharSequence getCurrentSubText(Player player) {
            return null;
        }
    }

    /* loaded from: classes2.dex */
    public interface NotificationListener {
        default void onNotificationCancelled(int i, boolean z) {
        }

        default void onNotificationPosted(int i, Notification notification, boolean z) {
        }
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface Priority {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface Visibility {
    }

    /* loaded from: classes2.dex */
    public static class Builder {
        protected int channelDescriptionResourceId;
        protected final String channelId;
        protected int channelImportance;
        protected int channelNameResourceId;
        protected final Context context;
        @Nullable
        protected CustomActionReceiver customActionReceiver;
        protected int fastForwardActionIconResourceId;
        @Nullable
        protected String groupKey;
        protected MediaDescriptionAdapter mediaDescriptionAdapter;
        protected int nextActionIconResourceId;
        protected final int notificationId;
        @Nullable
        protected NotificationListener notificationListener;
        protected int pauseActionIconResourceId;
        protected int playActionIconResourceId;
        protected int previousActionIconResourceId;
        protected int rewindActionIconResourceId;
        protected int smallIconResourceId;
        protected int stopActionIconResourceId;

        @Deprecated
        public Builder(Context context, int i, String str, MediaDescriptionAdapter mediaDescriptionAdapter) {
            this(context, i, str);
            this.mediaDescriptionAdapter = mediaDescriptionAdapter;
        }

        public Builder(Context context, int i, String str) {
            Assertions.checkArgument(i > 0);
            this.context = context;
            this.notificationId = i;
            this.channelId = str;
            this.channelImportance = 2;
            this.mediaDescriptionAdapter = new DefaultMediaDescriptionAdapter(null);
            this.smallIconResourceId = R.drawable.exo_notification_small_icon;
            this.playActionIconResourceId = R.drawable.exo_notification_play;
            this.pauseActionIconResourceId = R.drawable.exo_notification_pause;
            this.stopActionIconResourceId = R.drawable.exo_notification_stop;
            this.rewindActionIconResourceId = R.drawable.exo_notification_rewind;
            this.fastForwardActionIconResourceId = R.drawable.exo_notification_fastforward;
            this.previousActionIconResourceId = R.drawable.exo_notification_previous;
            this.nextActionIconResourceId = R.drawable.exo_notification_next;
        }

        public Builder setChannelNameResourceId(int i) {
            this.channelNameResourceId = i;
            return this;
        }

        public Builder setChannelDescriptionResourceId(int i) {
            this.channelDescriptionResourceId = i;
            return this;
        }

        public Builder setChannelImportance(int i) {
            this.channelImportance = i;
            return this;
        }

        public Builder setNotificationListener(NotificationListener notificationListener) {
            this.notificationListener = notificationListener;
            return this;
        }

        public Builder setCustomActionReceiver(CustomActionReceiver customActionReceiver) {
            this.customActionReceiver = customActionReceiver;
            return this;
        }

        public Builder setSmallIconResourceId(int i) {
            this.smallIconResourceId = i;
            return this;
        }

        public Builder setPlayActionIconResourceId(int i) {
            this.playActionIconResourceId = i;
            return this;
        }

        public Builder setPauseActionIconResourceId(int i) {
            this.pauseActionIconResourceId = i;
            return this;
        }

        public Builder setStopActionIconResourceId(int i) {
            this.stopActionIconResourceId = i;
            return this;
        }

        public Builder setRewindActionIconResourceId(int i) {
            this.rewindActionIconResourceId = i;
            return this;
        }

        public Builder setFastForwardActionIconResourceId(int i) {
            this.fastForwardActionIconResourceId = i;
            return this;
        }

        public Builder setPreviousActionIconResourceId(int i) {
            this.previousActionIconResourceId = i;
            return this;
        }

        public Builder setNextActionIconResourceId(int i) {
            this.nextActionIconResourceId = i;
            return this;
        }

        public Builder setGroup(String str) {
            this.groupKey = str;
            return this;
        }

        public Builder setMediaDescriptionAdapter(MediaDescriptionAdapter mediaDescriptionAdapter) {
            this.mediaDescriptionAdapter = mediaDescriptionAdapter;
            return this;
        }

        public PlayerNotificationManager build() {
            int i = this.channelNameResourceId;
            if (i != 0) {
                NotificationUtil.createNotificationChannel(this.context, this.channelId, i, this.channelDescriptionResourceId, this.channelImportance);
            }
            return new PlayerNotificationManager(this.context, this.channelId, this.notificationId, this.mediaDescriptionAdapter, this.notificationListener, this.customActionReceiver, this.smallIconResourceId, this.playActionIconResourceId, this.pauseActionIconResourceId, this.stopActionIconResourceId, this.rewindActionIconResourceId, this.fastForwardActionIconResourceId, this.previousActionIconResourceId, this.nextActionIconResourceId, this.groupKey);
        }
    }

    /* loaded from: classes2.dex */
    public final class BitmapCallback {
        private final int b;

        private BitmapCallback(int i) {
            this.b = i;
        }

        public void onBitmap(Bitmap bitmap) {
            if (bitmap != null) {
                PlayerNotificationManager.this.a(bitmap, this.b);
            }
        }
    }

    protected PlayerNotificationManager(Context context, String str, int i, MediaDescriptionAdapter mediaDescriptionAdapter, @Nullable NotificationListener notificationListener, @Nullable CustomActionReceiver customActionReceiver, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, @Nullable String str2) {
        Map<String, NotificationCompat.Action> map;
        Context applicationContext = context.getApplicationContext();
        this.b = applicationContext;
        this.c = str;
        this.d = i;
        this.e = mediaDescriptionAdapter;
        this.f = notificationListener;
        this.g = customActionReceiver;
        this.L = i2;
        this.P = str2;
        int i10 = a;
        a = i10 + 1;
        this.p = i10;
        this.i = NotificationManagerCompat.from(applicationContext);
        this.m = a(applicationContext, this.p, i3, i4, i5, i6, i7, i8, i9);
        for (String str3 : this.m.keySet()) {
            this.j.addAction(str3);
        }
        if (customActionReceiver != null) {
            map = customActionReceiver.createCustomActions(applicationContext, this.p);
        } else {
            map = Collections.emptyMap();
        }
        this.n = map;
        for (String str4 : this.n.keySet()) {
            this.j.addAction(str4);
        }
        this.o = a("com.google.android.exoplayer.dismiss", applicationContext, this.p);
        this.j.addAction("com.google.android.exoplayer.dismiss");
    }

    public final void setPlayer(@Nullable Player player) {
        boolean z = true;
        Assertions.checkState(Looper.myLooper() == Looper.getMainLooper());
        if (!(player == null || player.getApplicationLooper() == Looper.getMainLooper())) {
            z = false;
        }
        Assertions.checkArgument(z);
        Player player2 = this.s;
        if (player2 != player) {
            if (player2 != null) {
                player2.removeListener(this.k);
                if (player == null) {
                    a(false);
                }
            }
            this.s = player;
            if (player != null) {
                player.addListener(this.k);
                a();
            }
        }
    }

    @Deprecated
    public final void setControlDispatcher(ControlDispatcher controlDispatcher) {
        if (this.t != controlDispatcher) {
            this.t = controlDispatcher;
            invalidate();
        }
    }

    public final void setUseNextAction(boolean z) {
        if (this.y != z) {
            this.y = z;
            invalidate();
        }
    }

    public final void setUsePreviousAction(boolean z) {
        if (this.x != z) {
            this.x = z;
            invalidate();
        }
    }

    public final void setUseNextActionInCompactView(boolean z) {
        if (this.A != z) {
            this.A = z;
            if (z) {
                this.E = false;
            }
            invalidate();
        }
    }

    public final void setUsePreviousActionInCompactView(boolean z) {
        if (this.z != z) {
            this.z = z;
            if (z) {
                this.D = false;
            }
            invalidate();
        }
    }

    public final void setUseFastForwardAction(boolean z) {
        if (this.C != z) {
            this.C = z;
            invalidate();
        }
    }

    public final void setUseRewindAction(boolean z) {
        if (this.B != z) {
            this.B = z;
            invalidate();
        }
    }

    public final void setUseFastForwardActionInCompactView(boolean z) {
        if (this.E != z) {
            this.E = z;
            if (z) {
                this.A = false;
            }
            invalidate();
        }
    }

    public final void setUseRewindActionInCompactView(boolean z) {
        if (this.D != z) {
            this.D = z;
            if (z) {
                this.z = false;
            }
            invalidate();
        }
    }

    public final void setUsePlayPauseActions(boolean z) {
        if (this.F != z) {
            this.F = z;
            invalidate();
        }
    }

    public final void setUseStopAction(boolean z) {
        if (this.G != z) {
            this.G = z;
            invalidate();
        }
    }

    public final void setMediaSessionToken(MediaSessionCompat.Token token) {
        if (!Util.areEqual(this.w, token)) {
            this.w = token;
            invalidate();
        }
    }

    public final void setBadgeIconType(int i) {
        if (this.H != i) {
            switch (i) {
                case 0:
                case 1:
                case 2:
                    this.H = i;
                    invalidate();
                    return;
                default:
                    throw new IllegalArgumentException();
            }
        }
    }

    public final void setColorized(boolean z) {
        if (this.I != z) {
            this.I = z;
            invalidate();
        }
    }

    public final void setDefaults(int i) {
        if (this.J != i) {
            this.J = i;
            invalidate();
        }
    }

    public final void setColor(int i) {
        if (this.K != i) {
            this.K = i;
            invalidate();
        }
    }

    public final void setPriority(int i) {
        if (this.N != i) {
            switch (i) {
                case -2:
                case -1:
                case 0:
                case 1:
                case 2:
                    this.N = i;
                    invalidate();
                    return;
                default:
                    throw new IllegalArgumentException();
            }
        }
    }

    public final void setSmallIcon(@DrawableRes int i) {
        if (this.L != i) {
            this.L = i;
            invalidate();
        }
    }

    public final void setUseChronometer(boolean z) {
        if (this.O != z) {
            this.O = z;
            invalidate();
        }
    }

    public final void setVisibility(int i) {
        if (this.M != i) {
            switch (i) {
                case -1:
                case 0:
                case 1:
                    this.M = i;
                    invalidate();
                    return;
                default:
                    throw new IllegalStateException();
            }
        }
    }

    public final void invalidate() {
        if (this.u) {
            a();
        }
    }

    private void a(Player player, @Nullable Bitmap bitmap) {
        boolean ongoing = getOngoing(player);
        this.q = createNotification(player, this.q, ongoing, bitmap);
        NotificationCompat.Builder builder = this.q;
        boolean z = false;
        if (builder == null) {
            a(false);
            return;
        }
        Notification build = builder.build();
        this.i.notify(this.d, build);
        if (!this.u) {
            this.b.registerReceiver(this.l, this.j);
        }
        NotificationListener notificationListener = this.f;
        if (notificationListener != null) {
            int i = this.d;
            if (ongoing || !this.u) {
                z = true;
            }
            notificationListener.onNotificationPosted(i, build, z);
        }
        this.u = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(boolean z) {
        if (this.u) {
            this.u = false;
            this.h.removeMessages(0);
            this.i.cancel(this.d);
            this.b.unregisterReceiver(this.l);
            NotificationListener notificationListener = this.f;
            if (notificationListener != null) {
                notificationListener.onNotificationCancelled(this.d, z);
            }
        }
    }

    @Nullable
    protected NotificationCompat.Builder createNotification(Player player, @Nullable NotificationCompat.Builder builder, boolean z, @Nullable Bitmap bitmap) {
        NotificationCompat.Action action;
        if (player.getPlaybackState() != 1 || !player.getCurrentTimeline().isEmpty()) {
            List<String> actions = getActions(player);
            ArrayList arrayList = new ArrayList(actions.size());
            for (int i = 0; i < actions.size(); i++) {
                String str = actions.get(i);
                if (this.m.containsKey(str)) {
                    action = this.m.get(str);
                } else {
                    action = this.n.get(str);
                }
                if (action != null) {
                    arrayList.add(action);
                }
            }
            if (builder == null || !arrayList.equals(this.r)) {
                builder = new NotificationCompat.Builder(this.b, this.c);
                this.r = arrayList;
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    builder.addAction((NotificationCompat.Action) arrayList.get(i2));
                }
            }
            NotificationCompat.MediaStyle mediaStyle = new NotificationCompat.MediaStyle();
            MediaSessionCompat.Token token = this.w;
            if (token != null) {
                mediaStyle.setMediaSession(token);
            }
            mediaStyle.setShowActionsInCompactView(getActionIndicesForCompactView(actions, player));
            mediaStyle.setShowCancelButton(!z);
            mediaStyle.setCancelButtonIntent(this.o);
            builder.setStyle(mediaStyle);
            builder.setDeleteIntent(this.o);
            builder.setBadgeIconType(this.H).setOngoing(z).setColor(this.K).setColorized(this.I).setSmallIcon(this.L).setVisibility(this.M).setPriority(this.N).setDefaults(this.J);
            if (Util.SDK_INT < 21 || !this.O || !player.isPlaying() || player.isPlayingAd() || player.isCurrentWindowDynamic() || player.getPlaybackParameters().speed != 1.0f) {
                builder.setShowWhen(false).setUsesChronometer(false);
            } else {
                builder.setWhen(System.currentTimeMillis() - player.getContentPosition()).setShowWhen(true).setUsesChronometer(true);
            }
            builder.setContentTitle(this.e.getCurrentContentTitle(player));
            builder.setContentText(this.e.getCurrentContentText(player));
            builder.setSubText(this.e.getCurrentSubText(player));
            if (bitmap == null) {
                MediaDescriptionAdapter mediaDescriptionAdapter = this.e;
                int i3 = this.v + 1;
                this.v = i3;
                bitmap = mediaDescriptionAdapter.getCurrentLargeIcon(player, new BitmapCallback(i3));
            }
            a(builder, bitmap);
            builder.setContentIntent(this.e.createCurrentContentIntent(player));
            String str2 = this.P;
            if (str2 != null) {
                builder.setGroup(str2);
            }
            builder.setOnlyAlertOnce(true);
            return builder;
        }
        this.r = null;
        return null;
    }

    protected List<String> getActions(Player player) {
        boolean isCommandAvailable = player.isCommandAvailable(6);
        boolean z = true;
        boolean z2 = player.isCommandAvailable(10) && this.t.isRewindEnabled();
        if (!player.isCommandAvailable(11) || !this.t.isFastForwardEnabled()) {
            z = false;
        }
        boolean isCommandAvailable2 = player.isCommandAvailable(8);
        ArrayList arrayList = new ArrayList();
        if (this.x && isCommandAvailable) {
            arrayList.add(ACTION_PREVIOUS);
        }
        if (this.B && z2) {
            arrayList.add(ACTION_REWIND);
        }
        if (this.F) {
            if (a(player)) {
                arrayList.add(ACTION_PAUSE);
            } else {
                arrayList.add(ACTION_PLAY);
            }
        }
        if (this.C && z) {
            arrayList.add(ACTION_FAST_FORWARD);
        }
        if (this.y && isCommandAvailable2) {
            arrayList.add(ACTION_NEXT);
        }
        CustomActionReceiver customActionReceiver = this.g;
        if (customActionReceiver != null) {
            arrayList.addAll(customActionReceiver.getCustomActions(player));
        }
        if (this.G) {
            arrayList.add(ACTION_STOP);
        }
        return arrayList;
    }

    protected int[] getActionIndicesForCompactView(List<String> list, Player player) {
        int i;
        int i2;
        int i3;
        int indexOf = list.indexOf(ACTION_PAUSE);
        int indexOf2 = list.indexOf(ACTION_PLAY);
        if (this.z) {
            i = list.indexOf(ACTION_PREVIOUS);
        } else {
            i = this.D ? list.indexOf(ACTION_REWIND) : -1;
        }
        if (this.A) {
            i2 = list.indexOf(ACTION_NEXT);
        } else {
            i2 = this.E ? list.indexOf(ACTION_FAST_FORWARD) : -1;
        }
        int[] iArr = new int[3];
        int i4 = 0;
        if (i != -1) {
            iArr[0] = i;
            i4 = 1;
        }
        boolean a2 = a(player);
        if (indexOf != -1 && a2) {
            i3 = i4 + 1;
            iArr[i4] = indexOf;
        } else if (indexOf2 == -1 || a2) {
            i3 = i4;
        } else {
            i3 = i4 + 1;
            iArr[i4] = indexOf2;
        }
        if (i2 != -1) {
            i3++;
            iArr[i3] = i2;
        }
        return Arrays.copyOf(iArr, i3);
    }

    protected boolean getOngoing(Player player) {
        int playbackState = player.getPlaybackState();
        return (playbackState == 2 || playbackState == 3) && player.getPlayWhenReady();
    }

    private boolean a(Player player) {
        return (player.getPlaybackState() == 4 || player.getPlaybackState() == 1 || !player.getPlayWhenReady()) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        if (!this.h.hasMessages(0)) {
            this.h.sendEmptyMessage(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Bitmap bitmap, int i) {
        this.h.obtainMessage(1, i, -1, bitmap).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(Message message) {
        switch (message.what) {
            case 0:
                Player player = this.s;
                if (player == null) {
                    return true;
                }
                a(player, (Bitmap) null);
                return true;
            case 1:
                if (this.s == null || !this.u || this.v != message.arg1) {
                    return true;
                }
                a(this.s, (Bitmap) message.obj);
                return true;
            default:
                return false;
        }
    }

    private static Map<String, NotificationCompat.Action> a(Context context, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        HashMap hashMap = new HashMap();
        hashMap.put(ACTION_PLAY, new NotificationCompat.Action(i2, context.getString(R.string.exo_controls_play_description), a(ACTION_PLAY, context, i)));
        hashMap.put(ACTION_PAUSE, new NotificationCompat.Action(i3, context.getString(R.string.exo_controls_pause_description), a(ACTION_PAUSE, context, i)));
        hashMap.put(ACTION_STOP, new NotificationCompat.Action(i4, context.getString(R.string.exo_controls_stop_description), a(ACTION_STOP, context, i)));
        hashMap.put(ACTION_REWIND, new NotificationCompat.Action(i5, context.getString(R.string.exo_controls_rewind_description), a(ACTION_REWIND, context, i)));
        hashMap.put(ACTION_FAST_FORWARD, new NotificationCompat.Action(i6, context.getString(R.string.exo_controls_fastforward_description), a(ACTION_FAST_FORWARD, context, i)));
        hashMap.put(ACTION_PREVIOUS, new NotificationCompat.Action(i7, context.getString(R.string.exo_controls_previous_description), a(ACTION_PREVIOUS, context, i)));
        hashMap.put(ACTION_NEXT, new NotificationCompat.Action(i8, context.getString(R.string.exo_controls_next_description), a(ACTION_NEXT, context, i)));
        return hashMap;
    }

    private static PendingIntent a(String str, Context context, int i) {
        Intent intent = new Intent(str).setPackage(context.getPackageName());
        intent.putExtra(EXTRA_INSTANCE_ID, i);
        return PendingIntent.getBroadcast(context, i, intent, Util.SDK_INT >= 23 ? 201326592 : 134217728);
    }

    private static void a(NotificationCompat.Builder builder, @Nullable Bitmap bitmap) {
        builder.setLargeIcon(bitmap);
    }

    /* loaded from: classes2.dex */
    private class b implements Player.Listener {
        private b() {
        }

        @Override // com.google.android.exoplayer2.Player.Listener, com.google.android.exoplayer2.Player.EventListener
        public void onEvents(Player player, Player.Events events) {
            if (events.containsAny(5, 6, 8, 0, 13, 12, 9, 10, 15)) {
                PlayerNotificationManager.this.a();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class a extends BroadcastReceiver {
        private a() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Player player = PlayerNotificationManager.this.s;
            if (player != null && PlayerNotificationManager.this.u && intent.getIntExtra(PlayerNotificationManager.EXTRA_INSTANCE_ID, PlayerNotificationManager.this.p) == PlayerNotificationManager.this.p) {
                String action = intent.getAction();
                if (PlayerNotificationManager.ACTION_PLAY.equals(action)) {
                    if (player.getPlaybackState() == 1) {
                        PlayerNotificationManager.this.t.dispatchPrepare(player);
                    } else if (player.getPlaybackState() == 4) {
                        PlayerNotificationManager.this.t.dispatchSeekTo(player, player.getCurrentWindowIndex(), C.TIME_UNSET);
                    }
                    PlayerNotificationManager.this.t.dispatchSetPlayWhenReady(player, true);
                } else if (PlayerNotificationManager.ACTION_PAUSE.equals(action)) {
                    PlayerNotificationManager.this.t.dispatchSetPlayWhenReady(player, false);
                } else if (PlayerNotificationManager.ACTION_PREVIOUS.equals(action)) {
                    PlayerNotificationManager.this.t.dispatchPrevious(player);
                } else if (PlayerNotificationManager.ACTION_REWIND.equals(action)) {
                    PlayerNotificationManager.this.t.dispatchRewind(player);
                } else if (PlayerNotificationManager.ACTION_FAST_FORWARD.equals(action)) {
                    PlayerNotificationManager.this.t.dispatchFastForward(player);
                } else if (PlayerNotificationManager.ACTION_NEXT.equals(action)) {
                    PlayerNotificationManager.this.t.dispatchNext(player);
                } else if (PlayerNotificationManager.ACTION_STOP.equals(action)) {
                    PlayerNotificationManager.this.t.dispatchStop(player, true);
                } else if ("com.google.android.exoplayer.dismiss".equals(action)) {
                    PlayerNotificationManager.this.a(true);
                } else if (action != null && PlayerNotificationManager.this.g != null && PlayerNotificationManager.this.n.containsKey(action)) {
                    PlayerNotificationManager.this.g.onCustomAction(player, action, intent);
                }
            }
        }
    }
}
