import java.util.Map;

public class OutputNode extends Node{

    private Double expectedResult;

    public OutputNode(ActivationFunction activationFunction, ActivationFunction derivation, Double learningRate) {
        super(activationFunction, derivation, learningRate);
    }

    public void learn(Double expectedResult) {
        this.expectedResult = expectedResult;
        this.calculateDelta();
        for (Map.Entry<GraphEntity, Double> entry: inputEdges.entrySet()) {
            entry.getKey().learnNewWeights();
        }
    }

    @Override
    public void calculateDelta() {
        Double error = this.expectedResult - this.getOutput();
        this.delta = this.derivationFunction.computeOutputByInput(this.getOutput()) * error;
    }
}
