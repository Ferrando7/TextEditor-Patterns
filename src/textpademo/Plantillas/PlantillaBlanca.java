/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textpademo.Plantillas;

import textpademo.EditorTexto;

/**
 *
 * @author Jose Navarro Gregori/Óscar Ferrando Sanchis
 */
public class PlantillaBlanca extends Plantilla{

    @Override
    public void clearDoc() {
        //limpia el contenido del area de edición
        EditorTexto.getInstancia().getJTextArea().setText("");
        //limpia el contenido de las etiquetas en la barra de estado
        EditorTexto.getInstancia().getJLabelFilePath().setText("");
        EditorTexto.getInstancia().getJLabelFileSize().setText("");
    }
  
}
