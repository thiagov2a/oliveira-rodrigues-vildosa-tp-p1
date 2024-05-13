package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Bloque {

	double x;
	double y;
	boolean esPiedra;
	Image img;

	public Bloque() {
	}

	public Bloque(double x, double y, boolean esPiedra) {
		this.x = x;
		this.y = y;
		this.esPiedra = esPiedra;
		this.img = esPiedra ? Herramientas.cargarImagen("piedra.png") : Herramientas.cargarImagen("tierra.png");
	}
	
	public void dibujarse(Entorno entorno) {
		entorno.dibujarImagen(img, x, y, 0);
	}

}
