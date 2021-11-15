package com.example.todolist.consumer.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.NotFound;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Task")
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer","handler"})
public class Task implements Serializable {

    @Id
    @GeneratedValue
    Long id;

    @Column(name = "title", nullable = false, unique = true)
    String title;

    @NotEmpty(message = "Ne peut être Null")
    @Column(name = "description")
    String description;

    @Column(name = "completed")
    Boolean completed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todolistId",
            referencedColumnName = "id",
            nullable = false)
    Todolist todolist;
}
