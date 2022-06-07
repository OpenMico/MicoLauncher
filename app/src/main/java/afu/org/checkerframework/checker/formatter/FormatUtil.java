package afu.org.checkerframework.checker.formatter;

import afu.org.checkerframework.checker.formatter.qual.ConversionCategory;
import afu.org.checkerframework.checker.formatter.qual.ReturnsFormat;
import com.xiaomi.mipush.sdk.Constants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IllegalFormatConversionException;
import java.util.IllegalFormatException;
import java.util.MissingFormatArgumentException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.text.Typography;

/* loaded from: classes.dex */
public class FormatUtil {
    private static Pattern a = Pattern.compile("%(\\d+\\$)?([-#+ 0,(\\<]*)?(\\d+)?(\\.\\d+)?([tT])?([a-zA-Z%])");

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class a {
        private final int a;
        private final ConversionCategory b;

        public a(char c, int i) {
            this.a = i;
            this.b = ConversionCategory.fromConversionChar(c);
        }

        int a() {
            return this.a;
        }

        ConversionCategory b() {
            return this.b;
        }
    }

    @ReturnsFormat
    public static String asFormat(String str, ConversionCategory... conversionCategoryArr) throws IllegalFormatException {
        ConversionCategory[] formatParameterCategories = formatParameterCategories(str);
        if (formatParameterCategories.length == conversionCategoryArr.length) {
            for (int i = 0; i < conversionCategoryArr.length; i++) {
                if (conversionCategoryArr[i] != formatParameterCategories[i]) {
                    throw new IllegalFormatConversionCategoryException(conversionCategoryArr[i], formatParameterCategories[i]);
                }
            }
            return str;
        }
        throw new ExcessiveOrMissingFormatArgumentException(conversionCategoryArr.length, formatParameterCategories.length);
    }

    public static void tryFormatSatisfiability(String str) throws IllegalFormatException {
        String.format(str, null);
    }

    public static ConversionCategory[] formatParameterCategories(String str) throws IllegalFormatException {
        tryFormatSatisfiability(str);
        a[] a2 = a(str);
        HashMap hashMap = new HashMap();
        int i = -1;
        int i2 = -1;
        int i3 = -1;
        for (a aVar : a2) {
            int a3 = aVar.a();
            switch (a3) {
                case -1:
                    break;
                case 0:
                    i2++;
                    i3 = i2;
                    break;
                default:
                    i3 = a3 - 1;
                    break;
            }
            i = Math.max(i, i3);
            hashMap.put(Integer.valueOf(i3), ConversionCategory.intersect(hashMap.containsKey(Integer.valueOf(i3)) ? (ConversionCategory) hashMap.get(Integer.valueOf(i3)) : ConversionCategory.UNUSED, aVar.b()));
        }
        ConversionCategory[] conversionCategoryArr = new ConversionCategory[i + 1];
        for (int i4 = 0; i4 <= i; i4++) {
            conversionCategoryArr[i4] = hashMap.containsKey(Integer.valueOf(i4)) ? (ConversionCategory) hashMap.get(Integer.valueOf(i4)) : ConversionCategory.UNUSED;
        }
        return conversionCategoryArr;
    }

    private static int a(Matcher matcher) {
        String group = matcher.group(1);
        if (group != null) {
            return Integer.parseInt(group.substring(0, group.length() - 1));
        }
        return (matcher.group(2) == null || !matcher.group(2).contains(String.valueOf((char) Typography.less))) ? 0 : -1;
    }

    private static char b(Matcher matcher) {
        String group = matcher.group(5);
        if (group == null) {
            return matcher.group(6).charAt(0);
        }
        return group.charAt(0);
    }

    private static a[] a(String str) {
        ArrayList arrayList = new ArrayList();
        Matcher matcher = a.matcher(str);
        while (matcher.find()) {
            char b = b(matcher);
            if (!(b == '%' || b == 'n')) {
                arrayList.add(new a(b, a(matcher)));
            }
        }
        return (a[]) arrayList.toArray(new a[arrayList.size()]);
    }

    /* loaded from: classes.dex */
    public static class ExcessiveOrMissingFormatArgumentException extends MissingFormatArgumentException {
        private static final long serialVersionUID = 17000126;
        private final int expected;
        private final int found;

        public ExcessiveOrMissingFormatArgumentException(int i, int i2) {
            super(Constants.ACCEPT_TIME_SEPARATOR_SERVER);
            this.expected = i;
            this.found = i2;
        }

        public int getExpected() {
            return this.expected;
        }

        public int getFound() {
            return this.found;
        }

        @Override // java.util.MissingFormatArgumentException, java.lang.Throwable
        public String getMessage() {
            return String.format("Expected %d arguments but found %d.", Integer.valueOf(this.expected), Integer.valueOf(this.found));
        }
    }

    /* loaded from: classes.dex */
    public static class IllegalFormatConversionCategoryException extends IllegalFormatConversionException {
        private static final long serialVersionUID = 17000126;
        private final ConversionCategory expected;
        private final ConversionCategory found;

        public IllegalFormatConversionCategoryException(ConversionCategory conversionCategory, ConversionCategory conversionCategory2) {
            super(conversionCategory.chars.length() == 0 ? '-' : conversionCategory.chars.charAt(0), conversionCategory2.types == null ? Object.class : conversionCategory2.types[0]);
            this.expected = conversionCategory;
            this.found = conversionCategory2;
        }

        public ConversionCategory getExpected() {
            return this.expected;
        }

        public ConversionCategory getFound() {
            return this.found;
        }

        @Override // java.util.IllegalFormatConversionException, java.lang.Throwable
        public String getMessage() {
            return String.format("Expected category %s but found %s.", this.expected, this.found);
        }
    }
}
