package org.fourthline.cling.support.model;

import org.fourthline.cling.model.types.UnsignedIntegerFourBytes;

/* loaded from: classes5.dex */
public class SearchResult {
    protected UnsignedIntegerFourBytes containerUpdateID;
    protected UnsignedIntegerFourBytes count;
    protected String result;
    protected UnsignedIntegerFourBytes totalMatches;

    public SearchResult(String str, UnsignedIntegerFourBytes unsignedIntegerFourBytes, UnsignedIntegerFourBytes unsignedIntegerFourBytes2, UnsignedIntegerFourBytes unsignedIntegerFourBytes3) {
        this.result = str;
        this.count = unsignedIntegerFourBytes;
        this.totalMatches = unsignedIntegerFourBytes2;
        this.containerUpdateID = unsignedIntegerFourBytes3;
    }

    public SearchResult(String str, long j, long j2) {
        this(str, j, j2, 0L);
    }

    public SearchResult(String str, long j, long j2, long j3) {
        this(str, new UnsignedIntegerFourBytes(j), new UnsignedIntegerFourBytes(j2), new UnsignedIntegerFourBytes(j3));
    }

    public String getResult() {
        return this.result;
    }

    public UnsignedIntegerFourBytes getCount() {
        return this.count;
    }

    public long getCountLong() {
        return this.count.getValue().longValue();
    }

    public UnsignedIntegerFourBytes getTotalMatches() {
        return this.totalMatches;
    }

    public long getTotalMatchesLong() {
        return this.totalMatches.getValue().longValue();
    }

    public UnsignedIntegerFourBytes getContainerUpdateID() {
        return this.containerUpdateID;
    }

    public long getContainerUpdateIDLong() {
        return this.containerUpdateID.getValue().longValue();
    }
}
