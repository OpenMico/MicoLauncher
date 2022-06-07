package org.seamless.util.dbunit;

import org.dbunit.database.IDatabaseConnection;

/* loaded from: classes4.dex */
public abstract class MySQLDBUnitOperations extends DBUnitOperations {
    @Override // org.seamless.util.dbunit.DBUnitOperations
    protected void disableReferentialIntegrity(IDatabaseConnection iDatabaseConnection) {
        try {
            iDatabaseConnection.getConnection().prepareStatement("set foreign_key_checks=0").execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override // org.seamless.util.dbunit.DBUnitOperations
    protected void enableReferentialIntegrity(IDatabaseConnection iDatabaseConnection) {
        try {
            iDatabaseConnection.getConnection().prepareStatement("set foreign_key_checks=1").execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
