package bitacora.subnivel;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Control {
    private final static Logger LOGGER = Logger.getLogger("bitacora.subnivel.Control");
    
    public void controlar(){
        LOGGER.log(Level.INFO, "Proceso exitoso");
    }

}