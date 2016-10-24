/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textpademo.Comandos;

import javax.swing.JButton;
import textpademo.EditorTexto;

/**
 *
 * @author Jose Navarro Gregori/Óscar Ferrando Sanchis
 */
public class BotonEmisor extends JButton implements Emisor{
    public Comando comando;
    
    @Override
    public void setComando(Comando comando){
        this.comando = comando;
    }
    
    @Override
    public void ejecutarComando(){
        EditorTexto.getInstancia().getMemoriaDeshacer().ejecutar(comando);
    }    
}
