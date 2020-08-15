import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

/**
 * Implementation of a fully linked neural net with
 * one input, one hidden and one output layer.
 */
public class Network {

  private ArrayList<List<Neuron>> layers;
  private Random random  = new Random(1);

  public Network
      (
          int inputLayerNodes,
          int hiddenLayerNodes,
          int outputLayerNodes
      ){
    this.layers = new ArrayList<>();
    this.layers.add(generateRandomWeightedNeurons(inputLayerNodes, hiddenLayerNodes)); // hidden layer
    this.layers.add(generateRandomWeightedNeurons(hiddenLayerNodes, outputLayerNodes)); // output layer
  }

  private List<Neuron> generateRandomWeightedNeurons(int prevLayerCount, int layerCount){
    List<Neuron> buffer = new ArrayList<>();
    for (int i=0; i < layerCount; i++){
      buffer.add(generateRandomLayerWeightsNeuron(prevLayerCount));
    }
    return buffer;
  }

  private Neuron generateRandomLayerWeightsNeuron(int count) {
    List<Double> weights = new ArrayList<>();
    for (int i = 0; i < count + 1; i++) { // +1  because of the bias weight added at the end
      weights.add(random.nextDouble());
    }
    Neuron neuron = new Neuron();
    neuron.weights = weights;
    return neuron;
  }

  public double[] forwardPropagate(double[] row) {
    double[] inputs = row;
    for (List<Neuron> layer : layers) {
      double[] newInputs = new double[inputs.length];
      for(Neuron neuron : layer) {
        double out = activate(neuron, inputs);
        int index = layer.indexOf(neuron);
        newInputs[index] = out;
      }
      inputs = newInputs;
    }
    return inputs;
  }

  // note that the last item in the row is the expected result
  private double activate(Neuron neuron, double[] row){
    double in = neuron.weights.get(neuron.weights.size() - 1); // add last item
    for (int i = 0; i < row.length - 1; i++) {
      in += neuron.weights.get(i) * row[i];
    }
    double act = this.transfer(in);
    neuron.output = act;
    return act;
  }

  public void backPropagate(double[] expected) {
    ListIterator<List<Neuron>> iterator = layers.listIterator(layers.size());
    while (iterator.hasPrevious()) {
      List<Neuron> layer = iterator.previous();
      double[] errors = new double[layer.size()];

      if (layers.indexOf(layer) == layer.size() - 1) { // output layer

        for (int i = 0; i < layer.size(); i++) {
          Neuron neuron = layer.get(i);
          errors[i] = expected[i] - neuron.output;
        }

      } else { // hidden layer

        List<Neuron> nextLayer = layers.get(layers.indexOf(layer) + 1);
        for (int j = 0; j < layer.size(); j++) { // add part of next layer errors to each connected neuron
          errors[j] = 0;
          for (int i = 0; i < nextLayer.size(); i++) {
             errors[j] += nextLayer.get(i).weights.get(j) * nextLayer.get(i).delta;
          }
        }

      }

      for (int i = 0; i < layer.size(); i++) {
        Neuron n = layer.get(i);
        n.delta = errors[i] * this.transferDerivative(n.output);
      }
    }
  }

  private double transferDerivative(double out) {
    return out * (1 - out);
  }

  private double transfer(double in) {
    return 1.0 / (1.0 + Math.exp(-in));
  }

  public void updateWeights(double learningRate, Double[] row) {
    for(int i = 0; i < layers.size(); i++ ) {
      List<Neuron> layer = layers.get(i);
      List<Double> inputs = new ArrayList<>();

      // get inputs from last layer
      if (i != 0) {
        for (Neuron prev : layers.get(i-1)) {
          inputs.add(prev.output);
        }
      } else {
        inputs = new ArrayList<>(Arrays.asList(row));
        inputs.remove(inputs.size() - 1);
      }

      for (Neuron n : layer) {
        for (int j = 0; j < inputs.size(); j++) {
          Double updatedWeight = n.weights.get(j) + inputs.get(j) * learningRate * n.delta;
          n.weights.set(j, updatedWeight);
        }

        int j  = n.weights.size() - 1;
        n.weights.set(j, n.weights.get(j) + learningRate * n.delta);
      }

    }
  }

  public void trainNetwork(double[][] trainingData, double learningRate, int epochs) {
    for (int epoch = 0; epoch < epochs; epoch++) {
      double errorSum = 0;

      for (double[] row : trainingData) {
        double[] outputsOfLastLayer = forwardPropagate(row);
        double expectedTrue = row[row.length - 1];
        double[] expected = buildExpectedArray(expectedTrue, layers.get(layers.size() - 1).size());
        errorSum += calculateErrorSquaredSum(expected, outputsOfLastLayer);
        backPropagate(expected); // size of output layer
        updateWeights(learningRate, Arrays.stream(row).boxed().toArray(Double[]::new));
      }
      System.out.println(String.format("Epoch: %d, Squared Error: %f", epoch, errorSum));
    }
  }

  private double calculateErrorSquaredSum(double[] expected, double[] outputs) {
    double errorSum = 0;
    List<Neuron> layer = layers.get(layers.size() - 1);
    for (int i = 0; i < layer.size(); i++) {
      errorSum += Math.pow(Math.abs(expected[i] - outputs[i]),2);
    }
    return errorSum;
  }

  private double[] buildExpectedArray(double expectedTrue, int length) {
      double[] expected = new double[length];
      for (int i = 0; i < expected.length; i++) {
        if (i == expectedTrue) {
          expected[i] = 1.0;
        } else {
          expected[i] = 0.0;
        }
      }
      return expected;
  }

  @Override
  public String toString() {
    StringBuilder b = new StringBuilder();
    b.append("=================|| Current Results ||==================\n");
    for (List<Neuron> l : layers) {
      b.append("---------- Layer " + layers.indexOf(l) + "------");
      for (Neuron n : l) {
        b.append(n);
      }
      b.append("\n");
    }
    return b.toString();
  }
}
