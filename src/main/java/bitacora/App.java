package bitacora;

import bitacora.subnivel.Control;
import bitacora.subnivel.Utilidades;
import bitacora.subnivel.under.InternalSys;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Clase principal del ejemplo del uso de la bitacora
 *
 */
public class App {
    // Preparamos el log para cada paquete del proyecto, esto con el fin de capturar cada log
    // que se genere e irlo pasando al nivel superior hasta que encuentren un handler que los
    // maneje
    private final static Logger LOG_RAIZ = Logger.getLogger("bitacora");
    private final static Logger LOG_SUBNIVEL = Logger.getLogger("bitacora.subnivel");
    private final static Logger LOG_UNDER = Logger.getLogger("bitacora.subnivel.under");
    // El log para ESTA clase en particular
    private final static Logger LOGGER = Logger.getLogger("bitacora.App");

    public static void main(String[] args) {
        try {
            // Preparamos el log para cada paquete del proyecto, esto con el fin de capturar cada log
            // que se genere e irlo pasando al nivel superior hasta que encuentren un handler que los
            // maneje

            Handler consoleHandler = new ConsoleHandler();

            // Con el manejador de archivo, indicamos el archivo donde se mandaran los logs
            // El segundo argumento controla si se sobre escribe el archivo o se agregan los logs al final
            // Para sobre escribir pase un true para agregar al final, false para sobre escribir
            // todo el archivo
            Handler fileHandler = new FileHandler("./bitacora.log", false);

            // El formateador indica como presentar los datos, en este caso usaremos el formaro sencillo
            // el cual es mas facil de leer, si no usamos esto el log estara en formato xml por defecto
            SimpleFormatter simpleFormatter = new SimpleFormatter();

            // Se especifica que formateador usara el manejador (handler) de archivo
            fileHandler.setFormatter(simpleFormatter);

            // Asignamos los handles previamente declarados al log *raiz* esto es muy importante ya que
            // permitira que los logs de todas y cada una de las clases del programa que esten en ese paquete
            // o sus subpaquetes se almacenen en el archivo y aparescan en consola
            LOG_RAIZ.addHandler(consoleHandler);
            LOG_RAIZ.addHandler(fileHandler);

            // Indicamos a partir de que nivel deseamos mostrar los logs, podemos especificar un nivel en especifico
            // para ignorar informacion que no necesitemos

            consoleHandler.setLevel(Level.ALL);
            fileHandler.setLevel(Level.ALL);

            LOGGER.log(Level.INFO, "Bitacora inicializada");

            // Creamos los objetos de las otras clases
            Utilidades util = new Utilidades();
            Control control = new Control();
            InternalSys internalSys = new InternalSys();

            // Estas llamadas se registraran en el log
            LOGGER.log(Level.INFO, "Llamadas a los componentes del sistema");

            util.funcionDudosa();
            control.controlar();
            internalSys.llamadaSistema();

            LOGGER.log(Level.INFO, "Probando manejo de excepciones");

            try {
                throw new Exception("ERROR DE CONTROL DE FLUJO DE PROGRAMA");
            } catch (Exception e) {

                // Mediante el metodo getStack obtenemos el stackTrace de la excepcion en forma de un objecto String
                // de modo que podamos almacenarlo en bitacora para su analisis posterior
                LOGGER.log(Level.SEVERE, App.getStackTrace(e));
            }

            LOGGER.log(Level.INFO, "Programa corrio exitosamente");

        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Error de IO");
        } catch (SecurityException ex) {
            LOGGER.log(Level.SEVERE, "Error de Seguridad");
        }
    }

    /**
    * Esta funcion nos permite convertir el stackTrace en un String, necesario para poder imprimirlos al log debido a
    * cambios en como Java los maneja internamente
    * @param e Excepcion de la que queremos el StackTrace
    * @return StackTrace de la excepcion en forma de String
    */
    public static String getStackTrace(Exception e) {
        StringWriter sWriter = new StringWriter();
        PrintWriter pWriter = new PrintWriter(sWriter);
        e.printStackTrace(pWriter);
        return sWriter.toString();
    }
}
