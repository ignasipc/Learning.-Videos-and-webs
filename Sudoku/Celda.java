package sudokusolver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Celda extends JTextField {
    public static final int SIZE = 50;
    private int fila, columna, valor;
    
    public Celda(int fila, int columna, int valor){
        this.fila = fila;
        this.columna = columna;
        this.valor = valor;
        setPreferredSize(new Dimension(50, 50));
        setHorizontalAlignment(JTextField.CENTER);
        //setBackground(Color.RED);
        setFont(new Font("Arial", Font.BOLD, 24));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        if ((columna == 2 || columna == 5) && (fila == 2 || fila == 5)) {
            setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.BLACK));
        } else if (columna == 2 || columna == 5) {
            setBorder(BorderFactory.createMatteBorder(1, 1, 1, 3, Color.BLACK));
        } else if (fila == 2 || fila == 5) {
            setBorder(BorderFactory.createMatteBorder(1, 1, 3, 1, Color.BLACK));
        }
        
        if (valor==0){
            setText(String.valueOf(0));
        }
        
        // Agregar un escuchador de eventos para detectar la finalización de la edición
            addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                }

                @Override
                public void keyPressed(KeyEvent e) {
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    actualizarNumero(fila,columna);
                }
            });
            
            
    }
    
    public void setValorCelda(int fila, int columna, int valor){
        actualizarNumero(fila,columna, valor);
    }
    
    // Método para actualizar el número en la celda desde teclado
    public void actualizarNumero(int f, int c) {
        int b = this.getBloque();
        try {
            int nuevoNumero = Integer.parseInt(getText());
            if (nuevoNumero>=1 && nuevoNumero<=9){
                setText(String.valueOf(nuevoNumero));
                this.valor = nuevoNumero;  
                System.out.println("Ha escrito: "+nuevoNumero+" en la fila: "+(f+1)+" y la columna: "+(c+1)+" y en el bloque: "+b);
            }else{
                JOptionPane.showMessageDialog(Celda.this,
                    "Ingrese un número entre 1 y 9", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            // Manejar la entrada no válida, por ejemplo, dejar el número anterior
            JOptionPane.showMessageDialog(Celda.this,
                    "Ingrese un número válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    //Metodo para actualizar el numero en la celda desde el programa
    public void actualizarNumero(int f, int c, int v) {
        int b = this.getBloque();
        setText(String.valueOf(v));
        this.valor = v;                                                         /////////////////////////////////////////////////////////////////////////
        System.out.println("Ha escrito: "+v+" en la fila: "+(f+1)+" y la columna: "+(c+1)+" y en el bloque: "+b);
    }
    
    public void setNumero(int f, int c, String v) {
        int b = this.getBloque();
        setText(v);
        //System.out.println("Ha escrito: "+v+" en la fila: "+(f+1)+" y la columna: "+(c+1)+" y en el bloque: "+b);
    }
    
    public int getNumero(){
        return valor;
    }
    
    public int getFila(){
        return fila;
    }
    
    public int getColumna(){
        return columna;
    }
    
    private int getBloque(){
        return (((fila / 3) * 3 + columna / 3) + 1);
    }

}


///TO DO: Cada vez que ingrese un numero no valido, vaciar la celda