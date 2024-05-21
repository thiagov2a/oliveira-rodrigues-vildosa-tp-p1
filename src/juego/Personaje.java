package juego;

import java.awt.Image;
import java.util.Random;

import entorno.Entorno;
import entorno.Herramientas;

public class Personaje {

	// Imágenes estáticas del personaje, para optimizar recursos.
	private static final Image IZQ = Herramientas.cargarImagen("personaje-izq.png");
	private static final Image DER = Herramientas.cargarImagen("personaje-der.png");

	private static final Random rand = new Random();

	private double x;
	private double y;
	private double velocidad;
	// Dirección actual del personaje (false indica hacia la derecha).
	private boolean direccion;
	private boolean apoyado;
	private boolean cayendo;
	private boolean saltando;
	private int contadorSalto;
	private Proyectil proyectil;

	public Personaje() {
	}

	public Personaje(double x, double y) {
		this.x = x;
		this.y = y;
		this.velocidad = 2.5;
		this.direccion = rand.nextBoolean();
		this.apoyado = false;
		this.cayendo = false;
		this.saltando = false;
		this.contadorSalto = 0;
	}

	public void dibujar(Entorno entorno) {
		Image img = this.direccion ? IZQ : DER;
		entorno.dibujarImagen(img, this.x, this.y, 0.0);
	}

	public void mover(Entorno entorno) {
		double limiteIzq = this.getAncho() / 2;
		double limiteDer = entorno.ancho() - this.getAncho() / 2;

		// Mueve el personaje hacia la izquierda o la derecha según su dirección.
		if (this.direccion && this.x >= limiteIzq) {
			this.x -= this.velocidad;
		} else if (!this.direccion && this.x <= limiteDer) {
			this.x += this.velocidad;
		}
	}

	public void caerSubir() {
		// Si no está apoyado y no está saltando, entonces está cayendo
		if (!this.apoyado && !this.saltando) {
			this.cayendo = true;
			this.y += 3.0;
		} else {
			this.cayendo = false;
		}

		// Si está saltando
		if (this.saltando) {
			this.cayendo = false;
			this.y -= 7.0;
			this.contadorSalto++;
		}

		// Si el contador de salto supera un límite, el salto termina y empieza a caer
		if (this.contadorSalto > 21) {
			this.cayendo = true;
			this.saltando = false;
			this.contadorSalto = 0;
		}

		// Restablecer contador y velocidad de caída al aterrizar
		if (this.cayendo && this.apoyado) {
			this.cayendo = false;
			this.contadorSalto = 0;
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

	public boolean isCayendo() {
		return cayendo;
	}

	public void setCayendo(boolean cayendo) {
		this.cayendo = cayendo;
	}

	public boolean isSaltando() {
		return saltando;
	}

	public void setSaltando(boolean saltando) {
		this.saltando = saltando;
	}

	public int getContadorSalto() {
		return contadorSalto;
	}

	public void setContadorSalto(int contadorSalto) {
		this.contadorSalto = contadorSalto;
	}
}