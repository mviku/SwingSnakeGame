package com.github.mviku.snakegame;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

@SuppressWarnings("serial")
public class Snake extends JPanel implements KeyListener, Runnable
{
	int w,h,c=10,id,i=3,prevId;
	int sleepTime = 100;
	Point tempPoint,fruit,lastPoint;
	JFrame fr=new JFrame();
	java.util.List<Point> points=new ArrayList<Point>();
	ImageIcon pic;
	
	@SuppressWarnings("static-access")
	public void run()
	{
		for(i=0;i<4;i++)
			points.add(new Point(200+c*i,200));
		for(i=0;i<points.size();i++)
        {
        	//System.out.println(points.get(i).x+" "+ points.get(i).y);
        }
		//System.out.println(points.size());
		w=(500/c)*c;
		h=(500/c)*c;
		id=37;
		setBackground(Color.gray);
		JLabel lab=new JLabel("Play Snake Game");
		lab.setBounds(150, 25 , 300, 75);
		this.add(lab);
		pic=new ImageIcon("C:\\Users\\393809\\Pictures\\snake.jpg");
		fr.setTitle("Snake Xneria");
		fr.setVisible(true);
		fr.setResizable(false);
        fr.setSize(600, 600);
        fr.setLocationRelativeTo(null);
        fr.add(this);
		fr.addKeyListener(this);
		fr.setDefaultCloseOperation(fr.EXIT_ON_CLOSE);
		putFruit();
		repaint();
		this.fill();
		JOptionPane.showMessageDialog(null, "Game Over");
	}

	private void fill()
	{
		while(true)
		{
			ArrayList<Point> temp=new ArrayList<Point>();
			tempPoint=new Point();
			lastPoint=new Point();
			boolean crct=true;
			switch(id)
			{
			case 37:
				tempPoint.x=points.get(0).x-c;
				if(tempPoint.x<100)
					tempPoint.x=w-c;
				tempPoint.y=points.get(0).y;
				break;
			case 38:
				tempPoint.y=points.get(0).y-c;
				if(tempPoint.y<100)
					tempPoint.y=h-c;
				tempPoint.x=points.get(0).x;
				break;
			case 39:
				tempPoint.x=points.get(0).x+c;
				if(tempPoint.x>=w)
					tempPoint.x=100;
				tempPoint.y=points.get(0).y;
				break;
			case 40:
				tempPoint.y=points.get(0).y+c;
				if(tempPoint.y>=h)
					tempPoint.y=100;
				tempPoint.x=points.get(0).x;
				break;
			default:
				crct=false;
				id=prevId;
			}
			if(crct)
			{
				if(points.contains(tempPoint))
				{
					break;
				}
				else
				{
					try
					{
						temp.add(tempPoint);
						for(i=0;i<points.size()-1;i++)
							temp.add(points.get(i));
						lastPoint=points.get(i);
						points=null;
						points=new ArrayList<Point>();
						points.addAll(temp);
						temp=null;;
					}
					catch(IndexOutOfBoundsException ae)
					{
						
					}
				}
			}
			if(points.contains(fruit))
			{
				putFruit();
				points.add(lastPoint);
			}
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			repaint();
		}
	}

	private void putFruit()
	{
		int x,y;
		x=(((int) (Math.random()*12345648)%400)/c)*c+100;
		y=(((int) (Math.random()*12345648)%400)/c)*c+100;
		fruit=new Point(x,y);
		//System.out.println("("+fruit.x+","+fruit.y+")");
		repaint();
	}
	
	@Override
	public void keyPressed(KeyEvent ke) 
	{
		prevId=id;
		id=ke.getKeyCode();
		if(Math.abs(prevId-id)==2)
			id=prevId;
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}
	
	private void doDrawing(Graphics g) 
	{    
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(pic.getImage(), 500, 0, 90, 90, null);
        g2d.setColor(Color.white);
        g2d.fillRect(100, 100, 400, 400);
        g2d.setColor(Color.blue);
        g2d.fillOval(fruit.x, fruit.y , c , c);
        g2d.setColor(Color.red);
        g2d.fillRect(points.get(0).x, points.get(0).y, c, c);
        g2d.setColor(Color.black);
        for(i=1;i<points.size();i++)
        {
        	g2d.fillRect(points.get(i).x, points.get(i).y, c, c);
        	//System.out.println(points.get(i).x+" "+ points.get(i).y);
        }
        g2d.drawString((points.size()-4)+"", 50, 50);
    }

    @Override
    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        doDrawing(g);
    }
    
	public static void main(String[] args) throws InterruptedException
	{
		new Thread(new Snake()).start();;
	}
}
