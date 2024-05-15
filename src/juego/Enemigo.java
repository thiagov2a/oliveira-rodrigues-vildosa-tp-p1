package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Enemigo {

	private double x;
	private double y;
	private Image img;
	
	public Enemigo() {
	}
	
	public Enemigo(double x, double y, Image img) {
		this.x = x;
		this.y = y;
		this.img = img;
	}
	
	public void dibujarse(Entorno entorno) {
		entorno.dibujarImagen(this.img, this.x, this.y, 0);
	}
	
	public void mover(Entorno entorno) {
		if (img == Herramientas.cargarImagen("enemigo.png") && this.x <= 787.5) {
			this.x += 2;
			entorno.dibujarImagen(this.img, this.x, this.y, 0);			
		} else {
			this.img = Herramientas.cargarImagen("enemigo2.png");
		}
		
		if (img == Herramientas.cargarImagen("enemigo2.png") && this.x >= 12.5) {
			this.x -= 2;
			entorno.dibujarImagen(this.img, this.x, this.y, 0);		
		} else {
			this.img = Herramientas.cargarImagen("enemigo.png");
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
