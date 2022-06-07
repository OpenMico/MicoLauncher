package com.xiaomi.micolauncher.instruciton.factory;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.AudioPlayer;
import com.xiaomi.ai.api.Common;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.instruciton.impl.AudioPlayerFavoritesOperation;
import com.xiaomi.micolauncher.instruciton.impl.AudioPlayerPlayMusicOperation;
import com.xiaomi.micolauncher.instruciton.impl.AudioPlayerPlayOperation;
import com.xiaomi.micolauncher.skills.music.PlayerApi;

/* loaded from: classes3.dex */
public class AudioPlayerOperationFactory extends AbstractOperationFactory {
    @Override // com.xiaomi.micolauncher.instruciton.factory.AbstractOperationFactory
    public String namespace() {
        return "AudioPlayer";
    }

    @Override // com.xiaomi.micolauncher.instruciton.factory.AbstractOperationFactory
    public BaseOperation build(Instruction instruction, String str) {
        char c;
        int hashCode = str.hashCode();
        if (hashCode == -902666468) {
            if (str.equals(AIApiConstants.AudioPlayer.CancelFromFavorites)) {
                c = 2;
            }
            c = 65535;
        } else if (hashCode != 1033105579) {
            if (hashCode == 1492976498 && str.equals(AIApiConstants.AudioPlayer.AddToFavorites)) {
                c = 1;
            }
            c = 65535;
        } else {
            if (str.equals(AIApiConstants.AudioPlayer.Play)) {
                c = 0;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
                if (PlayerApi.isPlaying()) {
                    PlayerApi.pause();
                }
                if (a(instruction)) {
                    return new AudioPlayerPlayMusicOperation(instruction);
                }
                return new AudioPlayerPlayOperation(instruction);
            case 1:
                return new AudioPlayerFavoritesOperation(instruction, true);
            case 2:
                return new AudioPlayerFavoritesOperation(instruction, false);
            default:
                return null;
        }
    }

    private boolean a(Instruction instruction) {
        if (!(instruction.getPayload() instanceof AudioPlayer.Play)) {
            return false;
        }
        AudioPlayer.Play play = (AudioPlayer.Play) instruction.getPayload();
        if (!play.getAudioType().isPresent()) {
            return false;
        }
        Common.AudioType audioType = play.getAudioType().get();
        return audioType == Common.AudioType.MUSIC || audioType == Common.AudioType.NEWS || audioType == Common.AudioType.RADIO_STATION || audioType == Common.AudioType.BOOKS;
    }
}
