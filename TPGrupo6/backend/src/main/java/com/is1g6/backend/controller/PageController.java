package com.is1g6.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/")
    public String entryPage() {
        return "home";
    }

    @GetMapping("/register_form")
    public String registerForm() {
        return "register-form";
    }

    @GetMapping("/pedido")
    public String pedidosUserPage() {
        return "pedidos_user";
    }
    @GetMapping("/lista_pedidos")
    public String pedidosPage() {
        return "lista_pedidos";
    }
    @GetMapping("/stocks")
    public String stocksPage() {
        return "stocks";
    }
    @GetMapping("/product")
    public String productPage() {
        return "products";
    }
    @GetMapping("/product_create")
    public String createProductPage() {
        return "product_create";
    }
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }
    @GetMapping("/home")
    public String homePage() {
        return "home";
    }
    @GetMapping("/validator")
    public String validatorsPage() {
        return "validaciones";
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "forgot-password";
    }

    @GetMapping("/reset-password")
    public String resetPasswordPage() {
        return "reset-password";
    }
}
