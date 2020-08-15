import java.util.List;

public class Neuron {
  //note biasweight is the last item
  public List<Double> weights;
  public double output;
  public double delta;

  @Override
  public String toString() {
    return "\n --------- \n Weights: " +weights +" \n Delta: " + delta + "\n Out: " + output + "\n -------- \n";
  }
}
