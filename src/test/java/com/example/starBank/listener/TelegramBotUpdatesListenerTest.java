package com.example.starBank.listener;

import com.example.starBank.repositories.RecommendationsRepository;
import com.example.starBank.repositories.RecommendationsRuleRepository;
import com.example.starBank.services.RecommendationService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class TelegramBotUpdatesListenerTest {

    private RecommendationsRepository recommendationsRepository = mock(RecommendationsRepository.class);
    private RecommendationsRuleRepository recommendationsRuleRepository = mock(RecommendationsRuleRepository.class);
    private RecommendationService recommendationService = mock(RecommendationService.class);

    @Mock
    private TelegramBot telegramBotMock;
    @Mock
    private Update update;
    @InjectMocks
    private TelegramBotUpdatesListener out;

    @Test
    public void process_StartMessage_Test() throws URISyntaxException, IOException {
        //sources:
        Long id = 1l;
        final String userMessage = "/start";

        Update update = Mockito.mock(Update.class);
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);

        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn(userMessage);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(id);
        when(telegramBotMock.execute(any(SendMessage.class))).thenReturn(null);

        //test:
        out.process(List.of(update));
        //control:
        verify(telegramBotMock, times(1)).execute(any(SendMessage.class));
    }

    @Test
    public void process_ValidMessage_Test() throws URISyntaxException, IOException {
        //sources
        Long id = 1l;
        final String userMessage = "/recommend sheron.berge";

        Update update = Mockito.mock(Update.class);
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);

        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn(userMessage);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(id);
        when(telegramBotMock.execute(any(SendMessage.class))).thenReturn(null);

        //test:
        out.process(List.of(update));
        //control:
        verify(telegramBotMock, times(1)).execute(any(SendMessage.class));
    }

    @Test
    public void process_NotValidFormat_Test() throws URISyntaxException, IOException {
        //sources:
        Long id = 1l;
        final String userMessage = "/recommend";

        Update update = Mockito.mock(Update.class);
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);

        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn(userMessage);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(id);
        when(telegramBotMock.execute(any(SendMessage.class))).thenReturn(null);

        //test:
        out.process(List.of(update));
        //control:
        verify(telegramBotMock, times(1)).execute(any(SendMessage.class));
    }

    @Test
    public void process_NotValidUser_Test() throws URISyntaxException, IOException {
        //sources:
        Long id = 1l;
        final String userMessage = "/recommend sheron";

        Update update = Mockito.mock(Update.class);
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);

        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn(userMessage);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(id);
        when(telegramBotMock.execute(any(SendMessage.class))).thenReturn(null);

        //test:
        out.process(List.of(update));
        //control:
        verify(telegramBotMock, times(1)).execute(any(SendMessage.class));
    }
}
