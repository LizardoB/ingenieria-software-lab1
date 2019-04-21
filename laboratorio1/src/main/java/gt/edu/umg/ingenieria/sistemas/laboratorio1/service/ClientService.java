package gt.edu.umg.ingenieria.sistemas.laboratorio1.service;

import gt.edu.umg.ingenieria.sistemas.laboratorio1.dao.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import gt.edu.umg.ingenieria.sistemas.laboratorio1.model.Client;
import gt.edu.umg.ingenieria.sistemas.laboratorio1.model.ReglasException;
import java.util.List;
import jdk.nashorn.internal.ir.BreakNode;

/**
 *
 * @author Josué Barillas (jbarillas)
 */
@Service
public class ClientService {
    
    @Autowired
    private ClientRepository clientRepository;
    
    public String createClient(Client client){
        
        String result = "";
        String oldFirstName = client.getFirstName();
        String []  split = oldFirstName.split(" ");
        String oldLasttName = client.getLastName();
        String []  split2 = oldLasttName.split(" ");
        String newFirstName = "";
        String newLastName = "";
        
        try {
            
            for (int j = 0; j < split.length; j++) {
                split[j] = split[j].substring(0, 1).toUpperCase() + split[j].substring(1).toLowerCase();
                newFirstName = newFirstName + split[j] + " ";
            }
            
            for (int j = 0; j < split2.length; j++) {
                split2[j] = split2[j].substring(0, 1).toUpperCase() + split2[j].substring(1).toLowerCase();
                newLastName = newLastName + split2[j] + " ";
            }
            
            client.setFirstName(newFirstName);
            client.setLastName(newLastName);
            
            reglas(client.getAge(),client.getNit());
            
            this.clientRepository.save(client);
            result = "id = " + client.getId() + "\nfirstName = " + client.getFirstName()+ "\nlastName = " + client.getLastName() +
                     "\nnit = " + client.getNit() + "\nphone = " + client.getPhone() + "\naddress = " + client.getAddress() +
                     "\nage = " + client.getAge();
            
            return result;
            
        } catch (ReglasException e) {
            return result = e.getMessage();
        }
    }
    
    static void reglas(int num, String nit)throws ReglasException{
        
        boolean validar = false;
        
        if((num<18)){
            throw new ReglasException("Lo sentimos. El sistema solo permite el registro de usuarios mayores de edad.");
        }
        
        if(nit.matches("[0-9]*")){
            validar = true;
        }
        
        if ((nit.length()>10) || (validar == false)) {
            throw new ReglasException("NIT invalido.");
        } 
    }
    
    
    public List<Client> getAllClients(){
        return (List<Client>) this.clientRepository.findAll();
    }

    public Client getClientByNit(String nit) {
        return this.clientRepository.findClientByNit(nit);
    }
    
    public Client updateClient(Client client) {
        return this.clientRepository.save(client);        
    }
    
    public Client updateNit(Long id, String nit) {
        Client client = this.clientRepository.findById(id).get();
        client.setNit(nit);
        return this.clientRepository.save(client);
    }
    
    public Client updateNames(Long id, String firstName, String lastName) {
        Client client = this.clientRepository.findById(id).get();
        client.setFirstName(firstName);
        client.setLastName(lastName);
        return this.clientRepository.save(client);
    }
    
    public List<Client> getClientsByFNLN(String query) {
        return (List<Client>) this.clientRepository.findByFirstLast(query.replace("*","%"));
    }

}
