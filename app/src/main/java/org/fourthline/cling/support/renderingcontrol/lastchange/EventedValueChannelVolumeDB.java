package org.fourthline.cling.support.renderingcontrol.lastchange;

import java.util.Map;
import org.fourthline.cling.model.types.Datatype;
import org.fourthline.cling.model.types.InvalidValueException;
import org.fourthline.cling.model.types.UnsignedIntegerTwoBytes;
import org.fourthline.cling.model.types.UnsignedIntegerTwoBytesDatatype;
import org.fourthline.cling.support.lastchange.EventedValue;
import org.fourthline.cling.support.model.Channel;
import org.fourthline.cling.support.shared.AbstractMap;

/* loaded from: classes5.dex */
public class EventedValueChannelVolumeDB extends EventedValue<ChannelVolumeDB> {
    @Override // org.fourthline.cling.support.lastchange.EventedValue
    protected Datatype getDatatype() {
        return null;
    }

    public EventedValueChannelVolumeDB(ChannelVolumeDB channelVolumeDB) {
        super(channelVolumeDB);
    }

    public EventedValueChannelVolumeDB(Map.Entry<String, String>[] entryArr) {
        super(entryArr);
    }

    @Override // org.fourthline.cling.support.lastchange.EventedValue
    /* renamed from: valueOf  reason: avoid collision after fix types in other method */
    protected ChannelVolumeDB valueOf2(Map.Entry<String, String>[] entryArr) throws InvalidValueException {
        Channel channel = null;
        Integer num = null;
        for (Map.Entry<String, String> entry : entryArr) {
            if (entry.getKey().equals("channel")) {
                channel = Channel.valueOf(entry.getValue());
            }
            if (entry.getKey().equals("val")) {
                num = Integer.valueOf(new UnsignedIntegerTwoBytesDatatype().valueOf(entry.getValue()).getValue().intValue());
            }
        }
        if (channel == null || num == null) {
            return null;
        }
        return new ChannelVolumeDB(channel, num);
    }

    @Override // org.fourthline.cling.support.lastchange.EventedValue
    public Map.Entry<String, String>[] getAttributes() {
        return new Map.Entry[]{new AbstractMap.SimpleEntry("val", new UnsignedIntegerTwoBytesDatatype().getString(new UnsignedIntegerTwoBytes(getValue().getVolumeDB().intValue()))), new AbstractMap.SimpleEntry("channel", getValue().getChannel().name())};
    }

    @Override // org.fourthline.cling.support.lastchange.EventedValue
    public String toString() {
        return getValue().toString();
    }
}
