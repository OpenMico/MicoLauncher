package io.netty.channel.group;

import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes4.dex */
public class ChannelGroupException extends ChannelException implements Iterable<Map.Entry<Channel, Throwable>> {
    private static final long serialVersionUID = -4093064295562629453L;
    private final Collection<Map.Entry<Channel, Throwable>> failed;

    public ChannelGroupException(Collection<Map.Entry<Channel, Throwable>> collection) {
        if (collection == null) {
            throw new NullPointerException("causes");
        } else if (!collection.isEmpty()) {
            this.failed = Collections.unmodifiableCollection(collection);
        } else {
            throw new IllegalArgumentException("causes must be non empty");
        }
    }

    @Override // java.lang.Iterable
    public Iterator<Map.Entry<Channel, Throwable>> iterator() {
        return this.failed.iterator();
    }
}
