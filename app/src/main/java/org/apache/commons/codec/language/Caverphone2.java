package org.apache.commons.codec.language;

import androidx.exifinterface.media.ExifInterface;
import com.umeng.analytics.pro.ai;
import com.xiaomi.micolauncher.module.homepage.record.HomePageRecordHelper;
import com.xiaomi.onetrack.a.c;
import java.util.Locale;
import org.seamless.xhtml.XHTMLElement;

/* loaded from: classes5.dex */
public class Caverphone2 extends AbstractCaverphone {
    @Override // org.apache.commons.codec.StringEncoder
    public String encode(String str) {
        if (str == null || str.length() == 0) {
            return "1111111111";
        }
        String replaceAll = str.toLowerCase(Locale.ENGLISH).replaceAll("[^a-z]", "").replaceAll("e$", "").replaceAll("^cough", "cou2f").replaceAll("^rough", "rou2f").replaceAll("^tough", "tou2f").replaceAll("^enough", "enou2f").replaceAll("^trough", "trou2f").replaceAll("^gn", "2n").replaceAll("mb$", "m2").replaceAll("cq", "2q").replaceAll("ci", "si").replaceAll("ce", "se").replaceAll("cy", "sy").replaceAll("tch", "2ch").replaceAll(ai.aD, "k").replaceAll("q", "k").replaceAll("x", "k").replaceAll(ai.aC, "f").replaceAll("dg", "2g").replaceAll("tio", "sio").replaceAll("tia", "sia").replaceAll("d", ai.aF).replaceAll("ph", "fh").replaceAll("b", ai.av).replaceAll("sh", "s2").replaceAll(ai.aB, ai.az).replaceAll("^[aeiou]", "A").replaceAll("[aeiou]", "3").replaceAll("j", "y").replaceAll("^y3", "Y3").replaceAll("^y", "A").replaceAll("y", "3").replaceAll("3gh3", "3kh3").replaceAll("gh", "22").replaceAll("g", "k").replaceAll("s+", ExifInterface.LATITUDE_SOUTH).replaceAll("t+", ExifInterface.GPS_DIRECTION_TRUE).replaceAll("p+", "P").replaceAll("k+", "K").replaceAll("f+", HomePageRecordHelper.AREA_F).replaceAll("m+", "M").replaceAll("n+", "N").replaceAll("w3", "W3").replaceAll("wh3", "Wh3").replaceAll("w$", "3").replaceAll("w", "2").replaceAll("^h", "A").replaceAll(XHTMLElement.XPATH_PREFIX, "2").replaceAll("r3", "R3").replaceAll("r$", "3").replaceAll("r", "2").replaceAll("l3", "L3").replaceAll("l$", "3").replaceAll(c.a, "2").replaceAll("2", "").replaceAll("3$", "A").replaceAll("3", "");
        return (replaceAll + "1111111111").substring(0, 10);
    }
}
