package austral.ing.lab1.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Address {

  @Id
  @GeneratedValue
  private Long id;

  @ManyToMany(mappedBy = "addresses")
  private List<User> users = new ArrayList<>();

}
