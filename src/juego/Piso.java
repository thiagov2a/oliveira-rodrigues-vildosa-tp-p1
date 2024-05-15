package juego;

import entorno.Entorno;

public class Piso {

	private Bloque[] bloques;
	private Enemigo[] enemigos;

	public Piso(Bloque[] bloques, Enemigo[] enemigos) {
		this.bloques = bloques;
		this.enemigos = enemigos;
	}

	public void dibujarPiso(Entorno entorno) {
		for (Bloque bloque : bloques) {
			bloque.dibujarse(entorno);
		}
		for (Enemigo enemigo : enemigos) {
			enemigo.mover(entorno);
			enemigo.dibujar(entorno);
		}
	}

	public Bloque[] getBloques() {
		return bloques;
	}

	public void setBloques(Bloque[] bloques) {
		this.bloques = bloques;
	}

	public Enemigo[] getEnemigos() {
		return enemigos;
	}

	public void setEnemigos(Enemigo[] enemigos) {
		this.enemigos = enemigos;
	}

}
