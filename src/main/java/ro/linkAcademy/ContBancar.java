package ro.linkAcademy;

public class ContBancar {
    private String numarCont;
    private double balanta;
    private String tipDeCont;

    public ContBancar(String numarCont, double balanta,String tipDeCont){
        this.numarCont = numarCont;
        this.balanta = balanta;
        this.tipDeCont=tipDeCont;
    }

    public String getNumarCont() {
        return numarCont;
    }

    public void setNumarCont(String numarCont) {
        this.numarCont = numarCont;
    }

    public double getBalanta() {
        return balanta;
    }

    public void setBalanta(double balanta) {
        this.balanta = balanta;
    }

    public String getTipDeCont() {
        return tipDeCont;
    }

    public void setTipDeCont(String tipDeCont) {
        this.tipDeCont = tipDeCont;
    }

    public void depunere(double suma){
        this.balanta+=suma;
        System.out.println("Ai depus "+suma+" lei iar balanta este acum "+ this.balanta+" lei");

    }
    public void retragere(double suma){
        if(this.balanta>=suma) {
            this.balanta -= suma;
            System.out.println("Ai retras " + suma + " lei iar balanta este acum " + this.balanta + " lei");
        }else{
            System.out.println("Fonduri insuficiente");
        }

    }

    public void transfer(ContBancar destinatar, double suma){
        if(this.balanta>=(suma + suma * 0.1)) {
            this.balanta -= suma;
            destinatar.setBalanta(suma);
            this.balanta -= suma * 0.1;
            System.out.println("Ai transferat "+suma+" lei in contul "+destinatar.getNumarCont());
        }
    }

    @Override
    public String toString() {
        return "ContBancar{ IBAN= " + numarCont + ", balanta= " + balanta +", tipDeCont= " + tipDeCont;
    }
}
