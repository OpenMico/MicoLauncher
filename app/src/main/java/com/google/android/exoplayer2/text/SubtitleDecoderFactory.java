package com.google.android.exoplayer2.text;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.text.cea.Cea608Decoder;
import com.google.android.exoplayer2.text.cea.Cea708Decoder;
import com.google.android.exoplayer2.text.dvb.DvbDecoder;
import com.google.android.exoplayer2.text.pgs.PgsDecoder;
import com.google.android.exoplayer2.text.ssa.SsaDecoder;
import com.google.android.exoplayer2.text.subrip.SubripDecoder;
import com.google.android.exoplayer2.text.ttml.TtmlDecoder;
import com.google.android.exoplayer2.text.tx3g.Tx3gDecoder;
import com.google.android.exoplayer2.text.webvtt.Mp4WebvttDecoder;
import com.google.android.exoplayer2.text.webvtt.WebvttDecoder;
import com.google.android.exoplayer2.util.MimeTypes;

/* loaded from: classes2.dex */
public interface SubtitleDecoderFactory {
    public static final SubtitleDecoderFactory DEFAULT = new SubtitleDecoderFactory() { // from class: com.google.android.exoplayer2.text.SubtitleDecoderFactory.1
        @Override // com.google.android.exoplayer2.text.SubtitleDecoderFactory
        public boolean supportsFormat(Format format) {
            String str = format.sampleMimeType;
            return MimeTypes.TEXT_VTT.equals(str) || MimeTypes.TEXT_SSA.equals(str) || MimeTypes.APPLICATION_TTML.equals(str) || MimeTypes.APPLICATION_MP4VTT.equals(str) || MimeTypes.APPLICATION_SUBRIP.equals(str) || MimeTypes.APPLICATION_TX3G.equals(str) || MimeTypes.APPLICATION_CEA608.equals(str) || MimeTypes.APPLICATION_MP4CEA608.equals(str) || MimeTypes.APPLICATION_CEA708.equals(str) || MimeTypes.APPLICATION_DVBSUBS.equals(str) || MimeTypes.APPLICATION_PGS.equals(str);
        }

        @Override // com.google.android.exoplayer2.text.SubtitleDecoderFactory
        public SubtitleDecoder createDecoder(Format format) {
            String str = format.sampleMimeType;
            if (str != null) {
                char c = 65535;
                switch (str.hashCode()) {
                    case -1351681404:
                        if (str.equals(MimeTypes.APPLICATION_DVBSUBS)) {
                            c = '\t';
                            break;
                        }
                        break;
                    case -1248334819:
                        if (str.equals(MimeTypes.APPLICATION_PGS)) {
                            c = '\n';
                            break;
                        }
                        break;
                    case -1026075066:
                        if (str.equals(MimeTypes.APPLICATION_MP4VTT)) {
                            c = 2;
                            break;
                        }
                        break;
                    case -1004728940:
                        if (str.equals(MimeTypes.TEXT_VTT)) {
                            c = 0;
                            break;
                        }
                        break;
                    case 691401887:
                        if (str.equals(MimeTypes.APPLICATION_TX3G)) {
                            c = 5;
                            break;
                        }
                        break;
                    case 822864842:
                        if (str.equals(MimeTypes.TEXT_SSA)) {
                            c = 1;
                            break;
                        }
                        break;
                    case 930165504:
                        if (str.equals(MimeTypes.APPLICATION_MP4CEA608)) {
                            c = 7;
                            break;
                        }
                        break;
                    case 1566015601:
                        if (str.equals(MimeTypes.APPLICATION_CEA608)) {
                            c = 6;
                            break;
                        }
                        break;
                    case 1566016562:
                        if (str.equals(MimeTypes.APPLICATION_CEA708)) {
                            c = '\b';
                            break;
                        }
                        break;
                    case 1668750253:
                        if (str.equals(MimeTypes.APPLICATION_SUBRIP)) {
                            c = 4;
                            break;
                        }
                        break;
                    case 1693976202:
                        if (str.equals(MimeTypes.APPLICATION_TTML)) {
                            c = 3;
                            break;
                        }
                        break;
                }
                switch (c) {
                    case 0:
                        return new WebvttDecoder();
                    case 1:
                        return new SsaDecoder(format.initializationData);
                    case 2:
                        return new Mp4WebvttDecoder();
                    case 3:
                        return new TtmlDecoder();
                    case 4:
                        return new SubripDecoder();
                    case 5:
                        return new Tx3gDecoder(format.initializationData);
                    case 6:
                    case 7:
                        return new Cea608Decoder(str, format.accessibilityChannel, Cea608Decoder.MIN_DATA_CHANNEL_TIMEOUT_MS);
                    case '\b':
                        return new Cea708Decoder(format.accessibilityChannel, format.initializationData);
                    case '\t':
                        return new DvbDecoder(format.initializationData);
                    case '\n':
                        return new PgsDecoder();
                }
            }
            String valueOf = String.valueOf(str);
            throw new IllegalArgumentException(valueOf.length() != 0 ? "Attempted to create decoder for unsupported MIME type: ".concat(valueOf) : new String("Attempted to create decoder for unsupported MIME type: "));
        }
    };

    SubtitleDecoder createDecoder(Format format);

    boolean supportsFormat(Format format);
}
