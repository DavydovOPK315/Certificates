package com.epam.esm.dao;

import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// CRD operations for Tag
@Component
public class TagDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Tag> index() {
//        return jdbcTemplate.query("SELECT * FROM tag", new TagMapper());
        // if field from resultSet is equals
        return jdbcTemplate.query("SELECT * FROM tag", new BeanPropertyRowMapper<>(Tag.class));
    }


    // like prepared statement
    public Tag show(int id) {
        return jdbcTemplate.query("SELECT * FROM tag WHERE id=?", new Object[]{id}, new TagMapper())
                .stream().findAny().orElse(null);
    }

    public void save(Tag tag) {
        jdbcTemplate.update("INSERT INTO tag VALUES(?, ?)", 0, tag.getName());
    }

    public void update(int id, Tag tag) {
        jdbcTemplate.update("UPDATE tag SET name=? WHERE id=?", tag.getName(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM tag WHERE id=?", id);
    }
}
