package juego;

import java.awt.Image;
import java.util.Random;

import entorno.Entorno;
import entorno.Herramientas;

public class Personaje {

	// Imágenes estáticas del personaje, para optimizar recursos.
	private static final Image IZQ = Herramientas.cargarImagen("personaje-izq.png");
	private static final Image DER = Herramientas.cargarImagen("personaje-der.png");

	private static final Image TRIDENTE_IZQ = Herramientas.cargarImagen("personaje-tridente-izq.png");
	private static final Image TRIDENTE_DER = Herramientas.cargarImagen("personaje-tridente-der.png");

	private static final Image DISPARO_IZQ = Herramientas.cargarImagen("personaje-disparo-izq.png");
	private static final Image DISPARO_DER = Herramientas.cargarImagen("personaje-disparo-der.png");

	private static final double DIFERENCIA_PX = 12.0;
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
	private boolean disparando;
	private int contadorDisparo;
	private int estadoDisparo;
	private Proyectil proyectil;

	public Personaje() {
	}

	public Personaje(double x, double y) {
		this.x = x;
		this.y = y;
		this.velocidad = 3.0;
		this.direccion = rand.nextBoolean();
		this.apoyado = false;
		this.cayendo = false;
		this.saltando = false;
		this.contadorSalto = 0;
		this.disparando = false;
		this.contadorDisparo = 0;
		this.estadoDisparo = 0;
		this.proyectil = null;
	}

	public void dibujar(Entorno entorno) {
		Image img = seleccionarImagen();

		if (this.estadoDisparo == 0) {
			if (!this.disparando) {
				if (this.direccion) {
					entorno.dibujarImagen(img, this.x - DIFERENCIA_PX, this.y, 0.0);
				} else {
					entorno.dibujarImagen(img, this.x + DIFERENCIA_PX, this.y, 0.0);
				}
			} else {
				entorno.dibujarImagen(img, this.x, this.y, 0.0);
			}
		} else {
			entorno.dibujarImagen(img, this.x, this.y, 0.0);
		}

		dibujarProyectil(entorno);
	}

	private void dibujarProyectil(Entorno entorno) {
		if (this.proyectil != null) {
			this.proyectil.disparar(entorno);
			this.proyectil.dibujar(entorno);
		}
	}

	private Image seleccionarImagen() {
		if (this.estadoDisparo == 0) {
			if (!this.disparando) {
				return this.direccion ? TRIDENTE_IZQ : TRIDENTE_DER;
			} else {
				return this.direccion ? IZQ : DER;
			}
		} else {
			return this.direccion ? DISPARO_IZQ : DISPARO_DER;
		}
	}

	public void mover(Entorno entorno) {
		double limiteIZQ = this.getAncho() / 2;
		double limiteDER = entorno.ancho() - this.getAncho() / 2;

		// Mueve el personaje hacia la izquierda o la derecha según su dirección.
		if (this.direccion && this.x > limiteIZQ) {
			this.x -= this.velocidad;
		} else if (!this.direccion && this.x < limiteDER) {
			this.x += this.velocidad;
		}
	}

	public void caerSubir() {
		final double VELOCIDAD_CAIDA = 3.0;
		final double VELOCIDAD_SALTO = 5.0;
		final int LIMITE_SALTO = 30;

		// Verificar si está cayendo
		if (!this.apoyado && !this.saltando) {
			this.cayendo = true;
			this.y += VELOCIDAD_CAIDA;
		} else {
			this.cayendo = false;
		}

		// Ejecutar salto
		if (this.saltando) {
			this.y -= VELOCIDAD_SALTO;
			this.contadorSalto++;
			// Terminar salto si excede el límite
			if (this.contadorSalto > LIMITE_SALTO) {
				this.saltando = false;
				this.cayendo = true;
				this.contadorSalto = 0;
			}
		}
	}

	public void disparar() {
		if (!this.disparando && this.proyectil == null) {
			this.disparando = true;
			this.contadorDisparo = 0; // Inicia el contador de disparo
		}
	}

	public void cargarDisparo() {
		final int INICIO_DISPARO = 0;
		final int FINAL_DISPARO = 20;
		final int LIMITE_DISPARO = 160;

		// Si el proyectil ha sido disparado y está dado de baja, eliminarlo
		if (this.proyectil != null && this.proyectil.isBaja()) {
			this.proyectil = null;
		}

		// Manejo del disparo
		if (this.disparando) {
			// Comienza el disparo
			if (this.contadorDisparo == INICIO_DISPARO) {
				this.estadoDisparo = 1;
				this.proyectil = new Proyectil(this.x, this.y - this.getAlto() / 2.5, this.direccion, false);
			}

			// Finaliza la animación de disparo
			if (this.contadorDisparo == FINAL_DISPARO) {
				this.estadoDisparo = 0;
			}

			// Incrementar el contador de disparo
			this.contadorDisparo++;

			// Finalizar el estado de disparo después del límite
			if (this.contadorDisparo > LIMITE_DISPARO) {
				this.disparando = false;
				this.contadorDisparo = 0;
			}
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

	public boolean isDisparando() {
		return disparando;
	}

	public void setDisparando(boolean disparando) {
		this.disparando = disparando;
	}

	public int getContadorDisparo() {
		return contadorDisparo;
	}

	public void setContadorDisparo(int contadorDisparo) {
		this.contadorDisparo = contadorDisparo;
	}

	public int getEstadoDisparo() {
		return estadoDisparo;
	}

	public void setEstadoDisparo(int estadoDisparo) {
		this.estadoDisparo = estadoDisparo;
	}

	public Proyectil getProyectil() {
		return proyectil;
	}

	public void setProyectil(Proyectil proyectil) {
		this.proyectil = proyectil;
	}
}