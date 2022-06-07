package io.netty.handler.codec.spdy;

import io.netty.util.internal.StringUtil;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/* loaded from: classes4.dex */
public class DefaultSpdySettingsFrame implements SpdySettingsFrame {
    private boolean a;
    private final Map<Integer, a> b = new TreeMap();

    @Override // io.netty.handler.codec.spdy.SpdySettingsFrame
    public Set<Integer> ids() {
        return this.b.keySet();
    }

    @Override // io.netty.handler.codec.spdy.SpdySettingsFrame
    public boolean isSet(int i) {
        return this.b.containsKey(Integer.valueOf(i));
    }

    @Override // io.netty.handler.codec.spdy.SpdySettingsFrame
    public int getValue(int i) {
        a aVar = this.b.get(Integer.valueOf(i));
        if (aVar != null) {
            return aVar.a();
        }
        return -1;
    }

    @Override // io.netty.handler.codec.spdy.SpdySettingsFrame
    public SpdySettingsFrame setValue(int i, int i2) {
        return setValue(i, i2, false, false);
    }

    @Override // io.netty.handler.codec.spdy.SpdySettingsFrame
    public SpdySettingsFrame setValue(int i, int i2, boolean z, boolean z2) {
        if (i < 0 || i > 16777215) {
            throw new IllegalArgumentException("Setting ID is not valid: " + i);
        }
        Integer valueOf = Integer.valueOf(i);
        a aVar = this.b.get(valueOf);
        if (aVar != null) {
            aVar.a(i2);
            aVar.a(z);
            aVar.b(z2);
        } else {
            this.b.put(valueOf, new a(i2, z, z2));
        }
        return this;
    }

    @Override // io.netty.handler.codec.spdy.SpdySettingsFrame
    public SpdySettingsFrame removeValue(int i) {
        this.b.remove(Integer.valueOf(i));
        return this;
    }

    @Override // io.netty.handler.codec.spdy.SpdySettingsFrame
    public boolean isPersistValue(int i) {
        a aVar = this.b.get(Integer.valueOf(i));
        return aVar != null && aVar.b();
    }

    @Override // io.netty.handler.codec.spdy.SpdySettingsFrame
    public SpdySettingsFrame setPersistValue(int i, boolean z) {
        a aVar = this.b.get(Integer.valueOf(i));
        if (aVar != null) {
            aVar.a(z);
        }
        return this;
    }

    @Override // io.netty.handler.codec.spdy.SpdySettingsFrame
    public boolean isPersisted(int i) {
        a aVar = this.b.get(Integer.valueOf(i));
        return aVar != null && aVar.c();
    }

    @Override // io.netty.handler.codec.spdy.SpdySettingsFrame
    public SpdySettingsFrame setPersisted(int i, boolean z) {
        a aVar = this.b.get(Integer.valueOf(i));
        if (aVar != null) {
            aVar.b(z);
        }
        return this;
    }

    @Override // io.netty.handler.codec.spdy.SpdySettingsFrame
    public boolean clearPreviouslyPersistedSettings() {
        return this.a;
    }

    @Override // io.netty.handler.codec.spdy.SpdySettingsFrame
    public SpdySettingsFrame setClearPreviouslyPersistedSettings(boolean z) {
        this.a = z;
        return this;
    }

    private Set<Map.Entry<Integer, a>> a() {
        return this.b.entrySet();
    }

    private void a(StringBuilder sb) {
        for (Map.Entry<Integer, a> entry : a()) {
            a value = entry.getValue();
            sb.append("--> ");
            sb.append(entry.getKey());
            sb.append(':');
            sb.append(value.a());
            sb.append(" (persist value: ");
            sb.append(value.b());
            sb.append("; persisted: ");
            sb.append(value.c());
            sb.append(')');
            sb.append(StringUtil.NEWLINE);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(StringUtil.simpleClassName(this));
        sb.append(StringUtil.NEWLINE);
        a(sb);
        sb.setLength(sb.length() - StringUtil.NEWLINE.length());
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static final class a {
        private int a;
        private boolean b;
        private boolean c;

        a(int i, boolean z, boolean z2) {
            this.a = i;
            this.b = z;
            this.c = z2;
        }

        int a() {
            return this.a;
        }

        void a(int i) {
            this.a = i;
        }

        boolean b() {
            return this.b;
        }

        void a(boolean z) {
            this.b = z;
        }

        boolean c() {
            return this.c;
        }

        void b(boolean z) {
            this.c = z;
        }
    }
}
