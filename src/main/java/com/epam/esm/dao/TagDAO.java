package com.epam.esm.dao;

import com.epam.esm.entity.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

// CRD operations for Tag
@Component
@RequiredArgsConstructor
public class TagDAO {
    private final JdbcTemplate jdbcTemplate;

    public List<Tag> index() {
        return jdbcTemplate.query("SELECT * FROM tag", new BeanPropertyRowMapper<>(Tag.class));
    }

    public Tag getById(int id) {
        return jdbcTemplate.query("SELECT * FROM tag WHERE id=?", new Object[]{id}, new TagMapper())
                .stream().findAny().orElse(null);
    }

    public void create(Tag tag) {
        jdbcTemplate.update("INSERT INTO tag VALUES(?, ?)", 0, tag.getName());
    }

    public void update(int id, Tag tag) {
        jdbcTemplate.update("UPDATE tag SET name=? WHERE id=?", tag.getName(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM tag WHERE id=?", id);
    }

    public List<Tag> getTagsByCertificateId(int certificateId) {
        return jdbcTemplate.query("SELECT t.id, t.name FROM tag t JOIN gift_certificates_has_tag ct ON ct.tag_id = t.id AND ct.gift_certificates_id = ?", new Object[]{certificateId}, new TagMapper());
    }
}
