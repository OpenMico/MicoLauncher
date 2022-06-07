package com.xiaomi.micolauncher.skills.common.dlna;

import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.skills.music.controller.MediaSessionController;
import java.net.URI;
import org.fourthline.cling.support.avtransport.impl.state.AbstractState;
import org.fourthline.cling.support.avtransport.impl.state.Playing;
import org.fourthline.cling.support.model.AVTransport;
import org.fourthline.cling.support.model.SeekMode;

/* loaded from: classes3.dex */
public class RendererPlaying extends Playing {
    @Override // org.fourthline.cling.support.avtransport.impl.state.Playing
    public Class<? extends AbstractState> next() {
        return null;
    }

    @Override // org.fourthline.cling.support.avtransport.impl.state.Playing
    public Class<? extends AbstractState<?>> pause() {
        return null;
    }

    @Override // org.fourthline.cling.support.avtransport.impl.state.Playing
    public Class<? extends AbstractState<?>> previous() {
        return null;
    }

    @Override // org.fourthline.cling.support.avtransport.impl.state.Playing
    public Class<? extends AbstractState<?>> seek(SeekMode seekMode, String str) {
        return null;
    }

    @Override // org.fourthline.cling.support.avtransport.impl.state.Playing
    public Class<? extends AbstractState<?>> stop() {
        return null;
    }

    public RendererPlaying(AVTransport aVTransport) {
        super(aVTransport);
    }

    @Override // org.fourthline.cling.support.avtransport.impl.state.Playing
    public Class<? extends AbstractState<?>> setTransportURI(URI uri, String str) {
        L.dlna.i("%s %s %s", getClass().getSimpleName(), uri.toString(), str);
        return null;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [org.fourthline.cling.support.model.AVTransport] */
    @Override // org.fourthline.cling.support.avtransport.impl.state.Playing
    public Class<? extends AbstractState<?>> play(String str) {
        MediaSessionController.getInstance().playDlna(getTransport().getMediaInfo());
        return null;
    }
}
