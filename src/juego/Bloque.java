package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Bloque {

	// Imágenes estáticas para todos los enemigos, para optimizar recursos.
	private static final Image BEDROCK = Herramientas.cargarImagen("bedrock.png");
	private static final Image TIERRA = Herramientas.cargarImagen("tierra.png");
	private static final Image PIEDRA = Herramientas.cargarImagen("piedra.png");

	private double x;
	private double y;
	// Código del tipo de bloque (-1 para bedrock, 0 para tierra, 1 para piedra)
	private int tipoDeBloque;

	public Bloque() {
	}

	public Bloque(double x, double y, int tipoDeBloque) {
		this.x = x;
		this.y = y;
		this.tipoDeBloque = tipoDeBloque;
	}

	public void dibujarse(Entorno entorno) {
		Image img = seleccionarImagen();
		entorno.dibujarImagen(img, this.x, this.y, 0);
	}
	
	private Image seleccionarImagen() {
        switch (this.tipoDeBloque) {
            case -1: return BEDROCK;
            case 0: return TIERRA;
            default: return PIEDRA;
        }
    }

	public double getAncho() {
		return BEDROCK.getWidth(null);
	}

	public double getAlto() {
		return BEDROCK.getHeight(null);
	}
	
	//Getter & Setters
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

	public int getTipoDeBloque() {
		return tipoDeBloque;
	}

	public void setTipoDeBloque(int tipoDeBloque) {
		this.tipoDeBloque = tipoDeBloque;
	}

	public static Image getBedrock() {
		return BEDROCK;
	}

	public static Image getTierra() {
		return TIERRA;
	}

	public static Image getPiedra() {
		return PIEDRA;
	}

}
