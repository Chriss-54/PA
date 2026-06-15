package controller;

import view.view_lienzo;

public class subprocessMoveObject extends Thread {

	private view_lienzo vl;
	private boolean flag = true;
	private boolean paused = false;

	public subprocessMoveObject(view_lienzo vl_) {
		this.vl = vl_;
	}

	private int getRandomX() {
		return (int)(Math.random() * (vl.pn_lienzo.getWidth() - 50));
	}

	private int getRandomY() {
		return (int)(Math.random() * (vl.pn_lienzo.getHeight() - 50));
	}

	@Override
	public void run() {
		while (flag) {
			try {
				sleep(5000);
				
				synchronized (this) {
					while (paused) {
						wait();
					}
				}
				
				if (!vl.pn_lienzo.isGameOver() && !vl.pn_lienzo.isGameWon()) {
					vl.pn_lienzo.setObjeto1(getRandomX(), getRandomY());
				}
				
			} catch (InterruptedException e) {
				break;
			}
		}
	}

	public synchronized void pausar() {
		this.paused = true;
	}

	public synchronized void reanudar() {
		this.paused = false;
		notify();
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}