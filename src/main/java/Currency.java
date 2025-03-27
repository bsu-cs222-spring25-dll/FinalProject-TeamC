public class Currency {
    private double rate;
    private String name;

    public Currency(double rate, String name){
        this.rate = rate;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "rate=" + rate +
                ", name='" + name + '\'' +
                '}';
    }
}
