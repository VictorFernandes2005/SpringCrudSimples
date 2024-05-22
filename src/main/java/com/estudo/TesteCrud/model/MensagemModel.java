package com.estudo.TesteCrud.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class MensagemModel{

    @Id @GeneratedValue(strategy = GenerationType.AUTO) // defini a variável Como uma ID auto-incrementada
    private long id;

    @NotEmpty // defini a variável como não vazia (assim como da pra fazer no mysql)
    @Column(unique = true)
    private String titulo;

    @NotEmpty
    @ManyToOne
    private UserModel user;

    @NotEmpty
    private String mensagem;

    @NotEmpty
    private String data;

}
