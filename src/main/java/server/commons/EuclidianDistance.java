package server.commons;

public class EuclidianDistance {
    private final EuclidianDistance euclidianDistance = new EuclidianDistance();

    public EuclidianDistance(){
    }

    public static Double CalculateDistance(Posicao PInicial, Posicao PFinal){
        Double XDiff = Math.pow((PFinal.x()- PInicial.x()), 2);
        Double YDiff = Math.pow((PFinal.y()- PInicial.y()), 2);

        return Math.sqrt(XDiff+YDiff);
    }
}
