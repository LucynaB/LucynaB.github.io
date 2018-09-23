package lb.demo.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Mieszkanie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private int number;
    private double area;
    @ManyToOne
    private Wspolnota wspolnota;
    @OneToMany(mappedBy="mieszkanie")
    private List<Mieszkaniec> mieszkancy;

    public Mieszkanie() {
    }

    public Mieszkanie(int number, double area) {
        this.number = number;
        this.area = area;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public Wspolnota getWspolnota() {
        return wspolnota;
    }

    public void setWspolnota(Wspolnota wspolnota) {
        this.wspolnota = wspolnota;
    }

    public List<Mieszkaniec> getMieszkancy() {
        return mieszkancy;
    }

    public void setMieszkancy(List<Mieszkaniec> mieszkancy) {
        this.mieszkancy = mieszkancy;
    }
}
