package androidx.core.text.util;

import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import java.util.Locale;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: FindAddress.java */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes.dex */
class a {
    private static final C0012a[] a = {new C0012a(99, 99, -1, -1), new C0012a(35, 36, -1, -1), new C0012a(71, 72, -1, -1), new C0012a(96, 96, -1, -1), new C0012a(85, 86, -1, -1), new C0012a(90, 96, -1, -1), new C0012a(80, 81, -1, -1), new C0012a(6, 6, -1, -1), new C0012a(20, 20, -1, -1), new C0012a(19, 19, -1, -1), new C0012a(32, 34, -1, -1), new C0012a(96, 96, -1, -1), new C0012a(30, 31, -1, -1), new C0012a(96, 96, -1, -1), new C0012a(96, 96, -1, -1), new C0012a(50, 52, -1, -1), new C0012a(83, 83, -1, -1), new C0012a(60, 62, -1, -1), new C0012a(46, 47, -1, -1), new C0012a(66, 67, 73, -1), new C0012a(40, 42, -1, -1), new C0012a(70, 71, -1, -1), new C0012a(1, 2, -1, -1), new C0012a(20, 21, -1, -1), new C0012a(3, 4, -1, -1), new C0012a(96, 96, -1, -1), new C0012a(48, 49, -1, -1), new C0012a(55, 56, -1, -1), new C0012a(63, 65, -1, -1), new C0012a(96, 96, -1, -1), new C0012a(38, 39, -1, -1), new C0012a(55, 56, -1, -1), new C0012a(27, 28, -1, -1), new C0012a(58, 58, -1, -1), new C0012a(68, 69, -1, -1), new C0012a(3, 4, -1, -1), new C0012a(7, 8, -1, -1), new C0012a(87, 88, 86, -1), new C0012a(88, 89, 96, -1), new C0012a(10, 14, 0, 6), new C0012a(43, 45, -1, -1), new C0012a(73, 74, -1, -1), new C0012a(97, 97, -1, -1), new C0012a(15, 19, -1, -1), new C0012a(6, 6, 0, 9), new C0012a(96, 96, -1, -1), new C0012a(2, 2, -1, -1), new C0012a(29, 29, -1, -1), new C0012a(57, 57, -1, -1), new C0012a(37, 38, -1, -1), new C0012a(75, 79, 87, 88), new C0012a(84, 84, -1, -1), new C0012a(22, 24, 20, -1), new C0012a(6, 9, -1, -1), new C0012a(5, 5, -1, -1), new C0012a(98, 99, -1, -1), new C0012a(53, 54, -1, -1), new C0012a(24, 26, -1, -1), new C0012a(82, 83, -1, -1)};
    private static final Pattern b = Pattern.compile("[^,*•\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000\n\u000b\f\r\u0085\u2028\u2029]+(?=[,*•\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000\n\u000b\f\r\u0085\u2028\u2029]|$)", 2);
    private static final Pattern c = Pattern.compile("(?:one|[0-9]+([a-z](?=[^a-z]|$)|st|nd|rd|th)?)(?:-(?:one|[0-9]+([a-z](?=[^a-z]|$)|st|nd|rd|th)?))*(?=[,\"'\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000\n\u000b\f\r\u0085\u2028\u2029]|$)", 2);
    private static final Pattern d = Pattern.compile("(?:(ak|alaska)|(al|alabama)|(ar|arkansas)|(as|american[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+samoa)|(az|arizona)|(ca|california)|(co|colorado)|(ct|connecticut)|(dc|district[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+of[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+columbia)|(de|delaware)|(fl|florida)|(fm|federated[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+states[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+of[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+micronesia)|(ga|georgia)|(gu|guam)|(hi|hawaii)|(ia|iowa)|(id|idaho)|(il|illinois)|(in|indiana)|(ks|kansas)|(ky|kentucky)|(la|louisiana)|(ma|massachusetts)|(md|maryland)|(me|maine)|(mh|marshall[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+islands)|(mi|michigan)|(mn|minnesota)|(mo|missouri)|(mp|northern[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+mariana[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+islands)|(ms|mississippi)|(mt|montana)|(nc|north[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+carolina)|(nd|north[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+dakota)|(ne|nebraska)|(nh|new[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+hampshire)|(nj|new[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+jersey)|(nm|new[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+mexico)|(nv|nevada)|(ny|new[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+york)|(oh|ohio)|(ok|oklahoma)|(or|oregon)|(pa|pennsylvania)|(pr|puerto[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+rico)|(pw|palau)|(ri|rhode[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+island)|(sc|south[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+carolina)|(sd|south[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+dakota)|(tn|tennessee)|(tx|texas)|(ut|utah)|(va|virginia)|(vi|virgin[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+islands)|(vt|vermont)|(wa|washington)|(wi|wisconsin)|(wv|west[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+virginia)|(wy|wyoming))(?=[,*•\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000\n\u000b\f\r\u0085\u2028\u2029]|$)", 2);
    private static final Pattern e = Pattern.compile("(?:alley|annex|arcade|ave[.]?|avenue|alameda|bayou|beach|bend|bluffs?|bottom|boulevard|branch|bridge|brooks?|burgs?|bypass|broadway|camino|camp|canyon|cape|causeway|centers?|circles?|cliffs?|club|common|corners?|course|courts?|coves?|creek|crescent|crest|crossing|crossroad|curve|circulo|dale|dam|divide|drives?|estates?|expressway|extensions?|falls?|ferry|fields?|flats?|fords?|forest|forges?|forks?|fort|freeway|gardens?|gateway|glens?|greens?|groves?|harbors?|haven|heights|highway|hills?|hollow|inlet|islands?|isle|junctions?|keys?|knolls?|lakes?|land|landing|lane|lights?|loaf|locks?|lodge|loop|mall|manors?|meadows?|mews|mills?|mission|motorway|mount|mountains?|neck|orchard|oval|overpass|parks?|parkways?|pass|passage|path|pike|pines?|plains?|plaza|points?|ports?|prairie|privada|radial|ramp|ranch|rapids?|rd[.]?|rest|ridges?|river|roads?|route|row|rue|run|shoals?|shores?|skyway|springs?|spurs?|squares?|station|stravenue|stream|st[.]?|streets?|summit|speedway|terrace|throughway|trace|track|trafficway|trail|tunnel|turnpike|underpass|unions?|valleys?|viaduct|views?|villages?|ville|vista|walks?|wall|ways?|wells?|xing|xrd)(?=[,*•\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000\n\u000b\f\r\u0085\u2028\u2029]|$)", 2);
    private static final Pattern f = Pattern.compile("([0-9]+)(st|nd|rd|th)", 2);
    private static final Pattern g = Pattern.compile("(?:[0-9]{5}(?:-[0-9]{4})?)(?=[,*•\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000\n\u000b\f\r\u0085\u2028\u2029]|$)", 2);

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: FindAddress.java */
    /* renamed from: androidx.core.text.util.a$a  reason: collision with other inner class name */
    /* loaded from: classes.dex */
    public static class C0012a {
        int a;
        int b;
        int c;
        int d;

        C0012a(int i, int i2, int i3, int i4) {
            this.a = i;
            this.b = i2;
            this.c = i3;
            this.d = i4;
        }

        boolean a(String str) {
            int parseInt = Integer.parseInt(str.substring(0, 2));
            return (this.a <= parseInt && parseInt <= this.b) || parseInt == this.c || parseInt == this.d;
        }
    }

    private static boolean c(String str) {
        int i = 0;
        for (int i2 = 0; i2 < str.length(); i2++) {
            if (Character.isDigit(str.charAt(i2))) {
                i++;
            }
        }
        if (i > 5) {
            return false;
        }
        Matcher matcher = f.matcher(str);
        if (!matcher.find()) {
            return true;
        }
        int parseInt = Integer.parseInt(matcher.group(1));
        if (parseInt == 0) {
            return false;
        }
        String lowerCase = matcher.group(2).toLowerCase(Locale.getDefault());
        switch (parseInt % 10) {
            case 1:
                return lowerCase.equals(parseInt % 100 == 11 ? "th" : "st");
            case 2:
                return lowerCase.equals(parseInt % 100 == 12 ? "th" : "nd");
            case 3:
                return lowerCase.equals(parseInt % 100 == 13 ? "th" : "rd");
            default:
                return lowerCase.equals("th");
        }
    }

    @VisibleForTesting
    public static MatchResult a(String str, int i) {
        if (i > 0 && ":,\"'\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000\n\u000b\f\r\u0085\u2028\u2029".indexOf(str.charAt(i - 1)) == -1) {
            return null;
        }
        Matcher region = c.matcher(str).region(i, str.length());
        if (region.lookingAt()) {
            MatchResult matchResult = region.toMatchResult();
            if (c(matchResult.group(0))) {
                return matchResult;
            }
        }
        return null;
    }

    @VisibleForTesting
    public static MatchResult b(String str, int i) {
        if (i > 0 && ",*•\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000\n\u000b\f\r\u0085\u2028\u2029".indexOf(str.charAt(i - 1)) == -1) {
            return null;
        }
        Matcher region = d.matcher(str).region(i, str.length());
        if (region.lookingAt()) {
            return region.toMatchResult();
        }
        return null;
    }

    private static boolean a(String str, MatchResult matchResult) {
        if (matchResult == null) {
            return false;
        }
        int groupCount = matchResult.groupCount();
        while (true) {
            if (groupCount <= 0) {
                break;
            }
            int i = groupCount - 1;
            if (matchResult.group(groupCount) != null) {
                groupCount = i;
                break;
            }
            groupCount = i;
        }
        return g.matcher(str).matches() && a[groupCount].a(str);
    }

    @VisibleForTesting
    public static boolean a(String str) {
        return e.matcher(str).matches();
    }

    private static int b(String str, MatchResult matchResult) {
        MatchResult b2;
        int end = matchResult.end();
        String str2 = "";
        Matcher matcher = b.matcher(str);
        int i = -1;
        int i2 = -1;
        boolean z = false;
        int i3 = 1;
        int i4 = 1;
        boolean z2 = true;
        while (true) {
            if (end < str.length()) {
                if (matcher.find(end)) {
                    if (matcher.end() - matcher.start() <= 25) {
                        while (end < matcher.start()) {
                            end++;
                            if ("\n\u000b\f\r\u0085\u2028\u2029".indexOf(str.charAt(end)) != -1) {
                                i3++;
                            }
                        }
                        if (i3 > 5 || (i4 = i4 + 1) > 14) {
                            break;
                        }
                        if (a(str, end) == null) {
                            if (!a(matcher.group(0))) {
                                if (i4 == 5 && !z) {
                                    end = matcher.end();
                                    break;
                                }
                                if (z && i4 > 4 && (b2 = b(str, end)) != null) {
                                    if (str2.equals("et") && b2.group(0).equals("al")) {
                                        end = b2.end();
                                        break;
                                    }
                                    Matcher matcher2 = b.matcher(str);
                                    if (!matcher2.find(b2.end())) {
                                        i2 = b2.end();
                                        z2 = false;
                                    } else if (a(matcher2.group(0), b2)) {
                                        return matcher2.end();
                                    }
                                }
                                z2 = false;
                            } else {
                                z2 = false;
                                z = true;
                            }
                        } else if (z2 && i3 > 1) {
                            return -end;
                        } else {
                            if (i == -1) {
                                i = end;
                            }
                        }
                        str2 = matcher.group(0);
                        end = matcher.end();
                    } else {
                        return -matcher.end();
                    }
                } else {
                    return -str.length();
                }
            } else {
                break;
            }
        }
        if (i2 > 0) {
            return i2;
        }
        if (i > 0) {
            end = i;
        }
        return -end;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String b(String str) {
        Matcher matcher = c.matcher(str);
        int i = 0;
        while (matcher.find(i)) {
            if (c(matcher.group(0))) {
                int start = matcher.start();
                int b2 = b(str, matcher);
                if (b2 > 0) {
                    return str.substring(start, b2);
                }
                i = -b2;
            } else {
                i = matcher.end();
            }
        }
        return null;
    }
}
