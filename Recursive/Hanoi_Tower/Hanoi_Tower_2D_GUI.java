package Hanoi_Tower;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Hanoi_Tower_2D_GUI extends JPanel {

	static int tower[][];// the three towers' disks as stack
	static int top[];// top of the three stacks
	static int from, to;// moving 'from' tower number 'to' tower number
	static int diskInAir;// number of disk moved (1 to n)
	static int n, l, b, u;
	static Color colors[] = { Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED,
			Color.YELLOW };

	public Hanoi_Tower_2D_GUI() {
		tower = new int[3][10];
		top = new int[3];
	}

	static void push(int to, int diskno)
//putting disk on tower
	{
		tower[to - 1][++top[to - 1]] = diskno;
	}

	static int pop(int from)
//take topmost disk from tower
	{
		return (tower[from - 1][top[from - 1]--]);
	}

	Color getColor(int disknum) {
		return colors[disknum % 8];
	}

	void drawStill(Graphics g) {
		int j, i, disk;
		g.clearRect(0, 0, getWidth(), getHeight());
		for (j = 1; j <= 3; j++) {
			// draw tower
			g.setColor(Color.GRAY);
			g.fillRoundRect(j * l, u, 5, b - u, 1, 1);

			// draw all disks on tower
			for (i = 0; i <= top[j - 1]; i++) {
				disk = tower[j - 1][i];
				g.setColor(getColor(disk));
				g.fillRect(j * l - 15 - disk * 5, b - (i + 1) * 10, 35 + disk * 10, 10);
			}
		}
	}

	void drawFrame(Graphics g, int x, int y) {
		try {
			drawStill(g);
			g.setColor(getColor(diskInAir));
			g.fillRect(x - 15 - diskInAir * 5, y - 10, 35 + diskInAir * 10, 10);
			Thread.sleep(60);
		} catch (InterruptedException ex) {
		}
	}

	void animator(Graphics g)
//to show the movement of disk
	{
		int x, y, dif, sign;
		diskInAir = pop(from);
		x = from * l;
		y = b - (top[from - 1] + 1) * 10;
//taking disk upward from the tower
		for (; y > u - 20; y -= 8)
			drawFrame(g, x, y);

		y = u - 20;
		dif = to * l - x;
		sign = dif / Math.abs(dif);
//moving disk towards a target tower
		for (; Math.abs(x - to * l) >= 24; x += sign * 12)
			drawFrame(g, x, y);
		x = to * l;
//placing disk on a target tower
		for (; y < b - (top[to - 1] + 1) * 10; y += 8)
			drawFrame(g, x, y);
		push(to, diskInAir);
		drawStill(g);
	}

	void moveTopN(Graphics g, int n, int a, int b, int c) throws InterruptedException
//Move top n disk from tower 'a' to tower 'c'
//tower 'b' used for swapping
	{
		if (n >= 1) {
			moveTopN(g, n - 1, a, c, b);
			drawStill(g);
			Thread.sleep(700);
			from = a;
			to = c;
			// animating the move
			animator(g);
			moveTopN(g, n - 1, b, a, c);
		}
	}

	public static void main(String[] args) {

		int i;
		String s = JOptionPane.showInputDialog("Enter number of disks");
		n = Integer.parseInt(s);
		Hanoi_Tower_2D_GUI ha = new Hanoi_Tower_2D_GUI();
//setting all tower empty
		for (i = 0; i < 3; i++)
			top[i] = -1;

//putting all disks on tower 'a'
		for (i = n; i > 0; i--) {
			push(1, i);
		}

		JFrame fr = new JFrame();
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fr.setLayout(new BorderLayout());
		fr.setSize(640, 360);
		fr.add(ha);
		ha.setSize(fr.getSize());
		fr.setVisible(true);
		l = ha.getWidth() / 4;
		b = ha.getHeight() - 50;
		u = b - n * 12;
//start solving
		try {
			ha.moveTopN(ha.getGraphics(), n, 1, 2, 3);
		} catch (Exception ex) {
		}
	}

}
