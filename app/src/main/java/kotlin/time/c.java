package kotlin.time;

import com.umeng.analytics.pro.ai;
import com.xiaomi.smarthome.setting.ServerSetting;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.internal.Intrinsics;
import kotlin.time.DurationUnitKt;
import org.fourthline.cling.support.messagebox.parser.MessageElement;
import org.jetbrains.annotations.NotNull;
import org.seamless.xhtml.XHTMLElement;

/* compiled from: DurationUnit.kt */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0010\u0010\u0000\u001a\u00020\u0001*\u00060\u0002j\u0002`\u0003H\u0001¨\u0006\u0004"}, d2 = {"shortName", "", "Ljava/util/concurrent/TimeUnit;", "Lkotlin/time/DurationUnit;", "kotlin-stdlib"}, k = 5, mv = {1, 5, 1}, xi = 1, xs = "kotlin/time/DurationUnitKt")
/* loaded from: classes5.dex */
class c extends b {
    @SinceKotlin(version = "1.3")
    @ExperimentalTime
    @NotNull
    public static final String shortName(@NotNull TimeUnit shortName) {
        Intrinsics.checkNotNullParameter(shortName, "$this$shortName");
        switch (DurationUnitKt.WhenMappings.$EnumSwitchMapping$0[shortName.ordinal()]) {
            case 1:
                return "ns";
            case 2:
                return ServerSetting.SERVER_US;
            case 3:
                return "ms";
            case 4:
                return ai.az;
            case 5:
                return MessageElement.XPATH_PREFIX;
            case 6:
                return XHTMLElement.XPATH_PREFIX;
            case 7:
                return "d";
            default:
                throw new IllegalStateException(("Unknown unit: " + shortName).toString());
        }
    }
}
