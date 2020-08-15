public class StepFunction implements ActivationFunction {
  @Override
  public Double computeOutputByInput(Double in) {
    return in >= 1 ? 1.0 : 0.0;
  }

  @Override
  public ActivationFunction getDifferentiationFunction() {
    return this;
  }
}
