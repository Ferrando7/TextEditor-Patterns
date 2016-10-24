/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textpademo.Comandos;

import javax.swing.JOptionPane;
import textpademo.EditorTexto;

/**
 *
 * @author Jose Navarro Gregori/Óscar Ferrando Sanchis
 */
public class ComandoSalir extends ComandoGuardar{

    public ComandoSalir() {
        super();
    }

    @Override
    public void ejecutar() {
        if (EditorTexto.getInstancia().documentHasChanged() == true) {    //si el documento esta marcado como modificado
            //le ofrece al usuario guardar los cambios
            int option = JOptionPane.showConfirmDialog(EditorTexto.getInstancia().getJFrame(), "¿Desea guardar los cambios?");
 
            switch (option) {
                case JOptionPane.YES_OPTION:     //si elige que si
                    super.ejecutar();            //guarda el archivo
                    break;
                case JOptionPane.CLOSED_OPTION:
                case JOptionPane.CANCEL_OPTION:  //si elige cancelar
                    return;                      //cancela esta operación
                //en otro caso se continúa con la operación y no se guarda el documento actual
            }
        }
 
        System.exit(0);    //finaliza el programa con el código 0 (sin errores)
    }
}
