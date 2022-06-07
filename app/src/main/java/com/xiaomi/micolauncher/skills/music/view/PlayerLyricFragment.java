package com.xiaomi.micolauncher.skills.music.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.xiaomi.mico.common.DisplayUtils;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.Remote;
import com.xiaomi.micolauncher.common.base.BaseFragment;
import com.xiaomi.micolauncher.module.music.utils.MusicStatHelper;
import com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager;
import com.xiaomi.micolauncher.skills.music.model.MusicHelper;
import com.xiaomi.micolauncher.skills.music.model.lrc.LrcManager;
import com.xiaomi.micolauncher.skills.music.model.lrc.LrcRow;
import com.xiaomi.micolauncher.skills.music.model.lrc.LrcViewImpl1;
import java.util.List;

/* loaded from: classes3.dex */
public class PlayerLyricFragment extends BaseFragment implements LocalPlayerManager.MetadataChangedCallback {
    LrcViewImpl1 a;

    @Override // com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager.MetadataChangedCallback
    public void onMediaListChanged(Remote.Response.PlayerStatus playerStatus) {
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_player_lyric, (ViewGroup) null);
        this.a = (LrcViewImpl1) inflate.findViewById(R.id.lrc_view);
        this.a.setTotalRowsToDraw(DisplayUtils.getScreenHeightPixels(getContext()) > 480 ? 10 : 5);
        a();
        return inflate;
    }

    private void a() {
        Remote.Response.PlayerStatus playingPlayerStatus = LocalPlayerManager.getInstance().getPlayingPlayerStatus();
        if (MusicHelper.isPlayingSong(playingPlayerStatus.media_type)) {
            LrcManager.getInstance().loadLrc(playingPlayerStatus.play_song_detail, new LrcManager.OnLrcChangeListener() { // from class: com.xiaomi.micolauncher.skills.music.view.-$$Lambda$PlayerLyricFragment$Ht9Eldg6yHiGZFChOrFo1KTT7j8
                @Override // com.xiaomi.micolauncher.skills.music.model.lrc.LrcManager.OnLrcChangeListener
                public final void onLrcRowsChange(String str, List list) {
                    PlayerLyricFragment.this.a(str, list);
                }
            });
            a(playingPlayerStatus.play_song_detail.position, true, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str, List<LrcRow> list) {
        if (this.a != null) {
            if (list == null || list.isEmpty()) {
                this.a.setEmptyText(str, getString(R.string.music_no_lrc));
                return;
            }
            this.a.setLrcRows(str, list);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseFragment
    public void onActivate() {
        super.onActivate();
        a();
        LocalPlayerManager.getInstance().registerCallback(this);
        MusicStatHelper.recordPlayerShow(MusicStatHelper.PlayerPage.LYRIC);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseFragment
    public void onDeactivate() {
        super.onDeactivate();
        LocalPlayerManager.getInstance().unregisterCallback(this);
    }

    private void a(long j, boolean z, boolean z2) {
        LrcViewImpl1 lrcViewImpl1;
        if (this.mActivated && (lrcViewImpl1 = this.a) != null && lrcViewImpl1.hasLrcRows()) {
            this.a.seekTo((int) j, z, z2);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager.MetadataChangedCallback
    public void onMediaMetadataChanged(Remote.Response.PlayerStatus playerStatus) {
        a();
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager.MetadataChangedCallback
    public void onPlayerStateChanged(Remote.Response.PlayerStatus playerStatus) {
        if (playerStatus.play_song_detail != null) {
            a(playerStatus.play_song_detail.position, false, false);
        }
    }
}
