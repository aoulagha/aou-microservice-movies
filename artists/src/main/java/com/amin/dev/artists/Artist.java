package com.amin.dev.artists;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(indexes = {
        @Index(name = "idx_fname", columnList = "firstName"),
        @Index(name = "idx_lname", columnList = "lastName")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Artist {

    @Id
    @SequenceGenerator(
            name = "artist_id_sequence",
            sequenceName = "artist_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "artist_id_sequence"
    )
    private Integer id;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

}
