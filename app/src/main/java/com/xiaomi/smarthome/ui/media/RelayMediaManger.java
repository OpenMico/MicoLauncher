package com.xiaomi.smarthome.ui.media;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.xiaomi.accountsdk.request.SimpleRequestForAccount;
import com.xiaomi.mico.base.utils.MD5;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.media.MicoMediaEvent;
import com.xiaomi.micolauncher.common.media.RelayMediaEvent;
import com.xiaomi.micolauncher.common.media.RelayPlayEvent;
import com.xiaomi.micolauncher.common.utils.BlueToothUtil;
import com.xiaomi.onetrack.a.c;
import com.xiaomi.onetrack.api.b;
import com.xiaomi.smarthome.core.entity.MediaType;
import com.xiaomi.smarthome.core.entity.MicoMediaData;
import com.xiaomi.smarthome.core.miot.MediaControllerEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: RelayMediaManger.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\r\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000f\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020\b2\b\u0010/\u001a\u0004\u0018\u00010\bH\u0002J\u0010\u00100\u001a\u00020-2\u0006\u00101\u001a\u000202H\u0002J\u0010\u00103\u001a\u00020-2\u0006\u00104\u001a\u000202H\u0002J\b\u00105\u001a\u0004\u0018\u00010\bJ\u0014\u00106\u001a\u0004\u0018\u00010\b2\b\u00107\u001a\u0004\u0018\u00010\"H\u0002J\u0014\u00108\u001a\u0004\u0018\u00010\b2\b\u00101\u001a\u0004\u0018\u000102H\u0002J\u001e\u00109\u001a\u0004\u0018\u00010\b2\b\u0010:\u001a\u0004\u0018\u00010;2\b\u0010<\u001a\u0004\u0018\u00010\"H\u0002J\b\u0010=\u001a\u00020>H\u0002J\u0012\u0010?\u001a\u00020>2\b\u0010@\u001a\u0004\u0018\u00010\bH\u0002J\u0010\u0010A\u001a\u00020>2\u0006\u0010B\u001a\u00020CH\u0002J\u000e\u0010D\u001a\u00020>2\u0006\u0010@\u001a\u00020\bJ\u0006\u0010E\u001a\u00020>J\u0010\u0010F\u001a\u00020>2\u0006\u0010B\u001a\u00020GH\u0002J\u0012\u0010H\u001a\u00020>2\b\u0010\u0014\u001a\u0004\u0018\u00010\bH\u0002J\u0012\u0010I\u001a\u00020-2\b\u0010\u0014\u001a\u0004\u0018\u00010\bH\u0002J\b\u0010J\u001a\u00020-H\u0002J\u0010\u0010K\u001a\u00020-2\u0006\u0010B\u001a\u00020CH\u0007J\u0010\u0010L\u001a\u00020-2\u0006\u0010M\u001a\u00020NH\u0007J\u0010\u0010O\u001a\u00020-2\u0006\u0010B\u001a\u00020GH\u0007J\u0016\u0010P\u001a\u00020-2\u0006\u0010@\u001a\u00020\b2\u0006\u0010Q\u001a\u00020#J\b\u0010R\u001a\u00020-H\u0002J\u0010\u0010S\u001a\u00020-2\b\u0010T\u001a\u0004\u0018\u00010\u000fJ\u0006\u0010U\u001a\u00020-J\u0016\u0010V\u001a\u00020-2\f\u0010W\u001a\b\u0012\u0004\u0012\u00020\b0\u000eH\u0002J\u0012\u0010X\u001a\u00020-2\b\u0010\u0014\u001a\u0004\u0018\u00010\bH\u0002J\b\u0010Y\u001a\u00020-H\u0002J\u0010\u0010Z\u001a\u00020-2\b\u0010T\u001a\u0004\u0018\u00010\u000fJ\u000e\u0010[\u001a\u00020>2\u0006\u0010\\\u001a\u00020GR\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0014\u001a\u0004\u0018\u00010\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\n\"\u0004\b\u0016\u0010\fR(\u0010\u0018\u001a\u0004\u0018\u00010\b2\b\u0010\u0017\u001a\u0004\u0018\u00010\b@BX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\n\"\u0004\b\u001a\u0010\fR\u001a\u0010\u001b\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR*\u0010 \u001a\u001e\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020#0!j\u000e\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020#`$X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010%\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\u001d\"\u0004\b'\u0010\u001fR*\u0010)\u001a\b\u0012\u0004\u0012\u00020\b0\u000e2\f\u0010(\u001a\b\u0012\u0004\u0012\u00020\b0\u000e@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b*\u0010+¨\u0006]"}, d2 = {"Lcom/xiaomi/smarthome/ui/media/RelayMediaManger;", "", "()V", "MESSAGE_CALL_PLAY_DELAY", "", "MESSAGE_CALL_RELAY_DELAY", "PLAYSTATE_PLAY", "lastMiplayMediaData", "Lcom/xiaomi/smarthome/core/entity/MicoMediaData;", "getLastMiplayMediaData", "()Lcom/xiaomi/smarthome/core/entity/MicoMediaData;", "setLastMiplayMediaData", "(Lcom/xiaomi/smarthome/core/entity/MicoMediaData;)V", "listeners", "", "Lcom/xiaomi/smarthome/ui/media/RelayMediaListener;", "mainHandler", "Landroid/os/Handler;", "messageDelayTime", "", "micoMediaData", "getMicoMediaData", "setMicoMediaData", b.p, "playMediaData", "getPlayMediaData", "setPlayMediaData", "playState", "getPlayState", "()I", "setPlayState", "(I)V", "relayCallbacks", "Ljava/util/HashMap;", "", "Lcom/xiaomi/smarthome/ui/media/RelayPlayCallback;", "Lkotlin/collections/HashMap;", "relayListSize", "getRelayListSize", "setRelayListSize", "<set-?>", "relayMediaDataList", "getRelayMediaDataList", "()Ljava/util/List;", "changeDataInSamePosition", "", "olderData", "newData", "deleteItemByMediaType", "type", "Lcom/xiaomi/smarthome/core/entity/MediaType;", "deletePlayingItemDifferent", "mediaType", "getFirstData", "getRelayMediaByDeviceId", SimpleRequestForAccount.COOKIE_NAME_DEVICE_ID, "getRelayMediaByMediaType", "getRelayMediaByTitleAndSourceName", "title", "", "source", "hasPlayingData", "", "isEmptyData", "data", "isEmptyMedia", "event", "Lcom/xiaomi/micolauncher/common/media/MicoMediaEvent;", "isLoading", "isLocalPlaying", "isSamePlayDataAsLastMiplay", "Lcom/xiaomi/micolauncher/common/media/RelayMediaEvent;", "isSameTypeInFirstItem", "notifyNewDataChange", "notifyRelayDataChange", "onMediaChange", "onPlayStateChanged", "musicState", "Lcom/xiaomi/smarthome/core/miot/MediaControllerEvent$MusicState;", "onRelayMediaChange", "playRelay", "relayPlayCallback", "reOrderRelayList", "registerListener", c.a, "removeRelayFromBlueToothDevice", "resortArrayListByPlayState", "list", "sendDelayMessage", "sendFirstDataInList", "unRegisterListener", "verifyRelayFromBlueToothDevice", "relayMediaEvent", "smarthome_release"}, k = 1, mv = {1, 4, 2})
/* loaded from: classes4.dex */
public final class RelayMediaManger {
    @NotNull
    public static final RelayMediaManger INSTANCE;
    public static final int MESSAGE_CALL_PLAY_DELAY = 1;
    public static final int MESSAGE_CALL_RELAY_DELAY = 2;
    private static int c;
    private static int d;
    private static final Handler f;
    @Nullable
    private static MicoMediaData h;
    @Nullable
    private static MicoMediaData i;
    private static final List<RelayMediaListener> a = new ArrayList();
    private static final long b = TimeUnit.SECONDS.toMillis(30);
    private static final HashMap<String, RelayPlayCallback> e = new HashMap<>();
    @Nullable
    private static MicoMediaData g = new MicoMediaData(MediaType.NONE, "", "", "", null, null, null, 0, null, 496, null);
    @NotNull
    private static List<MicoMediaData> j = new ArrayList();

    static {
        RelayMediaManger relayMediaManger = new RelayMediaManger();
        INSTANCE = relayMediaManger;
        final Looper mainLooper = Looper.getMainLooper();
        f = new Handler(mainLooper) { // from class: com.xiaomi.smarthome.ui.media.RelayMediaManger$mainHandler$1
            @Override // android.os.Handler
            public void handleMessage(@Nullable Message message) {
                MicoMediaData a2;
                MicoMediaData a3;
                HashMap hashMap;
                HashMap hashMap2;
                Integer valueOf = message != null ? Integer.valueOf(message.what) : null;
                if (valueOf != null && valueOf.intValue() == 1) {
                    Object obj = message.obj;
                    if (obj instanceof MicoMediaData) {
                        MicoMediaData micoMediaData = (MicoMediaData) obj;
                        a3 = RelayMediaManger.INSTANCE.a(micoMediaData.getDeviceId());
                        if (!(a3 == null || a3.getDeviceId() == null)) {
                            RelayMediaManger relayMediaManger2 = RelayMediaManger.INSTANCE;
                            hashMap2 = RelayMediaManger.e;
                            RelayPlayCallback relayPlayCallback = (RelayPlayCallback) hashMap2.get(a3.getDeviceId());
                            if (relayPlayCallback != null) {
                                relayPlayCallback.onError(micoMediaData);
                            }
                        }
                        RelayMediaManger relayMediaManger3 = RelayMediaManger.INSTANCE;
                        hashMap = RelayMediaManger.e;
                        HashMap hashMap3 = hashMap;
                        String deviceId = micoMediaData.getDeviceId();
                        if (hashMap3 != null) {
                            TypeIntrinsics.asMutableMap(hashMap3).remove(deviceId);
                            RelayMediaManger.INSTANCE.d();
                            return;
                        }
                        throw new NullPointerException("null cannot be cast to non-null type kotlin.collections.MutableMap<K, V>");
                    }
                } else if (valueOf != null && valueOf.intValue() == 2) {
                    Object obj2 = message.obj;
                    if (obj2 instanceof String) {
                        a2 = RelayMediaManger.INSTANCE.a((String) obj2);
                        L.relay.d("relay msg get: %d", Integer.valueOf(RelayMediaManger.INSTANCE.getRelayMediaDataList().size()));
                        List<MicoMediaData> relayMediaDataList = RelayMediaManger.INSTANCE.getRelayMediaDataList();
                        if (relayMediaDataList != null) {
                            TypeIntrinsics.asMutableCollection(relayMediaDataList).remove(a2);
                            int indexOf = CollectionsKt.indexOf((List<? extends MicoMediaData>) RelayMediaManger.INSTANCE.getRelayMediaDataList(), RelayMediaManger.INSTANCE.getPlayMediaData());
                            if (a2 != null) {
                                int i2 = indexOf + 1;
                                if (i2 < RelayMediaManger.INSTANCE.getRelayMediaDataList().size()) {
                                    RelayMediaManger.INSTANCE.getRelayMediaDataList().add(i2, a2);
                                } else {
                                    RelayMediaManger.INSTANCE.getRelayMediaDataList().add(a2);
                                }
                                RelayMediaManger.INSTANCE.b();
                                RelayMediaManger.INSTANCE.a();
                                RelayMediaManger.INSTANCE.d();
                            }
                            L.relay.d("relay msg end: %d", Integer.valueOf(RelayMediaManger.INSTANCE.getRelayMediaDataList().size()));
                            return;
                        }
                        throw new NullPointerException("null cannot be cast to non-null type kotlin.collections.MutableCollection<T>");
                    }
                }
            }
        };
        EventBusRegistry.getEventBus().register(relayMediaManger);
    }

    private RelayMediaManger() {
    }

    public final int getRelayListSize() {
        return c;
    }

    public final void setRelayListSize(int i2) {
        c = i2;
    }

    public final int getPlayState() {
        return d;
    }

    public final void setPlayState(int i2) {
        d = i2;
    }

    @Nullable
    public final MicoMediaData getPlayMediaData() {
        return g;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void a(MicoMediaData micoMediaData) {
        if (micoMediaData == null) {
            g = new MicoMediaData(MediaType.NONE, "", "", "", null, null, null, 0, null, 496, null);
        } else {
            g = micoMediaData;
        }
    }

    @Nullable
    public final MicoMediaData getLastMiplayMediaData() {
        return h;
    }

    public final void setLastMiplayMediaData(@Nullable MicoMediaData micoMediaData) {
        h = micoMediaData;
    }

    @Nullable
    public final MicoMediaData getMicoMediaData() {
        return i;
    }

    public final void setMicoMediaData(@Nullable MicoMediaData micoMediaData) {
        i = micoMediaData;
    }

    @NotNull
    public final List<MicoMediaData> getRelayMediaDataList() {
        return j;
    }

    public final void registerListener(@Nullable RelayMediaListener relayMediaListener) {
        if (relayMediaListener != null) {
            a.add(relayMediaListener);
        }
    }

    public final void unRegisterListener(@Nullable RelayMediaListener relayMediaListener) {
        if (relayMediaListener != null) {
            a.remove(relayMediaListener);
        }
    }

    public final boolean isLoading(@NotNull MicoMediaData data) {
        Intrinsics.checkNotNullParameter(data, "data");
        HashMap<String, RelayPlayCallback> hashMap = e;
        String deviceId = data.getDeviceId();
        if (hashMap != null) {
            return hashMap.containsKey(deviceId);
        }
        throw new NullPointerException("null cannot be cast to non-null type kotlin.collections.Map<K, *>");
    }

    public final void playRelay(@NotNull MicoMediaData data, @NotNull RelayPlayCallback relayPlayCallback) {
        Intrinsics.checkNotNullParameter(data, "data");
        Intrinsics.checkNotNullParameter(relayPlayCallback, "relayPlayCallback");
        f.sendMessageDelayed(f.obtainMessage(1, data), b);
        String deviceId = data.getDeviceId();
        if (deviceId != null) {
            e.put(deviceId, relayPlayCallback);
            EventBusRegistry.getEventBus().post(new RelayPlayEvent(deviceId));
        }
    }

    private final boolean b(MicoMediaData micoMediaData) {
        return micoMediaData == null || TextUtils.isEmpty(micoMediaData.getTitle()) || micoMediaData.getMediaType() == MediaType.NONE;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onMediaChange(@NotNull MicoMediaEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        L.relay.d(event.toString());
        MicoMediaData micoMediaData = g;
        MediaType mediaType = null;
        if ((micoMediaData != null ? micoMediaData.getMediaType() : null) == MediaType.MIPLAY) {
            h = micoMediaData;
        }
        a(new MicoMediaData(event));
        removeRelayFromBlueToothDevice();
        boolean c2 = c(g);
        MicoMediaData micoMediaData2 = g;
        if ((micoMediaData2 != null ? micoMediaData2.getMediaType() : null) == MediaType.LOCAL) {
            i = g;
        }
        MicoMediaData micoMediaData3 = g;
        if (micoMediaData3 != null) {
            INSTANCE.a(micoMediaData3.getMediaType());
        }
        if (a(event)) {
            L.relay.d("send first %d", Integer.valueOf(j.size()));
            MicoMediaData micoMediaData4 = g;
            if (micoMediaData4 != null) {
                INSTANCE.b(micoMediaData4.getMediaType());
            }
            a();
        } else {
            if (!b(micoMediaData) && f.hasMessages(2)) {
                MediaType mediaType2 = micoMediaData != null ? micoMediaData.getMediaType() : null;
                MicoMediaData micoMediaData5 = g;
                if (mediaType2 == (micoMediaData5 != null ? micoMediaData5.getMediaType() : null) && !c2) {
                    MicoMediaData micoMediaData6 = g;
                    if (micoMediaData6 != null) {
                        mediaType = micoMediaData6.getMediaType();
                    }
                    MicoMediaData c3 = c(mediaType);
                    if (c3 != null) {
                        INSTANCE.a(c3, g);
                    }
                    L.relay.d("update item: %d", Integer.valueOf(j.size()));
                }
            }
            MicoMediaData micoMediaData7 = g;
            if (micoMediaData7 != null) {
                INSTANCE.b(micoMediaData7.getMediaType());
            }
            MicoMediaData micoMediaData8 = g;
            if (micoMediaData8 != null) {
                j.add(0, micoMediaData8);
            }
            L.relay.d("update item");
            d(g);
        }
        d();
    }

    private final boolean c(MicoMediaData micoMediaData) {
        if (j.size() == 0) {
            return false;
        }
        return j.get(0).getMediaType() == (micoMediaData != null ? micoMediaData.getMediaType() : null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void a() {
        if (j.size() > 0) {
            d(j.get(0));
        } else {
            d(g);
        }
    }

    private final boolean a(MicoMediaEvent micoMediaEvent) {
        return Intrinsics.areEqual(micoMediaEvent.getMediaType(), MediaType.NONE.name()) || TextUtils.isEmpty(micoMediaEvent.getTitle());
    }

    private final void a(MediaType mediaType) {
        ArrayList arrayList = new ArrayList();
        for (MicoMediaData micoMediaData : j) {
            if (!(micoMediaData.getMediaType() == mediaType || micoMediaData.getMediaType() == MediaType.NONE || micoMediaData.getMediaType() == MediaType.RELAY || micoMediaData.getMediaType() == MediaType.LOCAL)) {
                arrayList.add(micoMediaData);
            }
        }
        j.removeAll(arrayList);
    }

    private final void b(MediaType mediaType) {
        ArrayList arrayList = new ArrayList();
        for (MicoMediaData micoMediaData : j) {
            if (micoMediaData.getMediaType() == mediaType) {
                arrayList.add(micoMediaData);
            }
        }
        j.removeAll(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void b() {
        int indexOf = CollectionsKt.indexOf((List<? extends MicoMediaData>) j, i);
        if (indexOf == -1) {
            indexOf = CollectionsKt.indexOf((List<? extends MicoMediaData>) j, g);
        }
        int i2 = indexOf + 1;
        if (i2 < j.size()) {
            List<MicoMediaData> list = j;
            a(list.subList(i2, list.size()));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: RelayMediaManger.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u00032\u000e\u0010\u0005\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0006"}, d2 = {"<anonymous>", "", "item1", "Lcom/xiaomi/smarthome/core/entity/MicoMediaData;", "kotlin.jvm.PlatformType", "item2", "compare"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class a<T> implements Comparator<MicoMediaData> {
        public static final a a = new a();

        a() {
        }

        /* renamed from: a */
        public final int compare(MicoMediaData micoMediaData, MicoMediaData micoMediaData2) {
            if (micoMediaData.getMediaType() != MediaType.RELAY || micoMediaData2.getMediaType() != MediaType.RELAY) {
                return 0;
            }
            if (micoMediaData.getPlayState() != 2 || micoMediaData2.getPlayState() == 2) {
                return (micoMediaData.getPlayState() == 2 || micoMediaData2.getPlayState() != 2) ? 0 : 1;
            }
            return -1;
        }
    }

    private final void a(List<MicoMediaData> list) {
        Collections.sort(list, a.a);
    }

    private final boolean a(RelayMediaEvent relayMediaEvent) {
        if (h == null) {
            return false;
        }
        MicoMediaData micoMediaData = g;
        CharSequence charSequence = null;
        if ((micoMediaData != null ? micoMediaData.getMediaType() : null) != MediaType.MIPLAY) {
            MicoMediaData micoMediaData2 = g;
            if ((micoMediaData2 != null ? micoMediaData2.getMediaType() : null) != MediaType.NONE) {
                MicoMediaData micoMediaData3 = h;
                if (!TextUtils.equals(micoMediaData3 != null ? micoMediaData3.getTitle() : null, relayMediaEvent.getTitle())) {
                    return false;
                }
                MicoMediaData micoMediaData4 = h;
                if (!TextUtils.equals(micoMediaData4 != null ? micoMediaData4.getSource() : null, relayMediaEvent.getSource())) {
                    return false;
                }
                MicoMediaData micoMediaData5 = h;
                if (micoMediaData5 != null) {
                    charSequence = micoMediaData5.getArtist();
                }
                return TextUtils.equals(charSequence, relayMediaEvent.getArtist());
            }
        }
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onRelayMediaChange(@NotNull RelayMediaEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        L.relay.d("relay: %s", event);
        if (verifyRelayFromBlueToothDevice(event)) {
            L.relay.d("verifyRelayFromBlueToothDevice");
            return;
        }
        MicoMediaData a2 = a(event.getDeviceId());
        if (a2 == null) {
            if (!TextUtils.isEmpty(event.getTitle())) {
                MicoMediaData micoMediaData = new MicoMediaData(event);
                if (a(event)) {
                    j.add(micoMediaData);
                    h = null;
                    b();
                } else {
                    j.add(0, micoMediaData);
                }
                if (j.indexOf(micoMediaData) == 0) {
                    d(micoMediaData);
                    e(micoMediaData);
                }
                d();
                L.relay.d("new relay data: %d", Integer.valueOf(j.size()));
            }
        } else if (TextUtils.isEmpty(event.getTitle())) {
            e.remove(event.getDeviceId());
            if (j.size() > 0) {
                j.remove(a2);
                f.removeMessages(2, event.getDeviceId());
                if (TextUtils.equals(j.get(0).getDeviceId(), event.getDeviceId())) {
                    if (j.size() > 0) {
                        d(j.get(0));
                        L.relay.d("delete item: %d", Integer.valueOf(j.size()));
                    } else {
                        d(g);
                        L.relay.d("delete item left empty");
                    }
                }
                d();
            }
        } else {
            a(a2, new MicoMediaData(event));
            d();
        }
    }

    private final void a(MicoMediaData micoMediaData, MicoMediaData micoMediaData2) {
        if (micoMediaData2 != null) {
            int indexOf = j.indexOf(micoMediaData);
            j.remove(micoMediaData);
            if (indexOf >= 0) {
                j.add(indexOf, micoMediaData2);
            } else {
                j.add(micoMediaData2);
            }
            INSTANCE.b();
            if (j.indexOf(micoMediaData2) == 0) {
                INSTANCE.d(micoMediaData2);
            }
            L.relay.d("update relay item: %d", Integer.valueOf(indexOf));
        }
    }

    public final boolean isLocalPlaying() {
        MicoMediaData micoMediaData = g;
        return (micoMediaData != null ? micoMediaData.getMediaType() : null) == MediaType.LOCAL;
    }

    private final void d(MicoMediaData micoMediaData) {
        for (RelayMediaListener relayMediaListener : a) {
            relayMediaListener.onFirstDataChanged(micoMediaData);
        }
    }

    private final void e(MicoMediaData micoMediaData) {
        if (micoMediaData != null && micoMediaData.getMediaType() == MediaType.RELAY && INSTANCE.c()) {
            f.sendMessageDelayed(Message.obtain(f, 2, micoMediaData.getDeviceId()), TimeUnit.MINUTES.toMillis(5L));
            L.relay.d("notifyNewDataChange");
        }
    }

    private final boolean c() {
        MicoMediaData micoMediaData = g;
        if (micoMediaData == null) {
            return false;
        }
        MediaType mediaType = null;
        if ((micoMediaData != null ? micoMediaData.getMediaType() : null) == MediaType.NONE) {
            return false;
        }
        MicoMediaData micoMediaData2 = g;
        if (micoMediaData2 != null) {
            mediaType = micoMediaData2.getMediaType();
        }
        return mediaType != MediaType.RELAY;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void d() {
        for (RelayMediaListener relayMediaListener : a) {
            relayMediaListener.onDataChanged(j);
        }
        if (j.size() != c) {
            c = j.size();
            for (RelayMediaListener relayMediaListener2 : a) {
                relayMediaListener2.onDataSizeChange(c);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final MicoMediaData a(String str) {
        if (str == null) {
            return null;
        }
        for (MicoMediaData micoMediaData : j) {
            if (TextUtils.equals(micoMediaData.getDeviceId(), str)) {
                L.relay.d("getRelayMediaByDeviceId %s", micoMediaData.getTitle());
                return micoMediaData;
            }
        }
        return null;
    }

    private final MicoMediaData c(MediaType mediaType) {
        for (MicoMediaData micoMediaData : j) {
            if (micoMediaData.getMediaType() == mediaType) {
                L.relay.d("getRelayMediaByMediaType %s", micoMediaData.getTitle());
                return micoMediaData;
            }
        }
        return null;
    }

    @Nullable
    public final MicoMediaData getFirstData() {
        if (j.size() > 0) {
            return j.get(0);
        }
        return new MicoMediaData(MediaType.NONE, "", "", "", null, null, null, 0, null, 496, null);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public final void onPlayStateChanged(@NotNull MediaControllerEvent.MusicState musicState) {
        Intrinsics.checkNotNullParameter(musicState, "musicState");
        d = musicState.getState();
    }

    public final boolean verifyRelayFromBlueToothDevice(@NotNull RelayMediaEvent relayMediaEvent) {
        String connectedBluetoothDeviceAddressOnlyCase;
        Intrinsics.checkNotNullParameter(relayMediaEvent, "relayMediaEvent");
        MicoMediaData micoMediaData = g;
        if ((micoMediaData != null ? micoMediaData.getMediaType() : null) != MediaType.BT || (connectedBluetoothDeviceAddressOnlyCase = BlueToothUtil.getConnectedBluetoothDeviceAddressOnlyCase()) == null) {
            return false;
        }
        String MD5_32 = MD5.MD5_32(connectedBluetoothDeviceAddressOnlyCase);
        Intrinsics.checkNotNullExpressionValue(MD5_32, "MD5.MD5_32(it)");
        if (MD5_32 != null) {
            String upperCase = MD5_32.toUpperCase();
            Intrinsics.checkNotNullExpressionValue(upperCase, "(this as java.lang.String).toUpperCase()");
            return TextUtils.equals(upperCase, relayMediaEvent.getSourceBtMac());
        }
        throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
    }

    public final void removeRelayFromBlueToothDevice() {
        String connectedBluetoothDeviceAddressOnlyCase;
        MicoMediaData micoMediaData = g;
        if ((micoMediaData != null ? micoMediaData.getMediaType() : null) == MediaType.BT && (connectedBluetoothDeviceAddressOnlyCase = BlueToothUtil.getConnectedBluetoothDeviceAddressOnlyCase()) != null) {
            String MD5_32 = MD5.MD5_32(connectedBluetoothDeviceAddressOnlyCase);
            Intrinsics.checkNotNullExpressionValue(MD5_32, "MD5.MD5_32(it)");
            if (MD5_32 != null) {
                String upperCase = MD5_32.toUpperCase();
                Intrinsics.checkNotNullExpressionValue(upperCase, "(this as java.lang.String).toUpperCase()");
                for (MicoMediaData micoMediaData2 : j) {
                    if (TextUtils.equals(upperCase, micoMediaData2.getSourceBtMac()) && micoMediaData2.getMediaType() == MediaType.RELAY) {
                        L.relay.d("removeRelayFromBlueToothDevice");
                        j.remove(micoMediaData2);
                        return;
                    }
                }
                return;
            }
            throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
        }
    }
}
