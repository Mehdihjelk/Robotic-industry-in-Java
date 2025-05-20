package implement;

import model.*;
import pathfinder.FactoryToGraph;

import java.util.List;
import java.util.ArrayList;

// Ici on crée la classe Application qui crée et configure les différents éléments de l'usine: les pièces, les zones de stockage, les zones de chargement, les portes...
// Elle définit également le parcours que le robot doit suivre pour visiter les différents éléments de l'usine.

public class Application {

	private Factory factory;
	
    private Room room1, room2, room3, room4;
    private PackagingArea packagingArea1;
    private ChargingArea chargingArea1;
    private Area sortingArea1, stockDeliveryArea1, stock;
    private Booth booth1, booth2;
    private ChargingArea chargingArea2;
    private Door door1, door2, door3, door4;
    private Robot robot1;
    private Robot robot2;

    // liste des éléments à visiter par le robot 1.
    private List<Component> componentsToVisitByRobot1;
    // liste des éléments à visiter par le robot 2.
    private List<Component> componentsToVisitByRobot2;
    // Graphe de l'usine (permettant de trouver le chemin le plus court entre les éléments).
    private FactoryToGraph factoryGraph;

    public Application() {
    	
        factory = new Factory();
        factoryGraph = new FactoryToGraph(factory);
        
        componentsToVisitByRobot1 = new ArrayList<>();
        componentsToVisitByRobot2 = new ArrayList<>();
        
        room1 = new Room(10, 10, "Packaging Room", 90, 170);
        room2 = new Room(180, 10, "Sorting Room", 90, 210);
        room3 = new Room(10, 100, "Stock Room", 90, 220);
        room4 = new Room(230, 100, "Delivery Room", 90, 160);
        
        packagingArea1 = new PackagingArea(30, 40, "Packaging Area", 40, 40);
        chargingArea1 = new ChargingArea(155, 25, "Charging Point 1", 30, 20);
        sortingArea1 = new Area(220, 25, "Sorting Area", 40, 40);
        stockDeliveryArea1 = new Area(340, 100, "Stock Delivery Area", 50, 50);
        stock = new Area(15, 135, "Stock (St)", 50, 80);
        chargingArea2 = new ChargingArea(195, 105, "Charging Point 2", 20, 30);
        booth1 = new Booth(340, 10, "Booth I", 45, 40);
        booth2 = new Booth(340, 55, "Booth II", 45, 40);
       
        
        door1 = new Door(179, 65, "Porte 1", true);
        door2 = new Door(20, 99, "Porte 2", false);
        door3 = new Door(229, 150, "Porte 3", true);
        door4 = new Door(240, 99, "Porte 4", false);
      
        
        factory.addComponent(room1);
    	factory.addComponent(room2); 
    	factory.addComponent(room3); 
    	factory.addComponent(room4);
    	
    	factory.addComponent(packagingArea1);
    	factory.addComponent(chargingArea1);
    	factory.addComponent(sortingArea1);
    	factory.addComponent(stockDeliveryArea1);
    	factory.addComponent(stock);
    	factory.addComponent(chargingArea2);
        factory.addComponent(booth1);
    	factory.addComponent(booth2);
    	
        factory.addComponent(door1);
    	factory.addComponent(door2);
    	factory.addComponent(door3);
    	factory.addComponent(door4);
    	

    	componentsToVisitByRobot1.add(this.packagingArea1); 
    	componentsToVisitByRobot1.add(this.chargingArea1); 
    	componentsToVisitByRobot1.add(this.sortingArea1);      
    	componentsToVisitByRobot1.add(this.stockDeliveryArea1); 
    	componentsToVisitByRobot1.add(this.chargingArea2);     
    	componentsToVisitByRobot1.add(this.stock); 
        
    	robot1 = new Robot(15, 135, "Robot 1", 1, componentsToVisitByRobot1, factoryGraph);
        factory.addComponent(robot1);

        
        componentsToVisitByRobot2.add(this.sortingArea1);
        componentsToVisitByRobot2.add(this.booth1);
        componentsToVisitByRobot2.add(this.stockDeliveryArea1);
        componentsToVisitByRobot2.add(this.stock);
        robot2 = new Robot(220, 25, "Robot 2", 1, componentsToVisitByRobot2, factoryGraph); // position de départ = Sorting Area
        factory.addComponent(robot2);

    	
    }
	
	public Factory getFactory() {
		return this.factory;
	}
	
}
