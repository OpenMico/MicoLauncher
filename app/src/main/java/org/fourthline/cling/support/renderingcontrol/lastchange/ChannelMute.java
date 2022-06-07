package org.fourthline.cling.support.renderingcontrol.lastchange;

import org.fourthline.cling.support.model.Channel;

/* loaded from: classes5.dex */
public class ChannelMute {
    protected Channel channel;
    protected Boolean mute;

    public ChannelMute(Channel channel, Boolean bool) {
        this.channel = channel;
        this.mute = bool;
    }

    public Channel getChannel() {
        return this.channel;
    }

    public Boolean getMute() {
        return this.mute;
    }

    public String toString() {
        return "Mute: " + getMute() + " (" + getChannel() + ")";
    }
}
