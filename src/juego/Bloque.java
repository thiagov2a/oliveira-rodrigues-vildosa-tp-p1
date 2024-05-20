package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Bloque {

	// Imágenes estáticas para todos los bloques, para optimizar recursos.
	private static final Image BEDROCK = Herramientas.cargarImagen("bedrock.png");
	private static final Image TIERRA = Herramientas.cargarImagen("tierra.png");
	private static final Image PIEDRA = Herramientas.cargarImagen("piedra.png");

	private double x;
	private double y;
	// Código del tipo de bloque (-1 para bedrock, 0 para tierra, 1 para piedra)
	private int tipo;

	public Bloque(double x, double y, int tipo) {
		this.x = x;
		this.y = y;
		this.tipo = tipo;
	}

	public void dibujarse(Entorno entorno) {
		Image img = seleccionarImagen();
		entorno.dibujarImagen(img, this.x, this.y, 0);
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
		return BEDROCK.getHeight(null);
	}

	public double getAncho() {
		return BEDROCK.getWidth(null);
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

	// Getter & Setters

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
