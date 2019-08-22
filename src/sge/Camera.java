package sge;
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;


public class Camera implements KeyListener, MouseMotionListener, MouseListener, MouseWheelListener{
	public double xPos, yPos, xDir, yDir, xPlane, yPlane;
	public boolean left, right, forward, back, close;
	public final double MOVE_SPEED = .08;
	public final double ROTATION_SPEED = .045;
	public Camera(double x, double y, double xd, double yd, double xp, double yp) {
		xPos = x;
		yPos = y;
		xDir = xd;
		yDir = yd;
		xPlane = xp;
		yPlane = yp;
	}
	public void keyPressed(KeyEvent key) {
		if((key.getKeyCode() == KeyEvent.VK_A))
			left = true;
		if((key.getKeyCode() == KeyEvent.VK_D))
			right = true;
		if((key.getKeyCode() == KeyEvent.VK_W))
			forward = true;
		if((key.getKeyCode() == KeyEvent.VK_S))
			back = true;
		if((key.getKeyCode() == KeyEvent.VK_ESCAPE))
			close = true;	
	}

	
	public void keyReleased(KeyEvent key) {
		if((key.getKeyCode() == KeyEvent.VK_A))
			left = false;
		if((key.getKeyCode() == KeyEvent.VK_D))
			right = false;
		if((key.getKeyCode() == KeyEvent.VK_W))
			forward = false;
		if((key.getKeyCode() == KeyEvent.VK_S))
			back = false;
	}
	public void update(int[][] map) {
		if(forward) {
			if(map[(int)(xPos + xDir * MOVE_SPEED)][(int)yPos] == 0) {
				xPos+=xDir*MOVE_SPEED;
			}
			if(map[(int)xPos][(int)(yPos + yDir * MOVE_SPEED)] ==0)
				yPos+=yDir*MOVE_SPEED;
		}
		if(back) {
			if(map[(int)(xPos - xDir * MOVE_SPEED)][(int)yPos] == 0)
				xPos-=xDir*MOVE_SPEED;
			if(map[(int)xPos][(int)(yPos - yDir * MOVE_SPEED)]==0)
				yPos-=yDir*MOVE_SPEED;
		}
		if(right) {
			double oldxDir=xDir;
			xDir=xDir*Math.cos(-ROTATION_SPEED) - yDir*Math.sin(-ROTATION_SPEED);
			yDir=oldxDir*Math.sin(-ROTATION_SPEED) + yDir*Math.cos(-ROTATION_SPEED);
			double oldxPlane = xPlane;
			xPlane=xPlane*Math.cos(-ROTATION_SPEED) - yPlane*Math.sin(-ROTATION_SPEED);
			yPlane=oldxPlane*Math.sin(-ROTATION_SPEED) + yPlane*Math.cos(-ROTATION_SPEED);
		}
		if(left) {
			double oldxDir=xDir;
			xDir=xDir*Math.cos(ROTATION_SPEED) - yDir*Math.sin(ROTATION_SPEED);
			yDir=oldxDir*Math.sin(ROTATION_SPEED) + yDir*Math.cos(ROTATION_SPEED);
			double oldxPlane = xPlane;
			xPlane=xPlane*Math.cos(ROTATION_SPEED) - yPlane*Math.sin(ROTATION_SPEED);
			yPlane=oldxPlane*Math.sin(ROTATION_SPEED) + yPlane*Math.cos(ROTATION_SPEED);
		}
		if(close) {
			Game.close();
		}
	}
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
	
	public void mouseMoved(MouseEvent mouse){
		System.out.println(mouse.getX() +", "+ mouse.getY());
	}
	@Override
	public void mouseDragged(MouseEvent e) { }
	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) { }
	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("pew");
	}
	@Override
	public void mouseEntered(MouseEvent e) { }
	@Override
	public void mouseExited(MouseEvent e) { }
	@Override
	public void mousePressed(MouseEvent e) { 
		System.out.println("pew");
	}
	@Override
	public void mouseReleased(MouseEvent e) { }
}