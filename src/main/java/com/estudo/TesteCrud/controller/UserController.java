package com.estudo.TesteCrud.controller;

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

    private UserRepository userR;
    private MensagemRepository msgR;

    // Cadastrar novo User
    @RequestMapping(value = "/Cadastrar", method = RequestMethod.GET)
    public String form(){
        return"user/userForm";
    }

    @RequestMapping(value = "/Cadastrar", method = RequestMethod.POST)
    public String form(@Valid UserModel user, BindingResult result, RedirectAttributes att){

        if(result.hasErrors()){ // caso ocorra um erro ao cadastrar o user:
            att.addFlashAttribute("mensagem","verifique os valores inseridos.");
            return "redirect:/Cadastrar";
        }
        userR.save(user);
        att.addFlashAttribute("mensagem","User cadastrado!");
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

}
