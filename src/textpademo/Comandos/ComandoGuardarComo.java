/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textpademo.Comandos;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import textpademo.MetodosFicheros;
import textpademo.EditorTexto;
import static textpademo.MetodosFicheros.roundFileSize;
import static textpademo.MetodosFicheros.shortPathName;

/**
 *
 * @author Jose Navarro Gregori/Óscar Ferrando Sanchis
 */
public class ComandoGuardarComo extends Comando{

    public ComandoGuardarComo() {
        super();
    }

    @Override
    public void ejecutar() {
         JFileChooser fc = MetodosFicheros.getJFileChooser();    //obtiene un JFileChooser
 
        //presenta un dialogo modal para que el usuario seleccione un archivo
        int state = fc.showSaveDialog(EditorTexto.getInstancia().getJFrame());
        if (state == JFileChooser.APPROVE_OPTION) {    //si elige guardar en el archivo
            File f = fc.getSelectedFile();    //obtiene el archivo seleccionado
            
            try {
                //abre un flujo de datos hacia el archivo asociado seleccionado
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));
                //escribe desde el flujo de datos hacia el archivo
                EditorTexto.getInstancia().getJTextArea().write(bw);
                bw.close();    //cierra el flujo
 
                //nuevo título de la ventana con el nombre del archivo guardado
                EditorTexto.getInstancia().getJFrame().setTitle("TextPad Demo - " + f.getName());
 
                //muestra la ubicación del archivo guardado
                EditorTexto.getInstancia().getJLabelFilePath().setText(shortPathName(f.getAbsolutePath()));
                //muestra el tamaño del archivo guardado
                EditorTexto.getInstancia().getJLabelFileSize().setText(roundFileSize(f.length()));
 
                //establece el archivo guardado como el archivo actual
                EditorTexto.getInstancia().setCurrentFile(f);
                //marca el estado del documento como no modificado
                EditorTexto.getInstancia().setDocumentChanged(false);
            } catch (IOException ex) {    //en caso de que ocurra una excepción
                //presenta un dialogo modal con alguna información de la excepción
                JOptionPane.showMessageDialog(EditorTexto.getInstancia().getJFrame(),
                                              ex.getMessage(),
                                              ex.toString(),
                                              JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
