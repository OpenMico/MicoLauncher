package com.xiaomi.smarthome.core.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.xiaomi.mico.router.proxy.Proxies;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

/* compiled from: Db.kt */
@Database(entities = {CategoryRankEntity.class, DeviceRankEntity.class}, exportSchema = false, version = 5)
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b'\u0018\u0000 \u00052\u00020\u0001:\u0001\u0005B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&¨\u0006\u0006"}, d2 = {"Lcom/xiaomi/smarthome/core/db/Db;", "Landroidx/room/RoomDatabase;", "()V", "rankDao", "Lcom/xiaomi/smarthome/core/db/RankDao;", "Companion", "smarthome_release"}, k = 1, mv = {1, 4, 2})
/* loaded from: classes4.dex */
public abstract class Db extends RoomDatabase {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private static final Db$Companion$MIGRATION_4_5$1 b = new Migration(4, 5) { // from class: com.xiaomi.smarthome.core.db.Db$Companion$MIGRATION_4_5$1
        @Override // androidx.room.migration.Migration
        public void migrate(@NotNull SupportSQLiteDatabase database) {
            Intrinsics.checkNotNullParameter(database, "database");
            database.execSQL("CREATE TABLE `CategoryRankEntityCopy` (`categoryName` TEXT not null,`orderNo` INTEGER not null,`homeId` TEXT not null,`deviceCount` INTEGER not null, primary key (`categoryName`,`homeId`))");
            database.execSQL("INSERT INTO `CategoryRankEntityCopy` (`categoryName`  ,`orderNo` ,`homeId` ,`deviceCount` ) SELECT `categoryName`  ,`orderNo` ,`homeId` ,`deviceCount` FROM `CategoryRankEntity`");
            database.execSQL("DROP TABLE `CategoryRankEntity`");
            database.execSQL("ALTER TABLE `CategoryRankEntityCopy` RENAME TO `CategoryRankEntity`");
        }
    };
    @NotNull
    private static final Lazy c = LazyKt.lazy(LazyThreadSafetyMode.SYNCHRONIZED, (Function0) a.a);

    @NotNull
    public abstract RankDao rankDao();

    /* compiled from: Db.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000!\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005*\u0001\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0007R\u001b\u0010\b\u001a\u00020\t8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\n\u0010\u000b¨\u0006\u000e"}, d2 = {"Lcom/xiaomi/smarthome/core/db/Db$Companion;", "", "()V", "DB_FILE_NAME", "", "MIGRATION_4_5", "com/xiaomi/smarthome/core/db/Db$Companion$MIGRATION_4_5$1", "Lcom/xiaomi/smarthome/core/db/Db$Companion$MIGRATION_4_5$1;", "instance", "Lcom/xiaomi/smarthome/core/db/Db;", "getInstance", "()Lcom/xiaomi/smarthome/core/db/Db;", "instance$delegate", "Lkotlin/Lazy;", "smarthome_release"}, k = 1, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class Companion {
        @NotNull
        public final Db getInstance() {
            Lazy lazy = Db.c;
            Companion companion = Db.Companion;
            return (Db) lazy.getValue();
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: Db.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "Lcom/xiaomi/smarthome/core/db/Db;", "invoke"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    static final class a extends Lambda implements Function0<Db> {
        public static final a a = new a();

        a() {
            super(0);
        }

        @NotNull
        /* renamed from: a */
        public final Db invoke() {
            Context app2 = Proxies.Instance.getApp();
            Intrinsics.checkNotNull(app2);
            return (Db) Room.databaseBuilder(app2, Db.class, "mico_smarthome.db").fallbackToDestructiveMigration().addMigrations(Db.b).allowMainThreadQueries().build();
        }
    }
}
