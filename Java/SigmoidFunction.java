public class SigmoidFunction implements ActivationFunction{

    @Override
    public Double computeOutputByInput(Double in) {
        return  1 / (1 + Math.exp(-in));
    }

  @Override
  public ActivationFunction getDifferentiationFunction() {
    return new SigmoidDifferentationFunction();
  }
}
