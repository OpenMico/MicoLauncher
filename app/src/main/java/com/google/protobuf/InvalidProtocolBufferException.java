package com.google.protobuf;

import java.io.IOException;

/* loaded from: classes2.dex */
public class InvalidProtocolBufferException extends IOException {
    private static final long serialVersionUID = -1616151763072450476L;
    private MessageLite unfinishedMessage = null;

    public InvalidProtocolBufferException(String str) {
        super(str);
    }

    public InvalidProtocolBufferException(IOException iOException) {
        super(iOException.getMessage(), iOException);
    }

    public InvalidProtocolBufferException(String str, IOException iOException) {
        super(str, iOException);
    }

    public InvalidProtocolBufferException setUnfinishedMessage(MessageLite messageLite) {
        this.unfinishedMessage = messageLite;
        return this;
    }

    public MessageLite getUnfinishedMessage() {
        return this.unfinishedMessage;
    }

    public IOException unwrapIOException() {
        return getCause() instanceof IOException ? (IOException) getCause() : this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static InvalidProtocolBufferException a() {
        return new InvalidProtocolBufferException("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either that the input has been truncated or that an embedded message misreported its own length.");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static InvalidProtocolBufferException b() {
        return new InvalidProtocolBufferException("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static InvalidProtocolBufferException c() {
        return new InvalidProtocolBufferException("CodedInputStream encountered a malformed varint.");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static InvalidProtocolBufferException d() {
        return new InvalidProtocolBufferException("Protocol message contained an invalid tag (zero).");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static InvalidProtocolBufferException e() {
        return new InvalidProtocolBufferException("Protocol message end-group tag did not match expected tag.");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static InvalidWireTypeException f() {
        return new InvalidWireTypeException("Protocol message tag had invalid wire type.");
    }

    /* loaded from: classes2.dex */
    public static class InvalidWireTypeException extends InvalidProtocolBufferException {
        private static final long serialVersionUID = 3283890091615336259L;

        public InvalidWireTypeException(String str) {
            super(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static InvalidProtocolBufferException g() {
        return new InvalidProtocolBufferException("Protocol message had too many levels of nesting.  May be malicious.  Use CodedInputStream.setRecursionLimit() to increase the depth limit.");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static InvalidProtocolBufferException h() {
        return new InvalidProtocolBufferException("Protocol message was too large.  May be malicious.  Use CodedInputStream.setSizeLimit() to increase the size limit.");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static InvalidProtocolBufferException i() {
        return new InvalidProtocolBufferException("Failed to parse the message.");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static InvalidProtocolBufferException j() {
        return new InvalidProtocolBufferException("Protocol message had invalid UTF-8.");
    }
}
