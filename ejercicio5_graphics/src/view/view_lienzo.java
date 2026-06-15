package view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.UIManager;

import controller.logic_view_lienzo;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;

public class view_lienzo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public JButton btn_resume;
	public JButton btn_start;
	public JButton btn_pause;
	public JButton btn_reset;
	public JLabel  lbl_puntos;
	public lienzo  pn_lienzo;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// Fuerza a que la interfaz use los botones y estilos reales del sistema operativo
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					
					view_lienzo frame = new view_lienzo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public view_lienzo() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 429, 390);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel pn_control = new JPanel();
		pn_control.setBackground(Color.WHITE);
		pn_control.setBounds(10, 5, 400, 35);
		contentPane.add(pn_control);

		btn_pause = new JButton("PAUSE");
		btn_pause.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		btn_pause.setToolTipText("Pausar");
		btn_pause.setEnabled(false);
		pn_control.add(btn_pause);

		btn_start = new JButton("START");
		btn_start.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		btn_start.setToolTipText("Iniciar");
		pn_control.add(btn_start);

		btn_resume = new JButton("RESUME");
		btn_resume.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		btn_resume.setToolTipText("Continuar");
		btn_resume.setEnabled(false);
		pn_control.add(btn_resume);

		btn_reset = new JButton("RESET");
		btn_reset.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		btn_reset.setToolTipText("Reiniciar");
		btn_reset.setEnabled(false);
		pn_control.add(btn_reset);

		lbl_puntos = new JLabel("Puntos: 0   Vidas: 3");
		lbl_puntos.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lbl_puntos.setBounds(10, 45, 400, 20);
		contentPane.add(lbl_puntos);

		pn_lienzo = new lienzo();
		pn_lienzo.setBounds(10, 70, 400, 275);
		contentPane.add(pn_lienzo);

		new logic_view_lienzo(this);
	}
}