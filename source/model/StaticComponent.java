package model;

import fr.tp.inf112.projects.canvas.model.Shape;
import fr.tp.inf112.projects.canvas.model.Style;


public abstract class StaticComponent extends Component {

	private static final long serialVersionUID = 1L;

	public StaticComponent() {
		super();
	}


	public StaticComponent(int xCoordinate, int yCoordinate, String name, Style style, Shape shape) {
		super(xCoordinate, yCoordinate, name, style, shape);
	}

	public abstract int getWidth();
    
    public abstract int getHeight();
	
}
