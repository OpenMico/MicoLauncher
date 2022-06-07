package com.journeyapps.barcodescanner;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class DefaultDecoderFactory implements DecoderFactory {
    private Collection<BarcodeFormat> a;
    private Map<DecodeHintType, ?> b;
    private String c;
    private int d;

    public DefaultDecoderFactory() {
    }

    public DefaultDecoderFactory(Collection<BarcodeFormat> collection) {
        this.a = collection;
    }

    public DefaultDecoderFactory(Collection<BarcodeFormat> collection, Map<DecodeHintType, ?> map, String str, int i) {
        this.a = collection;
        this.b = map;
        this.c = str;
        this.d = i;
    }

    @Override // com.journeyapps.barcodescanner.DecoderFactory
    public Decoder createDecoder(Map<DecodeHintType, ?> map) {
        EnumMap enumMap = new EnumMap(DecodeHintType.class);
        enumMap.putAll(map);
        Map<DecodeHintType, ?> map2 = this.b;
        if (map2 != null) {
            enumMap.putAll(map2);
        }
        if (this.a != null) {
            enumMap.put((EnumMap) DecodeHintType.POSSIBLE_FORMATS, (DecodeHintType) this.a);
        }
        if (this.c != null) {
            enumMap.put((EnumMap) DecodeHintType.CHARACTER_SET, (DecodeHintType) this.c);
        }
        MultiFormatReader multiFormatReader = new MultiFormatReader();
        multiFormatReader.setHints(enumMap);
        switch (this.d) {
            case 0:
                return new Decoder(multiFormatReader);
            case 1:
                return new InvertedDecoder(multiFormatReader);
            case 2:
                return new MixedDecoder(multiFormatReader);
            default:
                return new Decoder(multiFormatReader);
        }
    }
}
