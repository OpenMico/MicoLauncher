package io.netty.channel.epoll;

import com.xiaomi.micolauncher.skills.update.VersionUtil;
import io.netty.util.internal.ObjectUtil;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/* compiled from: TcpMd5Util.java */
/* loaded from: classes4.dex */
final class g {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static Collection<InetAddress> a(AbstractEpollChannel abstractEpollChannel, Collection<InetAddress> collection, Map<InetAddress, byte[]> map) throws IOException {
        ObjectUtil.checkNotNull(abstractEpollChannel, "channel");
        ObjectUtil.checkNotNull(collection, VersionUtil.VERSION_CURRENT);
        ObjectUtil.checkNotNull(map, "newKeys");
        for (Map.Entry<InetAddress, byte[]> entry : map.entrySet()) {
            byte[] value = entry.getValue();
            if (entry.getKey() == null) {
                throw new IllegalArgumentException("newKeys contains an entry with null address: " + map);
            } else if (value == null) {
                throw new NullPointerException("newKeys[" + entry.getKey() + ']');
            } else if (value.length == 0) {
                throw new IllegalArgumentException("newKeys[" + entry.getKey() + "] has an empty key.");
            } else if (value.length > Native.TCP_MD5SIG_MAXKEYLEN) {
                throw new IllegalArgumentException("newKeys[" + entry.getKey() + "] has a key with invalid length; should not exceed the maximum length (" + Native.TCP_MD5SIG_MAXKEYLEN + ')');
            }
        }
        for (InetAddress inetAddress : collection) {
            if (!map.containsKey(inetAddress)) {
                Native.setTcpMd5Sig(abstractEpollChannel.fd().intValue(), inetAddress, null);
            }
        }
        if (map.isEmpty()) {
            return Collections.emptySet();
        }
        ArrayList arrayList = new ArrayList(map.size());
        for (Map.Entry<InetAddress, byte[]> entry2 : map.entrySet()) {
            Native.setTcpMd5Sig(abstractEpollChannel.fd().intValue(), entry2.getKey(), entry2.getValue());
            arrayList.add(entry2.getKey());
        }
        return arrayList;
    }
}
