package org.apache.commons.text;

import com.xiaomi.micolauncher.skills.music.model.lrc.LrcRow;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes5.dex */
public final class AlphabetConverter {
    private final Map<Integer, String> a;
    private final Map<String, String> b;
    private final int c;

    private AlphabetConverter(Map<Integer, String> map, Map<String, String> map2, int i) {
        this.a = map;
        this.b = map2;
        this.c = i;
    }

    public String encode(String str) throws UnsupportedEncodingException {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < str.length()) {
            int codePointAt = str.codePointAt(i);
            String str2 = this.a.get(Integer.valueOf(codePointAt));
            if (str2 != null) {
                sb.append(str2);
                i += Character.charCount(codePointAt);
            } else {
                throw new UnsupportedEncodingException("Couldn't find encoding for '" + a(codePointAt) + "' in " + str);
            }
        }
        return sb.toString();
    }

    public String decode(String str) throws UnsupportedEncodingException {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < str.length()) {
            Integer valueOf = Integer.valueOf(str.codePointAt(i));
            String a = a(valueOf.intValue());
            if (a.equals(this.a.get(valueOf))) {
                sb.append(a);
                i++;
            } else if (this.c + i <= str.length()) {
                String substring = str.substring(i, this.c + i);
                String str2 = this.b.get(substring);
                if (str2 != null) {
                    sb.append(str2);
                    i += this.c;
                } else {
                    throw new UnsupportedEncodingException("Unexpected string without decoding (" + substring + ") in " + str);
                }
            } else {
                throw new UnsupportedEncodingException("Unexpected end of string while decoding " + str);
            }
        }
        return sb.toString();
    }

    public int getEncodedCharLength() {
        return this.c;
    }

    public Map<Integer, String> getOriginalToEncoded() {
        return Collections.unmodifiableMap(this.a);
    }

    private void a(int i, String str, Collection<Integer> collection, Iterator<Integer> it, Map<Integer, String> map) {
        if (i > 0) {
            for (Integer num : collection) {
                int intValue = num.intValue();
                if (!it.hasNext()) {
                    return;
                }
                if (i != this.c || !map.containsKey(Integer.valueOf(intValue))) {
                    a(i - 1, str + a(intValue), collection, it, map);
                }
            }
            return;
        }
        Integer next = it.next();
        while (true) {
            Integer num2 = next;
            if (map.containsKey(num2)) {
                String a = a(num2.intValue());
                this.a.put(num2, a);
                this.b.put(a, a);
                if (it.hasNext()) {
                    next = it.next();
                } else {
                    return;
                }
            } else {
                String a2 = a(num2.intValue());
                this.a.put(num2, str);
                this.b.put(str, a2);
                return;
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Integer, String> entry : this.a.entrySet()) {
            sb.append(a(entry.getKey().intValue()));
            sb.append(" -> ");
            sb.append(entry.getValue());
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AlphabetConverter)) {
            return false;
        }
        AlphabetConverter alphabetConverter = (AlphabetConverter) obj;
        return this.a.equals(alphabetConverter.a) && this.b.equals(alphabetConverter.b) && this.c == alphabetConverter.c;
    }

    public int hashCode() {
        return Objects.hash(this.a, this.b, Integer.valueOf(this.c));
    }

    public static AlphabetConverter createConverterFromMap(Map<Integer, String> map) {
        Map unmodifiableMap = Collections.unmodifiableMap(map);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        int i = 1;
        for (Map.Entry entry : unmodifiableMap.entrySet()) {
            linkedHashMap.put(entry.getValue(), a(((Integer) entry.getKey()).intValue()));
            if (((String) entry.getValue()).length() > i) {
                i = ((String) entry.getValue()).length();
            }
        }
        return new AlphabetConverter(unmodifiableMap, linkedHashMap, i);
    }

    public static AlphabetConverter createConverterFromChars(Character[] chArr, Character[] chArr2, Character[] chArr3) {
        return createConverter(a(chArr), a(chArr2), a(chArr3));
    }

    private static Integer[] a(Character[] chArr) {
        if (chArr == null || chArr.length == 0) {
            return new Integer[0];
        }
        Integer[] numArr = new Integer[chArr.length];
        for (int i = 0; i < chArr.length; i++) {
            numArr[i] = Integer.valueOf(chArr[i].charValue());
        }
        return numArr;
    }

    public static AlphabetConverter createConverter(Integer[] numArr, Integer[] numArr2, Integer[] numArr3) {
        Integer num;
        LinkedHashSet<Integer> linkedHashSet = new LinkedHashSet(Arrays.asList(numArr));
        LinkedHashSet linkedHashSet2 = new LinkedHashSet(Arrays.asList(numArr2));
        LinkedHashSet<Integer> linkedHashSet3 = new LinkedHashSet(Arrays.asList(numArr3));
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        HashMap hashMap = new HashMap();
        for (Integer num2 : linkedHashSet3) {
            int intValue = num2.intValue();
            if (!linkedHashSet.contains(Integer.valueOf(intValue))) {
                throw new IllegalArgumentException("Can not use 'do not encode' list because original alphabet does not contain '" + a(intValue) + LrcRow.SINGLE_QUOTE);
            } else if (linkedHashSet2.contains(Integer.valueOf(intValue))) {
                hashMap.put(Integer.valueOf(intValue), a(intValue));
            } else {
                throw new IllegalArgumentException("Can not use 'do not encode' list because encoding alphabet does not contain '" + a(intValue) + LrcRow.SINGLE_QUOTE);
            }
        }
        if (linkedHashSet2.size() >= linkedHashSet.size()) {
            Iterator it = linkedHashSet2.iterator();
            for (Integer num3 : linkedHashSet) {
                int intValue2 = num3.intValue();
                String a = a(intValue2);
                if (hashMap.containsKey(Integer.valueOf(intValue2))) {
                    linkedHashMap.put(Integer.valueOf(intValue2), a);
                    linkedHashMap2.put(a, a);
                } else {
                    Object next = it.next();
                    while (true) {
                        num = (Integer) next;
                        if (!linkedHashSet3.contains(num)) {
                            break;
                        }
                        next = it.next();
                    }
                    String a2 = a(num.intValue());
                    linkedHashMap.put(Integer.valueOf(intValue2), a2);
                    linkedHashMap2.put(a2, a);
                }
            }
            return new AlphabetConverter(linkedHashMap, linkedHashMap2, 1);
        } else if (linkedHashSet2.size() - linkedHashSet3.size() >= 2) {
            int size = (linkedHashSet.size() - linkedHashSet3.size()) / (linkedHashSet2.size() - linkedHashSet3.size());
            int i = 1;
            while (size / linkedHashSet2.size() >= 1) {
                size /= linkedHashSet2.size();
                i++;
            }
            int i2 = i + 1;
            AlphabetConverter alphabetConverter = new AlphabetConverter(linkedHashMap, linkedHashMap2, i2);
            alphabetConverter.a(i2, "", linkedHashSet2, linkedHashSet.iterator(), hashMap);
            return alphabetConverter;
        } else {
            throw new IllegalArgumentException("Must have at least two encoding characters (excluding those in the 'do not encode' list), but has " + (linkedHashSet2.size() - linkedHashSet3.size()));
        }
    }

    private static String a(int i) {
        if (Character.charCount(i) == 1) {
            return String.valueOf((char) i);
        }
        return new String(Character.toChars(i));
    }
}
