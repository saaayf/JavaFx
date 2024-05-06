package com.skillseekr.Models.Offers;
import java.util.Date;
import java.util.List;

public class Offer {
    private Integer id;
    private String title;
    private String description;
    private String author;
    private Date created_at;
    private Motive motive;
    private Type type;
    private Location location;
    private Status status;
    private String file_name;
    private List<Skill> skills;
    private String downloadUrl;


    // default Constructor
    public Offer() {
    }
   //Constructor with id parameter
    public Offer(Integer id, String title, String description, String author, Date createdAt, Motive motive, Type type, Location location, Status status, String fileName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
        this.created_at = created_at;
        this.motive = motive;
        this.type = type;
        this.location = location;
        this.status = status;
        this.file_name = file_name;
        this.skills = skills;
    }

    // Constructor without id parameter
    public Offer(String title, String description, String author, Date createdAt, Motive motive, Type type, Location location, Status status, String fileName, List<Skill> skills) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.created_at = created_at;
        this.motive = motive;
        this.type = type;
        this.location = location;
        this.status = status;
        this.file_name = file_name;
        this.skills = skills;
    }

    // Getters and setters
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Motive getMotive() {
        return motive;
    }

    public void setMotive(Motive motive) {
        this.motive = motive;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }
    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }
    // Method to add a skill to the offer
    public void addSkill(Skill skill) {
        this.skills.add(skill);
    }

    // Method to remove a skill from the offer
    public void removeSkill(Skill skill) {
        this.skills.remove(skill);
    }
    public String getDownloadUrl() {
        return downloadUrl;
    }
    @Override
    public String toString() {
        return "Offer{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", author='" + author + '\'' +
                ", created_at=" + created_at +
                ", motive=" + motive +
                ", type=" + type +
                ", location=" + location +
                ", status=" + status +
                ", file_name='" + file_name + '\'' +
                ", skills=" + skills +
                '}';
    }
}


