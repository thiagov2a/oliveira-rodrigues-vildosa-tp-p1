package juego;

import java.awt.Image;

import entorno.Herramientas;

public class Fondo {

	private double x;
	private double y;
	private Image img;
	
	public Fondo() {
	}
	
	public Fondo(double x, double y) {
		this.x = x;
		this.y = y;
		this.img = Herramientas.cargarImagen(null);
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

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}
	
}
