package com.xiaomi.micolauncher.skills.common.dlna;

import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.skills.music.controller.MediaSessionController;
import java.net.URI;
import org.fourthline.cling.support.avtransport.impl.state.AbstractState;
import org.fourthline.cling.support.avtransport.impl.state.Stopped;
import org.fourthline.cling.support.model.AVTransport;
import org.fourthline.cling.support.model.SeekMode;

/* loaded from: classes3.dex */
public class RendererStopped extends Stopped {
    @Override // org.fourthline.cling.support.avtransport.impl.state.Stopped
    public Class<? extends AbstractState<?>> seek(SeekMode seekMode, String str) {
        return null;
    }

    public RendererStopped(AVTransport aVTransport) {
        super(aVTransport);
    }

    @Override // org.fourthline.cling.support.avtransport.impl.state.Stopped
    public Class<? extends AbstractState<?>> setTransportURI(URI uri, String str) {
        L.dlna.d(uri.toString());
        return null;
    }

    @Override // org.fourthline.cling.support.avtransport.impl.state.Stopped
    public Class<? extends AbstractState<?>> stop() {
        L.dlna.i("%s stop", getClass().getSimpleName());
        return null;
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [org.fourthline.cling.support.model.AVTransport] */
    @Override // org.fourthline.cling.support.avtransport.impl.state.Stopped
    public Class<? extends AbstractState> play(String str) {
        L.dlna.i("%s play", getClass().getSimpleName());
        MediaSessionController.getInstance().playDlna(getTransport().getMediaInfo());
        return RendererPlaying.class;
    }

    @Override // org.fourthline.cling.support.avtransport.impl.state.Stopped
    public Class<? extends AbstractState> next() {
        return RendererStopped.class;
    }

    @Override // org.fourthline.cling.support.avtransport.impl.state.Stopped
    public Class<? extends AbstractState> previous() {
        return RendererStopped.class;
    }
}
