package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class lienzo extends JPanel {

	private Point jugador = new Point(10, 80);
	private Point objeto1 = new Point(10, 20);
	
	// Variables para el movimiento del objeto
	private int objetoVelocidadX = 2;
	private int objetoVelocidadY = 2;
	private Random random = new Random();

	private int puntos   = 0;
	private int vidas    = 3;
	private boolean gameOver = false;
	private boolean gameWon  = false;

	private BufferedImage fondo;
	
	// Timer para movimiento del objeto
	private javax.swing.Timer movimientoTimer;

	public lienzo() {
		try {
			fondo = ImageIO.read(new File("src/img/chivo.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Iniciar el movimiento automático del objeto (velocidad aumentada)
		iniciarMovimientoObjeto();
		
		repaint();
	}
	
	private void iniciarMovimientoObjeto() {
		movimientoTimer = new javax.swing.Timer(50, e -> { // 50ms (más rápido que antes)
			if (!gameOver && !gameWon) {
				moverObjeto();
				verificarColisiones();
				repaint();
			}
		});
		movimientoTimer.start();
	}
	
	private void moverObjeto() {
		// Mover el objeto
		int nuevoX = objeto1.x + objetoVelocidadX;
		int nuevoY = objeto1.y + objetoVelocidadY;
		
		// Rebote en los bordes (con el tamaño del objeto)
		if (nuevoX <= 0 || nuevoX + 50 >= getWidth()) {
			objetoVelocidadX = -objetoVelocidadX;
			nuevoX = objeto1.x + objetoVelocidadX;
		}
		
		if (nuevoY <= 0 || nuevoY + 50 >= getHeight()) {
			objetoVelocidadY = -objetoVelocidadY;
			nuevoY = objeto1.y + objetoVelocidadY;
		}
		
		objeto1.setLocation(nuevoX, nuevoY);
	}
	
	// Método para cambiar dirección aleatoriamente al colisionar
	private void cambiarDireccionAleatoria() {
		objetoVelocidadX = random.nextInt(5) + 2; // Velocidad 2-6
		objetoVelocidadY = random.nextInt(5) + 2;
		
		// Asignar dirección aleatoria
		if (random.nextBoolean()) objetoVelocidadX = -objetoVelocidadX;
		if (random.nextBoolean()) objetoVelocidadY = -objetoVelocidadY;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (fondo != null) {
			g.drawImage(fondo, 0, 0, getWidth(), getHeight(), null);
		}

		if (gameOver) {
			dibujarPantalla(g, "GAME OVER", Color.RED);
			return;
		}
		if (gameWon) {
			dibujarPantalla(g, "GANASTE!", Color.YELLOW);
			return;
		}

		// Dibujar objeto en movimiento (cuadrado rojo)
		g.setColor(Color.white);
		g.drawRect(objeto1.x, objeto1.y, 50, 50);
		g.setColor(Color.red);
		g.fillRect(objeto1.x + 5, objeto1.y + 5, 40, 40);

		g.setColor(Color.white);
		g.fillOval(jugador.x, jugador.y, 30, 30);

		g.setColor(Color.green);
		g.drawOval(10, 150, 50, 50);

		int[] x = {35, 14, 57};
		int[] y = {150, 188, 188};
		g.setColor(Color.black);
		g.fillPolygon(x, y, 3);

		dibujarHUD(g);
	}
	
	// Método público para reiniciar el movimiento
	public void reiniciarMovimiento() {
		if (movimientoTimer != null && movimientoTimer.isRunning()) {
			movimientoTimer.stop();
		}
		objetoVelocidadX = random.nextInt(5) + 2;
		objetoVelocidadY = random.nextInt(5) + 2;
		if (random.nextBoolean()) objetoVelocidadX = -objetoVelocidadX;
		if (random.nextBoolean()) objetoVelocidadY = -objetoVelocidadY;
		iniciarMovimientoObjeto();
	}

	private void dibujarHUD(Graphics g) {
		g.setFont(new Font("Segoe UI", Font.BOLD, 12));
		g.setColor(Color.WHITE);
		g.fillRoundRect(3, 2, 170, 20, 6, 6);
		g.setColor(Color.BLACK);
		g.drawString("Puntos: " + puntos + "   Vidas: " + vidas, 8, 16);
	}

	private void dibujarPantalla(Graphics g, String msg, Color color) {
		g.setColor(new Color(0, 0, 0, 160));
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(color);
		g.setFont(new Font("Segoe UI", Font.BOLD, 26));
		int ancho = g.getFontMetrics().stringWidth(msg);
		g.drawString(msg, (getWidth() - ancho) / 2, getHeight() / 2 - 10);
		g.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		String sub = "Puntos: " + puntos;
		int aw = g.getFontMetrics().stringWidth(sub);
		g.setColor(Color.WHITE);
		g.drawString(sub, (getWidth() - aw) / 2, getHeight() / 2 + 18);
	}

	private boolean dentroLimites(int nx, int ny) {
		return nx >= 0
			&& ny >= 0
			&& nx + 30 <= getWidth()
			&& ny + 30 <= getHeight();
	}

	private void verificarColisiones() {
		Rectangle rJugador   = new Rectangle(jugador.x, jugador.y, 30, 30);
		Rectangle rObjeto1   = new Rectangle(objeto1.x + 5, objeto1.y + 5, 40, 40);
		Rectangle rOvalo     = new Rectangle(10, 150, 50, 50);
		Rectangle rTriangulo = new Rectangle(14, 150, 43, 38);

		if (rJugador.intersects(rObjeto1)) {
			puntos += 10;
			// El objeto cambia de dirección al colisionar
			cambiarDireccionAleatoria();
			// Mover el objeto un poco para evitar colisión múltiple
			objeto1.setLocation(objeto1.x + objetoVelocidadX, objeto1.y + objetoVelocidadY);
			
			if (puntos >= 50) { 
				gameWon = true;
				if (movimientoTimer != null) movimientoTimer.stop();
			}
		}

		if (rJugador.intersects(rOvalo)) {
			vidas--;
			jugador.setLocation(10, 80);
			if (vidas <= 0) { 
				gameOver = true;
				if (movimientoTimer != null) movimientoTimer.stop();
			}
		}

		if (rJugador.intersects(rTriangulo)) {
			vidas--;
			jugador.setLocation(10, 80);
			if (vidas <= 0) { 
				gameOver = true;
				if (movimientoTimer != null) movimientoTimer.stop();
			}
		}
	}

	public void setJugadorX(int x) {
		if (gameOver || gameWon) return;
		int nx = jugador.x + x;
		if (dentroLimites(nx, jugador.y)) {
			this.jugador.x += x;
			verificarColisiones();
		}
		repaint();
	}

	public void setJugadorY(int y) {
		if (gameOver || gameWon) return;
		int ny = jugador.y + y;
		if (dentroLimites(jugador.x, ny)) {
			this.jugador.y += y;
			verificarColisiones();
		}
		repaint();
	}

	public void setObjeto1(int x, int y) {
		objeto1.setLocation(x, y);
		verificarColisiones();
		repaint();
	}

	public void resetJuego() {
		// Detener el timer actual
		if (movimientoTimer != null && movimientoTimer.isRunning()) {
			movimientoTimer.stop();
		}
		
		jugador.setLocation(10, 80);
		objeto1.setLocation(10, 20);
		puntos   = 0;
		vidas    = 3;
		gameOver = false;
		gameWon  = false;
		
		// Reiniciar movimiento
		reiniciarMovimiento();
		
		repaint();
	}

	public int getPuntosActuales() {
		return puntos;
	}

	public boolean isGameOver() { return gameOver; }
	public boolean isGameWon()  { return gameWon;  }
	public int getPuntos()      { return puntos;   }
	public int getVidas()       { return vidas;    }
}