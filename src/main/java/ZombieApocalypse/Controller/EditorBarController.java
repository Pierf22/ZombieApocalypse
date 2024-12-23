package ZombieApocalypse.Controller;

import ZombieApocalypse.Model.Editor.EditorBarModel;
import ZombieApocalypse.Utility.GameData;
import ZombieApocalypse.Utility.PlayWav;
import ZombieApocalypse.Utility.ResourcesLoader;
import ZombieApocalypse.View.Editor.EditorBarView;
import ZombieApocalypse.View.Editor.EditorView;
import ZombieApocalypse.View.GameFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

public class EditorBarController {
    private final EditorBarView view;
    private EditorView editorView;
    private EditorBarModel model;

    public EditorBarController(EditorBarView view, EditorView editorView, EditorBarModel model) {
        this.editorView = editorView;
        this.view = view;
        this.model = model;
        view.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                view.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(ResourcesLoader.getInstance().getBufferedImage("/GameGeneral/crosshair.png", 32, 32, false), new Point(20, 20), "Cursor"));
            }
        });
    }

    public void addListener(){
        //evento per passare a schermata 2
        view.getArrow1().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if(GameData.sound)
                    PlayWav.getInstance().playButtonSound();
                view.removeAll();
                view.Page2();
                view.revalidate();
                view.repaint();
            }
        });
        //evento per passare a schermata1
        view.getArrow2().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if(GameData.sound)
                    PlayWav.getInstance().playButtonSound();
                view.removeAll();
                view.Page1();
                view.revalidate();
                view.repaint();
            }
        });
        //Quando il campo nome prende il focus caccio il placeholder, quando perde il focus lo rimetto se il campo è vuoto
        view.getTxtName().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(view.getTxtName().getText().equals("Insert map name:") || view.getTxtName().getText().equals("Nome mappa:") ){
                    view.getTxtName().setText("");
                    view.getTxtName().setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(view.getTxtName().getText().equals(""))
                    if(GameData.lang.equals(GameData.Language.EN)){
                        view.getTxtName().setText("Insert map name:");
                        view.getTxtName().setForeground(new Color(156, 156, 156));
                    }
                    else if(GameData.lang.equals(GameData.Language.IT)){
                        view.getTxtName().setText("Nome mappa:");
                        view.getTxtName().setForeground(new Color(156, 156, 156));
                    }
            }
        });
        //evento per mettere e cacciare le linee guida
        view.getLine().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if(GameData.sound)
                    PlayWav.getInstance().playButtonSound();
                if(editorView.line){
                    editorView.line = false;
                    editorView.repaint();
                }
                else {
                    editorView.line = true;
                    editorView.repaint();
                }
            }
        });
        //evento per uscire e tornare al menu
        view.getExit().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if(GameData.sound)
                    PlayWav.getInstance().playButtonSound();
                showDialog();
            }
        });
        //evento per resettare la mappa a quella di default
        view.getReset().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if(GameData.sound)
                    PlayWav.getInstance().playButtonSound();
                editorView.line = true;
                editorView.initWorld();
                editorView.repaint();
            }
        });
        //evento per salvare la mappa
        view.getSave().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if(GameData.sound)
                    PlayWav.getInstance().playButtonSound();
                if(!view.getTxtName().getText().equals("Insert map name:") && !view.getTxtName().getText().equals("Nome mappa:") && !view.getTxtName().getText().equals(""))
                    model.saveMap(view.getTxtName().getText());
                else{
                    if(GameData.lang.equals(GameData.Language.IT)){
                        view.getTxtName().setText("Nome mappa:");
                        view.getTxtName().setForeground(Color.red);
                    }
                    else{
                        view.getTxtName().setText("Insert map name:");
                        view.getTxtName().setForeground(Color.red);
                    }
                }
            }
        });
        //evento per settare il bordo rosso al pulsante che rappresenta il tassello della terra.
        //Faccio la stessa cosa con tutti gli altri tasselli
        view.getDirt0().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                model.clearBorder();
                EditorBarView.bloccoAttivo = 0;
                view.getDirt0().setBorder(new LineBorder(Color.red));
            }
        });

        view.getDirt1().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                model.clearBorder();
                EditorBarView.bloccoAttivo = 1;
                view.getDirt1().setBorder(new LineBorder(Color.red));
            }
        });

        view.getDirt2().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                model.clearBorder();
                EditorBarView.bloccoAttivo = 2;
                view.getDirt2().setBorder(new LineBorder(Color.red));
            }
        });

        view.getDirt3().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                model.clearBorder();
                EditorBarView.bloccoAttivo = 3;
                view.getDirt3().setBorder(new LineBorder(Color.red));
            }
        });

        view.getDivisorio().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                model.clearBorder();
                EditorBarView.bloccoAttivo = 4;
                view.getDivisorio().setBorder(new LineBorder(Color.red));
            }
        });

        view.getWater0().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                model.clearBorder();
                EditorBarView.bloccoAttivo = 5;
                view.getWater0().setBorder(new LineBorder(Color.red));
            }
        });

        view.getFlower1().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                model.clearBorder();
                EditorBarView.bloccoAttivo = 6;
                view.getFlower1().setBorder(new LineBorder(Color.red));
            }
        });

        view.getFlower2().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                model.clearBorder();
                EditorBarView.bloccoAttivo = 7;
                view.getFlower2().setBorder(new LineBorder(Color.red));
            }
        });

        view.getRoad1().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                model.clearBorder();
                EditorBarView.bloccoAttivo = 8;
                view.getRoad1().setBorder(new LineBorder(Color.red));
            }
        });

        view.getRoad2().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                model.clearBorder();
                EditorBarView.bloccoAttivo = 9;
                view.getRoad2().setBorder(new LineBorder(Color.red));
            }
        });

        view.getRoad3().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                model.clearBorder();
                EditorBarView.bloccoAttivo = 10;
                view.getRoad3().setBorder(new LineBorder(Color.red));
            }
        });

        view.getRoad4().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                model.clearBorder();
                EditorBarView.bloccoAttivo = 11;
                view.getRoad4().setBorder(new LineBorder(Color.red));
            }
        });

        view.getRoad5().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                model.clearBorder();
                EditorBarView.bloccoAttivo = 12;
                view.getRoad5().setBorder(new LineBorder(Color.red));
            }
        });

        view.getRoad6().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                model.clearBorder();
                EditorBarView.bloccoAttivo = 13;
                view.getRoad6().setBorder(new LineBorder(Color.red));
            }
        });

        view.getRoad7().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                model.clearBorder();
                EditorBarView.bloccoAttivo = 14;
                view.getRoad7().setBorder(new LineBorder(Color.red));
            }
        });

        view.getRoad8().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                model.clearBorder();
                EditorBarView.bloccoAttivo = 15;
                view.getRoad8().setBorder(new LineBorder(Color.red));
            }
        });

        view.getRoad9().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                model.clearBorder();
                EditorBarView.bloccoAttivo = 16;
                view.getRoad9().setBorder(new LineBorder(Color.red));
            }
        });

        view.getRoad10().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                model.clearBorder();
                EditorBarView.bloccoAttivo = 17;
                view.getRoad10().setBorder(new LineBorder(Color.red));
            }
        });

        view.getRoad11().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                model.clearBorder();
                EditorBarView.bloccoAttivo = 18;
                view.getRoad11().setBorder(new LineBorder(Color.red));
            }
        });

        view.getRoad12().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                model.clearBorder();
                EditorBarView.bloccoAttivo = 19;
                view.getRoad12().setBorder(new LineBorder(Color.red));
            }
        });

        view.getRoad13().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                model.clearBorder();
                EditorBarView.bloccoAttivo = 20;
                view.getRoad13().setBorder(new LineBorder(Color.red));
            }
        });

        view.getRoad14().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                model.clearBorder();
                EditorBarView.bloccoAttivo = 21;
                view.getRoad14().setBorder(new LineBorder(Color.red));
            }
        });

        view.getRoad15().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                model.clearBorder();
                EditorBarView.bloccoAttivo = 22;
                view.getRoad15().setBorder(new LineBorder(Color.red));
            }
        });

        view.getRoad16().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                model.clearBorder();
                EditorBarView.bloccoAttivo = 23;
                view.getRoad16().setBorder(new LineBorder(Color.red));
            }
        });

        view.getRoad17().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                model.clearBorder();
                EditorBarView.bloccoAttivo = 24;
                view.getRoad17().setBorder(new LineBorder(Color.red));
            }
        });

        view.getRoad18().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                model.clearBorder();
                EditorBarView.bloccoAttivo = 25;
                view.getRoad18().setBorder(new LineBorder(Color.red));
            }
        });

        view.getRoad19().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                model.clearBorder();
                EditorBarView.bloccoAttivo = 26;
                view.getRoad19().setBorder(new LineBorder(Color.red));
            }
        });

        view.getRoad20().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                model.clearBorder();
                EditorBarView.bloccoAttivo = 27;
                view.getRoad20().setBorder(new LineBorder(Color.red));
            }
        });

        view.getRoad21().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                model.clearBorder();
                EditorBarView.bloccoAttivo = 28;
                view.getRoad21().setBorder(new LineBorder(Color.red));
            }
        });

        view.getRoad22().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                model.clearBorder();
                EditorBarView.bloccoAttivo = 29;
                view.getRoad22().setBorder(new LineBorder(Color.red));
            }
        });

        view.getRoad23().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                model.clearBorder();
                EditorBarView.bloccoAttivo = 30;
                view.getRoad23().setBorder(new LineBorder(Color.red));
            }
        });

        view.getRoad24().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                model.clearBorder();
                EditorBarView.bloccoAttivo = 31;
                view.getRoad24().setBorder(new LineBorder(Color.red));
            }
        });

        view.getRoad25().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                model.clearBorder();
                EditorBarView.bloccoAttivo = 32;
                view.getRoad25().setBorder(new LineBorder(Color.red));
            }
        });

        view.getTxtName().addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if(view.getTxtName().getText().length() > 11)
                    e.consume();
            }
        });
    }

    private void showDialog() {
        //dialog che serve per comunicare all'utente che uscendo tutti i dati salavti verranno eliminati

        Font font = ResourcesLoader.getInstance().getFont("/Font/PixelFont.otf", 20, Font.PLAIN);
        GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
        UIManager.put("OptionPane.background",new Color(92,75,35));
        UIManager.put("Panel.background",new Color(18,17,15));
        UIManager.put("OptionPane.minimumSize",new Dimension(500,200));
        UIManager.put("OptionPane.border", new EmptyBorder(10, 10, 10,10));
        UIManager.put("OptionPane.font", ResourcesLoader.getInstance().getFont("/Font/PixelFont.otf", 20, Font.PLAIN));
        UIManager.put("OptionPane.foreground", Color.WHITE);

        //creo la label e gli setto il font personalizzato
        JLabel label = new JLabel();
        label.setFont(font.deriveFont(Font.PLAIN, 18));
        label.setForeground(Color.WHITE);
        label.setMinimumSize(new Dimension(100, 100));
        label.setPreferredSize(new Dimension(100, 100));
        label.setBorder(new EmptyBorder(10, 10, 0, 0));

        JButton btnMenu;
        JButton btnGo;
        if(GameData.lang.equals(GameData.Language.IT)) {
            btnMenu = new JButton("Esci");
            btnGo = new JButton("Annulla");
            label.setText("<html>Uscendo tutti i dati non salvati verranno eliminati. Non potrai, quindi, recuperare eventuali mappe non salvate.<br>Sei sicuro di voler uscire?</html>");
        }
        else {
            btnMenu = new JButton("Exit");
            btnGo = new JButton("Cancel");
            label.setText("<html>Leaving unsaved data will delete it. You will not be able to recover any unsaved maps.<br>Are you sure you want to quit?</html>");
        }

        btnMenu.setIcon(ResourcesLoader.getInstance().getImageIcon("/Login&Menu/sendButton.png", 230, 60, false));
        btnMenu.setHorizontalTextPosition(JButton.CENTER);
        btnMenu.setVerticalTextPosition(JButton.CENTER);
        btnMenu.setBorderPainted(false);
        btnMenu.setFocusPainted(false);
        btnMenu.setContentAreaFilled(false);
        btnMenu.setForeground(Color.WHITE);
        btnMenu.setFont(ResourcesLoader.getInstance().getFont("/Font/PixelFont.otf", 25, Font.PLAIN));

        btnGo.setIcon(ResourcesLoader.getInstance().getImageIcon("/Login&Menu/sendButton.png", 230, 60, false));
        btnGo.setHorizontalTextPosition(JButton.CENTER);
        btnGo.setVerticalTextPosition(JButton.CENTER);
        btnGo.setBorderPainted(false);
        btnGo.setFocusPainted(false);
        btnGo.setContentAreaFilled(false);
        btnGo.setForeground(Color.WHITE);
        btnGo.setFont(ResourcesLoader.getInstance().getFont("/Font/PixelFont.otf", 25, Font.PLAIN));

        //creo il joptionpane e gli assegno la label, poi creo il dialog e lo mostro
        JOptionPane pane = new JOptionPane(label,  JOptionPane.PLAIN_MESSAGE,  JOptionPane.DEFAULT_OPTION,null,  new JButton[] {btnMenu, btnGo});
        JDialog dialog = new JDialog();
        btnGo.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if(GameData.sound)
                    PlayWav.getInstance().playButtonSound();
                dialog.dispose();
            }
        });
        btnMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if(GameData.sound)
                    PlayWav.getInstance().playButtonSound();
                dialog.dispose();
                EditorBarView.bloccoAttivo = -1;
                GameFrame.menuLaunch();
            }
        });

        dialog.getContentPane().add(pane);
        dialog.setUndecorated(true);
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        dialog.setAlwaysOnTop(true);
        dialog.setModal(true);
        dialog.setSize(new Dimension(515, 220));
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

}
