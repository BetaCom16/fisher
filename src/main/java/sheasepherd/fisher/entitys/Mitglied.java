package sheasepherd.fisher.entitys;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Mitglied {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;
    private String vorname;
    private String nachname;
    private String telefonnummer;
    private String email;
    private String rolle;
    private String passwort;

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }
    public void setNachname(String nachname) {
        this.nachname = nachname;
    }
    public void setTelefonnummer(String telefonnummer) {
        this.telefonnummer = telefonnummer;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setRolle(String rolle) {
        this.rolle = rolle;
    }
    public void setPasswort(String paswort){
        this.passwort = paswort;
    }

    public String getVorname() {
        return vorname;
    }
    public String getNachname() {
        return nachname;
    }
    public String getTelefonnummer() {
        return telefonnummer;
    }
    public String getEmail() {
        return email;
    }
    public String getRolle() {
        return rolle;
    }
    public String getPasswort() {
        return passwort;
    }
}
