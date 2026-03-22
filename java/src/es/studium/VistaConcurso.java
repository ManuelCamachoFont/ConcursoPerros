package es.studium;

import java.awt.Button;
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

public class VistaConcurso {

	Frame ventana = new Frame("Concurso");
	
	Label lblTitulo = new Label("Lista de concursantes", Label.CENTER);
	Label lblOrden = new Label ("", Label.CENTER);
	Label lblPerro = new Label ("", Label.CENTER);
	Label lblDueno = new Label ("", Label.CENTER);
	Button btnPunt = new Button("Puntuar");
	Button btnSig = new Button ("Siguiente");
	Button btnFin = new Button ("Finalizar");
	
	Dialog diaFeedback = new Dialog (ventana, "Feedback", true);
	Label lblDialogo = new Label("");
	
	Dialog diaJ1 = new Dialog(ventana, "Presidente de mesa", false);
	Dialog diaJ2 = new Dialog(ventana, "Primer Adjunto", false);
	Dialog diaJ3 = new Dialog(ventana, "Segundo Adjunto", false);
	Dialog diaPunt = new Dialog(ventana, "Puntuación", true);
	Label lblTituloJ1 = new Label("", Label.CENTER);
	TextField txtVotoJ1 = new TextField (10);
	Button btnPuntJ1 = new Button("Puntuar");
	Label lblTituloJ2 = new Label("", Label.CENTER);
	TextField txtVotoJ2 = new TextField (10);
	Button btnPuntJ2 = new Button("Puntuar");
	Label lblTituloJ3 = new Label("", Label.CENTER);
	TextField txtVotoJ3 = new TextField (10);
	Button btnPuntJ3 = new Button("Puntuar");
	
	
	GridBagLayout gridbag = new GridBagLayout();
	GridBagConstraints gbc = new GridBagConstraints();
	
	Color blanco = new Color(255, 255, 255);
	Color negro = new Color (30, 30, 30);
	Color gris = new Color (90, 90, 90);
	Color azul = new Color (80, 80, 255);
	
	Font Serif = new Font ("Serif", 0, 16);
	
	public VistaConcurso() {
		ventana.setLayout(gridbag);
		ventana.setBackground(negro);
		ventana.setFont(Utilidades.elegirFuente("fuentes/Merienda.ttf", 1, 16));
		ventana.setSize(300, 350);
		
		gbc.insets = new Insets (10, 10, 10, 10);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		lblTitulo.setForeground(blanco);
		ventana.add(lblTitulo, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		lblOrden.setForeground(blanco);
		ventana.add(lblOrden, gbc);
		
		gbc.insets = new Insets (10, 10, 25, 10);
		gbc.gridx = 0;
		gbc.gridy = 2;
		lblPerro.setForeground(blanco);
		ventana.add(lblPerro, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		lblDueno.setForeground(blanco);
		ventana.add(lblDueno, gbc);
		
		gbc.insets = new Insets (25, 10, 10, 10);
		gbc.gridy = 4;
		gbc.gridwidth = 1;
		btnPunt.setForeground(blanco);
		btnPunt.setBackground(azul);
		ventana.add(btnPunt, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 4;
		btnSig.setForeground(blanco);
		btnSig.setBackground(azul);
		ventana.add(btnSig, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 4;
		btnFin.setForeground(blanco);
		btnFin.setBackground(azul);
		ventana.add(btnFin, gbc);
		btnFin.setVisible(false);
		
		ventana.setLocationRelativeTo(null);
		ventana.setResizable(false);
		ventana.setVisible(true);
		
		// Dialogos Jueces
	    
		lblTituloJ1.setForeground(blanco);
		lblTituloJ2.setForeground(blanco);
		lblTituloJ3.setForeground(blanco);
		
		diaJ2.setLayout(gridbag);
		diaJ2.setBackground(negro);
		diaJ2.setSize(230, 230);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		lblTituloJ2.setForeground(blanco);
		diaJ2.add(lblTituloJ2, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		txtVotoJ2.setBackground(gris);
		txtVotoJ2.setForeground(blanco);
		diaJ2.add(txtVotoJ2, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		btnPuntJ2.setBackground(azul);
		btnPuntJ2.setForeground(blanco);
		diaJ2.add(btnPuntJ2, gbc);
		diaJ2.setLocationRelativeTo(null);
	    int x = diaJ2.getLocation().x;
	    int y = diaJ2.getLocation().y;
		diaJ2.setResizable(false);
		diaJ2.setLocation(x, y + 300);
		diaJ2.setVisible(false);
		
		diaJ1.setLayout(gridbag);
		diaJ1.setBackground(negro);
		diaJ1.setSize(230, 230);
		
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		lblTituloJ1.setForeground(blanco);
		diaJ1.add(lblTituloJ1, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		txtVotoJ1.setBackground(gris);
		txtVotoJ1.setForeground(blanco);
		diaJ1.add(txtVotoJ1, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		btnPuntJ1.setBackground(azul);
		btnPuntJ1.setForeground(blanco);
		diaJ1.add(btnPuntJ1, gbc);
		diaJ1.setResizable(false);
		diaJ1.setLocation(x - diaJ2.getWidth() - 10, y + 300);
		diaJ1.setVisible(false);
		
		diaJ3.setLayout(gridbag);
		diaJ3.setBackground(negro);
		diaJ3.setSize(230, 230);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		lblTituloJ3.setForeground(blanco);
		diaJ3.add(lblTituloJ3, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		txtVotoJ3.setBackground(gris);
		txtVotoJ3.setForeground(blanco);
		diaJ3.add(txtVotoJ3, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		btnPuntJ3.setBackground(azul);
		btnPuntJ3.setForeground(blanco);
		diaJ3.add(btnPuntJ3, gbc);
		diaJ3.setResizable(false);
		diaJ3.setLocation(x + diaJ2.getWidth() + 10, y + 300);
		diaJ3.setVisible(false);
		
		// Dialogo errores
		
		diaFeedback.setLayout(new FlowLayout());
		diaFeedback.add(lblDialogo);
		diaFeedback.setSize(300, 80);
		diaFeedback.setLocationRelativeTo(null);
		diaFeedback.setResizable(false);
		diaFeedback.setVisible(false);
	}
	
}
