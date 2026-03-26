package es.studium;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Controlador extends WindowAdapter implements ActionListener, ItemListener {

	private VistaPrincipal v;
	private VistaPerrosMenu vpm;
	private VistaPerroCrear vpc;
	private VistaPerroModificar vpmod;
	private VistaPerroBorrar vpb;
	private VistaDuenosMenu vdm;
	private VistaDuenoCrear vdc;
	private VistaDuenoModificar vdmod;
	private VistaDuenoBorrar vdb;
	private VistaJuecesMenu vjm;
	private VistaJuezModificar vjmod;
	private VistaConcurso vc;
	private VistaConcursantes vcs;
	private VistaFin vf;
	private Modelo m;

	private List<String> perrosConcurso;
	private int indicePerro = 0;
	private int indicePerroC = 0;
	private float puntuacion = 0;
	private boolean votadoJ1 = false;
	private boolean votadoJ2 = false;
	private boolean votadoJ3 = false;

	public Controlador(VistaPrincipal vista, Modelo modelo) {

		this.v = vista;
		this.m = modelo;
		v.ventana.addWindowListener(this);
		v.btnPerros.addActionListener(this);
		v.btnDuenos.addActionListener(this);
		v.btnJueces.addActionListener(this);
		v.btnIni.addActionListener(this);
		v.btnLista.addActionListener(this);
		v.btnReglas.addActionListener(this);
		Utilidades.cursorBoton(v.btnPerros, v.btnDuenos, v.btnJueces, v.btnIni);
		Utilidades.aplicarIcono("recursos/iconos/paw.png", v.ventana);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Botón para acceder al Menú de los Perros desde el Menú Principal
		if (e.getSource().equals(v.btnPerros)) {
			if (vpm != null) {
				vpm.ventana.dispose();
			}
			vpm = new VistaPerrosMenu();
			Utilidades.cursorEspera(vpm.ventana);
			vpm.ventana.addWindowListener(this);
			vpm.btnCrear.addActionListener(this);
			vpm.btnMod.addActionListener(this);
			vpm.btnBorrar.addActionListener(this);
			Utilidades.cursorBoton(vpm.btnCrear, vpm.btnMod, vpm.btnBorrar);
			Utilidades.aplicarIcono("recursos/iconos/paw.png", vpm.ventana);
			Utilidades.cursorEstandar(vpm.ventana);
			// Botón para acceder a la creación de un Perro desde el Menú Perros
		} else if (vpm != null && e.getSource().equals(vpm.btnCrear)) {
			if (vpc != null) {
				vpc.ventana.dispose();
			}
			vpm.ventana.dispose();
			vpc = new VistaPerroCrear();
			Utilidades.cursorEspera(vpc.ventana);
			vpc.ventana.addWindowListener(this);
			vpc.diaFeedback.addWindowListener(this);
			vpc.btnImagen.addActionListener(this);
			vpc.btnCrear.addActionListener(this);
			Utilidades.cursorBoton(vpc.btnCrear);
			Utilidades.aplicarIcono("recursos/iconos/paw.png", vpc.ventana);

			try {
				vpc.choDuenos.add("-- Seleccione un dueño --");
				List<String> duenos = m.obtenerDuenos();
				for (String listaDuenos : duenos) {
					vpc.choDuenos.add(listaDuenos);
				}

			} catch (ClassNotFoundException cnfe) {
				System.err.println(cnfe.getMessage());
			} catch (SQLException se) {
				System.err.println(se.getMessage());
			}
			Utilidades.cursorEstandar(vpc.ventana);

		} else if (vpc != null && e.getSource().equals(vpc.btnImagen)) {
			Utilidades.cursorEspera(vpc.ventana);
			FileDialog fd = new FileDialog(vpc.ventana, "Seleccionar Imagen", FileDialog.LOAD);
			fd.setFilenameFilter(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".jpeg");
				}
			});
			fd.setVisible(true);
			if (fd.getFile() != null) {
				vpc.lblRutaImagen.setText(fd.getDirectory() + fd.getFile());
			}
			Utilidades.cursorEstandar(vpc.ventana);
		}

		// Botón crear para crear un Nuevo Perro desde la ventana de creación de Perros
		else if (vpc != null && e.getSource().equals(vpc.btnCrear)) {
			Utilidades.cursorEspera(vpc.ventana);
			String nombre = vpc.txtNombre.getText();
			String raza = vpc.txtRaza.getText();
			String color = vpc.txtColor.getText();
			String dueno = vpc.choDuenos.getSelectedItem();
			String idDueno = dueno.split(" \\| ")[0];
			String tamano = vpc.choTamano.getSelectedItem();
			String rutaImagen = vpc.lblRutaImagen.getText();
			String ruta;

			if (rutaImagen.equals("Sin imagen seleccionada")) {
				ruta = null;
			} else {
				ruta = rutaImagen;
			}

			try {
				if (!m.puedeCrearPerro(Integer.parseInt(idDueno))) {
					vpc.lblDialogo.setText("Este dueño ya tiene 3 perros registrados");
				} else if (!nombre.isEmpty() && !raza.isEmpty() && !color.isEmpty()
						&& vpc.choDuenos.getSelectedIndex() != 0 && vpc.choTamano.getSelectedIndex() != 0) {
					try {
						m.crearPerro(nombre, raza, tamano, color, ruta, idDueno);
						vpc.lblDialogo.setText("La inscripción de " + nombre + " se ha realizado con éxito.");
						vpc.txtNombre.setText("");
						vpc.txtRaza.setText("");
						vpc.txtColor.setText("");
					} catch (ClassNotFoundException cnfe) {
						vpc.lblDialogo.setText(cnfe.getMessage());

					} catch (SQLException se) {
						vpc.lblDialogo.setText(se.getMessage());
					} catch (FileNotFoundException fnfe) {
						vpc.lblDialogo.setText(fnfe.getMessage());
					}
				} else {
					vpc.lblDialogo.setText("Por favor, rellene todos los campos");
				}

			} catch (ClassNotFoundException cnfe) {
				vpc.lblDialogo.setText(cnfe.getMessage());

			} catch (NumberFormatException nfe) {
				vpc.lblDialogo.setText("Por favor, rellene todos los campos");
			} catch (SQLException se) {
				vpc.lblDialogo.setText(se.getMessage());
			} finally {
				vpc.diaFeedback.pack();
				vpc.diaFeedback.setVisible(true);
				Utilidades.cursorEstandar(vpc.ventana);
			}
			// Botón para acceder a la modificación de un Perro desde el Menú Perros
		} else if (vpm != null && e.getSource().equals(vpm.btnMod)) {
			vpm.ventana.dispose();
			if (vpmod != null) {
				vpmod.ventana.dispose();
			}
			vpmod = new VistaPerroModificar();
			Utilidades.cursorEspera(vpmod.ventana);
			vpmod.ventana.addWindowListener(this);
			vpmod.diaFeedback.addWindowListener(this);
			vpmod.btnMod.addActionListener(this);
			vpmod.btnImagen.addActionListener(this);
			vpmod.choPerros.addItemListener(this);
			Utilidades.cursorBoton(vpmod.btnMod);
			Utilidades.aplicarIcono("recursos/iconos/paw.png", vpmod.ventana);

			try {
				vpmod.choPerros.add("-- Seleccione un Perro --");
				List<String> perros = m.obtenerPerros();
				for (String listaPerros : perros) {
					vpmod.choPerros.add(listaPerros);
				}

			} catch (ClassNotFoundException cnfe) {
				System.err.println(cnfe.getMessage());
			} catch (SQLException se) {
				System.err.println(se.getMessage());
			}

			try {
				vpmod.choDuenos.add("-- Seleccione un dueño --");
				List<String> duenos = m.obtenerDuenos();
				for (String listaDuenos : duenos) {
					vpmod.choDuenos.add(listaDuenos);
				}

			} catch (ClassNotFoundException cnfe) {
				System.err.println(cnfe.getMessage());
			} catch (SQLException se) {
				System.err.println(se.getMessage());
			}
			Utilidades.cursorEstandar(vpmod.ventana);

			// Botón para efectuar la modificación de un Perro
		} else if (vpmod != null && e.getSource().equals(vpmod.btnMod)) {
			try {
				Utilidades.cursorEspera(vpmod.ventana);
				String nombre = vpmod.txtNombre.getText();
				String raza = vpmod.txtRaza.getText();
				String tamano = vpmod.choTamano.getSelectedItem();
				String color = vpmod.txtColor.getText();
				String dueno = vpmod.choDuenos.getSelectedItem();
				String perro = vpmod.choPerros.getSelectedItem();
				int idDueno = Integer.parseInt(dueno.split(" \\| ")[0]);
				int idPerro = Integer.parseInt(perro.split(" \\| ")[0]);
				String rutaImagen = vpmod.lblRutaImagen.getText();
				String ruta;

				if (rutaImagen.equals("Sin imagen seleccionada")) {
					ruta = null;
				} else {
					ruta = rutaImagen;
				}
				if (vpmod.choDuenos.getSelectedIndex() != 0 && vpmod.choPerros.getSelectedIndex() != 0
						&& vpmod.choTamano.getSelectedIndex() != 0 && !nombre.isEmpty() && !raza.isEmpty()
						&& !color.isEmpty()) {
					m.modificarPerro(nombre, raza, tamano, color, ruta, idDueno, idPerro);
					vpmod.lblDialogo.setText("Se ha modificado a " + perro.split(" \\| ")[1] + ", ahora es " + nombre
							+ " \\| " + raza + " \\| " + tamano + " \\| " + color + " \\| " + idDueno);
				} else {
					vpmod.lblDialogo.setText("Por favor, rellene todos los campos");
				}
			} catch (ClassNotFoundException cnfe) {
				vpmod.lblDialogo.setText(cnfe.getMessage());
			} catch (NumberFormatException nfe) {
				vpmod.lblDialogo.setText("Por favor, rellene todos los campos");
			} catch (SQLException se) {
				vpmod.lblDialogo.setText(se.getMessage());
			} catch (FileNotFoundException fnfe) {
				vpmod.lblDialogo.setText(fnfe.getMessage());
			} finally {
				vpmod.diaFeedback.pack();
				vpmod.diaFeedback.setVisible(true);
			}
			vpmod.choPerros.removeAll();
			vpmod.choDuenos.removeAll();
			try {
				vpmod.choPerros.add("-- Seleccione un perro --");
				List<String> perros = m.obtenerPerros();
				for (String listaPerros : perros) {
					vpmod.choPerros.add(listaPerros);
				}

			} catch (ClassNotFoundException cnfe) {
				System.err.println(cnfe.getMessage());
			} catch (SQLException se) {
				System.err.println(se.getMessage());
			}

			try {
				vpmod.choDuenos.add("-- Seleccione un dueño --");
				List<String> duenos = m.obtenerDuenos();
				for (String listaDuenos : duenos) {
					vpmod.choDuenos.add(listaDuenos);
				}

			} catch (ClassNotFoundException cnfe) {
				System.err.println(cnfe.getMessage());
			} catch (SQLException se) {
				System.err.println(se.getMessage());
			}
			vpmod.txtNombre.setText("");
			vpmod.txtRaza.setText("");
			vpmod.choTamano.select(0);
			vpmod.txtColor.setText("");
			vpmod.choDuenos.select(0);
			vpmod.choPerros.select(0);
			Utilidades.cursorEstandar(vpmod.ventana);

		} else if (vpmod != null && e.getSource().equals(vpmod.btnImagen)) {
			Utilidades.cursorEspera(vpmod.ventana);
			FileDialog fd = new FileDialog(vpmod.ventana, "Seleccionar Imagen", FileDialog.LOAD);
			fd.setFilenameFilter(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".jpeg");
				}
			});
			fd.setVisible(true);
			if (fd.getFile() != null) {
				vpmod.lblRutaImagen.setText(fd.getDirectory() + fd.getFile());
			}
			Utilidades.cursorEstandar(vpmod.ventana);
		}

		// Botón para acceder al borrado de los perros
		else if (vpm != null && e.getSource().equals(vpm.btnBorrar)) {
			vpm.ventana.dispose();
			if (vpb != null) {
				vpb.ventana.dispose();
			}
			vpb = new VistaPerroBorrar();
			Utilidades.cursorEspera(vpb.ventana);
			vpb.ventana.addWindowListener(this);
			vpb.btnBorrar.addActionListener(this);
			vpb.btnDiaSi.addActionListener(this);
			vpb.btnDiaNo.addActionListener(this);
			vpb.dialogo.addWindowListener(this);
			vpb.diaFeedback.addWindowListener(this);
			Utilidades.aplicarIcono("recursos/iconos/paw.png", vpb.ventana);
			Utilidades.cursorBoton(vpb.btnBorrar, vpb.btnDiaSi, vpb.btnDiaNo);
			try {
				vpb.choPerros.add("-- Seleccione un Perro --");
				List<String> perros = m.obtenerPerros();
				for (String listaPerros : perros) {
					vpb.choPerros.add(listaPerros);
				}
			} catch (ClassNotFoundException cnfe) {
				System.err.println(cnfe.getMessage());
			} catch (SQLException se) {
				System.err.println(se.getMessage());
			}
			Utilidades.cursorEstandar(vpb.ventana);

		} else if ((vpb != null && vpb.choPerros.getSelectedIndex() != 0) && e.getSource().equals(vpb.btnBorrar)) {
			String perro = vpb.choPerros.getSelectedItem();
			vpb.lblDia.setText("Vas a eliminar a " + perro);
			vpb.dialogo.setVisible(true);
		}
		// Botón Sí de confirmar borrado
		else if ((vpb != null && vpb.choPerros.getSelectedIndex() != 0) && e.getSource().equals(vpb.btnDiaSi)) {
			Utilidades.cursorEspera(vpb.ventana);
			int idPerro = Integer.parseInt(vpb.choPerros.getSelectedItem().split(" \\| ")[0]);
			try {
				vpb.dialogo.dispose();
				m.borrarP(idPerro);
				vpb.lblDialogo.setText("Se ha eliminado a " + vpb.choPerros.getSelectedItem() + " del concurso.");
				vpb.choPerros.removeAll();
				vpb.choPerros.add("-- Seleccione un Perro --");
				List<String> perros = m.obtenerPerros();
				for (String p : perros)
					vpb.choPerros.add(p);
			} catch (ClassNotFoundException cnfe) {
				vpb.lblDialogo.setText(cnfe.getMessage());
			} catch (SQLException se) {
				vpb.lblDialogo.setText(se.getMessage());
			}
			vpb.diaFeedback.pack();
			vpb.diaFeedback.setVisible(true);
			Utilidades.cursorEstandar(vpb.ventana);
		}
		// Botón No de confirmar borrado
		else if (vpb != null && e.getSource().equals(vpb.btnDiaNo)) {
			vpb.dialogo.dispose();
		}
		// Botón para acceder al Menú de los Dueños desde el Menú Principal
		else if (e.getSource().equals(v.btnDuenos)) {
			if (vdm != null) {
				vdm.ventana.dispose();
			}
			vdm = new VistaDuenosMenu();
			Utilidades.cursorEspera(vdm.ventana);
			vdm.ventana.addWindowListener(this);
			vdm.btnCrear.addActionListener(this);
			vdm.btnMod.addActionListener(this);
			vdm.btnBorrar.addActionListener(this);
			Utilidades.aplicarIcono("recursos/iconos/paw.png", vdm.ventana);
			Utilidades.cursorBoton(vdm.btnCrear, vdm.btnMod, vdm.btnBorrar);
			Utilidades.cursorEstandar(vdm.ventana);
			// Botón para acceder a la creación de un Dueño desde el Menú Dueños
		} else if (vdm != null && e.getSource().equals(vdm.btnCrear)) {
			if (vdc != null) {
				vdc.ventana.dispose();
			}
			vdm.ventana.dispose();
			vdc = new VistaDuenoCrear();
			Utilidades.cursorEspera(vdc.ventana);
			vdc.ventana.addWindowListener(this);
			vdc.diaFeedback.addWindowListener(this);
			vdc.btnCrear.addActionListener(this);
			Utilidades.aplicarIcono("recursos/iconos/paw.png", vdc.ventana);
			Utilidades.cursorBoton(vdc.btnCrear);
			Utilidades.cursorEstandar(vdc.ventana);
			// Botón para crear un Nuevo Dueño desde la ventana de creación de Dueños
		} else if (vdc != null && e.getSource().equals(vdc.btnCrear)) {
			Utilidades.cursorEspera(vdc.ventana);
			String nombre = vdc.txtNombre.getText();
			String apellidos = vdc.txtApellidos.getText();
			try {
				if (!m.puedeCrearDueno()) {
					vdc.lblDialogo.setText("Ya hay 5 dueños registrados, no se pueden añadir más");
				} else if (!nombre.isEmpty() && !apellidos.isEmpty()) {
					m.crearDueno(nombre, apellidos);
					vdc.lblDialogo.setText("La inscripción de " + nombre + apellidos + " se ha realizado con éxito.");
				} else {
					vdc.lblDialogo.setText("Por favor, rellene todos los campos");
				}
			} catch (ClassNotFoundException cnfe) {
				vdc.lblDialogo.setText(cnfe.getMessage());
			} catch (SQLException se) {

				vdc.lblDialogo.setText(se.getMessage());
			} finally {
				vdc.diaFeedback.pack();
				vdc.diaFeedback.setVisible(true);
				Utilidades.cursorEstandar(vdc.ventana);
			}

		}
		// Botón para acceder a la modificación de un Dueño desde el Menú Dueños
		else if (vdm != null && e.getSource().equals(vdm.btnMod)) {
			if (vdmod != null) {
				vdmod.ventana.dispose();
			}
			vdm.ventana.dispose();
			vdmod = new VistaDuenoModificar();
			Utilidades.cursorEspera(vdmod.ventana);
			vdmod.ventana.addWindowListener(this);
			vdmod.diaFeedback.addWindowListener(this);
			vdmod.btnMod.addActionListener(this);
			vdmod.choDuenos.addItemListener(this);
			Utilidades.cursorBoton(vdmod.btnMod);
			Utilidades.aplicarIcono("recursos/iconos/paw.png", vdmod.ventana);

			try {
				vdmod.choDuenos.add("-- Seleccione un dueño --");
				List<String> duenos = m.obtenerDuenos();
				for (String listaDuenos : duenos) {
					vdmod.choDuenos.add(listaDuenos);
				}

			} catch (ClassNotFoundException cnfe) {
				System.err.println(cnfe.getMessage());
			} catch (SQLException se) {
				System.err.println(se.getMessage());
			}
			Utilidades.cursorEstandar(vdmod.ventana);
		}

		// Botón para llevar a cabo la modificación de un Dueño
		else if (vdmod != null && e.getSource().equals(vdmod.btnMod)) {
			Utilidades.cursorEspera(vdmod.ventana);
			try {
				
				String nombre = vdmod.txtNombre.getText();
				String apellidos = vdmod.txtApellidos.getText();
				String dueno = vdmod.choDuenos.getSelectedItem();
				int idDueno = Integer.parseInt(dueno.split(" \\| ")[0]);
				if (!nombre.isEmpty() && !apellidos.isEmpty() && vdmod.choDuenos.getSelectedIndex() != 0) {
					m.modificarDueno(nombre, apellidos, idDueno);
					vdmod.lblDialogo.setText(
							"Se ha modificado a " + dueno.split(" \\| ")[1] + " " + dueno.split(" \\| ")[2] + ", ahora es " + nombre + " " + apellidos);
				} else {
					vdmod.lblDialogo.setText("Por favor, rellene todos los campos");
				}
			} catch (ClassNotFoundException cnfe) {
				vdmod.lblDialogo.setText(cnfe.getMessage());
			} catch (NumberFormatException nfe) {
				vdmod.lblDialogo.setText("Por favor, rellene todos los campos");
			} catch (SQLException se) {
				vdmod.lblDialogo.setText(se.getMessage());
			} 
			vdmod.choDuenos.removeAll();
			try {
				vdmod.choDuenos.add("-- Seleccione un dueño --");
				List<String> duenos = m.obtenerDuenos();
				for (String listaDuenos : duenos) {
					vdmod.choDuenos.add(listaDuenos);
				}

			} catch (ClassNotFoundException cnfe) {
				System.err.println(cnfe.getMessage());
			} catch (SQLException se) {
				System.err.println(se.getMessage());
			}
			vdmod.diaFeedback.pack();
			vdmod.diaFeedback.setVisible(true);
			Utilidades.cursorEstandar(vdmod.ventana);
		}

		else if (vdm != null && e.getSource().equals(vdm.btnBorrar)) {
			if (vdb != null) {
				vdb.ventana.dispose();
			}
			vdm.ventana.dispose();
			vdb = new VistaDuenoBorrar();
			Utilidades.cursorEspera(vdb.ventana);
			vdb.ventana.addWindowListener(this);
			vdb.btnBorrar.addActionListener(this);
			vdb.btnDiaSi.addActionListener(this);
			vdb.btnDiaNo.addActionListener(this);
			vdb.dialogo.addWindowListener(this);
			vdb.diaFeedback.addWindowListener(this);
			Utilidades.aplicarIcono("recursos/iconos/paw.png", vdb.ventana);
			Utilidades.cursorBoton(vdb.btnBorrar, vdb.btnDiaSi, vdb.btnDiaNo);
			try {
				vdb.choDuenos.add("-- Seleccione un Dueño --");
				List<String> duenos = m.obtenerDuenos();
				for (String listaDuenos : duenos) {
					vdb.choDuenos.add(listaDuenos);
				}
			} catch (ClassNotFoundException cnfe) {
				System.err.println(cnfe.getMessage());
			} catch (SQLException se) {
				System.err.println(se.getMessage());
			}
			Utilidades.cursorEstandar(vdb.ventana);

		} else if ((vdb != null && vdb.choDuenos.getSelectedIndex() != 0) && e.getSource().equals(vdb.btnBorrar)) {
			String dueno = vdb.choDuenos.getSelectedItem();
			vdb.lblDia.setText("Vas a eliminar a " + dueno);
			vdb.dialogo.setVisible(true);
		}
		// Botón Sí de confirmar borrado
		else if ((vdb != null && vdb.choDuenos.getSelectedIndex() != 0) && e.getSource().equals(vdb.btnDiaSi)) {
			Utilidades.cursorEspera(vdb.ventana);
			int idDueno = Integer.parseInt(vdb.choDuenos.getSelectedItem().split(" \\| ")[0]);
			try {
				vdb.dialogo.dispose();
				m.borrarD(idDueno);
				vdb.choDuenos.removeAll();
				vdb.choDuenos.add("-- Seleccione un Dueño --");
				List<String> duenos = m.obtenerDuenos();
				for (String d : duenos)
					vdb.choDuenos.add(d);
				vdb.lblDialogo.setText("Se ha eliminado a " + vdb.choDuenos.getSelectedItem() + " del concurso");
			} catch (ClassNotFoundException cnfe) {
				vdb.lblDialogo.setText(cnfe.getMessage());
			} catch (SQLException se) {
				vdb.lblDialogo.setText(se.getMessage());
			}
			vdb.diaFeedback.setVisible(true);
			Utilidades.cursorEstandar(vdb.ventana);
		}
		// Botón No de confirmar borrado
		else if (vdb != null && e.getSource().equals(vdb.btnDiaNo)) {
			vdb.dialogo.dispose();
		} else if (e.getSource().equals(v.btnJueces)) {
			if (vjm != null) {
				vjm.ventana.dispose();
			}
			vjm = new VistaJuecesMenu();
			Utilidades.cursorEspera(vjm.ventana);
			vjm.ventana.addWindowListener(this);
			vjm.btnMod.addActionListener(this);
			Utilidades.aplicarIcono("recursos/iconos/paw.png", vjm.ventana);
			Utilidades.cursorBoton(vjm.btnMod);
			Utilidades.cursorEstandar(vjm.ventana);
		}
		// Botón para acceder al Menú de los Jueces desde el Menú Principal
		else if (vjm != null && e.getSource().equals(vjm.btnMod)) {
			if (vjmod != null) {
				vjmod.ventana.dispose();
			}
			vjm.ventana.dispose();
			vjmod = new VistaJuezModificar();
			Utilidades.cursorEspera(vjmod.ventana);
			vjmod.ventana.addWindowListener(this);
			vjmod.diaFeedback.addWindowListener(this);
			vjmod.btnMod.addActionListener(this);
			vjmod.choJueces.addItemListener(this);
			Utilidades.cursorBoton(vjmod.btnMod);
			Utilidades.aplicarIcono("recursos/iconos/paw.png", vjmod.ventana);

			try {
				vjmod.choJueces.add("-- Seleccione un Juez --");
				List<String> jueces = m.obtenerJueces();
				for (String listaJueces : jueces) {
					vjmod.choJueces.add(listaJueces);
				}

			} catch (ClassNotFoundException cnfe) {
				System.err.println(cnfe.getMessage());
			} catch (SQLException se) {
				System.err.println(se.getMessage());
			}
			Utilidades.cursorEstandar(vjmod.ventana);
		}

		else if (vjmod != null && e.getSource().equals(vjmod.btnMod)) {
			try {
				Utilidades.cursorEspera(vjmod.ventana);
				String nombre = vjmod.txtNombre.getText();
				String apellidos = vjmod.txtApellidos.getText();
				String juez = vjmod.choJueces.getSelectedItem();
				int idJuez = Integer.parseInt(juez.split(" \\| ")[0]);
				if (!nombre.isEmpty() && !apellidos.isEmpty() && vjmod.choJueces.getSelectedIndex() != 0) {
					m.modificarJuez(nombre, apellidos, idJuez);
					vjmod.lblDialogo.setText("Se ha modificado a " + juez + ", ahora es " + nombre + " " + apellidos);
				} else {
					vjmod.lblDialogo.setText("Por favor, rellene todos los campos");
				}
			} catch (ClassNotFoundException cnfe) {
				vjmod.lblDialogo.setText(cnfe.getMessage());
			} catch (NumberFormatException nfe) {
				vjmod.lblDialogo.setText("Por favor, rellene todos los campos");
			} catch (SQLException se) {
				vjmod.lblDialogo.setText(se.getMessage());
			} finally {
				vjmod.diaFeedback.pack();
				vjmod.diaFeedback.setVisible(true);
			}
			vjmod.choJueces.removeAll();
			try {
				vjmod.choJueces.add("-- Seleccione un juez --");
				List<String> jueces = m.obtenerJueces();
				for (String listaJueces : jueces) {
					vjmod.choJueces.add(listaJueces);
				}

			} catch (ClassNotFoundException cnfe) {
				System.err.println(cnfe.getMessage());
			} catch (SQLException se) {
				System.err.println(se.getMessage());
			}

			Utilidades.cursorEstandar(vjmod.ventana);
		}
		// Botón para Iniciar el Concurso desde el Menú Principal
		else if (e.getSource().equals(v.btnIni)) {
			indicePerro = 0;
			puntuacion = 0;
			votadoJ1 = false;
			votadoJ2 = false;
			votadoJ3 = false;
			if (vc != null) {
				vc.ventana.dispose();
			}
			vc = new VistaConcurso();
			try {
				perrosConcurso = m.obtenerPerros();
				if (perrosConcurso.isEmpty()) {
					vc.lblPerro.setText("No hay perros registrados");
					vc.lblDueno.setText("");
					vc.lblOrden.setText("");
					vc.btnPunt.setEnabled(false);
					vc.btnSig.setVisible(false);
					vc.btnFin.setVisible(false);
				} else {
					String perro = perrosConcurso.get(indicePerro);
					vc.lblOrden.setText("Perro " + (indicePerro + 1) + " de " + perrosConcurso.size());
					vc.lblPerro.setText("Nombre: " + perro.split(" \\| ")[1].trim());
					int idDuenoFK = Integer.parseInt(perro.split(" \\| ")[5].trim());
					String dueno = m.buscarD(idDuenoFK);
					vc.lblDueno.setText("Dueño: " + dueno.split(" \\| ")[1].trim());
					String juez1 = m.buscarJ(1);
					String juez2 = m.buscarJ(2);
					String juez3 = m.buscarJ(3);
					vc.lblTituloJ1.setText(juez1.split(" \\| ")[1] + " " + juez1.split(" \\| ")[2]);
					vc.lblTituloJ2.setText(juez2.split(" \\| ")[1] + " " + juez2.split(" \\| ")[2]);
					vc.lblTituloJ3.setText(juez3.split(" \\| ")[1] + " " + juez3.split(" \\| ")[2]);

					if (indicePerro == perrosConcurso.size() - 1) {
						vc.btnSig.setVisible(false);
						vc.btnFin.setVisible(true);
					} else {
						vc.btnSig.setVisible(true);
						vc.btnFin.setVisible(false);
					}
				}
			} catch (ClassNotFoundException cnfe) {

				System.err.println(cnfe);
				;
			} catch (SQLException se) {

				System.err.println(se);
				;
			}
			Utilidades.cursorEspera(vc.ventana);
			vc.ventana.addWindowListener(this);
			vc.btnPunt.addActionListener(this);
			vc.btnSig.addActionListener(this);
			vc.btnFin.addActionListener(this);
			vc.diaFeedback.addWindowListener(this);
			vc.diaJ3.addWindowListener(this);
			vc.diaJ2.addWindowListener(this);
			vc.diaJ1.addWindowListener(this);
			vc.btnPuntJ3.addActionListener(this);
			vc.btnPuntJ2.addActionListener(this);
			vc.btnPuntJ1.addActionListener(this);
			Utilidades.aplicarIcono("recursos/iconos/paw.png", vc.ventana);
			Utilidades.cursorBoton(vc.btnPunt, vc.btnSig, vc.btnPuntJ1, vc.btnPuntJ2, vc.btnPuntJ3, vc.btnFin);
			Utilidades.cursorEstandar(vc.ventana);
			vc.ventana.validate();
			vc.ventana.repaint();
		} else if (vc != null && e.getSource().equals(vc.btnPunt)) {
			vc.btnPunt.setEnabled(false);
			vc.btnSig.setEnabled(false);
			vc.btnFin.setEnabled(false);
			vc.diaJ3.setVisible(true);
			vc.diaJ2.setVisible(true);
			vc.diaJ1.setVisible(true);
		}

		else if (vc != null && e.getSource().equals(vc.btnPuntJ1)) {
			try {
				String perro = perrosConcurso.get(indicePerro);
				int idPerro = Integer.parseInt(perro.split(" \\| ")[0].trim());
				float puntuacion1 = Float.parseFloat(vc.txtVotoJ1.getText());
				if (!votadoJ1 && puntuacion1 >= 0 && puntuacion1 <= 10) {
					puntuacion = puntuacion + puntuacion1;
					m.puntuarPerro(idPerro, puntuacion);
					vc.lblDialogo.setText("El Presidente de mesa ha votado con éxito");
					votadoJ1 = true;
					vc.diaJ1.dispose();
				} else if (votadoJ1) {
					vc.lblDialogo.setText("El Presidente ya ha votado");
					vc.diaJ1.dispose();
				} else {
					vc.lblDialogo.setText("Escriba un número entre el 0 y el 10");
				}
			} catch (NumberFormatException nfe) {
				vc.lblDialogo.setText("Escriba un número entre el 0 y el 10");
			} catch (ClassNotFoundException cnfe) {

				System.err.println(cnfe);
			} catch (SQLException se) {

				System.err.println(se);
			}
			vc.diaFeedback.pack();
			vc.diaFeedback.setVisible(true);
			vc.txtVotoJ1.setText("");

		}

		else if (vc != null && e.getSource().equals(vc.btnPuntJ2)) {
			try {
				String perro = perrosConcurso.get(indicePerro);
				int idPerro = Integer.parseInt(perro.split(" \\| ")[0].trim());
				float puntuacion2 = Float.parseFloat(vc.txtVotoJ2.getText());
				if (!votadoJ2 && puntuacion2 >= 0 && puntuacion2 <= 10) {
					puntuacion = puntuacion + puntuacion2;
					m.puntuarPerro(idPerro, puntuacion);
					vc.lblDialogo.setText("El Adjunto 1 ha votado con éxito");
					votadoJ2 = true;
					vc.diaJ2.dispose();
				} else if (votadoJ2) {
					vc.lblDialogo.setText("El Adjunto 1 ya ha votado");
					vc.diaJ2.dispose();
				} else {
					vc.lblDialogo.setText("Escriba un número entre el 0 y el 10");
				}
			} catch (NumberFormatException nfe) {
				vc.lblDialogo.setText("Escriba un número entre el 0 y el 10");
			} catch (ClassNotFoundException cnfe) {

				System.err.println(cnfe);
			} catch (SQLException se) {

				System.err.println(se);
			}
			vc.diaFeedback.pack();
			vc.diaFeedback.setVisible(true);
			vc.txtVotoJ2.setText("");
		}

		else if (vc != null && e.getSource().equals(vc.btnPuntJ3)) {
			try {
				String perro = perrosConcurso.get(indicePerro);
				int idPerro = Integer.parseInt(perro.split(" \\| ")[0].trim());
				float puntuacion3 = Float.parseFloat(vc.txtVotoJ3.getText());
				if (!votadoJ3 && puntuacion3 >= 0 && puntuacion3 <= 10) {
					puntuacion = puntuacion + puntuacion3;
					m.puntuarPerro(idPerro, puntuacion);
					vc.lblDialogo.setText("El Adjunto 1 ha votado con éxito");
					votadoJ3 = true;
					vc.diaJ3.dispose();
				} else if (votadoJ3) {
					vc.lblDialogo.setText("El Adjunto 1 ya ha votado");
					vc.diaJ3.dispose();
				} else {
					vc.lblDialogo.setText("Escriba un número entre el 0 y el 10");
				}
			} catch (NumberFormatException nfe) {
				vc.lblDialogo.setText("Escriba un número entre el 0 y el 10");
			} catch (ClassNotFoundException cnfe) {
				System.err.println(cnfe);
			} catch (SQLException se) {

				System.err.println(se);
			}
			vc.diaFeedback.pack();
			vc.diaFeedback.setVisible(true);
			vc.txtVotoJ3.setText("");
		}

		else if (vc != null && e.getSource().equals(vc.btnSig)) {
			try {
				puntuacion = 0;
				votadoJ1 = false;
				votadoJ2 = false;
				votadoJ3 = false;
				indicePerro = indicePerro + 1;
				String perro = perrosConcurso.get(indicePerro);
				vc.lblOrden.setText("Perro " + (indicePerro + 1) + " de " + perrosConcurso.size());
				vc.lblPerro.setText("Nombre: " + perro.split(" \\| ")[1].trim());
				int idDuenoFK = Integer.parseInt(perro.split(" \\| ")[5].trim());
				String dueno = m.buscarD(idDuenoFK);
				vc.lblDueno.setText("Dueño: " + dueno.split(" \\| ")[1].trim());
			} catch (ClassNotFoundException cnfe) {
				System.err.println(cnfe);
			} catch (SQLException se) {
				System.err.println(se);
			}

			if (indicePerro == perrosConcurso.size() - 1) {
				vc.btnSig.setVisible(false);
				vc.btnFin.setVisible(true);
			} else {
				vc.btnSig.setVisible(true);
				vc.btnFin.setVisible(false);
			}
			vc.ventana.validate();
			vc.ventana.repaint();
		}

		else if (vc != null && e.getSource().equals(vc.btnFin)) {
			if (vf != null) {
				vf.ventana.dispose();
			}
			vc.ventana.dispose();
			vf = new VistaFin();
			Utilidades.cursorEspera(vf.ventana);
			vf.ventana.addWindowListener(this);
			vf.btnReiniciarPunt.addActionListener(this);
			vf.btnReiniciar.addActionListener(this);
			Utilidades.aplicarIcono("recursos/iconos/paw.png", vf.ventana);
			try {
				List<String> resultados = m.obtenerResultados();
				String texto = "";
				int posicion = 1;
				for (String r : resultados) {
					String[] datos = r.split(" \\| ");
					texto += posicion + ". " + datos[0] + " - Puntuación: " + datos[1] + " - Dueño: " + datos[2] + " "
							+ datos[3] + "\n";
					posicion++;
				}

				float puntosTop1 = Float.parseFloat(resultados.get(0).split(" \\| ")[1]);
				float puntosTop2 = Float.parseFloat(resultados.get(1).split(" \\| ")[1]);

				if (puntosTop1 == puntosTop2) {
					vf.txtResultados.setText(texto);
					vf.lblGanador.setText("Se ha dado un empate");
				} else {
					String[] ganador = resultados.get(0).split(" \\| ");
					vf.txtResultados.setText(texto);
					vf.lblGanador.setText(ganador[0] + " - " + ganador[2] + " " + ganador[3]);
				}

			} catch (ClassNotFoundException cnfe) {
				System.err.println(cnfe);
			} catch (SQLException ex) {
				System.err.println(ex);
			}
			Utilidades.cursorBoton(vf.btnReiniciar);
			Utilidades.cursorBoton(vf.btnReiniciarPunt);
			Utilidades.cursorEstandar(vf.ventana);
			vf.ventana.validate();
			vf.ventana.repaint();
		} else if(vf !=null && e.getSource().equals(vf.btnReiniciar)){
			vf.dialogo.setVisible(true);
		}
		else if (vf != null && e.getSource().equals(vf.btnDiaSi)) {
			try {
				m.reiniciarConcurso();
				vf.ventana.dispose();
				indicePerro = 0;
				puntuacion = 0;
				votadoJ1 = false;
				votadoJ2 = false;
				votadoJ3 = false;
				perrosConcurso = null;
			} catch (ClassNotFoundException cnfe) {
				System.err.println(cnfe);
			} catch (SQLException se) {
				System.err.println(se);
			}
		} else if(vf!= null && e.getSource().equals(vf.btnDiaNo)) {
			vf.dialogo.dispose();
		} else if (vf != null && e.getSource().equals(vf.btnReiniciarPunt)) {
			try {
				m.reiniciarPuntuaciones();
				vf.ventana.dispose();
				indicePerroC = 0;
				puntuacion = 0;
				votadoJ1 = false;
				votadoJ2 = false;
				votadoJ3 = false;

			} catch (ClassNotFoundException cnfe) {
				System.err.println(cnfe);
			} catch (SQLException se) {
				System.err.println(se);
			}
		} else if (e.getSource().equals(v.btnLista)) {
			if (vcs != null) {
				vcs.ventana.dispose();
			}
			vcs = new VistaConcursantes();
			Utilidades.cursorEspera(vcs.ventana);
			try {
				perrosConcurso = m.obtenerPerros();
				if (!perrosConcurso.isEmpty()) {
					String perro = perrosConcurso.get(indicePerroC);
					vcs.lblNombre.setText("Nombre: " + perro.split(" \\| ")[1].trim());
					int idPerro = Integer.parseInt(perro.split(" \\| ")[0].trim());
					byte[] bytes = m.obtenerImagenPerro(idPerro);
					if (bytes != null) {
						vcs.imagen = ImageIO.read(new ByteArrayInputStream(bytes));

					}
					vcs.canvasImagen.repaint();
				}
			} catch (ClassNotFoundException cnfe) {

				System.err.println(cnfe);
				;
			} catch (SQLException se) {

				System.err.println(se);
				;
			} catch (IOException ioe) {

				System.err.println(ioe);
			}
			vcs.ventana.addWindowListener(this);
			vcs.btnSiguiente1.addActionListener(this);
			vcs.btnSiguiente2.addActionListener(this);
			vcs.btnVolver1.addActionListener(this);
			vcs.btnVolver2.addActionListener(this);
			Utilidades.aplicarIcono("recursos/iconos/paw.png", vcs.ventana);
			Utilidades.cursorBoton(vcs.btnSiguiente1, vcs.btnSiguiente2, vcs.btnVolver1, vcs.btnVolver2);
			Utilidades.cursorEstandar(vcs.ventana);
		} else if (vcs != null
				&& (e.getSource().equals(vcs.btnSiguiente1) || e.getSource().equals(vcs.btnSiguiente2))) {
			indicePerroC = indicePerroC + 1;
			try {
				perrosConcurso = m.obtenerPerros();
				if (!perrosConcurso.isEmpty()) {
					String perro = perrosConcurso.get(indicePerroC);
					vcs.lblNombre.setText("Nombre: " + perro.split(" \\| ")[1].trim());
					int idPerro = Integer.parseInt(perro.split(" \\| ")[0].trim());
					byte[] bytes = m.obtenerImagenPerro(idPerro);
					if (bytes != null) {
						vcs.imagen = ImageIO.read(new ByteArrayInputStream(bytes));

					}
					vcs.canvasImagen.repaint();
				}
			} catch (ClassNotFoundException cnfe) {

				System.err.println(cnfe);
				;
			} catch (SQLException se) {

				System.err.println(se);
				;
			} catch (IOException ioe) {

				System.err.println(ioe);
			}
			vcs.btnVolver1.setVisible(false);
			vcs.btnVolver2.setVisible(false);
			vcs.btnSiguiente1.setVisible(false);
			vcs.btnSiguiente2.setVisible(false);
			if (indicePerroC == perrosConcurso.size() - 1) {
				vcs.btnVolver2.setVisible(true);
				vcs.btnVolver2.setEnabled(true);
			} else if (indicePerroC == 0) {
				vcs.btnSiguiente1.setVisible(true);
				vcs.btnSiguiente1.setEnabled(true);
			} else {
				vcs.btnVolver1.setVisible(true);
				vcs.btnVolver1.setEnabled(true);
				vcs.btnSiguiente2.setVisible(true);
				vcs.btnSiguiente2.setEnabled(true);
			}
			vcs.ventana.validate();
			vcs.ventana.repaint();
		} else if (vcs != null && (e.getSource().equals(vcs.btnVolver1) || e.getSource().equals(vcs.btnVolver2))) {
			indicePerroC = indicePerroC - 1;
			try {
				perrosConcurso = m.obtenerPerros();
				if (!perrosConcurso.isEmpty()) {
					String perro = perrosConcurso.get(indicePerroC);
					vcs.lblNombre.setText("Nombre: " + perro.split(" \\| ")[1].trim());
					int idPerro = Integer.parseInt(perro.split(" \\| ")[0].trim());
					byte[] bytes = m.obtenerImagenPerro(idPerro);
					if (bytes != null) {
						vcs.imagen = ImageIO.read(new ByteArrayInputStream(bytes));

					}
					vcs.canvasImagen.repaint();
				}
			} catch (ClassNotFoundException cnfe) {

				System.err.println(cnfe);
				;
			} catch (SQLException se) {

				System.err.println(se);
				;
			} catch (IOException ioe) {

				System.err.println(ioe);
			}
			vcs.btnVolver1.setVisible(false);
			vcs.btnVolver2.setVisible(false);
			vcs.btnSiguiente1.setVisible(false);
			vcs.btnSiguiente2.setVisible(false);
			if (indicePerroC == perrosConcurso.size() - 1) {
				vcs.btnVolver2.setVisible(true);
				vcs.btnVolver2.setEnabled(true);
			} else if (indicePerroC == 0) {
				vcs.btnSiguiente1.setVisible(true);
				vcs.btnSiguiente1.setEnabled(true);
			} else {
				vcs.btnVolver1.setVisible(true);
				vcs.btnVolver1.setEnabled(true);
				vcs.btnSiguiente2.setVisible(true);
				vcs.btnSiguiente2.setEnabled(true);
			}
			vcs.ventana.validate();
			vcs.ventana.repaint();
		} else if (e.getSource().equals(v.btnReglas)) {
			try {
				File archivo = new File("ayuda.html");
				String rutaAbsoluta = archivo.getAbsolutePath();
				List<String> comando = new ArrayList<>();
				comando.add("cmd");
				comando.add("/c");
				comando.add("start");
				comando.add("msedge");
				comando.add("--app=file:///" + rutaAbsoluta);

				ProcessBuilder pb = new ProcessBuilder(comando);
				pb.start();

			} catch (Exception ex) {
				System.err.println("Error al abrir la ayuda: " + ex.getMessage());
				try {
					java.awt.Desktop.getDesktop().browse(new File("ayuda.html").toURI());
				} catch (Exception ex2) {
					ex2.printStackTrace();
				}
			}
		}
	}

	@Override
	public void windowClosing(WindowEvent e) {
		if (e.getSource().equals(v.ventana)) {
			System.exit(0);
		} else if (vpm != null && e.getSource().equals(vpm.ventana)) {
			vpm.ventana.dispose();
			vpm = null;
		} else if (vpc != null && e.getSource().equals(vpc.ventana)) {
			vpc.ventana.dispose();
			vpc = null;
		} else if (vpc != null && e.getSource().equals(vpc.diaFeedback)) {
			vpc.diaFeedback.dispose();
		} else if (vpmod != null && e.getSource().equals(vpmod.ventana)) {
			vpmod.ventana.dispose();
			vpmod = null;
		} else if (vpmod != null && e.getSource().equals(vpmod.diaFeedback)) {
			vpmod.diaFeedback.dispose();
		} else if (vpb != null && e.getSource().equals(vpb.ventana)) {
			vpb.ventana.dispose();
			vpb = null;
		} else if (vpb != null && e.getSource().equals(vpb.dialogo)) {
			vpb.dialogo.dispose();
		} else if (vpb != null && e.getSource().equals(vpb.diaFeedback)) {
			vpb.diaFeedback.dispose();
		} else if (vdm != null && e.getSource().equals(vdm.ventana)) {
			vdm.ventana.dispose();
			vdm = null;
		} else if (vdc != null && e.getSource().equals(vdc.ventana)) {
			vdc.ventana.dispose();
			vdc = null;
		} else if (vdmod != null && e.getSource().equals(vdmod.ventana)) {
			vdmod.ventana.dispose();
			vdmod = null;
		} else if (vdmod != null && e.getSource().equals(vdmod.diaFeedback)) {
			vdmod.diaFeedback.dispose();
		} else if (vdb != null && e.getSource().equals(vdb.ventana)) {
			vdb.ventana.dispose();
			vdb = null;
		} else if (vdb != null && e.getSource().equals(vdb.dialogo)) {
			vdb.dialogo.dispose();
		} else if (vdb != null && e.getSource().equals(vdb.diaFeedback)) {
			vdb.diaFeedback.dispose();
		} else if (vdc != null && e.getSource().equals(vdc.diaFeedback)) {
			vdc.diaFeedback.dispose();
		} else if (vjm != null && e.getSource().equals(vjm.ventana)) {
			vjm.ventana.dispose();
			vjm = null;
		} else if (vjmod != null && e.getSource().equals(vjmod.ventana)) {
			vjmod.ventana.dispose();
			vjmod = null;
		} else if (vjmod != null && e.getSource().equals(vjmod.diaFeedback)) {
			vjmod.diaFeedback.dispose();
		} else if (vc != null && e.getSource().equals(vc.ventana)) {
			vc.ventana.dispose();
			vc = null;
			indicePerro = 0;
		} else if (vc != null && e.getSource().equals(vc.diaFeedback)) {
			vc.diaFeedback.dispose();
		} else if (vc != null && e.getSource().equals(vc.diaJ1)) {
			vc.txtVotoJ1.setText("");
			vc.diaJ1.dispose();
		} else if (vc != null && e.getSource().equals(vc.diaJ2)) {
			vc.txtVotoJ2.setText("");
			vc.diaJ2.dispose();
		} else if (vc != null && e.getSource().equals(vc.diaJ3)) {
			vc.txtVotoJ3.setText("");
			vc.diaJ3.dispose();
		} else if (vf != null && e.getSource().equals(vf.ventana)) {
			vf.ventana.dispose();
			vf = null;
		} else if (vcs != null && e.getSource().equals(vcs.ventana)) {
			vcs.ventana.dispose();
			vcs = null;
			indicePerroC = 0;
		}

		if (vc != null && perrosConcurso != null && !vc.diaJ1.isVisible() && !vc.diaJ2.isVisible()
				&& !vc.diaJ3.isVisible()) {
			vc.btnPunt.setEnabled(true);
			if (indicePerro == perrosConcurso.size() - 1) {
				vc.btnFin.setVisible(true);
				vc.btnFin.setEnabled(true);
				vc.btnSig.setEnabled(false);
				vc.btnFin.repaint();
			} else {
				vc.btnSig.setEnabled(true);
				vc.btnSig.repaint();
			}
			vc.btnPunt.repaint();
			vc.ventana.validate();
			vc.ventana.repaint();
		}

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (vpmod != null && vpmod.choPerros.getSelectedIndex() != 0) {
			Utilidades.cursorEspera(vpmod.ventana);
			String nombre = vpmod.choPerros.getSelectedItem().split(" \\| ")[1];
			String raza = vpmod.choPerros.getSelectedItem().split(" \\| ")[2];
			String tamano = vpmod.choPerros.getSelectedItem().split(" \\| ")[3];
			String color = vpmod.choPerros.getSelectedItem().split(" \\| ")[4];
			int idDuenoFK = Integer.parseInt(vpmod.choPerros.getSelectedItem().split(" \\| ")[5]);
			String dueno = null;
			try {
				dueno = m.buscarD(idDuenoFK);
			} catch (ClassNotFoundException cnfe) {

				System.err.println(cnfe);
			} catch (SQLException se) {

				System.err.println(se);
			}
			vpmod.txtNombre.setText(nombre);
			vpmod.txtRaza.setText(raza);
			vpmod.choTamano.select(tamano);
			vpmod.txtColor.setText(color);
			vpmod.choDuenos.select(dueno);
			Utilidades.cursorEstandar(vpmod.ventana);

		} else if (vdmod != null && vdmod.choDuenos.getSelectedIndex() != 0) {
			Utilidades.cursorEspera(vdmod.ventana);
			String nombre = vdmod.choDuenos.getSelectedItem().split(" \\| ")[1];
			String apellidos = vdmod.choDuenos.getSelectedItem().split(" \\| ")[2];
			vdmod.txtNombre.setText(nombre);
			vdmod.txtApellidos.setText(apellidos);
			Utilidades.cursorEstandar(vdmod.ventana);

		} else if (vjmod != null && vjmod.choJueces.getSelectedIndex() != 0) {
			Utilidades.cursorEspera(vjmod.ventana);
			int idJuez = Integer.parseInt(vjmod.choJueces.getSelectedItem().split(" \\| ")[0]);

			try {
				String juez = m.buscarJ(idJuez);
				String nombre = juez.split(" \\| ")[1].trim();
				String apellidos = juez.split(" \\| ")[2].trim();
				vjmod.txtNombre.setText(nombre);
				vjmod.txtApellidos.setText(apellidos);
			} catch (ClassNotFoundException cnfe) {
				System.out.println(cnfe);

			} catch (SQLException se) {
				System.out.println(se);
			}

			Utilidades.cursorEstandar(vjmod.ventana);
		}

	}

}
