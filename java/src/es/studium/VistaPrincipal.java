package es.studium;

import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;

public class VistaPrincipal {

	Frame ventana = new Frame ("Concurso");
	Label lblTitulo = new Label("Menú Principal");
	Button btnPerros = new Button("Perros");
	Button btnDuenos = new Button("Dueños");
	Button btnJueces = new Button("Jueces");
	Button btnIni = new Button ("Iniciar concurso");
	
	GridBagLayout gridbag = new GridBagLayout();
	GridBagConstraints gbc = new GridBagConstraints();
	
	Color blanco = new Color(255, 255, 255);
	Color negro = new Color (30, 30, 30);
	Color azul = new Color (80, 80, 255);
	
	public VistaPrincipal() {
		ventana.setLayout(gridbag);
		ventana.setBackground(negro);
		ventana.setFont(Utilidades.elegirFuente("fuentes/Merienda.ttf", 3, 24));
		ventana.setSize(300, 350);
		
		gbc.insets = new Insets (10, 10, 10, 10);
		gbc.gridx = 0;
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
		btnIni.setBackground(azul);
		btnIni.setForeground(blanco);
		ventana.add(btnIni, gbc);
		
		ventana.setLocationRelativeTo(null);
		ventana.setResizable(false);
		ventana.setVisible(true);
	}
	
	public static void main(String[] args) {
		new VistaPrincipal();
	}
	
}


