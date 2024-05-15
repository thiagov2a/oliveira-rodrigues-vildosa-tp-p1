package juego;

import java.awt.Image;
import java.util.Random;

import entorno.Entorno;
import entorno.Herramientas;
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

		// Inicializar lo que haga falta para el jueg
		this.rand = new Random();
		this.pisos = crearPisos(4);
		// Tener en cuenta que la dimensión de la imagen afecta como se ve en pantalla,
		// por eso los 300px de más en el objeto lava
		this.lava = new Lava(400, 575 + 300);
		this.personaje = new Personaje(400, 505);

		// Inicia el juego!
		this.entorno.iniciar();
	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y por lo
	 * tanto es el método más importante de esta clase. Aquí se debe actualizar el
	 * estado interno del juego para simular el paso del tiempo (ver el enunciado
	 * del TP para mayor detalle).
	 */
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
	public void dibujarPisos() {
		for (int i = 0; i < this.pisos.length; i++) {
			this.pisos[i].dibujarPiso(entorno);
		}
	}

	// Generación de bloques de manera automática
	public Piso[] crearPisos(int cantPisos) {
		Piso[] pisos = new Piso[cantPisos];
		double y = 575; // Posición inicial de y
		//int cantEnemigos = 2;
		for (int i = 0; i < pisos.length; i++) {
			Piso piso = new Piso(crearBloques(y), crearEnemigos(2, y));
			pisos[i] = piso;
			y -= 150;
		}
		return pisos;
	}
	
	private Bloque[] crearBloques(double y) {
		int cantBloques = 16;
		double x = 25; // Posición inicial de x
		Bloque[] bloques = new Bloque[cantBloques];
		for (int i = 0; i < bloques.length; i++) {
			//boolean esPiedra = rand.int
			bloques[i] = new Bloque(x, y, false);
			x += 50;
		}
		return bloques;
	}
	
	private Enemigo[] crearEnemigos(int cant, double y) {
		double x = 200;
		y -= 70;
		Enemigo[] enemigos = new Enemigo[cant];
		for (int i = 0; i < enemigos.length; i++) {
			Image img = i == 0 ? Herramientas.cargarImagen("enemigo2.png") : Herramientas.cargarImagen("enemigo.png");
			enemigos[i] = new Enemigo(x, y, img);
			x += 400;
		}
		return enemigos;
	}

	public void dibujarPersonaje() {
		this.personaje.dibujarse(this.entorno);
		if (this.entorno.estaPresionada(this.entorno.TECLA_DERECHA)) {
			this.personaje.moverDerecha(this.entorno);
		}
		if (this.entorno.estaPresionada(this.entorno.TECLA_IZQUIERDA)) {
			this.personaje.moverIzquierda(this.entorno);
		}
	}

	public void dibujarLava() {
		int cantTicks = 6;
		this.lava.dibujarse(entorno); // Dibujar lava
		if (this.ticks % cantTicks == 0) {
			this.lava.subir(entorno); // Sube la lava cada x ticks
		}
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}