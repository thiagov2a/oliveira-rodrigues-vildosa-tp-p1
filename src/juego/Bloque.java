package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Bloque {

	private double x;
	private double y;
	private boolean esPiedra;
	private Image img;

	public Bloque() {
	}

	public Bloque(double x, double y, boolean esPiedra) {
		this.x = x;
		this.y = y;
		this.esPiedra = esPiedra;
		this.img = esPiedra ? Herramientas.cargarImagen("piedra.png") : Herramientas.cargarImagen("tierra.png");
	}
	
	public void dibujarse(Entorno entorno) {
		entorno.dibujarImagen(img, x, y, 0);
	}
	
	public double getAncho() {
		return img.getWidth(null);
	}

	public double getAlto() {
		return img.getHeight(null);
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

	public boolean isEsPiedra() {
		return esPiedra;
	}

	public void setEsPiedra(boolean esPiedra) {
		this.esPiedra = esPiedra;
	}

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}

}
