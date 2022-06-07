package com.xiaomi.micolauncher.module.main;

import android.content.Context;
import com.alibaba.fastjson.JSONObject;
import com.xiaomi.mico.base.utils.PreferenceUtils;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.micolauncher.common.ubus.UBus;
import com.xiaomi.micolauncher.module.homepage.HomePageUtils;
import com.xiaomi.micolauncher.module.main.manager.RecommendDataManager;
import com.xiaomi.micolauncher.module.music.manager.MusicDataManager;
import com.xiaomi.micolauncher.module.recommend.RecommendEvent;
import com.xiaomi.micolauncher.skills.music.controller.ChannelManager;
import com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager;
import com.xiaomi.micolauncher.skills.music.model.cache.AudioInfoCache;
import java.util.List;

/* loaded from: classes3.dex */
public class HomepageHandler implements UBus.UbusHandler {
    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(List list) throws Exception {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(List list) throws Exception {
    }

    @Override // com.xiaomi.micolauncher.common.ubus.UBus.UbusHandler
    public boolean canHandle(Context context, String str, String str2) {
        return str.startsWith("homepage");
    }

    @Override // com.xiaomi.micolauncher.common.ubus.UBus.UbusHandler
    public String handle(Context context, String str, String str2, String str3) {
        UBus.Response response = new UBus.Response();
        JSONObject parseObject = JSONObject.parseObject(str3);
        L.homepage.e("Homepage handler path : %s,method: %s,params: %s ", str, str2, str3);
        if ("music_source_change".equals(str2)) {
            String string = parseObject.getString("source");
            HomePageUtils.setMusicSource(context, string);
            RecommendDataManager.getManager().refreshMusicList(context, string);
            ChannelManager.getInstance().loadChannelList(true).subscribeOn(MicoSchedulers.io()).subscribe($$Lambda$HomepageHandler$QE7bTl8RDH1vkwNSCtuYGZBQjA.INSTANCE, $$Lambda$HomepageHandler$odfjSVjDxZo9WFkmg6FykySJyBI.INSTANCE);
            MusicDataManager.getManager().clearCache();
            response.code = 0;
            response.info = "succeed";
        } else if ("cp_account_bind_status_changed".equals(str2)) {
            boolean booleanValue = parseObject.getBoolean(SchemaActivity.VALUE_REGISTER_BIND).booleanValue();
            PreferenceUtils.setSettingBoolean(context, HomePageUtils.QQ_MUSIC_BIND, booleanValue);
            if (booleanValue) {
                ChannelManager.getInstance().loadChannelList(true).subscribeOn(MicoSchedulers.io()).subscribe($$Lambda$HomepageHandler$SJXB7HeZIZDa7BAMDr2amgHUd1A.INSTANCE, $$Lambda$HomepageHandler$Lt2Hs_tZf4O_AbKIpWFq2hrpbsk.INSTANCE);
                RecommendDataManager.getManager().refreshMusicList(context, "QQ");
                AudioInfoCache.clearDiskCacheIfNecessary();
            } else {
                EventBusRegistry.getEventBus().post(new RecommendEvent.QQMusicUnBinded());
            }
            response.code = 0;
            response.info = "succeed";
        } else if ("music_black_list_change".equals(str2)) {
            LocalPlayerManager.getInstance().clearBlackList();
            response.code = 0;
            response.info = "succeed";
        }
        return response.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(Throwable th) throws Exception {
        L.homepage.e("source change load channelList error : %s", th);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Throwable th) throws Exception {
        L.homepage.e("bind success load channelList error : %s", th);
    }
}
