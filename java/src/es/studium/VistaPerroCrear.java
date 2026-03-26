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

public class VistaPerroCrear {

	Frame ventana = new Frame("Perros");
	
	Label lblTitulo = new Label("Nuevo perro", Label.CENTER);
	Label lblNombre = new Label ("Nombre");
	TextField txtNombre = new TextField(20);
	Label lblRaza = new Label ("Raza");
	TextField txtRaza = new TextField(20);
	Label lblTamano = new Label ("Tamaño");
	Choice choTamano = new Choice();
	Label lblColor = new Label ("Color");
	TextField txtColor = new TextField (20);
	Label lblDueno = new Label ("Dueño");
	Choice choDuenos = new Choice();
	Label lblImagen = new Label ("Imagen");
	Button btnImagen = new Button ("Subir imagen");
	Label lblRutaImagen = new Label("Imagen sin elegir");
	
	Button btnCrear = new Button("Inscribir");
	
	GridBagLayout gridbag = new GridBagLayout();
	GridBagConstraints gbc = new GridBagConstraints();
	
	Dialog diaFeedback = new Dialog(ventana, "Aviso", true);
	Label lblDialogo = new Label("");
	
	Color blanco = new Color(255, 255, 255);
	Color negro = new Color (30, 30, 30);
	Color gris = new Color (90, 90, 90);
	Color azul = new Color (80, 80, 255);
	
	Font Serif = new Font ("Serif", 0, 16);
	
	public VistaPerroCrear() {
		
		ventana.setLayout(gridbag);
		ventana.setFont(Utilidades.elegirFuente("fuentes/Merienda.ttf", 2, 16));
		ventana.setBackground(negro);
		ventana.setSize(440, 480);
		gbc.insets = new Insets (10, 130, 10, 130);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		lblTitulo.setForeground(blanco);
		ventana.add(lblTitulo, gbc);
		gbc.gridwidth = 1;
	
		gbc.gridy = 1;
		gbc.insets.set(10, 130, 10, 10);
		lblNombre.setForeground(blanco);
		ventana.add(lblNombre, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.insets.set(10, 10, 10, 130);
		txtNombre.setFont(Serif);
		txtNombre.setBackground(gris);
		txtNombre.setForeground(blanco);
		ventana.add(txtNombre, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.insets.set(10, 130, 10, 10);
		lblRaza.setForeground(blanco);
		ventana.add(lblRaza, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.insets.set(10, 10, 10, 130);
		txtRaza.setFont(Serif);
		txtRaza.setBackground(gris);
		txtRaza.setForeground(blanco);
		ventana.add(txtRaza, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.insets.set(10, 130, 10, 10);
		lblTamano.setForeground(blanco);
		ventana.add(lblTamano, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.insets.set(10, 10, 10, 130);
		choTamano.add("-- Seleccione un Tamaño --");
		choTamano.add("Pequeño");
		choTamano.add("Mediano");
		choTamano.add("Grande");
		choTamano.setFont(Serif);
		choTamano.setBackground(gris);
		choTamano.setForeground(blanco);
		ventana.add(choTamano, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.insets.set(10, 130, 10, 10);
		lblColor.setForeground(blanco);
		ventana.add(lblColor, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.insets.set(10, 10, 10, 130);
		txtColor.setFont(Serif);
		txtColor.setBackground(gris);
		txtColor.setForeground(blanco);
		ventana.add(txtColor, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.insets.set(10, 130, 10, 10);
		lblDueno.setForeground(blanco);
		ventana.add(lblDueno, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 5;
		gbc.insets.set(10, 10, 10, 130);
		choDuenos.setFont(Serif);
		choDuenos.setBackground(gris);
		choDuenos.setForeground(blanco);
		ventana.add(choDuenos, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.insets.set(10, 130, 10, 10);
		lblImagen.setForeground(blanco);
		ventana.add(lblImagen, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.gridwidth = 2;
		gbc.insets.set(10, 130, 10, 130);
		lblRutaImagen.setForeground(blanco);
		ventana.add(lblRutaImagen, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 8;
		gbc.gridwidth = 2;
		gbc.insets.set(10, 130, 10, 130);
		btnCrear.setForeground(blanco);
		btnCrear.setBackground(azul);
		ventana.add(btnCrear, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1;
		gbc.insets.set(10, 10, 10, 130);
		gbc.gridx = 1;
		gbc.gridy = 6;
		btnImagen.setBackground(gris);
		btnImagen.setForeground(blanco);
		ventana.add(btnImagen, gbc);
		
		diaFeedback.setLayout(new FlowLayout());
		diaFeedback.setSize(250, 180);
		diaFeedback.setBackground(negro);
		lblDialogo.setForeground(blanco);
		diaFeedback.add(lblDialogo);
		diaFeedback.setLocationRelativeTo(null);
		diaFeedback.setResizable(false);
		diaFeedback.setVisible(false);
		
		ventana.setLocationRelativeTo(null);
		ventana.setResizable(false);
		ventana.setVisible(true);
	}
	
}
