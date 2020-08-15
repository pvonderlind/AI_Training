public interface ActivationFunction {
    public Double computeOutputByInput(Double in);
    public ActivationFunction getDifferentiationFunction();
}
