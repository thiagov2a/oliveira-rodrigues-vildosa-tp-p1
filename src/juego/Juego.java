package juego;

import java.util.Random;
import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	// Variables propias
	private Random rand;
	private Piso[] pisos;
	private Lava lava;
	private Personaje personaje;
	private int ticks;

	public Juego() {
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Super Elizabeth Sis, Volcano Edition - Grupo 3", 800, 600);

		// Inicializar lo que haga falta para el juego
		this.rand = new Random();
		this.pisos = crearPisos(4, 575);
		// Tener en cuenta que la dimensión de la imagen afecta como se ve en pantalla,
		// por eso los 300px de más en el objeto lava
		this.lava = new Lava(400, 875); // 575 + 300
		this.personaje = new Personaje(400, 505, 2, false);

		// Inicia el juego!
		this.entorno.iniciar();
	}

	@Override
	public void tick() {
		// Procesamiento de un instante de tiempo
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
			pisos[i] = crearPiso(y);
			y -= 150;
		}
		return pisos;
	}

	private Piso crearPiso(double y) {
		return new Piso(crearBloques(y), crearEnemigos(y));
	}

	private Bloque[] crearBloques(double y) {
		int cant = 16;
		double x = 25; // Posición inicial de x
		Bloque[] bloques = new Bloque[cant];
		for (int i = 0; i < cant; i++) {
			bloques[i] = crearBloque(x, y, false);
			x += 50;
		}
		return bloques;
	}

	private Bloque crearBloque(double x, double y, boolean esPiedra) {
		return new Bloque(x, y, esPiedra);
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
		int cantTicks = 6;
		lava.dibujarse(entorno); // Dibujar lava
		if (ticks % cantTicks == 0) {
			lava.subir(entorno); // Sube la lava cada x ticks
		}
	}

	public static void main(String[] args) {
		new Juego();
	}

}
