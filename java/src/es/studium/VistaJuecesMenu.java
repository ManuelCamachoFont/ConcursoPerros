package es.studium;

import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;

public class VistaJuecesMenu {

	Frame ventana = new Frame("Jueces");
	Label lblTitulo = new Label("Menú - Jueces");
	Button btnMod = new Button("Modificar");

	GridBagLayout gridbag = new GridBagLayout();
	GridBagConstraints gbc = new GridBagConstraints();
	
	Color blanco = new Color(255, 255, 255);
	Color negro = new Color (30, 30, 30);
	Color azul = new Color (80, 80, 255);
	
	public VistaJuecesMenu(){
		ventana.setLayout(gridbag);
		ventana.setBackground(negro);
		ventana.setFont(Utilidades.elegirFuente("fuentes/Merienda.ttf", 3, 24));
		ventana.setSize(250, 350);
		
		gbc.insets = new Insets (10, 10, 10, 10);
		gbc.gridx = 0;
		gbc.gridy = 0;
		lblTitulo.setForeground(blanco);
		ventana.add(lblTitulo, gbc);
		gbc.gridy = 1;
		btnMod.setForeground(blanco);
		btnMod.setBackground(azul);
		ventana.add(btnMod, gbc);

		ventana.setLocationRelativeTo(null);
		int x = ventana.getLocation().x;
		int y = ventana.getLocation().y;
		ventana.setLocation(x + 435, y);
		ventana.setResizable(false);
		ventana.setVisible(true);
	}
	
	public static void main(String[] args) {
		new VistaJuecesMenu();
	}
}

