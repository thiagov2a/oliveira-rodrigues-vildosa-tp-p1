package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Lava {

	double x;
	double y;
	Image img;

	public Lava() {
	}

	public Lava(double x, double y) {
		this.x = x;
		this.y = y;
		this.img = Herramientas.cargarImagen("lava.gif");
	}

	public void dibujarse(Entorno entorno) {
		entorno.dibujarImagen(this.img, this.x, this.y, 0);
	}

	public void subir(Entorno entorno) {
		if (this.y >= 300) {
			entorno.dibujarImagen(this.img, this.x, --this.y, 0);
		}
	}

}