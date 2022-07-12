package io.github.geancarloslc;

import io.github.geancarloslc.domain.entity.Cliente;
import io.github.geancarloslc.domain.repositorio.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class VendasApplication {

    @Bean
    public CommandLineRunner init(@Autowired Clientes clientes){
        return args ->{

            clientes.salvar(new Cliente("Gean"));
            clientes.salvar(new Cliente("Leandro"));

            System.out.println("Listando todos os Clientes Salvos");
            List<Cliente> listaClientes = clientes.obterTodos();
            listaClientes.forEach(System.out::println);

            //Lambda
            listaClientes.forEach(cliente -> {
                cliente.setNome(cliente.getNome() + " atualizado");
                clientes.atualizar(cliente);
            });

            System.out.println("Buscando o Cliente Especifico");
            clientes.buscarPorNome("Ge").forEach(System.out::println);

            System.out.println("Listando todos os Clientes Atualizados");
            listaClientes = clientes.obterTodos();
            listaClientes.forEach(System.out::println);

            //Deletando todos os clientes
            List<Cliente> listaClienteAtualizados = clientes.obterTodos();
            listaClienteAtualizados.forEach(cliente -> {
                clientes.deletar(cliente.getId());
            });

            listaClienteAtualizados = clientes.obterTodos();
            if(listaClienteAtualizados != null && !listaClienteAtualizados.isEmpty()){
                listaClienteAtualizados.forEach(System.out::println);
            } else {
                System.out.println("Todos os clientes foram deletados");
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}
