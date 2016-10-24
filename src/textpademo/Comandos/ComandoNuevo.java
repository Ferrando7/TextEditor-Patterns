/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textpademo.Comandos;

import javax.swing.JOptionPane;
import textpademo.EditorTexto;
import textpademo.Plantillas.Plantilla;
import textpademo.Plantillas.PlantillaBlanca;

/**
 *
 * @author Jose Navarro Gregori/Óscar Ferrando Sanchis
 */
public class ComandoNuevo extends ComandoGuardar{

    public ComandoNuevo() {
        super();
    }

    @Override
    public void ejecutar() {
        if (EditorTexto.getInstancia().documentHasChanged() == true) {
            //si el documento esta marcado como modificado
            //le ofrece al usuario guardar los cambios
            boolean intentaGuardar = true;
            
            while(intentaGuardar){
                int option = JOptionPane.showConfirmDialog(EditorTexto.getInstancia().getJFrame(), "¿Desea guardar los cambios?");

                switch (option) {
                    case JOptionPane.YES_OPTION:       //si elige que si
                        super.ejecutar();   //guarda el archivo
                        break;
                    case JOptionPane.NO_OPTION:
                        intentaGuardar = false;
                        break;
                    case JOptionPane.CLOSED_OPTION:
                    case JOptionPane.CANCEL_OPTION:    //si elige cancelar
                        return;                        //cancela esta operación
                    //en otro caso se continúa con la operación y no se guarda el documento actual
                }
            }
        }
        
        
        /*
        * MOD. PATRÓN PLANTILLA
        */
        Plantilla plantilla;
        plantilla = new PlantillaBlanca();
        plantilla.newDocument();
        /*
        * FIN MOD.
        */
    }
    
}
