package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "gift_certificates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Certificate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Long price;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "create_date")
    private String createDate;

    @Column(name = "last_update_date")
    private String lastUpdateDate;

    @OneToMany(mappedBy = "certificate")
    private List<Order> orders;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "gift_certificates_has_tag",
            joinColumns = @JoinColumn(name = "gift_certificates_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;

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
                ", tags=" + tags +
                '}';
    }
}