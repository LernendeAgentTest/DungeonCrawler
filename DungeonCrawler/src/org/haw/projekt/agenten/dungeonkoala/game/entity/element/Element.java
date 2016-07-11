package org.haw.projekt.agenten.dungeonkoala.game.entity.element;

import javax.swing.ImageIcon;

public enum Element{
	
	WATER {
		@Override
		public double versusElement(Element element) {
			switch(element) {
				case FIRE:		return EFFECTIVE;
				case GRASS:		return NOT_EFFECTIVE;
				default:		return NORMAL;
			}
		}

		@Override
		public String getElementButtonImage() {
			return BLUE_BUTTON_IMAGE;
		}

		@Override
		public String getElementAttackImage(int energy) {
			switch (energy){
				case 1: return WATER_ATTACK_LEVEL_1_IMAGE; 
				case 2: return WATER_ATTACK_LEVEL_2_IMAGE;
				case 3: return WATER_ATTACK_LEVEL_3_IMAGE;
				default: return WATER_ATTACK_LEVEL_0_IMAGE;
			}
		}

		@Override
		public String getElementBlockImage() {
			return WATERSHIELD_ICON;
		}

		@Override
		public ImageIcon getElementIcon() {
			return new ImageIcon(getClass().getClassLoader().getResource(ICON_ELEMENT_WATER));
		}
	},
	
	FIRE {
		@Override
		public double versusElement(Element element) {
			switch(element) {
				case GRASS:		return EFFECTIVE;
				case WATER: 	return NOT_EFFECTIVE;
				default:		return NORMAL;
			}
		}

		@Override
		public String getElementButtonImage() {
			return RED_BUTTON_IMAGE;
		}

		@Override
		public String getElementAttackImage(int energy) {
			switch (energy){
				case 1: return FIRE_ATTACK_LEVEL_1_IMAGE;
				case 2: return FIRE_ATTACK_LEVEL_2_IMAGE;
				case 3: return FIRE_ATTACK_LEVEL_3_IMAGE;
				default: return FIRE_ATTACK_LEVEL_0_IMAGE;
			}
		}

		@Override
		public String getElementBlockImage() {
			return FIRESHIELD_ICON;
		}

		@Override
		public ImageIcon getElementIcon() {
			return new ImageIcon(getClass().getClassLoader().getResource(ICON_ELEMENT_FIRE));
		}
	},
	
	GRASS {
		@Override
		public double versusElement(Element element) {
			switch(element) {
				case WATER: 	return EFFECTIVE;
				case FIRE:		return NOT_EFFECTIVE;
				default:		return NORMAL;
			}
		}

		@Override
		public String getElementButtonImage() {
			return GREEN_BUTTON_IMAGE;
		}

		@Override
		public String getElementAttackImage(int energy) {
			switch (energy){
				case 1: return GRASS_ATTACK_LEVEL_1_IMAGE; 
				case 2: return GRASS_ATTACK_LEVEL_2_IMAGE;
				case 3: return GRASS_ATTACK_LEVEL_3_IMAGE;
				default: return GRASS_ATTACK_LEVEL_0_IMAGE;
			}
		}

		@Override
		public String getElementBlockImage() {
			return GRASSSHIELD_ICON;
		}

		@Override
		public ImageIcon getElementIcon() {
			return new ImageIcon(getClass().getClassLoader().getResource(ICON_ELEMENT_GRASS));
		}
		
	};
	
	/**
	 * @param welche Element eingeben
	 * @return faktor: 1 = normal, 0.5 = halb verschwächen, 2.0 = doppelt verstärken
	 */
	public abstract double versusElement(Element element);
	
	public abstract String getElementButtonImage();
	
	public abstract String getElementAttackImage(int energy);
	
	public abstract String getElementBlockImage();
	
	public abstract ImageIcon getElementIcon();
	
	public static int ALL_ELEMENTS = Element.values().length;
	
	public static double NOT_EFFECTIVE = 0.5;
	public static double NORMAL = 1.0;
	public static double EFFECTIVE = 2.0;
	
	public static double NOT_EFFECTIVE_FOR_ROOM = 0.8;
	public static double NORMAL_FOR_ROOM = 1.0;
	public static double EFFECTIVE_FOR_ROOM = 1.2;
	
	private static final String BLUE_BUTTON_IMAGE ="res/img/buttons/blueButton.png";
	private static final String RED_BUTTON_IMAGE ="res/img/buttons/redButton.png";
	private static final String GREEN_BUTTON_IMAGE ="res/img/buttons/greenButton.png";
	
	private static final String FIRE_ATTACK_LEVEL_0_IMAGE = "img/actions/FireAttackLevel0.png";
	private static final String FIRE_ATTACK_LEVEL_1_IMAGE = "img/actions/FireAttackLevel1.png";
	private static final String FIRE_ATTACK_LEVEL_2_IMAGE = "img/actions/FireAttackLevel2.png";
	private static final String FIRE_ATTACK_LEVEL_3_IMAGE = "img/actions/FireAttackLevel3.png";
	
	private static final String WATER_ATTACK_LEVEL_0_IMAGE = "img/actions/WaterAttackLevel0.png";
	private static final String WATER_ATTACK_LEVEL_1_IMAGE = "img/actions/WaterAttackLevel1.png";
	private static final String WATER_ATTACK_LEVEL_2_IMAGE = "img/actions/WaterAttackLevel2.png";
	private static final String WATER_ATTACK_LEVEL_3_IMAGE = "img/actions/WaterAttackLevel3.png";
	
	private static final String GRASS_ATTACK_LEVEL_0_IMAGE = "img/actions/GrassAttackLevel0.png";
	private static final String GRASS_ATTACK_LEVEL_1_IMAGE = "img/actions/GrassAttackLevel1.png";
	private static final String GRASS_ATTACK_LEVEL_2_IMAGE = "img/actions/GrassAttackLevel2.png";
	private static final String GRASS_ATTACK_LEVEL_3_IMAGE = "img/actions/GrassAttackLevel3.png";
	
	private static final String ICON_ELEMENT_FIRE = "img/entities/elements/fire.png";
	private static final String ICON_ELEMENT_WATER = "img/entities/elements/water.png";
	private static final String ICON_ELEMENT_GRASS = "img/entities/elements/grass.png";
	
	private static final String FIRESHIELD_ICON = "img/actions/fireshield.png";
	private static final String WATERSHIELD_ICON = "img/actions/watershield.png";
	private static final String GRASSSHIELD_ICON = "img/actions/grassshield.png";
	

	public static Element getRandomElement() {
		Element elementTyp = null;
		double autoChooseElement = Math.random();

		if (autoChooseElement < 1.0 / ALL_ELEMENTS) {
			elementTyp = Element.FIRE;
		} else if (autoChooseElement < 2.0 / ALL_ELEMENTS) {
			elementTyp = Element.WATER;
		} else if (autoChooseElement < 3.0 / ALL_ELEMENTS) {
			elementTyp = Element.GRASS;
		}
		return elementTyp;
	}
}