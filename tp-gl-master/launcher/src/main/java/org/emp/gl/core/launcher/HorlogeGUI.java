package org.emp.gl.core.launcher;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.beans.PropertyChangeEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.emp.gl.timer.service.TimerChangeListener;
import org.emp.gl.timer.service.TimerService;

public class HorlogeGUI extends JFrame implements TimerChangeListener {
    private TimerService timerService;
    private JLabel labelHeure;
    private JLabel labelSecondes;
    private JLabel labelDate;
    private JLabel labelAmPm;
    private RoundedPanel mainPanel;
    
    // Palette de couleurs bleu et blanc
    private final Color BACKGROUND_COLOR = new Color(240, 245, 255); // Bleu très clair
    private final Color PRIMARY_BLUE = new Color(70, 130, 255); // Bleu vif
    private final Color DARK_BLUE = new Color(30, 80, 180); // Bleu foncé
    private final Color ACCENT_BLUE = new Color(100, 160, 255); // Bleu medium
    private final Color TEXT_COLOR = new Color(50, 50, 80); // Texte foncé
    private final Color WHITE = Color.WHITE;
    
    public HorlogeGUI(TimerService timerService) {
        this.timerService = timerService;
        this.timerService.addTimeChangeListener(this);
        
        initializeUI();
    }
    
    private void initializeUI() {
        setTitle("Horloge Moderne");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setUndecorated(true); // Pour les bords arrondis
        setBackground(new Color(0, 0, 0, 0)); // Fond transparent
        
        // Panel principal arrondi
        mainPanel = new RoundedPanel(35);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(35, 35, 35, 35));
        
        // Configuration de l'affichage de l'heure
        setupTimeDisplay();
        
        add(mainPanel);
        
        pack();
        setSize(450, 250);
        setLocationRelativeTo(null);
        
        // Appliquer la forme arrondie à la fenêtre
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 50, 50));
        setVisible(true);
        
        // Ajouter la possibilité de déplacer la fenêtre
        addMouseListeners();
        
        // Ombre portée
        setShadowEffect();
    }
    
    private void setupTimeDisplay() {
        JPanel timePanel = new JPanel(new BorderLayout());
        timePanel.setOpaque(false);
        
        // Heure principale (heures et minutes)
        labelHeure = new JLabel("", SwingConstants.CENTER);
        labelHeure.setFont(new Font("Segoe UI", Font.BOLD, 58));
        labelHeure.setForeground(DARK_BLUE);
        
        // Panel pour secondes et AM/PM
        JPanel bottomTimePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        bottomTimePanel.setOpaque(false);
        
        // Secondes
        labelSecondes = new JLabel("", SwingConstants.CENTER);
        labelSecondes.setFont(new Font("Segoe UI", Font.BOLD, 22));
        labelSecondes.setForeground(PRIMARY_BLUE);
        
        // AM/PM
        labelAmPm = new JLabel("", SwingConstants.CENTER);
        labelAmPm.setFont(new Font("Segoe UI", Font.BOLD, 16));
        labelAmPm.setForeground(ACCENT_BLUE);
        
        bottomTimePanel.add(labelSecondes);
        bottomTimePanel.add(createSeparator());
        bottomTimePanel.add(labelAmPm);
        
        // Date
        labelDate = new JLabel("", SwingConstants.CENTER);
        labelDate.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        labelDate.setForeground(TEXT_COLOR);
        labelDate.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        
        timePanel.add(labelHeure, BorderLayout.CENTER);
        timePanel.add(bottomTimePanel, BorderLayout.SOUTH);
        
        mainPanel.add(timePanel, BorderLayout.CENTER);
        mainPanel.add(labelDate, BorderLayout.SOUTH);
        
        updateAffichage();
    }
    
    private JLabel createSeparator() {
        JLabel separator = new JLabel("•");
        separator.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        separator.setForeground(new Color(200, 210, 230));
        return separator;
    }
    
    private void updateAffichage() {
        int heures = timerService.getHeures();
        int minutes = timerService.getMinutes();
        int secondes = timerService.getSecondes();
        
        // Format 12h ou 24h
        String amPm = "";
        int heuresAffichage = heures;
        
        if (heures >= 12) {
            amPm = "PM";
            if (heures > 12) heuresAffichage = heures - 12;
        } else {
            amPm = "AM";
            if (heures == 0) heuresAffichage = 12;
        }
        
        // Heure principale (heures:minutes)
        String heureText = String.format("%02d : %02d", heuresAffichage, minutes);
        labelHeure.setText(heureText);
        
        // Secondes
        labelSecondes.setText(String.format("%02d", secondes));
        
        // AM/PM
        labelAmPm.setText(amPm);
        
        // Date actuelle
        updateDate();
    }
    
    private void updateDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy");
        String dateFormatted = now.format(formatter);
        
        // Mettre en majuscule la première lettre
        dateFormatted = dateFormatted.substring(0, 1).toUpperCase() + dateFormatted.substring(1);
        labelDate.setText(dateFormatted);
    }
    
    private void addMouseListeners() {
        final Point[] dragOffset = new Point[1];
        
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent e) {
                dragOffset[0] = new Point(e.getX(), e.getY());
            }
            
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    dispose();
                }
            }
        });
        
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent e) {
                Point currentLocation = getLocation();
                setLocation(currentLocation.x + e.getX() - dragOffset[0].x,
                           currentLocation.y + e.getY() - dragOffset[0].y);
            }
        });
    }
    
    private void setShadowEffect() {
        // Créer un effet d'ombre avec un panel parent
        JPanel shadowPanel = new JPanel();
        shadowPanel.setBackground(Color.WHITE);
        shadowPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SwingUtilities.invokeLater(() -> updateAffichage());
    }
    
    // Panel avec coins arrondis et effet de brillance
    class RoundedPanel extends JPanel {
        private int cornerRadius;
        
        public RoundedPanel(int radius) {
            cornerRadius = radius;
            setOpaque(false);
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D graphics = (Graphics2D) g;
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            int width = getWidth();
            int height = getHeight();
            
            // Fond principal avec dégradé bleu clair
            GradientPaint backgroundGradient = new GradientPaint(
                0, 0, new Color(245, 248, 255),
                width, height, new Color(235, 242, 255)
            );
            graphics.setPaint(backgroundGradient);
            graphics.fillRoundRect(0, 0, width, height, cornerRadius, cornerRadius);
            
            // Bordure bleue subtile
            graphics.setColor(new Color(PRIMARY_BLUE.getRed(), PRIMARY_BLUE.getGreen(), PRIMARY_BLUE.getBlue(), 60));
            graphics.setStroke(new BasicStroke(2.5f));
            graphics.drawRoundRect(1, 1, width-2, height-2, cornerRadius, cornerRadius);
            
            // Effet de brillance en haut
            GradientPaint shineGradient = new GradientPaint(
                0, 0, new Color(255, 255, 255, 80),
                0, height/3, new Color(255, 255, 255, 0)
            );
            graphics.setPaint(shineGradient);
            graphics.fillRoundRect(2, 2, width-4, height/3, cornerRadius-2, cornerRadius-2);
        }
    }
    
    // Méthode pour tester indépendamment
    public static void main(String[] args) {
        // Pour tester sans le TimerService réel
        SwingUtilities.invokeLater(() -> {
            TimerService testService = new TimerService() {
                public int getHeures() { return LocalDateTime.now().getHour(); }
                public int getMinutes() { return LocalDateTime.now().getMinute(); }
                public int getSecondes() { return LocalDateTime.now().getSecond(); }
                public int getDixiemeDeSeconde() { return 0; }
                public void addTimeChangeListener(TimerChangeListener pl) {}
                public void removeTimeChangeListener(TimerChangeListener pl) {}
            };
            
            new HorlogeGUI(testService);
        });
    }
}