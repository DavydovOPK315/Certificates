package com.epam.esm.dao;

import com.epam.esm.entity.Certificate;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CertificateDAO {
    private final JdbcTemplate jdbcTemplate;

    public void create(Certificate certificate) {
        jdbcTemplate.update("INSERT INTO gift_certificates (`name`, `description`, `price`, `duration`, `create_date`, `last_update_date`) VALUES(?, ?, ?, ?, ?, ?)",
                certificate.getName(),
                certificate.getDescription(),
                certificate.getPrice(),
                certificate.getDuration(),
                certificate.getCreateDate(),
                certificate.getLastUpdateDate());
    }

    public Certificate getByName(String name) {
        return jdbcTemplate.query("SELECT * FROM gift_certificates WHERE name=?", new Object[]{name}, new BeanPropertyRowMapper<>(Certificate.class))
                .stream()
                .findAny()
                .orElse(null);
    }

    public Certificate getById(int id) {
        return jdbcTemplate.query("SELECT * FROM gift_certificates WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Certificate.class))
                .stream()
                .findAny()
                .orElse(null);
    }

    public void update(Certificate certificate) {
        jdbcTemplate.update("UPDATE gift_certificates SET name = ?, description = ?, price = ?, duration = ?, last_update_date = ? WHERE id = ?;",
                certificate.getName(),
                certificate.getDescription(),
                certificate.getPrice(),
                certificate.getDuration(),
                certificate.getLastUpdateDate(),
                certificate.getId());
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM gift_certificates WHERE id=?", id);
    }

    public List<Certificate> getAllByTagName(String tagName) {
        return jdbcTemplate.query("SELECT c.* FROM gift_certificates c JOIN gift_certificates_has_tag ct ON c.id = ct.gift_certificates_id JOIN tag t " +
                "ON t.id = ct.tag_id AND t.name = ?", new Object[]{tagName}, new BeanPropertyRowMapper<>(Certificate.class));
    }

    public List<Certificate> getAllByName(String name) {
        String value = "%" + name + "%";
        return jdbcTemplate.query("SELECT * FROM gift_certificates WHERE name LIKE ?", new Object[]{value}, new BeanPropertyRowMapper<>(Certificate.class));
    }

    public List<Certificate> getAllByDescription(String description) {
        String value = "%" + description + "%";
        return jdbcTemplate.query("SELECT * FROM gift_certificates WHERE description LIKE ?", new Object[]{value}, new BeanPropertyRowMapper<>(Certificate.class));
    }

    public List<Certificate> getAll() {
        return jdbcTemplate.query("SELECT * FROM gift_certificates", new BeanPropertyRowMapper<>(Certificate.class));
    }

    public List<Certificate> getAllAndSortByDateDescAndNameDesc() {
        return jdbcTemplate.query("SELECT * FROM gift_certificates ORDER BY create_date DESC, name DESC", new BeanPropertyRowMapper<>(Certificate.class));
    }

    public List<Certificate> getAllAndSortByDateAscAndNameAsc() {
        return jdbcTemplate.query("SELECT * FROM gift_certificates ORDER BY create_date ASC, name ASC", new BeanPropertyRowMapper<>(Certificate.class));
    }

    public List<Certificate> getAllAndSortByDateDeskAndNameAsc() {
        return jdbcTemplate.query("SELECT * FROM gift_certificates ORDER BY create_date DESC, name ASC", new BeanPropertyRowMapper<>(Certificate.class));
    }

    public List<Certificate> getAllAndSortByDateAscAndNameDesc() {
        return jdbcTemplate.query("SELECT * FROM gift_certificates ORDER BY create_date ASC, name DESC", new BeanPropertyRowMapper<>(Certificate.class));
    }
}