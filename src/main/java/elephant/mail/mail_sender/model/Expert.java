package elephant.mail.mail_sender.model;

import elephant.mail.mail_sender.enums.Role;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.*;

@Entity
public class Expert extends User {
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Skill> skills;
    private int rank;
    @Lob
    @Column(name = "PROFILE_PIC")
    private byte[] image;

    public Expert() {
        skills = new HashSet<>();
        setRole(Role.EXPERT);
        image = new byte[300000];
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void addSkill(Skill skill) {
        this.skills.add(skill);
    }

    @Override
    public String toString() {
        return "Expert{" +
                "skills=" + skills +
                ", rank=" + rank +
                ", image=" + Arrays.toString(image) +
                '}';
    }
}
