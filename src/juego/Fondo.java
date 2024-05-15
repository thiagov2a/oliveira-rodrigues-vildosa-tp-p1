package juego;

import java.awt.Image;

import entorno.Herramientas;

public class Fondo {

	double x;
	double y;
	Image img;
	
	public Fondo() {
	}
	
	public Fondo(double x, double y) {
		this.x = x;
		this.y = y;
		this.img = Herramientas.cargarImagen(null);
	}
	
}
