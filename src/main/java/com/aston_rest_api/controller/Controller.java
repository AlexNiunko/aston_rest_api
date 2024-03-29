package com.aston_rest_api.controller;

import java.io.*;
import java.util.Enumeration;

import com.aston_rest_api.db.ConnectionManagerImpl;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "controller", urlPatterns = "/controller")
public class Controller extends HttpServlet {

    public void init() {
        ConnectionManagerImpl connectionManager=ConnectionManagerImpl.getInstance();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {


    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

}