package org.apache.commons.codec.language.bm;

import com.xiaomi.micolauncher.skills.music.model.lrc.LrcRow;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;
import org.apache.commons.codec.language.bm.Languages;

/* loaded from: classes5.dex */
public class Lang {
    private static final Map<NameType, Lang> a = new EnumMap(NameType.class);
    private final Languages b;
    private final List<a> c;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static final class a {
        private final boolean a;
        private final Set<String> b;
        private final Pattern c;

        private a(Pattern pattern, Set<String> set, boolean z) {
            this.c = pattern;
            this.b = set;
            this.a = z;
        }

        public boolean a(String str) {
            return this.c.matcher(str).find();
        }
    }

    static {
        NameType[] values = NameType.values();
        for (NameType nameType : values) {
            a.put(nameType, loadFromResource(String.format("org/apache/commons/codec/language/bm/%s_lang.txt", nameType.getName()), Languages.getInstance(nameType)));
        }
    }

    public static Lang instance(NameType nameType) {
        return a.get(nameType);
    }

    /* JADX WARN: Finally extract failed */
    public static Lang loadFromResource(String str, Languages languages) {
        ArrayList arrayList = new ArrayList();
        InputStream resourceAsStream = Lang.class.getClassLoader().getResourceAsStream(str);
        if (resourceAsStream != null) {
            Scanner scanner = new Scanner(resourceAsStream, "UTF-8");
            boolean z = false;
            while (scanner.hasNextLine()) {
                try {
                    String nextLine = scanner.nextLine();
                    if (z) {
                        if (nextLine.endsWith("*/")) {
                            z = false;
                        }
                    } else if (nextLine.startsWith("/*")) {
                        z = true;
                    } else {
                        int indexOf = nextLine.indexOf("//");
                        String trim = (indexOf >= 0 ? nextLine.substring(0, indexOf) : nextLine).trim();
                        if (trim.length() != 0) {
                            String[] split = trim.split("\\s+");
                            if (split.length == 3) {
                                arrayList.add(new a(Pattern.compile(split[0]), new HashSet(Arrays.asList(split[1].split("\\+"))), split[2].equals("true")));
                            } else {
                                throw new IllegalArgumentException("Malformed line '" + nextLine + "' in language resource '" + str + LrcRow.SINGLE_QUOTE);
                            }
                        }
                    }
                } catch (Throwable th) {
                    scanner.close();
                    throw th;
                }
            }
            scanner.close();
            return new Lang(arrayList, languages);
        }
        throw new IllegalStateException("Unable to resolve required resource:org/apache/commons/codec/language/bm/%s_lang.txt");
    }

    private Lang(List<a> list, Languages languages) {
        this.c = Collections.unmodifiableList(list);
        this.b = languages;
    }

    public String guessLanguage(String str) {
        Languages.LanguageSet guessLanguages = guessLanguages(str);
        return guessLanguages.isSingleton() ? guessLanguages.getAny() : Languages.ANY;
    }

    public Languages.LanguageSet guessLanguages(String str) {
        String lowerCase = str.toLowerCase(Locale.ENGLISH);
        HashSet hashSet = new HashSet(this.b.getLanguages());
        for (a aVar : this.c) {
            if (aVar.a(lowerCase)) {
                if (aVar.a) {
                    hashSet.retainAll(aVar.b);
                } else {
                    hashSet.removeAll(aVar.b);
                }
            }
        }
        Languages.LanguageSet from = Languages.LanguageSet.from(hashSet);
        return from.equals(Languages.NO_LANGUAGES) ? Languages.ANY_LANGUAGE : from;
    }
}
