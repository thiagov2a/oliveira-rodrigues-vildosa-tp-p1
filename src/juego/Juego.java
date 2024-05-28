package juego;

import java.awt.Image;
import java.util.Random;
import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {

	private static final Image VICTORIA = Herramientas.cargarImagen("victoria.gif");

	private Entorno entorno;
	private Random rand;
	private GestorColisiones gestorColisiones;
	private int ticks;
	private int tipoFinal;
	private boolean terminado;

	private Fondo fondo;
	private Piso[] pisos;
	private Enemigo[] enemigos;
	private Personaje personaje;
	private Lava lava;

	public Juego() {
		this.entorno = new Entorno(this, "Minecraft Edición 2D - Grupo 3", 800, 600);
		this.rand = new Random();
		this.gestorColisiones = new GestorColisiones();
		this.ticks = 0;
		this.tipoFinal = -1;
		this.terminado = false;
		inicializarJuego();
		this.entorno.iniciar();
	}

	private void inicializarJuego() {
		this.fondo = new Fondo(400.0, 300.0);
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
		if (!terminado) {
			renderizarJuego();
			ticks++;
			System.out.println(ticks);
		} else {
			System.out.println("LOL");
			pantallaFinal(tipoFinal);
		}
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

	// TODO: Hacer que aparezcan enemigos cuando hay menos de 2
	private void dibujarEnemigos() {
		for (int i = 0; i < enemigos.length; i++) {
			Enemigo enemigo = enemigos[i];
			if (enemigo != null) {
				manejarColision(enemigo);
				actualizarEstadoEnemigo(enemigo);
				dibujarProyectilEnemigo(enemigo);
				enemigo.dibujar(entorno);
			}
		}
	}

	private void manejarColision(Enemigo enemigo) {
		if (this.personaje != null && this.personaje.getProyectil() != null && this.gestorColisiones
				.detectarColisionProyectilPersonajeEnemigo(this.personaje.getProyectil(), enemigo)) {
			this.personaje.setProyectil(null);
			enemigo.setBaja(true); // Dar de baja al enemigo para que el proyectil siga en curso
		}

		if (manejarCondiciones(enemigo)
				&& gestorColisiones.detectarColisionProyectilEnemigoPersonaje(enemigo.getProyectil(), this.personaje)) {
			enemigo.setProyectil(null);
			this.personaje = null;
		}

		if (manejarCondiciones(enemigo)
				&& gestorColisiones.detectarColisionProyectilEnemigoEscudo(enemigo.getProyectil(), this.personaje)) {
			enemigo.setProyectil(null);
		}

	}

	private boolean manejarCondiciones(Enemigo enemigo) {
		return enemigo != null && enemigo.getProyectil() != null && this.personaje != null;
	}

	private void actualizarEstadoEnemigo(Enemigo enemigo) {
		if (enemigo.isBaja() && enemigo.getProyectil() == null) {
			eliminarEnemigo(enemigo);
		} else {
			enemigo.setApoyado(gestorColisiones.detectarApoyoEnemigo(enemigo, pisos));
			enemigo.mover(entorno);
			enemigo.caerDisparar(entorno);
		}
	}

	private void dibujarProyectilEnemigo(Enemigo enemigo) {
		Proyectil proyectil = enemigo.getProyectil();
		if (proyectil != null) {
			proyectil.disparar(entorno);
			proyectil.dibujar(entorno);
		}
	}

	private void eliminarEnemigo(Enemigo enemigo) {
		for (int i = 0; i < enemigos.length; i++) {
			if (enemigos[i] == enemigo) {
				enemigos[i] = null;
				break;
			}
		}
	}

	private void dibujarPersonaje() {
		if (this.personaje != null) {
			if (gestorColisiones.detectarColisionPersonajeEnemigo(personaje, enemigos)) {
				personaje = null;
				return; // Retorna en caso de colisión con enemigo.
			}

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

			if (entorno.sePresiono('z') && !personaje.isSaltando() && !personaje.isCayendo()) {
				personaje.escudar();
			}

			personaje.cargarDisparo();
			personaje.cargarEscudo();
			personaje.caerSubir();
			personaje.dibujar(entorno);
		}
	}

	private void dibujarLava() {
		lava.subir(entorno);
		lava.dibujar(entorno);
	}

	private void pantallaFinal(int tipoFinal) {
		switch (tipoFinal) {
		case 0:
			entorno.dibujarImagen(VICTORIA, 400.0, 300.0, 0.0);
		case 1:
			;
		default:
			;
		}
	}

	public void detectarColisiones() {
	}

	public static void main(String[] args) {
		new Juego();
	}
}
