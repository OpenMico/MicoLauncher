package org.apache.commons.codec.language.bm;

import com.xiaomi.micolauncher.common.track.VideoTrackHelper;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;
import org.apache.commons.codec.language.bm.Languages;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes5.dex */
public class Rule {
    public static final String ALL = "ALL";
    public static final RPattern ALL_STRINGS_RMATCHER = new RPattern() { // from class: org.apache.commons.codec.language.bm.Rule.1
        @Override // org.apache.commons.codec.language.bm.Rule.RPattern
        public boolean isMatch(CharSequence charSequence) {
            return true;
        }
    };
    private static final Map<NameType, Map<RuleType, Map<String, Map<String, List<Rule>>>>> a = new EnumMap(NameType.class);
    private final RPattern b;
    private final String c;
    private final PhonemeExpr d;
    private final RPattern e;

    /* loaded from: classes5.dex */
    public interface PhonemeExpr {
        Iterable<Phoneme> getPhonemes();
    }

    /* loaded from: classes5.dex */
    public interface RPattern {
        boolean isMatch(CharSequence charSequence);
    }

    /* loaded from: classes5.dex */
    public static final class Phoneme implements PhonemeExpr {
        public static final Comparator<Phoneme> COMPARATOR = new Comparator<Phoneme>() { // from class: org.apache.commons.codec.language.bm.Rule.Phoneme.1
            /* renamed from: a */
            public int compare(Phoneme phoneme, Phoneme phoneme2) {
                for (int i = 0; i < phoneme.a.length(); i++) {
                    if (i >= phoneme2.a.length()) {
                        return 1;
                    }
                    int charAt = phoneme.a.charAt(i) - phoneme2.a.charAt(i);
                    if (charAt != 0) {
                        return charAt;
                    }
                }
                return phoneme.a.length() < phoneme2.a.length() ? -1 : 0;
            }
        };
        private final StringBuilder a;
        private final Languages.LanguageSet b;

        public Phoneme(CharSequence charSequence, Languages.LanguageSet languageSet) {
            this.a = new StringBuilder(charSequence);
            this.b = languageSet;
        }

        public Phoneme(Phoneme phoneme, Phoneme phoneme2) {
            this(phoneme.a, phoneme.b);
            this.a.append((CharSequence) phoneme2.a);
        }

        public Phoneme(Phoneme phoneme, Phoneme phoneme2, Languages.LanguageSet languageSet) {
            this(phoneme.a, languageSet);
            this.a.append((CharSequence) phoneme2.a);
        }

        public Phoneme append(CharSequence charSequence) {
            this.a.append(charSequence);
            return this;
        }

        public Languages.LanguageSet getLanguages() {
            return this.b;
        }

        @Override // org.apache.commons.codec.language.bm.Rule.PhonemeExpr
        public Iterable<Phoneme> getPhonemes() {
            return Collections.singleton(this);
        }

        public CharSequence getPhonemeText() {
            return this.a;
        }

        @Deprecated
        public Phoneme join(Phoneme phoneme) {
            return new Phoneme(this.a.toString() + phoneme.a.toString(), this.b.restrictTo(phoneme.b));
        }

        public Phoneme mergeWithLanguage(Languages.LanguageSet languageSet) {
            return new Phoneme(this.a.toString(), this.b.merge(languageSet));
        }

        public String toString() {
            return this.a.toString() + "[" + this.b + "]";
        }
    }

    /* loaded from: classes5.dex */
    public static final class PhonemeList implements PhonemeExpr {
        private final List<Phoneme> a;

        public PhonemeList(List<Phoneme> list) {
            this.a = list;
        }

        @Override // org.apache.commons.codec.language.bm.Rule.PhonemeExpr
        public List<Phoneme> getPhonemes() {
            return this.a;
        }
    }

    static {
        NameType[] values = NameType.values();
        for (NameType nameType : values) {
            EnumMap enumMap = new EnumMap(RuleType.class);
            RuleType[] values2 = RuleType.values();
            for (RuleType ruleType : values2) {
                HashMap hashMap = new HashMap();
                for (String str : Languages.getInstance(nameType).getLanguages()) {
                    try {
                        hashMap.put(str, a(b(nameType, ruleType, str), a(nameType, ruleType, str)));
                    } catch (IllegalStateException e) {
                        throw new IllegalStateException("Problem processing " + a(nameType, ruleType, str), e);
                    }
                }
                if (!ruleType.equals(RuleType.RULES)) {
                    hashMap.put(VideoTrackHelper.PAGE_COMMON, a(b(nameType, ruleType, VideoTrackHelper.PAGE_COMMON), a(nameType, ruleType, VideoTrackHelper.PAGE_COMMON)));
                }
                enumMap.put((EnumMap) ruleType, (RuleType) Collections.unmodifiableMap(hashMap));
            }
            a.put(nameType, Collections.unmodifiableMap(enumMap));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean b(CharSequence charSequence, char c) {
        for (int i = 0; i < charSequence.length(); i++) {
            if (charSequence.charAt(i) == c) {
                return true;
            }
        }
        return false;
    }

    private static String a(NameType nameType, RuleType ruleType, String str) {
        return String.format("org/apache/commons/codec/language/bm/%s_%s_%s.txt", nameType.getName(), ruleType.getName(), str);
    }

    private static Scanner b(NameType nameType, RuleType ruleType, String str) {
        String a2 = a(nameType, ruleType, str);
        InputStream resourceAsStream = Languages.class.getClassLoader().getResourceAsStream(a2);
        if (resourceAsStream != null) {
            return new Scanner(resourceAsStream, "UTF-8");
        }
        throw new IllegalArgumentException("Unable to load resource: " + a2);
    }

    private static Scanner a(String str) {
        String format = String.format("org/apache/commons/codec/language/bm/%s.txt", str);
        InputStream resourceAsStream = Languages.class.getClassLoader().getResourceAsStream(format);
        if (resourceAsStream != null) {
            return new Scanner(resourceAsStream, "UTF-8");
        }
        throw new IllegalArgumentException("Unable to load resource: " + format);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean c(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence2.length() > charSequence.length()) {
            return false;
        }
        int length = charSequence.length() - 1;
        for (int length2 = charSequence2.length() - 1; length2 >= 0; length2--) {
            if (charSequence.charAt(length) != charSequence2.charAt(length2)) {
                return false;
            }
            length--;
        }
        return true;
    }

    public static List<Rule> getInstance(NameType nameType, RuleType ruleType, Languages.LanguageSet languageSet) {
        Map<String, List<Rule>> instanceMap = getInstanceMap(nameType, ruleType, languageSet);
        ArrayList arrayList = new ArrayList();
        for (List<Rule> list : instanceMap.values()) {
            arrayList.addAll(list);
        }
        return arrayList;
    }

    public static List<Rule> getInstance(NameType nameType, RuleType ruleType, String str) {
        return getInstance(nameType, ruleType, Languages.LanguageSet.from(new HashSet(Arrays.asList(str))));
    }

    public static Map<String, List<Rule>> getInstanceMap(NameType nameType, RuleType ruleType, Languages.LanguageSet languageSet) {
        return getInstanceMap(nameType, ruleType, languageSet.isSingleton() ? languageSet.getAny() : Languages.ANY);
    }

    public static Map<String, List<Rule>> getInstanceMap(NameType nameType, RuleType ruleType, String str) {
        Map<String, List<Rule>> map = a.get(nameType).get(ruleType).get(str);
        if (map != null) {
            return map;
        }
        throw new IllegalArgumentException(String.format("No rules found for %s, %s, %s.", nameType.getName(), ruleType.getName(), str));
    }

    private static Phoneme b(String str) {
        int indexOf = str.indexOf("[");
        if (indexOf < 0) {
            return new Phoneme(str, Languages.ANY_LANGUAGE);
        }
        if (str.endsWith("]")) {
            return new Phoneme(str.substring(0, indexOf), Languages.LanguageSet.from(new HashSet(Arrays.asList(str.substring(indexOf + 1, str.length() - 1).split("[+]")))));
        }
        throw new IllegalArgumentException("Phoneme expression contains a '[' but does not end in ']'");
    }

    private static PhonemeExpr c(String str) {
        if (!str.startsWith("(")) {
            return b(str);
        }
        if (str.endsWith(")")) {
            ArrayList arrayList = new ArrayList();
            String substring = str.substring(1, str.length() - 1);
            for (String str2 : substring.split("[|]")) {
                arrayList.add(b(str2));
            }
            if (substring.startsWith("|") || substring.endsWith("|")) {
                arrayList.add(new Phoneme("", Languages.ANY_LANGUAGE));
            }
            return new PhonemeList(arrayList);
        }
        throw new IllegalArgumentException("Phoneme starts with '(' so must end with ')'");
    }

    private static Map<String, List<Rule>> a(Scanner scanner, final String str) {
        HashMap hashMap = new HashMap();
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (scanner.hasNextLine()) {
            final int i4 = i2 + 1;
            String nextLine = scanner.nextLine();
            if (i3 != 0) {
                if (nextLine.endsWith("*/")) {
                    i = i;
                    i3 = i;
                } else {
                    i = i;
                }
            } else if (nextLine.startsWith("/*")) {
                i = i;
                i3 = 1;
            } else {
                int indexOf = nextLine.indexOf("//");
                String trim = (indexOf >= 0 ? nextLine.substring(i, indexOf) : nextLine).trim();
                if (trim.length() == 0) {
                    i2 = i4;
                } else if (trim.startsWith("#include")) {
                    String trim2 = trim.substring(8).trim();
                    if (!trim2.contains(StringUtils.SPACE)) {
                        hashMap.putAll(a(a(trim2), str + "->" + trim2));
                        i = i;
                    } else {
                        throw new IllegalArgumentException("Malformed import statement '" + nextLine + "' in " + str);
                    }
                } else {
                    String[] split = trim.split("\\s+");
                    if (split.length == 4) {
                        try {
                            final String e = e(split[i]);
                            final String e2 = e(split[1]);
                            final String e3 = e(split[2]);
                            Rule rule = new Rule(e, e2, e3, c(e(split[3]))) { // from class: org.apache.commons.codec.language.bm.Rule.3
                                private final int f;
                                private final String g;

                                {
                                    this.f = i4;
                                    this.g = str;
                                }

                                public String toString() {
                                    return "Rule{line=" + this.f + ", loc='" + this.g + "', pat='" + e + "', lcon='" + e2 + "', rcon='" + e3 + "'}";
                                }
                            };
                            i = 0;
                            String substring = rule.c.substring(0, 1);
                            List list = (List) hashMap.get(substring);
                            if (list == null) {
                                list = new ArrayList();
                                hashMap.put(substring, list);
                            }
                            list.add(rule);
                        } catch (IllegalArgumentException e4) {
                            throw new IllegalStateException("Problem parsing line '" + i4 + "' in " + str, e4);
                        }
                    } else {
                        throw new IllegalArgumentException("Malformed rule statement split into " + split.length + " parts: " + nextLine + " in " + str);
                    }
                }
            }
            i2 = i4;
        }
        return hashMap;
    }

    private static RPattern d(final String str) {
        boolean startsWith = str.startsWith("^");
        boolean endsWith = str.endsWith("$");
        final String substring = str.substring(startsWith ? 1 : 0, endsWith ? str.length() - 1 : str.length());
        if (substring.contains("[")) {
            boolean startsWith2 = substring.startsWith("[");
            boolean endsWith2 = substring.endsWith("]");
            if (startsWith2 && endsWith2) {
                final String substring2 = substring.substring(1, substring.length() - 1);
                if (!substring2.contains("[")) {
                    boolean startsWith3 = substring2.startsWith("^");
                    if (startsWith3) {
                        substring2 = substring2.substring(1);
                    }
                    final boolean z = true ^ startsWith3;
                    if (startsWith && endsWith) {
                        return new RPattern() { // from class: org.apache.commons.codec.language.bm.Rule.8
                            @Override // org.apache.commons.codec.language.bm.Rule.RPattern
                            public boolean isMatch(CharSequence charSequence) {
                                return charSequence.length() == 1 && Rule.b(substring2, charSequence.charAt(0)) == z;
                            }
                        };
                    }
                    if (startsWith) {
                        return new RPattern() { // from class: org.apache.commons.codec.language.bm.Rule.9
                            @Override // org.apache.commons.codec.language.bm.Rule.RPattern
                            public boolean isMatch(CharSequence charSequence) {
                                return charSequence.length() > 0 && Rule.b(substring2, charSequence.charAt(0)) == z;
                            }
                        };
                    }
                    if (endsWith) {
                        return new RPattern() { // from class: org.apache.commons.codec.language.bm.Rule.10
                            @Override // org.apache.commons.codec.language.bm.Rule.RPattern
                            public boolean isMatch(CharSequence charSequence) {
                                return charSequence.length() > 0 && Rule.b(substring2, charSequence.charAt(charSequence.length() - 1)) == z;
                            }
                        };
                    }
                }
            }
        } else if (!startsWith || !endsWith) {
            if ((startsWith || endsWith) && substring.length() == 0) {
                return ALL_STRINGS_RMATCHER;
            }
            if (startsWith) {
                return new RPattern() { // from class: org.apache.commons.codec.language.bm.Rule.6
                    @Override // org.apache.commons.codec.language.bm.Rule.RPattern
                    public boolean isMatch(CharSequence charSequence) {
                        return Rule.d(charSequence, substring);
                    }
                };
            }
            if (endsWith) {
                return new RPattern() { // from class: org.apache.commons.codec.language.bm.Rule.7
                    @Override // org.apache.commons.codec.language.bm.Rule.RPattern
                    public boolean isMatch(CharSequence charSequence) {
                        return Rule.c(charSequence, substring);
                    }
                };
            }
        } else if (substring.length() == 0) {
            return new RPattern() { // from class: org.apache.commons.codec.language.bm.Rule.4
                @Override // org.apache.commons.codec.language.bm.Rule.RPattern
                public boolean isMatch(CharSequence charSequence) {
                    return charSequence.length() == 0;
                }
            };
        } else {
            return new RPattern() { // from class: org.apache.commons.codec.language.bm.Rule.5
                @Override // org.apache.commons.codec.language.bm.Rule.RPattern
                public boolean isMatch(CharSequence charSequence) {
                    return charSequence.equals(substring);
                }
            };
        }
        return new RPattern() { // from class: org.apache.commons.codec.language.bm.Rule.2
            Pattern a;

            {
                this.a = Pattern.compile(str);
            }

            @Override // org.apache.commons.codec.language.bm.Rule.RPattern
            public boolean isMatch(CharSequence charSequence) {
                return this.a.matcher(charSequence).find();
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean d(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence2.length() > charSequence.length()) {
            return false;
        }
        for (int i = 0; i < charSequence2.length(); i++) {
            if (charSequence.charAt(i) != charSequence2.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    private static String e(String str) {
        if (str.startsWith("\"")) {
            str = str.substring(1);
        }
        return str.endsWith("\"") ? str.substring(0, str.length() - 1) : str;
    }

    public Rule(String str, String str2, String str3, PhonemeExpr phonemeExpr) {
        this.c = str;
        this.b = d(str2 + "$");
        this.e = d("^" + str3);
        this.d = phonemeExpr;
    }

    public RPattern getLContext() {
        return this.b;
    }

    public String getPattern() {
        return this.c;
    }

    public PhonemeExpr getPhoneme() {
        return this.d;
    }

    public RPattern getRContext() {
        return this.e;
    }

    public boolean patternAndContextMatches(CharSequence charSequence, int i) {
        if (i >= 0) {
            int length = this.c.length() + i;
            if (length <= charSequence.length() && charSequence.subSequence(i, length).equals(this.c) && this.e.isMatch(charSequence.subSequence(length, charSequence.length()))) {
                return this.b.isMatch(charSequence.subSequence(0, i));
            }
            return false;
        }
        throw new IndexOutOfBoundsException("Can not match pattern at negative indexes");
    }
}
