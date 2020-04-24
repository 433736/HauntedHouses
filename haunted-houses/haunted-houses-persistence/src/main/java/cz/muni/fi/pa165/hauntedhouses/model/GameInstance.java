package cz.muni.fi.pa165.hauntedhouses.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author David Hofman
 */
@Entity
public class GameInstance implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable=false)
    private int banishesAttempted;

    @Column(nullable=false)
    private int banishesRequired;

    @OneToOne
    @JoinColumn(unique = true, nullable = false)
    private Player player;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "gameInstance")
    private Specter specter;

    public GameInstance() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getBanishesAttempted() {
        return banishesAttempted;
    }

    public void setBanishesAttempted(int banishesAttempted) {
        this.banishesAttempted = banishesAttempted;
    }

    public int getBanishesRequired() {
        return banishesRequired;
    }

    public void setBanishesRequired(int banishesRequired) {
        this.banishesRequired = banishesRequired;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Specter getSpecter() {
        return specter;
    }

    public void setSpecter(Specter specter) {
        this.specter = specter;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof GameInstance)) return false;
        if(this == obj) return true;
        GameInstance gameInstance = (GameInstance) obj;
        return getPlayer().equals(gameInstance.getPlayer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPlayer());
    }

    @Override
    public String toString() {
        return "GameInstance{" +
                "id=" + id +
                ", banishesAttempted=" + banishesAttempted +
                ", banishesRequired=" + banishesRequired +
                '}';
    }
}
