package juego;

import java.util.Random;
import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {

	private Entorno entorno;
	private Random rand;
	private Fondo fondo;
	private Piso[] pisos;
	private Enemigo[] enemigos; // Un solo arreglo de enemigos
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
		this.fondo = new Fondo(400, 300);
		int cantidadPisos = 4;
		this.pisos = new Piso[cantidadPisos];
		int cantidadEnemigos = cantidadPisos * 2; // Dos enemigos por piso
		this.enemigos = new Enemigo[cantidadEnemigos];
		crearPisosYEnemigos(cantidadPisos, 575);
		this.personaje = new Personaje(400, 505, 3, false);
		this.lava = new Lava(400, 875, 0.1);
	}

	private void crearPisosYEnemigos(int cantidadPisos, double yInicial) {
		for (int i = 0; i < cantidadPisos; i++) {
			boolean esPrimerPiso = (i == 0);
			this.pisos[i] = new Piso(yInicial, esPrimerPiso);

			for (int j = 0; j < 2; j++) { // Crear dos enemigos por piso
				double x = rand.nextDouble(50, 750);
				boolean direccion = rand.nextBoolean();
				this.enemigos[i * 2 + j] = new Enemigo(x, yInicial - 70, 4, direccion);
			}
			yInicial -= 150;
		}
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
			piso.dibujarPiso(entorno);
		}
	}

	private void dibujarEnemigos() {
		for (Enemigo enemigo : enemigos) {
			enemigo.mover(entorno);
			enemigo.dibujar(entorno);
		}
	}

	private void dibujarPersonaje() {
		if (entorno.estaPresionada(entorno.TECLA_DERECHA)) {
			personaje.setDireccion(false);
			personaje.mover(entorno);
		}

		if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
			personaje.setDireccion(true);
			personaje.mover(entorno);
		}

		personaje.dibujar(entorno);
	}

	private void dibujarLava() {
		lava.subir(entorno);
		lava.dibujarse(entorno);
	}

	public static void main(String[] args) {
		new Juego();
	}
}
