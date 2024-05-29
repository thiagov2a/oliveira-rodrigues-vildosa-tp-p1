package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Puntaje {

	private static final Image IMAGEN_PUNTAJE = Herramientas.cargarImagen("puntaje.gif");
	private static final Color COLOR_TEXTO = Color.GREEN;

	private double x;
	private double y;
	private int puntos;

	public Puntaje(double x, double y) {
		this.x = x;
		this.y = y;
		this.puntos = 0;
	}

	public void dibujar(Entorno entorno) {
		entorno.dibujarImagen(IMAGEN_PUNTAJE, x, y, 0.0);
		entorno.cambiarFont("Arial", 32, COLOR_TEXTO);
		entorno.escribirTexto(String.valueOf(puntos), x + this.getAncho(), y + this.getAlto() / 3.5);
	}

	public void sumar() {
		this.puntos += 2;
	}

	public double getAncho() {
		return IMAGEN_PUNTAJE.getWidth(null);
	}

	public double getAlto() {
		return IMAGEN_PUNTAJE.getWidth(null);
	}

	public double getTecho() {
		return this.y - this.getAlto() / 2;
	}

	public double getPiso() {
		return this.y + this.getAlto() / 2;
	}

	public double getIzquierda() {
		return this.x - this.getAncho() / 2;
	}

	public double getDerecha() {
		return this.x + this.getAncho() / 2;
	}
}
