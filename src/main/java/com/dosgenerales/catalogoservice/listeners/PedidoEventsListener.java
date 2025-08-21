package com.dosgenerales.catalogoservice.listeners;

import com.dosgenerales.catalogoservice.dto.PedidoPagadoEvent;
import com.dosgenerales.catalogoservice.model.Producto;
import com.dosgenerales.catalogoservice.repository.ProductoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PedidoEventsListener {

    private final ProductoRepository productoRepository;

    @Transactional
    @KafkaListener(topics = "${app.kafka.topic.pedidos-pagados}", groupId = "catalogo-service-group")
    public void handlePedidoPagadoEvent(PedidoPagadoEvent event) {
        log.info("Evento de pedido pagado recibido para pedidoId: {}", event.getPedidoId());

        event.getProductoVendidos().forEach(productoVendido -> {
            log.info("Actualizando stock para productoId: {} | Cantidad vendida: {}",
                    productoVendido.getProductoId(), productoVendido.getCantidad());

            Producto producto = productoRepository.findById(productoVendido.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado para el evento"));

            int nuevoStock = producto.getStock() - productoVendido.getCantidad();
            if (nuevoStock < 0) {
                log.error("¡SOBREVENTA! Stock insuficiente para productoId: {}", producto.getId());
            }

            producto.setStock(nuevoStock);
            productoRepository.save(producto);
        });

        log.info("Stock actualizado correctamente para pedidoId: {}", event.getPedidoId());
    }
}
