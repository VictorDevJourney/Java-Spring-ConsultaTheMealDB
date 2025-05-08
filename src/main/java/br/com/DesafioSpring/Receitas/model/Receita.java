package br.com.DesafioSpring.Receitas.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Receita(
        @JsonAlias("idMeal") String id,
        @JsonAlias("strMeal") String nome,
        @JsonAlias("strCategory") String categoria,
        @JsonAlias("strArea") String area,
        @JsonAlias("strInstructions") String instrucoes) {
}