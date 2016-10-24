/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textpademo.Memento;

import java.util.LinkedList;
import textpademo.Comandos.Comando;
import textpademo.Comandos.ComandoUndoable;

/**
 *
 * @author Jose Navarro Gregori/Óscar Ferrando Sanchis
 */
public class ListaDeComandosUndoable {
   private final LinkedList<ComandoUndoable> comandoStack = new LinkedList<>();
   private final LinkedList<ComandoUndoable> rehacerStack = new LinkedList<>();
    
   public void ejecutar(Comando comando) {
      comando.ejecutar();
      //Ejecutamos todos los comandos pero solo añadimos a la lista enlazada los que sean comandos que puedan deshacerse
      if(comando instanceof ComandoUndoable){
        comandoStack.addFirst((ComandoUndoable) comando);
        rehacerStack.clear();
      }
   }

   public void deshacer() {
      if (comandoStack.isEmpty())
         return;
      ComandoUndoable comando = comandoStack.removeFirst();
      System.out.println("Deshaciendo");
      comando.deshacer();
      rehacerStack.addFirst(comando);
   }

   public void rehacer() {
      if (rehacerStack.isEmpty())
         return;
      ComandoUndoable comando = rehacerStack.removeFirst();
      System.out.println("Rehaciendo");
      comando.rehacer();
      comandoStack.addFirst(comando);
   }
   
   public boolean sePuedeDeshacer(){
       return !comandoStack.isEmpty();
   }
   
   public boolean sePuedeRehacer(){
       return !rehacerStack.isEmpty();
   }
   
   public void limpiarListas(){
       comandoStack.clear();
       rehacerStack.clear();
   }
}
