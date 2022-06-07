package org.eclipse.jetty.server.session;

import com.xiaomi.micolauncher.skills.music.model.lrc.LrcRow;
import com.xiaomi.mipush.sdk.Constants;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.SessionManager;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.session.JDBCSessionManager;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
public class JDBCSessionIdManager extends AbstractSessionIdManager {
    static final Logger LOG = SessionHandler.LOG;
    protected String _blobType;
    protected String _connectionUrl;
    protected String _createSessionIdTable;
    protected String _createSessionTable;
    protected DataSource _datasource;
    protected DatabaseAdaptor _dbAdaptor;
    protected String _deleteId;
    protected String _deleteOldExpiredSessions;
    protected String _deleteSession;
    protected Driver _driver;
    protected String _driverClassName;
    protected String _insertId;
    protected String _insertSession;
    protected String _jndiName;
    protected long _lastScavengeTime;
    protected String _longType;
    protected String _queryId;
    protected String _selectBoundedExpiredSessions;
    private String _selectExpiredSessions;
    protected Server _server;
    protected TimerTask _task;
    protected Timer _timer;
    protected String _updateSession;
    protected String _updateSessionAccessTime;
    protected String _updateSessionNode;
    protected final HashSet<String> _sessionIds = new HashSet<>();
    protected String _sessionIdTable = "JettySessionIds";
    protected String _sessionTable = "JettySessions";
    protected String _sessionTableRowId = "rowId";
    protected long _scavengeIntervalMs = 600000;

    /* loaded from: classes5.dex */
    public class DatabaseAdaptor {
        String _dbName;
        boolean _isLower;
        boolean _isUpper;

        public DatabaseAdaptor(DatabaseMetaData databaseMetaData) throws SQLException {
            JDBCSessionIdManager.this = r5;
            this._dbName = databaseMetaData.getDatabaseProductName().toLowerCase(Locale.ENGLISH);
            JDBCSessionIdManager.LOG.debug("Using database {}", this._dbName);
            this._isLower = databaseMetaData.storesLowerCaseIdentifiers();
            this._isUpper = databaseMetaData.storesUpperCaseIdentifiers();
        }

        public String convertIdentifier(String str) {
            if (this._isLower) {
                return str.toLowerCase(Locale.ENGLISH);
            }
            return this._isUpper ? str.toUpperCase(Locale.ENGLISH) : str;
        }

        public String getDBName() {
            return this._dbName;
        }

        public String getBlobType() {
            if (JDBCSessionIdManager.this._blobType != null) {
                return JDBCSessionIdManager.this._blobType;
            }
            return this._dbName.startsWith("postgres") ? "bytea" : "blob";
        }

        public String getLongType() {
            if (JDBCSessionIdManager.this._longType != null) {
                return JDBCSessionIdManager.this._longType;
            }
            return this._dbName.startsWith("oracle") ? "number(20)" : "bigint";
        }

        public InputStream getBlobInputStream(ResultSet resultSet, String str) throws SQLException {
            if (this._dbName.startsWith("postgres")) {
                return new ByteArrayInputStream(resultSet.getBytes(str));
            }
            return resultSet.getBlob(str).getBinaryStream();
        }

        public String getRowIdColumnName() {
            String str = this._dbName;
            return (str == null || !str.startsWith("oracle")) ? "rowId" : "srowId";
        }

        public boolean isEmptyStringNull() {
            return this._dbName.startsWith("oracle");
        }

        public PreparedStatement getLoadStatement(Connection connection, String str, String str2, String str3) throws SQLException {
            if ((str2 == null || "".equals(str2)) && isEmptyStringNull()) {
                PreparedStatement prepareStatement = connection.prepareStatement("select * from " + JDBCSessionIdManager.this._sessionTable + " where sessionId = ? and contextPath is null and virtualHost = ?");
                prepareStatement.setString(1, str);
                prepareStatement.setString(2, str3);
                return prepareStatement;
            }
            PreparedStatement prepareStatement2 = connection.prepareStatement("select * from " + JDBCSessionIdManager.this._sessionTable + " where sessionId = ? and contextPath = ? and virtualHost = ?");
            prepareStatement2.setString(1, str);
            prepareStatement2.setString(2, str2);
            prepareStatement2.setString(3, str3);
            return prepareStatement2;
        }
    }

    public JDBCSessionIdManager(Server server) {
        this._server = server;
    }

    public JDBCSessionIdManager(Server server, Random random) {
        super(random);
        this._server = server;
    }

    public void setDriverInfo(String str, String str2) {
        this._driverClassName = str;
        this._connectionUrl = str2;
    }

    public void setDriverInfo(Driver driver, String str) {
        this._driver = driver;
        this._connectionUrl = str;
    }

    public void setDatasource(DataSource dataSource) {
        this._datasource = dataSource;
    }

    public DataSource getDataSource() {
        return this._datasource;
    }

    public String getDriverClassName() {
        return this._driverClassName;
    }

    public String getConnectionUrl() {
        return this._connectionUrl;
    }

    public void setDatasourceName(String str) {
        this._jndiName = str;
    }

    public String getDatasourceName() {
        return this._jndiName;
    }

    public void setBlobType(String str) {
        this._blobType = str;
    }

    public String getBlobType() {
        return this._blobType;
    }

    public String getLongType() {
        return this._longType;
    }

    public void setLongType(String str) {
        this._longType = str;
    }

    public void setScavengeInterval(long j) {
        if (j <= 0) {
            j = 60;
        }
        long j2 = this._scavengeIntervalMs;
        long j3 = j * 1000;
        this._scavengeIntervalMs = j3;
        long j4 = this._scavengeIntervalMs / 10;
        if (System.currentTimeMillis() % 2 == 0) {
            this._scavengeIntervalMs += j4;
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Scavenging every " + this._scavengeIntervalMs + " ms", new Object[0]);
        }
        if (this._timer == null) {
            return;
        }
        if (j3 != j2 || this._task == null) {
            synchronized (this) {
                if (this._task != null) {
                    this._task.cancel();
                }
                this._task = new TimerTask() { // from class: org.eclipse.jetty.server.session.JDBCSessionIdManager.1
                    @Override // java.util.TimerTask, java.lang.Runnable
                    public void run() {
                        JDBCSessionIdManager.this.scavenge();
                    }
                };
                this._timer.schedule(this._task, this._scavengeIntervalMs, this._scavengeIntervalMs);
            }
        }
    }

    public long getScavengeInterval() {
        return this._scavengeIntervalMs / 1000;
    }

    @Override // org.eclipse.jetty.server.SessionIdManager
    public void addSession(HttpSession httpSession) {
        if (httpSession != null) {
            synchronized (this._sessionIds) {
                String clusterId = ((JDBCSessionManager.Session) httpSession).getClusterId();
                try {
                    insert(clusterId);
                    this._sessionIds.add(clusterId);
                } catch (Exception e) {
                    Logger logger = LOG;
                    logger.warn("Problem storing session id=" + clusterId, e);
                }
            }
        }
    }

    @Override // org.eclipse.jetty.server.SessionIdManager
    public void removeSession(HttpSession httpSession) {
        if (httpSession != null) {
            removeSession(((JDBCSessionManager.Session) httpSession).getClusterId());
        }
    }

    public void removeSession(String str) {
        if (str != null) {
            synchronized (this._sessionIds) {
                if (LOG.isDebugEnabled()) {
                    Logger logger = LOG;
                    logger.debug("Removing session id=" + str, new Object[0]);
                }
                try {
                    this._sessionIds.remove(str);
                    delete(str);
                } catch (Exception e) {
                    Logger logger2 = LOG;
                    logger2.warn("Problem removing session id=" + str, e);
                }
            }
        }
    }

    @Override // org.eclipse.jetty.server.SessionIdManager
    public String getClusterId(String str) {
        int lastIndexOf = str.lastIndexOf(46);
        return lastIndexOf > 0 ? str.substring(0, lastIndexOf) : str;
    }

    @Override // org.eclipse.jetty.server.SessionIdManager
    public String getNodeId(String str, HttpServletRequest httpServletRequest) {
        if (this._workerName == null) {
            return str;
        }
        return str + '.' + this._workerName;
    }

    @Override // org.eclipse.jetty.server.SessionIdManager
    public boolean idInUse(String str) {
        boolean contains;
        if (str == null) {
            return false;
        }
        String clusterId = getClusterId(str);
        synchronized (this._sessionIds) {
            contains = this._sessionIds.contains(clusterId);
        }
        if (contains) {
            return true;
        }
        try {
            return exists(clusterId);
        } catch (Exception e) {
            Logger logger = LOG;
            logger.warn("Problem checking inUse for id=" + clusterId, e);
            return false;
        }
    }

    @Override // org.eclipse.jetty.server.SessionIdManager
    public void invalidateAll(String str) {
        SessionManager sessionManager;
        removeSession(str);
        synchronized (this._sessionIds) {
            Handler[] childHandlersByClass = this._server.getChildHandlersByClass(ContextHandler.class);
            for (int i = 0; childHandlersByClass != null && i < childHandlersByClass.length; i++) {
                SessionHandler sessionHandler = (SessionHandler) ((ContextHandler) childHandlersByClass[i]).getChildHandlerByClass(SessionHandler.class);
                if (!(sessionHandler == null || (sessionManager = sessionHandler.getSessionManager()) == null || !(sessionManager instanceof JDBCSessionManager))) {
                    ((JDBCSessionManager) sessionManager).invalidateSession(str);
                }
            }
        }
    }

    @Override // org.eclipse.jetty.server.session.AbstractSessionIdManager, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStart() {
        try {
            initializeDatabase();
            prepareTables();
            cleanExpiredSessions();
            super.doStart();
            if (LOG.isDebugEnabled()) {
                Logger logger = LOG;
                logger.debug("Scavenging interval = " + getScavengeInterval() + " sec", new Object[0]);
            }
            this._timer = new Timer("JDBCSessionScavenger", true);
            setScavengeInterval(getScavengeInterval());
        } catch (Exception e) {
            LOG.warn("Problem initialising JettySessionIds table", e);
        }
    }

    @Override // org.eclipse.jetty.server.session.AbstractSessionIdManager, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStop() throws Exception {
        synchronized (this) {
            if (this._task != null) {
                this._task.cancel();
            }
            if (this._timer != null) {
                this._timer.cancel();
            }
            this._timer = null;
        }
        this._sessionIds.clear();
        super.doStop();
    }

    public Connection getConnection() throws SQLException {
        DataSource dataSource = this._datasource;
        if (dataSource != null) {
            return dataSource.getConnection();
        }
        return DriverManager.getConnection(this._connectionUrl);
    }

    private void prepareTables() throws SQLException {
        Connection connection;
        Throwable th;
        this._createSessionIdTable = "create table " + this._sessionIdTable + " (id varchar(120), primary key(id))";
        this._selectBoundedExpiredSessions = "select * from " + this._sessionTable + " where expiryTime >= ? and expiryTime <= ?";
        this._selectExpiredSessions = "select * from " + this._sessionTable + " where expiryTime >0 and expiryTime <= ?";
        this._deleteOldExpiredSessions = "delete from " + this._sessionTable + " where expiryTime >0 and expiryTime <= ?";
        this._insertId = "insert into " + this._sessionIdTable + " (id)  values (?)";
        this._deleteId = "delete from " + this._sessionIdTable + " where id = ?";
        this._queryId = "select * from " + this._sessionIdTable + " where id = ?";
        try {
            connection = getConnection();
            try {
                connection.setAutoCommit(true);
                DatabaseMetaData metaData = connection.getMetaData();
                this._dbAdaptor = new DatabaseAdaptor(metaData);
                this._sessionTableRowId = this._dbAdaptor.getRowIdColumnName();
                if (!metaData.getTables(null, null, this._dbAdaptor.convertIdentifier(this._sessionIdTable), null).next()) {
                    connection.createStatement().executeUpdate(this._createSessionIdTable);
                }
                String convertIdentifier = this._dbAdaptor.convertIdentifier(this._sessionTable);
                if (!metaData.getTables(null, null, convertIdentifier, null).next()) {
                    String blobType = this._dbAdaptor.getBlobType();
                    String longType = this._dbAdaptor.getLongType();
                    this._createSessionTable = "create table " + this._sessionTable + " (" + this._sessionTableRowId + " varchar(120), sessionId varchar(120),  contextPath varchar(60), virtualHost varchar(60), lastNode varchar(60), accessTime " + longType + ",  lastAccessTime " + longType + ", createTime " + longType + ", cookieTime " + longType + ",  lastSavedTime " + longType + ", expiryTime " + longType + ", map " + blobType + ", primary key(" + this._sessionTableRowId + "))";
                    connection.createStatement().executeUpdate(this._createSessionTable);
                }
                String str = "idx_" + this._sessionTable + "_expiry";
                String str2 = "idx_" + this._sessionTable + "_session";
                ResultSet indexInfo = metaData.getIndexInfo(null, null, convertIdentifier, false, false);
                boolean z = false;
                boolean z2 = false;
                while (indexInfo.next()) {
                    String string = indexInfo.getString("INDEX_NAME");
                    if (str.equalsIgnoreCase(string)) {
                        z = true;
                    } else if (str2.equalsIgnoreCase(string)) {
                        z2 = true;
                    }
                }
                if (!z || !z2) {
                    Statement createStatement = connection.createStatement();
                    if (!z) {
                        createStatement.executeUpdate("create index " + str + " on " + this._sessionTable + " (expiryTime)");
                    }
                    if (!z2) {
                        createStatement.executeUpdate("create index " + str2 + " on " + this._sessionTable + " (sessionId, contextPath)");
                    }
                }
                this._insertSession = "insert into " + this._sessionTable + " (" + this._sessionTableRowId + ", sessionId, contextPath, virtualHost, lastNode, accessTime, lastAccessTime, createTime, cookieTime, lastSavedTime, expiryTime, map)  values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                StringBuilder sb = new StringBuilder();
                sb.append("delete from ");
                sb.append(this._sessionTable);
                sb.append(" where ");
                sb.append(this._sessionTableRowId);
                sb.append(" = ?");
                this._deleteSession = sb.toString();
                this._updateSession = "update " + this._sessionTable + " set lastNode = ?, accessTime = ?, lastAccessTime = ?, lastSavedTime = ?, expiryTime = ?, map = ? where " + this._sessionTableRowId + " = ?";
                this._updateSessionNode = "update " + this._sessionTable + " set lastNode = ? where " + this._sessionTableRowId + " = ?";
                this._updateSessionAccessTime = "update " + this._sessionTable + " set lastNode = ?, accessTime = ?, lastAccessTime = ?, lastSavedTime = ?, expiryTime = ? where " + this._sessionTableRowId + " = ?";
                if (connection != null) {
                    connection.close();
                }
            } catch (Throwable th2) {
                th = th2;
                if (connection != null) {
                    connection.close();
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            connection = null;
        }
    }

    private void insert(String str) throws SQLException {
        Throwable th;
        Connection connection;
        try {
            connection = getConnection();
            try {
                connection.setAutoCommit(true);
                PreparedStatement prepareStatement = connection.prepareStatement(this._queryId);
                prepareStatement.setString(1, str);
                if (!prepareStatement.executeQuery().next()) {
                    PreparedStatement prepareStatement2 = connection.prepareStatement(this._insertId);
                    prepareStatement2.setString(1, str);
                    prepareStatement2.executeUpdate();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Throwable th2) {
                th = th2;
                if (connection != null) {
                    connection.close();
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            connection = null;
        }
    }

    private void delete(String str) throws SQLException {
        Throwable th;
        Connection connection;
        try {
            connection = getConnection();
            try {
                connection.setAutoCommit(true);
                PreparedStatement prepareStatement = connection.prepareStatement(this._deleteId);
                prepareStatement.setString(1, str);
                prepareStatement.executeUpdate();
                if (connection != null) {
                    connection.close();
                }
            } catch (Throwable th2) {
                th = th2;
                if (connection != null) {
                    connection.close();
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            connection = null;
        }
    }

    private boolean exists(String str) throws SQLException {
        Throwable th;
        Connection connection;
        try {
            connection = getConnection();
        } catch (Throwable th2) {
            th = th2;
            connection = null;
        }
        try {
            connection.setAutoCommit(true);
            PreparedStatement prepareStatement = connection.prepareStatement(this._queryId);
            prepareStatement.setString(1, str);
            boolean next = prepareStatement.executeQuery().next();
            if (connection != null) {
                connection.close();
            }
            return next;
        } catch (Throwable th3) {
            th = th3;
            if (connection != null) {
                connection.close();
            }
            throw th;
        }
    }

    public void scavenge() {
        SessionManager sessionManager;
        ArrayList arrayList = new ArrayList();
        Connection connection = null;
        try {
            try {
                try {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Scavenge sweep started at " + System.currentTimeMillis(), new Object[0]);
                    }
                    if (this._lastScavengeTime > 0) {
                        connection = getConnection();
                        connection.setAutoCommit(true);
                        PreparedStatement prepareStatement = connection.prepareStatement(this._selectBoundedExpiredSessions);
                        long j = this._lastScavengeTime - this._scavengeIntervalMs;
                        long j2 = this._lastScavengeTime;
                        if (LOG.isDebugEnabled()) {
                            LOG.debug(" Searching for sessions expired between " + j + " and " + j2, new Object[0]);
                        }
                        prepareStatement.setLong(1, j);
                        prepareStatement.setLong(2, j2);
                        ResultSet executeQuery = prepareStatement.executeQuery();
                        while (executeQuery.next()) {
                            String string = executeQuery.getString("sessionId");
                            arrayList.add(string);
                            if (LOG.isDebugEnabled()) {
                                LOG.debug(" Found expired sessionId=" + string, new Object[0]);
                            }
                        }
                        Handler[] childHandlersByClass = this._server.getChildHandlersByClass(ContextHandler.class);
                        for (int i = 0; childHandlersByClass != null && i < childHandlersByClass.length; i++) {
                            SessionHandler sessionHandler = (SessionHandler) ((ContextHandler) childHandlersByClass[i]).getChildHandlerByClass(SessionHandler.class);
                            if (!(sessionHandler == null || (sessionManager = sessionHandler.getSessionManager()) == null || !(sessionManager instanceof JDBCSessionManager))) {
                                ((JDBCSessionManager) sessionManager).expire(arrayList);
                            }
                        }
                        long j3 = this._lastScavengeTime - (this._scavengeIntervalMs * 2);
                        if (j3 > 0) {
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("Deleting old expired sessions expired before " + j3, new Object[0]);
                            }
                            PreparedStatement prepareStatement2 = connection.prepareStatement(this._deleteOldExpiredSessions);
                            prepareStatement2.setLong(1, j3);
                            int executeUpdate = prepareStatement2.executeUpdate();
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("Deleted " + executeUpdate + " rows", new Object[0]);
                            }
                        }
                    }
                    this._lastScavengeTime = System.currentTimeMillis();
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Scavenge sweep ended at " + this._lastScavengeTime, new Object[0]);
                    }
                    if (connection != null) {
                        connection.close();
                    }
                } catch (Exception e) {
                    if (isRunning()) {
                        LOG.warn("Problem selecting expired sessions", e);
                    } else {
                        LOG.ignore(e);
                    }
                    this._lastScavengeTime = System.currentTimeMillis();
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Scavenge sweep ended at " + this._lastScavengeTime, new Object[0]);
                    }
                    if (connection != null) {
                        connection.close();
                    }
                }
            } catch (SQLException e2) {
                LOG.warn(e2);
            }
        } catch (Throwable th) {
            this._lastScavengeTime = System.currentTimeMillis();
            if (LOG.isDebugEnabled()) {
                LOG.debug("Scavenge sweep ended at " + this._lastScavengeTime, new Object[0]);
            }
            if (0 != 0) {
                try {
                    connection.close();
                } catch (SQLException e3) {
                    LOG.warn(e3);
                }
            }
            throw th;
        }
    }

    private void cleanExpiredSessions() throws Exception {
        Connection connection;
        try {
            ArrayList arrayList = new ArrayList();
            connection = null;
            try {
                connection = getConnection();
                connection.setTransactionIsolation(8);
                connection.setAutoCommit(false);
                PreparedStatement prepareStatement = connection.prepareStatement(this._selectExpiredSessions);
                long currentTimeMillis = System.currentTimeMillis();
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Searching for sessions expired before {}", Long.valueOf(currentTimeMillis));
                }
                prepareStatement.setLong(1, currentTimeMillis);
                ResultSet executeQuery = prepareStatement.executeQuery();
                while (executeQuery.next()) {
                    String string = executeQuery.getString("sessionId");
                    arrayList.add(string);
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Found expired sessionId={}", string);
                    }
                }
                if (!arrayList.isEmpty()) {
                    Statement createStatement = connection.createStatement();
                    createStatement.executeUpdate(createCleanExpiredSessionsSql("delete from " + this._sessionTable + " where sessionId in ", arrayList));
                    Statement createStatement2 = connection.createStatement();
                    createStatement2.executeUpdate(createCleanExpiredSessionsSql("delete from " + this._sessionIdTable + " where id in ", arrayList));
                }
                connection.commit();
                synchronized (this._sessionIds) {
                    this._sessionIds.removeAll(arrayList);
                }
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        LOG.warn(e);
                    }
                }
            } catch (Exception e2) {
                if (connection != null) {
                    connection.rollback();
                }
                throw e2;
            }
        } catch (Throwable th) {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e3) {
                    LOG.warn(e3);
                }
            }
            throw th;
        }
    }

    private String createCleanExpiredSessionsSql(String str, Collection<String> collection) throws Exception {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str);
        stringBuffer.append("(");
        Iterator<String> it = collection.iterator();
        while (it.hasNext()) {
            stringBuffer.append(LrcRow.SINGLE_QUOTE + it.next() + LrcRow.SINGLE_QUOTE);
            if (it.hasNext()) {
                stringBuffer.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
            }
        }
        stringBuffer.append(")");
        if (LOG.isDebugEnabled()) {
            LOG.debug("Cleaning expired sessions with: {}", stringBuffer);
        }
        return stringBuffer.toString();
    }

    private void initializeDatabase() throws Exception {
        if (this._datasource == null) {
            if (this._jndiName != null) {
                this._datasource = (DataSource) new InitialContext().lookup(this._jndiName);
                return;
            }
            Driver driver = this._driver;
            if (driver == null || this._connectionUrl == null) {
                String str = this._driverClassName;
                if (str == null || this._connectionUrl == null) {
                    throw new IllegalStateException("No database configured for sessions");
                }
                Class.forName(str);
                return;
            }
            DriverManager.registerDriver(driver);
        }
    }
}
