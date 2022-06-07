package io.netty.channel.epoll;

/* loaded from: classes4.dex */
final class NativeStaticallyReferencedJniMethods {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static native int epollerr();

    /* JADX INFO: Access modifiers changed from: package-private */
    public static native int epollet();

    /* JADX INFO: Access modifiers changed from: package-private */
    public static native int epollin();

    /* JADX INFO: Access modifiers changed from: package-private */
    public static native int epollout();

    /* JADX INFO: Access modifiers changed from: package-private */
    public static native int epollrdhup();

    /* JADX INFO: Access modifiers changed from: package-private */
    public static native int iovMax();

    /* JADX INFO: Access modifiers changed from: package-private */
    public static native boolean isSupportingSendmmsg();

    /* JADX INFO: Access modifiers changed from: package-private */
    public static native boolean isSupportingTcpFastopen();

    /* JADX INFO: Access modifiers changed from: package-private */
    public static native String kernelVersion();

    /* JADX INFO: Access modifiers changed from: package-private */
    public static native long ssizeMax();

    /* JADX INFO: Access modifiers changed from: package-private */
    public static native int tcpMd5SigMaxKeyLen();

    /* JADX INFO: Access modifiers changed from: package-private */
    public static native int uioMaxIov();
}
