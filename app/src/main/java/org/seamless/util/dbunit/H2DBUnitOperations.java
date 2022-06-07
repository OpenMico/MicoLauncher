package org.seamless.util.dbunit;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.datatype.DataTypeException;
import org.dbunit.dataset.datatype.DefaultDataTypeFactory;

/* loaded from: classes4.dex */
public abstract class H2DBUnitOperations extends DBUnitOperations {
    @Override // org.seamless.util.dbunit.DBUnitOperations
    protected void disableReferentialIntegrity(IDatabaseConnection iDatabaseConnection) {
        try {
            iDatabaseConnection.getConnection().prepareStatement("set referential_integrity FALSE").execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override // org.seamless.util.dbunit.DBUnitOperations
    protected void enableReferentialIntegrity(IDatabaseConnection iDatabaseConnection) {
        try {
            iDatabaseConnection.getConnection().prepareStatement("set referential_integrity TRUE").execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.seamless.util.dbunit.DBUnitOperations
    public void editConfig(DatabaseConfig databaseConfig) {
        super.editConfig(databaseConfig);
        databaseConfig.setProperty("http://www.dbunit.org/properties/datatypeFactory", new DefaultDataTypeFactory() { // from class: org.seamless.util.dbunit.H2DBUnitOperations.1
            public DataType createDataType(int i, String str) throws DataTypeException {
                if (i == 16) {
                    return DataType.BOOLEAN;
                }
                return H2DBUnitOperations.super.createDataType(i, str);
            }
        });
    }
}
