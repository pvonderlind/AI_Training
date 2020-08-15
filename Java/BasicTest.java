public class BasicTest {

  // last entry is the expected output.
  private static double[][] trainingData = {
      {2.7810836, 2.550537003, 0},
      {1.465489372, 2.362125076, 0},
      {3.396561688, 4.400293529, 0},
      {1.38807019, 1.850220317, 0},
      {3.06407232, 3.005305973, 0},
      {7.627531214, 2.759262235, 1},
      {5.332441248, 2.088626775, 1},
      {6.922596716, 1.77106367, 1},
      {8.675418651, -0.242068655, 1},
      {7.673756466, 3.508563011, 1}
  };

  public static void main(String[] args) {
    Network network = new Network(2, 2, 2);
    network.trainNetwork(trainingData, 0.5, 2000);
    System.out.println(network);
  }
}
