package elephant.mail.mail_sender.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "Expert_Skill",
            joinColumns = {@JoinColumn(name = "skill_id")},
            inverseJoinColumns = {@JoinColumn(name = "expert_id")}
    )
    private Set<Expert> experts;
    private double basePrice;
    private String shortDescription;
    @ManyToOne(cascade = CascadeType.ALL)
    private Category category;
    private String title;

    public Skill() {
        experts = new HashSet<>();
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public Set<Expert> getExperts() {
        return experts;
    }

    public void setExperts(Set<Expert> experts) {
        this.experts = experts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }
}
