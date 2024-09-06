# RC Circuit Simulator

This project simulates the behavior of an RC (resistor-capacitor) circuit using a GUI built with Java Swing. The user inputs the circuit parameters like capacitance, resistance, and initial voltage, and the program visualizes the circuit and displays the changing voltage across the capacitor over time until it reaches steady state.

A timer updates the simulation periodically, while the voltage history is displayed on the screen, and the circuit diagram and real-time voltage values are dynamically drawn in the interface. A BinaryTree is used to calculate the voltage in the components of the circuit and a LinkedList is used to store the voltage values of the capacitor at different time intervals.
