package com.rodrigomv.edutrackbackend.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/chats")
@CrossOrigin(origins = "*")
public class ChatController {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ChatContact {
        private String id;
        private String name;
        private String avatar;
        private String role;
        private String type; // "estudiante" | "padre" | "colega"
        private boolean online;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MessageItem {
        private String id;
        private String senderId;
        private String text;
        private String timestamp;
        private boolean sentByMe;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ChatRoom {
        private String id;
        private ChatContact contact;
        private int unreadCount;
        private List<MessageItem> messages;
    }

    private static final List<ChatRoom> chatRooms = new ArrayList<>();

    static {
        // Chat 1: Valeria Castillo
        List<MessageItem> m1 = new ArrayList<>();
        m1.add(new MessageItem("m1_1", "c_valeria", "Profesor, buenas tardes. Quería hacerle una consulta sobre el trabajo final de diagramas.", "14:20", false));
        m1.add(new MessageItem("m1_2", "docente", "Hola Valeria, buenas tardes. Claro, dime cuál es tu duda.", "14:22", true));
        m1.add(new MessageItem("m1_3", "c_valeria", "¿Podemos usar la herramienta Lucidchart para la entrega, o prefiere algún formato de archivo específico?", "14:25", false));
        m1.add(new MessageItem("m1_4", "docente", "Sí, Lucidchart está excelente. Solo recuerden exportarlo como PDF para adjuntarlo en la plataforma Edutrack.", "14:28", true));
        m1.add(new MessageItem("m1_5", "c_valeria", "Perfecto profesor, muchas gracias por la aclaración. Que tenga buen día.", "14:30", false));
        chatRooms.add(new ChatRoom("chat1", new ChatContact("c_valeria", "Valeria Castillo", "", "Estudiante • Ingeniería de Software", "estudiante", true), 0, m1));

        // Chat 2: Carlos Mendoza
        List<MessageItem> m2 = new ArrayList<>();
        m2.add(new MessageItem("m2_1", "docente", "Carlos, he notado que has faltado a las últimas clases de Ingeniería de Software. ¿Todo bien?", "Ayer, 09:00", true));
        m2.add(new MessageItem("m2_2", "c_carlos", "Estimado docente, disculpe. Tuve un contratiempo familiar de salud.", "10:15", false));
        m2.add(new MessageItem("m2_3", "c_carlos", "Quería saber si puedo enviarle por aquí los justificantes médicos para no perder la nota del taller.", "10:16", false));
        chatRooms.add(new ChatRoom("chat2", new ChatContact("c_carlos", "Carlos Mendoza", "", "Estudiante • Ingeniería de Software", "estudiante", false), 2, m2));

        // Chat 3: Luis Mendoza (Padre)
        List<MessageItem> m3 = new ArrayList<>();
        m3.add(new MessageItem("m3_1", "c_luis_padre", "Buenas tardes Dr. Roberto, le escribo para coordinar una cita con usted sobre la situación académica de mi hijo Carlos.", "Ayer, 16:30", false));
        m3.add(new MessageItem("m3_2", "docente", "Estimado Luis, un gusto saludarlo. Por supuesto, podemos reunirnos virtualmente o en cubículo este viernes a las 11:00 AM.", "Ayer, 17:00", true));
        m3.add(new MessageItem("m3_3", "c_luis_padre", "Excelente, el viernes a las 11:00 AM me viene genial. ¿Me podría enviar el enlace de Zoom por este medio?", "Ayer, 17:15", false));
        m3.add(new MessageItem("m3_4", "docente", "Perfecto. Le estaré enviando la invitación de Teams o Zoom el día jueves por la tarde. Quedamos en eso.", "Ayer, 17:30", true));
        chatRooms.add(new ChatRoom("chat3", new ChatContact("c_luis_padre", "Luis Mendoza (Padre)", "", "Padre de Carlos Mendoza", "padre", true), 0, m3));

        // Chat 4: Dra. Ana María Silva
        List<MessageItem> m4 = new ArrayList<>();
        m4.add(new MessageItem("m4_1", "c_ana_colega", "Roberto, ¿tienes a la mano el temario actualizado del módulo de patrones de diseño?", "Lunes, 11:12", false));
        m4.add(new MessageItem("m4_2", "docente", "Hola Ana. Sí, dame unos minutos y te lo comparto en la carpeta del drive compartido de la facultad.", "Lunes, 11:20", true));
        m4.add(new MessageItem("m4_3", "c_ana_colega", "Muchas gracias, me servirá para coordinar la práctica integradora del mes.", "Lunes, 11:22", false));
        chatRooms.add(new ChatRoom("chat4", new ChatContact("c_ana_colega", "Dra. Ana María Silva", "", "Colega • Docente de Redes I", "colega", true), 0, m4));
    }

    @GetMapping
    public ResponseEntity<List<ChatRoom>> getChats() {
        return ResponseEntity.ok(chatRooms);
    }

    @GetMapping("/{chatId}/messages")
    public ResponseEntity<List<MessageItem>> getMessages(@PathVariable String chatId) {
        for (ChatRoom room : chatRooms) {
            if (room.getId().equals(chatId)) {
                room.setUnreadCount(0); // Mark as read
                return ResponseEntity.ok(room.getMessages());
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{chatId}/messages")
    public ResponseEntity<MessageItem> sendMessage(
            @PathVariable String chatId,
            @RequestBody MessageItem newMessage
    ) {
        for (ChatRoom room : chatRooms) {
            if (room.getId().equals(chatId)) {
                newMessage.setId("msg_sent_" + System.currentTimeMillis());
                newMessage.setSenderId("docente");
                newMessage.setSentByMe(true);
                room.getMessages().add(newMessage);
                return ResponseEntity.ok(newMessage);
            }
        }
        return ResponseEntity.notFound().build();
    }
}
