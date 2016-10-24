/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textpademo.Comandos;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
import textpademo.EditorTexto;

/**
 *
 * @author Jose Navarro Gregori/Óscar Ferrando Sanchis
 */
public class ComandoGuardar extends ComandoGuardarComo{

    public ComandoGuardar() {
        super();
    }

    @Override
    public  void ejecutar() {
        if (EditorTexto.getInstancia().getCurrentFile() == null) {    //si no hay un archivo asociado al documento actual
            super.ejecutar();
        } else if (EditorTexto.getInstancia().documentHasChanged() == true) {    //si el documento esta marcado como modificado
            try {
                //abre un flujo de datos hacia el archivo asociado al documento actual
                BufferedWriter bw = new BufferedWriter(new FileWriter(EditorTexto.getInstancia().getCurrentFile()));
                //escribe desde el flujo de datos hacia el archivo
                EditorTexto.getInstancia().getJTextArea().write(bw);
                bw.close();    //cierra el flujo
 
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
