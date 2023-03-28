package com.epam.esm.entity;

import lombok.Data;

import java.util.Objects;

@Data
public class Certificate {
    private Integer id;
    private String name;
    private String description;
    private Integer price;
    private Integer duration;
    private String createDate;
    private String lastUpdateDate;

    @Override
    public String toString() {
        return "Certificate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", createDate='" + createDate + '\'' +
                ", lastUpdateDate='" + lastUpdateDate + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Certificate)) return false;
        Certificate that = (Certificate) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName()) && Objects.equals(getDescription(), that.getDescription()) && Objects.equals(getPrice(), that.getPrice()) && Objects.equals(getDuration(), that.getDuration()) && Objects.equals(getCreateDate(), that.getCreateDate()) && Objects.equals(getLastUpdateDate(), that.getLastUpdateDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getPrice(), getDuration(), getCreateDate(), getLastUpdateDate());
    }
}
