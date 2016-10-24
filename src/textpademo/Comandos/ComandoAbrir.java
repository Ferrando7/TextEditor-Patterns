/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textpademo.Comandos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import textpademo.MetodosFicheros;
import textpademo.EditorTexto;

/**
 *
 * @author Jose Navarro Gregori/Óscar Ferrando Sanchis
 */
public class ComandoAbrir extends ComandoGuardar{

    public ComandoAbrir() {
        super();
    }

    @Override
    public void ejecutar() {
        if (EditorTexto.getInstancia().documentHasChanged() == true) {    //si el documento esta marcado como modificado
            //le ofrece al usuario guardar los cambios
            int option = JOptionPane.showConfirmDialog(EditorTexto.getInstancia().getJFrame(), "¿Desea guardar los cambios?");
 
            switch (option) {
                case JOptionPane.YES_OPTION:     //si elige que si
                    super.ejecutar(); //guarda el archivo
                    break;
                case JOptionPane.CLOSED_OPTION:
                case JOptionPane.CANCEL_OPTION:  //si elige cancelar
                    return;                      //cancela esta operación
                //en otro caso se continúa con la operación y no se guarda el documento actual
            }
        }
 
        JFileChooser fc = MetodosFicheros.getJFileChooser();    //obtiene un JFileChooser
 
        //presenta un dialogo modal para que el usuario seleccione un archivo
        int state = fc.showOpenDialog(EditorTexto.getInstancia().getJFrame());
 
        if (state == JFileChooser.APPROVE_OPTION) {    //si elige abrir el archivo
            File f = fc.getSelectedFile();    //obtiene el archivo seleccionado
 
            try ( //abre un flujo de datos desde el archivo seleccionado
                BufferedReader br = new BufferedReader(new FileReader(f))) {
                //lee desde el flujo de datos hacia el area de edición
                EditorTexto.getInstancia().getJTextArea().read(br, null);
                //cierra el flujo
            } catch (IOException ex) {    //en caso de que ocurra una excepción
                //presenta un dialogo modal con alguna información de la excepción
                JOptionPane.showMessageDialog(EditorTexto.getInstancia().getJFrame(),
                                              ex.getMessage(),
                                              ex.toString(),
                                              JOptionPane.ERROR_MESSAGE);
            }
            EditorTexto.getInstancia().getMemoriaDeshacer().limpiarListas();    //se limpia las listas de comandos que se pueden deshacer y rehacer
            EditorTexto.getInstancia().actualizarBotonesDeshacerYRehacer();          //se actualiza el estado de las opciones "Deshacer" y "Rehacer"
            //nuevo título de la ventana con el nombre del archivo cargado
            EditorTexto.getInstancia().getJFrame().setTitle("TextPad Demo - " + f.getName());
            //muestra la ubicación del archivo actual
            EditorTexto.getInstancia().getJLabelFilePath().setText(MetodosFicheros.shortPathName(f.getAbsolutePath()));
            //muestra el tamaño del archivo actual
            EditorTexto.getInstancia().getJLabelFileSize().setText(MetodosFicheros.roundFileSize(f.length()));
            //establece el archivo cargado como el archivo actual
            EditorTexto.getInstancia().setCurrentFile(f);
            //marca el estado del documento como no modificado
            EditorTexto.getInstancia().setDocumentChanged(false);
        }
    }
    
}
