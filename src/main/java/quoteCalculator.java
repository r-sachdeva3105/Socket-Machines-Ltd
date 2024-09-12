import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/quote")
public class quoteCalculator extends HttpServlet {
	private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("index.html");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String socketType = request.getParameter("type");
        String quantityStr = request.getParameter("quantity");

        int quantity;
        try {
            quantity = Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Quantity must be a valid number.");
            return;
        }
        
        if (quantity < 1) {
        	response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Quantity must be greater than 0.");
            return;
        }

        double pricePerSocket = getPricePerSocket(socketType);
        double totalPrice = pricePerSocket * quantity;

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\"></head><body>");
        out.println("<h1>Quote Details</h1>");
        out.println("<p>Socket Type: " + socketType + "</p>");
        out.println("<p>Quantity: " + quantity + "</p>");
        out.println("<p>Total Price: $" + String.format("%.2f", totalPrice) + "</p>");
        out.println("</body></html>");
    }
    
    private double getPricePerSocket(String socketType) {
        switch (socketType) {
            case "A":
                return 50;
            case "B":
                return 75;
            case "C":
                return 100;
            default:
                return 0;
        }
    }

}
