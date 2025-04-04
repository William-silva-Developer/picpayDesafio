package com.PicpaySimplificado.domain.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.web.reactive.function.client.WebClient;

import com.PicpaySimplificado.api.responseDtos.NotificationResponseDTO;
import com.PicpaySimplificado.domain.model.User;
import com.PicpaySimplificado.domain.requestDtos.NotificationResquestDTO;

import reactor.core.publisher.Mono;

@Service
public class NotificationService {

//video no 52

    @Value("${api.notify}")
    private String url;
    
    private final WebClient webClient;
    
    
    public NotificationService(WebClient webClient){
        this.webClient = webClient;
    }
    
    public void sendNotification(User user, String message) throws Exception{
    try {
        String email = user.getEmail();
        NotificationResquestDTO notificationResquestDTO = new NotificationResquestDTO(email, message);
        Mono<NotificationResponseDTO> responseMono = webClient.post()
            .uri(url)
            .bodyValue(notificationResquestDTO)
            .retrieve()
            .bodyToMono(NotificationResponseDTO.class);
            
            NotificationResponseDTO response = responseMono.block();
            System.out.println(response);
            if(!response.success().equals("ok")){
                throw new Exception("Serviço de notificação está fora do ar.");
            }
    } catch (Exception e) {
        throw new Exception("Erro ao envia notificação. "+e.getMessage());
    }
    }
    

}
