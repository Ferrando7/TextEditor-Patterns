/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textpademo.Plantillas;

import textpademo.EditorTexto;

/**
 *
 * @author Óscar Ferrando Sanchis
 */
public abstract class Plantilla {

      public abstract void clearDoc();
      
      public  void newDocument(){
        EditorTexto.getInstancia().getJFrame().setTitle("Sin Título");    //nuevo título de la ventana

        clearDoc();
  
        EditorTexto.getInstancia().getMemoriaDeshacer().limpiarListas();    //limpia el buffer del administrador de edición
        EditorTexto.getInstancia().actualizarBotonesDeshacerYRehacer();          //actualiza el estado de las opciones "Deshacer" y "Rehacer"
 
        //el archivo asociado al documento actual se establece como null
        EditorTexto.getInstancia().setCurrentFile(null);
        //marca el estado del documento como no modificado
        EditorTexto.getInstancia().setDocumentChanged(false);
       
      }
    
}
