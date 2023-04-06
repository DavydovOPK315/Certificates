package com.epam.esm.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "order")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private String date;

    @Column(name = "price")
    private Long price;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "gift_certificates_id")
    private Certificate certificate;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", price=" + price +
                ", user=" + user +
                ", certificate=" + certificate +
                '}';
    }
}