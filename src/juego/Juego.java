package juego;

import java.util.Random;
import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {

	private Entorno entorno;
	private Random rand;
	private GestorColisiones gestorColisiones;
	private int ticks;

	private Fondo fondo;
	private Piso[] pisos;
	private Enemigo[] enemigos;
	private Personaje personaje;
	private Lava lava;

	public Juego() {
		this.entorno = new Entorno(this, "Super Elizabeth Sis, Volcano Edition - Grupo 3", 800, 600);
		this.rand = new Random();
		this.gestorColisiones = new GestorColisiones();
		this.ticks = 0;
		inicializarJuego();
		this.entorno.iniciar();
	}

	private void inicializarJuego() {
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
		for (int i = 0; i < enemigos.length; i++) {
			Enemigo enemigo = enemigos[i];
			if (enemigo != null) {
				if (gestorColisiones.detectarColisionEnemigoProyectil(personaje.getProyectil(), enemigo)) {
					personaje.setProyectil(null);
					enemigos[i] = null;
				} else {
					enemigo.setApoyado(gestorColisiones.detectarApoyoEnemigo(enemigo, pisos));
					enemigo.mover(entorno);
					enemigo.caerDisparar(entorno);
					enemigo.dibujar(entorno);
				}
			}
		}
	}

	private void dibujarPersonaje() {
		if (this.personaje != null) {
			personaje.setApoyado(gestorColisiones.detectarApoyoPersonaje(personaje, pisos));

			if (gestorColisiones.detectarColisionBloque(personaje, pisos)) {
				personaje.setSaltando(false);
				personaje.setContadorSalto(0);
			}

			if (entorno.estaPresionada(entorno.TECLA_DERECHA)
					&& !gestorColisiones.detectarColisionBloqueIZQ(personaje, pisos)) {
				personaje.setDireccion(false);
				personaje.mover(entorno);
			}

			if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)
					&& !gestorColisiones.detectarColisionBloqueDER(personaje, pisos)) {
				personaje.setDireccion(true);
				personaje.mover(entorno);
			}

			if (entorno.sePresiono('x') && !personaje.isSaltando() && !personaje.isCayendo()) {
				personaje.setSaltando(true);
			}

			if (entorno.sePresiono('c') && !personaje.isSaltando() && !personaje.isCayendo()) {
				personaje.disparar();
			}

			personaje.cargarDisparo();

			personaje.caerSubir();

			personaje.dibujar(entorno);
		}
	}

	private void dibujarLava() {
		lava.subir(entorno);
		lava.dibujarse(entorno);
	}

	public void detectarColisiones() {
	}

	public static void main(String[] args) {
		new Juego();
	}
}
