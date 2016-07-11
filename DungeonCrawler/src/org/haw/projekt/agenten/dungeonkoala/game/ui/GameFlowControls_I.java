package org.haw.projekt.agenten.dungeonkoala.game.ui;

public interface GameFlowControls_I {
	
	void addGameFlowControlsListener(GameFlowControlsListener_I listener);
	
	
	void removeGameFlowControlsListener(GameFlowControlsListener_I listener);
	
	
	int getCurrentDuration();
	
	
	void setCurrentDuration(int durationMS);
	
	
	boolean isPausePressed();
	
	
	void setPausePressed(boolean pause);
	
	
	boolean isDurationSliderEnabled();
	
	
	void setDurationSliderEnabled(boolean enable);
	
	
	boolean isPauseEnabled();
	
	
	void setPauseEnabled(boolean enable);
	
	
	boolean isContinueEnabled();
	
	
	void setContinueEnabled(boolean enable);
	
}
