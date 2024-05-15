package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Lava {

	private double x;
	private double y;
	private Image img;

	public Lava() {
	}

	public Lava(double x, double y) {
		this.x = x;
		this.y = y;
		this.img = Herramientas.cargarImagen("lava.gif");
	}

	public void dibujarse(Entorno entorno) {
		entorno.dibujarImagen(this.img, this.x, this.y, 0);
	}

	public void subir(Entorno entorno) {
		double borde = getAlto() / 2;
		if (this.y >= borde) {
			entorno.dibujarImagen(this.img, this.x, --this.y, 0);
		}
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

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}

}