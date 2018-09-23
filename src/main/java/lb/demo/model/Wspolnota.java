package lb.demo.model;

import lb.demo.model.Mieszkanie;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Wspolnota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @NotNull
    @Size(min=2, max=30)
    private String adress;
    @NotNull
    private int number;
    @OneToMany(mappedBy="wspolnota")
    private List<Mieszkanie> mieszkania;

    public Wspolnota() {
    }

    public Wspolnota(String name, String adress, int number) {
        this.name = name;
        this.adress = adress;
        this.number = number;
    }

//    public Wspolnota(String adress, int number) {
//        this.adress = adress;
//        this.number = number;
//        this.name = adress+" "+number;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<Mieszkanie> getMieszkania() {
        return mieszkania;
    }

    public void setMieszkania(List<Mieszkanie> mieszkania) {
        this.mieszkania = mieszkania;
    }
}
