package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: AI01393xDecoder.java */
/* loaded from: classes2.dex */
public final class d extends h {
    /* JADX INFO: Access modifiers changed from: package-private */
    public d(BitArray bitArray) {
        super(bitArray);
    }

    @Override // com.google.zxing.oned.rss.expanded.decoders.AbstractExpandedDecoder
    public String parseInformation() throws NotFoundException, FormatException {
        if (getInformation().getSize() >= 48) {
            StringBuilder sb = new StringBuilder();
            b(sb, 8);
            int a = getGeneralDecoder().a(48, 2);
            sb.append("(393");
            sb.append(a);
            sb.append(')');
            int a2 = getGeneralDecoder().a(50, 10);
            if (a2 / 100 == 0) {
                sb.append('0');
            }
            if (a2 / 10 == 0) {
                sb.append('0');
            }
            sb.append(a2);
            sb.append(getGeneralDecoder().a(60, (String) null).a());
            return sb.toString();
        }
        throw NotFoundException.getNotFoundInstance();
    }
}
