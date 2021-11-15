package com.example.todolist.consumer.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "todolist")
@EqualsAndHashCode(of = {"name"})
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer","handler"})
public class Todolist implements Serializable {

    @Id
    @GeneratedValue
    Long id;

    @NotEmpty(message = "Ne doit pas être Null")
    @Column(nullable = false, unique = true)
    String name;

    @NotEmpty(message = "Ne doit pas être Null")
    @Column(nullable = false)
    String surName;


}
