package ada.wm2.CRUDOperation.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagesConfig {

    @Bean
    public String message1(){
        return new String("Welcome To Course Page");
    }

    @Bean
    public String message2(){
        return new String("Welcome To Student Page");
    }
}
