package org.apache.commons.text.similarity;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

/* compiled from: RegexTokenizer.java */
/* loaded from: classes5.dex */
class b implements c<CharSequence> {
    /* renamed from: a */
    public CharSequence[] b(CharSequence charSequence) {
        Validate.isTrue(StringUtils.isNotBlank(charSequence), "Invalid text", new Object[0]);
        Matcher matcher = Pattern.compile("(\\w)+").matcher(charSequence.toString());
        ArrayList arrayList = new ArrayList();
        while (matcher.find()) {
            arrayList.add(matcher.group(0));
        }
        return (CharSequence[]) arrayList.toArray(new String[0]);
    }
}
