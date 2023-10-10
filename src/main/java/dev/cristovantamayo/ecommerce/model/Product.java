package dev.cristovantamayo.ecommerce.model;

import dev.cristovantamayo.ecommerce.listeners.GenerateInvoiceListener;
import dev.cristovantamayo.ecommerce.listeners.GenericListener;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners({ GenericListener.class })

@Entity
@Table(name = "product",
        uniqueConstraints = { @UniqueConstraint(name = "unq_product_name", columnNames = { "name" })},
        indexes = { @Index(name = "idx_product_name", columnList = "name") })
public class Product extends EntityBaseInteger {

    @Column(length = 100, nullable = false)
    private String name;

    @Column(columnDefinition = "varchar(215) not null default 'empty'")
    private String description;

    public BigDecimal price;

    @ManyToMany
    @JoinTable(name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    @Column(name = "categories")
    private List<Category> categories;

    @OneToOne(mappedBy = "product")
    private Stock stock;

    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updatedAt;

    @ElementCollection
    @CollectionTable(name = "product_tag", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "tag", length = 50, nullable = false)
    private List<String> tags;

    @ElementCollection
    @CollectionTable(name = "product_attribute", joinColumns = @JoinColumn(name = "product_id"))
    private List<Attribute> attributes;

    @Column(length = 20000)
    private byte[] photo;

    public static Product of (String name, String description, BigDecimal price,
                              List<Category> categories, Stock stock, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new Product(name, description, price, categories, stock, createdAt, updatedAt, null, null, null);
    }
}
