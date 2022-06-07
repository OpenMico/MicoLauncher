package com.xiaomi.micolauncher.skills.music.view_v2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xiaomi.ai.api.Template;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.Remote;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.instruciton.impl.TemplateStationsOperation;
import com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class AudioBookPlayListActivity extends BaseActivity implements LocalPlayerManager.MetadataChangedCallback {
    public static boolean eventFormDisplayDetail;
    public static boolean isClickRadioList;
    public static boolean listHasShow;
    RecyclerView a;
    private PlayerRadioListAdapter b;
    private Template.Stations c;
    private Remote.Response.PlayerStatus d;
    private String e;
    private int f;
    private boolean g;

    @Override // com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager.MetadataChangedCallback
    public void onMediaListChanged(Remote.Response.PlayerStatus playerStatus) {
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager.MetadataChangedCallback
    public void onMediaMetadataChanged(Remote.Response.PlayerStatus playerStatus) {
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        String str;
        super.onCreate(bundle);
        setContentView(R.layout.activity_audio_book_play_list_v2);
        listHasShow = true;
        this.a = (RecyclerView) findViewById(R.id.player_list_rv);
        this.a.setLayoutManager(new LinearLayoutManager(this));
        this.c = LocalPlayerManager.getInstance().getStations();
        if (this.c != null) {
            this.d = LocalPlayerManager.getInstance().getPlayerStatus();
            this.g = LocalPlayerManager.getInstance().getPlayingPlayerStatus().isPlayingStatus();
            Remote.Response.PlayerStatus playerStatus = this.d;
            if (playerStatus != null && playerStatus.play_song_detail != null) {
                this.e = this.d.play_song_detail.albumGlobalID;
                if (this.e != null) {
                    int i = 0;
                    while (true) {
                        if (i >= ContainerUtil.getSize(this.c.getItems())) {
                            break;
                        } else if (this.e.equals(this.c.getItems().get(i).getAiAlbumId())) {
                            this.f = i;
                            break;
                        } else {
                            i++;
                        }
                    }
                }
            }
            this.b = new PlayerRadioListAdapter(this.c.getItems(), this);
            this.b.setPlayStatus(this.g);
            View inflate = LayoutInflater.from(this).inflate(R.layout.item_audio_book_more_title, (ViewGroup) null);
            if (this.c.getTitle() == null || !this.c.getTitle().isPresent()) {
                str = getString(R.string.audio_book_find_title);
            } else {
                String str2 = this.c.getTitle().get();
                if (str2.length() > 10) {
                    str2 = str2.substring(0, 10) + "...";
                }
                str = getString(R.string.audio_book_relationship, new Object[]{str2});
            }
            ((TextView) inflate.findViewById(R.id.title)).setText(str);
            this.b.addHeaderView(inflate);
            this.a.setAdapter(this.b);
            setDefaultScheduleDuration();
            setScheduleDuration(TimeUnit.SECONDS.toMillis(15L));
            this.b.setSelectPosition(this.f + 1);
            this.a.scrollToPosition(this.f);
            this.b.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.xiaomi.micolauncher.skills.music.view_v2.-$$Lambda$AudioBookPlayListActivity$aX6b23V1C6s8MsGIlQFNlxtfoA0
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                    AudioBookPlayListActivity.this.a(baseQuickAdapter, view, i2);
                }
            });
            ((SimpleItemAnimator) this.a.getItemAnimator()).setSupportsChangeAnimations(false);
        }
    }

    public /* synthetic */ void a(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        int i2 = i + 1;
        if (this.b.getSelectPosition() != i2) {
            this.b.setSelectPosition(i2);
            isClickRadioList = true;
            TemplateStationsOperation.eventRequest(this.c.getItems().get(i).getAiAlbumId());
        }
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        LocalPlayerManager.getInstance().registerCallback(this);
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        LocalPlayerManager.getInstance().unregisterCallback(this);
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, android.app.Activity, android.view.Window.Callback
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getX() >= 604.0f) {
            return super.dispatchTouchEvent(motionEvent);
        }
        finish();
        return true;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager.MetadataChangedCallback
    public void onPlayerStateChanged(Remote.Response.PlayerStatus playerStatus) {
        if (this.g != playerStatus.isPlayingStatus()) {
            this.g = playerStatus.isPlayingStatus();
            L.player.i("stations onPlayerStateChanged isPlaying %b", Boolean.valueOf(playerStatus.isPlayingStatus()));
            this.b.setPlayStatus(playerStatus.isPlayingStatus());
            this.b.notifyDataSetChanged();
        }
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, android.app.Activity
    public void finish() {
        overridePendingTransition(0, 0);
        super.finish();
    }
}
