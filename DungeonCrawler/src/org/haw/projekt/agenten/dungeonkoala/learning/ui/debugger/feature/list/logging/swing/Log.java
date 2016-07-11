package org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.feature.list.logging.swing;

import org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.feature.list.logging.Logger;
import org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.feature.list.logging.LoggerListener;

/**
 *  A log window displaying the previous feature states
 *  logged by the {@link Logger}
 */
public class Log {

    private LogUI _ui;
    private Logger _logger;

    /**
     * creates a new view and displays it
     * @param logger the logger used
     */
    public Log(Logger logger) {
        _ui = new LogUI();
        _logger = logger;
            for(String s : _logger.getLog()){
                _ui.appendLogMessage(s);
            }
        _logger.addListener(new LoggerListener() {
           	@Override
			public void onLog(String newLog) {
           		_ui.appendLogMessage(newLog);
			}
        });
    }
    
    public void show() {
        _ui.show();
    }
}
