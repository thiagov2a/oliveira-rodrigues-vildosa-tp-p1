package juego;

import java.awt.Image;
import java.util.Random;

import entorno.Entorno;
import entorno.Herramientas;

public class Enemigo {

	// Imágenes estáticas para todos los enemigos, para optimizar recursos.
	private static final Image IZQ = Herramientas.cargarImagen("enemigo-izq.png");
	private static final Image DER = Herramientas.cargarImagen("enemigo-der.png");

	private static final Image ARCO_IZQ = Herramientas.cargarImagen("enemigo-arco-izq.png");
	private static final Image ARCO_DER = Herramientas.cargarImagen("enemigo-arco-der.png");

	private static final Image DISPARO_IZQ = Herramientas.cargarImagen("enemigo-disparo-izq.png");
	private static final Image DISPARO_DER = Herramientas.cargarImagen("enemigo-disparo-der.png");

	// Diferencia de píxeles entre la imagen de ARCO y DISPARO.
	private static final double DIFERENCIA_PX = 9.0;
	private static final Random rand = new Random();

	private double x;
	private double y;
	private double velocidad;
	// Dirección actual del enemigo (false indica hacia la derecha).
	private boolean direccion;
	private boolean apoyado;
	private boolean disparando;
	private int contadorDisparo;
	private int estadoDisparo;
	private Proyectil proyectil;
	private boolean baja;

	public Enemigo(double x, double y) {
		this.x = x;
		this.y = y;
		this.velocidad = 2.0;
		this.direccion = rand.nextBoolean();
		this.disparando = false;
		this.contadorDisparo = rand.nextInt(0, 20);
		this.estadoDisparo = 0;
		this.proyectil = null;
		this.baja = false;
	}

	public void dibujar(Entorno entorno) {
		Image img = seleccionarImagen();

		if (!this.baja) {
			if (this.estadoDisparo == 1 && this.disparando) {
				if (this.direccion) {
					entorno.dibujarImagen(img, this.x - DIFERENCIA_PX, this.y, 0.0);
				} else {
					entorno.dibujarImagen(img, this.x + DIFERENCIA_PX, this.y, 0.0);
				}
			} else {
				entorno.dibujarImagen(img, this.x, this.y, 0.0);
			}
		}
	}

	public boolean isBaja() {
		return baja;
	}

	public void setBaja(boolean baja) {
		this.baja = baja;
	}

	private Image seleccionarImagen() {
		if (this.estadoDisparo == 0) {
			return this.direccion ? ARCO_IZQ : ARCO_DER;
		} else {
			return this.direccion ? DISPARO_IZQ : DISPARO_DER;
		}
	}

	public void mover(Entorno entorno) {
		double limiteIZQ = this.getAncho() / 2;
		double limiteDER = entorno.ancho() - this.getAncho() / 2;

		if (this.apoyado) {
			// Mueve al enemigo hacia la izquierda o la derecha según su dirección.
			if (this.direccion && this.x >= limiteIZQ) {
				this.x -= this.velocidad;
			} else if (!this.direccion && this.x <= limiteDER) {
				this.x += this.velocidad;
			} else {
				this.direccion = !this.direccion; // Cambia la dirección si se alcanza cualquiera de los bordes.
			}
		}
	}

	public void caerDisparar(Entorno entorno) {
		final double VELOCIDAD_CAIDA = 3.0;
		final int INICIO_DISPARO = 0;
		final int FINAL_DISPARO = 20;
		final int LIMITE_DISPARO = 160;

		// Si no está apoyado, cae
		if (!this.apoyado) {
			this.y += VELOCIDAD_CAIDA;
		}

		// Si el proyectil ha sido disparado y está dado de baja, eliminarlo
		if (this.proyectil != null && this.proyectil.isBaja()) {
			this.proyectil = null;
		}

		// Si está apoyado y no hay proyectil, puede comenzar a disparar
		if (this.apoyado && this.proyectil == null) {
			this.disparando = true;
		}

		// Manejo del disparo
		if (this.disparando) {
			// Comienza el disparo
			if (this.contadorDisparo == INICIO_DISPARO) {
				this.estadoDisparo = 1;
				this.proyectil = new Proyectil(this.x, this.y - this.getAlto() / 4.5, this.direccion, true);
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
