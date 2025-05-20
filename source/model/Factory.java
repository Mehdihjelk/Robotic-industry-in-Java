package model;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import pathfinder.Position;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.io.Serializable;
import java.io.IOException;
import java.io.ObjectInputStream;

import fr.tp.inf112.projects.canvas.model.*;
import fr.tp.inf112.projects.canvas.controller.*;


public class Factory implements Canvas, Observable, Serializable {
	
	private static final Logger LOGGER = Logger.getLogger(Factory.class.getName());

	private static final int HEIGHT = 200;
	private static final int WIDTH = 400;
	private static final String NAME = "Factory";
    private String id;
	private static final Color DEFAULT_COLOR = null;
	private static final Stroke DEFAULT_STROKE = null;
	
	private List<Component> factoryComponents;
    private boolean simulationStarted;
	private transient List<Observer> factoryObservers;

    public Factory() {
        this.simulationStarted = false;
        this.factoryObservers = new ArrayList<>();
        this.factoryComponents = new ArrayList<>();
    }

    public boolean removeComponent(Component component) {
        return factoryComponents.remove(component);
    }
    
    public void addComponent(Component component) {
        factoryComponents.add(component);
    }

    public List<Component> getComponents() {
        return java.util.Collections.unmodifiableList(factoryComponents);
    }
    
    @Override
    public Collection<Figure> getFigures() {
        List<Figure> figures = new ArrayList<>();
        for (Component component : factoryComponents) {
            figures.add(component);
        }
        return figures;
    }
    @Override 
    public int getHeight() {
    	return HEIGHT;
    }

    @Override
    public int getWidth() {
    	return WIDTH;
    }
    @Override
    public String getName() {
    	return NAME;
    }
    
    @Override
    public Style getStyle (){
        return new Style() {
            
            @Override
            public Stroke getStroke() {
                return DEFAULT_STROKE;
            }
            @Override
            public Color getBackgroundColor() {
                return DEFAULT_COLOR;
            }

            
        };
    }
    
    
    //Fait agir chaque composant de l'usine et notifie les observateurs
     
    public void behave() {
        for (Component component : factoryComponents) {
            component.behave();
        }
        notifyObservers();
    }
    public boolean isSimulationStarted() {
        return simulationStarted;
    }

    public void startSimulation() {
        this.simulationStarted = true;
        notifyObservers();
    }


    public void stopSimulation() {
        this.simulationStarted = false;
        notifyObservers();
    }


    @Override
    public boolean addObserver(Observer observer) {
        return this.factoryObservers.add(observer);
    }

    @Override
    public boolean removeObserver(Observer observer) {
    	return this.factoryObservers.remove(observer);
    }

    //Pour notifier TOUS les observateurs que le modèle a changé!!
    public void notifyObservers() {
        for (Observer observer : this.factoryObservers) {
            observer.modelChanged();
        }
    }
    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    
    
    //Renvoie l'ensemble des positions qui recouvrent tous les composants de l'usine.
     
    public Set<Position> allOverlay() {   	
    	Set<Position> union = new HashSet<>();
		for(Component component : factoryComponents) {
			Set<Position> overlaySet = component.overlay();
	        if (overlaySet != null) {
	        	union.addAll(overlaySet);
	        }
		}
    	return union;
    }

    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
    ois.defaultReadObject();
    this.factoryObservers = new ArrayList<>(); 
    LOGGER.info("Factory désérialisée et liste des observateurs réinitialisée.");
    
    }
}
