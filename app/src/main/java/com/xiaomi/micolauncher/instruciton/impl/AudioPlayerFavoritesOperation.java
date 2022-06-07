package com.xiaomi.micolauncher.instruciton.impl;

import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.api.model.Remote;
import com.xiaomi.micolauncher.common.stat.PlaybackTrackHelper;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.skills.music.controller.CollectionManager;
import com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager;
import java.util.Optional;

/* loaded from: classes3.dex */
public class AudioPlayerFavoritesOperation extends BaseOperation<Instruction> {
    private boolean b;

    public AudioPlayerFavoritesOperation(Instruction instruction, boolean z) {
        super(instruction);
        this.b = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a() {
        CollectionManager.getInstance().refreshLoveStatus(Optional.of(Boolean.valueOf(this.b)));
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        ThreadUtil.getIoThreadPool().submit(new Runnable() { // from class: com.xiaomi.micolauncher.instruciton.impl.-$$Lambda$AudioPlayerFavoritesOperation$wxW6F5ZXrpKW16BATbjhFZpi_8w
            @Override // java.lang.Runnable
            public final void run() {
                AudioPlayerFavoritesOperation.this.a();
            }
        });
        Remote.Response.PlayerStatus playerStatus = LocalPlayerManager.getInstance().getPlayerStatus();
        if (playerStatus != null) {
            PlaybackTrackHelper.postFavoriteEvent(playerStatus.play_song_detail, "voice", this.b);
        }
        return BaseOperation.OpState.STATE_SUCCESS;
    }
}
