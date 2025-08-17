package com.dosgenerales.catalogoservice.service;

import com.dosgenerales.catalogoservice.dto.ProductoRequestDTO;
import com.dosgenerales.catalogoservice.dto.ProductoResponseDTO;
import com.dosgenerales.catalogoservice.model.Producto;
import com.dosgenerales.catalogoservice.repository.ProductoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<ProductoResponseDTO> findAll() {
        return productoRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ProductoResponseDTO findById(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con id: " + id));
        return convertToDto(producto);
    }

    public ProductoResponseDTO save(ProductoRequestDTO requestDTO) {
        Producto producto = new Producto();
        producto.setNombre(requestDTO.getNombre());
        producto.setDescripcion(requestDTO.getDescripcion());
        producto.setPrecio(requestDTO.getPrecio());
        producto.setStock(requestDTO.getStock());
        producto.setCategoria(requestDTO.getCategoria());
        producto.setImageUrl(requestDTO.getImageUrl());

        Producto savedProducto = productoRepository.save(producto);
        return convertToDto(savedProducto);
    }

    public ProductoResponseDTO update(Long id, ProductoRequestDTO requestDTO) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con id: " + id));

        producto.setNombre(requestDTO.getNombre());
        producto.setDescripcion(requestDTO.getDescripcion());
        producto.setPrecio(requestDTO.getPrecio());
        producto.setStock(requestDTO.getStock());
        producto.setCategoria(requestDTO.getCategoria());
        producto.setImageUrl(requestDTO.getImageUrl());

        Producto updatedProducto = productoRepository.save(producto);
        return convertToDto(updatedProducto);
    }

    public void deleteById(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new EntityNotFoundException("Producto no encontrado con id: " + id);
        }
        productoRepository.deleteById(id);
    }

    private ProductoResponseDTO convertToDto(Producto producto) {
        ProductoResponseDTO dto = new ProductoResponseDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecio(producto.getPrecio());
        dto.setStock(producto.getStock());
        dto.setCategoria(producto.getCategoria());
        dto.setImageUrl(producto.getImageUrl());
        return dto;
    }
}
