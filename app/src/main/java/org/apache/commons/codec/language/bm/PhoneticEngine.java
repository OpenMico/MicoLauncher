package org.apache.commons.codec.language.bm;

import com.umeng.analytics.pro.c;
import com.xiaomi.micolauncher.common.track.VideoTrackHelper;
import com.xiaomi.micolauncher.skills.music.model.lrc.LrcRow;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.smarthome.setting.ServerSetting;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.apache.commons.codec.language.bm.Languages;
import org.apache.commons.codec.language.bm.Rule;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes5.dex */
public class PhoneticEngine {
    private static final Map<NameType, Set<String>> a = new EnumMap(NameType.class);
    private final Lang b;
    private final NameType c;
    private final RuleType d;
    private final boolean e;
    private final int f;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class a {
        private final Set<Rule.Phoneme> a;

        public static a a(Languages.LanguageSet languageSet) {
            return new a(new Rule.Phoneme("", languageSet));
        }

        private a(Rule.Phoneme phoneme) {
            this.a = new LinkedHashSet();
            this.a.add(phoneme);
        }

        private a(Set<Rule.Phoneme> set) {
            this.a = set;
        }

        public void a(CharSequence charSequence) {
            for (Rule.Phoneme phoneme : this.a) {
                phoneme.append(charSequence);
            }
        }

        public void a(Rule.PhonemeExpr phonemeExpr, int i) {
            LinkedHashSet linkedHashSet = new LinkedHashSet(i);
            loop0: for (Rule.Phoneme phoneme : this.a) {
                for (Rule.Phoneme phoneme2 : phonemeExpr.getPhonemes()) {
                    Languages.LanguageSet restrictTo = phoneme.getLanguages().restrictTo(phoneme2.getLanguages());
                    if (!restrictTo.isEmpty()) {
                        Rule.Phoneme phoneme3 = new Rule.Phoneme(phoneme, phoneme2, restrictTo);
                        if (linkedHashSet.size() < i) {
                            linkedHashSet.add(phoneme3);
                            if (linkedHashSet.size() >= i) {
                                break loop0;
                            }
                        } else {
                            continue;
                        }
                    }
                }
            }
            this.a.clear();
            this.a.addAll(linkedHashSet);
        }

        public Set<Rule.Phoneme> a() {
            return this.a;
        }

        public String b() {
            StringBuilder sb = new StringBuilder();
            for (Rule.Phoneme phoneme : this.a) {
                if (sb.length() > 0) {
                    sb.append("|");
                }
                sb.append(phoneme.getPhonemeText());
            }
            return sb.toString();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static final class b {
        private final Map<String, List<Rule>> a;
        private final CharSequence b;
        private a c;
        private int d;
        private final int e;
        private boolean f;

        public b(Map<String, List<Rule>> map, CharSequence charSequence, a aVar, int i, int i2) {
            if (map != null) {
                this.a = map;
                this.c = aVar;
                this.b = charSequence;
                this.d = i;
                this.e = i2;
                return;
            }
            throw new NullPointerException("The finalRules argument must not be null");
        }

        public int a() {
            return this.d;
        }

        public a b() {
            return this.c;
        }

        public b c() {
            this.f = false;
            Map<String, List<Rule>> map = this.a;
            CharSequence charSequence = this.b;
            int i = this.d;
            List<Rule> list = map.get(charSequence.subSequence(i, i + 1));
            int i2 = 1;
            if (list != null) {
                Iterator<Rule> it = list.iterator();
                i2 = 1;
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    Rule next = it.next();
                    int length = next.getPattern().length();
                    if (next.patternAndContextMatches(this.b, this.d)) {
                        this.c.a(next.getPhoneme(), this.e);
                        this.f = true;
                        i2 = length;
                        break;
                    }
                    i2 = length;
                }
            } else {
                i2 = 1;
            }
            if (this.f) {
            }
            this.d += i2;
            return this;
        }

        public boolean d() {
            return this.f;
        }
    }

    static {
        a.put(NameType.ASHKENAZI, Collections.unmodifiableSet(new HashSet(Arrays.asList("bar", "ben", "da", ServerSetting.SERVER_DE, "van", "von"))));
        a.put(NameType.SEPHARDIC, Collections.unmodifiableSet(new HashSet(Arrays.asList("al", "el", "da", "dal", ServerSetting.SERVER_DE, "del", "dela", "de la", "della", "des", "di", "do", "dos", c.W, "van", "von"))));
        a.put(NameType.GENERIC, Collections.unmodifiableSet(new HashSet(Arrays.asList("da", "dal", ServerSetting.SERVER_DE, "del", "dela", "de la", "della", "des", "di", "do", "dos", c.W, "van", "von"))));
    }

    private static String a(Iterable<String> iterable, String str) {
        StringBuilder sb = new StringBuilder();
        Iterator<String> it = iterable.iterator();
        if (it.hasNext()) {
            sb.append(it.next());
        }
        while (it.hasNext()) {
            sb.append(str);
            sb.append(it.next());
        }
        return sb.toString();
    }

    public PhoneticEngine(NameType nameType, RuleType ruleType, boolean z) {
        this(nameType, ruleType, z, 20);
    }

    public PhoneticEngine(NameType nameType, RuleType ruleType, boolean z, int i) {
        if (ruleType != RuleType.RULES) {
            this.c = nameType;
            this.d = ruleType;
            this.e = z;
            this.b = Lang.instance(nameType);
            this.f = i;
            return;
        }
        throw new IllegalArgumentException("ruleType must not be " + RuleType.RULES);
    }

    private a a(a aVar, Map<String, List<Rule>> map) {
        if (map == null) {
            throw new NullPointerException("finalRules can not be null");
        } else if (map.isEmpty()) {
            return aVar;
        } else {
            TreeMap treeMap = new TreeMap(Rule.Phoneme.COMPARATOR);
            for (Rule.Phoneme phoneme : aVar.a()) {
                a a2 = a.a(phoneme.getLanguages());
                String charSequence = phoneme.getPhonemeText().toString();
                int i = 0;
                while (i < charSequence.length()) {
                    b c = new b(map, charSequence, a2, i, this.f).c();
                    boolean d = c.d();
                    a2 = c.b();
                    if (!d) {
                        a2.a(charSequence.subSequence(i, i + 1));
                    }
                    i = c.a();
                }
                for (Rule.Phoneme phoneme2 : a2.a()) {
                    if (treeMap.containsKey(phoneme2)) {
                        Rule.Phoneme mergeWithLanguage = ((Rule.Phoneme) treeMap.remove(phoneme2)).mergeWithLanguage(phoneme2.getLanguages());
                        treeMap.put(mergeWithLanguage, mergeWithLanguage);
                    } else {
                        treeMap.put(phoneme2, phoneme2);
                    }
                }
            }
            return new a(treeMap.keySet());
        }
    }

    public String encode(String str) {
        return encode(str, this.b.guessLanguages(str));
    }

    public String encode(String str, Languages.LanguageSet languageSet) {
        String str2;
        Map<String, List<Rule>> instanceMap = Rule.getInstanceMap(this.c, RuleType.RULES, languageSet);
        Map<String, List<Rule>> instanceMap2 = Rule.getInstanceMap(this.c, this.d, VideoTrackHelper.PAGE_COMMON);
        Map<String, List<Rule>> instanceMap3 = Rule.getInstanceMap(this.c, this.d, languageSet);
        String trim = str.toLowerCase(Locale.ENGLISH).replace('-', ' ').trim();
        if (this.c == NameType.GENERIC) {
            if (trim.length() < 2 || !trim.substring(0, 2).equals("d'")) {
                for (String str3 : a.get(this.c)) {
                    if (trim.startsWith(str3 + StringUtils.SPACE)) {
                        String substring = trim.substring(str3.length() + 1);
                        String str4 = str3 + substring;
                        return "(" + encode(substring) + ")-(" + encode(str4) + ")";
                    }
                }
            } else {
                String substring2 = trim.substring(2);
                String str5 = "d" + substring2;
                return "(" + encode(substring2) + ")-(" + encode(str5) + ")";
            }
        }
        List<String> asList = Arrays.asList(trim.split("\\s+"));
        ArrayList<String> arrayList = new ArrayList();
        switch (this.c) {
            case SEPHARDIC:
                for (String str6 : asList) {
                    String[] split = str6.split(LrcRow.SINGLE_QUOTE);
                    arrayList.add(split[split.length - 1]);
                }
                arrayList.removeAll(a.get(this.c));
                break;
            case ASHKENAZI:
                arrayList.addAll(asList);
                arrayList.removeAll(a.get(this.c));
                break;
            case GENERIC:
                arrayList.addAll(asList);
                break;
            default:
                throw new IllegalStateException("Unreachable case: " + this.c);
        }
        if (this.e) {
            str2 = a(arrayList, StringUtils.SPACE);
        } else if (arrayList.size() == 1) {
            str2 = (String) asList.iterator().next();
        } else {
            StringBuilder sb = new StringBuilder();
            for (String str7 : arrayList) {
                sb.append(Constants.ACCEPT_TIME_SEPARATOR_SERVER);
                sb.append(encode(str7));
            }
            return sb.substring(1);
        }
        a a2 = a.a(languageSet);
        int i = 0;
        while (i < str2.length()) {
            b c = new b(instanceMap, str2, a2, i, this.f).c();
            i = c.a();
            a2 = c.b();
        }
        return a(a(a2, instanceMap2), instanceMap3).b();
    }

    public Lang getLang() {
        return this.b;
    }

    public NameType getNameType() {
        return this.c;
    }

    public RuleType getRuleType() {
        return this.d;
    }

    public boolean isConcat() {
        return this.e;
    }

    public int getMaxPhonemes() {
        return this.f;
    }
}
