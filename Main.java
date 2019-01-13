import javax.swing.JFrame;

public class Main {
	public static final int Length = 700, Width = 600;
	public static void main(String[] args) {
		JFrame frame = new JFrame("Break Breaker");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Game game = new Game();
		frame.setBounds(10, 10, Length, Width);
		frame.setResizable(false);
		frame.add(game);
		frame.setVisible(true);
		
	}
}
