package com.dosgenerales.catalogoservice.controller;

import com.dosgenerales.catalogoservice.model.Producto;
import com.dosgenerales.catalogoservice.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping
    public List<Producto> getAllProdictos() {
        return productoRepository.findAll();
    }

    @PostMapping
    public Producto createProducto(@RequestBody Producto producto) {
        return productoRepository.save(producto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable Long id ) {
        return productoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> updateProducto(@PathVariable Long id, @RequestBody Producto productoDetails) {
        return productoRepository.findById(id)
                .map(producto -> {
                    producto.setNombre(productoDetails.getNombre());
                    producto.setDescripcion(productoDetails.getDescripcion());
                    producto.setPrecio(productoDetails.getPrecio());
                    producto.setStock(productoDetails.getStock());
                    producto.setCategoria(productoDetails.getCategoria());
                    Producto updateProducto = productoRepository.save(producto);
                    return ResponseEntity.ok(updateProducto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProducto(@PathVariable Long id) {
        return productoRepository.findById(id)
                .map(producto -> {
                    productoRepository.delete(producto);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
