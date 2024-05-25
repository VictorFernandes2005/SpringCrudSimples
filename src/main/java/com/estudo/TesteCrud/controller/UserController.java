package com.estudo.TesteCrud.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.estudo.TesteCrud.model.MensagemModel;
import com.estudo.TesteCrud.model.UserModel;
import com.estudo.TesteCrud.repository.MensagemRepository;
import com.estudo.TesteCrud.repository.UserRepository;

import jakarta.validation.Valid;

@Controller // defini para o spring que essa classe é um controlador
public class UserController {

    @Autowired
    private UserRepository userR;
    private MensagemRepository msgR;

    // Cadastrar novo User
    @RequestMapping(value = "/Cadastrar", method = RequestMethod.GET)
    public String addUser(){
        return"user/userForm";
    }

    @RequestMapping(value = "/Cadastrar", method = RequestMethod.POST)
    public String addUser(@Valid UserModel user, BindingResult result){
        if(result.hasErrors()){return"deu_pau";}
        System.out.println(userR==null);
        userR.save(user);
        return "redirect:/Cadastrar";
    }

    // Listar Users
    @RequestMapping(value = "/AllUsers")
    public ModelAndView listaUsers(){
        ModelAndView mv = new ModelAndView("user/listaUser");
        Iterable<UserModel>users = userR.findAll();
        mv.addObject("AllUsers", users);
        return mv;
    }
    // user especifico
    @RequestMapping(value = "/{username}",method = RequestMethod.GET)
    public ModelAndView descUser(@PathVariable("username") String username){
        UserModel user = userR.findByUsername(username);
        ModelAndView mv = new ModelAndView("user/listaUser");
        mv.addObject("user", user);
        Iterable<MensagemModel> msgs = msgR.findByUser(user);
        mv.addObject("mensagens", msgs);
        return mv;
    }
    //deleta vaga
    @RequestMapping(value = "/delUser")
    public String deletarUser(String username){
        UserModel user = userR.findByUsername(username);
        userR.delete(user);
        return "redirect:/user";

    }

    // adicionar mensagem

    @RequestMapping(value = "/NewMsg", method = RequestMethod.GET)
    public String addMsg(){
        return "user/msgForm";
    }
    @RequestMapping(value = "/NewMsg/{username}")
    public String addMsg(@PathVariable("username") String username, @Valid MensagemModel msg, 
                        BindingResult result, RedirectAttributes att){
        if(result.hasErrors()){
            att.addFlashAttribute("Mensgagem", "erro bobo");
            return "redirect:/NewMsg";
        }
        UserModel user = userR.findByUsername(username);
        msg.setUser(user);
        msgR.save(msg);
        att.addFlashAttribute("mensagem","viva!");
        return "redirect:/NewMsg/{username}";
    }

    // deleta mensagem

}
