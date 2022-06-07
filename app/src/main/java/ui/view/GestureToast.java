package ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.module.camera2.event.GestureInfoEvent;

/* loaded from: classes6.dex */
public class GestureToast {
    /* JADX WARN: Type inference failed for: r6v0, types: [com.elvishew.xlog.printer.file.FilePrinter$Builder, android.content.res.Resources] */
    /* JADX WARN: Type inference failed for: r6v1, types: [void, int] */
    /* JADX WARN: Type inference failed for: r7v1, types: [void, int] */
    /* JADX WARN: Type inference failed for: r8v0, types: [com.elvishew.xlog.printer.file.FilePrinter$Builder, android.content.res.Resources] */
    public static void showGesture(Context context, GestureInfoEvent.GestureType gestureType, CharSequence charSequence) {
        L.camera2.i("gesture toast show  -----------------------------------------%s", gestureType.toString());
        View inflate = LayoutInflater.from(context).inflate(R.layout.gesture_toast_layout, (ViewGroup) null);
        ((LinearLayout) inflate.findViewById(R.id.gesture_fl)).setLayoutParams(new FrameLayout.LayoutParams((int) context.getResources().fillEmptyFields(), (int) context.getResources().fillEmptyFields()));
        Toast toast = new Toast(context.getApplicationContext());
        toast.setGravity(17, 0, 0);
        toast.setDuration(0);
        toast.setView(inflate);
        ((TextView) inflate.findViewById(R.id.gesture_text)).setText(charSequence);
        ((ImageView) inflate.findViewById(R.id.gesture_img)).setImageResource(a(gestureType));
        toast.show();
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.CharSequence, android.text.SpannableString, com.elvishew.xlog.flattener.Flattener] */
    /* JADX WARN: Type inference failed for: r1v1, types: [void, java.lang.CharSequence] */
    /* JADX WARN: Type inference failed for: r5v0, types: [android.content.Context, com.elvishew.xlog.printer.file.FilePrinter$Builder$1] */
    /* JADX WARN: Unknown variable types count: 3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void showForwardGesture(android.content.Context r5) {
        /*
            android.text.SpannableString r0 = new android.text.SpannableString
            r1 = 2131821124(0x7f110244, float:1.9274982E38)
            void r1 = r5.<init>(r0)
            r0.<init>(r1)
            android.text.style.ImageSpan r1 = new android.text.style.ImageSpan
            r2 = 1
            r3 = 2131230915(0x7f0800c3, float:1.8077896E38)
            r1.<init>(r5, r3, r2)
            int r3 = r0.length()
            int r3 = r3 - r2
            int r2 = r0.length()
            r4 = 17
            r0.setSpan(r1, r3, r2, r4)
            com.xiaomi.micolauncher.module.camera2.event.GestureInfoEvent$GestureType r1 = com.xiaomi.micolauncher.module.camera2.event.GestureInfoEvent.GestureType.FastForward
            showGesture(r5, r1, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ui.view.GestureToast.showForwardGesture(android.content.Context):void");
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.CharSequence, android.text.SpannableString, com.elvishew.xlog.flattener.Flattener] */
    /* JADX WARN: Type inference failed for: r1v1, types: [void, java.lang.CharSequence] */
    /* JADX WARN: Type inference failed for: r5v0, types: [android.content.Context, com.elvishew.xlog.printer.file.FilePrinter$Builder$1] */
    /* JADX WARN: Unknown variable types count: 3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void showBackwardGesture(android.content.Context r5) {
        /*
            android.text.SpannableString r0 = new android.text.SpannableString
            r1 = 2131821123(0x7f110243, float:1.927498E38)
            void r1 = r5.<init>(r0)
            r0.<init>(r1)
            android.text.style.ImageSpan r1 = new android.text.style.ImageSpan
            r2 = 1
            r3 = 2131230914(0x7f0800c2, float:1.8077894E38)
            r1.<init>(r5, r3, r2)
            r3 = 0
            r4 = 17
            r0.setSpan(r1, r3, r2, r4)
            com.xiaomi.micolauncher.module.camera2.event.GestureInfoEvent$GestureType r1 = com.xiaomi.micolauncher.module.camera2.event.GestureInfoEvent.GestureType.FastBackward
            showGesture(r5, r1, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: ui.view.GestureToast.showBackwardGesture(android.content.Context):void");
    }

    private static int a(GestureInfoEvent.GestureType gestureType) {
        switch (gestureType) {
            case Stop:
                return R.drawable.gesture_off;
            case FastForward:
                return R.drawable.gesture_forward;
            case ThumbsUp:
                return R.drawable.gesture_collect;
            case FastBackward:
                return R.drawable.gesture_back;
            default:
                return R.drawable.gesture_ok;
        }
    }
}
