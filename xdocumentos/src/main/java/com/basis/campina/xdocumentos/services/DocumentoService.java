package com.basis.campina.xdocumentos.services;

import com.basis.campina.xdocumentos.config.ApplicationProperties;
import com.basis.campina.xdocumentos.services.dto.DocumentoDTO;
import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentoService {

    private final MinioClient client;
    private final ApplicationProperties applicationProperties;

    public void salvarDocumentos(List<DocumentoDTO> documentos){
        for (DocumentoDTO documentoDTO : documentos) {
            save(documentoDTO);
        }
    }

    @SneakyThrows
    public String save(DocumentoDTO documentoDTO) {
        client.putObject(PutObjectArgs.builder()
                .stream(new ByteArrayInputStream(documentoDTO.getFile().getBytes()),
                        documentoDTO.getFile().getBytes().length, 0)
                .contentType(StandardCharsets.UTF_8.toString())
                .bucket(applicationProperties.getBucket())
                .object(documentoDTO.getUuId())
                .build());
        return documentoDTO.getUuId();
    }

    @SneakyThrows
    public DocumentoDTO getDocument(String uuId) {
        GetObjectResponse file = client.getObject(GetObjectArgs.builder()
                .bucket(applicationProperties.getBucket())
                .object(uuId)
                .build());
        return new DocumentoDTO(uuId, IOUtils.toString(file, StandardCharsets.UTF_8.toString()));
    }

}