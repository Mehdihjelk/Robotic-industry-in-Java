package pathfinder;

import java.io.Serializable;
import java.util.Objects;


public class Position implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int xCoordinate;
	private int yCoordinate;
	
	
	public Position(int xCoordinate, int yCoordinate) {
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
	}
	
	public int getxCoordinate() {
		return xCoordinate;
	}
	
	public void setxCoordinate(int xCoordinate) {
		this.xCoordinate = xCoordinate;
	}
	
	public int getyCoordinate() {
		return yCoordinate;
	}
	
	public void setyCoordinate(int yCoordinate) {
		this.yCoordinate = yCoordinate;
	}
	
	@Override
    public String toString() {
        return "Position{" + "xCoordinate=" + xCoordinate +", yCoordinate=" + yCoordinate +'}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
		Position position = (Position) o;

        if (xCoordinate != position.xCoordinate) 
			return false;
		
        if (o == null || getClass() != o.getClass()) 
			return false;

        return yCoordinate == position.yCoordinate;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(xCoordinate, yCoordinate);
    }
	
}
