package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: AnyAIDecoder.java */
/* loaded from: classes2.dex */
public final class j extends AbstractExpandedDecoder {
    /* JADX INFO: Access modifiers changed from: package-private */
    public j(BitArray bitArray) {
        super(bitArray);
    }

    @Override // com.google.zxing.oned.rss.expanded.decoders.AbstractExpandedDecoder
    public String parseInformation() throws NotFoundException, FormatException {
        return getGeneralDecoder().a(new StringBuilder(), 5);
    }
}
