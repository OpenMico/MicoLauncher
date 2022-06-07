package io.netty.handler.codec.mqtt;

import io.netty.util.internal.StringUtil;
import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
public final class MqttSubscribePayload {
    private final List<MqttTopicSubscription> a;

    public MqttSubscribePayload(List<MqttTopicSubscription> list) {
        this.a = Collections.unmodifiableList(list);
    }

    public List<MqttTopicSubscription> topicSubscriptions() {
        return this.a;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(StringUtil.simpleClassName(this));
        sb.append('[');
        for (int i = 0; i < this.a.size() - 1; i++) {
            sb.append(this.a.get(i));
            sb.append(", ");
        }
        List<MqttTopicSubscription> list = this.a;
        sb.append(list.get(list.size() - 1));
        sb.append(']');
        return sb.toString();
    }
}
