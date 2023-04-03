package com.epam.esm.service;

import com.epam.esm.dao.CertificateHasTagDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CertificateHasTagService {
    private final CertificateHasTagDAO certificateHasTagDAO;

    public void create(int giftCertificatesId, int tagId) {
        certificateHasTagDAO.create(giftCertificatesId, tagId);
    }

    public void delete(int giftCertificatesId) {
        certificateHasTagDAO.delete(giftCertificatesId);
    }
}
