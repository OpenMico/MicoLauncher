package io.netty.handler.timeout;

import com.xiaomi.account.openauth.XiaomiOAuthConstants;
import io.netty.util.internal.ObjectUtil;

/* loaded from: classes4.dex */
public class IdleStateEvent {
    private final IdleState a;
    private final boolean b;
    public static final IdleStateEvent FIRST_READER_IDLE_STATE_EVENT = new IdleStateEvent(IdleState.READER_IDLE, true);
    public static final IdleStateEvent READER_IDLE_STATE_EVENT = new IdleStateEvent(IdleState.READER_IDLE, false);
    public static final IdleStateEvent FIRST_WRITER_IDLE_STATE_EVENT = new IdleStateEvent(IdleState.WRITER_IDLE, true);
    public static final IdleStateEvent WRITER_IDLE_STATE_EVENT = new IdleStateEvent(IdleState.WRITER_IDLE, false);
    public static final IdleStateEvent FIRST_ALL_IDLE_STATE_EVENT = new IdleStateEvent(IdleState.ALL_IDLE, true);
    public static final IdleStateEvent ALL_IDLE_STATE_EVENT = new IdleStateEvent(IdleState.ALL_IDLE, false);

    protected IdleStateEvent(IdleState idleState, boolean z) {
        this.a = (IdleState) ObjectUtil.checkNotNull(idleState, XiaomiOAuthConstants.EXTRA_STATE_2);
        this.b = z;
    }

    public IdleState state() {
        return this.a;
    }

    public boolean isFirst() {
        return this.b;
    }
}
