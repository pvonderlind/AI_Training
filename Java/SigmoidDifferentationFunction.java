public class SigmoidDifferentationFunction implements ActivationFunction{
    @Override
    public Double computeOutputByInput(Double in) {
        SigmoidFunction g = new SigmoidFunction();
        Double gx = g.computeOutputByInput(in);
        return gx * (1.0 - gx);
    }
}
