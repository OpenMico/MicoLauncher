package org.fourthline.cling.support.shared;

import java.awt.Dimension;
import java.awt.Frame;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import org.fourthline.cling.model.ModelUtil;
import org.seamless.swing.Application;
import org.seamless.xml.DOM;
import org.seamless.xml.DOMParser;
import org.w3c.dom.Document;

/* loaded from: classes5.dex */
public class TextExpandDialog extends JDialog {
    private static Logger log = Logger.getLogger(TextExpandDialog.class.getName());

    public TextExpandDialog(Frame frame, String str) {
        super(frame);
        setResizable(true);
        JTextArea jTextArea = new JTextArea();
        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        jScrollPane.setPreferredSize(new Dimension(500, 400));
        add(jScrollPane);
        if (str.startsWith("<") && str.endsWith(">")) {
            try {
                str = new DOMParser() { // from class: org.fourthline.cling.support.shared.TextExpandDialog.1
                    @Override // org.seamless.xml.DOMParser
                    protected DOM createDOM(Document document) {
                        return null;
                    }
                }.print(str, 2, false);
            } catch (Exception e) {
                Logger logger = log;
                logger.severe("Error pretty printing XML: " + e.toString());
            }
        } else if (str.startsWith("http-get")) {
            str = ModelUtil.commaToNewline(str);
        }
        jTextArea.setEditable(false);
        jTextArea.setText(str);
        pack();
        Application.center(this, getOwner());
        setVisible(true);
    }
}
