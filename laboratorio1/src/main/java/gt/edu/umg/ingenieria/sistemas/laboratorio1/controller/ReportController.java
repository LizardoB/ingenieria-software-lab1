package gt.edu.umg.ingenieria.sistemas.laboratorio1.controller;

import gt.edu.umg.ingenieria.sistemas.laboratorio1.service.ReportService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clientes")
public class ReportController {
    
    @Autowired
    private ReportService reportService;
    
    @GetMapping("generarReporteClientes")
    public String getLitas() throws IOException {
        return this.reportService.generarReporteClientes();
    }
    
    
}
