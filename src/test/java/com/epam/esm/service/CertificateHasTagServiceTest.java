package com.epam.esm.service;

import com.epam.esm.dao.CertificateHasTagDAO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CertificateHasTagServiceTest {

    @Mock
    private CertificateHasTagDAO certificateHasTagDAO;

    @Spy
    @InjectMocks
    private CertificateHasTagService service;

    @Test
    void create() {
        doNothing().when(certificateHasTagDAO).create(anyInt(), anyInt());

        assertDoesNotThrow(() -> service.create(anyInt(), anyInt()));

        verify(service, times(1)).create(anyInt(), anyInt());
        verify(certificateHasTagDAO, times(1)).create(anyInt(), anyInt());
    }

    @Test
    void delete() {
        doNothing().when(certificateHasTagDAO).delete(anyInt());

        assertDoesNotThrow(() -> service.delete(anyInt()));

        verify(service, times(1)).delete(anyInt());
        verify(certificateHasTagDAO, times(1)).delete(anyInt());
    }
}