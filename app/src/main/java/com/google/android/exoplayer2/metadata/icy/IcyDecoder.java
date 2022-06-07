package com.google.android.exoplayer2.metadata.icy;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.MetadataInputBuffer;
import com.google.android.exoplayer2.metadata.SimpleMetadataDecoder;
import com.google.common.base.Ascii;
import com.google.common.base.Charsets;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes2.dex */
public final class IcyDecoder extends SimpleMetadataDecoder {
    private static final Pattern a = Pattern.compile("(.+?)='(.*?)';", 32);
    private final CharsetDecoder b = Charsets.UTF_8.newDecoder();
    private final CharsetDecoder c = Charsets.ISO_8859_1.newDecoder();

    @Override // com.google.android.exoplayer2.metadata.SimpleMetadataDecoder
    protected Metadata decode(MetadataInputBuffer metadataInputBuffer, ByteBuffer byteBuffer) {
        String a2 = a(byteBuffer);
        byte[] bArr = new byte[byteBuffer.limit()];
        byteBuffer.get(bArr);
        if (a2 == null) {
            return new Metadata(new IcyInfo(bArr, null, null));
        }
        Matcher matcher = a.matcher(a2);
        String str = null;
        String str2 = null;
        for (int i = 0; matcher.find(i); i = matcher.end()) {
            String group = matcher.group(1);
            String group2 = matcher.group(2);
            if (group != null) {
                String lowerCase = Ascii.toLowerCase(group);
                char c = 65535;
                int hashCode = lowerCase.hashCode();
                if (hashCode != -315603473) {
                    if (hashCode == 1646559960 && lowerCase.equals("streamtitle")) {
                        c = 0;
                    }
                } else if (lowerCase.equals("streamurl")) {
                    c = 1;
                }
                switch (c) {
                    case 0:
                        str = group2;
                        continue;
                    case 1:
                        str2 = group2;
                        continue;
                }
            }
        }
        return new Metadata(new IcyInfo(bArr, str, str2));
    }

    @Nullable
    private String a(ByteBuffer byteBuffer) {
        try {
            return this.b.decode(byteBuffer).toString();
        } catch (CharacterCodingException unused) {
            try {
                return this.c.decode(byteBuffer).toString();
            } catch (CharacterCodingException unused2) {
                return null;
            } finally {
                this.c.reset();
                byteBuffer.rewind();
            }
        } finally {
            this.b.reset();
            byteBuffer.rewind();
        }
    }
}
