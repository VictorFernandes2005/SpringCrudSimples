package com.estudo.TesteCrud.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String getAddMsg(Model model){
        MensagemModel msg = new MensagemModel();
        model.addAttribute("msg", msg);
        return "msg/msgAdd";
    }

    @RequestMapping(value="/novaMensagem",method=RequestMethod.POST) // cria uma nova mensagem
    public String postAddMsg(@Valid MensagemModel msg, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("msg",msg);
            model.addAttribute("error", "Erro! Um ou mais campos de textos inválidos!");
            return "msg/msgAdd";
        }
        mr.save(msg);
        return"redirect:/";
    }

    @RequestMapping(value="/editarMensagem/{id}",method=RequestMethod.GET) // redireciona para o msgEdit
    public ModelAndView getEditMsg(@PathVariable("id") long id){
        ModelAndView mv = new ModelAndView("/msg/msgEdit");
        Optional<MensagemModel> msg = mr.findById(id);
        if(!msg.isPresent()){
            mv.addObject("error", "Não existe nenhuma mensagem com o id inserido!");
            return mv;
        }
        mv.addObject("msg", msg);
        return mv;
    }
    @RequestMapping(value="/editarMensagem",method=RequestMethod.POST) // edita a mensagem
    public String postEditMsg(@Valid MensagemModel msg, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("msg",msg);
            model.addAttribute("error", "Erro! Um ou mais campos de textos inválidos!");
            return "msg/msgEdit";
        }
        MensagemModel bdMsg = mr.findById(msg.getId()).get();
        bdMsg.setMensagem(msg.getMensagem());
        bdMsg.setTitulo(msg.getTitulo());
        bdMsg.setData(msg.getData());
        mr.save(bdMsg);
        return "redirect:/";
    }

    @RequestMapping(value="/deletarMensagem/{id}",method=RequestMethod.GET) // redireciona para o msgDel
    public ModelAndView getDelMsg(@PathVariable("id") long id){
        ModelAndView mv = new ModelAndView("/msg/msgDel");
        Optional<MensagemModel> msg = mr.findById(id);
        if(!msg.isPresent()){
            mv.addObject("error", "Não existe nenhuma mensagem com o id inserido!");
            return mv;
        }
        mv.addObject("titulo", msg.get().getTitulo());
        mv.addObject("mensagem", msg.get().getMensagem());
        mv.addObject("data", msg.get().getData());
        mv.addObject("msg", msg);
        return mv;
    }
    @RequestMapping(value="/deletarMensagem",method=RequestMethod.POST) // deleta a mensagem
    public String postDelMsg(@Valid MensagemModel msg){
        mr.deleteById(msg.getId());
        return "redirect:/";
    }
}
