package model;


public class PackagingArea extends Area {

	public PackagingArea() {
		super();
	}
	//pour creer un packagagingarea deja positionée nommée et dimensionnée...
	public PackagingArea(int xCoordinate, int yCoordinate, String name, int height, int width) {
		super(xCoordinate, yCoordinate, name, height, width);
	}

}
