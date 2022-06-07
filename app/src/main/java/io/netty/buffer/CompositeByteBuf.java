package io.netty.buffer;

import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.ObjectUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import kotlin.UShort;

/* loaded from: classes4.dex */
public class CompositeByteBuf extends AbstractReferenceCountedByteBuf implements Iterable<ByteBuf> {
    static final /* synthetic */ boolean d = !CompositeByteBuf.class.desiredAssertionStatus();
    private static final ByteBuffer e = Unpooled.EMPTY_BUFFER.nioBuffer();
    private static final Iterator<ByteBuf> f = Collections.emptyList().iterator();
    private final ByteBufAllocator g;
    private final boolean h;
    private final List<a> i;
    private final int j;
    private boolean k;

    @Override // io.netty.buffer.AbstractReferenceCountedByteBuf, io.netty.buffer.ByteBuf, io.netty.util.ReferenceCounted
    public CompositeByteBuf touch() {
        return this;
    }

    @Override // io.netty.buffer.AbstractReferenceCountedByteBuf, io.netty.buffer.ByteBuf, io.netty.util.ReferenceCounted
    public CompositeByteBuf touch(Object obj) {
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf unwrap() {
        return null;
    }

    public CompositeByteBuf(ByteBufAllocator byteBufAllocator, boolean z, int i) {
        super(Integer.MAX_VALUE);
        if (byteBufAllocator != null) {
            this.g = byteBufAllocator;
            this.h = z;
            this.j = i;
            this.i = a(i);
            return;
        }
        throw new NullPointerException("alloc");
    }

    public CompositeByteBuf(ByteBufAllocator byteBufAllocator, boolean z, int i, ByteBuf... byteBufArr) {
        super(Integer.MAX_VALUE);
        if (byteBufAllocator == null) {
            throw new NullPointerException("alloc");
        } else if (i >= 2) {
            this.g = byteBufAllocator;
            this.h = z;
            this.j = i;
            this.i = a(i);
            a(false, 0, byteBufArr);
            b();
            setIndex(0, capacity());
        } else {
            throw new IllegalArgumentException("maxNumComponents: " + i + " (expected: >= 2)");
        }
    }

    public CompositeByteBuf(ByteBufAllocator byteBufAllocator, boolean z, int i, Iterable<ByteBuf> iterable) {
        super(Integer.MAX_VALUE);
        if (byteBufAllocator == null) {
            throw new NullPointerException("alloc");
        } else if (i >= 2) {
            this.g = byteBufAllocator;
            this.h = z;
            this.j = i;
            this.i = a(i);
            a(false, 0, iterable);
            b();
            setIndex(0, capacity());
        } else {
            throw new IllegalArgumentException("maxNumComponents: " + i + " (expected: >= 2)");
        }
    }

    private static List<a> a(int i) {
        return new ArrayList(Math.min(16, i));
    }

    public CompositeByteBuf(ByteBufAllocator byteBufAllocator) {
        super(Integer.MAX_VALUE);
        this.g = byteBufAllocator;
        this.h = false;
        this.j = 0;
        this.i = Collections.emptyList();
    }

    public CompositeByteBuf addComponent(ByteBuf byteBuf) {
        return addComponent(false, byteBuf);
    }

    public CompositeByteBuf addComponents(ByteBuf... byteBufArr) {
        return addComponents(false, byteBufArr);
    }

    public CompositeByteBuf addComponents(Iterable<ByteBuf> iterable) {
        return addComponents(false, iterable);
    }

    public CompositeByteBuf addComponent(int i, ByteBuf byteBuf) {
        return addComponent(false, i, byteBuf);
    }

    public CompositeByteBuf addComponent(boolean z, ByteBuf byteBuf) {
        ObjectUtil.checkNotNull(byteBuf, "buffer");
        a(z, this.i.size(), byteBuf);
        b();
        return this;
    }

    public CompositeByteBuf addComponents(boolean z, ByteBuf... byteBufArr) {
        a(z, this.i.size(), byteBufArr);
        b();
        return this;
    }

    public CompositeByteBuf addComponents(boolean z, Iterable<ByteBuf> iterable) {
        a(z, this.i.size(), iterable);
        b();
        return this;
    }

    public CompositeByteBuf addComponent(boolean z, int i, ByteBuf byteBuf) {
        ObjectUtil.checkNotNull(byteBuf, "buffer");
        a(z, i, byteBuf);
        b();
        return this;
    }

    private int a(boolean z, int i, ByteBuf byteBuf) {
        Throwable th;
        if (d || byteBuf != null) {
            boolean z2 = false;
            try {
                b(i);
                int readableBytes = byteBuf.readableBytes();
                a aVar = new a(byteBuf.order(ByteOrder.BIG_ENDIAN).slice());
                if (i == this.i.size()) {
                    z2 = this.i.add(aVar);
                    if (i == 0) {
                        aVar.d = readableBytes;
                    } else {
                        aVar.c = this.i.get(i - 1).d;
                        aVar.d = aVar.c + readableBytes;
                    }
                } else {
                    this.i.add(i, aVar);
                    if (readableBytes != 0) {
                        try {
                            c(i);
                        } catch (Throwable th2) {
                            th = th2;
                            z2 = true;
                            if (!z2) {
                                byteBuf.release();
                            }
                            throw th;
                        }
                    }
                    z2 = true;
                }
                if (z) {
                    writerIndex(writerIndex() + byteBuf.readableBytes());
                }
                if (!z2) {
                    byteBuf.release();
                }
                return i;
            } catch (Throwable th3) {
                th = th3;
            }
        } else {
            throw new AssertionError();
        }
    }

    public CompositeByteBuf addComponents(int i, ByteBuf... byteBufArr) {
        a(false, i, byteBufArr);
        b();
        return this;
    }

    private int a(boolean z, int i, ByteBuf... byteBufArr) {
        Throwable th;
        ObjectUtil.checkNotNull(byteBufArr, "buffers");
        int i2 = 0;
        try {
            b(i);
            while (true) {
                if (i2 >= byteBufArr.length) {
                    break;
                }
                i2++;
                try {
                    ByteBuf byteBuf = byteBufArr[i2];
                    if (byteBuf == null) {
                        i2 = i2;
                        break;
                    }
                    i = a(z, i, byteBuf) + 1;
                    int size = this.i.size();
                    if (i > size) {
                        i = size;
                    }
                    i2 = i2;
                } catch (Throwable th2) {
                    th = th2;
                    while (i2 < byteBufArr.length) {
                        ByteBuf byteBuf2 = byteBufArr[i2];
                        if (byteBuf2 != null) {
                            try {
                                byteBuf2.release();
                            } catch (Throwable unused) {
                            }
                        }
                        i2++;
                    }
                    throw th;
                }
            }
            while (i2 < byteBufArr.length) {
                ByteBuf byteBuf3 = byteBufArr[i2];
                if (byteBuf3 != null) {
                    try {
                        byteBuf3.release();
                    } catch (Throwable unused2) {
                    }
                }
                i2++;
            }
            return i;
        } catch (Throwable th3) {
            th = th3;
        }
    }

    public CompositeByteBuf addComponents(int i, Iterable<ByteBuf> iterable) {
        a(false, i, iterable);
        b();
        return this;
    }

    private int a(boolean z, int i, Iterable<ByteBuf> iterable) {
        if (iterable instanceof ByteBuf) {
            return a(z, i, (ByteBuf) iterable);
        }
        ObjectUtil.checkNotNull(iterable, "buffers");
        if (!(iterable instanceof Collection)) {
            iterable = new ArrayList();
            try {
                for (ByteBuf byteBuf : iterable) {
                    iterable.add(byteBuf);
                }
                iterable = iterable;
            } finally {
                if (iterable != iterable) {
                    for (ByteBuf byteBuf2 : iterable) {
                        if (byteBuf2 != null) {
                            try {
                                byteBuf2.release();
                            } catch (Throwable unused) {
                            }
                        }
                    }
                }
            }
        }
        Collection collection = (Collection) iterable;
        return a(z, i, (ByteBuf[]) collection.toArray(new ByteBuf[collection.size()]));
    }

    private void b() {
        int size = this.i.size();
        if (size > this.j) {
            ByteBuf e2 = e(this.i.get(size - 1).d);
            for (int i = 0; i < size; i++) {
                a aVar = this.i.get(i);
                e2.writeBytes(aVar.a);
                aVar.a();
            }
            a aVar2 = new a(e2);
            aVar2.d = aVar2.b;
            this.i.clear();
            this.i.add(aVar2);
        }
    }

    private void b(int i) {
        ensureAccessible();
        if (i < 0 || i > this.i.size()) {
            throw new IndexOutOfBoundsException(String.format("cIndex: %d (expected: >= 0 && <= numComponents(%d))", Integer.valueOf(i), Integer.valueOf(this.i.size())));
        }
    }

    private void c(int i, int i2) {
        ensureAccessible();
        if (i < 0 || i + i2 > this.i.size()) {
            throw new IndexOutOfBoundsException(String.format("cIndex: %d, numComponents: %d (expected: cIndex >= 0 && cIndex + numComponents <= totalNumComponents(%d))", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(this.i.size())));
        }
    }

    private void c(int i) {
        int size = this.i.size();
        if (size > i) {
            a aVar = this.i.get(i);
            if (i == 0) {
                aVar.c = 0;
                aVar.d = aVar.b;
                i++;
            }
            while (i < size) {
                a aVar2 = this.i.get(i);
                aVar2.c = this.i.get(i - 1).d;
                aVar2.d = aVar2.c + aVar2.b;
                i++;
            }
        }
    }

    public CompositeByteBuf removeComponent(int i) {
        b(i);
        a remove = this.i.remove(i);
        remove.a();
        if (remove.b > 0) {
            c(i);
        }
        return this;
    }

    public CompositeByteBuf removeComponents(int i, int i2) {
        c(i, i2);
        if (i2 == 0) {
            return this;
        }
        List<a> subList = this.i.subList(i, i2 + i);
        boolean z = false;
        for (a aVar : subList) {
            if (aVar.b > 0) {
                z = true;
            }
            aVar.a();
        }
        subList.clear();
        if (z) {
            c(i);
        }
        return this;
    }

    @Override // java.lang.Iterable
    public Iterator<ByteBuf> iterator() {
        ensureAccessible();
        if (this.i.isEmpty()) {
            return f;
        }
        return new b();
    }

    public List<ByteBuf> decompose(int i, int i2) {
        checkIndex(i, i2);
        if (i2 == 0) {
            return Collections.emptyList();
        }
        int componentIndex = toComponentIndex(i);
        ArrayList arrayList = new ArrayList(this.i.size());
        a aVar = this.i.get(componentIndex);
        ByteBuf duplicate = aVar.a.duplicate();
        duplicate.readerIndex(i - aVar.c);
        while (true) {
            int readableBytes = duplicate.readableBytes();
            if (i2 <= readableBytes) {
                duplicate.writerIndex(duplicate.readerIndex() + i2);
                arrayList.add(duplicate);
                break;
            }
            arrayList.add(duplicate);
            i2 -= readableBytes;
            componentIndex++;
            duplicate = this.i.get(componentIndex).a.duplicate();
            if (i2 <= 0) {
                break;
            }
        }
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            arrayList.set(i3, ((ByteBuf) arrayList.get(i3)).slice());
        }
        return arrayList;
    }

    @Override // io.netty.buffer.ByteBuf
    public boolean isDirect() {
        int size = this.i.size();
        if (size == 0) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!this.i.get(i).a.isDirect()) {
                return false;
            }
        }
        return true;
    }

    @Override // io.netty.buffer.ByteBuf
    public boolean hasArray() {
        switch (this.i.size()) {
            case 0:
                return true;
            case 1:
                return this.i.get(0).a.hasArray();
            default:
                return false;
        }
    }

    @Override // io.netty.buffer.ByteBuf
    public byte[] array() {
        switch (this.i.size()) {
            case 0:
                return EmptyArrays.EMPTY_BYTES;
            case 1:
                return this.i.get(0).a.array();
            default:
                throw new UnsupportedOperationException();
        }
    }

    @Override // io.netty.buffer.ByteBuf
    public int arrayOffset() {
        switch (this.i.size()) {
            case 0:
                return 0;
            case 1:
                return this.i.get(0).a.arrayOffset();
            default:
                throw new UnsupportedOperationException();
        }
    }

    @Override // io.netty.buffer.ByteBuf
    public boolean hasMemoryAddress() {
        switch (this.i.size()) {
            case 0:
                return Unpooled.EMPTY_BUFFER.hasMemoryAddress();
            case 1:
                return this.i.get(0).a.hasMemoryAddress();
            default:
                return false;
        }
    }

    @Override // io.netty.buffer.ByteBuf
    public long memoryAddress() {
        switch (this.i.size()) {
            case 0:
                return Unpooled.EMPTY_BUFFER.memoryAddress();
            case 1:
                return this.i.get(0).a.memoryAddress();
            default:
                throw new UnsupportedOperationException();
        }
    }

    @Override // io.netty.buffer.ByteBuf
    public int capacity() {
        int size = this.i.size();
        if (size == 0) {
            return 0;
        }
        return this.i.get(size - 1).d;
    }

    @Override // io.netty.buffer.ByteBuf
    public CompositeByteBuf capacity(int i) {
        ensureAccessible();
        if (i < 0 || i > maxCapacity()) {
            throw new IllegalArgumentException("newCapacity: " + i);
        }
        int capacity = capacity();
        if (i > capacity) {
            int i2 = i - capacity;
            if (this.i.size() < this.j) {
                ByteBuf e2 = e(i2);
                e2.setIndex(0, i2);
                a(false, this.i.size(), e2);
            } else {
                ByteBuf e3 = e(i2);
                e3.setIndex(0, i2);
                a(false, this.i.size(), e3);
                b();
            }
        } else if (i < capacity) {
            int i3 = capacity - i;
            List<a> list = this.i;
            ListIterator<a> listIterator = list.listIterator(list.size());
            while (true) {
                if (!listIterator.hasPrevious()) {
                    break;
                }
                a previous = listIterator.previous();
                if (i3 < previous.b) {
                    a aVar = new a(previous.a.slice(0, previous.b - i3));
                    aVar.c = previous.c;
                    aVar.d = aVar.c + aVar.b;
                    listIterator.set(aVar);
                    break;
                }
                i3 -= previous.b;
                listIterator.remove();
            }
            if (readerIndex() > i) {
                setIndex(i, i);
            } else if (writerIndex() > i) {
                writerIndex(i);
            }
        }
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBufAllocator alloc() {
        return this.g;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteOrder order() {
        return ByteOrder.BIG_ENDIAN;
    }

    public int numComponents() {
        return this.i.size();
    }

    public int maxNumComponents() {
        return this.j;
    }

    public int toComponentIndex(int i) {
        checkIndex(i);
        int size = this.i.size();
        int i2 = 0;
        while (i2 <= size) {
            int i3 = (i2 + size) >>> 1;
            a aVar = this.i.get(i3);
            if (i >= aVar.d) {
                i2 = i3 + 1;
            } else if (i >= aVar.c) {
                return i3;
            } else {
                size = i3 - 1;
            }
        }
        throw new Error("should not reach here");
    }

    public int toByteIndex(int i) {
        b(i);
        return this.i.get(i).c;
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public byte getByte(int i) {
        return _getByte(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public byte _getByte(int i) {
        a d2 = d(i);
        return d2.a.getByte(i - d2.c);
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public short _getShort(int i) {
        a d2 = d(i);
        if (i + 2 <= d2.d) {
            return d2.a.getShort(i - d2.c);
        }
        if (order() == ByteOrder.BIG_ENDIAN) {
            return (short) ((_getByte(i + 1) & 255) | ((_getByte(i) & 255) << 8));
        }
        return (short) (((_getByte(i + 1) & 255) << 8) | (_getByte(i) & 255));
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public short _getShortLE(int i) {
        a d2 = d(i);
        if (i + 2 <= d2.d) {
            return d2.a.getShortLE(i - d2.c);
        }
        if (order() == ByteOrder.BIG_ENDIAN) {
            return (short) (((_getByte(i + 1) & 255) << 8) | (_getByte(i) & 255));
        }
        return (short) ((_getByte(i + 1) & 255) | ((_getByte(i) & 255) << 8));
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public int _getUnsignedMedium(int i) {
        a d2 = d(i);
        if (i + 3 <= d2.d) {
            return d2.a.getUnsignedMedium(i - d2.c);
        }
        if (order() == ByteOrder.BIG_ENDIAN) {
            return (_getByte(i + 2) & 255) | ((_getShort(i) & UShort.MAX_VALUE) << 8);
        }
        return ((_getByte(i + 2) & 255) << 16) | (_getShort(i) & UShort.MAX_VALUE);
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public int _getUnsignedMediumLE(int i) {
        a d2 = d(i);
        if (i + 3 <= d2.d) {
            return d2.a.getUnsignedMediumLE(i - d2.c);
        }
        if (order() == ByteOrder.BIG_ENDIAN) {
            return ((_getByte(i + 2) & 255) << 16) | (_getShortLE(i) & UShort.MAX_VALUE);
        }
        return (_getByte(i + 2) & 255) | ((_getShortLE(i) & UShort.MAX_VALUE) << 8);
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public int _getInt(int i) {
        a d2 = d(i);
        if (i + 4 <= d2.d) {
            return d2.a.getInt(i - d2.c);
        }
        if (order() == ByteOrder.BIG_ENDIAN) {
            return (_getShort(i + 2) & UShort.MAX_VALUE) | ((_getShort(i) & UShort.MAX_VALUE) << 16);
        }
        return ((_getShort(i + 2) & UShort.MAX_VALUE) << 16) | (_getShort(i) & UShort.MAX_VALUE);
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public int _getIntLE(int i) {
        a d2 = d(i);
        if (i + 4 <= d2.d) {
            return d2.a.getIntLE(i - d2.c);
        }
        if (order() == ByteOrder.BIG_ENDIAN) {
            return ((_getShortLE(i + 2) & UShort.MAX_VALUE) << 16) | (_getShortLE(i) & UShort.MAX_VALUE);
        }
        return (_getShortLE(i + 2) & UShort.MAX_VALUE) | ((_getShortLE(i) & UShort.MAX_VALUE) << 16);
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public long _getLong(int i) {
        a d2 = d(i);
        if (i + 8 <= d2.d) {
            return d2.a.getLong(i - d2.c);
        }
        if (order() == ByteOrder.BIG_ENDIAN) {
            return ((_getInt(i) & 4294967295L) << 32) | (_getInt(i + 4) & 4294967295L);
        }
        return (_getInt(i) & 4294967295L) | ((4294967295L & _getInt(i + 4)) << 32);
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public long _getLongLE(int i) {
        a d2 = d(i);
        if (i + 8 <= d2.d) {
            return d2.a.getLongLE(i - d2.c);
        }
        if (order() == ByteOrder.BIG_ENDIAN) {
            return (_getIntLE(i) & 4294967295L) | ((4294967295L & _getIntLE(i + 4)) << 32);
        }
        return ((_getIntLE(i) & 4294967295L) << 32) | (_getIntLE(i + 4) & 4294967295L);
    }

    @Override // io.netty.buffer.ByteBuf
    public CompositeByteBuf getBytes(int i, byte[] bArr, int i2, int i3) {
        checkDstIndex(i, i3, i2, bArr.length);
        if (i3 == 0) {
            return this;
        }
        int componentIndex = toComponentIndex(i);
        while (i3 > 0) {
            a aVar = this.i.get(componentIndex);
            ByteBuf byteBuf = aVar.a;
            int i4 = i - aVar.c;
            int min = Math.min(i3, byteBuf.capacity() - i4);
            byteBuf.getBytes(i4, bArr, i2, min);
            i += min;
            i2 += min;
            i3 -= min;
            componentIndex++;
        }
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public CompositeByteBuf getBytes(int i, ByteBuffer byteBuffer) {
        int limit = byteBuffer.limit();
        int remaining = byteBuffer.remaining();
        checkIndex(i, remaining);
        if (remaining == 0) {
            return this;
        }
        int componentIndex = toComponentIndex(i);
        while (remaining > 0) {
            try {
                a aVar = this.i.get(componentIndex);
                ByteBuf byteBuf = aVar.a;
                int i2 = i - aVar.c;
                int min = Math.min(remaining, byteBuf.capacity() - i2);
                byteBuffer.limit(byteBuffer.position() + min);
                byteBuf.getBytes(i2, byteBuffer);
                i += min;
                remaining -= min;
                componentIndex++;
            } finally {
                byteBuffer.limit(limit);
            }
        }
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public CompositeByteBuf getBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        checkDstIndex(i, i3, i2, byteBuf.capacity());
        if (i3 == 0) {
            return this;
        }
        int componentIndex = toComponentIndex(i);
        while (i3 > 0) {
            a aVar = this.i.get(componentIndex);
            ByteBuf byteBuf2 = aVar.a;
            int i4 = i - aVar.c;
            int min = Math.min(i3, byteBuf2.capacity() - i4);
            byteBuf2.getBytes(i4, byteBuf, i2, min);
            i += min;
            i2 += min;
            i3 -= min;
            componentIndex++;
        }
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public int getBytes(int i, GatheringByteChannel gatheringByteChannel, int i2) throws IOException {
        if (nioBufferCount() == 1) {
            return gatheringByteChannel.write(internalNioBuffer(i, i2));
        }
        long write = gatheringByteChannel.write(nioBuffers(i, i2));
        if (write > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) write;
    }

    @Override // io.netty.buffer.ByteBuf
    public int getBytes(int i, FileChannel fileChannel, long j, int i2) throws IOException {
        if (nioBufferCount() == 1) {
            return fileChannel.write(internalNioBuffer(i, i2), j);
        }
        long j2 = 0;
        for (ByteBuffer byteBuffer : nioBuffers(i, i2)) {
            j2 += fileChannel.write(byteBuffer, j + j2);
        }
        if (j2 > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) j2;
    }

    @Override // io.netty.buffer.ByteBuf
    public CompositeByteBuf getBytes(int i, OutputStream outputStream, int i2) throws IOException {
        checkIndex(i, i2);
        if (i2 == 0) {
            return this;
        }
        int componentIndex = toComponentIndex(i);
        while (i2 > 0) {
            a aVar = this.i.get(componentIndex);
            ByteBuf byteBuf = aVar.a;
            int i3 = i - aVar.c;
            int min = Math.min(i2, byteBuf.capacity() - i3);
            byteBuf.getBytes(i3, outputStream, min);
            i += min;
            i2 -= min;
            componentIndex++;
        }
        return this;
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf setByte(int i, int i2) {
        a d2 = d(i);
        d2.a.setByte(i - d2.c, i2);
        return this;
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public void _setByte(int i, int i2) {
        setByte(i, i2);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf setShort(int i, int i2) {
        return (CompositeByteBuf) super.setShort(i, i2);
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public void _setShort(int i, int i2) {
        a d2 = d(i);
        if (i + 2 <= d2.d) {
            d2.a.setShort(i - d2.c, i2);
        } else if (order() == ByteOrder.BIG_ENDIAN) {
            _setByte(i, (byte) (i2 >>> 8));
            _setByte(i + 1, (byte) i2);
        } else {
            _setByte(i, (byte) i2);
            _setByte(i + 1, (byte) (i2 >>> 8));
        }
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public void _setShortLE(int i, int i2) {
        a d2 = d(i);
        if (i + 2 <= d2.d) {
            d2.a.setShortLE(i - d2.c, i2);
        } else if (order() == ByteOrder.BIG_ENDIAN) {
            _setByte(i, (byte) i2);
            _setByte(i + 1, (byte) (i2 >>> 8));
        } else {
            _setByte(i, (byte) (i2 >>> 8));
            _setByte(i + 1, (byte) i2);
        }
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf setMedium(int i, int i2) {
        return (CompositeByteBuf) super.setMedium(i, i2);
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public void _setMedium(int i, int i2) {
        a d2 = d(i);
        if (i + 3 <= d2.d) {
            d2.a.setMedium(i - d2.c, i2);
        } else if (order() == ByteOrder.BIG_ENDIAN) {
            _setShort(i, (short) (i2 >> 8));
            _setByte(i + 2, (byte) i2);
        } else {
            _setShort(i, (short) i2);
            _setByte(i + 2, (byte) (i2 >>> 16));
        }
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public void _setMediumLE(int i, int i2) {
        a d2 = d(i);
        if (i + 3 <= d2.d) {
            d2.a.setMediumLE(i - d2.c, i2);
        } else if (order() == ByteOrder.BIG_ENDIAN) {
            _setShortLE(i, (short) i2);
            _setByte(i + 2, (byte) (i2 >>> 16));
        } else {
            _setShortLE(i, (short) (i2 >> 8));
            _setByte(i + 2, (byte) i2);
        }
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf setInt(int i, int i2) {
        return (CompositeByteBuf) super.setInt(i, i2);
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public void _setInt(int i, int i2) {
        a d2 = d(i);
        if (i + 4 <= d2.d) {
            d2.a.setInt(i - d2.c, i2);
        } else if (order() == ByteOrder.BIG_ENDIAN) {
            _setShort(i, (short) (i2 >>> 16));
            _setShort(i + 2, (short) i2);
        } else {
            _setShort(i, (short) i2);
            _setShort(i + 2, (short) (i2 >>> 16));
        }
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public void _setIntLE(int i, int i2) {
        a d2 = d(i);
        if (i + 4 <= d2.d) {
            d2.a.setIntLE(i - d2.c, i2);
        } else if (order() == ByteOrder.BIG_ENDIAN) {
            _setShortLE(i, (short) i2);
            _setShortLE(i + 2, (short) (i2 >>> 16));
        } else {
            _setShortLE(i, (short) (i2 >>> 16));
            _setShortLE(i + 2, (short) i2);
        }
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf setLong(int i, long j) {
        return (CompositeByteBuf) super.setLong(i, j);
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public void _setLong(int i, long j) {
        a d2 = d(i);
        if (i + 8 <= d2.d) {
            d2.a.setLong(i - d2.c, j);
        } else if (order() == ByteOrder.BIG_ENDIAN) {
            _setInt(i, (int) (j >>> 32));
            _setInt(i + 4, (int) j);
        } else {
            _setInt(i, (int) j);
            _setInt(i + 4, (int) (j >>> 32));
        }
    }

    @Override // io.netty.buffer.AbstractByteBuf
    public void _setLongLE(int i, long j) {
        a d2 = d(i);
        if (i + 8 <= d2.d) {
            d2.a.setLongLE(i - d2.c, j);
        } else if (order() == ByteOrder.BIG_ENDIAN) {
            _setIntLE(i, (int) j);
            _setIntLE(i + 4, (int) (j >>> 32));
        } else {
            _setIntLE(i, (int) (j >>> 32));
            _setIntLE(i + 4, (int) j);
        }
    }

    @Override // io.netty.buffer.ByteBuf
    public CompositeByteBuf setBytes(int i, byte[] bArr, int i2, int i3) {
        checkSrcIndex(i, i3, i2, bArr.length);
        if (i3 == 0) {
            return this;
        }
        int componentIndex = toComponentIndex(i);
        while (i3 > 0) {
            a aVar = this.i.get(componentIndex);
            ByteBuf byteBuf = aVar.a;
            int i4 = i - aVar.c;
            int min = Math.min(i3, byteBuf.capacity() - i4);
            byteBuf.setBytes(i4, bArr, i2, min);
            i += min;
            i2 += min;
            i3 -= min;
            componentIndex++;
        }
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public CompositeByteBuf setBytes(int i, ByteBuffer byteBuffer) {
        int limit = byteBuffer.limit();
        int remaining = byteBuffer.remaining();
        checkIndex(i, remaining);
        if (remaining == 0) {
            return this;
        }
        int componentIndex = toComponentIndex(i);
        while (remaining > 0) {
            try {
                a aVar = this.i.get(componentIndex);
                ByteBuf byteBuf = aVar.a;
                int i2 = i - aVar.c;
                int min = Math.min(remaining, byteBuf.capacity() - i2);
                byteBuffer.limit(byteBuffer.position() + min);
                byteBuf.setBytes(i2, byteBuffer);
                i += min;
                remaining -= min;
                componentIndex++;
            } finally {
                byteBuffer.limit(limit);
            }
        }
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public CompositeByteBuf setBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        checkSrcIndex(i, i3, i2, byteBuf.capacity());
        if (i3 == 0) {
            return this;
        }
        int componentIndex = toComponentIndex(i);
        while (i3 > 0) {
            a aVar = this.i.get(componentIndex);
            ByteBuf byteBuf2 = aVar.a;
            int i4 = i - aVar.c;
            int min = Math.min(i3, byteBuf2.capacity() - i4);
            byteBuf2.setBytes(i4, byteBuf, i2, min);
            i += min;
            i2 += min;
            i3 -= min;
            componentIndex++;
        }
        return this;
    }

    @Override // io.netty.buffer.ByteBuf
    public int setBytes(int i, InputStream inputStream, int i2) throws IOException {
        checkIndex(i, i2);
        if (i2 == 0) {
            return inputStream.read(EmptyArrays.EMPTY_BYTES);
        }
        int componentIndex = toComponentIndex(i);
        int i3 = 0;
        while (true) {
            a aVar = this.i.get(componentIndex);
            ByteBuf byteBuf = aVar.a;
            int i4 = i - aVar.c;
            int min = Math.min(i2, byteBuf.capacity() - i4);
            if (min == 0) {
                componentIndex++;
                continue;
            } else {
                int bytes = byteBuf.setBytes(i4, inputStream, min);
                if (bytes < 0) {
                    if (i3 == 0) {
                        return -1;
                    }
                } else if (bytes == min) {
                    i += min;
                    i2 -= min;
                    i3 += min;
                    componentIndex++;
                    continue;
                } else {
                    i += bytes;
                    i2 -= bytes;
                    i3 += bytes;
                    continue;
                }
            }
            if (i2 <= 0) {
                break;
            }
        }
        return i3;
    }

    @Override // io.netty.buffer.ByteBuf
    public int setBytes(int i, ScatteringByteChannel scatteringByteChannel, int i2) throws IOException {
        checkIndex(i, i2);
        if (i2 == 0) {
            return scatteringByteChannel.read(e);
        }
        int componentIndex = toComponentIndex(i);
        int i3 = 0;
        while (true) {
            a aVar = this.i.get(componentIndex);
            ByteBuf byteBuf = aVar.a;
            int i4 = i - aVar.c;
            int min = Math.min(i2, byteBuf.capacity() - i4);
            if (min != 0) {
                int bytes = byteBuf.setBytes(i4, scatteringByteChannel, min);
                if (bytes == 0) {
                    break;
                } else if (bytes < 0) {
                    if (i3 == 0) {
                        return -1;
                    }
                } else if (bytes == min) {
                    i += min;
                    i2 -= min;
                    i3 += min;
                    componentIndex++;
                    continue;
                } else {
                    i += bytes;
                    i2 -= bytes;
                    i3 += bytes;
                    continue;
                }
            } else {
                componentIndex++;
                continue;
            }
            if (i2 <= 0) {
                break;
            }
        }
        return i3;
    }

    @Override // io.netty.buffer.ByteBuf
    public int setBytes(int i, FileChannel fileChannel, long j, int i2) throws IOException {
        checkIndex(i, i2);
        if (i2 == 0) {
            return fileChannel.read(e, j);
        }
        int componentIndex = toComponentIndex(i);
        int i3 = 0;
        while (true) {
            a aVar = this.i.get(componentIndex);
            ByteBuf byteBuf = aVar.a;
            int i4 = i - aVar.c;
            int min = Math.min(i2, byteBuf.capacity() - i4);
            if (min != 0) {
                int bytes = byteBuf.setBytes(i4, fileChannel, j + i3, min);
                if (bytes == 0) {
                    break;
                } else if (bytes < 0) {
                    if (i3 == 0) {
                        return -1;
                    }
                } else if (bytes == min) {
                    i += min;
                    i2 -= min;
                    i3 += min;
                    componentIndex++;
                    continue;
                } else {
                    i += bytes;
                    i2 -= bytes;
                    i3 += bytes;
                    continue;
                }
            } else {
                componentIndex++;
                continue;
            }
            if (i2 <= 0) {
                break;
            }
        }
        return i3;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuf copy(int i, int i2) {
        checkIndex(i, i2);
        ByteBuf buffer = Unpooled.buffer(i2);
        if (i2 != 0) {
            a(i, i2, toComponentIndex(i), buffer);
        }
        return buffer;
    }

    private void a(int i, int i2, int i3, ByteBuf byteBuf) {
        int i4 = 0;
        while (i2 > 0) {
            a aVar = this.i.get(i3);
            ByteBuf byteBuf2 = aVar.a;
            int i5 = i - aVar.c;
            int min = Math.min(i2, byteBuf2.capacity() - i5);
            byteBuf2.getBytes(i5, byteBuf, i4, min);
            i += min;
            i4 += min;
            i2 -= min;
            i3++;
        }
        byteBuf.writerIndex(byteBuf.capacity());
    }

    public ByteBuf component(int i) {
        return internalComponent(i).duplicate();
    }

    public ByteBuf componentAtOffset(int i) {
        return internalComponentAtOffset(i).duplicate();
    }

    public ByteBuf internalComponent(int i) {
        b(i);
        return this.i.get(i).a;
    }

    public ByteBuf internalComponentAtOffset(int i) {
        return d(i).a;
    }

    private a d(int i) {
        checkIndex(i);
        int size = this.i.size();
        int i2 = 0;
        while (i2 <= size) {
            int i3 = (i2 + size) >>> 1;
            a aVar = this.i.get(i3);
            if (i >= aVar.d) {
                i2 = i3 + 1;
            } else if (i < aVar.c) {
                size = i3 - 1;
            } else if (d || aVar.b != 0) {
                return aVar;
            } else {
                throw new AssertionError();
            }
        }
        throw new Error("should not reach here");
    }

    @Override // io.netty.buffer.ByteBuf
    public int nioBufferCount() {
        switch (this.i.size()) {
            case 0:
                return 1;
            case 1:
                return this.i.get(0).a.nioBufferCount();
            default:
                int size = this.i.size();
                int i = 0;
                for (int i2 = 0; i2 < size; i2++) {
                    i += this.i.get(i2).a.nioBufferCount();
                }
                return i;
        }
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuffer internalNioBuffer(int i, int i2) {
        switch (this.i.size()) {
            case 0:
                return e;
            case 1:
                return this.i.get(0).a.internalNioBuffer(i, i2);
            default:
                throw new UnsupportedOperationException();
        }
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuffer nioBuffer(int i, int i2) {
        checkIndex(i, i2);
        switch (this.i.size()) {
            case 0:
                return e;
            case 1:
                if (this.i.get(0).a.nioBufferCount() == 1) {
                    return this.i.get(0).a.nioBuffer(i, i2);
                }
                break;
        }
        ByteBuffer order = ByteBuffer.allocate(i2).order(order());
        for (ByteBuffer byteBuffer : nioBuffers(i, i2)) {
            order.put(byteBuffer);
        }
        order.flip();
        return order;
    }

    @Override // io.netty.buffer.ByteBuf
    public ByteBuffer[] nioBuffers(int i, int i2) {
        checkIndex(i, i2);
        if (i2 == 0) {
            return new ByteBuffer[]{e};
        }
        ArrayList arrayList = new ArrayList(this.i.size());
        int componentIndex = toComponentIndex(i);
        while (i2 > 0) {
            a aVar = this.i.get(componentIndex);
            ByteBuf byteBuf = aVar.a;
            int i3 = i - aVar.c;
            int min = Math.min(i2, byteBuf.capacity() - i3);
            switch (byteBuf.nioBufferCount()) {
                case 0:
                    throw new UnsupportedOperationException();
                case 1:
                    arrayList.add(byteBuf.nioBuffer(i3, min));
                    break;
                default:
                    Collections.addAll(arrayList, byteBuf.nioBuffers(i3, min));
                    break;
            }
            i += min;
            i2 -= min;
            componentIndex++;
        }
        return (ByteBuffer[]) arrayList.toArray(new ByteBuffer[arrayList.size()]);
    }

    public CompositeByteBuf consolidate() {
        ensureAccessible();
        int numComponents = numComponents();
        if (numComponents <= 1) {
            return this;
        }
        ByteBuf e2 = e(this.i.get(numComponents - 1).d);
        for (int i = 0; i < numComponents; i++) {
            a aVar = this.i.get(i);
            e2.writeBytes(aVar.a);
            aVar.a();
        }
        this.i.clear();
        this.i.add(new a(e2));
        c(0);
        return this;
    }

    public CompositeByteBuf consolidate(int i, int i2) {
        c(i, i2);
        if (i2 <= 1) {
            return this;
        }
        int i3 = i2 + i;
        ByteBuf e2 = e(this.i.get(i3 - 1).d - this.i.get(i).c);
        for (int i4 = i; i4 < i3; i4++) {
            a aVar = this.i.get(i4);
            e2.writeBytes(aVar.a);
            aVar.a();
        }
        this.i.subList(i + 1, i3).clear();
        this.i.set(i, new a(e2));
        c(i);
        return this;
    }

    public CompositeByteBuf discardReadComponents() {
        ensureAccessible();
        int readerIndex = readerIndex();
        if (readerIndex == 0) {
            return this;
        }
        int writerIndex = writerIndex();
        if (readerIndex == writerIndex && writerIndex == capacity()) {
            for (a aVar : this.i) {
                aVar.a();
            }
            this.i.clear();
            setIndex(0, 0);
            adjustMarkers(readerIndex);
            return this;
        }
        int componentIndex = toComponentIndex(readerIndex);
        for (int i = 0; i < componentIndex; i++) {
            this.i.get(i).a();
        }
        this.i.subList(0, componentIndex).clear();
        int i2 = this.i.get(0).c;
        c(0);
        setIndex(readerIndex - i2, writerIndex - i2);
        adjustMarkers(i2);
        return this;
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf discardReadBytes() {
        ensureAccessible();
        int readerIndex = readerIndex();
        if (readerIndex == 0) {
            return this;
        }
        int writerIndex = writerIndex();
        if (readerIndex == writerIndex && writerIndex == capacity()) {
            for (a aVar : this.i) {
                aVar.a();
            }
            this.i.clear();
            setIndex(0, 0);
            adjustMarkers(readerIndex);
            return this;
        }
        int componentIndex = toComponentIndex(readerIndex);
        for (int i = 0; i < componentIndex; i++) {
            this.i.get(i).a();
        }
        this.i.subList(0, componentIndex).clear();
        a aVar2 = this.i.get(0);
        int i2 = readerIndex - aVar2.c;
        if (i2 == aVar2.b) {
            this.i.remove(0);
        } else {
            this.i.set(0, new a(aVar2.a.slice(i2, aVar2.b - i2)));
        }
        c(0);
        setIndex(0, writerIndex - readerIndex);
        adjustMarkers(readerIndex);
        return this;
    }

    private ByteBuf e(int i) {
        return this.h ? alloc().directBuffer(i) : alloc().heapBuffer(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public String toString() {
        String abstractReferenceCountedByteBuf = super.toString();
        String substring = abstractReferenceCountedByteBuf.substring(0, abstractReferenceCountedByteBuf.length() - 1);
        return substring + ", components=" + this.i.size() + ')';
    }

    /* loaded from: classes4.dex */
    public static final class a {
        final ByteBuf a;
        final int b;
        int c;
        int d;

        a(ByteBuf byteBuf) {
            this.a = byteBuf;
            this.b = byteBuf.readableBytes();
        }

        void a() {
            this.a.release();
        }
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf readerIndex(int i) {
        return (CompositeByteBuf) super.readerIndex(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf writerIndex(int i) {
        return (CompositeByteBuf) super.writerIndex(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf setIndex(int i, int i2) {
        return (CompositeByteBuf) super.setIndex(i, i2);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf clear() {
        return (CompositeByteBuf) super.clear();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf markReaderIndex() {
        return (CompositeByteBuf) super.markReaderIndex();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf resetReaderIndex() {
        return (CompositeByteBuf) super.resetReaderIndex();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf markWriterIndex() {
        return (CompositeByteBuf) super.markWriterIndex();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf resetWriterIndex() {
        return (CompositeByteBuf) super.resetWriterIndex();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf ensureWritable(int i) {
        return (CompositeByteBuf) super.ensureWritable(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf getBytes(int i, ByteBuf byteBuf) {
        return (CompositeByteBuf) super.getBytes(i, byteBuf);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf getBytes(int i, ByteBuf byteBuf, int i2) {
        return (CompositeByteBuf) super.getBytes(i, byteBuf, i2);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf getBytes(int i, byte[] bArr) {
        return (CompositeByteBuf) super.getBytes(i, bArr);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf setBoolean(int i, boolean z) {
        return (CompositeByteBuf) super.setBoolean(i, z);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf setChar(int i, int i2) {
        return (CompositeByteBuf) super.setChar(i, i2);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf setFloat(int i, float f2) {
        return (CompositeByteBuf) super.setFloat(i, f2);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf setDouble(int i, double d2) {
        return (CompositeByteBuf) super.setDouble(i, d2);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf setBytes(int i, ByteBuf byteBuf) {
        return (CompositeByteBuf) super.setBytes(i, byteBuf);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf setBytes(int i, ByteBuf byteBuf, int i2) {
        return (CompositeByteBuf) super.setBytes(i, byteBuf, i2);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf setBytes(int i, byte[] bArr) {
        return (CompositeByteBuf) super.setBytes(i, bArr);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf setZero(int i, int i2) {
        return (CompositeByteBuf) super.setZero(i, i2);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf readBytes(ByteBuf byteBuf) {
        return (CompositeByteBuf) super.readBytes(byteBuf);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf readBytes(ByteBuf byteBuf, int i) {
        return (CompositeByteBuf) super.readBytes(byteBuf, i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf readBytes(ByteBuf byteBuf, int i, int i2) {
        return (CompositeByteBuf) super.readBytes(byteBuf, i, i2);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf readBytes(byte[] bArr) {
        return (CompositeByteBuf) super.readBytes(bArr);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf readBytes(byte[] bArr, int i, int i2) {
        return (CompositeByteBuf) super.readBytes(bArr, i, i2);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf readBytes(ByteBuffer byteBuffer) {
        return (CompositeByteBuf) super.readBytes(byteBuffer);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf readBytes(OutputStream outputStream, int i) throws IOException {
        return (CompositeByteBuf) super.readBytes(outputStream, i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf skipBytes(int i) {
        return (CompositeByteBuf) super.skipBytes(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf writeBoolean(boolean z) {
        return (CompositeByteBuf) super.writeBoolean(z);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf writeByte(int i) {
        return (CompositeByteBuf) super.writeByte(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf writeShort(int i) {
        return (CompositeByteBuf) super.writeShort(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf writeMedium(int i) {
        return (CompositeByteBuf) super.writeMedium(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf writeInt(int i) {
        return (CompositeByteBuf) super.writeInt(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf writeLong(long j) {
        return (CompositeByteBuf) super.writeLong(j);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf writeChar(int i) {
        return (CompositeByteBuf) super.writeChar(i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf writeFloat(float f2) {
        return (CompositeByteBuf) super.writeFloat(f2);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf writeDouble(double d2) {
        return (CompositeByteBuf) super.writeDouble(d2);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf writeBytes(ByteBuf byteBuf) {
        return (CompositeByteBuf) super.writeBytes(byteBuf);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf writeBytes(ByteBuf byteBuf, int i) {
        return (CompositeByteBuf) super.writeBytes(byteBuf, i);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf writeBytes(ByteBuf byteBuf, int i, int i2) {
        return (CompositeByteBuf) super.writeBytes(byteBuf, i, i2);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf writeBytes(byte[] bArr) {
        return (CompositeByteBuf) super.writeBytes(bArr);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf writeBytes(byte[] bArr, int i, int i2) {
        return (CompositeByteBuf) super.writeBytes(bArr, i, i2);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf writeBytes(ByteBuffer byteBuffer) {
        return (CompositeByteBuf) super.writeBytes(byteBuffer);
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf writeZero(int i) {
        return (CompositeByteBuf) super.writeZero(i);
    }

    @Override // io.netty.buffer.AbstractReferenceCountedByteBuf, io.netty.buffer.ByteBuf, io.netty.util.ReferenceCounted
    public CompositeByteBuf retain(int i) {
        return (CompositeByteBuf) super.retain(i);
    }

    @Override // io.netty.buffer.AbstractReferenceCountedByteBuf, io.netty.buffer.ByteBuf, io.netty.util.ReferenceCounted
    public CompositeByteBuf retain() {
        return (CompositeByteBuf) super.retain();
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public ByteBuffer[] nioBuffers() {
        return nioBuffers(readerIndex(), readableBytes());
    }

    @Override // io.netty.buffer.AbstractByteBuf, io.netty.buffer.ByteBuf
    public CompositeByteBuf discardSomeReadBytes() {
        return discardReadComponents();
    }

    @Override // io.netty.buffer.AbstractReferenceCountedByteBuf
    public void deallocate() {
        if (!this.k) {
            this.k = true;
            int size = this.i.size();
            for (int i = 0; i < size; i++) {
                this.i.get(i).a();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public final class b implements Iterator<ByteBuf> {
        private final int b;
        private int c;

        private b() {
            CompositeByteBuf.this = r1;
            this.b = CompositeByteBuf.this.i.size();
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.b > this.c;
        }

        /* renamed from: a */
        public ByteBuf next() {
            if (this.b != CompositeByteBuf.this.i.size()) {
                throw new ConcurrentModificationException();
            } else if (hasNext()) {
                try {
                    List list = CompositeByteBuf.this.i;
                    int i = this.c;
                    this.c = i + 1;
                    return ((a) list.get(i)).a;
                } catch (IndexOutOfBoundsException unused) {
                    throw new ConcurrentModificationException();
                }
            } else {
                throw new NoSuchElementException();
            }
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException("Read-Only");
        }
    }
}
