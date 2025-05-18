package com.example.demo.controller;

import com.example.demo.model.Pedido;
import com.example.demo.repositories.PedidoRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import com.example.demo.models.UserModel;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private UserRepository userRepository;

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
    
    @PutMapping("/update/{id}")
    public ResponseEntity<?> atualizarPedido(@PathVariable String id, @RequestBody Pedido pedidoAtualizado) {
        return pedidoRepository.findById(id)
            .map(pedidoExistente -> {
                pedidoExistente.setFrete(pedidoAtualizado.getFrete());
                pedidoExistente.setEnderecoEntrega(pedidoAtualizado.getEnderecoEntrega());
                pedidoExistente.setFormaPagamento(pedidoAtualizado.getFormaPagamento());
                pedidoExistente.setItens(pedidoAtualizado.getItens());
                pedidoExistente.setUserId(pedidoAtualizado.getUserId());
                pedidoExistente.setStatus(pedidoAtualizado.getStatus());
                pedidoExistente.setTotal(pedidoAtualizado.getTotal());
                pedidoExistente.setData(pedidoAtualizado.getData());

                pedidoRepository.save(pedidoExistente);
                return ResponseEntity.ok(pedidoExistente);
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/pedidos/email/{email}")
    public ResponseEntity<List<Pedido>> buscarPedidosPorEmail(@PathVariable String email) {
        Optional<UserModel> user = userRepository.findByEmail(email);
        if (user.isEmpty()) return ResponseEntity.notFound().build();
        List<Pedido> pedidos = pedidoRepository.findByUserId(user.get().getId());
        return ResponseEntity.ok(pedidos);
    }

}


