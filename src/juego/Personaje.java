package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Personaje {

	double x;
	double y;
	Image img;

	public Personaje() {
	}

	public Personaje(double x, double y) {
		this.x = x;
		this.y = y;
		this.img = Herramientas.cargarImagen("personaje.png");
	}

	public void dibujarse(Entorno entorno) {
		entorno.dibujarImagen(this.img, this.x, this.y, 0);
	}

	public void moverDerecha(Entorno entorno) {
		this.img = Herramientas.cargarImagen("personaje.png");
		this.x += 2;
		entorno.dibujarImagen(this.img, this.x, this.y, 0);
	}

	public void moverIzquierda(Entorno entorno) {
		this.img = Herramientas.cargarImagen("personaje2.png");
		this.x -= 2;
		entorno.dibujarImagen(this.img, this.x, this.y, 0);
	}

}