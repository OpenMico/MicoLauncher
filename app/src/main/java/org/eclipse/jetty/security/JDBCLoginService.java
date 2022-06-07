package org.eclipse.jetty.security;

import com.xiaomi.mipush.sdk.Constants;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import org.eclipse.jetty.server.UserIdentity;
import org.eclipse.jetty.util.Loader;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.security.Credential;

/* loaded from: classes5.dex */
public class JDBCLoginService extends MappedLoginService {
    private static final Logger LOG = Log.getLogger(JDBCLoginService.class);
    private int _cacheTime;
    private Connection _con;
    private String _config;
    private String _jdbcDriver;
    private long _lastHashPurge;
    private String _password;
    private String _roleSql;
    private String _roleTableRoleField;
    private String _url;
    private String _userName;
    private String _userSql;
    private String _userTableKey;
    private String _userTablePasswordField;

    @Override // org.eclipse.jetty.security.MappedLoginService
    protected void loadUsers() {
    }

    public JDBCLoginService() throws IOException {
    }

    public JDBCLoginService(String str) throws IOException {
        setName(str);
    }

    public JDBCLoginService(String str, String str2) throws IOException {
        setName(str);
        setConfig(str2);
    }

    public JDBCLoginService(String str, IdentityService identityService, String str2) throws IOException {
        setName(str);
        setIdentityService(identityService);
        setConfig(str2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.eclipse.jetty.security.MappedLoginService, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStart() throws Exception {
        String str;
        String str2;
        Properties properties = new Properties();
        properties.load(Resource.newResource(this._config).getInputStream());
        this._jdbcDriver = properties.getProperty("jdbcdriver");
        this._url = properties.getProperty("url");
        this._userName = properties.getProperty("username");
        this._password = properties.getProperty("password");
        String property = properties.getProperty("usertable");
        this._userTableKey = properties.getProperty("usertablekey");
        String property2 = properties.getProperty("usertableuserfield");
        this._userTablePasswordField = properties.getProperty("usertablepasswordfield");
        String property3 = properties.getProperty("roletable");
        String property4 = properties.getProperty("roletablekey");
        this._roleTableRoleField = properties.getProperty("roletablerolefield");
        String property5 = properties.getProperty("userroletable");
        String property6 = properties.getProperty("userroletableuserkey");
        String property7 = properties.getProperty("userroletablerolekey");
        this._cacheTime = new Integer(properties.getProperty("cachetime")).intValue();
        String str3 = this._jdbcDriver;
        if (str3 == null || str3.equals("") || (str = this._url) == null || str.equals("") || (str2 = this._userName) == null || str2.equals("") || this._password == null || this._cacheTime < 0) {
            LOG.warn("UserRealm " + getName() + " has not been properly configured", new Object[0]);
        }
        this._cacheTime *= 1000;
        this._lastHashPurge = 0L;
        this._userSql = "select " + this._userTableKey + Constants.ACCEPT_TIME_SEPARATOR_SP + this._userTablePasswordField + " from " + property + " where " + property2 + " = ?";
        this._roleSql = "select r." + this._roleTableRoleField + " from " + property3 + " r, " + property5 + " u where u." + property6 + " = ? and r." + property4 + " = u." + property7;
        Loader.loadClass(getClass(), this._jdbcDriver).newInstance();
        super.doStart();
    }

    public String getConfig() {
        return this._config;
    }

    public void setConfig(String str) {
        if (!isRunning()) {
            this._config = str;
            return;
        }
        throw new IllegalStateException("Running");
    }

    public void connectDatabase() {
        try {
            Class.forName(this._jdbcDriver);
            this._con = DriverManager.getConnection(this._url, this._userName, this._password);
        } catch (ClassNotFoundException e) {
            Logger logger = LOG;
            logger.warn("UserRealm " + getName() + " could not connect to database; will try later", e);
        } catch (SQLException e2) {
            Logger logger2 = LOG;
            logger2.warn("UserRealm " + getName() + " could not connect to database; will try later", e2);
        }
    }

    @Override // org.eclipse.jetty.security.MappedLoginService, org.eclipse.jetty.security.LoginService
    public UserIdentity login(String str, Object obj) {
        long currentTimeMillis = System.currentTimeMillis();
        int i = this._cacheTime;
        if (currentTimeMillis - this._lastHashPurge > i || i == 0) {
            this._users.clear();
            this._lastHashPurge = currentTimeMillis;
            closeConnection();
        }
        return super.login(str, obj);
    }

    @Override // org.eclipse.jetty.security.MappedLoginService
    protected UserIdentity loadUser(String str) {
        try {
            if (this._con == null) {
                connectDatabase();
            }
            if (this._con != null) {
                PreparedStatement prepareStatement = this._con.prepareStatement(this._userSql);
                prepareStatement.setObject(1, str);
                ResultSet executeQuery = prepareStatement.executeQuery();
                if (!executeQuery.next()) {
                    return null;
                }
                int i = executeQuery.getInt(this._userTableKey);
                String string = executeQuery.getString(this._userTablePasswordField);
                prepareStatement.close();
                PreparedStatement prepareStatement2 = this._con.prepareStatement(this._roleSql);
                prepareStatement2.setInt(1, i);
                ResultSet executeQuery2 = prepareStatement2.executeQuery();
                ArrayList arrayList = new ArrayList();
                while (executeQuery2.next()) {
                    arrayList.add(executeQuery2.getString(this._roleTableRoleField));
                }
                prepareStatement2.close();
                return putUser(str, Credential.getCredential(string), (String[]) arrayList.toArray(new String[arrayList.size()]));
            }
            throw new SQLException("Can't connect to database");
        } catch (SQLException e) {
            Logger logger = LOG;
            logger.warn("UserRealm " + getName() + " could not load user information from database", e);
            closeConnection();
            return null;
        }
    }

    private void closeConnection() {
        if (this._con != null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Closing db connection for JDBCUserRealm", new Object[0]);
            }
            try {
                this._con.close();
            } catch (Exception e) {
                LOG.ignore(e);
            }
        }
        this._con = null;
    }
}
