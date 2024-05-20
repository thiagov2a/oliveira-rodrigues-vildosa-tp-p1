package juego;

import java.awt.Image;
import java.util.Random;

import entorno.Entorno;
import entorno.Herramientas;

public class Enemigo {

	// Imágenes estáticas para todos los enemigos, para optimizar recursos.
	private static final Image IZQ = Herramientas.cargarImagen("enemigo-izq.png");
	private static final Image DER = Herramientas.cargarImagen("enemigo-der.png");

	private static final Random rand = new Random();

	private double x;
	private double y;
	private double velocidad;
	// Dirección actual del enemigo (false indica hacia la derecha).
	private boolean direccion;
	private boolean apoyado;

	public Enemigo(double x, double y) {
		this.x = x;
		this.y = y;
		this.velocidad = 2;
		this.direccion = rand.nextBoolean();
	}

	public void dibujar(Entorno entorno) {
		Image img = this.direccion ? IZQ : DER;
		entorno.dibujarImagen(img, this.x, this.y, 0);
	}

	public void mover(Entorno entorno) {
		double bordeIzq = this.getAncho() / 2;
		double bordeDer = entorno.ancho() - this.getAncho() / 2;

		if (this.apoyado) {
			// Mueve al enemigo hacia la izquierda o la derecha según su dirección.
			if (this.direccion && this.x >= bordeIzq) {
				this.x -= this.velocidad;
			} else if (!this.direccion && this.x <= bordeDer) {
				this.x += this.velocidad;
			} else {
				this.direccion = !this.direccion; // Cambia la dirección si se alcanza cualquiera de los bordes.
			}
		}
	}

	public void caer() {
		if (!this.apoyado) {
			this.y += 1.5;
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

	public boolean isApoyado() {
		return apoyado;
	}

	public void setApoyado(boolean apoyado) {
		this.apoyado = apoyado;
	}
}
