package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Fondo {

	private static final Image IMG = Herramientas.cargarImagen("fondo.png");
	
	private double x;
	private double y;
	
	public Fondo() {
	}
	
	public Fondo(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void dibujar(Entorno entorno) {
		entorno.dibujarImagen(IMG, x, y, 0);
	}
	
	public double getAncho() {
		return IMG.getWidth(null);
	}

	public double getAlto() {
		return IMG.getHeight(null);
	}

	//Getters & Setters
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

	public static Image getImg() {
		return IMG;
	}
	
}
