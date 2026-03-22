package es.studium;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;

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
	private VistaFin vf;
	private Modelo m;
	
	private List <String> perrosConcurso;
	private int indicePerro = 0;
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
		Utilidades.cursorBoton(v.btnPerros, v.btnDuenos, v.btnJueces, v.btnIni);
		Utilidades.aplicarIcono("recursos/iconos/paw.png", v.ventana);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Botón para acceder al Menú de los Perros desde el Menú Principal
		if (e.getSource().equals(v.btnPerros)) {
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
			vpm.ventana.dispose();
			vpc = new VistaPerroCrear();
			Utilidades.cursorEspera(vpc.ventana);
			vpc.ventana.addWindowListener(this);
			vpc.diaFeedback.addWindowListener(this);
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
				System.out.println(cnfe.getMessage());
			} catch (SQLException se) {
				System.out.println(se.getMessage());
			}
			Utilidades.cursorEstandar(vpc.ventana);
		// Botón crear para crear un Nuevo Perro desde la ventana de creación de Perros
		} else if (vpc != null && vpc.choDuenos.getSelectedIndex()!=0 && e.getSource().equals(vpc.btnCrear)) {
			Utilidades.cursorEspera(vpc.ventana);
			String nombre = vpc.txtNombre.getText();
			String raza = vpc.txtRaza.getText();
			String tamano = vpc.txtTamano.getText();
			String color = vpc.txtColor.getText();
			String dueno = vpc.choDuenos.getSelectedItem();
			String idDueno = dueno.split(" \\| ")[0];
			try {		
				if (!m.puedeCrearPerro(Integer.parseInt(idDueno))) {
		            vpc.lblDialogo.setText("Este dueño ya tiene 3 perros registrados");      
		        }
				else {
				m.crearPerro(nombre, raza, tamano, color, idDueno);
				vpc.lblDialogo.setText("La operación se ha realizado con éxito");
				}
			} catch (ClassNotFoundException cnfe) {
				vpc.lblDialogo.setText(cnfe.getMessage());
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
			vpmod = new VistaPerroModificar();
			Utilidades.cursorEspera(vpmod.ventana);
			vpmod.ventana.addWindowListener(this);
			vpmod.diaFeedback.addWindowListener(this);
			vpmod.btnMod.addActionListener(this);
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
				System.out.println(cnfe.getMessage());
			} catch (SQLException se) {
				System.out.println(se.getMessage());
			}
			
			try {
				vpmod.choDuenos.add("-- Seleccione un dueño --");
				List<String> duenos = m.obtenerDuenos();
				for (String listaDuenos : duenos) {
					vpmod.choDuenos.add(listaDuenos);
				}

			} catch (ClassNotFoundException cnfe) {
				System.out.println(cnfe.getMessage());
			} catch (SQLException se) {
				System.out.println(se.getMessage());
			}
			Utilidades.cursorEstandar(vpmod.ventana);
			
		// Botón para efectuar la modificación de un Perro
		} else if (vpmod != null && e.getSource().equals(vpmod.btnMod)) {
			try {
				Utilidades.cursorEspera(vpmod.ventana);
				String nombre = vpmod.txtNombre.getText();
				String raza = vpmod.txtRaza.getText();
				String tamano = vpmod.txtTamano.getText();
				String color = vpmod.txtColor.getText();
				String dueno = vpmod.choDuenos.getSelectedItem();
				String perro = vpmod.choPerros.getSelectedItem();
				int idDueno = Integer.parseInt(dueno.split(" \\| ")[0]);
				int idPerro = Integer.parseInt(perro.split(" \\| ")[0]);
				m.modificarPerro(nombre, raza, tamano, color, idDueno, idPerro);
				vpmod.lblDialogo.setText("La operación se ha realizado con éxito");
			} catch (ClassNotFoundException cnfe) {
				vpmod.lblDialogo.setText(cnfe.getMessage());
			} catch (SQLException se) {
				vpmod.lblDialogo.setText(se.getMessage());
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
				System.out.println(cnfe.getMessage());
			} catch (SQLException se) {
				System.out.println(se.getMessage());
			}
			
			try {
				vpmod.choDuenos.add("-- Seleccione un dueño --");
				List<String> duenos = m.obtenerDuenos();
				for (String listaDuenos : duenos) {
					vpmod.choDuenos.add(listaDuenos);
				}

			} catch (ClassNotFoundException cnfe) {
				System.out.println(cnfe.getMessage());
			} catch (SQLException se) {
				System.out.println(se.getMessage());
			}
			Utilidades.cursorEstandar(vpmod.ventana);
			
		
		// Botón para acceder al borrado de los perros
		} else if (vpm != null && e.getSource().equals(vpm.btnBorrar)) {
			vpm.ventana.dispose();
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
				System.out.println(cnfe.getMessage());
			} catch (SQLException se) {
				System.out.println(se.getMessage());
			}
			Utilidades.cursorEstandar(vpb.ventana);
		
		}
		else if ((vpb != null && vpb.choPerros.getSelectedIndex() != 0) && e.getSource().equals(vpb.btnBorrar)) {
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
			        vpb.lblDialogo.setText("El borrado de " + vpb.choPerros.getSelectedItem() + " se ha realizado con éxito.");
			        vpb.choPerros.removeAll();
			        vpb.choPerros.add("-- Seleccione un Perro --");
			        List<String> perros = m.obtenerPerros();
			        for (String p : perros) vpb.choPerros.add(p);
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
		}else if (vdm != null && e.getSource().equals(vdm.btnCrear)) {
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
		}else if(vdc != null && e.getSource().equals(vdc.btnCrear)) {
			Utilidades.cursorEspera(vdc.ventana);
			String nombre = vdc.txtNombre.getText();
			String apellidos = vdc.txtApellidos.getText();
			try {
				if (!m.puedeCrearDueno()) {
		            vdc.lblDialogo.setText("Ya hay 5 dueños registrados, no se pueden añadir más");
		        }
				else {
				m.crearDueno(nombre, apellidos);
				vdc.lblDialogo.setText("La operación se ha realizado con éxito");
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
		else if (vdm != null && e.getSource().equals(vdm.btnMod)){
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
				System.out.println(cnfe.getMessage());
			} catch (SQLException se) {
				System.out.println(se.getMessage());
			}
			Utilidades.cursorEstandar(vdmod.ventana);
		}
		
		// Botón para llevar a cabo la modificación de un Dueño
		else if(vdmod != null && e.getSource().equals(vdmod.btnMod)){
			try {
				Utilidades.cursorEspera(vdmod.ventana);
				String nombre = vdmod.txtNombre.getText();
				String apellidos = vdmod.txtApellidos.getText();
				String dueno = vdmod.choDuenos.getSelectedItem();
				int idDueno = Integer.parseInt(dueno.split(" \\| ")[0]);
				m.modificarDueno(nombre, apellidos, idDueno);
				vdmod.lblDialogo.setText("La modificación de " + dueno + " se ha realizado con éxito");
			} catch (ClassNotFoundException cnfe) {
				vdmod.lblDialogo.setText(cnfe.getMessage());
			} catch (SQLException se) {
				vdmod.lblDialogo.setText(se.getMessage());
			} finally {
				vdmod.diaFeedback.pack();
				vdmod.diaFeedback.setVisible(true);
			}
			vdmod.choDuenos.removeAll();
			try {
				vdmod.choDuenos.add("-- Seleccione un dueño --");
				List<String> duenos = m.obtenerDuenos();
				for (String listaDuenos : duenos) {
					vdmod.choDuenos.add(listaDuenos);
				}

			} catch (ClassNotFoundException cnfe) {
				System.out.println(cnfe.getMessage());
			} catch (SQLException se) {
				System.out.println(se.getMessage());
			}
			
			Utilidades.cursorEstandar(vdmod.ventana);
		}
		 else if (vdm != null && e.getSource().equals(vdm.btnBorrar)) {
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
					System.out.println(cnfe.getMessage());
				} catch (SQLException se) {
					System.out.println(se.getMessage());
				}
				Utilidades.cursorEstandar(vdb.ventana);
			
			}
			else if ((vdb != null && vdb.choDuenos.getSelectedIndex() != 0) && e.getSource().equals(vdb.btnBorrar)) {
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
				        for (String d : duenos) vdb.choDuenos.add(d);
				        vdb.lblDialogo.setText("El borrado de " + vdb.choDuenos.getSelectedItem() + " se ha llevado a cabo con éxito");
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
			}
			else if(e.getSource().equals(v.btnJueces)){
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
				System.out.println(cnfe.getMessage());
			} catch (SQLException se) {
				System.out.println(se.getMessage());
			}
			Utilidades.cursorEstandar(vjmod.ventana);
		}
		
		else if (vjmod !=null && e.getSource().equals(vjmod.btnMod)) {
			try {
				Utilidades.cursorEspera(vjmod.ventana);
				String nombre = vjmod.txtNombre.getText();
				String apellidos = vjmod.txtApellidos.getText();
				String juez = vjmod.choJueces.getSelectedItem();
				int idJuez = Integer.parseInt(juez.split(" \\| ")[0]);
				m.modificarJuez(nombre, apellidos, idJuez);
				vjmod.lblDialogo.setText("La modificación de " + juez + " se ha realizado con éxito");
			} catch (ClassNotFoundException cnfe) {
				vjmod.lblDialogo.setText(cnfe.getMessage());
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
				System.out.println(cnfe.getMessage());
			} catch (SQLException se) {
				System.out.println(se.getMessage());
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
			vc = new VistaConcurso();
			try {
				perrosConcurso = m.obtenerPerros();
				String perro = perrosConcurso.get(indicePerro);
				vc.lblOrden.setText("Perro " + (indicePerro + 1));
				vc.lblPerro.setText(perro.split(" \\| ")[1].trim());
				int idDuenoFK = Integer.parseInt(perro.split(" \\| ")[5].trim());
				String dueno = m.buscarD(idDuenoFK);
				vc.lblDueno.setText(dueno.split(" \\| ")[1].trim());
				String juez1 = m.buscarJ(1);
				String juez2 = m.buscarJ(2);
				String juez3 = m.buscarJ(3);
				vc.lblTituloJ1.setText(juez1.split(" \\| ")[1]+ " " + juez1.split(" \\| ")[2]);
				vc.lblTituloJ2.setText(juez2.split(" \\| ")[1]+ " " + juez2.split(" \\| ")[2]);
				vc.lblTituloJ3.setText(juez3.split(" \\| ")[1]+ " " + juez3.split(" \\| ")[2]);
				
				if (indicePerro == perrosConcurso.size()-1) {
					vc.btnSig.setVisible(false);
					vc.btnFin.setVisible(true);
				}
				else {
					vc.btnSig.setVisible(true);
					vc.btnFin.setVisible(false);
				}
			} catch (ClassNotFoundException cnfe) {
				
				System.out.println(cnfe);;
			} catch (SQLException se) {

				System.out.println(se);;
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
			Utilidades.cursorBoton(vc.btnPunt, vc.btnSig);
			Utilidades.cursorEstandar(vc.ventana);
			vc.ventana.validate();
			vc.ventana.repaint();
		}
		else if (vc != null && e.getSource().equals(vc.btnPunt)) {
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
		            votadoJ1 = true;
				}
				else if (votadoJ1) {
					vc.txtVotoJ1.setText("Ya ha votado");
				}
				else {
					vc.txtVotoJ1.setText("Número 0-10");
				}
			} catch (NumberFormatException nfe) {
				System.out.println(nfe);
			}catch (ClassNotFoundException cnfe) {
				
				System.out.println(cnfe);
			} catch (SQLException se) {

				System.out.println(se);
			}
			
		}
		
		else if (vc != null && e.getSource().equals(vc.btnPuntJ2)) {
			try {
				String perro = perrosConcurso.get(indicePerro);
				int idPerro = Integer.parseInt(perro.split(" \\| ")[0].trim());
				float puntuacion2 = Float.parseFloat(vc.txtVotoJ2.getText());
			    if (!votadoJ2 && puntuacion2 >= 0 && puntuacion2 <= 10) {
		            puntuacion = puntuacion + puntuacion2;
		            m.puntuarPerro(idPerro, puntuacion);
		            votadoJ2 = true;
			    }
				else if (votadoJ2) {
					vc.txtVotoJ2.setText("Ya ha votado");
				}
				else {
					vc.txtVotoJ2.setText("Número 0-10");
				}
			} catch (NumberFormatException nfe) {
				System.out.println(nfe);
			}catch (ClassNotFoundException cnfe) {
				
				System.out.println(cnfe);
			} catch (SQLException se) {

				System.out.println(se);
			}
		}
		
		else if (vc != null && e.getSource().equals(vc.btnPuntJ3)) {
			try {
				String perro = perrosConcurso.get(indicePerro);
				int idPerro = Integer.parseInt(perro.split(" \\| ")[0].trim());
				float puntuacion3 = Float.parseFloat(vc.txtVotoJ3.getText());
				 if (!votadoJ3 && puntuacion3>= 0 && puntuacion3 <= 10) {
			            puntuacion = puntuacion + puntuacion3;
			            m.puntuarPerro(idPerro, puntuacion);
			            votadoJ3 = true;
				    }
					else if (votadoJ3) {
						vc.txtVotoJ3.setText("Ya ha votado");
					}
				else {
					vc.txtVotoJ3.setText("Número 0-10");
				}
			} catch (NumberFormatException nfe) {
				System.out.println(nfe);
			}catch (ClassNotFoundException cnfe) {
				
				System.out.println(cnfe);
			} catch (SQLException se) {

				System.out.println(se);
			}
		}
		
		else if (vc != null && e.getSource().equals(vc.btnSig)) {
			try {
				puntuacion = 0;
				votadoJ1 = false;
				votadoJ2 = false;
				votadoJ3 = false;
				indicePerro = indicePerro + 1;
				String perro = perrosConcurso.get(indicePerro);
				vc.lblOrden.setText("Perro " + (indicePerro + 1));
				vc.lblPerro.setText(perro.split(" \\| ")[1].trim());
				int idDuenoFK = Integer.parseInt(perro.split(" \\| ")[5].trim());
				String dueno = m.buscarD(idDuenoFK);
				vc.lblDueno.setText(dueno.split(" \\| ")[1].trim());
		    } catch (ClassNotFoundException cnfe) {
		        System.err.println(cnfe);
		    } catch (SQLException se) {
		        System.err.println(se);
		    }

			if (indicePerro == perrosConcurso.size()-1) {
				vc.btnSig.setVisible(false);
				vc.btnFin.setVisible(true);
			}
			else {
				vc.btnSig.setVisible(true);
				vc.btnFin.setVisible(false);
			}
			vc.ventana.validate();
			vc.ventana.repaint();
		}
		
		else if (vc != null && e.getSource().equals(vc.btnFin)) {
			vc.ventana.dispose();
			vf = new VistaFin();
			Utilidades.cursorEspera(vf.ventana);
			vf.ventana.addWindowListener(this);
			vf.btnReiniciar.addActionListener(this);
			Utilidades.aplicarIcono("recursos/iconos/paw.png", vf.ventana);
			try {
		        List<String> resultados = m.obtenerResultados();
		        String texto = "";
		        int posicion = 1;
		        for (String r : resultados) {
		            String[] datos = r.split(" \\| ");
		            texto += posicion + ". " + datos[0] + 
		                     " - Puntuación: " + datos[1] + 
		                     " - Dueño: " + datos[2] + " " + datos[3] + "\n";
		            posicion++;
		        }
		        vf.txtResultados.setText(texto);
		        String[] ganador = resultados.get(0).split(" \\| ");
		        vf.lblGanador.setText(ganador[0] + " - " + ganador[2] + " " + ganador[3]);
		    } catch (ClassNotFoundException cnfe) {
		        System.err.println(cnfe);
		    }
			catch (SQLException ex) {
				System.err.println(ex);
			}
			Utilidades.cursorBoton(vf.btnReiniciar);
			Utilidades.cursorEstandar(vf.ventana);
			vf.ventana.validate();
			vf.ventana.repaint();
		}
			else if (vf != null && e.getSource().equals(vf.btnReiniciar)) {
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
			    }
			    catch (SQLException se) {
			    	System.err.println(se);
			    }
			}
		

		
		

	}

	@Override
	public void windowClosing(WindowEvent e) {
		if (e.getSource().equals(v.ventana)) {
			System.exit(0);
		} else if (vpm != null && e.getSource().equals(vpm.ventana)) {
			vpm.ventana.dispose();
		} else if (vpc != null && e.getSource().equals(vpc.ventana)) {
			vpc.ventana.dispose();
		} else if (vpc != null && e.getSource().equals(vpc.diaFeedback)) {
			vpc.diaFeedback.dispose();
		} else if (vpmod != null && e.getSource().equals(vpmod.ventana)) {
			vpmod.ventana.dispose();
		} else if (vpmod != null && e.getSource().equals(vpmod.diaFeedback)) {
			vpmod.diaFeedback.dispose();
		} else if (vpb !=null && e.getSource().equals(vpb.ventana)) {
			vpb.ventana.dispose();
		}else if (vpb != null && e.getSource().equals(vpb.dialogo)) {
			vpb.dialogo.dispose();
		}else if (vpb != null && e.getSource().equals(vpb.diaFeedback)) {
			vpb.diaFeedback.dispose();
		}
		else if (vdm != null && e.getSource().equals(vdm.ventana)) {
			vdm.ventana.dispose();
		} else if (vdc != null && e.getSource().equals(vdc.ventana)) {
			vdc.ventana.dispose();
		}
		else if (vdmod !=null && e.getSource().equals(vdmod.ventana)) {
			vdmod.ventana.dispose();
		}
		else if (vdmod != null && e.getSource().equals(vdmod.diaFeedback)) {
		    vdmod.diaFeedback.dispose();
		}
		else if (vdb != null && e.getSource().equals(vdb.ventana)) {
			vdb.ventana.dispose();
		}
		else if (vdb != null && e.getSource().equals(vdb.diaFeedback)) {
		    vdb.diaFeedback.dispose();
		}
		else if (vdc != null && e.getSource().equals(vdc.diaFeedback)) {
			vdc.diaFeedback.dispose();
		}else if (vjm != null && e.getSource().equals(vjm.ventana)) {
			vjm.ventana.dispose();
		}
		else if (vjmod !=null && e.getSource().equals(vjmod.ventana)) {
			vjmod.ventana.dispose();
		}
		else if (vjmod != null && e.getSource().equals(vjmod.diaFeedback)) {
			vjmod.diaFeedback.dispose();
		}
		else if (vc != null && e.getSource().equals(vc.ventana)) {
			vc.ventana.dispose();
			indicePerro = 0;
		}
		else if (vc != null && e.getSource().equals(vc.diaFeedback)) {
		    vc.diaFeedback.dispose();
		}
		else if (vc!= null && e.getSource().equals(vc.diaJ1)) {
			vc.txtVotoJ1.setText(null);
			vc.diaJ1.dispose();
		}
		else if (vc!= null && e.getSource().equals(vc.diaJ2)) {
			vc.txtVotoJ2.setText(null);
			vc.diaJ2.dispose();
		}
		else if (vc!= null && e.getSource().equals(vc.diaJ3)) {
			vc.txtVotoJ3.setText(null);
			vc.diaJ3.dispose();
		}
		else if (vf != null && e.getSource().equals(vf.ventana)) {
			    vf.ventana.dispose();
		
		}
		
		if (vc != null && perrosConcurso != null && !vc.diaJ1.isVisible() && !vc.diaJ2.isVisible() && !vc.diaJ3.isVisible()) {
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
		if (vpmod != null && vpmod.choPerros.getSelectedIndex() != 0){
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
			vpmod.txtTamano.setText(tamano);
			vpmod.txtColor.setText(color);
			vpmod.choDuenos.select(dueno);
			Utilidades.cursorEstandar(vpmod.ventana);
			
		}
		else if (vdmod != null && vdmod.choDuenos.getSelectedIndex() != 0){
			Utilidades.cursorEspera(vdmod.ventana);
			String nombre = vdmod.choDuenos.getSelectedItem().split(" \\| ")[1];
			String apellidos = vdmod.choDuenos.getSelectedItem().split(" \\| ")[2];
			vdmod.txtNombre.setText(nombre);
			vdmod.txtApellidos.setText(apellidos);
			Utilidades.cursorEstandar(vdmod.ventana);
			
		}
		else if (vjmod != null && vjmod.choJueces.getSelectedIndex() !=0) {
			Utilidades.cursorEspera(vjmod.ventana);
			int idJuez = Integer.parseInt(vjmod.choJueces.getSelectedItem().split(" \\| ")[0]);
			
			try {
				String juez = m.buscarJ(idJuez);
				String nombre = juez.split(" \\| ")[1].trim();
				String apellidos = juez.split(" \\| ")[2].trim();
				vjmod.txtNombre.setText(nombre);
				vjmod.txtApellidos.setText(apellidos);
			} catch (ClassNotFoundException cnfe) {
				System.out.println(cnfe);;
			} catch (SQLException se) {
				System.out.println(se);
			}
			
			
			Utilidades.cursorEstandar(vjmod.ventana);
		}
		
	}
	
}
