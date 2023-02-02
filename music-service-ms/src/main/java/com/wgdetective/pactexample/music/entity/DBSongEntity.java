package com.wgdetective.pactexample.music.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

@NoArgsConstructor
@Getter
@Setter
@Table("Song")
public class DBSongEntity {

    @Id
    private Long id;

    @Version
    private Long version;

    private String author;

    private String name;
}
