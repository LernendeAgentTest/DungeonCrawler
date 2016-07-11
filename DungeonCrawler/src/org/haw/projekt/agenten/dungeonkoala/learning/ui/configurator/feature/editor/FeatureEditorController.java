package org.haw.projekt.agenten.dungeonkoala.learning.ui.configurator.feature.editor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.haw.projekt.agenten.dungeonkoala.learning.feature.features.AbstractDungeonKoalaFeature;

public class FeatureEditorController implements FeatureEditorUIListener_I {
	private FeatureEditorUI_I _ui;
	private List<AbstractDungeonKoalaFeature> _activeFeatures;
	private List<AbstractDungeonKoalaFeature> _inactiveFeatures;
	
	public FeatureEditorController(FeatureEditorUI_I ui, List<AbstractDungeonKoalaFeature> activeFeatures, List<AbstractDungeonKoalaFeature> inactiveFeatures) {
		// Sorgt dafür, dass das selbe Feature nicht in beiden Listen vorhanden ist.
		// Streng genommen müsste jedes Feature dafür aber eigentlich die Equals-Methode überschreiben.
		// So verlassen wir uns im Moment darauf, dass in den übergebenen Listen nur die Objekte aus
		// der DungeonKoalaFeatures-Klasse verwendet werden und nie selbst erzeugte Objekte enthalten.
		List<AbstractDungeonKoalaFeature> schnittmenge = new ArrayList<>(activeFeatures);
		schnittmenge.retainAll(inactiveFeatures);
		inactiveFeatures.removeAll(schnittmenge);
		
		_activeFeatures = Objects.requireNonNull(activeFeatures);
		_inactiveFeatures = Objects.requireNonNull(inactiveFeatures);
		
		_ui = Objects.requireNonNull(ui);
		_ui.addFeatureEditorUIListener(this);
		
		_ui.setActiveFeatures(_activeFeatures);
		_ui.setInactiveFeatures(_inactiveFeatures);
	}
	
	public List<AbstractDungeonKoalaFeature> getActiveFeatures() {
		return new ArrayList<AbstractDungeonKoalaFeature>(_activeFeatures);
	}
	
	public List<AbstractDungeonKoalaFeature> getInactiveFeatures() {
		return new ArrayList<AbstractDungeonKoalaFeature>(_inactiveFeatures);
	}
	
	public void setEnabled(boolean enable) {
		_ui.setEnabled(enable);
	}
	
	@Override
	public void onAddFeatures(List<AbstractDungeonKoalaFeature> features) {
		_activeFeatures.addAll(features);
		_inactiveFeatures.removeAll(features);
		
		_ui.setActiveFeatures(_activeFeatures);
		_ui.setInactiveFeatures(_inactiveFeatures);
	}
	
	@Override
	public void onRemoveFeatures(List<AbstractDungeonKoalaFeature> features) {
		_activeFeatures.removeAll(features);
		_inactiveFeatures.addAll(features);
		
		_ui.setActiveFeatures(_activeFeatures);
		_ui.setInactiveFeatures(_inactiveFeatures);
	}
}
