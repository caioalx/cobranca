package com.algaworks.cobranca.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.cobranca.model.StatusTitulo;
import com.algaworks.cobranca.model.Titulo;
import com.algaworks.cobranca.repository.Titulos;

@Controller
@RequestMapping("/titulos")
public class TituloController {
	
	private static final String CADASTRO_VIEW = "CadastroTitulo";
	
	@Autowired
	private Titulos titulos;
	
	@RequestMapping("/novo")
	public ModelAndView novo(){
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(new Titulo());
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Titulo titulo, Errors erros, RedirectAttributes attributes){
		if(erros.hasErrors()){
			return CADASTRO_VIEW;
		}
		
		titulos.save(titulo);
		
		attributes.addFlashAttribute("mensagem", "Título salvo com sucesso!");
		return "redirect:/titulos/novo";
	}
	
	@RequestMapping
	public ModelAndView pesquisar(){
		ModelAndView mv = new ModelAndView("PesquisaTitulos");
		List<Titulo> lista = titulos.findAll();
		mv.addObject("titulos", lista);
		return mv;
	}
	
	@RequestMapping("{codigo}")
	public ModelAndView edicao(@PathVariable("codigo") Titulo titulo){
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(titulo);
		return mv;
	}
	
	@ModelAttribute("todosStatusTitulo")
	public List<StatusTitulo> todoStatusTitulo(){
		return Arrays.asList(StatusTitulo.values());
	}
	
}
