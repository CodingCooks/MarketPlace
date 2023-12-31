package com.pashonokk.marketplace.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, updatable = false)
    private String name;
    @Column(unique = true, nullable = false, updatable = false)
    private String description;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_category_id")
    private SubCategory subCategory;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "advertisement", orphanRemoval = true)
    @Setter(AccessLevel.PRIVATE)
    private Set<Image> images = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "savedAdvertisements")
    @Setter(AccessLevel.PRIVATE)
    private Set<User> users = new HashSet<>();

    @Column(unique = true, nullable = false, updatable = false)
    private String location;
    private LocalDate creationDate;

    private boolean isActive = true;

    private Long likes;
    private Long views;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    public void linkImages() {
        for (Image image : images) {
            image.setAdvertisement(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Advertisement that = (Advertisement) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
