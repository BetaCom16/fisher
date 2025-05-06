package sheasepherd.fisher.entitys;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Geisternetz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;
    private double latitude;
    private double longitude;
    private String size;
    private String status;

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

}
