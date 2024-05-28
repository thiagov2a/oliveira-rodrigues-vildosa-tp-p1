package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Lava {

	private static final Image GIF = Herramientas.cargarImagen("lava.gif");

	private double x;
	private double y;
	private double velocidad;

	public Lava() {
	}

	public Lava(double x, double y) {
		this.x = x;
		this.y = y;
		this.velocidad = 0.1;
	}

	public void dibujar(Entorno entorno) {
		entorno.dibujarImagen(GIF, this.x, this.y, 0.0);
	}

	public void subir(Entorno entorno) {
		double limiteSuperior = entorno.alto() - this.getAlto() / 2;
		if (this.y > limiteSuperior) {
			this.y -= this.velocidad;
		}
	}

	public double getAncho() {
		return GIF.getWidth(null);
	}

	public double getAlto() {
		return GIF.getHeight(null);
	}

}