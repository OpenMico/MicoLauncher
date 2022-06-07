package com.xiaomi.micolauncher.module.child.childstory;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.xiaomi.micolauncher.api.model.ChildVideo;
import com.xiaomi.micolauncher.api.model.IListItem;
import com.xiaomi.micolauncher.api.model.Remote;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.schema.SchemaManager;
import com.xiaomi.micolauncher.common.schema.handler.HomepageSchemaHandler;
import com.xiaomi.micolauncher.module.child.childstory.ChildPlayUtil;
import com.xiaomi.micolauncher.module.child.childvideo.ChildVideoDataManager;
import com.xiaomi.micolauncher.module.child.childvideo.MiTvVideoDetailActivity;
import com.xiaomi.micolauncher.skills.music.PlayerApi;
import com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import io.reactivex.functions.Consumer;

/* loaded from: classes3.dex */
public class ChildPlayUtil {

    /* loaded from: classes3.dex */
    public interface OnPlayIndexLoadListener {
        void onPlayIndexLoad(int i);
    }

    public static void playStory(Context context, IListItem iListItem) {
        if (LocalPlayerManager.getInstance().isBlack(iListItem.getItemTitle())) {
            SchemaManager.handleSchema(context, HomepageSchemaHandler.PATH_BLACKLIST);
            return;
        }
        Uri parse = Uri.parse(iListItem.getItemTarget());
        PlayerApi.playStation(context, parse.getQueryParameter("id"), parse.getQueryParameter("origin"), Remote.Request.PlaylistStation.convertStationType(parse.getQueryParameter("type")));
    }

    public static void playVideo(Context context, String str, String str2, String str3, ChildVideo.MiTvType miTvType) {
        Intent intent = new Intent(context, MiTvVideoDetailActivity.class);
        intent.putExtra(MiTvVideoDetailActivity.EXTRA_ID, str);
        intent.putExtra(MiTvVideoDetailActivity.EXTRA_TITLE, str2);
        intent.putExtra(MiTvVideoDetailActivity.EXTRA_COVER, str3);
        intent.putExtra(MiTvVideoDetailActivity.EXTRA_TYPE, miTvType);
        ActivityLifeCycleManager.startActivityQuietly(intent);
    }

    @SuppressLint({"CheckResult"})
    public static void getPlayIndexFromDb(String str, final OnPlayIndexLoadListener onPlayIndexLoadListener) {
        ChildVideoDataManager.getManager().loadVideoItemFromDb(str).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.child.childstory.-$$Lambda$ChildPlayUtil$ScgtpF2Fixf2_9p3Nqg4j3st_xA
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ChildPlayUtil.a(ChildPlayUtil.OnPlayIndexLoadListener.this, (VideoItem) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.module.child.childstory.-$$Lambda$ChildPlayUtil$Y6ZKpRele5QFqJ-c4momn-C2_gk
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                Throwable th = (Throwable) obj;
                ChildPlayUtil.OnPlayIndexLoadListener.this.onPlayIndexLoad(0);
            }
        });
    }

    public static /* synthetic */ void a(OnPlayIndexLoadListener onPlayIndexLoadListener, VideoItem videoItem) throws Exception {
        int ci = videoItem.getCi() - 1;
        L.video.d("load VideoItem from db  %s  %d", videoItem.getTitle(), Integer.valueOf(ci));
        onPlayIndexLoadListener.onPlayIndexLoad(Math.max(ci, 0));
    }
}
