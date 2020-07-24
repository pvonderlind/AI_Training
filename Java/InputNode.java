import java.rmi.activation.ActivationID;
import java.util.Scanner;

public class InputNode extends Node{

    private Scanner scanner = new Scanner(System.in);
    private String message;
    private Double weight;

    public InputNode(String inputMessage, ActivationFunction act, ActivationFunction dev, Double learningRate) {
        super(act, dev, learningRate);
        this.message = inputMessage;
        this.weight = null;
    }

    @Override
    public Double getOutput() {
        if (weight == null) {
            System.out.println(message);
            weight = scanner.nextDouble();
        }
        return weight;
    }

    public void setNewInput(Double in) {
        this.weight = in;
    }

}
