package persistence;

import fr.tp.inf112.projects.canvas.model.impl.AbstractCanvasPersistenceManager;
import fr.tp.inf112.projects.canvas.model.CanvasChooser;
import fr.tp.inf112.projects.canvas.model.Canvas;

import java.io.*;


public class FileCanvasPersistenceManager extends AbstractCanvasPersistenceManager {

    public FileCanvasPersistenceManager(CanvasChooser chooser) {
        super(chooser);
    }

    //Lecture d'un canvas à partir d'un fichier identifié par son ID
     
    @Override
    public Canvas read(String canvasId) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(canvasId))) {
            return (Canvas) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException("Class has not been found while reading canvas", e);
        }
    }
    
    //Suppression d'un canvas identifié par son ID

    @Override
    public boolean delete(Canvas canvasModel) throws IOException {
        File file = new File(canvasModel.getId());
        return file.delete();
    }
    //Persistance d'un canvas dans un fichier identifié par son ID

    @Override
    public void persist(Canvas canvasModel) throws IOException {
        String canvasId = canvasModel.getId();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(canvasId))) {
        	oos.writeObject(canvasModel);
        }
    }
}
