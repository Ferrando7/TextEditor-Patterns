/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textpademo.Comandos;

import textpademo.EditorTexto;
import textpademo.Memento.Memento;

/**
 *
 * @author Jose Navarro Gregori/Óscar Ferrando Sanchis
 */
public abstract class ComandoUndoable extends Comando{
    protected Memento memento;
    // Constructor
    public ComandoUndoable(){
    }
    
    public void deshacer(){
        Memento mementoRehacer = EditorTexto.getInstancia().getMemento();
        EditorTexto.getInstancia().setMemento(memento);
        memento = mementoRehacer;
    }
    public void rehacer(){
        Memento mementoDeshacer = EditorTexto.getInstancia().getMemento();
        EditorTexto.getInstancia().setMemento(memento);
        memento = mementoDeshacer;  
    }
}
