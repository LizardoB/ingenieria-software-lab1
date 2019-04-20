package gt.edu.umg.ingenieria.sistemas.laboratorio1.service;

import gt.edu.umg.ingenieria.sistemas.laboratorio1.dao.ClientRepository;
import gt.edu.umg.ingenieria.sistemas.laboratorio1.model.Client;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    @Autowired
    private ClientRepository clientRepository;

    public String generarReporteClientes() throws IOException {
        
        String result = "";
        
        List<Client> lista = (List<Client>) this.clientRepository.findAll();
        StringBuilder c1 = new StringBuilder();

        c1.append("<!DOCTYPE html>\n" +
        "        <html>\n" +
        "        <head>\n" +
        "        <style>\n" +
        "        table {\n" +
        "          font-family: arial, sans-serif;\n" +
        "          border-collapse: collapse;\n" +
        "          width: 100%;\n" +
        "        }\n" +
        "\n" +
        "        td, th {\n" +
        "          border: 1px solid #dddddd;\n" +
        "          text-align: left;\n" +
        "          padding: 8px;\n" +
        "        }" +
        "n" +
        "\n" +
        "        tr:nth-child(even) {\n" +
        "          background-color: #dddddd;\n" +
        "        }\n" +
        "        </style>\n" +
        "        </head>\n" +
        "        <body>\n" +
        "\n" +
        "        <h2>Reporte de Clientes</h2>");
        
        c1.append("<table>\n" +
        "          <tr>\n" +
        "            <th>ID</th>\n" +
        "            <th>First Name</th>\n" +
        "            <th>Last Name</th>\n" +
        "            <th>NIT</th>\n" +
        "            <th>Phone</th>\n" +
        "            <th>Address</th>\n" +
        "            <th>Age</th>\n" +
        "          </tr>");
     
        
        for (Client client1 : lista) {
            c1.append("<tr>");
            c1.append("<td>");
            c1.append(client1.getId());
            c1.append("</td>");
            c1.append("<td>");
            c1.append(client1.getFirstName());
            c1.append("</td>");
            c1.append("<td>");
            c1.append(client1.getLastName());
            c1.append("</td>");
            c1.append("<td>");
            c1.append(client1.getNit());
            c1.append("</td>");
            c1.append("<td>");
            c1.append(client1.getPhone());
            c1.append("</td>");
            c1.append("<td>");
            c1.append(client1.getAddress());
            c1.append("</td>");
            c1.append("<td>");
            c1.append(client1.getAge());
            c1.append("</td>");
            c1.append("</tr>");
        }

        c1.append("</table>\n" +
        "\n" +
        "        </body>\n" +
        "        </html>");
        
     
        File dir = new File("/var/www");
        List<String> ficheros = Arrays.asList(dir.list());
  
        Collections.sort(ficheros, new Comparator<String>() {
            public int compare(String o1, String o2) {
                return extractInt(o1) - extractInt(o2);
            }

            int extractInt(String s) {
                String num = s.replaceAll("\\D", "");
                return num.isEmpty() ? 0 : Integer.parseInt(num);
            }
        });
      
        String palabra = "";
        if (ficheros.isEmpty())
           palabra = "Clientes_1.html";
        else
           palabra = ficheros.get(ficheros.size()-1);

        char[] caracteres = palabra.toCharArray();
        String p = "";
        int cont = 0;

        for (int i = 0; i < caracteres.length; i++) {
            if(Character.isDigit(caracteres[i])){
                p += caracteres[i];
            }
        }

        if (ficheros.isEmpty() == false)
            cont = Integer.parseInt(p)+1;
        else
            cont = 0+1;

  
        String ruta2 = "/var/www/Clientes_"+cont+".html";
        File archivo2 = new File(ruta2);
        BufferedWriter bw;

        bw = new BufferedWriter(new FileWriter(archivo2));
        bw.write(c1.toString());

        
        bw.close();

        result = "El reporte /var/www/Clientes_"+cont+".html ha sido generado.";
        return result;
    }

}
