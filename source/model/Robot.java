package model;

import pathfinder.*;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import fr.tp.inf112.projects.canvas.model.*;


public class Robot extends MovingComponent {

	
	private static final long serialVersionUID = 1L;
	
    private List<Component> componentsToVisit;
    private int currentComponentIndex;
    
    private InterfaceFactoryPathFinder factoryPathFinder;
    private List<Position> currentPath;
    private int currentPathIndex;
    
    private static final int ROBOT_RADIUS = 10;
 
	//private boolean puckOn; pas utilisé

    private static final Color DEFAULT_COLOR = new DefaultColor();

    private static final Stroke DEFAULT_STROKE = new DefaultStroke();    

    public Robot() {
    	super(0, 0, null, getDefaultStyle(), getDefaultShape(), 0);
    }
    
    // Pour la gestion de la pause indépendante
    private long pauseStartTime = -1;
    private long pauseDuration = 500; // 0.5 seconde de pause sur une zone

    public Robot(int xCoordinate, 
    		int yCoordinate, 
    		String name, 
    		int timeDelay, 
    		List<Component> componentsToVisit,
    		FactoryToGraph factoryGraph) {
    	
        super(xCoordinate, yCoordinate, name, getDefaultStyle(), getDefaultShape(), timeDelay);
        //this.puckOn = false;
        this.currentComponentIndex = 0;
        this.currentPathIndex = 0;
        this.componentsToVisit = componentsToVisit;
        this.factoryPathFinder = new FactoryPathFinder(factoryGraph);
    }
    
    private static Style getDefaultStyle() {
        return new DefaultStyle();
    }

    private static OvalShape getDefaultShape() {
        return new DefaultOvalShape();
    }
    
    public void setComponentsToVisit(List<Component> componentsToVisit) {
        this.componentsToVisit = componentsToVisit;
    }
    //si la palette est sur le robot
    /*public void setPuckOn(boolean puckOn) {
        this.puckOn = puckOn;
        setFactoryNotify();
    }*/
    
    public int getRadius() {
    	return ROBOT_RADIUS;
    }
    
    @Override
    public Style getStyle() {
        return getDefaultStyle();
    }

    @Override
    public OvalShape getShape() {
        return getDefaultShape();
    }
    //calcule le chemin vers le prochain composant à visiter
    private void calculatePath() {
    	Component nextComponent = componentsToVisit.get(currentComponentIndex);
        currentPath = factoryPathFinder.findPath(this.getPosition(), nextComponent.getPosition());
        currentComponentIndex = (currentComponentIndex + 1) % componentsToVisit.size();
        currentPathIndex = 0;
    }

    //comportement du robot
    @Override
    public void behave() {
        if (componentsToVisit.isEmpty()) {
            return;
        }
        
        // Vérifie si le robot est sur une zone d'arrêt
        Component currentComponent = componentsToVisit.get((currentComponentIndex == 0 ? componentsToVisit.size() : currentComponentIndex) - 1);
        boolean onPauseArea = (currentComponent instanceof model.Area || currentComponent instanceof model.Booth)
            && this.getxCoordinate() == currentComponent.getPosition().getxCoordinate()
            && this.getyCoordinate() == currentComponent.getPosition().getyCoordinate();
        
        /* Affiche l'état du robot à chaque tick car j'ai l'impression les robots sont syncrhonisés
        mais apres vérif surement un bug d'affichage ?
        String etatPause;
        if (onPauseArea && (pauseStartTime > 0) && (System.currentTimeMillis() - pauseStartTime < pauseDuration)) {
            etatPause = "[EN PAUSE]";
        } else {
            etatPause = "[EN MOUVEMENT]";
        }
        System.out.println("[" + System.currentTimeMillis() + "] " + this.getName() + " x=" + this.getxCoordinate() + " y=" + this.getyCoordinate() + " " + etatPause);
        */
        if (onPauseArea) {
            if (pauseStartTime < 0) {
                pauseStartTime = System.currentTimeMillis();
            }
            if (System.currentTimeMillis() - pauseStartTime < pauseDuration) {
                // On attend (pause non-bloquante)
                return;
            } else {
                pauseStartTime = -1; // Fin de la pause
            }
        }
        if (currentPath == null || currentPathIndex >= currentPath.size()) {
            calculatePath();
        }
        if (currentPath != null && currentPathIndex < currentPath.size()) {
            Position nextPosition = currentPath.get(currentPathIndex);
            this.setxCoordinate(nextPosition.getxCoordinate());
            this.setyCoordinate(nextPosition.getyCoordinate());
            currentPathIndex++;
        }
    }



    //pour définir la couleur du robot en bleu
    private static class DefaultColor implements Color, Serializable {
    private static final long serialVersionUID = 1L;
    @Override
    public int getRedComponent() {
        return 0;
    }
    @Override
    public int getGreenComponent() {
        return 0;
    }
    @Override
    public int getBlueComponent() {
        return 139;
    }
}

    private static class DefaultStroke implements Stroke, Serializable {
    	
    	private static final long serialVersionUID = 1L;
    	@Override
        public Color getColor() {
            return DEFAULT_COLOR;
        }

        @Override
        public float getThickness() {
            return 2.0f;
        }

        @Override
        public float[] getDashPattern() {
            return null;
        }
    }
    // pour définir le style du robot
    private static class DefaultStyle implements Style, Serializable {
    	
    	private static final long serialVersionUID = 1L;
    	@Override
        public Color getBackgroundColor() {
            return DEFAULT_COLOR;
        }

        @Override
        public Stroke getStroke() {
            return DEFAULT_STROKE;
        }
    }
    // pour définir la forme ovale comme forme par défaut du robot
    private static class DefaultOvalShape implements OvalShape, Serializable {
    	
    	private static final long serialVersionUID = 1L;
    	@Override
        public int getWidth() {
            return ROBOT_RADIUS;
        }

        @Override
        public int getHeight() {
            return ROBOT_RADIUS;
        }
    }

	@Override
	public Set<Position> overlay() {
		return null;
	}
}
