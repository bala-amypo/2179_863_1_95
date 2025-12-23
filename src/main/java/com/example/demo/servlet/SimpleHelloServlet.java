package com.example.demo.servlet;

import jakarta.servlet.*;

@WebServlet("hello-servlet")
public class SimpleHelloServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException{

        res.setStatus(HttpServletResponse.SC_OK);
        res.setContentType("text/plain");
        res.getWriter().write("Hello from Simple Servlet");
        }
    
    @Overrride
    protected void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException{
        res.setStatus(HttpServletResponse.SC_OK);
    }



}