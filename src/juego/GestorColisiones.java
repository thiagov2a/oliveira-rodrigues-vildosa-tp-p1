package juego;

public class GestorColisiones {

	public GestorColisiones() {
	}

	// Enemigo

	public boolean detectarApoyoEnemigo(Enemigo enemigo, Piso[] pisos) {
		for (Piso piso : pisos) {
			if (piso != null && detectarApoyoEnemigo(enemigo, piso)) {
				return true;
			}
		}
		return false;
	}

	private boolean detectarApoyoEnemigo(Enemigo enemigo, Piso piso) {
		for (Bloque bloque : piso.getBloques()) {
			if (bloque != null && detectarApoyoEnemigo(enemigo, bloque)) {
				return true;
			}
		}
		return false;
	}

	private boolean detectarApoyoEnemigo(Enemigo enemigo, Bloque bloque) {
		boolean estaSobreBloque = Math.abs(enemigo.getPiso() - bloque.getTecho()) < 3;
		boolean estaEntreLadosBloque = enemigo.getDerecha() > bloque.getIzquierda()
				&& enemigo.getIzquierda() < bloque.getDerecha();
		return estaSobreBloque && estaEntreLadosBloque;
	}

	// Personaje

	public boolean detectarApoyoPersonaje(Personaje personaje, Piso[] pisos) {
		for (Piso piso : pisos) {
			if (piso != null && detectarApoyoPersonaje(personaje, piso)) {
				return true;
			}
		}
		return false;
	}

	private boolean detectarApoyoPersonaje(Personaje personaje, Piso piso) {
		if (personaje != null) {
			for (Bloque bloque : piso.getBloques()) {
				if (bloque != null && detectarApoyoPersonaje(personaje, bloque)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean detectarApoyoPersonaje(Personaje personaje, Bloque bloque) {
		boolean estaSobreBloque = Math.abs(personaje.getPiso() - bloque.getTecho()) < 3;
		boolean estaEntreLadosBloque = personaje.getDerecha() > bloque.getIzquierda()
				&& personaje.getIzquierda() < bloque.getDerecha();
		return estaSobreBloque && estaEntreLadosBloque;
	}

	public boolean detectarColisionBloque(Personaje personaje, Piso[] pisos) {
		for (Piso piso : pisos) {
			if (piso != null && detectarColisionBloque(personaje, piso)) {
				return true;
			}
		}
		return false;
	}

	private boolean detectarColisionBloque(Personaje personaje, Piso piso) {
		Bloque[] bloques = piso.getBloques();
		for (int i = 0; i < bloques.length; i++) {
			if (bloques[i] != null && detectarColisionBloque(personaje, bloques[i])) {
				if (bloques[i].getTipo() == 0) {
					bloques[i] = null;
				}
				return true;
			}
		}
		return false;
	}

	private boolean detectarColisionBloque(Personaje personaje, Bloque bloque) {
		boolean estaDebajoBloque = Math.abs(personaje.getTecho() - bloque.getPiso()) < 5;
		boolean estaEntreLadosBloque = personaje.getDerecha() > bloque.getIzquierda()
				&& personaje.getIzquierda() < bloque.getDerecha();
		return estaDebajoBloque && estaEntreLadosBloque;
	}

	public boolean detectarColisionBloqueIZQ(Personaje personaje, Piso[] pisos) {
		for (Piso piso : pisos) {
			if (piso != null && detectarColisionBloqueIZQ(personaje, piso)) {
				return true;
			}
		}
		return false;
	}

	private boolean detectarColisionBloqueIZQ(Personaje personaje, Piso piso) {
		if (personaje != null) {
			for (Bloque bloque : piso.getBloques()) {
				if (bloque != null && detectarColisionBloqueIZQ(personaje, bloque)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean detectarColisionBloqueIZQ(Personaje personaje, Bloque bloque) {
		boolean estaEnBordeIZQ = Math.abs(personaje.getDerecha() - bloque.getIzquierda()) < 3;
		boolean estaEnMismaAltura = personaje.getPiso() > bloque.getTecho() + 2
				&& personaje.getTecho() < bloque.getPiso();
		return estaEnMismaAltura && estaEnBordeIZQ;
	}

	public boolean detectarColisionBloqueDER(Personaje personaje, Piso[] pisos) {
		for (Piso piso : pisos) {
			if (piso != null && detectarColisionBloqueDER(personaje, piso)) {
				return true;
			}
		}
		return false;
	}

	private boolean detectarColisionBloqueDER(Personaje personaje, Piso piso) {
		if (personaje != null) {
			for (Bloque bloque : piso.getBloques()) {
				if (bloque != null && detectarColisionBloqueDER(personaje, bloque)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean detectarColisionBloqueDER(Personaje personaje, Bloque bloque) {
		boolean estaEnBordeDER = Math.abs(personaje.getIzquierda() - bloque.getDerecha()) < 3;
		boolean estaEnMismaAltura = personaje.getPiso() > bloque.getTecho() + 2
				&& personaje.getTecho() < bloque.getPiso();
		return estaEnMismaAltura && estaEnBordeDER;
	}

	public boolean detectarColisionEnemigoProyectil(Personaje personaje, Enemigo[] enemigos) {
		for (Enemigo enemigo : enemigos) {
			if (enemigo != null && detectarColisionEnemigoProyectil(personaje, enemigo)) {
				return true;
			}
		}
		return false;
	}

	public boolean detectarColisionEnemigoProyectil(Personaje personaje, Enemigo enemigo) {
		if (personaje != null && detectarColisionEnemigoProyectil(personaje.getProyectil(), enemigo)) {
			return true;
		}
		return false;
	}

	public boolean detectarColisionEnemigoProyectil(Proyectil proyectil, Enemigo enemigo) {
		if (proyectil != null && enemigo != null) {
			return (proyectil.getX() - enemigo.getX()) * (proyectil.getX() - enemigo.getX())
					+ (proyectil.getY() - enemigo.getY()) * (proyectil.getY() - enemigo.getY()) < 50 * 50;
		}
		return false;
	}

}
