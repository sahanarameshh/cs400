import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class HelloWebServer {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000),0);

        HttpContext context = server.createContext("/");
        context.setHandler(exchange -> {
		    String query = exchange.getRequestURI().getQuery();
            String course_value = query.split("&")[0].split("=")[1];
            String name_value = query.split("&")[1].split("=")[1];

            String response = "Hello " + name_value +  "! <br/> I hope you are having a great " + java.time.LocalDate.now();
            System.out.println("Server Received HTTP Request");
		    
            exchange.sendResponseHeaders(200, response.length());
            exchange.getResponseHeaders().add("Content-type","text/html");

            OutputStream out = exchange.getResponseBody();
            byte[] bytes = response.getBytes();
            out.write(bytes);
            out.close();
	    });

        server.start();
	    System.out.println("Hello Web Server Running...");
    }
}