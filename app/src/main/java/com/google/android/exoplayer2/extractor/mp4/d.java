package com.google.android.exoplayer2.extractor.mp4;

import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.GaplessInfoHolder;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.id3.ApicFrame;
import com.google.android.exoplayer2.metadata.id3.CommentFrame;
import com.google.android.exoplayer2.metadata.id3.Id3Frame;
import com.google.android.exoplayer2.metadata.id3.InternalFrame;
import com.google.android.exoplayer2.metadata.id3.TextInformationFrame;
import com.google.android.exoplayer2.metadata.mp4.MdtaMetadataEntry;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableByteArray;
import org.fourthline.cling.support.model.dlna.DLNAProfiles;

/* compiled from: MetadataUtil.java */
/* loaded from: classes2.dex */
final class d {
    @VisibleForTesting
    static final String[] a = {"Blues", "Classic Rock", "Country", "Dance", "Disco", "Funk", "Grunge", "Hip-Hop", "Jazz", "Metal", "New Age", "Oldies", "Other", "Pop", "R&B", "Rap", "Reggae", "Rock", "Techno", "Industrial", "Alternative", "Ska", "Death Metal", "Pranks", "Soundtrack", "Euro-Techno", "Ambient", "Trip-Hop", "Vocal", "Jazz+Funk", "Fusion", "Trance", "Classical", "Instrumental", "Acid", "House", "Game", "Sound Clip", "Gospel", "Noise", "AlternRock", "Bass", "Soul", "Punk", "Space", "Meditative", "Instrumental Pop", "Instrumental Rock", "Ethnic", "Gothic", "Darkwave", "Techno-Industrial", "Electronic", "Pop-Folk", "Eurodance", "Dream", "Southern Rock", "Comedy", "Cult", "Gangsta", "Top 40", "Christian Rap", "Pop/Funk", "Jungle", "Native American", "Cabaret", "New Wave", "Psychadelic", "Rave", "Showtunes", "Trailer", "Lo-Fi", "Tribal", "Acid Punk", "Acid Jazz", "Polka", "Retro", "Musical", "Rock & Roll", "Hard Rock", "Folk", "Folk-Rock", "National Folk", "Swing", "Fast Fusion", "Bebob", "Latin", "Revival", "Celtic", "Bluegrass", "Avantgarde", "Gothic Rock", "Progressive Rock", "Psychedelic Rock", "Symphonic Rock", "Slow Rock", "Big Band", "Chorus", "Easy Listening", "Acoustic", "Humour", "Speech", "Chanson", "Opera", "Chamber Music", "Sonata", "Symphony", "Booty Bass", "Primus", "Porn Groove", "Satire", "Slow Jam", "Club", "Tango", "Samba", "Folklore", "Ballad", "Power Ballad", "Rhythmic Soul", "Freestyle", "Duet", "Punk Rock", "Drum Solo", "A capella", "Euro-House", "Dance Hall", "Goa", "Drum & Bass", "Club-House", "Hardcore", "Terror", "Indie", "BritPop", "Afro-Punk", "Polsk Punk", "Beat", "Christian Gangsta Rap", "Heavy Metal", "Black Metal", "Crossover", "Contemporary Christian", "Christian Rock", "Merengue", "Salsa", "Thrash Metal", "Anime", "Jpop", "Synthpop", "Abstract", "Art Rock", "Baroque", "Bhangra", "Big beat", "Breakbeat", "Chillout", "Downtempo", "Dub", "EBM", "Eclectic", "Electro", "Electroclash", "Emo", "Experimental", "Garage", "Global", "IDM", "Illbient", "Industro-Goth", "Jam Band", "Krautrock", "Leftfield", "Lounge", "Math Rock", "New Romantic", "Nu-Breakz", "Post-Punk", "Post-Rock", "Psytrance", "Shoegaze", "Space Rock", "Trop Rock", "World Music", "Neoclassical", "Audiobook", "Audio theatre", "Neue Deutsche Welle", "Podcast", "Indie-Rock", "G-Funk", "Dubstep", "Garage Rock", "Psybient"};

    public static void a(int i, @Nullable Metadata metadata, @Nullable Metadata metadata2, Format.Builder builder, Metadata... metadataArr) {
        Metadata metadata3;
        Metadata metadata4 = new Metadata(new Metadata.Entry[0]);
        if (i == 1) {
            if (metadata != null) {
                metadata3 = metadata;
            }
            metadata3 = metadata4;
        } else {
            if (i == 2 && metadata2 != null) {
                for (int i2 = 0; i2 < metadata2.length(); i2++) {
                    Metadata.Entry entry = metadata2.get(i2);
                    if (entry instanceof MdtaMetadataEntry) {
                        MdtaMetadataEntry mdtaMetadataEntry = (MdtaMetadataEntry) entry;
                        if (MdtaMetadataEntry.KEY_ANDROID_CAPTURE_FPS.equals(mdtaMetadataEntry.key)) {
                            metadata3 = new Metadata(mdtaMetadataEntry);
                            break;
                        }
                    }
                }
            }
            metadata3 = metadata4;
        }
        for (Metadata metadata5 : metadataArr) {
            metadata3 = metadata3.copyWithAppendedEntriesFrom(metadata5);
        }
        if (metadata3.length() > 0) {
            builder.setMetadata(metadata3);
        }
    }

    public static void a(int i, GaplessInfoHolder gaplessInfoHolder, Format.Builder builder) {
        if (i == 1 && gaplessInfoHolder.hasGaplessInfo()) {
            builder.setEncoderDelay(gaplessInfoHolder.encoderDelay).setEncoderPadding(gaplessInfoHolder.encoderPadding);
        }
    }

    @Nullable
    public static Metadata.Entry a(ParsableByteArray parsableByteArray) {
        int position = parsableByteArray.getPosition() + parsableByteArray.readInt();
        int readInt = parsableByteArray.readInt();
        int i = (readInt >> 24) & 255;
        try {
            if (i == 169 || i == 253) {
                int i2 = 16777215 & readInt;
                if (i2 == 6516084) {
                    return a(readInt, parsableByteArray);
                }
                if (i2 == 7233901 || i2 == 7631467) {
                    return a(readInt, "TIT2", parsableByteArray);
                }
                if (i2 == 6516589 || i2 == 7828084) {
                    return a(readInt, "TCOM", parsableByteArray);
                }
                if (i2 == 6578553) {
                    return a(readInt, "TDRC", parsableByteArray);
                }
                if (i2 == 4280916) {
                    return a(readInt, "TPE1", parsableByteArray);
                }
                if (i2 == 7630703) {
                    return a(readInt, "TSSE", parsableByteArray);
                }
                if (i2 == 6384738) {
                    return a(readInt, "TALB", parsableByteArray);
                }
                if (i2 == 7108978) {
                    return a(readInt, "USLT", parsableByteArray);
                }
                if (i2 == 6776174) {
                    return a(readInt, "TCON", parsableByteArray);
                }
                if (i2 == 6779504) {
                    return a(readInt, "TIT1", parsableByteArray);
                }
            } else if (readInt == 1735291493) {
                return b(parsableByteArray);
            } else {
                if (readInt == 1684632427) {
                    return b(readInt, "TPOS", parsableByteArray);
                }
                if (readInt == 1953655662) {
                    return b(readInt, "TRCK", parsableByteArray);
                }
                if (readInt == 1953329263) {
                    return a(readInt, "TBPM", parsableByteArray, true, false);
                }
                if (readInt == 1668311404) {
                    return a(readInt, "TCMP", parsableByteArray, true, true);
                }
                if (readInt == 1668249202) {
                    return c(parsableByteArray);
                }
                if (readInt == 1631670868) {
                    return a(readInt, "TPE2", parsableByteArray);
                }
                if (readInt == 1936682605) {
                    return a(readInt, "TSOT", parsableByteArray);
                }
                if (readInt == 1936679276) {
                    return a(readInt, "TSO2", parsableByteArray);
                }
                if (readInt == 1936679282) {
                    return a(readInt, "TSOA", parsableByteArray);
                }
                if (readInt == 1936679265) {
                    return a(readInt, "TSOP", parsableByteArray);
                }
                if (readInt == 1936679791) {
                    return a(readInt, "TSOC", parsableByteArray);
                }
                if (readInt == 1920233063) {
                    return a(readInt, "ITUNESADVISORY", parsableByteArray, false, false);
                }
                if (readInt == 1885823344) {
                    return a(readInt, "ITUNESGAPLESS", parsableByteArray, false, true);
                }
                if (readInt == 1936683886) {
                    return a(readInt, "TVSHOWSORT", parsableByteArray);
                }
                if (readInt == 1953919848) {
                    return a(readInt, "TVSHOW", parsableByteArray);
                }
                if (readInt == 757935405) {
                    return a(parsableByteArray, position);
                }
            }
            String valueOf = String.valueOf(a.c(readInt));
            Log.d("MetadataUtil", valueOf.length() != 0 ? "Skipped unknown metadata entry: ".concat(valueOf) : new String("Skipped unknown metadata entry: "));
            return null;
        } finally {
            parsableByteArray.setPosition(position);
        }
    }

    @Nullable
    public static MdtaMetadataEntry a(ParsableByteArray parsableByteArray, int i, String str) {
        while (true) {
            int position = parsableByteArray.getPosition();
            if (position >= i) {
                return null;
            }
            int readInt = parsableByteArray.readInt();
            if (parsableByteArray.readInt() == 1684108385) {
                int readInt2 = parsableByteArray.readInt();
                int readInt3 = parsableByteArray.readInt();
                int i2 = readInt - 16;
                byte[] bArr = new byte[i2];
                parsableByteArray.readBytes(bArr, 0, i2);
                return new MdtaMetadataEntry(str, bArr, readInt3, readInt2);
            }
            parsableByteArray.setPosition(position + readInt);
        }
    }

    @Nullable
    private static TextInformationFrame a(int i, String str, ParsableByteArray parsableByteArray) {
        int readInt = parsableByteArray.readInt();
        if (parsableByteArray.readInt() == 1684108385) {
            parsableByteArray.skipBytes(8);
            return new TextInformationFrame(str, null, parsableByteArray.readNullTerminatedString(readInt - 16));
        }
        String valueOf = String.valueOf(a.c(i));
        Log.w("MetadataUtil", valueOf.length() != 0 ? "Failed to parse text attribute: ".concat(valueOf) : new String("Failed to parse text attribute: "));
        return null;
    }

    @Nullable
    private static CommentFrame a(int i, ParsableByteArray parsableByteArray) {
        int readInt = parsableByteArray.readInt();
        if (parsableByteArray.readInt() == 1684108385) {
            parsableByteArray.skipBytes(8);
            String readNullTerminatedString = parsableByteArray.readNullTerminatedString(readInt - 16);
            return new CommentFrame(C.LANGUAGE_UNDETERMINED, readNullTerminatedString, readNullTerminatedString);
        }
        String valueOf = String.valueOf(a.c(i));
        Log.w("MetadataUtil", valueOf.length() != 0 ? "Failed to parse comment attribute: ".concat(valueOf) : new String("Failed to parse comment attribute: "));
        return null;
    }

    @Nullable
    private static Id3Frame a(int i, String str, ParsableByteArray parsableByteArray, boolean z, boolean z2) {
        int d = d(parsableByteArray);
        if (z2) {
            d = Math.min(1, d);
        }
        if (d < 0) {
            String valueOf = String.valueOf(a.c(i));
            Log.w("MetadataUtil", valueOf.length() != 0 ? "Failed to parse uint8 attribute: ".concat(valueOf) : new String("Failed to parse uint8 attribute: "));
            return null;
        } else if (z) {
            return new TextInformationFrame(str, null, Integer.toString(d));
        } else {
            return new CommentFrame(C.LANGUAGE_UNDETERMINED, str, Integer.toString(d));
        }
    }

    @Nullable
    private static TextInformationFrame b(int i, String str, ParsableByteArray parsableByteArray) {
        int readInt = parsableByteArray.readInt();
        if (parsableByteArray.readInt() == 1684108385 && readInt >= 22) {
            parsableByteArray.skipBytes(10);
            int readUnsignedShort = parsableByteArray.readUnsignedShort();
            if (readUnsignedShort > 0) {
                StringBuilder sb = new StringBuilder(11);
                sb.append(readUnsignedShort);
                String sb2 = sb.toString();
                int readUnsignedShort2 = parsableByteArray.readUnsignedShort();
                if (readUnsignedShort2 > 0) {
                    String valueOf = String.valueOf(sb2);
                    StringBuilder sb3 = new StringBuilder(String.valueOf(valueOf).length() + 12);
                    sb3.append(valueOf);
                    sb3.append("/");
                    sb3.append(readUnsignedShort2);
                    sb2 = sb3.toString();
                }
                return new TextInformationFrame(str, null, sb2);
            }
        }
        String valueOf2 = String.valueOf(a.c(i));
        Log.w("MetadataUtil", valueOf2.length() != 0 ? "Failed to parse index/count attribute: ".concat(valueOf2) : new String("Failed to parse index/count attribute: "));
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x001c  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0014  */
    @androidx.annotation.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static com.google.android.exoplayer2.metadata.id3.TextInformationFrame b(com.google.android.exoplayer2.util.ParsableByteArray r3) {
        /*
            int r3 = d(r3)
            r0 = 0
            if (r3 <= 0) goto L_0x0011
            java.lang.String[] r1 = com.google.android.exoplayer2.extractor.mp4.d.a
            int r2 = r1.length
            if (r3 > r2) goto L_0x0011
            int r3 = r3 + (-1)
            r3 = r1[r3]
            goto L_0x0012
        L_0x0011:
            r3 = r0
        L_0x0012:
            if (r3 == 0) goto L_0x001c
            com.google.android.exoplayer2.metadata.id3.TextInformationFrame r1 = new com.google.android.exoplayer2.metadata.id3.TextInformationFrame
            java.lang.String r2 = "TCON"
            r1.<init>(r2, r0, r3)
            return r1
        L_0x001c:
            java.lang.String r3 = "MetadataUtil"
            java.lang.String r1 = "Failed to parse standard genre code"
            com.google.android.exoplayer2.util.Log.w(r3, r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.mp4.d.b(com.google.android.exoplayer2.util.ParsableByteArray):com.google.android.exoplayer2.metadata.id3.TextInformationFrame");
    }

    @Nullable
    private static ApicFrame c(ParsableByteArray parsableByteArray) {
        int readInt = parsableByteArray.readInt();
        if (parsableByteArray.readInt() == 1684108385) {
            int b = a.b(parsableByteArray.readInt());
            String str = b == 13 ? "image/jpeg" : b == 14 ? DLNAProfiles.DLNAMimeTypes.MIME_IMAGE_PNG : null;
            if (str == null) {
                StringBuilder sb = new StringBuilder(41);
                sb.append("Unrecognized cover art flags: ");
                sb.append(b);
                Log.w("MetadataUtil", sb.toString());
                return null;
            }
            parsableByteArray.skipBytes(4);
            byte[] bArr = new byte[readInt - 16];
            parsableByteArray.readBytes(bArr, 0, bArr.length);
            return new ApicFrame(str, null, 3, bArr);
        }
        Log.w("MetadataUtil", "Failed to parse cover art attribute");
        return null;
    }

    @Nullable
    private static Id3Frame a(ParsableByteArray parsableByteArray, int i) {
        int i2 = -1;
        int i3 = -1;
        String str = null;
        String str2 = null;
        while (parsableByteArray.getPosition() < i) {
            int position = parsableByteArray.getPosition();
            int readInt = parsableByteArray.readInt();
            int readInt2 = parsableByteArray.readInt();
            parsableByteArray.skipBytes(4);
            if (readInt2 == 1835360622) {
                str = parsableByteArray.readNullTerminatedString(readInt - 12);
            } else if (readInt2 == 1851878757) {
                str2 = parsableByteArray.readNullTerminatedString(readInt - 12);
            } else {
                if (readInt2 == 1684108385) {
                    i2 = position;
                    i3 = readInt;
                }
                parsableByteArray.skipBytes(readInt - 12);
            }
        }
        if (str == null || str2 == null || i2 == -1) {
            return null;
        }
        parsableByteArray.setPosition(i2);
        parsableByteArray.skipBytes(16);
        return new InternalFrame(str, str2, parsableByteArray.readNullTerminatedString(i3 - 16));
    }

    private static int d(ParsableByteArray parsableByteArray) {
        parsableByteArray.skipBytes(4);
        if (parsableByteArray.readInt() == 1684108385) {
            parsableByteArray.skipBytes(8);
            return parsableByteArray.readUnsignedByte();
        }
        Log.w("MetadataUtil", "Failed to parse uint8 attribute value");
        return -1;
    }
}
