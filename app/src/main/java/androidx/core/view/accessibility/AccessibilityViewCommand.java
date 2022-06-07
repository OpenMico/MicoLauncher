package androidx.core.view.accessibility;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

/* loaded from: classes.dex */
public interface AccessibilityViewCommand {
    boolean perform(@NonNull View view, @Nullable CommandArguments commandArguments);

    /* loaded from: classes.dex */
    public static abstract class CommandArguments {
        Bundle a;

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public void setBundle(Bundle bundle) {
            this.a = bundle;
        }
    }

    /* loaded from: classes.dex */
    public static final class MoveAtGranularityArguments extends CommandArguments {
        public int getGranularity() {
            return this.a.getInt(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT);
        }

        public boolean getExtendSelection() {
            return this.a.getBoolean(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN);
        }
    }

    /* loaded from: classes.dex */
    public static final class MoveHtmlArguments extends CommandArguments {
        public String getHTMLElement() {
            return this.a.getString(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_HTML_ELEMENT_STRING);
        }
    }

    /* loaded from: classes.dex */
    public static final class SetSelectionArguments extends CommandArguments {
        public int getStart() {
            return this.a.getInt(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_SELECTION_START_INT);
        }

        public int getEnd() {
            return this.a.getInt(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_SELECTION_END_INT);
        }
    }

    /* loaded from: classes.dex */
    public static final class SetTextArguments extends CommandArguments {
        public CharSequence getText() {
            return this.a.getCharSequence(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE);
        }
    }

    /* loaded from: classes.dex */
    public static final class ScrollToPositionArguments extends CommandArguments {
        public int getRow() {
            return this.a.getInt(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_ROW_INT);
        }

        public int getColumn() {
            return this.a.getInt(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_COLUMN_INT);
        }
    }

    /* loaded from: classes.dex */
    public static final class SetProgressArguments extends CommandArguments {
        public float getProgress() {
            return this.a.getFloat(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_PROGRESS_VALUE);
        }
    }

    /* loaded from: classes.dex */
    public static final class MoveWindowArguments extends CommandArguments {
        public int getX() {
            return this.a.getInt(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_MOVE_WINDOW_X);
        }

        public int getY() {
            return this.a.getInt(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_MOVE_WINDOW_Y);
        }
    }
}
