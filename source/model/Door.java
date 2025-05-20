package model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import fr.tp.inf112.projects.canvas.model.*;
import pathfinder.Position;



public class Door extends StaticComponent {

	private static final long serialVersionUID = 1L;
	private static final int HEIGHT = 30;
    private static final int WIDTH = 2;
    private boolean vertical;

    // Couleur rouge par défaut pour toutes les portes
    private static final Color DEFAULT_COLOR = new Color() {
        @Override
        public int getRedComponent() { return 255; }
        @Override
        public int getGreenComponent() { return 0; }
        @Override
        public int getBlueComponent() { return 0; }
    };
    
    public Door(int xCoordinate, int yCoordinate, String name, boolean vertical) {
    super(xCoordinate, yCoordinate, name, getDefaultStyle(), getDefaultShape(vertical));
    this.vertical = vertical;
}

private static Style getDefaultStyle() {
    return new DefaultStyle(DEFAULT_COLOR);
}
    
    private static RectangleShape getDefaultShape(boolean vertical) {
        return new DefaultRectangleShape(vertical);
    }

    
    @Override
    public RectangleShape getShape() {
        return getDefaultShape(this.vertical);
    }

    @Override
    public int getWidth() {
    	return Door.WIDTH;
    }
    
    @Override
    public int getHeight() {
    	return Door.HEIGHT;
    }
    
    @Override
    public Set<Position> overlay() {
    	Robot modelRobot = new Robot();
    	int xTopLeftCorner;
    	int yTopLeftCorner;
    	int xBottomRightCorner;
    	int yBottomRightCorner;
    	
    	if (!vertical) {
    		xTopLeftCorner = this.getxCoordinate();
            yTopLeftCorner = this.getyCoordinate() - modelRobot.getRadius();
            xBottomRightCorner = this.getxCoordinate() + this.getHeight() - modelRobot.getRadius();
            yBottomRightCorner = this.getyCoordinate() + this.getWidth();
    	} else{
    		xTopLeftCorner = this.getxCoordinate() - modelRobot.getRadius();
            yTopLeftCorner = this.getyCoordinate();
            xBottomRightCorner = this.getxCoordinate() + this.getWidth();
            yBottomRightCorner = this.getyCoordinate() + this.getHeight() - modelRobot.getRadius();
    	}
    	
    	Set<Position> allowedVertices = new HashSet<>();
    	
    	for(int x = xTopLeftCorner; x <= xBottomRightCorner; x++) {
    		for(int y = yTopLeftCorner; y <= yBottomRightCorner; y++) {
    			allowedVertices.add(new Position(x,y));
    		}
    	}	
    	return allowedVertices;
    }
    
    public Color getColor() {
        return DEFAULT_COLOR;
    }
    // Style par défaut pour la porte (couleur rouge)
    private static class DefaultStyle implements Style, Serializable {
        private static final long serialVersionUID = 1L;
        private final Color color;
        public DefaultStyle(Color color) { this.color = color; }
        @Override
        public Color getBackgroundColor() { return color; }
        @Override
        public Stroke getStroke() { return null; } // à personnaliser si besoin
    }

    //la porte est un rectangle par défaut
    private static class DefaultRectangleShape implements RectangleShape, Serializable {
      
    	private static final long serialVersionUID = 1L;
    	private final boolean vertical;

        DefaultRectangleShape(boolean vertical) {
            this.vertical = vertical;
        }

        @Override
        public int getWidth() {
            return this.vertical ? WIDTH : HEIGHT;
        }

        @Override
        public int getHeight() {
            return this.vertical ? HEIGHT : WIDTH;
        }
    }
}
