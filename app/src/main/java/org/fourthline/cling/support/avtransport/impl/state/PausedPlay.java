package org.fourthline.cling.support.avtransport.impl.state;

import java.net.URI;
import java.util.logging.Logger;
import org.fourthline.cling.support.avtransport.lastchange.AVTransportVariable;
import org.fourthline.cling.support.model.AVTransport;
import org.fourthline.cling.support.model.TransportAction;
import org.fourthline.cling.support.model.TransportInfo;
import org.fourthline.cling.support.model.TransportState;

/* loaded from: classes5.dex */
public abstract class PausedPlay<T extends AVTransport> extends AbstractState<T> {
    private static final Logger log = Logger.getLogger(PausedPlay.class.getName());

    public abstract Class<? extends AbstractState<?>> play(String str);

    public abstract Class<? extends AbstractState<?>> setTransportURI(URI uri, String str);

    public abstract Class<? extends AbstractState<?>> stop();

    public PausedPlay(T t) {
        super(t);
    }

    public void onEntry() {
        log.fine("Setting transport state to PAUSED_PLAYBACK");
        getTransport().setTransportInfo(new TransportInfo(TransportState.PAUSED_PLAYBACK, getTransport().getTransportInfo().getCurrentTransportStatus(), getTransport().getTransportInfo().getCurrentSpeed()));
        getTransport().getLastChange().setEventedValue(getTransport().getInstanceId(), new AVTransportVariable.TransportState(TransportState.PAUSED_PLAYBACK), new AVTransportVariable.CurrentTransportActions(getCurrentTransportActions()));
    }

    @Override // org.fourthline.cling.support.avtransport.impl.state.AbstractState
    public TransportAction[] getCurrentTransportActions() {
        return new TransportAction[]{TransportAction.Stop, TransportAction.Play};
    }
}
