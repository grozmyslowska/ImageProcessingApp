package viewer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

class MyImagePanel extends JPanel {
    BufferedImage image = null;

    private final JToolBar menuBar = new JToolBar();

    private final JMenuItem EditRGB = new JMenuItem("Edit RGB");
    private final JMenuItem R = new JMenuItem("R: ");
    private JTextField RTextField = new JTextField("0");
    private final JMenuItem G = new JMenuItem("G: ");
    private JTextField GTextField = new JTextField("0");
    private final JMenuItem B = new JMenuItem("B: ");
    private JTextField BTextField = new JTextField("0");

    private final JMenuItem CurrentRGB = new JMenuItem("Current RGB");
    private final JMenuItem RR = new JMenuItem("R: ");
    private final JMenuItem CurrentR = new JMenuItem("");
    private final JMenuItem GG = new JMenuItem("G: ");
    private  JMenuItem CurrentG = new JMenuItem("");
    private final JMenuItem BB = new JMenuItem("B: ");
    private final JMenuItem CurrentB = new JMenuItem("");

    private float scaleFactor;
    private JButton plus = new JButton("+");
    private JButton minus = new JButton("-");

    MyImagePanel(){
        super();

        this.plus.setPreferredSize(new Dimension(40,0));
        this.minus.setPreferredSize(new Dimension(40,0));

        this.menuBar.add(this.plus);
        this.menuBar.add(this.minus);

        this.menuBar.add(this.CurrentRGB);
        this.menuBar.add(this.RR);
        this.menuBar.add(this.CurrentR);
        this.menuBar.add(this.GG);
        this.menuBar.add(this.CurrentG);
        this.menuBar.add(this.BB);
        this.menuBar.add(this.CurrentB);

        this.RTextField.setPreferredSize(new Dimension(40,0));
        this.GTextField.setPreferredSize(new Dimension(40,0));
        this.BTextField.setPreferredSize(new Dimension(40,0));

        this.menuBar.add(this.EditRGB);
        this.menuBar.add(this.R);
        this.menuBar.add(this.RTextField);
        this.menuBar.add(this.G);
        this.menuBar.add(this.GTextField);
        this.menuBar.add(this.B);
        this.menuBar.add(this.BTextField);

        RTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyPressed(KeyEvent e) {
                if(RTextField.getText().startsWith("0"))RTextField.setText(RTextField.getText().substring(1));
            }
            @Override
            public void keyReleased(KeyEvent e) {
                if (!RTextField.getText().equals("")) {
                    int val;
                    try {
                        val = Integer.parseInt(RTextField.getText());
                        if(val<0 || val >255) {
                            JOptionPane.showMessageDialog(null, "Only numbers in range 0 - 255 are allowed!");
                            RTextField.setText("0");
                        }
                    } catch (Exception ee){
                        JOptionPane.showMessageDialog(null, "Only numbers in range 0 - 255 are allowed!");
                        RTextField.setText("0");
                    }
                } else RTextField.setText("0");
            }
        });
        GTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyPressed(KeyEvent e) {
                if(GTextField.getText().startsWith("0"))GTextField.setText(GTextField.getText().substring(1));
            }
            @Override
            public void keyReleased(KeyEvent e) {
                if (!GTextField.getText().equals("")) {
                    int val;
                    try {
                        val = Integer.parseInt(GTextField.getText());
                        if(val<0 || val >255) {
                            JOptionPane.showMessageDialog(null, "Only numbers in range 0 - 255 are allowed!");
                            GTextField.setText("0");
                        }
                    } catch (Exception ee){
                        JOptionPane.showMessageDialog(null, "Only numbers in range 0 - 255 are allowed!");
                        GTextField.setText("0");
                    }
                } else GTextField.setText("0");
            }
        });
        BTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyPressed(KeyEvent e) {
                if(BTextField.getText().startsWith("0"))BTextField.setText(BTextField.getText().substring(1));
            }
            @Override
            public void keyReleased(KeyEvent e) {
                if (!BTextField.getText().equals("")) {
                    int val;
                    try {
                        val = Integer.parseInt(BTextField.getText());
                        if(val<0 || val >255) {
                            JOptionPane.showMessageDialog(null, "Only numbers in range 0 - 255 are allowed!");
                            BTextField.setText("0");
                        }
                    } catch (Exception ee){
                        JOptionPane.showMessageDialog(null, "Only numbers in range 0 - 255 are allowed!");
                        BTextField.setText("0");
                    }
                } else BTextField.setText("0");
            }
        });


        this.add(this.menuBar);

        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent ev) {
                if(image!=null && ev.getX()/scaleFactor<image.getWidth() && ev.getY()/scaleFactor<image.getHeight()) {
                    int R = Integer.parseInt(RTextField.getText());
                    int G = Integer.parseInt(GTextField.getText());
                    int B = Integer.parseInt(BTextField.getText());
                    image.setRGB((int) (ev.getX()/scaleFactor), (int) (ev.getY()/scaleFactor), (new Color(R, G, B)).getRGB());
                    repaint();
                }
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent ev) {
                if(image!=null && ev.getX()/scaleFactor<image.getWidth() && ev.getY()/scaleFactor<image.getHeight()) {
                    Color c = new Color(image.getRGB((int) (ev.getX()/scaleFactor), (int) (ev.getY()/scaleFactor)));
                    CurrentR.setText(Integer.toString(c.getRed()));
                    CurrentG.setText(Integer.toString(c.getGreen()));
                    CurrentB.setText(Integer.toString(c.getBlue()));
                } else {
                    CurrentR.setText("");
                    CurrentG.setText("");
                    CurrentB.setText("");
                }
            }
        });
        this.plus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scaleFactor*=1.2;
                repaint();
            }
        });
        this.minus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scaleFactor/=1.2;
                repaint();
            }
        });


        this.scaleFactor = 1;
        this.setSize(0, 0);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(image != null){
//            g.drawImage(image, 0, 0, this);
            Graphics2D g2 = (Graphics2D) g;
            int newW = (int) (image.getWidth() * scaleFactor);
            int newH = (int) (image.getHeight() * scaleFactor);
            this.setPreferredSize(new Dimension(newW, newH));
            this.revalidate();
            g2.drawImage(image, 0, 0, newW, newH, null);
        }
    }

    public void setScaleFactor(float scaleFactor) {
        this.scaleFactor = scaleFactor;
        this.repaint();
    }

    public void setImage(BufferedImage img){
        this.image = img;
        repaint();
    }

    public BufferedImage getImage() {
        return image;
    }

}