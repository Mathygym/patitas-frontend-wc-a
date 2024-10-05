package pe.edu.cibertec.patita_frontend_wc_a.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import pe.edu.cibertec.patita_frontend_wc_a.dto.LoginResponseDTO;
import pe.edu.cibertec.patita_frontend_wc_a.dto.LoginResquestDTO;
import pe.edu.cibertec.patita_frontend_wc_a.viewmodel.LoginModel;

@Controller
@RequestMapping("/login")
public class LoginControlller {

    @Autowired
    WebClient webClientAutenticacion;


    //private final RestTemplate restTemplate = new RestTemplate();
    //private final String bakcs = "http://localhost:8081/autenticacion/login";

    @GetMapping("/inicio")
    public String inicio(Model model) {
        LoginModel loginModel = new LoginModel("00", "", "");
        model.addAttribute("loginModel", loginModel);
        return "inicio";
    }

    @PostMapping("/autenticar")
    public String autenticar(@RequestParam("tipoDocumento") String tipoDocumento,
                             @RequestParam("numeroDocumento") String numeroDocumento,
                             @RequestParam("password") String password,
                             Model model) {

        //validar campos de entrada
        if (tipoDocumento == null || tipoDocumento.trim().length() == 0 ||
                numeroDocumento == null || numeroDocumento.trim().length() == 0 ||
                password == null || password.trim().length() == 0) {
            LoginModel loginModel = new LoginModel("01", "Error:debe completar los datos", "Cristopher Matias");
            model.addAttribute("loginModel", loginModel);
            return "inicio";
        }
        //invokar servicio de autenticacion 20/09/24

        // LoginModel loginModel = new LoginModel("00", "", "Cristopher Matias");
        //model.addAttribute("loginModel", loginModel);
        //return "principal";

        // Crear el DTO para enviar al backend
        //  LoginResquestDTO loginRequest = new LoginResquestDTO(tipoDocumento, numeroDocumento, password);
        //LoginResponseDTO response = restTemplate.postForObject(bakcs, loginRequest, LoginResponseDTO.class);

        //String codigo = response != null ? response.codigo() : "99";
        //String mensaje = response != null ? response.mensaje() : "Error de conexión";
        //String nombreUsuario = response != null && "00".equals(codigo) ? response.nombreUsuario() : "";

        //model.addAttribute(


        //      "loginModel", new LoginModel(codigo, mensaje, nombreUsuario));

        // Redirigir a "principal" si la autenticación es exitosa
        //return "00".equals(codigo) ? "principal" : "inicio";

        //invokar servicio de autenticacion



         try {


             LoginResquestDTO loginRequestDTO = new LoginResquestDTO(tipoDocumento, numeroDocumento, password);
             LoginResponseDTO loginResponseDTO = webClientAutenticacion.postForObject("/login", loginRequestDTO, LoginResponseDTO.class);

             if(loginResponseDTO.codigo().equals("00")){
                 LoginModel loginModel = new LoginModel("00", "", loginResponseDTO.nombreUsuario());
                 model.addAttribute("loginModel", loginModel);
                 return "principal";

             }
             else {
                 LoginModel loginModel = new LoginModel("00", "Error:fallida", "");
                 model.addAttribute("loginModel", loginModel);
                 return "inicio";
             }

         }catch (Exception e){
             LoginModel loginModel = new LoginModel("99", "Error:  Ocurrio un problema", "");
             model.addAttribute("loginModel", loginModel);
             System.out.println(e.getMessage());
             return "inicio";
         }

    }

    }

