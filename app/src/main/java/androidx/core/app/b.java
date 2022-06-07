package androidx.core.app;

import android.app.Notification;
import android.app.RemoteInput;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.SparseArray;
import android.widget.RemoteViews;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.collection.ArraySet;
import androidx.core.app.NotificationCompat;
import androidx.core.graphics.drawable.IconCompat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: NotificationCompatBuilder.java */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes.dex */
public class b implements NotificationBuilderWithBuilderAccessor {
    private final Context a;
    private final Notification.Builder b;
    private final NotificationCompat.Builder c;
    private RemoteViews d;
    private RemoteViews e;
    private final List<Bundle> f = new ArrayList();
    private final Bundle g = new Bundle();
    private int h;
    private RemoteViews i;

    /* JADX INFO: Access modifiers changed from: package-private */
    public b(NotificationCompat.Builder builder) {
        List<String> list;
        List<String> a;
        this.c = builder;
        this.a = builder.mContext;
        if (Build.VERSION.SDK_INT >= 26) {
            this.b = new Notification.Builder(builder.mContext, builder.I);
        } else {
            this.b = new Notification.Builder(builder.mContext);
        }
        Notification notification = builder.Q;
        this.b.setWhen(notification.when).setSmallIcon(notification.icon, notification.iconLevel).setContent(notification.contentView).setTicker(notification.tickerText, builder.f).setVibrate(notification.vibrate).setLights(notification.ledARGB, notification.ledOnMS, notification.ledOffMS).setOngoing((notification.flags & 2) != 0).setOnlyAlertOnce((notification.flags & 8) != 0).setAutoCancel((notification.flags & 16) != 0).setDefaults(notification.defaults).setContentTitle(builder.b).setContentText(builder.c).setContentInfo(builder.h).setContentIntent(builder.d).setDeleteIntent(notification.deleteIntent).setFullScreenIntent(builder.e, (notification.flags & 128) != 0).setLargeIcon(builder.g).setNumber(builder.i).setProgress(builder.r, builder.s, builder.t);
        if (Build.VERSION.SDK_INT < 21) {
            this.b.setSound(notification.sound, notification.audioStreamType);
        }
        if (Build.VERSION.SDK_INT >= 16) {
            this.b.setSubText(builder.o).setUsesChronometer(builder.l).setPriority(builder.j);
            Iterator<NotificationCompat.Action> it = builder.mActions.iterator();
            while (it.hasNext()) {
                a(it.next());
            }
            if (builder.B != null) {
                this.g.putAll(builder.B);
            }
            if (Build.VERSION.SDK_INT < 20) {
                if (builder.x) {
                    this.g.putBoolean(NotificationCompatExtras.EXTRA_LOCAL_ONLY, true);
                }
                if (builder.u != null) {
                    this.g.putString(NotificationCompatExtras.EXTRA_GROUP_KEY, builder.u);
                    if (builder.v) {
                        this.g.putBoolean(NotificationCompatExtras.EXTRA_GROUP_SUMMARY, true);
                    } else {
                        this.g.putBoolean(NotificationManagerCompat.EXTRA_USE_SIDE_CHANNEL, true);
                    }
                }
                if (builder.w != null) {
                    this.g.putString(NotificationCompatExtras.EXTRA_SORT_KEY, builder.w);
                }
            }
            this.d = builder.F;
            this.e = builder.G;
        }
        if (Build.VERSION.SDK_INT >= 17) {
            this.b.setShowWhen(builder.k);
        }
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21 && (a = a(a(builder.mPersonList), builder.mPeople)) != null && !a.isEmpty()) {
            this.g.putStringArray(NotificationCompat.EXTRA_PEOPLE, (String[]) a.toArray(new String[a.size()]));
        }
        if (Build.VERSION.SDK_INT >= 20) {
            this.b.setLocalOnly(builder.x).setGroup(builder.u).setGroupSummary(builder.v).setSortKey(builder.w);
            this.h = builder.N;
        }
        if (Build.VERSION.SDK_INT >= 21) {
            this.b.setCategory(builder.A).setColor(builder.C).setVisibility(builder.D).setPublicVersion(builder.E).setSound(notification.sound, notification.audioAttributes);
            if (Build.VERSION.SDK_INT < 28) {
                list = a(a(builder.mPersonList), builder.mPeople);
            } else {
                list = builder.mPeople;
            }
            if (list != null && !list.isEmpty()) {
                for (String str : list) {
                    this.b.addPerson(str);
                }
            }
            this.i = builder.H;
            if (builder.a.size() > 0) {
                Bundle bundle = builder.getExtras().getBundle("android.car.EXTENSIONS");
                bundle = bundle == null ? new Bundle() : bundle;
                Bundle bundle2 = new Bundle(bundle);
                Bundle bundle3 = new Bundle();
                for (int i = 0; i < builder.a.size(); i++) {
                    bundle3.putBundle(Integer.toString(i), c.a(builder.a.get(i)));
                }
                bundle.putBundle("invisible_actions", bundle3);
                bundle2.putBundle("invisible_actions", bundle3);
                builder.getExtras().putBundle("android.car.EXTENSIONS", bundle);
                this.g.putBundle("android.car.EXTENSIONS", bundle2);
            }
        }
        if (Build.VERSION.SDK_INT >= 23 && builder.S != null) {
            this.b.setSmallIcon(builder.S);
        }
        if (Build.VERSION.SDK_INT >= 24) {
            this.b.setExtras(builder.B).setRemoteInputHistory(builder.q);
            if (builder.F != null) {
                this.b.setCustomContentView(builder.F);
            }
            if (builder.G != null) {
                this.b.setCustomBigContentView(builder.G);
            }
            if (builder.H != null) {
                this.b.setCustomHeadsUpContentView(builder.H);
            }
        }
        if (Build.VERSION.SDK_INT >= 26) {
            this.b.setBadgeIconType(builder.J).setSettingsText(builder.p).setShortcutId(builder.K).setTimeoutAfter(builder.M).setGroupAlertBehavior(builder.N);
            if (builder.z) {
                this.b.setColorized(builder.y);
            }
            if (!TextUtils.isEmpty(builder.I)) {
                this.b.setSound(null).setDefaults(0).setLights(0, 0, 0).setVibrate(null);
            }
        }
        if (Build.VERSION.SDK_INT >= 28) {
            Iterator<Person> it2 = builder.mPersonList.iterator();
            while (it2.hasNext()) {
                this.b.addPerson(it2.next().toAndroidPerson());
            }
        }
        if (Build.VERSION.SDK_INT >= 29) {
            this.b.setAllowSystemGeneratedContextualActions(builder.O);
            this.b.setBubbleMetadata(NotificationCompat.BubbleMetadata.toPlatform(builder.P));
            if (builder.L != null) {
                this.b.setLocusId(builder.L.toLocusId());
            }
        }
        if (builder.R) {
            if (this.c.v) {
                this.h = 2;
            } else {
                this.h = 1;
            }
            this.b.setVibrate(null);
            this.b.setSound(null);
            notification.defaults &= -2;
            notification.defaults &= -3;
            this.b.setDefaults(notification.defaults);
            if (Build.VERSION.SDK_INT >= 26) {
                if (TextUtils.isEmpty(this.c.u)) {
                    this.b.setGroup(NotificationCompat.GROUP_KEY_SILENT);
                }
                this.b.setGroupAlertBehavior(this.h);
            }
        }
    }

    @Nullable
    private static List<String> a(@Nullable List<String> list, @Nullable List<String> list2) {
        if (list == null) {
            return list2;
        }
        if (list2 == null) {
            return list;
        }
        ArraySet arraySet = new ArraySet(list.size() + list2.size());
        arraySet.addAll(list);
        arraySet.addAll(list2);
        return new ArrayList(arraySet);
    }

    @Nullable
    private static List<String> a(@Nullable List<Person> list) {
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(list.size());
        for (Person person : list) {
            arrayList.add(person.resolveToLegacyUri());
        }
        return arrayList;
    }

    @Override // androidx.core.app.NotificationBuilderWithBuilderAccessor
    public Notification.Builder getBuilder() {
        return this.b;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Context a() {
        return this.a;
    }

    public Notification b() {
        Bundle extras;
        RemoteViews makeHeadsUpContentView;
        RemoteViews makeBigContentView;
        NotificationCompat.Style style = this.c.n;
        if (style != null) {
            style.apply(this);
        }
        RemoteViews makeContentView = style != null ? style.makeContentView(this) : null;
        Notification c = c();
        if (makeContentView != null) {
            c.contentView = makeContentView;
        } else if (this.c.F != null) {
            c.contentView = this.c.F;
        }
        if (!(Build.VERSION.SDK_INT < 16 || style == null || (makeBigContentView = style.makeBigContentView(this)) == null)) {
            c.bigContentView = makeBigContentView;
        }
        if (!(Build.VERSION.SDK_INT < 21 || style == null || (makeHeadsUpContentView = this.c.n.makeHeadsUpContentView(this)) == null)) {
            c.headsUpContentView = makeHeadsUpContentView;
        }
        if (!(Build.VERSION.SDK_INT < 16 || style == null || (extras = NotificationCompat.getExtras(c)) == null)) {
            style.addCompatExtras(extras);
        }
        return c;
    }

    private void a(NotificationCompat.Action action) {
        Notification.Action.Builder builder;
        Bundle bundle;
        if (Build.VERSION.SDK_INT >= 20) {
            IconCompat iconCompat = action.getIconCompat();
            if (Build.VERSION.SDK_INT >= 23) {
                builder = new Notification.Action.Builder(iconCompat != null ? iconCompat.toIcon() : null, action.getTitle(), action.getActionIntent());
            } else {
                builder = new Notification.Action.Builder(iconCompat != null ? iconCompat.getResId() : 0, action.getTitle(), action.getActionIntent());
            }
            if (action.getRemoteInputs() != null) {
                for (RemoteInput remoteInput : RemoteInput.a(action.getRemoteInputs())) {
                    builder.addRemoteInput(remoteInput);
                }
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
            bundle.putInt("android.support.action.semanticAction", action.getSemanticAction());
            if (Build.VERSION.SDK_INT >= 28) {
                builder.setSemanticAction(action.getSemanticAction());
            }
            if (Build.VERSION.SDK_INT >= 29) {
                builder.setContextual(action.isContextual());
            }
            bundle.putBoolean("android.support.action.showsUserInterface", action.getShowsUserInterface());
            builder.addExtras(bundle);
            this.b.addAction(builder.build());
        } else if (Build.VERSION.SDK_INT >= 16) {
            this.f.add(c.a(this.b, action));
        }
    }

    protected Notification c() {
        if (Build.VERSION.SDK_INT >= 26) {
            return this.b.build();
        }
        if (Build.VERSION.SDK_INT >= 24) {
            Notification build = this.b.build();
            if (this.h != 0) {
                if (!(build.getGroup() == null || (build.flags & 512) == 0 || this.h != 2)) {
                    a(build);
                }
                if (build.getGroup() != null && (build.flags & 512) == 0 && this.h == 1) {
                    a(build);
                }
            }
            return build;
        } else if (Build.VERSION.SDK_INT >= 21) {
            this.b.setExtras(this.g);
            Notification build2 = this.b.build();
            RemoteViews remoteViews = this.d;
            if (remoteViews != null) {
                build2.contentView = remoteViews;
            }
            RemoteViews remoteViews2 = this.e;
            if (remoteViews2 != null) {
                build2.bigContentView = remoteViews2;
            }
            RemoteViews remoteViews3 = this.i;
            if (remoteViews3 != null) {
                build2.headsUpContentView = remoteViews3;
            }
            if (this.h != 0) {
                if (!(build2.getGroup() == null || (build2.flags & 512) == 0 || this.h != 2)) {
                    a(build2);
                }
                if (build2.getGroup() != null && (build2.flags & 512) == 0 && this.h == 1) {
                    a(build2);
                }
            }
            return build2;
        } else if (Build.VERSION.SDK_INT >= 20) {
            this.b.setExtras(this.g);
            Notification build3 = this.b.build();
            RemoteViews remoteViews4 = this.d;
            if (remoteViews4 != null) {
                build3.contentView = remoteViews4;
            }
            RemoteViews remoteViews5 = this.e;
            if (remoteViews5 != null) {
                build3.bigContentView = remoteViews5;
            }
            if (this.h != 0) {
                if (!(build3.getGroup() == null || (build3.flags & 512) == 0 || this.h != 2)) {
                    a(build3);
                }
                if (build3.getGroup() != null && (build3.flags & 512) == 0 && this.h == 1) {
                    a(build3);
                }
            }
            return build3;
        } else if (Build.VERSION.SDK_INT >= 19) {
            SparseArray<Bundle> a = c.a(this.f);
            if (a != null) {
                this.g.putSparseParcelableArray(NotificationCompatExtras.EXTRA_ACTION_EXTRAS, a);
            }
            this.b.setExtras(this.g);
            Notification build4 = this.b.build();
            RemoteViews remoteViews6 = this.d;
            if (remoteViews6 != null) {
                build4.contentView = remoteViews6;
            }
            RemoteViews remoteViews7 = this.e;
            if (remoteViews7 != null) {
                build4.bigContentView = remoteViews7;
            }
            return build4;
        } else if (Build.VERSION.SDK_INT < 16) {
            return this.b.getNotification();
        } else {
            Notification build5 = this.b.build();
            Bundle extras = NotificationCompat.getExtras(build5);
            Bundle bundle = new Bundle(this.g);
            for (String str : this.g.keySet()) {
                if (extras.containsKey(str)) {
                    bundle.remove(str);
                }
            }
            extras.putAll(bundle);
            SparseArray<Bundle> a2 = c.a(this.f);
            if (a2 != null) {
                NotificationCompat.getExtras(build5).putSparseParcelableArray(NotificationCompatExtras.EXTRA_ACTION_EXTRAS, a2);
            }
            RemoteViews remoteViews8 = this.d;
            if (remoteViews8 != null) {
                build5.contentView = remoteViews8;
            }
            RemoteViews remoteViews9 = this.e;
            if (remoteViews9 != null) {
                build5.bigContentView = remoteViews9;
            }
            return build5;
        }
    }

    private void a(Notification notification) {
        notification.sound = null;
        notification.vibrate = null;
        notification.defaults &= -2;
        notification.defaults &= -3;
    }
}
