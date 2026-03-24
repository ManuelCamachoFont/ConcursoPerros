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

public class VistaJuezModificar {

	Frame ventana = new Frame("Jueces");
	
	Label lblTitulo = new Label("Modificar juez", Label.CENTER);
	Choice choJueces = new Choice();
	Label lblNombre = new Label ("Nombre");
	TextField txtNombre = new TextField(20);
	Label lblApellidos = new Label ("Apellidos");
	TextField txtApellidos = new TextField(20);
	
	Button btnMod = new Button("Modificar");
	
	GridBagLayout gridbag = new GridBagLayout();
	GridBagConstraints gbc = new GridBagConstraints();
	
	Dialog diaFeedback = new Dialog(ventana, "Aviso", true);
	Label lblDialogo = new Label("");
	
	Color blanco = new Color(255, 255, 255);
	Color negro = new Color (30, 30, 30);
	Color gris = new Color (90, 90, 90);
	Color azul = new Color (80, 80, 255);
	
	Font Serif = new Font ("Serif", 0, 16);
	
	public VistaJuezModificar() {
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
		choJueces.setFont(Serif);
		choJueces.setForeground(blanco);
		choJueces.setBackground(gris);
		ventana.add(choJueces, gbc);
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
		lblApellidos.setForeground(blanco);
		ventana.add(lblApellidos, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 3;
		txtApellidos.setFont(Serif);
		txtApellidos.setBackground(gris);
		txtApellidos.setForeground(blanco);
		ventana.add(txtApellidos, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		btnMod.setForeground(blanco);
		btnMod.setBackground(azul);
		ventana.add(btnMod, gbc);
		
		diaFeedback.setLayout(new FlowLayout());
		diaFeedback.setSize(250, 180);
		diaFeedback.setForeground(blanco);
		lblDialogo.setBackground(negro);
		diaFeedback.add(lblDialogo);
		diaFeedback.setLocationRelativeTo(null);
		diaFeedback.setResizable(false);
		diaFeedback.setVisible(false);
		
		ventana.setLocationRelativeTo(null);
		ventana.setResizable(false);
		ventana.setVisible(true);
	}
	
}

