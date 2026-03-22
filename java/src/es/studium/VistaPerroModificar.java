package es.studium;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.TextField;

public class VistaPerroModificar {

	Frame ventana = new Frame("Concurso");
	
	Label lblTitulo = new Label("Modificar perro", Label.CENTER);
	Choice choPerros = new Choice();
	Label lblNombre = new Label ("Nombre");
	TextField txtNombre = new TextField(20);
	Label lblRaza = new Label ("Raza");
	TextField txtRaza = new TextField(20);
	Label lblTamano = new Label ("Tamaño");
	TextField txtTamano = new TextField (20);
	Label lblColor = new Label ("Color");
	TextField txtColor = new TextField (20);
	Label lblDueno = new Label ("Dueño");
	Choice choDuenos = new Choice();
	
	Button btnMod = new Button("Modificar");
	
	GridBagLayout gridbag = new GridBagLayout();
	GridBagConstraints gbc = new GridBagConstraints();
	
	Dialog diaFeedback = new Dialog(ventana, "Feedback", true);
	Label lblDialogo = new Label("");
	
	Color blanco = new Color(255, 255, 255);
	Color negro = new Color (30, 30, 30);
	Color gris = new Color (90, 90, 90);
	Color azul = new Color (80, 80, 255);
	
	Font Serif = new Font ("Serif", 0, 16);
	
	public VistaPerroModificar() {
		ventana.setLayout(gridbag);
		ventana.setBackground(negro);
		ventana.setFont(Utilidades.elegirFuente("fuentes/Merienda.ttf", 2, 16));
		ventana.setSize(500, 460);
		gbc.insets = new Insets (10, 10, 10, 10);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		lblTitulo.setForeground(blanco);
		ventana.add(lblTitulo, gbc);
		
		
		gbc.gridy = 1;
		choPerros.setFont(Serif);
		ventana.add(choPerros, gbc);
		gbc.gridwidth = 1;
	
		gbc.gridy = 2;
		lblNombre.setForeground(blanco);
		ventana.add(lblNombre, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		txtNombre.setFont(Serif);
		txtNombre.setBackground(gris);
		txtNombre.setForeground(blanco);
		ventana.add(txtNombre, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		lblRaza.setForeground(blanco);
		ventana.add(lblRaza, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 3;
		txtRaza.setFont(Serif);
		txtRaza.setBackground(gris);
		txtRaza.setForeground(blanco);
		ventana.add(txtRaza, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		lblTamano.setForeground(blanco);
		ventana.add(lblTamano, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 4;
		txtTamano.setFont(Serif);
		txtTamano.setBackground(gris);
		txtTamano.setForeground(blanco);
		ventana.add(txtTamano, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 5;
		lblColor.setForeground(blanco);
		ventana.add(lblColor, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 5;
		txtColor.setFont(Serif);
		txtColor.setBackground(gris);
		txtColor.setForeground(blanco);
		ventana.add(txtColor, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 6;
		lblDueno.setForeground(blanco);
		ventana.add(lblDueno, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 6;
		choDuenos.setFont(Serif);
		ventana.add(choDuenos, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.gridwidth = 2;
		btnMod.setForeground(blanco);
		btnMod.setBackground(azul);
		ventana.add(btnMod, gbc);
		
		diaFeedback.setLayout(new FlowLayout());
		diaFeedback.setSize(250, 180);
		diaFeedback.add(lblDialogo);
		diaFeedback.setLocationRelativeTo(null);
		diaFeedback.setResizable(false);
		diaFeedback.setVisible(false);
		
		ventana.setLocationRelativeTo(null);
		ventana.setResizable(false);
		ventana.setVisible(true);
	}
	
}

