package com.xiaomi.micolauncher.common.ubus.handlers;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import androidx.annotation.VisibleForTesting;
import com.google.gson.annotations.SerializedName;
import com.xiaomi.mico.base.utils.ScreenUtil;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.event.AlarmTtsEvent;
import com.xiaomi.micolauncher.common.player.MultiAudioPlayer;
import com.xiaomi.micolauncher.common.player.TextPlayer;
import com.xiaomi.micolauncher.common.player.base.BasePlayer;
import com.xiaomi.micolauncher.common.player.policy.AudioSource;
import com.xiaomi.micolauncher.common.push.MicoNotification;
import com.xiaomi.micolauncher.common.ubus.handlers.AbstractGsonUbusHandler;
import com.xiaomi.onetrack.a.a;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class MicoMessageHandler extends AbstractGsonUbusHandler {
    private static final String CREATE = "create";
    private static final String PATH = "notify";
    private static final String TAG = "MicoMessageHandler";
    private static final boolean TEST_NOTIFICATION = false;
    private CopyOnWriteArrayList<MsgEntity> earthquakeAlarmQueue;
    private ResourceTextPlayer resourceTextPlayer = null;

    @VisibleForTesting
    /* loaded from: classes3.dex */
    public static class MsgEntity {
        @SerializedName("expire")
        public long expireTime;
        @SerializedName("id")
        public String id;
        @SerializedName(a.d)
        public int level;
        @SerializedName("text")
        public String text;
        @SerializedName("title")
        public String title;
    }

    @Override // com.xiaomi.micolauncher.common.ubus.handlers.AbstractGsonUbusHandler, com.xiaomi.micolauncher.common.ubus.UBus.UbusHandler
    public boolean canHandle(Context context, String str, String str2) {
        return PATH.equals(str);
    }

    @Override // com.xiaomi.micolauncher.common.ubus.handlers.AbstractGsonUbusHandler
    public Object handleProto(final Context context, String str, String str2, String str3, AbstractGsonUbusHandler.HandleResultCodeOnError handleResultCodeOnError) {
        if (!CREATE.equals(str2)) {
            L.ubus.w("Unexpected message %s", str2);
            return null;
        } else if (ContainerUtil.isEmpty(str3)) {
            handleResultCodeOnError.errorCode = -3;
            return null;
        } else {
            final MsgEntity msgEntity = (MsgEntity) Gsons.getGson().fromJson(str3, (Class<Object>) MsgEntity.class);
            if (msgEntity == null || ContainerUtil.isEmpty(msgEntity.text)) {
                handleResultCodeOnError.errorCode = -3;
                return null;
            }
            ScreenUtil.turnScreenOn(context);
            ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.common.ubus.handlers.-$$Lambda$MicoMessageHandler$YAyaG2G8owCvOAAGbZjmb4_4uH4
                @Override // java.lang.Runnable
                public final void run() {
                    MicoMessageHandler.lambda$handleProto$0(MicoMessageHandler.this, msgEntity, context);
                }
            });
            return null;
        }
    }

    public static /* synthetic */ void lambda$handleProto$0(MicoMessageHandler micoMessageHandler, MsgEntity msgEntity, Context context) {
        ResourceTextPlayer resourceTextPlayer = micoMessageHandler.resourceTextPlayer;
        if (resourceTextPlayer == null || !resourceTextPlayer.isPlaying()) {
            if (!EventBusRegistry.getEventBus().isRegistered(micoMessageHandler)) {
                EventBusRegistry.getEventBus().register(micoMessageHandler);
            }
            micoMessageHandler.resourceTextPlayer = new ResourceTextPlayer(context, msgEntity);
            micoMessageHandler.resourceTextPlayer.setPlaying(true);
            micoMessageHandler.resourceTextPlayer.start(context);
        } else if (!micoMessageHandler.resourceTextPlayer.isSameText(msgEntity.text)) {
            if (micoMessageHandler.earthquakeAlarmQueue == null) {
                micoMessageHandler.earthquakeAlarmQueue = new CopyOnWriteArrayList<>();
            }
            micoMessageHandler.earthquakeAlarmQueue.add(msgEntity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class ResourceTextPlayer {
        private static final String BROWSE_MICO_MESSAGE_NOTIFICATION = "com.xiaomi.micolauncher.BROWSE_MICO_MESSAGE_NOTIFICATION";
        private static final int NOTIFICATION_ID = MicoNotification.generateUniqueId();
        static final int NOTIFICATION_TIMEOUT_AFTER = (int) TimeUnit.MINUTES.toSeconds(1);
        private static final int REQUEST_ID = 20190808;
        private static final int STAGE_RING = 0;
        private Context context;
        private volatile boolean isPlaying;
        private final MsgEntity msg;
        private MultiAudioPlayer preRingtonePlayer = null;

        ResourceTextPlayer(Context context, MsgEntity msgEntity) {
            this.msg = msgEntity;
            this.context = context;
        }

        public Context getContext() {
            return this.context;
        }

        private void showNotification(Context context) {
            MicoNotification.createRemoteViews(context, this.msg.text);
            PendingIntent pendingIntent = getPendingIntent(context);
            MicoNotification.Builder builder = new MicoNotification.Builder(context);
            builder.setNotificationId(NOTIFICATION_ID).setPendingIntent(pendingIntent).setText(this.msg.text).setTitle(this.msg.title).setTimeoutAfter(NOTIFICATION_TIMEOUT_AFTER);
            builder.build().show(BROWSE_MICO_MESSAGE_NOTIFICATION, new BrowseMicoMsgBroadcastReceiver(context, this.msg.text));
        }

        private PendingIntent getPendingIntent(Context context) {
            return PendingIntent.getBroadcast(context, REQUEST_ID, new Intent().setAction(BROWSE_MICO_MESSAGE_NOTIFICATION), 268435456);
        }

        boolean isSameText(String str) {
            return TextUtils.equals(str, this.msg.text);
        }

        void setPlaying(boolean z) {
            this.isPlaying = z;
        }

        public boolean isPlaying() {
            return this.isPlaying;
        }

        public void start(Context context) {
            L.ubus.d("start message fake player");
            this.preRingtonePlayer = new MultiAudioPlayer(BasePlayer.StreamType.STREAM_TYPE_ALARM, true);
            this.preRingtonePlayer.playRawFile(R.raw.earthquake_alarm, context);
            TextPlayer.play(context, this.msg.text, AudioSource.AUDIO_SOURCE_ALARM_EARTHQUAKE);
            this.isPlaying = true;
        }
    }

    /* loaded from: classes3.dex */
    private static class BrowseMicoMsgBroadcastReceiver extends BroadcastReceiver {
        private Context context;
        private String ttsText;

        public BrowseMicoMsgBroadcastReceiver(Context context, String str) {
            this.context = context;
            this.ttsText = str;
            L.base.d("browse mico message %s", context);
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            L.base.d("receive browse mico message %s", context);
            if (TextUtils.equals(intent.getAction(), "com.xiaomi.micolauncher.BROWSE_MICO_MESSAGE_NOTIFICATION")) {
                TextPlayer.play(context, this.ttsText, AudioSource.AUDIO_SOURCE_UI);
                context.unregisterReceiver(this);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAlarmTtsEvent(AlarmTtsEvent alarmTtsEvent) {
        if (alarmTtsEvent.getSource().equals(AudioSource.AUDIO_SOURCE_ALARM_EARTHQUAKE)) {
            L.alarm.i("MicoMessageHandler onAlarmTtsEvent %s", Boolean.valueOf(alarmTtsEvent.isStart()));
            if (!alarmTtsEvent.isStart() && this.resourceTextPlayer != null) {
                CopyOnWriteArrayList<MsgEntity> copyOnWriteArrayList = this.earthquakeAlarmQueue;
                if (copyOnWriteArrayList == null || copyOnWriteArrayList.isEmpty()) {
                    this.resourceTextPlayer.setPlaying(false);
                    return;
                }
                Context context = this.resourceTextPlayer.getContext();
                this.resourceTextPlayer = new ResourceTextPlayer(context, this.earthquakeAlarmQueue.remove(0));
                this.resourceTextPlayer.setPlaying(true);
                this.resourceTextPlayer.start(context);
            }
        }
    }
}
