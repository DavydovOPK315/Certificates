package com.epam.esm.entity;

import lombok.Data;

import java.util.Objects;

@Data
public class CertificateHasTag {
    private int giftCertificatesId;
    private int tagId;

    @Override
    public String toString() {
        return "CertificateHasTag{" +
                "giftCertificatesId=" + giftCertificatesId +
                ", tagId=" + tagId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CertificateHasTag)) return false;
        CertificateHasTag that = (CertificateHasTag) o;
        return getGiftCertificatesId() == that.getGiftCertificatesId() && getTagId() == that.getTagId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGiftCertificatesId(), getTagId());
    }
}
