package io.github.geancarloslc.domain.repositorio;

import io.github.geancarloslc.domain.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
//Retorna todas as exceções que ocorrem no banco traduzidas para o Java
public class Clientes {

    @Autowired
    //Interface que faz todas as operações na base de dados com as entidades mapeadas
    private EntityManager entityManager;


    @Transactional
    //EntityManager necessita de uma transação para realizar as operações
    public Cliente salvar(Cliente cliente){
        entityManager.persist(cliente);
        return cliente;
    }

    @Transactional
    //Update
    public Cliente atualizar(Cliente cliente){
        entityManager.merge(cliente);
        return cliente;
    }

    @Transactional
    public void deletar(Cliente cliente){
        if(!entityManager.contains(cliente)){
            //Sincronizando o cliente com a base
            cliente = entityManager.merge(cliente);
        }

        entityManager.remove(cliente);
    }

    @Transactional
    public void deletar(Integer id){
        Cliente cliente = entityManager.find(Cliente.class, id);
        deletar(cliente);
    }

    @Transactional(readOnly = true)
    //readOnly = true Otimiza as pesquisas, pois não guarda nada no cache
    public List<Cliente> buscarPorNome(String nome){
        String jpql = " select c from Cliente c where c.nome like :nome ";
        TypedQuery<Cliente> query = entityManager.createQuery(jpql, Cliente.class);
        query.setParameter("nome", "%" + nome + "%");
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    public List<Cliente> obterTodos(){
        List<Cliente> cliente = entityManager
                .createQuery(" from Cliente ", Cliente.class)
                .getResultList();

        return cliente;


    }

}
