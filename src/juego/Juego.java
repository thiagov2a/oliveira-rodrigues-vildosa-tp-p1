package juego;

import java.util.Random;
import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {

	private Entorno entorno;
	private Random rand;
	private Fondo fondo;
	private Piso[] pisos;
	private Enemigo[] enemigos;
	private Personaje personaje;
	private Lava lava;
	private int ticks;

	public Juego() {
		this.entorno = new Entorno(this, "Super Elizabeth Sis, Volcano Edition - Grupo 3", 800, 600);
		inicializarJuego();
		this.entorno.iniciar();
	}

	private void inicializarJuego() {
		this.rand = new Random();
		this.fondo = new Fondo(400.0, 301.0);

		int cantidadPisos = 4;
		this.pisos = crearPisos(cantidadPisos, 575.0);
		this.enemigos = crearEnemigos(cantidadPisos, 500.0);
		this.personaje = new Personaje(400.0, 500.0);
		this.lava = new Lava(400.0, 900.0);
	}

	private Piso[] crearPisos(int cantidadPisos, double yInicial) {
		Piso[] pisos = new Piso[cantidadPisos];
		for (int i = 0; i < cantidadPisos; i++) {
			boolean esPrimerPiso = (i == 0);
			pisos[i] = new Piso(yInicial, esPrimerPiso);
			yInicial -= 150.0; // Cambiar y para el próximo piso
		}
		return pisos;
	}

	private Enemigo[] crearEnemigos(int cantidadPisos, double yInicial) {
		Enemigo[] enemigos = new Enemigo[cantidadPisos * 2];
		int indice = 0;

		for (int i = 0; i < cantidadPisos; i++) {
			for (int j = 0; j < 2; j++) { // Crear dos enemigos por piso
				boolean esPrimerEnemigo = (j == 0);
				double x = esPrimerEnemigo ? rand.nextDouble(50.0, 300.0) : rand.nextDouble(500.0, 750.0);
				enemigos[indice++] = new Enemigo(x, yInicial);
			}
			yInicial -= 150.0; // Cambiar y para el próximo piso
		}
		return enemigos;
	}

	@Override
	public void tick() {
		renderizarJuego();
		ticks++;
		System.out.println(ticks);
	}

	private void renderizarJuego() {
		this.fondo.dibujar(entorno);
		dibujarPisos();
		dibujarEnemigos();
		dibujarPersonaje();
		dibujarLava();
	}

	private void dibujarPisos() {
		for (Piso piso : pisos) {
			if (piso != null) {
				piso.dibujarPiso(entorno);
			}
		}
	}

	private void dibujarEnemigos() {
		for (Enemigo enemigo : enemigos) {
			if (enemigo != null) {
				enemigo.setApoyado(detectarApoyo(enemigo, pisos));
				enemigo.mover(entorno);
				enemigo.caerDisparar(entorno);
				enemigo.dibujar(entorno);
			}
		}
	}

	public boolean detectarApoyo(Enemigo enemigo, Piso[] pisos) {
		for (Piso piso : pisos) {
			if (piso != null && detectarApoyo(enemigo, piso)) {
				return true;
			}
		}
		return false;
	}

	private boolean detectarApoyo(Enemigo enemigo, Piso piso) {
		for (Bloque bloque : piso.getBloques()) {
			if (bloque != null && detectarApoyo(enemigo, bloque)) {
				return true;
			}
		}
		return false;
	}

	private boolean detectarApoyo(Enemigo enemigo, Bloque bloque) {
		boolean estaSobreBloque = Math.abs(enemigo.getPiso() - bloque.getTecho()) < 3;
		boolean estaEntreLadosBloque = enemigo.getDerecha() > bloque.getIzquierda()
				&& enemigo.getIzquierda() < bloque.getDerecha();

		return estaSobreBloque && estaEntreLadosBloque;
	}

	private void dibujarPersonaje() {
		if (this.personaje != null) {
			personaje.setApoyado(detectarApoyo(personaje, pisos));

			if (detectarColision(personaje, pisos)) {
				personaje.setSaltando(false);
				personaje.setContadorSalto(0);
			}

			if (!detectarLados(personaje, pisos)) {
				if (entorno.estaPresionada(entorno.TECLA_DERECHA)) {
					personaje.setDireccion(false);
					personaje.mover(entorno);
				}

				if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
					personaje.setDireccion(true);
					personaje.mover(entorno);
				}
			}

			if (entorno.sePresiono('x') && !personaje.isSaltando() && !personaje.isCayendo()) {
				personaje.setSaltando(true);
			}

			personaje.caerSubir();

			personaje.dibujar(entorno);
		}
	}

	public boolean detectarApoyo(Personaje personaje, Piso[] pisos) {
		for (Piso piso : pisos) {
			if (piso != null && detectarApoyo(personaje, piso)) {
				return true;
			}
		}
		return false;
	}

	private boolean detectarApoyo(Personaje personaje, Piso piso) {
		if (personaje != null) {
			for (Bloque bloque : piso.getBloques()) {
				if (bloque != null && detectarApoyo(personaje, bloque)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean detectarApoyo(Personaje personaje, Bloque bloque) {
		boolean estaDebajoBloque = Math.abs(personaje.getPiso() - bloque.getTecho()) < 3;
		boolean estaEntreLadosBloque = personaje.getDerecha() > bloque.getIzquierda()
				&& personaje.getIzquierda() < bloque.getDerecha();

		return estaDebajoBloque && estaEntreLadosBloque;
	}

	public boolean detectarLados(Personaje personaje, Piso[] pisos) {
		for (Piso piso : pisos) {
			if (piso != null && detectarLados(personaje, piso)) {
				return true;
			}
		}
		return false;
	}

	private boolean detectarLados(Personaje personaje, Piso piso) {
		if (personaje != null) {
			for (Bloque bloque : piso.getBloques()) {
				if (bloque != null && detectarLados(personaje, bloque)) {
					return true;
				}
			}
		}
		return false;
	}

	
	// TODO: Refactorizar para lado izquierdo y derecho.
	private boolean detectarLados(Personaje personaje, Bloque bloque) {
		boolean estaEnMismaAltura = personaje.getPiso() > bloque.getTecho() && personaje.getTecho() < bloque.getPiso();
		boolean estaEnAlgunBorde = Math.abs(personaje.getDerecha() - bloque.getIzquierda()) < 2
				|| Math.abs(personaje.getIzquierda() - bloque.getDerecha()) < 2;

		return estaEnMismaAltura && estaEnAlgunBorde;
	}

	public boolean detectarColision(Personaje personaje, Piso[] pisos) {
		for (Piso piso : pisos) {
			if (piso != null && detectarColision(personaje, piso)) {
				return true;
			}
		}
		return false;
	}

	private boolean detectarColision(Personaje personaje, Piso piso) {
		Bloque[] bloques = piso.getBloques();
		for (int i = 0; i < bloques.length; i++) {
			if (bloques[i] != null && detectarColision(personaje, bloques[i])) {
				if (bloques[i].getTipo() == 0) {
					bloques[i] = null;
				}
				return true;
			}
		}
		return false;
	}

	private boolean detectarColision(Personaje personaje, Bloque bloque) {
		boolean estaSobreBloque = Math.abs(personaje.getTecho() - bloque.getPiso()) < 5;
		boolean estaEntreLadosBloque = personaje.getDerecha() > bloque.getIzquierda()
				&& personaje.getIzquierda() < bloque.getDerecha();

		return estaSobreBloque && estaEntreLadosBloque;
	}

	private void dibujarLava() {
		lava.subir(entorno);
		lava.dibujarse(entorno);
	}

	public static void main(String[] args) {
		new Juego();
	}
}
