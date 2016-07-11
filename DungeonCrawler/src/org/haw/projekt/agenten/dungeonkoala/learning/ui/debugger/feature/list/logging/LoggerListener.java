package org.haw.projekt.agenten.dungeonkoala.learning.ui.debugger.feature.list.logging;

import org.haw.projekt.agenten.dungeonkoala.learning.feature.features.AbstractDungeonKoalaFeature;

/**
 * Listener interface zum lauschen an dem {@link Logger}
 */
public interface LoggerListener {
    /**
     * wird aufgerufen wenn eine neue Log nachricht kommt
     * @param newLog die veränderungen des {@link AbstractDungeonKoalaFeature}'s
     */
    void onLog(String newLog);
}
