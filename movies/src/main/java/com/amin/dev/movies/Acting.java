package com.amin.dev.movies;

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
public class Acting {

    @Id
    @SequenceGenerator(
            name = "act_id_sequence",
            sequenceName = "act_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "act_id_sequence"
    )
    Integer id;

    Integer idMovie;

    Integer idActor;


}
