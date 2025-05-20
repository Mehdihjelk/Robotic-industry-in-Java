package model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import fr.tp.inf112.projects.canvas.model.*;
import pathfinder.Position;

public class Room extends StaticComponent {

	private static final long serialVersionUID = 1L;
	private int height;
    private int width;
    private static final Stroke DEFAULT_STROKE = new DefaultStroke();

    public Room() {
        super();
    }

    public Room(int xCoordinate, int yCoordinate, String name, int height, int width) {
        super(xCoordinate, yCoordinate, name, getDefaultStyle(), getDefaultShape(height, width));
        this.height = height;
        this.width = width;
    }

    private static Style getDefaultStyle() {
        return new DefaultStyle();
    }

    private static RectangleShape getDefaultShape(int height, int width) {
        return new DefaultRectangleShape(height, width);
    }

    @Override
    public Style getStyle() {
        return getDefaultStyle();
    }

    @Override
    public RectangleShape getShape() {
        return getDefaultShape(this.height, this.width);
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }
    
    @Override
    public Set<Position> overlay() {
    	Robot modelRobot = new Robot();
    	int xTopLeftCorner = this.getxCoordinate();
    	int yTopLeftCorner = this.getyCoordinate();
    	int xBottomRightCorner = this.getxCoordinate() + this.getWidth() - modelRobot.getRadius();
    	int yBottomRightCorner = this.getyCoordinate() + this.getHeight() - modelRobot.getRadius();
    	
    	Set<Position> allowedVertices = new HashSet<>();
    	
    	for(int x = xTopLeftCorner; x <= xBottomRightCorner; x++) {
    		for(int y = yTopLeftCorner; y <= yBottomRightCorner; y++) {
    			allowedVertices.add(new Position(x,y));
    		}
    	}
    	return allowedVertices;
    }
    
    private static class DefaultStroke implements Stroke, Serializable {
        
		private static final long serialVersionUID = 1L;

		@Override
        public Color getColor() {
            return null;
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

    private static class DefaultStyle implements Style, Serializable {

		private static final long serialVersionUID = 1L;

		@Override
        public Color getBackgroundColor() {
            return null;
        }

        @Override
        public Stroke getStroke() {
            return DEFAULT_STROKE;
        }
    }

    private static class DefaultRectangleShape implements RectangleShape, Serializable {
 
		private static final long serialVersionUID = 1L;
		private final int width;
        private final int height;

        DefaultRectangleShape(int height, int width) {
        	this.height = height;
            this.width = width;
        }

        @Override
        public int getWidth() {
            return this.width;
        }

        @Override
        public int getHeight() {
            return this.height;
        }
    }
}
