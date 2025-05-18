package com.example.demo.controller;

import com.example.demo.model.Pedido;
import com.example.demo.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @GetMapping
    public ResponseEntity<List<Pedido>> listar() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscar(@PathVariable String id) {
        return pedidoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<Pedido>> buscarPorUsuario(@PathVariable String userId) {
        String userIdWithoutQuotes = userId.replace("\"", "");
        List<Pedido> pedidos = pedidoRepository.findByUserId(userIdWithoutQuotes);
        System.out.println(userIdWithoutQuotes);
        return ResponseEntity.ok(pedidos);
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Pedido pedido) {
        System.out.println("Pedido: " + pedido);
        System.out.println(pedido);

        try {
            Pedido saved = pedidoRepository.save(pedido);
            return ResponseEntity.ok(saved); // retorna o objeto salvo
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError()
                    .body("Erro ao salvar pedido: " + e.getMessage());
        }
    }
}


