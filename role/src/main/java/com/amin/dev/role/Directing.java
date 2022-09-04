package com.amin.dev.role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Directing {

    @Id
    @SequenceGenerator(
            name = "dir_id_sequence",
            sequenceName = "dir_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "dir_id_sequence"
    )
    Integer id;

    Integer idMovie;

    Integer idDirector;
}
