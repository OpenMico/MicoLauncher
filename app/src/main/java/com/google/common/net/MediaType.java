package com.google.common.net;

import com.fasterxml.jackson.core.JsonPointer;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Ascii;
import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.UnmodifiableIterator;
import com.google.errorprone.annotations.Immutable;
import com.google.errorprone.annotations.concurrent.LazyInit;
import com.xiaomi.micolauncher.skills.music.model.lrc.LrcRow;
import io.netty.handler.codec.http.HttpHeaders;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Immutable
@Beta
@GwtCompatible
/* loaded from: classes2.dex */
public final class MediaType {
    private final String f;
    private final String g;
    private final ImmutableListMultimap<String, String> h;
    @LazyInit
    private String i;
    @LazyInit
    private int j;
    @LazyInit
    private Optional<Charset> k;
    private static final ImmutableListMultimap<String, String> a = ImmutableListMultimap.of("charset", Ascii.toLowerCase(Charsets.UTF_8.name()));
    private static final CharMatcher b = CharMatcher.ascii().and(CharMatcher.javaIsoControl().negate()).and(CharMatcher.isNot(' ')).and(CharMatcher.noneOf("()<>@,;:\\\"/[]?="));
    private static final CharMatcher c = CharMatcher.ascii().and(CharMatcher.noneOf("\"\\\r"));
    private static final CharMatcher d = CharMatcher.anyOf(" \t\r\n");
    private static final Map<MediaType, MediaType> e = Maps.newHashMap();
    public static final MediaType ANY_TYPE = a("*", "*");
    public static final MediaType ANY_TEXT_TYPE = a("text", "*");
    public static final MediaType ANY_IMAGE_TYPE = a(MimeTypes.BASE_TYPE_IMAGE, "*");
    public static final MediaType ANY_AUDIO_TYPE = a("audio", "*");
    public static final MediaType ANY_VIDEO_TYPE = a("video", "*");
    public static final MediaType ANY_APPLICATION_TYPE = a(MimeTypes.BASE_TYPE_APPLICATION, "*");
    public static final MediaType CACHE_MANIFEST_UTF_8 = b("text", "cache-manifest");
    public static final MediaType CSS_UTF_8 = b("text", "css");
    public static final MediaType CSV_UTF_8 = b("text", "csv");
    public static final MediaType HTML_UTF_8 = b("text", "html");
    public static final MediaType I_CALENDAR_UTF_8 = b("text", "calendar");
    public static final MediaType PLAIN_TEXT_UTF_8 = b("text", "plain");
    public static final MediaType TEXT_JAVASCRIPT_UTF_8 = b("text", "javascript");
    public static final MediaType TSV_UTF_8 = b("text", "tab-separated-values");
    public static final MediaType VCARD_UTF_8 = b("text", "vcard");
    public static final MediaType WML_UTF_8 = b("text", "vnd.wap.wml");
    public static final MediaType XML_UTF_8 = b("text", "xml");
    public static final MediaType VTT_UTF_8 = b("text", "vtt");
    public static final MediaType BMP = a(MimeTypes.BASE_TYPE_IMAGE, "bmp");
    public static final MediaType CRW = a(MimeTypes.BASE_TYPE_IMAGE, "x-canon-crw");
    public static final MediaType GIF = a(MimeTypes.BASE_TYPE_IMAGE, "gif");
    public static final MediaType ICO = a(MimeTypes.BASE_TYPE_IMAGE, "vnd.microsoft.icon");
    public static final MediaType JPEG = a(MimeTypes.BASE_TYPE_IMAGE, "jpeg");
    public static final MediaType PNG = a(MimeTypes.BASE_TYPE_IMAGE, "png");
    public static final MediaType PSD = a(MimeTypes.BASE_TYPE_IMAGE, "vnd.adobe.photoshop");
    public static final MediaType SVG_UTF_8 = b(MimeTypes.BASE_TYPE_IMAGE, "svg+xml");
    public static final MediaType TIFF = a(MimeTypes.BASE_TYPE_IMAGE, "tiff");
    public static final MediaType WEBP = a(MimeTypes.BASE_TYPE_IMAGE, "webp");
    public static final MediaType MP4_AUDIO = a("audio", "mp4");
    public static final MediaType MPEG_AUDIO = a("audio", "mpeg");
    public static final MediaType OGG_AUDIO = a("audio", "ogg");
    public static final MediaType WEBM_AUDIO = a("audio", "webm");
    public static final MediaType L16_AUDIO = a("audio", "l16");
    public static final MediaType L24_AUDIO = a("audio", "l24");
    public static final MediaType BASIC_AUDIO = a("audio", "basic");
    public static final MediaType AAC_AUDIO = a("audio", "aac");
    public static final MediaType VORBIS_AUDIO = a("audio", "vorbis");
    public static final MediaType WMA_AUDIO = a("audio", "x-ms-wma");
    public static final MediaType WAX_AUDIO = a("audio", "x-ms-wax");
    public static final MediaType VND_REAL_AUDIO = a("audio", "vnd.rn-realaudio");
    public static final MediaType VND_WAVE_AUDIO = a("audio", "vnd.wave");
    public static final MediaType MP4_VIDEO = a("video", "mp4");
    public static final MediaType MPEG_VIDEO = a("video", "mpeg");
    public static final MediaType OGG_VIDEO = a("video", "ogg");
    public static final MediaType QUICKTIME = a("video", "quicktime");
    public static final MediaType WEBM_VIDEO = a("video", "webm");
    public static final MediaType WMV = a("video", "x-ms-wmv");
    public static final MediaType FLV_VIDEO = a("video", "x-flv");
    public static final MediaType THREE_GPP_VIDEO = a("video", "3gpp");
    public static final MediaType THREE_GPP2_VIDEO = a("video", "3gpp2");
    public static final MediaType APPLICATION_XML_UTF_8 = b(MimeTypes.BASE_TYPE_APPLICATION, "xml");
    public static final MediaType ATOM_UTF_8 = b(MimeTypes.BASE_TYPE_APPLICATION, "atom+xml");
    public static final MediaType BZIP2 = a(MimeTypes.BASE_TYPE_APPLICATION, "x-bzip2");
    public static final MediaType DART_UTF_8 = b(MimeTypes.BASE_TYPE_APPLICATION, "dart");
    public static final MediaType APPLE_PASSBOOK = a(MimeTypes.BASE_TYPE_APPLICATION, "vnd.apple.pkpass");
    public static final MediaType EOT = a(MimeTypes.BASE_TYPE_APPLICATION, "vnd.ms-fontobject");
    public static final MediaType EPUB = a(MimeTypes.BASE_TYPE_APPLICATION, "epub+zip");
    public static final MediaType FORM_DATA = a(MimeTypes.BASE_TYPE_APPLICATION, "x-www-form-urlencoded");
    public static final MediaType KEY_ARCHIVE = a(MimeTypes.BASE_TYPE_APPLICATION, "pkcs12");
    public static final MediaType APPLICATION_BINARY = a(MimeTypes.BASE_TYPE_APPLICATION, HttpHeaders.Values.BINARY);
    public static final MediaType GZIP = a(MimeTypes.BASE_TYPE_APPLICATION, "x-gzip");
    public static final MediaType JAVASCRIPT_UTF_8 = b(MimeTypes.BASE_TYPE_APPLICATION, "javascript");
    public static final MediaType JSON_UTF_8 = b(MimeTypes.BASE_TYPE_APPLICATION, "json");
    public static final MediaType MANIFEST_JSON_UTF_8 = b(MimeTypes.BASE_TYPE_APPLICATION, "manifest+json");
    public static final MediaType KML = a(MimeTypes.BASE_TYPE_APPLICATION, "vnd.google-earth.kml+xml");
    public static final MediaType KMZ = a(MimeTypes.BASE_TYPE_APPLICATION, "vnd.google-earth.kmz");
    public static final MediaType MBOX = a(MimeTypes.BASE_TYPE_APPLICATION, "mbox");
    public static final MediaType APPLE_MOBILE_CONFIG = a(MimeTypes.BASE_TYPE_APPLICATION, "x-apple-aspen-config");
    public static final MediaType MICROSOFT_EXCEL = a(MimeTypes.BASE_TYPE_APPLICATION, "vnd.ms-excel");
    public static final MediaType MICROSOFT_POWERPOINT = a(MimeTypes.BASE_TYPE_APPLICATION, "vnd.ms-powerpoint");
    public static final MediaType MICROSOFT_WORD = a(MimeTypes.BASE_TYPE_APPLICATION, "msword");
    public static final MediaType NACL_APPLICATION = a(MimeTypes.BASE_TYPE_APPLICATION, "x-nacl");
    public static final MediaType NACL_PORTABLE_APPLICATION = a(MimeTypes.BASE_TYPE_APPLICATION, "x-pnacl");
    public static final MediaType OCTET_STREAM = a(MimeTypes.BASE_TYPE_APPLICATION, "octet-stream");
    public static final MediaType OGG_CONTAINER = a(MimeTypes.BASE_TYPE_APPLICATION, "ogg");
    public static final MediaType OOXML_DOCUMENT = a(MimeTypes.BASE_TYPE_APPLICATION, "vnd.openxmlformats-officedocument.wordprocessingml.document");
    public static final MediaType OOXML_PRESENTATION = a(MimeTypes.BASE_TYPE_APPLICATION, "vnd.openxmlformats-officedocument.presentationml.presentation");
    public static final MediaType OOXML_SHEET = a(MimeTypes.BASE_TYPE_APPLICATION, "vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    public static final MediaType OPENDOCUMENT_GRAPHICS = a(MimeTypes.BASE_TYPE_APPLICATION, "vnd.oasis.opendocument.graphics");
    public static final MediaType OPENDOCUMENT_PRESENTATION = a(MimeTypes.BASE_TYPE_APPLICATION, "vnd.oasis.opendocument.presentation");
    public static final MediaType OPENDOCUMENT_SPREADSHEET = a(MimeTypes.BASE_TYPE_APPLICATION, "vnd.oasis.opendocument.spreadsheet");
    public static final MediaType OPENDOCUMENT_TEXT = a(MimeTypes.BASE_TYPE_APPLICATION, "vnd.oasis.opendocument.text");
    public static final MediaType PDF = a(MimeTypes.BASE_TYPE_APPLICATION, "pdf");
    public static final MediaType POSTSCRIPT = a(MimeTypes.BASE_TYPE_APPLICATION, "postscript");
    public static final MediaType PROTOBUF = a(MimeTypes.BASE_TYPE_APPLICATION, "protobuf");
    public static final MediaType RDF_XML_UTF_8 = b(MimeTypes.BASE_TYPE_APPLICATION, "rdf+xml");
    public static final MediaType RTF_UTF_8 = b(MimeTypes.BASE_TYPE_APPLICATION, "rtf");
    public static final MediaType SFNT = a(MimeTypes.BASE_TYPE_APPLICATION, "font-sfnt");
    public static final MediaType SHOCKWAVE_FLASH = a(MimeTypes.BASE_TYPE_APPLICATION, "x-shockwave-flash");
    public static final MediaType SKETCHUP = a(MimeTypes.BASE_TYPE_APPLICATION, "vnd.sketchup.skp");
    public static final MediaType SOAP_XML_UTF_8 = b(MimeTypes.BASE_TYPE_APPLICATION, "soap+xml");
    public static final MediaType TAR = a(MimeTypes.BASE_TYPE_APPLICATION, "x-tar");
    public static final MediaType WOFF = a(MimeTypes.BASE_TYPE_APPLICATION, "font-woff");
    public static final MediaType WOFF2 = a(MimeTypes.BASE_TYPE_APPLICATION, "font-woff2");
    public static final MediaType XHTML_UTF_8 = b(MimeTypes.BASE_TYPE_APPLICATION, "xhtml+xml");
    public static final MediaType XRD_UTF_8 = b(MimeTypes.BASE_TYPE_APPLICATION, "xrd+xml");
    public static final MediaType ZIP = a(MimeTypes.BASE_TYPE_APPLICATION, "zip");
    private static final Joiner.MapJoiner l = Joiner.on("; ").withKeyValueSeparator("=");

    private static MediaType a(String str, String str2) {
        MediaType a2 = a(new MediaType(str, str2, ImmutableListMultimap.of()));
        a2.k = Optional.absent();
        return a2;
    }

    private static MediaType b(String str, String str2) {
        MediaType a2 = a(new MediaType(str, str2, a));
        a2.k = Optional.of(Charsets.UTF_8);
        return a2;
    }

    private static MediaType a(MediaType mediaType) {
        e.put(mediaType, mediaType);
        return mediaType;
    }

    private MediaType(String str, String str2, ImmutableListMultimap<String, String> immutableListMultimap) {
        this.f = str;
        this.g = str2;
        this.h = immutableListMultimap;
    }

    public String type() {
        return this.f;
    }

    public String subtype() {
        return this.g;
    }

    public ImmutableListMultimap<String, String> parameters() {
        return this.h;
    }

    private Map<String, ImmutableMultiset<String>> b() {
        return Maps.transformValues(this.h.asMap(), new Function<Collection<String>, ImmutableMultiset<String>>() { // from class: com.google.common.net.MediaType.1
            /* renamed from: a */
            public ImmutableMultiset<String> apply(Collection<String> collection) {
                return ImmutableMultiset.copyOf(collection);
            }
        });
    }

    public Optional<Charset> charset() {
        Optional<Charset> optional = this.k;
        if (optional == null) {
            String str = null;
            Optional<Charset> absent = Optional.absent();
            UnmodifiableIterator<String> it = this.h.get((ImmutableListMultimap<String, String>) "charset").iterator();
            optional = absent;
            while (it.hasNext()) {
                String next = it.next();
                if (str == null) {
                    optional = Optional.of(Charset.forName(next));
                    str = next;
                } else if (!str.equals(next)) {
                    throw new IllegalStateException("Multiple charset values defined: " + str + ", " + next);
                }
            }
            this.k = optional;
        }
        return optional;
    }

    public MediaType withoutParameters() {
        return this.h.isEmpty() ? this : create(this.f, this.g);
    }

    public MediaType withParameters(Multimap<String, String> multimap) {
        return a(this.f, this.g, multimap);
    }

    public MediaType withParameters(String str, Iterable<String> iterable) {
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(iterable);
        String b2 = b(str);
        ImmutableListMultimap.Builder builder = ImmutableListMultimap.builder();
        UnmodifiableIterator<Map.Entry<String, String>> it = this.h.entries().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> next = it.next();
            String key = next.getKey();
            if (!b2.equals(key)) {
                builder.put((ImmutableListMultimap.Builder) key, next.getValue());
            }
        }
        for (String str2 : iterable) {
            builder.put((ImmutableListMultimap.Builder) b2, c(b2, str2));
        }
        MediaType mediaType = new MediaType(this.f, this.g, builder.build());
        if (!b2.equals("charset")) {
            mediaType.k = this.k;
        }
        return (MediaType) MoreObjects.firstNonNull(e.get(mediaType), mediaType);
    }

    public MediaType withParameter(String str, String str2) {
        return withParameters(str, ImmutableSet.of(str2));
    }

    public MediaType withCharset(Charset charset) {
        Preconditions.checkNotNull(charset);
        MediaType withParameter = withParameter("charset", charset.name());
        withParameter.k = Optional.of(charset);
        return withParameter;
    }

    public boolean hasWildcard() {
        return "*".equals(this.f) || "*".equals(this.g);
    }

    public boolean is(MediaType mediaType) {
        return (mediaType.f.equals("*") || mediaType.f.equals(this.f)) && (mediaType.g.equals("*") || mediaType.g.equals(this.g)) && this.h.entries().containsAll(mediaType.h.entries());
    }

    public static MediaType create(String str, String str2) {
        MediaType a2 = a(str, str2, ImmutableListMultimap.of());
        a2.k = Optional.absent();
        return a2;
    }

    private static MediaType a(String str, String str2, Multimap<String, String> multimap) {
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(str2);
        Preconditions.checkNotNull(multimap);
        String b2 = b(str);
        String b3 = b(str2);
        Preconditions.checkArgument(!"*".equals(b2) || "*".equals(b3), "A wildcard type cannot be used with a non-wildcard subtype");
        ImmutableListMultimap.Builder builder = ImmutableListMultimap.builder();
        for (Map.Entry<String, String> entry : multimap.entries()) {
            String b4 = b(entry.getKey());
            builder.put((ImmutableListMultimap.Builder) b4, c(b4, entry.getValue()));
        }
        MediaType mediaType = new MediaType(b2, b3, builder.build());
        return (MediaType) MoreObjects.firstNonNull(e.get(mediaType), mediaType);
    }

    private static String b(String str) {
        Preconditions.checkArgument(b.matchesAllOf(str));
        return Ascii.toLowerCase(str);
    }

    private static String c(String str, String str2) {
        return "charset".equals(str) ? Ascii.toLowerCase(str2) : str2;
    }

    public static MediaType parse(String str) {
        String str2;
        Preconditions.checkNotNull(str);
        a aVar = new a(str);
        try {
            String b2 = aVar.b(b);
            aVar.a(JsonPointer.SEPARATOR);
            String b3 = aVar.b(b);
            ImmutableListMultimap.Builder builder = ImmutableListMultimap.builder();
            while (aVar.b()) {
                aVar.a(d);
                aVar.a(';');
                aVar.a(d);
                String b4 = aVar.b(b);
                aVar.a('=');
                if ('\"' == aVar.a()) {
                    aVar.a('\"');
                    StringBuilder sb = new StringBuilder();
                    while ('\"' != aVar.a()) {
                        if ('\\' == aVar.a()) {
                            aVar.a('\\');
                            sb.append(aVar.c(CharMatcher.ascii()));
                        } else {
                            sb.append(aVar.b(c));
                        }
                    }
                    str2 = sb.toString();
                    aVar.a('\"');
                } else {
                    str2 = aVar.b(b);
                }
                builder.put((ImmutableListMultimap.Builder) b4, str2);
            }
            return a(b2, b3, builder.build());
        } catch (IllegalStateException e2) {
            throw new IllegalArgumentException("Could not parse '" + str + LrcRow.SINGLE_QUOTE, e2);
        }
    }

    /* loaded from: classes2.dex */
    private static final class a {
        final String a;
        int b = 0;

        a(String str) {
            this.a = str;
        }

        String a(CharMatcher charMatcher) {
            Preconditions.checkState(b());
            int i = this.b;
            this.b = charMatcher.negate().indexIn(this.a, i);
            return b() ? this.a.substring(i, this.b) : this.a.substring(i);
        }

        String b(CharMatcher charMatcher) {
            int i = this.b;
            String a = a(charMatcher);
            Preconditions.checkState(this.b != i);
            return a;
        }

        char c(CharMatcher charMatcher) {
            Preconditions.checkState(b());
            char a = a();
            Preconditions.checkState(charMatcher.matches(a));
            this.b++;
            return a;
        }

        char a(char c) {
            Preconditions.checkState(b());
            Preconditions.checkState(a() == c);
            this.b++;
            return c;
        }

        char a() {
            Preconditions.checkState(b());
            return this.a.charAt(this.b);
        }

        boolean b() {
            int i = this.b;
            return i >= 0 && i < this.a.length();
        }
    }

    public boolean equals(@NullableDecl Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MediaType)) {
            return false;
        }
        MediaType mediaType = (MediaType) obj;
        return this.f.equals(mediaType.f) && this.g.equals(mediaType.g) && b().equals(mediaType.b());
    }

    public int hashCode() {
        int i = this.j;
        if (i != 0) {
            return i;
        }
        int hashCode = Objects.hashCode(this.f, this.g, b());
        this.j = hashCode;
        return hashCode;
    }

    public String toString() {
        String str = this.i;
        if (str != null) {
            return str;
        }
        String c2 = c();
        this.i = c2;
        return c2;
    }

    private String c() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.f);
        sb.append(JsonPointer.SEPARATOR);
        sb.append(this.g);
        if (!this.h.isEmpty()) {
            sb.append("; ");
            l.appendTo(sb, Multimaps.transformValues((ListMultimap) this.h, (Function) new Function<String, String>() { // from class: com.google.common.net.MediaType.2
                /* renamed from: a */
                public String apply(String str) {
                    return MediaType.b.matchesAllOf(str) ? str : MediaType.c(str);
                }
            }).entries());
        }
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String c(String str) {
        StringBuilder sb = new StringBuilder(str.length() + 16);
        sb.append('\"');
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (charAt == '\r' || charAt == '\\' || charAt == '\"') {
                sb.append('\\');
            }
            sb.append(charAt);
        }
        sb.append('\"');
        return sb.toString();
    }
}
