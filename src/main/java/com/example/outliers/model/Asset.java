package com.example.outliers.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Asset implements Serializable {
    /**
     * Entity ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    /**
     * The version used for optimistic locking.
     */
    @Version
    @Column(name = "VERSION")
    private Integer version;

    @NotNull(message = "Age may not be null")
    @NotEmpty(message = "Age may not be empty")
    private String age;

    @NotNull(message = "Uptime may not be null")
    @NotEmpty(message = "Uptime may not be empty")
    private String uptime;

    @NotNull(message = "numOfFailures positive number value is required")
    @Min(value = 0, message = "numOfFailures positive number, min 0 is required")
    private int numOfFailures;

    public Asset() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getUptime() {
        return uptime;
    }

    public void setUptime(String uptime) {
        this.uptime = uptime;
    }

    public int getNumOfFailures() {
        return numOfFailures;
    }

    public void setNumOfFailures(int numOfFailures) {
        this.numOfFailures = numOfFailures;
    }

    @Override
    public String toString() {
        return "Asset{" +
                "id=" + id +
                ", age='" + age + '\'' +
                ", uptime='" + uptime + '\'' +
                ", numOfFailures=" + numOfFailures +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Asset)) {
            return false;
        }
        final Asset asset = (Asset) o;
        return Objects.equals(id, asset.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
