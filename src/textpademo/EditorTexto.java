
/**
 * TPEditor.java
 *
 * Ejemplo de un editor básico para documentos de texto plano utilizando la biblioteca gráfica Swing.
 * Funciona desde Java SE 5.0 en adelante.
 */

package textpademo;

import textpademo.Comandos.BotonEmisor;
import textpademo.Comandos.MenuItemEmisor;
import textpademo.Comandos.Emisor;
import textpademo.Comandos.ComandoGuardarComo;
import textpademo.Comandos.ComandoAbrir;
import textpademo.Comandos.ComandoNuevo;
import textpademo.Comandos.ComandoSeleccionarTodo;
import textpademo.Comandos.ComandoBuscarSiguiente;
import textpademo.Comandos.Comando;
import textpademo.Comandos.ComandoCortar;
import textpademo.Comandos.ComandoImprimir;
import textpademo.Comandos.ComandoColorFondo;
import textpademo.Comandos.ComandoIrALinia;
import textpademo.Comandos.ComandoGuardar;
import textpademo.Comandos.ComandoPegar;
import textpademo.Comandos.ComandoFuente;
import textpademo.Comandos.ComandoSalir;
import textpademo.Comandos.ComandoBuscar;
import textpademo.Comandos.ComandoColorFuente;
import textpademo.Comandos.ComandoCopiar;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import textpademo.Memento.ListaDeComandosUndoable;
import textpademo.Memento.Memento;
 
/**
 * Clase principal donde se construye la GUI del editor.
 *
 * @author Dark[byte] modificado por Jose Navarro Gregori y Oscar Ferrando Sanchis
 */
public class EditorTexto {
    //REFACTORING
    private String lastSearch;     //la última búsqueda de texto realizada, por defecto no contiene nada
    
    /*
    * MODIFICACIÓN PATRÓN SINGLETON
    */
    private static EditorTexto instancia;

    /*
    * FIN MOD.
    */
    private final JFrame jFrame;            //instancia de JFrame (ventana principal)
    private JMenuBar jMenuBar;        //instancia de JMenuBar (barra de menú)
    private JToolBar jToolBar;        //instancia de JToolBar (barra de herramientas)
    private JTextArea jTextArea;      //instancia de JTextArea (área de edición)
    private JPopupMenu jPopupMenu;    //instancia de JPopupMenu (menú emergente)
    private JPanel statusBar;         //instancia de JPanel (barra de estado)
 
    private JCheckBoxMenuItem itemLineWrap;         //instancias de algunos items de menú que necesitan ser accesibles
    private JCheckBoxMenuItem itemShowToolBar;
    private JCheckBoxMenuItem itemFixedToolBar;
    private JCheckBoxMenuItem itemShowStatusBar;
    private JMenuItem mbItemUndo;
    private JMenuItem mbItemRedo;
    private JMenuItem mpItemUndo;
    private JMenuItem mpItemRedo;
 
    private JButton buttonUndo;    //instancias de algunos botones que necesitan ser accesibles
    private JButton buttonRedo;
 
    private JLabel sbFilePath;    //etiqueta que muestra la ubicación del archivo actual
    private JLabel sbFileSize;    //etiqueta que muestra el tamaño del archivo actual
    private JLabel sbCaretPos;    //etiqueta que muestra la posición del cursor en el área de edición
 
    private boolean hasChanged = false;    //el estado del documento actual, no modificado por defecto
    private File currentFile = null;       //el archivo actual, ninguno por defecto
 
    private final EventHandler eventHandler;          //instancia de EventHandler (la clase que maneja eventos)
    
    /*
    * MODIFICACIÓN PARA PATRÓN MEMENTO
    */
    private final ListaDeComandosUndoable memoriaDeshacer;
    /*
    * FIN MOD.
    */
    
    /**
     * MODIFICACIÓN PARA PATRÓN COMANDO
     */
    private final Comando comandoCopiar;
    private final Comando comandoNuevo;
    private final Comando comandoAbrir;
    private final Comando comandoGuardar;
    private final Comando comandoGuardarComo;
    private final Comando comandoImprimir;
    private final Comando comandoSalir;
    private final Comando comandoBuscar;
    private final Comando comandoBuscarSiguiente;
    private final Comando comandoIrALinia;
    private final Comando comandoFuente;
    private final Comando comandoColorFuente;
    private final Comando comandoColorFondo;
    private final Comando comandoSeleccionarTodo;
    /**
     * FIN MOD.
     */
    
    /*
    * MOD. PATRÓN SINGLETON
    */
    private EditorTexto(){
        try {    //LookAndFeel nativo
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            System.err.println(ex);
        }
 
        //construye un JFrame con título
        jFrame = new JFrame("TextPad Demo - Sin Título");
        jFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
 
        //asigna un manejador de eventos para el cierre del JFrame
        jFrame.addWindowListener(new WindowAdapter() {
 
            @Override
            public void windowClosing(WindowEvent we) {
                comandoSalir.ejecutar();
            }
        });
 
        eventHandler = new EventHandler();              //construye una instancia de EventHandler
        
        /*
        * MODIFICACIÓN PARA PATRÓN MEMENTO
        */
        memoriaDeshacer = new ListaDeComandosUndoable();
        /*
        * FIN MOD.
        */
        
        /**
        * MODIFICACIÓN PARA PATRÓN COMANDO
        */
        
        comandoCopiar = new ComandoCopiar();
        comandoNuevo = new ComandoNuevo();
        comandoAbrir = new ComandoAbrir();
        comandoGuardar = new ComandoGuardar();
        comandoGuardarComo = new ComandoGuardarComo();
        comandoImprimir = new ComandoImprimir();
        comandoSalir = new ComandoSalir();
        comandoBuscar = new ComandoBuscar();
        comandoBuscarSiguiente = new ComandoBuscarSiguiente();
        comandoIrALinia = new ComandoIrALinia();
        comandoFuente = new ComandoFuente();
        comandoColorFuente = new ComandoColorFuente();
        comandoColorFondo = new ComandoColorFondo();
        comandoSeleccionarTodo = new ComandoSeleccionarTodo();
        /**
        * FIN MODIFICACIÓN
        */
        
        
        buildTextArea();     //construye el área de edición, es importante que esta sea la primera parte en construirse
        buildMenuBar();      //construye la barra de menú
        buildToolBar();      //construye la barra de herramientas
        buildStatusBar();    //construye la barra de estado
        buildPopupMenu();    //construye el menú emergente
 
        jFrame.setJMenuBar(jMenuBar);                              //designa la barra de menú del JFrame
        Container c = jFrame.getContentPane();                     //obtiene el contendor principal
        c.add(jToolBar, BorderLayout.NORTH);                       //añade la barra de herramientas, orientación NORTE del contendor
        c.add(new JScrollPane(jTextArea), BorderLayout.CENTER);    //añade el area de edición en el CENTRO
        c.add(statusBar, BorderLayout.SOUTH);                      //añade la barra de estado, orientación SUR
 
        //configura el JFrame con un tamaño inicial proporcionado con respecto a la pantalla
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        jFrame.setSize(pantalla.width / 2, pantalla.height / 2);
 
        //centra el JFrame en pantalla
        jFrame.setLocationRelativeTo(null);
        lastSearch = "";
    }
 
    public static EditorTexto getInstancia(){
      if (instancia == null){
        instancia = new EditorTexto();
      }
      return instancia;
    }
    /*
    * FIN MOD.
    */
    
    /**
     * Punto de entrada del programa.
     *
     * Instanciamos esta clase para construir la GUI y hacerla visible.
     *
     * @param args argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        //construye la GUI en el EDT (Event Dispatch Thread)
        javax.swing.SwingUtilities.invokeLater(() -> {
            EditorTexto.getInstancia().jFrame.setVisible(true);
        });
    }
    /**
     * Construye el área de edición.
     */
    private void buildTextArea() {
        jTextArea = new JTextArea();    //construye un JTextArea
 
        //se configura por defecto para que se ajusten las líneas al tamaño del área de texto ...
        jTextArea.setLineWrap(true);
        //... y que se respete la integridad de las palaras en el ajuste
        jTextArea.setWrapStyleWord(true);
 
        //asigna el manejador de eventos para el cursor
        jTextArea.addCaretListener(eventHandler);
        //asigna el manejador de eventos para el ratón
        jTextArea.addMouseListener(eventHandler);
        //asigna el manejador de eventos para registrar los cambios sobre el documento
        jTextArea.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyPressed(KeyEvent e) {
                hasChanged = true;
            }
            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
 
        //remueve las posibles combinaciones de teclas asociadas por defecto con el JTextArea
        jTextArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK), "none");    //remueve CTRL + X ("Cortar")
        jTextArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK), "none");    //remueve CTRL + C ("Copiar")
        jTextArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK), "none");    //remueve CTRL + V ("Pegar")
    }
 
    /**
     * Construye la barra de menú.
     */
    private void buildMenuBar() {
        jMenuBar = new JMenuBar();    //construye un JMenuBar
 
        //construye el menú "Archivo", a continuación se construyen los items para este menú
        JMenu menuFile = new JMenu("Archivo");
 
        //construye el item "Nuevo"
        MenuItemEmisor itemNew = new MenuItemEmisor("Nuevo");
        //le asigna una conbinación de teclas
        itemNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK));
        //le asigna un nombre de comando
        itemNew.setActionCommand("cmd_new");
        itemNew.setComando(comandoNuevo);
 
        MenuItemEmisor itemOpen = new MenuItemEmisor("Abrir");
        itemOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK));
        itemOpen.setActionCommand("cmd_open");
        itemOpen.setComando(comandoAbrir);
 
        MenuItemEmisor itemSave = new MenuItemEmisor("Guardar");
        itemSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK));
        itemSave.setActionCommand("cmd_save");
        itemSave.setComando(comandoGuardar);
 
        MenuItemEmisor itemSaveAs = new MenuItemEmisor("Guardar como...");
        itemSaveAs.setActionCommand("cmd_saveas");
        itemSaveAs.addActionListener(eventHandler);
        itemSaveAs.setComando(comandoGuardarComo);
 
        MenuItemEmisor itemPrint = new MenuItemEmisor("Imprimir");
        itemPrint.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_MASK));
        itemPrint.setActionCommand("cmd_print");
        itemPrint.setComando(comandoImprimir);
 
        MenuItemEmisor itemExit = new MenuItemEmisor("Salir");
        itemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_MASK));
        itemExit.setActionCommand("cmd_exit");
        itemExit.setComando(comandoSalir);
 
        menuFile.add(itemNew);    //se añaden los items al menú "Archivo"
        menuFile.add(itemOpen);
        menuFile.add(itemSave);
        menuFile.add(itemSaveAs);
        menuFile.addSeparator();
        menuFile.add(itemPrint);
        menuFile.addSeparator();
        menuFile.add(itemExit);
        //----------------------------------------------
 
        //construye el menú "Editar", a continuación se construyen los items para este menú
        JMenu menuEdit = new JMenu("Editar");
 
        mbItemUndo = new JMenuItem("Deshacer");
        mbItemUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        mbItemUndo.setEnabled(false);
        mbItemUndo.setActionCommand("cmd_undo");
        mbItemUndo.addActionListener((ActionEvent e) -> {
            memoriaDeshacer.deshacer();
            actualizarBotonesDeshacerYRehacer();
        });
 
        mbItemRedo = new JMenuItem("Rehacer");
        mbItemRedo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
        mbItemRedo.setEnabled(false);
        mbItemRedo.setActionCommand("cmd_redo");
        mbItemRedo.addActionListener((ActionEvent e) -> {
            memoriaDeshacer.rehacer();
            actualizarBotonesDeshacerYRehacer();
        });
 
        JMenuItem itemCut = new JMenuItem("Cortar");
        itemCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        itemCut.setActionCommand("cmd_cut");
        itemCut.addActionListener((ActionEvent e) -> {
            memoriaDeshacer.ejecutar(new ComandoCortar());
            actualizarBotonesDeshacerYRehacer();
        });
 
        MenuItemEmisor itemCopy = new MenuItemEmisor("Copiar");
        itemCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        itemCopy.setActionCommand("cmd_copy");
        itemCopy.setComando(comandoCopiar);
        
 
        JMenuItem itemPaste = new JMenuItem("Pegar");
        itemPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        itemPaste.setActionCommand("cmd_paste");
        itemPaste.addActionListener((ActionEvent e) -> {
            memoriaDeshacer.ejecutar(new ComandoPegar());
            actualizarBotonesDeshacerYRehacer();
        });
 
        MenuItemEmisor itemSearch = new MenuItemEmisor("Buscar");
        itemSearch.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
        itemSearch.setActionCommand("cmd_search");
        itemSearch.setComando(comandoBuscar);
 
        MenuItemEmisor itemSearchNext = new MenuItemEmisor("Buscar siguiente");
        itemSearchNext.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));
        itemSearchNext.setActionCommand("cmd_searchnext");
        itemSearchNext.setComando(comandoBuscarSiguiente);
 
        MenuItemEmisor itemGotoLine = new MenuItemEmisor("Ir a la línea...");
        itemGotoLine.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));
        itemGotoLine.setActionCommand("cmd_gotoline");
        itemGotoLine.setComando(comandoIrALinia);
 
        MenuItemEmisor itemSelectAll = new MenuItemEmisor("Seleccionar todo");
        itemSelectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        itemSelectAll.setActionCommand("cmd_selectall");
        itemSelectAll.setComando(comandoSeleccionarTodo);
 
        menuEdit.add(mbItemUndo);    //se añaden los items al menú "Editar"
        menuEdit.add(mbItemRedo);
        menuEdit.addSeparator();     //añade separadores entre algunos items
        menuEdit.add(itemCut);
        menuEdit.add(itemCopy);
        menuEdit.add(itemPaste);
        menuEdit.addSeparator();
        menuEdit.add(itemSearch);
        menuEdit.add(itemSearchNext);
        menuEdit.add(itemGotoLine);
        menuEdit.addSeparator();
        menuEdit.add(itemSelectAll);
        //----------------------------------------------
 
        //construye el menú "Opciones", a continuación se construyen los items para este menú
        JMenu menuTools = new JMenu("Opciones");
 
        itemLineWrap = new JCheckBoxMenuItem("Ajuste de línea");
        itemLineWrap.setSelected(true);
        itemLineWrap.setActionCommand("cmd_linewrap");
        itemLineWrap.setEnabled(false);
 
        itemShowToolBar = new JCheckBoxMenuItem("Ver barra de herramientas");
        itemShowToolBar.setSelected(true);
        itemShowToolBar.setActionCommand("cmd_showtoolbar");
        itemShowToolBar.setEnabled(false);
 
        itemFixedToolBar = new JCheckBoxMenuItem("Fijar barra de herramientas");
        itemFixedToolBar.setSelected(true);
        itemFixedToolBar.setActionCommand("cmd_fixedtoolbar");
        itemFixedToolBar.setEnabled(false);
 
        itemShowStatusBar = new JCheckBoxMenuItem("Ver barra de estado");
        itemShowStatusBar.setSelected(true);
        itemShowStatusBar.setActionCommand("cmd_showstatusbar");
        itemShowStatusBar.setEnabled(false);
 
        MenuItemEmisor itemFont = new MenuItemEmisor("Fuente de letra");
        itemFont.setActionCommand("cmd_font");
        itemFont.setComando(comandoFuente);
 
        MenuItemEmisor itemFontColor = new MenuItemEmisor("Color de letra");
        itemFontColor.setActionCommand("cmd_fontcolor");
        itemFontColor.setComando(comandoColorFuente);
 
        MenuItemEmisor itemBackgroundColor = new MenuItemEmisor("Color de fondo");
        itemBackgroundColor.setActionCommand("cmd_backgroundcolor");
        itemBackgroundColor.setComando(comandoColorFondo);
 
        menuTools.add(itemLineWrap);    //se añaden los items al menú "Opciones"
        menuTools.add(itemShowToolBar);
        menuTools.add(itemFixedToolBar);
        menuTools.add(itemShowStatusBar);
        menuTools.addSeparator();
        menuTools.add(itemFont);
        menuTools.add(itemFontColor);
        menuTools.add(itemBackgroundColor);
 
        //construye el menú "Ayuda", a continuación se construye el único item para este menú
        JMenu menuHelp = new JMenu("Ayuda");
 
        JMenuItem itemAbout = new JMenuItem("Acerca de");
        itemAbout.setActionCommand("cmd_about");
        itemAbout.setEnabled(false);
 
        menuHelp.add(itemAbout);     //se añade el único item al menú "Ayuda"
        //----------------------------------------------
 
        jMenuBar.add(menuFile);    //se añaden los menúes construidos a la barra de menú
        jMenuBar.add(Box.createHorizontalStrut(5));    //añade espacios entre cada menú
        jMenuBar.add(menuEdit);
        jMenuBar.add(Box.createHorizontalStrut(5));
        jMenuBar.add(menuTools);
        jMenuBar.add(Box.createHorizontalStrut(5));
        jMenuBar.add(menuHelp);
 
        /** itera sobre todos los componentes de la barra de menú, se les asigna el mismo
        manejador de eventos a todos excepto a los separadores */
        for (Component c1 : jMenuBar.getComponents()) {
            //si el componente es un menú
            if (c1.getClass().equals(javax.swing.JMenu.class)) {
                //itera sobre los componentes del menú
                for (Component c2 : ((JMenu) c1).getMenuComponents()) {
                    //si el componente es un MenuItemEmisor
                    if (c2.getClass().equals(MenuItemEmisor.class)) {
                        ((MenuItemEmisor) c2).addActionListener(eventHandler);
                    }
                }
            }
        }
    }
 
    /**
     * Construye la barra de herramientas.
     */
    private void buildToolBar() {
        jToolBar = new JToolBar();       //construye un JToolBar
        jToolBar.setFloatable(false);    //se configura por defecto como barra fija
 
        //construye el botón "Nuevo"
        BotonEmisor buttonNew = new BotonEmisor();
        //le asigna una etiqueta flotante
        buttonNew.setToolTipText("Nuevo");
        //le asigna una imagen ubicada en los recursos del proyecto
        buttonNew.setIcon(new ImageIcon(getClass().getResource("/res/tp_new.png")));
        //le asigna un nombre de comando
        buttonNew.setActionCommand("cmd_new");
        buttonNew.setComando(comandoNuevo);
        buttonNew.addActionListener(eventHandler);
 
        BotonEmisor buttonOpen = new BotonEmisor();
        buttonOpen.setToolTipText("Abrir");
        buttonOpen.setIcon(new ImageIcon(getClass().getResource("/res/tp_open.png")));
        buttonOpen.setActionCommand("cmd_open");
        buttonOpen.setComando(comandoAbrir);
        buttonOpen.addActionListener(eventHandler);
 
        BotonEmisor buttonSave = new BotonEmisor();
        buttonSave.setToolTipText("Guardar");
        buttonSave.setIcon(new ImageIcon(getClass().getResource("/res/tp_save.png")));
        buttonSave.setActionCommand("cmd_save");
        buttonSave.setComando(comandoGuardar);
        buttonSave.addActionListener(eventHandler);
 
        BotonEmisor buttonSaveAs = new BotonEmisor();
        buttonSaveAs.setToolTipText("Guardar como...");
        buttonSaveAs.setIcon(new ImageIcon(getClass().getResource("/res/tp_saveas.png")));
        buttonSaveAs.setActionCommand("cmd_saveas");
        buttonSaveAs.setComando(comandoGuardarComo);
        buttonSaveAs.addActionListener(eventHandler);
 
        BotonEmisor buttonPrint = new BotonEmisor();
        buttonPrint.setToolTipText("Imprimir");
        buttonPrint.setIcon(new ImageIcon(getClass().getResource("/res/tp_print.png")));
        buttonPrint.setActionCommand("cmd_print");
        buttonPrint.setComando(comandoImprimir);
        buttonPrint.addActionListener(eventHandler);
 
        buttonUndo = new JButton();
        buttonUndo.setEnabled(false);
        buttonUndo.setToolTipText("Deshacer último comando");
        buttonUndo.setIcon(new ImageIcon(getClass().getResource("/res/tp_undo.png")));
        buttonUndo.setActionCommand("cmd_undo");
        buttonUndo.addActionListener((ActionEvent e) -> {
            memoriaDeshacer.deshacer();
            actualizarBotonesDeshacerYRehacer();
        });
 
        buttonRedo = new JButton();
        buttonRedo.setEnabled(false);
        buttonRedo.setToolTipText("Rehacer último comando");
        buttonRedo.setIcon(new ImageIcon(getClass().getResource("/res/tp_redo.png")));
        buttonRedo.setActionCommand("cmd_redo");
        buttonRedo.addActionListener((ActionEvent e) -> {
            memoriaDeshacer.rehacer();
            actualizarBotonesDeshacerYRehacer();
        });
 
        JButton buttonCut = new JButton();
        buttonCut.setToolTipText("Cortar");
        buttonCut.setIcon(new ImageIcon(getClass().getResource("/res/tp_cut.png")));
        buttonCut.setActionCommand("cmd_cut");
        buttonCut.addActionListener((ActionEvent e) -> {
            memoriaDeshacer.ejecutar(new ComandoCortar());
            actualizarBotonesDeshacerYRehacer();
        });
 
        BotonEmisor buttonCopy = new BotonEmisor();
        buttonCopy.setToolTipText("Copiar");
        buttonCopy.setIcon(new ImageIcon(getClass().getResource("/res/tp_copy.png")));
        buttonCopy.setActionCommand("cmd_copy");
        buttonCopy.setComando(comandoCopiar);
        buttonCopy.addActionListener(eventHandler);
 
        JButton buttonPaste = new JButton();
        buttonPaste.setToolTipText("Pegar");
        buttonPaste.setIcon(new ImageIcon(getClass().getResource("/res/tp_paste.png")));
        buttonPaste.setActionCommand("cmd_paste");
        buttonPaste.addActionListener((ActionEvent e) -> {
            memoriaDeshacer.ejecutar(new ComandoPegar());
            actualizarBotonesDeshacerYRehacer();
        });
 
        jToolBar.add(buttonNew);    //se añaden los botones construidos a la barra de herramientas
        jToolBar.add(buttonOpen);
        jToolBar.add(buttonSave);
        jToolBar.add(buttonSaveAs);
        jToolBar.addSeparator();    //añade separadores entre algunos botones
        jToolBar.add(buttonPrint);
        jToolBar.addSeparator();
        jToolBar.add(buttonUndo);
        jToolBar.add(buttonRedo);
        jToolBar.addSeparator();
        jToolBar.add(buttonCut);
        jToolBar.add(buttonCopy);
        jToolBar.add(buttonPaste);
    }
 
    /**
     * Construye la barra de estado.
     */
    private void buildStatusBar() {
        statusBar = new JPanel();    //construye un JPanel
        //se configura con un BoxLayout
        statusBar.setLayout(new BoxLayout(statusBar, BoxLayout.LINE_AXIS));
        //le añade un borde compuesto
        statusBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLoweredBevelBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
 
        //construye la etiqueta para mostrar la ubicación del archivo actual
        sbFilePath = new JLabel("...");
        //construye la etiqueta para mostrar el tamaño del archivo actual
        sbFileSize = new JLabel("");
        //construye la etiqueta para mostrar la posición del cursor en el documento actual
        sbCaretPos = new JLabel("...");
 
        /** se añaden las etiquetas construidas al JPanel, el resultado es un panel
        similar a una barra de estado */
        statusBar.add(sbFilePath);
        statusBar.add(Box.createRigidArea(new Dimension(10, 0)));
        statusBar.add(sbFileSize);
        statusBar.add(Box.createRigidArea(new Dimension(10, 0)));
        statusBar.add(Box.createHorizontalGlue());
        statusBar.add(sbCaretPos);
    }
 
    /**
     * Construye el menú emergente.
     */
    private void buildPopupMenu() {
        jPopupMenu = new JPopupMenu();    //construye un JPopupMenu
 
        //construye el item "Deshacer"
        mpItemUndo = new JMenuItem("Deshacer");
        //se configura desactivado por defecto
        mpItemUndo.setEnabled(false);
        //le asigna un nombre de comando
        mpItemUndo.setActionCommand("cmd_undo");
        mpItemUndo.addActionListener((ActionEvent e) -> {
            memoriaDeshacer.deshacer();
            actualizarBotonesDeshacerYRehacer();
        });
 
        mpItemRedo = new JMenuItem("Rehacer");
        mpItemRedo.setEnabled(false);
        mpItemRedo.setActionCommand("cmd_redo");
        mpItemRedo.addActionListener((ActionEvent e) -> {
            memoriaDeshacer.rehacer();
            actualizarBotonesDeshacerYRehacer();
        });
 
        JMenuItem itemCut = new JMenuItem("Cortar");
        itemCut.setActionCommand("cmd_cut");
        itemCut.addActionListener((ActionEvent e) -> {
            memoriaDeshacer.ejecutar(new ComandoCortar());
            actualizarBotonesDeshacerYRehacer();
        });
 
        MenuItemEmisor itemCopy = new MenuItemEmisor("Copiar");
        itemCopy.setActionCommand("cmd_copy");
        itemCopy.setComando(comandoCopiar);
        
 
        JMenuItem itemPaste = new JMenuItem("Pegar");
        itemPaste.setActionCommand("cmd_paste");
        itemPaste.addActionListener((ActionEvent e) -> {
            memoriaDeshacer.ejecutar(new ComandoPegar());
            actualizarBotonesDeshacerYRehacer();
        });
 
        MenuItemEmisor itemGoto = new MenuItemEmisor("Ir a...");
        itemGoto.setActionCommand("cmd_gotoline");
        itemGoto.setComando(comandoIrALinia);
 
        MenuItemEmisor itemSearch = new MenuItemEmisor("Buscar");
        itemSearch.setActionCommand("cmd_search");
        itemSearch.setComando(comandoBuscar);
 
        MenuItemEmisor itemSearchNext = new MenuItemEmisor("Buscar siguiente");
        itemSearchNext.setActionCommand("cmd_searchnext");
        itemSearchNext.setComando(comandoBuscarSiguiente);
 
        MenuItemEmisor itemSelectAll = new MenuItemEmisor("Seleccionar todo");
        itemSelectAll.setActionCommand("cmd_selectall");
        itemSelectAll.setComando(comandoSeleccionarTodo);
 
        jPopupMenu.add(mpItemUndo);    //se añaden los items al menú emergente
        jPopupMenu.add(mpItemRedo);
        jPopupMenu.addSeparator();     //añade separadores entre algunos items
        jPopupMenu.add(itemCut);
        jPopupMenu.add(itemCopy);
        jPopupMenu.add(itemPaste);
        jPopupMenu.addSeparator();
        jPopupMenu.add(itemGoto);
        jPopupMenu.add(itemSearch);
        jPopupMenu.add(itemSearchNext);
        jPopupMenu.addSeparator();
        jPopupMenu.add(itemSelectAll);
 
        /** itera sobre todos los componentes del menú emergente, se les asigna el mismo
        manejador de eventos a todos excepto a los separadores */
        for (Component c : jPopupMenu.getComponents()) {
            //si el componente es un MenuItemEmisor
            if (c.getClass().equals(MenuItemEmisor.class)) {
                ((MenuItemEmisor) c).addActionListener(eventHandler);
            }
        }
    }
 
    /**
     * Hace visible el menú emergente.
     *
     * @param me evento del ratón
     */
    private void showPopupMenu(MouseEvent me) {
        if (me.isPopupTrigger() == true) {    //si el evento es el desencadenador de menú emergente
            //hace visible el menú emergente en las coordenadas actuales del ratón
            jPopupMenu.show(me.getComponent(), me.getX(), me.getY());
        }
    }
 
    /**
     * Actualiza el estado de las opciones "Deshacer" y "Rehacer".
     */
    //REFACTORING
    public void actualizarBotonesDeshacerYRehacer() {
        //averigua si se pueden deshacer los cambios en el documento actual
        boolean sePuedeDeshacer = memoriaDeshacer.sePuedeDeshacer();
        //averigua si se pueden rehacer los cambios en el documento actual
        boolean sePuedeRehacer = memoriaDeshacer.sePuedeRehacer();
 
        //activa o desactiva las opciones en la barra de menú
        mbItemUndo.setEnabled(sePuedeDeshacer);
        mbItemRedo.setEnabled(sePuedeRehacer);
 
        //activa o desactiva las opciones en la barra de herramientas
        buttonUndo.setEnabled(sePuedeDeshacer);
        buttonRedo.setEnabled(sePuedeRehacer);
 
        //activa o desactiva las opciones en el menú emergente
        mpItemUndo.setEnabled(sePuedeDeshacer);
        mpItemRedo.setEnabled(sePuedeRehacer);
    }
 
    /**
     * Retorna la instancia de EventHandler, la clase interna que maneja eventos.
     *
     * @return el manejador de eventos.
     */
    public EventHandler getEventHandler() {
        return eventHandler;
    }
 
    
    /** MODIFICACIÓN PARA PATRÓN MEMENTO
     *   Retorna la instancia de ListaDeComandosUndoable, la cual administra las ediciones sobre
     *   el documento en el área de texto.
     *
     * @return el administrador de edición.
     */
    public ListaDeComandosUndoable getMemoriaDeshacer() {
        return memoriaDeshacer;
    }
    /*
    * FIN MOD.
    */
    
    /**
     * Retorna el estado del documento actual.
     *
     * @return true si ah sido modificado, false en caso contrario
     */
    public boolean documentHasChanged() {
        return hasChanged;
    }
 
    /**
     * Establece el estado del documento actual.
     *
     * @param hasChanged true si ah sido modificado, false en caso contrario
     */
    public void setDocumentChanged(boolean hasChanged) {
        this.hasChanged = hasChanged;
    }
 
    /**
     * Retorna la instancia de JTextArea, el área de edición.
     *
     * @return retorna el área de edición.
     */
    public JTextArea getJTextArea() {
        return jTextArea;
    }
    
    
    /**
     * Retorna la instancia de JFrame, la ventana principal del editor.
     *
     * @return la ventana principal del editor.
     */
    public JFrame getJFrame() {
        return jFrame;
    }
 
    /**
     * Retorna la instancia de File, el archivo actual.
     *
     * @return el archivo actual
     */
    public File getCurrentFile() {
        return currentFile;
    }
 
    /**
     * Establece el archivo actual.
     *
     * @param currentFile el archivo actual
     */
    public void setCurrentFile(File currentFile) {
        this.currentFile = currentFile;
    }
 
    /**
     * Retorna la instancia de la etiqueta sbFilePath, donde se muestra la ubicación
     * del archivo actual.
     *
     * @return la instancia de la etiqueta sbFilePath
     */
    public JLabel getJLabelFilePath() {
        return sbFilePath;
    }
 
    /**
     * Retorna la instancia de la etiqueta sbFileSize, donde se muestra el tamaño
     * del archivo actual
     *
     * @return la instancia de la etiqueta sbFileSize
     */
    public JLabel getJLabelFileSize() {
        return sbFileSize;
    }
    
    //REFACTORING
     /**
     * @return the lastSearch
     */
    public String getLastSearch() {
        return lastSearch;
    }

    /**
     * @param aLastSearch the lastSearch to set
     */
    public void setLastSearch(String aLastSearch) {
        lastSearch = aLastSearch;
    }
    
    /*
    * MODIFICACIÓN PARA PATRÓN MEMENTO
    *
    * @return the memento
    */
    public Memento getMemento() {
        return new Memento(jTextArea.getText());
    }

    /**
     * @param memento the memento to set
     */
    public void setMemento(Memento memento) {
        this.jTextArea.setText(memento.getEstado());
        hasChanged = true;
    }
    /*
    * FIN MOD.
    */
    
    /**
     * Clase interna que extiende e implementa las clases e interfaces necesarias para
     * atender y manejar los eventos sobre la GUI principal del editor.
     */
    class EventHandler extends MouseAdapter implements ActionListener,
                                                       CaretListener{
 
        /**
         * Atiende y maneja los eventos de acción.
         *
         * @param ae evento de acción
        */ 
        @Override
        public void actionPerformed(ActionEvent ae) {
            System.out.println(ae.getActionCommand());
            
            //Si el objeto implementa la interfaz Emisor
            //REFACTORING
            //Emisor e = (Emisor) ae.getSource();
            //e.ejecutarComando();
            ((Emisor) ae.getSource()).ejecutarComando();
            actualizarBotonesDeshacerYRehacer();
        }
        
        /**
         * Atiende y maneja los eventos del cursor.
         *
         * @param ce evento del cursor
         */
        @Override
        public void caretUpdate(CaretEvent e) {
            final int caretPos;  //valor de la posición del cursor sin inicializar
            int y = 1;           //valor de la línea inicialmente en 1
            int x = 1;           //valor de la columna inicialmente en 1
 
            try {
                //obtiene la posición del cursor con respecto al inicio del JTextArea (área de edición)
                caretPos = jTextArea.getCaretPosition();
                //sabiendo lo anterior se obtiene el valor de la línea actual (se cuenta desde 0)
                y = jTextArea.getLineOfOffset(caretPos);
 
                /** a la posición del cursor se le resta la posición del inicio de la línea para
                determinar el valor de la columna actual */
                x = caretPos - jTextArea.getLineStartOffset(y);
 
                //al valor de la línea actual se le suma 1 porque estas comienzan contándose desde 0
                y += 1;
            } catch (BadLocationException ex) {    //en caso de que ocurra una excepción
                System.err.println(ex);
            }
 
            /** muestra la información recolectada en la etiqueta sbCaretPos de la
            barra de estado, también se incluye el número total de lineas */
            sbCaretPos.setText("Líneas: " + jTextArea.getLineCount() + " - Y: " + y + " - X: " + x);
        }
        
        /**
         * Atiende y maneja los eventos sobre el ratón cuando este es presionado.
         *
         * @param me evento del ratón
         */
        @Override
        public void mousePressed(MouseEvent me) {
            showPopupMenu(me);
        }
 
        /**
         * Atiende y maneja los eventos sobre el ratón cuando este es liberado.
         *
         * @param me evento del ratón
         */
        @Override
        public void mouseReleased(MouseEvent me) {
            showPopupMenu(me);
        }
    }
}