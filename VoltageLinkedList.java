import java.util.ArrayList;

class VoltageNode {
    double voltage;
    VoltageNode next;

    VoltageNode(double voltage) {
        this.voltage = voltage;
    }
}

class VoltageLinkedList {
    VoltageNode head;
    VoltageNode tail;
    ArrayList<Double> voltages = new ArrayList<>();
    void add(double voltage) {
        VoltageNode newNode = new VoltageNode(voltage);
        if (tail != null) {
            tail.next = newNode;
        }
        tail = newNode;
        if (head == null) {
            head = newNode;
        }
    }

    void clear() {
        head = null;
        tail = null;
    }

    void getHistory() {
        if (voltages.size() > 5)
        {
            voltages.remove(0);
        }
        VoltageNode current = head;
        while (current.next != null) {
            current = current.next;
        }
        voltages.add((int)(current.voltage * 1000) / 1000.0);
    }
}