package com.google.android.exoplayer2.source.dash.manifest;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Pair;
import android.util.Xml;
import androidx.annotation.Nullable;
import androidx.exifinterface.media.ExifInterface;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.audio.Ac3Util;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.metadata.emsg.EventMessage;
import com.google.android.exoplayer2.source.dash.manifest.SegmentBase;
import com.google.android.exoplayer2.upstream.ParsingLoadable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.UriUtil;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.util.XmlPullParserUtil;
import com.google.common.base.Ascii;
import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.umeng.analytics.pro.ai;
import com.xiaomi.infra.galaxy.fds.Common;
import com.xiaomi.mi_soundbox_command_sdk.Commands;
import com.xiaomi.miplay.mylibrary.statistic.OneTrackWorldUrl;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.onetrack.api.b;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes2.dex */
public class DashManifestParser extends DefaultHandler implements ParsingLoadable.Parser<DashManifest> {
    private static final Pattern a = Pattern.compile("(\\d+)(?:/(\\d+))?");
    private static final Pattern b = Pattern.compile("CC([1-4])=.*");
    private static final Pattern c = Pattern.compile("([1-9]|[1-5][0-9]|6[0-3])=.*");
    private static final int[] d = {-1, 1, 2, 3, 4, 5, 6, 8, 2, 3, 4, 7, 8, 24, 8, 12, 10, 12, 14, 12, 14};
    private final XmlPullParserFactory e;

    private static long a(long j, long j2) {
        if (j2 != C.TIME_UNSET) {
            j = j2;
        }
        return j == Long.MAX_VALUE ? C.TIME_UNSET : j;
    }

    public DashManifestParser() {
        try {
            this.e = XmlPullParserFactory.newInstance();
        } catch (XmlPullParserException e) {
            throw new RuntimeException("Couldn't create XmlPullParserFactory instance", e);
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.android.exoplayer2.upstream.ParsingLoadable.Parser
    public DashManifest parse(Uri uri, InputStream inputStream) throws IOException {
        try {
            XmlPullParser newPullParser = this.e.newPullParser();
            newPullParser.setInput(inputStream, null);
            if (newPullParser.next() == 2 && "MPD".equals(newPullParser.getName())) {
                return parseMediaPresentationDescription(newPullParser, new BaseUrl(uri.toString()));
            }
            throw ParserException.createForMalformedManifest("inputStream does not contain a valid media presentation description", null);
        } catch (XmlPullParserException e) {
            throw ParserException.createForMalformedManifest(null, e);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:70:0x01b8  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x01d8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected com.google.android.exoplayer2.source.dash.manifest.DashManifest parseMediaPresentationDescription(org.xmlpull.v1.XmlPullParser r44, com.google.android.exoplayer2.source.dash.manifest.BaseUrl r45) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 491
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.dash.manifest.DashManifestParser.parseMediaPresentationDescription(org.xmlpull.v1.XmlPullParser, com.google.android.exoplayer2.source.dash.manifest.BaseUrl):com.google.android.exoplayer2.source.dash.manifest.DashManifest");
    }

    protected DashManifest buildMediaPresentationDescription(long j, long j2, long j3, boolean z, long j4, long j5, long j6, long j7, @Nullable ProgramInformation programInformation, @Nullable UtcTimingElement utcTimingElement, @Nullable ServiceDescriptionElement serviceDescriptionElement, @Nullable Uri uri, List<Period> list) {
        return new DashManifest(j, j2, j3, z, j4, j5, j6, j7, programInformation, utcTimingElement, serviceDescriptionElement, uri, list);
    }

    protected UtcTimingElement parseUtcTiming(XmlPullParser xmlPullParser) {
        return buildUtcTimingElement(xmlPullParser.getAttributeValue(null, "schemeIdUri"), xmlPullParser.getAttributeValue(null, b.p));
    }

    protected UtcTimingElement buildUtcTimingElement(String str, String str2) {
        return new UtcTimingElement(str, str2);
    }

    protected ServiceDescriptionElement parseServiceDescription(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        float f;
        float f2;
        long j;
        long j2;
        long j3;
        float f3 = -3.4028235E38f;
        float f4 = -3.4028235E38f;
        long j4 = -9223372036854775807L;
        long j5 = -9223372036854775807L;
        long j6 = -9223372036854775807L;
        while (true) {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, "Latency")) {
                j3 = parseLong(xmlPullParser, OneTrackWorldUrl.ACTION_TARGET, C.TIME_UNSET);
                j2 = parseLong(xmlPullParser, "min", C.TIME_UNSET);
                j = parseLong(xmlPullParser, "max", C.TIME_UNSET);
                f2 = f3;
                f = f4;
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "PlaybackRate")) {
                j3 = j4;
                j2 = j5;
                j = j6;
                f2 = parseFloat(xmlPullParser, "min", -3.4028235E38f);
                f = parseFloat(xmlPullParser, "max", -3.4028235E38f);
            } else {
                j3 = j4;
                j2 = j5;
                j = j6;
                f2 = f3;
                f = f4;
            }
            if (XmlPullParserUtil.isEndTag(xmlPullParser, "ServiceDescription")) {
                return new ServiceDescriptionElement(j3, j2, j, f2, f);
            }
            j4 = j3;
            j5 = j2;
            j6 = j;
            f3 = f2;
            f4 = f;
        }
    }

    protected Pair<Period, Long> parsePeriod(XmlPullParser xmlPullParser, List<BaseUrl> list, long j, long j2, long j3, long j4) throws XmlPullParserException, IOException {
        ArrayList arrayList;
        ArrayList arrayList2;
        DashManifestParser dashManifestParser = this;
        Object obj = null;
        String attributeValue = xmlPullParser.getAttributeValue(null, "id");
        long parseDuration = parseDuration(xmlPullParser, "start", j);
        long j5 = C.TIME_UNSET;
        long j6 = j3 != C.TIME_UNSET ? j3 + parseDuration : -9223372036854775807L;
        long parseDuration2 = parseDuration(xmlPullParser, "duration", C.TIME_UNSET);
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        ArrayList arrayList5 = new ArrayList();
        boolean z = false;
        long j7 = j2;
        long j8 = -9223372036854775807L;
        SegmentBase segmentBase = null;
        Descriptor descriptor = null;
        while (true) {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, "BaseURL")) {
                if (!z) {
                    j7 = dashManifestParser.parseAvailabilityTimeOffsetUs(xmlPullParser, j7);
                    z = true;
                }
                arrayList5.addAll(parseBaseUrl(xmlPullParser, list));
                arrayList = arrayList4;
                arrayList5 = arrayList5;
                arrayList2 = arrayList3;
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "AdaptationSet")) {
                j7 = j7;
                arrayList5 = arrayList5;
                arrayList2 = arrayList3;
                arrayList2.add(parseAdaptationSet(xmlPullParser, !arrayList5.isEmpty() ? arrayList5 : list, segmentBase, parseDuration2, j7, j8, j6, j4));
                arrayList = arrayList4;
                obj = null;
                j5 = C.TIME_UNSET;
            } else {
                j7 = j7;
                arrayList5 = arrayList5;
                arrayList2 = arrayList3;
                if (XmlPullParserUtil.isStartTag(xmlPullParser, "EventStream")) {
                    arrayList4.add(parseEventStream(xmlPullParser));
                    arrayList = arrayList4;
                    obj = null;
                    j5 = C.TIME_UNSET;
                } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "SegmentBase")) {
                    arrayList = arrayList4;
                    segmentBase = parseSegmentBase(xmlPullParser, null);
                    obj = null;
                    j7 = j7;
                    j5 = C.TIME_UNSET;
                } else {
                    arrayList = arrayList4;
                    if (XmlPullParserUtil.isStartTag(xmlPullParser, "SegmentList")) {
                        long parseAvailabilityTimeOffsetUs = parseAvailabilityTimeOffsetUs(xmlPullParser, C.TIME_UNSET);
                        obj = null;
                        SegmentBase.SegmentList parseSegmentList = parseSegmentList(xmlPullParser, null, j6, parseDuration2, j7, parseAvailabilityTimeOffsetUs, j4);
                        j8 = parseAvailabilityTimeOffsetUs;
                        j7 = j7;
                        j5 = C.TIME_UNSET;
                        segmentBase = parseSegmentList;
                    } else {
                        obj = null;
                        if (XmlPullParserUtil.isStartTag(xmlPullParser, "SegmentTemplate")) {
                            long parseAvailabilityTimeOffsetUs2 = parseAvailabilityTimeOffsetUs(xmlPullParser, C.TIME_UNSET);
                            j5 = -9223372036854775807L;
                            j8 = parseAvailabilityTimeOffsetUs2;
                            j7 = j7;
                            segmentBase = parseSegmentTemplate(xmlPullParser, null, ImmutableList.of(), j6, parseDuration2, j7, parseAvailabilityTimeOffsetUs2, j4);
                        } else {
                            j5 = C.TIME_UNSET;
                            if (XmlPullParserUtil.isStartTag(xmlPullParser, "AssetIdentifier")) {
                                descriptor = parseDescriptor(xmlPullParser, "AssetIdentifier");
                                j7 = j7;
                            } else {
                                maybeSkipTag(xmlPullParser);
                            }
                        }
                    }
                }
            }
            if (XmlPullParserUtil.isEndTag(xmlPullParser, "Period")) {
                return Pair.create(buildPeriod(attributeValue, parseDuration, arrayList2, arrayList, descriptor), Long.valueOf(parseDuration2));
            }
            arrayList3 = arrayList2;
            arrayList4 = arrayList;
            dashManifestParser = this;
        }
    }

    protected Period buildPeriod(@Nullable String str, long j, List<AdaptationSet> list, List<EventStream> list2, @Nullable Descriptor descriptor) {
        return new Period(str, j, list, list2, descriptor);
    }

    protected AdaptationSet parseAdaptationSet(XmlPullParser xmlPullParser, List<BaseUrl> list, @Nullable SegmentBase segmentBase, long j, long j2, long j3, long j4, long j5) throws XmlPullParserException, IOException {
        ArrayList arrayList;
        ArrayList arrayList2;
        ArrayList arrayList3;
        ArrayList arrayList4;
        ArrayList arrayList5;
        ArrayList arrayList6;
        DashManifestParser dashManifestParser = this;
        XmlPullParser xmlPullParser2 = xmlPullParser;
        int parseInt = parseInt(xmlPullParser2, "id", -1);
        int parseContentType = parseContentType(xmlPullParser);
        Object obj = null;
        String attributeValue = xmlPullParser2.getAttributeValue(null, "mimeType");
        String attributeValue2 = xmlPullParser2.getAttributeValue(null, "codecs");
        int parseInt2 = parseInt(xmlPullParser2, "width", -1);
        int parseInt3 = parseInt(xmlPullParser2, "height", -1);
        float parseFrameRate = parseFrameRate(xmlPullParser2, -1.0f);
        int parseInt4 = parseInt(xmlPullParser2, "audioSamplingRate", -1);
        String attributeValue3 = xmlPullParser2.getAttributeValue(null, "lang");
        String attributeValue4 = xmlPullParser2.getAttributeValue(null, "label");
        ArrayList arrayList7 = new ArrayList();
        ArrayList arrayList8 = new ArrayList();
        ArrayList arrayList9 = new ArrayList();
        ArrayList arrayList10 = new ArrayList();
        ArrayList arrayList11 = new ArrayList();
        ArrayList arrayList12 = new ArrayList();
        ArrayList arrayList13 = new ArrayList();
        ArrayList arrayList14 = new ArrayList();
        SegmentBase segmentBase2 = segmentBase;
        int i = -1;
        String str = attributeValue4;
        String str2 = null;
        boolean z = false;
        long j6 = j2;
        long j7 = j3;
        int i2 = parseContentType;
        while (true) {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser2, "BaseURL")) {
                if (!z) {
                    j6 = dashManifestParser.parseAvailabilityTimeOffsetUs(xmlPullParser2, j6);
                    z = true;
                }
                arrayList14.addAll(parseBaseUrl(xmlPullParser, list));
                j7 = j7;
                arrayList14 = arrayList14;
                arrayList5 = arrayList13;
                arrayList4 = arrayList12;
                arrayList3 = arrayList11;
                arrayList10 = arrayList10;
                arrayList2 = arrayList9;
                arrayList6 = arrayList8;
                arrayList = arrayList7;
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser2, "ContentProtection")) {
                Pair<String, DrmInitData.SchemeData> parseContentProtection = parseContentProtection(xmlPullParser);
                if (parseContentProtection.first != null) {
                    str2 = (String) parseContentProtection.first;
                }
                if (parseContentProtection.second != null) {
                    arrayList7.add((DrmInitData.SchemeData) parseContentProtection.second);
                }
                j7 = j7;
                j6 = j6;
                arrayList14 = arrayList14;
                arrayList5 = arrayList13;
                arrayList4 = arrayList12;
                arrayList3 = arrayList11;
                arrayList10 = arrayList10;
                arrayList2 = arrayList9;
                arrayList6 = arrayList8;
                arrayList = arrayList7;
                obj = null;
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser2, "ContentComponent")) {
                attributeValue3 = b(attributeValue3, xmlPullParser2.getAttributeValue(null, "lang"));
                i2 = a(i2, parseContentType(xmlPullParser));
                j7 = j7;
                j6 = j6;
                arrayList14 = arrayList14;
                arrayList5 = arrayList13;
                arrayList4 = arrayList12;
                arrayList3 = arrayList11;
                arrayList10 = arrayList10;
                arrayList2 = arrayList9;
                arrayList6 = arrayList8;
                arrayList = arrayList7;
                obj = null;
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser2, "Role")) {
                arrayList10.add(parseDescriptor(xmlPullParser2, "Role"));
                i2 = i2;
                attributeValue3 = attributeValue3;
                j6 = j6;
                arrayList14 = arrayList14;
                arrayList5 = arrayList13;
                arrayList4 = arrayList12;
                arrayList3 = arrayList11;
                arrayList10 = arrayList10;
                arrayList2 = arrayList9;
                arrayList6 = arrayList8;
                arrayList = arrayList7;
                obj = null;
                j7 = j7;
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser2, "AudioChannelConfiguration")) {
                i = parseAudioChannelConfiguration(xmlPullParser);
                j7 = j7;
                j6 = j6;
                arrayList14 = arrayList14;
                arrayList5 = arrayList13;
                arrayList4 = arrayList12;
                arrayList3 = arrayList11;
                arrayList10 = arrayList10;
                arrayList2 = arrayList9;
                arrayList6 = arrayList8;
                arrayList = arrayList7;
                obj = null;
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser2, "Accessibility")) {
                arrayList9.add(parseDescriptor(xmlPullParser2, "Accessibility"));
                i2 = i2;
                attributeValue3 = attributeValue3;
                j6 = j6;
                arrayList14 = arrayList14;
                arrayList5 = arrayList13;
                arrayList4 = arrayList12;
                arrayList3 = arrayList11;
                arrayList10 = arrayList10;
                arrayList2 = arrayList9;
                arrayList6 = arrayList8;
                arrayList = arrayList7;
                obj = null;
                j7 = j7;
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser2, "EssentialProperty")) {
                arrayList11.add(parseDescriptor(xmlPullParser2, "EssentialProperty"));
                i2 = i2;
                attributeValue3 = attributeValue3;
                j6 = j6;
                arrayList14 = arrayList14;
                arrayList5 = arrayList13;
                arrayList4 = arrayList12;
                arrayList3 = arrayList11;
                arrayList10 = arrayList10;
                arrayList2 = arrayList9;
                arrayList6 = arrayList8;
                arrayList = arrayList7;
                obj = null;
                j7 = j7;
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser2, "SupplementalProperty")) {
                arrayList12.add(parseDescriptor(xmlPullParser2, "SupplementalProperty"));
                i2 = i2;
                attributeValue3 = attributeValue3;
                j6 = j6;
                arrayList14 = arrayList14;
                arrayList5 = arrayList13;
                arrayList4 = arrayList12;
                arrayList3 = arrayList11;
                arrayList10 = arrayList10;
                arrayList2 = arrayList9;
                arrayList6 = arrayList8;
                arrayList = arrayList7;
                obj = null;
                j7 = j7;
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser2, "Representation")) {
                j6 = j6;
                arrayList14 = arrayList14;
                arrayList4 = arrayList12;
                arrayList3 = arrayList11;
                arrayList10 = arrayList10;
                arrayList2 = arrayList9;
                arrayList = arrayList7;
                obj = null;
                RepresentationInfo parseRepresentation = parseRepresentation(xmlPullParser, !arrayList14.isEmpty() ? arrayList14 : list, attributeValue, attributeValue2, parseInt2, parseInt3, parseFrameRate, i, parseInt4, attributeValue3, arrayList10, arrayList2, arrayList3, arrayList4, segmentBase2, j4, j, j6, j7, j5);
                i2 = a(i2, MimeTypes.getTrackType(parseRepresentation.format.sampleMimeType));
                arrayList5 = arrayList13;
                arrayList5.add(parseRepresentation);
                j7 = j7;
                attributeValue3 = attributeValue3;
                arrayList6 = arrayList8;
                xmlPullParser2 = xmlPullParser;
            } else {
                attributeValue3 = attributeValue3;
                j6 = j6;
                arrayList14 = arrayList14;
                arrayList5 = arrayList13;
                arrayList4 = arrayList12;
                arrayList3 = arrayList11;
                arrayList10 = arrayList10;
                arrayList2 = arrayList9;
                arrayList = arrayList7;
                obj = null;
                if (XmlPullParserUtil.isStartTag(xmlPullParser, "SegmentBase")) {
                    segmentBase2 = parseSegmentBase(xmlPullParser, (SegmentBase.SingleSegmentBase) segmentBase2);
                    j7 = j7;
                    i2 = i2;
                    attributeValue3 = attributeValue3;
                    arrayList6 = arrayList8;
                    xmlPullParser2 = xmlPullParser;
                } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "SegmentList")) {
                    j7 = parseAvailabilityTimeOffsetUs(xmlPullParser, j7);
                    segmentBase2 = parseSegmentList(xmlPullParser, (SegmentBase.SegmentList) segmentBase2, j4, j, j6, j7, j5);
                    xmlPullParser2 = xmlPullParser;
                    i2 = i2;
                    attributeValue3 = attributeValue3;
                    arrayList6 = arrayList8;
                } else {
                    j7 = j7;
                    i2 = i2;
                    if (XmlPullParserUtil.isStartTag(xmlPullParser, "SegmentTemplate")) {
                        j7 = parseAvailabilityTimeOffsetUs(xmlPullParser, j7);
                        xmlPullParser2 = xmlPullParser;
                        segmentBase2 = parseSegmentTemplate(xmlPullParser, (SegmentBase.SegmentTemplate) segmentBase2, arrayList4, j4, j, j6, j7, j5);
                        i2 = i2;
                        attributeValue3 = attributeValue3;
                        arrayList6 = arrayList8;
                    } else {
                        xmlPullParser2 = xmlPullParser;
                        if (XmlPullParserUtil.isStartTag(xmlPullParser2, "InbandEventStream")) {
                            arrayList6 = arrayList8;
                            arrayList6.add(parseDescriptor(xmlPullParser2, "InbandEventStream"));
                        } else {
                            arrayList6 = arrayList8;
                            if (XmlPullParserUtil.isStartTag(xmlPullParser2, "Label")) {
                                j7 = j7;
                                str = parseLabel(xmlPullParser);
                                i2 = i2;
                                attributeValue3 = attributeValue3;
                            } else if (XmlPullParserUtil.isStartTag(xmlPullParser)) {
                                parseAdaptationSetChild(xmlPullParser);
                            }
                        }
                    }
                }
            }
            if (XmlPullParserUtil.isEndTag(xmlPullParser2, "AdaptationSet")) {
                break;
            }
            arrayList8 = arrayList6;
            arrayList13 = arrayList5;
            arrayList12 = arrayList4;
            arrayList11 = arrayList3;
            arrayList9 = arrayList2;
            arrayList7 = arrayList;
            dashManifestParser = this;
        }
        ArrayList arrayList15 = new ArrayList(arrayList5.size());
        for (int i3 = 0; i3 < arrayList5.size(); i3++) {
            arrayList15.add(buildRepresentation((RepresentationInfo) arrayList5.get(i3), str, str2, arrayList, arrayList6));
        }
        return buildAdaptationSet(parseInt, i2, arrayList15, arrayList2, arrayList3, arrayList4);
    }

    protected AdaptationSet buildAdaptationSet(int i, int i2, List<Representation> list, List<Descriptor> list2, List<Descriptor> list3, List<Descriptor> list4) {
        return new AdaptationSet(i, i2, list, list2, list3, list4);
    }

    protected int parseContentType(XmlPullParser xmlPullParser) {
        String attributeValue = xmlPullParser.getAttributeValue(null, "contentType");
        if (TextUtils.isEmpty(attributeValue)) {
            return -1;
        }
        if ("audio".equals(attributeValue)) {
            return 1;
        }
        if ("video".equals(attributeValue)) {
            return 2;
        }
        return "text".equals(attributeValue) ? 3 : -1;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00a5  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x010d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected android.util.Pair<java.lang.String, com.google.android.exoplayer2.drm.DrmInitData.SchemeData> parseContentProtection(org.xmlpull.v1.XmlPullParser r10) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 292
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.dash.manifest.DashManifestParser.parseContentProtection(org.xmlpull.v1.XmlPullParser):android.util.Pair");
    }

    protected void parseAdaptationSetChild(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        maybeSkipTag(xmlPullParser);
    }

    protected RepresentationInfo parseRepresentation(XmlPullParser xmlPullParser, List<BaseUrl> list, @Nullable String str, @Nullable String str2, int i, int i2, float f, int i3, int i4, @Nullable String str3, List<Descriptor> list2, List<Descriptor> list3, List<Descriptor> list4, List<Descriptor> list5, @Nullable SegmentBase segmentBase, long j, long j2, long j3, long j4, long j5) throws XmlPullParserException, IOException {
        ArrayList arrayList;
        SegmentBase.SegmentList segmentList;
        ArrayList arrayList2;
        ArrayList arrayList3;
        ArrayList arrayList4;
        int i5;
        DashManifestParser dashManifestParser = this;
        String attributeValue = xmlPullParser.getAttributeValue(null, "id");
        int parseInt = parseInt(xmlPullParser, "bandwidth", -1);
        String parseString = parseString(xmlPullParser, "mimeType", str);
        String parseString2 = parseString(xmlPullParser, "codecs", str2);
        int parseInt2 = parseInt(xmlPullParser, "width", i);
        int parseInt3 = parseInt(xmlPullParser, "height", i2);
        float parseFrameRate = parseFrameRate(xmlPullParser, f);
        int parseInt4 = parseInt(xmlPullParser, "audioSamplingRate", i4);
        ArrayList arrayList5 = new ArrayList();
        ArrayList arrayList6 = new ArrayList();
        ArrayList arrayList7 = new ArrayList(list4);
        ArrayList arrayList8 = new ArrayList(list5);
        ArrayList arrayList9 = new ArrayList();
        boolean z = false;
        int i6 = i3;
        long j6 = j3;
        String str4 = null;
        SegmentBase segmentBase2 = segmentBase;
        long j7 = j4;
        while (true) {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, "BaseURL")) {
                if (!z) {
                    j6 = dashManifestParser.parseAvailabilityTimeOffsetUs(xmlPullParser, j6);
                    z = true;
                }
                arrayList9.addAll(parseBaseUrl(xmlPullParser, list));
                arrayList = arrayList9;
                arrayList2 = arrayList5;
                i5 = i6;
                segmentList = segmentBase2;
                arrayList3 = arrayList6;
                arrayList4 = arrayList8;
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "AudioChannelConfiguration")) {
                segmentList = segmentBase2;
                arrayList = arrayList9;
                arrayList2 = arrayList5;
                i5 = parseAudioChannelConfiguration(xmlPullParser);
                arrayList3 = arrayList6;
                arrayList4 = arrayList8;
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "SegmentBase")) {
                SegmentBase.SingleSegmentBase parseSegmentBase = dashManifestParser.parseSegmentBase(xmlPullParser, (SegmentBase.SingleSegmentBase) segmentBase2);
                arrayList = arrayList9;
                arrayList2 = arrayList5;
                i5 = i6;
                arrayList3 = arrayList6;
                arrayList4 = arrayList8;
                segmentList = parseSegmentBase;
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "SegmentList")) {
                long parseAvailabilityTimeOffsetUs = dashManifestParser.parseAvailabilityTimeOffsetUs(xmlPullParser, j7);
                arrayList = arrayList9;
                SegmentBase.SegmentList parseSegmentList = parseSegmentList(xmlPullParser, (SegmentBase.SegmentList) segmentBase2, j, j2, j6, parseAvailabilityTimeOffsetUs, j5);
                arrayList2 = arrayList5;
                i5 = i6;
                j7 = parseAvailabilityTimeOffsetUs;
                j6 = j6;
                arrayList4 = arrayList8;
                arrayList3 = arrayList6;
                arrayList7 = arrayList7;
                segmentList = parseSegmentList;
            } else {
                arrayList = arrayList9;
                if (XmlPullParserUtil.isStartTag(xmlPullParser, "SegmentTemplate")) {
                    long parseAvailabilityTimeOffsetUs2 = dashManifestParser.parseAvailabilityTimeOffsetUs(xmlPullParser, j7);
                    arrayList2 = arrayList5;
                    SegmentBase.SegmentTemplate parseSegmentTemplate = parseSegmentTemplate(xmlPullParser, (SegmentBase.SegmentTemplate) segmentBase2, list5, j, j2, j6, parseAvailabilityTimeOffsetUs2, j5);
                    i5 = i6;
                    j7 = parseAvailabilityTimeOffsetUs2;
                    j6 = j6;
                    arrayList4 = arrayList8;
                    arrayList3 = arrayList6;
                    arrayList7 = arrayList7;
                    segmentList = parseSegmentTemplate;
                } else {
                    arrayList2 = arrayList5;
                    if (XmlPullParserUtil.isStartTag(xmlPullParser, "ContentProtection")) {
                        Pair<String, DrmInitData.SchemeData> parseContentProtection = parseContentProtection(xmlPullParser);
                        if (parseContentProtection.first != null) {
                            str4 = (String) parseContentProtection.first;
                        }
                        if (parseContentProtection.second != null) {
                            arrayList2.add((DrmInitData.SchemeData) parseContentProtection.second);
                        }
                        i5 = i6;
                        j6 = j6;
                        arrayList4 = arrayList8;
                        arrayList3 = arrayList6;
                        arrayList7 = arrayList7;
                        segmentList = segmentBase2;
                    } else {
                        if (XmlPullParserUtil.isStartTag(xmlPullParser, "InbandEventStream")) {
                            arrayList3 = arrayList6;
                            arrayList3.add(parseDescriptor(xmlPullParser, "InbandEventStream"));
                            arrayList4 = arrayList8;
                            arrayList7 = arrayList7;
                        } else {
                            arrayList3 = arrayList6;
                            if (XmlPullParserUtil.isStartTag(xmlPullParser, "EssentialProperty")) {
                                arrayList7 = arrayList7;
                                arrayList7.add(parseDescriptor(xmlPullParser, "EssentialProperty"));
                                arrayList4 = arrayList8;
                            } else {
                                arrayList7 = arrayList7;
                                if (XmlPullParserUtil.isStartTag(xmlPullParser, "SupplementalProperty")) {
                                    arrayList4 = arrayList8;
                                    arrayList4.add(parseDescriptor(xmlPullParser, "SupplementalProperty"));
                                } else {
                                    arrayList4 = arrayList8;
                                    maybeSkipTag(xmlPullParser);
                                }
                            }
                        }
                        i5 = i6;
                        j6 = j6;
                        segmentList = segmentBase2;
                    }
                }
            }
            if (XmlPullParserUtil.isEndTag(xmlPullParser, "Representation")) {
                break;
            }
            arrayList8 = arrayList4;
            arrayList6 = arrayList3;
            arrayList5 = arrayList2;
            segmentBase2 = segmentList;
            dashManifestParser = this;
            i6 = i5;
            arrayList9 = arrayList;
        }
        Format buildFormat = buildFormat(attributeValue, parseString, parseInt2, parseInt3, parseFrameRate, i5, parseInt4, parseInt, str3, list2, list3, parseString2, arrayList7, arrayList4);
        SegmentBase.SingleSegmentBase singleSegmentBase = segmentList;
        if (segmentList == null) {
            singleSegmentBase = new SegmentBase.SingleSegmentBase();
        }
        boolean isEmpty = arrayList.isEmpty();
        ArrayList arrayList10 = arrayList;
        if (isEmpty) {
            arrayList10 = list;
        }
        return new RepresentationInfo(buildFormat, arrayList10, singleSegmentBase, str4, arrayList2, arrayList3, -1L);
    }

    protected Format buildFormat(@Nullable String str, @Nullable String str2, int i, int i2, float f, int i3, int i4, int i5, @Nullable String str3, List<Descriptor> list, List<Descriptor> list2, @Nullable String str4, List<Descriptor> list3, List<Descriptor> list4) {
        String str5 = str4;
        String a2 = a(str2, str5);
        if (MimeTypes.AUDIO_E_AC3.equals(a2)) {
            a2 = parseEac3SupplementalProperties(list4);
            if (MimeTypes.AUDIO_E_AC3_JOC.equals(a2)) {
                str5 = Ac3Util.E_AC3_JOC_CODEC_STRING;
            }
        }
        int parseSelectionFlagsFromRoleDescriptors = parseSelectionFlagsFromRoleDescriptors(list);
        Format.Builder language = new Format.Builder().setId(str).setContainerMimeType(str2).setSampleMimeType(a2).setCodecs(str5).setPeakBitrate(i5).setSelectionFlags(parseSelectionFlagsFromRoleDescriptors).setRoleFlags(parseRoleFlagsFromRoleDescriptors(list) | parseRoleFlagsFromAccessibilityDescriptors(list2) | parseRoleFlagsFromProperties(list3) | parseRoleFlagsFromProperties(list4)).setLanguage(str3);
        if (MimeTypes.isVideo(a2)) {
            language.setWidth(i).setHeight(i2).setFrameRate(f);
        } else if (MimeTypes.isAudio(a2)) {
            language.setChannelCount(i3).setSampleRate(i4);
        } else if (MimeTypes.isText(a2)) {
            int i6 = -1;
            if (MimeTypes.APPLICATION_CEA608.equals(a2)) {
                i6 = parseCea608AccessibilityChannel(list2);
            } else if (MimeTypes.APPLICATION_CEA708.equals(a2)) {
                i6 = parseCea708AccessibilityChannel(list2);
            }
            language.setAccessibilityChannel(i6);
        }
        return language.build();
    }

    protected Representation buildRepresentation(RepresentationInfo representationInfo, @Nullable String str, @Nullable String str2, ArrayList<DrmInitData.SchemeData> arrayList, ArrayList<Descriptor> arrayList2) {
        Format.Builder buildUpon = representationInfo.format.buildUpon();
        if (str != null) {
            buildUpon.setLabel(str);
        }
        String str3 = representationInfo.drmSchemeType;
        if (str3 == null) {
            str3 = str2;
        }
        ArrayList<DrmInitData.SchemeData> arrayList3 = representationInfo.drmSchemeDatas;
        arrayList3.addAll(arrayList);
        if (!arrayList3.isEmpty()) {
            a(arrayList3);
            buildUpon.setDrmInitData(new DrmInitData(str3, arrayList3));
        }
        ArrayList<Descriptor> arrayList4 = representationInfo.inbandEventStreams;
        arrayList4.addAll(arrayList2);
        return Representation.newInstance(representationInfo.revisionId, buildUpon.build(), representationInfo.baseUrls, representationInfo.segmentBase, arrayList4);
    }

    protected SegmentBase.SingleSegmentBase parseSegmentBase(XmlPullParser xmlPullParser, @Nullable SegmentBase.SingleSegmentBase singleSegmentBase) throws XmlPullParserException, IOException {
        long parseLong = parseLong(xmlPullParser, "timescale", singleSegmentBase != null ? singleSegmentBase.b : 1L);
        long j = 0;
        long parseLong2 = parseLong(xmlPullParser, "presentationTimeOffset", singleSegmentBase != null ? singleSegmentBase.c : 0L);
        long j2 = singleSegmentBase != null ? singleSegmentBase.d : 0L;
        if (singleSegmentBase != null) {
            j = singleSegmentBase.e;
        }
        RangedUri rangedUri = null;
        String attributeValue = xmlPullParser.getAttributeValue(null, "indexRange");
        if (attributeValue != null) {
            String[] split = attributeValue.split(Constants.ACCEPT_TIME_SEPARATOR_SERVER);
            j2 = Long.parseLong(split[0]);
            j = (Long.parseLong(split[1]) - j2) + 1;
        }
        if (singleSegmentBase != null) {
            rangedUri = singleSegmentBase.a;
        }
        do {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, "Initialization")) {
                rangedUri = parseInitialization(xmlPullParser);
            } else {
                maybeSkipTag(xmlPullParser);
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, "SegmentBase"));
        return buildSingleSegmentBase(rangedUri, parseLong, parseLong2, j2, j);
    }

    protected SegmentBase.SingleSegmentBase buildSingleSegmentBase(RangedUri rangedUri, long j, long j2, long j3, long j4) {
        return new SegmentBase.SingleSegmentBase(rangedUri, j, j2, j3, j4);
    }

    protected SegmentBase.SegmentList parseSegmentList(XmlPullParser xmlPullParser, @Nullable SegmentBase.SegmentList segmentList, long j, long j2, long j3, long j4, long j5) throws XmlPullParserException, IOException {
        long j6 = 1;
        long parseLong = parseLong(xmlPullParser, "timescale", segmentList != null ? segmentList.b : 1L);
        long parseLong2 = parseLong(xmlPullParser, "presentationTimeOffset", segmentList != null ? segmentList.c : 0L);
        long parseLong3 = parseLong(xmlPullParser, "duration", segmentList != null ? segmentList.e : C.TIME_UNSET);
        if (segmentList != null) {
            j6 = segmentList.d;
        }
        long parseLong4 = parseLong(xmlPullParser, "startNumber", j6);
        long a2 = a(j3, j4);
        List<SegmentBase.SegmentTimelineElement> list = null;
        List<RangedUri> list2 = null;
        RangedUri rangedUri = null;
        do {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, "Initialization")) {
                rangedUri = parseInitialization(xmlPullParser);
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "SegmentTimeline")) {
                list = parseSegmentTimeline(xmlPullParser, parseLong, j2);
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "SegmentURL")) {
                if (list2 == null) {
                    list2 = new ArrayList<>();
                }
                list2.add(parseSegmentUrl(xmlPullParser));
            } else {
                maybeSkipTag(xmlPullParser);
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, "SegmentList"));
        if (segmentList != null) {
            if (rangedUri == null) {
                rangedUri = segmentList.a;
            }
            if (list == null) {
                list = segmentList.f;
            }
            if (list2 == null) {
                list2 = segmentList.h;
            }
        } else {
            list2 = list2;
            rangedUri = rangedUri;
        }
        return buildSegmentList(rangedUri, parseLong, parseLong2, parseLong4, parseLong3, list, a2, list2, j5, j);
    }

    protected SegmentBase.SegmentList buildSegmentList(RangedUri rangedUri, long j, long j2, long j3, long j4, @Nullable List<SegmentBase.SegmentTimelineElement> list, long j5, @Nullable List<RangedUri> list2, long j6, long j7) {
        return new SegmentBase.SegmentList(rangedUri, j, j2, j3, j4, list, j5, list2, C.msToUs(j6), C.msToUs(j7));
    }

    protected SegmentBase.SegmentTemplate parseSegmentTemplate(XmlPullParser xmlPullParser, @Nullable SegmentBase.SegmentTemplate segmentTemplate, List<Descriptor> list, long j, long j2, long j3, long j4, long j5) throws XmlPullParserException, IOException {
        long j6 = 1;
        long parseLong = parseLong(xmlPullParser, "timescale", segmentTemplate != null ? segmentTemplate.b : 1L);
        long parseLong2 = parseLong(xmlPullParser, "presentationTimeOffset", segmentTemplate != null ? segmentTemplate.c : 0L);
        long parseLong3 = parseLong(xmlPullParser, "duration", segmentTemplate != null ? segmentTemplate.e : C.TIME_UNSET);
        if (segmentTemplate != null) {
            j6 = segmentTemplate.d;
        }
        long parseLong4 = parseLong(xmlPullParser, "startNumber", j6);
        long parseLastSegmentNumberSupplementalProperty = parseLastSegmentNumberSupplementalProperty(list);
        long a2 = a(j3, j4);
        List<SegmentBase.SegmentTimelineElement> list2 = null;
        UrlTemplate parseUrlTemplate = parseUrlTemplate(xmlPullParser, "media", segmentTemplate != null ? segmentTemplate.i : null);
        UrlTemplate parseUrlTemplate2 = parseUrlTemplate(xmlPullParser, "initialization", segmentTemplate != null ? segmentTemplate.h : null);
        RangedUri rangedUri = null;
        while (true) {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, "Initialization")) {
                rangedUri = parseInitialization(xmlPullParser);
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "SegmentTimeline")) {
                list2 = parseSegmentTimeline(xmlPullParser, parseLong, j2);
            } else {
                maybeSkipTag(xmlPullParser);
            }
            if (XmlPullParserUtil.isEndTag(xmlPullParser, "SegmentTemplate")) {
                break;
            }
        }
        if (segmentTemplate != null) {
            if (rangedUri == null) {
                rangedUri = segmentTemplate.a;
            }
            if (list2 == null) {
                list2 = segmentTemplate.f;
            }
        } else {
            list2 = list2;
            rangedUri = rangedUri;
        }
        return buildSegmentTemplate(rangedUri, parseLong, parseLong2, parseLong4, parseLastSegmentNumberSupplementalProperty, parseLong3, list2, a2, parseUrlTemplate2, parseUrlTemplate, j5, j);
    }

    protected SegmentBase.SegmentTemplate buildSegmentTemplate(RangedUri rangedUri, long j, long j2, long j3, long j4, long j5, List<SegmentBase.SegmentTimelineElement> list, long j6, @Nullable UrlTemplate urlTemplate, @Nullable UrlTemplate urlTemplate2, long j7, long j8) {
        return new SegmentBase.SegmentTemplate(rangedUri, j, j2, j3, j4, j5, list, j6, urlTemplate, urlTemplate2, C.msToUs(j7), C.msToUs(j8));
    }

    protected EventStream parseEventStream(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        String parseString = parseString(xmlPullParser, "schemeIdUri", "");
        String parseString2 = parseString(xmlPullParser, b.p, "");
        long parseLong = parseLong(xmlPullParser, "timescale", 1L);
        ArrayList arrayList = new ArrayList();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(512);
        do {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, "Event")) {
                arrayList.add(parseEvent(xmlPullParser, parseString, parseString2, parseLong, byteArrayOutputStream));
            } else {
                maybeSkipTag(xmlPullParser);
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, "EventStream"));
        long[] jArr = new long[arrayList.size()];
        EventMessage[] eventMessageArr = new EventMessage[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            Pair pair = (Pair) arrayList.get(i);
            jArr[i] = ((Long) pair.first).longValue();
            eventMessageArr[i] = (EventMessage) pair.second;
        }
        return buildEventStream(parseString, parseString2, parseLong, jArr, eventMessageArr);
    }

    protected EventStream buildEventStream(String str, String str2, long j, long[] jArr, EventMessage[] eventMessageArr) {
        return new EventStream(str, str2, j, jArr, eventMessageArr);
    }

    protected Pair<Long, EventMessage> parseEvent(XmlPullParser xmlPullParser, String str, String str2, long j, ByteArrayOutputStream byteArrayOutputStream) throws IOException, XmlPullParserException {
        long parseLong = parseLong(xmlPullParser, "id", 0L);
        long parseLong2 = parseLong(xmlPullParser, "duration", C.TIME_UNSET);
        long parseLong3 = parseLong(xmlPullParser, "presentationTime", 0L);
        long scaleLargeTimestamp = Util.scaleLargeTimestamp(parseLong2, 1000L, j);
        long scaleLargeTimestamp2 = Util.scaleLargeTimestamp(parseLong3, 1000000L, j);
        String parseString = parseString(xmlPullParser, "messageData", null);
        byte[] parseEventObject = parseEventObject(xmlPullParser, byteArrayOutputStream);
        Long valueOf = Long.valueOf(scaleLargeTimestamp2);
        if (parseString != null) {
            parseEventObject = Util.getUtf8Bytes(parseString);
        }
        return Pair.create(valueOf, buildEvent(str, str2, parseLong, scaleLargeTimestamp, parseEventObject));
    }

    protected byte[] parseEventObject(XmlPullParser xmlPullParser, ByteArrayOutputStream byteArrayOutputStream) throws XmlPullParserException, IOException {
        byteArrayOutputStream.reset();
        XmlSerializer newSerializer = Xml.newSerializer();
        newSerializer.setOutput(byteArrayOutputStream, Charsets.UTF_8.name());
        xmlPullParser.nextToken();
        while (!XmlPullParserUtil.isEndTag(xmlPullParser, "Event")) {
            switch (xmlPullParser.getEventType()) {
                case 0:
                    newSerializer.startDocument(null, false);
                    break;
                case 1:
                    newSerializer.endDocument();
                    break;
                case 2:
                    newSerializer.startTag(xmlPullParser.getNamespace(), xmlPullParser.getName());
                    for (int i = 0; i < xmlPullParser.getAttributeCount(); i++) {
                        newSerializer.attribute(xmlPullParser.getAttributeNamespace(i), xmlPullParser.getAttributeName(i), xmlPullParser.getAttributeValue(i));
                    }
                    break;
                case 3:
                    newSerializer.endTag(xmlPullParser.getNamespace(), xmlPullParser.getName());
                    break;
                case 4:
                    newSerializer.text(xmlPullParser.getText());
                    break;
                case 5:
                    newSerializer.cdsect(xmlPullParser.getText());
                    break;
                case 6:
                    newSerializer.entityRef(xmlPullParser.getText());
                    break;
                case 7:
                    newSerializer.ignorableWhitespace(xmlPullParser.getText());
                    break;
                case 8:
                    newSerializer.processingInstruction(xmlPullParser.getText());
                    break;
                case 9:
                    newSerializer.comment(xmlPullParser.getText());
                    break;
                case 10:
                    newSerializer.docdecl(xmlPullParser.getText());
                    break;
            }
            xmlPullParser.nextToken();
        }
        newSerializer.flush();
        return byteArrayOutputStream.toByteArray();
    }

    protected EventMessage buildEvent(String str, String str2, long j, long j2, byte[] bArr) {
        return new EventMessage(str, str2, j2, j, bArr);
    }

    protected List<SegmentBase.SegmentTimelineElement> parseSegmentTimeline(XmlPullParser xmlPullParser, long j, long j2) throws XmlPullParserException, IOException {
        ArrayList arrayList = new ArrayList();
        long j3 = 0;
        boolean z = false;
        int i = 0;
        long j4 = -9223372036854775807L;
        do {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, ExifInterface.LATITUDE_SOUTH)) {
                j3 = parseLong(xmlPullParser, ai.aF, C.TIME_UNSET);
                if (z) {
                    j3 = a(arrayList, j3, j4, i, j3);
                }
                if (j3 == C.TIME_UNSET) {
                }
                z = true;
                j4 = parseLong(xmlPullParser, "d", C.TIME_UNSET);
                i = parseInt(xmlPullParser, "r", 0);
            } else {
                maybeSkipTag(xmlPullParser);
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, "SegmentTimeline"));
        if (z) {
            a(arrayList, j3, j4, i, Util.scaleLargeTimestamp(j2, j, 1000L));
        }
        return arrayList;
    }

    private long a(List<SegmentBase.SegmentTimelineElement> list, long j, long j2, int i, long j3) {
        int ceilDivide = i >= 0 ? i + 1 : (int) Util.ceilDivide(j3 - j, j2);
        for (int i2 = 0; i2 < ceilDivide; i2++) {
            list.add(buildSegmentTimelineElement(j, j2));
            j += j2;
        }
        return j;
    }

    protected SegmentBase.SegmentTimelineElement buildSegmentTimelineElement(long j, long j2) {
        return new SegmentBase.SegmentTimelineElement(j, j2);
    }

    @Nullable
    protected UrlTemplate parseUrlTemplate(XmlPullParser xmlPullParser, String str, @Nullable UrlTemplate urlTemplate) {
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        return attributeValue != null ? UrlTemplate.compile(attributeValue) : urlTemplate;
    }

    protected RangedUri parseInitialization(XmlPullParser xmlPullParser) {
        return parseRangedUrl(xmlPullParser, "sourceURL", Common.RANGE);
    }

    protected RangedUri parseSegmentUrl(XmlPullParser xmlPullParser) {
        return parseRangedUrl(xmlPullParser, "media", "mediaRange");
    }

    protected RangedUri parseRangedUrl(XmlPullParser xmlPullParser, String str, String str2) {
        long j;
        long j2;
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        String attributeValue2 = xmlPullParser.getAttributeValue(null, str2);
        if (attributeValue2 != null) {
            String[] split = attributeValue2.split(Constants.ACCEPT_TIME_SEPARATOR_SERVER);
            long parseLong = Long.parseLong(split[0]);
            if (split.length == 2) {
                j = (Long.parseLong(split[1]) - parseLong) + 1;
                j2 = parseLong;
            } else {
                j = -1;
                j2 = parseLong;
            }
        } else {
            j2 = 0;
            j = -1;
        }
        return buildRangedUri(attributeValue, j2, j);
    }

    protected RangedUri buildRangedUri(String str, long j, long j2) {
        return new RangedUri(str, j, j2);
    }

    protected ProgramInformation parseProgramInformation(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException {
        String str;
        String str2 = null;
        String parseString = parseString(xmlPullParser, "moreInformationURL", null);
        String parseString2 = parseString(xmlPullParser, "lang", null);
        String str3 = null;
        String str4 = null;
        while (true) {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, "Title")) {
                str2 = xmlPullParser.nextText();
                str = str4;
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "Source")) {
                str3 = xmlPullParser.nextText();
                str = str4;
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, ExifInterface.TAG_COPYRIGHT)) {
                str = xmlPullParser.nextText();
            } else {
                maybeSkipTag(xmlPullParser);
                str = str4;
            }
            if (XmlPullParserUtil.isEndTag(xmlPullParser, "ProgramInformation")) {
                return new ProgramInformation(str2, str3, str, parseString, parseString2);
            }
            str4 = str;
        }
    }

    protected String parseLabel(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        return parseText(xmlPullParser, "Label");
    }

    protected List<BaseUrl> parseBaseUrl(XmlPullParser xmlPullParser, List<BaseUrl> list) throws XmlPullParserException, IOException {
        String attributeValue = xmlPullParser.getAttributeValue(null, "dvb:priority");
        int parseInt = attributeValue != null ? Integer.parseInt(attributeValue) : 1;
        String attributeValue2 = xmlPullParser.getAttributeValue(null, "dvb:weight");
        int parseInt2 = attributeValue2 != null ? Integer.parseInt(attributeValue2) : 1;
        String attributeValue3 = xmlPullParser.getAttributeValue(null, "serviceLocation");
        String parseText = parseText(xmlPullParser, "BaseURL");
        if (attributeValue3 == null) {
            attributeValue3 = parseText;
        }
        if (UriUtil.isAbsolute(parseText)) {
            return Lists.newArrayList(new BaseUrl(parseText, attributeValue3, parseInt, parseInt2));
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            BaseUrl baseUrl = list.get(i);
            arrayList.add(new BaseUrl(UriUtil.resolve(baseUrl.url, parseText), baseUrl.serviceLocation, baseUrl.priority, baseUrl.weight));
        }
        return arrayList;
    }

    protected long parseAvailabilityTimeOffsetUs(XmlPullParser xmlPullParser, long j) {
        String attributeValue = xmlPullParser.getAttributeValue(null, "availabilityTimeOffset");
        if (attributeValue == null) {
            return j;
        }
        if ("INF".equals(attributeValue)) {
            return Long.MAX_VALUE;
        }
        return Float.parseFloat(attributeValue) * 1000000.0f;
    }

    protected int parseAudioChannelConfiguration(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        char c2;
        String parseString = parseString(xmlPullParser, "schemeIdUri", null);
        int hashCode = parseString.hashCode();
        int i = -1;
        if (hashCode == -1352850286) {
            if (parseString.equals("urn:mpeg:dash:23003:3:audio_channel_configuration:2011")) {
                c2 = 0;
            }
            c2 = 65535;
        } else if (hashCode == -1138141449) {
            if (parseString.equals("tag:dolby.com,2014:dash:audio_channel_configuration:2011")) {
                c2 = 2;
            }
            c2 = 65535;
        } else if (hashCode != -986633423) {
            if (hashCode == 2036691300 && parseString.equals("urn:dolby:dash:audio_channel_configuration:2011")) {
                c2 = 3;
            }
            c2 = 65535;
        } else {
            if (parseString.equals("urn:mpeg:mpegB:cicp:ChannelConfiguration")) {
                c2 = 1;
            }
            c2 = 65535;
        }
        switch (c2) {
            case 0:
                i = parseInt(xmlPullParser, b.p, -1);
                break;
            case 1:
                i = parseMpegChannelConfiguration(xmlPullParser);
                break;
            case 2:
            case 3:
                i = parseDolbyChannelConfiguration(xmlPullParser);
                break;
        }
        do {
            xmlPullParser.next();
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, "AudioChannelConfiguration"));
        return i;
    }

    protected int parseSelectionFlagsFromRoleDescriptors(List<Descriptor> list) {
        int i = 0;
        for (int i2 = 0; i2 < list.size(); i2++) {
            Descriptor descriptor = list.get(i2);
            if (Ascii.equalsIgnoreCase("urn:mpeg:dash:role:2011", descriptor.schemeIdUri)) {
                i |= parseSelectionFlagsFromDashRoleScheme(descriptor.value);
            }
        }
        return i;
    }

    protected int parseSelectionFlagsFromDashRoleScheme(@Nullable String str) {
        if (str == null) {
            return 0;
        }
        char c2 = 65535;
        int hashCode = str.hashCode();
        if (hashCode != -1574842690) {
            if (hashCode == 3343801 && str.equals("main")) {
                c2 = 0;
            }
        } else if (str.equals("forced_subtitle")) {
            c2 = 1;
        }
        switch (c2) {
            case 0:
                return 1;
            case 1:
                return 2;
            default:
                return 0;
        }
    }

    protected int parseRoleFlagsFromRoleDescriptors(List<Descriptor> list) {
        int i = 0;
        for (int i2 = 0; i2 < list.size(); i2++) {
            Descriptor descriptor = list.get(i2);
            if (Ascii.equalsIgnoreCase("urn:mpeg:dash:role:2011", descriptor.schemeIdUri)) {
                i |= parseRoleFlagsFromDashRoleScheme(descriptor.value);
            }
        }
        return i;
    }

    protected int parseRoleFlagsFromAccessibilityDescriptors(List<Descriptor> list) {
        int i = 0;
        for (int i2 = 0; i2 < list.size(); i2++) {
            Descriptor descriptor = list.get(i2);
            if (Ascii.equalsIgnoreCase("urn:mpeg:dash:role:2011", descriptor.schemeIdUri)) {
                i |= parseRoleFlagsFromDashRoleScheme(descriptor.value);
            } else if (Ascii.equalsIgnoreCase("urn:tva:metadata:cs:AudioPurposeCS:2007", descriptor.schemeIdUri)) {
                i |= parseTvaAudioPurposeCsValue(descriptor.value);
            }
        }
        return i;
    }

    protected int parseRoleFlagsFromProperties(List<Descriptor> list) {
        int i = 0;
        for (int i2 = 0; i2 < list.size(); i2++) {
            if (Ascii.equalsIgnoreCase("http://dashif.org/guidelines/trickmode", list.get(i2).schemeIdUri)) {
                i |= 16384;
            }
        }
        return i;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    protected int parseRoleFlagsFromDashRoleScheme(@Nullable String str) {
        if (str == null) {
            return 0;
        }
        char c2 = 65535;
        switch (str.hashCode()) {
            case -2060497896:
                if (str.equals("subtitle")) {
                    c2 = '\b';
                    break;
                }
                break;
            case -1724546052:
                if (str.equals("description")) {
                    c2 = '\n';
                    break;
                }
                break;
            case -1580883024:
                if (str.equals("enhanced-audio-intelligibility")) {
                    c2 = 11;
                    break;
                }
                break;
            case -1574842690:
                if (str.equals("forced_subtitle")) {
                    c2 = 7;
                    break;
                }
                break;
            case -1408024454:
                if (str.equals("alternate")) {
                    c2 = 1;
                    break;
                }
                break;
            case 99825:
                if (str.equals("dub")) {
                    c2 = 4;
                    break;
                }
                break;
            case 3343801:
                if (str.equals("main")) {
                    c2 = 0;
                    break;
                }
                break;
            case 3530173:
                if (str.equals("sign")) {
                    c2 = '\t';
                    break;
                }
                break;
            case 552573414:
                if (str.equals("caption")) {
                    c2 = 6;
                    break;
                }
                break;
            case 899152809:
                if (str.equals("commentary")) {
                    c2 = 3;
                    break;
                }
                break;
            case 1629013393:
                if (str.equals("emergency")) {
                    c2 = 5;
                    break;
                }
                break;
            case 1855372047:
                if (str.equals("supplementary")) {
                    c2 = 2;
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 4;
            case 3:
                return 8;
            case 4:
                return 16;
            case 5:
                return 32;
            case 6:
                return 64;
            case 7:
            case '\b':
                return 128;
            case '\t':
                return 256;
            case '\n':
                return 512;
            case 11:
                return 2048;
            default:
                return 0;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    protected int parseTvaAudioPurposeCsValue(@Nullable String str) {
        if (str == null) {
            return 0;
        }
        char c2 = 65535;
        switch (str.hashCode()) {
            case 49:
                if (str.equals("1")) {
                    c2 = 0;
                    break;
                }
                break;
            case 50:
                if (str.equals("2")) {
                    c2 = 1;
                    break;
                }
                break;
            case 51:
                if (str.equals("3")) {
                    c2 = 2;
                    break;
                }
                break;
            case 52:
                if (str.equals(Commands.ResolutionValues.BITSTREAM_BLUE_HIGH)) {
                    c2 = 3;
                    break;
                }
                break;
            case 54:
                if (str.equals("6")) {
                    c2 = 4;
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
                return 512;
            case 1:
                return 2048;
            case 2:
                return 4;
            case 3:
                return 8;
            case 4:
                return 1;
            default:
                return 0;
        }
    }

    public static void maybeSkipTag(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException {
        if (XmlPullParserUtil.isStartTag(xmlPullParser)) {
            int i = 1;
            while (i != 0) {
                xmlPullParser.next();
                if (XmlPullParserUtil.isStartTag(xmlPullParser)) {
                    i++;
                } else if (XmlPullParserUtil.isEndTag(xmlPullParser)) {
                    i--;
                }
            }
        }
    }

    private static void a(ArrayList<DrmInitData.SchemeData> arrayList) {
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            DrmInitData.SchemeData schemeData = arrayList.get(size);
            if (!schemeData.hasData()) {
                int i = 0;
                while (true) {
                    if (i >= arrayList.size()) {
                        break;
                    } else if (arrayList.get(i).canReplace(schemeData)) {
                        arrayList.remove(size);
                        break;
                    } else {
                        i++;
                    }
                }
            }
        }
    }

    @Nullable
    private static String a(@Nullable String str, @Nullable String str2) {
        if (MimeTypes.isAudio(str)) {
            return MimeTypes.getAudioMediaMimeType(str2);
        }
        if (MimeTypes.isVideo(str)) {
            return MimeTypes.getVideoMediaMimeType(str2);
        }
        if (MimeTypes.isText(str)) {
            return MimeTypes.APPLICATION_RAWCC.equals(str) ? MimeTypes.getTextMediaMimeType(str2) : str;
        }
        if (!MimeTypes.APPLICATION_MP4.equals(str)) {
            return null;
        }
        String mediaMimeType = MimeTypes.getMediaMimeType(str2);
        return MimeTypes.TEXT_VTT.equals(mediaMimeType) ? MimeTypes.APPLICATION_MP4VTT : mediaMimeType;
    }

    @Nullable
    private static String b(@Nullable String str, @Nullable String str2) {
        if (str == null) {
            return str2;
        }
        if (str2 == null) {
            return str;
        }
        Assertions.checkState(str.equals(str2));
        return str;
    }

    private static int a(int i, int i2) {
        if (i == -1) {
            return i2;
        }
        if (i2 == -1) {
            return i;
        }
        Assertions.checkState(i == i2);
        return i;
    }

    protected static Descriptor parseDescriptor(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        String parseString = parseString(xmlPullParser, "schemeIdUri", "");
        String parseString2 = parseString(xmlPullParser, b.p, null);
        String parseString3 = parseString(xmlPullParser, "id", null);
        do {
            xmlPullParser.next();
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, str));
        return new Descriptor(parseString, parseString2, parseString3);
    }

    protected static int parseCea608AccessibilityChannel(List<Descriptor> list) {
        for (int i = 0; i < list.size(); i++) {
            Descriptor descriptor = list.get(i);
            if ("urn:scte:dash:cc:cea-608:2015".equals(descriptor.schemeIdUri) && descriptor.value != null) {
                Matcher matcher = b.matcher(descriptor.value);
                if (matcher.matches()) {
                    return Integer.parseInt(matcher.group(1));
                }
                String valueOf = String.valueOf(descriptor.value);
                Log.w("MpdParser", valueOf.length() != 0 ? "Unable to parse CEA-608 channel number from: ".concat(valueOf) : new String("Unable to parse CEA-608 channel number from: "));
            }
        }
        return -1;
    }

    protected static int parseCea708AccessibilityChannel(List<Descriptor> list) {
        for (int i = 0; i < list.size(); i++) {
            Descriptor descriptor = list.get(i);
            if ("urn:scte:dash:cc:cea-708:2015".equals(descriptor.schemeIdUri) && descriptor.value != null) {
                Matcher matcher = c.matcher(descriptor.value);
                if (matcher.matches()) {
                    return Integer.parseInt(matcher.group(1));
                }
                String valueOf = String.valueOf(descriptor.value);
                Log.w("MpdParser", valueOf.length() != 0 ? "Unable to parse CEA-708 service block number from: ".concat(valueOf) : new String("Unable to parse CEA-708 service block number from: "));
            }
        }
        return -1;
    }

    protected static String parseEac3SupplementalProperties(List<Descriptor> list) {
        for (int i = 0; i < list.size(); i++) {
            Descriptor descriptor = list.get(i);
            String str = descriptor.schemeIdUri;
            if ("tag:dolby.com,2018:dash:EC3_ExtensionType:2018".equals(str) && "JOC".equals(descriptor.value)) {
                return MimeTypes.AUDIO_E_AC3_JOC;
            }
            if ("tag:dolby.com,2014:dash:DolbyDigitalPlusExtensionType:2014".equals(str) && Ac3Util.E_AC3_JOC_CODEC_STRING.equals(descriptor.value)) {
                return MimeTypes.AUDIO_E_AC3_JOC;
            }
        }
        return MimeTypes.AUDIO_E_AC3;
    }

    protected static float parseFrameRate(XmlPullParser xmlPullParser, float f) {
        String attributeValue = xmlPullParser.getAttributeValue(null, "frameRate");
        if (attributeValue == null) {
            return f;
        }
        Matcher matcher = a.matcher(attributeValue);
        if (!matcher.matches()) {
            return f;
        }
        int parseInt = Integer.parseInt(matcher.group(1));
        String group = matcher.group(2);
        return !TextUtils.isEmpty(group) ? parseInt / Integer.parseInt(group) : parseInt;
    }

    protected static long parseDuration(XmlPullParser xmlPullParser, String str, long j) {
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        return attributeValue == null ? j : Util.parseXsDuration(attributeValue);
    }

    protected static long parseDateTime(XmlPullParser xmlPullParser, String str, long j) throws ParserException {
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        return attributeValue == null ? j : Util.parseXsDateTime(attributeValue);
    }

    protected static String parseText(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        String str2 = "";
        do {
            xmlPullParser.next();
            if (xmlPullParser.getEventType() == 4) {
                str2 = xmlPullParser.getText();
            } else {
                maybeSkipTag(xmlPullParser);
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, str));
        return str2;
    }

    protected static int parseInt(XmlPullParser xmlPullParser, String str, int i) {
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        return attributeValue == null ? i : Integer.parseInt(attributeValue);
    }

    protected static long parseLong(XmlPullParser xmlPullParser, String str, long j) {
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        return attributeValue == null ? j : Long.parseLong(attributeValue);
    }

    protected static float parseFloat(XmlPullParser xmlPullParser, String str, float f) {
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        return attributeValue == null ? f : Float.parseFloat(attributeValue);
    }

    protected static String parseString(XmlPullParser xmlPullParser, String str, String str2) {
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        return attributeValue == null ? str2 : attributeValue;
    }

    protected static int parseMpegChannelConfiguration(XmlPullParser xmlPullParser) {
        int parseInt = parseInt(xmlPullParser, b.p, -1);
        if (parseInt < 0) {
            return -1;
        }
        int[] iArr = d;
        if (parseInt < iArr.length) {
            return iArr[parseInt];
        }
        return -1;
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0056 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0057 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x005a A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x005c A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x005d A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected static int parseDolbyChannelConfiguration(org.xmlpull.v1.XmlPullParser r5) {
        /*
            java.lang.String r0 = "value"
            r1 = 0
            java.lang.String r5 = r5.getAttributeValue(r1, r0)
            r0 = -1
            if (r5 != 0) goto L_0x000b
            return r0
        L_0x000b:
            java.lang.String r5 = com.google.common.base.Ascii.toLowerCase(r5)
            int r1 = r5.hashCode()
            r2 = 1596796(0x185d7c, float:2.237588E-39)
            r3 = 2
            r4 = 1
            if (r1 == r2) goto L_0x0048
            r2 = 2937391(0x2cd22f, float:4.116161E-39)
            if (r1 == r2) goto L_0x003e
            r2 = 3094035(0x2f3613, float:4.335666E-39)
            if (r1 == r2) goto L_0x0034
            r2 = 3133436(0x2fcffc, float:4.390879E-39)
            if (r1 == r2) goto L_0x002a
            goto L_0x0052
        L_0x002a:
            java.lang.String r1 = "fa01"
            boolean r5 = r5.equals(r1)
            if (r5 == 0) goto L_0x0052
            r5 = 3
            goto L_0x0053
        L_0x0034:
            java.lang.String r1 = "f801"
            boolean r5 = r5.equals(r1)
            if (r5 == 0) goto L_0x0052
            r5 = r3
            goto L_0x0053
        L_0x003e:
            java.lang.String r1 = "a000"
            boolean r5 = r5.equals(r1)
            if (r5 == 0) goto L_0x0052
            r5 = r4
            goto L_0x0053
        L_0x0048:
            java.lang.String r1 = "4000"
            boolean r5 = r5.equals(r1)
            if (r5 == 0) goto L_0x0052
            r5 = 0
            goto L_0x0053
        L_0x0052:
            r5 = r0
        L_0x0053:
            switch(r5) {
                case 0: goto L_0x005d;
                case 1: goto L_0x005c;
                case 2: goto L_0x005a;
                case 3: goto L_0x0057;
                default: goto L_0x0056;
            }
        L_0x0056:
            return r0
        L_0x0057:
            r5 = 8
            return r5
        L_0x005a:
            r5 = 6
            return r5
        L_0x005c:
            return r3
        L_0x005d:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.dash.manifest.DashManifestParser.parseDolbyChannelConfiguration(org.xmlpull.v1.XmlPullParser):int");
    }

    protected static long parseLastSegmentNumberSupplementalProperty(List<Descriptor> list) {
        for (int i = 0; i < list.size(); i++) {
            Descriptor descriptor = list.get(i);
            if (Ascii.equalsIgnoreCase("http://dashif.org/guidelines/last-segment-number", descriptor.schemeIdUri)) {
                return Long.parseLong(descriptor.value);
            }
        }
        return -1L;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes2.dex */
    public static final class RepresentationInfo {
        public final ImmutableList<BaseUrl> baseUrls;
        public final ArrayList<DrmInitData.SchemeData> drmSchemeDatas;
        @Nullable
        public final String drmSchemeType;
        public final Format format;
        public final ArrayList<Descriptor> inbandEventStreams;
        public final long revisionId;
        public final SegmentBase segmentBase;

        public RepresentationInfo(Format format, List<BaseUrl> list, SegmentBase segmentBase, @Nullable String str, ArrayList<DrmInitData.SchemeData> arrayList, ArrayList<Descriptor> arrayList2, long j) {
            this.format = format;
            this.baseUrls = ImmutableList.copyOf((Collection) list);
            this.segmentBase = segmentBase;
            this.drmSchemeType = str;
            this.drmSchemeDatas = arrayList;
            this.inbandEventStreams = arrayList2;
            this.revisionId = j;
        }
    }
}
