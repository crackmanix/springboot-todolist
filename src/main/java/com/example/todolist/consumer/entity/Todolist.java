package com.example.todolist.consumer.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@ToString(of = {"name"})
@EqualsAndHashCode(of = {"name"})
@Table(name = "todolist")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer","handler"})
public class Todolist implements Serializable {

    @Id
    @GeneratedValue
    Long id;

    @NotEmpty(message = "Ne doti pas être Null")
    @Column(nullable = false, unique = true)
    String name;

}
