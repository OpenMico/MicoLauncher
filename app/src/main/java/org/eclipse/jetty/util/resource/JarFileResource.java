package org.eclipse.jetty.util.resource;

import com.fasterxml.jackson.core.JsonPointer;
import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
class JarFileResource extends JarResource {
    private static final Logger LOG = Log.getLogger(JarFileResource.class);
    private boolean _directory;
    private JarEntry _entry;
    private boolean _exists;
    private File _file;
    private JarFile _jarFile;
    private String _jarUrl;
    private String[] _list;
    private String _path;

    @Override // org.eclipse.jetty.util.resource.Resource
    public String encode(String str) {
        return str;
    }

    JarFileResource(URL url) {
        super(url);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public JarFileResource(URL url, boolean z) {
        super(url, z);
    }

    @Override // org.eclipse.jetty.util.resource.JarResource, org.eclipse.jetty.util.resource.URLResource, org.eclipse.jetty.util.resource.Resource
    public synchronized void release() {
        this._list = null;
        this._entry = null;
        this._file = null;
        this._jarFile = null;
        super.release();
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [java.util.jar.JarEntry, java.util.jar.JarFile, java.lang.String[], java.io.File] */
    /* JADX WARN: Unknown variable types count: 1 */
    @Override // org.eclipse.jetty.util.resource.JarResource, org.eclipse.jetty.util.resource.URLResource
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected boolean checkConnection() {
        /*
            r3 = this;
            r0 = 0
            super.checkConnection()     // Catch: all -> 0x0018
            java.net.JarURLConnection r1 = r3._jarConnection
            if (r1 != 0) goto L_0x0010
            r3._entry = r0
            r3._file = r0
            r3._jarFile = r0
            r3._list = r0
        L_0x0010:
            java.util.jar.JarFile r0 = r3._jarFile
            if (r0 == 0) goto L_0x0016
            r0 = 1
            goto L_0x0017
        L_0x0016:
            r0 = 0
        L_0x0017:
            return r0
        L_0x0018:
            r1 = move-exception
            java.net.JarURLConnection r2 = r3._jarConnection
            if (r2 != 0) goto L_0x0025
            r3._entry = r0
            r3._file = r0
            r3._jarFile = r0
            r3._list = r0
        L_0x0025:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.resource.JarFileResource.checkConnection():boolean");
    }

    @Override // org.eclipse.jetty.util.resource.JarResource
    protected synchronized void newConnection() throws IOException {
        super.newConnection();
        this._entry = null;
        this._file = null;
        this._jarFile = null;
        this._list = null;
        int indexOf = this._urlString.indexOf("!/") + 2;
        this._jarUrl = this._urlString.substring(0, indexOf);
        this._path = this._urlString.substring(indexOf);
        if (this._path.length() == 0) {
            this._path = null;
        }
        this._jarFile = this._jarConnection.getJarFile();
        this._file = new File(this._jarFile.getName());
    }

    @Override // org.eclipse.jetty.util.resource.JarResource, org.eclipse.jetty.util.resource.URLResource, org.eclipse.jetty.util.resource.Resource
    public boolean exists() {
        boolean z = true;
        if (this._exists) {
            return true;
        }
        if (this._urlString.endsWith("!/")) {
            try {
                return newResource(this._urlString.substring(4, this._urlString.length() - 2)).exists();
            } catch (Exception e) {
                LOG.ignore(e);
                return false;
            }
        } else {
            boolean checkConnection = checkConnection();
            if (this._jarUrl == null || this._path != null) {
                JarFile jarFile = null;
                if (checkConnection) {
                    jarFile = this._jarFile;
                } else {
                    try {
                        JarURLConnection jarURLConnection = (JarURLConnection) new URL(this._jarUrl).openConnection();
                        jarURLConnection.setUseCaches(getUseCaches());
                        jarFile = jarURLConnection.getJarFile();
                    } catch (Exception e2) {
                        LOG.ignore(e2);
                    }
                }
                if (jarFile != null && this._entry == null && !this._directory) {
                    Enumeration<JarEntry> entries = jarFile.entries();
                    while (true) {
                        if (!entries.hasMoreElements()) {
                            break;
                        }
                        JarEntry nextElement = entries.nextElement();
                        String replace = nextElement.getName().replace('\\', JsonPointer.SEPARATOR);
                        if (!replace.equals(this._path)) {
                            if (!this._path.endsWith("/")) {
                                if (replace.startsWith(this._path) && replace.length() > this._path.length() && replace.charAt(this._path.length()) == '/') {
                                    this._directory = true;
                                    break;
                                }
                            } else if (replace.startsWith(this._path)) {
                                this._directory = true;
                                break;
                            }
                        } else {
                            this._entry = nextElement;
                            this._directory = this._path.endsWith("/");
                            break;
                        }
                    }
                    if (this._directory && !this._urlString.endsWith("/")) {
                        this._urlString += "/";
                        try {
                            this._url = new URL(this._urlString);
                        } catch (MalformedURLException e3) {
                            LOG.warn(e3);
                        }
                    }
                }
                if (!this._directory && this._entry == null) {
                    z = false;
                }
                this._exists = z;
                return this._exists;
            }
            this._directory = checkConnection;
            return true;
        }
    }

    @Override // org.eclipse.jetty.util.resource.URLResource, org.eclipse.jetty.util.resource.Resource
    public boolean isDirectory() {
        return this._urlString.endsWith("/") || (exists() && this._directory);
    }

    @Override // org.eclipse.jetty.util.resource.URLResource, org.eclipse.jetty.util.resource.Resource
    public long lastModified() {
        JarEntry jarEntry;
        if (!checkConnection() || this._file == null) {
            return -1L;
        }
        if (!exists() || (jarEntry = this._entry) == null) {
            return this._file.lastModified();
        }
        return jarEntry.getTime();
    }

    @Override // org.eclipse.jetty.util.resource.URLResource, org.eclipse.jetty.util.resource.Resource
    public synchronized String[] list() {
        List<String> list;
        if (isDirectory() && this._list == null) {
            try {
                list = listEntries();
            } catch (Exception e) {
                Logger logger = LOG;
                logger.warn("Retrying list:" + e, new Object[0]);
                LOG.debug(e);
                release();
                list = listEntries();
            }
            if (list != null) {
                this._list = new String[list.size()];
                list.toArray(this._list);
            }
        }
        return this._list;
    }

    private List<String> listEntries() {
        checkConnection();
        ArrayList arrayList = new ArrayList(32);
        JarFile jarFile = this._jarFile;
        if (jarFile == null) {
            try {
                JarURLConnection jarURLConnection = (JarURLConnection) new URL(this._jarUrl).openConnection();
                jarURLConnection.setUseCaches(getUseCaches());
                jarFile = jarURLConnection.getJarFile();
            } catch (Exception e) {
                e.printStackTrace();
                LOG.ignore(e);
            }
        }
        Enumeration<JarEntry> entries = jarFile.entries();
        String substring = this._urlString.substring(this._urlString.indexOf("!/") + 2);
        while (entries.hasMoreElements()) {
            String replace = entries.nextElement().getName().replace('\\', JsonPointer.SEPARATOR);
            if (replace.startsWith(substring) && replace.length() != substring.length()) {
                String substring2 = replace.substring(substring.length());
                int indexOf = substring2.indexOf(47);
                if (indexOf >= 0) {
                    if (indexOf != 0 || substring2.length() != 1) {
                        if (indexOf == 0) {
                            substring2 = substring2.substring(indexOf + 1, substring2.length());
                        } else {
                            substring2 = substring2.substring(0, indexOf + 1);
                        }
                        if (arrayList.contains(substring2)) {
                        }
                    }
                }
                arrayList.add(substring2);
            }
        }
        return arrayList;
    }

    @Override // org.eclipse.jetty.util.resource.URLResource, org.eclipse.jetty.util.resource.Resource
    public long length() {
        JarEntry jarEntry;
        if (!isDirectory() && (jarEntry = this._entry) != null) {
            return jarEntry.getSize();
        }
        return -1L;
    }

    public static Resource getNonCachingResource(Resource resource) {
        return !(resource instanceof JarFileResource) ? resource : new JarFileResource(((JarFileResource) resource).getURL(), false);
    }

    @Override // org.eclipse.jetty.util.resource.URLResource, org.eclipse.jetty.util.resource.Resource
    public boolean isContainedIn(Resource resource) throws MalformedURLException {
        String str = this._urlString;
        int indexOf = str.indexOf("!/");
        if (indexOf > 0) {
            str = str.substring(0, indexOf);
        }
        if (str.startsWith("jar:")) {
            str = str.substring(4);
        }
        return new URL(str).sameFile(resource.getURL());
    }
}
