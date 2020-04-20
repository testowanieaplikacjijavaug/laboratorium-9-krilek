package laboratorium.krilek;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class MessengerTest {
    private Messenger sut;
    private TemplateEngine templateEngineMock;
    private MailServer mailServerMock;
    private Template templateMock;
    private Client clientMock;

    @BeforeEach
    public void setup(){
        templateEngineMock = mock(TemplateEngine.class);
        mailServerMock = mock(MailServer.class);
        templateMock = mock(Template.class);
        clientMock = mock(Client.class);
        sut = new Messenger(this.mailServerMock, this.templateEngineMock);
    }

    @Test
    public void sendMessageNullClient(){
        assertThrows(IllegalArgumentException.class, () -> this.sut.sendMessage(null, this.templateMock));
    }
    @Test
    public void sendMessageNullTemplate(){
        assertThrows(IllegalArgumentException.class, () -> this.sut.sendMessage(this.clientMock, null));
    }

    @Test
    public void sendMessageCheckMailContent(){
        ArgumentCaptor<String> emailArgument = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> contentArgument = ArgumentCaptor.forClass(String.class);
        String messageContent = "Content";
        when(templateEngineMock.prepareMessage(any(Template.class), any(Client.class))).thenReturn(messageContent);

        this.sut.sendMessage(this.clientMock, this.templateMock);

        verify(templateEngineMock).prepareMessage(any(Template.class), any(Client.class));
        verify(mailServerMock).send(emailArgument.capture(), contentArgument.capture());
        assertEquals(messageContent, contentArgument.getValue());
    }

    @Test
    public void sendMessageCheckMailClientEmail(){
        ArgumentCaptor<String> emailArgument = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> contentArgument = ArgumentCaptor.forClass(String.class);
        String messageContent = "Content";
        String clientEmail = "email@lol.com";
        when(templateEngineMock.prepareMessage(any(Template.class), any(Client.class))).thenReturn(messageContent);
        when(clientMock.getEmail()).thenReturn(clientEmail);

        this.sut.sendMessage(this.clientMock, this.templateMock);

        verify(templateEngineMock).prepareMessage(any(Template.class), any(Client.class));
        verify(mailServerMock).send(emailArgument.capture(), contentArgument.capture());
        assertEquals(clientEmail, emailArgument.getValue());
    }

    @Test
    public void sendMessageCheckPrepareMessageGetsParameters(){
        String getEmailValue = "email@test.com";
        when(this.clientMock.getEmail()).thenReturn(getEmailValue);
        ArgumentCaptor<Template> templateArgument = ArgumentCaptor.forClass(Template.class);
        ArgumentCaptor<Client> clientArgument = ArgumentCaptor.forClass(Client.class);

        this.sut.sendMessage(this.clientMock, this.templateMock);

        verify(templateEngineMock).prepareMessage(templateArgument.capture(), clientArgument.capture());
        assertAll(() -> {
            assertEquals(getEmailValue, clientArgument.getValue().getEmail());
            assertEquals(this.templateMock, templateArgument.getValue());
        });
    }
}
