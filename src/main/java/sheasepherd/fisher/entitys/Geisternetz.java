package sheasepherd.fisher.entitys;

import jakarta.persistence.*;

@Entity
public class Geisternetz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;
    private double latitude;
    private double longitude;
    private String size;
    private String status;
    private String date;

    @ManyToOne
    @JoinColumn(name = "mitglied_id")
    private Mitglied meldendePerson;

    @Column(nullable = true)
    private Boolean anonym;

    @ManyToOne
    @JoinColumn(name = "bergende_mitglied_id")
    private Mitglied bergendePerson;

    public Mitglied getBergendePerson() {
        return bergendePerson;
    }

    public void setBergendePerson(Mitglied bergendePerson) {
        this.bergendePerson = bergendePerson;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public void setSize(String size) {
        this.size = size;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public void setDate(String date){
        this.date = date;
    }
    public void setAnonym(Boolean anonym){
        this.anonym = anonym;
    }
    public void setMeldendePerson(Mitglied meldendePerson){
        this.meldendePerson = meldendePerson;
    }

    public double getLatitude() {
        return latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public String getSize() {
        return size;
    }
    public String getStatus(){
        return status;
    }
    public int getId(){
        return id;
    }
    public String getDate(){
        return date;
    }
    public Boolean getAnonym() {
        return anonym;
    }
    public Mitglied getMeldendePerson() {
        return meldendePerson;
    }

}
