import java.util.Scanner;

public class BasicTest {

    public static void main(String[] args) {
        ActivationFunction sig = new SigmoidFunction();
        ActivationFunction diffSig = new SigmoidDifferentationFunction();
        Double alpha = 0.8;
        Node n1 = new Node(sig, diffSig, alpha);
        Node n2 = new Node(sig, diffSig, alpha);

        InputNode i1 = new InputNode("Enter first number [0,1]",sig,diffSig,alpha);
        InputNode i2 = new InputNode("Enter second number [0,1]",sig,diffSig,alpha);


        OutputNode o1 = new OutputNode(sig, diffSig, alpha);

        n1.addInputEdge(i1, 1.0);
        n1.addInputEdge(i2, -1.0);

        n2.addInputEdge(i1, -1.0);
        n2.addInputEdge(i2, 1.0);

        o1.addInputEdge(n1, 1.0);
        o1.addInputEdge(n2, 1.0);

        System.out.println("Output: " + o1.getOutput()s);

    }

}
