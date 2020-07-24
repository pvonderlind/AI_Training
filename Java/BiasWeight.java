public class BiasWeight implements GraphEntity {

    private Double weight;

    public BiasWeight(){
        this.weight = 1.0;
    }

    @Override
    public Double getOutput() {
        return weight;
    }

    @Override
    public void learnNewWeights() {
        // do nothing
    }
}
