import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
public class RCCircuitSimulator extends JFrame {
    private SimulationTree parameterTree;
    private VoltageLinkedList voltageHistory;
    double time;
    private Timer timer;

    public RCCircuitSimulator() {
        // Ask user for capacitance, resistance, and voltage

        double capacitance = Double.parseDouble(JOptionPane.showInputDialog("Enter capacitance (in uF Default 100):"));
        double resistance = Double.parseDouble(JOptionPane.showInputDialog("Enter resistance (in ohms Default 1000):"));
        double initialVoltage = Double.parseDouble(JOptionPane.showInputDialog("Enter initial voltage (in V Default 5):"));


        parameterTree = new SimulationTree();
        voltageHistory = new VoltageLinkedList();
        initializeParameters(capacitance, resistance, initialVoltage);

        setTitle("RC Circuit Simulator");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new CircuitPanel());

        timer = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                time += 0.2;
                repaint();
            }
        });
    }

    private void initializeParameters(double c, double r, double v) {
        parameterTree.add(new SimulationParameter(r, c/100000, v));

        // Add more parameters if needed
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RCCircuitSimulator simulator = new RCCircuitSimulator();
            simulator.setVisible(true);
            simulator.startSimulation();
        });
    }

    private void startSimulation() {
        SimulationParameter parameter = parameterTree.getFirst();
        if (parameter != null) {
            time = 0;
            voltageHistory.clear();
            timer.start();
        }
    }

    class CircuitPanel extends JPanel {
        private static final int PANEL_WIDTH = 600;
        private static final int PANEL_HEIGHT = 400;
        private final double dt = 0.2; // time step
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawCircuit(g);
            drawVoltage(g);
        }

        private void drawCircuit(Graphics g) {
            g.setColor(Color.BLACK);
            

        // Draw resistor as zigzag line
        int[] xPoints = {200, 210, 230, 250, 270, 290, 300};
        int[] yPoints = {80, 70, 90, 70, 90, 70, 80};
        g.drawPolyline(xPoints, yPoints, xPoints.length);

        // Draw capacitor as two filled rectangles
        int value = 0;
        if (voltageHistory.voltages.size() > 0) {
            value = (int)((voltageHistory.voltages.get(voltageHistory.voltages.size()-1)/parameterTree.getFirst().initialVoltage) * 255);
        }
        Color color = new Color(value, 0, 0);
        g.setColor(color);
        g.fillRect(350, 148, 25, 5);
        color = new Color(0,0,value);
        g.setColor(color);
        g.fillRect(350, 158, 25, 5);
        g.setColor(Color.BLACK);
        g.drawLine(362, 148, 362, 80);
        g.drawLine(300, 80, 362, 80);
        g.drawLine(362, 158, 362, 230);
        g.drawLine(100, 230, 362, 230);
        // Draw battery
        g.drawLine(100, 160, 100, 230); // Battery vertical line
        g.drawLine(90, 150, 110, 150);   // Positive terminal (long line)
        g.drawLine(95, 160, 105, 160);   // Negative terminal (short line)
        g.drawLine(100, 80, 100, 150); 
        g.drawLine(100, 80, 200, 80);
        // Labels
        g.drawString("R", 240, 65);
        g.drawString("C", 337, 154);
        g.drawString("+", 105, 145);
        }

        private void drawVoltage(Graphics g) {
            SimulationParameter parameter = parameterTree.getFirst();
            if (parameter != null) {
                double V_c = parameter.initialVoltage
                        * (1 - Math.exp(-time / (parameter.resistance * parameter.capacitance)));
                voltageHistory.add(V_c);
                voltageHistory.getHistory();
                g.drawString(String.format("Vc: %.2f V", V_c), 250, 300);

                // Display voltage history
                g.drawString("Voltage History:", 480, 32);
                g.drawLine(525, 40, 525, 200);
                for (int i = 0; i < voltageHistory.voltages.size(); i++) {
                    String history = String.format("%.2f V", voltageHistory.voltages.get(i));
                    g.drawString(history, 530, 50 + i * 20);
                    g.drawString(String.format("%.2f s", time - (voltageHistory.voltages.size()-i-1) * 0.1), 480, 50 + i * 20);
                }

            }
        }
    }
}