package com.projeto.luispaf.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.luispaf.model.Caixa;
import com.projeto.luispaf.model.PedidoVendido;
import com.projeto.luispaf.model.DTO.FiltroDTO;
import com.projeto.luispaf.model.DTO.RelatorioPorPeriodoDTO;
import com.projeto.luispaf.service.impl.CaixaServiceImpl;
import com.projeto.luispaf.service.impl.PacoteVendidoServiceImpl;
import com.projeto.luispaf.service.impl.PedidoVendidoServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/caixa")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CaixaController {
	
	@Autowired
	CaixaServiceImpl caixaServiceImpl;
	@Autowired
	PedidoVendidoServiceImpl pedidoVendidoServiceImpl;
	@Autowired
	PacoteVendidoServiceImpl pacoteVendidoServiceImpl;
	
	@PostMapping("/salvar")
	public ResponseEntity<?> salvar(@RequestBody Caixa caixa) throws Exception {
		Long qtdeCaixaDoDia = caixaServiceImpl.getQtdeCaixaCriadoDataAtual();
		Long qtdeCaixaAbertoOutrasDatas = caixaServiceImpl.getQtdeCaixaAbertoOutrasDatas();
		
		if (qtdeCaixaAbertoOutrasDatas == 0) {
			if (qtdeCaixaDoDia == 0) {
				Caixa caixaSalvo = caixaServiceImpl.salvar(caixa);
				return new ResponseEntity<>(caixaSalvo, HttpStatus.OK);
			} 
			// Apenas um caixa pode ser aberto por dia
			return new ResponseEntity<String>("Já existe um caixa criado para a data atual!", HttpStatus.EXPECTATION_FAILED);
		} 			
		// Se tiver caixa aberto de outras datas, não pode abrir um novo caixa enquanto não o fechar
		return new ResponseEntity<String>("Já existe um caixa aberto de outra data, favor fechar", HttpStatus.EXPECTATION_FAILED);
	}
	
	@PostMapping("/fecharCaixaDiasAnteriores")
	public ResponseEntity<?> fecharCaixaDiasAnteriores(@RequestBody Caixa caixa) throws Exception {
		try {
			
			// Fecha todos os pedidos em aberto com a mesma data de abertura do caixa pendente
			List<PedidoVendido> pedidosAbertos = pedidoVendidoServiceImpl.listarPedidosAberto();			
			pedidosAbertos.forEach(pedv -> {
				pedv.setStatus("RECEBIDO");
				pedv.setDataCriacao(caixa.getDataAbertura());
				pedv.setDataFinalizacao(caixa.getDataAbertura());				
				pedidoVendidoServiceImpl.salvar(pedv);				
			});
			
			Double totalPedidosvendido = caixaServiceImpl.getTotalproduto(caixa.getDataAbertura());
			Double totalPacoteVendido = caixaServiceImpl.getTotalPacote(caixa.getDataAbertura());
			Double totalCaixa = totalPedidosvendido + totalPacoteVendido;
			
			caixa.setTotalProduto(totalPedidosvendido);
			caixa.setTotalPacote(totalPacoteVendido);
			caixa.setTotalCaixa(totalCaixa);
			Caixa caixaSalvo = caixaServiceImpl.salvar(caixa);
			return new ResponseEntity<>(caixaSalvo, HttpStatus.OK);			
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
		}		
	}
	
	@PostMapping("/fecharCaixaDoDia")
	public ResponseEntity<?> fecharCaixaDoDia(@RequestBody Caixa caixa) throws Exception {
		
		if (pedidoVendidoServiceImpl.getQtdePedidosAbertos() > 0) {
			// Não deixa fexar o caixa caso tenha alguma venda ainda em aberto
			return new ResponseEntity<String>("Existe venda em aberto, favor fechar", HttpStatus.EXPECTATION_FAILED);
		}
		Caixa caixaSalvo = caixaServiceImpl.salvar(caixa);
		return new ResponseEntity<>(caixaSalvo, HttpStatus.OK);
	}
	
	@GetMapping("/totalPacoteDoDia")
	public ResponseEntity<?> getTotalPacoteDoDia() throws Exception{
		Double totalPacoteDia = caixaServiceImpl.getTotalPacoteDoDia();
		return new ResponseEntity<>(totalPacoteDia, HttpStatus.OK);
	}
	
	@GetMapping("/totalprodutoDoDia")
	public ResponseEntity<?> getTotalprodutoDoDia() throws Exception{
		Double totalPacoteDia = caixaServiceImpl.getTotalprodutoDoDia();
		return new ResponseEntity<>(totalPacoteDia, HttpStatus.OK);
	}
	
	@GetMapping("/totalcaixa")
	public ResponseEntity<?> getTotalcaixaDoDia() throws Exception{
		Double totalPacoteDia = caixaServiceImpl.getTotalCaixaDoDia();
		return new ResponseEntity<>(totalPacoteDia, HttpStatus.OK);
	}
		
	@GetMapping("/caixasBertoDatasAnteriores")
	public ResponseEntity<?> getCaixasBertoDatasAnteriores() throws Exception{
		List<Caixa> lista = caixaServiceImpl.getCaixasBertoDatasAnteriores();
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}	
	
	@GetMapping("/caixaAbertoDoDia")
	public ResponseEntity<?> getCaixaAbertoDoDia() throws Exception{
		try {
			Caixa caixa = caixaServiceImpl.getCaixaAbertoDoDia();
			return new ResponseEntity<>(caixa, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Falha ao buscar caixa aberto do dia:" + e.getMessage(), HttpStatus.EXPECTATION_FAILED);
		}		
	}	
	
	
	@PostMapping("/relatorioPorPeriodo")
	public ResponseEntity<?> relatorioPorPeriodo(@RequestBody FiltroDTO filtro) throws Exception {
		try {			
			Long qtdeProdutoVendido = 0l;
			Long qtdePacoteVendido = 0l;			
			Double totalproduto = 0d;
			Double totalpacote =  0d;
			Double totalcaixa =  0d;	
			
			if (filtro.getCodigoCliente() == null) {
				qtdeProdutoVendido = pedidoVendidoServiceImpl.getTotalProdutoVendidoPorPeriodo(filtro.getDataInicio(), filtro.getDataFim());
				qtdePacoteVendido = pacoteVendidoServiceImpl.getTotalPacoteVendidoPorPeriodo(filtro.getDataInicio(), filtro.getDataFim());
			    totalproduto = caixaServiceImpl.getTotalprodutoPorPeriodo(filtro.getDataInicio(), filtro.getDataFim());
				totalpacote = caixaServiceImpl.getTotalpacotePorPeriodo(filtro.getDataInicio(), filtro.getDataFim());
				totalcaixa = caixaServiceImpl.getTotalcaixaPorPeriodo(filtro.getDataInicio(), filtro.getDataFim());				
				RelatorioPorPeriodoDTO ret = new RelatorioPorPeriodoDTO(totalproduto, totalpacote, totalcaixa, qtdePacoteVendido, qtdeProdutoVendido);
				return new ResponseEntity<List<RelatorioPorPeriodoDTO>>(List.of(ret), HttpStatus.OK);
			} else {
				
				qtdeProdutoVendido = pedidoVendidoServiceImpl.getQtdePedidoPorClienteEPeriodo(filtro.getDataInicio(), filtro.getDataFim(), filtro.getCodigoCliente());
				qtdePacoteVendido = pacoteVendidoServiceImpl.getQtdePacotePorClienteEPeriodo(filtro.getDataInicio(), filtro.getDataFim(), filtro.getCodigoCliente());
				totalproduto = pedidoVendidoServiceImpl.getValorPedidoPorClienteEPeriodo(filtro.getDataInicio(), filtro.getDataFim(), filtro.getCodigoCliente());
				totalpacote = pacoteVendidoServiceImpl.getValorPacotePorClienteEPeriodo(filtro.getDataInicio(), filtro.getDataFim(), filtro.getCodigoCliente());
				totalcaixa = totalproduto + totalpacote;
				RelatorioPorPeriodoDTO ret = new RelatorioPorPeriodoDTO(totalproduto, totalpacote, totalcaixa, qtdePacoteVendido, qtdeProdutoVendido);
				return new ResponseEntity<List<RelatorioPorPeriodoDTO>>(List.of(ret), HttpStatus.OK);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<String>("Falha ao gerar relatorioPorPeriodo:" + e.getMessage(), HttpStatus.EXPECTATION_FAILED);
		}		
	}
	
	
	@GetMapping("/getDataAtual")
	public ResponseEntity<?> getDataAtual() throws Exception{		
		Date dataAtual = caixaServiceImpl.getDataAtual();
		SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String stringDate = DateFor.format(dataAtual);
		return new ResponseEntity<String>(stringDate, HttpStatus.OK);
	}
}
