package com.ConsultaAutomovel.ConsultaAutomovel.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Veiculo(

        @JsonAlias("Valor") String valor ,
        @JsonAlias ("Marca") String marca,
        @JsonAlias ("Modelo")String modelo,
        @JsonAlias ("AnoModelo")Integer data,
        @JsonAlias ("Combustivel")String tipoCombustivel

) {
}
