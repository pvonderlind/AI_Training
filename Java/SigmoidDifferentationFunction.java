public class SigmoidDifferentationFunction implements ActivationFunction{
    @Override
    public Double computeOutputByInput(Double in) {
        return in * (1.0 - in);
    }

  @Override
  public ActivationFunction getDifferentiationFunction() {
    return this;
  }
}
