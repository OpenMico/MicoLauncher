package org.fourthline.cling.support.model.dlna.types;

/* loaded from: classes5.dex */
public class CodedDataBuffer {
    private Long size;
    private TransferMechanism tranfer;

    /* loaded from: classes5.dex */
    public enum TransferMechanism {
        IMMEDIATELY,
        TIMESTAMP,
        OTHER
    }

    public CodedDataBuffer(Long l, TransferMechanism transferMechanism) {
        this.size = l;
        this.tranfer = transferMechanism;
    }

    public Long getSize() {
        return this.size;
    }

    public TransferMechanism getTranfer() {
        return this.tranfer;
    }
}
