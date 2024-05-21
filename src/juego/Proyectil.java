package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Proyectil {

	private final static Image FLECHA_IZQ = Herramientas.cargarImagen("flecha-izq.png");
	private final static Image FLECHA_DER = Herramientas.cargarImagen("flecha-der.png");

	private final static Image BOLA_IZQ = Herramientas.cargarImagen("flecha-izq.png");
	private final static Image BOLA_DER = Herramientas.cargarImagen("flecha-der.png");

	private double x;
	private double y;
	private double velocidad;
	private boolean direccion;
	private boolean tipo; // false = Proyectil de Personaje, true = Proyectil de Enemigo
	private boolean baja;

	public Proyectil() {
	}

	public Proyectil(double x, double y, boolean direccion, boolean tipo) {
		this.x = x;
		this.y = y;
		this.velocidad = 5.0;
		this.direccion = direccion;
		this.tipo = tipo;
	}

	public void dibujar(Entorno entorno) {
		Image img = seleccionarImagen();
		entorno.dibujarImagen(img, x, y, 0.0);
	}

	private Image seleccionarImagen() {
		if (this.tipo) {
			return this.direccion ? FLECHA_IZQ : FLECHA_DER;
		} else {
			return this.direccion ? BOLA_IZQ : BOLA_DER;
		}
	}

	public void disparar(Entorno entorno) {
		double limiteIzq = this.getAncho() / 2;
		double limiteDer = entorno.ancho() - this.getAncho() / 2;

		// Mueve el proyectil hacia la izquierda o la derecha según su dirección.
		if (this.direccion && this.x >= limiteIzq) {
			this.x -= this.velocidad;
		} else if (!this.direccion && this.x <= limiteDer) {
			this.x += this.velocidad;
		} else {
			baja = true; // Da de baja el proyectil en caso de llegar a los bordes.
		}
	}

	public double getAncho() {
		return FLECHA_IZQ.getWidth(null);
	}

	public double getAlto() {
		return FLECHA_IZQ.getHeight(null);
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

	// Getters & Setter

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

	public boolean isTipo() {
		return tipo;
	}

	public void setTipo(boolean tipo) {
		this.tipo = tipo;
	}

	public boolean isBaja() {
		return baja;
	}

	public void setBaja(boolean baja) {
		this.baja = baja;
	}
}
