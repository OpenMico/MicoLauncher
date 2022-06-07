package com.journeyapps.barcodescanner;

import com.google.zxing.DecodeHintType;
import java.util.Map;

/* loaded from: classes2.dex */
public interface DecoderFactory {
    Decoder createDecoder(Map<DecodeHintType, ?> map);
}
