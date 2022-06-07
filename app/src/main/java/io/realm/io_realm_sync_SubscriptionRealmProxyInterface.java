package io.realm;

import java.util.Date;

/* loaded from: classes5.dex */
public interface io_realm_sync_SubscriptionRealmProxyInterface {
    Date realmGet$createdAt();

    String realmGet$errorMessage();

    Date realmGet$expiresAt();

    String realmGet$matchesProperty();

    String realmGet$name();

    String realmGet$query();

    int realmGet$queryParseCounter();

    byte realmGet$status();

    Long realmGet$timeToLive();

    Date realmGet$updatedAt();

    void realmSet$createdAt(Date date);

    void realmSet$errorMessage(String str);

    void realmSet$expiresAt(Date date);

    void realmSet$matchesProperty(String str);

    void realmSet$name(String str);

    void realmSet$query(String str);

    void realmSet$queryParseCounter(int i);

    void realmSet$status(byte b);

    void realmSet$timeToLive(Long l);

    void realmSet$updatedAt(Date date);
}
