/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textpademo.Comandos;

import java.awt.Color;
import javax.swing.JColorChooser;
import textpademo.EditorTexto;

/**
 *
 * @author Jose Navarro Gregori/Óscar Ferrando Sanchis
 */
public class ComandoColorFuente extends Comando{

    public ComandoColorFuente() {
        super();
    }

    @Override
    public void ejecutar() {
        //presenta el dialogo de selección de colores
        Color color = JColorChooser.showDialog(EditorTexto.getInstancia().getJFrame(),
                                               "TextPad Demo - Color de letra:",
                                               EditorTexto.getInstancia().getJTextArea().getForeground());
        if (color != null) {    //si un color fue seleccionado
            //se establece como color del fuente y cursor
            EditorTexto.getInstancia().getJTextArea().setForeground(color);
            EditorTexto.getInstancia().getJTextArea().setCaretColor(color);
        }
    }
}
