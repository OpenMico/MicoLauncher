package io.netty.buffer;

import io.netty.util.ResourceLeak;
import io.netty.util.ResourceLeakDetector;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;

/* loaded from: classes4.dex */
public abstract class AbstractByteBufAllocator implements ByteBufAllocator {
    private final boolean a;
    private final ByteBuf b;

    protected abstract ByteBuf newDirectBuffer(int i, int i2);

    protected abstract ByteBuf newHeapBuffer(int i, int i2);

    protected static ByteBuf toLeakAwareBuffer(ByteBuf byteBuf) {
        switch (ResourceLeakDetector.getLevel()) {
            case SIMPLE:
                ResourceLeak open = AbstractByteBuf.a.open(byteBuf);
                if (open != null) {
                    return new v(byteBuf, open);
                }
                break;
            case ADVANCED:
            case PARANOID:
                ResourceLeak open2 = AbstractByteBuf.a.open(byteBuf);
                if (open2 != null) {
                    return new c(byteBuf, open2);
                }
                break;
        }
        return byteBuf;
    }

    protected static CompositeByteBuf toLeakAwareBuffer(CompositeByteBuf compositeByteBuf) {
        switch (ResourceLeakDetector.getLevel()) {
            case SIMPLE:
                ResourceLeak open = AbstractByteBuf.a.open(compositeByteBuf);
                if (open != null) {
                    return new w(compositeByteBuf, open);
                }
                break;
            case ADVANCED:
            case PARANOID:
                ResourceLeak open2 = AbstractByteBuf.a.open(compositeByteBuf);
                if (open2 != null) {
                    return new d(compositeByteBuf, open2);
                }
                break;
        }
        return compositeByteBuf;
    }

    protected AbstractByteBufAllocator() {
        this(false);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public AbstractByteBufAllocator(boolean z) {
        this.a = z && PlatformDependent.hasUnsafe();
        this.b = new EmptyByteBuf(this);
    }

    @Override // io.netty.buffer.ByteBufAllocator
    public ByteBuf buffer() {
        if (this.a) {
            return directBuffer();
        }
        return heapBuffer();
    }

    @Override // io.netty.buffer.ByteBufAllocator
    public ByteBuf buffer(int i) {
        if (this.a) {
            return directBuffer(i);
        }
        return heapBuffer(i);
    }

    @Override // io.netty.buffer.ByteBufAllocator
    public ByteBuf buffer(int i, int i2) {
        if (this.a) {
            return directBuffer(i, i2);
        }
        return heapBuffer(i, i2);
    }

    @Override // io.netty.buffer.ByteBufAllocator
    public ByteBuf ioBuffer() {
        if (PlatformDependent.hasUnsafe()) {
            return directBuffer(256);
        }
        return heapBuffer(256);
    }

    @Override // io.netty.buffer.ByteBufAllocator
    public ByteBuf ioBuffer(int i) {
        if (PlatformDependent.hasUnsafe()) {
            return directBuffer(i);
        }
        return heapBuffer(i);
    }

    @Override // io.netty.buffer.ByteBufAllocator
    public ByteBuf ioBuffer(int i, int i2) {
        if (PlatformDependent.hasUnsafe()) {
            return directBuffer(i, i2);
        }
        return heapBuffer(i, i2);
    }

    @Override // io.netty.buffer.ByteBufAllocator
    public ByteBuf heapBuffer() {
        return heapBuffer(256, Integer.MAX_VALUE);
    }

    @Override // io.netty.buffer.ByteBufAllocator
    public ByteBuf heapBuffer(int i) {
        return heapBuffer(i, Integer.MAX_VALUE);
    }

    @Override // io.netty.buffer.ByteBufAllocator
    public ByteBuf heapBuffer(int i, int i2) {
        if (i == 0 && i2 == 0) {
            return this.b;
        }
        a(i, i2);
        return newHeapBuffer(i, i2);
    }

    @Override // io.netty.buffer.ByteBufAllocator
    public ByteBuf directBuffer() {
        return directBuffer(256, Integer.MAX_VALUE);
    }

    @Override // io.netty.buffer.ByteBufAllocator
    public ByteBuf directBuffer(int i) {
        return directBuffer(i, Integer.MAX_VALUE);
    }

    @Override // io.netty.buffer.ByteBufAllocator
    public ByteBuf directBuffer(int i, int i2) {
        if (i == 0 && i2 == 0) {
            return this.b;
        }
        a(i, i2);
        return newDirectBuffer(i, i2);
    }

    @Override // io.netty.buffer.ByteBufAllocator
    public CompositeByteBuf compositeBuffer() {
        if (this.a) {
            return compositeDirectBuffer();
        }
        return compositeHeapBuffer();
    }

    @Override // io.netty.buffer.ByteBufAllocator
    public CompositeByteBuf compositeBuffer(int i) {
        if (this.a) {
            return compositeDirectBuffer(i);
        }
        return compositeHeapBuffer(i);
    }

    @Override // io.netty.buffer.ByteBufAllocator
    public CompositeByteBuf compositeHeapBuffer() {
        return compositeHeapBuffer(16);
    }

    @Override // io.netty.buffer.ByteBufAllocator
    public CompositeByteBuf compositeHeapBuffer(int i) {
        return toLeakAwareBuffer(new CompositeByteBuf(this, false, i));
    }

    @Override // io.netty.buffer.ByteBufAllocator
    public CompositeByteBuf compositeDirectBuffer() {
        return compositeDirectBuffer(16);
    }

    @Override // io.netty.buffer.ByteBufAllocator
    public CompositeByteBuf compositeDirectBuffer(int i) {
        return toLeakAwareBuffer(new CompositeByteBuf(this, true, i));
    }

    private static void a(int i, int i2) {
        if (i < 0) {
            throw new IllegalArgumentException("initialCapacity: " + i + " (expectd: 0+)");
        } else if (i > i2) {
            throw new IllegalArgumentException(String.format("initialCapacity: %d (expected: not greater than maxCapacity(%d)", Integer.valueOf(i), Integer.valueOf(i2)));
        }
    }

    public String toString() {
        return StringUtil.simpleClassName(this) + "(directByDefault: " + this.a + ')';
    }

    @Override // io.netty.buffer.ByteBufAllocator
    public int calculateNewCapacity(int i, int i2) {
        if (i < 0) {
            throw new IllegalArgumentException("minNewCapacity: " + i + " (expectd: 0+)");
        } else if (i > i2) {
            throw new IllegalArgumentException(String.format("minNewCapacity: %d (expected: not greater than maxCapacity(%d)", Integer.valueOf(i), Integer.valueOf(i2)));
        } else if (i == 4194304) {
            return 4194304;
        } else {
            if (i > 4194304) {
                int i3 = (i / 4194304) * 4194304;
                return i3 > i2 - 4194304 ? i2 : i3 + 4194304;
            }
            int i4 = 64;
            while (i4 < i) {
                i4 <<= 1;
            }
            return Math.min(i4, i2);
        }
    }
}
