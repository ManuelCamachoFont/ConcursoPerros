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
import java.io.File;

import javax.imageio.ImageIO;

public class VistaConcursantes {

	Frame ventana = new Frame("Concursantes");
	
	Label lblTitulo = new Label("Lista de concursantes", Label.CENTER);
	Label lblNombre = new Label ("Nombre", Label.CENTER);
	
	Button btnSiguiente1 = new Button("Siguiente");
	Button btnSiguiente2 = new Button("Siguiente");
	Button btnVolver1 = new Button("Volver");
	Button btnVolver2 = new Button("Volver");

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
	
	Font Serif = new Font ("Serif", 0, 16);
	
	public VistaConcursantes() {
		ventana.setLayout(gridbag);
		ventana.setBackground(negro);
		ventana.setFont((Utilidades.elegirFuente("fuentes/Merienda.ttf", 2, 16)));
		ventana.setSize(500, 500);
		gbc.insets = new Insets (10, 10, 10, 10);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		lblTitulo.setForeground(blanco);
		ventana.add(lblTitulo, gbc);
	
		gbc.gridy = 1;
		lblNombre.setForeground(blanco);
		ventana.add(lblNombre, gbc);
		
		try {
            imagen = ImageIO.read(new File("recursos/imagenes/concursantes.png"));
            canvasImagen.repaint();
        } catch (Exception e) {
            System.err.println("Error cargando imagen: " + e.getMessage());
        }
        
        gbc.gridy = 2;
        canvasImagen.setSize(250, 250);
        ventana.add(canvasImagen, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		btnSiguiente1.setForeground(blanco);
		btnSiguiente1.setBackground(azul);
		ventana.add(btnSiguiente1, gbc);
		

		gbc.gridy = 3;
		btnVolver2.setForeground(blanco);
		btnVolver2.setBackground(azul);
		btnVolver2.setEnabled(false);
		btnVolver2.setVisible(false);
		ventana.add(btnVolver2, gbc);
		
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.EAST;
		btnSiguiente2.setForeground(blanco);
		btnSiguiente2.setBackground(azul);
		btnSiguiente2.setEnabled(false);
		btnSiguiente2.setVisible(false);
		ventana.add(btnSiguiente2, gbc);
		
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.WEST;
		btnVolver1.setForeground(blanco);
		btnVolver1.setBackground(azul);
		btnVolver1.setEnabled(false);
		btnVolver1.setVisible(false);
		ventana.add(btnVolver1, gbc);
		
		ventana.setLocationRelativeTo(null);
		int x = ventana.getLocation().x;
		int y = ventana.getLocation().y;
		ventana.setLocation(x-530, y);
		ventana.setResizable(false);
		ventana.setVisible(true);
	}
	
}
