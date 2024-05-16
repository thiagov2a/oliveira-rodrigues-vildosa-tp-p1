package juego;

import java.util.Random;
import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	// Variables propias
	private Random rand;
	private Fondo fondo;
	private Piso[] pisos;
	private Personaje personaje;
	private Lava lava;

	private int ticks;

	public Juego() {
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Super Elizabeth Sis, Volcano Edition - Grupo 3", 800, 600);

		// Inicializar lo que haga falta para el juego
		this.rand = new Random();

		// Tener en cuenta que la dimensión de la imagen afecta como se ve en pantalla,
		// por eso los 300px de más en el objeto lava
		this.fondo = new Fondo(400, 300);
		this.pisos = crearPisos(4, 575);
		this.personaje = new Personaje(400, 505, 2, false);
		this.lava = new Lava(400, 875, 0.1); // 575 + 300

		// Inicia el juego!
		this.entorno.iniciar();
	}

	@Override
	public void tick() {
		// Procesamiento de un instante de tiempo
		this.fondo.dibujar(entorno);
		dibujarPisos();
		dibujarPersonaje();
		dibujarLava();

		ticks++;
		System.out.println(ticks);
	}

	// Métodos propios
	// Dibujar pisos en pantalla
	private void dibujarPisos() {
		for (Piso piso : pisos) {
			piso.dibujarPiso(entorno);
		}
	}

	// Generación de pisos de manera automática
	private Piso[] crearPisos(int cant, double y) {
		Piso[] pisos = new Piso[cant];
		for (int i = 0; i < cant; i++) {
			int tipoDeBloque = (i == 0) ? -1 : 0;
			pisos[i] = crearPiso(y, tipoDeBloque);
			y -= 150;
		}
		return pisos;
	}

	private Piso crearPiso(double y, int tipoDeBloque) {
		return new Piso(crearBloques(y, tipoDeBloque), crearEnemigos(y));
	}

	private Bloque[] crearBloques(double y, int tipoDeBloque) {
		int cant = 16;
		double x = 25; // Posición inicial de x
		Bloque[] bloques = new Bloque[cant];
		for (int i = 0; i < cant; i++) {
			bloques[i] = crearBloque(x, y, tipoDeBloque);
			x += 50;
		}
		return bloques;
	}

	private Bloque crearBloque(double x, double y, int tipoDeBloque) {
		return new Bloque(x, y, tipoDeBloque);
	}

	private Enemigo[] crearEnemigos(double y) {
		y -= 70;
		int cant = 2;
		Enemigo[] enemigos = new Enemigo[cant];

		for (int i = 0; i < cant; i++) {
			double x = (i == 0) ? rand.nextDouble(12.5, 300) : rand.nextDouble(500, 787.5);
			boolean direccion = (i == 0) ? true : false;
			enemigos[i] = crearEnemigo(x, y, direccion);
		}

		return enemigos;
	}

	private Enemigo crearEnemigo(double x, double y, boolean direccion) {
		return new Enemigo(x, y, 4, direccion);
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
