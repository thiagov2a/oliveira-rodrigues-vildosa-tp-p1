package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Enemigo {

	// Imágenes estáticas para todos los enemigos, para optimizar recursos.
	private static final Image IZQ = Herramientas.cargarImagen("enemigo-izq.png");
	private static final Image DER = Herramientas.cargarImagen("enemigo-der.png");

	private double x;
	private double y;
	private double velocidad;
	// Dirección actual del enemigo (false indica hacia la derecha).
	private boolean direccion;

	public Enemigo(double x, double y, double velocidad, boolean direccion) {
		this.x = x;
		this.y = y;
		this.velocidad = velocidad;
		this.direccion = direccion;
	}

	public void dibujar(Entorno entorno) {
		Image img = direccion ? IZQ : DER;
		entorno.dibujarImagen(img, this.x, this.y, 0);
	}

	public void mover(Entorno entorno) {
		double bordeIzq = getAncho() / 2;
		double bordeDer = entorno.ancho() - getAncho() / 2;

		// Mueve al enemigo hacia la izquierda o la derecha según su dirección.
		if (direccion && this.x >= bordeIzq) {
			this.x -= this.velocidad;
		} else if (!direccion && this.x <= bordeDer) {
			this.x += this.velocidad;
		} else {
			direccion = !direccion; // Cambia la dirección si se alcanza cualquiera de los límites
		}
	}

	public double getAncho() {
		return IZQ.getWidth(null);
	}

	public double getAlto() {
		return IZQ.getHeight(null);
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

	// Getters & Setters
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

	public boolean isDireccion() {
		return direccion;
	}

	public void setDireccion(boolean direccion) {
		this.direccion = direccion;
	}

	public static Image getIzq() {
		return IZQ;
	}

	public static Image getDer() {
		return DER;
	}

}
