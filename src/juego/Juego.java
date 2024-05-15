package juego;

import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;

	// Variables propias
	// Piso[] pisos;
	Bloque[] pisoInicial;
	Lava lava;
	Personaje personaje;
	int ticks;

	Juego() {
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Super Elizabeth Sis, Volcano Edition - Grupo 3", 800, 600);

		// Inicializar lo que haga falta para el juego
		pisoInicial = crearPiso(575);

		// Tener en cuenta que la dimensión de la imagen afecta como se ve en pantalla,
		// por eso los 300px de más
		lava = new Lava(400, 575 + 300);
		personaje = new Personaje(400, 505);

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
		dibujarPiso(pisoInicial);
		
		personaje.dibujarse(entorno);
		if (entorno.estaPresionada(entorno.TECLA_DERECHA)) {
			personaje.moverDerecha(entorno);
		}
		if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
			personaje.moverIzquierda(entorno);
		}

		lava.dibujarse(entorno);
		if (ticks % 6 == 0) {
			lava.subir(entorno);
		}
		
		ticks++;
		System.out.println(ticks);
	}

	// Métodos propios

	// Dibujar lava
	// TODO: que la lava no se vaya del rango, con un reloj hacer que la lava suba
	// lentamente

	// Dibujar pisos en pantalla
	public void dibujarPiso(Bloque[] piso) {
		for (int i = 0; i < piso.length; i++) {
			piso[i].dibujarse(entorno);
		}
	}

	// Generación de bloques de manera automática
	public Bloque[] crearPiso(double y) {
		Bloque[] piso = new Bloque[16];
		double x = 25;

		for (int i = 0; i < piso.length; i++) {
			piso[i] = new Bloque(x, y, false);
			x += 50;
		}

		return piso;
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}