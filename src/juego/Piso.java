package juego;

import java.util.Iterator;

import entorno.Entorno;

public class Piso {

	private Bloque[] bloques;
	private Enemigo[] enemigos;

	public Piso() {
	}

	public Piso(Bloque[] bloques, Enemigo[] enemigos) {
		this.bloques = bloques;
		this.enemigos = enemigos;
	}

	public void dibujarPiso(Entorno entorno) {
		for (int i = 0; i < this.bloques.length; i++) {
			this.bloques[i].dibujarse(entorno);
		}
		for (int i = 0; i < this.enemigos.length; i++) {
			this.enemigos[i].mover(entorno);
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
