package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Bloque {

	private static final Image BEDROCK = Herramientas.cargarImagen("bedrock.png");
	private static final Image TIERRA = Herramientas.cargarImagen("tierra.png");
	private static final Image PIEDRA = Herramientas.cargarImagen("piedra.png");

	private double x;
	private double y;
	private int tipo;

	public Bloque(double x, double y, int tipo) {
		this.x = x;
		this.y = y;
		this.tipo = tipo;
	}

	public void dibujarse(Entorno entorno) {
		entorno.dibujarImagen(seleccionarImagen(), this.x, this.y, 0.0);
	}

	private Image seleccionarImagen() {
		switch (this.tipo) {
		case -1:
			return BEDROCK;
		case 0:
			return TIERRA;
		default:
			return PIEDRA;
		}
	}

	public double getAlto() {
		return seleccionarImagen().getHeight(null);
	}

	public double getAncho() {
		return seleccionarImagen().getWidth(null);
	}

	public double getIzquierda() {
		return this.x - this.getAncho() / 2;
	}

	public double getDerecha() {
		return this.x + this.getAncho() / 2;
	}

	public double getTecho() {
		return this.y - this.getAlto() / 2;
	}

	public double getPiso() {
		return this.y + this.getAlto() / 2;
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

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
}
