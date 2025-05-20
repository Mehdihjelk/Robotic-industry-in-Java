package test;

import fr.tp.inf112.projects.canvas.view.CanvasViewer;

import java.util.logging.Logger;
import java.util.Arrays;

import implement.*;
import controller.*;
import model.Factory;
import model.Robot;
import model.Component;



public class SimulatorApp {

	
	private static final Logger LOGGER = Logger.getLogger(SimulatorApp.class.getName());
	
	public static void main(String[] args) {
		
		// Configuration du logging
		//System.setProperty("java.util.logging.config.file", "config/logging.properties");
		LOGGER.info("Starting the robot simulator...");
        LOGGER.config("With parameters " + Arrays.toString(args) + ".");
        
        // Début de l'app
		Application app = new Application();
		SimulatorController controller = new SimulatorController(app.getFactory());
        CanvasViewer canvasViewer = new CanvasViewer(controller);
        controller.addObserver(canvasViewer);
        
        canvasViewer.show();



    }
	
}
