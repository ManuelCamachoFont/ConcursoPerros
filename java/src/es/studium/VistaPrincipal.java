package es.studium;

import java.awt.Button;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Label;
import java.io.File;

import javax.imageio.ImageIO;

public class VistaPrincipal {

	Frame ventana = new Frame("Menú principal");
	Label lblTitulo = new Label("¡Bienvenido al concurso!");
	Button btnPerros = new Button("Menú - Perros");
	Button btnDuenos = new Button("Menú - Dueños");
	Button btnJueces = new Button("Menú - Jueces");
	Button btnIni = new Button("Iniciar concurso");

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
	Color negro = new Color(30, 30, 30);
	Color azul = new Color(80, 80, 255);
	Color dorado = new Color(200, 178, 115);

	public VistaPrincipal() {
		ventana.setLayout(gridbag);
		ventana.setBackground(negro);
		ventana.setFont(Utilidades.elegirFuente("fuentes/Merienda.ttf", 3, 24));
		ventana.setSize(600, 450);

		try {
			imagen = ImageIO.read(new File("recursos/imagenes/portada.png"));
			canvasImagen.repaint();
		} catch (Exception e) {
			System.err.println("Error cargando imagen: " + e.getMessage());
		}

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 5;
		canvasImagen.setSize(256, 310);
		ventana.add(canvasImagen, gbc);
		gbc.gridheight = 1;
		
		gbc.insets = new Insets(10, 25, 10, 10);
		gbc.gridx = 1;
		gbc.gridy = 0;
		lblTitulo.setForeground(blanco);
		ventana.add(lblTitulo, gbc);

		gbc.gridy = 1;
		btnPerros.setBackground(azul);
		btnPerros.setForeground(blanco);
		ventana.add(btnPerros, gbc);

		gbc.gridy = 2;
		btnDuenos.setBackground(azul);
		btnDuenos.setForeground(blanco);
		ventana.add(btnDuenos, gbc);

		gbc.gridy = 3;
		btnJueces.setBackground(azul);
		btnJueces.setForeground(blanco);
		ventana.add(btnJueces, gbc);

		gbc.gridy = 4;
		gbc.insets = new Insets(25, 25, 10, 10);
		btnIni.setBackground(dorado);
		btnIni.setForeground(blanco);
		ventana.add(btnIni, gbc);

		ventana.setLocationRelativeTo(null);
		ventana.setResizable(false);
		ventana.setVisible(true);
	}

}
