package com.xiaomi.miplay.mylibrary.session;

import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.PlaybackState;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.xiaomi.miplay.mylibrary.session.data.MediaMetaData;
import com.xiaomi.miplay.mylibrary.session.utils.Logger;
import java.util.Iterator;
import java.util.LinkedList;

/* loaded from: classes4.dex */
public class ActiveSessionRecordStack extends LinkedList<ActiveSessionRecord> {
    @Nullable
    public ActiveSessionRecord getTopAudioSession() {
        if (isEmpty()) {
            return null;
        }
        ActiveSessionRecord last = getLast();
        if (MediaMetaData.isMediaMetadataInvalid(last.getMediaController().getMetadata())) {
            return null;
        }
        return last;
    }

    public boolean add(ActiveSessionRecord activeSessionRecord) {
        MediaController mediaController = activeSessionRecord.getMediaController();
        PlaybackState playbackState = mediaController.getPlaybackState();
        MediaMetadata metadata = mediaController.getMetadata();
        if (playbackState == null || MediaMetaData.isMediaMetadataInvalid(metadata) || playbackState.getState() != 3) {
            addFirst(activeSessionRecord);
            return true;
        }
        addLast(activeSessionRecord);
        return true;
    }

    public void add(int i, ActiveSessionRecord activeSessionRecord) {
        throw new UnsupportedOperationException();
    }

    public boolean onPlaybackStateChange(ActiveSessionRecord activeSessionRecord, int i) {
        Logger.d("ActiveSessionRecordStack", "onPlaybackStateChange.", new Object[0]);
        int indexOf = indexOf(activeSessionRecord);
        if (indexOf < 0) {
            Logger.w("ActiveSessionRecordStack", "illegal record, package: " + activeSessionRecord.getPackageName(), new Object[0]);
            return false;
        }
        boolean isMediaMetadataInvalid = MediaMetaData.isMediaMetadataInvalid(activeSessionRecord.getMediaController().getMetadata());
        ActiveSessionRecord activeSessionRecord2 = null;
        ActiveSessionRecord last = isEmpty() ? null : getLast();
        if (isMediaMetadataInvalid || i != 3) {
            int size = size() - 1;
            while (true) {
                if (size < 0) {
                    size = -1;
                    break;
                }
                ActiveSessionRecord activeSessionRecord3 = get(size);
                if (!MediaMetaData.isMediaMetadataInvalid(activeSessionRecord3.getMediaController().getMetadata()) && activeSessionRecord3.getPlaybackState() == 3) {
                    break;
                }
                size--;
            }
            if (size >= 0) {
                addLast(remove(size));
            }
        } else {
            addLast(remove(indexOf));
        }
        if (!isEmpty()) {
            activeSessionRecord2 = getLast();
        }
        return activeSessionRecord2 != last;
    }

    @Nullable
    public ActiveSessionRecord findByMediaController(@NonNull MediaController mediaController) {
        Iterator it = iterator();
        while (it.hasNext()) {
            ActiveSessionRecord activeSessionRecord = (ActiveSessionRecord) it.next();
            if (activeSessionRecord.getMediaController().getSessionToken().equals(mediaController.getSessionToken())) {
                return activeSessionRecord;
            }
        }
        return null;
    }
}
