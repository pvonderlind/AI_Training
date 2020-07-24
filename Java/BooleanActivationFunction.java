public class BooleanActivationFunction implements ActivationFunction {
    @Override
    public Double computeOutputByInput(Double in) {
        return (in >= 0.0) ? 1.0 : 0.0;
    }
}
