package org.fourthline.cling.transport.spi;

import org.fourthline.cling.model.message.StreamRequestMessage;
import org.fourthline.cling.model.message.StreamResponseMessage;
import org.fourthline.cling.transport.spi.StreamClientConfiguration;

/* loaded from: classes5.dex */
public interface StreamClient<C extends StreamClientConfiguration> {
    C getConfiguration();

    StreamResponseMessage sendRequest(StreamRequestMessage streamRequestMessage) throws InterruptedException;

    void stop();
}
