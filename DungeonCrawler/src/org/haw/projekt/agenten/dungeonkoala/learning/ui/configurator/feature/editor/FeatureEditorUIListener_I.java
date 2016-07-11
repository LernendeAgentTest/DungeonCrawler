package org.haw.projekt.agenten.dungeonkoala.learning.ui.configurator.feature.editor;

import java.util.List;

import org.haw.projekt.agenten.dungeonkoala.learning.feature.features.AbstractDungeonKoalaFeature;


public interface FeatureEditorUIListener_I {
	/**
	 * Wird aufgerufen, wenn ein neues Feature hinzugefügt werden soll.
	 * @param features Eine Liste der hinzuzufügenden Features
	 */
	void onAddFeatures(List<AbstractDungeonKoalaFeature> features);
	
	/**
	 * Wird aufgerufen, wenn ein Feature entfernt werden soll.
	 * @param features Eine Liste der zu entfernenden Features
	 */
	void onRemoveFeatures(List<AbstractDungeonKoalaFeature> features);
}
