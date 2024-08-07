package juego;

import java.util.Random;
import entorno.Entorno;

public class Piso {

	private static final Random rand = new Random();

	private Bloque[] bloques;

	public Piso(double y, boolean esPrimerPiso) {
		this.bloques = crearBloques(y, esPrimerPiso);
	}

	public static Piso[] crearPisos(int cantidad, double yInicial) {
		Piso[] pisos = new Piso[cantidad];
		for (int i = 0; i < cantidad; i++) {
			pisos[i] = new Piso(yInicial, i == 0);
			yInicial -= 150.0;
		}
		return pisos;
	}

	public void dibujarPiso(Entorno entorno) {
		for (Bloque bloque : bloques) {
			if (bloque != null) {
				bloque.dibujarse(entorno);
			}
		}
	}

	private Bloque[] crearBloques(double y, boolean esPrimerPiso) {
		int cantidad = 16;
		double x = 25.0;
		Bloque[] bloques = new Bloque[cantidad];

		for (int i = 0; i < cantidad; i++) {
			int tipoDeBloque;
			if (esPrimerPiso) {
				tipoDeBloque = -1;
			} else {
				tipoDeBloque = rand.nextInt(10) < 3 ? 1 : 0; // 30% de probabilidad para el tipo 1
			}
			bloques[i] = new Bloque(x, y, tipoDeBloque);
			x += 50.0;
		}

		// Asegurarse de que hay al menos un bloque destructible si no es el primer piso
		if (!esPrimerPiso && !hayBloqueDestructible(bloques)) {
			int indiceAleatorio = rand.nextInt(cantidad);
			bloques[indiceAleatorio].setTipo(0);
		}

		return bloques;
	}

	// Verifica si hay al menos un bloque destructible en el array
	private boolean hayBloqueDestructible(Bloque[] bloques) {
		for (Bloque bloque : bloques) {
			if (bloque.getTipo() == 0) {
				return true;
			}
		}
		return false;
	}

	public Bloque[] getBloques() {
		return bloques;
	}

	public void setBloques(Bloque[] bloques) {
		this.bloques = bloques;
	}
}
