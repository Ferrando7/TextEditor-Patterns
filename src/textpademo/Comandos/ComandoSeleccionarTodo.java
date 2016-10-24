/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textpademo.Comandos;

import textpademo.EditorTexto;

/**
 *
 * @author Jose Navarro Gregori/Óscar Ferrando Sanchis
 */
public class ComandoSeleccionarTodo extends Comando{

    public ComandoSeleccionarTodo() {
        super();
    }

    @Override
    public void ejecutar() {
        EditorTexto.getInstancia().getJTextArea().selectAll();
    }
    
}
