/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textpademo.Comandos;

import textpademo.EditorTexto;
import textpademo.PrintAction;

/**
 *
 * @author Jose Navarro Gregori/Óscar Ferrando Sanchis
 */
public class ComandoImprimir extends Comando{

    public ComandoImprimir() {
        super();
    }

    @Override
    public void ejecutar() {
        boolean result = false;    //resultado de la impresión, por defecto es false
 
        //si el documento actual no esta vacío
        if (EditorTexto.getInstancia().getJTextArea().getText().trim().equals("") == false) {
            //invoca nuestra la clase PrintAction para presentar el dialogo de impresión
            result = PrintAction.print(EditorTexto.getInstancia().getJTextArea(), EditorTexto.getInstancia().getJFrame());
        }
    }
}
