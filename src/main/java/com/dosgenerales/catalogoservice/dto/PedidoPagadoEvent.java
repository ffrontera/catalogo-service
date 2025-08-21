package com.dosgenerales.catalogoservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoPagadoEvent {
    private Long  pedidoId;
    private List<ProductoVendidoDTO> productoVendidos;
}
