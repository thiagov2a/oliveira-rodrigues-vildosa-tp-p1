package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Corazon {

	private static final Image CORAZON = Herramientas.cargarImagen("corazon.png");
	private static final Image CORAZON_VACIO = Herramientas.cargarImagen("corazon-vacio.png");
	private static final Image PERDER_CORAZON = Herramientas.cargarImagen("perder-corazon.gif");

	private static final double DESPLAZAMIENTO_X = 34.0;

	private double x;
	private double y;
	private boolean perdido;
	private int contadorPerdido;
	private int estadoPerdido;

	public Corazon(double x, double y) {
		this.x = x;
		this.y = y;
		this.perdido = false;
		this.contadorPerdido = 0;
		this.estadoPerdido = 0;
	}

	public static Corazon[] crearCorazones(int cantidad, double inicioX, double y) {
		Corazon[] corazones = new Corazon[cantidad];
		for (int i = 0; i < cantidad; i++) {
			corazones[i] = new Corazon(inicioX + i * DESPLAZAMIENTO_X, y);
		}
		return corazones;
	}

	public void dibujar(Entorno entorno) {
		entorno.dibujarImagen(seleccionarImagen(), x, y, 0.0);
	}

	private Image seleccionarImagen() {
		if (this.estadoPerdido == 0) {
			return this.perdido ? CORAZON_VACIO : CORAZON;
		}
		return PERDER_CORAZON;
	}

	public void perder() {
		if (!this.perdido) {
			this.perdido = true;
			this.contadorPerdido = 0;
		}
	}

	public void actualizarAnimacion() {
		final int INICIO_ANIMACION = 0;
		final int FINAL_ANIMACION = 25;
		if (this.perdido) {
			if (this.contadorPerdido == INICIO_ANIMACION) {
				this.estadoPerdido = 1;
			}
			if (this.contadorPerdido == FINAL_ANIMACION) {
				this.estadoPerdido = 0;
			}
			this.contadorPerdido++;
		}
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

	public boolean isPerdido() {
		return perdido;
	}

	public void setPerdido(boolean perdido) {
		this.perdido = perdido;
	}

	public int getContadorPerdido() {
		return contadorPerdido;
	}

	public void setContadorPerdido(int contadorPerdido) {
		this.contadorPerdido = contadorPerdido;
	}

	public int getEstadoPerdido() {
		return estadoPerdido;
	}

	public void setEstadoPerdido(int estadoPerdido) {
		this.estadoPerdido = estadoPerdido;
	}
}