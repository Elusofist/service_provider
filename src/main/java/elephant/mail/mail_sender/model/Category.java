package elephant.mail.mail_sender.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String serviceCategoryName;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL,
                fetch = FetchType.EAGER)
    private Set<Skill> skills;

    public Category() {
        skills = new HashSet<>();
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public String getServiceCategoryName() {
        return serviceCategoryName;
    }

    public void setServiceCategoryName(String serviceCategoryName) {
        this.serviceCategoryName = serviceCategoryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", serviceCategoryName='" + serviceCategoryName + '\'' +
                ", skills=" + skills +
                '}';
    }
}
