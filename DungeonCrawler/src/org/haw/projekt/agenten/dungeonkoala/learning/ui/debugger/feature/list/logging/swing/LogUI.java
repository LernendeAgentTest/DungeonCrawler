package org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.feature.list.logging.swing;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;

/**
 * The ui class for the log view
 */
public class LogUI {

    private JFrame _frame;
    private JTextArea _content;

    private static final int LOG_ROWS = 50;
    private static final int LOG_COLS = 61;

    public LogUI() {
        _frame = new JFrame("Feature Log");
        _content = new JTextArea(LOG_ROWS, LOG_COLS);
        _content.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(_content);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        DefaultCaret caret = (DefaultCaret) _content.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        _content.setLayout(new BoxLayout(_content, BoxLayout.PAGE_AXIS));
        _frame.setContentPane(scrollPane);
        _frame.pack();
    }

    /**
     * creates an new {@link JLabel} and appends it in the tab with the given {@link org.haw.projekt.agenten.dungeonkoala.learning.feature.Feature} name
     * @param message the new message
     */
    public void appendLogMessage(String message) {
    	if(_content.getLineCount() > 99) {
        	try {
    			int posOfLastLineToTrunk = _content.getLineEndOffset(0);
    			_content.replaceRange("",0,posOfLastLineToTrunk);
    		} catch (BadLocationException e) {
    			e.printStackTrace();
    		}
    	}
        _content.append(message + "\r\n");
    }


    /**
     * displays the ui
     * equals to {@code JFrame.setVisible(ture)}
     */
    public void show() {
        _frame.setVisible(true);
    }

    /**
     * hides the ui
     * equals to {@code JFrame.setVisible(false)}
     */
    public void hide() {
        _frame.setVisible(false);
    }
}
