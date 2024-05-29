package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {

	private static final Image VICTORIA = Herramientas.cargarImagen("victoria.gif");
	private static final Image PERDISTE = Herramientas.cargarImagen("perdiste.gif");
	private static final int ANCHO_VENTANA = 800;
	private static final int ALTO_VENTANA = 600;
	private static final int CANTIDAD_PISOS = 4;
	private static final int CANTIDAD_CORAZONES = 5;

	private Entorno entorno;
	private GestorColisiones gestorColisiones;
	private boolean juegoTerminado;
	private boolean juegoGanado;
	private Fondo fondo;
	private Piso[] pisos;
	private Enemigo[] enemigos;
	private Personaje personaje;
	private Lava lava;
	private Corazon[] corazones;
	private Puntaje puntaje;

	public Juego() {
		this.entorno = new Entorno(this, "Minecraft Edición 2D - Grupo 3", ANCHO_VENTANA, ALTO_VENTANA);
		this.gestorColisiones = new GestorColisiones();
		this.juegoTerminado = false;
		this.juegoGanado = false;
		inicializarJuego();
		this.entorno.iniciar();
	}

	private void inicializarJuego() {
		this.fondo = new Fondo(ANCHO_VENTANA / 2.0, ALTO_VENTANA / 2.0);
		this.pisos = Piso.crearPisos(CANTIDAD_PISOS, 575.0);
		this.enemigos = Enemigo.crearEnemigos(CANTIDAD_PISOS, 500.0);
		this.personaje = new Personaje(400.0, 500.0);
		this.lava = new Lava(400.0, 900.0);
		this.corazones = Corazon.crearCorazones(CANTIDAD_CORAZONES, 25.0, 25.0);
		this.puntaje = new Puntaje(25.0, 75.0);
	}

	@Override
	public void tick() {
		if (!juegoTerminado) {
			actualizarJuego();
		} else {
			mostrarPantallaFinal();
		}
	}

	private void actualizarJuego() {
		renderizarJuego();
		// manejarInteracciones();
		verificarFinDelJuego();
	}

	private void renderizarJuego() {
		this.fondo.dibujar(entorno);
		dibujarPisos();
		dibujarEnemigos();
		dibujarPersonaje();
		lava.dibujar(entorno);
		dibujarHUD();
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
		if (this.personaje == null) {
			return;
		}

		// Colisión del proyectil del personaje con el enemigo
		if (this.personaje.getProyectil() != null
				&& gestorColisiones.proyectilColisionaEnemigo(this.personaje.getProyectil(), enemigo)) {
			this.personaje.setProyectil(null);
			enemigo.setBaja(true);
			puntaje.sumar();
		}

		// Colisión entre proyectiles del personaje y del enemigo
		if (this.personaje.getProyectil() != null && enemigo.getProyectil() != null && gestorColisiones
				.proyectilColisionaProyectilEnemigo(this.personaje.getProyectil(), enemigo.getProyectil())) {
			this.personaje.setProyectil(null);
			enemigo.setProyectil(null);
		}

		// Colisión del proyectil del enemigo con el personaje
		if (enemigo.getProyectil() != null) {
			if (gestorColisiones.proyectilColisionaPersonaje(enemigo.getProyectil(), this.personaje)) {
				enemigo.setProyectil(null);
				perderCorazon();
			}

			// Colisión del proyectil del enemigo con el escudo del personaje
			if (gestorColisiones.proyectilColisionaEscudo(enemigo.getProyectil(), this.personaje)) {
				enemigo.setProyectil(null);
			}
		}

		// Colisión del enemigo con la lava
		if (gestorColisiones.enemigoColisionaConLava(enemigo, this.lava)) {
			enemigo.setBaja(true);
		}
	}

	private void actualizarEstadoEnemigo(Enemigo enemigo) {
		if (enemigo.isBaja() && enemigo.getProyectil() == null) {
			eliminarEnemigo(enemigo);
		} else {
			enemigo.setApoyado(gestorColisiones.enemigoApoyado(enemigo, pisos));
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
		if (this.personaje == null) {
			return;
		}

		if (gestorColisiones.colisionaConLava(personaje, lava)
				|| gestorColisiones.personajeEnemigo(personaje, enemigos)) {
			perderCorazon();
		}

		personaje.setApoyado(gestorColisiones.personajeApoyado(personaje, pisos));

		if (gestorColisiones.colisionBloque(personaje, pisos)) {
			personaje.setSaltando(false);
			personaje.setContadorSalto(0);
		}

		if (entorno.estaPresionada(entorno.TECLA_DERECHA)
				&& !gestorColisiones.colisionBloqueIzquierdo(personaje, pisos)) {
			personaje.setDireccion(false);
			personaje.mover(entorno);
		}

		if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)
				&& !gestorColisiones.colisionBloqueDerecho(personaje, pisos)) {
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

	private void dibujarHUD() {
		for (Corazon corazon : corazones) {
			corazon.dibujar(entorno);
			corazon.actualizarAnimacion();
		}
		this.puntaje.dibujar(entorno);
	}

	private void perderCorazon() {
		for (int i = corazones.length - 1; i >= 0; i--) {
			if (!corazones[i].isPerdido()) {
				corazones[i].perder();
				break;
			}
		}
	}

	private void verificarFinDelJuego() {
		if (todosCorazonesPerdidos()) {
			juegoTerminado = true;
			juegoGanado = false;
		}
		if (personajeAlcanzoMeta()) {
			juegoTerminado = true;
			juegoGanado = true;
		}
	}

	private boolean todosCorazonesPerdidos() {
		for (Corazon corazon : corazones) {
			if (!corazon.isPerdido())
				return false;
		}
		return true;
	}

	private boolean personajeAlcanzoMeta() {
		return personaje.getY() < 0.0; // Assuming reaching the top of the screen is winning
	}

	private void mostrarPantallaFinal() {
		Image imagenFinal = juegoGanado ? VICTORIA : PERDISTE;
		entorno.dibujarImagen(imagenFinal, 400.0, 300.0, 0.0);
	}

	public static void main(String[] args) {
		new Juego();
	}
}
