package bitacora.subnivel;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Utilidades {
    private final static Logger LOGGER = Logger.getLogger("bitacora.subnivel.Utilidades");

    public void funcionDudosa(){
        LOGGER.log(Level.SEVERE, "ERROR MASIVO");
    }
}
