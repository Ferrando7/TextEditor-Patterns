
/**
 * ActionPerformer.java
 */

package textpademo;

import java.io.File;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
 
/**
 * Clase que ejecuta las operaciones solicitadas.
 * 
 * @author Dark[byte] modificado por Jose Navarro Gregori y Oscar Ferrando Sanchis
 */
public class MetodosFicheros {
    /**
     * Constructor de la clase.
     * 
     */
    public MetodosFicheros() {
    }
    /**
     * Retorna la instancia de un JFileChooser, con el cual se muestra un dialogo que permite
     * seleccionar un archivo.
     * 
     * @return un dialogo para seleccionar un archivo.
     */
    public static JFileChooser getJFileChooser() {
        JFileChooser fc = new JFileChooser();                     //construye un JFileChooser
        fc.setDialogTitle("TextPad Demo - Elige un archivo:");    //se le establece un título
        fc.setMultiSelectionEnabled(false);                       //desactiva la multi-selección
        fc.setFileFilter(textFileFilter);                         //aplica un filtro de extensiones
        return fc;    //retorna el JFileChooser
    }
 
    /**
     * Clase anónima interna que extiende la clase javax.swing.filechooser.FileFilter para 
     * establecer un filtro de archivos en el JFileChooser.
     */
    public static FileFilter textFileFilter = new FileFilter() {
 
        @Override
        public boolean accept(File f) {
            //acepta directorios y archivos de extensión .txt
            return f.isDirectory() || f.getName().toLowerCase().endsWith("txt");
        }
 
        @Override
        public String getDescription() {
            //la descripción del tipo de archivo aceptado
            return "Text Files";
        }
    };
 
    /**
     * Retorna la ruta de la ubicación de un archivo en forma reducida.
     * 
     * @param longPath la ruta de un archivo
     * @return la ruta reducida del archivo
     */
    public static String shortPathName(String longPath) {
        //construye un arreglo de cadenas, donde cada una es un nombre de directorio
        String[] tokens = longPath.split(Pattern.quote(File.separator));
 
        //construye un StringBuilder donde se añadirá el resultado
        StringBuilder shortpath = new StringBuilder();
 
        //itera sobre el arreglo de cadenas
        for (int i = 0 ; i < tokens.length ; i++) {
            if (i == tokens.length - 1) {              //si la cadena actual es la última, es el nombre del archivo
                shortpath.append(tokens[i]);    //añade al resultado sin separador
                break;                          //termina el bucle
            } else if (tokens[i].length() >= 10) {     //si la cadena actual tiene 10 o más caracteres
                //se toman los primeros 3 caracteres y se añade al resultado con un separador
                shortpath.append(tokens[i].substring(0, 3)).append("...").append(File.separator);
            } else {                                   //si la cadena actual tiene menos de 10 caracteres
                //añade al resultado con un separador
                shortpath.append(tokens[i]).append(File.separator);
            }
        }
 
        return shortpath.toString();    //retorna la cadena resultante
    }
 
    /**
     * Redondea la longitud de un archivo en KiloBytes si es necesario.
     * 
     * @param length longitud de un archivo
     * @return el tamaño redondeado  
     */
    public static String roundFileSize(long length) {
        //retorna el tamaño del archivo redondeado
        return (length < 1024) ? length + " bytes" : (length / 1024) + " Kbytes";
    }
}