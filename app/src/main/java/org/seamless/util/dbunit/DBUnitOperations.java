package org.seamless.util.dbunit;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;

/* loaded from: classes4.dex */
public abstract class DBUnitOperations extends ArrayList<Op> {
    private static final Logger log = Logger.getLogger(DBUnitOperations.class.getName());

    protected abstract void disableReferentialIntegrity(IDatabaseConnection iDatabaseConnection);

    /* JADX INFO: Access modifiers changed from: protected */
    public void editConfig(DatabaseConfig databaseConfig) {
    }

    protected abstract void enableReferentialIntegrity(IDatabaseConnection iDatabaseConnection);

    public abstract DataSource getDataSource();

    /* loaded from: classes4.dex */
    public static abstract class Op {
        ReplacementDataSet dataSet;
        DatabaseOperation operation;

        protected abstract InputStream openStream(String str);

        public Op(String str) {
            this(str, null, DatabaseOperation.CLEAN_INSERT);
        }

        public Op(String str, String str2) {
            this(str, str2, DatabaseOperation.CLEAN_INSERT);
        }

        public Op(String str, String str2, DatabaseOperation databaseOperation) {
            ReplacementDataSet replacementDataSet;
            try {
                if (str2 != null) {
                    replacementDataSet = new ReplacementDataSet(new FlatXmlDataSet(openStream(str), openStream(str2)));
                } else {
                    replacementDataSet = new ReplacementDataSet(new FlatXmlDataSet(openStream(str)));
                }
                this.dataSet = replacementDataSet;
                this.dataSet.addReplacementObject("[NULL]", (Object) null);
                this.operation = databaseOperation;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public IDataSet getDataSet() {
            return this.dataSet;
        }

        public DatabaseOperation getOperation() {
            return this.operation;
        }

        public void execute(IDatabaseConnection iDatabaseConnection) {
            try {
                this.operation.execute(iDatabaseConnection, this.dataSet);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /* loaded from: classes4.dex */
    public static class ClasspathOp extends Op {
        public ClasspathOp(String str) {
            super(str);
        }

        public ClasspathOp(String str, String str2) {
            super(str, str2);
        }

        public ClasspathOp(String str, String str2, DatabaseOperation databaseOperation) {
            super(str, str2, databaseOperation);
        }

        @Override // org.seamless.util.dbunit.DBUnitOperations.Op
        protected InputStream openStream(String str) {
            return Thread.currentThread().getContextClassLoader().getResourceAsStream(str);
        }
    }

    /* loaded from: classes4.dex */
    public class FileOp extends Op {
        public FileOp(String str) {
            super(str);
        }

        public FileOp(String str, String str2) {
            super(str, str2);
        }

        public FileOp(String str, String str2, DatabaseOperation databaseOperation) {
            super(str, str2, databaseOperation);
        }

        @Override // org.seamless.util.dbunit.DBUnitOperations.Op
        protected InputStream openStream(String str) {
            try {
                return new FileInputStream(str);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void execute() {
        Throwable th;
        IDatabaseConnection iDatabaseConnection;
        Logger logger = log;
        logger.info("Executing DBUnit operations: " + size());
        try {
            iDatabaseConnection = getConnection();
            try {
                disableReferentialIntegrity(iDatabaseConnection);
                Iterator<Op> it = iterator();
                while (it.hasNext()) {
                    it.next().execute(iDatabaseConnection);
                }
                enableReferentialIntegrity(iDatabaseConnection);
                if (iDatabaseConnection != null) {
                    try {
                        iDatabaseConnection.close();
                    } catch (Exception e) {
                        Logger logger2 = log;
                        Level level = Level.WARNING;
                        logger2.log(level, "Failed to close connection after DBUnit operation: " + e, (Throwable) e);
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (iDatabaseConnection != null) {
                    try {
                        iDatabaseConnection.close();
                    } catch (Exception e2) {
                        Logger logger3 = log;
                        Level level2 = Level.WARNING;
                        logger3.log(level2, "Failed to close connection after DBUnit operation: " + e2, (Throwable) e2);
                    }
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            iDatabaseConnection = null;
        }
    }

    protected IDatabaseConnection getConnection() {
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection(getDataSource().getConnection());
            editConfig(databaseConnection.getConfig());
            return databaseConnection;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
