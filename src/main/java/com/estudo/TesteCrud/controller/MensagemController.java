package com.estudo.TesteCrud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.estudo.TesteCrud.model.MensagemModel;
import com.estudo.TesteCrud.repository.MensagemRepository;

import jakarta.validation.Valid;

@Controller
public class MensagemController {

    @Autowired
    MensagemRepository mr;

    @RequestMapping(value="/") //mostra todas as mensagens do bd
    public ModelAndView allMsg(){
        ModelAndView mv = new ModelAndView("/msg/index");
        mv.addObject("msg", mr.findAll());
        return mv;
    }

    @RequestMapping(value="/novaMensagem", method=RequestMethod.GET) // redireciona para o msgAdd
    public String getAddMsg(){
        return "msg/msgAdd";
    }

    @RequestMapping(value="/novaMensagem",method=RequestMethod.POST) // cria uma nova mensagem
    public String postAddMsg(@Valid MensagemModel msg, BindingResult result){
        mr.save(msg);
        return"redirect:/";
    }

    @RequestMapping(value="/editarMensagem/{id}",method=RequestMethod.GET) // redireciona para o msgEdit
    public ModelAndView getEditMsg(@PathVariable("id") long id){
        System.out.println("*\nid: "+id+"\n*");
        ModelAndView mv = new ModelAndView("/msg/msgEdit");
        mv.addObject("msg", mr.findById(id));
        return mv;
    }
    @RequestMapping(value="/editarMensagem",method=RequestMethod.POST) // edita a mensagem
    public String postEditMsg(@Valid MensagemModel msg, BindingResult result){
        MensagemModel bdMsg = mr.findById(msg.getId()).get();
        bdMsg.setMensagem(msg.getMensagem());
        bdMsg.setTitulo(msg.getTitulo());
        bdMsg.setData(msg.getData());
        mr.save(bdMsg);
        return "redirect:/";
    }
}
