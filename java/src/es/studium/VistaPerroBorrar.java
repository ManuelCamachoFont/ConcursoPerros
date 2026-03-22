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

public class VistaPerroBorrar {
	Frame ventana = new Frame("Concurso");
	Label lblTexto = new Label("¿Qué Perro desea eliminar?");
	Choice choPerros = new Choice();
	Button btnBorrar = new Button("Eliminar");
	
	Dialog dialogo = new Dialog(ventana, "¿Segur@?", true);
	Label lblDia = new Label("");
	Label lblDia2 = new Label("¿Estás seguro de eliminarlo?");
	Button btnDiaSi = new Button("Si");
	Button btnDiaNo = new Button("No");
	
	Dialog diaFeedback = new Dialog(ventana, "Confirmación", true);
	Label lblDialogo = new Label("");
	
	GridBagLayout gridbag = new GridBagLayout();
	GridBagConstraints gbc = new GridBagConstraints();
	
	Color blanco = new Color(255, 255, 255);
	Color negro = new Color (30, 30, 30);
	Color azul = new Color (80, 80, 255);
	
	Font Serif = new Font ("Serif", 0, 16);
	
	public VistaPerroBorrar() {
		ventana.setLayout(gridbag);
		ventana.setBackground(negro);
		ventana.setFont(Utilidades.elegirFuente("fuentes/Merienda.ttf", 2, 16));
		gbc.insets = new Insets(10, 10, 10, 10);

		gbc.gridx = 0;
		gbc.gridy = 0;
		lblTexto.setForeground(blanco);
		ventana.add(lblTexto, gbc);

		gbc.gridy = 1;
		choPerros.setFont(Serif);
		ventana.add(choPerros, gbc);

		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		btnBorrar.setBackground(azul);
		btnBorrar.setForeground(blanco);
		ventana.add(btnBorrar, gbc);

		ventana.setSize(500, 220);
		ventana.setLocationRelativeTo(null);
		ventana.setResizable(false);
		ventana.setVisible(true);
		
		dialogo.setSize(450, 198);
		dialogo.setLayout(gridbag);
		dialogo.setBackground(negro);
		dialogo.setFont(Utilidades.elegirFuente("fuentes/Merienda.ttf", 3, 16));

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridwidth = 4;
		gbc.anchor = GridBagConstraints.CENTER;
		lblDia.setFont(Serif);
		lblDia.setForeground(blanco);
		dialogo.add(lblDia, gbc);

		gbc.gridy = 1;
		lblDia2.setForeground(blanco);
		dialogo.add(lblDia2, gbc);
		gbc.gridwidth = 1;

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.SOUTHWEST;
		btnDiaSi.setBackground(new Color(180, 211, 178));
		dialogo.add(btnDiaSi, gbc);

		gbc.gridx = 3;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.SOUTHEAST;
		btnDiaNo.setBackground(new Color(243, 70, 74));
		dialogo.add(btnDiaNo, gbc);

		dialogo.setLocationRelativeTo(null);
		dialogo.setResizable(false);
		dialogo.setVisible(false);

		diaFeedback.setLayout(new FlowLayout());
		diaFeedback.add(lblDialogo);
		diaFeedback.setSize(300, 80);
		diaFeedback.setLocationRelativeTo(null);
		dialogo.setResizable(false);
		diaFeedback.setVisible(false);
	}
}
