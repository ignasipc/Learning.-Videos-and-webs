package sudokusolver;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class AppSudoku extends JFrame {
    private static final int FILAS = 9;
    private static final int COLUMNAS = 9;

    //Paneles
    private Container panelContenidos = null;
    private JPanel panelSudoku = null;
    private Celda [][] sudoku = null;
    private JPanel panelBotones = null;
    private JButton BResolver = null;
    private JButton BNuevoSudoku = null;
    private JSplitPane separador = null;
    
    
    public AppSudoku() {
        panelSudoku = new JPanel();
        panelBotones = new JPanel();
        
        panelContenidos = getContentPane();
        panelContenidos.setLayout(new BorderLayout());
        panelSudoku.setLayout(new GridLayout(FILAS,COLUMNAS));
        
        
        
        sudoku = new Celda[FILAS][COLUMNAS];

        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                sudoku[i][j] = new Celda(i,j, 0);  // Crear instancias de Celda                
                panelSudoku.add(sudoku[i][j]);  // Agregar la instancia de Celda al panelSudoku
            }
        }
        
        //PANEL BOTONES
        panelBotones.setLayout(new GridLayout(1,2));
        BResolver = new JButton("RESOLVER");
        BNuevoSudoku = new JButton("NUEVO SUDOKU");
        
        BResolver.addActionListener(new eventoBoton());
        BNuevoSudoku.addActionListener(new eventoBoton());
        
        panelBotones.add(BResolver);
        panelBotones.add(BNuevoSudoku);
        
        
        //SEPARADOR
        separador = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        separador.setEnabled(false);
        
        separador.setTopComponent(panelSudoku);
        separador.setBottomComponent(panelBotones);
        
        
        //PANEL CONTENIDOS
        panelContenidos.add(panelSudoku,BorderLayout.NORTH);
        panelContenidos.add(separador, BorderLayout.CENTER);
        panelContenidos.add(panelBotones, BorderLayout.SOUTH);
        
        
        //VENTANA
        setTitle("Sudoku solver");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    
    private class eventoBoton implements ActionListener{
                @Override
                public void actionPerformed(ActionEvent evento) {
                    switch(evento.getActionCommand()){
                        case "RESOLVER":
                            System.out.println("Ha pulsado el Boton Resolver");
                            iniciarResolucion();
                            break;

                        case "NUEVO SUDOKU":
                            System.out.println("Ha pulsado el Boton Nuevo Sudoku");
                            nuevoSudoku();
                            break;
                    }
                }
    }
    
    private void iniciarResolucion() {
        try {
            // Backtracking del sudoku
            if (resolverSudoku(0, 0)) {
                System.out.println("Solucion encontrada :)");
            } else {
                System.out.println("No existe solución :(");
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(AppSudoku.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean resolverSudoku(int f, int c) throws InterruptedException{        
        if (f == 9) {
            return true;
        } else if (c == 9) {
            return resolverSudoku(f + 1, 0);
        } else if (sudoku[f][c].getNumero() != 0) {
            return resolverSudoku(f, c + 1);
        } else {
            for (int k = 1; k <= 9; k++) {
                if (esValido(f, c, k)) {
                    sudoku[f][c].actualizarNumero(f, c, k);
                    
                    //Thread.sleep(100);
                    
                    if (resolverSudoku(f, c + 1)) {
                        return true;
                    }
                    sudoku[f][c].actualizarNumero(f, c, 0);
                }
            }
            return false;
        }
    }
    
    private boolean esValido(int fila, int columna, int numeroAInsertar){
        boolean notInRow = contieneEnFila(fila, numeroAInsertar);
        boolean notInColumn = contieneEnColumna(columna, numeroAInsertar);
        boolean notInBox = contieneEnCaja(fila, columna, numeroAInsertar);
        return !notInRow && !notInColumn && !notInBox;
    }
    
    private boolean contieneEnFila(int fila, int numeroAInsertar){
        for (int i = 0; i < COLUMNAS; i++) {
            if (sudoku[fila][i].getNumero() == numeroAInsertar){                
                return true;
            }
        }
        return false;
    }
    
    private boolean contieneEnColumna(int columna, int numeroAInsertar){
        for (int i = 0; i < FILAS; i++) {
            if (sudoku[i][columna].getNumero() == numeroAInsertar){
                return true;
            }
        }
        return false;        
    }
    
    private boolean contieneEnCaja(int fila, int columna, int numeroAInsertar){
        int empiezaEnFila = (fila/3) * 3;
        int empiezaEnColumna = (columna/3) * 3;
        
        for (int i = empiezaEnFila; i < empiezaEnFila+3; i++) {
            for (int j = empiezaEnColumna; j < empiezaEnColumna+3; j++) {
                if(sudoku[i][j].getNumero() == numeroAInsertar){
                    return true;
                }
            }
        }
        return false;
    }
    
    public void nuevoSudoku(){
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                sudoku[i][j].actualizarNumero(i, j, 0);
                sudoku[i][j].setBackground(Color.WHITE);
            }
        }
    }
    
    
}
   
