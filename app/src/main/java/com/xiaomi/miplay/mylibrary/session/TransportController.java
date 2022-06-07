package com.xiaomi.miplay.mylibrary.session;

import android.media.session.MediaController;
import android.text.TextUtils;
import android.view.KeyEvent;
import com.xiaomi.miplay.mylibrary.utils.Constant;

/* loaded from: classes4.dex */
public class TransportController implements TransportControllerDelegate {
    private final TransportControllerDelegate a;

    public TransportController(MediaController mediaController) {
        this.a = a(mediaController);
    }

    @Override // com.xiaomi.miplay.mylibrary.session.TransportControllerDelegate
    public void play() {
        this.a.play();
    }

    @Override // com.xiaomi.miplay.mylibrary.session.TransportControllerDelegate
    public void pause() {
        this.a.pause();
    }

    @Override // com.xiaomi.miplay.mylibrary.session.TransportControllerDelegate
    public void seekTo(long j) {
        this.a.seekTo(j);
    }

    private static TransportControllerDelegate a(MediaController mediaController) {
        if (TextUtils.equals(mediaController.getPackageName(), Constant.MUSIC_QQ)) {
            return new b(mediaController);
        }
        return new a(mediaController);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static class a implements TransportControllerDelegate {
        private final MediaController a;

        public a(MediaController mediaController) {
            this.a = mediaController;
        }

        @Override // com.xiaomi.miplay.mylibrary.session.TransportControllerDelegate
        public void play() {
            this.a.getTransportControls().play();
        }

        @Override // com.xiaomi.miplay.mylibrary.session.TransportControllerDelegate
        public void pause() {
            this.a.getTransportControls().pause();
        }

        @Override // com.xiaomi.miplay.mylibrary.session.TransportControllerDelegate
        public void seekTo(long j) {
            this.a.getTransportControls().seekTo(j);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static class b implements TransportControllerDelegate {
        private final MediaController a;

        public b(MediaController mediaController) {
            this.a = mediaController;
        }

        @Override // com.xiaomi.miplay.mylibrary.session.TransportControllerDelegate
        public void play() {
            KeyEvent keyEvent = new KeyEvent(0, 85);
            KeyEvent keyEvent2 = new KeyEvent(1, 85);
            this.a.dispatchMediaButtonEvent(keyEvent);
            this.a.dispatchMediaButtonEvent(keyEvent2);
        }

        @Override // com.xiaomi.miplay.mylibrary.session.TransportControllerDelegate
        public void pause() {
            KeyEvent keyEvent = new KeyEvent(0, 85);
            KeyEvent keyEvent2 = new KeyEvent(1, 85);
            this.a.dispatchMediaButtonEvent(keyEvent);
            this.a.dispatchMediaButtonEvent(keyEvent2);
        }

        @Override // com.xiaomi.miplay.mylibrary.session.TransportControllerDelegate
        public void seekTo(long j) {
            this.a.getTransportControls().seekTo(j);
        }
    }
}
