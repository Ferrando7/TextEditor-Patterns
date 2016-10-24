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
public class ComandoBuscarSiguiente extends ComandoBuscar{

    public ComandoBuscarSiguiente() {
        super();
    }

    @Override
    public void ejecutar() {
        if (EditorTexto.getInstancia().getLastSearch().length() > 0) {    //si la última búsqueda contiene texto
            String textAreaContent = EditorTexto.getInstancia().getJTextArea().getText();    //se obtiene todo el contenido del área de edición
            int pos = EditorTexto.getInstancia().getJTextArea().getCaretPosition();    //se obtiene la posición del cursor sobre el área de edición
            //buscando a partir desde la posición del cursor, se obtiene la posición de la primera ocurrencia del texto
            pos = textAreaContent.indexOf(EditorTexto.getInstancia().getLastSearch(), pos);
 
            if (pos > -1) {    //si la posición es mayor a -1 significa que la búsqueda fue positiva
                //selecciona el texto en el área de edición para resaltarlo
                EditorTexto.getInstancia().getJTextArea().select(pos, pos + EditorTexto.getInstancia().getLastSearch().length());
            }
        } else {    //si la última búsqueda no contiene nada
            super.ejecutar();    //invoca el metodo ejecutar el comando Buscar
        }
    }
}
