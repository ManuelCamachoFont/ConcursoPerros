package es.studium;

import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;

public class VistaPerrosMenu {

	Frame ventana = new Frame("Perros");
	Label lblTitulo = new Label("Menú - Perros");
	Button btnCrear = new Button("Inscribir");
	Button btnMod = new Button("Modificar");
	Button btnBorrar = new Button("Eliminar");
	
	GridBagLayout gridbag = new GridBagLayout();
	GridBagConstraints gbc = new GridBagConstraints();
	
	Color blanco = new Color(255, 255, 255);
	Color negro = new Color (30, 30, 30);
	Color azul = new Color (80, 80, 255);
	
	public VistaPerrosMenu(){
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
		btnCrear.setBackground(azul);
		btnCrear.setForeground(blanco);
		ventana.add(btnCrear, gbc);
		
		gbc.gridy = 2;
		btnMod.setBackground(azul);
		btnMod.setForeground(blanco);
		ventana.add(btnMod, gbc);
		
		gbc.gridy = 3;
		btnBorrar.setBackground(azul);
		btnBorrar.setForeground(blanco);
		ventana.add(btnBorrar, gbc);
		
		ventana.setLocationRelativeTo(null);
		ventana.setResizable(false);
		ventana.setVisible(true);
	}
	
	public static void main(String[] args) {
		new VistaPerrosMenu();
	}
}
