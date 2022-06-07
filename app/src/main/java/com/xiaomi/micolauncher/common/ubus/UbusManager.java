package com.xiaomi.micolauncher.common.ubus;

import android.content.Context;
import android.util.ArrayMap;
import android.util.ArraySet;
import androidx.annotation.VisibleForTesting;
import com.xiaomi.micolauncher.UserBehaviorProxy;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.event.PlayByRemoteEvent;
import com.xiaomi.micolauncher.common.ubus.HandlerWrapper;
import com.xiaomi.micolauncher.common.ubus.UBus;
import com.xiaomi.micolauncher.common.ubus.handlers.AlarmHandler;
import com.xiaomi.micolauncher.common.ubus.handlers.ChildModeHandler;
import com.xiaomi.micolauncher.common.ubus.handlers.InternalHandler;
import com.xiaomi.micolauncher.common.ubus.handlers.LocalAlbumHandler;
import com.xiaomi.micolauncher.common.ubus.handlers.LockScreenMode;
import com.xiaomi.micolauncher.common.ubus.handlers.MeshHandler;
import com.xiaomi.micolauncher.common.ubus.handlers.MiBrainUbusHandler;
import com.xiaomi.micolauncher.common.ubus.handlers.MiTvHandler;
import com.xiaomi.micolauncher.common.ubus.handlers.MicoMessageHandler;
import com.xiaomi.micolauncher.common.ubus.handlers.NightMode;
import com.xiaomi.micolauncher.common.ubus.handlers.PnsHelper;
import com.xiaomi.micolauncher.common.ubus.handlers.VideoMonitorHandler;
import com.xiaomi.micolauncher.common.ubus.handlers.VoicePrintRegisterHandler;
import com.xiaomi.micolauncher.common.ubus.handlers.VoipHandler;
import com.xiaomi.micolauncher.common.ubus.handlers.session.SessionLogHandler;
import com.xiaomi.micolauncher.module.main.HomepageHandler;
import com.xiaomi.micolauncher.skills.music.PlayerApi;
import com.xiaomi.micolauncher.skills.voip.model.VoipModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes3.dex */
public class UbusManager {
    private static final UbusManager a = new UbusManager();
    private ArrayMap<String, String> d;
    private List<UBus.UbusHandler> b = new ArrayList();
    private ArraySet<String> e = new ArraySet<>();
    private Set<WhiteMessage> f = new ArraySet();
    private boolean c = true;

    public static UbusManager getInstance() {
        return a;
    }

    private UbusManager() {
        this.e.add(LockScreenMode.PATH);
        this.e.add(NightMode.NIGHT_MODE);
        this.e.add(LocalAlbumHandler.PATH);
        this.e.add(InternalHandler.PATH_OTA);
        this.f.add(new WhiteMessage(VoipHandler.PATH_VOIP, VoipHandler.CUNIQ_REMOTE_CTRL_ENABLE));
        this.f.add(new WhiteMessage(VoipHandler.PATH_VOIP, VoipHandler.CUNIQ_REMOTE_CTRL_DISABLE));
        this.f.add(new WhiteMessage(InternalHandler.SYNC_UNBIND_DEVICE, "POST"));
        ArrayMap arrayMap = new ArrayMap();
        HandlerWrapper.UbusMessage ubusMessage = new HandlerWrapper.UbusMessage(PlayerApi.UbusHandler.PATH_LAYER, PlayerApi.UbusHandler.PLAYER_PLAY_ALBUM_PLAYLIST);
        PlayByRemoteEvent playByRemoteEvent = new PlayByRemoteEvent();
        arrayMap.put(ubusMessage, playByRemoteEvent);
        arrayMap.put(new HandlerWrapper.UbusMessage(PlayerApi.UbusHandler.PATH_LAYER, PlayerApi.UbusHandler.PLAYER_PLAY_OPERATION), playByRemoteEvent);
        arrayMap.put(new HandlerWrapper.UbusMessage(PlayerApi.UbusHandler.PATH_LAYER, PlayerApi.UbusHandler.PLAYER_PLAY_MUSIC), playByRemoteEvent);
        a(new PlayerApi.UbusHandler(), arrayMap);
        a(new InternalHandler());
        a(new SessionLogHandler());
        a(new AlarmHandler());
        a(new VoipHandler());
        a(new MiTvHandler());
        a(new LocalAlbumHandler());
        a(new HomepageHandler());
        a(new PnsHelper());
        a(new NightMode());
        a(new LockScreenMode());
        a(new ChildModeHandler());
        a(new MeshHandler());
        a(new VoicePrintRegisterHandler());
        a(new MiBrainUbusHandler());
        a(new MicoMessageHandler());
        a(new VideoMonitorHandler());
        a();
    }

    private void a() {
        this.d = new ArrayMap<>();
        this.d.put("mediaplayer.player_get_play_status", "");
        this.d.put("upnp-disc.list", "");
        this.d.put("/internal/mibrain_level.GET", "");
    }

    public void enable() {
        this.c = true;
    }

    public void disable() {
        this.c = false;
    }

    private void a(UBus.UbusHandler ubusHandler) {
        this.b.add(ubusHandler);
    }

    private void a(UBus.UbusHandler ubusHandler, Map<HandlerWrapper.UbusMessage, Object> map) {
        this.b.add(new HandlerWrapper(ubusHandler, map));
    }

    public String handle(Context context, String str, String str2, String str3) {
        L.ubus.i("UbusManager handle path=%s,method=%s,message=%s", str, str2, str3);
        String str4 = str + "." + str2;
        if (!this.d.containsKey(str4)) {
            UserBehaviorProxy.setUserBehavior(context, str4);
        }
        if (this.c || (str.equals(InternalHandler.SYNC_UNBIND_DEVICE) && str2.equals("POST"))) {
            for (UBus.UbusHandler ubusHandler : this.b) {
                if (ubusHandler.canHandle(context, str, str2)) {
                    return ubusHandler.handle(context, str, str2, str3);
                }
            }
        }
        if (!this.c && VoipModel.getInstance().isVoipActive() && (!str.equals(PlayerApi.UbusHandler.PATH_LAYER) || str2.equals(PlayerApi.UbusHandler.PLAYER_SET_VOLUME) || str2.equals(PlayerApi.UbusHandler.PLAYER_GET_PLAY_STATUS))) {
            for (UBus.UbusHandler ubusHandler2 : this.b) {
                if (ubusHandler2.canHandle(context, str, str2)) {
                    return ubusHandler2.handle(context, str, str2, str3);
                }
            }
        }
        UBus.Response response = new UBus.Response();
        response.code = UBusError.REMOTE_UBUS_ERROR.getCode();
        return response.toString();
    }

    public boolean canHandleBeforeInit(String str, String str2) {
        return this.e.contains(str) || this.f.contains(new WhiteMessage(str, str2));
    }

    @VisibleForTesting
    public Set<WhiteMessage> getWhiteMessages() {
        return this.f;
    }

    @VisibleForTesting
    /* loaded from: classes3.dex */
    public static class WhiteMessage {
        String a;
        String b;

        public WhiteMessage(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            WhiteMessage whiteMessage = (WhiteMessage) obj;
            return Objects.equals(this.a, whiteMessage.a) && Objects.equals(this.b, whiteMessage.b);
        }

        public int hashCode() {
            return Objects.hash(this.a, this.b);
        }
    }
}
