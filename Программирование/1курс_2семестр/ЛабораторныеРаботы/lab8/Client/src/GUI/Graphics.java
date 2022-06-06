package GUI;

import Dependency.Command;
import Dependency.Worker;
import NetInteraction.ClientEvents;
import NetInteraction.ClientManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Graphics extends JPanel implements ActionListener {
    private final int startX;
    private final double startY;
    final ClientEvents clientEvents;
    final ClientManager clientManager;
    final Timer timer = new Timer(10, this);
    int rotation = 0;
    final ArrayList<Rectangle> workerRectangles;
    final Dimension size;
    final Thread updateThread;
    final String language;
    public Graphics(ClientEvents clientEvents, Dimension size,String languageIn) {
        this.clientEvents = clientEvents;
        this.clientManager = clientEvents.getClientManager();
        this.workerRectangles = new ArrayList<>();
        this.size = size;
        this.startX = (int) (size.getWidth() / 2);
        this.startY = (int) (size.getHeight() / 2);
        language = languageIn;
        updateThread = new Thread(()->{
            while(!Thread.currentThread().isInterrupted()){
                try {
                    Thread.sleep(1000);
                    clientEvents.commandMode(new Command("getCollectionForTable"));
                } catch (InterruptedException | ClassNotFoundException | IOException ignored) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        updateThread.start();
        timer.start();
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                int m_x = e.getX();
                double m_y = e.getY();
                for (Shape s : workerRectangles) {
                    if (s.contains(m_x, m_y)) {
                        UpdateWorkerForm workerForm = new UpdateWorkerForm(clientEvents, ((WorkerRectangle) s).getWorker()
                                , language);
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        setPreferredSize(size);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (rotation < 360) {
            rotation += 5;
        } else {
            rotation = 0;
        }
        repaint();
    }


    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        workerRectangles.clear();
        java.awt.Graphics2D g2 = (Graphics2D) g;
        for (Map.Entry<Integer, Worker> entry : clientManager.getCollectionForTable()
                .getWorkerList().entrySet()) {
            Worker worker = entry.getValue();
            g2.setColor(Color.decode(String.valueOf(worker.getAuthor().hashCode())));
            AffineTransform transform = new AffineTransform();
            WorkerRectangle rect = new WorkerRectangle(worker,
                    Math.abs(worker.getSalary() * worker.getCoordinates().getX())%(startX) + startX / 3,
                    Math.abs(worker.getSalary() * worker.getCoordinates().getY())%(startY) + startY / 3);
            transform.rotate(Math.toRadians(rotation), rect.getX()
                    + rect.getWidth()/ 2, rect.getY() + rect.getHeight() / 2);
            Shape transformed = transform.createTransformedShape(rect);
            workerRectangles.add(rect);
            g2.fill(transformed);
        }
        g2.setColor(Color.black);
        g2.drawLine(0,(int)Math.round(startY),size.width,(int)Math.round(startY));
        g2.drawLine(startX,size.height,startX,0);
        int count =0;
        for(int i=0;i<size.getHeight()/50;i++){
            g2.drawLine(startX+5,Math.toIntExact(Math.round(size.getHeight()-count)),startX-5
                    , Math.toIntExact(Math.round(size.getHeight() - count)));
            count+=50;
        }
        count=0;
        for(int i=0;i<size.getWidth()/50;i++){
            g2.drawLine(count, (int) startY-5
                    ,count, (int) startY+5);
            count+=50;
        }
    }

    public Thread getUpdateThread() {
        return updateThread;
    }
}
