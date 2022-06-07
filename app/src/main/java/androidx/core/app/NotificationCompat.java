package androidx.core.app;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Person;
import android.app.RemoteInput;
import android.content.Context;
import android.content.LocusId;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.util.SparseArray;
import android.widget.RemoteViews;
import androidx.annotation.ColorInt;
import androidx.annotation.DimenRes;
import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.core.R;
import androidx.core.app.Person;
import androidx.core.content.LocusIdCompat;
import androidx.core.content.pm.ShortcutInfoCompat;
import androidx.core.graphics.drawable.IconCompat;
import androidx.core.text.BidiFormatter;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import com.milink.base.contract.MiLinkKeys;
import com.umeng.analytics.pro.c;
import com.xiaomi.mi_soundbox_command_sdk.MiSoundBoxCommandExtras;
import com.xiaomi.micolauncher.common.skill.DomainConfig;
import com.xiaomi.onetrack.a.k;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class NotificationCompat {
    public static final int BADGE_ICON_LARGE = 2;
    public static final int BADGE_ICON_NONE = 0;
    public static final int BADGE_ICON_SMALL = 1;
    public static final String CATEGORY_ALARM = "alarm";
    public static final String CATEGORY_CALL = "call";
    public static final String CATEGORY_EMAIL = "email";
    public static final String CATEGORY_ERROR = "err";
    public static final String CATEGORY_EVENT = "event";
    public static final String CATEGORY_LOCATION_SHARING = "location_sharing";
    public static final String CATEGORY_MESSAGE = "msg";
    public static final String CATEGORY_MISSED_CALL = "missed_call";
    public static final String CATEGORY_NAVIGATION = "navigation";
    public static final String CATEGORY_PROGRESS = "progress";
    public static final String CATEGORY_PROMO = "promo";
    public static final String CATEGORY_RECOMMENDATION = "recommendation";
    public static final String CATEGORY_REMINDER = "reminder";
    public static final String CATEGORY_SERVICE = "service";
    public static final String CATEGORY_SOCIAL = "social";
    public static final String CATEGORY_STATUS = "status";
    public static final String CATEGORY_STOPWATCH = "stopwatch";
    public static final String CATEGORY_SYSTEM = "sys";
    public static final String CATEGORY_TRANSPORT = "transport";
    public static final String CATEGORY_WORKOUT = "workout";
    @ColorInt
    public static final int COLOR_DEFAULT = 0;
    public static final int DEFAULT_ALL = -1;
    public static final int DEFAULT_LIGHTS = 4;
    public static final int DEFAULT_SOUND = 1;
    public static final int DEFAULT_VIBRATE = 2;
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_AUDIO_CONTENTS_URI = "android.audioContents";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_BACKGROUND_IMAGE_URI = "android.backgroundImageUri";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_BIG_TEXT = "android.bigText";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_CHANNEL_GROUP_ID = "android.intent.extra.CHANNEL_GROUP_ID";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_CHANNEL_ID = "android.intent.extra.CHANNEL_ID";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_CHRONOMETER_COUNT_DOWN = "android.chronometerCountDown";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_COLORIZED = "android.colorized";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_COMPACT_ACTIONS = "android.compactActions";
    public static final String EXTRA_COMPAT_TEMPLATE = "androidx.core.app.extra.COMPAT_TEMPLATE";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_CONVERSATION_TITLE = "android.conversationTitle";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_HIDDEN_CONVERSATION_TITLE = "android.hiddenConversationTitle";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_HISTORIC_MESSAGES = "android.messages.historic";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_INFO_TEXT = "android.infoText";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_IS_GROUP_CONVERSATION = "android.isGroupConversation";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_LARGE_ICON = "android.largeIcon";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_LARGE_ICON_BIG = "android.largeIcon.big";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_MEDIA_SESSION = "android.mediaSession";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_MESSAGES = "android.messages";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_MESSAGING_STYLE_USER = "android.messagingStyleUser";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_NOTIFICATION_ID = "android.intent.extra.NOTIFICATION_ID";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_NOTIFICATION_TAG = "android.intent.extra.NOTIFICATION_TAG";
    @SuppressLint({"ActionValue"})
    @Deprecated
    public static final String EXTRA_PEOPLE = "android.people";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_PEOPLE_LIST = "android.people.list";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_PICTURE = "android.picture";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_PROGRESS = "android.progress";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_PROGRESS_INDETERMINATE = "android.progressIndeterminate";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_PROGRESS_MAX = "android.progressMax";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_REMOTE_INPUT_HISTORY = "android.remoteInputHistory";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_SELF_DISPLAY_NAME = "android.selfDisplayName";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_SHOW_CHRONOMETER = "android.showChronometer";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_SHOW_WHEN = "android.showWhen";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_SMALL_ICON = "android.icon";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_SUB_TEXT = "android.subText";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_SUMMARY_TEXT = "android.summaryText";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_TEMPLATE = "android.template";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_TEXT = "android.text";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_TEXT_LINES = "android.textLines";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_TITLE = "android.title";
    @SuppressLint({"ActionValue"})
    public static final String EXTRA_TITLE_BIG = "android.title.big";
    public static final int FLAG_AUTO_CANCEL = 16;
    public static final int FLAG_BUBBLE = 4096;
    public static final int FLAG_FOREGROUND_SERVICE = 64;
    public static final int FLAG_GROUP_SUMMARY = 512;
    @Deprecated
    public static final int FLAG_HIGH_PRIORITY = 128;
    public static final int FLAG_INSISTENT = 4;
    public static final int FLAG_LOCAL_ONLY = 256;
    public static final int FLAG_NO_CLEAR = 32;
    public static final int FLAG_ONGOING_EVENT = 2;
    public static final int FLAG_ONLY_ALERT_ONCE = 8;
    public static final int FLAG_SHOW_LIGHTS = 1;
    public static final int GROUP_ALERT_ALL = 0;
    public static final int GROUP_ALERT_CHILDREN = 2;
    public static final int GROUP_ALERT_SUMMARY = 1;
    public static final String GROUP_KEY_SILENT = "silent";
    @SuppressLint({"ActionValue"})
    public static final String INTENT_CATEGORY_NOTIFICATION_PREFERENCES = "android.intent.category.NOTIFICATION_PREFERENCES";
    public static final int PRIORITY_DEFAULT = 0;
    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_LOW = -1;
    public static final int PRIORITY_MAX = 2;
    public static final int PRIORITY_MIN = -2;
    public static final int STREAM_DEFAULT = -1;
    public static final int VISIBILITY_PRIVATE = 0;
    public static final int VISIBILITY_PUBLIC = 1;
    public static final int VISIBILITY_SECRET = -1;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    /* loaded from: classes.dex */
    public @interface BadgeIconType {
    }

    /* loaded from: classes.dex */
    public interface Extender {
        @NonNull
        Builder extend(@NonNull Builder builder);
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    /* loaded from: classes.dex */
    public @interface GroupAlertBehavior {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    /* loaded from: classes.dex */
    public @interface NotificationVisibility {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    /* loaded from: classes.dex */
    public @interface StreamType {
    }

    /* loaded from: classes.dex */
    public static class Builder {
        String A;
        Bundle B;
        int C;
        int D;
        Notification E;
        RemoteViews F;
        RemoteViews G;
        RemoteViews H;
        String I;
        int J;
        String K;
        LocusIdCompat L;
        long M;
        int N;
        boolean O;
        BubbleMetadata P;
        Notification Q;
        boolean R;
        Icon S;
        ArrayList<Action> a;
        CharSequence b;
        CharSequence c;
        PendingIntent d;
        PendingIntent e;
        RemoteViews f;
        Bitmap g;
        CharSequence h;
        int i;
        int j;
        boolean k;
        boolean l;
        boolean m;
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public ArrayList<Action> mActions;
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public Context mContext;
        @Deprecated
        public ArrayList<String> mPeople;
        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public ArrayList<Person> mPersonList;
        Style n;
        CharSequence o;
        CharSequence p;
        CharSequence[] q;
        int r;
        int s;
        boolean t;
        String u;
        boolean v;
        String w;
        boolean x;
        boolean y;
        boolean z;

        @RequiresApi(19)
        public Builder(@NonNull Context context, @NonNull Notification notification) {
            this(context, NotificationCompat.getChannelId(notification));
            ArrayList parcelableArrayList;
            Bundle bundle = notification.extras;
            Style extractStyleFromNotification = Style.extractStyleFromNotification(notification);
            setContentTitle(NotificationCompat.getContentTitle(notification)).setContentText(NotificationCompat.getContentText(notification)).setContentInfo(NotificationCompat.getContentInfo(notification)).setSubText(NotificationCompat.getSubText(notification)).setSettingsText(NotificationCompat.getSettingsText(notification)).setStyle(extractStyleFromNotification).setContentIntent(notification.contentIntent).setGroup(NotificationCompat.getGroup(notification)).setGroupSummary(NotificationCompat.isGroupSummary(notification)).setLocusId(NotificationCompat.getLocusId(notification)).setWhen(notification.when).setShowWhen(NotificationCompat.getShowWhen(notification)).setUsesChronometer(NotificationCompat.getUsesChronometer(notification)).setAutoCancel(NotificationCompat.getAutoCancel(notification)).setOnlyAlertOnce(NotificationCompat.getOnlyAlertOnce(notification)).setOngoing(NotificationCompat.getOngoing(notification)).setLocalOnly(NotificationCompat.getLocalOnly(notification)).setLargeIcon(notification.largeIcon).setBadgeIconType(NotificationCompat.getBadgeIconType(notification)).setCategory(NotificationCompat.getCategory(notification)).setBubbleMetadata(NotificationCompat.getBubbleMetadata(notification)).setNumber(notification.number).setTicker(notification.tickerText).setContentIntent(notification.contentIntent).setDeleteIntent(notification.deleteIntent).setFullScreenIntent(notification.fullScreenIntent, NotificationCompat.a(notification)).setSound(notification.sound, notification.audioStreamType).setVibrate(notification.vibrate).setLights(notification.ledARGB, notification.ledOnMS, notification.ledOffMS).setDefaults(notification.defaults).setPriority(notification.priority).setColor(NotificationCompat.getColor(notification)).setVisibility(NotificationCompat.getVisibility(notification)).setPublicVersion(NotificationCompat.getPublicVersion(notification)).setSortKey(NotificationCompat.getSortKey(notification)).setTimeoutAfter(NotificationCompat.getTimeoutAfter(notification)).setShortcutId(NotificationCompat.getShortcutId(notification)).setProgress(bundle.getInt(NotificationCompat.EXTRA_PROGRESS_MAX), bundle.getInt(NotificationCompat.EXTRA_PROGRESS), bundle.getBoolean(NotificationCompat.EXTRA_PROGRESS_INDETERMINATE)).setAllowSystemGeneratedContextualActions(NotificationCompat.getAllowSystemGeneratedContextualActions(notification)).setSmallIcon(notification.icon, notification.iconLevel).addExtras(a(notification, extractStyleFromNotification));
            if (Build.VERSION.SDK_INT >= 23) {
                this.S = notification.getSmallIcon();
            }
            if (!(notification.actions == null || notification.actions.length == 0)) {
                for (Notification.Action action : notification.actions) {
                    addAction(Action.Builder.fromAndroidAction(action).build());
                }
            }
            if (Build.VERSION.SDK_INT >= 21) {
                List<Action> invisibleActions = NotificationCompat.getInvisibleActions(notification);
                if (!invisibleActions.isEmpty()) {
                    for (Action action2 : invisibleActions) {
                        addInvisibleAction(action2);
                    }
                }
            }
            String[] stringArray = notification.extras.getStringArray(NotificationCompat.EXTRA_PEOPLE);
            if (!(stringArray == null || stringArray.length == 0)) {
                for (String str : stringArray) {
                    addPerson(str);
                }
            }
            if (Build.VERSION.SDK_INT >= 28 && (parcelableArrayList = notification.extras.getParcelableArrayList(NotificationCompat.EXTRA_PEOPLE_LIST)) != null && !parcelableArrayList.isEmpty()) {
                Iterator it = parcelableArrayList.iterator();
                while (it.hasNext()) {
                    addPerson(Person.fromAndroidPerson((Person) it.next()));
                }
            }
            if (Build.VERSION.SDK_INT >= 24 && bundle.containsKey(NotificationCompat.EXTRA_CHRONOMETER_COUNT_DOWN)) {
                setChronometerCountDown(bundle.getBoolean(NotificationCompat.EXTRA_CHRONOMETER_COUNT_DOWN));
            }
            if (Build.VERSION.SDK_INT >= 26 && bundle.containsKey(NotificationCompat.EXTRA_COLORIZED)) {
                setColorized(bundle.getBoolean(NotificationCompat.EXTRA_COLORIZED));
            }
        }

        @Nullable
        @RequiresApi(19)
        private static Bundle a(@NonNull Notification notification, @Nullable Style style) {
            if (notification.extras == null) {
                return null;
            }
            Bundle bundle = new Bundle(notification.extras);
            bundle.remove(NotificationCompat.EXTRA_TITLE);
            bundle.remove(NotificationCompat.EXTRA_TEXT);
            bundle.remove(NotificationCompat.EXTRA_INFO_TEXT);
            bundle.remove(NotificationCompat.EXTRA_SUB_TEXT);
            bundle.remove(NotificationCompat.EXTRA_CHANNEL_ID);
            bundle.remove(NotificationCompat.EXTRA_CHANNEL_GROUP_ID);
            bundle.remove(NotificationCompat.EXTRA_SHOW_WHEN);
            bundle.remove(NotificationCompat.EXTRA_PROGRESS);
            bundle.remove(NotificationCompat.EXTRA_PROGRESS_MAX);
            bundle.remove(NotificationCompat.EXTRA_PROGRESS_INDETERMINATE);
            bundle.remove(NotificationCompat.EXTRA_CHRONOMETER_COUNT_DOWN);
            bundle.remove(NotificationCompat.EXTRA_COLORIZED);
            bundle.remove(NotificationCompat.EXTRA_PEOPLE_LIST);
            bundle.remove(NotificationCompat.EXTRA_PEOPLE);
            bundle.remove(NotificationCompatExtras.EXTRA_SORT_KEY);
            bundle.remove(NotificationCompatExtras.EXTRA_GROUP_KEY);
            bundle.remove(NotificationCompatExtras.EXTRA_GROUP_SUMMARY);
            bundle.remove(NotificationCompatExtras.EXTRA_LOCAL_ONLY);
            bundle.remove(NotificationCompatExtras.EXTRA_ACTION_EXTRAS);
            Bundle bundle2 = bundle.getBundle("android.car.EXTENSIONS");
            if (bundle2 != null) {
                Bundle bundle3 = new Bundle(bundle2);
                bundle3.remove("invisible_actions");
                bundle.putBundle("android.car.EXTENSIONS", bundle3);
            }
            if (style != null) {
                style.clearCompatExtraKeys(bundle);
            }
            return bundle;
        }

        public Builder(@NonNull Context context, @NonNull String str) {
            this.mActions = new ArrayList<>();
            this.mPersonList = new ArrayList<>();
            this.a = new ArrayList<>();
            this.k = true;
            this.x = false;
            this.C = 0;
            this.D = 0;
            this.J = 0;
            this.N = 0;
            this.Q = new Notification();
            this.mContext = context;
            this.I = str;
            this.Q.when = System.currentTimeMillis();
            this.Q.audioStreamType = -1;
            this.j = 0;
            this.mPeople = new ArrayList<>();
            this.O = true;
        }

        @Deprecated
        public Builder(@NonNull Context context) {
            this(context, (String) null);
        }

        @NonNull
        public Builder setWhen(long j) {
            this.Q.when = j;
            return this;
        }

        @NonNull
        public Builder setShowWhen(boolean z) {
            this.k = z;
            return this;
        }

        @NonNull
        @RequiresApi(23)
        public Builder setSmallIcon(@NonNull IconCompat iconCompat) {
            this.S = iconCompat.toIcon(this.mContext);
            return this;
        }

        @NonNull
        public Builder setUsesChronometer(boolean z) {
            this.l = z;
            return this;
        }

        @NonNull
        @RequiresApi(24)
        public Builder setChronometerCountDown(boolean z) {
            this.m = z;
            getExtras().putBoolean(NotificationCompat.EXTRA_CHRONOMETER_COUNT_DOWN, z);
            return this;
        }

        @NonNull
        public Builder setSmallIcon(int i) {
            this.Q.icon = i;
            return this;
        }

        @NonNull
        public Builder setSmallIcon(int i, int i2) {
            Notification notification = this.Q;
            notification.icon = i;
            notification.iconLevel = i2;
            return this;
        }

        @NonNull
        @Deprecated
        public Builder setNotificationSilent() {
            this.R = true;
            return this;
        }

        @NonNull
        public Builder setSilent(boolean z) {
            this.R = z;
            return this;
        }

        @NonNull
        public Builder setContentTitle(@Nullable CharSequence charSequence) {
            this.b = limitCharSequenceLength(charSequence);
            return this;
        }

        @NonNull
        public Builder setContentText(@Nullable CharSequence charSequence) {
            this.c = limitCharSequenceLength(charSequence);
            return this;
        }

        @NonNull
        public Builder setSubText(@Nullable CharSequence charSequence) {
            this.o = limitCharSequenceLength(charSequence);
            return this;
        }

        @NonNull
        public Builder setSettingsText(@Nullable CharSequence charSequence) {
            this.p = limitCharSequenceLength(charSequence);
            return this;
        }

        @NonNull
        public Builder setRemoteInputHistory(@Nullable CharSequence[] charSequenceArr) {
            this.q = charSequenceArr;
            return this;
        }

        @NonNull
        public Builder setNumber(int i) {
            this.i = i;
            return this;
        }

        @NonNull
        public Builder setContentInfo(@Nullable CharSequence charSequence) {
            this.h = limitCharSequenceLength(charSequence);
            return this;
        }

        @NonNull
        public Builder setProgress(int i, int i2, boolean z) {
            this.r = i;
            this.s = i2;
            this.t = z;
            return this;
        }

        @NonNull
        public Builder setContent(@Nullable RemoteViews remoteViews) {
            this.Q.contentView = remoteViews;
            return this;
        }

        @NonNull
        public Builder setContentIntent(@Nullable PendingIntent pendingIntent) {
            this.d = pendingIntent;
            return this;
        }

        @NonNull
        public Builder setDeleteIntent(@Nullable PendingIntent pendingIntent) {
            this.Q.deleteIntent = pendingIntent;
            return this;
        }

        @NonNull
        public Builder setFullScreenIntent(@Nullable PendingIntent pendingIntent, boolean z) {
            this.e = pendingIntent;
            a(128, z);
            return this;
        }

        @NonNull
        public Builder setTicker(@Nullable CharSequence charSequence) {
            this.Q.tickerText = limitCharSequenceLength(charSequence);
            return this;
        }

        @NonNull
        @Deprecated
        public Builder setTicker(@Nullable CharSequence charSequence, @Nullable RemoteViews remoteViews) {
            this.Q.tickerText = limitCharSequenceLength(charSequence);
            this.f = remoteViews;
            return this;
        }

        @NonNull
        public Builder setLargeIcon(@Nullable Bitmap bitmap) {
            this.g = a(bitmap);
            return this;
        }

        @Nullable
        private Bitmap a(@Nullable Bitmap bitmap) {
            if (bitmap == null || Build.VERSION.SDK_INT >= 27) {
                return bitmap;
            }
            Resources resources = this.mContext.getResources();
            int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.compat_notification_large_icon_max_width);
            int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.compat_notification_large_icon_max_height);
            if (bitmap.getWidth() <= dimensionPixelSize && bitmap.getHeight() <= dimensionPixelSize2) {
                return bitmap;
            }
            double min = Math.min(dimensionPixelSize / Math.max(1, bitmap.getWidth()), dimensionPixelSize2 / Math.max(1, bitmap.getHeight()));
            return Bitmap.createScaledBitmap(bitmap, (int) Math.ceil(bitmap.getWidth() * min), (int) Math.ceil(bitmap.getHeight() * min), true);
        }

        @NonNull
        public Builder setSound(@Nullable Uri uri) {
            Notification notification = this.Q;
            notification.sound = uri;
            notification.audioStreamType = -1;
            if (Build.VERSION.SDK_INT >= 21) {
                this.Q.audioAttributes = new AudioAttributes.Builder().setContentType(4).setUsage(5).build();
            }
            return this;
        }

        @NonNull
        public Builder setSound(@Nullable Uri uri, int i) {
            Notification notification = this.Q;
            notification.sound = uri;
            notification.audioStreamType = i;
            if (Build.VERSION.SDK_INT >= 21) {
                this.Q.audioAttributes = new AudioAttributes.Builder().setContentType(4).setLegacyStreamType(i).build();
            }
            return this;
        }

        @NonNull
        public Builder setVibrate(@Nullable long[] jArr) {
            this.Q.vibrate = jArr;
            return this;
        }

        @NonNull
        public Builder setLights(@ColorInt int i, int i2, int i3) {
            Notification notification = this.Q;
            notification.ledARGB = i;
            notification.ledOnMS = i2;
            notification.ledOffMS = i3;
            int i4 = (notification.ledOnMS == 0 || this.Q.ledOffMS == 0) ? 0 : 1;
            Notification notification2 = this.Q;
            notification2.flags = i4 | (notification2.flags & (-2));
            return this;
        }

        @NonNull
        public Builder setOngoing(boolean z) {
            a(2, z);
            return this;
        }

        @NonNull
        public Builder setColorized(boolean z) {
            this.y = z;
            this.z = true;
            return this;
        }

        @NonNull
        public Builder setOnlyAlertOnce(boolean z) {
            a(8, z);
            return this;
        }

        @NonNull
        public Builder setAutoCancel(boolean z) {
            a(16, z);
            return this;
        }

        @NonNull
        public Builder setLocalOnly(boolean z) {
            this.x = z;
            return this;
        }

        @NonNull
        public Builder setCategory(@Nullable String str) {
            this.A = str;
            return this;
        }

        @NonNull
        public Builder setDefaults(int i) {
            Notification notification = this.Q;
            notification.defaults = i;
            if ((i & 4) != 0) {
                notification.flags |= 1;
            }
            return this;
        }

        private void a(int i, boolean z) {
            if (z) {
                Notification notification = this.Q;
                notification.flags = i | notification.flags;
                return;
            }
            Notification notification2 = this.Q;
            notification2.flags = (~i) & notification2.flags;
        }

        @NonNull
        public Builder setPriority(int i) {
            this.j = i;
            return this;
        }

        @NonNull
        @Deprecated
        public Builder addPerson(@Nullable String str) {
            if (str != null && !str.isEmpty()) {
                this.mPeople.add(str);
            }
            return this;
        }

        @NonNull
        public Builder addPerson(@Nullable Person person) {
            if (person != null) {
                this.mPersonList.add(person);
            }
            return this;
        }

        @NonNull
        public Builder clearPeople() {
            this.mPersonList.clear();
            this.mPeople.clear();
            return this;
        }

        @NonNull
        public Builder setGroup(@Nullable String str) {
            this.u = str;
            return this;
        }

        @NonNull
        public Builder setGroupSummary(boolean z) {
            this.v = z;
            return this;
        }

        @NonNull
        public Builder setSortKey(@Nullable String str) {
            this.w = str;
            return this;
        }

        @NonNull
        public Builder addExtras(@Nullable Bundle bundle) {
            if (bundle != null) {
                Bundle bundle2 = this.B;
                if (bundle2 == null) {
                    this.B = new Bundle(bundle);
                } else {
                    bundle2.putAll(bundle);
                }
            }
            return this;
        }

        @NonNull
        public Builder setExtras(@Nullable Bundle bundle) {
            this.B = bundle;
            return this;
        }

        @NonNull
        public Bundle getExtras() {
            if (this.B == null) {
                this.B = new Bundle();
            }
            return this.B;
        }

        @NonNull
        public Builder addAction(int i, @Nullable CharSequence charSequence, @Nullable PendingIntent pendingIntent) {
            this.mActions.add(new Action(i, charSequence, pendingIntent));
            return this;
        }

        @NonNull
        public Builder addAction(@Nullable Action action) {
            if (action != null) {
                this.mActions.add(action);
            }
            return this;
        }

        @NonNull
        public Builder clearActions() {
            this.mActions.clear();
            return this;
        }

        @NonNull
        @RequiresApi(21)
        public Builder addInvisibleAction(int i, @Nullable CharSequence charSequence, @Nullable PendingIntent pendingIntent) {
            this.a.add(new Action(i, charSequence, pendingIntent));
            return this;
        }

        @NonNull
        @RequiresApi(21)
        public Builder addInvisibleAction(@Nullable Action action) {
            if (action != null) {
                this.a.add(action);
            }
            return this;
        }

        @NonNull
        public Builder clearInvisibleActions() {
            this.a.clear();
            Bundle bundle = this.B.getBundle("android.car.EXTENSIONS");
            if (bundle != null) {
                Bundle bundle2 = new Bundle(bundle);
                bundle2.remove("invisible_actions");
                this.B.putBundle("android.car.EXTENSIONS", bundle2);
            }
            return this;
        }

        @NonNull
        public Builder setStyle(@Nullable Style style) {
            if (this.n != style) {
                this.n = style;
                Style style2 = this.n;
                if (style2 != null) {
                    style2.setBuilder(this);
                }
            }
            return this;
        }

        @NonNull
        public Builder setColor(@ColorInt int i) {
            this.C = i;
            return this;
        }

        @NonNull
        public Builder setVisibility(int i) {
            this.D = i;
            return this;
        }

        @NonNull
        public Builder setPublicVersion(@Nullable Notification notification) {
            this.E = notification;
            return this;
        }

        private boolean a() {
            Style style = this.n;
            return style == null || !style.displayCustomViewInline();
        }

        @Nullable
        @SuppressLint({"BuilderSetStyle"})
        public RemoteViews createContentView() {
            RemoteViews makeContentView;
            if (this.F != null && a()) {
                return this.F;
            }
            b bVar = new b(this);
            Style style = this.n;
            if (style != null && (makeContentView = style.makeContentView(bVar)) != null) {
                return makeContentView;
            }
            Notification b = bVar.b();
            if (Build.VERSION.SDK_INT >= 24) {
                return Notification.Builder.recoverBuilder(this.mContext, b).createContentView();
            }
            return b.contentView;
        }

        @Nullable
        @SuppressLint({"BuilderSetStyle"})
        public RemoteViews createBigContentView() {
            RemoteViews makeBigContentView;
            if (Build.VERSION.SDK_INT < 16) {
                return null;
            }
            if (this.G != null && a()) {
                return this.G;
            }
            b bVar = new b(this);
            Style style = this.n;
            if (style != null && (makeBigContentView = style.makeBigContentView(bVar)) != null) {
                return makeBigContentView;
            }
            Notification b = bVar.b();
            if (Build.VERSION.SDK_INT >= 24) {
                return Notification.Builder.recoverBuilder(this.mContext, b).createBigContentView();
            }
            return b.bigContentView;
        }

        @Nullable
        @SuppressLint({"BuilderSetStyle"})
        public RemoteViews createHeadsUpContentView() {
            RemoteViews makeHeadsUpContentView;
            if (Build.VERSION.SDK_INT < 21) {
                return null;
            }
            if (this.H != null && a()) {
                return this.H;
            }
            b bVar = new b(this);
            Style style = this.n;
            if (style != null && (makeHeadsUpContentView = style.makeHeadsUpContentView(bVar)) != null) {
                return makeHeadsUpContentView;
            }
            Notification b = bVar.b();
            if (Build.VERSION.SDK_INT >= 24) {
                return Notification.Builder.recoverBuilder(this.mContext, b).createHeadsUpContentView();
            }
            return b.headsUpContentView;
        }

        @NonNull
        public Builder setCustomContentView(@Nullable RemoteViews remoteViews) {
            this.F = remoteViews;
            return this;
        }

        @NonNull
        public Builder setCustomBigContentView(@Nullable RemoteViews remoteViews) {
            this.G = remoteViews;
            return this;
        }

        @NonNull
        public Builder setCustomHeadsUpContentView(@Nullable RemoteViews remoteViews) {
            this.H = remoteViews;
            return this;
        }

        @NonNull
        public Builder setChannelId(@NonNull String str) {
            this.I = str;
            return this;
        }

        @NonNull
        public Builder setTimeoutAfter(long j) {
            this.M = j;
            return this;
        }

        @NonNull
        public Builder setShortcutId(@Nullable String str) {
            this.K = str;
            return this;
        }

        @NonNull
        public Builder setShortcutInfo(@Nullable ShortcutInfoCompat shortcutInfoCompat) {
            if (shortcutInfoCompat == null) {
                return this;
            }
            this.K = shortcutInfoCompat.getId();
            if (this.L == null) {
                if (shortcutInfoCompat.getLocusId() != null) {
                    this.L = shortcutInfoCompat.getLocusId();
                } else if (shortcutInfoCompat.getId() != null) {
                    this.L = new LocusIdCompat(shortcutInfoCompat.getId());
                }
            }
            if (this.b == null) {
                setContentTitle(shortcutInfoCompat.getShortLabel());
            }
            return this;
        }

        @NonNull
        public Builder setLocusId(@Nullable LocusIdCompat locusIdCompat) {
            this.L = locusIdCompat;
            return this;
        }

        @NonNull
        public Builder setBadgeIconType(int i) {
            this.J = i;
            return this;
        }

        @NonNull
        public Builder setGroupAlertBehavior(int i) {
            this.N = i;
            return this;
        }

        @NonNull
        public Builder setBubbleMetadata(@Nullable BubbleMetadata bubbleMetadata) {
            this.P = bubbleMetadata;
            return this;
        }

        @NonNull
        public Builder extend(@NonNull Extender extender) {
            extender.extend(this);
            return this;
        }

        @NonNull
        public Builder setAllowSystemGeneratedContextualActions(boolean z) {
            this.O = z;
            return this;
        }

        @NonNull
        @Deprecated
        public Notification getNotification() {
            return build();
        }

        @NonNull
        public Notification build() {
            return new b(this).b();
        }

        @Nullable
        protected static CharSequence limitCharSequenceLength(@Nullable CharSequence charSequence) {
            return (charSequence != null && charSequence.length() > 5120) ? charSequence.subSequence(0, 5120) : charSequence;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public RemoteViews getContentView() {
            return this.F;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public RemoteViews getBigContentView() {
            return this.G;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public RemoteViews getHeadsUpContentView() {
            return this.H;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public long getWhenIfShowing() {
            if (this.k) {
                return this.Q.when;
            }
            return 0L;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public int getPriority() {
            return this.j;
        }

        @ColorInt
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public int getColor() {
            return this.C;
        }

        @Nullable
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public BubbleMetadata getBubbleMetadata() {
            return this.P;
        }
    }

    /* loaded from: classes.dex */
    public static abstract class Style {
        CharSequence a;
        CharSequence b;
        boolean c = false;
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        protected Builder mBuilder;

        private static float a(float f, float f2, float f3) {
            return f < f2 ? f2 : f > f3 ? f3 : f;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public void apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public boolean displayCustomViewInline() {
            return false;
        }

        @Nullable
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        protected String getClassName() {
            return null;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public RemoteViews makeBigContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            return null;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public RemoteViews makeContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            return null;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public RemoteViews makeHeadsUpContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            return null;
        }

        public void setBuilder(@Nullable Builder builder) {
            if (this.mBuilder != builder) {
                this.mBuilder = builder;
                Builder builder2 = this.mBuilder;
                if (builder2 != null) {
                    builder2.setStyle(this);
                }
            }
        }

        @Nullable
        public Notification build() {
            Builder builder = this.mBuilder;
            if (builder != null) {
                return builder.build();
            }
            return null;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public void addCompatExtras(@NonNull Bundle bundle) {
            if (this.c) {
                bundle.putCharSequence(NotificationCompat.EXTRA_SUMMARY_TEXT, this.b);
            }
            CharSequence charSequence = this.a;
            if (charSequence != null) {
                bundle.putCharSequence(NotificationCompat.EXTRA_TITLE_BIG, charSequence);
            }
            String className = getClassName();
            if (className != null) {
                bundle.putString(NotificationCompat.EXTRA_COMPAT_TEMPLATE, className);
            }
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        protected void restoreFromCompatExtras(@NonNull Bundle bundle) {
            if (bundle.containsKey(NotificationCompat.EXTRA_SUMMARY_TEXT)) {
                this.b = bundle.getCharSequence(NotificationCompat.EXTRA_SUMMARY_TEXT);
                this.c = true;
            }
            this.a = bundle.getCharSequence(NotificationCompat.EXTRA_TITLE_BIG);
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        protected void clearCompatExtraKeys(@NonNull Bundle bundle) {
            bundle.remove(NotificationCompat.EXTRA_SUMMARY_TEXT);
            bundle.remove(NotificationCompat.EXTRA_TITLE_BIG);
            bundle.remove(NotificationCompat.EXTRA_COMPAT_TEMPLATE);
        }

        @Nullable
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public static Style extractStyleFromNotification(@NonNull Notification notification) {
            Bundle extras = NotificationCompat.getExtras(notification);
            if (extras == null) {
                return null;
            }
            return b(extras);
        }

        @Nullable
        private static Style b(@Nullable String str) {
            if (str != null && Build.VERSION.SDK_INT >= 16) {
                if (str.equals(Notification.BigPictureStyle.class.getName())) {
                    return new BigPictureStyle();
                }
                if (str.equals(Notification.BigTextStyle.class.getName())) {
                    return new BigTextStyle();
                }
                if (str.equals(Notification.InboxStyle.class.getName())) {
                    return new InboxStyle();
                }
                if (Build.VERSION.SDK_INT >= 24) {
                    if (str.equals(Notification.MessagingStyle.class.getName())) {
                        return new MessagingStyle();
                    }
                    if (str.equals(Notification.DecoratedCustomViewStyle.class.getName())) {
                        return new DecoratedCustomViewStyle();
                    }
                }
            }
            return null;
        }

        @Nullable
        static Style a(@Nullable String str) {
            if (str == null) {
                return null;
            }
            char c = 65535;
            switch (str.hashCode()) {
                case -716705180:
                    if (str.equals("androidx.core.app.NotificationCompat$DecoratedCustomViewStyle")) {
                        c = 3;
                        break;
                    }
                    break;
                case -171946061:
                    if (str.equals("androidx.core.app.NotificationCompat$BigPictureStyle")) {
                        c = 1;
                        break;
                    }
                    break;
                case 912942987:
                    if (str.equals("androidx.core.app.NotificationCompat$InboxStyle")) {
                        c = 2;
                        break;
                    }
                    break;
                case 919595044:
                    if (str.equals("androidx.core.app.NotificationCompat$BigTextStyle")) {
                        c = 0;
                        break;
                    }
                    break;
                case 2090799565:
                    if (str.equals("androidx.core.app.NotificationCompat$MessagingStyle")) {
                        c = 4;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    return new BigTextStyle();
                case 1:
                    return new BigPictureStyle();
                case 2:
                    return new InboxStyle();
                case 3:
                    return new DecoratedCustomViewStyle();
                case 4:
                    return new MessagingStyle();
                default:
                    return null;
            }
        }

        @Nullable
        static Style a(@NonNull Bundle bundle) {
            Style a = a(bundle.getString(NotificationCompat.EXTRA_COMPAT_TEMPLATE));
            if (a != null) {
                return a;
            }
            if (bundle.containsKey(NotificationCompat.EXTRA_SELF_DISPLAY_NAME) || bundle.containsKey(NotificationCompat.EXTRA_MESSAGING_STYLE_USER)) {
                return new MessagingStyle();
            }
            if (bundle.containsKey(NotificationCompat.EXTRA_PICTURE)) {
                return new BigPictureStyle();
            }
            if (bundle.containsKey(NotificationCompat.EXTRA_BIG_TEXT)) {
                return new BigTextStyle();
            }
            if (bundle.containsKey(NotificationCompat.EXTRA_TEXT_LINES)) {
                return new InboxStyle();
            }
            return b(bundle.getString(NotificationCompat.EXTRA_TEMPLATE));
        }

        @Nullable
        static Style b(@NonNull Bundle bundle) {
            Style a = a(bundle);
            if (a == null) {
                return null;
            }
            try {
                a.restoreFromCompatExtras(bundle);
                return a;
            } catch (ClassCastException unused) {
                return null;
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:71:0x01c3  */
        /* JADX WARN: Removed duplicated region for block: B:75:0x01e5  */
        /* JADX WARN: Removed duplicated region for block: B:88:0x023f  */
        /* JADX WARN: Removed duplicated region for block: B:89:0x0241  */
        /* JADX WARN: Removed duplicated region for block: B:92:0x024a  */
        @androidx.annotation.NonNull
        @androidx.annotation.RestrictTo({androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public android.widget.RemoteViews applyStandardTemplate(boolean r13, int r14, boolean r15) {
            /*
                Method dump skipped, instructions count: 591
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.core.app.NotificationCompat.Style.applyStandardTemplate(boolean, int, boolean):android.widget.RemoteViews");
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public Bitmap createColoredBitmap(int i, int i2) {
            return a(i, i2, 0);
        }

        Bitmap a(@NonNull IconCompat iconCompat, int i) {
            return a(iconCompat, i, 0);
        }

        private Bitmap a(int i, int i2, int i3) {
            return a(IconCompat.createWithResource(this.mBuilder.mContext, i), i2, i3);
        }

        private Bitmap a(@NonNull IconCompat iconCompat, int i, int i2) {
            Drawable loadDrawable = iconCompat.loadDrawable(this.mBuilder.mContext);
            int intrinsicWidth = i2 == 0 ? loadDrawable.getIntrinsicWidth() : i2;
            if (i2 == 0) {
                i2 = loadDrawable.getIntrinsicHeight();
            }
            Bitmap createBitmap = Bitmap.createBitmap(intrinsicWidth, i2, Bitmap.Config.ARGB_8888);
            loadDrawable.setBounds(0, 0, intrinsicWidth, i2);
            if (i != 0) {
                loadDrawable.mutate().setColorFilter(new PorterDuffColorFilter(i, PorterDuff.Mode.SRC_IN));
            }
            loadDrawable.draw(new Canvas(createBitmap));
            return createBitmap;
        }

        private Bitmap a(int i, int i2, int i3, int i4) {
            int i5 = R.drawable.notification_icon_background;
            if (i4 == 0) {
                i4 = 0;
            }
            Bitmap a = a(i5, i4, i2);
            Canvas canvas = new Canvas(a);
            Drawable mutate = this.mBuilder.mContext.getResources().getDrawable(i).mutate();
            mutate.setFilterBitmap(true);
            int i6 = (i2 - i3) / 2;
            int i7 = i3 + i6;
            mutate.setBounds(i6, i6, i7, i7);
            mutate.setColorFilter(new PorterDuffColorFilter(-1, PorterDuff.Mode.SRC_ATOP));
            mutate.draw(canvas);
            return a;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public void buildIntoRemoteViews(RemoteViews remoteViews, RemoteViews remoteViews2) {
            a(remoteViews);
            remoteViews.removeAllViews(R.id.notification_main_column);
            remoteViews.addView(R.id.notification_main_column, remoteViews2.clone());
            remoteViews.setViewVisibility(R.id.notification_main_column, 0);
            if (Build.VERSION.SDK_INT >= 21) {
                remoteViews.setViewPadding(R.id.notification_main_column_container, 0, a(), 0, 0);
            }
        }

        private void a(RemoteViews remoteViews) {
            remoteViews.setViewVisibility(R.id.title, 8);
            remoteViews.setViewVisibility(R.id.text2, 8);
            remoteViews.setViewVisibility(R.id.text, 8);
        }

        private int a() {
            Resources resources = this.mBuilder.mContext.getResources();
            int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.notification_top_pad);
            int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.notification_top_pad_large_text);
            float a = (a(resources.getConfiguration().fontScale, 1.0f, 1.3f) - 1.0f) / 0.29999995f;
            return Math.round(((1.0f - a) * dimensionPixelSize) + (a * dimensionPixelSize2));
        }
    }

    /* loaded from: classes.dex */
    public static class BigPictureStyle extends Style {
        private Bitmap d;
        private IconCompat e;
        private boolean f;

        @Override // androidx.core.app.NotificationCompat.Style
        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        protected String getClassName() {
            return "androidx.core.app.NotificationCompat$BigPictureStyle";
        }

        public BigPictureStyle() {
        }

        public BigPictureStyle(@Nullable Builder builder) {
            setBuilder(builder);
        }

        @NonNull
        public BigPictureStyle setBigContentTitle(@Nullable CharSequence charSequence) {
            this.a = Builder.limitCharSequenceLength(charSequence);
            return this;
        }

        @NonNull
        public BigPictureStyle setSummaryText(@Nullable CharSequence charSequence) {
            this.b = Builder.limitCharSequenceLength(charSequence);
            this.c = true;
            return this;
        }

        @NonNull
        public BigPictureStyle bigPicture(@Nullable Bitmap bitmap) {
            this.d = bitmap;
            return this;
        }

        @NonNull
        public BigPictureStyle bigLargeIcon(@Nullable Bitmap bitmap) {
            this.e = bitmap == null ? null : IconCompat.createWithBitmap(bitmap);
            this.f = true;
            return this;
        }

        @Override // androidx.core.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public void apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (Build.VERSION.SDK_INT >= 16) {
                Notification.BigPictureStyle bigPicture = new Notification.BigPictureStyle(notificationBuilderWithBuilderAccessor.getBuilder()).setBigContentTitle(this.a).bigPicture(this.d);
                if (this.f) {
                    Context context = null;
                    if (this.e == null) {
                        a.a(bigPicture, (Bitmap) null);
                    } else if (Build.VERSION.SDK_INT >= 23) {
                        if (notificationBuilderWithBuilderAccessor instanceof b) {
                            context = ((b) notificationBuilderWithBuilderAccessor).a();
                        }
                        b.a(bigPicture, this.e.toIcon(context));
                    } else if (this.e.getType() == 1) {
                        a.a(bigPicture, this.e.getBitmap());
                    } else {
                        a.a(bigPicture, (Bitmap) null);
                    }
                }
                if (this.c) {
                    a.a(bigPicture, this.b);
                }
            }
        }

        @Override // androidx.core.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        protected void restoreFromCompatExtras(@NonNull Bundle bundle) {
            super.restoreFromCompatExtras(bundle);
            if (bundle.containsKey(NotificationCompat.EXTRA_LARGE_ICON_BIG)) {
                this.e = a(bundle.getParcelable(NotificationCompat.EXTRA_LARGE_ICON_BIG));
                this.f = true;
            }
            this.d = (Bitmap) bundle.getParcelable(NotificationCompat.EXTRA_PICTURE);
        }

        @Nullable
        private static IconCompat a(@Nullable Parcelable parcelable) {
            if (parcelable == null) {
                return null;
            }
            if (Build.VERSION.SDK_INT >= 23 && (parcelable instanceof Icon)) {
                return IconCompat.createFromIcon((Icon) parcelable);
            }
            if (parcelable instanceof Bitmap) {
                return IconCompat.createWithBitmap((Bitmap) parcelable);
            }
            return null;
        }

        @Override // androidx.core.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        protected void clearCompatExtraKeys(@NonNull Bundle bundle) {
            super.clearCompatExtraKeys(bundle);
            bundle.remove(NotificationCompat.EXTRA_LARGE_ICON_BIG);
            bundle.remove(NotificationCompat.EXTRA_PICTURE);
        }

        @RequiresApi(16)
        /* loaded from: classes.dex */
        private static class a {
            @RequiresApi(16)
            static void a(Notification.BigPictureStyle bigPictureStyle, Bitmap bitmap) {
                bigPictureStyle.bigLargeIcon(bitmap);
            }

            @RequiresApi(16)
            static void a(Notification.BigPictureStyle bigPictureStyle, CharSequence charSequence) {
                bigPictureStyle.setSummaryText(charSequence);
            }
        }

        @RequiresApi(23)
        /* loaded from: classes.dex */
        private static class b {
            @RequiresApi(23)
            static void a(Notification.BigPictureStyle bigPictureStyle, Icon icon) {
                bigPictureStyle.bigLargeIcon(icon);
            }
        }
    }

    /* loaded from: classes.dex */
    public static class BigTextStyle extends Style {
        private CharSequence d;

        @Override // androidx.core.app.NotificationCompat.Style
        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        protected String getClassName() {
            return "androidx.core.app.NotificationCompat$BigTextStyle";
        }

        public BigTextStyle() {
        }

        public BigTextStyle(@Nullable Builder builder) {
            setBuilder(builder);
        }

        @NonNull
        public BigTextStyle setBigContentTitle(@Nullable CharSequence charSequence) {
            this.a = Builder.limitCharSequenceLength(charSequence);
            return this;
        }

        @NonNull
        public BigTextStyle setSummaryText(@Nullable CharSequence charSequence) {
            this.b = Builder.limitCharSequenceLength(charSequence);
            this.c = true;
            return this;
        }

        @NonNull
        public BigTextStyle bigText(@Nullable CharSequence charSequence) {
            this.d = Builder.limitCharSequenceLength(charSequence);
            return this;
        }

        @Override // androidx.core.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public void apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (Build.VERSION.SDK_INT >= 16) {
                Notification.BigTextStyle bigText = new Notification.BigTextStyle(notificationBuilderWithBuilderAccessor.getBuilder()).setBigContentTitle(this.a).bigText(this.d);
                if (this.c) {
                    bigText.setSummaryText(this.b);
                }
            }
        }

        @Override // androidx.core.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        protected void restoreFromCompatExtras(@NonNull Bundle bundle) {
            super.restoreFromCompatExtras(bundle);
            this.d = bundle.getCharSequence(NotificationCompat.EXTRA_BIG_TEXT);
        }

        @Override // androidx.core.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public void addCompatExtras(@NonNull Bundle bundle) {
            super.addCompatExtras(bundle);
            if (Build.VERSION.SDK_INT < 21) {
                bundle.putCharSequence(NotificationCompat.EXTRA_BIG_TEXT, this.d);
            }
        }

        @Override // androidx.core.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        protected void clearCompatExtraKeys(@NonNull Bundle bundle) {
            super.clearCompatExtraKeys(bundle);
            bundle.remove(NotificationCompat.EXTRA_BIG_TEXT);
        }
    }

    /* loaded from: classes.dex */
    public static class MessagingStyle extends Style {
        public static final int MAXIMUM_RETAINED_MESSAGES = 25;
        private final List<Message> d = new ArrayList();
        private final List<Message> e = new ArrayList();
        private Person f;
        @Nullable
        private CharSequence g;
        @Nullable
        private Boolean h;

        @Override // androidx.core.app.NotificationCompat.Style
        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        protected String getClassName() {
            return "androidx.core.app.NotificationCompat$MessagingStyle";
        }

        MessagingStyle() {
        }

        @Deprecated
        public MessagingStyle(@NonNull CharSequence charSequence) {
            this.f = new Person.Builder().setName(charSequence).build();
        }

        public MessagingStyle(@NonNull Person person) {
            if (!TextUtils.isEmpty(person.getName())) {
                this.f = person;
                return;
            }
            throw new IllegalArgumentException("User's name must not be empty.");
        }

        @Nullable
        @Deprecated
        public CharSequence getUserDisplayName() {
            return this.f.getName();
        }

        @NonNull
        public Person getUser() {
            return this.f;
        }

        @NonNull
        public MessagingStyle setConversationTitle(@Nullable CharSequence charSequence) {
            this.g = charSequence;
            return this;
        }

        @Nullable
        public CharSequence getConversationTitle() {
            return this.g;
        }

        @NonNull
        @Deprecated
        public MessagingStyle addMessage(@Nullable CharSequence charSequence, long j, @Nullable CharSequence charSequence2) {
            this.d.add(new Message(charSequence, j, new Person.Builder().setName(charSequence2).build()));
            if (this.d.size() > 25) {
                this.d.remove(0);
            }
            return this;
        }

        @NonNull
        public MessagingStyle addMessage(@Nullable CharSequence charSequence, long j, @Nullable Person person) {
            addMessage(new Message(charSequence, j, person));
            return this;
        }

        @NonNull
        public MessagingStyle addMessage(@Nullable Message message) {
            if (message != null) {
                this.d.add(message);
                if (this.d.size() > 25) {
                    this.d.remove(0);
                }
            }
            return this;
        }

        @NonNull
        public MessagingStyle addHistoricMessage(@Nullable Message message) {
            if (message != null) {
                this.e.add(message);
                if (this.e.size() > 25) {
                    this.e.remove(0);
                }
            }
            return this;
        }

        @NonNull
        public List<Message> getMessages() {
            return this.d;
        }

        @NonNull
        public List<Message> getHistoricMessages() {
            return this.e;
        }

        @NonNull
        public MessagingStyle setGroupConversation(boolean z) {
            this.h = Boolean.valueOf(z);
            return this;
        }

        public boolean isGroupConversation() {
            if (this.mBuilder != null && this.mBuilder.mContext.getApplicationInfo().targetSdkVersion < 28 && this.h == null) {
                return this.g != null;
            }
            Boolean bool = this.h;
            if (bool != null) {
                return bool.booleanValue();
            }
            return false;
        }

        @Nullable
        public static MessagingStyle extractMessagingStyleFromNotification(@NonNull Notification notification) {
            Style extractStyleFromNotification = Style.extractStyleFromNotification(notification);
            if (extractStyleFromNotification instanceof MessagingStyle) {
                return (MessagingStyle) extractStyleFromNotification;
            }
            return null;
        }

        @Override // androidx.core.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public void apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            CharSequence charSequence;
            Notification.MessagingStyle messagingStyle;
            setGroupConversation(isGroupConversation());
            if (Build.VERSION.SDK_INT >= 24) {
                if (Build.VERSION.SDK_INT >= 28) {
                    messagingStyle = new Notification.MessagingStyle(this.f.toAndroidPerson());
                } else {
                    messagingStyle = new Notification.MessagingStyle(this.f.getName());
                }
                for (Message message : this.d) {
                    messagingStyle.addMessage(message.a());
                }
                if (Build.VERSION.SDK_INT >= 26) {
                    for (Message message2 : this.e) {
                        messagingStyle.addHistoricMessage(message2.a());
                    }
                }
                if (this.h.booleanValue() || Build.VERSION.SDK_INT >= 28) {
                    messagingStyle.setConversationTitle(this.g);
                }
                if (Build.VERSION.SDK_INT >= 28) {
                    messagingStyle.setGroupConversation(this.h.booleanValue());
                }
                messagingStyle.setBuilder(notificationBuilderWithBuilderAccessor.getBuilder());
                return;
            }
            Message a = a();
            if (this.g != null && this.h.booleanValue()) {
                notificationBuilderWithBuilderAccessor.getBuilder().setContentTitle(this.g);
            } else if (a != null) {
                notificationBuilderWithBuilderAccessor.getBuilder().setContentTitle("");
                if (a.getPerson() != null) {
                    notificationBuilderWithBuilderAccessor.getBuilder().setContentTitle(a.getPerson().getName());
                }
            }
            if (a != null) {
                Notification.Builder builder = notificationBuilderWithBuilderAccessor.getBuilder();
                if (this.g != null) {
                    charSequence = a(a);
                } else {
                    charSequence = a.getText();
                }
                builder.setContentText(charSequence);
            }
            if (Build.VERSION.SDK_INT >= 16) {
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
                boolean z = this.g != null || b();
                for (int size = this.d.size() - 1; size >= 0; size--) {
                    Message message3 = this.d.get(size);
                    CharSequence a2 = z ? a(message3) : message3.getText();
                    if (size != this.d.size() - 1) {
                        spannableStringBuilder.insert(0, (CharSequence) "\n");
                    }
                    spannableStringBuilder.insert(0, a2);
                }
                new Notification.BigTextStyle(notificationBuilderWithBuilderAccessor.getBuilder()).setBigContentTitle(null).bigText(spannableStringBuilder);
            }
        }

        @Nullable
        private Message a() {
            for (int size = this.d.size() - 1; size >= 0; size--) {
                Message message = this.d.get(size);
                if (!(message.getPerson() == null || TextUtils.isEmpty(message.getPerson().getName()))) {
                    return message;
                }
            }
            if (this.d.isEmpty()) {
                return null;
            }
            List<Message> list = this.d;
            return list.get(list.size() - 1);
        }

        private boolean b() {
            for (int size = this.d.size() - 1; size >= 0; size--) {
                Message message = this.d.get(size);
                if (message.getPerson() != null && message.getPerson().getName() == null) {
                    return true;
                }
            }
            return false;
        }

        private CharSequence a(@NonNull Message message) {
            BidiFormatter instance = BidiFormatter.getInstance();
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            boolean z = Build.VERSION.SDK_INT >= 21;
            int i = z ? ViewCompat.MEASURED_STATE_MASK : -1;
            String name = message.getPerson() == null ? "" : message.getPerson().getName();
            if (TextUtils.isEmpty(name)) {
                name = this.f.getName();
                if (z && this.mBuilder.getColor() != 0) {
                    i = this.mBuilder.getColor();
                }
            }
            CharSequence unicodeWrap = instance.unicodeWrap(name);
            spannableStringBuilder.append(unicodeWrap);
            spannableStringBuilder.setSpan(a(i), spannableStringBuilder.length() - unicodeWrap.length(), spannableStringBuilder.length(), 33);
            spannableStringBuilder.append((CharSequence) "  ").append(instance.unicodeWrap(message.getText() == null ? "" : message.getText()));
            return spannableStringBuilder;
        }

        @NonNull
        private TextAppearanceSpan a(int i) {
            return new TextAppearanceSpan(null, 0, 0, ColorStateList.valueOf(i), null);
        }

        @Override // androidx.core.app.NotificationCompat.Style
        public void addCompatExtras(@NonNull Bundle bundle) {
            super.addCompatExtras(bundle);
            bundle.putCharSequence(NotificationCompat.EXTRA_SELF_DISPLAY_NAME, this.f.getName());
            bundle.putBundle(NotificationCompat.EXTRA_MESSAGING_STYLE_USER, this.f.toBundle());
            bundle.putCharSequence(NotificationCompat.EXTRA_HIDDEN_CONVERSATION_TITLE, this.g);
            if (this.g != null && this.h.booleanValue()) {
                bundle.putCharSequence(NotificationCompat.EXTRA_CONVERSATION_TITLE, this.g);
            }
            if (!this.d.isEmpty()) {
                bundle.putParcelableArray(NotificationCompat.EXTRA_MESSAGES, Message.a(this.d));
            }
            if (!this.e.isEmpty()) {
                bundle.putParcelableArray(NotificationCompat.EXTRA_HISTORIC_MESSAGES, Message.a(this.e));
            }
            Boolean bool = this.h;
            if (bool != null) {
                bundle.putBoolean(NotificationCompat.EXTRA_IS_GROUP_CONVERSATION, bool.booleanValue());
            }
        }

        @Override // androidx.core.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        protected void restoreFromCompatExtras(@NonNull Bundle bundle) {
            super.restoreFromCompatExtras(bundle);
            this.d.clear();
            if (bundle.containsKey(NotificationCompat.EXTRA_MESSAGING_STYLE_USER)) {
                this.f = Person.fromBundle(bundle.getBundle(NotificationCompat.EXTRA_MESSAGING_STYLE_USER));
            } else {
                this.f = new Person.Builder().setName(bundle.getString(NotificationCompat.EXTRA_SELF_DISPLAY_NAME)).build();
            }
            this.g = bundle.getCharSequence(NotificationCompat.EXTRA_CONVERSATION_TITLE);
            if (this.g == null) {
                this.g = bundle.getCharSequence(NotificationCompat.EXTRA_HIDDEN_CONVERSATION_TITLE);
            }
            Parcelable[] parcelableArray = bundle.getParcelableArray(NotificationCompat.EXTRA_MESSAGES);
            if (parcelableArray != null) {
                this.d.addAll(Message.a(parcelableArray));
            }
            Parcelable[] parcelableArray2 = bundle.getParcelableArray(NotificationCompat.EXTRA_HISTORIC_MESSAGES);
            if (parcelableArray2 != null) {
                this.e.addAll(Message.a(parcelableArray2));
            }
            if (bundle.containsKey(NotificationCompat.EXTRA_IS_GROUP_CONVERSATION)) {
                this.h = Boolean.valueOf(bundle.getBoolean(NotificationCompat.EXTRA_IS_GROUP_CONVERSATION));
            }
        }

        @Override // androidx.core.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        protected void clearCompatExtraKeys(@NonNull Bundle bundle) {
            super.clearCompatExtraKeys(bundle);
            bundle.remove(NotificationCompat.EXTRA_MESSAGING_STYLE_USER);
            bundle.remove(NotificationCompat.EXTRA_SELF_DISPLAY_NAME);
            bundle.remove(NotificationCompat.EXTRA_CONVERSATION_TITLE);
            bundle.remove(NotificationCompat.EXTRA_HIDDEN_CONVERSATION_TITLE);
            bundle.remove(NotificationCompat.EXTRA_MESSAGES);
            bundle.remove(NotificationCompat.EXTRA_HISTORIC_MESSAGES);
            bundle.remove(NotificationCompat.EXTRA_IS_GROUP_CONVERSATION);
        }

        /* loaded from: classes.dex */
        public static final class Message {
            private final CharSequence a;
            private final long b;
            @Nullable
            private final Person c;
            private Bundle d;
            @Nullable
            private String e;
            @Nullable
            private Uri f;

            public Message(@Nullable CharSequence charSequence, long j, @Nullable Person person) {
                this.d = new Bundle();
                this.a = charSequence;
                this.b = j;
                this.c = person;
            }

            @Deprecated
            public Message(@Nullable CharSequence charSequence, long j, @Nullable CharSequence charSequence2) {
                this(charSequence, j, new Person.Builder().setName(charSequence2).build());
            }

            @NonNull
            public Message setData(@Nullable String str, @Nullable Uri uri) {
                this.e = str;
                this.f = uri;
                return this;
            }

            @Nullable
            public CharSequence getText() {
                return this.a;
            }

            public long getTimestamp() {
                return this.b;
            }

            @NonNull
            public Bundle getExtras() {
                return this.d;
            }

            @Nullable
            @Deprecated
            public CharSequence getSender() {
                Person person = this.c;
                if (person == null) {
                    return null;
                }
                return person.getName();
            }

            @Nullable
            public Person getPerson() {
                return this.c;
            }

            @Nullable
            public String getDataMimeType() {
                return this.e;
            }

            @Nullable
            public Uri getDataUri() {
                return this.f;
            }

            @NonNull
            private Bundle b() {
                Bundle bundle = new Bundle();
                CharSequence charSequence = this.a;
                if (charSequence != null) {
                    bundle.putCharSequence("text", charSequence);
                }
                bundle.putLong("time", this.b);
                Person person = this.c;
                if (person != null) {
                    bundle.putCharSequence(k.j, person.getName());
                    if (Build.VERSION.SDK_INT >= 28) {
                        bundle.putParcelable("sender_person", this.c.toAndroidPerson());
                    } else {
                        bundle.putBundle(DomainConfig.Person.DOMAIN_NAME, this.c.toBundle());
                    }
                }
                String str = this.e;
                if (str != null) {
                    bundle.putString("type", str);
                }
                Uri uri = this.f;
                if (uri != null) {
                    bundle.putParcelable(MiSoundBoxCommandExtras.URI, uri);
                }
                Bundle bundle2 = this.d;
                if (bundle2 != null) {
                    bundle.putBundle("extras", bundle2);
                }
                return bundle;
            }

            @NonNull
            static Bundle[] a(@NonNull List<Message> list) {
                Bundle[] bundleArr = new Bundle[list.size()];
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    bundleArr[i] = list.get(i).b();
                }
                return bundleArr;
            }

            @NonNull
            static List<Message> a(@NonNull Parcelable[] parcelableArr) {
                Message a;
                ArrayList arrayList = new ArrayList(parcelableArr.length);
                for (int i = 0; i < parcelableArr.length; i++) {
                    if ((parcelableArr[i] instanceof Bundle) && (a = a((Bundle) parcelableArr[i])) != null) {
                        arrayList.add(a);
                    }
                }
                return arrayList;
            }

            @Nullable
            static Message a(@NonNull Bundle bundle) {
                Person person;
                try {
                    if (bundle.containsKey("text") && bundle.containsKey("time")) {
                        if (bundle.containsKey(DomainConfig.Person.DOMAIN_NAME)) {
                            person = Person.fromBundle(bundle.getBundle(DomainConfig.Person.DOMAIN_NAME));
                        } else if (bundle.containsKey("sender_person") && Build.VERSION.SDK_INT >= 28) {
                            person = Person.fromAndroidPerson((android.app.Person) bundle.getParcelable("sender_person"));
                        } else if (bundle.containsKey(k.j)) {
                            person = new Person.Builder().setName(bundle.getCharSequence(k.j)).build();
                        } else {
                            person = null;
                        }
                        Message message = new Message(bundle.getCharSequence("text"), bundle.getLong("time"), person);
                        if (bundle.containsKey("type") && bundle.containsKey(MiSoundBoxCommandExtras.URI)) {
                            message.setData(bundle.getString("type"), (Uri) bundle.getParcelable(MiSoundBoxCommandExtras.URI));
                        }
                        if (bundle.containsKey("extras")) {
                            message.getExtras().putAll(bundle.getBundle("extras"));
                        }
                        return message;
                    }
                    return null;
                } catch (ClassCastException unused) {
                    return null;
                }
            }

            @NonNull
            @RequiresApi(24)
            @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
            Notification.MessagingStyle.Message a() {
                Notification.MessagingStyle.Message message;
                Person person = getPerson();
                CharSequence charSequence = null;
                android.app.Person person2 = null;
                if (Build.VERSION.SDK_INT >= 28) {
                    CharSequence text = getText();
                    long timestamp = getTimestamp();
                    if (person != null) {
                        person2 = person.toAndroidPerson();
                    }
                    message = new Notification.MessagingStyle.Message(text, timestamp, person2);
                } else {
                    CharSequence text2 = getText();
                    long timestamp2 = getTimestamp();
                    if (person != null) {
                        charSequence = person.getName();
                    }
                    message = new Notification.MessagingStyle.Message(text2, timestamp2, charSequence);
                }
                if (getDataMimeType() != null) {
                    message.setData(getDataMimeType(), getDataUri());
                }
                return message;
            }
        }
    }

    /* loaded from: classes.dex */
    public static class InboxStyle extends Style {
        private ArrayList<CharSequence> d = new ArrayList<>();

        @Override // androidx.core.app.NotificationCompat.Style
        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        protected String getClassName() {
            return "androidx.core.app.NotificationCompat$InboxStyle";
        }

        public InboxStyle() {
        }

        public InboxStyle(@Nullable Builder builder) {
            setBuilder(builder);
        }

        @NonNull
        public InboxStyle setBigContentTitle(@Nullable CharSequence charSequence) {
            this.a = Builder.limitCharSequenceLength(charSequence);
            return this;
        }

        @NonNull
        public InboxStyle setSummaryText(@Nullable CharSequence charSequence) {
            this.b = Builder.limitCharSequenceLength(charSequence);
            this.c = true;
            return this;
        }

        @NonNull
        public InboxStyle addLine(@Nullable CharSequence charSequence) {
            if (charSequence != null) {
                this.d.add(Builder.limitCharSequenceLength(charSequence));
            }
            return this;
        }

        @Override // androidx.core.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public void apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (Build.VERSION.SDK_INT >= 16) {
                Notification.InboxStyle bigContentTitle = new Notification.InboxStyle(notificationBuilderWithBuilderAccessor.getBuilder()).setBigContentTitle(this.a);
                if (this.c) {
                    bigContentTitle.setSummaryText(this.b);
                }
                Iterator<CharSequence> it = this.d.iterator();
                while (it.hasNext()) {
                    bigContentTitle.addLine(it.next());
                }
            }
        }

        @Override // androidx.core.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        protected void restoreFromCompatExtras(@NonNull Bundle bundle) {
            super.restoreFromCompatExtras(bundle);
            this.d.clear();
            if (bundle.containsKey(NotificationCompat.EXTRA_TEXT_LINES)) {
                Collections.addAll(this.d, bundle.getCharSequenceArray(NotificationCompat.EXTRA_TEXT_LINES));
            }
        }

        @Override // androidx.core.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        protected void clearCompatExtraKeys(@NonNull Bundle bundle) {
            super.clearCompatExtraKeys(bundle);
            bundle.remove(NotificationCompat.EXTRA_TEXT_LINES);
        }
    }

    /* loaded from: classes.dex */
    public static class DecoratedCustomViewStyle extends Style {
        @Override // androidx.core.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public boolean displayCustomViewInline() {
            return true;
        }

        @Override // androidx.core.app.NotificationCompat.Style
        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        protected String getClassName() {
            return "androidx.core.app.NotificationCompat$DecoratedCustomViewStyle";
        }

        @Override // androidx.core.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public void apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (Build.VERSION.SDK_INT >= 24) {
                notificationBuilderWithBuilderAccessor.getBuilder().setStyle(new Notification.DecoratedCustomViewStyle());
            }
        }

        @Override // androidx.core.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public RemoteViews makeContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (Build.VERSION.SDK_INT < 24 && this.mBuilder.getContentView() != null) {
                return a(this.mBuilder.getContentView(), false);
            }
            return null;
        }

        @Override // androidx.core.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public RemoteViews makeBigContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (Build.VERSION.SDK_INT >= 24) {
                return null;
            }
            RemoteViews bigContentView = this.mBuilder.getBigContentView();
            if (bigContentView == null) {
                bigContentView = this.mBuilder.getContentView();
            }
            if (bigContentView == null) {
                return null;
            }
            return a(bigContentView, true);
        }

        @Override // androidx.core.app.NotificationCompat.Style
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public RemoteViews makeHeadsUpContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (Build.VERSION.SDK_INT >= 24) {
                return null;
            }
            RemoteViews headsUpContentView = this.mBuilder.getHeadsUpContentView();
            RemoteViews contentView = headsUpContentView != null ? headsUpContentView : this.mBuilder.getContentView();
            if (headsUpContentView == null) {
                return null;
            }
            return a(contentView, true);
        }

        private RemoteViews a(RemoteViews remoteViews, boolean z) {
            int min;
            boolean z2 = true;
            int i = 0;
            RemoteViews applyStandardTemplate = applyStandardTemplate(true, R.layout.notification_template_custom_big, false);
            applyStandardTemplate.removeAllViews(R.id.actions);
            List<Action> a = a(this.mBuilder.mActions);
            if (!z || a == null || (min = Math.min(a.size(), 3)) <= 0) {
                z2 = false;
            } else {
                for (int i2 = 0; i2 < min; i2++) {
                    applyStandardTemplate.addView(R.id.actions, a(a.get(i2)));
                }
            }
            if (!z2) {
                i = 8;
            }
            applyStandardTemplate.setViewVisibility(R.id.actions, i);
            applyStandardTemplate.setViewVisibility(R.id.action_divider, i);
            buildIntoRemoteViews(applyStandardTemplate, remoteViews);
            return applyStandardTemplate;
        }

        private static List<Action> a(List<Action> list) {
            if (list == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            for (Action action : list) {
                if (!action.isContextual()) {
                    arrayList.add(action);
                }
            }
            return arrayList;
        }

        private RemoteViews a(Action action) {
            int i;
            boolean z = action.actionIntent == null;
            String packageName = this.mBuilder.mContext.getPackageName();
            if (z) {
                i = R.layout.notification_action_tombstone;
            } else {
                i = R.layout.notification_action;
            }
            RemoteViews remoteViews = new RemoteViews(packageName, i);
            IconCompat iconCompat = action.getIconCompat();
            if (iconCompat != null) {
                remoteViews.setImageViewBitmap(R.id.action_image, a(iconCompat, this.mBuilder.mContext.getResources().getColor(R.color.notification_action_color_filter)));
            }
            remoteViews.setTextViewText(R.id.action_text, action.title);
            if (!z) {
                remoteViews.setOnClickPendingIntent(R.id.action_container, action.actionIntent);
            }
            if (Build.VERSION.SDK_INT >= 15) {
                remoteViews.setContentDescription(R.id.action_container, action.title);
            }
            return remoteViews;
        }
    }

    /* loaded from: classes.dex */
    public static class Action {
        public static final int SEMANTIC_ACTION_ARCHIVE = 5;
        public static final int SEMANTIC_ACTION_CALL = 10;
        public static final int SEMANTIC_ACTION_DELETE = 4;
        public static final int SEMANTIC_ACTION_MARK_AS_READ = 2;
        public static final int SEMANTIC_ACTION_MARK_AS_UNREAD = 3;
        public static final int SEMANTIC_ACTION_MUTE = 6;
        public static final int SEMANTIC_ACTION_NONE = 0;
        public static final int SEMANTIC_ACTION_REPLY = 1;
        public static final int SEMANTIC_ACTION_THUMBS_DOWN = 9;
        public static final int SEMANTIC_ACTION_THUMBS_UP = 8;
        public static final int SEMANTIC_ACTION_UNMUTE = 7;
        final Bundle a;
        public PendingIntent actionIntent;
        boolean b;
        @Nullable
        private IconCompat c;
        private final RemoteInput[] d;
        private final RemoteInput[] e;
        private boolean f;
        private final int g;
        private final boolean h;
        @Deprecated
        public int icon;
        public CharSequence title;

        /* loaded from: classes.dex */
        public interface Extender {
            @NonNull
            Builder extend(@NonNull Builder builder);
        }

        @Retention(RetentionPolicy.SOURCE)
        /* loaded from: classes.dex */
        public @interface SemanticAction {
        }

        public Action(int i, @Nullable CharSequence charSequence, @Nullable PendingIntent pendingIntent) {
            this(i != 0 ? IconCompat.createWithResource(null, "", i) : null, charSequence, pendingIntent);
        }

        public Action(@Nullable IconCompat iconCompat, @Nullable CharSequence charSequence, @Nullable PendingIntent pendingIntent) {
            this(iconCompat, charSequence, pendingIntent, new Bundle(), (RemoteInput[]) null, (RemoteInput[]) null, true, 0, true, false);
        }

        public Action(int i, @Nullable CharSequence charSequence, @Nullable PendingIntent pendingIntent, @Nullable Bundle bundle, @Nullable RemoteInput[] remoteInputArr, @Nullable RemoteInput[] remoteInputArr2, boolean z, int i2, boolean z2, boolean z3) {
            this(i != 0 ? IconCompat.createWithResource(null, "", i) : null, charSequence, pendingIntent, bundle, remoteInputArr, remoteInputArr2, z, i2, z2, z3);
        }

        Action(@Nullable IconCompat iconCompat, @Nullable CharSequence charSequence, @Nullable PendingIntent pendingIntent, @Nullable Bundle bundle, @Nullable RemoteInput[] remoteInputArr, @Nullable RemoteInput[] remoteInputArr2, boolean z, int i, boolean z2, boolean z3) {
            this.b = true;
            this.c = iconCompat;
            if (iconCompat != null && iconCompat.getType() == 2) {
                this.icon = iconCompat.getResId();
            }
            this.title = Builder.limitCharSequenceLength(charSequence);
            this.actionIntent = pendingIntent;
            this.a = bundle == null ? new Bundle() : bundle;
            this.d = remoteInputArr;
            this.e = remoteInputArr2;
            this.f = z;
            this.g = i;
            this.b = z2;
            this.h = z3;
        }

        @Deprecated
        public int getIcon() {
            return this.icon;
        }

        @Nullable
        public IconCompat getIconCompat() {
            int i;
            if (this.c == null && (i = this.icon) != 0) {
                this.c = IconCompat.createWithResource(null, "", i);
            }
            return this.c;
        }

        @Nullable
        public CharSequence getTitle() {
            return this.title;
        }

        @Nullable
        public PendingIntent getActionIntent() {
            return this.actionIntent;
        }

        @NonNull
        public Bundle getExtras() {
            return this.a;
        }

        public boolean getAllowGeneratedReplies() {
            return this.f;
        }

        @Nullable
        public RemoteInput[] getRemoteInputs() {
            return this.d;
        }

        public int getSemanticAction() {
            return this.g;
        }

        public boolean isContextual() {
            return this.h;
        }

        @Nullable
        public RemoteInput[] getDataOnlyRemoteInputs() {
            return this.e;
        }

        public boolean getShowsUserInterface() {
            return this.b;
        }

        /* loaded from: classes.dex */
        public static final class Builder {
            private final IconCompat a;
            private final CharSequence b;
            private final PendingIntent c;
            private boolean d;
            private final Bundle e;
            private ArrayList<RemoteInput> f;
            private int g;
            private boolean h;
            private boolean i;

            @NonNull
            @RequiresApi(19)
            @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
            public static Builder fromAndroidAction(@NonNull Notification.Action action) {
                Builder builder;
                RemoteInput[] remoteInputs;
                if (Build.VERSION.SDK_INT < 23 || action.getIcon() == null) {
                    builder = new Builder(action.icon, action.title, action.actionIntent);
                } else {
                    builder = new Builder(IconCompat.createFromIcon(action.getIcon()), action.title, action.actionIntent);
                }
                if (!(Build.VERSION.SDK_INT < 20 || (remoteInputs = action.getRemoteInputs()) == null || remoteInputs.length == 0)) {
                    for (RemoteInput remoteInput : remoteInputs) {
                        builder.addRemoteInput(RemoteInput.a(remoteInput));
                    }
                }
                if (Build.VERSION.SDK_INT >= 24) {
                    builder.d = action.getAllowGeneratedReplies();
                }
                if (Build.VERSION.SDK_INT >= 28) {
                    builder.setSemanticAction(action.getSemanticAction());
                }
                if (Build.VERSION.SDK_INT >= 29) {
                    builder.setContextual(action.isContextual());
                }
                return builder;
            }

            public Builder(@Nullable IconCompat iconCompat, @Nullable CharSequence charSequence, @Nullable PendingIntent pendingIntent) {
                this(iconCompat, charSequence, pendingIntent, new Bundle(), null, true, 0, true, false);
            }

            public Builder(int i, @Nullable CharSequence charSequence, @Nullable PendingIntent pendingIntent) {
                this(i != 0 ? IconCompat.createWithResource(null, "", i) : null, charSequence, pendingIntent, new Bundle(), null, true, 0, true, false);
            }

            public Builder(@NonNull Action action) {
                this(action.getIconCompat(), action.title, action.actionIntent, new Bundle(action.a), action.getRemoteInputs(), action.getAllowGeneratedReplies(), action.getSemanticAction(), action.b, action.isContextual());
            }

            private Builder(@Nullable IconCompat iconCompat, @Nullable CharSequence charSequence, @Nullable PendingIntent pendingIntent, @NonNull Bundle bundle, @Nullable RemoteInput[] remoteInputArr, boolean z, int i, boolean z2, boolean z3) {
                this.d = true;
                this.h = true;
                this.a = iconCompat;
                this.b = Builder.limitCharSequenceLength(charSequence);
                this.c = pendingIntent;
                this.e = bundle;
                this.f = remoteInputArr == null ? null : new ArrayList<>(Arrays.asList(remoteInputArr));
                this.d = z;
                this.g = i;
                this.h = z2;
                this.i = z3;
            }

            @NonNull
            public Builder addExtras(@Nullable Bundle bundle) {
                if (bundle != null) {
                    this.e.putAll(bundle);
                }
                return this;
            }

            @NonNull
            public Bundle getExtras() {
                return this.e;
            }

            @NonNull
            public Builder addRemoteInput(@Nullable RemoteInput remoteInput) {
                if (this.f == null) {
                    this.f = new ArrayList<>();
                }
                if (remoteInput != null) {
                    this.f.add(remoteInput);
                }
                return this;
            }

            @NonNull
            public Builder setAllowGeneratedReplies(boolean z) {
                this.d = z;
                return this;
            }

            @NonNull
            public Builder setSemanticAction(int i) {
                this.g = i;
                return this;
            }

            @NonNull
            public Builder setContextual(boolean z) {
                this.i = z;
                return this;
            }

            @NonNull
            public Builder setShowsUserInterface(boolean z) {
                this.h = z;
                return this;
            }

            @NonNull
            public Builder extend(@NonNull Extender extender) {
                extender.extend(this);
                return this;
            }

            private void a() {
                if (this.i && this.c == null) {
                    throw new NullPointerException("Contextual Actions must contain a valid PendingIntent");
                }
            }

            @NonNull
            public Action build() {
                a();
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                ArrayList<RemoteInput> arrayList3 = this.f;
                if (arrayList3 != null) {
                    Iterator<RemoteInput> it = arrayList3.iterator();
                    while (it.hasNext()) {
                        RemoteInput next = it.next();
                        if (next.isDataOnly()) {
                            arrayList.add(next);
                        } else {
                            arrayList2.add(next);
                        }
                    }
                }
                RemoteInput[] remoteInputArr = null;
                RemoteInput[] remoteInputArr2 = arrayList.isEmpty() ? null : (RemoteInput[]) arrayList.toArray(new RemoteInput[arrayList.size()]);
                if (!arrayList2.isEmpty()) {
                    remoteInputArr = (RemoteInput[]) arrayList2.toArray(new RemoteInput[arrayList2.size()]);
                }
                return new Action(this.a, this.b, this.c, this.e, remoteInputArr, remoteInputArr2, this.d, this.g, this.h, this.i);
            }
        }

        /* loaded from: classes.dex */
        public static final class WearableExtender implements Extender {
            private int a;
            private CharSequence b;
            private CharSequence c;
            private CharSequence d;

            public WearableExtender() {
                this.a = 1;
            }

            public WearableExtender(@NonNull Action action) {
                this.a = 1;
                Bundle bundle = action.getExtras().getBundle("android.wearable.EXTENSIONS");
                if (bundle != null) {
                    this.a = bundle.getInt(MiLinkKeys.PARAM_FLAGS, 1);
                    this.b = bundle.getCharSequence("inProgressLabel");
                    this.c = bundle.getCharSequence("confirmLabel");
                    this.d = bundle.getCharSequence("cancelLabel");
                }
            }

            @Override // androidx.core.app.NotificationCompat.Action.Extender
            @NonNull
            public Builder extend(@NonNull Builder builder) {
                Bundle bundle = new Bundle();
                int i = this.a;
                if (i != 1) {
                    bundle.putInt(MiLinkKeys.PARAM_FLAGS, i);
                }
                CharSequence charSequence = this.b;
                if (charSequence != null) {
                    bundle.putCharSequence("inProgressLabel", charSequence);
                }
                CharSequence charSequence2 = this.c;
                if (charSequence2 != null) {
                    bundle.putCharSequence("confirmLabel", charSequence2);
                }
                CharSequence charSequence3 = this.d;
                if (charSequence3 != null) {
                    bundle.putCharSequence("cancelLabel", charSequence3);
                }
                builder.getExtras().putBundle("android.wearable.EXTENSIONS", bundle);
                return builder;
            }

            @NonNull
            public WearableExtender clone() {
                WearableExtender wearableExtender = new WearableExtender();
                wearableExtender.a = this.a;
                wearableExtender.b = this.b;
                wearableExtender.c = this.c;
                wearableExtender.d = this.d;
                return wearableExtender;
            }

            @NonNull
            public WearableExtender setAvailableOffline(boolean z) {
                a(1, z);
                return this;
            }

            public boolean isAvailableOffline() {
                return (this.a & 1) != 0;
            }

            private void a(int i, boolean z) {
                if (z) {
                    this.a = i | this.a;
                    return;
                }
                this.a = (~i) & this.a;
            }

            @NonNull
            @Deprecated
            public WearableExtender setInProgressLabel(@Nullable CharSequence charSequence) {
                this.b = charSequence;
                return this;
            }

            @Nullable
            @Deprecated
            public CharSequence getInProgressLabel() {
                return this.b;
            }

            @NonNull
            @Deprecated
            public WearableExtender setConfirmLabel(@Nullable CharSequence charSequence) {
                this.c = charSequence;
                return this;
            }

            @Nullable
            @Deprecated
            public CharSequence getConfirmLabel() {
                return this.c;
            }

            @NonNull
            @Deprecated
            public WearableExtender setCancelLabel(@Nullable CharSequence charSequence) {
                this.d = charSequence;
                return this;
            }

            @Nullable
            @Deprecated
            public CharSequence getCancelLabel() {
                return this.d;
            }

            @NonNull
            public WearableExtender setHintLaunchesActivity(boolean z) {
                a(2, z);
                return this;
            }

            public boolean getHintLaunchesActivity() {
                return (this.a & 2) != 0;
            }

            @NonNull
            public WearableExtender setHintDisplayActionInline(boolean z) {
                a(4, z);
                return this;
            }

            public boolean getHintDisplayActionInline() {
                return (this.a & 4) != 0;
            }
        }
    }

    /* loaded from: classes.dex */
    public static final class WearableExtender implements Extender {
        @Deprecated
        public static final int SCREEN_TIMEOUT_LONG = -1;
        @Deprecated
        public static final int SCREEN_TIMEOUT_SHORT = 0;
        @Deprecated
        public static final int SIZE_DEFAULT = 0;
        @Deprecated
        public static final int SIZE_FULL_SCREEN = 5;
        @Deprecated
        public static final int SIZE_LARGE = 4;
        @Deprecated
        public static final int SIZE_MEDIUM = 3;
        @Deprecated
        public static final int SIZE_SMALL = 2;
        @Deprecated
        public static final int SIZE_XSMALL = 1;
        public static final int UNSET_ACTION_INDEX = -1;
        private ArrayList<Action> a;
        private int b;
        private PendingIntent c;
        private ArrayList<Notification> d;
        private Bitmap e;
        private int f;
        private int g;
        private int h;
        private int i;
        private int j;
        private int k;
        private int l;
        private String m;
        private String n;

        public WearableExtender() {
            this.a = new ArrayList<>();
            this.b = 1;
            this.d = new ArrayList<>();
            this.g = GravityCompat.END;
            this.h = -1;
            this.i = 0;
            this.k = 80;
        }

        public WearableExtender(@NonNull Notification notification) {
            this.a = new ArrayList<>();
            this.b = 1;
            this.d = new ArrayList<>();
            this.g = GravityCompat.END;
            this.h = -1;
            this.i = 0;
            this.k = 80;
            Bundle extras = NotificationCompat.getExtras(notification);
            Bundle bundle = extras != null ? extras.getBundle("android.wearable.EXTENSIONS") : null;
            if (bundle != null) {
                ArrayList parcelableArrayList = bundle.getParcelableArrayList("actions");
                if (Build.VERSION.SDK_INT >= 16 && parcelableArrayList != null) {
                    Action[] actionArr = new Action[parcelableArrayList.size()];
                    for (int i = 0; i < actionArr.length; i++) {
                        if (Build.VERSION.SDK_INT >= 20) {
                            actionArr[i] = NotificationCompat.a((Notification.Action) parcelableArrayList.get(i));
                        } else if (Build.VERSION.SDK_INT >= 16) {
                            actionArr[i] = c.a((Bundle) parcelableArrayList.get(i));
                        }
                    }
                    Collections.addAll(this.a, actionArr);
                }
                this.b = bundle.getInt(MiLinkKeys.PARAM_FLAGS, 1);
                this.c = (PendingIntent) bundle.getParcelable("displayIntent");
                Notification[] a = NotificationCompat.a(bundle, c.t);
                if (a != null) {
                    Collections.addAll(this.d, a);
                }
                this.e = (Bitmap) bundle.getParcelable("background");
                this.f = bundle.getInt("contentIcon");
                this.g = bundle.getInt("contentIconGravity", GravityCompat.END);
                this.h = bundle.getInt("contentActionIndex", -1);
                this.i = bundle.getInt("customSizePreset", 0);
                this.j = bundle.getInt("customContentHeight");
                this.k = bundle.getInt("gravity", 80);
                this.l = bundle.getInt("hintScreenTimeout");
                this.m = bundle.getString("dismissalId");
                this.n = bundle.getString("bridgeTag");
            }
        }

        @Override // androidx.core.app.NotificationCompat.Extender
        @NonNull
        public Builder extend(@NonNull Builder builder) {
            Bundle bundle = new Bundle();
            if (!this.a.isEmpty()) {
                if (Build.VERSION.SDK_INT >= 16) {
                    ArrayList<? extends Parcelable> arrayList = new ArrayList<>(this.a.size());
                    Iterator<Action> it = this.a.iterator();
                    while (it.hasNext()) {
                        Action next = it.next();
                        if (Build.VERSION.SDK_INT >= 20) {
                            arrayList.add(a(next));
                        } else if (Build.VERSION.SDK_INT >= 16) {
                            arrayList.add(c.a(next));
                        }
                    }
                    bundle.putParcelableArrayList("actions", arrayList);
                } else {
                    bundle.putParcelableArrayList("actions", null);
                }
            }
            int i = this.b;
            if (i != 1) {
                bundle.putInt(MiLinkKeys.PARAM_FLAGS, i);
            }
            PendingIntent pendingIntent = this.c;
            if (pendingIntent != null) {
                bundle.putParcelable("displayIntent", pendingIntent);
            }
            if (!this.d.isEmpty()) {
                ArrayList<Notification> arrayList2 = this.d;
                bundle.putParcelableArray(c.t, (Parcelable[]) arrayList2.toArray(new Notification[arrayList2.size()]));
            }
            Bitmap bitmap = this.e;
            if (bitmap != null) {
                bundle.putParcelable("background", bitmap);
            }
            int i2 = this.f;
            if (i2 != 0) {
                bundle.putInt("contentIcon", i2);
            }
            int i3 = this.g;
            if (i3 != 8388613) {
                bundle.putInt("contentIconGravity", i3);
            }
            int i4 = this.h;
            if (i4 != -1) {
                bundle.putInt("contentActionIndex", i4);
            }
            int i5 = this.i;
            if (i5 != 0) {
                bundle.putInt("customSizePreset", i5);
            }
            int i6 = this.j;
            if (i6 != 0) {
                bundle.putInt("customContentHeight", i6);
            }
            int i7 = this.k;
            if (i7 != 80) {
                bundle.putInt("gravity", i7);
            }
            int i8 = this.l;
            if (i8 != 0) {
                bundle.putInt("hintScreenTimeout", i8);
            }
            String str = this.m;
            if (str != null) {
                bundle.putString("dismissalId", str);
            }
            String str2 = this.n;
            if (str2 != null) {
                bundle.putString("bridgeTag", str2);
            }
            builder.getExtras().putBundle("android.wearable.EXTENSIONS", bundle);
            return builder;
        }

        @RequiresApi(20)
        private static Notification.Action a(Action action) {
            Notification.Action.Builder builder;
            Bundle bundle;
            if (Build.VERSION.SDK_INT >= 23) {
                IconCompat iconCompat = action.getIconCompat();
                builder = new Notification.Action.Builder(iconCompat == null ? null : iconCompat.toIcon(), action.getTitle(), action.getActionIntent());
            } else {
                IconCompat iconCompat2 = action.getIconCompat();
                builder = new Notification.Action.Builder((iconCompat2 == null || iconCompat2.getType() != 2) ? 0 : iconCompat2.getResId(), action.getTitle(), action.getActionIntent());
            }
            if (action.getExtras() != null) {
                bundle = new Bundle(action.getExtras());
            } else {
                bundle = new Bundle();
            }
            bundle.putBoolean("android.support.allowGeneratedReplies", action.getAllowGeneratedReplies());
            if (Build.VERSION.SDK_INT >= 24) {
                builder.setAllowGeneratedReplies(action.getAllowGeneratedReplies());
            }
            builder.addExtras(bundle);
            RemoteInput[] remoteInputs = action.getRemoteInputs();
            if (remoteInputs != null) {
                for (RemoteInput remoteInput : RemoteInput.a(remoteInputs)) {
                    builder.addRemoteInput(remoteInput);
                }
            }
            return builder.build();
        }

        @NonNull
        public WearableExtender clone() {
            WearableExtender wearableExtender = new WearableExtender();
            wearableExtender.a = new ArrayList<>(this.a);
            wearableExtender.b = this.b;
            wearableExtender.c = this.c;
            wearableExtender.d = new ArrayList<>(this.d);
            wearableExtender.e = this.e;
            wearableExtender.f = this.f;
            wearableExtender.g = this.g;
            wearableExtender.h = this.h;
            wearableExtender.i = this.i;
            wearableExtender.j = this.j;
            wearableExtender.k = this.k;
            wearableExtender.l = this.l;
            wearableExtender.m = this.m;
            wearableExtender.n = this.n;
            return wearableExtender;
        }

        @NonNull
        public WearableExtender addAction(@NonNull Action action) {
            this.a.add(action);
            return this;
        }

        @NonNull
        public WearableExtender addActions(@NonNull List<Action> list) {
            this.a.addAll(list);
            return this;
        }

        @NonNull
        public WearableExtender clearActions() {
            this.a.clear();
            return this;
        }

        @NonNull
        public List<Action> getActions() {
            return this.a;
        }

        @NonNull
        @Deprecated
        public WearableExtender setDisplayIntent(@Nullable PendingIntent pendingIntent) {
            this.c = pendingIntent;
            return this;
        }

        @Nullable
        @Deprecated
        public PendingIntent getDisplayIntent() {
            return this.c;
        }

        @NonNull
        @Deprecated
        public WearableExtender addPage(@NonNull Notification notification) {
            this.d.add(notification);
            return this;
        }

        @NonNull
        @Deprecated
        public WearableExtender addPages(@NonNull List<Notification> list) {
            this.d.addAll(list);
            return this;
        }

        @NonNull
        @Deprecated
        public WearableExtender clearPages() {
            this.d.clear();
            return this;
        }

        @NonNull
        @Deprecated
        public List<Notification> getPages() {
            return this.d;
        }

        @NonNull
        @Deprecated
        public WearableExtender setBackground(@Nullable Bitmap bitmap) {
            this.e = bitmap;
            return this;
        }

        @Nullable
        @Deprecated
        public Bitmap getBackground() {
            return this.e;
        }

        @NonNull
        @Deprecated
        public WearableExtender setContentIcon(int i) {
            this.f = i;
            return this;
        }

        @Deprecated
        public int getContentIcon() {
            return this.f;
        }

        @NonNull
        @Deprecated
        public WearableExtender setContentIconGravity(int i) {
            this.g = i;
            return this;
        }

        @Deprecated
        public int getContentIconGravity() {
            return this.g;
        }

        @NonNull
        public WearableExtender setContentAction(int i) {
            this.h = i;
            return this;
        }

        public int getContentAction() {
            return this.h;
        }

        @NonNull
        @Deprecated
        public WearableExtender setGravity(int i) {
            this.k = i;
            return this;
        }

        @Deprecated
        public int getGravity() {
            return this.k;
        }

        @NonNull
        @Deprecated
        public WearableExtender setCustomSizePreset(int i) {
            this.i = i;
            return this;
        }

        @Deprecated
        public int getCustomSizePreset() {
            return this.i;
        }

        @NonNull
        @Deprecated
        public WearableExtender setCustomContentHeight(int i) {
            this.j = i;
            return this;
        }

        @Deprecated
        public int getCustomContentHeight() {
            return this.j;
        }

        @NonNull
        public WearableExtender setStartScrollBottom(boolean z) {
            a(8, z);
            return this;
        }

        public boolean getStartScrollBottom() {
            return (this.b & 8) != 0;
        }

        @NonNull
        public WearableExtender setContentIntentAvailableOffline(boolean z) {
            a(1, z);
            return this;
        }

        public boolean getContentIntentAvailableOffline() {
            return (this.b & 1) != 0;
        }

        @NonNull
        @Deprecated
        public WearableExtender setHintHideIcon(boolean z) {
            a(2, z);
            return this;
        }

        @Deprecated
        public boolean getHintHideIcon() {
            return (this.b & 2) != 0;
        }

        @NonNull
        @Deprecated
        public WearableExtender setHintShowBackgroundOnly(boolean z) {
            a(4, z);
            return this;
        }

        @Deprecated
        public boolean getHintShowBackgroundOnly() {
            return (this.b & 4) != 0;
        }

        @NonNull
        @Deprecated
        public WearableExtender setHintAvoidBackgroundClipping(boolean z) {
            a(16, z);
            return this;
        }

        @Deprecated
        public boolean getHintAvoidBackgroundClipping() {
            return (this.b & 16) != 0;
        }

        @NonNull
        @Deprecated
        public WearableExtender setHintScreenTimeout(int i) {
            this.l = i;
            return this;
        }

        @Deprecated
        public int getHintScreenTimeout() {
            return this.l;
        }

        @NonNull
        @Deprecated
        public WearableExtender setHintAmbientBigPicture(boolean z) {
            a(32, z);
            return this;
        }

        @Deprecated
        public boolean getHintAmbientBigPicture() {
            return (this.b & 32) != 0;
        }

        @NonNull
        public WearableExtender setHintContentIntentLaunchesActivity(boolean z) {
            a(64, z);
            return this;
        }

        public boolean getHintContentIntentLaunchesActivity() {
            return (this.b & 64) != 0;
        }

        @NonNull
        public WearableExtender setDismissalId(@Nullable String str) {
            this.m = str;
            return this;
        }

        @Nullable
        public String getDismissalId() {
            return this.m;
        }

        @NonNull
        public WearableExtender setBridgeTag(@Nullable String str) {
            this.n = str;
            return this;
        }

        @Nullable
        public String getBridgeTag() {
            return this.n;
        }

        private void a(int i, boolean z) {
            if (z) {
                this.b = i | this.b;
                return;
            }
            this.b = (~i) & this.b;
        }
    }

    /* loaded from: classes.dex */
    public static final class CarExtender implements Extender {
        private Bitmap a;
        private UnreadConversation b;
        private int c;

        public CarExtender() {
            this.c = 0;
        }

        public CarExtender(@NonNull Notification notification) {
            this.c = 0;
            if (Build.VERSION.SDK_INT >= 21) {
                Bundle bundle = NotificationCompat.getExtras(notification) == null ? null : NotificationCompat.getExtras(notification).getBundle("android.car.EXTENSIONS");
                if (bundle != null) {
                    this.a = (Bitmap) bundle.getParcelable("large_icon");
                    this.c = bundle.getInt("app_color", 0);
                    this.b = a(bundle.getBundle("car_conversation"));
                }
            }
        }

        @RequiresApi(21)
        private static UnreadConversation a(@Nullable Bundle bundle) {
            String[] strArr;
            RemoteInput remoteInput;
            boolean z;
            if (bundle == null) {
                return null;
            }
            Parcelable[] parcelableArray = bundle.getParcelableArray("messages");
            if (parcelableArray != null) {
                String[] strArr2 = new String[parcelableArray.length];
                int i = 0;
                while (true) {
                    if (i >= strArr2.length) {
                        z = true;
                        break;
                    } else if (!(parcelableArray[i] instanceof Bundle)) {
                        z = false;
                        break;
                    } else {
                        strArr2[i] = ((Bundle) parcelableArray[i]).getString("text");
                        if (strArr2[i] == null) {
                            z = false;
                            break;
                        }
                        i++;
                    }
                }
                if (!z) {
                    return null;
                }
                strArr = strArr2;
            } else {
                strArr = null;
            }
            PendingIntent pendingIntent = (PendingIntent) bundle.getParcelable("on_read");
            PendingIntent pendingIntent2 = (PendingIntent) bundle.getParcelable("on_reply");
            RemoteInput remoteInput2 = (RemoteInput) bundle.getParcelable("remote_input");
            String[] stringArray = bundle.getStringArray("participants");
            if (stringArray == null || stringArray.length != 1) {
                return null;
            }
            if (remoteInput2 != null) {
                remoteInput = new RemoteInput(remoteInput2.getResultKey(), remoteInput2.getLabel(), remoteInput2.getChoices(), remoteInput2.getAllowFreeFormInput(), Build.VERSION.SDK_INT >= 29 ? remoteInput2.getEditChoicesBeforeSending() : 0, remoteInput2.getExtras(), null);
            } else {
                remoteInput = null;
            }
            return new UnreadConversation(strArr, remoteInput, pendingIntent2, pendingIntent, stringArray, bundle.getLong("timestamp"));
        }

        @RequiresApi(21)
        private static Bundle a(@NonNull UnreadConversation unreadConversation) {
            Bundle bundle = new Bundle();
            String str = (unreadConversation.getParticipants() == null || unreadConversation.getParticipants().length <= 1) ? null : unreadConversation.getParticipants()[0];
            Parcelable[] parcelableArr = new Parcelable[unreadConversation.getMessages().length];
            for (int i = 0; i < parcelableArr.length; i++) {
                Bundle bundle2 = new Bundle();
                bundle2.putString("text", unreadConversation.getMessages()[i]);
                bundle2.putString("author", str);
                parcelableArr[i] = bundle2;
            }
            bundle.putParcelableArray("messages", parcelableArr);
            RemoteInput remoteInput = unreadConversation.getRemoteInput();
            if (remoteInput != null) {
                bundle.putParcelable("remote_input", new RemoteInput.Builder(remoteInput.getResultKey()).setLabel(remoteInput.getLabel()).setChoices(remoteInput.getChoices()).setAllowFreeFormInput(remoteInput.getAllowFreeFormInput()).addExtras(remoteInput.getExtras()).build());
            }
            bundle.putParcelable("on_reply", unreadConversation.getReplyPendingIntent());
            bundle.putParcelable("on_read", unreadConversation.getReadPendingIntent());
            bundle.putStringArray("participants", unreadConversation.getParticipants());
            bundle.putLong("timestamp", unreadConversation.getLatestTimestamp());
            return bundle;
        }

        @Override // androidx.core.app.NotificationCompat.Extender
        @NonNull
        public Builder extend(@NonNull Builder builder) {
            if (Build.VERSION.SDK_INT < 21) {
                return builder;
            }
            Bundle bundle = new Bundle();
            Bitmap bitmap = this.a;
            if (bitmap != null) {
                bundle.putParcelable("large_icon", bitmap);
            }
            int i = this.c;
            if (i != 0) {
                bundle.putInt("app_color", i);
            }
            UnreadConversation unreadConversation = this.b;
            if (unreadConversation != null) {
                bundle.putBundle("car_conversation", a(unreadConversation));
            }
            builder.getExtras().putBundle("android.car.EXTENSIONS", bundle);
            return builder;
        }

        @NonNull
        public CarExtender setColor(@ColorInt int i) {
            this.c = i;
            return this;
        }

        @ColorInt
        public int getColor() {
            return this.c;
        }

        @NonNull
        public CarExtender setLargeIcon(@Nullable Bitmap bitmap) {
            this.a = bitmap;
            return this;
        }

        @Nullable
        public Bitmap getLargeIcon() {
            return this.a;
        }

        @NonNull
        @Deprecated
        public CarExtender setUnreadConversation(@Nullable UnreadConversation unreadConversation) {
            this.b = unreadConversation;
            return this;
        }

        @Nullable
        @Deprecated
        public UnreadConversation getUnreadConversation() {
            return this.b;
        }

        @Deprecated
        /* loaded from: classes.dex */
        public static class UnreadConversation {
            private final String[] a;
            private final RemoteInput b;
            private final PendingIntent c;
            private final PendingIntent d;
            private final String[] e;
            private final long f;

            UnreadConversation(@Nullable String[] strArr, @Nullable RemoteInput remoteInput, @Nullable PendingIntent pendingIntent, @Nullable PendingIntent pendingIntent2, @Nullable String[] strArr2, long j) {
                this.a = strArr;
                this.b = remoteInput;
                this.d = pendingIntent2;
                this.c = pendingIntent;
                this.e = strArr2;
                this.f = j;
            }

            @Nullable
            public String[] getMessages() {
                return this.a;
            }

            @Nullable
            public RemoteInput getRemoteInput() {
                return this.b;
            }

            @Nullable
            public PendingIntent getReplyPendingIntent() {
                return this.c;
            }

            @Nullable
            public PendingIntent getReadPendingIntent() {
                return this.d;
            }

            @Nullable
            public String[] getParticipants() {
                return this.e;
            }

            @Nullable
            public String getParticipant() {
                String[] strArr = this.e;
                if (strArr.length > 0) {
                    return strArr[0];
                }
                return null;
            }

            public long getLatestTimestamp() {
                return this.f;
            }

            /* loaded from: classes.dex */
            public static class Builder {
                private final List<String> a = new ArrayList();
                private final String b;
                private RemoteInput c;
                private PendingIntent d;
                private PendingIntent e;
                private long f;

                public Builder(@NonNull String str) {
                    this.b = str;
                }

                @NonNull
                public Builder addMessage(@Nullable String str) {
                    if (str != null) {
                        this.a.add(str);
                    }
                    return this;
                }

                @NonNull
                public Builder setReplyAction(@Nullable PendingIntent pendingIntent, @Nullable RemoteInput remoteInput) {
                    this.c = remoteInput;
                    this.e = pendingIntent;
                    return this;
                }

                @NonNull
                public Builder setReadPendingIntent(@Nullable PendingIntent pendingIntent) {
                    this.d = pendingIntent;
                    return this;
                }

                @NonNull
                public Builder setLatestTimestamp(long j) {
                    this.f = j;
                    return this;
                }

                @NonNull
                public UnreadConversation build() {
                    List<String> list = this.a;
                    return new UnreadConversation((String[]) list.toArray(new String[list.size()]), this.c, this.e, this.d, new String[]{this.b}, this.f);
                }
            }
        }
    }

    /* loaded from: classes.dex */
    public static final class BubbleMetadata {
        private PendingIntent a;
        private PendingIntent b;
        private IconCompat c;
        private int d;
        @DimenRes
        private int e;
        private int f;
        private String g;

        private BubbleMetadata(@Nullable PendingIntent pendingIntent, @Nullable PendingIntent pendingIntent2, @Nullable IconCompat iconCompat, int i, @DimenRes int i2, int i3, @Nullable String str) {
            this.a = pendingIntent;
            this.c = iconCompat;
            this.d = i;
            this.e = i2;
            this.b = pendingIntent2;
            this.f = i3;
            this.g = str;
        }

        @Nullable
        @SuppressLint({"InvalidNullConversion"})
        public PendingIntent getIntent() {
            return this.a;
        }

        @Nullable
        public String getShortcutId() {
            return this.g;
        }

        @Nullable
        public PendingIntent getDeleteIntent() {
            return this.b;
        }

        @Nullable
        @SuppressLint({"InvalidNullConversion"})
        public IconCompat getIcon() {
            return this.c;
        }

        @Dimension(unit = 0)
        public int getDesiredHeight() {
            return this.d;
        }

        @DimenRes
        public int getDesiredHeightResId() {
            return this.e;
        }

        public boolean getAutoExpandBubble() {
            return (this.f & 1) != 0;
        }

        public boolean isNotificationSuppressed() {
            return (this.f & 2) != 0;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public void setFlags(int i) {
            this.f = i;
        }

        @Nullable
        public static Notification.BubbleMetadata toPlatform(@Nullable BubbleMetadata bubbleMetadata) {
            if (bubbleMetadata == null) {
                return null;
            }
            if (Build.VERSION.SDK_INT >= 30) {
                return b.a(bubbleMetadata);
            }
            if (Build.VERSION.SDK_INT == 29) {
                return a.a(bubbleMetadata);
            }
            return null;
        }

        @Nullable
        public static BubbleMetadata fromPlatform(@Nullable Notification.BubbleMetadata bubbleMetadata) {
            if (bubbleMetadata == null) {
                return null;
            }
            if (Build.VERSION.SDK_INT >= 30) {
                return b.a(bubbleMetadata);
            }
            if (Build.VERSION.SDK_INT == 29) {
                return a.a(bubbleMetadata);
            }
            return null;
        }

        /* loaded from: classes.dex */
        public static final class Builder {
            private PendingIntent a;
            private IconCompat b;
            private int c;
            @DimenRes
            private int d;
            private int e;
            private PendingIntent f;
            private String g;

            @Deprecated
            public Builder() {
            }

            @RequiresApi(30)
            public Builder(@NonNull String str) {
                if (!TextUtils.isEmpty(str)) {
                    this.g = str;
                    return;
                }
                throw new NullPointerException("Bubble requires a non-null shortcut id");
            }

            public Builder(@NonNull PendingIntent pendingIntent, @NonNull IconCompat iconCompat) {
                if (pendingIntent == null) {
                    throw new NullPointerException("Bubble requires non-null pending intent");
                } else if (iconCompat != null) {
                    this.a = pendingIntent;
                    this.b = iconCompat;
                } else {
                    throw new NullPointerException("Bubbles require non-null icon");
                }
            }

            @NonNull
            public Builder setIntent(@NonNull PendingIntent pendingIntent) {
                if (this.g != null) {
                    throw new IllegalStateException("Created as a shortcut bubble, cannot set a PendingIntent. Consider using BubbleMetadata.Builder(PendingIntent,Icon) instead.");
                } else if (pendingIntent != null) {
                    this.a = pendingIntent;
                    return this;
                } else {
                    throw new NullPointerException("Bubble requires non-null pending intent");
                }
            }

            @NonNull
            public Builder setIcon(@NonNull IconCompat iconCompat) {
                if (this.g != null) {
                    throw new IllegalStateException("Created as a shortcut bubble, cannot set an Icon. Consider using BubbleMetadata.Builder(PendingIntent,Icon) instead.");
                } else if (iconCompat != null) {
                    this.b = iconCompat;
                    return this;
                } else {
                    throw new NullPointerException("Bubbles require non-null icon");
                }
            }

            @NonNull
            public Builder setDesiredHeight(@Dimension(unit = 0) int i) {
                this.c = Math.max(i, 0);
                this.d = 0;
                return this;
            }

            @NonNull
            public Builder setDesiredHeightResId(@DimenRes int i) {
                this.d = i;
                this.c = 0;
                return this;
            }

            @NonNull
            public Builder setAutoExpandBubble(boolean z) {
                a(1, z);
                return this;
            }

            @NonNull
            public Builder setSuppressNotification(boolean z) {
                a(2, z);
                return this;
            }

            @NonNull
            public Builder setDeleteIntent(@Nullable PendingIntent pendingIntent) {
                this.f = pendingIntent;
                return this;
            }

            @NonNull
            @SuppressLint({"SyntheticAccessor"})
            public BubbleMetadata build() {
                if (this.g == null && this.a == null) {
                    throw new NullPointerException("Must supply pending intent or shortcut to bubble");
                } else if (this.g == null && this.b == null) {
                    throw new NullPointerException("Must supply an icon or shortcut for the bubble");
                } else {
                    BubbleMetadata bubbleMetadata = new BubbleMetadata(this.a, this.f, this.b, this.c, this.d, this.e, this.g);
                    bubbleMetadata.setFlags(this.e);
                    return bubbleMetadata;
                }
            }

            @NonNull
            private Builder a(int i, boolean z) {
                if (z) {
                    this.e = i | this.e;
                } else {
                    this.e = (~i) & this.e;
                }
                return this;
            }
        }

        @RequiresApi(29)
        /* loaded from: classes.dex */
        public static class a {
            @Nullable
            @RequiresApi(29)
            static Notification.BubbleMetadata a(@Nullable BubbleMetadata bubbleMetadata) {
                if (bubbleMetadata == null || bubbleMetadata.getIntent() == null) {
                    return null;
                }
                Notification.BubbleMetadata.Builder suppressNotification = new Notification.BubbleMetadata.Builder().setIcon(bubbleMetadata.getIcon().toIcon()).setIntent(bubbleMetadata.getIntent()).setDeleteIntent(bubbleMetadata.getDeleteIntent()).setAutoExpandBubble(bubbleMetadata.getAutoExpandBubble()).setSuppressNotification(bubbleMetadata.isNotificationSuppressed());
                if (bubbleMetadata.getDesiredHeight() != 0) {
                    suppressNotification.setDesiredHeight(bubbleMetadata.getDesiredHeight());
                }
                if (bubbleMetadata.getDesiredHeightResId() != 0) {
                    suppressNotification.setDesiredHeightResId(bubbleMetadata.getDesiredHeightResId());
                }
                return suppressNotification.build();
            }

            @Nullable
            @RequiresApi(29)
            static BubbleMetadata a(@Nullable Notification.BubbleMetadata bubbleMetadata) {
                if (bubbleMetadata == null || bubbleMetadata.getIntent() == null) {
                    return null;
                }
                Builder suppressNotification = new Builder(bubbleMetadata.getIntent(), IconCompat.createFromIcon(bubbleMetadata.getIcon())).setAutoExpandBubble(bubbleMetadata.getAutoExpandBubble()).setDeleteIntent(bubbleMetadata.getDeleteIntent()).setSuppressNotification(bubbleMetadata.isNotificationSuppressed());
                if (bubbleMetadata.getDesiredHeight() != 0) {
                    suppressNotification.setDesiredHeight(bubbleMetadata.getDesiredHeight());
                }
                if (bubbleMetadata.getDesiredHeightResId() != 0) {
                    suppressNotification.setDesiredHeightResId(bubbleMetadata.getDesiredHeightResId());
                }
                return suppressNotification.build();
            }
        }

        @RequiresApi(30)
        /* loaded from: classes.dex */
        public static class b {
            @Nullable
            @RequiresApi(30)
            static Notification.BubbleMetadata a(@Nullable BubbleMetadata bubbleMetadata) {
                Notification.BubbleMetadata.Builder builder;
                if (bubbleMetadata == null) {
                    return null;
                }
                if (bubbleMetadata.getShortcutId() != null) {
                    builder = new Notification.BubbleMetadata.Builder(bubbleMetadata.getShortcutId());
                } else {
                    builder = new Notification.BubbleMetadata.Builder(bubbleMetadata.getIntent(), bubbleMetadata.getIcon().toIcon());
                }
                builder.setDeleteIntent(bubbleMetadata.getDeleteIntent()).setAutoExpandBubble(bubbleMetadata.getAutoExpandBubble()).setSuppressNotification(bubbleMetadata.isNotificationSuppressed());
                if (bubbleMetadata.getDesiredHeight() != 0) {
                    builder.setDesiredHeight(bubbleMetadata.getDesiredHeight());
                }
                if (bubbleMetadata.getDesiredHeightResId() != 0) {
                    builder.setDesiredHeightResId(bubbleMetadata.getDesiredHeightResId());
                }
                return builder.build();
            }

            @Nullable
            @RequiresApi(30)
            static BubbleMetadata a(@Nullable Notification.BubbleMetadata bubbleMetadata) {
                Builder builder;
                if (bubbleMetadata == null) {
                    return null;
                }
                if (bubbleMetadata.getShortcutId() != null) {
                    builder = new Builder(bubbleMetadata.getShortcutId());
                } else {
                    builder = new Builder(bubbleMetadata.getIntent(), IconCompat.createFromIcon(bubbleMetadata.getIcon()));
                }
                builder.setAutoExpandBubble(bubbleMetadata.getAutoExpandBubble()).setDeleteIntent(bubbleMetadata.getDeleteIntent()).setSuppressNotification(bubbleMetadata.isNotificationSuppressed());
                if (bubbleMetadata.getDesiredHeight() != 0) {
                    builder.setDesiredHeight(bubbleMetadata.getDesiredHeight());
                }
                if (bubbleMetadata.getDesiredHeightResId() != 0) {
                    builder.setDesiredHeightResId(bubbleMetadata.getDesiredHeightResId());
                }
                return builder.build();
            }
        }
    }

    @NonNull
    static Notification[] a(@NonNull Bundle bundle, @NonNull String str) {
        Parcelable[] parcelableArray = bundle.getParcelableArray(str);
        if ((parcelableArray instanceof Notification[]) || parcelableArray == null) {
            return (Notification[]) parcelableArray;
        }
        Notification[] notificationArr = new Notification[parcelableArray.length];
        for (int i = 0; i < parcelableArray.length; i++) {
            notificationArr[i] = (Notification) parcelableArray[i];
        }
        bundle.putParcelableArray(str, notificationArr);
        return notificationArr;
    }

    @Nullable
    public static Bundle getExtras(@NonNull Notification notification) {
        if (Build.VERSION.SDK_INT >= 19) {
            return notification.extras;
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return c.a(notification);
        }
        return null;
    }

    public static int getActionCount(@NonNull Notification notification) {
        if (Build.VERSION.SDK_INT >= 19) {
            if (notification.actions != null) {
                return notification.actions.length;
            }
            return 0;
        } else if (Build.VERSION.SDK_INT >= 16) {
            return c.b(notification);
        } else {
            return 0;
        }
    }

    @Nullable
    public static Action getAction(@NonNull Notification notification, int i) {
        if (Build.VERSION.SDK_INT >= 20) {
            return a(notification.actions[i]);
        }
        Bundle bundle = null;
        if (Build.VERSION.SDK_INT >= 19) {
            Notification.Action action = notification.actions[i];
            SparseArray sparseParcelableArray = notification.extras.getSparseParcelableArray(NotificationCompatExtras.EXTRA_ACTION_EXTRAS);
            if (sparseParcelableArray != null) {
                bundle = (Bundle) sparseParcelableArray.get(i);
            }
            return c.a(action.icon, action.title, action.actionIntent, bundle);
        } else if (Build.VERSION.SDK_INT >= 16) {
            return c.a(notification, i);
        } else {
            return null;
        }
    }

    @Nullable
    public static BubbleMetadata getBubbleMetadata(@NonNull Notification notification) {
        if (Build.VERSION.SDK_INT >= 29) {
            return BubbleMetadata.fromPlatform(notification.getBubbleMetadata());
        }
        return null;
    }

    @NonNull
    @RequiresApi(20)
    static Action a(@NonNull Notification.Action action) {
        RemoteInput[] remoteInputArr;
        boolean z;
        int i;
        RemoteInput[] remoteInputs = action.getRemoteInputs();
        IconCompat iconCompat = null;
        boolean z2 = false;
        if (remoteInputs == null) {
            remoteInputArr = null;
        } else {
            RemoteInput[] remoteInputArr2 = new RemoteInput[remoteInputs.length];
            for (int i2 = 0; i2 < remoteInputs.length; i2++) {
                RemoteInput remoteInput = remoteInputs[i2];
                remoteInputArr2[i2] = new RemoteInput(remoteInput.getResultKey(), remoteInput.getLabel(), remoteInput.getChoices(), remoteInput.getAllowFreeFormInput(), Build.VERSION.SDK_INT >= 29 ? remoteInput.getEditChoicesBeforeSending() : 0, remoteInput.getExtras(), null);
            }
            remoteInputArr = remoteInputArr2;
        }
        if (Build.VERSION.SDK_INT >= 24) {
            z = action.getExtras().getBoolean("android.support.allowGeneratedReplies") || action.getAllowGeneratedReplies();
        } else {
            z = action.getExtras().getBoolean("android.support.allowGeneratedReplies");
        }
        boolean z3 = action.getExtras().getBoolean("android.support.action.showsUserInterface", true);
        if (Build.VERSION.SDK_INT >= 28) {
            i = action.getSemanticAction();
        } else {
            i = action.getExtras().getInt("android.support.action.semanticAction", 0);
        }
        if (Build.VERSION.SDK_INT >= 29) {
            z2 = action.isContextual();
        }
        if (Build.VERSION.SDK_INT < 23) {
            return new Action(action.icon, action.title, action.actionIntent, action.getExtras(), remoteInputArr, (RemoteInput[]) null, z, i, z3, z2);
        }
        if (action.getIcon() == null && action.icon != 0) {
            return new Action(action.icon, action.title, action.actionIntent, action.getExtras(), remoteInputArr, (RemoteInput[]) null, z, i, z3, z2);
        }
        if (action.getIcon() != null) {
            iconCompat = IconCompat.createFromIconOrNullIfZeroResId(action.getIcon());
        }
        return new Action(iconCompat, action.title, action.actionIntent, action.getExtras(), remoteInputArr, (RemoteInput[]) null, z, i, z3, z2);
    }

    @NonNull
    @RequiresApi(21)
    public static List<Action> getInvisibleActions(@NonNull Notification notification) {
        Bundle bundle;
        Bundle bundle2;
        ArrayList arrayList = new ArrayList();
        if (!(Build.VERSION.SDK_INT < 19 || (bundle = notification.extras.getBundle("android.car.EXTENSIONS")) == null || (bundle2 = bundle.getBundle("invisible_actions")) == null)) {
            for (int i = 0; i < bundle2.size(); i++) {
                arrayList.add(c.a(bundle2.getBundle(Integer.toString(i))));
            }
        }
        return arrayList;
    }

    @NonNull
    public static List<Person> getPeople(@NonNull Notification notification) {
        String[] stringArray;
        ArrayList arrayList = new ArrayList();
        if (Build.VERSION.SDK_INT >= 28) {
            ArrayList parcelableArrayList = notification.extras.getParcelableArrayList(EXTRA_PEOPLE_LIST);
            if (parcelableArrayList != null && !parcelableArrayList.isEmpty()) {
                Iterator it = parcelableArrayList.iterator();
                while (it.hasNext()) {
                    arrayList.add(Person.fromAndroidPerson((android.app.Person) it.next()));
                }
            }
        } else if (!(Build.VERSION.SDK_INT < 19 || (stringArray = notification.extras.getStringArray(EXTRA_PEOPLE)) == null || stringArray.length == 0)) {
            for (String str : stringArray) {
                arrayList.add(new Person.Builder().setUri(str).build());
            }
        }
        return arrayList;
    }

    @Nullable
    @RequiresApi(19)
    public static CharSequence getContentTitle(@NonNull Notification notification) {
        return notification.extras.getCharSequence(EXTRA_TITLE);
    }

    @Nullable
    @RequiresApi(19)
    public static CharSequence getContentText(@NonNull Notification notification) {
        return notification.extras.getCharSequence(EXTRA_TEXT);
    }

    @Nullable
    @RequiresApi(19)
    public static CharSequence getContentInfo(@NonNull Notification notification) {
        return notification.extras.getCharSequence(EXTRA_INFO_TEXT);
    }

    @Nullable
    @RequiresApi(19)
    public static CharSequence getSubText(@NonNull Notification notification) {
        return notification.extras.getCharSequence(EXTRA_SUB_TEXT);
    }

    @Nullable
    public static String getCategory(@NonNull Notification notification) {
        if (Build.VERSION.SDK_INT >= 21) {
            return notification.category;
        }
        return null;
    }

    public static boolean getLocalOnly(@NonNull Notification notification) {
        if (Build.VERSION.SDK_INT >= 20) {
            return (notification.flags & 256) != 0;
        }
        if (Build.VERSION.SDK_INT >= 19) {
            return notification.extras.getBoolean(NotificationCompatExtras.EXTRA_LOCAL_ONLY);
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return c.a(notification).getBoolean(NotificationCompatExtras.EXTRA_LOCAL_ONLY);
        }
        return false;
    }

    @Nullable
    public static String getGroup(@NonNull Notification notification) {
        if (Build.VERSION.SDK_INT >= 20) {
            return notification.getGroup();
        }
        if (Build.VERSION.SDK_INT >= 19) {
            return notification.extras.getString(NotificationCompatExtras.EXTRA_GROUP_KEY);
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return c.a(notification).getString(NotificationCompatExtras.EXTRA_GROUP_KEY);
        }
        return null;
    }

    @RequiresApi(19)
    public static boolean getShowWhen(@NonNull Notification notification) {
        return notification.extras.getBoolean(EXTRA_SHOW_WHEN);
    }

    @RequiresApi(19)
    public static boolean getUsesChronometer(@NonNull Notification notification) {
        return notification.extras.getBoolean(EXTRA_SHOW_CHRONOMETER);
    }

    public static boolean getOnlyAlertOnce(@NonNull Notification notification) {
        return (notification.flags & 8) != 0;
    }

    public static boolean getAutoCancel(@NonNull Notification notification) {
        return (notification.flags & 16) != 0;
    }

    public static boolean getOngoing(@NonNull Notification notification) {
        return (notification.flags & 2) != 0;
    }

    public static int getColor(@NonNull Notification notification) {
        if (Build.VERSION.SDK_INT >= 21) {
            return notification.color;
        }
        return 0;
    }

    public static int getVisibility(@NonNull Notification notification) {
        if (Build.VERSION.SDK_INT >= 21) {
            return notification.visibility;
        }
        return 0;
    }

    @Nullable
    public static Notification getPublicVersion(@NonNull Notification notification) {
        if (Build.VERSION.SDK_INT >= 21) {
            return notification.publicVersion;
        }
        return null;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    static boolean a(@NonNull Notification notification) {
        return (notification.flags & 128) != 0;
    }

    public static boolean isGroupSummary(@NonNull Notification notification) {
        if (Build.VERSION.SDK_INT >= 20) {
            return (notification.flags & 512) != 0;
        }
        if (Build.VERSION.SDK_INT >= 19) {
            return notification.extras.getBoolean(NotificationCompatExtras.EXTRA_GROUP_SUMMARY);
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return c.a(notification).getBoolean(NotificationCompatExtras.EXTRA_GROUP_SUMMARY);
        }
        return false;
    }

    @Nullable
    public static String getSortKey(@NonNull Notification notification) {
        if (Build.VERSION.SDK_INT >= 20) {
            return notification.getSortKey();
        }
        if (Build.VERSION.SDK_INT >= 19) {
            return notification.extras.getString(NotificationCompatExtras.EXTRA_SORT_KEY);
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return c.a(notification).getString(NotificationCompatExtras.EXTRA_SORT_KEY);
        }
        return null;
    }

    @Nullable
    public static String getChannelId(@NonNull Notification notification) {
        if (Build.VERSION.SDK_INT >= 26) {
            return notification.getChannelId();
        }
        return null;
    }

    public static long getTimeoutAfter(@NonNull Notification notification) {
        if (Build.VERSION.SDK_INT >= 26) {
            return notification.getTimeoutAfter();
        }
        return 0L;
    }

    public static int getBadgeIconType(@NonNull Notification notification) {
        if (Build.VERSION.SDK_INT >= 26) {
            return notification.getBadgeIconType();
        }
        return 0;
    }

    @Nullable
    public static String getShortcutId(@NonNull Notification notification) {
        if (Build.VERSION.SDK_INT >= 26) {
            return notification.getShortcutId();
        }
        return null;
    }

    @Nullable
    public static CharSequence getSettingsText(@NonNull Notification notification) {
        if (Build.VERSION.SDK_INT >= 26) {
            return notification.getSettingsText();
        }
        return null;
    }

    @Nullable
    public static LocusIdCompat getLocusId(@NonNull Notification notification) {
        LocusId locusId;
        if (Build.VERSION.SDK_INT < 29 || (locusId = notification.getLocusId()) == null) {
            return null;
        }
        return LocusIdCompat.toLocusIdCompat(locusId);
    }

    public static int getGroupAlertBehavior(@NonNull Notification notification) {
        if (Build.VERSION.SDK_INT >= 26) {
            return notification.getGroupAlertBehavior();
        }
        return 0;
    }

    public static boolean getAllowSystemGeneratedContextualActions(@NonNull Notification notification) {
        if (Build.VERSION.SDK_INT >= 29) {
            return notification.getAllowSystemGeneratedContextualActions();
        }
        return false;
    }
}
