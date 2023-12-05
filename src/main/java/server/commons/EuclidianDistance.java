package server.commons;

public class EuclidianDistance {
    private final EuclidianDistance euclidianDistance = new EuclidianDistance();

    public EuclidianDistance(){
    }

    public static Float CalculateDistance(Posicao PInicial, Posicao PFinal){
        Double XDiff = Math.pow((PFinal.x()- PInicial.x()), 2);
        Double YDiff = Math.pow((PFinal.y()- PInicial.y()), 2);

        return (float) Math.sqrt(XDiff+YDiff);
    }
}
