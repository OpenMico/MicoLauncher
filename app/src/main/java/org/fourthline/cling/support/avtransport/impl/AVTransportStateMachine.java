package org.fourthline.cling.support.avtransport.impl;

import java.net.URI;
import org.fourthline.cling.support.avtransport.impl.state.AbstractState;
import org.fourthline.cling.support.model.SeekMode;
import org.seamless.statemachine.StateMachine;

/* loaded from: classes5.dex */
public interface AVTransportStateMachine extends StateMachine<AbstractState> {
    void next();

    void pause();

    void play(String str);

    void previous();

    void record();

    void seek(SeekMode seekMode, String str);

    void setNextTransportURI(URI uri, String str);

    void setTransportURI(URI uri, String str);

    void stop();
}
