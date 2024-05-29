package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Lava {

	private static final Image IMAGEN_LAVA = Herramientas.cargarImagen("lava.gif");

	private double x;
	private double y;
	private double velocidad;

	public Lava(double x, double y) {
		this.x = x;
		this.y = y;
		this.velocidad = 0.15;
	}

	public void dibujar(Entorno entorno) {
		mover(entorno);
		entorno.dibujarImagen(IMAGEN_LAVA, x, y, 0.0);
	}

	private void mover(Entorno entorno) {
		double limiteSuperior = entorno.alto() - this.getAlto() / 2;
		if (y > limiteSuperior) {
			y -= velocidad;
		}
	}

	public double getAncho() {
		return IMAGEN_LAVA.getWidth(null);
	}

	public double getAlto() {
		return IMAGEN_LAVA.getHeight(null);
	}

	public double getTecho() {
		return y - getAlto() / 2;
	}

	public double getPiso() {
		return y + getAlto() / 2;
	}

	public double getIzquierda() {
		return x - getAncho() / 2;
	}

	public double getDerecha() {
		return x + getAncho() / 2;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(double velocidad) {
		this.velocidad = velocidad;
	}
}
