package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import view.view_lienzo;

public class logic_view_lienzo implements ActionListener, KeyListener {

	private view_lienzo vl;
	private subprocessMoveObject hilo1;

	public logic_view_lienzo(view_lienzo vl_) {
		this.vl = vl_;

		this.vl.btn_start.addActionListener(this);
		this.vl.btn_pause.addActionListener(this);
		this.vl.btn_resume.addActionListener(this);
		this.vl.btn_reset.addActionListener(this);

		this.vl.btn_start.addKeyListener(this);
		this.vl.btn_pause.addKeyListener(this);
		this.vl.btn_resume.addKeyListener(this);
		this.vl.btn_reset.addKeyListener(this);
		this.vl.pn_lienzo.addKeyListener(this);
		this.vl.pn_lienzo.setFocusable(true);

		this.vl.addKeyListener(this);
		this.vl.setFocusable(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == vl.btn_start) {
			hilo1 = new subprocessMoveObject(vl);
			hilo1.start();
			vl.btn_start.setEnabled(false);
			vl.btn_pause.setEnabled(true);
			vl.btn_resume.setEnabled(false);
			vl.btn_reset.setEnabled(true);
			vl.requestFocusInWindow();
		} else if (e.getSource() == vl.btn_pause) {
			if (hilo1 != null) {
				hilo1.pausar();
			}
			vl.btn_pause.setEnabled(false);
			vl.btn_resume.setEnabled(true);
			vl.requestFocusInWindow();
		} else if (e.getSource() == vl.btn_resume) {
			if (hilo1 != null) {
				hilo1.reanudar();
			}
			vl.btn_pause.setEnabled(true);
			vl.btn_resume.setEnabled(false);
			vl.requestFocusInWindow();
		} else if (e.getSource() == vl.btn_reset) {
			if (hilo1 != null) {
				hilo1.setFlag(false);
				hilo1.interrupt();
			}
			vl.pn_lienzo.resetJuego();
			vl.lbl_puntos.setText("Puntos: 0   Vidas: 3");
			vl.btn_start.setEnabled(true);
			vl.btn_pause.setEnabled(false);
			vl.btn_resume.setEnabled(false);
			vl.btn_reset.setEnabled(false);
			vl.requestFocusInWindow();
		}
	}

	public void actualizarLabel() {
		vl.lbl_puntos.setText(
			"Puntos: " + vl.pn_lienzo.getPuntos() +
			"   Vidas: " + vl.pn_lienzo.getVidas()
		);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			vl.pn_lienzo.setJugadorY(-5);
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			vl.pn_lienzo.setJugadorY(5);
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			vl.pn_lienzo.setJugadorX(-5);
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			vl.pn_lienzo.setJugadorX(5);
		}
		actualizarLabel();
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}