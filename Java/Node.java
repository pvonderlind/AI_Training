import java.util.HashMap;
import java.util.Map;

public class Node implements GraphEntity{
    protected HashMap<GraphEntity,Double> inputEdges;
    private HashMap<Node,Double> outputEdges;
    private Double output;
    protected ActivationFunction activationFunction;
    protected ActivationFunction derivationFunction;
    protected Double delta;
    protected Double learningRate;

    public Node(ActivationFunction activationFunction, ActivationFunction derivation, Double learningRate) {
        this.inputEdges = new HashMap<>();
        this.outputEdges = new HashMap<>();
        this.output = null;
        this.activationFunction = activationFunction;
        this.derivationFunction = derivation;
        this.learningRate = learningRate;
    }

    public void addInputEdge(Node predecessor, Double weight) {
        if(!this.inputEdges.containsKey(predecessor)) {
            this.inputEdges.put(predecessor, weight);
            predecessor.addOutputEdge(this, weight);
        }
    }

    public void addOutputEdge(Node next, Double weight) {
        this.outputEdges.put(next, weight);
    }

    private Double calculateOutput() {
        double in = this.calculateInputSum();
        return activationFunction.computeOutputByInput(in);
    }

    private Double calculateInputSum() {
        double in = 0.0;
        for (Map.Entry<GraphEntity, Double> edge: inputEdges.entrySet()) {
            Double weight = edge.getValue();
            GraphEntity node = edge.getKey();
            in += weight * node.getOutput();
        }
        return in;
    }

    @Override
    public Double getOutput() {
        output = calculateOutput();
        return output;
    }

    public void calculateDelta() {
        Double sumOfEdgesWithWeights = 0.0;
        for (Map.Entry<Node,Double> entry: outputEdges.entrySet()) {
            sumOfEdgesWithWeights += entry.getValue() * entry.getKey().getDelta();
        }
        this.delta = sumOfEdgesWithWeights * derivationFunction.computeOutputByInput(calculateInputSum());
        System.out.println("New delta: " + this.delta);
    }

    public Double getDelta() {
        calculateDelta();
        return this.delta;
    }

    @Override
    public void learnNewWeights() {
        for (Map.Entry<Node, Double> entry : outputEdges.entrySet()) {
            Double deltaK = entry.getKey().getDelta();
            Double aj = this.getOutput();
            Double weightik = entry.getValue();
            Double newWeight = weightik + learningRate * aj * deltaK;
            entry.setValue(newWeight);
            entry.getKey().updateInputWeightForChildNode(this, newWeight);
        }
    }

    public void updateInputWeightForChildNode(Node n, Double weight) {
        if (inputEdges.containsKey(n)){
            inputEdges.replace(n, weight);
        }
    }
}
