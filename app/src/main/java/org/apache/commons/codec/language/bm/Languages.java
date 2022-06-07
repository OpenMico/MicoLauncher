package org.apache.commons.codec.language.bm;

import java.io.InputStream;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

/* loaded from: classes5.dex */
public class Languages {
    public static final String ANY = "any";
    public static final LanguageSet ANY_LANGUAGE;
    public static final LanguageSet NO_LANGUAGES;
    private static final Map<NameType, Languages> a = new EnumMap(NameType.class);
    private final Set<String> b;

    /* loaded from: classes5.dex */
    public static abstract class LanguageSet {
        public abstract boolean contains(String str);

        public abstract String getAny();

        public abstract boolean isEmpty();

        public abstract boolean isSingleton();

        /* JADX INFO: Access modifiers changed from: package-private */
        public abstract LanguageSet merge(LanguageSet languageSet);

        public abstract LanguageSet restrictTo(LanguageSet languageSet);

        public static LanguageSet from(Set<String> set) {
            return set.isEmpty() ? Languages.NO_LANGUAGES : new SomeLanguages(set);
        }
    }

    /* loaded from: classes5.dex */
    public static final class SomeLanguages extends LanguageSet {
        private final Set<String> a;

        private SomeLanguages(Set<String> set) {
            this.a = Collections.unmodifiableSet(set);
        }

        @Override // org.apache.commons.codec.language.bm.Languages.LanguageSet
        public boolean contains(String str) {
            return this.a.contains(str);
        }

        @Override // org.apache.commons.codec.language.bm.Languages.LanguageSet
        public String getAny() {
            return this.a.iterator().next();
        }

        public Set<String> getLanguages() {
            return this.a;
        }

        @Override // org.apache.commons.codec.language.bm.Languages.LanguageSet
        public boolean isEmpty() {
            return this.a.isEmpty();
        }

        @Override // org.apache.commons.codec.language.bm.Languages.LanguageSet
        public boolean isSingleton() {
            return this.a.size() == 1;
        }

        @Override // org.apache.commons.codec.language.bm.Languages.LanguageSet
        public LanguageSet restrictTo(LanguageSet languageSet) {
            if (languageSet == Languages.NO_LANGUAGES) {
                return languageSet;
            }
            if (languageSet == Languages.ANY_LANGUAGE) {
                return this;
            }
            SomeLanguages someLanguages = (SomeLanguages) languageSet;
            HashSet hashSet = new HashSet(Math.min(this.a.size(), someLanguages.a.size()));
            for (String str : this.a) {
                if (someLanguages.a.contains(str)) {
                    hashSet.add(str);
                }
            }
            return from(hashSet);
        }

        @Override // org.apache.commons.codec.language.bm.Languages.LanguageSet
        public LanguageSet merge(LanguageSet languageSet) {
            if (languageSet == Languages.NO_LANGUAGES) {
                return this;
            }
            if (languageSet == Languages.ANY_LANGUAGE) {
                return languageSet;
            }
            HashSet hashSet = new HashSet(this.a);
            for (String str : ((SomeLanguages) languageSet).a) {
                hashSet.add(str);
            }
            return from(hashSet);
        }

        public String toString() {
            return "Languages(" + this.a.toString() + ")";
        }
    }

    static {
        NameType[] values = NameType.values();
        for (NameType nameType : values) {
            a.put(nameType, getInstance(a(nameType)));
        }
        NO_LANGUAGES = new LanguageSet() { // from class: org.apache.commons.codec.language.bm.Languages.1
            @Override // org.apache.commons.codec.language.bm.Languages.LanguageSet
            public boolean contains(String str) {
                return false;
            }

            @Override // org.apache.commons.codec.language.bm.Languages.LanguageSet
            public boolean isEmpty() {
                return true;
            }

            @Override // org.apache.commons.codec.language.bm.Languages.LanguageSet
            public boolean isSingleton() {
                return false;
            }

            @Override // org.apache.commons.codec.language.bm.Languages.LanguageSet
            public LanguageSet merge(LanguageSet languageSet) {
                return languageSet;
            }

            @Override // org.apache.commons.codec.language.bm.Languages.LanguageSet
            public LanguageSet restrictTo(LanguageSet languageSet) {
                return this;
            }

            public String toString() {
                return "NO_LANGUAGES";
            }

            @Override // org.apache.commons.codec.language.bm.Languages.LanguageSet
            public String getAny() {
                throw new NoSuchElementException("Can't fetch any language from the empty language set.");
            }
        };
        ANY_LANGUAGE = new LanguageSet() { // from class: org.apache.commons.codec.language.bm.Languages.2
            @Override // org.apache.commons.codec.language.bm.Languages.LanguageSet
            public boolean contains(String str) {
                return true;
            }

            @Override // org.apache.commons.codec.language.bm.Languages.LanguageSet
            public boolean isEmpty() {
                return false;
            }

            @Override // org.apache.commons.codec.language.bm.Languages.LanguageSet
            public boolean isSingleton() {
                return false;
            }

            @Override // org.apache.commons.codec.language.bm.Languages.LanguageSet
            public LanguageSet merge(LanguageSet languageSet) {
                return languageSet;
            }

            @Override // org.apache.commons.codec.language.bm.Languages.LanguageSet
            public LanguageSet restrictTo(LanguageSet languageSet) {
                return languageSet;
            }

            public String toString() {
                return "ANY_LANGUAGE";
            }

            @Override // org.apache.commons.codec.language.bm.Languages.LanguageSet
            public String getAny() {
                throw new NoSuchElementException("Can't fetch any language from the any language set.");
            }
        };
    }

    public static Languages getInstance(NameType nameType) {
        return a.get(nameType);
    }

    /* JADX WARN: Finally extract failed */
    public static Languages getInstance(String str) {
        HashSet hashSet = new HashSet();
        InputStream resourceAsStream = Languages.class.getClassLoader().getResourceAsStream(str);
        if (resourceAsStream != null) {
            Scanner scanner = new Scanner(resourceAsStream, "UTF-8");
            boolean z = false;
            while (scanner.hasNextLine()) {
                try {
                    String trim = scanner.nextLine().trim();
                    if (z) {
                        if (trim.endsWith("*/")) {
                            z = false;
                        }
                    } else if (trim.startsWith("/*")) {
                        z = true;
                    } else if (trim.length() > 0) {
                        hashSet.add(trim);
                    }
                } catch (Throwable th) {
                    scanner.close();
                    throw th;
                }
            }
            scanner.close();
            return new Languages(Collections.unmodifiableSet(hashSet));
        }
        throw new IllegalArgumentException("Unable to resolve required resource: " + str);
    }

    private static String a(NameType nameType) {
        return String.format("org/apache/commons/codec/language/bm/%s_languages.txt", nameType.getName());
    }

    private Languages(Set<String> set) {
        this.b = set;
    }

    public Set<String> getLanguages() {
        return this.b;
    }
}
