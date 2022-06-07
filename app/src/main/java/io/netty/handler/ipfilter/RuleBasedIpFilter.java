package io.netty.handler.ipfilter;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import java.net.InetSocketAddress;

@ChannelHandler.Sharable
/* loaded from: classes4.dex */
public class RuleBasedIpFilter extends AbstractRemoteAddressFilter<InetSocketAddress> {
    private final IpFilterRule[] a;

    public RuleBasedIpFilter(IpFilterRule... ipFilterRuleArr) {
        if (ipFilterRuleArr != null) {
            this.a = ipFilterRuleArr;
            return;
        }
        throw new NullPointerException("rules");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean accept(ChannelHandlerContext channelHandlerContext, InetSocketAddress inetSocketAddress) throws Exception {
        IpFilterRule ipFilterRule;
        IpFilterRule[] ipFilterRuleArr = this.a;
        int length = ipFilterRuleArr.length;
        for (int i = 0; i < length && (ipFilterRule = ipFilterRuleArr[i]) != null; i++) {
            if (ipFilterRule.matches(inetSocketAddress)) {
                return ipFilterRule.ruleType() == IpFilterRuleType.ACCEPT;
            }
        }
        return true;
    }
}
