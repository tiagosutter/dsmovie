package com.devsuperior.dsmovie.entities;

import javax.persistence.*;

@Entity
@Table(name = "tb_score")
public class Score {

    @EmbeddedId
    private ScorePK id = new ScorePK();
    private Double value;

    public Score() {
    }

    public Movie getMovie(Movie movie) {
        return id.getMovie();
    }

    public void setMovie(Movie movie) {
        id.setMovie(movie);
    }

    public User getUser(User user) {
        return id.getUser();
    }

    public void setUser(User user) {
        id.setUser(user);
    }

    public ScorePK getId() {
        return id;
    }

    public void setId(ScorePK id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
