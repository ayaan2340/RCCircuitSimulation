class SimulationParameter {
    double resistance;
    double capacitance;
    double initialVoltage;

    SimulationParameter(double resistance, double capacitance, double initialVoltage) {
        this.resistance = resistance;
        this.capacitance = capacitance;
        this.initialVoltage = initialVoltage;
    }
}

class TreeNode {
    SimulationParameter parameter;
    TreeNode left;
    TreeNode right;

    TreeNode(SimulationParameter parameter) {
        this.parameter = parameter;
    }
}

class SimulationTree {
    TreeNode root;

    void add(SimulationParameter parameter) {
        root = addRecursive(root, parameter);
    }

    private TreeNode addRecursive(TreeNode node, SimulationParameter parameter) {
        if (node == null) {
            return new TreeNode(parameter);
        }
        if (parameter.resistance < node.parameter.resistance) {
            node.left = addRecursive(node.left, parameter);
        } else if (parameter.resistance > node.parameter.resistance) {
            node.right = addRecursive(node.right, parameter);
        }
        return node;
    }

    SimulationParameter getFirst() {
        return root != null ? root.parameter : null;
    }
}