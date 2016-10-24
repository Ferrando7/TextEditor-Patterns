/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textpademo.Comandos;

import java.awt.Font;
import textpademo.EditorTexto;
import textpademo.JFontChooser;

/**
 *
 * @author Jose Navarro Gregori/Óscar Ferrando Sanchis
 */
public class ComandoFuente extends Comando{

    public ComandoFuente() {
        super();
    }

    @Override
    public void ejecutar() {
        //presenta el dialogo de selección de fuentes
        Font font = JFontChooser.showDialog(EditorTexto.getInstancia().getJFrame(),
                                            "TextPad Demo - Fuente de letra:",
                                            EditorTexto.getInstancia().getJTextArea().getFont());
        if (font != null) {    //si un fuente fue seleccionado
            //se establece como fuente del area de edición
            EditorTexto.getInstancia().getJTextArea().setFont(font);
        }
    }
}
