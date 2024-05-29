package juego;

public class GestorColisiones {

	public GestorColisiones() {
	}

	public boolean enemigoApoyado(Enemigo enemigo, Piso[] pisos) {
		for (Piso piso : pisos) {
			if (piso != null && apoyadoSobrePiso(enemigo, piso)) {
				return true;
			}
		}
		return false;
	}

	private boolean apoyadoSobrePiso(Enemigo enemigo, Piso piso) {
		for (Bloque bloque : piso.getBloques()) {
			if (bloque != null && apoyadoSobreBloque(enemigo, bloque)) {
				return true;
			}
		}
		return false;
	}

	private boolean apoyadoSobreBloque(Enemigo enemigo, Bloque bloque) {
		boolean sobreBloque = Math.abs(enemigo.getPiso() - bloque.getTecho()) < 3;
		boolean entreLadosBloque = enemigo.getDerecha() > bloque.getIzquierda()
				&& enemigo.getIzquierda() < bloque.getDerecha();
		return sobreBloque && entreLadosBloque;
	}

	public boolean proyectilEnemigoPersonaje(Enemigo[] enemigos, Personaje personaje) {
		for (Enemigo enemigo : enemigos) {
			if (proyectilColisionaPersonaje(enemigo.getProyectil(), personaje)) {
				return true;
			}
		}
		return false;
	}

	public boolean proyectilColisionaPersonaje(Proyectil proyectil, Personaje personaje) {
		return (proyectil.getX() - personaje.getX()) * (proyectil.getX() - personaje.getX())
				+ (proyectil.getY() - personaje.getY()) * (proyectil.getY() - personaje.getY()) < 40 * 40;
	}

	public boolean proyectilEnemigoEscudo(Enemigo[] enemigos, Personaje personaje) {
		for (Enemigo enemigo : enemigos) {
			if (proyectilColisionaEscudo(enemigo.getProyectil(), personaje)) {
				return true;
			}
		}
		return false;
	}

	public boolean proyectilColisionaEscudo(Proyectil proyectil, Personaje personaje) {
		if (proyectil != null && personaje != null) {
			if (personaje.isEscudando() && personaje.isDireccion()) {
				return Math.abs(proyectil.getDerecha() - (personaje.getIzquierda() - 12)) < 5;
			} else if (personaje.isEscudando() && !personaje.isDireccion()) {
				return Math.abs(proyectil.getIzquierda() - (personaje.getDerecha() + 12)) < 5;
			}
		}
		return false;
	}

	public boolean personajeApoyado(Personaje personaje, Piso[] pisos) {
		for (Piso piso : pisos) {
			if (piso != null && apoyoSobrePiso(personaje, piso)) {
				return true;
			}
		}
		return false;
	}

	private boolean apoyoSobrePiso(Personaje personaje, Piso piso) {
		if (personaje != null) {
			for (Bloque bloque : piso.getBloques()) {
				if (bloque != null && apoyoSobreBloque(personaje, bloque)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean apoyoSobreBloque(Personaje personaje, Bloque bloque) {
		boolean sobreBloque = Math.abs(personaje.getPiso() - bloque.getTecho()) < 3;
		boolean entreLadosBloque = personaje.getDerecha() > bloque.getIzquierda()
				&& personaje.getIzquierda() < bloque.getDerecha();
		return sobreBloque && entreLadosBloque;
	}

	public boolean colisionBloque(Personaje personaje, Piso[] pisos) {
		for (Piso piso : pisos) {
			if (piso != null && colisionConBloque(personaje, piso)) {
				return true;
			}
		}
		return false;
	}

	private boolean colisionConBloque(Personaje personaje, Piso piso) {
		Bloque[] bloques = piso.getBloques();
		for (int i = 0; i < bloques.length; i++) {
			if (bloques[i] != null && colisionConBloque(personaje, bloques[i])) {
				if (bloques[i].getTipo() == 0) {
					bloques[i] = null;
				}
				return true;
			}
		}
		return false;
	}

	private boolean colisionConBloque(Personaje personaje, Bloque bloque) {
		boolean debajoBloque = Math.abs(personaje.getTecho() - bloque.getPiso()) < 5;
		boolean entreLadosBloque = personaje.getDerecha() > bloque.getIzquierda()
				&& personaje.getIzquierda() < bloque.getDerecha();
		return debajoBloque && entreLadosBloque;
	}

	public boolean colisionBloqueIzquierdo(Personaje personaje, Piso[] pisos) {
		for (Piso piso : pisos) {
			if (piso != null && colisionConBloqueIzquierdo(personaje, piso)) {
				return true;
			}
		}
		return false;
	}

	private boolean colisionConBloqueIzquierdo(Personaje personaje, Piso piso) {
		if (personaje != null) {
			for (Bloque bloque : piso.getBloques()) {
				if (bloque != null && colisionConBloqueIzquierdo(personaje, bloque)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean colisionConBloqueIzquierdo(Personaje personaje, Bloque bloque) {
		boolean enBordeIzquierdo = Math.abs(personaje.getDerecha() - bloque.getIzquierda()) < 3;
		boolean enMismaAltura = personaje.getPiso() > bloque.getTecho() + 2 && personaje.getTecho() < bloque.getPiso();
		return enMismaAltura && enBordeIzquierdo;
	}

	public boolean colisionBloqueDerecho(Personaje personaje, Piso[] pisos) {
		for (Piso piso : pisos) {
			if (piso != null && colisionConBloqueDerecho(personaje, piso)) {
				return true;
			}
		}
		return false;
	}

	private boolean colisionConBloqueDerecho(Personaje personaje, Piso piso) {
		if (personaje != null) {
			for (Bloque bloque : piso.getBloques()) {
				if (bloque != null && colisionConBloqueDerecho(personaje, bloque)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean colisionConBloqueDerecho(Personaje personaje, Bloque bloque) {
		boolean enBordeDerecho = Math.abs(personaje.getIzquierda() - bloque.getDerecha()) < 3;
		boolean enMismaAltura = personaje.getPiso() > bloque.getTecho() + 2 && personaje.getTecho() < bloque.getPiso();
		return enMismaAltura && enBordeDerecho;
	}

	public boolean proyectilColisionaEnemigo(Proyectil proyectil, Enemigo enemigo) {
		return (proyectil.getX() - enemigo.getX()) * (proyectil.getX() - enemigo.getX())
				+ (proyectil.getY() - enemigo.getY()) * (proyectil.getY() - enemigo.getY()) < 40 * 40;
	}

	public boolean proyectilColisionaProyectilEnemigo(Proyectil proyectil, Proyectil enemigo) {
		return (proyectil.getX() - enemigo.getX()) * (proyectil.getX() - enemigo.getX())
				+ (proyectil.getY() - enemigo.getY()) * (proyectil.getY() - enemigo.getY()) < 40 * 40;
	}

	public boolean personajeEnemigo(Personaje personaje, Enemigo[] enemigos) {
		if (personaje != null) {
			for (Enemigo enemigo : enemigos) {
				if (enemigo != null && !enemigo.isBaja() && colisionaConEnemigo(personaje, enemigo)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean colisionaConEnemigo(Personaje personaje, Enemigo enemigo) {
		return (personaje.getX() - enemigo.getX()) * (personaje.getX() - enemigo.getX())
				+ (personaje.getY() - enemigo.getY()) * (personaje.getY() - enemigo.getY()) < 25 * 25;
	}

	public boolean colisionaConLava(Personaje personaje, Lava lava) {
		return Math.abs(personaje.getPiso() - lava.getTecho()) < 3;
	}

	public boolean enemigoColisionaConLava(Enemigo enemigo, Lava lava) {
		return Math.abs(enemigo.getPiso() - lava.getTecho()) < 3;
	}

}
