/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import textpademo.Comandos.ComandoCortar;
import textpademo.Comandos.ComandoNuevo;
import textpademo.Comandos.ComandoPegar;
import textpademo.EditorTexto;

/**
 *
 * @author Jose Navarro Gregori/Óscar Ferrando Sanchis
 */
public class TestsEditorTexto {
    
    private EditorTexto editor;
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @Before
    public void setUp() {
        editor = EditorTexto.getInstancia();
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void validarQuePuedaDeshacerseAlCortarTexto() {
        editor.getMemoriaDeshacer().ejecutar(new ComandoCortar());
        assertTrue(editor.getMemoriaDeshacer().sePuedeDeshacer());
    }
    
    @Test
    public void validarFuncionamientoComandoMemento(){
	String estadoAntes = editor.getMemento().getEstado();
	editor.getMemoriaDeshacer().ejecutar(new ComandoCortar());
	editor.getMemoriaDeshacer().deshacer();
	String estadoDespues = editor.getMemento().getEstado();
	assertTrue(estadoAntes.equals(estadoDespues));
    }
    
    @Test
    public void validarQuePuedaRehacerseAlDeshacer() {
        editor.getMemoriaDeshacer().ejecutar(new ComandoCortar());
        editor.getMemoriaDeshacer().deshacer();
        assertTrue(editor.getMemoriaDeshacer().sePuedeRehacer());
    }
    
    @Test
    public void validarPatronSingleton(){
        EditorTexto instancia2 = EditorTexto.getInstancia();
        assertNotNull(editor);
        assertNotNull(instancia2);
        assertTrue(editor.equals(instancia2));
    }
    
    @Test
    public void valiarNuevoDocumento(){
        editor.getJTextArea().setText("Prueba");
        assertTrue(!editor.getJTextArea().getText().equals(""));
        editor.getMemoriaDeshacer().ejecutar(new ComandoPegar());
        editor.getMemoriaDeshacer().ejecutar(new ComandoPegar());
        editor.getMemoriaDeshacer().deshacer();
        //Nos preguntará si queremos guardar el fichero ya que no estará vacio
        editor.getMemoriaDeshacer().ejecutar(new ComandoNuevo());
        assertTrue(editor.getJTextArea().getText().equals(""));
        assertTrue(!editor.getMemoriaDeshacer().sePuedeDeshacer());
        assertTrue(!editor.getMemoriaDeshacer().sePuedeRehacer());
    }
}
