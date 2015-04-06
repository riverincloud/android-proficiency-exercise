package com.riverincloud.androidproficiencyexercise;

/**
 * Created by Di on 1/04/2015.
 *
 * A object of the Row class represents a row of the list of rows from the json feed.
 */
public class Row {

    private String title;
    private String description;
    private String imageHref;

    public Row() {
    }

    public Row(String title, String description, String imageHref) {
        this.title = title;
        this.description = description;
        this.imageHref = imageHref;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageHref() {
        return imageHref;
    }

    public void setImageHref(String imageHref) {
        this.imageHref = imageHref;
    }

    @Override
    public String toString() {
        return "Row{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imageHref='" + imageHref + '\'' +
                '}';
    }

}
