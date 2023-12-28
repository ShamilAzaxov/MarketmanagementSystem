package config;

public abstract class SingletonOfFactory {

    private static ListDbFactory listDbFactory;
    public static ListDbFactory getListDbFactory(){
        if (listDbFactory == null) {
            listDbFactory = new ListDbFactory();
        }
        return listDbFactory;
    }
}
