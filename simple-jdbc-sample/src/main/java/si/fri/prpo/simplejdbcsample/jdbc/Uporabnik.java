package si.fri.prpo.simplejdbcsample.jdbc;

public class Uporabnik extends Entiteta {

    private static final long serialVersionUID = 1L;

    private String ime;
    private String priimek;
    private String uporabniskoIme;

    public Uporabnik(String ime, String priimek, String uporabniskoIme) {
        super();
        this.ime = ime;
        this.priimek = priimek;
        this.uporabniskoIme = uporabniskoIme;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPriimek() {
        return priimek;
    }

    public void setPriimek(String priimek) {
        this.priimek = priimek;
    }

    public String getUporabniskoIme() {
        return uporabniskoIme;
    }

    public void setUporabniskoIme(String uporabniskoIme) {
        this.uporabniskoIme = uporabniskoIme;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Ime: ");
        sb.append(this.ime);
        sb.append(" Priimek: ");
        sb.append(this.priimek);
        sb.append(" Uporabnisko ime: ");
        sb.append(this.uporabniskoIme);

        return sb.toString();
    }

}
