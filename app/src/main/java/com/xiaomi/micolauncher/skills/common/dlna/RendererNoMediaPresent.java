package com.xiaomi.micolauncher.skills.common.dlna;

import com.xiaomi.micolauncher.common.L;
import java.net.URI;
import org.fourthline.cling.binding.annotations.UpnpService;
import org.fourthline.cling.binding.annotations.UpnpServiceId;
import org.fourthline.cling.binding.annotations.UpnpServiceType;
import org.fourthline.cling.support.avtransport.impl.state.AbstractState;
import org.fourthline.cling.support.avtransport.impl.state.NoMediaPresent;
import org.fourthline.cling.support.avtransport.lastchange.AVTransportVariable;
import org.fourthline.cling.support.model.AVTransport;
import org.fourthline.cling.support.model.MediaInfo;
import org.fourthline.cling.support.model.PositionInfo;

@UpnpService(serviceId = @UpnpServiceId("SwitchPower"), serviceType = @UpnpServiceType(value = "SwitchPower", version = 1))
/* loaded from: classes3.dex */
public class RendererNoMediaPresent extends NoMediaPresent {
    public RendererNoMediaPresent(AVTransport aVTransport) {
        super(aVTransport);
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [org.fourthline.cling.support.model.AVTransport] */
    /* JADX WARN: Type inference failed for: r0v2, types: [org.fourthline.cling.support.model.AVTransport] */
    /* JADX WARN: Type inference failed for: r0v3, types: [org.fourthline.cling.support.model.AVTransport] */
    /* JADX WARN: Type inference failed for: r10v1, types: [org.fourthline.cling.support.model.AVTransport] */
    @Override // org.fourthline.cling.support.avtransport.impl.state.NoMediaPresent
    public Class<? extends AbstractState> setTransportURI(URI uri, String str) {
        L.dlna.i("try to play %s %s", uri, str);
        getTransport().setMediaInfo(new MediaInfo(uri.toString(), str));
        getTransport().setPositionInfo(new PositionInfo(1L, str, uri.toString()));
        getTransport().getLastChange().setEventedValue(getTransport().getInstanceId(), new AVTransportVariable.AVTransportURI(uri), new AVTransportVariable.CurrentTrackURI(uri));
        return RendererStopped.class;
    }
}
