package laboratorium.krilek;

public interface IFriendsCollection {
    Person findByName(String name);
    void save(Person p);
}
