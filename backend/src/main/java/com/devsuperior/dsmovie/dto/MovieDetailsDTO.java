package com.devsuperior.dsmovie.dto;

import com.devsuperior.dsmovie.entities.Movie;

public class MovieDetailsDTO {
    private Long id;
    private String title;
    private Double score;
    private Integer count;
    private String image;
    private String description;

    public MovieDetailsDTO() {
    }

    public MovieDetailsDTO(Long id, String title, Double score, Integer count, String image, String description) {
        this.id = id;
        this.title = title;
        this.score = score;
        this.count = count;
        this.image = image;
        this.description = description;
    }

    public MovieDetailsDTO(Movie movie) {
        id = movie.getId();
        title = movie.getTitle();
        score = movie.getScore();
        count = movie.getCount();
        image = movie.getImage();
        description = movie.getDescription();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}