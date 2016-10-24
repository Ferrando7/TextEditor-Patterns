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
public class ComandoBuscar extends Comando{

    public ComandoBuscar() {
        super();
    }

    @Override
    public void ejecutar() {
        //solicita al usuario que introduzca el texto a buscar
        String text = JOptionPane.showInputDialog(
                EditorTexto.getInstancia().getJFrame(),
                "Texto:",
                "TextPad Demo - Buscar",
                JOptionPane.QUESTION_MESSAGE);
 
        if (text != null) {    //si se introdujo texto (puede ser una cadena vacía)
            String textAreaContent = EditorTexto.getInstancia().getJTextArea().getText();    //obtiene todo el contenido del área de edición
            int pos = textAreaContent.indexOf(text);    //obtiene la posición de la primera ocurrencia del texto
 
            if (pos > -1) {    //si la posición es mayor a -1 significa que la búsqueda fue positiva
                //selecciona el texto en el área de edición para resaltarlo
                EditorTexto.getInstancia().getJTextArea().select(pos, pos + text.length());
            }
 
            //establece el texto buscado como el texto de la última búsqueda realizada
            EditorTexto.getInstancia().setLastSearch(text);
        }
    }
    
}
