package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Personaje {

	private double x;
	private double y;
	private Image img;

	public Personaje() {
	}

	public Personaje(double x, double y) {
		this.x = x;
		this.y = y;
		this.img = Herramientas.cargarImagen("personaje.png");
	}

	public void dibujarse(Entorno entorno) {
		entorno.dibujarImagen(this.img, this.x, this.y, 0);
	}

	public void moverDerecha(Entorno entorno) {
		this.img = Herramientas.cargarImagen("personaje.png");
		if (this.x <= 787.5) {
			this.x += 2; // Velocidad
			entorno.dibujarImagen(this.img, this.x, this.y, 0);			
		}
	}

	public void moverIzquierda(Entorno entorno) {
		this.img = Herramientas.cargarImagen("personaje2.png");
		if (this.x >= 12.5) {
			this.x -= 2; // Velocidad
			entorno.dibujarImagen(this.img, this.x, this.y, 0);			
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