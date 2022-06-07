package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: AI01AndOtherAIs.java */
/* loaded from: classes2.dex */
public final class g extends h {
    /* JADX INFO: Access modifiers changed from: package-private */
    public g(BitArray bitArray) {
        super(bitArray);
    }

    @Override // com.google.zxing.oned.rss.expanded.decoders.AbstractExpandedDecoder
    public String parseInformation() throws NotFoundException, FormatException {
        StringBuilder sb = new StringBuilder();
        sb.append("(01)");
        int length = sb.length();
        sb.append(getGeneralDecoder().a(4, 4));
        a(sb, 8, length);
        return getGeneralDecoder().a(sb, 48);
    }
}
