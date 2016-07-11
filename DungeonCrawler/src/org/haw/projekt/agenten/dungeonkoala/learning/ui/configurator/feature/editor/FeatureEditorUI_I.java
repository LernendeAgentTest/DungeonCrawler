package org.haw.projekt.agenten.dungeonkoala.learning.ui.configurator.feature.editor;

import java.util.List;

import org.haw.projekt.agenten.dungeonkoala.learning.feature.features.AbstractDungeonKoalaFeature;


public interface FeatureEditorUI_I {
	/**
	 * Registriert einen Listener um dem hinzufügen/entfernen von Features zu lauschen.
	 * @param l der Listener
	 */
	void addFeatureEditorUIListener(FeatureEditorUIListener_I l);
	
	/**
	 * Entfernt einen Listener um nicht mehr auf Änderungen an Features zu lauschen.
	 * @param l der Listener
	 */
	void removeFeatureEditorUIListener(FeatureEditorUIListener_I l);
	
	
	void setActiveFeatures(List<AbstractDungeonKoalaFeature> features);
	
	
	List<AbstractDungeonKoalaFeature> getSelectedActiveFeatures();
	
	
	void setInactiveFeatures(List<AbstractDungeonKoalaFeature> features);
	
	
	List<AbstractDungeonKoalaFeature> getSelectedInactiveFeatures();
	
	/**
	 * Ermöglicht das ein- und ausschalten der Buttons auf der UI
	 * @param enabled {@code true} für enabled {@code false} für disabled
	 */
	void setEnabled(boolean enabled);
}
