package laboratorium.krilek;

import java.util.Collection;
import java.util.HashSet;

public class RaceResultService {
    private Collection<Client> clients;
    public RaceResultService(Collection<Client> clients){
        this.clients = clients;
    }

    public RaceResultService(){
        this(new HashSet<>());
    }

    public void addSubscriber(Client client){
        clients.add(client);
    }

    public void send(Message message){
        for (Client client: clients){
            client.receive(message);
        }
    }

    public void removeSubscriber(Client client){
        clients.remove(client);
    }
}