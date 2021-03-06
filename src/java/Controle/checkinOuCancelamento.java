package Controle;

import Modelo.Passagem;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Júnior
 */
@WebServlet(name = "checkinOuCancelamento", urlPatterns = {"/checkinOuCancelamento"})
public class checkinOuCancelamento extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String cpf = ((Modelo.Cliente) request.getSession().getAttribute("clienteAutenticado")).getCpf();
        Passagem p = new Passagem();
        p.setCodigo(Integer.parseInt(request.getParameter("codPassagem")));
        if(p.getFromDb()){
            if(cpf.equals(p.getCliente().getCpf())){
                if(p.isCheckin() || p.isCancelada()){
                    request.setAttribute("VaiDa", "Checkin já foi feito ou a passagem foi cancelada");
                    System.out.println("Checkin já foi feito");
                }
                else if(request.getParameter("submit").equals("checkin")){
                    if(p.checkin()){
                        request.setAttribute("VaiDa", "Que não vai dá oq");
                        System.out.println("Deu bom");
                    }
                    else{
                        request.setAttribute("VaiDa", "Não vai dá não");
                        System.out.println("Deu ruim");
                    }
                }
                else{
                    if(p.cancelar()){
                        request.setAttribute("VaiDa", "Que não vai dá oq");
                        System.out.println("Deu bom");
                    }
                    else{
                        request.setAttribute("VaiDa", "Não vai dá não");
                        System.out.println("Deu ruim");
                    }
                }
            }
            else{
                request.setAttribute("VaiDa", "Essa Passagem pertence a outro cliente");
                System.out.println("Essa Passagem pertence a outro cliente");
            }
        }
        else{
            request.setAttribute("VaiDa", "Não vai dá não");
            System.out.println("Deu ruim");
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("checkinOuCancelamento.jsp");
        dispatcher.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
