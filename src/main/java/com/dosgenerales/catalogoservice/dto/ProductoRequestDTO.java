package com.dosgenerales.catalogoservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductoRequestDTO {

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;

    private String descripcion;

    @Positive(message = "El precio debe ser un número positivo")
    private Double precio;

    @Positive(message = "El stock debe ser un número positivo")
    private Integer stock;

    private String categoria;

    private String imageUrl;
}
