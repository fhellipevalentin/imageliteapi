package io.github.dougllasfps.imageliteapi.domain.entity;

import io.github.dougllasfps.imageliteapi.domain.enums.ImageExtension;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_image")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;
    private Long size;
    @Enumerated(EnumType.STRING)
    private ImageExtension extension;
    @CreatedDate
    private LocalDateTime uploadDate;
    private String tags;
    @Lob
    private byte[] file;

    public String getFileName() {
        return  getName().concat(".").concat(getExtension().name());
    }

}
