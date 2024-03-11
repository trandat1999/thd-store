package com.thd.store.dto.file;

import com.thd.store.entity.File;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author DatNuclear 15/02/2024
 * @project store
 */
@Data
@NoArgsConstructor
public class FileDto {
    private Long id;
    private String name;
    private Long size;
    private String description;
    private String path;

    public FileDto(File entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.name = entity.getName();
            this.size = entity.getSize();
            this.description = entity.getDescription();
            this.path = entity.getPath();
        }
    }
}
