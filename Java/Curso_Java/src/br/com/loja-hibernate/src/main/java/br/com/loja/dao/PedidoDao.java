package br.com.loja.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.loja.modelo.Pedido;
import br.com.loja.vo.RelatorioDeVendasVo;

public class PedidoDao {
	
	
	private EntityManager em;
	
	
	public PedidoDao (EntityManager em)
	{
		this.em = em;
	}
	
	
	public void cadastrar(Pedido pedido)
	{
		this.em.persist(pedido);
	}
	
	public BigDecimal valorTotalVendido()
	{
		String jpql = "SELECT SUM(p.valorTotal) FROM Pedido p";
		
		return em.createQuery(jpql, BigDecimal.class).getSingleResult();
	}
	
	public List<RelatorioDeVendasVo> relatorioDeVendas()
	{
		String jpql = "SELECT new br.com.loja.vo.RelatorioDeVendasVo( "
				+ "produto.nome,"
				+"SUM(item.quantidade),"
				+ "MAX(pedido.data))"
				+"FROM Pedido pedido "
				+"JOIN pedido.itens item"
				+ "JOIN item.produto produto"
				+"GROUP BY produto.nome"
				+ "ORDER BY item.quantidade DESC";
		
		return em.createQuery(jpql, RelatorioDeVendasVo.class).getResultList();
	}
	
	public Pedido buscarPedidoComCLiente(Long id)
	{
		return em.createQuery("SELECT p FROM Pedido p JOIN FETCH WHERE p.id = :id", Pedido.class).setParameter("id", id).getSingleResult();
	}
}
