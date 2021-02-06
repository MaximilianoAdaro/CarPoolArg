package austral.ing.lab1.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "TYPE_NOTIFICATION")
public class TypeNotification {

    public TypeNotification(String typeName) {
        this.typeName = typeName;
    }

    public TypeNotification() {
    }

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long idTypeNotification;

    @Column(name = "TYPE_NAME")
    private String typeName;

    public long getIdTypeNotification() {
        return idTypeNotification;
    }

    public void setIdTypeNotification(long idTypeNotification) {
        this.idTypeNotification = idTypeNotification;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "TypeNotification{" +
                "idTypeNotification=" + idTypeNotification +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
