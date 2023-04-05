package com.epam.esm.dao;

import com.epam.esm.entity.Certificate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CertificateDAOTest {
    private static CertificateDAO dao;
    private final Certificate certificate;
    private final Certificate certificate2;

    {
        String date = "2023-03-03T00:15:42.265";
        certificate = new Certificate(1, "c1", "desc1", 100, 10, date, date);
        date = "2023-04-03T00:15:42.375";
        certificate2 = new Certificate(2, "c2", "desc2", 200, 20, date, date);
    }

    @BeforeAll
    static void setUp() {
        EmbeddedDatabase db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("db_scripts/schema.sql")
                .addScript("db_scripts/test-data.sql")
                .build();

        dao = new CertificateDAO(new JdbcTemplate(db));
    }

    @Test
    void getByName() {
        Certificate result = dao.getByName(certificate.getName());
        assertEquals(result.getName(), certificate.getName());
    }

    @Test
    void getById() {
        Certificate result = dao.getById(certificate.getId());
        assertEquals(result.getId(), certificate.getId());
    }

    @Test
    void update() {
        Certificate certificateFromDb;
        Integer initialPrice = certificate.getPrice();
        String initialName = certificate.getName();

        certificate.setPrice(999);
        certificate.setName("tempC");

        assertDoesNotThrow(() -> dao.update(certificate));

        certificateFromDb = dao.getById(certificate.getId());
        assertEquals(certificate.getPrice(), certificateFromDb.getPrice());
        assertEquals(certificate.getName(), certificateFromDb.getName());

        certificate.setPrice(initialPrice);
        certificate.setName(initialName);

        assertDoesNotThrow(() -> dao.update(certificate));

        certificateFromDb = dao.getById(certificate.getId());
        assertEquals(certificate.getPrice(), certificateFromDb.getPrice());
        assertEquals(certificate.getName(), certificateFromDb.getName());
    }

    @Test
    void create() {
        List<Certificate> certificateList = dao.getAll();

        assertEquals(2, certificateList.size());
        assertEquals(certificate.getName(), certificateList.get(0).getName());
        assertEquals(certificate2.getName(), certificateList.get(1).getName());
    }

    @Test
    void delete() {
        String date = LocalDateTime.now().toString();
        Certificate tempCertificate = new Certificate(3, "c3", "desc3", 3000, 300, date, date);
        int initialQuantity = dao.getAll().size();

        dao.create(tempCertificate);
        dao.delete(3);

        assertEquals(2, initialQuantity);
    }

    @Test
    void getAllByTagName() {
        List<Certificate> result = dao.getAllByTagName("tag2");
        assertEquals(2, result.size());
    }

    @Test
    void getAllByName() {
        List<Certificate> result = dao.getAllByName("c");
        assertEquals(2, result.size());
    }

    @Test
    void getAllByDescription() {
        List<Certificate> result = dao.getAllByDescription("desc");
        assertEquals(2, result.size());
    }

    @Test
    void getAll() {
        List<Certificate> result = dao.getAll();
        assertEquals(2, result.size());
    }

    @Test
    void getAllAndSortByDateDescAndNameDesc() {
        List<Certificate> result = dao.getAllAndSortByDateDescAndNameDesc();
        assertEquals(2, result.size());
    }

    @Test
    void getAllAndSortByDateAscAndNameAscSuccess() {
        List<Certificate> result = dao.getAllAndSortByDateAscAndNameAsc();
        assertEquals(2, result.size());
    }

    @Test
    void getAllAndSortByDateDeskAndNameAsc() {
        List<Certificate> result = dao.getAllAndSortByDateDeskAndNameAsc();
        assertEquals(2, result.size());
    }

    @Test
    void getAllAndSortByDateAscAndNameDesc() {
        List<Certificate> result = dao.getAllAndSortByDateAscAndNameDesc();
        assertEquals(2, result.size());
    }
}