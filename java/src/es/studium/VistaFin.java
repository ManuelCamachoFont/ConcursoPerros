package es.studium;

import java.awt.Button;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Label;
import java.awt.TextArea;
import java.io.File;

import javax.imageio.ImageIO;

public class VistaFin {
    Frame ventana = new Frame("Resultados del Concurso");
    Label lblTitulo = new Label("Fin del Concurso - Resultados", Label.CENTER);
    TextArea txtResultados = new TextArea(15, 70);
    Label lblGanadorTitulo = new Label("Ganador del concurso");
    Label lblGanador = new Label ("");
    Button btnReiniciar = new Button("Reiniciar concurso");
    
    Image imagen;
    Canvas canvasImagen = new Canvas() {
        @Override
        public void paint(Graphics g) {
            if (imagen != null) {
                g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
            }
        }
    };
    
    GridBagLayout gridbag = new GridBagLayout();
    GridBagConstraints gbc = new GridBagConstraints();
    
    Color blanco = new Color(255, 255, 255);
	Color negro = new Color (30, 30, 30);
	Color gris = new Color (90, 90, 90);
	Color azul = new Color (80, 80, 255);
    
    public VistaFin() {
        ventana.setLayout(gridbag);
        ventana.setBackground(negro);
        ventana.setFont(Utilidades.elegirFuente("fuentes/Merienda.ttf", 1, 14));
        ventana.setSize(900, 500);
        gbc.insets = new Insets(10, 10, 10, 10);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        lblTitulo.setForeground(blanco);
        ventana.add(lblTitulo, gbc);
        gbc.gridwidth = 1;
        
        gbc.gridy = 1;
        gbc.gridheight = 3;
        txtResultados.setEditable(false);
        txtResultados.setBackground(gris);
        txtResultados.setForeground(blanco);
        ventana.add(txtResultados, gbc);
        gbc.gridheight = 1;
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        lblGanadorTitulo.setForeground(blanco);
        ventana.add(lblGanadorTitulo, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        lblGanador.setForeground(blanco);
        ventana.add(lblGanador, gbc);

        try {
            imagen = ImageIO.read(new File("recursos/imagenes/concurso.png"));
            canvasImagen.repaint();
        } catch (Exception e) {
            System.err.println("Error cargando imagen: " + e.getMessage());
        }
        
        gbc.gridy = 3;
        canvasImagen.setSize(150, 150);
        ventana.add(canvasImagen, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        btnReiniciar.setBackground(azul);
        btnReiniciar.setForeground(blanco);
        ventana.add(btnReiniciar, gbc);
        
        ventana.setLocationRelativeTo(null);
        ventana.setResizable(false);
        ventana.setVisible(true);
    }
    
}
