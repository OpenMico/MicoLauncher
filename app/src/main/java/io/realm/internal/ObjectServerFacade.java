package io.realm.internal;

import android.content.Context;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.exceptions.RealmException;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes5.dex */
public class ObjectServerFacade {
    private static final ObjectServerFacade a = new ObjectServerFacade();
    private static ObjectServerFacade b;

    public void addSupportForObjectLevelPermissions(RealmConfiguration.Builder builder) {
    }

    public void createNativeSyncSession(RealmConfiguration realmConfiguration) {
    }

    public void downloadInitialRemoteChanges(RealmConfiguration realmConfiguration) {
    }

    public void downloadInitialSubscriptions(Realm realm) {
    }

    public String getSyncServerCertificateAssetName(RealmConfiguration realmConfiguration) {
        return null;
    }

    public String getSyncServerCertificateFilePath(RealmConfiguration realmConfiguration) {
        return null;
    }

    public void initialize(Context context, String str) {
    }

    public boolean isPartialRealm(RealmConfiguration realmConfiguration) {
        return false;
    }

    public void realmClosed(RealmConfiguration realmConfiguration) {
    }

    public boolean wasDownloadInterrupted(Throwable th) {
        return false;
    }

    public void wrapObjectStoreSessionIfRequired(OsRealmConfig osRealmConfig) {
    }

    static {
        b = null;
        try {
            b = (ObjectServerFacade) Class.forName("io.realm.internal.SyncObjectServerFacade").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (ClassNotFoundException unused) {
        } catch (IllegalAccessException e) {
            throw new RealmException("Failed to init SyncObjectServerFacade", e);
        } catch (InstantiationException e2) {
            throw new RealmException("Failed to init SyncObjectServerFacade", e2);
        } catch (NoSuchMethodException e3) {
            throw new RealmException("Failed to init SyncObjectServerFacade", e3);
        } catch (InvocationTargetException e4) {
            throw new RealmException("Failed to init SyncObjectServerFacade", e4.getTargetException());
        }
    }

    public Object[] getSyncConfigurationOptions(RealmConfiguration realmConfiguration) {
        return new Object[12];
    }

    public static ObjectServerFacade getFacade(boolean z) {
        if (z) {
            return b;
        }
        return a;
    }

    public static ObjectServerFacade getSyncFacadeIfPossible() {
        ObjectServerFacade objectServerFacade = b;
        return objectServerFacade != null ? objectServerFacade : a;
    }
}
