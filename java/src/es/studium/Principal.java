package es.studium;

public class Principal {

	public static void main(String[] args) {

		VistaPrincipal vista = new VistaPrincipal();
		Modelo modelo = new Modelo();
		Controlador controlador = new Controlador(vista, modelo);
	}

}
