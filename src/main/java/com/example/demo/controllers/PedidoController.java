package com.example.demo.controller;

import com.example.demo.model.Pedido;
import com.example.demo.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @GetMapping
    public List<Pedido> listar() {
        return pedidoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Pedido buscar(@PathVariable String id) {
        return pedidoRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Pedido criar(@RequestBody Pedido pedido) {
        return pedidoRepository.save(pedido);
    }
}
