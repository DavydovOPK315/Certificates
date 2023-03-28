package com.epam.esm.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CertificateHasTagDAO {
    private final JdbcTemplate jdbcTemplate;

    public void create(int giftCertificatesId, int tagId) {
        jdbcTemplate.update("INSERT INTO gift_certificates_has_tag VALUES(?, ?)", giftCertificatesId, tagId);
    }

    public void delete(int giftCertificatesId) {
        jdbcTemplate.update("DELETE FROM gift_certificates_has_tag WHERE gift_certificates_id=?", giftCertificatesId);
    }
}
