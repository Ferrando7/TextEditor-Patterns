/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textpademo.Comandos;

import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import textpademo.EditorTexto;

/**
 *
 * @author Jose Navarro Gregori/Óscar Ferrando Sanchis
 */
public class ComandoIrALinia extends Comando{

    public ComandoIrALinia() {
        super();
    }

    @Override
    public void ejecutar() {
        //solicita al usuario que introduzca el número de línea
        String line = JOptionPane.showInputDialog(
                EditorTexto.getInstancia().getJFrame(),
                "Número:",
                "TextPad Demo - Ir a la línea...",
                JOptionPane.QUESTION_MESSAGE);
 
        if (line != null && line.length() > 0) {    //si se introdujo un dato
            try {
                int pos = Integer.parseInt(line);    //el dato introducido se convierte en entero
 
                //si el número de línea esta dentro de los límites del área de texto
                if (pos >= 0 && pos <= EditorTexto.getInstancia().getJTextArea().getLineCount()) {
                    //posiciona el cursor en el inicio de la línea
                    EditorTexto.getInstancia().getJTextArea().setCaretPosition(EditorTexto.getInstancia().getJTextArea().getLineStartOffset(pos));
                }
            } catch (NumberFormatException | BadLocationException ex) {    //en caso de que ocurran excepciones
                System.err.println(ex);
            }
        }
    }
}
