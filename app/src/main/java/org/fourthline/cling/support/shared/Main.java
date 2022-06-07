package org.fourthline.cling.support.shared;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread;
import java.util.logging.LogManager;
import javax.inject.Inject;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import org.fourthline.cling.support.shared.log.LogView;
import org.seamless.swing.Application;
import org.seamless.swing.logging.LogMessage;
import org.seamless.swing.logging.LoggingHandler;
import org.seamless.util.OS;
import org.seamless.util.logging.LoggingUtil;

/* loaded from: classes5.dex */
public abstract class Main implements Thread.UncaughtExceptionHandler, ShutdownHandler {
    protected boolean isRegularShutdown;
    @Inject
    LogView.Presenter logPresenter;
    protected final JFrame errorWindow = new JFrame();
    protected final LoggingHandler loggingHandler = new LoggingHandler() { // from class: org.fourthline.cling.support.shared.Main.1
        protected void log(LogMessage logMessage) {
            Main.this.logPresenter.pushMessage(logMessage);
        }
    };

    protected abstract String getAppName();

    public void init() {
        try {
            if (OS.checkForMac()) {
                NewPlatformApple.setup(this, getAppName());
            }
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception unused) {
        }
        this.errorWindow.setPreferredSize(new Dimension(900, 400));
        this.errorWindow.addWindowListener(new WindowAdapter() { // from class: org.fourthline.cling.support.shared.Main.2
            public void windowClosing(WindowEvent windowEvent) {
                Main.this.errorWindow.dispose();
            }
        });
        Thread.setDefaultUncaughtExceptionHandler(this);
        Runtime.getRuntime().addShutdownHook(new Thread() { // from class: org.fourthline.cling.support.shared.Main.3
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                if (!Main.this.isRegularShutdown) {
                    Main.this.shutdown();
                }
            }
        });
        if (System.getProperty("java.util.logging.config.file") == null) {
            LoggingUtil.resetRootHandler(this.loggingHandler);
        } else {
            LogManager.getLogManager().getLogger("").addHandler(this.loggingHandler);
        }
    }

    @Override // org.fourthline.cling.support.shared.ShutdownHandler
    public void shutdown() {
        this.isRegularShutdown = true;
        SwingUtilities.invokeLater(new Runnable() { // from class: org.fourthline.cling.support.shared.Main.4
            @Override // java.lang.Runnable
            public void run() {
                Main.this.errorWindow.dispose();
            }
        });
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, final Throwable th) {
        PrintStream printStream = System.err;
        printStream.println("In thread '" + thread + "' uncaught exception: " + th);
        th.printStackTrace(System.err);
        SwingUtilities.invokeLater(new Runnable() { // from class: org.fourthline.cling.support.shared.Main.5
            @Override // java.lang.Runnable
            public void run() {
                Main.this.errorWindow.getContentPane().removeAll();
                JTextArea jTextArea = new JTextArea();
                jTextArea.setEditable(false);
                StringBuilder sb = new StringBuilder();
                sb.append("An exceptional error occurred!\nYou can try to continue or exit the application.\n\n");
                sb.append("Please tell us about this here:\nhttp://www.4thline.org/projects/mailinglists-cling.html\n\n");
                sb.append("-------------------------------------------------------------------------------------------------------------\n\n");
                StringWriter stringWriter = new StringWriter();
                th.printStackTrace(new PrintWriter(stringWriter));
                sb.append(stringWriter.toString());
                jTextArea.setText(sb.toString());
                Main.this.errorWindow.getContentPane().add(new JScrollPane(jTextArea), "Center");
                JButton jButton = new JButton("Exit Application");
                jButton.addActionListener(new ActionListener() { // from class: org.fourthline.cling.support.shared.Main.5.1
                    public void actionPerformed(ActionEvent actionEvent) {
                        System.exit(1);
                    }
                });
                Main.this.errorWindow.getContentPane().add(jButton, "South");
                Main.this.errorWindow.pack();
                Application.center(Main.this.errorWindow);
                jTextArea.setCaretPosition(0);
                Main.this.errorWindow.setVisible(true);
            }
        });
    }

    protected void removeLoggingHandler() {
        LogManager.getLogManager().getLogger("").removeHandler(this.loggingHandler);
    }
}
