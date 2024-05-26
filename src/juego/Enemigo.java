package juego;

import java.awt.Image;
import java.util.Random;

import entorno.Entorno;
import entorno.Herramientas;

public class Enemigo {

	// Imágenes estáticas para todos los enemigos, para optimizar recursos.
	// private static final Image IZQ =
	// Herramientas.cargarImagen("enemigo-izq.png");
	// private static final Image DER =
	// Herramientas.cargarImagen("enemigo-der.png");

	private static final Image ARCO_IZQ = Herramientas.cargarImagen("enemigo-arco-izq.png");
	private static final Image ARCO_DER = Herramientas.cargarImagen("enemigo-arco-der.png");

	private static final Image DISPARO_IZQ = Herramientas.cargarImagen("enemigo-disparo-izq.png");
	private static final Image DISPARO_DER = Herramientas.cargarImagen("enemigo-disparo-der.png");

	// Diferencia de píxeles entre la imagen de ARCO y DISPARO.
	private static final double DIFERENCIA_PX = 4.5;
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

	public Enemigo(double x, double y) {
		this.x = x;
		this.y = y;
		this.velocidad = 2.0;
		this.direccion = rand.nextBoolean();
		this.disparando = false;
		this.contadorDisparo = rand.nextInt(0, 20);
		this.estadoDisparo = 0;
		this.proyectil = null;
	}

	public void dibujar(Entorno entorno) {
		Image img = seleccionarImagen();
		if (this.disparando && this.direccion) {
			entorno.dibujarImagen(img, this.x - DIFERENCIA_PX, this.y, 0.0);
		} else if (this.disparando && !this.direccion) {
			entorno.dibujarImagen(img, this.x + DIFERENCIA_PX, this.y, 0.0);
		} else {
			entorno.dibujarImagen(img, this.x, this.y, 0.0);
		}
		dibujarProyectil(entorno);
	}

	private Image seleccionarImagen() {
		if (this.estadoDisparo == 0) {
			return this.direccion ? ARCO_IZQ : ARCO_DER;
		} else {
			return this.direccion ? DISPARO_IZQ : DISPARO_DER;
		}
	}

	private void dibujarProyectil(Entorno entorno) {
		if (this.proyectil != null) {
			this.proyectil.setDireccion(this.direccion);
			this.proyectil.disparar(entorno);
			this.proyectil.dibujar(entorno);
		}
	}

	public void mover(Entorno entorno) {
		double limiteIzq = this.getAncho() / 2;
		double limeteDer = entorno.ancho() - this.getAncho() / 2;

		if (this.apoyado) {
			// Mueve al enemigo hacia la izquierda o la derecha según su dirección.
			if (this.direccion && this.x >= limiteIzq) {
				this.x -= this.velocidad;
			} else if (!this.direccion && this.x <= limeteDer) {
				this.x += this.velocidad;
			} else {
				this.direccion = !this.direccion; // Cambia la dirección si se alcanza cualquiera de los bordes.
			}
		}
	}

	public void caerDisparar(Entorno entorno) {

		if (!this.apoyado) {
			this.y += 3.0;
		}

		if (this.proyectil != null && proyectil.isBaja()) {
			this.proyectil = null;
		}

		if (this.apoyado && this.proyectil == null) {
			this.disparando = true;
		}

		if (this.disparando) {

			if (this.contadorDisparo == 0) {
				this.estadoDisparo = 1;
				this.proyectil = new Proyectil(this.x, this.y - this.getAlto() / 4.5, this.direccion, true);
			}

			if (this.contadorDisparo == 20) {
				this.estadoDisparo = 0;
			}

			this.contadorDisparo++;
		}

		if (this.contadorDisparo > 160) {
			this.disparando = false;
			this.contadorDisparo = 0;
		}
	}

	public double getAncho() {
		return ARCO_IZQ.getWidth(null);
	}

	public double getAlto() {
		return ARCO_IZQ.getHeight(null);
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
