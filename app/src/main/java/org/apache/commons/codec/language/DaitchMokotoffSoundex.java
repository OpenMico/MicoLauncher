package org.apache.commons.codec.language;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;

/* loaded from: classes5.dex */
public class DaitchMokotoffSoundex implements StringEncoder {
    private static final Map<Character, List<b>> a = new HashMap();
    private static final Map<Character, Character> b = new HashMap();
    private final boolean c;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static final class a {
        private final StringBuilder a;
        private String b;
        private String c;

        private a() {
            this.a = new StringBuilder();
            this.c = null;
            this.b = null;
        }

        public a a() {
            a aVar = new a();
            aVar.a.append(toString());
            aVar.c = this.c;
            return aVar;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof a)) {
                return false;
            }
            return toString().equals(((a) obj).toString());
        }

        public void b() {
            while (this.a.length() < 6) {
                this.a.append('0');
                this.b = null;
            }
        }

        public int hashCode() {
            return toString().hashCode();
        }

        public void a(String str, boolean z) {
            String str2 = this.c;
            if ((str2 == null || !str2.endsWith(str) || z) && this.a.length() < 6) {
                this.a.append(str);
                if (this.a.length() > 6) {
                    StringBuilder sb = this.a;
                    sb.delete(6, sb.length());
                }
                this.b = null;
            }
            this.c = str;
        }

        public String toString() {
            if (this.b == null) {
                this.b = this.a.toString();
            }
            return this.b;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static final class b {
        private final String a;
        private final String[] b;
        private final String[] c;
        private final String[] d;

        private boolean a(char c) {
            return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
        }

        protected b(String str, String str2, String str3, String str4) {
            this.a = str;
            this.b = str2.split("\\|");
            this.c = str3.split("\\|");
            this.d = str4.split("\\|");
        }

        public int a() {
            return this.a.length();
        }

        public String[] a(String str, boolean z) {
            if (z) {
                return this.b;
            }
            int a = a();
            if (a < str.length() ? a(str.charAt(a)) : false) {
                return this.c;
            }
            return this.d;
        }

        public boolean a(String str) {
            return str.startsWith(this.a);
        }

        public String toString() {
            return String.format("%s=(%s,%s,%s)", this.a, Arrays.asList(this.b), Arrays.asList(this.c), Arrays.asList(this.d));
        }
    }

    static {
        InputStream resourceAsStream = DaitchMokotoffSoundex.class.getClassLoader().getResourceAsStream("org/apache/commons/codec/language/dmrules.txt");
        if (resourceAsStream != null) {
            Scanner scanner = new Scanner(resourceAsStream, "UTF-8");
            a(scanner, "org/apache/commons/codec/language/dmrules.txt", a, b);
            scanner.close();
            for (Map.Entry<Character, List<b>> entry : a.entrySet()) {
                Collections.sort(entry.getValue(), new Comparator<b>() { // from class: org.apache.commons.codec.language.DaitchMokotoffSoundex.1
                    /* renamed from: a */
                    public int compare(b bVar, b bVar2) {
                        return bVar2.a() - bVar.a();
                    }
                });
            }
            return;
        }
        throw new IllegalArgumentException("Unable to load resource: org/apache/commons/codec/language/dmrules.txt");
    }

    private static void a(Scanner scanner, String str, Map<Character, List<b>> map, Map<Character, Character> map2) {
        int i = 0;
        boolean z = false;
        while (scanner.hasNextLine()) {
            i++;
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
                if (trim.length() == 0) {
                    continue;
                } else if (trim.contains("=")) {
                    String[] split = trim.split("=");
                    if (split.length == 2) {
                        String str2 = split[0];
                        String str3 = split[1];
                        if (str2.length() == 1 && str3.length() == 1) {
                            map2.put(Character.valueOf(str2.charAt(0)), Character.valueOf(str3.charAt(0)));
                        } else {
                            throw new IllegalArgumentException("Malformed folding statement - patterns are not single characters: " + nextLine + " in " + str);
                        }
                    } else {
                        throw new IllegalArgumentException("Malformed folding statement split into " + split.length + " parts: " + nextLine + " in " + str);
                    }
                } else {
                    String[] split2 = trim.split("\\s+");
                    if (split2.length == 4) {
                        try {
                            b bVar = new b(a(split2[0]), a(split2[1]), a(split2[2]), a(split2[3]));
                            char charAt = bVar.a.charAt(0);
                            List<b> list = map.get(Character.valueOf(charAt));
                            if (list == null) {
                                list = new ArrayList<>();
                                map.put(Character.valueOf(charAt), list);
                            }
                            list.add(bVar);
                        } catch (IllegalArgumentException e) {
                            throw new IllegalStateException("Problem parsing line '" + i + "' in " + str, e);
                        }
                    } else {
                        throw new IllegalArgumentException("Malformed rule statement split into " + split2.length + " parts: " + nextLine + " in " + str);
                    }
                }
            }
        }
    }

    private static String a(String str) {
        if (str.startsWith("\"")) {
            str = str.substring(1);
        }
        return str.endsWith("\"") ? str.substring(0, str.length() - 1) : str;
    }

    public DaitchMokotoffSoundex() {
        this(true);
    }

    public DaitchMokotoffSoundex(boolean z) {
        this.c = z;
    }

    private String b(String str) {
        StringBuilder sb = new StringBuilder();
        char[] charArray = str.toCharArray();
        for (char c : charArray) {
            if (!Character.isWhitespace(c)) {
                char lowerCase = Character.toLowerCase(c);
                if (this.c && b.containsKey(Character.valueOf(lowerCase))) {
                    lowerCase = b.get(Character.valueOf(lowerCase)).charValue();
                }
                sb.append(lowerCase);
            }
        }
        return sb.toString();
    }

    @Override // org.apache.commons.codec.Encoder
    public Object encode(Object obj) throws EncoderException {
        if (obj instanceof String) {
            return encode((String) obj);
        }
        throw new EncoderException("Parameter supplied to DaitchMokotoffSoundex encode is not of type java.lang.String");
    }

    @Override // org.apache.commons.codec.StringEncoder
    public String encode(String str) {
        if (str == null) {
            return null;
        }
        return a(str, false)[0];
    }

    public String soundex(String str) {
        String[] a2 = a(str, true);
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (String str2 : a2) {
            sb.append(str2);
            i++;
            if (i < a2.length) {
                sb.append('|');
            }
        }
        return sb.toString();
    }

    private String[] a(String str, boolean z) {
        int i;
        if (str == null) {
            return null;
        }
        String b2 = b(str);
        LinkedHashSet<a> linkedHashSet = new LinkedHashSet();
        linkedHashSet.add(new a());
        int i2 = 0;
        char c = 0;
        while (i2 < b2.length()) {
            char charAt = b2.charAt(i2);
            if (!Character.isWhitespace(charAt)) {
                String substring = b2.substring(i2);
                List<b> list = a.get(Character.valueOf(charAt));
                if (list != null) {
                    List arrayList = z ? new ArrayList() : Collections.EMPTY_LIST;
                    Iterator<b> it = list.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            b2 = b2;
                            i = 1;
                            break;
                        }
                        b next = it.next();
                        if (next.a(substring)) {
                            if (z) {
                                arrayList.clear();
                            }
                            String[] a2 = next.a(substring, c == 0);
                            boolean z2 = a2.length > 1 && z;
                            for (a aVar : linkedHashSet) {
                                int length = a2.length;
                                int i3 = 0;
                                while (true) {
                                    if (i3 >= length) {
                                        b2 = b2;
                                        break;
                                    }
                                    String str2 = a2[i3];
                                    a a3 = z2 ? aVar.a() : aVar;
                                    b2 = b2;
                                    a3.a(str2, (c == 'm' && charAt == 'n') || (c == 'n' && charAt == 'm'));
                                    if (z) {
                                        arrayList.add(a3);
                                        i3++;
                                        b2 = b2;
                                    }
                                }
                            }
                            b2 = b2;
                            if (z) {
                                linkedHashSet.clear();
                                linkedHashSet.addAll(arrayList);
                            }
                            i = 1;
                            i2 += next.a() - 1;
                        }
                    }
                    c = charAt;
                    i2 += i;
                }
            }
            b2 = b2;
            i = 1;
            i2 += i;
        }
        String[] strArr = new String[linkedHashSet.size()];
        int i4 = 0;
        for (a aVar2 : linkedHashSet) {
            aVar2.b();
            i4++;
            strArr[i4] = aVar2.toString();
        }
        return strArr;
    }
}
