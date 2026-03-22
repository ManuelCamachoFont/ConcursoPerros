package es.studium;

import java.awt.Button;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Utilidades {

	public static Font elegirFuente(String ruta, int estilo, int tamano) {
		try {
			Font nuevaFuente = Font.createFont(Font.TRUETYPE_FONT, Utilidades.class.getResourceAsStream(ruta));
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(nuevaFuente);
			return nuevaFuente.deriveFont(estilo, tamano);
		} catch (FontFormatException | IOException e) {
			return new Font("Serif", estilo, tamano);
		}
	}
	
	public static Image cargarIcono(String ruta) {
        try {
            return ImageIO.read(new File(ruta));
        } catch (Exception e) {
            return null;
        }
    }

    public static void aplicarIcono(String ruta, Frame... ventanas) {
        Image icono = cargarIcono(ruta);
        if (icono != null) {
            for (Frame ventana : ventanas) {
                ventana.setIconImage(icono);
            }
        }
    }
    
    public static void cursorEstandar(Frame ventana) {
    	ventana.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
	
	public static void cursorBoton(Button... botones) {
	    for (Button btn : botones) {
	        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
	    }
	}
	
	public static void cursorEspera(Frame ventana) {
	       ventana.setCursor(new Cursor(Cursor.WAIT_CURSOR));
	}
}
