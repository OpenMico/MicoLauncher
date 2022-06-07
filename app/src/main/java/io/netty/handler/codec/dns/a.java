package io.netty.handler.codec.dns;

import com.xiaomi.micolauncher.api.model.Video;
import io.netty.channel.AddressedEnvelope;
import io.netty.util.internal.StringUtil;
import java.net.SocketAddress;

/* compiled from: DnsMessageUtil.java */
/* loaded from: classes4.dex */
final class a {
    public static StringBuilder a(StringBuilder sb, DnsQuery dnsQuery) {
        b(sb, dnsQuery);
        b(sb, (DnsMessage) dnsQuery);
        return sb;
    }

    public static StringBuilder a(StringBuilder sb, DnsResponse dnsResponse) {
        b(sb, dnsResponse);
        b(sb, (DnsMessage) dnsResponse);
        return sb;
    }

    public static StringBuilder a(StringBuilder sb, int i) {
        String str;
        int i2 = i & 65535;
        switch (i2) {
            case 1:
                str = "IN";
                break;
            case 2:
                str = "CSNET";
                break;
            case 3:
                str = "CHAOS";
                break;
            case 4:
                str = "HESIOD";
                break;
            default:
                switch (i2) {
                    case DnsRecord.CLASS_NONE /* 254 */:
                        str = "NONE";
                        break;
                    case 255:
                        str = Video.Category.ANY_CATEGORY_TYPE;
                        break;
                    default:
                        str = null;
                        break;
                }
        }
        if (str != null) {
            sb.append(str);
        } else {
            sb.append("UNKNOWN(");
            sb.append(i2);
            sb.append(')');
        }
        return sb;
    }

    private static void b(StringBuilder sb, DnsQuery dnsQuery) {
        sb.append(StringUtil.simpleClassName(dnsQuery));
        sb.append('(');
        StringBuilder a = a(sb, (DnsMessage) dnsQuery);
        a.append(dnsQuery.id());
        a.append(", ");
        a.append(dnsQuery.opCode());
        if (dnsQuery.isRecursionDesired()) {
            sb.append(", RD");
        }
        if (dnsQuery.z() != 0) {
            sb.append(", Z: ");
            sb.append(dnsQuery.z());
        }
        sb.append(')');
    }

    private static void b(StringBuilder sb, DnsResponse dnsResponse) {
        boolean z;
        sb.append(StringUtil.simpleClassName(dnsResponse));
        sb.append('(');
        StringBuilder a = a(sb, (DnsMessage) dnsResponse);
        a.append(dnsResponse.id());
        a.append(", ");
        a.append(dnsResponse.opCode());
        a.append(", ");
        a.append(dnsResponse.code());
        a.append(StringUtil.COMMA);
        if (dnsResponse.isRecursionDesired()) {
            sb.append(" RD");
            z = false;
        } else {
            z = true;
        }
        if (dnsResponse.isAuthoritativeAnswer()) {
            sb.append(" AA");
            z = false;
        }
        if (dnsResponse.isTruncated()) {
            sb.append(" TC");
            z = false;
        }
        if (dnsResponse.isRecursionAvailable()) {
            sb.append(" RA");
            z = false;
        }
        if (dnsResponse.z() != 0) {
            if (!z) {
                sb.append(StringUtil.COMMA);
            }
            sb.append(" Z: ");
            sb.append(dnsResponse.z());
        }
        if (z) {
            sb.setCharAt(sb.length() - 1, ')');
        } else {
            sb.append(')');
        }
    }

    private static StringBuilder a(StringBuilder sb, DnsMessage dnsMessage) {
        if (!(dnsMessage instanceof AddressedEnvelope)) {
            return sb;
        }
        AddressedEnvelope addressedEnvelope = (AddressedEnvelope) dnsMessage;
        SocketAddress sender = addressedEnvelope.sender();
        if (sender != null) {
            sb.append("from: ");
            sb.append(sender);
            sb.append(", ");
        }
        SocketAddress recipient = addressedEnvelope.recipient();
        if (recipient != null) {
            sb.append("to: ");
            sb.append(recipient);
            sb.append(", ");
        }
        return sb;
    }

    private static void b(StringBuilder sb, DnsMessage dnsMessage) {
        a(sb, dnsMessage, DnsSection.QUESTION);
        a(sb, dnsMessage, DnsSection.ANSWER);
        a(sb, dnsMessage, DnsSection.AUTHORITY);
        a(sb, dnsMessage, DnsSection.ADDITIONAL);
    }

    private static void a(StringBuilder sb, DnsMessage dnsMessage, DnsSection dnsSection) {
        int count = dnsMessage.count(dnsSection);
        for (int i = 0; i < count; i++) {
            sb.append(StringUtil.NEWLINE);
            sb.append('\t');
            sb.append(dnsMessage.recordAt(dnsSection, i));
        }
    }
}
