package austral.ing.lab1.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "LOCATIONS")
public class Location {

    public Location(String name, String address, float lat, float lng) {
        this.name = name;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
    }

    public Location() {}

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long idLocation;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "LAT")
    private float lat;

    @Column(name = "LNG")
    private float lng;

    public long getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(long idLocation) {
        this.idLocation = idLocation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "Location{" +
                "idLocation=" + idLocation +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
